package com.android.systemui.statusbar.policy;

/* loaded from: classes3.dex */
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
