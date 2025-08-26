package com.android.systemui.unfold.updates.screen;

import com.android.systemui.unfold.util.CallbackController;

/* loaded from: classes3.dex */
public interface ScreenStatusProvider extends CallbackController {

    public interface ScreenListener {
        void onScreenTurnedOn();

        void onScreenTurningOff();

        void onScreenTurningOn();
    }
}
