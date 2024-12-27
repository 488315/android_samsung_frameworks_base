package com.android.server.display.brightness.clamper;

import com.android.server.display.DisplayDeviceConfig;
import com.android.server.display.brightness.clamper.BrightnessPowerClamper;
import com.android.server.display.utils.DeviceConfigParsingUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BrightnessPowerClamper$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BrightnessPowerClamper f$0;
    public final /* synthetic */ BrightnessPowerClamper.PowerData f$1;

    public /* synthetic */ BrightnessPowerClamper$$ExternalSyntheticLambda3(BrightnessPowerClamper brightnessPowerClamper, BrightnessPowerClamper.PowerData powerData, int i) {
        this.$r8$classId = i;
        this.f$0 = brightnessPowerClamper;
        this.f$1 = powerData;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BrightnessPowerClamper brightnessPowerClamper = this.f$0;
                brightnessPowerClamper.setDisplayData(this.f$1);
                brightnessPowerClamper.mPowerThrottlingDataOverride = DeviceConfigParsingUtils.parseDeviceConfigMap(brightnessPowerClamper.mConfigParameterProvider.mDeviceConfig.getString("display_manager", "power_throttling_data", (String) null), brightnessPowerClamper.mDataPointMapper, brightnessPowerClamper.mDataSetMapper);
                DisplayDeviceConfig.PowerThrottlingConfigData powerThrottlingConfigData = brightnessPowerClamper.mPowerThrottlingConfigData;
                if (powerThrottlingConfigData != null) {
                    BrightnessPowerClamper$$ExternalSyntheticLambda4 brightnessPowerClamper$$ExternalSyntheticLambda4 = new BrightnessPowerClamper$$ExternalSyntheticLambda4(brightnessPowerClamper);
                    brightnessPowerClamper.mInjector.getClass();
                    PmicMonitor pmicMonitor = new PmicMonitor(brightnessPowerClamper$$ExternalSyntheticLambda4, powerThrottlingConfigData.pollingWindowMillis);
                    brightnessPowerClamper.mPmicMonitor = pmicMonitor;
                    pmicMonitor.start();
                    break;
                }
                break;
            default:
                BrightnessPowerClamper brightnessPowerClamper2 = this.f$0;
                brightnessPowerClamper2.setDisplayData(this.f$1);
                brightnessPowerClamper2.recalculateActiveData();
                break;
        }
    }
}
