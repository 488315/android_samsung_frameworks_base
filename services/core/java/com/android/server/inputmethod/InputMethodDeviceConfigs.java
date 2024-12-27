package com.android.server.inputmethod;

import android.app.ActivityThread;
import android.provider.DeviceConfig;

public final class InputMethodDeviceConfigs {
    public final InputMethodDeviceConfigs$$ExternalSyntheticLambda0 mDeviceConfigChangedListener;
    public boolean mHideImeWhenNoEditorFocus;

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
