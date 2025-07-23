package com.android.systemui.communal.data.repository;

import com.android.systemui.communal.widgets.GlanceableHubWidgetManager;
import com.android.systemui.communal.widgets.GlanceableHubWidgetManager$$ExternalSyntheticLambda1;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalWidgetRepositoryRemoteImpl$updateWidgetOrder$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Map<Integer, Integer> $widgetIdToRankMap;
    int label;
    final /* synthetic */ CommunalWidgetRepositoryRemoteImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalWidgetRepositoryRemoteImpl$updateWidgetOrder$1(CommunalWidgetRepositoryRemoteImpl communalWidgetRepositoryRemoteImpl, Map<Integer, Integer> map, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalWidgetRepositoryRemoteImpl;
        this.$widgetIdToRankMap = map;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalWidgetRepositoryRemoteImpl$updateWidgetOrder$1(this.this$0, this.$widgetIdToRankMap, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalWidgetRepositoryRemoteImpl$updateWidgetOrder$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        GlanceableHubWidgetManager glanceableHubWidgetManager = this.this$0.glanceableHubWidgetManager;
        Map<Integer, Integer> map = this.$widgetIdToRankMap;
        glanceableHubWidgetManager.getClass();
        glanceableHubWidgetManager.runOnService(new GlanceableHubWidgetManager$$ExternalSyntheticLambda1(map, 0));
        return Unit.INSTANCE;
    }
}
