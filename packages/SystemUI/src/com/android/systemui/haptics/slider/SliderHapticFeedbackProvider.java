package com.android.systemui.haptics.slider;

import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.view.VelocityTracker;
import android.view.animation.AccelerateInterpolator;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.VibratorHelper$$ExternalSyntheticLambda1;
import com.android.systemui.util.time.SystemClock;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SliderHapticFeedbackProvider implements SliderStateListener {
    public static final VibrationAttributes VIBRATION_ATTRIBUTES_PIPELINING;
    public final SystemClock clock;
    public final SliderHapticFeedbackConfig config;
    public float dragTextureLastProgress;
    public long dragTextureLastTime;
    public boolean hasVibratedAtLowerBookend;
    public boolean hasVibratedAtUpperBookend;
    public final AccelerateInterpolator positionAccelerateInterpolator;
    public final float thresholdUntilNextDragCallMillis;
    public final AccelerateInterpolator velocityAccelerateInterpolator;
    public final VelocityTracker velocityTracker;
    public final VibratorHelper vibratorHelper;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        VIBRATION_ATTRIBUTES_PIPELINING = new VibrationAttributes.Builder().setUsage(18).setFlags(8).build();
    }

    public SliderHapticFeedbackProvider(VibratorHelper vibratorHelper, VelocityTracker velocityTracker, SliderHapticFeedbackConfig sliderHapticFeedbackConfig, SystemClock systemClock) {
        this.vibratorHelper = vibratorHelper;
        this.velocityTracker = velocityTracker;
        this.config = sliderHapticFeedbackConfig;
        this.clock = systemClock;
        this.velocityAccelerateInterpolator = new AccelerateInterpolator(sliderHapticFeedbackConfig.velocityInterpolatorFactor);
        this.positionAccelerateInterpolator = new AccelerateInterpolator(sliderHapticFeedbackConfig.progressInterpolatorFactor);
        this.dragTextureLastTime = systemClock.elapsedRealtime();
        this.dragTextureLastProgress = -1.0f;
        this.thresholdUntilNextDragCallMillis = (vibratorHelper.mVibrator.getPrimitiveDurations(8)[0] * sliderHapticFeedbackConfig.numberOfLowTicks) + sliderHapticFeedbackConfig.deltaMillisForDragInterval;
    }

    public final float getTrackedVelocity() {
        VelocityTracker velocityTracker = this.velocityTracker;
        SliderHapticFeedbackConfig sliderHapticFeedbackConfig = this.config;
        velocityTracker.computeCurrentVelocity(1000, sliderHapticFeedbackConfig.maxVelocityToScale);
        if (this.velocityTracker.isAxisSupported(sliderHapticFeedbackConfig.velocityAxis)) {
            return this.velocityTracker.getAxisVelocity(sliderHapticFeedbackConfig.velocityAxis);
        }
        return 0.0f;
    }

    public final void onProgress(float f) {
        float abs = Math.abs(getTrackedVelocity());
        long elapsedRealtime = this.clock.elapsedRealtime();
        if (elapsedRealtime - this.dragTextureLastTime >= this.thresholdUntilNextDragCallMillis) {
            float abs2 = Math.abs(f - this.dragTextureLastProgress);
            SliderHapticFeedbackConfig sliderHapticFeedbackConfig = this.config;
            if (abs2 >= sliderHapticFeedbackConfig.deltaProgressForDragThreshold) {
                float scaleOnDragTexture = scaleOnDragTexture(abs, f);
                VibrationEffect.Composition startComposition = VibrationEffect.startComposition();
                for (int i = 0; i < sliderHapticFeedbackConfig.numberOfLowTicks; i++) {
                    startComposition.addPrimitive(8, scaleOnDragTexture);
                }
                VibrationEffect compose = startComposition.compose();
                VibrationAttributes vibrationAttributes = VIBRATION_ATTRIBUTES_PIPELINING;
                VibratorHelper vibratorHelper = this.vibratorHelper;
                if (vibratorHelper.hasVibrator()) {
                    vibratorHelper.mExecutor.execute(new VibratorHelper$$ExternalSyntheticLambda1(vibratorHelper, compose, vibrationAttributes));
                }
                this.dragTextureLastTime = elapsedRealtime;
                this.dragTextureLastProgress = f;
            }
        }
        this.hasVibratedAtUpperBookend = false;
        this.hasVibratedAtLowerBookend = false;
    }

    public final float scaleOnDragTexture(float f, float f2) {
        float interpolation = this.velocityAccelerateInterpolator.getInterpolation(Math.min(f / this.config.maxVelocityToScale, 1.0f));
        return (float) Math.pow((interpolation * r1.additionalVelocityMaxBump) + (this.positionAccelerateInterpolator.getInterpolation(f2) * (r1.progressBasedDragMaxScale - r1.progressBasedDragMinScale)) + r1.progressBasedDragMinScale, r1.exponent);
    }

    public final float scaleOnEdgeCollision(float f) {
        AccelerateInterpolator accelerateInterpolator = this.velocityAccelerateInterpolator;
        SliderHapticFeedbackConfig sliderHapticFeedbackConfig = this.config;
        float interpolation = accelerateInterpolator.getInterpolation(Math.min(f / sliderHapticFeedbackConfig.maxVelocityToScale, 1.0f));
        float f2 = sliderHapticFeedbackConfig.upperBookendScale;
        float f3 = sliderHapticFeedbackConfig.lowerBookendScale;
        return (float) Math.pow(DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f2, f3, interpolation, f3), sliderHapticFeedbackConfig.exponent);
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
        	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
        */
    public /* synthetic */ SliderHapticFeedbackProvider(com.android.systemui.statusbar.VibratorHelper r18, android.view.VelocityTracker r19, com.android.systemui.haptics.slider.SliderHapticFeedbackConfig r20, com.android.systemui.util.time.SystemClock r21, int r22, kotlin.jvm.internal.DefaultConstructorMarker r23) {
        /*
            r17 = this;
            r0 = r22 & 4
            if (r0 == 0) goto L24
            com.android.systemui.haptics.slider.SliderHapticFeedbackConfig r0 = new com.android.systemui.haptics.slider.SliderHapticFeedbackConfig
            r15 = 8191(0x1fff, float:1.1478E-41)
            r16 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r1 = r0
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)
            r1 = r17
            r2 = r18
            r3 = r19
        L21:
            r4 = r21
            goto L2d
        L24:
            r1 = r17
            r2 = r18
            r3 = r19
            r0 = r20
            goto L21
        L2d:
            r1.<init>(r2, r3, r0, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.haptics.slider.SliderHapticFeedbackProvider.<init>(com.android.systemui.statusbar.VibratorHelper, android.view.VelocityTracker, com.android.systemui.haptics.slider.SliderHapticFeedbackConfig, com.android.systemui.util.time.SystemClock, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
