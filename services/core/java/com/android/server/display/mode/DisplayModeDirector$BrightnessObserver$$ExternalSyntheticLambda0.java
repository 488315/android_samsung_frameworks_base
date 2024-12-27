package com.android.server.display.mode;

import com.android.server.display.mode.DisplayModeDirector;
import com.android.server.display.utils.DeviceConfigParsingUtils;
import java.util.concurrent.Callable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda0 implements Callable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayModeDirector.BrightnessObserver f$0;

    public /* synthetic */ DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda0(DisplayModeDirector.BrightnessObserver brightnessObserver, int i) {
        this.$r8$classId = i;
        this.f$0 = brightnessObserver;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        switch (this.$r8$classId) {
            case 0:
                return DeviceConfigParsingUtils.displayBrightnessThresholdsIntToFloat(this.f$0.this$0.mConfigParameterProvider.getIntArrayProperty("peak_refresh_rate_brightness_thresholds"));
            case 1:
                return DeviceConfigParsingUtils.displayBrightnessThresholdsIntToFloat(this.f$0.this$0.mConfigParameterProvider.getIntArrayProperty("fixed_refresh_rate_high_display_brightness_thresholds"));
            case 2:
                return DeviceConfigParsingUtils.ambientBrightnessThresholdsIntToFloat(this.f$0.this$0.mConfigParameterProvider.getIntArrayProperty("fixed_refresh_rate_high_ambient_brightness_thresholds"));
            case 3:
                return DeviceConfigParsingUtils.displayBrightnessThresholdsIntToFloat(this.f$0.this$0.mConfigParameterProvider.getIntArrayProperty("peak_refresh_rate_brightness_thresholds"));
            case 4:
                return DeviceConfigParsingUtils.ambientBrightnessThresholdsIntToFloat(this.f$0.this$0.mConfigParameterProvider.getIntArrayProperty("peak_refresh_rate_ambient_thresholds"));
            case 5:
                return DeviceConfigParsingUtils.ambientBrightnessThresholdsIntToFloat(this.f$0.this$0.mConfigParameterProvider.getIntArrayProperty("fixed_refresh_rate_high_ambient_brightness_thresholds"));
            case 6:
                return DeviceConfigParsingUtils.displayBrightnessThresholdsIntToFloat(this.f$0.this$0.mConfigParameterProvider.getIntArrayProperty("peak_refresh_rate_brightness_thresholds"));
            default:
                return DeviceConfigParsingUtils.ambientBrightnessThresholdsIntToFloat(this.f$0.this$0.mConfigParameterProvider.getIntArrayProperty("peak_refresh_rate_ambient_thresholds"));
        }
    }
}
