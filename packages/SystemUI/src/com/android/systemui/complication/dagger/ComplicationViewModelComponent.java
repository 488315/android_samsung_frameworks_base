package com.android.systemui.complication.dagger;

import com.android.systemui.complication.Complication;
import com.android.systemui.complication.ComplicationId;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface ComplicationViewModelComponent {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        ComplicationViewModelComponent create(Complication complication, ComplicationId complicationId);
    }
}
