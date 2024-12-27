package com.android.systemui.communal.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.shared.model.CommunalWidgetContentModel;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class CommunalInteractor$widgetContent$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ CommunalInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalInteractor$widgetContent$2(CommunalInteractor communalInteractor, Continuation continuation) {
        super(3, continuation);
        this.this$0 = communalInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        CommunalInteractor$widgetContent$2 communalInteractor$widgetContent$2 = new CommunalInteractor$widgetContent$2(this.this$0, (Continuation) obj3);
        communalInteractor$widgetContent$2.L$0 = (List) obj;
        communalInteractor$widgetContent$2.Z$0 = booleanValue;
        return communalInteractor$widgetContent$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2;
        int identifier;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        boolean z = this.Z$0;
        CommunalInteractor communalInteractor = this.this$0;
        if (z) {
            CommunalInteractor.Companion companion = CommunalInteractor.Companion;
            communalInteractor.getClass();
            return list;
        }
        Iterator it = ((UserTrackerImpl) communalInteractor.userTracker).getUserProfiles().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj2 = null;
                break;
            }
            obj2 = it.next();
            if (((UserInfo) obj2).isManagedProfile()) {
                break;
            }
        }
        UserInfo userInfo = (UserInfo) obj2;
        ArrayList arrayList = new ArrayList();
        for (Object obj3 : list) {
            CommunalWidgetContentModel communalWidgetContentModel = (CommunalWidgetContentModel) obj3;
            if (communalWidgetContentModel instanceof CommunalWidgetContentModel.Available) {
                identifier = ((CommunalWidgetContentModel.Available) communalWidgetContentModel).providerInfo.getProfile().getIdentifier();
            } else {
                if (!(communalWidgetContentModel instanceof CommunalWidgetContentModel.Pending)) {
                    throw new NoWhenBranchMatchedException();
                }
                identifier = ((CommunalWidgetContentModel.Pending) communalWidgetContentModel).user.getIdentifier();
            }
            boolean z2 = false;
            if (userInfo != null && identifier == userInfo.id) {
                z2 = true;
            }
            if (!z2) {
                arrayList.add(obj3);
            }
        }
        return arrayList;
    }
}
