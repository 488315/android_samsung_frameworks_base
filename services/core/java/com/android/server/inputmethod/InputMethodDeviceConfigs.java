package com.android.server.inputmethod;

import android.app.ActivityThread;
import android.provider.DeviceConfig;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class InputMethodDeviceConfigs {
    public final InputMethodDeviceConfigs$$ExternalSyntheticLambda0 mDeviceConfigChangedListener;
    public boolean mHideImeWhenNoEditorFocus;

    /* JADX WARN: Type inference failed for: r0v0, types: [android.provider.DeviceConfig$OnPropertiesChangedListener, com.android.server.inputmethod.InputMethodDeviceConfigs$$ExternalSyntheticLambda0] */
    public InputMethodDeviceConfigs() {
        ?? r0 = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.inputmethod.InputMethodDeviceConfigs$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                InputMethodDeviceConfigs inputMethodDeviceConfigs = InputMethodDeviceConfigs.this;
                inputMethodDeviceConfigs.getClass();
                if ("input_method_manager".equals(properties.getNamespace())) {
                    for (String str : properties.getKeyset()) {
                        if ("hide_ime_when_no_editor_focus".equals(str)) {
                            inputMethodDeviceConfigs.mHideImeWhenNoEditorFocus = properties.getBoolean(str, true);
                        }
                    }
                }
            }
        };
        this.mDeviceConfigChangedListener = r0;
        this.mHideImeWhenNoEditorFocus = DeviceConfig.getBoolean("input_method_manager", "hide_ime_when_no_editor_focus", true);
        DeviceConfig.addOnPropertiesChangedListener("input_method_manager", ActivityThread.currentApplication().getMainExecutor(), (DeviceConfig.OnPropertiesChangedListener) r0);
    }

    public void destroy() {
        DeviceConfig.removeOnPropertiesChangedListener(this.mDeviceConfigChangedListener);
    }
}
