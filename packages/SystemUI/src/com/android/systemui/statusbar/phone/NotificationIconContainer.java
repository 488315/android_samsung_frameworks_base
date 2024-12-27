package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Configuration;
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
import com.android.internal.statusbar.StatusBarIcon;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.StatusBarIconView;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public Runnable mIsolatedIconAnimationEndRunnable;
    public StatusBarIconView mIsolatedIconForAnimation;
    public int mMaxIconsOnLockscreen;
    public int mMaxStaticIcons;
    public boolean mOnKeyguardStatusBar;
    public boolean mOverrideIconColor;
    public ArrayMap mReplacingIconsLegacy;
    public int mShelfIconColor;
    public int mSpeedBumpIndex;
    public int mStaticDotDiameter;
    public int mThemedTextColorPrimary;
    public float mVisualOverflowStart;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.phone.NotificationIconContainer$2, reason: invalid class name */
    public final class AnonymousClass2 extends AnimationProperties {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.phone.NotificationIconContainer$3, reason: invalid class name */
    public final class AnonymousClass3 extends AnimationProperties {
        public final AnimationFilter mAnimationFilter = new AnimationFilter();

        @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
        public final AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.phone.NotificationIconContainer$7, reason: invalid class name */
    public final class AnonymousClass7 implements Runnable {
        public int mPendingCallbacks;
        public final /* synthetic */ Runnable val$endRunnable;

        public AnonymousClass7(NotificationIconContainer notificationIconContainer, int i, Runnable runnable) {
            this.val$endRunnable = runnable;
            this.mPendingCallbacks = i;
        }

        @Override // java.lang.Runnable
        public final void run() {
            int i = this.mPendingCallbacks - 1;
            this.mPendingCallbacks = i;
            if (i == 0) {
                this.val$endRunnable.run();
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                if (property == View.TRANSLATION_Y && iconState.iconAppearAmount == 0.0f && iconState.mView.getVisibility() == 0) {
                    iconState.mView.setVisibility(4);
                }
            }
        };

        /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.phone.NotificationIconContainer$IconState$$ExternalSyntheticLambda0] */
        public IconState(View view) {
            this.mView = view;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:101:0x0137  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x006a  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x0087  */
        /* JADX WARN: Removed duplicated region for block: B:51:0x00e2  */
        /* JADX WARN: Removed duplicated region for block: B:62:0x0108  */
        /* JADX WARN: Type inference failed for: r14v0, types: [com.android.systemui.statusbar.notification.stack.ViewState, com.android.systemui.statusbar.phone.NotificationIconContainer$IconState] */
        /* JADX WARN: Type inference failed for: r1v19 */
        /* JADX WARN: Type inference failed for: r1v20 */
        /* JADX WARN: Type inference failed for: r1v27, types: [com.android.systemui.statusbar.notification.stack.AnimationProperties, com.android.systemui.statusbar.phone.NotificationIconContainer$3] */
        /* JADX WARN: Type inference failed for: r1v29 */
        /* JADX WARN: Type inference failed for: r1v35 */
        @Override // com.android.systemui.statusbar.notification.stack.ViewState
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void applyToView(android.view.View r15) {
            /*
                Method dump skipped, instructions count: 398
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationIconContainer.IconState.applyToView(android.view.View):void");
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

    public NotificationIconContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSpeedBumpIndex = -1;
        this.mIsStaticLayout = true;
        this.mIconStates = new HashMap();
        this.mActualLayoutWidth = Integer.MIN_VALUE;
        this.mActualPaddingEnd = -2.14748365E9f;
        this.mActualPaddingStart = -2.14748365E9f;
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

    public void calculateIconXTranslations() {
        IconState iconState;
        boolean z;
        float f = this.mActualPaddingStart;
        if (f == -2.14748365E9f) {
            f = getPaddingStart();
        }
        int childCount = getChildCount();
        int i = NotificationIconContainerRefactor.$r8$clinit;
        boolean z2 = this.mOnKeyguardStatusBar;
        int i2 = z2 ? 0 : this.mIsStaticLayout ? this.mMaxStaticIcons : childCount;
        if (!z2) {
            i2 = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getStatusBarNotificationStyle() == 0 ? 0 : 3;
        }
        int i3 = this.mActualLayoutWidth;
        if (i3 == Integer.MIN_VALUE) {
            i3 = getWidth();
        }
        float f2 = i3;
        float f3 = this.mActualPaddingEnd;
        if (f3 == -2.14748365E9f) {
            f3 = getPaddingEnd();
        }
        float f4 = f2 - f3;
        this.mVisualOverflowStart = 0.0f;
        this.mFirstVisibleIconState = null;
        int i4 = -1;
        int i5 = 0;
        while (true) {
            if (i5 >= childCount) {
                break;
            }
            View childAt = getChildAt(i5);
            IconState iconState2 = (IconState) this.mIconStates.get(childAt);
            float f5 = 1.0f;
            if (iconState2.iconAppearAmount == 1.0f) {
                iconState2.setXTranslation(f);
            }
            if (this.mFirstVisibleIconState == null) {
                this.mFirstVisibleIconState = iconState2;
            }
            iconState2.visibleState = iconState2.hidden ? 2 : 0;
            if (!shouldForceOverflow(i5, this.mSpeedBumpIndex, iconState2.iconAppearAmount, i2)) {
                if (!isOverflowing(i5 == childCount + (-1), f, f4, this.mIconSize)) {
                    z = false;
                    if (i4 == -1 && z) {
                        this.mVisualOverflowStart = f;
                        i4 = i5;
                    }
                    int i6 = NotificationIconContainerRefactor.$r8$clinit;
                    if (this.mOnKeyguardStatusBar && (childAt instanceof StatusBarIconView)) {
                        f5 = ((StatusBarIconView) childAt).getIconScaleIncreased();
                    }
                    f += iconState2.iconAppearAmount * childAt.getWidth() * f5;
                    i5++;
                }
            }
            z = true;
            if (i4 == -1) {
                this.mVisualOverflowStart = f;
                i4 = i5;
            }
            int i62 = NotificationIconContainerRefactor.$r8$clinit;
            if (this.mOnKeyguardStatusBar) {
                f5 = ((StatusBarIconView) childAt).getIconScaleIncreased();
            }
            f += iconState2.iconAppearAmount * childAt.getWidth() * f5;
            i5++;
        }
        this.mIsShowingOverflowDot = false;
        if (i4 != -1) {
            float f6 = this.mVisualOverflowStart;
            while (i4 < childCount) {
                View childAt2 = getChildAt(i4);
                IconState iconState3 = (IconState) this.mIconStates.get(childAt2);
                int i7 = this.mStaticDotDiameter + this.mDotPadding;
                iconState3.setXTranslation(f6);
                if (this.mIsShowingOverflowDot) {
                    iconState3.visibleState = 2;
                    childAt2.setImportantForAccessibility(2);
                } else {
                    float f7 = iconState3.iconAppearAmount;
                    if (f7 < 0.8f) {
                        iconState3.visibleState = 0;
                    } else {
                        iconState3.visibleState = 1;
                        this.mIsShowingOverflowDot = true;
                    }
                    childAt2.setImportantForAccessibility(1);
                    f6 = (i7 * f7) + f6;
                }
                i4++;
            }
        } else if (childCount > 0) {
            this.mFirstVisibleIconState = (IconState) this.mIconStates.get(getChildAt(0));
        }
        if (isLayoutRtl()) {
            for (int i8 = 0; i8 < childCount; i8++) {
                IconState iconState4 = (IconState) this.mIconStates.get(getChildAt(i8));
                iconState4.setXTranslation((getWidth() - iconState4.mXTranslation) - r1.getWidth());
            }
        }
        StatusBarIconView statusBarIconView = this.mIsolatedIcon;
        if (statusBarIconView == null || (iconState = (IconState) this.mIconStates.get(statusBarIconView)) == null) {
            return;
        }
        iconState.visibleState = 2;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public void initResources() {
        getResources().getInteger(R.integer.max_notif_icons_on_aod);
        this.mMaxIconsOnLockscreen = getResources().getInteger(R.integer.max_notif_icons_on_lockscreen);
        this.mMaxStaticIcons = getResources().getInteger(R.integer.max_notif_static_icons);
        this.mDotPadding = getResources().getDimensionPixelSize(R.dimen.overflow_icon_dot_padding);
        this.mStaticDotDiameter = getResources().getDimensionPixelSize(R.dimen.overflow_dot_radius) * 2;
        this.mThemedTextColorPrimary = Utils.getColorAttr(android.R.attr.textColorPrimary, new ContextThemeWrapper(getContext(), android.R.style.Theme.DeviceDefault.DayNight)).getDefaultColor();
        this.mShelfIconColor = ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).getTextColor(0, false, true);
    }

    public boolean isOverflowing(boolean z, float f, float f2, float f3) {
        return z ? f + f3 > f2 : (f3 * 2.0f) + f > f2;
    }

    public final boolean isReplacingIcon(View view) {
        ArrayList arrayList;
        if (!(view instanceof StatusBarIconView)) {
            return false;
        }
        StatusBarIconView statusBarIconView = (StatusBarIconView) view;
        Icon icon = statusBarIconView.mIcon.icon;
        String groupKey = statusBarIconView.mNotification.getGroupKey();
        int i = NotificationIconContainerRefactor.$r8$clinit;
        ArrayMap arrayMap = this.mReplacingIconsLegacy;
        if (arrayMap == null || (arrayList = (ArrayList) arrayMap.get(groupKey)) == null) {
            return false;
        }
        return icon.sameAs(((StatusBarIcon) arrayList.get(0)).icon);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setRequestedFrameRate(-2.0f);
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
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), 0);
        float f = this.mActualPaddingStart;
        if (f == -2.14748365E9f) {
            f = getPaddingStart();
        }
        float f2 = this.mActualPaddingEnd;
        if (f2 == -2.14748365E9f) {
            f2 = getPaddingEnd();
        }
        int i5 = (int) (f2 + f);
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            measureChild(childAt, makeMeasureSpec, i2);
            if (i6 <= i4) {
                i5 = childAt.getMeasuredWidth() + i5;
            }
        }
        setMeasuredDimension(ViewGroup.resolveSize(i5, i), View.MeasureSpec.getSize(i2));
    }

    @Override // android.view.ViewGroup
    public final void onViewAdded(View view) {
        AnimationDrawable animationDrawable;
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
            int i2 = NotificationIconContainerRefactor.$r8$clinit;
            StatusBarIconView statusBarIconView = (StatusBarIconView) view;
            statusBarIconView.updateIconDimens();
            statusBarIconView.getClass();
            int i3 = NotificationIconContainerRefactor.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            statusBarIconView.mDozer.getClass();
            Animator animator = (Animator) statusBarIconView.getTag(R.id.doze_intensity_tag);
            if (animator != null) {
                animator.cancel();
            }
            statusBarIconView.mDozeAmount = Float.valueOf(0.0f).floatValue();
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
            if ((this.mAnimationsEnabled || statusBarIconView == this.mIsolatedIcon) && !isReplacingIcon) {
                addTransientView(statusBarIconView, 0);
                statusBarIconView.setVisibleState(2, true, new NotificationIconContainer$$ExternalSyntheticLambda0(this, statusBarIconView), view == this.mIsolatedIcon ? 110L : 0L);
            }
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
                if (!this.mIsStaticLayout) {
                    return;
                }
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
