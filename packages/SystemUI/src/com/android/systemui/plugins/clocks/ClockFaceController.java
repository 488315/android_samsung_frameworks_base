package com.android.systemui.plugins.clocks;

import android.view.View;

public interface ClockFaceController {
    ClockAnimations getAnimations();

    ClockFaceConfig getConfig();

    ClockFaceEvents getEvents();

    ClockFaceLayout getLayout();

    View getView();
}
