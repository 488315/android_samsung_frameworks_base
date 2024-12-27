package com.android.systemui.statusbar.connectivity;

import com.android.systemui.demomode.DemoMode;
import com.android.systemui.statusbar.policy.CallbackController;

public interface NetworkController extends CallbackController, DemoMode {

    public interface EmergencyListener {
    }
}
