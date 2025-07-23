package com.android.systemui.user.domain.interactor;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class UserSwitcherInteractor$executeAction$2 extends FunctionReferenceImpl implements Function0 {
    public UserSwitcherInteractor$executeAction$2(Object obj) {
        super(0, obj, UserSwitcherInteractor.class, "dismissDialog", "dismissDialog()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        UserSwitcherInteractor userSwitcherInteractor = (UserSwitcherInteractor) this.receiver;
        int i = UserSwitcherInteractor.$r8$clinit;
        userSwitcherInteractor.dismissDialog();
        return Unit.INSTANCE;
    }
}
