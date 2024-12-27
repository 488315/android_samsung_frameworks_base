package com.android.server.wm;

import android.animation.ValueAnimator;
import android.util.Slog;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DragState$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ValueAnimator valueAnimator = (ValueAnimator) obj;
                try {
                    valueAnimator.removeAllUpdateListeners();
                    valueAnimator.removeAllListeners();
                    valueAnimator.cancel();
                    break;
                } catch (Exception e) {
                    Slog.w("WindowManager", "Unable to cancel animator", e);
                    return;
                }
            case 1:
                ((DisplayContent) obj).mDisplayRotation.mDeferredRotationPauseCount++;
                break;
            default:
                DisplayRotation displayRotation = ((DisplayContent) obj).mDisplayRotation;
                int i = displayRotation.mDeferredRotationPauseCount;
                if (i > 0) {
                    int i2 = i - 1;
                    displayRotation.mDeferredRotationPauseCount = i2;
                    if (i2 == 0) {
                        displayRotation.updateRotationAndSendNewConfigIfChanged();
                        break;
                    }
                }
                break;
        }
    }
}
