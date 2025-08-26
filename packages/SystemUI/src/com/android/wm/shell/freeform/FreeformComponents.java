package com.android.wm.shell.freeform;

import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.transition.Transitions;
import java.util.Optional;

/* loaded from: classes3.dex */
public class FreeformComponents {
    public final ShellTaskOrganizer.TaskListener mTaskListener;

    public FreeformComponents(ShellTaskOrganizer.TaskListener taskListener, Optional<Transitions.TransitionHandler> optional, Optional<Transitions.TransitionObserver> optional2, Optional<FreeformTaskTransitionStarterInitializer> optional3) {
        this.mTaskListener = taskListener;
    }
}
