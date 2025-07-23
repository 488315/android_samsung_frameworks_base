package com.android.systemui.user.domain.interactor;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class UserSwitcherInteractor$exitGuestUser$3 extends FunctionReferenceImpl implements Function1 {
    public UserSwitcherInteractor$exitGuestUser$3(Object obj) {
        super(1, obj, UserSwitcherInteractor.class, "switchUser", "switchUser(I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        int intValue = ((Number) obj).intValue();
        UserSwitcherInteractor userSwitcherInteractor = (UserSwitcherInteractor) this.receiver;
        int i = UserSwitcherInteractor.$r8$clinit;
        userSwitcherInteractor.switchUser(intValue);
        return Unit.INSTANCE;
    }
}
