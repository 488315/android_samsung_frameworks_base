package com.android.systemui.settings.brightness;

import com.android.systemui.Dependency;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.settings.brightness.SecBrightnessSliderController;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class SecBrightnessSliderController$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        SecBrightnessSliderController.Companion companion = SecBrightnessSliderController.Companion;
        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
    }
}
