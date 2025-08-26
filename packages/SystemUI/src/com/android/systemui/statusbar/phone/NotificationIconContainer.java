package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Icon;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import com.android.app.animation.Interpolators;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.util.ContrastColorUtil;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.headsup.shared.StatusBarNoHunBehavior;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;
import com.android.systemui.statusbar.notification.stack.AnimationFilter;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ViewState;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import noticolorpicker.NotificationColorPicker;

/* loaded from: classes3.dex */
public class NotificationIconContainer extends ViewGroup {
    public static final AnonymousClass4 ADD_ICON_PROPERTIES;
    public static final AnonymousClass1 DOT_ANIMATION_PROPERTIES;
    public static final AnonymousClass2 ICON_ANIMATION_PROPERTIES;
    public static final AnonymousClass6 UNISOLATION_PROPERTY;
    public static final AnonymousClass5 UNISOLATION_PROPERTY_OTHERS;
    public static final AnonymousClass3 sTempProperties;
    public final int[] mAbsolutePosition;
    public int mActualLayoutWidth;
    public float mActualPaddingEnd;
    public final float mActualPaddingStart;
    public int mAddAnimationStartIndex;
    public boolean mAnimationsEnabled;
    public int mCannedAnimationStartIndex;
    public boolean mChangingViewPositions;
    public boolean mDisallowNextAnimation;
    public int mDotPadding;
    public IconState mFirstVisibleIconState;
    public int mIconSize;
    public final HashMap mIconStates;
    public boolean mIsShowingOverflowDot;
    public boolean mIsStaticLayout;
    public StatusBarIconView mIsolatedIcon;
    public StatusBarIconView mIsolatedIconForAnimation;
    public int mMaxIconsOnLockscreen;
    public int mMaxStaticIcons;
    public boolean mOnKeyguardStatusBar;
    public boolean mOverrideIconColor;
    public ArrayMap mReplacingIconsLegacy;
    public int mShelfIconColor;
    public final int mSpeedBumpIndex;
    public int mStaticDotDiameter;
    public int mThemedTextColorPrimary;
    public float mVisualOverflowStart;

    /* renamed from: com.android.systemui.statusbar.phone.NotificationIconContainer$2, reason: invalid class name */
    public class AnonymousClass2 extends AnimationProperties {
        public final AnimationFilter mAnimationFilter;

        public AnonymousClass2() {
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
    }

    /* renamed from: com.android.systemui.statusbar.phone.NotificationIconContainer$3, reason: invalid class name */
    public class AnonymousClass3 extends AnimationProperties {
        public final AnimationFilter mAnimationFilter = new AnimationFilter();

        @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
        public final AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    }

    public class IconState extends ViewState {
        public static final /* synthetic */ int $r8$clinit = 0;
        public float clampedAppearAmount;
        public float iconAppearAmount;
        public int iconColor;
        public boolean justAdded;
        public boolean justReplaced;
        public final NotificationIconContainer$IconState$$ExternalSyntheticLambda0 mCannedAnimationEndListener;
        public final View mView;
        public boolean needsCannedAnimation;
        public boolean noAnimations;
        public int visibleState;

        /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.phone.NotificationIconContainer$IconState$$ExternalSyntheticLambda0] */
        public IconState(View view) {
            super(false);
            this.iconAppearAmount = 1.0f;
            this.clampedAppearAmount = 1.0f;
            this.justAdded = true;
            this.iconColor = 0;
            this.mView = view;
            this.mCannedAnimationEndListener = new Consumer() { // from class: com.android.systemui.statusbar.phone.NotificationIconContainer$IconState$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    NotificationIconContainer.IconState iconState = this.f$0;
                    Property property = (Property) obj;
                    int i = NotificationIconContainer.IconState.$r8$clinit;
                    iconState.getClass();
                    if (property == View.TRANSLATION_Y && iconState.iconAppearAmount == 0.0f && iconState.mView.getVisibility() == 0) {
                        iconState.mView.setVisibility(4);
                    }
                }
            };
        }

        @Override // com.android.systemui.statusbar.notification.stack.ViewState
        public final void applyToView(View view) {
            AnimationProperties animationProperties;
            boolean z;
            if (view instanceof StatusBarIconView) {
                StatusBarIconView statusBarIconView = (StatusBarIconView) view;
                int i = this.visibleState;
                boolean z2 = (i == 2 && statusBarIconView.mVisibleState == 1) || (i == 1 && statusBarIconView.mVisibleState == 2);
                AnonymousClass1 anonymousClass1 = NotificationIconContainer.DOT_ANIMATION_PROPERTIES;
                NotificationIconContainer notificationIconContainer = NotificationIconContainer.this;
                boolean z3 = ((!notificationIconContainer.mAnimationsEnabled && statusBarIconView != notificationIconContainer.mIsolatedIcon) || notificationIconContainer.mDisallowNextAnimation || this.noAnimations || z2) ? false : true;
                if (z3) {
                    if (this.justAdded || this.justReplaced) {
                        super.applyToView(statusBarIconView);
                        if (this.justAdded && this.iconAppearAmount != 0.0f) {
                            statusBarIconView.setAlpha(0.0f);
                            statusBarIconView.setVisibleState(2, false, null, 0L);
                            animationProperties = NotificationIconContainer.ADD_ICON_PROPERTIES;
                            z = true;
                        }
                        z = false;
                        animationProperties = null;
                    } else {
                        if (i != statusBarIconView.mVisibleState) {
                            animationProperties = NotificationIconContainer.DOT_ANIMATION_PROPERTIES;
                            z = true;
                        }
                        z = false;
                        animationProperties = null;
                    }
                    if (!z && notificationIconContainer.mAddAnimationStartIndex >= 0 && notificationIconContainer.indexOfChild(view) >= notificationIconContainer.mAddAnimationStartIndex && (statusBarIconView.mVisibleState != 2 || this.visibleState != 2)) {
                        animationProperties = NotificationIconContainer.DOT_ANIMATION_PROPERTIES;
                        z = true;
                    }
                    if (this.needsCannedAnimation) {
                        AnonymousClass3 anonymousClass3 = NotificationIconContainer.sTempProperties;
                        AnimationFilter animationFilter = anonymousClass3.mAnimationFilter;
                        animationFilter.reset();
                        AnonymousClass2 anonymousClass2 = NotificationIconContainer.ICON_ANIMATION_PROPERTIES;
                        animationFilter.combineFilter(anonymousClass2.mAnimationFilter);
                        anonymousClass3.mInterpolatorMap = null;
                        android.util.ArrayMap arrayMap = anonymousClass2.mInterpolatorMap;
                        if (arrayMap != null) {
                            anonymousClass3.mInterpolatorMap = new android.util.ArrayMap();
                            anonymousClass3.mInterpolatorMap.putAll(arrayMap);
                        }
                        anonymousClass3.setCustomInterpolator(View.TRANSLATION_Y, statusBarIconView.mShowsConversation ? Interpolators.ICON_OVERSHOT_LESS : Interpolators.ICON_OVERSHOT);
                        anonymousClass3.mAnimationEndAction = this.mCannedAnimationEndListener;
                        if (animationProperties != null) {
                            animationFilter.combineFilter(animationProperties.getAnimationFilter());
                            android.util.ArrayMap arrayMap2 = animationProperties.mInterpolatorMap;
                            if (arrayMap2 != null) {
                                if (anonymousClass3.mInterpolatorMap == null) {
                                    anonymousClass3.mInterpolatorMap = new android.util.ArrayMap();
                                }
                                anonymousClass3.mInterpolatorMap.putAll(arrayMap2);
                            }
                        }
                        anonymousClass3.duration = 100L;
                        notificationIconContainer.mCannedAnimationStartIndex = notificationIconContainer.indexOfChild(view);
                        z = true;
                    }
                    if (!z && notificationIconContainer.mCannedAnimationStartIndex >= 0 && notificationIconContainer.indexOfChild(view) > notificationIconContainer.mCannedAnimationStartIndex && (statusBarIconView.mVisibleState != 2 || this.visibleState != 2)) {
                        AnonymousClass3 anonymousClass32 = NotificationIconContainer.sTempProperties;
                        AnimationFilter animationFilter2 = anonymousClass32.mAnimationFilter;
                        animationFilter2.reset();
                        animationFilter2.animateX = true;
                        anonymousClass32.mInterpolatorMap = null;
                        anonymousClass32.duration = 100L;
                    }
                    int i2 = StatusBarNoHunBehavior.$r8$clinit;
                    StatusBarIconView statusBarIconView2 = notificationIconContainer.mIsolatedIconForAnimation;
                    if (statusBarIconView2 != null) {
                        if (view == statusBarIconView2) {
                            NotificationIconContainer.UNISOLATION_PROPERTY.delay = notificationIconContainer.mIsolatedIcon == null ? 0L : 100L;
                        } else {
                            NotificationIconContainer.UNISOLATION_PROPERTY_OTHERS.delay = notificationIconContainer.mIsolatedIcon != null ? 0L : 100L;
                        }
                    }
                }
                statusBarIconView.setVisibleState(this.visibleState, false, null, 0L);
                if (!notificationIconContainer.mOverrideIconColor) {
                    statusBarIconView.setIconColor(notificationIconContainer.mIsStaticLayout ? statusBarIconView.mDrawableColor : this.iconColor, this.needsCannedAnimation && z3);
                } else if (NotificationUtils.isGrayscale(statusBarIconView, ContrastColorUtil.getInstance(notificationIconContainer.getContext()))) {
                    statusBarIconView.setIconColor(notificationIconContainer.mShelfIconColor, this.needsCannedAnimation && z3);
                }
                super.applyToView(view);
                NotificationIconContainer.sTempProperties.mAnimationEndAction = null;
            }
            this.justAdded = false;
            this.justReplaced = false;
            this.needsCannedAnimation = false;
        }

        @Override // com.android.systemui.statusbar.notification.stack.ViewState
        public final void initFrom(View view) {
            super.initFrom(view);
            if (view instanceof StatusBarIconView) {
                this.iconColor = ((StatusBarIconView) view).mDrawableColor;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.statusbar.notification.stack.AnimationProperties, com.android.systemui.statusbar.phone.NotificationIconContainer$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.statusbar.notification.stack.AnimationProperties, com.android.systemui.statusbar.phone.NotificationIconContainer$4] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.statusbar.notification.stack.AnimationProperties, com.android.systemui.statusbar.phone.NotificationIconContainer$5] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.statusbar.notification.stack.AnimationProperties, com.android.systemui.statusbar.phone.NotificationIconContainer$6] */
    static {
        ?? r0 = new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.NotificationIconContainer.1
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
        };
        r0.duration = 200L;
        DOT_ANIMATION_PROPERTIES = r0;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        anonymousClass2.duration = 100L;
        ICON_ANIMATION_PROPERTIES = anonymousClass2;
        sTempProperties = new AnonymousClass3();
        ?? r02 = new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.NotificationIconContainer.4
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
        r02.duration = 200L;
        r02.delay = 50L;
        ADD_ICON_PROPERTIES = r02;
        ?? r03 = new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.NotificationIconContainer.5
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
        r03.duration = 110L;
        UNISOLATION_PROPERTY_OTHERS = r03;
        ?? r04 = new AnimationProperties() { // from class: com.android.systemui.statusbar.phone.NotificationIconContainer.6
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
        };
        r04.duration = 110L;
        UNISOLATION_PROPERTY = r04;
    }

    public NotificationIconContainer(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet);
        this.mSpeedBumpIndex = -1;
        this.mIsStaticLayout = true;
        this.mIconStates = new HashMap();
        this.mActualLayoutWidth = Integer.MIN_VALUE;
        this.mActualPaddingEnd = -2.1474836E9f;
        this.mActualPaddingStart = -2.1474836E9f;
        this.mAddAnimationStartIndex = -1;
        this.mCannedAnimationStartIndex = -1;
        this.mAnimationsEnabled = true;
        this.mAbsolutePosition = new int[2];
        initResources();
        setWillNotDraw(true);
    }

    public final void applyIconStates() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            ViewState viewState = (ViewState) this.mIconStates.get(childAt);
            if (viewState != null) {
                viewState.applyToView(childAt);
            }
        }
        this.mAddAnimationStartIndex = -1;
        this.mCannedAnimationStartIndex = -1;
        this.mDisallowNextAnimation = false;
        this.mIsolatedIconForAnimation = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void calculateIconXTranslations() {
        IconState iconState;
        boolean z;
        float leftBound = getLeftBound();
        int childCount = getChildCount();
        int i = NotificationIconContainerRefactor.$r8$clinit;
        boolean z2 = this.mOnKeyguardStatusBar;
        int i2 = z2 ? 0 : this.mIsStaticLayout ? this.mMaxStaticIcons : childCount;
        if (!z2) {
            i2 = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getStatusBarNotificationStyle() == 0 ? 0 : 3;
        }
        float rightBound = getRightBound();
        this.mVisualOverflowStart = 0.0f;
        this.mFirstVisibleIconState = null;
        int i3 = 0;
        int i4 = -1;
        while (true) {
            if (i3 >= childCount) {
                break;
            }
            View childAt = getChildAt(i3);
            IconState iconState2 = (IconState) this.mIconStates.get(childAt);
            float iconScaleIncreased = 1.0f;
            if (iconState2.iconAppearAmount == 1.0f) {
                iconState2.setXTranslation(leftBound);
            }
            if (this.mFirstVisibleIconState == null) {
                this.mFirstVisibleIconState = iconState2;
            }
            iconState2.visibleState = iconState2.hidden ? 2 : 0;
            if (shouldForceOverflow(i3, this.mSpeedBumpIndex, iconState2.iconAppearAmount, i2)) {
                z = true;
            } else if (!isOverflowing(i3 == childCount + (-1), leftBound, rightBound, this.mIconSize)) {
                z = false;
            }
            if (i4 == -1 && z) {
                this.mVisualOverflowStart = leftBound;
                i4 = i3;
            }
            int i5 = NotificationIconContainerRefactor.$r8$clinit;
            if (this.mOnKeyguardStatusBar && (childAt instanceof StatusBarIconView)) {
                iconScaleIncreased = ((StatusBarIconView) childAt).getIconScaleIncreased();
            }
            leftBound += iconState2.iconAppearAmount * childAt.getWidth() * iconScaleIncreased;
            i3++;
        }
        this.mIsShowingOverflowDot = false;
        if (i4 != -1) {
            float f = this.mVisualOverflowStart;
            while (i4 < childCount) {
                View childAt2 = getChildAt(i4);
                IconState iconState3 = (IconState) this.mIconStates.get(childAt2);
                int i6 = this.mStaticDotDiameter + this.mDotPadding;
                iconState3.setXTranslation(f);
                if (this.mIsShowingOverflowDot) {
                    iconState3.visibleState = 2;
                    childAt2.setImportantForAccessibility(2);
                } else {
                    float f2 = iconState3.iconAppearAmount;
                    if (f2 < 0.8f) {
                        iconState3.visibleState = 0;
                    } else {
                        iconState3.visibleState = 1;
                        this.mIsShowingOverflowDot = true;
                    }
                    childAt2.setImportantForAccessibility(1);
                    f = (i6 * f2) + f;
                }
                i4++;
            }
        } else if (childCount > 0) {
            this.mFirstVisibleIconState = (IconState) this.mIconStates.get(getChildAt(0));
        }
        if (isLayoutRtl()) {
            for (int i7 = 0; i7 < childCount; i7++) {
                IconState iconState4 = (IconState) this.mIconStates.get(getChildAt(i7));
                iconState4.setXTranslation((getWidth() - iconState4.mXTranslation) - r1.getWidth());
            }
        }
        int i8 = StatusBarNoHunBehavior.$r8$clinit;
        StatusBarIconView statusBarIconView = this.mIsolatedIcon;
        if (statusBarIconView == null || (iconState = (IconState) this.mIconStates.get(statusBarIconView)) == null) {
            return;
        }
        iconState.visibleState = 2;
    }

    public final float getActualPaddingStart() {
        float f = this.mActualPaddingStart;
        return f == -2.1474836E9f ? getPaddingStart() : f;
    }

    public float getLeftBound() {
        return getActualPaddingStart();
    }

    public float getRightBound() {
        int width = this.mActualLayoutWidth;
        if (width == Integer.MIN_VALUE) {
            width = getWidth();
        }
        float f = width;
        float paddingEnd = this.mActualPaddingEnd;
        if (paddingEnd == -2.1474836E9f) {
            paddingEnd = getPaddingEnd();
        }
        return f - paddingEnd;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public void initResources() throws Resources.NotFoundException {
        getResources().getInteger(R.integer.max_notif_icons_on_aod);
        this.mMaxIconsOnLockscreen = getResources().getInteger(R.integer.max_notif_icons_on_lockscreen);
        this.mMaxStaticIcons = getResources().getInteger(R.integer.max_notif_static_icons);
        this.mDotPadding = getResources().getDimensionPixelSize(R.dimen.overflow_icon_dot_padding);
        this.mStaticDotDiameter = getResources().getDimensionPixelSize(R.dimen.overflow_dot_radius) * 2;
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getContext(), android.R.style.Theme.DeviceDefault.DayNight);
        this.mThemedTextColorPrimary = Utils.getColorAttr(android.R.attr.textColorPrimary, contextThemeWrapper).getDefaultColor();
        Utils.getColorAttr(android.R.attr.textColorPrimaryInverse, contextThemeWrapper).getDefaultColor();
        this.mShelfIconColor = ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).getTextColor(0, false, true);
    }

    public boolean isOverflowing(boolean z, float f, float f2, float f3) {
        return z ? f + f3 > f2 : (f3 * 2.0f) + f > f2;
    }

    public final boolean isReplacingIcon(View view) {
        ArrayList arrayList;
        if (view instanceof StatusBarIconView) {
            StatusBarIconView statusBarIconView = (StatusBarIconView) view;
            Icon icon = statusBarIconView.mIcon.icon;
            String groupKey = statusBarIconView.mNotification.getGroupKey();
            int i = NotificationIconContainerRefactor.$r8$clinit;
            ArrayMap arrayMap = this.mReplacingIconsLegacy;
            if (arrayMap != null && (arrayList = (ArrayList) arrayMap.get(groupKey)) != null) {
                return icon.sameAs(((StatusBarIcon) arrayList.get(0)).icon);
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setRequestedFrameRate(-2.0f);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        initResources();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(-65536);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(getActualPaddingStart(), 0.0f, getRightBound(), getHeight(), paint);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        float height = getHeight() / 2.0f;
        this.mIconSize = 0;
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            View childAt = getChildAt(i5);
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            int i6 = (int) (height - (measuredHeight / 2.0f));
            childAt.layout(0, i6, measuredWidth, measuredHeight + i6);
            if (i5 == 0) {
                setIconSize(childAt.getWidth());
            }
        }
        getLocationOnScreen(this.mAbsolutePosition);
        if (this.mIsStaticLayout || this.mOnKeyguardStatusBar) {
            resetViewStates();
            calculateIconXTranslations();
            applyIconStates();
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        int i3 = NotificationIconContainerRefactor.$r8$clinit;
        int i4 = this.mOnKeyguardStatusBar ? 0 : this.mIsStaticLayout ? this.mMaxStaticIcons : childCount;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), 0);
        float actualPaddingStart = getActualPaddingStart();
        float paddingEnd = this.mActualPaddingEnd;
        if (paddingEnd == -2.1474836E9f) {
            paddingEnd = getPaddingEnd();
        }
        int measuredWidth = (int) (paddingEnd + actualPaddingStart);
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            measureChild(childAt, iMakeMeasureSpec, i2);
            if (i5 <= i4) {
                measuredWidth = childAt.getMeasuredWidth() + measuredWidth;
            }
        }
        setMeasuredDimension(ViewGroup.resolveSize(measuredWidth, i), View.MeasureSpec.getSize(i2));
    }

    @Override // android.view.ViewGroup
    public final void onViewAdded(View view) {
        AnimationDrawable animationDrawable;
        super.onViewAdded(view);
        boolean zIsReplacingIcon = isReplacingIcon(view);
        if (!this.mChangingViewPositions) {
            IconState iconState = new IconState(view);
            if (zIsReplacingIcon) {
                iconState.justAdded = false;
                iconState.justReplaced = true;
            }
            this.mIconStates.put(view, iconState);
        }
        int iIndexOfChild = indexOfChild(view);
        if (iIndexOfChild < getChildCount() - 1 && !zIsReplacingIcon && ((IconState) this.mIconStates.get(getChildAt(iIndexOfChild + 1))).iconAppearAmount > 0.0f) {
            int i = this.mAddAnimationStartIndex;
            if (i < 0) {
                this.mAddAnimationStartIndex = iIndexOfChild;
            } else {
                this.mAddAnimationStartIndex = Math.min(i, iIndexOfChild);
            }
        }
        if (view instanceof StatusBarIconView) {
            int i2 = NotificationIconContainerRefactor.$r8$clinit;
            StatusBarIconView statusBarIconView = (StatusBarIconView) view;
            statusBarIconView.updateIconDimens();
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            statusBarIconView.mDozer.getClass();
            Animator animator = (Animator) statusBarIconView.getTag(R.id.doze_intensity_tag);
            if (animator != null) {
                animator.cancel();
            }
            statusBarIconView.mDozeAmount = 0.0f;
            statusBarIconView.updateDecorColor();
            statusBarIconView.updateIconColor();
            float f = statusBarIconView.mDozeAmount;
            if (f == 0.0f || f == 1.0f) {
                boolean z = f == 0.0f;
                if (statusBarIconView.mAllowAnimation != z) {
                    statusBarIconView.mAllowAnimation = z;
                    statusBarIconView.updateAnim();
                    if (statusBarIconView.mAllowAnimation || (animationDrawable = statusBarIconView.mAnim) == null) {
                        return;
                    }
                    animationDrawable.setVisible(statusBarIconView.getVisibility() == 0, true);
                }
            }
        }
    }

    @Override // android.view.ViewGroup
    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        if (view instanceof StatusBarIconView) {
            boolean zIsReplacingIcon = isReplacingIcon(view);
            StatusBarIconView statusBarIconView = (StatusBarIconView) view;
            if ((this.mAnimationsEnabled || statusBarIconView == this.mIsolatedIcon) && statusBarIconView.mVisibleState != 2 && view.getVisibility() == 0 && zIsReplacingIcon) {
                float translationX = statusBarIconView.getTranslationX();
                int childCount = 0;
                while (true) {
                    if (childCount >= getChildCount()) {
                        childCount = getChildCount();
                        break;
                    } else if (getChildAt(childCount).getTranslationX() > translationX) {
                        break;
                    } else {
                        childCount++;
                    }
                }
                int i = this.mAddAnimationStartIndex;
                if (i < 0) {
                    this.mAddAnimationStartIndex = childCount;
                } else {
                    this.mAddAnimationStartIndex = Math.min(i, childCount);
                }
            }
            if (this.mChangingViewPositions) {
                return;
            }
            this.mIconStates.remove(view);
            if ((this.mAnimationsEnabled || statusBarIconView == this.mIsolatedIcon) && !zIsReplacingIcon) {
                addTransientView(statusBarIconView, 0);
                boolean z = view == this.mIsolatedIcon;
                int i2 = StatusBarNoHunBehavior.$r8$clinit;
                statusBarIconView.setVisibleState(2, true, new NotificationIconContainer$$ExternalSyntheticLambda0(this, statusBarIconView), z ? 110L : 0L);
            }
        }
    }

    public final void resetViewStates() throws Resources.NotFoundException {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            ViewState viewState = (ViewState) this.mIconStates.get(childAt);
            viewState.initFrom(childAt);
            StatusBarIconView statusBarIconView = this.mIsolatedIcon;
            viewState.setAlpha((statusBarIconView == null || childAt == statusBarIconView) ? 1.0f : 0.0f);
            viewState.hidden = false;
            if (childAt instanceof StatusBarIconView) {
                StatusBarIconView statusBarIconView2 = (StatusBarIconView) childAt;
                statusBarIconView2.reloadDimens$1();
                statusBarIconView2.maybeUpdateIconScaleDimens();
            }
        }
    }

    public final void setAnimationsEnabled(boolean z) {
        if (!z && this.mAnimationsEnabled) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                ViewState viewState = (ViewState) this.mIconStates.get(childAt);
                if (viewState != null) {
                    viewState.cancelAnimations(childAt);
                    viewState.applyToView(childAt);
                }
            }
        }
        this.mAnimationsEnabled = z;
    }

    public void setIconSize(int i) {
        this.mIconSize = i;
    }

    public boolean shouldForceOverflow(int i, int i2, float f, int i3) {
        int i4 = NotificationIconContainerRefactor.$r8$clinit;
        return (i2 != -1 && i >= i2 && f > 0.0f) || i >= i3;
    }

    @Override // android.view.View
    public final String toString() {
        int i = NotificationIconContainerRefactor.$r8$clinit;
        return "NotificationIconContainer(dozing=false onLockScreen=false overrideIconColor=" + this.mOverrideIconColor + " speedBumpIndex=" + this.mSpeedBumpIndex + " themedTextColorPrimary=#" + Integer.toHexString(this.mThemedTextColorPrimary) + ')';
    }
}
