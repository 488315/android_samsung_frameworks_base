package com.android.wm.shell.unfold;

import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface ShellUnfoldProgressProvider {
    public static final AnonymousClass1 NO_PROVIDER = new ShellUnfoldProgressProvider() { // from class: com.android.wm.shell.unfold.ShellUnfoldProgressProvider.1
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface UnfoldListener {
        void onStateChangeFinished();

        void onStateChangeProgress(float f);

        default void onFoldStateChanged(boolean z) {
        }

        default void onStateChangeStarted() {
        }
    }

    default void addListener(Executor executor, UnfoldListener unfoldListener) {
    }
}
