package androidx.slice.widget;

import android.R;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.slice.ArrayUtils;
import androidx.slice.Slice;
import androidx.slice.SliceItem;
import androidx.slice.SliceMetadata;
import androidx.slice.core.SliceAction;
import androidx.slice.core.SliceActionImpl;
import androidx.slice.core.SliceQuery;
import androidx.slice.widget.SliceViewPolicy;
import com.android.systemui.volume.VolumePanelDialog$$ExternalSyntheticLambda4;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SliceView extends ViewGroup implements Observer, View.OnClickListener {
    public static final C05153 SLICE_ACTION_PRIORITY_COMPARATOR = new Comparator() { // from class: androidx.slice.widget.SliceView.3
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            int priority = ((SliceAction) obj).getPriority();
            int priority2 = ((SliceAction) obj2).getPriority();
            if (priority < 0 && priority2 < 0) {
                return 0;
            }
            if (priority >= 0) {
                if (priority2 >= 0) {
                    if (priority2 >= priority) {
                        if (priority2 <= priority) {
                            return 0;
                        }
                    }
                }
                return -1;
            }
            return 1;
        }
    };
    public ActionRow mActionRow;
    public int mActionRowHeight;
    public List mActions;
    public int[] mClickInfo;
    public Slice mCurrentSlice;
    public boolean mCurrentSliceLoggedVisible;
    public SliceMetricsWrapper mCurrentSliceMetrics;
    public SliceChildView mCurrentView;
    public int mDownX;
    public int mDownY;
    public Handler mHandler;
    public boolean mInLongpress;
    public int mLargeHeight;
    public ListContent mListContent;
    public View.OnLongClickListener mLongClickListener;
    public final RunnableC05131 mLongpressCheck;
    public int mMinTemplateHeight;
    public View.OnClickListener mOnClickListener;
    public boolean mPressing;
    public final RunnableC05142 mRefreshLastUpdated;
    public int mShortcutSize;
    public boolean mShowActionDividers;
    public boolean mShowHeaderDivider;
    public final boolean mShowLastUpdated;
    public boolean mShowTitleItems;
    public SliceMetadata mSliceMetadata;
    public VolumePanelDialog$$ExternalSyntheticLambda4 mSliceObserver;
    public SliceStyle mSliceStyle;
    public int mThemeTintColor;
    public int mTouchSlopSquared;
    public SliceViewPolicy mViewPolicy;

    public SliceView(Context context) {
        this(context, null);
    }

    public final void applyConfigurations() {
        this.mCurrentView.setSliceActionListener(this.mSliceObserver);
        SliceChildView sliceChildView = this.mCurrentView;
        SliceStyle sliceStyle = this.mSliceStyle;
        sliceChildView.setStyle(sliceStyle, sliceStyle.getRowStyle(null));
        this.mCurrentView.setTint(getTintColor());
        ListContent listContent = this.mListContent;
        if (listContent == null || listContent.getLayoutDir() == -1) {
            this.mCurrentView.setLayoutDirection(2);
        } else {
            this.mCurrentView.setLayoutDirection(this.mListContent.getLayoutDir());
        }
    }

    public final ViewGroup.LayoutParams getChildLp(View view) {
        if (!(view instanceof ShortcutView)) {
            return new ViewGroup.LayoutParams(-1, -1);
        }
        int i = this.mShortcutSize;
        return new ViewGroup.LayoutParams(i, i);
    }

    public final int getTintColor() {
        int i = this.mThemeTintColor;
        if (i != -1) {
            return i;
        }
        SliceItem findSubtype = SliceQuery.findSubtype(this.mCurrentSlice, "int", "color");
        return findSubtype != null ? findSubtype.getInt() : SliceViewUtil.getColorAttr(R.attr.colorAccent, getContext());
    }

    public final boolean handleTouchForLongpress(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mHandler.removeCallbacks(this.mLongpressCheck);
            this.mDownX = (int) motionEvent.getRawX();
            this.mDownY = (int) motionEvent.getRawY();
            this.mPressing = true;
            this.mInLongpress = false;
            this.mHandler.postDelayed(this.mLongpressCheck, ViewConfiguration.getLongPressTimeout());
            return false;
        }
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                int rawX = ((int) motionEvent.getRawX()) - this.mDownX;
                int rawY = ((int) motionEvent.getRawY()) - this.mDownY;
                if ((rawY * rawY) + (rawX * rawX) > this.mTouchSlopSquared) {
                    this.mPressing = false;
                    this.mHandler.removeCallbacks(this.mLongpressCheck);
                }
                return this.mInLongpress;
            }
            if (actionMasked != 3) {
                return false;
            }
        }
        boolean z = this.mInLongpress;
        this.mPressing = false;
        this.mInLongpress = false;
        this.mHandler.removeCallbacks(this.mLongpressCheck);
        return z;
    }

    public final void init(Context context, AttributeSet attributeSet, int i, int i2) {
        SliceStyle sliceStyle = new SliceStyle(context, attributeSet, i, i2);
        this.mSliceStyle = sliceStyle;
        this.mThemeTintColor = sliceStyle.mTintColor;
        this.mShortcutSize = getContext().getResources().getDimensionPixelSize(com.android.systemui.R.dimen.abc_slice_shortcut_size);
        this.mMinTemplateHeight = getContext().getResources().getDimensionPixelSize(com.android.systemui.R.dimen.abc_slice_row_min_height);
        this.mLargeHeight = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.abc_slice_large_height);
        this.mActionRowHeight = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.abc_slice_action_row_height);
        this.mViewPolicy = new SliceViewPolicy();
        TemplateView templateView = new TemplateView(getContext());
        this.mCurrentView = templateView;
        templateView.setPolicy(this.mViewPolicy);
        SliceChildView sliceChildView = this.mCurrentView;
        addView(sliceChildView, getChildLp(sliceChildView));
        applyConfigurations();
        ActionRow actionRow = new ActionRow(getContext(), true);
        this.mActionRow = actionRow;
        actionRow.setBackground(new ColorDrawable(-1118482));
        ActionRow actionRow2 = this.mActionRow;
        addView(actionRow2, getChildLp(actionRow2));
        updateActions();
        int scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mTouchSlopSquared = scaledTouchSlop * scaledTouchSlop;
        this.mHandler = new Handler();
        setClipToPadding(false);
        super.setOnClickListener(this);
    }

    public final void logSliceMetricsVisibilityChange(boolean z) {
        SliceMetricsWrapper sliceMetricsWrapper = this.mCurrentSliceMetrics;
        if (sliceMetricsWrapper != null) {
            if (z && !this.mCurrentSliceLoggedVisible) {
                sliceMetricsWrapper.logVisible();
                this.mCurrentSliceLoggedVisible = true;
            }
            if (z || !this.mCurrentSliceLoggedVisible) {
                return;
            }
            this.mCurrentSliceMetrics.logHidden();
            this.mCurrentSliceLoggedVisible = false;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isShown()) {
            logSliceMetricsVisibilityChange(true);
            refreshLastUpdatedLabel(true);
        }
    }

    @Override // androidx.lifecycle.Observer
    public final void onChanged(Object obj) {
        RowContent rowContent;
        Slice slice = (Slice) obj;
        LocationBasedViewTracker.trackInputFocused(this);
        LocationBasedViewTracker.trackA11yFocus(this);
        boolean z = false;
        if (slice == null || slice.getUri() == null) {
            logSliceMetricsVisibilityChange(false);
            this.mCurrentSliceMetrics = null;
        } else {
            Slice slice2 = this.mCurrentSlice;
            if (slice2 == null || !slice2.getUri().equals(slice.getUri())) {
                logSliceMetricsVisibilityChange(false);
                this.mCurrentSliceMetrics = new SliceMetricsWrapper(getContext(), slice.getUri());
            }
        }
        boolean z2 = (slice == null || this.mCurrentSlice == null || !slice.getUri().equals(this.mCurrentSlice.getUri())) ? false : true;
        SliceMetadata sliceMetadata = this.mSliceMetadata;
        this.mCurrentSlice = slice;
        SliceMetadata from = slice != null ? SliceMetadata.from(getContext(), this.mCurrentSlice) : null;
        this.mSliceMetadata = from;
        if (!z2) {
            this.mCurrentView.resetView();
        } else if (sliceMetadata.getLoadingState() == 2 && from.getLoadingState() == 0) {
            return;
        }
        SliceMetadata sliceMetadata2 = this.mSliceMetadata;
        ListContent listContent = sliceMetadata2 != null ? sliceMetadata2.mListContent : null;
        this.mListContent = listContent;
        if (this.mShowTitleItems) {
            this.mShowTitleItems = true;
            if (listContent != null && (rowContent = listContent.mHeaderContent) != null) {
                rowContent.mShowTitleItems = true;
            }
        }
        if (this.mShowHeaderDivider) {
            this.mShowHeaderDivider = true;
            if (listContent != null && listContent.mHeaderContent != null && listContent.mRowItems.size() > 1) {
                listContent.mHeaderContent.mShowBottomDivider = true;
            }
        }
        if (this.mShowActionDividers) {
            this.mShowActionDividers = true;
            ListContent listContent2 = this.mListContent;
            if (listContent2 != null) {
                Iterator it = listContent2.mRowItems.iterator();
                while (it.hasNext()) {
                    SliceContent sliceContent = (SliceContent) it.next();
                    if (sliceContent instanceof RowContent) {
                        ((RowContent) sliceContent).mShowActionDivider = true;
                    }
                }
            }
        }
        ListContent listContent3 = this.mListContent;
        if (listContent3 == null || !listContent3.isValid()) {
            this.mActions = null;
            this.mCurrentView.resetView();
            updateActions();
            return;
        }
        this.mCurrentView.setLoadingActions(null);
        SliceMetadata sliceMetadata3 = this.mSliceMetadata;
        this.mActions = sliceMetadata3.mSliceActions;
        this.mCurrentView.setLastUpdated(sliceMetadata3.mLastUpdated);
        SliceChildView sliceChildView = this.mCurrentView;
        if (this.mShowLastUpdated && this.mSliceMetadata.isExpired()) {
            z = true;
        }
        sliceChildView.setShowLastUpdated(z);
        this.mCurrentView.setAllowTwoLines(ArrayUtils.contains(this.mSliceMetadata.mSlice.mHints, "permission_request"));
        this.mCurrentView.setTint(getTintColor());
        if (this.mListContent.getLayoutDir() != -1) {
            this.mCurrentView.setLayoutDirection(this.mListContent.getLayoutDir());
        } else {
            this.mCurrentView.setLayoutDirection(2);
        }
        this.mCurrentView.setSliceContent(this.mListContent);
        updateActions();
        logSliceMetricsVisibilityChange(true);
        refreshLastUpdatedLabel(true);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int[] iArr;
        ListContent listContent = this.mListContent;
        if (listContent == null || listContent.getShortcut(getContext()) == null) {
            View.OnClickListener onClickListener = this.mOnClickListener;
            if (onClickListener != null) {
                onClickListener.onClick(this);
                return;
            }
            return;
        }
        try {
            SliceActionImpl sliceActionImpl = (SliceActionImpl) this.mListContent.getShortcut(getContext());
            SliceItem sliceItem = sliceActionImpl.mActionItem;
            SliceItem sliceItem2 = sliceActionImpl.mSliceItem;
            if (sliceItem != null) {
                sliceItem.fireActionInternal(getContext(), null);
            }
            if (sliceItem == null || this.mSliceObserver == null || (iArr = this.mClickInfo) == null || iArr.length <= 1) {
                return;
            }
            EventInfo eventInfo = new EventInfo(this.mViewPolicy.mMode, 3, iArr[0], iArr[1]);
            this.mSliceObserver.onSliceAction(eventInfo);
            if (this.mCurrentSliceMetrics == null || sliceItem2.getSlice() == null || sliceItem2.getSlice().getUri() == null) {
                return;
            }
            this.mCurrentSliceMetrics.mSliceMetrics.logTouch(eventInfo.actionType, sliceItem2.getSlice().getUri());
        } catch (PendingIntent.CanceledException e) {
            Log.e("SliceView", "PendingIntent for slice cannot be sent", e);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        logSliceMetricsVisibilityChange(false);
        refreshLastUpdatedLabel(false);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return (this.mLongClickListener != null && handleTouchForLongpress(motionEvent)) || super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        SliceChildView sliceChildView = this.mCurrentView;
        sliceChildView.layout(0, 0, sliceChildView.getMeasuredWidth(), sliceChildView.getMeasuredHeight());
        if (this.mActionRow.getVisibility() != 8) {
            int measuredHeight = sliceChildView.getMeasuredHeight();
            ActionRow actionRow = this.mActionRow;
            actionRow.layout(0, measuredHeight, actionRow.getMeasuredWidth(), this.mActionRow.getMeasuredHeight() + measuredHeight);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x00e1, code lost:
    
        if (r2 >= (r10 + r0)) goto L70;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMeasure(int i, int i2) {
        int i3;
        SliceAdapter sliceAdapter;
        TemplateView templateView;
        ListContent listContent;
        SliceAdapter sliceAdapter2;
        int size = View.MeasureSpec.getSize(i);
        if (3 == this.mViewPolicy.mMode) {
            size = getPaddingRight() + getPaddingLeft() + this.mShortcutSize;
        }
        int i4 = this.mActionRow.getVisibility() != 8 ? this.mActionRowHeight : 0;
        int size2 = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i2);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        int i5 = ((layoutParams == null || layoutParams.height != -2) && mode != 0) ? size2 : -1;
        ListContent listContent2 = this.mListContent;
        if (listContent2 != null && listContent2.isValid()) {
            SliceViewPolicy sliceViewPolicy = this.mViewPolicy;
            if (sliceViewPolicy.mMode != 3) {
                if (i5 > 0 && i5 < this.mSliceStyle.mRowMaxHeight) {
                    int i6 = this.mMinTemplateHeight;
                    if (i5 <= i6) {
                        i5 = i6;
                    }
                    if (sliceViewPolicy.mMaxSmallHeight != i5) {
                        sliceViewPolicy.mMaxSmallHeight = i5;
                        SliceViewPolicy.PolicyChangeListener policyChangeListener = sliceViewPolicy.mListener;
                        if (policyChangeListener != null && (sliceAdapter2 = ((TemplateView) policyChangeListener).mAdapter) != null) {
                            sliceAdapter2.notifyHeaderChanged();
                        }
                    }
                } else if (sliceViewPolicy.mMaxSmallHeight != 0) {
                    sliceViewPolicy.mMaxSmallHeight = 0;
                    SliceViewPolicy.PolicyChangeListener policyChangeListener2 = sliceViewPolicy.mListener;
                    if (policyChangeListener2 != null && (sliceAdapter = ((TemplateView) policyChangeListener2).mAdapter) != null) {
                        sliceAdapter.notifyHeaderChanged();
                    }
                }
                SliceViewPolicy sliceViewPolicy2 = this.mViewPolicy;
                if (i5 != sliceViewPolicy2.mMaxHeight) {
                    sliceViewPolicy2.mMaxHeight = i5;
                    SliceViewPolicy.PolicyChangeListener policyChangeListener3 = sliceViewPolicy2.mListener;
                    if (policyChangeListener3 != null && (listContent = (templateView = (TemplateView) policyChangeListener3).mListContent) != null) {
                        templateView.updateDisplayedItems(listContent.getHeight(templateView.mSliceStyle, templateView.mViewPolicy));
                    }
                }
            }
        }
        int paddingTop = (size2 - getPaddingTop()) - getPaddingBottom();
        if (mode != 1073741824) {
            ListContent listContent3 = this.mListContent;
            if (listContent3 == null || !listContent3.isValid()) {
                paddingTop = i4;
            } else {
                SliceViewPolicy sliceViewPolicy3 = this.mViewPolicy;
                if (sliceViewPolicy3.mMode == 3) {
                    i3 = this.mShortcutSize;
                } else {
                    int height = this.mListContent.getHeight(this.mSliceStyle, sliceViewPolicy3) + i4;
                    if (paddingTop > height || mode == 0) {
                        paddingTop = height;
                    } else if (!this.mSliceStyle.mExpandToAvailableHeight) {
                        if (this.mViewPolicy.mMode == 2) {
                            i3 = this.mLargeHeight;
                        }
                        int i7 = this.mMinTemplateHeight;
                        if (paddingTop <= i7) {
                            paddingTop = i7;
                        }
                    }
                }
                paddingTop = i3 + i4;
            }
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        this.mActionRow.measure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec(i4 > 0 ? getPaddingBottom() + i4 : 0, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
        this.mCurrentView.measure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec(getPaddingTop() + paddingTop + (i4 <= 0 ? getPaddingBottom() : 0), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
        setMeasuredDimension(size, this.mActionRow.getMeasuredHeight() + this.mCurrentView.getMeasuredHeight());
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        return (this.mLongClickListener != null && handleTouchForLongpress(motionEvent)) || super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (isAttachedToWindow()) {
            logSliceMetricsVisibilityChange(i == 0);
            refreshLastUpdatedLabel(i == 0);
        }
    }

    @Override // android.view.View
    public final void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        logSliceMetricsVisibilityChange(i == 0);
        refreshLastUpdatedLabel(i == 0);
    }

    public final void refreshLastUpdatedLabel(boolean z) {
        SliceMetadata sliceMetadata;
        if (!this.mShowLastUpdated || (sliceMetadata = this.mSliceMetadata) == null) {
            return;
        }
        if (sliceMetadata.mExpiry == -1) {
            return;
        }
        if (!z) {
            this.mHandler.removeCallbacks(this.mRefreshLastUpdated);
            return;
        }
        Handler handler = this.mHandler;
        RunnableC05142 runnableC05142 = this.mRefreshLastUpdated;
        long j = 60000;
        if (!sliceMetadata.isExpired()) {
            SliceMetadata sliceMetadata2 = this.mSliceMetadata;
            sliceMetadata2.getClass();
            long currentTimeMillis = System.currentTimeMillis();
            long j2 = sliceMetadata2.mExpiry;
            long j3 = 0;
            if (j2 != 0 && j2 != -1 && currentTimeMillis <= j2) {
                j3 = j2 - currentTimeMillis;
            }
            j = 60000 + j3;
        }
        handler.postDelayed(runnableC05142, j);
    }

    @Override // android.view.View
    public final void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    @Override // android.view.View
    public final void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        super.setOnLongClickListener(onLongClickListener);
        this.mLongClickListener = onLongClickListener;
    }

    public final void updateActions() {
        if (this.mActions == null) {
            this.mActionRow.setVisibility(8);
            this.mCurrentView.setSliceActions(null);
            this.mCurrentView.setInsets(getPaddingStart(), getPaddingTop(), getPaddingEnd(), getPaddingBottom());
        } else {
            ArrayList arrayList = new ArrayList(this.mActions);
            Collections.sort(arrayList, SLICE_ACTION_PRIORITY_COMPARATOR);
            this.mCurrentView.setSliceActions(arrayList);
            this.mCurrentView.setInsets(getPaddingStart(), getPaddingTop(), getPaddingEnd(), getPaddingBottom());
            this.mActionRow.setVisibility(8);
        }
    }

    public SliceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.sliceViewStyle);
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.slice.widget.SliceView$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [androidx.slice.widget.SliceView$2] */
    public SliceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShowLastUpdated = true;
        this.mCurrentSliceLoggedVisible = false;
        this.mShowTitleItems = false;
        this.mShowHeaderDivider = false;
        this.mShowActionDividers = false;
        this.mThemeTintColor = -1;
        this.mLongpressCheck = new Runnable() { // from class: androidx.slice.widget.SliceView.1
            @Override // java.lang.Runnable
            public final void run() {
                View.OnLongClickListener onLongClickListener;
                SliceView sliceView = SliceView.this;
                if (!sliceView.mPressing || (onLongClickListener = sliceView.mLongClickListener) == null) {
                    return;
                }
                sliceView.mInLongpress = true;
                onLongClickListener.onLongClick(sliceView);
                SliceView.this.performHapticFeedback(0);
            }
        };
        this.mRefreshLastUpdated = new Runnable() { // from class: androidx.slice.widget.SliceView.2
            @Override // java.lang.Runnable
            public final void run() {
                SliceMetadata sliceMetadata = SliceView.this.mSliceMetadata;
                if (sliceMetadata != null && sliceMetadata.isExpired()) {
                    SliceView.this.mCurrentView.setShowLastUpdated(true);
                    SliceView sliceView = SliceView.this;
                    sliceView.mCurrentView.setSliceContent(sliceView.mListContent);
                }
                SliceView.this.mHandler.postDelayed(this, 60000L);
            }
        };
        init(context, attributeSet, i, 2132019191);
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.slice.widget.SliceView$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [androidx.slice.widget.SliceView$2] */
    public SliceView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mShowLastUpdated = true;
        this.mCurrentSliceLoggedVisible = false;
        this.mShowTitleItems = false;
        this.mShowHeaderDivider = false;
        this.mShowActionDividers = false;
        this.mThemeTintColor = -1;
        this.mLongpressCheck = new Runnable() { // from class: androidx.slice.widget.SliceView.1
            @Override // java.lang.Runnable
            public final void run() {
                View.OnLongClickListener onLongClickListener;
                SliceView sliceView = SliceView.this;
                if (!sliceView.mPressing || (onLongClickListener = sliceView.mLongClickListener) == null) {
                    return;
                }
                sliceView.mInLongpress = true;
                onLongClickListener.onLongClick(sliceView);
                SliceView.this.performHapticFeedback(0);
            }
        };
        this.mRefreshLastUpdated = new Runnable() { // from class: androidx.slice.widget.SliceView.2
            @Override // java.lang.Runnable
            public final void run() {
                SliceMetadata sliceMetadata = SliceView.this.mSliceMetadata;
                if (sliceMetadata != null && sliceMetadata.isExpired()) {
                    SliceView.this.mCurrentView.setShowLastUpdated(true);
                    SliceView sliceView = SliceView.this;
                    sliceView.mCurrentView.setSliceContent(sliceView.mListContent);
                }
                SliceView.this.mHandler.postDelayed(this, 60000L);
            }
        };
        init(context, attributeSet, i, i2);
    }
}
