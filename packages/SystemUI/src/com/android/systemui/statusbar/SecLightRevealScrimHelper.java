package com.android.systemui.statusbar;

import android.content.Context;
import android.graphics.Point;
import android.view.animation.PathInterpolator;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.doze.AODParameters;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.LightRevealEffect;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.view.SemWindowManager;
import dagger.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecLightRevealScrimHelper {
    public static final Companion Companion = new Companion(null);
    public static final PathInterpolator SEC_LIGHT_REVEAL_INTERPOLATOR = new PathInterpolator(0.45f, 0.18f, 0.35f, 1.0f);
    public final BiometricUnlockController biometricUnlockController;
    public final BroadcastDispatcher broadcastDispatcher;
    public final Context context;
    public final DozeParameters dozeParameters;
    public boolean isFolded;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final LightRevealScrim lightRevealScrim;
    public final Lazy pluginAODManagerLazy;
    public int powerKeyYPos;
    public final ScreenLifecycle screenLifecycle;
    public final ScreenOffAnimationController screenOffAnimationController;
    public SecCircleReveal secCircleReveal;
    private final SettingsHelper settingsHelper;
    public final WakefulnessLifecycle wakefulnessLifecycle;
    public float secRevealCenterX = -1.0f;
    public float secRevealCenterY = -1.0f;
    public float secRevealDoubleTapX = -1.0f;
    public float secRevealDoubleTapY = -1.0f;
    public final Point physicalDisplaySize = new Point(1080, 2340);
    public final kotlin.Lazy semWindowManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.SecLightRevealScrimHelper$semWindowManager$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return SemWindowManager.getInstance();
        }
    });

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SecCircleReveal implements LightRevealEffect {
        public float centerX;
        public float centerY;
        public final float endRadius;
        public final float startRadius;

        public SecCircleReveal(float f, float f2, float f3, float f4) {
            this.centerX = f;
            this.centerY = f2;
            this.startRadius = f3;
            this.endRadius = f4;
        }

        @Override // com.android.systemui.statusbar.LightRevealEffect
        public final void setRevealAmountOnScrim(float f, LightRevealScrim lightRevealScrim) {
            SecLightRevealScrimHelper.Companion.getClass();
            float interpolation = SecLightRevealScrimHelper.SEC_LIGHT_REVEAL_INTERPOLATOR.getInterpolation(f);
            LightRevealEffect.Companion.getClass();
            float percentPastThreshold = LightRevealEffect.Companion.getPercentPastThreshold(interpolation, 0.5f);
            float f2 = this.endRadius;
            float f3 = this.startRadius;
            float m$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f2, f3, f, f3);
            lightRevealScrim.setRevealGradientEndColorAlpha(1.0f - percentPastThreshold);
            if (LsRune.AOD_LIGHT_REVEAL) {
                float f4 = 1.0f - f;
                if (lightRevealScrim.revealDimGradientEndColorAlpha != f4) {
                    lightRevealScrim.revealDimGradientEndColorAlpha = f4;
                    lightRevealScrim.setPaintColorFilter();
                }
            }
            float f5 = this.centerX;
            float f6 = this.centerY;
            lightRevealScrim.setRevealGradientBounds(f5 - m$1, f6 - m$1, f5 + m$1, f6 + m$1);
        }
    }

    public SecLightRevealScrimHelper(Context context, Lazy lazy, ScreenOffAnimationController screenOffAnimationController, BiometricUnlockController biometricUnlockController, KeyguardStateController keyguardStateController, WakefulnessLifecycle wakefulnessLifecycle, ScreenLifecycle screenLifecycle, LightRevealScrim lightRevealScrim, BroadcastDispatcher broadcastDispatcher, DozeParameters dozeParameters, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.context = context;
        this.pluginAODManagerLazy = lazy;
        this.screenOffAnimationController = screenOffAnimationController;
        this.biometricUnlockController = biometricUnlockController;
        this.keyguardStateController = keyguardStateController;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.screenLifecycle = screenLifecycle;
        this.lightRevealScrim = lightRevealScrim;
        this.broadcastDispatcher = broadcastDispatcher;
        this.dozeParameters = dozeParameters;
        this.settingsHelper = settingsHelper;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    public final boolean disableLightRevealAnimation() {
        int i;
        if (LsRune.COVER_VIRTUAL_DISPLAY && this.keyguardUpdateMonitor.isCoverClosed() && this.settingsHelper.isAODShown()) {
            return false;
        }
        if (LsRune.AOD_SUB_DISPLAY_COVER && this.isFolded) {
            return !this.settingsHelper.isAODShown();
        }
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        if (!((KeyguardStateControllerImpl) keyguardStateController).mShowing || ((KeyguardStateControllerImpl) keyguardStateController).mOccluded || (i = this.wakefulnessLifecycle.mLastWakeReason) == 103 || i == 9 || this.screenLifecycle.mScreenState != 2) {
            return true;
        }
        AODParameters aODParameters = this.dozeParameters.mAODParameters;
        return (aODParameters == null || aODParameters.mDozeUiState) ? false : true;
    }
}
