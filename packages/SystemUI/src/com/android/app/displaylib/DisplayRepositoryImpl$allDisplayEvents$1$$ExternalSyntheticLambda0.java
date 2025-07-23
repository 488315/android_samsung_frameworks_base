package com.android.app.displaylib;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class DisplayRepositoryImpl$allDisplayEvents$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ DisplayRepositoryImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DisplayRepositoryImpl$allDisplayEvents$1$$ExternalSyntheticLambda0(DisplayRepositoryImpl displayRepositoryImpl, DisplayRepositoryImpl$allDisplayEvents$1$callback$1 displayRepositoryImpl$allDisplayEvents$1$callback$1) {
        this.f$0 = displayRepositoryImpl;
        this.f$1 = displayRepositoryImpl$allDisplayEvents$1$callback$1;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.displayManager.unregisterDisplayListener((DisplayRepositoryImpl$allDisplayEvents$1$callback$1) this.f$1);
                break;
            default:
                this.f$0.displayManager.unregisterDisplayListener((DisplayRepositoryImpl$connectedDisplayIds$1$callback$1) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ DisplayRepositoryImpl$allDisplayEvents$1$$ExternalSyntheticLambda0(DisplayRepositoryImpl displayRepositoryImpl, DisplayRepositoryImpl$connectedDisplayIds$1$callback$1 displayRepositoryImpl$connectedDisplayIds$1$callback$1) {
        this.f$0 = displayRepositoryImpl;
        this.f$1 = displayRepositoryImpl$connectedDisplayIds$1$callback$1;
    }
}
