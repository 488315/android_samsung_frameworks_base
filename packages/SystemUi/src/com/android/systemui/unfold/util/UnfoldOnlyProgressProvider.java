package com.android.systemui.unfold.util;

import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.updates.FoldProvider;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UnfoldOnlyProgressProvider implements UnfoldTransitionProgressProvider {
    public final Executor executor;
    public boolean isFolded;
    public final ScopedUnfoldTransitionProgressProvider scopedProvider;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        ((ArrayList) this.scopedProvider.listeners).add((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void removeCallback(Object obj) {
        ((ArrayList) this.scopedProvider.listeners).remove((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    public /* synthetic */ UnfoldOnlyProgressProvider(FoldProvider foldProvider, Executor executor, UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(foldProvider, executor, unfoldTransitionProgressProvider, (i & 8) != 0 ? new ScopedUnfoldTransitionProgressProvider(unfoldTransitionProgressProvider) : scopedUnfoldTransitionProgressProvider);
    }
}
