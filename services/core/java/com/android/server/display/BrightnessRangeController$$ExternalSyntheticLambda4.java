package com.android.server.display;

import com.android.server.display.brightness.clamper.BrightnessClamperController;

public final /* synthetic */ class BrightnessRangeController$$ExternalSyntheticLambda4
        implements BrightnessClamperController.ClamperChangeListener,
                HighBrightnessModeController.HdrBrightnessDeviceConfig {
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BrightnessRangeController$$ExternalSyntheticLambda4(Object obj) {
        this.f$0 = obj;
    }

    @Override // com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig
    public float getHdrBrightnessFromSdr(float f, float f2) {
        return ((DisplayDeviceConfig) this.f$0).getHdrBrightnessFromSdr(f);
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener
    public void onChanged() {
        ((Runnable) this.f$0).run();
    }
}
