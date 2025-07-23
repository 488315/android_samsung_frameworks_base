package com.android.wm.shell.freeform;

import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.transition.Transitions;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class FreeformComponents {
    public final ShellTaskOrganizer.TaskListener mTaskListener;

    public FreeformComponents(ShellTaskOrganizer.TaskListener taskListener, Optional<Transitions.TransitionHandler> optional, Optional<Transitions.TransitionObserver> optional2, Optional<FreeformTaskTransitionStarterInitializer> optional3) {
        this.mTaskListener = taskListener;
    }
}
