package com.android.systemui.dreams.complication.dagger;

import com.android.systemui.complication.Complication;
import com.android.systemui.dreams.complication.HideComplicationTouchHandler;
import com.android.systemui.touch.TouchInsetManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ComplicationComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        ComplicationComponent create(Complication.VisibilityController visibilityController, TouchInsetManager touchInsetManager);
    }

    HideComplicationTouchHandler getHideComplicationTouchHandler();
}
