package com.android.systemui.user.domain.interactor;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final /* synthetic */ class UserSwitcherInteractor$exitGuestUser$2 extends FunctionReferenceImpl implements Function0 {
    public UserSwitcherInteractor$exitGuestUser$2(Object obj) {
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
