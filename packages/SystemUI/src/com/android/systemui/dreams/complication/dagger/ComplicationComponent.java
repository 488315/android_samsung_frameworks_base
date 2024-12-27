package com.android.systemui.dreams.complication.dagger;

import com.android.systemui.complication.Complication;
import com.android.systemui.dreams.complication.HideComplicationTouchHandler;
import com.android.systemui.touch.TouchInsetManager;

public interface ComplicationComponent {

    public interface Factory {
        ComplicationComponent create(Complication.VisibilityController visibilityController, TouchInsetManager touchInsetManager);
    }

    HideComplicationTouchHandler getHideComplicationTouchHandler();
}
