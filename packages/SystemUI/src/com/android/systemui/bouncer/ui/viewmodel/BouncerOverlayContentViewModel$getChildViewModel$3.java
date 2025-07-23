package com.android.systemui.bouncer.ui.viewmodel;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class BouncerOverlayContentViewModel$getChildViewModel$3 extends FunctionReferenceImpl implements Function0 {
    public BouncerOverlayContentViewModel$getChildViewModel$3(Object obj) {
        super(0, obj, BouncerOverlayContentViewModel.class, "onIntentionalUserInput", "onIntentionalUserInput()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        BouncerOverlayContentViewModel.access$onIntentionalUserInput((BouncerOverlayContentViewModel) this.receiver);
        return Unit.INSTANCE;
    }
}
