package com.android.systemui.communal.domain.interactor;

import android.appwidget.AppWidgetProviderInfo;
import android.os.UserHandle;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.shared.model.CommunalWidgetContentModel;
import com.android.systemui.communal.widgets.CommunalAppWidgetHost;
import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class CommunalInteractor$widgetContent$3 extends SuspendLambda implements Function4 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CommunalInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalInteractor$widgetContent$3(CommunalInteractor communalInteractor, Continuation continuation) {
        super(4, continuation);
        this.this$0 = communalInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        int intValue = ((Number) obj2).intValue();
        CommunalInteractor$widgetContent$3 communalInteractor$widgetContent$3 = new CommunalInteractor$widgetContent$3(this.this$0, (Continuation) obj4);
        communalInteractor$widgetContent$3.L$0 = (List) obj;
        communalInteractor$widgetContent$3.I$0 = intValue;
        return communalInteractor$widgetContent$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CommunalContentModel.WidgetContent pendingWidget;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        int i = this.I$0;
        List<CommunalWidgetContentModel> list2 = list;
        CommunalInteractor communalInteractor = this.this$0;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        for (CommunalWidgetContentModel communalWidgetContentModel : list2) {
            if (communalWidgetContentModel instanceof CommunalWidgetContentModel.Available) {
                AppWidgetProviderInfo appWidgetProviderInfo = ((CommunalWidgetContentModel.Available) communalWidgetContentModel).providerInfo;
                if ((appWidgetProviderInfo.widgetCategory & i) != 0) {
                    int i2 = ((CommunalWidgetContentModel.Available) communalWidgetContentModel).appWidgetId;
                    CommunalAppWidgetHost communalAppWidgetHost = communalInteractor.appWidgetHost;
                    UserHandle profile = appWidgetProviderInfo.getProfile();
                    pendingWidget = new CommunalContentModel.WidgetContent.Widget(i2, appWidgetProviderInfo, communalAppWidgetHost, communalInteractor.userManager.isManagedProfile(profile.getIdentifier()) && communalInteractor.userManager.isQuietModeEnabled(profile));
                } else {
                    pendingWidget = new CommunalContentModel.WidgetContent.DisabledWidget(((CommunalWidgetContentModel.Available) communalWidgetContentModel).appWidgetId, appWidgetProviderInfo);
                }
            } else {
                if (!(communalWidgetContentModel instanceof CommunalWidgetContentModel.Pending)) {
                    throw new NoWhenBranchMatchedException();
                }
                int i3 = ((CommunalWidgetContentModel.Pending) communalWidgetContentModel).appWidgetId;
                CommunalWidgetContentModel.Pending pending = (CommunalWidgetContentModel.Pending) communalWidgetContentModel;
                pendingWidget = new CommunalContentModel.WidgetContent.PendingWidget(i3, pending.packageName, pending.icon);
            }
            arrayList.add(pendingWidget);
        }
        return arrayList;
    }
}
