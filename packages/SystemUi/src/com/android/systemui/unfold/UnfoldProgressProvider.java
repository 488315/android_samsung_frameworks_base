package com.android.systemui.unfold;

import com.android.wm.shell.unfold.ShellUnfoldProgressProvider;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UnfoldProgressProvider implements ShellUnfoldProgressProvider {
    public final UnfoldTransitionProgressProvider unfoldProgressProvider;

    public UnfoldProgressProvider(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider) {
        this.unfoldProgressProvider = unfoldTransitionProgressProvider;
    }

    @Override // com.android.wm.shell.unfold.ShellUnfoldProgressProvider
    public final void addListener(final Executor executor, final ShellUnfoldProgressProvider.UnfoldListener unfoldListener) {
        this.unfoldProgressProvider.addCallback(new UnfoldTransitionProgressProvider.TransitionProgressListener() { // from class: com.android.systemui.unfold.UnfoldProgressProvider$addListener$1
            @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
            public final void onTransitionFinished() {
                final ShellUnfoldProgressProvider.UnfoldListener unfoldListener2 = unfoldListener;
                executor.execute(new Runnable() { // from class: com.android.systemui.unfold.UnfoldProgressProvider$addListener$1$onTransitionFinished$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ShellUnfoldProgressProvider.UnfoldListener.this.onStateChangeFinished();
                    }
                });
            }

            @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
            public final void onTransitionProgress(final float f) {
                final ShellUnfoldProgressProvider.UnfoldListener unfoldListener2 = unfoldListener;
                executor.execute(new Runnable() { // from class: com.android.systemui.unfold.UnfoldProgressProvider$addListener$1$onTransitionProgress$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ShellUnfoldProgressProvider.UnfoldListener.this.onStateChangeProgress(f);
                    }
                });
            }

            @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
            public final void onTransitionStarted() {
                final ShellUnfoldProgressProvider.UnfoldListener unfoldListener2 = unfoldListener;
                executor.execute(new Runnable() { // from class: com.android.systemui.unfold.UnfoldProgressProvider$addListener$1$onTransitionStarted$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ShellUnfoldProgressProvider.UnfoldListener.this.onStateChangeStarted();
                    }
                });
            }
        });
    }
}
