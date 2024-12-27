package com.android.systemui.unfold.util;

import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.updates.FoldProvider;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UnfoldOnlyProgressProvider implements UnfoldTransitionProgressProvider {
    public final Executor executor;
    public boolean isFolded;
    public final ScopedUnfoldTransitionProgressProvider scopedProvider;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class FoldListener implements FoldProvider.FoldCallback {
        public FoldListener() {
        }

        @Override // com.android.systemui.unfold.updates.FoldProvider.FoldCallback
        public final void onFoldUpdated(boolean z) {
            UnfoldOnlyProgressProvider unfoldOnlyProgressProvider = UnfoldOnlyProgressProvider.this;
            if (z) {
                unfoldOnlyProgressProvider.scopedProvider.setReadyToHandleTransition(true);
            }
            unfoldOnlyProgressProvider.isFolded = z;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SourceTransitionListener implements UnfoldTransitionProgressProvider.TransitionProgressListener {
        public SourceTransitionListener() {
        }

        @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
        public final void onTransitionFinished() {
            UnfoldOnlyProgressProvider unfoldOnlyProgressProvider = UnfoldOnlyProgressProvider.this;
            if (unfoldOnlyProgressProvider.isFolded) {
                return;
            }
            unfoldOnlyProgressProvider.scopedProvider.setReadyToHandleTransition(false);
        }
    }

    public UnfoldOnlyProgressProvider(FoldProvider foldProvider, Executor executor, UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider) {
        this.executor = executor;
        this.scopedProvider = scopedUnfoldTransitionProgressProvider;
        foldProvider.registerCallback(new FoldListener(), executor);
        unfoldTransitionProgressProvider.addCallback(new SourceTransitionListener());
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void addCallback(Object obj) {
        this.scopedProvider.listeners.add((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void removeCallback(Object obj) {
        this.scopedProvider.listeners.remove((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    public /* synthetic */ UnfoldOnlyProgressProvider(FoldProvider foldProvider, Executor executor, UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(foldProvider, executor, unfoldTransitionProgressProvider, (i & 8) != 0 ? new ScopedUnfoldTransitionProgressProvider(unfoldTransitionProgressProvider) : scopedUnfoldTransitionProgressProvider);
    }
}
