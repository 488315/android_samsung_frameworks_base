package com.android.systemui.communal.widgets;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
final class CommunalAppWidgetHostStartable$onStartInForegroundUser$7 extends SuspendLambda implements Function2 {
    /* synthetic */ int I$0;
    int label;
    final /* synthetic */ CommunalAppWidgetHostStartable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalAppWidgetHostStartable$onStartInForegroundUser$7(CommunalAppWidgetHostStartable communalAppWidgetHostStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalAppWidgetHostStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalAppWidgetHostStartable$onStartInForegroundUser$7 communalAppWidgetHostStartable$onStartInForegroundUser$7 = new CommunalAppWidgetHostStartable$onStartInForegroundUser$7(this.this$0, continuation);
        communalAppWidgetHostStartable$onStartInForegroundUser$7.I$0 = ((Number) obj).intValue();
        return communalAppWidgetHostStartable$onStartInForegroundUser$7;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalAppWidgetHostStartable$onStartInForegroundUser$7) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        CommunalAppWidgetHostStartable communalAppWidgetHostStartable = this.this$0;
        int i2 = CommunalAppWidgetHostStartable.$r8$clinit;
        communalAppWidgetHostStartable.getCommunalInteractor().widgetRepository.deleteWidget(i);
        return Unit.INSTANCE;
    }
}
