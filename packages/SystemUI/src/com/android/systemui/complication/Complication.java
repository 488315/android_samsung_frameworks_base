package com.android.systemui.complication;

import com.android.systemui.complication.DreamClockTimeComplication;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface Complication {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Host {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface VisibilityController {
    }

    DreamClockTimeComplication.DreamClockTimeViewHolder createView();

    default int getRequiredTypeAvailability() {
        return 0;
    }
}
