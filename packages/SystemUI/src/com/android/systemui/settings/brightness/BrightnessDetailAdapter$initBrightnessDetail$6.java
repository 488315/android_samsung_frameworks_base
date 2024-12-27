package com.android.systemui.settings.brightness;

import android.view.ViewGroup;
import java.util.function.Function;

public final class BrightnessDetailAdapter$initBrightnessDetail$6 implements Function {
    public final /* synthetic */ BrightnessDetailAdapter this$0;

    public BrightnessDetailAdapter$initBrightnessDetail$6(BrightnessDetailAdapter brightnessDetailAdapter) {
        this.this$0 = brightnessDetailAdapter;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return BrightnessDetailAdapter.access$addDividerView(this.this$0, (ViewGroup) obj);
    }
}
