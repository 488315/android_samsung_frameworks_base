package com.android.systemui.unfold;

import com.android.systemui.unfold.updates.FoldProvider;
import com.android.systemui.unfold.util.UnfoldOnlyProgressProvider;
import java.util.concurrent.Executor;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UnfoldTransitionModule$provideUnfoldOnlyProvider$1 implements Function {
    public final /* synthetic */ Executor $executor;
    public final /* synthetic */ FoldProvider $foldProvider;

    public UnfoldTransitionModule$provideUnfoldOnlyProvider$1(FoldProvider foldProvider, Executor executor) {
        this.$foldProvider = foldProvider;
        this.$executor = executor;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return new UnfoldOnlyProgressProvider(this.$foldProvider, this.$executor, (UnfoldTransitionProgressProvider) obj, null, 8, null);
    }
}
