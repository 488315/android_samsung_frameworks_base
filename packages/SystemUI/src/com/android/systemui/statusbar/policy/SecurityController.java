package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;

public interface SecurityController extends CallbackController, Dumpable {

    public interface SecurityControllerCallback {
        void onStateChanged();
    }
}
