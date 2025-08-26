package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
final class MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$latest$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $block;
    final /* synthetic */ int $subId;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$latest$1(int i, Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.$subId = i;
        this.$block = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$latest$1 mobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$latest$1 = new MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$latest$1(this.$subId, this.$block, continuation);
        mobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$latest$1.L$0 = obj;
        return mobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$latest$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$latest$1) create((Map) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(this.$subId, (Map) this.L$0);
        return (mobileIconInteractor == null || (flow = (Flow) this.$block.mo781invoke(mobileIconInteractor)) == null) ? EmptyFlow.INSTANCE : flow;
    }
}
