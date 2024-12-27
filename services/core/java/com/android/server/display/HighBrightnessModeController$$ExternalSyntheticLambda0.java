package com.android.server.display;

import android.os.Trace;

public final /* synthetic */ class HighBrightnessModeController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ HighBrightnessModeController$$ExternalSyntheticLambda0(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((HighBrightnessModeController) obj).recalculateTimeAllowance();
                break;
            default:
                HighBrightnessModeController.HdrListener hdrListener =
                        (HighBrightnessModeController.HdrListener) obj;
                int i2 = HighBrightnessModeController.HdrListener.$r8$clinit;
                hdrListener.getClass();
                Trace.traceBegin(131072L, "HBMController#onHdrInfoChanged");
                HighBrightnessModeController highBrightnessModeController = hdrListener.this$0;
                highBrightnessModeController.mIsHdrLayerPresent = hdrListener.mIsBrightnessScaledUp;
                highBrightnessModeController.onBrightnessChanged(
                        highBrightnessModeController.mThrottlingReason,
                        highBrightnessModeController.mBrightness,
                        highBrightnessModeController.mUnthrottledBrightness);
                Trace.traceEnd(131072L);
                break;
        }
    }
}
