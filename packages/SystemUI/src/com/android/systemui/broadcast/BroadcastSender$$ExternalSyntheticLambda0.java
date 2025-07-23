package com.android.systemui.broadcast;

import android.content.Intent;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class BroadcastSender$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BroadcastSender f$0;
    public final /* synthetic */ Intent f$1;

    public /* synthetic */ BroadcastSender$$ExternalSyntheticLambda0(BroadcastSender broadcastSender, Intent intent, int i) {
        this.$r8$classId = i;
        this.f$0 = broadcastSender;
        this.f$1 = intent;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.context.sendBroadcast(this.f$1, "com.android.systemui.permission.SELF");
                break;
            default:
                this.f$0.context.sendBroadcast(this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
