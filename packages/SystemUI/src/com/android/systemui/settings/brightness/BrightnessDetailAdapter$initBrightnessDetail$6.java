package com.android.systemui.settings.brightness;

import android.view.ViewGroup;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
