package com.android.systemui.unfold;

import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.updates.FoldProvider;
import com.android.wm.shell.unfold.ShellUnfoldProgressProvider;
import java.util.concurrent.Executor;

public final class UnfoldProgressProvider implements ShellUnfoldProgressProvider {
    public final FoldProvider foldProvider;
    public final UnfoldTransitionProgressProvider unfoldProgressProvider;

    public UnfoldProgressProvider(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, FoldProvider foldProvider) {
        this.unfoldProgressProvider = unfoldTransitionProgressProvider;
        this.foldProvider = foldProvider;
    }

    @Override // com.android.wm.shell.unfold.ShellUnfoldProgressProvider
    public final void addListener(final Executor executor, final ShellUnfoldProgressProvider.UnfoldListener unfoldListener) {
        this.unfoldProgressProvider.addCallback(new UnfoldTransitionProgressProvider.TransitionProgressListener() { // from class: com.android.systemui.unfold.UnfoldProgressProvider$addListener$1
            @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
            public final void onTransitionFinished() {
                Executor executor2 = executor;
                final ShellUnfoldProgressProvider.UnfoldListener unfoldListener2 = unfoldListener;
                executor2.execute(new Runnable() { // from class: com.android.systemui.unfold.UnfoldProgressProvider$addListener$1$onTransitionFinished$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ShellUnfoldProgressProvider.UnfoldListener.this.onStateChangeFinished();
                    }
                });
            }

            @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
            public final void onTransitionProgress(final float f) {
                Executor executor2 = executor;
                final ShellUnfoldProgressProvider.UnfoldListener unfoldListener2 = unfoldListener;
                executor2.execute(new Runnable() { // from class: com.android.systemui.unfold.UnfoldProgressProvider$addListener$1$onTransitionProgress$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ShellUnfoldProgressProvider.UnfoldListener.this.onStateChangeProgress(f);
                    }
                });
            }

            @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
            public final void onTransitionStarted() {
                Executor executor2 = executor;
                final ShellUnfoldProgressProvider.UnfoldListener unfoldListener2 = unfoldListener;
                executor2.execute(new Runnable() { // from class: com.android.systemui.unfold.UnfoldProgressProvider$addListener$1$onTransitionStarted$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ShellUnfoldProgressProvider.UnfoldListener.this.onStateChangeStarted();
                    }
                });
            }
        });
        this.foldProvider.registerCallback(new FoldProvider.FoldCallback() { // from class: com.android.systemui.unfold.UnfoldProgressProvider$addListener$2
            @Override // com.android.systemui.unfold.updates.FoldProvider.FoldCallback
            public final void onFoldUpdated(boolean z) {
                ShellUnfoldProgressProvider.UnfoldListener.this.onFoldStateChanged(z);
            }
        }, executor);
    }
}
