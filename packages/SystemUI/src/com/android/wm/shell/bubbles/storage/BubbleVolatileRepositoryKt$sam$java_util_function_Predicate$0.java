package com.android.wm.shell.bubbles.storage;

import java.util.function.Predicate;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleVolatileRepositoryKt$sam$java_util_function_Predicate$0 implements Predicate {
    public final /* synthetic */ Function1 function;

    public BubbleVolatileRepositoryKt$sam$java_util_function_Predicate$0(Function1 function1) {
        this.function = function1;
    }

    @Override // java.util.function.Predicate
    public final /* synthetic */ boolean test(Object obj) {
        return ((Boolean) this.function.mo781invoke(obj)).booleanValue();
    }
}
