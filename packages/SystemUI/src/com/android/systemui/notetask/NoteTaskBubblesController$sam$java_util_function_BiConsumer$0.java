package com.android.systemui.notetask;

import java.util.function.BiConsumer;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class NoteTaskBubblesController$sam$java_util_function_BiConsumer$0 implements BiConsumer {
    public final /* synthetic */ Function2 function;

    public NoteTaskBubblesController$sam$java_util_function_BiConsumer$0(Function2 function2) {
        this.function = function2;
    }

    @Override // java.util.function.BiConsumer
    public final /* synthetic */ void accept(Object obj, Object obj2) {
        this.function.invoke(obj, obj2);
    }
}
