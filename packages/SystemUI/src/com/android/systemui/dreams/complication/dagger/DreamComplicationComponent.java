package com.android.systemui.dreams.complication.dagger;

import com.android.systemui.complication.Complication;
import com.android.systemui.touch.TouchInsetManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface DreamComplicationComponent {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        DreamComplicationComponent create(Complication.VisibilityController visibilityController, TouchInsetManager touchInsetManager);
    }
}
