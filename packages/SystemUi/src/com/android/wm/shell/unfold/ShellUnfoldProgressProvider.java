package com.android.wm.shell.unfold;

import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface ShellUnfoldProgressProvider {
    public static final C41521 NO_PROVIDER = new ShellUnfoldProgressProvider() { // from class: com.android.wm.shell.unfold.ShellUnfoldProgressProvider.1
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface UnfoldListener {
        void onStateChangeFinished();

        void onStateChangeProgress(float f);

        default void onStateChangeStarted() {
        }
    }

    default void addListener(Executor executor, UnfoldListener unfoldListener) {
    }
}
