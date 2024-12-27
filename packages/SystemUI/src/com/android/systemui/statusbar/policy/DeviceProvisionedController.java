package com.android.systemui.statusbar.policy;

public interface DeviceProvisionedController extends CallbackController {

    public interface DeviceProvisionedListener {
        default void onUserSwitched() {
            onUserSetupChanged();
        }

        default void onDeviceProvisionedChanged() {
        }

        default void onUserSetupChanged() {
        }
    }
}
