package com.android.wm.shell.shared;

import android.window.RemoteTransition;
import android.window.TransitionFilter;
import com.android.systemui.display.data.repository.FocusedDisplayRepositoryImpl$focusedTask$1$listener$1;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public interface ShellTransitions {
    default void unregisterRemote(RemoteTransition remoteTransition) {
    }

    default void unsetFocusTransitionListener(FocusTransitionListener focusTransitionListener) {
    }

    default void registerRemote(TransitionFilter transitionFilter, RemoteTransition remoteTransition) {
    }

    default void setFocusTransitionListener(FocusedDisplayRepositoryImpl$focusedTask$1$listener$1 focusedDisplayRepositoryImpl$focusedTask$1$listener$1, Executor executor) {
    }
}
