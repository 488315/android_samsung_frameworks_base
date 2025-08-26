package com.android.systemui.statusbar.notification;

import java.util.function.BiFunction;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final /* synthetic */ class ConversationNotificationManager$sam$java_util_function_BiFunction$0 implements BiFunction {
    public final /* synthetic */ Function2 function;

    public ConversationNotificationManager$sam$java_util_function_BiFunction$0(Function2 function2) {
        this.function = function2;
    }

    @Override // java.util.function.BiFunction
    public final /* synthetic */ Object apply(Object obj, Object obj2) {
        return this.function.invoke(obj, obj2);
    }
}
