package com.android.systemui.user.domain.interactor;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final /* synthetic */ class UserInteractor$exitGuestUser$3 extends FunctionReferenceImpl implements Function1 {
    public UserInteractor$exitGuestUser$3(Object obj) {
        super(1, obj, UserInteractor.class, "switchUser", "switchUser(I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        int intValue = ((Number) obj).intValue();
        UserInteractor userInteractor = (UserInteractor) this.receiver;
        int i = UserInteractor.$r8$clinit;
        userInteractor.switchUser(intValue);
        return Unit.INSTANCE;
    }
}
