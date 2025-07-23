package com.android.wm.shell.freeform;

import android.os.IBinder;
import android.window.WindowContainerTransaction;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface FreeformTaskTransitionStarter {
    IBinder startMinimizeAllTransition(WindowContainerTransaction windowContainerTransaction, int i);

    IBinder startMinimizedModeTransition(int i, WindowContainerTransaction windowContainerTransaction, boolean z);

    IBinder startPipTransition(WindowContainerTransaction windowContainerTransaction);

    IBinder startRemoveTransition(WindowContainerTransaction windowContainerTransaction);

    void startWindowingModeTransition(WindowContainerTransaction windowContainerTransaction, int i);
}
