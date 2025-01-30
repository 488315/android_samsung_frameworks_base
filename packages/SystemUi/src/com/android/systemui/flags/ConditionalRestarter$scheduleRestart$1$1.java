package com.android.systemui.flags;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
final /* synthetic */ class ConditionalRestarter$scheduleRestart$1$1 extends AdaptedFunctionReference implements Function0 {
    public ConditionalRestarter$scheduleRestart$1$1(Object obj) {
        super(0, obj, ConditionalRestarter.class, "scheduleRestart", "scheduleRestart(Ljava/lang/String;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ConditionalRestarter conditionalRestarter = (ConditionalRestarter) this.receiver;
        int i = ConditionalRestarter.$r8$clinit;
        conditionalRestarter.scheduleRestart("");
        return Unit.INSTANCE;
    }
}
