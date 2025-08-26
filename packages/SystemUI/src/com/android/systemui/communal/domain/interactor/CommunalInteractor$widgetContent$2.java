package com.android.systemui.communal.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.shared.model.CommunalWidgetContentModel;
import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
final class CommunalInteractor$widgetContent$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CommunalInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalInteractor$widgetContent$2(CommunalInteractor communalInteractor, Continuation continuation) {
        super(3, continuation);
        this.this$0 = communalInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalInteractor$widgetContent$2 communalInteractor$widgetContent$2 = new CommunalInteractor$widgetContent$2(this.this$0, (Continuation) obj3);
        communalInteractor$widgetContent$2.L$0 = (List) obj;
        communalInteractor$widgetContent$2.L$1 = (UserInfo) obj2;
        return communalInteractor$widgetContent$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int identifier;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        UserInfo userInfo = (UserInfo) this.L$1;
        CommunalInteractor communalInteractor = this.this$0;
        CommunalInteractor.Companion companion = CommunalInteractor.Companion;
        communalInteractor.getClass();
        if (userInfo == null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : list) {
            CommunalWidgetContentModel communalWidgetContentModel = (CommunalWidgetContentModel) obj2;
            if (communalWidgetContentModel instanceof CommunalWidgetContentModel.Available) {
                identifier = ((CommunalWidgetContentModel.Available) communalWidgetContentModel).providerInfo.getProfile().getIdentifier();
            } else {
                if (!(communalWidgetContentModel instanceof CommunalWidgetContentModel.Pending)) {
                    throw new NoWhenBranchMatchedException();
                }
                identifier = ((CommunalWidgetContentModel.Pending) communalWidgetContentModel).user.getIdentifier();
            }
            if (identifier != userInfo.id) {
                arrayList.add(obj2);
            }
        }
        return arrayList;
    }
}
