package com.android.systemui.bouncer.ui.viewmodel;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class BouncerViewModel$getChildViewModel$4 extends FunctionReferenceImpl implements Function0 {
    public BouncerViewModel$getChildViewModel$4(Object obj) {
        super(0, obj, BouncerViewModel.class, "onIntentionalUserInput", "onIntentionalUserInput()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        BouncerViewModel.access$onIntentionalUserInput((BouncerViewModel) this.receiver);
        return Unit.INSTANCE;
    }
}
