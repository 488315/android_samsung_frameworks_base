package com.android.systemui.keyevent.data.repository;

import com.android.systemui.statusbar.CommandQueue;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyEventRepositoryImpl$isPowerButtonDown$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyEventRepositoryImpl f$0;
    public final /* synthetic */ CommandQueue.Callbacks f$1;

    public /* synthetic */ KeyEventRepositoryImpl$isPowerButtonDown$1$$ExternalSyntheticLambda0(KeyEventRepositoryImpl keyEventRepositoryImpl, CommandQueue.Callbacks callbacks, int i) {
        this.$r8$classId = i;
        this.f$0 = keyEventRepositoryImpl;
        this.f$1 = callbacks;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.commandQueue.removeCallback(this.f$1);
                break;
            default:
                this.f$0.commandQueue.removeCallback(this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
