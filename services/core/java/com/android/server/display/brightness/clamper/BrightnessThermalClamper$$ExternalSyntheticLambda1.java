package com.android.server.display.brightness.clamper;

import com.android.server.display.brightness.clamper.BrightnessThermalClamper;
import com.android.server.display.utils.DeviceConfigParsingUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BrightnessThermalClamper$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BrightnessThermalClamper f$0;
    public final /* synthetic */ BrightnessThermalClamper.ThermalData f$1;

    public /* synthetic */ BrightnessThermalClamper$$ExternalSyntheticLambda1(BrightnessThermalClamper brightnessThermalClamper, BrightnessThermalClamper.ThermalData thermalData, int i) {
        this.$r8$classId = i;
        this.f$0 = brightnessThermalClamper;
        this.f$1 = thermalData;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BrightnessThermalClamper brightnessThermalClamper = this.f$0;
                brightnessThermalClamper.setDisplayData(this.f$1);
                brightnessThermalClamper.mThermalThrottlingDataOverride = DeviceConfigParsingUtils.parseDeviceConfigMap(brightnessThermalClamper.mConfigParameterProvider.mDeviceConfig.getString("display_manager", "brightness_throttling_data", (String) null), brightnessThermalClamper.mDataPointMapper, brightnessThermalClamper.mDataSetMapper);
                break;
            default:
                BrightnessThermalClamper brightnessThermalClamper2 = this.f$0;
                brightnessThermalClamper2.setDisplayData(this.f$1);
                brightnessThermalClamper2.recalculateActiveData$1();
                break;
        }
    }
}
