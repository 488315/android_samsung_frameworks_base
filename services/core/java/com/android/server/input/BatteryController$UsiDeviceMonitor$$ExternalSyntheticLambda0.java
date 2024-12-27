package com.android.server.input;

import android.hardware.input.IInputDeviceBatteryState;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BatteryController$UsiDeviceMonitor$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryController.UsiDeviceMonitor f$0;

    public /* synthetic */ BatteryController$UsiDeviceMonitor$$ExternalSyntheticLambda0(
            BatteryController.UsiDeviceMonitor usiDeviceMonitor, int i) {
        this.$r8$classId = i;
        this.f$0 = usiDeviceMonitor;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        BatteryController.UsiDeviceMonitor usiDeviceMonitor = this.f$0;
        Long l = (Long) obj;
        switch (i) {
            case 0:
                if (usiDeviceMonitor.mValidityTimeoutCallback != null
                        || ((IInputDeviceBatteryState) usiDeviceMonitor.mState).capacity
                                != FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    usiDeviceMonitor.markUsiBatteryValid();
                    break;
                }
                break;
            case 1:
                usiDeviceMonitor.getClass();
                usiDeviceMonitor.updateBatteryStateFromNative(l.longValue());
                usiDeviceMonitor.markUsiBatteryValid();
                break;
            default:
                BatteryController$UsiDeviceMonitor$$ExternalSyntheticLambda2
                        batteryController$UsiDeviceMonitor$$ExternalSyntheticLambda2 =
                                usiDeviceMonitor.mValidityTimeoutCallback;
                if (batteryController$UsiDeviceMonitor$$ExternalSyntheticLambda2 != null) {
                    BatteryController.this.mHandler.removeCallbacks(
                            batteryController$UsiDeviceMonitor$$ExternalSyntheticLambda2);
                    usiDeviceMonitor.mValidityTimeoutCallback = null;
                    break;
                }
                break;
        }
    }
}
