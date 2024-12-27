package com.android.systemui.qs.tileimpl;

import android.animation.ValueAnimator;
import android.os.VibrationEffect;
import android.util.Log;
import com.android.systemui.haptics.qs.LongPressHapticBuilder;
import com.android.systemui.haptics.qs.QSLongPressEffect;
import com.android.systemui.statusbar.VibratorHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSTileViewImpl$initLongPressEffectCallback$1 {
    public final /* synthetic */ QSTileViewImpl this$0;

    public QSTileViewImpl$initLongPressEffectCallback$1(QSTileViewImpl qSTileViewImpl) {
        this.this$0 = qSTileViewImpl;
    }

    public final void onReverseAnimator() {
        QSTileViewImpl qSTileViewImpl = this.this$0;
        ValueAnimator valueAnimator = qSTileViewImpl.longPressEffectAnimator;
        if (valueAnimator != null) {
            float animatedFraction = valueAnimator.getAnimatedFraction();
            QSLongPressEffect qSLongPressEffect = qSTileViewImpl.longPressEffect;
            if (qSLongPressEffect != null) {
                LongPressHapticBuilder longPressHapticBuilder = LongPressHapticBuilder.INSTANCE;
                int[] iArr = qSLongPressEffect.durations;
                int i = iArr != null ? iArr[0] : 0;
                int i2 = qSLongPressEffect.effectDuration;
                longPressHapticBuilder.getClass();
                float f = animatedFraction * i2;
                VibrationEffect vibrationEffect = null;
                if (f != 0.0f) {
                    if (i == 0) {
                        Log.d("LongPressHapticBuilder", "Cannot play reverse haptics because LOW_TICK is not supported");
                    } else {
                        int i3 = (int) (f / i);
                        if (i3 != 0) {
                            VibrationEffect.Composition startComposition = VibrationEffect.startComposition();
                            float f2 = 0.08f / i3;
                            for (int i4 = 0; i4 < i3; i4++) {
                                startComposition.addPrimitive(8, Math.max(0.08f - (i4 * f2), 0.0f), 0);
                            }
                            vibrationEffect = startComposition.compose();
                        }
                    }
                }
                VibratorHelper vibratorHelper = qSLongPressEffect.vibratorHelper;
                if (vibratorHelper != null) {
                    vibratorHelper.cancel();
                }
                if (vibratorHelper != null && vibrationEffect != null) {
                    vibratorHelper.vibrate(vibrationEffect);
                }
            }
            valueAnimator.reverse();
        }
    }
}
