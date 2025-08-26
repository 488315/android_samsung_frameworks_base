package com.android.systemui.haptics.slider;

import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.view.animation.AccelerateInterpolator;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.time.SystemClock;
import com.google.android.msdl.domain.MSDLPlayer;
import kotlin.jvm.internal.DefaultConstructorMarker;

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

    /* JADX WARN: Removed duplicated region for block: B:15:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onProgress(float f) {
        float fAbs = Math.abs(this.velocityProvider.getTrackedVelocity());
        long jElapsedRealtime = this.clock.elapsedRealtime();
        if (jElapsedRealtime - this.dragTextureLastTime >= this.thresholdUntilNextDragCallMillis) {
            float fAbs2 = Math.abs(f - this.dragTextureLastProgress);
            SliderHapticFeedbackConfig sliderHapticFeedbackConfig = this.config;
            if (fAbs2 >= sliderHapticFeedbackConfig.deltaProgressForDragThreshold) {
                float f2 = sliderHapticFeedbackConfig.sliderStepSize;
                if (f2 <= 0.0f) {
                    float fScaleOnDragTexture = scaleOnDragTexture(fAbs, f);
                    VibratorHelper vibratorHelper = this.vibratorHelper;
                    if (f2 == 0.0f) {
                        VibrationEffect.Composition compositionStartComposition = VibrationEffect.startComposition();
                        for (int i = 0; i < sliderHapticFeedbackConfig.numberOfLowTicks; i++) {
                            compositionStartComposition.addPrimitive(8, fScaleOnDragTexture);
                        }
                        vibratorHelper.vibrate(compositionStartComposition.compose(), VIBRATION_ATTRIBUTES_PIPELINING);
                    } else if (f2 > 0.0f) {
                        vibratorHelper.vibrate(VibrationEffect.startComposition().addPrimitive(7, fScaleOnDragTexture).compose(), VIBRATION_ATTRIBUTES_PIPELINING);
                    }
                    this.dragTextureLastTime = jElapsedRealtime;
                    this.dragTextureLastProgress = f;
                } else if (f2 > 0.0f) {
                    float f3 = f / f2;
                    if (Math.abs(f3 - ((float) Math.rint(f3))) < 0.001f) {
                    }
                }
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

    public /* synthetic */ SliderHapticFeedbackProvider(VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, SliderDragVelocityProvider sliderDragVelocityProvider, SliderHapticFeedbackConfig sliderHapticFeedbackConfig, SystemClock systemClock, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(vibratorHelper, mSDLPlayer, sliderDragVelocityProvider, (i & 8) != 0 ? new SliderHapticFeedbackConfig(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0.0f, 0, 0.0f, 0.0f, 0.0f, 0.0f, null, 32767, null) : sliderHapticFeedbackConfig, systemClock);
    }
}
