package com.android.systemui.unfold.updates.hinge;

import com.android.systemui.unfold.util.CallbackController;

public interface HingeAngleProvider extends CallbackController {
    void start();

    void stop();
}
