package com.android.systemui.user.domain.interactor;

import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final /* synthetic */ class UserInteractor$selectUser$1 extends FunctionReferenceImpl implements Function3 {
    public UserInteractor$selectUser$1(Object obj) {
        super(3, obj, UserInteractor.class, "exitGuestUser", "exitGuestUser(IIZ)V", 0);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ((UserInteractor) this.receiver).exitGuestUser(((Number) obj).intValue(), ((Number) obj2).intValue(), ((Boolean) obj3).booleanValue());
        return Unit.INSTANCE;
    }
}
