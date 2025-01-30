package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Icon;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import com.android.app.animation.Interpolators;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.util.ContrastColorUtil;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.stack.AnimationFilter;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ViewState;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NotificationIconContainer extends ViewGroup {
    public static final C30814 ADD_ICON_PROPERTIES;
    public static final C30781 DOT_ANIMATION_PROPERTIES;
    public static final C30792 ICON_ANIMATION_PROPERTIES;
    public static final C30836 UNISOLATION_PROPERTY;
    public static final C30825 UNISOLATION_PROPERTY_OTHERS;
    public static final C30803 sTempProperties;
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
    public boolean mDozing;
    public IconState mFirstVisibleIconState;
    public int mIconSize;
    public final HashMap mIconStates;
    public boolean mInNotificationIconShelf;
    public boolean mIsShowingOverflowDot;
    public boolean mIsStaticLayout;
    public StatusBarIconView mIsolatedIcon;
    public StatusBarIconView mIsolatedIconForAnimation;
    public int mMaxIconsOnAod;
    public int mMaxIconsOnLockscreen;
    public int mMaxStaticIcons;
    public boolean mOnLockScreen;
    public ArrayMap mReplacingIcons;
    public int mShelfIconColor;
    public int mSpeedBumpIndex;
    public int mStaticDotDiameter;
    public int mThemedTextColorPrimary;
    public float mVisualOverflowStart;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.NotificationIconContainer$2 */
    public final class C30792 extends AnimationProperties {
        public final AnimationFilter mAnimationFilter;

        public C30792() {
            AnimationFilter animationFilter = new AnimationFilter();
            animationFilter.animateX = true;
            animationFilter.animateY = true;
            animationFilter.animateAlpha = true;
            Property property = View.SCALE_X;
            ArraySet arraySet = animationFilter.mAnimatedProperties;
            arraySet.add(property);
            arraySet.add(View.SCALE_Y);
            this.mAnimationFilter = animationFilter;
        }

        @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
        public final AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.NotificationIconContainer$3 */
    public final class C30803 extends AnimationProperties {
        public final AnimationFilter mAnimationFilter = new AnimationFilter();

        @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
        public final AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IconState extends ViewState {
        public boolean justReplaced;
        public final View mView;
        public boolean needsCannedAnimation;
        public boolean noAnimations;
        public int visibleState;
        public float iconAppearAmount = 1.0f;
        public float clampedAppearAmount = 1.0f;
        public boolean justAdded = true;
        public int iconColor = 0;
        public final NotificationIconContainer$IconState$$ExternalSyntheticLambda0 mCannedAnimationEndListener = new Consumer() { // from class: com.android.systemui.statusbar.phone.NotificationIconContainer$IconState$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationIconContainer.IconState iconState = NotificationIconContainer.IconState.this;
                Property property = (Property) obj;
                iconState.getClass();
                if (property == View.TRANSLATION_Y && iconState.iconAppearAmount == 0.0f) {
                    View view = iconState.mView;
                    if (view.getVisibility() == 0) {
                        view.setVisibility(4);
                    }
                }
            }
        };

        /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.phone.NotificationIconContainer$IconState$$ExternalSyntheticLambda0] */
        public IconState(View view) {
            this.mView = view;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r14v0, types: [com.android.systemui.statusbar.notification.stack.ViewState, com.android.systemui.statusbar.phone.NotificationIconContainer$IconState] */
        /* JADX WARN: Type inference failed for: r2v19 */
        /* JADX WARN: Type inference failed for: r2v20 */
        /* JADX WARN: Type inference failed for: r2v23, types: [com.android.systemui.statusbar.notification.stack.AnimationProperties, com.android.systemui.statusbar.phone.NotificationIconContainer$3] */
        /* JADX WARN: Type inference failed for: r2v25 */
        /* JADX WARN: Type inference failed for: r2v33 */
        @Override // com.android.systemui.statusbar.notification.stack.ViewState
        public final void applyToView(View view) {
            boolean z;
            AnimationProperties animationProperties;
            boolean z2;
            AnimationProperties animationProperties2;
            AnimationProperties animationProperties3;
            AnimationProperties animationProperties4;
            if (view instanceof StatusBarIconView) {
                StatusBarIconView statusBarIconView = (StatusBarIconView) view;
                int i = this.visibleState;
                boolean z3 = (i == 2 && statusBarIconView.mVisibleState == 1) || (i == 1 && statusBarIconView.mVisibleState == 2);
                C30781 c30781 = NotificationIconContainer.DOT_ANIMATION_PROPERTIES;
                NotificationIconContainer notificationIconContainer = NotificationIconContainer.this;
                boolean z4 = (!(notificationIconContainer.mAnimationsEnabled || statusBarIconView == notificationIconContainer.mIsolatedIcon) || notificationIconContainer.mDisallowNextAnimation || this.noAnimations || z3) ? false : true;
                if (z4) {
                    if (this.justAdded || this.justReplaced) {
                        super.applyToView(statusBarIconView);
                        if (this.justAdded && this.iconAppearAmount != 0.0f) {
                            statusBarIconView.setAlpha(0.0f);
                            statusBarIconView.setVisibleState(2, false, null, 0L);
                            animationProperties4 = NotificationIconContainer.ADD_ICON_PROPERTIES;
                            z2 = true;
                            animationProperties2 = animationProperties4;
                        }
                        z2 = false;
                        animationProperties2 = null;
                    } else {
                        if (i != statusBarIconView.mVisibleState) {
                            animationProperties4 = NotificationIconContainer.DOT_ANIMATION_PROPERTIES;
                            z2 = true;
                            animationProperties2 = animationProperties4;
                        }
                        z2 = false;
                        animationProperties2 = null;
                    }
                    if (!z2 && notificationIconContainer.mAddAnimationStartIndex >= 0 && notificationIconContainer.indexOfChild(view) >= notificationIconContainer.mAddAnimationStartIndex && (statusBarIconView.mVisibleState != 2 || this.visibleState != 2)) {
                        animationProperties2 = NotificationIconContainer.DOT_ANIMATION_PROPERTIES;
                        z2 = true;
                    }
                    ?? r2 = animationProperties2;
                    if (this.needsCannedAnimation) {
                        C30803 c30803 = NotificationIconContainer.sTempProperties;
                        AnimationFilter animationFilter = c30803.mAnimationFilter;
                        animationFilter.reset();
                        C30792 c30792 = NotificationIconContainer.ICON_ANIMATION_PROPERTIES;
                        animationFilter.combineFilter(c30792.mAnimationFilter);
                        c30803.mInterpolatorMap = null;
                        android.util.ArrayMap arrayMap = c30792.mInterpolatorMap;
                        if (arrayMap != null) {
                            c30803.mInterpolatorMap = new android.util.ArrayMap();
                            c30803.mInterpolatorMap.putAll(arrayMap);
                        }
                        c30803.setCustomInterpolator(View.TRANSLATION_Y, statusBarIconView.mShowsConversation ? Interpolators.ICON_OVERSHOT_LESS : Interpolators.ICON_OVERSHOT);
                        c30803.mAnimationEndAction = this.mCannedAnimationEndListener;
                        if (animationProperties2 != null) {
                            animationFilter.combineFilter(animationProperties2.getAnimationFilter());
                            android.util.ArrayMap arrayMap2 = animationProperties2.mInterpolatorMap;
                            if (arrayMap2 != null) {
                                if (c30803.mInterpolatorMap == null) {
                                    c30803.mInterpolatorMap = new android.util.ArrayMap();
                                }
                                c30803.mInterpolatorMap.putAll(arrayMap2);
                            }
                        }
                        c30803.duration = 100L;
                        notificationIconContainer.mCannedAnimationStartIndex = notificationIconContainer.indexOfChild(view);
                        r2 = c30803;
                        z2 = true;
                    }
                    if (!z2 && notificationIconContainer.mCannedAnimationStartIndex >= 0 && notificationIconContainer.indexOfChild(view) > notificationIconContainer.mCannedAnimationStartIndex && (statusBarIconView.mVisibleState != 2 || this.visibleState != 2)) {
                        r2 = NotificationIconContainer.sTempProperties;
                        AnimationFilter animationFilter2 = r2.mAnimationFilter;
                        animationFilter2.reset();
                        animationFilter2.animateX = true;
                        r2.mInterpolatorMap = null;
                        r2.duration = 100L;
                        z2 = true;
                    }
                    StatusBarIconView statusBarIconView2 = notificationIconContainer.mIsolatedIconForAnimation;
                    if (statusBarIconView2 != null) {
                        if (view == statusBarIconView2) {
                            animationProperties3 = NotificationIconContainer.UNISOLATION_PROPERTY;
                            animationProperties3.delay = notificationIconContainer.mIsolatedIcon == null ? 0L : 100L;
                        } else {
                            animationProperties3 = NotificationIconContainer.UNISOLATION_PROPERTY_OTHERS;
                            animationProperties3.delay = notificationIconContainer.mIsolatedIcon != null ? 0L : 100L;
                        }
                        animationProperties = animationProperties3;
                        z = true;
                    } else {
                        animationProperties = r2;
                        z = z2;
                    }
                } else {
                    z = false;
                    animationProperties = null;
                }
                statusBarIconView.setVisibleState(this.visibleState, z4, null, 0L);
                if (!notificationIconContainer.mInNotificationIconShelf) {
                    statusBarIconView.setIconColor(notificationIconContainer.mIsStaticLayout ? statusBarIconView.mDrawableColor : this.iconColor, this.needsCannedAnimation && z4);
                } else if (NotificationUtils.isGrayscale(statusBarIconView, ContrastColorUtil.getInstance(notificationIconContainer.getContext()))) {
                    statusBarIconView.setIconColor(notificationIconContainer.mShelfIconColor, this.needsCannedAnimation && z4);
                }
                if (z) {
                    animateTo(statusBarIconView, animationProperties);
                } else {
                    super.applyToView(view);
                }
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
        C30792 c30792 = new C30792();
        c30792.duration = 100L;
        ICON_ANIMATION_PROPERTIES = c30792;
        sTempProperties = new C30803();
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

    public NotificationIconContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsStaticLayout = true;
        this.mIconStates = new HashMap();
        this.mActualLayoutWidth = VideoPlayer.MEDIA_ERROR_SYSTEM;
        this.mActualPaddingEnd = -2.1474836E9f;
        this.mActualPaddingStart = -2.1474836E9f;
        this.mAddAnimationStartIndex = -1;
        this.mCannedAnimationStartIndex = -1;
        this.mSpeedBumpIndex = -1;
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

    /* JADX WARN: Removed duplicated region for block: B:34:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00a8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void calculateIconXTranslations() {
        IconState iconState;
        boolean z;
        boolean z2;
        float f;
        float actualPaddingStart = getActualPaddingStart();
        int childCount = getChildCount();
        SimpleStatusBarIconController simpleStatusBarIconController = (SimpleStatusBarIconController) Dependency.get(SimpleStatusBarIconController.class);
        int childCount2 = (simpleStatusBarIconController.mSettingsValue == 1 && this == simpleStatusBarIconController.mNotificationIconContainer) ? simpleStatusBarIconController.mSimpleStatusBarMaxNotificationNum : getChildCount();
        int i = this.mActualLayoutWidth;
        if (i == Integer.MIN_VALUE) {
            i = getWidth();
        }
        float f2 = i;
        float f3 = this.mActualPaddingEnd;
        if (f3 == -2.1474836E9f) {
            f3 = getPaddingEnd();
        }
        float f4 = f2 - f3;
        this.mVisualOverflowStart = 0.0f;
        this.mFirstVisibleIconState = null;
        int i2 = -1;
        int i3 = 0;
        while (true) {
            if (i3 >= childCount) {
                break;
            }
            View childAt = getChildAt(i3);
            IconState iconState2 = (IconState) this.mIconStates.get(childAt);
            float f5 = 1.0f;
            if (iconState2.iconAppearAmount == 1.0f) {
                iconState2.setXTranslation(actualPaddingStart);
            }
            if (this.mFirstVisibleIconState == null) {
                this.mFirstVisibleIconState = iconState2;
            }
            iconState2.visibleState = iconState2.hidden ? 2 : 0;
            boolean shouldForceOverflow = shouldForceOverflow(i3, this.mSpeedBumpIndex, iconState2.iconAppearAmount, childCount2);
            if (!shouldForceOverflow) {
                if (!isOverflowing(i3 == childCount + (-1), actualPaddingStart, f4, this.mIconSize)) {
                    z = false;
                    z2 = this.mIsStaticLayout;
                    if (z2) {
                        float f6 = this.mIconSize;
                        z = f6 + actualPaddingStart > f4 - f6 || shouldForceOverflow;
                    }
                    if (i2 == -1 && z) {
                        f = z2 ? actualPaddingStart : f4 - this.mIconSize;
                        this.mVisualOverflowStart = f;
                        if (!shouldForceOverflow || z2) {
                            this.mVisualOverflowStart = Math.min(actualPaddingStart, f);
                        }
                        i2 = i3;
                    }
                    if (this.mOnLockScreen && (childAt instanceof StatusBarIconView)) {
                        StatusBarIconView statusBarIconView = (StatusBarIconView) childAt;
                        f5 = statusBarIconView.mStatusBarIconDrawingSizeIncreased / statusBarIconView.mStatusBarIconDrawingSize;
                    }
                    actualPaddingStart += iconState2.iconAppearAmount * childAt.getWidth() * f5;
                    i3++;
                }
            }
            z = true;
            z2 = this.mIsStaticLayout;
            if (z2) {
            }
            if (i2 == -1) {
                if (z2) {
                }
                this.mVisualOverflowStart = f;
                if (!shouldForceOverflow) {
                }
                this.mVisualOverflowStart = Math.min(actualPaddingStart, f);
                i2 = i3;
            }
            if (this.mOnLockScreen) {
                StatusBarIconView statusBarIconView2 = (StatusBarIconView) childAt;
                f5 = statusBarIconView2.mStatusBarIconDrawingSizeIncreased / statusBarIconView2.mStatusBarIconDrawingSize;
            }
            actualPaddingStart += iconState2.iconAppearAmount * childAt.getWidth() * f5;
            i3++;
        }
        this.mIsShowingOverflowDot = false;
        if (i2 != -1) {
            float f7 = this.mVisualOverflowStart;
            while (i2 < childCount) {
                View childAt2 = getChildAt(i2);
                IconState iconState3 = (IconState) this.mIconStates.get(childAt2);
                int i4 = this.mStaticDotDiameter + this.mDotPadding;
                iconState3.setXTranslation(f7);
                if (this.mIsShowingOverflowDot) {
                    iconState3.visibleState = 2;
                    childAt2.setImportantForAccessibility(2);
                } else {
                    float f8 = iconState3.iconAppearAmount;
                    if (f8 < 0.8f) {
                        iconState3.visibleState = 0;
                    } else {
                        iconState3.visibleState = 1;
                        this.mIsShowingOverflowDot = true;
                    }
                    childAt2.setImportantForAccessibility(1);
                    f7 = (i4 * f8) + f7;
                }
                i2++;
            }
        } else if (childCount > 0) {
            this.mFirstVisibleIconState = (IconState) this.mIconStates.get(getChildAt(0));
        }
        if (isLayoutRtl()) {
            for (int i5 = 0; i5 < childCount; i5++) {
                IconState iconState4 = (IconState) this.mIconStates.get(getChildAt(i5));
                iconState4.setXTranslation((getWidth() - iconState4.mXTranslation) - r1.getWidth());
            }
        }
        StatusBarIconView statusBarIconView3 = this.mIsolatedIcon;
        if (statusBarIconView3 == null || (iconState = (IconState) this.mIconStates.get(statusBarIconView3)) == null) {
            return;
        }
        iconState.visibleState = 2;
    }

    public final float getActualPaddingStart() {
        float f = this.mActualPaddingStart;
        return f == -2.1474836E9f ? getPaddingStart() : f;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public void initResources() {
        this.mMaxIconsOnAod = getResources().getInteger(R.integer.max_notif_icons_on_aod);
        this.mMaxIconsOnLockscreen = getResources().getInteger(R.integer.max_notif_icons_on_lockscreen);
        this.mMaxStaticIcons = getResources().getInteger(R.integer.max_notif_static_icons);
        this.mDotPadding = getResources().getDimensionPixelSize(R.dimen.overflow_icon_dot_padding);
        this.mStaticDotDiameter = getResources().getDimensionPixelSize(R.dimen.overflow_dot_radius) * 2;
        this.mThemedTextColorPrimary = Utils.getColorAttr(android.R.attr.textColorPrimary, new ContextThemeWrapper(getContext(), android.R.style.Theme.DeviceDefault.DayNight)).getDefaultColor();
    }

    public boolean isOverflowing(boolean z, float f, float f2, float f3) {
        if (!z) {
            f2 -= f3;
        }
        return f >= f2;
    }

    public final boolean isReplacingIcon(View view) {
        if (this.mReplacingIcons == null || !(view instanceof StatusBarIconView)) {
            return false;
        }
        StatusBarIconView statusBarIconView = (StatusBarIconView) view;
        Icon icon = statusBarIconView.mIcon.icon;
        ArrayList arrayList = (ArrayList) this.mReplacingIcons.get(statusBarIconView.mNotification.getGroupKey());
        return arrayList != null && icon.sameAs(((StatusBarIcon) arrayList.get(0)).icon);
    }

    public void onClockColorChanged(int i) {
        this.mShelfIconColor = i;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        initResources();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        new Paint();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
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
        if (this.mIsStaticLayout) {
            resetViewStates();
            calculateIconXTranslations();
            applyIconStates();
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        int i3 = this.mOnLockScreen ? this.mMaxIconsOnAod : this.mIsStaticLayout ? this.mMaxStaticIcons : childCount;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), 0);
        float actualPaddingStart = getActualPaddingStart();
        float f = this.mActualPaddingEnd;
        if (f == -2.1474836E9f) {
            f = getPaddingEnd();
        }
        int i4 = (int) (f + actualPaddingStart);
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            measureChild(childAt, makeMeasureSpec, i2);
            if (i5 <= i3) {
                i4 = childAt.getMeasuredWidth() + i4;
            }
        }
        setMeasuredDimension(ViewGroup.resolveSize(i4, i), View.MeasureSpec.getSize(i2));
    }

    @Override // android.view.ViewGroup
    public final void onViewAdded(View view) {
        super.onViewAdded(view);
        boolean isReplacingIcon = isReplacingIcon(view);
        if (!this.mChangingViewPositions) {
            IconState iconState = new IconState(view);
            if (isReplacingIcon) {
                iconState.justAdded = false;
                iconState.justReplaced = true;
            }
            this.mIconStates.put(view, iconState);
        }
        int indexOfChild = indexOfChild(view);
        if (indexOfChild < getChildCount() - 1 && !isReplacingIcon && ((IconState) this.mIconStates.get(getChildAt(indexOfChild + 1))).iconAppearAmount > 0.0f) {
            int i = this.mAddAnimationStartIndex;
            if (i < 0) {
                this.mAddAnimationStartIndex = indexOfChild;
            } else {
                this.mAddAnimationStartIndex = Math.min(i, indexOfChild);
            }
        }
        if (view instanceof StatusBarIconView) {
            ((StatusBarIconView) view).setDozing$1(this.mDozing, false);
        }
    }

    @Override // android.view.ViewGroup
    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        if (view instanceof StatusBarIconView) {
            boolean isReplacingIcon = isReplacingIcon(view);
            StatusBarIconView statusBarIconView = (StatusBarIconView) view;
            if ((this.mAnimationsEnabled || statusBarIconView == this.mIsolatedIcon) && statusBarIconView.mVisibleState != 2 && view.getVisibility() == 0 && isReplacingIcon) {
                float translationX = statusBarIconView.getTranslationX();
                int i = 0;
                while (true) {
                    if (i >= getChildCount()) {
                        i = getChildCount();
                        break;
                    } else if (getChildAt(i).getTranslationX() > translationX) {
                        break;
                    } else {
                        i++;
                    }
                }
                int i2 = this.mAddAnimationStartIndex;
                if (i2 < 0) {
                    this.mAddAnimationStartIndex = i;
                } else {
                    this.mAddAnimationStartIndex = Math.min(i2, i);
                }
            }
            if (this.mChangingViewPositions) {
                return;
            }
            this.mIconStates.remove(view);
            if (!(this.mAnimationsEnabled || statusBarIconView == this.mIsolatedIcon) || isReplacingIcon) {
                return;
            }
            addTransientView(statusBarIconView, 0);
            statusBarIconView.setVisibleState(2, true, new NotificationIconContainer$$ExternalSyntheticLambda0(this, statusBarIconView), view == this.mIsolatedIcon ? 110L : 0L);
        }
    }

    public final void resetViewStates() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            ViewState viewState = (ViewState) this.mIconStates.get(childAt);
            viewState.initFrom(childAt);
            StatusBarIconView statusBarIconView = this.mIsolatedIcon;
            viewState.setAlpha((statusBarIconView == null || childAt == statusBarIconView) ? 1.0f : 0.0f);
            viewState.hidden = false;
            if (childAt instanceof StatusBarIconView) {
                StatusBarIconView statusBarIconView2 = (StatusBarIconView) childAt;
                statusBarIconView2.reloadDimens();
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
        return (i2 != -1 && i >= i2 && f > 0.0f) || i >= i3;
    }

    @Override // android.view.View
    public final String toString() {
        return "NotificationIconContainer(dozing=" + this.mDozing + " onLockScreen=" + this.mOnLockScreen + " inNotificationIconShelf=" + this.mInNotificationIconShelf + " speedBumpIndex=" + this.mSpeedBumpIndex + " themedTextColorPrimary=#" + Integer.toHexString(this.mThemedTextColorPrimary) + ')';
    }
}
