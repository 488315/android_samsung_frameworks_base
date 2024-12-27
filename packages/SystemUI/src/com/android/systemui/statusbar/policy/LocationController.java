package com.android.systemui.statusbar.policy;

public interface LocationController extends CallbackController {

    public interface LocationChangeCallback {
        default void onLocationActiveChanged(boolean z) {
        }

        default void onLocationSettingsChanged(boolean z) {
        }
    }
}
