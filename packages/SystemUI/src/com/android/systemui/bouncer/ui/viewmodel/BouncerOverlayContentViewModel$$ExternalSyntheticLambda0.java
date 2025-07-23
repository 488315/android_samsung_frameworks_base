package com.android.systemui.bouncer.ui.viewmodel;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class BouncerOverlayContentViewModel$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BouncerOverlayContentViewModel f$0;

    public /* synthetic */ BouncerOverlayContentViewModel$$ExternalSyntheticLambda0(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, int i) {
        this.$r8$classId = i;
        this.f$0 = bouncerOverlayContentViewModel;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.wipeDialogMessage.setValue(null);
                return Unit.INSTANCE;
            case 1:
                this.f$0.lockoutDialogMessage.setValue(null);
                return Unit.INSTANCE;
            default:
                return this.f$0.bouncerMessageViewModelFactory.create();
        }
    }
}
