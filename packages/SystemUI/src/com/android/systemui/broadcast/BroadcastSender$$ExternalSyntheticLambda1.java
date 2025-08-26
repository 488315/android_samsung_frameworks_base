package com.android.systemui.broadcast;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final /* synthetic */ class BroadcastSender$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ BroadcastSender f$0;

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        this.f$0.context.closeSystemDialogs();
        return Unit.INSTANCE;
    }
}
