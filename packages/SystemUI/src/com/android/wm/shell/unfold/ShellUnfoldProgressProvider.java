package com.android.wm.shell.unfold;

import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public interface ShellUnfoldProgressProvider {
    public static final AnonymousClass1 NO_PROVIDER = new ShellUnfoldProgressProvider() { // from class: com.android.wm.shell.unfold.ShellUnfoldProgressProvider.1
    };

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
