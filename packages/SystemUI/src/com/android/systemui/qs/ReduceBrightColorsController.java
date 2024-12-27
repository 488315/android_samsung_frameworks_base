package com.android.systemui.qs;

import com.android.systemui.statusbar.policy.CallbackController;

public interface ReduceBrightColorsController extends CallbackController {

    public interface Listener {
        void onActivated(boolean z);
    }
}
