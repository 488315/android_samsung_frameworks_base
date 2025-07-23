package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class PrimaryBouncerToGoneTransitionViewModel$lockscreenAlpha$2 extends FunctionReferenceImpl implements Function0 {
    public PrimaryBouncerToGoneTransitionViewModel$lockscreenAlpha$2(Object obj) {
        super(0, obj, PrimaryBouncerInteractor.class, "willRunDismissFromKeyguard", "willRunDismissFromKeyguard()Z", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return Boolean.valueOf(((PrimaryBouncerInteractor) this.receiver).willRunDismissFromKeyguard());
    }
}
