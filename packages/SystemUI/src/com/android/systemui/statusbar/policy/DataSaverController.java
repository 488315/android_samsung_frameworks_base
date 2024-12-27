package com.android.systemui.statusbar.policy;

public interface DataSaverController extends CallbackController {

    public interface Listener {
        void onDataSaverChanged(boolean z);
    }
}
