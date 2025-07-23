package com.android.internal.app;

import android.app.prediction.AppPredictor;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Trace;
import android.os.UserHandle;
import android.service.chooser.ChooserTarget;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.app.ChooserActivity;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.ResolverListAdapter;
import com.android.internal.app.chooser.ChooserTargetInfo;
import com.android.internal.app.chooser.DisplayResolveInfo;
import com.android.internal.app.chooser.MultiDisplayResolveInfo;
import com.android.internal.app.chooser.SelectableTargetInfo;
import com.android.internal.app.chooser.TargetInfo;
import com.android.internal.hidden_from_bootclasspath.android.service.chooser.Flags;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class ChooserListAdapter extends ResolverListAdapter {
    public static final float CALLER_TARGET_SCORE_BOOST = 900.0f;
    private static final boolean DEBUG = false;
    private static final int MAX_CHOOSER_TARGETS_PER_APP = 2;
    private static final int MAX_SUGGESTED_APP_TARGETS = 4;
    public static final int NO_POSITION = -1;
    private static final float PINNED_SHORTCUT_TARGET_SCORE_BOOST = 1000.0f;
    public static final float SHORTCUT_TARGET_SCORE_BOOST = 90.0f;
    private static final String TAG = "ChooserListAdapter";
    public static final int TARGET_BAD = -1;
    public static final int TARGET_CALLER = 0;
    public static final int TARGET_SERVICE = 1;
    public static final int TARGET_STANDARD = 2;
    public static final int TARGET_STANDARD_AZ = 3;
    private AppPredictor mAppPredictor;
    private AppPredictor.Callback mAppPredictorCallback;
    private ResolverAppPredictorCallback mAppPredictorCallbackWrapper;
    private boolean mApplySharingAppLimits;
    private final ChooserActivity.BaseChooserTargetComparator mBaseTargetComparator;
    private final List<DisplayResolveInfo> mCallerTargets;
    private final ChooserActivityLogger mChooserActivityLogger;
    private final ChooserListCommunicator mChooserListCommunicator;
    private boolean mEnableStackedApps;
    private final Map<SelectableTargetInfo, LoadDirectShareIconTask> mIconLoaders;
    private final UserHandle mInitialIntentsUserSpace;
    private boolean mListViewDataChanged;
    private final int mMaxShortcutTargetsPerApp;
    private int mNumShortcutResults;
    private Consumer<DisplayResolveInfo> mOnIconLoadedListener;
    private final View.OnLayoutChangeListener mPinTextSpacingListener;
    private ChooserTargetInfo mPlaceHolderTargetInfo;
    private final SelectableTargetInfo.SelectableTargetInfoCommunicator mSelectableTargetInfoCommunicator;
    private final List<ChooserTargetInfo> mServiceTargets;
    private List<DisplayResolveInfo> mSortedList;

    public interface ChooserListCommunicator extends ResolverListAdapter.ResolverListCommunicator {
        int getMaxRankedTargets();

        boolean isSendAction(Intent intent);

        void sendListViewUpdateMessage(UserHandle userHandle);

        boolean shouldShowContentPreview();

        boolean shouldShowServiceTargets();
    }

    @Override // com.android.internal.app.ResolverListAdapter
    protected boolean alwaysShowSubLabel() {
        return true;
    }

    /* renamed from: com.android.internal.app.ChooserListAdapter$1, reason: invalid class name */
    class AnonymousClass1 implements View.OnLayoutChangeListener {
        AnonymousClass1(ChooserListAdapter chooserListAdapter) {
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            final TextView textView = (TextView) view;
            Layout layout = textView.getLayout();
            if (layout != null) {
                int i9 = 0;
                for (int i10 = 0; i10 < layout.getLineCount(); i10++) {
                    i9 = Math.max((int) Math.ceil(layout.getLineMax(i10)), i9);
                }
                int paddingLeft = i9 + textView.getPaddingLeft() + textView.getPaddingRight();
                if (textView.getWidth() > paddingLeft) {
                    ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
                    layoutParams.width = paddingLeft;
                    textView.setLayoutParams(layoutParams);
                    textView.post(new Runnable() { // from class: com.android.internal.app.ChooserListAdapter$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            TextView.this.requestLayout();
                        }
                    });
                }
                textView.removeOnLayoutChangeListener(this);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ChooserListAdapter(android.content.Context r12, java.util.List<android.content.Intent> r13, android.content.Intent[] r14, java.util.List<android.content.pm.ResolveInfo> r15, boolean r16, com.android.internal.app.ResolverListController r17, com.android.internal.app.ChooserListAdapter.ChooserListCommunicator r18, com.android.internal.app.chooser.SelectableTargetInfo.SelectableTargetInfoCommunicator r19, android.content.pm.PackageManager r20, com.android.internal.app.ChooserActivityLogger r21, android.os.UserHandle r22) {
        /*
            Method dump skipped, instructions count: 291
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.ChooserListAdapter.<init>(android.content.Context, java.util.List, android.content.Intent[], java.util.List, boolean, com.android.internal.app.ResolverListController, com.android.internal.app.ChooserListAdapter$ChooserListCommunicator, com.android.internal.app.chooser.SelectableTargetInfo$SelectableTargetInfoCommunicator, android.content.pm.PackageManager, com.android.internal.app.ChooserActivityLogger, android.os.UserHandle):void");
    }

    public void setOnIconLoadedListener(Consumer<DisplayResolveInfo> consumer) {
        this.mOnIconLoadedListener = consumer;
    }

    AppPredictor getAppPredictor() {
        return this.mAppPredictor;
    }

    @Override // com.android.internal.app.ResolverListAdapter
    public void handlePackagesChanged() {
        createPlaceHolders();
        this.mChooserListCommunicator.onHandlePackagesChanged(this);
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        if (this.mListViewDataChanged) {
            return;
        }
        this.mChooserListCommunicator.sendListViewUpdateMessage(getUserHandle());
        this.mListViewDataChanged = true;
    }

    void refreshListView() {
        if (this.mListViewDataChanged) {
            super.notifyDataSetChanged();
        }
        this.mListViewDataChanged = false;
    }

    private void createPlaceHolders() {
        this.mNumShortcutResults = 0;
        this.mServiceTargets.clear();
        for (int i = 0; i < this.mChooserListCommunicator.getMaxRankedTargets(); i++) {
            this.mServiceTargets.add(this.mPlaceHolderTargetInfo);
        }
    }

    @Override // com.android.internal.app.ResolverListAdapter
    View onCreateView(ViewGroup viewGroup) {
        return this.mInflater.inflate(R.layout.resolve_grid_item, viewGroup, false);
    }

    @Override // com.android.internal.app.ResolverListAdapter
    protected void onBindView(View view, TargetInfo targetInfo, int i) {
        ResolverListAdapter.ViewHolder viewHolder = (ResolverListAdapter.ViewHolder) view.getTag();
        if (targetInfo == null) {
            viewHolder.icon.lambda$setImageURIAsync$0(this.mContext.getDrawable(R.drawable.resolver_icon_placeholder));
            return;
        }
        viewHolder.bindLabel(targetInfo.getDisplayLabel(), targetInfo.getExtendedInfo(), alwaysShowSubLabel());
        viewHolder.bindIcon(targetInfo);
        if (targetInfo instanceof SelectableTargetInfo) {
            SelectableTargetInfo selectableTargetInfo = (SelectableTargetInfo) targetInfo;
            DisplayResolveInfo displayResolveInfo = selectableTargetInfo.getDisplayResolveInfo();
            String displayLabel = displayResolveInfo != null ? displayResolveInfo.getDisplayLabel() : "";
            CharSequence extendedInfo = targetInfo.getExtendedInfo();
            CharSequence[] charSequenceArr = new CharSequence[3];
            charSequenceArr[0] = targetInfo.getDisplayLabel();
            charSequenceArr[1] = extendedInfo != null ? extendedInfo : "";
            charSequenceArr[2] = displayLabel;
            viewHolder.updateContentDescription(String.join(" ", charSequenceArr));
            if (!selectableTargetInfo.hasDisplayIcon()) {
                loadDirectShareIcon(selectableTargetInfo);
            }
        } else if (targetInfo instanceof DisplayResolveInfo) {
            DisplayResolveInfo displayResolveInfo2 = (DisplayResolveInfo) targetInfo;
            if (!displayResolveInfo2.hasDisplayIcon()) {
                loadIcon(displayResolveInfo2);
            }
        }
        if (targetInfo instanceof ChooserActivity.PlaceHolderTargetInfo) {
            viewHolder.text.setMaxWidth(this.mContext.getResources().getDimensionPixelSize(R.dimen.chooser_direct_share_label_placeholder_max_width));
            viewHolder.text.setBackground(this.mContext.getResources().getDrawable(R.drawable.chooser_direct_share_label_placeholder, this.mContext.getTheme()));
            viewHolder.itemView.setBackground(null);
        } else {
            viewHolder.text.setMaxWidth(Integer.MAX_VALUE);
            viewHolder.text.setBackground(null);
            viewHolder.itemView.setBackground(viewHolder.defaultItemViewBackground);
        }
        viewHolder.text.removeOnLayoutChangeListener(this.mPinTextSpacingListener);
        if (targetInfo instanceof MultiDisplayResolveInfo) {
            Drawable drawable = this.mContext.getDrawable(R.drawable.chooser_group_background);
            viewHolder.text.setPaddingRelative(0, 0, drawable.getIntrinsicWidth(), 0);
            viewHolder.text.setBackground(drawable);
        } else {
            if (targetInfo.isPinned() && (getPositionTargetType(i) == 2 || getPositionTargetType(i) == 1)) {
                Drawable drawable2 = this.mContext.getDrawable(R.drawable.chooser_pinned_background);
                viewHolder.text.setPaddingRelative(drawable2.getIntrinsicWidth(), 0, 0, 0);
                viewHolder.text.setBackground(drawable2);
                viewHolder.text.addOnLayoutChangeListener(this.mPinTextSpacingListener);
                return;
            }
            viewHolder.text.setBackground(null);
            viewHolder.text.setPaddingRelative(0, 0, 0, 0);
        }
    }

    @Override // com.android.internal.app.ResolverListAdapter
    protected void onIconLoaded(DisplayResolveInfo displayResolveInfo) {
        Consumer<DisplayResolveInfo> consumer;
        if (Flags.notifySingleItemChangeOnIconLoad() && (consumer = this.mOnIconLoadedListener) != null) {
            consumer.accept(displayResolveInfo);
        } else {
            notifyDataSetChanged();
        }
    }

    private void loadDirectShareIcon(SelectableTargetInfo selectableTargetInfo) {
        if (this.mIconLoaders.get(selectableTargetInfo) == null) {
            LoadDirectShareIconTask createLoadDirectShareIconTask = createLoadDirectShareIconTask(selectableTargetInfo);
            this.mIconLoaders.put(selectableTargetInfo, createLoadDirectShareIconTask);
            createLoadDirectShareIconTask.loadIcon();
        }
    }

    protected LoadDirectShareIconTask createLoadDirectShareIconTask(SelectableTargetInfo selectableTargetInfo) {
        return new LoadDirectShareIconTask(selectableTargetInfo);
    }

    void updateAlphabeticalList() {
        new AsyncTask<Void, Void, List<DisplayResolveInfo>>() { // from class: com.android.internal.app.ChooserListAdapter.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public List<DisplayResolveInfo> doInBackground(Void... voidArr) {
                ArrayList<DisplayResolveInfo> arrayList = new ArrayList();
                arrayList.addAll(ChooserListAdapter.this.mDisplayList);
                arrayList.addAll(ChooserListAdapter.this.mCallerTargets);
                if (!ChooserListAdapter.this.mEnableStackedApps) {
                    return arrayList;
                }
                HashMap hashMap = new HashMap();
                for (DisplayResolveInfo displayResolveInfo : arrayList) {
                    if (displayResolveInfo.getResolveInfo().userHandle == null) {
                        Log.e(ChooserListAdapter.TAG, "ResolveInfo with null UserHandle found: " + displayResolveInfo.getResolveInfo());
                    }
                    String str = displayResolveInfo.getResolvedComponentName().getPackageName() + '#' + ((Object) displayResolveInfo.getDisplayLabel()) + '#' + displayResolveInfo.getResolveInfo().userHandle.getIdentifier();
                    DisplayResolveInfo displayResolveInfo2 = (DisplayResolveInfo) hashMap.get(str);
                    if (displayResolveInfo2 == null) {
                        hashMap.put(str, displayResolveInfo);
                    } else if (displayResolveInfo2 instanceof MultiDisplayResolveInfo) {
                        ((MultiDisplayResolveInfo) displayResolveInfo2).addTarget(displayResolveInfo);
                    } else {
                        MultiDisplayResolveInfo multiDisplayResolveInfo = new MultiDisplayResolveInfo(str, displayResolveInfo2);
                        multiDisplayResolveInfo.addTarget(displayResolveInfo);
                        hashMap.put(str, multiDisplayResolveInfo);
                    }
                }
                ArrayList arrayList2 = new ArrayList();
                arrayList2.addAll(hashMap.values());
                Collections.sort(arrayList2, new ChooserActivity.AzInfoComparator(ChooserListAdapter.this.mContext));
                return arrayList2;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(List<DisplayResolveInfo> list) {
                ChooserListAdapter.this.mSortedList = list;
                ChooserListAdapter.this.notifyDataSetChanged();
            }
        }.execute(new Void[0]);
    }

    @Override // com.android.internal.app.ResolverListAdapter, android.widget.Adapter
    public int getCount() {
        return getRankedTargetCount() + getAlphaTargetCount() + getSelectableServiceTargetCount() + getCallerTargetCount();
    }

    @Override // com.android.internal.app.ResolverListAdapter
    public int getUnfilteredCount() {
        int unfilteredCount = super.getUnfilteredCount();
        if (unfilteredCount > this.mChooserListCommunicator.getMaxRankedTargets()) {
            unfilteredCount += this.mChooserListCommunicator.getMaxRankedTargets();
        }
        return unfilteredCount + getSelectableServiceTargetCount() + getCallerTargetCount();
    }

    public int getCallerTargetCount() {
        return this.mCallerTargets.size();
    }

    public int getSelectableServiceTargetCount() {
        Iterator<ChooserTargetInfo> it = this.mServiceTargets.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next() instanceof SelectableTargetInfo) {
                i++;
            }
        }
        return i;
    }

    public int getServiceTargetCount() {
        if (this.mChooserListCommunicator.shouldShowServiceTargets()) {
            return Math.min(this.mServiceTargets.size(), this.mChooserListCommunicator.getMaxRankedTargets());
        }
        return 0;
    }

    int getAlphaTargetCount() {
        int size = this.mSortedList.size();
        if (this.mCallerTargets.size() + this.mDisplayList.size() > this.mChooserListCommunicator.getMaxRankedTargets()) {
            return size;
        }
        return 0;
    }

    public int getRankedTargetCount() {
        return Math.min(this.mChooserListCommunicator.getMaxRankedTargets() - getCallerTargetCount(), super.getCount());
    }

    public int getPositionTargetType(int i) {
        int serviceTargetCount = getServiceTargetCount();
        if (i < serviceTargetCount) {
            return 1;
        }
        int callerTargetCount = getCallerTargetCount();
        if (i - serviceTargetCount < callerTargetCount) {
            return 0;
        }
        int i2 = serviceTargetCount + callerTargetCount;
        int rankedTargetCount = getRankedTargetCount();
        if (i - i2 < rankedTargetCount) {
            return 2;
        }
        return i - (i2 + rankedTargetCount) < getAlphaTargetCount() ? 3 : -1;
    }

    @Override // com.android.internal.app.ResolverListAdapter, android.widget.Adapter
    public TargetInfo getItem(int i) {
        return targetInfoForPosition(i, true);
    }

    @Override // com.android.internal.app.ResolverListAdapter
    public TargetInfo targetInfoForPosition(int i, boolean z) {
        int selectableServiceTargetCount;
        if (i == -1) {
            return null;
        }
        if (z) {
            selectableServiceTargetCount = getServiceTargetCount();
        } else {
            selectableServiceTargetCount = getSelectableServiceTargetCount();
        }
        if (i < selectableServiceTargetCount) {
            return this.mServiceTargets.get(i);
        }
        int callerTargetCount = getCallerTargetCount();
        int i2 = i - selectableServiceTargetCount;
        if (i2 < callerTargetCount) {
            return this.mCallerTargets.get(i2);
        }
        int i3 = selectableServiceTargetCount + callerTargetCount;
        int rankedTargetCount = getRankedTargetCount();
        int i4 = i - i3;
        if (i4 < rankedTargetCount) {
            if (z) {
                return super.getItem(i4);
            }
            return getDisplayResolveInfo(i4);
        }
        int i5 = i - (i3 + rankedTargetCount);
        if (i5 >= getAlphaTargetCount() || this.mSortedList.isEmpty()) {
            return null;
        }
        return this.mSortedList.get(i5);
    }

    @Override // com.android.internal.app.ResolverListAdapter
    protected boolean shouldAddResolveInfo(DisplayResolveInfo displayResolveInfo) {
        Iterator<DisplayResolveInfo> it = this.mCallerTargets.iterator();
        while (it.hasNext()) {
            if (this.mResolverListCommunicator.resolveInfoMatch(displayResolveInfo.getResolveInfo(), it.next().getResolveInfo())) {
                return false;
            }
        }
        return super.shouldAddResolveInfo(displayResolveInfo);
    }

    public List<ChooserTargetInfo> getSurfacedTargetInfo() {
        return this.mServiceTargets.subList(0, Math.min(this.mChooserListCommunicator.getMaxRankedTargets(), getSelectableServiceTargetCount()));
    }

    public void addServiceResults(DisplayResolveInfo displayResolveInfo, List<ChooserTarget> list, int i, Map<ChooserTarget, ShortcutInfo> map) {
        if (list.size() == 0) {
            return;
        }
        DisplayResolveInfo displayResolveInfo2 = displayResolveInfo;
        float baseScore = getBaseScore(displayResolveInfo2, i);
        Collections.sort(list, this.mBaseTargetComparator);
        boolean z = i == 2 || i == 3;
        int min = this.mApplySharingAppLimits ? Math.min(list.size(), z ? this.mMaxShortcutTargetsPerApp : 2) : list.size();
        float f = 0.0f;
        int i2 = 0;
        boolean z2 = false;
        while (i2 < min) {
            ChooserTarget chooserTarget = list.get(i2);
            float score = chooserTarget.getScore();
            if (this.mApplySharingAppLimits) {
                score *= baseScore;
                if (i2 > 0 && score >= f) {
                    score = 0.95f * f;
                }
            }
            ShortcutInfo shortcutInfo = z ? map.get(chooserTarget) : null;
            if (shortcutInfo != null && shortcutInfo.isPinned()) {
                score += 1000.0f;
            }
            float f2 = score;
            boolean insertServiceTarget = insertServiceTarget(new SelectableTargetInfo(this.mContext.createContextAsUser(getUserHandle(), 0), displayResolveInfo2, chooserTarget, f2, this.mSelectableTargetInfoCommunicator, shortcutInfo));
            if (insertServiceTarget && z) {
                this.mNumShortcutResults++;
            }
            z2 |= insertServiceTarget;
            i2++;
            displayResolveInfo2 = displayResolveInfo;
            f = f2;
        }
        if (z2) {
            notifyDataSetChanged();
        }
    }

    int getNumServiceTargetsForExpand() {
        return this.mNumShortcutResults;
    }

    public float getBaseScore(DisplayResolveInfo displayResolveInfo, int i) {
        if (displayResolveInfo == null) {
            return 900.0f;
        }
        float score = super.getScore(displayResolveInfo);
        return (i == 2 || i == 3) ? score * 90.0f : score;
    }

    static /* synthetic */ boolean lambda$completeServiceTargetLoading$0(ChooserTargetInfo chooserTargetInfo) {
        return chooserTargetInfo instanceof ChooserActivity.PlaceHolderTargetInfo;
    }

    public void completeServiceTargetLoading() {
        this.mServiceTargets.removeIf(new Predicate() { // from class: com.android.internal.app.ChooserListAdapter$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ChooserListAdapter.lambda$completeServiceTargetLoading$0((ChooserTargetInfo) obj);
            }
        });
        if (this.mServiceTargets.isEmpty()) {
            this.mServiceTargets.add(new ChooserActivity.EmptyTargetInfo());
            this.mChooserActivityLogger.logSharesheetEmptyDirectShareRow();
        }
        notifyDataSetChanged();
    }

    private boolean insertServiceTarget(ChooserTargetInfo chooserTargetInfo) {
        if (this.mServiceTargets.size() == 1 && (this.mServiceTargets.get(0) instanceof ChooserActivity.EmptyTargetInfo)) {
            return false;
        }
        Iterator<ChooserTargetInfo> it = this.mServiceTargets.iterator();
        while (it.hasNext()) {
            if (chooserTargetInfo.isSimilar(it.next())) {
                return false;
            }
        }
        int size = this.mServiceTargets.size();
        float modifiedScore = chooserTargetInfo.getModifiedScore();
        for (int i = 0; i < Math.min(size, this.mChooserListCommunicator.getMaxRankedTargets()); i++) {
            ChooserTargetInfo chooserTargetInfo2 = this.mServiceTargets.get(i);
            if (chooserTargetInfo2 == null) {
                this.mServiceTargets.set(i, chooserTargetInfo);
                return true;
            }
            if (modifiedScore > chooserTargetInfo2.getModifiedScore()) {
                this.mServiceTargets.add(i, chooserTargetInfo);
                return true;
            }
        }
        if (size >= this.mChooserListCommunicator.getMaxRankedTargets()) {
            return false;
        }
        this.mServiceTargets.add(chooserTargetInfo);
        return true;
    }

    public ChooserTarget getChooserTargetForValue(int i) {
        return this.mServiceTargets.get(i).getChooserTarget();
    }

    @Override // com.android.internal.app.ResolverListAdapter
    AsyncTask<List<ResolverActivity.ResolvedComponentInfo>, Void, List<ResolverActivity.ResolvedComponentInfo>> createSortingTask(final boolean z) {
        return new AsyncTask<List<ResolverActivity.ResolvedComponentInfo>, Void, List<ResolverActivity.ResolvedComponentInfo>>() { // from class: com.android.internal.app.ChooserListAdapter.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public List<ResolverActivity.ResolvedComponentInfo> doInBackground(List<ResolverActivity.ResolvedComponentInfo>... listArr) {
                Trace.beginSection("ChooserListAdapter#SortingTask");
                ChooserListAdapter.this.mResolverListController.topK(listArr[0], ChooserListAdapter.this.mChooserListCommunicator.getMaxRankedTargets());
                Trace.endSection();
                return listArr[0];
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(List<ResolverActivity.ResolvedComponentInfo> list) {
                ChooserListAdapter.this.processSortedList(list, z);
                if (z) {
                    ChooserListAdapter.this.mChooserListCommunicator.updateProfileViewButton();
                    ChooserListAdapter.this.notifyDataSetChanged();
                }
            }
        };
    }

    public void setAppPredictor(AppPredictor appPredictor) {
        this.mAppPredictor = appPredictor;
    }

    public void setAppPredictorCallback(AppPredictor.Callback callback, ResolverAppPredictorCallback resolverAppPredictorCallback) {
        this.mAppPredictorCallback = callback;
        this.mAppPredictorCallbackWrapper = resolverAppPredictorCallback;
    }

    public void destroyAppPredictor() {
        if (getAppPredictor() != null) {
            getAppPredictor().unregisterPredictionUpdates(this.mAppPredictorCallback);
            getAppPredictor().destroy();
            setAppPredictor(null);
        }
        ResolverAppPredictorCallback resolverAppPredictorCallback = this.mAppPredictorCallbackWrapper;
        if (resolverAppPredictorCallback != null) {
            resolverAppPredictorCallback.destroy();
        }
    }

    public class LoadDirectShareIconTask extends AsyncTask<Void, Void, Boolean> {
        private final SelectableTargetInfo mTargetInfo;

        private LoadDirectShareIconTask(SelectableTargetInfo selectableTargetInfo) {
            this.mTargetInfo = selectableTargetInfo;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Boolean doInBackground(Void... voidArr) {
            return Boolean.valueOf(this.mTargetInfo.loadIcon());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Boolean bool) {
            if (bool.booleanValue()) {
                ChooserListAdapter.this.notifyDataSetChanged();
            }
        }

        public void loadIcon() {
            execute(new Void[0]);
        }
    }
}
