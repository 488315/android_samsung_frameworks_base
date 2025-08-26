package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import com.android.keyguard.AlphaOptimizedLinearLayout;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.statusbar.StatusIconDisplayable;
import com.android.systemui.statusbar.notification.stack.AnimationFilter;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ViewState;
import com.android.systemui.util.DeviceState;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class StatusIconContainer extends AlphaOptimizedLinearLayout {
    public static final AnonymousClass1 ADD_ICON_PROPERTIES;
    public static final AnonymousClass3 ANIMATE_ALL_PROPERTIES;
    public final Configuration mConfiguration;
    public int mCutoutRightSideAvailableWidth;
    public int mCutoutRightSideIconsWidth;
    public float mDeltaWidth;
    public int mIconSpacing;
    public final ArrayList mIgnoredSlots;
    public IndicatorCutoutUtil mIndicatorCutoutUtil;
    public final ArrayList mLayoutStates;
    public final ArrayList mMeasureViews;
    public boolean mNeedsUnderflow;
    public boolean mQsExpansionTransitioning;
    public boolean mShouldRestrictIcons;
    public SidelingCutoutContainerInfo mSidelingCutoutContainerInfo;

    public class StatusIconState extends ViewState {
        public boolean justAdded;
        public boolean qsExpansionTransitioning;
        public int visibleState;

        public StatusIconState() {
            super(false);
            this.visibleState = 0;
            this.justAdded = true;
            this.qsExpansionTransitioning = false;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.systemui.statusbar.notification.stack.ViewState
        public final void applyToView(View view) {
            AnimationProperties animationProperties;
            if (view.getParent() instanceof View) {
                ((View) view.getParent()).getWidth();
            }
            if (view instanceof StatusIconDisplayable) {
                StatusIconDisplayable statusIconDisplayable = (StatusIconDisplayable) view;
                boolean z = true;
                if (this.justAdded || (statusIconDisplayable.getVisibleState() == 2 && this.visibleState == 0)) {
                    super.applyToView(view);
                    view.setAlpha(0.0f);
                    statusIconDisplayable.setVisibleState(2);
                    animationProperties = StatusIconContainer.ADD_ICON_PROPERTIES;
                } else {
                    animationProperties = null;
                    if (statusIconDisplayable.getVisibleState() != this.visibleState) {
                        if (statusIconDisplayable.getVisibleState() == 0 && this.visibleState == 2) {
                            z = false;
                        } else {
                            animationProperties = StatusIconContainer.ANIMATE_ALL_PROPERTIES;
                        }
                    }
                }
                statusIconDisplayable.setVisibleState(this.visibleState, z);
                if (animationProperties == null || this.qsExpansionTransitioning) {
                    super.applyToView(view);
                } else {
                    animateTo(view, animationProperties);
                }
                this.qsExpansionTransitioning = false;
                this.justAdded = false;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.statusbar.notification.stack.AnimationProperties, com.android.systemui.statusbar.phone.StatusIconContainer$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.statusbar.notification.stack.AnimationProperties, com.android.systemui.statusbar.phone.StatusIconContainer$3] */
    static {
        ?? r0 = new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.StatusIconContainer.1
            public final AnimationFilter mAnimationFilter;

            {
                AnimationFilter animationFilter = new AnimationFilter();
                animationFilter.animateAlpha = true;
                this.mAnimationFilter = animationFilter;
            }

            @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
            public final AnimationFilter getAnimationFilter() {
                return this.mAnimationFilter;
            }
        };
        r0.duration = 200L;
        r0.delay = 50L;
        ADD_ICON_PROPERTIES = r0;
        new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.StatusIconContainer.2
            public final AnimationFilter mAnimationFilter;

            {
                AnimationFilter animationFilter = new AnimationFilter();
                animationFilter.animateX = true;
                this.mAnimationFilter = animationFilter;
            }

            @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
            public final AnimationFilter getAnimationFilter() {
                return this.mAnimationFilter;
            }
        }.duration = 200L;
        ?? r02 = new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.StatusIconContainer.3
            public final AnimationFilter mAnimationFilter;

            {
                AnimationFilter animationFilter = new AnimationFilter();
                animationFilter.animateX = true;
                animationFilter.animateY = true;
                animationFilter.animateAlpha = true;
                animationFilter.mAnimatedProperties.add(View.SCALE_X);
                animationFilter.mAnimatedProperties.add(View.SCALE_Y);
                this.mAnimationFilter = animationFilter;
            }

            @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
            public final AnimationFilter getAnimationFilter() {
                return this.mAnimationFilter;
            }
        };
        r02.duration = 200L;
        ANIMATE_ALL_PROPERTIES = r02;
    }

    public StatusIconContainer(Context context) {
        this(context, null);
    }

    public static int getViewTotalMeasuredWidth(View view) {
        return view.getPaddingEnd() + view.getPaddingStart() + view.getMeasuredWidth();
    }

    public final void addIgnoredSlot(String str) {
        if (this.mIgnoredSlots.contains(str)) {
            return;
        }
        this.mIgnoredSlots.add(str);
        requestLayout();
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (BasicRune.STATUS_POP_OVER_PANEL_BAR && windowInsets.getDisplayCutout() != null && this.mIndicatorCutoutUtil != null && DeviceState.isShowingPopOverStatusBar(getContext()) && isShown()) {
            post(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusIconContainer$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    StatusIconContainer statusIconContainer = this.f$0;
                    Rect displayCutoutAreaToExclude = statusIconContainer.mIndicatorCutoutUtil.getDisplayCutoutAreaToExclude();
                    if (displayCutoutAreaToExclude != null) {
                        SidelingCutoutContainerInfo sidelingCutoutContainerInfo = statusIconContainer.mSidelingCutoutContainerInfo;
                        int rightSideAvailableWidth = sidelingCutoutContainerInfo != null ? sidelingCutoutContainerInfo.getRightSideAvailableWidth(displayCutoutAreaToExclude) : 0;
                        if (statusIconContainer.mCutoutRightSideAvailableWidth != rightSideAvailableWidth) {
                            statusIconContainer.mCutoutRightSideAvailableWidth = rightSideAvailableWidth;
                            statusIconContainer.requestLayout();
                        }
                    }
                }
            });
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        int iDiff = configuration.diff(this.mConfiguration);
        this.mConfiguration.setTo(configuration);
        if ((1073745920 & iDiff) != 0) {
            reloadDimens$2();
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        IndicatorCutoutUtil indicatorCutoutUtil;
        Rect displayCutoutAreaToExclude;
        float height = getHeight() / 2.0f;
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            View childAt = getChildAt(i5);
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            int i6 = (int) (height - (measuredHeight / 2.0f));
            childAt.layout(0, i6, measuredWidth, measuredHeight + i6);
        }
        for (int i7 = 0; i7 < getChildCount(); i7++) {
            View childAt2 = getChildAt(i7);
            StatusIconState statusIconState = (StatusIconState) childAt2.getTag(R.id.status_bar_view_state_tag);
            if (statusIconState != null) {
                statusIconState.initFrom(childAt2);
                statusIconState.setAlpha(1.0f);
                statusIconState.hidden = false;
            }
        }
        this.mLayoutStates.clear();
        float width = getWidth();
        float paddingEnd = width - getPaddingEnd();
        getPaddingStart();
        int childCount = getChildCount();
        boolean z2 = false;
        for (int i8 = childCount - 1; i8 >= 0; i8--) {
            View childAt3 = getChildAt(i8);
            StatusIconDisplayable statusIconDisplayable = (StatusIconDisplayable) childAt3;
            StatusIconState statusIconState2 = (StatusIconState) childAt3.getTag(R.id.status_bar_view_state_tag);
            if (!statusIconDisplayable.isIconVisible() || statusIconDisplayable.isIconBlocked() || this.mIgnoredSlots.contains(statusIconDisplayable.getSlot())) {
                statusIconState2.visibleState = 2;
            } else {
                float paddingEnd2 = paddingEnd - (childAt3.getPaddingEnd() + (childAt3.getPaddingStart() + childAt3.getWidth()));
                statusIconState2.visibleState = 0;
                statusIconState2.setXTranslation(paddingEnd2);
                this.mLayoutStates.add(0, statusIconState2);
                if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT && (indicatorCutoutUtil = this.mIndicatorCutoutUtil) != null && this.mSidelingCutoutContainerInfo != null && (displayCutoutAreaToExclude = indicatorCutoutUtil.getDisplayCutoutAreaToExclude()) != null) {
                    float width2 = ((getWidth() - getPaddingEnd()) - this.mCutoutRightSideAvailableWidth) - statusIconState2.mXTranslation;
                    if (width2 > 0.0f && !z2) {
                        paddingEnd2 -= ((childAt3.getPaddingEnd() + (childAt3.getPaddingStart() + childAt3.getWidth())) + ((getResources().getDimensionPixelSize(R.dimen.indicator_marquee_max_shift) * 2) + displayCutoutAreaToExclude.width())) - width2;
                        statusIconState2.setXTranslation(paddingEnd2);
                        z2 = true;
                    }
                }
                paddingEnd = paddingEnd2 - this.mIconSpacing;
            }
        }
        if (isLayoutRtl()) {
            for (int i9 = 0; i9 < childCount; i9++) {
                StatusIconState statusIconState3 = (StatusIconState) getChildAt(i9).getTag(R.id.status_bar_view_state_tag);
                statusIconState3.setXTranslation((width - statusIconState3.mXTranslation) - r0.getWidth());
            }
        }
        for (int i10 = 0; i10 < getChildCount(); i10++) {
            View childAt4 = getChildAt(i10);
            StatusIconState statusIconState4 = (StatusIconState) childAt4.getTag(R.id.status_bar_view_state_tag);
            if (statusIconState4 != null) {
                statusIconState4.applyToView(childAt4);
                statusIconState4.qsExpansionTransitioning = this.mQsExpansionTransitioning;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x008b A[PHI: r10
      0x008b: PHI (r10v9 int) = (r10v7 int), (r10v12 int) binds: [B:40:0x009d, B:32:0x0089] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x008e A[PHI: r10
      0x008e: PHI (r10v8 int) = (r10v7 int), (r10v12 int) binds: [B:40:0x009d, B:32:0x0089] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x014c  */
    @Override // android.widget.LinearLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMeasure(int i, int i2) {
        int paddingTop;
        IndicatorCutoutUtil indicatorCutoutUtil;
        Rect displayCutoutAreaToExclude;
        int dimensionPixelSize;
        int viewTotalMeasuredWidth;
        this.mMeasureViews.clear();
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int childCount = getChildCount();
        int iMax = 0;
        for (int i3 = 0; i3 < childCount; i3++) {
            StatusIconDisplayable statusIconDisplayable = (StatusIconDisplayable) getChildAt(i3);
            if (statusIconDisplayable.isIconVisible() && !statusIconDisplayable.isIconBlocked() && !this.mIgnoredSlots.contains(statusIconDisplayable.getSlot())) {
                this.mMeasureViews.add((View) statusIconDisplayable);
            }
        }
        int size2 = this.mMeasureViews.size();
        int i4 = size2 <= 20 ? 20 : 19;
        int i5 = ((LinearLayout) this).mPaddingLeft + ((LinearLayout) this).mPaddingRight;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, 0);
        this.mNeedsUnderflow = this.mShouldRestrictIcons && size2 > 20;
        int i6 = 0;
        int i7 = i5;
        boolean z = true;
        while (i6 < size2) {
            View view = (View) this.mMeasureViews.get((size2 - i6) - 1);
            measureChild(view, iMakeMeasureSpec, i2);
            int i8 = i6 == size2 + (-1) ? 0 : this.mIconSpacing;
            if (!this.mShouldRestrictIcons) {
                viewTotalMeasuredWidth = getViewTotalMeasuredWidth(view) + i8 + i5;
                if (viewTotalMeasuredWidth <= size) {
                }
            } else if (i6 < i4 && z) {
                viewTotalMeasuredWidth = getViewTotalMeasuredWidth(view) + i8 + i5;
                if (viewTotalMeasuredWidth <= size) {
                    i7 = viewTotalMeasuredWidth;
                    i5 = i7;
                } else {
                    i5 = viewTotalMeasuredWidth;
                }
            } else if (z) {
                z = false;
                if (i5 <= size) {
                    i7 = i5;
                }
            }
            i6++;
        }
        if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT && (indicatorCutoutUtil = this.mIndicatorCutoutUtil) != null && this.mSidelingCutoutContainerInfo != null && (displayCutoutAreaToExclude = indicatorCutoutUtil.getDisplayCutoutAreaToExclude()) != null) {
            this.mCutoutRightSideIconsWidth = 0;
            SidelingCutoutContainerInfo sidelingCutoutContainerInfo = this.mSidelingCutoutContainerInfo;
            this.mCutoutRightSideAvailableWidth = sidelingCutoutContainerInfo != null ? sidelingCutoutContainerInfo.getRightSideAvailableWidth(displayCutoutAreaToExclude) : 0;
            int i9 = 0;
            while (true) {
                if (i9 >= size2) {
                    dimensionPixelSize = 0;
                    break;
                }
                View view2 = (View) this.mMeasureViews.get((size2 - i9) - 1);
                int i10 = i9 == size2 + (-1) ? 0 : this.mIconSpacing;
                if (this.mShouldRestrictIcons) {
                    int viewTotalMeasuredWidth2 = getViewTotalMeasuredWidth(view2) + i10 + this.mCutoutRightSideIconsWidth;
                    this.mCutoutRightSideIconsWidth = viewTotalMeasuredWidth2;
                    if (viewTotalMeasuredWidth2 - this.mCutoutRightSideAvailableWidth > 0) {
                        dimensionPixelSize = (getResources().getDimensionPixelSize(R.dimen.indicator_marquee_max_shift) * 2) + displayCutoutAreaToExclude.width() + (getViewTotalMeasuredWidth(view2) - (this.mCutoutRightSideIconsWidth - this.mCutoutRightSideAvailableWidth));
                        break;
                    }
                }
                i9++;
            }
            float f = dimensionPixelSize;
            this.mDeltaWidth = f;
            i7 = (int) (i7 + f);
            size = (int) (size + f);
            i5 = (int) (i5 + f);
        }
        if (i7 <= ((LinearLayout) this).mPaddingLeft + ((LinearLayout) this).mPaddingRight) {
            size = 0;
        } else if (i7 >= 0 && i7 < size) {
            size = i7;
        }
        if (mode != 1073741824) {
            if (mode == Integer.MIN_VALUE && i5 > size) {
                this.mNeedsUnderflow = true;
            }
            ArrayList arrayList = this.mMeasureViews;
            if (View.MeasureSpec.getMode(i2) != 1073741824) {
                paddingTop = View.MeasureSpec.getSize(i2);
            } else {
                int size3 = arrayList.size();
                int i11 = 0;
                while (i11 < size3) {
                    Object obj = arrayList.get(i11);
                    i11++;
                    iMax = Math.max(((View) obj).getMeasuredHeight(), iMax);
                }
                paddingTop = getPaddingTop() + iMax + getPaddingBottom();
            }
            setMeasuredDimension(i5, paddingTop);
        }
        if (!this.mNeedsUnderflow && i5 > size) {
            this.mNeedsUnderflow = true;
        }
        i5 = size;
        ArrayList arrayList2 = this.mMeasureViews;
        if (View.MeasureSpec.getMode(i2) != 1073741824) {
        }
        setMeasuredDimension(i5, paddingTop);
    }

    @Override // android.view.ViewGroup
    public final void onViewAdded(View view) {
        super.onViewAdded(view);
        StatusIconState statusIconState = new StatusIconState();
        statusIconState.justAdded = true;
        view.setTag(R.id.status_bar_view_state_tag, statusIconState);
    }

    @Override // android.view.ViewGroup
    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        view.setTag(R.id.status_bar_view_state_tag, null);
    }

    public final void reloadDimens$2() throws Resources.NotFoundException {
        getResources().getDimensionPixelSize(17106384);
        getResources().getDimensionPixelSize(R.dimen.overflow_icon_dot_padding);
        this.mIconSpacing = getResources().getDimensionPixelSize(R.dimen.status_bar_system_icon_spacing);
        getResources().getDimensionPixelSize(R.dimen.overflow_dot_radius);
    }

    public final void removeIgnoredSlot(String str) {
        if (this.mIgnoredSlots.remove(str)) {
            requestLayout();
        }
    }

    public StatusIconContainer(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet);
        this.mShouldRestrictIcons = true;
        this.mLayoutStates = new ArrayList();
        this.mMeasureViews = new ArrayList();
        this.mIgnoredSlots = new ArrayList();
        this.mConfiguration = new Configuration(context.getResources().getConfiguration());
        reloadDimens$2();
        setWillNotDraw(true);
    }
}
