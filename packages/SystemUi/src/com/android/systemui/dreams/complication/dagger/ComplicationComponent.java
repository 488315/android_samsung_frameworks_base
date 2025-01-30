package com.android.systemui.dreams.complication.dagger;

import com.android.systemui.complication.Complication;
import com.android.systemui.dreams.complication.HideComplicationTouchHandler;
import com.android.systemui.touch.TouchInsetManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface ComplicationComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        ComplicationComponent create(Complication.VisibilityController visibilityController, TouchInsetManager touchInsetManager);
    }

    HideComplicationTouchHandler getHideComplicationTouchHandler();
}
