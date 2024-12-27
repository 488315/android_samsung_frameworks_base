package com.android.systemui.user.domain.interactor;

import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final /* synthetic */ class UserSwitcherInteractor$selectUser$1 extends FunctionReferenceImpl implements Function3 {
    public UserSwitcherInteractor$selectUser$1(Object obj) {
        super(3, obj, UserSwitcherInteractor.class, "exitGuestUser", "exitGuestUser(IIZ)V", 0);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ((UserSwitcherInteractor) this.receiver).exitGuestUser(((Number) obj).intValue(), ((Number) obj2).intValue(), ((Boolean) obj3).booleanValue());
        return Unit.INSTANCE;
    }
}
