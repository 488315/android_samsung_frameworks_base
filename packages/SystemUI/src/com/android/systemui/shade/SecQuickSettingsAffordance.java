package com.android.systemui.shade;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.AnimHelper;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
    public AnimatorSet hideAnimSet;
    public boolean isAnimating;
    public boolean isAttachedView;
    public boolean isBouncingAnimating;
    public boolean isHideAnimating;
    public boolean isRemoveView;
    public boolean isShowAnimating;
    public boolean isVisibleView;
    public int lastDensityDpi;
    public int lastOrientation;
    public View layout;
    public final WindowManager.LayoutParams layoutParams;
    public AnimatorSet showAnimSet;
    public final WindowManager windowManager;
    public final ArrayList showAnimList = new ArrayList();
    public final ArrayList bouncing1AnimList = new ArrayList();
    public final ArrayList bouncing2AnimList = new ArrayList();
    public final ArrayList hideAnimList = new ArrayList();
    private final SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
    public final Handler handler = new Handler(Looper.getMainLooper());

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
            boolean z = secQuickSettingsAffordance.isRemoveView;
            if (!z) {
                secQuickSettingsAffordance.initAnimProperties(true);
                return;
            }
            EmergencyButtonController$$ExternalSyntheticOutline0.m("reset isAttachedView = ", ", isRemoveView = ", "SecQuickSettingsAffordance", secQuickSettingsAffordance.isAttachedView, z);
            secQuickSettingsAffordance.isAttachedView = false;
            secQuickSettingsAffordance.windowManager.removeViewImmediate(secQuickSettingsAffordance.layout);
            secQuickSettingsAffordance.showAnimList.clear();
            secQuickSettingsAffordance.bouncing1AnimList.clear();
            secQuickSettingsAffordance.bouncing2AnimList.clear();
            secQuickSettingsAffordance.hideAnimList.clear();
            secQuickSettingsAffordance.layout = null;
            secQuickSettingsAffordance.glowView = null;
            secQuickSettingsAffordance.arrowView = null;
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
                Boolean valueOf = animatorSet2 != null ? Boolean.valueOf(animatorSet2.isRunning()) : null;
                Log.d(tag, logPrefix + " onAnimationCancel isAnimating = " + z + ", isRunning = " + valueOf + ", isAttachedView = " + SecQuickSettingsAffordance.this.isAttachedView);
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
            if (!secQuickSettingsAffordance2.isAttachedView) {
                secQuickSettingsAffordance2.isAttachedView = true;
                secQuickSettingsAffordance2.windowManager.addView(secQuickSettingsAffordance2.layout, secQuickSettingsAffordance2.layoutParams);
            }
            SecQuickSettingsAffordance secQuickSettingsAffordance3 = SecQuickSettingsAffordance.this;
            if (!secQuickSettingsAffordance3.isVisibleView) {
                secQuickSettingsAffordance3.isVisibleView = true;
                secQuickSettingsAffordance3.updateLayoutVisibility();
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

    public SecQuickSettingsAffordance(Context context) {
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService("window");
        this.lastDensityDpi = context.getResources().getConfiguration().densityDpi;
        this.lastOrientation = context.getResources().getConfiguration().orientation;
        Log.d("SecQuickSettingsAffordance", "init");
        updateLayoutAndAnimators();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2024, 1336, -3);
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("SecQuickSettingsAffordance");
        this.layoutParams = layoutParams;
    }

    public static final void access$hiding(SecQuickSettingsAffordance secQuickSettingsAffordance) {
        boolean z = secQuickSettingsAffordance.isVisibleView;
        boolean z2 = secQuickSettingsAffordance.isShowAnimating;
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("hiding isVisibleView = ", ", isShowAnimating = ", ", isBouncingAnimating = ", z, z2), secQuickSettingsAffordance.isBouncingAnimating, ", isAnimating = ", secQuickSettingsAffordance.isAnimating, "SecQuickSettingsAffordance");
        if (secQuickSettingsAffordance.settingsHelper.isRemoveAnimation() || secQuickSettingsAffordance.lastOrientation == 2) {
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

    public static final void access$showing(SecQuickSettingsAffordance secQuickSettingsAffordance, Runnable runnable) {
        boolean z = secQuickSettingsAffordance.isAnimating;
        int i = secQuickSettingsAffordance.lastOrientation;
        ActionBarContextView$$ExternalSyntheticOutline0.m(KeyguardFMMViewController$$ExternalSyntheticOutline0.m("showing isAnimating = ", i, " -> true, lastOrientation = ", z, ", isRemoveView = "), secQuickSettingsAffordance.isRemoveView, "SecQuickSettingsAffordance");
        if (secQuickSettingsAffordance.settingsHelper.isRemoveAnimation() || secQuickSettingsAffordance.lastOrientation == 2 || secQuickSettingsAffordance.isRemoveView) {
            return;
        }
        if (secQuickSettingsAffordance.isAnimating) {
            Log.d("SecQuickSettingsAffordance", "start cancel anim duplicated anim");
            AnimatorSet animatorSet = secQuickSettingsAffordance.showAnimSet;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            AnimatorSet animatorSet2 = secQuickSettingsAffordance.hideAnimSet;
            if (animatorSet2 != null) {
                animatorSet2.cancel();
            }
        }
        secQuickSettingsAffordance.initAnimProperties(false);
        secQuickSettingsAffordance.isAnimating = true;
        if (runnable != null) {
            runnable.run();
        }
        AnimatorSet animatorSet3 = secQuickSettingsAffordance.showAnimSet;
        if (animatorSet3 != null) {
            animatorSet3.start();
        }
    }

    public final void displayEffect(final AnimHelper.AnimationState animationState, final Runnable runnable) {
        Log.d("SecQuickSettingsAffordance", "displayEffect animationState = " + animationState);
        this.handler.post(new Runnable() { // from class: com.android.systemui.shade.SecQuickSettingsAffordance$displayEffect$1

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
                int i = WhenMappings.$EnumSwitchMapping$0[AnimHelper.AnimationState.this.ordinal()];
                if (i == 1) {
                    SecQuickSettingsAffordance.access$showing(this, runnable);
                } else {
                    if (i != 2) {
                        return;
                    }
                    SecQuickSettingsAffordance.access$hiding(this);
                }
            }
        });
    }

    public final int getArrowTranslateY() {
        return this.context.getResources().getDimensionPixelSize(R.dimen.qs_affordance_arrow_translate_y);
    }

    public final int getGlowHeight() {
        return this.context.getResources().getDimensionPixelSize(R.dimen.qs_affordance_glow_height);
    }

    public final void initAnimProperties(boolean z) {
        AnimHelper.INSTANCE.initProperty(INIT_PROPERTY_FIELDS, this.glowView, this.arrowView);
        if (z) {
            boolean z2 = this.isAnimating;
            boolean z3 = this.isAttachedView;
            boolean z4 = this.isVisibleView;
            int i = this.bouncingCount;
            boolean z5 = this.isBouncingAnimating;
            StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("initAnimProperties VI layout visibility is GONE, isAnimating = ", ", isAttachedView = ", ", isVisibleView = ", z2, z3);
            m.append(z4);
            m.append(", bouncingCount = ");
            m.append(i);
            m.append(", isBouncingAnimating = ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(m, z5, "SecQuickSettingsAffordance");
            this.isAnimating = false;
            this.bouncingCount = 0;
            this.isBouncingAnimating = false;
            if (this.isAttachedView) {
                this.isVisibleView = false;
                updateLayoutVisibility();
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        if (configuration != null) {
            boolean z = this.isAttachedView;
            boolean isRemoveAnimation = this.settingsHelper.isRemoveAnimation();
            boolean z2 = this.isRemoveView;
            StringBuilder sb = new StringBuilder("onConfigChanged newConfig = ");
            sb.append(configuration);
            sb.append(", isAttachedView = ");
            sb.append(z);
            sb.append(", noAnim = ");
            KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(sb, isRemoveAnimation, ", isRemoveView = ", z2, "SecQuickSettingsAffordance");
            if (!this.isAttachedView || this.settingsHelper.isRemoveAnimation() || this.isRemoveView) {
                this.lastDensityDpi = configuration.densityDpi;
                this.lastOrientation = configuration.orientation;
                return;
            }
            int i = this.lastDensityDpi;
            int i2 = configuration.densityDpi;
            boolean z3 = i != i2;
            if (z3 || this.lastOrientation != configuration.orientation) {
                this.lastDensityDpi = i2;
                this.lastOrientation = configuration.orientation;
                if (this.isAttachedView) {
                    this.isAttachedView = false;
                    this.windowManager.removeViewImmediate(this.layout);
                }
                if (z3) {
                    this.showAnimList.clear();
                    this.bouncing1AnimList.clear();
                    this.bouncing2AnimList.clear();
                    this.hideAnimList.clear();
                    updateLayoutAndAnimators();
                }
            }
        }
    }

    public final void startHideAnimSet() {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("startHideAnimSet isHideAnimating = ", ", isAnimating = ", "SecQuickSettingsAffordance", this.isHideAnimating, this.isAnimating);
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
            float floatValue = (imageView2 != null ? Float.valueOf(imageView2.getTranslationY()) : Integer.valueOf(getGlowHeight())).floatValue();
            AnimHelper.AnimProperty animProperty2 = HIDE_ANIM_GLOW_TRANSLATION_Y_PROPERTY;
            animProperty2.setFromValue(floatValue);
            animProperty2.setToValue(getGlowHeight() * 0.8f);
            AnimHelper.AnimPairSet animPairSet2 = new AnimHelper.AnimPairSet(imageView2, animProperty2);
            ImageView imageView3 = this.arrowView;
            float alpha2 = imageView3 != null ? imageView3.getAlpha() : 1.0f;
            AnimHelper.AnimProperty animProperty3 = HIDE_ANIM_ARROW_ALPHA_PROPERTY;
            animProperty3.setFromValue(alpha2);
            AnimHelper.AnimPairSet animPairSet3 = new AnimHelper.AnimPairSet(imageView3, animProperty3);
            ImageView imageView4 = this.arrowView;
            float floatValue2 = (imageView4 != null ? Float.valueOf(imageView4.getTranslationY()) : Integer.valueOf(getArrowTranslateY())).floatValue();
            AnimHelper.AnimProperty animProperty4 = HIDE_ANIM_ARROW_TRANSLATION_Y_PROPERTY;
            animProperty4.setFromValue(floatValue2);
            animProperty4.setToValue(getArrowTranslateY() * 0.8f);
            AnimatorSet makeAnimSet = animHelper.makeAnimSet(new AnimHelper.AnimPairSet[]{animPairSet, animPairSet2, animPairSet3, new AnimHelper.AnimPairSet(imageView4, animProperty4)}, this.hideAnimList, new HideAnimatorListener("Hide"));
            this.hideAnimSet = makeAnimSet;
            if (makeAnimSet != null) {
                makeAnimSet.setStartDelay(100L);
            }
            AnimatorSet animatorSet2 = this.hideAnimSet;
            if (animatorSet2 != null) {
                animatorSet2.start();
            }
        }
    }

    public final void updateLayoutAndAnimators() {
        this.layout = LayoutInflater.from(this.context).inflate(R.layout.sec_quick_settings_affordance, (ViewGroup) null);
        updateLayoutVisibility();
        View view = this.layout;
        this.glowView = view != null ? (ImageView) view.findViewById(R.id.sec_quick_settings_affordance_glow) : null;
        View view2 = this.layout;
        this.arrowView = view2 != null ? (ImageView) view2.findViewById(R.id.sec_quick_settings_affordance_arrow) : null;
        initAnimProperties(true);
        AnimHelper animHelper = AnimHelper.INSTANCE;
        AnimHelper.AnimPairSet animPairSet = new AnimHelper.AnimPairSet(this.glowView, SHOW_ANIM_GLOW_ALPHA_PROPERTY);
        ImageView imageView = this.glowView;
        float glowHeight = getGlowHeight();
        AnimHelper.AnimProperty animProperty = SHOW_ANIM_GLOW_TRANSLATION_Y_PROPERTY;
        animProperty.setToValue(glowHeight);
        Unit unit = Unit.INSTANCE;
        AnimHelper.AnimPairSet animPairSet2 = new AnimHelper.AnimPairSet(imageView, animProperty);
        AnimHelper.AnimPairSet animPairSet3 = new AnimHelper.AnimPairSet(this.arrowView, SHOW_ANIM_ARROW_ALPHA_PROPERTY);
        ImageView imageView2 = this.arrowView;
        float arrowTranslateY = getArrowTranslateY();
        AnimHelper.AnimProperty animProperty2 = SHOW_ANIM_ARROW_TRANSLATION_Y_PROPERTY;
        animProperty2.setToValue(arrowTranslateY);
        this.showAnimSet = animHelper.makeAnimSet(new AnimHelper.AnimPairSet[]{animPairSet, animPairSet2, animPairSet3, new AnimHelper.AnimPairSet(imageView2, animProperty2)}, this.showAnimList, new ShowAnimatorListener("Show"));
        this.bouncing1AnimSet = AnimHelper.makeAnimSet$default(animHelper, new AnimHelper.AnimPairSet[]{new AnimHelper.AnimPairSet(this.arrowView, BOUNCING1_ANIM_ARROW_ALPHA_PROPERTY)}, this.bouncing1AnimList, null, 4, null);
        AnimHelper.AnimPairSet animPairSet4 = new AnimHelper.AnimPairSet(this.arrowView, BOUNCING2_ANIM_ARROW_ALPHA_PROPERTY);
        ImageView imageView3 = this.arrowView;
        float arrowTranslateY2 = getArrowTranslateY();
        AnimHelper.AnimProperty animProperty3 = BOUNCING2_ANIM_ARROW_TRANSLATION_Y_PROPERTY;
        animProperty3.setToValue(arrowTranslateY2);
        AnimatorSet makeAnimSet$default = AnimHelper.makeAnimSet$default(animHelper, new AnimHelper.AnimPairSet[]{animPairSet4, new AnimHelper.AnimPairSet(imageView3, animProperty3)}, this.bouncing2AnimList, null, 4, null);
        this.bouncing2AnimSet = makeAnimSet$default;
        AnimatorSet animatorSet = this.bouncing1AnimSet;
        if (animatorSet != null) {
            animatorSet.addListener(new BouncingAnimatorListener(this, "Bouncing1", makeAnimSet$default, 0, 4, null));
        }
        AnimatorSet animatorSet2 = this.bouncing2AnimSet;
        if (animatorSet2 != null) {
            animatorSet2.addListener(new BouncingAnimatorListener("Bouncing2", this.bouncing1AnimSet, 100));
        }
        AnimHelper.AnimPairSet animPairSet5 = new AnimHelper.AnimPairSet(this.glowView, HIDE_ANIM_GLOW_ALPHA_PROPERTY);
        ImageView imageView4 = this.glowView;
        float glowHeight2 = getGlowHeight();
        AnimHelper.AnimProperty animProperty4 = HIDE_ANIM_GLOW_TRANSLATION_Y_PROPERTY;
        animProperty4.setFromValue(glowHeight2);
        animProperty4.setToValue(getGlowHeight() * 0.8f);
        AnimHelper.AnimPairSet animPairSet6 = new AnimHelper.AnimPairSet(imageView4, animProperty4);
        AnimHelper.AnimPairSet animPairSet7 = new AnimHelper.AnimPairSet(this.arrowView, HIDE_ANIM_ARROW_ALPHA_PROPERTY);
        ImageView imageView5 = this.arrowView;
        float arrowTranslateY3 = getArrowTranslateY();
        AnimHelper.AnimProperty animProperty5 = HIDE_ANIM_ARROW_TRANSLATION_Y_PROPERTY;
        animProperty5.setFromValue(arrowTranslateY3);
        animProperty5.setToValue(getArrowTranslateY() * 0.8f);
        this.hideAnimSet = animHelper.makeAnimSet(new AnimHelper.AnimPairSet[]{animPairSet5, animPairSet6, animPairSet7, new AnimHelper.AnimPairSet(imageView5, animProperty5)}, this.hideAnimList, new HideAnimatorListener("Hide"));
    }

    public final void updateLayoutVisibility() {
        View view = this.layout;
        if (view == null) {
            return;
        }
        view.setVisibility(this.isVisibleView ? 0 : 8);
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
                boolean isCanceled = isCanceled();
                SecQuickSettingsAffordance secQuickSettingsAffordance2 = SecQuickSettingsAffordance.this;
                Log.d(tag, logPrefix + " onAnimationEnd isCanceled = " + isCanceled + ", bouncingCount = " + secQuickSettingsAffordance2.bouncingCount + ", isBouncingAnimating = " + secQuickSettingsAffordance2.isBouncingAnimating);
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
