package com.android.systemui.statusbar.policy;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class ConfigurationControllerExtKt {
    public static final Flow getOnConfigChanged(ConfigurationController configurationController) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        ConfigurationControllerExtKt$onConfigChanged$1 configurationControllerExtKt$onConfigChanged$1 = new ConfigurationControllerExtKt$onConfigChanged$1(configurationController, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(configurationControllerExtKt$onConfigChanged$1);
    }

    public static final Flow getOnDensityOrFontScaleChanged(ConfigurationController configurationController) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        ConfigurationControllerExtKt$onDensityOrFontScaleChanged$1 configurationControllerExtKt$onDensityOrFontScaleChanged$1 = new ConfigurationControllerExtKt$onDensityOrFontScaleChanged$1(configurationController, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(configurationControllerExtKt$onDensityOrFontScaleChanged$1);
    }

    public static final Flow getOnThemeChanged(ConfigurationController configurationController) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        ConfigurationControllerExtKt$onThemeChanged$1 configurationControllerExtKt$onThemeChanged$1 = new ConfigurationControllerExtKt$onThemeChanged$1(configurationController, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(configurationControllerExtKt$onThemeChanged$1);
    }
}
