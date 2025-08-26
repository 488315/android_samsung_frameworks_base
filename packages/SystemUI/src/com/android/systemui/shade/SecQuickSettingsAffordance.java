package com.android.systemui.shade;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.core.view.OneShotPreDrawListener;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor$broadcastReceiver$1$onReceive$1;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.AnimHelper;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class SecQuickSettingsAffordance implements ConfigurationController.ConfigurationListener {
    public static final AnimHelper.AnimProperty BOUNCING1_ANIM_ARROW_ALPHA_PROPERTY;
    public static final AnimHelper.AnimProperty BOUNCING2_ANIM_ARROW_ALPHA_PROPERTY;
    public static final AnimHelper.AnimProperty BOUNCING2_ANIM_ARROW_TRANSLATION_Y_PROPERTY;
    public static final AnimHelper.AnimProperty HIDE_ANIM_ARROW_ALPHA_PROPERTY;
    public static final AnimHelper.AnimProperty HIDE_ANIM_ARROW_TRANSLATION_Y_PROPERTY;
    public static final AnimHelper.AnimProperty HIDE_ANIM_GLOW_ALPHA_PROPERTY;
    public static final AnimHelper.AnimProperty HIDE_ANIM_GLOW_TRANSLATION_Y_PROPERTY;
    public static final AnimHelper.AnimationType[] INIT_PROPERTY_FIELDS;
    public static final AnimHelper.AnimProperty SHOW_ANIM_ARROW_ALPHA_PROPERTY;
    public static final AnimHelper.AnimProperty SHOW_ANIM_ARROW_TRANSLATION_Y_PROPERTY;
    public static final AnimHelper.AnimProperty SHOW_ANIM_GLOW_ALPHA_PROPERTY;
    public static final AnimHelper.AnimProperty SHOW_ANIM_GLOW_TRANSLATION_Y_PROPERTY;
    public ImageView arrowView;
    public AnimatorSet bouncing1AnimSet;
    public AnimatorSet bouncing2AnimSet;
    public int bouncingCount;
    public final Context context;
    public ImageView glowView;
    public final Handler handler;
    public AnimatorSet hideAnimSet;
    public boolean isAnimating;
    public boolean isAttachedView;
    public boolean isBouncingAnimating;
    public boolean isHideAnimating;
    public boolean isRemoveView;
    public boolean isReversed;
    public boolean isShowAnimating;
    public boolean isVisibleView;
    public int lastDensityDpi;
    public int lastLayoutDirection;
    public int lastOrientation;
    public View layout;
    public final WindowManager.LayoutParams layoutParams;
    private final SettingsHelper settingsHelper;
    private SettingsHelper.OnChangedCallback settingsListener;
    public AnimatorSet showAnimSet;
    public final WindowManager windowManager;
    public final ArrayList showAnimList = new ArrayList();
    public final ArrayList bouncing1AnimList = new ArrayList();
    public final ArrayList bouncing2AnimList = new ArrayList();
    public final ArrayList hideAnimList = new ArrayList();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class HideAnimatorListener extends AnimHelper.BaseAnimatorListener {
        public HideAnimatorListener(String str) {
            super("SecQuickSettingsAffordance", str, true);
        }

        @Override // com.android.systemui.util.AnimHelper.BaseAnimatorListener, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            if (getDebug()) {
                Log.d(getTag(), getLogPrefix() + " onAnimationEnd isRemoveView = " + SecQuickSettingsAffordance.this.isRemoveView);
            }
            SecQuickSettingsAffordance.this.isHideAnimating = false;
            if (isCanceled()) {
                return;
            }
            SecQuickSettingsAffordance secQuickSettingsAffordance = SecQuickSettingsAffordance.this;
            if (!secQuickSettingsAffordance.isRemoveView) {
                secQuickSettingsAffordance.initAnimProperties(true);
            } else {
                secQuickSettingsAffordance.reset$3();
                SecQuickSettingsAffordance.access$unregisterListener(SecQuickSettingsAffordance.this);
            }
        }

        @Override // com.android.systemui.util.AnimHelper.BaseAnimatorListener, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            SecQuickSettingsAffordance.this.isHideAnimating = true;
        }
    }

    public final class ShowAnimatorListener extends AnimHelper.BaseAnimatorListener {
        public ShowAnimatorListener(String str) {
            super("SecQuickSettingsAffordance", str, true);
        }

        @Override // com.android.systemui.util.AnimHelper.BaseAnimatorListener, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            AnimatorSet animatorSet;
            if (getDebug()) {
                String tag = getTag();
                String logPrefix = getLogPrefix();
                SecQuickSettingsAffordance secQuickSettingsAffordance = SecQuickSettingsAffordance.this;
                boolean z = secQuickSettingsAffordance.isAnimating;
                AnimatorSet animatorSet2 = secQuickSettingsAffordance.showAnimSet;
                Boolean boolValueOf = animatorSet2 != null ? Boolean.valueOf(animatorSet2.isRunning()) : null;
                Log.d(tag, logPrefix + " onAnimationCancel isAnimating = " + z + ", isRunning = " + boolValueOf + ", isAttachedView = " + SecQuickSettingsAffordance.this.isAttachedView);
            }
            setCanceled(true);
            SecQuickSettingsAffordance secQuickSettingsAffordance2 = SecQuickSettingsAffordance.this;
            if (secQuickSettingsAffordance2.isAnimating && (animatorSet = secQuickSettingsAffordance2.showAnimSet) != null && animatorSet.isRunning()) {
                SecQuickSettingsAffordance.this.startHideAnimSet();
                return;
            }
            SecQuickSettingsAffordance secQuickSettingsAffordance3 = SecQuickSettingsAffordance.this;
            if (secQuickSettingsAffordance3.isAttachedView) {
                secQuickSettingsAffordance3.initAnimProperties(true);
            } else {
                secQuickSettingsAffordance3.isAnimating = false;
            }
        }

        @Override // com.android.systemui.util.AnimHelper.BaseAnimatorListener, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            if (getDebug()) {
                Log.d(getTag(), getLogPrefix() + " onAnimationEnd isHideAnimating = " + SecQuickSettingsAffordance.this.isHideAnimating + ", isCanceled = " + isCanceled() + ", isVisibleView = " + SecQuickSettingsAffordance.this.isVisibleView);
            }
            SecQuickSettingsAffordance.this.isShowAnimating = false;
            if (isCanceled()) {
                return;
            }
            SecQuickSettingsAffordance secQuickSettingsAffordance = SecQuickSettingsAffordance.this;
            secQuickSettingsAffordance.isBouncingAnimating = true;
            AnimatorSet animatorSet = secQuickSettingsAffordance.bouncing1AnimSet;
            if (animatorSet != null) {
                animatorSet.setStartDelay(100L);
            }
            AnimatorSet animatorSet2 = SecQuickSettingsAffordance.this.bouncing1AnimSet;
            if (animatorSet2 != null) {
                animatorSet2.start();
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator, boolean z) {
            super.onAnimationStart(animator, z);
            if (getDebug()) {
                String tag = getTag();
                String logPrefix = getLogPrefix();
                SecQuickSettingsAffordance secQuickSettingsAffordance = SecQuickSettingsAffordance.this;
                Log.d(tag, logPrefix + " onAnimationStart isVisibleView = " + secQuickSettingsAffordance.isVisibleView + ", isAttachedView = " + secQuickSettingsAffordance.isAttachedView);
            }
            SecQuickSettingsAffordance secQuickSettingsAffordance2 = SecQuickSettingsAffordance.this;
            if (!secQuickSettingsAffordance2.isVisibleView) {
                secQuickSettingsAffordance2.isVisibleView = true;
                secQuickSettingsAffordance2.updateLayoutVisibility();
            }
            SecQuickSettingsAffordance.this.initAnimProperties(false);
            setCanceled(false);
            SecQuickSettingsAffordance.this.isShowAnimating = true;
        }
    }

    static {
        new Companion(null);
        PathInterpolator pathInterpolator = new PathInterpolator(0.61f, 1.0f, 0.88f, 1.0f);
        PathInterpolator pathInterpolator2 = new PathInterpolator(0.33f, 1.0f, 0.68f, 1.0f);
        AnimHelper.AnimationType animationType = AnimHelper.AnimationType.ALPHA;
        AnimHelper.AnimationType animationType2 = AnimHelper.AnimationType.TRANSLATION_Y;
        INIT_PROPERTY_FIELDS = new AnimHelper.AnimationType[]{animationType, animationType2};
        SHOW_ANIM_GLOW_ALPHA_PROPERTY = new AnimHelper.AnimProperty(animationType, 500, 0, 0.0f, 1.0f, pathInterpolator);
        SHOW_ANIM_GLOW_TRANSLATION_Y_PROPERTY = new AnimHelper.AnimProperty(animationType2, 500, 0, 0.0f, -1.0f, pathInterpolator2);
        SHOW_ANIM_ARROW_ALPHA_PROPERTY = new AnimHelper.AnimProperty(animationType, 300, 200, 0.0f, 1.0f, pathInterpolator);
        SHOW_ANIM_ARROW_TRANSLATION_Y_PROPERTY = new AnimHelper.AnimProperty(animationType2, 500, 0, 0.0f, -1.0f, pathInterpolator2);
        BOUNCING1_ANIM_ARROW_ALPHA_PROPERTY = new AnimHelper.AnimProperty(animationType, 200, 0, 1.0f, 0.0f, pathInterpolator);
        BOUNCING2_ANIM_ARROW_ALPHA_PROPERTY = new AnimHelper.AnimProperty(animationType, 400, 0, 0.0f, 1.0f, pathInterpolator);
        BOUNCING2_ANIM_ARROW_TRANSLATION_Y_PROPERTY = new AnimHelper.AnimProperty(animationType2, 400, 0, 0.0f, -1.0f, pathInterpolator2);
        HIDE_ANIM_GLOW_ALPHA_PROPERTY = new AnimHelper.AnimProperty(animationType, 200, 0, 1.0f, 0.0f, pathInterpolator);
        HIDE_ANIM_GLOW_TRANSLATION_Y_PROPERTY = new AnimHelper.AnimProperty(animationType2, 200, 0, -1.0f, -1.0f, pathInterpolator);
        HIDE_ANIM_ARROW_ALPHA_PROPERTY = new AnimHelper.AnimProperty(animationType, 100, 0, 1.0f, 0.0f, pathInterpolator);
        HIDE_ANIM_ARROW_TRANSLATION_Y_PROPERTY = new AnimHelper.AnimProperty(animationType2, 200, 0, -1.0f, -1.0f, pathInterpolator);
    }

    public SecQuickSettingsAffordance(Context context, DisplayLifecycle displayLifecycle) {
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService("window");
        this.lastDensityDpi = context.getResources().getConfiguration().densityDpi;
        this.lastOrientation = context.getResources().getConfiguration().orientation;
        this.lastLayoutDirection = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(context);
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        this.settingsHelper = settingsHelper;
        this.handler = new Handler(Looper.getMainLooper());
        this.settingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.shade.SecQuickSettingsAffordance$settingsListener$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (Intrinsics.areEqual(Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL_REVERSED), uri)) {
                    SecQuickSettingsAffordance secQuickSettingsAffordance = this.this$0;
                    boolean zIsPanelSplitReversed = secQuickSettingsAffordance.settingsHelper.isPanelSplitReversed();
                    secQuickSettingsAffordance.isReversed = zIsPanelSplitReversed;
                    Log.d("SecQuickSettingsAffordance", "OnChangedCallback isReversed = " + zIsPanelSplitReversed);
                    secQuickSettingsAffordance.removeView();
                }
            }
        };
        new DisplayLifecycle.Observer() { // from class: com.android.systemui.shade.SecQuickSettingsAffordance$displayLifecycleObserver$1
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                EmergencyButtonController$$ExternalSyntheticOutline0.m("onFolderStateChanged isOpened = ", "SecQuickSettingsAffordance", z);
                AnimHelper.AnimationType[] animationTypeArr = SecQuickSettingsAffordance.INIT_PROPERTY_FIELDS;
                this.this$0.removeView();
            }
        };
        Log.d("SecQuickSettingsAffordance", "init");
        settingsHelper.registerCallback(this.settingsListener, Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL_REVERSED));
        this.isReversed = settingsHelper.isPanelSplitReversed();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2024, 1336, -3);
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("SecQuickSettingsAffordance");
        this.layoutParams = layoutParams;
    }

    public static final void access$hiding(SecQuickSettingsAffordance secQuickSettingsAffordance) {
        boolean z = secQuickSettingsAffordance.isVisibleView;
        boolean z2 = secQuickSettingsAffordance.isShowAnimating;
        boolean z3 = secQuickSettingsAffordance.isBouncingAnimating;
        boolean z4 = secQuickSettingsAffordance.isAnimating;
        boolean zIsInvalidOrientation = secQuickSettingsAffordance.isInvalidOrientation();
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("hiding isVisibleView = ", ", isShowAnimating = ", ", isBouncingAnimating = ", z, z2);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, z3, ", isAnimating = ", z4, ", isInvalidOrientation = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sbM, zIsInvalidOrientation, "SecQuickSettingsAffordance");
        if (secQuickSettingsAffordance.settingsHelper.isRemoveAnimation() || secQuickSettingsAffordance.isInvalidOrientation()) {
            return;
        }
        if (secQuickSettingsAffordance.isShowAnimating) {
            AnimatorSet animatorSet = secQuickSettingsAffordance.showAnimSet;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            secQuickSettingsAffordance.startHideAnimSet();
            return;
        }
        if (!secQuickSettingsAffordance.isBouncingAnimating) {
            if (!secQuickSettingsAffordance.isVisibleView) {
                if (secQuickSettingsAffordance.isAnimating) {
                    secQuickSettingsAffordance.isAnimating = false;
                    return;
                }
                return;
            } else {
                AnimatorSet animatorSet2 = secQuickSettingsAffordance.showAnimSet;
                if (animatorSet2 != null) {
                    animatorSet2.cancel();
                }
                secQuickSettingsAffordance.startHideAnimSet();
                return;
            }
        }
        if (secQuickSettingsAffordance.bouncingCount % 2 == 0) {
            AnimatorSet animatorSet3 = secQuickSettingsAffordance.bouncing1AnimSet;
            if (animatorSet3 != null) {
                animatorSet3.cancel();
                return;
            }
            return;
        }
        AnimatorSet animatorSet4 = secQuickSettingsAffordance.bouncing2AnimSet;
        if (animatorSet4 != null) {
            animatorSet4.cancel();
        }
    }

    public static final void access$unregisterListener(SecQuickSettingsAffordance secQuickSettingsAffordance) {
        secQuickSettingsAffordance.settingsHelper.unregisterCallback(secQuickSettingsAffordance.settingsListener);
    }

    public static boolean isLargeScreen$1() {
        return ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet();
    }

    public final void displayEffect(final AnimHelper.AnimationState animationState, final SecQuickSettingsAffordanceInteractor$broadcastReceiver$1$onReceive$1 secQuickSettingsAffordanceInteractor$broadcastReceiver$1$onReceive$1) {
        Log.d("SecQuickSettingsAffordance", "displayEffect animationState = " + animationState);
        this.handler.post(new Runnable() { // from class: com.android.systemui.shade.SecQuickSettingsAffordance.displayEffect.1

            /* renamed from: com.android.systemui.shade.SecQuickSettingsAffordance$displayEffect$1$WhenMappings */
            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[AnimHelper.AnimationState.values().length];
                    try {
                        iArr[AnimHelper.AnimationState.SHOWING.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[AnimHelper.AnimationState.HIDING.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i = WhenMappings.$EnumSwitchMapping$0[animationState.ordinal()];
                if (i != 1) {
                    if (i != 2) {
                        return;
                    }
                    SecQuickSettingsAffordance.access$hiding(this);
                } else {
                    SecQuickSettingsAffordance secQuickSettingsAffordance = this;
                    Runnable runnable = secQuickSettingsAffordanceInteractor$broadcastReceiver$1$onReceive$1;
                    AnimHelper.AnimationType[] animationTypeArr = SecQuickSettingsAffordance.INIT_PROPERTY_FIELDS;
                    secQuickSettingsAffordance.showing(runnable);
                }
            }
        });
    }

    public final int getArrowTranslateY() {
        return isLargeScreen$1() ? this.context.getResources().getDimensionPixelSize(R.dimen.qs_affordance_arrow_translation_y_tablet) : this.context.getResources().getDimensionPixelSize(R.dimen.qs_affordance_arrow_translation_y);
    }

    public final float getArrowWidthFactor() {
        return isLargeScreen$1() ? this.lastOrientation == 2 ? 0.0375f : 0.06f : ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isFoldWide() ? 0.0679f : 0.1111f;
    }

    public final int getGlowHeight() {
        return this.context.getResources().getDimensionPixelSize(R.dimen.qs_affordance_glow_height);
    }

    public final float getGlowWidthFactor() {
        return isLargeScreen$1() ? this.lastOrientation == 2 ? 0.2312f : 0.3f : ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isFoldWide() ? 0.3f : 0.33f;
    }

    public final void initAnimProperties(boolean z) {
        AnimHelper.INSTANCE.initProperty(INIT_PROPERTY_FIELDS, this.glowView, this.arrowView);
        if (z) {
            boolean z2 = this.isAnimating;
            boolean z3 = this.isAttachedView;
            boolean z4 = this.isVisibleView;
            int i = this.bouncingCount;
            boolean z5 = this.isBouncingAnimating;
            StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("initAnimProperties VI layout visibility is GONE, isAnimating = ", ", isAttachedView = ", ", isVisibleView = ", z2, z3);
            sbM.append(z4);
            sbM.append(", bouncingCount = ");
            sbM.append(i);
            sbM.append(", isBouncingAnimating = ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sbM, z5, "SecQuickSettingsAffordance");
            this.isAnimating = false;
            this.bouncingCount = 0;
            this.isBouncingAnimating = false;
            if (this.isAttachedView) {
                this.isVisibleView = false;
                updateLayoutVisibility();
            }
        }
    }

    public final boolean isInvalidOrientation() {
        return !isLargeScreen$1() && this.lastOrientation == 2;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        if (configuration != null) {
            boolean z = this.isAttachedView;
            boolean zIsRemoveAnimation = this.settingsHelper.isRemoveAnimation();
            boolean z2 = this.isRemoveView;
            StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("onConfigChanged isAttachedView = ", ", noAnim = ", ", isRemoveView = ", z, zIsRemoveAnimation);
            sbM.append(z2);
            sbM.append(", newConfig = ");
            sbM.append(configuration);
            Log.d("SecQuickSettingsAffordance", sbM.toString());
            if (!this.isAttachedView || this.settingsHelper.isRemoveAnimation() || this.isRemoveView) {
                this.lastDensityDpi = configuration.densityDpi;
                this.lastOrientation = configuration.orientation;
                this.lastLayoutDirection = configuration.getLayoutDirection();
            } else {
                if (this.lastDensityDpi == configuration.densityDpi && this.lastOrientation == configuration.orientation && this.lastLayoutDirection == configuration.getLayoutDirection()) {
                    return;
                }
                this.lastDensityDpi = configuration.densityDpi;
                this.lastOrientation = configuration.orientation;
                this.lastLayoutDirection = configuration.getLayoutDirection();
                removeView();
            }
        }
    }

    public final void removeView() {
        Log.d("SecQuickSettingsAffordance", "removeView");
        if (this.isAnimating) {
            AnimatorSet animatorSet = this.showAnimSet;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            AnimatorSet animatorSet2 = this.bouncing1AnimSet;
            if (animatorSet2 != null) {
                animatorSet2.cancel();
            }
            AnimatorSet animatorSet3 = this.bouncing2AnimSet;
            if (animatorSet3 != null) {
                animatorSet3.cancel();
            }
            AnimatorSet animatorSet4 = this.hideAnimSet;
            if (animatorSet4 != null) {
                animatorSet4.cancel();
            }
        }
        if (this.isAttachedView) {
            reset$3();
        }
    }

    public final void reset$3() {
        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("reset isAttachedView = ", ", isRemoveView = ", "SecQuickSettingsAffordance", this.isAttachedView, this.isRemoveView);
        this.isAttachedView = false;
        View view = this.layout;
        if (view != null && view.isAttachedToWindow()) {
            this.windowManager.removeViewImmediate(this.layout);
        }
        this.showAnimList.clear();
        this.bouncing1AnimList.clear();
        this.bouncing2AnimList.clear();
        this.hideAnimList.clear();
        this.layout = null;
        this.glowView = null;
        this.arrowView = null;
    }

    public final void showing(Runnable runnable) {
        final SecQuickSettingsAffordance secQuickSettingsAffordance;
        CarrierTextManager$$ExternalSyntheticOutline0.m(KeyguardFMMViewController$$ExternalSyntheticOutline0.m("showing isAnimating = ", this.lastOrientation, " -> true, lastOrientation = ", this.isAnimating, ", isRemoveView = "), this.isRemoveView, ", isInvalidOrientation = ", isInvalidOrientation(), "SecQuickSettingsAffordance");
        if (this.settingsHelper.isRemoveAnimation() || isInvalidOrientation() || this.isRemoveView) {
            return;
        }
        if (this.isAttachedView) {
            if (this.isAnimating) {
                Log.d("SecQuickSettingsAffordance", "start cancel anim duplicated anim");
                AnimatorSet animatorSet = this.showAnimSet;
                if (animatorSet != null) {
                    animatorSet.cancel();
                }
                AnimatorSet animatorSet2 = this.hideAnimSet;
                if (animatorSet2 != null) {
                    animatorSet2.cancel();
                }
            }
            initAnimProperties(false);
            this.isAnimating = true;
            if (runnable != null) {
                runnable.run();
            }
            AnimatorSet animatorSet3 = this.showAnimSet;
            if (animatorSet3 != null) {
                animatorSet3.start();
                return;
            }
            return;
        }
        this.isAttachedView = true;
        this.layout = LayoutInflater.from(this.context).inflate(R.layout.sec_quick_settings_affordance, (ViewGroup) null);
        updateLayoutVisibility();
        View view = this.layout;
        ImageView imageView = view != null ? (ImageView) view.findViewById(R.id.sec_quick_settings_affordance_glow) : null;
        this.glowView = imageView;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) (imageView != null ? imageView.getLayoutParams() : null);
        layoutParams.width = (int) (getGlowWidthFactor() * this.context.getResources().getDisplayMetrics().widthPixels);
        int glowHeight = getGlowHeight();
        layoutParams.height = glowHeight;
        layoutParams.topMargin = -glowHeight;
        layoutParams.gravity = this.isReversed ? 8388659 : 8388661;
        View view2 = this.layout;
        ImageView imageView2 = view2 != null ? (ImageView) view2.findViewById(R.id.sec_quick_settings_affordance_arrow) : null;
        this.arrowView = imageView2;
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) (imageView2 != null ? imageView2.getLayoutParams() : null);
        layoutParams2.width = (int) (getArrowWidthFactor() * this.context.getResources().getDisplayMetrics().widthPixels);
        layoutParams2.height = (int) (this.context.getResources().getDisplayMetrics().heightPixels * (isLargeScreen$1() ? this.lastOrientation == 2 ? 0.0225f : 0.021f : ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isFoldWide() ? 0.0203f : 0.0192f));
        layoutParams2.topMargin = getGlowHeight();
        int glowWidthFactor = (int) ((getGlowWidthFactor() - getArrowWidthFactor()) * this.context.getResources().getDisplayMetrics().widthPixels * 0.5f);
        if (this.isReversed) {
            layoutParams2.setMarginStart(glowWidthFactor);
            layoutParams2.setMarginEnd(0);
            layoutParams2.gravity = 8388659;
        } else {
            layoutParams2.setMarginStart(0);
            layoutParams2.setMarginEnd(glowWidthFactor);
            layoutParams2.gravity = 8388661;
        }
        initAnimProperties(true);
        this.showAnimList.clear();
        AnimHelper animHelper = AnimHelper.INSTANCE;
        AnimHelper.AnimPairSet animPairSet = new AnimHelper.AnimPairSet(this.glowView, SHOW_ANIM_GLOW_ALPHA_PROPERTY);
        ImageView imageView3 = this.glowView;
        float glowHeight2 = getGlowHeight();
        AnimHelper.AnimProperty animProperty = SHOW_ANIM_GLOW_TRANSLATION_Y_PROPERTY;
        animProperty.setToValue(glowHeight2);
        Unit unit = Unit.INSTANCE;
        AnimHelper.AnimPairSet animPairSet2 = new AnimHelper.AnimPairSet(imageView3, animProperty);
        AnimHelper.AnimPairSet animPairSet3 = new AnimHelper.AnimPairSet(this.arrowView, SHOW_ANIM_ARROW_ALPHA_PROPERTY);
        ImageView imageView4 = this.arrowView;
        float arrowTranslateY = getArrowTranslateY();
        AnimHelper.AnimProperty animProperty2 = SHOW_ANIM_ARROW_TRANSLATION_Y_PROPERTY;
        animProperty2.setToValue(arrowTranslateY);
        this.showAnimSet = animHelper.makeAnimSet(new AnimHelper.AnimPairSet[]{animPairSet, animPairSet2, animPairSet3, new AnimHelper.AnimPairSet(imageView4, animProperty2)}, this.showAnimList, new ShowAnimatorListener("Show"));
        this.bouncing1AnimList.clear();
        this.bouncing1AnimSet = AnimHelper.makeAnimSet$default(animHelper, new AnimHelper.AnimPairSet[]{new AnimHelper.AnimPairSet(this.arrowView, BOUNCING1_ANIM_ARROW_ALPHA_PROPERTY)}, this.bouncing1AnimList, null, 4, null);
        this.bouncing2AnimList.clear();
        AnimHelper.AnimPairSet animPairSet4 = new AnimHelper.AnimPairSet(this.arrowView, BOUNCING2_ANIM_ARROW_ALPHA_PROPERTY);
        ImageView imageView5 = this.arrowView;
        float arrowTranslateY2 = getArrowTranslateY();
        AnimHelper.AnimProperty animProperty3 = BOUNCING2_ANIM_ARROW_TRANSLATION_Y_PROPERTY;
        animProperty3.setToValue(arrowTranslateY2);
        AnimatorSet animatorSetMakeAnimSet$default = AnimHelper.makeAnimSet$default(animHelper, new AnimHelper.AnimPairSet[]{animPairSet4, new AnimHelper.AnimPairSet(imageView5, animProperty3)}, this.bouncing2AnimList, null, 4, null);
        this.bouncing2AnimSet = animatorSetMakeAnimSet$default;
        AnimatorSet animatorSet4 = this.bouncing1AnimSet;
        if (animatorSet4 != null) {
            secQuickSettingsAffordance = this;
            animatorSet4.addListener(new BouncingAnimatorListener(secQuickSettingsAffordance, "Bouncing1", animatorSetMakeAnimSet$default, 0, 4, null));
        } else {
            secQuickSettingsAffordance = this;
        }
        AnimatorSet animatorSet5 = secQuickSettingsAffordance.bouncing2AnimSet;
        if (animatorSet5 != null) {
            animatorSet5.addListener(secQuickSettingsAffordance.new BouncingAnimatorListener("Bouncing2", secQuickSettingsAffordance.bouncing1AnimSet, 100));
        }
        final View view3 = secQuickSettingsAffordance.layout;
        if (view3 != null) {
            OneShotPreDrawListener.add(view3, new Runnable() { // from class: com.android.systemui.shade.SecQuickSettingsAffordance$updateLayoutAndAnimators$$inlined$doOnPreDraw$1
                @Override // java.lang.Runnable
                public final void run() {
                    Log.d("SecQuickSettingsAffordance", "doOnPreDraw");
                    SecQuickSettingsAffordance secQuickSettingsAffordance2 = secQuickSettingsAffordance;
                    secQuickSettingsAffordance2.isVisibleView = true;
                    secQuickSettingsAffordance2.updateLayoutVisibility();
                    secQuickSettingsAffordance.showing(null);
                }
            });
        }
        secQuickSettingsAffordance.windowManager.addView(secQuickSettingsAffordance.layout, secQuickSettingsAffordance.layoutParams);
    }

    public final void startHideAnimSet() {
        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("startHideAnimSet isHideAnimating = ", ", isAnimating = ", "SecQuickSettingsAffordance", this.isHideAnimating, this.isAnimating);
        if (!this.isHideAnimating && this.isAnimating && this.isAttachedView) {
            this.hideAnimList.clear();
            AnimatorSet animatorSet = this.hideAnimSet;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            AnimHelper animHelper = AnimHelper.INSTANCE;
            ImageView imageView = this.glowView;
            float alpha = imageView != null ? imageView.getAlpha() : 1.0f;
            AnimHelper.AnimProperty animProperty = HIDE_ANIM_GLOW_ALPHA_PROPERTY;
            animProperty.setFromValue(alpha);
            Unit unit = Unit.INSTANCE;
            AnimHelper.AnimPairSet animPairSet = new AnimHelper.AnimPairSet(imageView, animProperty);
            ImageView imageView2 = this.glowView;
            float fFloatValue = (imageView2 != null ? Float.valueOf(imageView2.getTranslationY()) : Integer.valueOf(getGlowHeight())).floatValue();
            AnimHelper.AnimProperty animProperty2 = HIDE_ANIM_GLOW_TRANSLATION_Y_PROPERTY;
            animProperty2.setFromValue(fFloatValue);
            animProperty2.setToValue(getGlowHeight() * 0.8f);
            AnimHelper.AnimPairSet animPairSet2 = new AnimHelper.AnimPairSet(imageView2, animProperty2);
            ImageView imageView3 = this.arrowView;
            float alpha2 = imageView3 != null ? imageView3.getAlpha() : 1.0f;
            AnimHelper.AnimProperty animProperty3 = HIDE_ANIM_ARROW_ALPHA_PROPERTY;
            animProperty3.setFromValue(alpha2);
            AnimHelper.AnimPairSet animPairSet3 = new AnimHelper.AnimPairSet(imageView3, animProperty3);
            ImageView imageView4 = this.arrowView;
            float fFloatValue2 = (imageView4 != null ? Float.valueOf(imageView4.getTranslationY()) : Integer.valueOf(getArrowTranslateY())).floatValue();
            AnimHelper.AnimProperty animProperty4 = HIDE_ANIM_ARROW_TRANSLATION_Y_PROPERTY;
            animProperty4.setFromValue(fFloatValue2);
            animProperty4.setToValue(getArrowTranslateY() * 0.8f);
            AnimatorSet animatorSetMakeAnimSet = animHelper.makeAnimSet(new AnimHelper.AnimPairSet[]{animPairSet, animPairSet2, animPairSet3, new AnimHelper.AnimPairSet(imageView4, animProperty4)}, this.hideAnimList, new HideAnimatorListener("Hide"));
            this.hideAnimSet = animatorSetMakeAnimSet;
            if (animatorSetMakeAnimSet != null) {
                animatorSetMakeAnimSet.setStartDelay(100L);
            }
            AnimatorSet animatorSet2 = this.hideAnimSet;
            if (animatorSet2 != null) {
                animatorSet2.start();
            }
        }
    }

    public final void updateLayoutVisibility() {
        View view = this.layout;
        if (view != null) {
            view.setVisibility(this.isVisibleView ? 0 : 8);
        }
    }

    public final class BouncingAnimatorListener extends AnimHelper.BaseAnimatorListener {
        public final AnimatorSet nextAnimSet;
        public final int startDelay;

        public BouncingAnimatorListener(String str, AnimatorSet animatorSet, int i) {
            super("SecQuickSettingsAffordance", str, true);
            this.nextAnimSet = animatorSet;
            this.startDelay = i;
        }

        @Override // com.android.systemui.util.AnimHelper.BaseAnimatorListener, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            SecQuickSettingsAffordance secQuickSettingsAffordance;
            int i;
            if (getDebug()) {
                String tag = getTag();
                String logPrefix = getLogPrefix();
                boolean zIsCanceled = isCanceled();
                SecQuickSettingsAffordance secQuickSettingsAffordance2 = SecQuickSettingsAffordance.this;
                Log.d(tag, logPrefix + " onAnimationEnd isCanceled = " + zIsCanceled + ", bouncingCount = " + secQuickSettingsAffordance2.bouncingCount + ", isBouncingAnimating = " + secQuickSettingsAffordance2.isBouncingAnimating);
            }
            if (isCanceled() || (i = (secQuickSettingsAffordance = SecQuickSettingsAffordance.this).bouncingCount) >= 5) {
                SecQuickSettingsAffordance secQuickSettingsAffordance3 = SecQuickSettingsAffordance.this;
                secQuickSettingsAffordance3.bouncingCount = 0;
                secQuickSettingsAffordance3.isBouncingAnimating = false;
                secQuickSettingsAffordance3.startHideAnimSet();
                return;
            }
            secQuickSettingsAffordance.bouncingCount = i + 1;
            AnimatorSet animatorSet = this.nextAnimSet;
            if (animatorSet != null) {
                animatorSet.setStartDelay(this.startDelay);
            }
            AnimatorSet animatorSet2 = this.nextAnimSet;
            if (animatorSet2 != null) {
                animatorSet2.start();
            }
        }

        public /* synthetic */ BouncingAnimatorListener(SecQuickSettingsAffordance secQuickSettingsAffordance, String str, AnimatorSet animatorSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, animatorSet, (i2 & 4) != 0 ? 0 : i);
        }
    }
}
