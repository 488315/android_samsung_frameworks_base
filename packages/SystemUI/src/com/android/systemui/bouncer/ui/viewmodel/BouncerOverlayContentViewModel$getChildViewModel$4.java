package com.android.systemui.bouncer.ui.viewmodel;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes.dex */
final /* synthetic */ class BouncerOverlayContentViewModel$getChildViewModel$4 extends FunctionReferenceImpl implements Function0 {
    public BouncerOverlayContentViewModel$getChildViewModel$4(Object obj) {
        super(0, obj, BouncerOverlayContentViewModel.class, "onIntentionalUserInput", "onIntentionalUserInput()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        BouncerOverlayContentViewModel.access$onIntentionalUserInput((BouncerOverlayContentViewModel) this.receiver);
        return Unit.INSTANCE;
    }
}
