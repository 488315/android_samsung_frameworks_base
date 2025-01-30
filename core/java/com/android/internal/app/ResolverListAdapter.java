package com.android.internal.app;

import android.Manifest;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.PermissionChecker;
import android.content.p002pm.ActivityInfo;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.LabeledIntent;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.p009os.AsyncTask;
import android.p009os.RemoteException;
import android.p009os.Trace;
import android.p009os.UserHandle;
import android.p009os.UserManager;
import android.provider.Settings;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.C4337R;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.chooser.DisplayResolveInfo;
import com.android.internal.app.chooser.TargetInfo;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.share.SemShareConstants;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes4.dex */
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
    private final SemPersonaManager mSpm;
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

    public ResolverListAdapter(Context context, List<Intent> payloadIntents, Intent[] initialIntents, List<ResolveInfo> rList, boolean filterLastUsed, ResolverListController resolverListController, ResolverListCommunicator resolverListCommunicator, boolean isAudioCaptureDevice, UserHandle initialIntentsUserSpace) {
        this.mContext = context;
        this.mIntents = payloadIntents;
        this.mInitialIntents = initialIntents;
        this.mBaseResolveList = rList;
        this.mInflater = LayoutInflater.from(context);
        this.mPm = context.getPackageManager();
        this.mSpm = (SemPersonaManager) context.getSystemService(SemPersonaManager.class);
        this.mFilterLastUsed = filterLastUsed;
        this.mResolverListController = resolverListController;
        this.mResolverListCommunicator = resolverListCommunicator;
        this.mIsAudioCaptureDevice = isAudioCaptureDevice;
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        this.mIconDpi = am.getLauncherLargeIconDensity();
        this.mInitialIntentsUserSpace = initialIntentsUserSpace;
    }

    public ResolverListController getResolverListController() {
        return this.mResolverListController;
    }

    public void handlePackagesChanged() {
        this.mResolverListCommunicator.onHandlePackagesChanged(this);
    }

    public void setPlaceholderCount(int count) {
        this.mPlaceholderCount = count;
    }

    public int getPlaceholderCount() {
        return this.mPlaceholderCount;
    }

    boolean semIsComponentEqual(ResolveInfo r1, ResolveInfo r2) {
        ComponentName c1;
        if (r1 == null) {
            c1 = null;
        } else {
            try {
                c1 = new ComponentName(r1.activityInfo.applicationInfo.packageName, r1.activityInfo.name);
            } catch (Exception e) {
                Log.m96e(TAG, "semIsComponentEqual : " + e);
                return false;
            }
        }
        ComponentName c2 = r2 != null ? new ComponentName(r2.activityInfo.applicationInfo.packageName, r2.activityInfo.name) : null;
        return c1.equals(c2);
    }

    public DisplayResolveInfo getFilteredItem() {
        int i;
        if (this.mFilterLastUsed && (i = this.mLastChosenPosition) >= 0) {
            return this.mDisplayList.get(i);
        }
        return null;
    }

    public DisplayResolveInfo getOtherProfile() {
        return this.mOtherProfile;
    }

    public int getFilteredPosition() {
        int i;
        if (this.mFilterLastUsed && (i = this.mLastChosenPosition) >= 0) {
            return i;
        }
        return -1;
    }

    public boolean hasFilteredItem() {
        return this.mFilterLastUsed && this.mLastChosen != null;
    }

    public float getScore(DisplayResolveInfo target) {
        return this.mResolverListController.getScore(target);
    }

    public float getScore(TargetInfo targetInfo) {
        return this.mResolverListController.getScore(targetInfo);
    }

    public void updateModel(TargetInfo targetInfo) {
        this.mResolverListController.updateModel(targetInfo);
    }

    public void updateChooserCounts(String packageName, String action, UserHandle userHandle) {
        this.mResolverListController.updateChooserCounts(packageName, userHandle, action);
    }

    List<ResolverActivity.ResolvedComponentInfo> getUnfilteredResolveList() {
        return this.mUnfilteredResolveList;
    }

    protected boolean rebuildList(boolean doPostProcessing) {
        Trace.beginSection("ResolverListAdapter#rebuildList");
        this.mIsTabLoaded = false;
        this.mLastChosenPosition = -1;
        this.mDefaultSms = Telephony.Sms.getDefaultSmsPackage(this.mContext);
        if (this.mResolverListCommunicator.semIsSupportsAlwaysUseOption()) {
            this.mResolverListController.semSetSupportAlwaysUseOption(true);
        }
        List<ResolverActivity.ResolvedComponentInfo> currentResolveList = getInitialRebuiltResolveList();
        this.mUnfilteredResolveList = performPrimaryResolveListFiltering(currentResolveList);
        ResolverActivity.ResolvedComponentInfo otherProfileInfo = getFirstNonCurrentUserResolvedComponentInfo(currentResolveList);
        updateOtherProfileTreatment(otherProfileInfo);
        if (otherProfileInfo != null) {
            currentResolveList.remove(otherProfileInfo);
        }
        boolean needsCopyOfUnfiltered = this.mUnfilteredResolveList == currentResolveList;
        List<ResolverActivity.ResolvedComponentInfo> originalList = performSecondaryResolveListFiltering(currentResolveList, needsCopyOfUnfiltered);
        if (originalList != null) {
            this.mUnfilteredResolveList = originalList;
        }
        HashMap<String, ResolverActivity.ResolvedComponentInfo> map = new HashMap<>();
        List<ResolverActivity.ResolvedComponentInfo> uniqueList = new ArrayList<>();
        for (ResolverActivity.ResolvedComponentInfo info : currentResolveList) {
            String packageName = info.name.getPackageName();
            if (map.containsKey(packageName)) {
                ResolverActivity.ResolvedComponentInfo storedInfo = map.get(packageName);
                ResolveInfo ri = info.getResolveInfoAt(0);
                ResolveInfo sri = storedInfo.getResolveInfoAt(0);
                if (ri != null && sri != null) {
                    if (sri.userHandle.equals(ri.userHandle)) {
                        storedInfo.getSimilarList().add(info);
                    }
                }
            }
            uniqueList.add(info);
            info.getSimilarList().add(info);
            map.put(packageName, info);
        }
        boolean result = finishRebuildingListWithFilteredResults(uniqueList, doPostProcessing);
        Trace.endSection();
        return result;
    }

    List<ResolverActivity.ResolvedComponentInfo> getInitialRebuiltResolveList() {
        if (this.mBaseResolveList != null) {
            List<ResolverActivity.ResolvedComponentInfo> currentResolveList = new ArrayList<>();
            this.mResolverListController.addResolveListDedupe(currentResolveList, this.mResolverListCommunicator.getTargetIntent(), this.mBaseResolveList);
            return currentResolveList;
        }
        return this.mResolverListController.getResolversForIntent(true, this.mResolverListCommunicator.shouldGetActivityMetadata(), this.mResolverListCommunicator.shouldGetOnlyDefaultActivities(), this.mIntents);
    }

    List<ResolverActivity.ResolvedComponentInfo> performPrimaryResolveListFiltering(List<ResolverActivity.ResolvedComponentInfo> currentResolveList) {
        if (this.mBaseResolveList != null || currentResolveList == null) {
            return currentResolveList;
        }
        List<ResolverActivity.ResolvedComponentInfo> originalList = this.mResolverListController.filterIneligibleActivities(currentResolveList, true);
        return originalList == null ? currentResolveList : originalList;
    }

    List<ResolverActivity.ResolvedComponentInfo> performSecondaryResolveListFiltering(List<ResolverActivity.ResolvedComponentInfo> currentResolveList, boolean returnCopyOfOriginalListIfModified) {
        if (currentResolveList == null || currentResolveList.isEmpty()) {
            return currentResolveList;
        }
        return this.mResolverListController.filterLowPriority(currentResolveList, returnCopyOfOriginalListIfModified);
    }

    void updateOtherProfileTreatment(ResolverActivity.ResolvedComponentInfo otherProfileInfo) {
        this.mLastChosen = null;
        if (otherProfileInfo != null) {
            this.mOtherProfile = makeOtherProfileDisplayResolveInfo(this.mContext, otherProfileInfo, this.mPm, this.mResolverListCommunicator, this.mIconDpi);
            return;
        }
        this.mOtherProfile = null;
        try {
            ResolveInfo lastChosen = this.mResolverListController.getLastChosen();
            this.mLastChosen = lastChosen;
            if (lastChosen != null) {
                setLastChosenInfo(lastChosen);
            }
        } catch (RemoteException re) {
            Log.m94d(TAG, "Error calling getLastChosenActivity\n" + re);
        }
    }

    boolean finishRebuildingListWithFilteredResults(List<ResolverActivity.ResolvedComponentInfo> filteredResolveList, boolean doPostProcessing) {
        if (filteredResolveList == null || filteredResolveList.size() < 2) {
            setPlaceholderCount(0);
            processSortedList(filteredResolveList, doPostProcessing);
            return true;
        }
        int placeholderCount = filteredResolveList.size();
        if (this.mResolverListCommunicator.useLayoutWithDefault()) {
            placeholderCount--;
        }
        setPlaceholderCount(placeholderCount);
        if (this.mResolverListCommunicator.semGetOldItemCount() != getPlaceholderCount() || this.mResolverListCommunicator.semNeedSortAfterPinned() || this.mResolverListCommunicator.semIsNeedSortingInRebuildList()) {
            postListReadyRunnable(doPostProcessing, false);
            createSortingTask(doPostProcessing).execute(filteredResolveList);
        }
        this.mResolverListCommunicator.semSetNeedSortAfterPinned(false);
        this.mResolverListCommunicator.semSetNeedSortingInRebuildList(false);
        return false;
    }

    AsyncTask<List<ResolverActivity.ResolvedComponentInfo>, Void, List<ResolverActivity.ResolvedComponentInfo>> createSortingTask(final boolean doPostProcessing) {
        return new AsyncTask<List<ResolverActivity.ResolvedComponentInfo>, Void, List<ResolverActivity.ResolvedComponentInfo>>() { // from class: com.android.internal.app.ResolverListAdapter.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.p009os.AsyncTask
            public List<ResolverActivity.ResolvedComponentInfo> doInBackground(List<ResolverActivity.ResolvedComponentInfo>... params) {
                Log.m94d(ResolverListAdapter.TAG, "list up doInBackground!");
                if (ResolverListAdapter.this.mResolverListCommunicator.semIsDestroyed() || ResolverListAdapter.this.mResolverListCommunicator.semIsFinishing()) {
                    Log.m102w(ResolverListAdapter.TAG, "activity is finished.. stop sorting!");
                } else {
                    ResolverListAdapter.this.mResolverListController.sort(params[0]);
                    try {
                        List<ResolverActivity.ResolvedComponentInfo> list = params[0];
                        ResolverListAdapter resolverListAdapter = ResolverListAdapter.this;
                        Collections.sort(list, resolverListAdapter.new SemResolverListComparator(resolverListAdapter.mContext));
                    } catch (Exception e) {
                        Log.m97e(ResolverListAdapter.TAG, "SemResolverListComparator failed!!", e);
                    }
                }
                return params[0];
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.p009os.AsyncTask
            public void onPostExecute(List<ResolverActivity.ResolvedComponentInfo> sortedComponents) {
                Log.m94d(ResolverListAdapter.TAG, "list up process done!!");
                ResolverListAdapter.this.processSortedList(sortedComponents, doPostProcessing);
                ResolverListAdapter.this.notifyDataSetChanged();
                if (doPostProcessing) {
                    ResolverListAdapter.this.mResolverListCommunicator.updateProfileViewButton();
                }
            }
        };
    }

    protected void processSortedList(List<ResolverActivity.ResolvedComponentInfo> sortedComponents, boolean doPostProcessing) {
        SemPersonaManager semPersonaManager;
        int n = sortedComponents != null ? sortedComponents.size() : 0;
        Trace.beginSection("ResolverListAdapter#processSortedList:" + n);
        boolean isAppSeparationPresent = false;
        Set<String> separatedAppsList = new HashSet<>();
        if (SemPersonaManager.isDoEnabled(this.mInitialIntentsUserSpace.getIdentifier()) && (semPersonaManager = this.mSpm) != null && semPersonaManager.isAppSeparationPresent()) {
            isAppSeparationPresent = true;
            separatedAppsList = new HashSet<>(this.mSpm.getSeparatedAppsList());
        }
        this.mDisplayList.clear();
        boolean z = true;
        if (n != 0) {
            if (this.mInitialIntents != null) {
                int i = 0;
                while (true) {
                    Intent[] intentArr = this.mInitialIntents;
                    if (i >= intentArr.length) {
                        break;
                    }
                    Intent ii = intentArr[i];
                    if (ii != null) {
                        Intent rii = ii.getClass() == Intent.class ? ii : new Intent(ii);
                        ActivityInfo ai = rii.resolveActivityInfo(this.mPm, 0);
                        if (ai == null) {
                            Log.m102w(TAG, "No activity found for " + ii);
                        } else {
                            ResolveInfo ri = new ResolveInfo();
                            ri.activityInfo = ai;
                            UserManager userManager = (UserManager) this.mContext.getSystemService("user");
                            if (ii instanceof LabeledIntent) {
                                LabeledIntent li = (LabeledIntent) ii;
                                ri.resolvePackageName = li.getSourcePackage();
                                ri.labelRes = li.getLabelResource();
                                ri.nonLocalizedLabel = li.getNonLocalizedLabel();
                                ri.icon = li.getIconResource();
                                ri.iconResourceId = ri.icon;
                            }
                            if (userManager.isManagedProfile()) {
                                ri.noResourceId = z;
                                ri.icon = 0;
                            }
                            if (!isAppSeparationPresent || !separatedAppsList.contains(ri.activityInfo.packageName)) {
                                ri.userHandle = this.mInitialIntentsUserSpace;
                                addResolveInfo(new DisplayResolveInfo(ii, ri, ri.loadLabel(this.mPm), null, ii, makePresentationGetter(ri)));
                            }
                        }
                    }
                    i++;
                    z = true;
                }
            }
            DisplayResolveInfo displayResolveInfo = this.mCopyButtonDri;
            if (displayResolveInfo != null) {
                addResolveInfo(displayResolveInfo);
            }
            for (ResolverActivity.ResolvedComponentInfo rci : sortedComponents) {
                ResolveInfo ri2 = rci.getResolveInfoAt(0);
                if (ri2 != null && (!isAppSeparationPresent || !separatedAppsList.contains(ri2.activityInfo.packageName))) {
                    addResolveInfoWithAlternates(rci);
                }
            }
        }
        this.mResolverListCommunicator.sendVoiceChoicesIfNeeded();
        postListReadyRunnable(doPostProcessing, true, this.mResolverListCommunicator.semIsNeedSortingInRebuildList());
        this.mResolverListCommunicator.semSetNeedSortAfterPinned(false);
        this.mResolverListCommunicator.semSetNeedSortingInRebuildList(false);
        this.mIsTabLoaded = true;
        Trace.endSection();
    }

    void postListReadyRunnable(boolean doPostProcessing, boolean rebuildCompleted) {
        postListReadyRunnable(doPostProcessing, rebuildCompleted, false);
    }

    void postListReadyRunnable(final boolean doPostProcessing, final boolean rebuildCompleted, final boolean skipAutoLaunch) {
        if (this.mPostListReadyRunnable == null) {
            this.mPostListReadyRunnable = new Runnable() { // from class: com.android.internal.app.ResolverListAdapter.2
                @Override // java.lang.Runnable
                public void run() {
                    ResolverListAdapter.this.mResolverListCommunicator.onPostListReady(ResolverListAdapter.this, doPostProcessing, rebuildCompleted, skipAutoLaunch);
                    ResolverListAdapter.this.mPostListReadyRunnable = null;
                }
            };
            this.mContext.getMainThreadHandler().post(this.mPostListReadyRunnable);
        }
    }

    private void addResolveInfoWithAlternates(ResolverActivity.ResolvedComponentInfo rci) {
        int count = rci.getCount();
        int i = 0;
        Intent intent = rci.getIntentAt(0);
        ResolveInfo add = rci.getResolveInfoAt(0);
        Intent replaceIntent = this.mResolverListCommunicator.getReplacementIntent(add.activityInfo, intent);
        Intent defaultIntent = this.mResolverListCommunicator.getReplacementIntent(add.activityInfo, this.mResolverListCommunicator.getTargetIntent());
        DisplayResolveInfo dri = new DisplayResolveInfo(intent, add, replaceIntent != null ? replaceIntent : defaultIntent, makePresentationGetter(add));
        boolean isHasSimilarList = false;
        if (rci.getSimilarList().size() > 0) {
            Iterator<ResolverActivity.ResolvedComponentInfo> it = rci.getSimilarList().iterator();
            while (it.hasNext()) {
                ResolverActivity.ResolvedComponentInfo storedInfo = it.next();
                ResolveInfo ri = storedInfo.getResolveInfoAt(i);
                DisplayResolveInfo driInside = new DisplayResolveInfo(intent, ri, replaceIntent != null ? replaceIntent : defaultIntent, makePresentationGetter(ri));
                driInside.setPinned(rci.isPinned());
                dri.getSimilarList().add(driInside);
                Log.m94d(TAG, "driInside.mDisplayLabel->" + ((Object) driInside.getDisplayLabel()) + " driInside.mExtendedInfo;" + ((Object) driInside.getExtendedInfo()) + " driInside.mResolveInfo" + driInside.getResolveInfo());
                isHasSimilarList = true;
                i = 0;
            }
        }
        dri.setPinned(rci.isPinned());
        if (rci.isPinned()) {
            Log.m98i(TAG, "Pinned item: " + rci.name);
        }
        if (!needToHideSmsPackage(dri) || this.mResolverListCommunicator.semIsSupportsAlwaysUseOption()) {
            addResolveInfo(dri);
        }
        if (replaceIntent == intent) {
            for (int i2 = 1; i2 < count; i2++) {
                Intent altIntent = rci.getIntentAt(i2);
                dri.addAlternateSourceIntent(altIntent);
            }
        }
        updateLastChosenPosition(add, Boolean.valueOf(isHasSimilarList));
    }

    private void updateLastChosenPosition(ResolveInfo info, Boolean isHasSimilarList) {
        ResolveInfo resolveInfo;
        if (this.mOtherProfile != null) {
            this.mLastChosenPosition = -1;
        } else if (isHasSimilarList.booleanValue() && (resolveInfo = this.mLastChosen) != null && resolveInfo.activityInfo.packageName.equals(info.activityInfo.packageName)) {
            this.mLastChosenPosition = this.mDisplayList.size() - 1;
            setLastChosenInfo(this.mLastChosen);
        }
    }

    private void addResolveInfo(DisplayResolveInfo dri) {
        if (dri != null && dri.getResolveInfo() != null && dri.getResolveInfo().targetUserId == -2) {
            if (shouldAddResolveInfo(dri)) {
                this.mDisplayList.add(dri);
                Log.m98i(TAG, "Add DisplayResolveInfo component: " + dri.getResolvedComponentName() + ", intent component: " + dri.getResolvedIntent().getComponent());
                return;
            }
            return;
        }
        if (dri != null && dri.getResolveInfo() != null) {
            if (IntentForwarderActivity.FORWARD_INTENT_TO_PARENT.compareTo(dri.getResolveInfo().getComponentInfo().getComponentName().getClassName()) == 0) {
                this.mDisplayList.add(0, dri);
                Log.m98i(TAG, "Add DRI forward intent: " + dri.getResolvedComponentName() + ", intent component: " + dri.getResolvedIntent().getComponent());
            } else if (IntentForwarderActivity.FORWARD_INTENT_TO_MANAGED_PROFILE4.compareTo(dri.getResolveInfo().getComponentInfo().getComponentName().getClassName()) == 0) {
                boolean isEnabledSecureFolder = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.HIDE_SECURE_FOLDER_FLAG, 0, 0) == 0;
                if (isEnabledSecureFolder) {
                    this.mDisplayList.add(dri);
                    Log.m98i(TAG, "Add DRI secure folder : " + dri.getResolvedComponentName() + ", intent component: " + dri.getResolvedIntent().getComponent());
                }
            }
        }
    }

    protected boolean shouldAddResolveInfo(DisplayResolveInfo dri) {
        if (!this.mMessageAppSkipped && !this.mResolverListCommunicator.semIsSupportsAlwaysUseOption() && needToHideSmsPackage(dri)) {
            return false;
        }
        for (DisplayResolveInfo existingInfo : this.mDisplayList) {
            if (this.mResolverListCommunicator.resolveInfoMatch(dri.getResolveInfo(), existingInfo.getResolveInfo()) && !SemDualAppManager.isDualAppId(UserHandle.getUserId(dri.getResolveInfo().activityInfo.applicationInfo.uid)) && !SemDualAppManager.isDualAppId(UserHandle.getUserId(existingInfo.getResolveInfo().activityInfo.applicationInfo.uid))) {
                return false;
            }
        }
        return true;
    }

    public ResolveInfo resolveInfoForPosition(int position, boolean filtered) {
        TargetInfo target = targetInfoForPosition(position, filtered);
        if (target != null) {
            return target.getResolveInfo();
        }
        return null;
    }

    public TargetInfo targetInfoForPosition(int position, boolean filtered) {
        if (filtered) {
            return getItem(position);
        }
        if (this.mDisplayList.size() > position) {
            return this.mDisplayList.get(position);
        }
        return null;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<DisplayResolveInfo> list = this.mDisplayList;
        if (list == null || list.isEmpty()) {
            int totalSize = this.mPlaceholderCount;
            return totalSize;
        }
        int totalSize2 = this.mDisplayList.size();
        return totalSize2;
    }

    public int getUnfilteredCount() {
        return this.mDisplayList.size();
    }

    @Override // android.widget.Adapter
    public TargetInfo getItem(int position) {
        if (this.mDisplayList.size() > position) {
            return this.mDisplayList.get(position);
        }
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    public int getDisplayResolveInfoCount() {
        return this.mDisplayList.size();
    }

    public DisplayResolveInfo getDisplayResolveInfo(int index) {
        return this.mDisplayList.get(index);
    }

    @Override // android.widget.Adapter
    public final View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = createView(parent);
        }
        onBindView(view, getItem(position), position);
        return view;
    }

    public final View createView(ViewGroup parent) {
        View view = onCreateView(parent);
        ViewHolder holder = new ViewHolder(view);
        semSetTextSizeByMaxFontScale(holder.text, C4337R.dimen.sem_resolver_item_text_size);
        semSetTextSizeByMaxFontScale(holder.text2, C4337R.dimen.sem_resolver_item_text_size_secondary);
        view.setTag(holder);
        return view;
    }

    View onCreateView(ViewGroup parent) {
        return this.mInflater.inflate(C4337R.layout.sem_resolver_grid_item, parent, false);
    }

    public final void bindView(int position, View view) {
        onBindView(view, getItem(position), position);
    }

    protected void onBindView(View view, TargetInfo info, int position) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (info == null) {
            holder.icon.setImageDrawable(this.mContext.getDrawable(C4337R.drawable.resolver_icon_placeholder));
            holder.bindLabel("", "", false);
            return;
        }
        if (info instanceof DisplayResolveInfo) {
            DisplayResolveInfo dri = (DisplayResolveInfo) info;
            if (dri.hasDisplayLabel()) {
                holder.bindLabel(dri.getDisplayLabel(), dri.getExtendedInfo(), alwaysShowSubLabel());
            } else {
                holder.bindLabel("", "", false);
                loadLabel(dri);
            }
            holder.bindIcon(info);
            if (!dri.hasDisplayIcon()) {
                if (!this.mResolverListCommunicator.semIsSupportsAlwaysUseOption() || (this.mResolverListCommunicator.semIsSupportsAlwaysUseOption() && (position == 0 || position == 1))) {
                    try {
                        new LoadIconTask((DisplayResolveInfo) info, holder).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                        return;
                    } catch (RejectedExecutionException e) {
                        Log.m97e(TAG, "LoadIconTask failed!!", e);
                        return;
                    }
                }
                loadIcon(dri);
            }
        }
    }

    protected LoadLabelTask getLoadLabelTask(DisplayResolveInfo info, ViewHolder holder) {
        return new LoadLabelTask(info, holder);
    }

    protected final void loadIcon(DisplayResolveInfo info) {
        if (this.mIconLoaders.get(info) == null) {
            LoadIconTask task = new LoadIconTask(info);
            this.mIconLoaders.put(info, task);
            task.execute(new Void[0]);
        }
    }

    private void loadLabel(DisplayResolveInfo info) {
        if (this.mLabelLoaders.get(info) == null) {
            LoadLabelTask task = createLoadLabelTask(info);
            this.mLabelLoaders.put(info, task);
            task.execute(new Void[0]);
        }
    }

    protected LoadLabelTask createLoadLabelTask(DisplayResolveInfo info) {
        return new LoadLabelTask(info);
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

    private <T extends AsyncTask> void cancelTasks(Collection<T> tasks) {
        for (T task : tasks) {
            task.cancel(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ColorMatrixColorFilter getSuspendedColorMatrix() {
        if (sSuspendedMatrixColorFilter == null) {
            ColorMatrix tempBrightnessMatrix = new ColorMatrix();
            float[] mat = tempBrightnessMatrix.getArray();
            mat[0] = 0.5f;
            mat[6] = 0.5f;
            mat[12] = 0.5f;
            mat[4] = 127;
            mat[9] = 127;
            mat[14] = 127;
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0.0f);
            matrix.preConcat(tempBrightnessMatrix);
            sSuspendedMatrixColorFilter = new ColorMatrixColorFilter(matrix);
        }
        return sSuspendedMatrixColorFilter;
    }

    ActivityInfoPresentationGetter makePresentationGetter(ActivityInfo ai) {
        return new ActivityInfoPresentationGetter(this.mContext, this.mIconDpi, ai);
    }

    ResolveInfoPresentationGetter makePresentationGetter(ResolveInfo ri) {
        return new ResolveInfoPresentationGetter(this.mContext, this.mIconDpi, ri);
    }

    Drawable loadIconForResolveInfo(ResolveInfo ri) {
        if (SemPersonaManager.ICON_CLASS_FOR_SECUREFOLDER_FORWARD_TO_PROFILE.equals(ri.activityInfo.name) || SemPersonaManager.ICON_CLASS_FOR_INTENT_FORWARD_TO_PARENT.equals(ri.activityInfo.name) || "com.samsung.knox.securefolder.presentation.switcher.view.B2CStoreFilesActivity".equals(ri.activityInfo.name)) {
            return makePresentationGetter(ri).getKnoxIcon();
        }
        return makePresentationGetter(ri).getIcon(ResolverActivity.getResolveInfoUserHandle(ri, getUserHandle()));
    }

    void loadFilteredItemIconTaskAsync(final ImageView iconView) {
        final DisplayResolveInfo iconInfo = getFilteredItem();
        if (iconView != null && iconInfo != null) {
            new AsyncTask<Void, Void, Drawable>() { // from class: com.android.internal.app.ResolverListAdapter.3
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.p009os.AsyncTask
                public Drawable doInBackground(Void... params) {
                    return ResolverListAdapter.this.loadIconForResolveInfo(iconInfo.getResolveInfo());
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.p009os.AsyncTask
                public void onPostExecute(Drawable d) {
                    iconView.setImageDrawable(d);
                }
            }.execute(new Void[0]);
        }
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

    protected boolean alwaysShowSubLabel() {
        return false;
    }

    private static ResolverActivity.ResolvedComponentInfo getFirstNonCurrentUserResolvedComponentInfo(List<ResolverActivity.ResolvedComponentInfo> resolveList) {
        if (resolveList == null) {
            return null;
        }
        for (ResolverActivity.ResolvedComponentInfo info : resolveList) {
            ResolveInfo resolveInfo = info.getResolveInfoAt(0);
            if (UserHandle.myUserId() != 0 || !SemPersonaManager.isSecureFolderId(resolveInfo.targetUserId)) {
                if (!SemPersonaManager.isSecureFolderId(UserHandle.myUserId()) || resolveInfo.targetUserId != 0) {
                    if (resolveInfo.targetUserId != -2) {
                        return info;
                    }
                }
            }
        }
        return null;
    }

    private static DisplayResolveInfo makeOtherProfileDisplayResolveInfo(Context context, ResolverActivity.ResolvedComponentInfo resolvedComponentInfo, PackageManager pm, ResolverListCommunicator resolverListCommunicator, int iconDpi) {
        ResolveInfo resolveInfo = resolvedComponentInfo.getResolveInfoAt(0);
        Intent pOrigIntent = resolverListCommunicator.getReplacementIntent(resolveInfo.activityInfo, resolvedComponentInfo.getIntentAt(0));
        Intent replacementIntent = resolverListCommunicator.getReplacementIntent(resolveInfo.activityInfo, resolverListCommunicator.getTargetIntent());
        ResolveInfoPresentationGetter presentationGetter = new ResolveInfoPresentationGetter(context, iconDpi, resolveInfo);
        return new DisplayResolveInfo(resolvedComponentInfo.getIntentAt(0), resolveInfo, resolveInfo.loadLabel(pm), resolveInfo.loadLabel(pm), pOrigIntent != null ? pOrigIntent : replacementIntent, presentationGetter);
    }

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

        void updateProfileViewButton();

        boolean useLayoutWithDefault();

        default boolean shouldGetOnlyDefaultActivities() {
            return true;
        }
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
            this.badge = (ImageView) view.findViewById(C4337R.id.target_badge);
        }

        public void bindLabel(CharSequence label, CharSequence subLabel, boolean showSubLabel) {
            this.text.setText(label);
            if (TextUtils.equals(label, subLabel)) {
                subLabel = null;
            }
            this.text2.setText(subLabel);
            if (showSubLabel || subLabel != null) {
                this.text2.setVisibility(0);
                this.text.setLines(1);
            } else {
                this.text2.setVisibility(8);
                this.text.setLines(2);
            }
            this.itemView.setContentDescription(null);
        }

        public void updateContentDescription(String description) {
            this.itemView.setContentDescription(description);
        }

        public void bindIcon(TargetInfo info) {
            this.icon.setImageDrawable(info.getDisplayIcon(this.itemView.getContext()));
            if (info.isSuspended()) {
                this.icon.setColorFilter(ResolverListAdapter.getSuspendedColorMatrix());
            } else {
                this.icon.setColorFilter((ColorFilter) null);
            }
        }
    }

    protected class LoadLabelTask extends AsyncTask<Void, Void, CharSequence[]> {
        private final DisplayResolveInfo mDisplayResolveInfo;
        private ViewHolder mHolder;

        protected LoadLabelTask(DisplayResolveInfo dri, ViewHolder holder) {
            this.mDisplayResolveInfo = dri;
            this.mHolder = holder;
        }

        protected LoadLabelTask(DisplayResolveInfo dri) {
            this.mDisplayResolveInfo = dri;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.p009os.AsyncTask
        public CharSequence[] doInBackground(Void... voids) {
            ResolveInfoPresentationGetter pg = ResolverListAdapter.this.makePresentationGetter(this.mDisplayResolveInfo.getResolveInfo());
            if (ResolverListAdapter.this.mIsAudioCaptureDevice) {
                ActivityInfo activityInfo = this.mDisplayResolveInfo.getResolveInfo().activityInfo;
                String packageName = activityInfo.packageName;
                int uid = activityInfo.applicationInfo.uid;
                boolean hasRecordPermission = PermissionChecker.checkPermissionForPreflight(ResolverListAdapter.this.mContext, Manifest.permission.RECORD_AUDIO, -1, uid, packageName) == 0;
                if (!hasRecordPermission) {
                    return new CharSequence[]{pg.getLabel(), ResolverListAdapter.this.mContext.getString(C4337R.string.usb_device_resolve_prompt_warn)};
                }
            }
            return new CharSequence[]{pg.getLabel(), pg.getSubLabel()};
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.p009os.AsyncTask
        public void onPostExecute(CharSequence[] result) {
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
                Log.m98i(ResolverListAdapter.TAG, "ClassName : " + ResolverListAdapter.this.mLastChosen.activityInfo.name + ", mLastChosenActivityIndex : " + ResolverListAdapter.this.mLastChosenActivityIndex + ", mDisplayResolveInfo.getSimilarList().size() : " + this.mDisplayResolveInfo.getSimilarList().size());
            }
            this.mDisplayResolveInfo.setDisplayLabel(result[0]);
            DisplayResolveInfo displayResolveInfo = this.mDisplayResolveInfo;
            displayResolveInfo.setExtendedInfo((displayResolveInfo.getSimilarList().size() <= 1 || ResolverListAdapter.this.mLastChosenActivityIndex < 0) ? result[1] : ResolverListAdapter.this.getLastChosenActivity());
            ResolverListAdapter.this.notifyDataSetChanged();
        }
    }

    class LoadIconTask extends AsyncTask<Void, Void, Drawable> {
        boolean mCheckViewHolder;
        protected final DisplayResolveInfo mDisplayResolveInfo;
        private ViewHolder mHolder;
        private final ResolveInfo mResolveInfo;

        LoadIconTask(DisplayResolveInfo dri, ViewHolder holder) {
            this.mDisplayResolveInfo = dri;
            this.mResolveInfo = dri.getResolveInfo();
            this.mHolder = holder;
        }

        LoadIconTask(DisplayResolveInfo dri) {
            this.mDisplayResolveInfo = dri;
            this.mResolveInfo = dri.getResolveInfo();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.p009os.AsyncTask
        public Drawable doInBackground(Void... params) {
            return ResolverListAdapter.this.loadIconForResolveInfo(this.mResolveInfo);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p009os.AsyncTask
        public void onPostExecute(Drawable d) {
            DisplayResolveInfo otherProfile = ResolverListAdapter.this.getOtherProfile();
            DisplayResolveInfo displayResolveInfo = this.mDisplayResolveInfo;
            if (otherProfile == displayResolveInfo) {
                ResolverListAdapter.this.mResolverListCommunicator.updateProfileViewButton();
            } else if (!displayResolveInfo.hasDisplayIcon()) {
                this.mDisplayResolveInfo.setDisplayIcon(d);
                ResolverListAdapter.this.notifyDataSetChanged();
            }
        }
    }

    public static class ResolveInfoPresentationGetter extends ActivityInfoPresentationGetter {
        private final ResolveInfo mRi;

        public ResolveInfoPresentationGetter(Context ctx, int iconDpi, ResolveInfo ri) {
            super(ctx, iconDpi, ri.activityInfo);
            this.mRi = ri;
        }

        @Override // com.android.internal.app.ResolverListAdapter.ActivityInfoPresentationGetter, com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        Drawable getIconSubstituteInternal() {
            Drawable dr = null;
            try {
                if (this.mRi.resolvePackageName != null && this.mRi.icon != 0) {
                    dr = loadIconFromResource(this.mPm.getResourcesForApplication(this.mRi.resolvePackageName), this.mRi.icon);
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.m97e(ResolverListAdapter.TAG, "SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON permission granted but couldn't find resources for package", e);
            }
            return dr == null ? super.getIconSubstituteInternal() : dr;
        }

        @Override // com.android.internal.app.ResolverListAdapter.ActivityInfoPresentationGetter, com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        String getAppSubLabelInternal() {
            return this.mRi.loadLabel(this.mPm).toString();
        }

        @Override // com.android.internal.app.ResolverListAdapter.ActivityInfoPresentationGetter, com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        String getAppLabelForSubstitutePermission() {
            return this.mRi.getComponentInfo().loadLabel(this.mPm).toString();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Drawable getKnoxIcon() {
            Drawable dr = this.mRi.loadIcon(this.mPm);
            return (dr == null || SemPersonaManager.ICON_CLASS_FOR_INTENT_FORWARD_TO_PARENT.equals(this.mRi.activityInfo.name)) ? super.getIconSubstituteInternal() : dr;
        }

        @Override // com.android.internal.app.ResolverListAdapter.ActivityInfoPresentationGetter, com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        public UserHandle getUserHandle(int uid) {
            if (SemPersonaManager.ICON_CLASS_FOR_INTENT_FORWARD_TO_PARENT.equals(this.mRi.activityInfo.name)) {
                return UserHandle.getUserHandleForUid(0);
            }
            return super.getUserHandle(uid);
        }

        @Override // com.android.internal.app.ResolverListAdapter.ActivityInfoPresentationGetter, com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        public boolean isSecureFolderComponent() {
            return SemPersonaManager.ICON_CLASS_FOR_INTENT_FORWARD_TO_PARENT.equals(this.mRi.activityInfo.name) || SemPersonaManager.ICON_CLASS_FOR_SECUREFOLDER_FORWARD_TO_PROFILE.equals(this.mRi.activityInfo.name) || "com.samsung.knox.securefolder.presentation.switcher.view.B2CStoreFilesActivity".equals(this.mRi.activityInfo.name);
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

        @Override // com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        public /* bridge */ /* synthetic */ boolean isSecureFolderComponent() {
            return super.isSecureFolderComponent();
        }

        public ActivityInfoPresentationGetter(Context ctx, int iconDpi, ActivityInfo activityInfo) {
            super(ctx, iconDpi, activityInfo.applicationInfo);
            this.mActivityInfo = activityInfo;
        }

        @Override // com.android.internal.app.ResolverListAdapter.TargetPresentationGetter
        Drawable getIconSubstituteInternal() {
            try {
                if (this.mActivityInfo.icon == 0) {
                    return null;
                }
                Drawable dr = loadIconFromResource(this.mPm.getResourcesForApplication(this.mActivityInfo.applicationInfo), this.mActivityInfo.icon);
                return dr;
            } catch (PackageManager.NameNotFoundException e) {
                Log.m97e(ResolverListAdapter.TAG, "SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON permission granted but couldn't find resources for package", e);
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

        TargetPresentationGetter(Context ctx, int iconDpi, ApplicationInfo ai) {
            this.mCtx = ctx;
            PackageManager packageManager = ctx.getPackageManager();
            this.mPm = packageManager;
            this.mAi = ai;
            this.mIconDpi = iconDpi;
            this.mHasSubstitutePermission = packageManager.checkPermission(Manifest.permission.SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON, ai.packageName) == 0;
        }

        public Drawable getIcon(UserHandle userHandle) {
            return new BitmapDrawable(this.mCtx.getResources(), getIconBitmap(userHandle));
        }

        public Bitmap getIconBitmap(UserHandle userHandle) {
            Drawable dr = null;
            if (this.mHasSubstitutePermission) {
                dr = getIconSubstituteInternal();
            }
            Object obj = this.mCtx;
            if ((obj instanceof ResolverListCommunicator) && ((ResolverListCommunicator) obj).semIsOverlayThemesEnabled() && ((ResolverListCommunicator) this.mCtx).semGetAppIconTheme() != null) {
                if (dr == null) {
                    dr = this.mAi.loadUnbadgedIcon(this.mPm);
                } else if (this.mPm.semShouldPackIntoIconTray(this.mAi.packageName)) {
                    dr = this.mPm.semGetDrawableForIconTray(dr, 1);
                }
            } else {
                if (dr == null) {
                    try {
                        if (this.mAi.icon != 0) {
                            dr = loadIconFromResource(this.mPm.getResourcesForApplication(this.mAi), this.mAi.icon);
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                    }
                }
                if (dr == null) {
                    dr = this.mAi.loadIcon(this.mPm);
                }
                if (dr != null && this.mPm.semShouldPackIntoIconTray(this.mAi.packageName)) {
                    dr = this.mPm.semGetDrawableForIconTray(dr, 48);
                }
            }
            Bitmap icon = drawableToBitmap(this.mPm.getUserBadgedIcon(dr, getUserHandle(this.mAi.uid)));
            return icon;
        }

        public UserHandle getUserHandle(int uid) {
            return UserHandle.getUserHandleForUid(uid);
        }

        public boolean isSecureFolderComponent() {
            return false;
        }

        public String getLabel() {
            String label = null;
            if (this.mHasSubstitutePermission) {
                label = getAppLabelForSubstitutePermission();
            }
            if (label == null) {
                String label2 = (String) this.mAi.loadLabel(this.mPm);
                return label2;
            }
            return label;
        }

        public String getSubLabel() {
            if (this.mHasSubstitutePermission) {
                String appSubLabel = getAppSubLabelInternal();
                if (!TextUtils.isEmpty(appSubLabel) && !TextUtils.equals(appSubLabel, getLabel())) {
                    return appSubLabel;
                }
                return null;
            }
            return getAppSubLabelInternal();
        }

        protected String loadLabelFromResource(Resources res, int resId) {
            return res.getString(resId);
        }

        protected Drawable loadIconFromResource(Resources res, int resId) {
            try {
                return res.getDrawableForDensity(resId, this.mIconDpi);
            } catch (Exception e) {
                Log.m102w(ResolverListAdapter.TAG, "loadIconFromResource: " + e);
                return null;
            }
        }

        private Bitmap drawableToBitmap(Drawable drawable) {
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
    }

    public void semForceHandlePackagesChanged() {
        this.mResolverListCommunicator.semOnForceHandlePackagesChanged(this);
    }

    void semSetTextSizeByMaxFontScale(TextView textView, int resId) {
        if (textView != null) {
            int defaultTextSize = this.mContext.getResources().getDimensionPixelSize(resId);
            textView.setTextSize(0, defaultTextSize * semGetFontScale());
        }
    }

    float semGetFontScale() {
        float fontScale = this.mContext.getResources().getConfiguration().fontScale;
        if (fontScale > 1.2f) {
            return 1.2f;
        }
        return fontScale;
    }

    private String StringReplaceForSpace(String str) {
        return str.replaceAll(" ", "").replaceAll("\\s", "").toLowerCase();
    }

    private boolean needToHideSmsPackage(DisplayResolveInfo dri) {
        try {
            if (TextUtils.isEmpty(this.mDefaultSms)) {
                Log.m98i(TAG, "no default sms");
                this.mMessageAppSkipped = true;
                return false;
            }
            if (PACKAGE_NAME_SAMSUNG_MESSAGES.equals(this.mDefaultSms)) {
                if (PACKAGE_NAME_GOOGLE_MESSAGES.equals(dri.getResolveInfo().activityInfo.packageName)) {
                    Log.m98i(TAG, "skip add " + dri.getResolveInfo().activityInfo.packageName + ". Default SMS package is " + this.mDefaultSms);
                    this.mMessageAppSkipped = true;
                    return true;
                }
                return false;
            }
            if (PACKAGE_NAME_GOOGLE_MESSAGES.equals(this.mDefaultSms) && PACKAGE_NAME_SAMSUNG_MESSAGES.equals(dri.getResolveInfo().activityInfo.packageName)) {
                Log.m98i(TAG, "skip add " + dri.getResolveInfo().activityInfo.packageName + ". Default SMS package is " + this.mDefaultSms);
                this.mMessageAppSkipped = true;
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.m96e(TAG, "getDefaultSmsPackage: " + e);
            return false;
        }
    }

    private void setLastChosenInfo(ResolveInfo resolveInfo) {
        this.mLastChosenActivityFromPm = resolveInfo.loadLabel(this.mPm).toString();
        this.mLastChosenPackageFromPm = resolveInfo.getComponentInfo().packageName;
    }

    public String getLastChosenActivity() {
        if (!TextUtils.isEmpty(this.mLastChosenActivityFromPm)) {
            return this.mLastChosenActivityFromPm;
        }
        return null;
    }

    public String getLastChosenPackage() {
        if (!TextUtils.isEmpty(this.mLastChosenPackageFromPm)) {
            return this.mLastChosenPackageFromPm;
        }
        return null;
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
        public final int compare(ResolverActivity.ResolvedComponentInfo lhsp, ResolverActivity.ResolvedComponentInfo rhsp) {
            ResolveInfo lhs = lhsp.getResolveInfoAt(0);
            ResolveInfo rhs = rhsp.getResolveInfoAt(0);
            int lhsScore = 0;
            int rhsScore = 0;
            ApplicationInfo lhsAi = lhs.activityInfo.applicationInfo;
            ApplicationInfo rhlsAi = rhs.activityInfo.applicationInfo;
            if (lhs.filter != null && lhs.filter.getHosts() != null && lhs.filter.getHosts().length > 0) {
                return -1;
            }
            if (rhs.filter != null && rhs.filter.getHosts() != null && rhs.filter.getHosts().length > 0) {
                return 1;
            }
            if (lhsAi.metaData != null) {
                lhsScore = lhsAi.metaData.getInt(SemShareConstants.METADATA_RESOLVER_RANKING_PRIORITY_KEY, 0);
            }
            if (rhlsAi.metaData != null) {
                rhsScore = rhlsAi.metaData.getInt(SemShareConstants.METADATA_RESOLVER_RANKING_PRIORITY_KEY, 0);
            }
            if (lhsScore != rhsScore) {
                return rhsScore - lhsScore;
            }
            ResolveInfoPresentationGetter lhsPg = ResolverListAdapter.this.makePresentationGetter(lhs);
            ResolveInfoPresentationGetter rhsPg = ResolverListAdapter.this.makePresentationGetter(rhs);
            return this.mCollator.compare(lhsPg.getLabel().trim(), rhsPg.getLabel().trim());
        }
    }
}
