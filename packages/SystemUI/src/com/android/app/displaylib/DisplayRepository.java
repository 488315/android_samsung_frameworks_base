package com.android.app.displaylib;

import android.view.Display;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes.dex */
public interface DisplayRepository {

    public interface PendingDisplay {
    }

    Flow getDefaultDisplayOff();

    Display getDisplay(int i);

    Flow getDisplayAdditionEvent();

    Flow getDisplayChangeEvent();

    StateFlow getDisplayIds();

    Flow getDisplayRemovalEvent();

    StateFlow getDisplays();

    Flow getPendingDisplay();
}
