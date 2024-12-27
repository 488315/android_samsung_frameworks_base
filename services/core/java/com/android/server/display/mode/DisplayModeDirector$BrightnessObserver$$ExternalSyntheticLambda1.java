package com.android.server.display.mode;

import com.android.server.display.DisplayDeviceConfig;
import java.util.concurrent.Callable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda1 implements Callable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayDeviceConfig f$0;

    public /* synthetic */ DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda1(DisplayDeviceConfig displayDeviceConfig, int i) {
        this.$r8$classId = i;
        this.f$0 = displayDeviceConfig;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        switch (this.$r8$classId) {
            case 0:
                return this.f$0.mHighDisplayBrightnessThresholds;
            case 1:
                return this.f$0.mHighDisplayBrightnessThresholds;
            case 2:
                return this.f$0.mHighAmbientBrightnessThresholds;
            case 3:
                return this.f$0.mLowDisplayBrightnessThresholds;
            case 4:
                return this.f$0.mLowAmbientBrightnessThresholds;
            case 5:
                return this.f$0.mHighAmbientBrightnessThresholds;
            case 6:
                return this.f$0.mLowDisplayBrightnessThresholds;
            default:
                return this.f$0.mLowAmbientBrightnessThresholds;
        }
    }
}
