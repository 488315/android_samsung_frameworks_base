package com.android.internal.app;

import android.Manifest;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.PermissionChecker;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.chooser.DisplayResolveInfo;
import com.android.internal.app.chooser.TargetInfo;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.core.pm.PmUtils;
import com.samsung.android.share.SemShareConstants;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes5.dex */
public class ResolverListAdapter extends BaseAdapter {
    private static String PACKAGE_NAME_GOOGLE_MESSAGES = "com.google.android.apps.messaging";
    private static String PACKAGE_NAME_SAMSUNG_MESSAGES = "com.samsung.android.messaging";
    private static final String TAG = "ResolverListAdapter";
    private static ColorMatrixColorFilter sSuspendedMatrixColorFilter;
    private final List<ResolveInfo> mBaseResolveList;
    protected final Context mContext;
    protected DisplayResolveInfo mCopyButtonDri;
    private boolean mFilterLastUsed;
    private final int mIconDpi;
    protected final LayoutInflater mInflater;
    private final Intent[] mInitialIntents;
    private final UserHandle mInitialIntentsUserSpace;
    private final List<Intent> mIntents;
    private final boolean mIsAudioCaptureDevice;
    private boolean mIsTabLoaded;
    protected ResolveInfo mLastChosen;
    private DisplayResolveInfo mOtherProfile;
    private int mPlaceholderCount;
    private final PackageManager mPm;
    private Runnable mPostListReadyRunnable;
    final ResolverListCommunicator mResolverListCommunicator;
    ResolverListController mResolverListController;
    private List<ResolverActivity.ResolvedComponentInfo> mUnfilteredResolveList;
    private int mLastChosenPosition = -1;
    private final Map<DisplayResolveInfo, LoadIconTask> mIconLoaders = new HashMap();
    private final Map<DisplayResolveInfo, LoadLabelTask> mLabelLoaders = new HashMap();
    private String mDefaultSms = null;
    private boolean mMessageAppSkipped = false;
    private String mLastChosenActivityFromPm = null;
    private String mLastChosenPackageFromPm = null;
    private int mLastChosenActivityIndex = -1;
    List<DisplayResolveInfo> mDisplayList = new ArrayList();

    interface ResolverListCommunicator {
        Intent getReplacementIntent(ActivityInfo activityInfo, Intent intent);

        Intent getTargetIntent();

        void onHandlePackagesChanged(ResolverListAdapter resolverListAdapter);

        void onPostListReady(ResolverListAdapter resolverListAdapter, boolean z, boolean z2, boolean z3);

        boolean resolveInfoMatch(ResolveInfo resolveInfo, ResolveInfo resolveInfo2);

        String semGetAppIconTheme();

        int semGetOldItemCount();

        boolean semIsDestroyed();

        boolean semIsFinishing();

        boolean semIsNeedSortingInRebuildList();

        boolean semIsOverlayThemesEnabled();

        boolean semIsSupportsAlwaysUseOption();

        boolean semNeedSortAfterPinned();

        void semOnForceHandlePackagesChanged(ResolverListAdapter resolverListAdapter);

        void semSetNeedSortAfterPinned(boolean z);

        void semSetNeedSortingInRebuildList(boolean z);

        void sendVoiceChoicesIfNeeded();

        boolean shouldGetActivityMetadata();

        default boolean shouldGetOnlyDefaultActivities() {
            return true;
        }

        void updateProfileViewButton();

        boolean useLayoutWithDefault();
    }

    protected boolean alwaysShowSubLabel() {
        return false;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public ResolverListAdapter(Context context, List<Intent> list, Intent[] intentArr, List<ResolveInfo> list2, boolean z, ResolverListController resolverListController, ResolverListCommunicator resolverListCommunicator, boolean z2, UserHandle userHandle) {
        this.mContext = context;
        this.mIntents = list;
        this.mInitialIntents = intentArr;
        this.mBaseResolveList = list2;
        this.mInflater = LayoutInflater.from(context);
        this.mPm = context.getPackageManager();
        this.mFilterLastUsed = z;
        this.mResolverListController = resolverListController;
        this.mResolverListCommunicator = resolverListCommunicator;
        this.mIsAudioCaptureDevice = z2;
        this.mIconDpi = ((ActivityManager) context.getSystemService("activity")).getLauncherLargeIconDensity();
        this.mInitialIntentsUserSpace = userHandle;
    }

    public ResolverListController getResolverListController() {
        return this.mResolverListController;
    }

    public void handlePackagesChanged() {
        this.mResolverListCommunicator.onHandlePackagesChanged(this);
    }

    public void setPlaceholderCount(int i) {
        this.mPlaceholderCount = i;
    }

    public int getPlaceholderCount() {
        return this.mPlaceholderCount;
    }

    boolean semIsComponentEqual(ResolveInfo resolveInfo, ResolveInfo resolveInfo2) {
        ComponentName componentName;
        if (resolveInfo != null) {
            try {
                componentName = new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name);
            } catch (Exception e) {
                Log.e(TAG, "semIsComponentEqual : " + e);
                return false;
            }
        } else {
            componentName = null;
        }
        return componentName.equals(resolveInfo2 != null ? new ComponentName(resolveInfo2.activityInfo.applicationInfo.packageName, resolveInfo2.activityInfo.name) : null);
    }

    public DisplayResolveInfo getFilteredItem() {
        int i;
        if (!this.mFilterLastUsed || (i = this.mLastChosenPosition) < 0) {
            return null;
        }
        return this.mDisplayList.get(i);
    }

    public DisplayResolveInfo getOtherProfile() {
        return this.mOtherProfile;
    }

    public int getFilteredPosition() {
        int i;
        if (!this.mFilterLastUsed || (i = this.mLastChosenPosition) < 0) {
            return -1;
        }
        return i;
    }

    public boolean hasFilteredItem() {
        return this.mFilterLastUsed && this.mLastChosen != null;
    }

    public float getScore(DisplayResolveInfo displayResolveInfo) {
        return this.mResolverListController.getScore(displayResolveInfo);
    }

    public float getScore(TargetInfo targetInfo) {
        return this.mResolverListController.getScore(targetInfo);
    }

    public void updateModel(TargetInfo targetInfo) {
        this.mResolverListController.updateModel(targetInfo);
    }

    public void updateChooserCounts(String str, String str2, UserHandle userHandle) {
        this.mResolverListController.updateChooserCounts(str, userHandle, str2);
    }

    List<ResolverActivity.ResolvedComponentInfo> getUnfilteredResolveList() {
        return this.mUnfilteredResolveList;
    }

    protected boolean rebuildList(boolean z) {
        Trace.beginSection("ResolverListAdapter#rebuildList");
        this.mIsTabLoaded = false;
        this.mLastChosenPosition = -1;
        this.mDefaultSms = Telephony.Sms.getDefaultSmsPackage(this.mContext);
        if (this.mResolverListCommunicator.semIsSupportsAlwaysUseOption()) {
            this.mResolverListController.semSetSupportAlwaysUseOption(true);
        }
        List<ResolverActivity.ResolvedComponentInfo> initialRebuiltResolveList = getInitialRebuiltResolveList();
        this.mUnfilteredResolveList = performPrimaryResolveListFiltering(initialRebuiltResolveList);
        ResolverActivity.ResolvedComponentInfo firstNonCurrentUserResolvedComponentInfo = getFirstNonCurrentUserResolvedComponentInfo(initialRebuiltResolveList);
        updateOtherProfileTreatment(firstNonCurrentUserResolvedComponentInfo);
        if (firstNonCurrentUserResolvedComponentInfo != null) {
            initialRebuiltResolveList.remove(firstNonCurrentUserResolvedComponentInfo);
        }
        List<ResolverActivity.ResolvedComponentInfo> listPerformSecondaryResolveListFiltering = performSecondaryResolveListFiltering(initialRebuiltResolveList, this.mUnfilteredResolveList == initialRebuiltResolveList);
        if (listPerformSecondaryResolveListFiltering != null) {
            this.mUnfilteredResolveList = listPerformSecondaryResolveListFiltering;
        }
        HashMap map = new HashMap();
        ArrayList arrayList = new ArrayList();
        for (ResolverActivity.ResolvedComponentInfo resolvedComponentInfo : initialRebuiltResolveList) {
            String packageName = resolvedComponentInfo.name.getPackageName();
            if (map.containsKey(packageName)) {
                ResolverActivity.ResolvedComponentInfo resolvedComponentInfo2 = (ResolverActivity.ResolvedComponentInfo) map.get(packageName);
                ResolveInfo resolveInfoAt = resolvedComponentInfo.getResolveInfoAt(0);
                ResolveInfo resolveInfoAt2 = resolvedComponentInfo2.getResolveInfoAt(0);
                if (resolveInfoAt != null && resolveInfoAt2 != null) {
                    if (resolveInfoAt2.userHandle.equals(resolveInfoAt.userHandle)) {
                        resolvedComponentInfo2.getSimilarList().add(resolvedComponentInfo);
                    }
                }
            }
            arrayList.add(resolvedComponentInfo);
            resolvedComponentInfo.getSimilarList().add(resolvedComponentInfo);
            map.put(packageName, resolvedComponentInfo);
        }
        boolean zFinishRebuildingListWithFilteredResults = finishRebuildingListWithFilteredResults(arrayList, z);
        Trace.endSection();
        return zFinishRebuildingListWithFilteredResults;
    }

    List<ResolverActivity.ResolvedComponentInfo> getInitialRebuiltResolveList() {
        if (this.mBaseResolveList != null) {
            ArrayList arrayList = new ArrayList();
            this.mResolverListController.addResolveListDedupe(arrayList, this.mResolverListCommunicator.getTargetIntent(), this.mBaseResolveList);
            return arrayList;
        }
        return this.mResolverListController.getResolversForIntent(true, this.mResolverListCommunicator.shouldGetActivityMetadata(), this.mResolverListCommunicator.shouldGetOnlyDefaultActivities(), this.mIntents);
    }

    List<ResolverActivity.ResolvedComponentInfo> performPrimaryResolveListFiltering(List<ResolverActivity.ResolvedComponentInfo> list) {
        ArrayList<ResolverActivity.ResolvedComponentInfo> arrayListFilterIneligibleActivities;
        return (this.mBaseResolveList != null || list == null || (arrayListFilterIneligibleActivities = this.mResolverListController.filterIneligibleActivities(list, true)) == null) ? list : arrayListFilterIneligibleActivities;
    }

    List<ResolverActivity.ResolvedComponentInfo> performSecondaryResolveListFiltering(List<ResolverActivity.ResolvedComponentInfo> list, boolean z) {
        return (list == null || list.isEmpty()) ? list : this.mResolverListController.filterLowPriority(list, z);
    }

    void updateOtherProfileTreatment(ResolverActivity.ResolvedComponentInfo resolvedComponentInfo) {
        this.mLastChosen = null;
        if (resolvedComponentInfo != null) {
            this.mOtherProfile = makeOtherProfileDisplayResolveInfo(this.mContext, resolvedComponentInfo, this.mPm, this.mResolverListCommunicator, this.mIconDpi);
            return;
        }
        this.mOtherProfile = null;
        try {
            ResolveInfo lastChosen = this.mResolverListController.getLastChosen();
            this.mLastChosen = lastChosen;
            if (lastChosen != null) {
                setLastChosenInfo(lastChosen);
            }
        } catch (RemoteException e) {
            Log.d(TAG, "Error calling getLastChosenActivity\n" + e);
        }
    }

    boolean finishRebuildingListWithFilteredResults(List<ResolverActivity.ResolvedComponentInfo> list, boolean z) {
        if (list == null || list.size() < 2) {
            setPlaceholderCount(0);
            processSortedList(list, z);
            return true;
        }
        int size = list.size();
        if (this.mResolverListCommunicator.useLayoutWithDefault()) {
            size--;
        }
        setPlaceholderCount(size);
        if (this.mResolverListCommunicator.semGetOldItemCount() != getPlaceholderCount() || this.mResolverListCommunicator.semNeedSortAfterPinned() || this.mResolverListCommunicator.semIsNeedSortingInRebuildList()) {
            postListReadyRunnable(z, false);
            createSortingTask(z).execute(list);
        }
        this.mResolverListCommunicator.semSetNeedSortAfterPinned(false);
        this.mResolverListCommunicator.semSetNeedSortingInRebuildList(false);
        return false;
    }

    AsyncTask<List<ResolverActivity.ResolvedComponentInfo>, Void, List<ResolverActivity.ResolvedComponentInfo>> createSortingTask(final boolean z) {
        return new AsyncTask<List<ResolverActivity.ResolvedComponentInfo>, Void, List<ResolverActivity.ResolvedComponentInfo>>() { // from class: com.android.internal.app.ResolverListAdapter.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public List<ResolverActivity.ResolvedComponentInfo> doInBackground(List<ResolverActivity.ResolvedComponentInfo>... listArr) {
                Log.d(ResolverListAdapter.TAG, "list up doInBackground!");
                if (ResolverListAdapter.this.mResolverListCommunicator.semIsDestroyed() || ResolverListAdapter.this.mResolverListCommunicator.semIsFinishing()) {
                    Log.w(ResolverListAdapter.TAG, "activity is finished.. stop sorting!");
                } else {
                    ResolverListAdapter.this.mResolverListController.sort(listArr[0]);
                    try {
                        List<ResolverActivity.ResolvedComponentInfo> list = listArr[0];
                        ResolverListAdapter resolverListAdapter = ResolverListAdapter.this;
                        Collections.sort(list, resolverListAdapter.new SemResolverListComparator(resolverListAdapter.mContext));
                    } catch (Exception e) {
                        Log.e(ResolverListAdapter.TAG, "SemResolverListComparator failed!!", e);
                    }
                }
                return listArr[0];
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(List<ResolverActivity.ResolvedComponentInfo> list) {
                Log.d(ResolverListAdapter.TAG, "list up process done!!");
                ResolverListAdapter.this.processSortedList(list, z);
                ResolverListAdapter.this.notifyDataSetChanged();
                if (z) {
                    ResolverListAdapter.this.mResolverListCommunicator.updateProfileViewButton();
                }
            }
        };
    }

    protected void processSortedList(List<ResolverActivity.ResolvedComponentInfo> list, boolean z) {
        int size = list != null ? list.size() : 0;
        Trace.beginSection("ResolverListAdapter#processSortedList:" + size);
        this.mDisplayList.clear();
        if (size != 0) {
            if (this.mInitialIntents != null) {
                int i = 0;
                while (true) {
                    Intent[] intentArr = this.mInitialIntents;
                    if (i >= intentArr.length) {
                        break;
                    }
                    Intent intent = intentArr[i];
                    if (intent != null) {
                        ActivityInfo activityInfoResolveActivityInfo = (intent.getClass() == Intent.class ? intent : new Intent(intent)).resolveActivityInfo(this.mPm, 0);
                        if (activityInfoResolveActivityInfo == null) {
                            Log.w(TAG, "No activity found for " + intent);
                        } else {
                            ResolveInfo resolveInfo = new ResolveInfo();
                            resolveInfo.activityInfo = activityInfoResolveActivityInfo;
                            UserManager userManager = (UserManager) this.mContext.getSystemService("user");
                            if (intent instanceof LabeledIntent) {
                                LabeledIntent labeledIntent = (LabeledIntent) intent;
                                resolveInfo.resolvePackageName = labeledIntent.getSourcePackage();
                                resolveInfo.labelRes = labeledIntent.getLabelResource();
                                resolveInfo.nonLocalizedLabel = labeledIntent.getNonLocalizedLabel();
                                resolveInfo.icon = labeledIntent.getIconResource();
                                resolveInfo.iconResourceId = resolveInfo.icon;
                            }
                            if (userManager.isManagedProfile()) {
                                resolveInfo.noResourceId = true;
                                resolveInfo.icon = 0;
                            }
                            resolveInfo.userHandle = this.mInitialIntentsUserSpace;
                            addResolveInfo(new DisplayResolveInfo(intent, resolveInfo, resolveInfo.loadLabel(this.mPm), null, intent, makePresentationGetter(resolveInfo)));
                        }
                    }
                    i++;
                }
            }
            DisplayResolveInfo displayResolveInfo = this.mCopyButtonDri;
            if (displayResolveInfo != null) {
                addResolveInfo(displayResolveInfo);
            }
            for (ResolverActivity.ResolvedComponentInfo resolvedComponentInfo : list) {
                if (resolvedComponentInfo.getResolveInfoAt(0) != null) {
                    addResolveInfoWithAlternates(resolvedComponentInfo);
                }
            }
        }
        this.mResolverListCommunicator.sendVoiceChoicesIfNeeded();
        postListReadyRunnable(z, true, this.mResolverListCommunicator.semIsNeedSortingInRebuildList());
        this.mResolverListCommunicator.semSetNeedSortAfterPinned(false);
        this.mResolverListCommunicator.semSetNeedSortingInRebuildList(false);
        this.mIsTabLoaded = true;
        Trace.endSection();
    }

    void postListReadyRunnable(boolean z, boolean z2) {
        postListReadyRunnable(z, z2, false);
    }

    void postListReadyRunnable(final boolean z, final boolean z2, final boolean z3) {
        if (this.mPostListReadyRunnable == null) {
            this.mPostListReadyRunnable = new Runnable() { // from class: com.android.internal.app.ResolverListAdapter.2
                @Override // java.lang.Runnable
                public void run() {
                    ResolverListAdapter.this.mResolverListCommunicator.onPostListReady(ResolverListAdapter.this, z, z2, z3);
                    ResolverListAdapter.this.mPostListReadyRunnable = null;
                }
            };
            this.mContext.getMainThreadHandler().post(this.mPostListReadyRunnable);
        }
    }

    private void addResolveInfoWithAlternates(ResolverActivity.ResolvedComponentInfo resolvedComponentInfo) {
        int count = resolvedComponentInfo.getCount();
        boolean z = false;
        Intent intentAt = resolvedComponentInfo.getIntentAt(0);
        ResolveInfo resolveInfoAt = resolvedComponentInfo.getResolveInfoAt(0);
        Intent replacementIntent = this.mResolverListCommunicator.getReplacementIntent(resolveInfoAt.activityInfo, intentAt);
        Intent replacementIntent2 = this.mResolverListCommunicator.getReplacementIntent(resolveInfoAt.activityInfo, this.mResolverListCommunicator.getTargetIntent());
        DisplayResolveInfo displayResolveInfo = new DisplayResolveInfo(intentAt, resolveInfoAt, replacementIntent != null ? replacementIntent : replacementIntent2, makePresentationGetter(resolveInfoAt));
        if (resolvedComponentInfo.getSimilarList().size() > 0) {
            Iterator<ResolverActivity.ResolvedComponentInfo> it = resolvedComponentInfo.getSimilarList().iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                ResolveInfo resolveInfoAt2 = it.next().getResolveInfoAt(0);
                DisplayResolveInfo displayResolveInfo2 = new DisplayResolveInfo(intentAt, resolveInfoAt2, replacementIntent != null ? replacementIntent : replacementIntent2, makePresentationGetter(resolveInfoAt2));
                displayResolveInfo2.setPinned(resolvedComponentInfo.isPinned());
                displayResolveInfo.getSimilarList().add(displayResolveInfo2);
                Log.d(TAG, "driInside.mDisplayLabel->" + ((Object) displayResolveInfo2.getDisplayLabel()) + " driInside.mExtendedInfo;" + ((Object) displayResolveInfo2.getExtendedInfo()) + " driInside.mResolveInfo" + displayResolveInfo2.getResolveInfo());
                z2 = true;
            }
            z = z2;
        }
        displayResolveInfo.setPinned(resolvedComponentInfo.isPinned());
        if (resolvedComponentInfo.isPinned()) {
            Log.i(TAG, "Pinned item: " + resolvedComponentInfo.name);
        }
        if (!needToHideSmsPackage(displayResolveInfo) || this.mResolverListCommunicator.semIsSupportsAlwaysUseOption()) {
            addResolveInfo(displayResolveInfo);
        }
        if (replacementIntent == intentAt) {
            for (int i = 1; i < count; i++) {
                displayResolveInfo.addAlternateSourceIntent(resolvedComponentInfo.getIntentAt(i));
            }
        }
        updateLastChosenPosition(resolveInfoAt, Boolean.valueOf(z));
    }

    private void updateLastChosenPosition(ResolveInfo resolveInfo, Boolean bool) {
        ResolveInfo resolveInfo2;
        if (this.mOtherProfile != null) {
            this.mLastChosenPosition = -1;
        } else if (bool.booleanValue() && (resolveInfo2 = this.mLastChosen) != null && resolveInfo2.activityInfo.packageName.equals(resolveInfo.activityInfo.packageName)) {
            this.mLastChosenPosition = this.mDisplayList.size() - 1;
            setLastChosenInfo(this.mLastChosen);
        }
    }

    private void addResolveInfo(DisplayResolveInfo displayResolveInfo) {
        if (displayResolveInfo == null || displayResolveInfo.getResolveInfo() == null || displayResolveInfo.getResolveInfo().targetUserId != -2 || !shouldAddResolveInfo(displayResolveInfo)) {
            return;
        }
        this.mDisplayList.add(displayResolveInfo);
        Log.i(TAG, "Add DisplayResolveInfo component: " + displayResolveInfo.getResolvedComponentName() + ", intent component: " + displayResolveInfo.getResolvedIntent().getComponent());
    }

    protected boolean shouldAddResolveInfo(DisplayResolveInfo displayResolveInfo) {
        if (!this.mMessageAppSkipped && !this.mResolverListCommunicator.semIsSupportsAlwaysUseOption() && needToHideSmsPackage(displayResolveInfo)) {
            return false;
        }
        for (DisplayResolveInfo displayResolveInfo2 : this.mDisplayList) {
            if (this.mResolverListCommunicator.resolveInfoMatch(displayResolveInfo.getResolveInfo(), displayResolveInfo2.getResolveInfo()) && !SemDualAppManager.isDualAppId(UserHandle.getUserId(displayResolveInfo.getResolveInfo().activityInfo.applicationInfo.uid)) && !SemDualAppManager.isDualAppId(UserHandle.getUserId(displayResolveInfo2.getResolveInfo().activityInfo.applicationInfo.uid))) {
                return false;
            }
        }
        return true;
    }

    public ResolveInfo resolveInfoForPosition(int i, boolean z) {
        TargetInfo targetInfoTargetInfoForPosition = targetInfoForPosition(i, z);
        if (targetInfoTargetInfoForPosition != null) {
            return targetInfoTargetInfoForPosition.getResolveInfo();
        }
        return null;
    }

    public TargetInfo targetInfoForPosition(int i, boolean z) {
        if (z) {
            return getItem(i);
        }
        if (this.mDisplayList.size() > i) {
            return this.mDisplayList.get(i);
        }
        return null;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<DisplayResolveInfo> list = this.mDisplayList;
        if (list == null || list.isEmpty()) {
            return this.mPlaceholderCount;
        }
        return this.mDisplayList.size();
    }

    public int getUnfilteredCount() {
        return this.mDisplayList.size();
    }

    @Override // android.widget.Adapter
    public TargetInfo getItem(int i) {
        if (this.mDisplayList.size() > i) {
            return this.mDisplayList.get(i);
        }
        return null;
    }

    public int getDisplayResolveInfoCount() {
        return this.mDisplayList.size();
    }

    public DisplayResolveInfo getDisplayResolveInfo(int i) {
        return this.mDisplayList.get(i);
    }

    @Override // android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
        if (view == null) {
            view = createView(viewGroup);
        }
        onBindView(view, getItem(i), i);
        return view;
    }

    public final View createView(ViewGroup viewGroup) {
        View viewOnCreateView = onCreateView(viewGroup);
        ViewHolder viewHolder = new ViewHolder(viewOnCreateView);
        semSetTextSizeByMaxFontScale(viewHolder.text, R.dimen.sem_resolver_item_text_size);
        semSetTextSizeByMaxFontScale(viewHolder.text2, R.dimen.sem_resolver_item_text_size_secondary);
        viewOnCreateView.setTag(viewHolder);
        return viewOnCreateView;
    }

    View onCreateView(ViewGroup viewGroup) {
        Context context = this.mContext;
        if ((context instanceof ResolverActivity) && ((ResolverActivity) context).mIsAiAssist) {
            return this.mInflater.inflate(R.layout.sem_resolver_ai_list_item, viewGroup, false);
        }
        return this.mInflater.inflate(R.layout.sem_resolver_grid_item, viewGroup, false);
    }

    public final void bindView(int i, View view) throws Resources.NotFoundException {
        onBindView(view, getItem(i), i);
    }

    protected void onBindView(View view, TargetInfo targetInfo, int i) throws Resources.NotFoundException {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (targetInfo == null) {
            viewHolder.icon.setImageDrawable(this.mContext.getDrawable(R.drawable.resolver_icon_placeholder));
            viewHolder.bindLabel("", "", false);
            return;
        }
        if (targetInfo instanceof DisplayResolveInfo) {
            DisplayResolveInfo displayResolveInfo = (DisplayResolveInfo) targetInfo;
            if (displayResolveInfo.hasDisplayLabel()) {
                viewHolder.bindLabel(displayResolveInfo.getDisplayLabel(), displayResolveInfo.getExtendedInfo(), alwaysShowSubLabel());
            } else {
                viewHolder.bindLabel("", "", false);
                loadLabel(displayResolveInfo);
            }
            viewHolder.bindIcon(targetInfo);
            if (displayResolveInfo.hasDisplayIcon()) {
                return;
            }
            if (!this.mResolverListCommunicator.semIsSupportsAlwaysUseOption() || (this.mResolverListCommunicator.semIsSupportsAlwaysUseOption() && (i == 0 || i == 1))) {
                try {
                    new LoadIconTask((DisplayResolveInfo) targetInfo, viewHolder).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                    return;
                } catch (RejectedExecutionException e) {
                    Log.e(TAG, "LoadIconTask failed!!", e);
                    return;
                }
            }
            loadIcon(displayResolveInfo);
        }
    }

    protected LoadLabelTask getLoadLabelTask(DisplayResolveInfo displayResolveInfo, ViewHolder viewHolder) {
        return new LoadLabelTask(displayResolveInfo, viewHolder);
    }

    protected final void loadIcon(DisplayResolveInfo displayResolveInfo) {
        if (this.mIconLoaders.get(displayResolveInfo) == null) {
            LoadIconTask loadIconTask = new LoadIconTask(displayResolveInfo);
            this.mIconLoaders.put(displayResolveInfo, loadIconTask);
            loadIconTask.execute(new Void[0]);
        }
    }

    protected void onIconLoaded(DisplayResolveInfo displayResolveInfo) {
        notifyDataSetChanged();
    }

    private void loadLabel(DisplayResolveInfo displayResolveInfo) {
        if (this.mLabelLoaders.get(displayResolveInfo) == null) {
            LoadLabelTask loadLabelTaskCreateLoadLabelTask = createLoadLabelTask(displayResolveInfo);
            this.mLabelLoaders.put(displayResolveInfo, loadLabelTaskCreateLoadLabelTask);
            loadLabelTaskCreateLoadLabelTask.execute(new Void[0]);
        }
    }

    protected LoadLabelTask createLoadLabelTask(DisplayResolveInfo displayResolveInfo) {
        return new LoadLabelTask(displayResolveInfo);
    }

    public void onDestroy() {
        if (this.mPostListReadyRunnable != null) {
            this.mContext.getMainThreadHandler().removeCallbacks(this.mPostListReadyRunnable);
            this.mPostListReadyRunnable = null;
        }
        ResolverListController resolverListController = this.mResolverListController;
        if (resolverListController != null) {
            resolverListController.destroy();
        }
        cancelTasks(this.mIconLoaders.values());
        cancelTasks(this.mLabelLoaders.values());
        this.mIconLoaders.clear();
        this.mLabelLoaders.clear();
    }

    private <T extends AsyncTask> void cancelTasks(Collection<T> collection) {
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            it.next().cancel(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ColorMatrixColorFilter getSuspendedColorMatrix() {
        if (sSuspendedMatrixColorFilter == null) {
            ColorMatrix colorMatrix = new ColorMatrix();
            float[] array = colorMatrix.getArray();
            array[0] = 0.5f;
            array[6] = 0.5f;
            array[12] = 0.5f;
            float f = 127;
            array[4] = f;
            array[9] = f;
            array[14] = f;
            ColorMatrix colorMatrix2 = new ColorMatrix();
            colorMatrix2.setSaturation(0.0f);
            colorMatrix2.preConcat(colorMatrix);
            sSuspendedMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix2);
        }
        return sSuspendedMatrixColorFilter;
    }

    ActivityInfoPresentationGetter makePresentationGetter(ActivityInfo activityInfo) {
        return new ActivityInfoPresentationGetter(this.mContext, this.mIconDpi, activityInfo);
    }

    ResolveInfoPresentationGetter makePresentationGetter(ResolveInfo resolveInfo) {
        return new ResolveInfoPresentationGetter(this.mContext, this.mIconDpi, resolveInfo);
    }

    Drawable loadIconForResolveInfo(ResolveInfo resolveInfo) {
        return makePresentationGetter(resolveInfo).getIcon(resolveInfo.userHandle);
    }

    void loadFilteredItemIconTaskAsync(final ImageView imageView) {
        final DisplayResolveInfo filteredItem = getFilteredItem();
        if (imageView == null || filteredItem == null) {
            return;
        }
        new AsyncTask<Void, Void, Drawable>() { // from class: com.android.internal.app.ResolverListAdapter.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public Drawable doInBackground(Void... voidArr) {
                return ResolverListAdapter.this.loadIconForResolveInfo(filteredItem.getResolveInfo());
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(Drawable drawable) {
                imageView.setImageDrawable(drawable);
            }
        }.execute(new Void[0]);
    }

    public UserHandle getUserHandle() {
        return this.mResolverListController.getUserHandle();
    }

    protected List<ResolverActivity.ResolvedComponentInfo> getResolversForUser(UserHandle userHandle) {
        return this.mResolverListController.getResolversForIntentAsUser(true, this.mResolverListCommunicator.shouldGetActivityMetadata(), this.mResolverListCommunicator.shouldGetOnlyDefaultActivities(), this.mIntents, userHandle);
    }

    protected List<Intent> getIntents() {
        return this.mIntents;
    }

    protected boolean isTabLoaded() {
        return this.mIsTabLoaded;
    }

    protected void markTabLoaded() {
        this.mIsTabLoaded = true;
    }

    private static ResolverActivity.ResolvedComponentInfo getFirstNonCurrentUserResolvedComponentInfo(List<ResolverActivity.ResolvedComponentInfo> list) {
        if (list == null) {
            return null;
        }
        for (ResolverActivity.ResolvedComponentInfo resolvedComponentInfo : list) {
            if (resolvedComponentInfo.getResolveInfoAt(0).targetUserId != -2) {
                return resolvedComponentInfo;
            }
        }
        return null;
    }

    private static DisplayResolveInfo makeOtherProfileDisplayResolveInfo(Context context, ResolverActivity.ResolvedComponentInfo resolvedComponentInfo, PackageManager packageManager, ResolverListCommunicator resolverListCommunicator, int i) {
        ResolveInfo resolveInfoAt = resolvedComponentInfo.getResolveInfoAt(0);
        Intent replacementIntent = resolverListCommunicator.getReplacementIntent(resolveInfoAt.activityInfo, resolvedComponentInfo.getIntentAt(0));
        Intent replacementIntent2 = resolverListCommunicator.getReplacementIntent(resolveInfoAt.activityInfo, resolverListCommunicator.getTargetIntent());
        return new DisplayResolveInfo(resolvedComponentInfo.getIntentAt(0), resolveInfoAt, resolveInfoAt.loadLabel(packageManager), resolveInfoAt.loadLabel(packageManager), replacementIntent != null ? replacementIntent : replacementIntent2, new ResolveInfoPresentationGetter(context, i, resolveInfoAt));
    }

    public static class ViewHolder {
        public ImageView badge;
        public Drawable defaultItemViewBackground;
        public ImageView icon;
        public View itemView;
        public TextView text;
        public TextView text2;

        public ViewHolder(View view) {
            this.itemView = view;
            this.defaultItemViewBackground = view.getBackground();
            this.text = (TextView) view.findViewById(16908308);
            this.text2 = (TextView) view.findViewById(16908309);
            this.icon = (ImageView) view.findViewById(16908294);
            this.badge = (ImageView) view.findViewById(R.id.target_badge);
        }

        public void bindLabel(CharSequence charSequence, CharSequence charSequence2, boolean z) throws Resources.NotFoundException {
            this.text.lambda$setTextAsync$0(charSequence);
            if (TextUtils.equals(charSequence, charSequence2)) {
                charSequence2 = null;
            }
            this.text2.lambda$setTextAsync$0(charSequence2);
            if (z || charSequence2 != null) {
                this.text2.setVisibility(0);
                this.text.setLines(1);
            } else {
                this.text2.setVisibility(8);
                this.text.setLines(2);
            }
            this.itemView.setContentDescription(null);
        }

        public void updateContentDescription(String str) throws Resources.NotFoundException {
            this.itemView.setContentDescription(str);
        }

        public void bindIcon(TargetInfo targetInfo) {
            this.icon.setImageDrawable(targetInfo.getDisplayIcon(this.itemView.getContext()));
            if (targetInfo.isSuspended()) {
                this.icon.setColorFilter(ResolverListAdapter.getSuspendedColorMatrix());
            } else {
                this.icon.setColorFilter((ColorFilter) null);
            }
        }
    }

    protected class LoadLabelTask extends AsyncTask<Void, Void, CharSequence[]> {
        private final DisplayResolveInfo mDisplayResolveInfo;
        private ViewHolder mHolder;

        protected LoadLabelTask(DisplayResolveInfo displayResolveInfo, ViewHolder viewHolder) {
            this.mDisplayResolveInfo = displayResolveInfo;
            this.mHolder = viewHolder;
        }

        protected LoadLabelTask(DisplayResolveInfo displayResolveInfo) {
            this.mDisplayResolveInfo = displayResolveInfo;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public CharSequence[] doInBackground(Void... voidArr) {
            ResolveInfoPresentationGetter resolveInfoPresentationGetterMakePresentationGetter = ResolverListAdapter.this.makePresentationGetter(this.mDisplayResolveInfo.getResolveInfo());
            if (ResolverListAdapter.this.mIsAudioCaptureDevice) {
                ActivityInfo activityInfo = this.mDisplayResolveInfo.getResolveInfo().activityInfo;
                if (PermissionChecker.checkPermissionForPreflight(ResolverListAdapter.this.mContext, Manifest.permission.RECORD_AUDIO, -1, activityInfo.applicationInfo.uid, activityInfo.packageName) != 0) {
                    return new CharSequence[]{resolveInfoPresentationGetterMakePresentationGetter.getLabel(), ResolverListAdapter.this.mContext.getString(R.string.usb_device_resolve_prompt_warn)};
                }
            }
            return new CharSequence[]{resolveInfoPresentationGetterMakePresentationGetter.getLabel(), resolveInfoPresentationGetterMakePresentationGetter.getSubLabel()};
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(CharSequence[] charSequenceArr) {
            if (this.mDisplayResolveInfo.hasDisplayLabel()) {
                return;
            }
            if (this.mDisplayResolveInfo.getSimilarList().size() > 1) {
                for (int i = 0; i < this.mDisplayResolveInfo.getSimilarList().size(); i++) {
                    if (ResolverListAdapter.this.semIsComponentEqual(this.mDisplayResolveInfo.getSimilarList().get(i).getResolveInfo(), ResolverListAdapter.this.mLastChosen)) {
                        ResolverListAdapter.this.mLastChosenActivityIndex = i;
                    }
                }
            }
            if (ResolverListAdapter.this.mLastChosen != null) {
                Log.i(ResolverListAdapter.TAG, "ClassName : " + ResolverListAdapter.this.mLastChosen.activityInfo.name + ", mLastChosenActivityIndex : " + ResolverListAdapter.this.mLastChosenActivityIndex + ", mDisplayResolveInfo.getSimilarList().size() : " + this.mDisplayResolveInfo.getSimilarList().size());
            }
            this.mDisplayResolveInfo.setDisplayLabel(charSequenceArr[0]);
            DisplayResolveInfo displayResolveInfo = this.mDisplayResolveInfo;
            displayResolveInfo.setExtendedInfo((displayResolveInfo.getSimilarList().size() <= 1 || ResolverListAdapter.this.mLastChosenActivityIndex < 0) ? charSequenceArr[1] : ResolverListAdapter.this.getLastChosenActivity());
            ResolverListAdapter.this.notifyDataSetChanged();
        }
    }

    class LoadIconTask extends AsyncTask<Void, Void, Drawable> {
        boolean mCheckViewHolder;
        protected final DisplayResolveInfo mDisplayResolveInfo;
        private ViewHolder mHolder;
        private final ResolveInfo mResolveInfo;

        LoadIconTask(DisplayResolveInfo displayResolveInfo, ViewHolder viewHolder) {
            this.mDisplayResolveInfo = displayResolveInfo;
            this.mResolveInfo = displayResolveInfo.getResolveInfo();
            this.mHolder = viewHolder;
        }

        LoadIconTask(DisplayResolveInfo displayResolveInfo) {
            this.mDisplayResolveInfo = displayResolveInfo;
            this.mResolveInfo = displayResolveInfo.getResolveInfo();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Drawable doInBackground(Void... voidArr) {
            return ResolverListAdapter.this.loadIconForResolveInfo(this.mResolveInfo);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.AsyncTask
        public void onPostExecute(Drawable drawable) {
            DisplayResolveInfo otherProfile = ResolverListAdapter.this.getOtherProfile();
            DisplayResolveInfo displayResolveInfo = this.mDisplayResolveInfo;
            if (otherProfile == displayResolveInfo) {
                ResolverListAdapter.this.mResolverListCommunicator.updateProfileViewButton();
            } else {
                if (displayResolveInfo.hasDisplayIcon()) {
                    return;
                }
                this.mDisplayResolveInfo.setDisplayIcon(drawable);
                ResolverListAdapter.this.onIconLoaded(this.mDisplayResolveInfo);
            }
        }
    }

    public static class ResolveInfoPresentationGetter extends ActivityInfoPresentationGetter {
        private final ResolveInfo mRi;

        public ResolveInfoPresentationGetter(Context context, int i, ResolveInfo resolveInfo) {
            super(context, i, resolveInfo.activityInfo);
            this.mRi = resolveInfo;
        }

        @Override // com.android.internal.app.ResolverListAdapter.ActivityInfoPresentationGetter, com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        Drawable getIconSubstituteInternal() {
            Drawable drawableLoadIconFromResource = null;
            try {
                if (this.mRi.resolvePackageName != null && this.mRi.icon != 0) {
                    drawableLoadIconFromResource = loadIconFromResource(this.mPm.getResourcesForApplication(this.mRi.resolvePackageName), this.mRi.icon);
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(ResolverListAdapter.TAG, "SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON permission granted but couldn't find resources for package", e);
            }
            return drawableLoadIconFromResource == null ? super.getIconSubstituteInternal() : drawableLoadIconFromResource;
        }

        @Override // com.android.internal.app.ResolverListAdapter.ActivityInfoPresentationGetter, com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        String getAppSubLabelInternal() {
            return this.mRi.loadLabel(this.mPm).toString();
        }

        @Override // com.android.internal.app.ResolverListAdapter.ActivityInfoPresentationGetter, com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        String getAppLabelForSubstitutePermission() {
            return this.mRi.getComponentInfo().loadLabel(this.mPm).toString();
        }
    }

    public static class ActivityInfoPresentationGetter extends TargetPresentationGetter {
        private final ActivityInfo mActivityInfo;

        @Override // com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        public /* bridge */ /* synthetic */ Drawable getIcon(UserHandle userHandle) {
            return super.getIcon(userHandle);
        }

        @Override // com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        public /* bridge */ /* synthetic */ Bitmap getIconBitmap(UserHandle userHandle) {
            return super.getIconBitmap(userHandle);
        }

        @Override // com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        public /* bridge */ /* synthetic */ String getLabel() {
            return super.getLabel();
        }

        @Override // com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        public /* bridge */ /* synthetic */ String getSubLabel() {
            return super.getSubLabel();
        }

        @Override // com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        public /* bridge */ /* synthetic */ UserHandle getUserHandle(int i) {
            return super.getUserHandle(i);
        }

        public ActivityInfoPresentationGetter(Context context, int i, ActivityInfo activityInfo) {
            super(context, i, activityInfo.applicationInfo);
            this.mActivityInfo = activityInfo;
        }

        @Override // com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        Drawable getIconSubstituteInternal() {
            try {
                if (this.mActivityInfo.icon != 0) {
                    return loadIconFromResource(this.mPm.getResourcesForApplication(this.mActivityInfo.applicationInfo), this.mActivityInfo.icon);
                }
                return null;
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(ResolverListAdapter.TAG, "SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON permission granted but couldn't find resources for package", e);
                return null;
            }
        }

        @Override // com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        String getAppSubLabelInternal() {
            return (String) this.mActivityInfo.loadLabel(this.mPm);
        }

        @Override // com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        String getAppLabelForSubstitutePermission() {
            return getAppSubLabelInternal();
        }
    }

    private static abstract class TargetPresentationGetter {
        private final ApplicationInfo mAi;
        private Context mCtx;
        private final boolean mHasSubstitutePermission;
        private final int mIconDpi;
        protected PackageManager mPm;

        abstract String getAppLabelForSubstitutePermission();

        abstract String getAppSubLabelInternal();

        abstract Drawable getIconSubstituteInternal();

        TargetPresentationGetter(Context context, int i, ApplicationInfo applicationInfo) {
            this.mCtx = context;
            this.mPm = context.getPackageManager();
            this.mAi = applicationInfo;
            this.mIconDpi = i;
            if ((context instanceof ResolverActivity) && ((ResolverActivity) context).mIsAiAssist) {
                this.mHasSubstitutePermission = true;
            } else {
                this.mHasSubstitutePermission = this.mPm.checkPermission(Manifest.permission.SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON, applicationInfo.packageName) == 0;
            }
        }

        public Drawable getIcon(UserHandle userHandle) {
            return new BitmapDrawable(this.mCtx.getResources(), getIconBitmap(userHandle));
        }

        public Bitmap getIconBitmap(UserHandle userHandle) {
            Drawable iconSubstituteInternal = this.mHasSubstitutePermission ? getIconSubstituteInternal() : null;
            Object obj = this.mCtx;
            if ((!(obj instanceof ResolverListCommunicator) || !((ResolverListCommunicator) obj).semIsOverlayThemesEnabled() || ((ResolverListCommunicator) this.mCtx).semGetAppIconTheme() == null) && !PmUtils.supportLiveIcon(this.mAi, this.mCtx)) {
                if (iconSubstituteInternal == null) {
                    try {
                        if (this.mAi.icon != 0) {
                            iconSubstituteInternal = loadIconFromResource(this.mPm.getResourcesForApplication(this.mAi), this.mAi.icon);
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.e(ResolverListAdapter.TAG, "Failed to load icon", e);
                    }
                }
                if (iconSubstituteInternal == null) {
                    iconSubstituteInternal = this.mAi.loadIcon(this.mPm);
                }
                if (iconSubstituteInternal != null && this.mPm.semShouldPackIntoIconTray(this.mAi.packageName)) {
                    iconSubstituteInternal = this.mPm.semGetDrawableForIconTray(iconSubstituteInternal, 48);
                }
            } else if (iconSubstituteInternal == null) {
                iconSubstituteInternal = this.mAi.loadUnbadgedIcon(this.mPm);
            } else if (this.mPm.semShouldPackIntoIconTray(this.mAi.packageName)) {
                iconSubstituteInternal = this.mPm.semGetDrawableForIconTray(iconSubstituteInternal, 1);
            }
            return drawableToBitmap(this.mPm.getUserBadgedIcon(iconSubstituteInternal, getUserHandle(this.mAi.uid)));
        }

        public UserHandle getUserHandle(int i) {
            return UserHandle.getUserHandleForUid(i);
        }

        public String getLabel() {
            String appLabelForSubstitutePermission = this.mHasSubstitutePermission ? getAppLabelForSubstitutePermission() : null;
            return appLabelForSubstitutePermission == null ? (String) this.mAi.loadLabel(this.mPm) : appLabelForSubstitutePermission;
        }

        public String getSubLabel() {
            if (this.mHasSubstitutePermission) {
                String appSubLabelInternal = getAppSubLabelInternal();
                if (TextUtils.isEmpty(appSubLabelInternal) || TextUtils.equals(appSubLabelInternal, getLabel())) {
                    return null;
                }
                return appSubLabelInternal;
            }
            return getAppSubLabelInternal();
        }

        protected String loadLabelFromResource(Resources resources, int i) {
            return resources.getString(i);
        }

        protected Drawable loadIconFromResource(Resources resources, int i) {
            try {
                return resources.getDrawableForDensity(i, this.mIconDpi);
            } catch (Resources.NotFoundException e) {
                Log.e(ResolverListAdapter.TAG, "Resource not found", e);
                return null;
            }
        }

        private Bitmap drawableToBitmap(Drawable drawable) {
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmapCreateBitmap;
        }
    }

    public void semForceHandlePackagesChanged() {
        this.mResolverListCommunicator.semOnForceHandlePackagesChanged(this);
    }

    void semSetTextSizeByMaxFontScale(TextView textView, int i) {
        if (textView != null) {
            textView.setTextSize(0, this.mContext.getResources().getDimensionPixelSize(i) * semGetFontScale());
        }
    }

    float semGetFontScale() {
        float f = this.mContext.getResources().getConfiguration().fontScale;
        if (f > 1.2f) {
            return 1.2f;
        }
        return f;
    }

    private String StringReplaceForSpace(String str) {
        return str.replaceAll(" ", "").replaceAll("\\s", "").toLowerCase();
    }

    private boolean needToHideSmsPackage(DisplayResolveInfo displayResolveInfo) {
        if (!TextUtils.isEmpty(this.mDefaultSms)) {
            if (PACKAGE_NAME_SAMSUNG_MESSAGES.equals(this.mDefaultSms)) {
                if (!PACKAGE_NAME_GOOGLE_MESSAGES.equals(displayResolveInfo.getResolveInfo().activityInfo.packageName)) {
                    return false;
                }
                Log.i(TAG, "skip add " + displayResolveInfo.getResolveInfo().activityInfo.packageName + ". Default SMS package is " + this.mDefaultSms);
                this.mMessageAppSkipped = true;
                return true;
            }
            if (!PACKAGE_NAME_GOOGLE_MESSAGES.equals(this.mDefaultSms) || !PACKAGE_NAME_SAMSUNG_MESSAGES.equals(displayResolveInfo.getResolveInfo().activityInfo.packageName)) {
                return false;
            }
            Log.i(TAG, "skip add " + displayResolveInfo.getResolveInfo().activityInfo.packageName + ". Default SMS package is " + this.mDefaultSms);
            this.mMessageAppSkipped = true;
            return true;
        }
        Log.i(TAG, "no default sms");
        this.mMessageAppSkipped = true;
        return false;
    }

    private void setLastChosenInfo(ResolveInfo resolveInfo) {
        this.mLastChosenActivityFromPm = resolveInfo.loadLabel(this.mPm).toString();
        this.mLastChosenPackageFromPm = resolveInfo.getComponentInfo().packageName;
    }

    public String getLastChosenActivity() {
        if (TextUtils.isEmpty(this.mLastChosenActivityFromPm)) {
            return null;
        }
        return this.mLastChosenActivityFromPm;
    }

    public String getLastChosenPackage() {
        if (TextUtils.isEmpty(this.mLastChosenPackageFromPm)) {
            return null;
        }
        return this.mLastChosenPackageFromPm;
    }

    public int getLastChosenActivityIndex() {
        return this.mLastChosenActivityIndex;
    }

    class SemResolverListComparator implements Comparator<ResolverActivity.ResolvedComponentInfo> {
        Collator mCollator;

        SemResolverListComparator(Context context) {
            this.mCollator = Collator.getInstance(context.getResources().getConfiguration().locale);
        }

        @Override // java.util.Comparator
        public final int compare(ResolverActivity.ResolvedComponentInfo resolvedComponentInfo, ResolverActivity.ResolvedComponentInfo resolvedComponentInfo2) {
            ResolveInfo resolveInfoAt = resolvedComponentInfo.getResolveInfoAt(0);
            ResolveInfo resolveInfoAt2 = resolvedComponentInfo2.getResolveInfoAt(0);
            ApplicationInfo applicationInfo = resolveInfoAt.activityInfo.applicationInfo;
            ApplicationInfo applicationInfo2 = resolveInfoAt2.activityInfo.applicationInfo;
            if (resolveInfoAt.filter != null && resolveInfoAt.filter.getHosts() != null && resolveInfoAt.filter.getHosts().length > 0) {
                return -1;
            }
            if (resolveInfoAt2.filter != null && resolveInfoAt2.filter.getHosts() != null && resolveInfoAt2.filter.getHosts().length > 0) {
                return 1;
            }
            int i = applicationInfo.metaData != null ? applicationInfo.metaData.getInt(SemShareConstants.METADATA_RESOLVER_RANKING_PRIORITY_KEY, 0) : 0;
            int i2 = applicationInfo2.metaData != null ? applicationInfo2.metaData.getInt(SemShareConstants.METADATA_RESOLVER_RANKING_PRIORITY_KEY, 0) : 0;
            if (i != i2) {
                return i2 - i;
            }
            return this.mCollator.compare(ResolverListAdapter.this.makePresentationGetter(resolveInfoAt).getLabel().trim(), ResolverListAdapter.this.makePresentationGetter(resolveInfoAt2).getLabel().trim());
        }
    }
}
