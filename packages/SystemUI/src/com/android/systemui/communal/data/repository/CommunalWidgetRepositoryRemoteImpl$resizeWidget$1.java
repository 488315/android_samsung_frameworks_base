package com.android.systemui.communal.data.repository;

import com.android.systemui.communal.widgets.GlanceableHubWidgetManager;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalWidgetRepositoryRemoteImpl$resizeWidget$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $appWidgetId;
    final /* synthetic */ int $spanY;
    final /* synthetic */ Map<Integer, Integer> $widgetIdToRankMap;
    int label;
    final /* synthetic */ CommunalWidgetRepositoryRemoteImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalWidgetRepositoryRemoteImpl$resizeWidget$1(CommunalWidgetRepositoryRemoteImpl communalWidgetRepositoryRemoteImpl, int i, int i2, Map<Integer, Integer> map, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalWidgetRepositoryRemoteImpl;
        this.$appWidgetId = i;
        this.$spanY = i2;
        this.$widgetIdToRankMap = map;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalWidgetRepositoryRemoteImpl$resizeWidget$1(this.this$0, this.$appWidgetId, this.$spanY, this.$widgetIdToRankMap, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalWidgetRepositoryRemoteImpl$resizeWidget$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        GlanceableHubWidgetManager glanceableHubWidgetManager = this.this$0.glanceableHubWidgetManager;
        final int i = this.$appWidgetId;
        final int i2 = this.$spanY;
        final Map<Integer, Integer> map = this.$widgetIdToRankMap;
        glanceableHubWidgetManager.getClass();
        glanceableHubWidgetManager.runOnService(new Function1() { // from class: com.android.systemui.communal.widgets.GlanceableHubWidgetManager$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                Map map2 = map;
                int i3 = GlanceableHubWidgetManager.$r8$clinit;
                ((IGlanceableHubWidgetManagerService) obj2).resizeWidget(i, i2, CollectionsKt___CollectionsKt.toIntArray(map2.keySet()), CollectionsKt___CollectionsKt.toIntArray(map2.values()));
                return Unit.INSTANCE;
            }
        });
        return Unit.INSTANCE;
    }
}
