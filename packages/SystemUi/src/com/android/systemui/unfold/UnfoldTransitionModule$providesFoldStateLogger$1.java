package com.android.systemui.unfold;

import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UnfoldTransitionModule$providesFoldStateLogger$1 implements Function {
    public static final UnfoldTransitionModule$providesFoldStateLogger$1 INSTANCE = new UnfoldTransitionModule$providesFoldStateLogger$1();

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return new FoldStateLogger((FoldStateLoggingProvider) obj);
    }
}
