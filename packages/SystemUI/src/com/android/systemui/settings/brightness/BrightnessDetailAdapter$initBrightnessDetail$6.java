package com.android.systemui.settings.brightness;

import android.view.ViewGroup;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
