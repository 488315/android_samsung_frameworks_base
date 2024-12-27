package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;

public interface FlashlightController extends CallbackController, Dumpable {

    public interface FlashlightListener {
        void onFlashlightAvailabilityChanged(boolean z);

        void onFlashlightChanged(boolean z);

        void onFlashlightError();
    }
}
