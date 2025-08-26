package com.android.systemui.statusbar.policy;

/* loaded from: classes3.dex */
public interface LocationController extends CallbackController {

    public interface LocationChangeCallback {
        default void onLocationActiveChanged(boolean z) {
        }

        default void onLocationSettingsChanged(boolean z) {
        }
    }
}
