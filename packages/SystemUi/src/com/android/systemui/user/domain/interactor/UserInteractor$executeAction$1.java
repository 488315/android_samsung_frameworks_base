package com.android.systemui.user.domain.interactor;

import com.android.systemui.user.domain.model.ShowDialogRequestModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final /* synthetic */ class UserInteractor$executeAction$1 extends FunctionReferenceImpl implements Function1 {
    public UserInteractor$executeAction$1(Object obj) {
        super(1, obj, UserInteractor.class, "showDialog", "showDialog(Lcom/android/systemui/user/domain/model/ShowDialogRequestModel;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        UserInteractor userInteractor = (UserInteractor) this.receiver;
        int i = UserInteractor.$r8$clinit;
        userInteractor.showDialog((ShowDialogRequestModel) obj);
        return Unit.INSTANCE;
    }
}
