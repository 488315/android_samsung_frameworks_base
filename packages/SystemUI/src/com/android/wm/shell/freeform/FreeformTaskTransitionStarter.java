package com.android.wm.shell.freeform;

import android.os.IBinder;
import android.window.WindowContainerTransaction;

/* loaded from: classes3.dex */
public interface FreeformTaskTransitionStarter {
    IBinder startMinimizeAllTransition(WindowContainerTransaction windowContainerTransaction, int i);

    IBinder startMinimizedModeTransition(int i, WindowContainerTransaction windowContainerTransaction, boolean z);

    IBinder startPipTransition(WindowContainerTransaction windowContainerTransaction);

    IBinder startRemoveTransition(WindowContainerTransaction windowContainerTransaction);

    void startWindowingModeTransition(WindowContainerTransaction windowContainerTransaction, int i);
}
