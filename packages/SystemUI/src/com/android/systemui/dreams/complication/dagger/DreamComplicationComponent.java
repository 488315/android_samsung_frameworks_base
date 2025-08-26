package com.android.systemui.dreams.complication.dagger;

import com.android.systemui.complication.Complication;
import com.android.systemui.touch.TouchInsetManager;

/* loaded from: classes2.dex */
public interface DreamComplicationComponent {

    public interface Factory {
        DreamComplicationComponent create(Complication.VisibilityController visibilityController, TouchInsetManager touchInsetManager);
    }
}
