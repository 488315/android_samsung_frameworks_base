package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class BouncerToGoneFlows$scrimAlpha$2 extends FunctionReferenceImpl implements Function0 {
    public BouncerToGoneFlows$scrimAlpha$2(Object obj) {
        super(0, obj, PrimaryBouncerInteractor.class, "willRunDismissFromKeyguard", "willRunDismissFromKeyguard()Z", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return Boolean.valueOf(((PrimaryBouncerInteractor) this.receiver).willRunDismissFromKeyguard());
    }
}
