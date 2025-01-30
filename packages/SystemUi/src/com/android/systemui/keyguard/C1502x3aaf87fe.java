package com.android.systemui.keyguard;

import java.util.function.IntConsumer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.keyguard.KeyguardViewMediatorHelperImplKt$sam$java_util_function_IntConsumer$0 */
/* loaded from: classes.dex */
public final /* synthetic */ class C1502x3aaf87fe implements IntConsumer {
    public final /* synthetic */ Function1 function;

    public C1502x3aaf87fe(Function1 function1) {
        this.function = function1;
    }

    @Override // java.util.function.IntConsumer
    public final /* synthetic */ void accept(int i) {
        this.function.invoke(Integer.valueOf(i));
    }
}
