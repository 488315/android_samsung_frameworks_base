package com.android.systemui.complication;

import com.android.systemui.complication.DreamClockTimeComplication;

/* loaded from: classes2.dex */
public interface Complication {

    public interface Host {
    }

    public interface VisibilityController {
    }

    DreamClockTimeComplication.DreamClockTimeViewHolder createView();

    default int getRequiredTypeAvailability() {
        return 0;
    }
}
