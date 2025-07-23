package com.android.systemui.haptics.slider;

import android.os.VibrationAttributes;
import android.view.animation.AccelerateInterpolator;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.time.SystemClock;
import com.google.android.msdl.domain.MSDLPlayer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final SliderDragVelocityProvider velocityProvider;
    public final VibratorHelper vibratorHelper;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        VIBRATION_ATTRIBUTES_PIPELINING = new VibrationAttributes.Builder().setUsage(18).setFlags(8).build();
    }

    public SliderHapticFeedbackProvider(VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, SliderDragVelocityProvider sliderDragVelocityProvider, SliderHapticFeedbackConfig sliderHapticFeedbackConfig, SystemClock systemClock) {
        this.vibratorHelper = vibratorHelper;
        this.velocityProvider = sliderDragVelocityProvider;
        this.config = sliderHapticFeedbackConfig;
        this.clock = systemClock;
        this.velocityAccelerateInterpolator = new AccelerateInterpolator(sliderHapticFeedbackConfig.velocityInterpolatorFactor);
        this.positionAccelerateInterpolator = new AccelerateInterpolator(sliderHapticFeedbackConfig.progressInterpolatorFactor);
        this.dragTextureLastTime = systemClock.elapsedRealtime();
        this.dragTextureLastProgress = -1.0f;
        this.thresholdUntilNextDragCallMillis = (vibratorHelper.mVibrator.getPrimitiveDurations(8)[0] * sliderHapticFeedbackConfig.numberOfLowTicks) + sliderHapticFeedbackConfig.deltaMillisForDragInterval;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x004d, code lost:
    
        if (java.lang.Math.abs(r8 - ((float) java.lang.Math.rint(r8))) < 0.001f) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onProgress(float r12) {
        /*
            r11 = this;
            com.android.systemui.haptics.slider.SliderDragVelocityProvider r0 = r11.velocityProvider
            float r0 = r0.getTrackedVelocity()
            float r0 = java.lang.Math.abs(r0)
            com.android.systemui.util.time.SystemClock r1 = r11.clock
            long r1 = r1.elapsedRealtime()
            long r3 = r11.dragTextureLastTime
            long r3 = r1 - r3
            float r3 = (float) r3
            float r4 = r11.thresholdUntilNextDragCallMillis
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            r4 = 0
            if (r3 >= 0) goto L1e
            goto L8c
        L1e:
            float r3 = r11.dragTextureLastProgress
            float r3 = r12 - r3
            float r3 = java.lang.Math.abs(r3)
            com.android.systemui.haptics.slider.SliderHapticFeedbackConfig r5 = r11.config
            float r6 = r5.deltaProgressForDragThreshold
            int r3 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r3 >= 0) goto L2f
            goto L8c
        L2f:
            float r3 = r5.sliderStepSize
            r6 = 0
            int r7 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r7 <= 0) goto L4f
            int r8 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r8 > 0) goto L3b
            goto L8c
        L3b:
            float r8 = r12 / r3
            double r9 = (double) r8
            double r9 = java.lang.Math.rint(r9)
            float r9 = (float) r9
            float r8 = r8 - r9
            float r8 = java.lang.Math.abs(r8)
            r9 = 981668463(0x3a83126f, float:0.001)
            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r8 >= 0) goto L8c
        L4f:
            float r0 = r11.scaleOnDragTexture(r0, r12)
            int r3 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            com.android.systemui.statusbar.VibratorHelper r6 = r11.vibratorHelper
            if (r3 != 0) goto L74
            android.os.VibrationEffect$Composition r3 = android.os.VibrationEffect.startComposition()
            r7 = r4
        L5e:
            int r8 = r5.numberOfLowTicks
            if (r7 >= r8) goto L6a
            r8 = 8
            r3.addPrimitive(r8, r0)
            int r7 = r7 + 1
            goto L5e
        L6a:
            android.os.VibrationEffect r0 = r3.compose()
            android.os.VibrationAttributes r3 = com.android.systemui.haptics.slider.SliderHapticFeedbackProvider.VIBRATION_ATTRIBUTES_PIPELINING
            r6.vibrate(r0, r3)
            goto L88
        L74:
            if (r7 <= 0) goto L88
            android.os.VibrationEffect$Composition r3 = android.os.VibrationEffect.startComposition()
            r5 = 7
            android.os.VibrationEffect$Composition r0 = r3.addPrimitive(r5, r0)
            android.os.VibrationEffect r0 = r0.compose()
            android.os.VibrationAttributes r3 = com.android.systemui.haptics.slider.SliderHapticFeedbackProvider.VIBRATION_ATTRIBUTES_PIPELINING
            r6.vibrate(r0, r3)
        L88:
            r11.dragTextureLastTime = r1
            r11.dragTextureLastProgress = r12
        L8c:
            r11.hasVibratedAtUpperBookend = r4
            r11.hasVibratedAtLowerBookend = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.haptics.slider.SliderHapticFeedbackProvider.onProgress(float):void");
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

    public /* synthetic */ SliderHapticFeedbackProvider(VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, SliderDragVelocityProvider sliderDragVelocityProvider, SliderHapticFeedbackConfig sliderHapticFeedbackConfig, SystemClock systemClock, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(vibratorHelper, mSDLPlayer, sliderDragVelocityProvider, (i & 8) != 0 ? new SliderHapticFeedbackConfig(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0.0f, 0, 0.0f, 0.0f, 0.0f, 0.0f, null, 32767, null) : sliderHapticFeedbackConfig, systemClock);
    }
}
