package com.android.wm.shell.bubbles.storage;

import java.util.function.Predicate;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleVolatileRepositoryKt$sam$java_util_function_Predicate$0 implements Predicate {
    public final /* synthetic */ Function1 function;

    public BubbleVolatileRepositoryKt$sam$java_util_function_Predicate$0(Function1 function1) {
        this.function = function1;
    }

    @Override // java.util.function.Predicate
    public final /* synthetic */ boolean test(Object obj) {
        return ((Boolean) this.function.mo779invoke(obj)).booleanValue();
    }
}
