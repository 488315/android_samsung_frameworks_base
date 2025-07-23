package androidx.slice.widget;

import android.R;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import com.android.systemui.volume.VolumePanelDialog$$ExternalSyntheticLambda5;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SliceView extends ViewGroup implements Observer, View.OnClickListener {
    public static final AnonymousClass3 SLICE_ACTION_PRIORITY_COMPARATOR = new Comparator() { // from class: androidx.slice.widget.SliceView.3
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            int priority = ((SliceAction) obj).getPriority();
            int priority2 = ((SliceAction) obj2).getPriority();
            if (priority < 0 && priority2 < 0) {
                return 0;
            }
            if (priority < 0) {
                return 1;
            }
            if (priority2 < 0) {
                return -1;
            }
            if (priority2 < priority) {
                return 1;
            }
            return priority2 > priority ? -1 : 0;
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
    public final AnonymousClass1 mLongpressCheck;
    public int mMinTemplateHeight;
    public View.OnClickListener mOnClickListener;
    public boolean mPressing;
    public final AnonymousClass2 mRefreshLastUpdated;
    public int mShortcutSize;
    public boolean mShowActionDividers;
    public boolean mShowHeaderDivider;
    public final boolean mShowLastUpdated;
    public boolean mShowTitleItems;
    public SliceMetadata mSliceMetadata;
    public VolumePanelDialog$$ExternalSyntheticLambda5 mSliceObserver;
    public SliceStyle mSliceStyle;
    public int mThemeTintColor;
    public int mTouchSlopSquared;
    public SliceViewPolicy mViewPolicy;

    public SliceView(Context context) {
        this(context, null);
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

    public final void init$1(Context context, AttributeSet attributeSet, int i, int i2) {
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
        this.mCurrentView.setSliceActionListener(this.mSliceObserver);
        SliceChildView sliceChildView2 = this.mCurrentView;
        SliceStyle sliceStyle2 = this.mSliceStyle;
        sliceChildView2.setStyle(sliceStyle2, sliceStyle2.getRowStyle());
        this.mCurrentView.setTint(getTintColor());
        ListContent listContent = this.mListContent;
        if (listContent == null || listContent.getLayoutDir() == -1) {
            this.mCurrentView.setLayoutDirection(2);
        } else {
            this.mCurrentView.setLayoutDirection(this.mListContent.getLayoutDir());
        }
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
                sliceMetricsWrapper.mSliceMetrics.logVisible();
                this.mCurrentSliceLoggedVisible = true;
            }
            if (z || !this.mCurrentSliceLoggedVisible) {
                return;
            }
            this.mCurrentSliceMetrics.mSliceMetrics.logHidden();
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
        setSlice((Slice) obj);
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
            this.mViewPolicy.getClass();
            EventInfo eventInfo = new EventInfo(2, 3, iArr[0], iArr[1]);
            this.mSliceObserver.onSliceAction(eventInfo);
            if (this.mCurrentSliceMetrics == null || sliceItem2.getSlice() == null || Uri.parse(sliceItem2.getSlice().mUri) == null) {
                return;
            }
            this.mCurrentSliceMetrics.mSliceMetrics.logTouch(eventInfo.actionType, Uri.parse(sliceItem2.getSlice().mUri));
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

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        SliceAdapter sliceAdapter;
        ListContent listContent;
        SliceAdapter sliceAdapter2;
        int size = View.MeasureSpec.getSize(i);
        this.mViewPolicy.getClass();
        int i3 = this.mActionRow.getVisibility() != 8 ? this.mActionRowHeight : 0;
        int size2 = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i2);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        int i4 = ((layoutParams == null || layoutParams.height != -2) && mode != 0) ? size2 : -1;
        ListContent listContent2 = this.mListContent;
        if (listContent2 != null && listContent2.isValid()) {
            SliceViewPolicy sliceViewPolicy = this.mViewPolicy;
            sliceViewPolicy.getClass();
            if (i4 > 0 && i4 < this.mSliceStyle.mRowMaxHeight) {
                int i5 = this.mMinTemplateHeight;
                if (i4 <= i5) {
                    i4 = i5;
                }
                if (sliceViewPolicy.mMaxSmallHeight != i4) {
                    sliceViewPolicy.mMaxSmallHeight = i4;
                    TemplateView templateView = sliceViewPolicy.mListener;
                    if (templateView != null && (sliceAdapter2 = templateView.mAdapter) != null) {
                        sliceAdapter2.notifyHeaderChanged();
                    }
                }
            } else if (sliceViewPolicy.mMaxSmallHeight != 0) {
                sliceViewPolicy.mMaxSmallHeight = 0;
                TemplateView templateView2 = sliceViewPolicy.mListener;
                if (templateView2 != null && (sliceAdapter = templateView2.mAdapter) != null) {
                    sliceAdapter.notifyHeaderChanged();
                }
            }
            SliceViewPolicy sliceViewPolicy2 = this.mViewPolicy;
            if (i4 != sliceViewPolicy2.mMaxHeight) {
                sliceViewPolicy2.mMaxHeight = i4;
                TemplateView templateView3 = sliceViewPolicy2.mListener;
                if (templateView3 != null && (listContent = templateView3.mListContent) != null) {
                    templateView3.updateDisplayedItems(listContent.getHeight(templateView3.mSliceStyle, templateView3.mViewPolicy));
                }
            }
        }
        int paddingTop = (size2 - getPaddingTop()) - getPaddingBottom();
        if (mode != 1073741824) {
            ListContent listContent3 = this.mListContent;
            if (listContent3 == null || !listContent3.isValid()) {
                paddingTop = i3;
            } else {
                SliceViewPolicy sliceViewPolicy3 = this.mViewPolicy;
                sliceViewPolicy3.getClass();
                int height = this.mListContent.getHeight(this.mSliceStyle, sliceViewPolicy3) + i3;
                if (paddingTop > height || mode == 0) {
                    paddingTop = height;
                } else if (!this.mSliceStyle.mExpandToAvailableHeight) {
                    this.mViewPolicy.getClass();
                    int i6 = this.mLargeHeight + i3;
                    if (paddingTop >= i6 || paddingTop <= (i6 = this.mMinTemplateHeight)) {
                        paddingTop = i6;
                    }
                }
            }
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, 1073741824);
        this.mActionRow.measure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec(i3 > 0 ? getPaddingBottom() + i3 : 0, 1073741824));
        this.mCurrentView.measure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec(getPaddingTop() + paddingTop + (i3 <= 0 ? getPaddingBottom() : 0), 1073741824));
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
        if (!this.mShowLastUpdated || (sliceMetadata = this.mSliceMetadata) == null || sliceMetadata.mExpiry == -1) {
            return;
        }
        if (!z) {
            this.mHandler.removeCallbacks(this.mRefreshLastUpdated);
            return;
        }
        Handler handler = this.mHandler;
        AnonymousClass2 anonymousClass2 = this.mRefreshLastUpdated;
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
        handler.postDelayed(anonymousClass2, j);
    }

    public final void setMode(int i) {
        this.mViewPolicy.getClass();
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

    public final void setSlice(Slice slice) {
        RowContent rowContent;
        LocationBasedViewTracker.trackInputFocused(this);
        LocationBasedViewTracker.trackA11yFocus(this);
        boolean z = false;
        if (slice == null || Uri.parse(slice.mUri) == null) {
            logSliceMetricsVisibilityChange(false);
            this.mCurrentSliceMetrics = null;
        } else {
            Slice slice2 = this.mCurrentSlice;
            if (slice2 == null || !Uri.parse(slice2.mUri).equals(Uri.parse(slice.mUri))) {
                logSliceMetricsVisibilityChange(false);
                this.mCurrentSliceMetrics = new SliceMetricsWrapper(getContext(), Uri.parse(slice.mUri));
            }
        }
        boolean z2 = (slice == null || this.mCurrentSlice == null || !Uri.parse(slice.mUri).equals(Uri.parse(this.mCurrentSlice.mUri))) ? false : true;
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
                ArrayList arrayList = listContent2.mRowItems;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    SliceContent sliceContent = (SliceContent) obj;
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

    public void setSliceViewPolicy(SliceViewPolicy sliceViewPolicy) {
        this.mViewPolicy = sliceViewPolicy;
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
        init$1(context, attributeSet, i, com.android.systemui.R.style.Widget_SliceView);
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
        init$1(context, attributeSet, i, i2);
    }
}
