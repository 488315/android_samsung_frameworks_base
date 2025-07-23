package com.android.systemui.user.domain.interactor;

import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class UserSwitcherInteractor$selectUser$2 extends FunctionReferenceImpl implements Function3 {
    public UserSwitcherInteractor$selectUser$2(Object obj) {
        super(3, obj, UserSwitcherInteractor.class, "exitGuestUser", "exitGuestUser(IIZ)V", 0);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ((UserSwitcherInteractor) this.receiver).exitGuestUser(((Number) obj).intValue(), ((Number) obj2).intValue(), ((Boolean) obj3).booleanValue());
        return Unit.INSTANCE;
    }
}
