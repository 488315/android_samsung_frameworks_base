package com.android.systemui.statusbar.policy;

import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class ConfigurationControllerExtKt {
    public static final Flow getOnConfigChanged(ConfigurationController configurationController) {
        return FlowConflatedKt.conflatedCallbackFlow(new ConfigurationControllerExtKt$onConfigChanged$1(configurationController, null));
    }

    public static final Flow getOnDensityOrFontScaleChanged(ConfigurationController configurationController) {
        return FlowConflatedKt.conflatedCallbackFlow(new ConfigurationControllerExtKt$onDensityOrFontScaleChanged$1(configurationController, null));
    }
}
