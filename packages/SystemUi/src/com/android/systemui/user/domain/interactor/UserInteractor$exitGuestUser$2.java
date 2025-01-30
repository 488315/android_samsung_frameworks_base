package com.android.systemui.user.domain.interactor;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final /* synthetic */ class UserInteractor$exitGuestUser$2 extends FunctionReferenceImpl implements Function0 {
    public UserInteractor$exitGuestUser$2(Object obj) {
        super(0, obj, UserInteractor.class, "dismissDialog", "dismissDialog()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((UserInteractor) this.receiver).dismissDialog();
        return Unit.INSTANCE;
    }
}
