package com.android.systemui.user.domain.interactor;

import com.android.systemui.user.domain.model.ShowDialogRequestModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class UserSwitcherInteractor$executeAction$1 extends FunctionReferenceImpl implements Function1 {
    public UserSwitcherInteractor$executeAction$1(Object obj) {
        super(1, obj, UserSwitcherInteractor.class, "showDialog", "showDialog(Lcom/android/systemui/user/domain/model/ShowDialogRequestModel;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        UserSwitcherInteractor userSwitcherInteractor = (UserSwitcherInteractor) this.receiver;
        int i = UserSwitcherInteractor.$r8$clinit;
        userSwitcherInteractor.showDialog((ShowDialogRequestModel) obj);
        return Unit.INSTANCE;
    }
}
