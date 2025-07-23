package com.android.systemui.communal.widgets;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalAppWidgetHostStartable$updateAppWidgetHostActive$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $active;
    int label;
    final /* synthetic */ CommunalAppWidgetHostStartable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalAppWidgetHostStartable$updateAppWidgetHostActive$2(boolean z, CommunalAppWidgetHostStartable communalAppWidgetHostStartable, Continuation continuation) {
        super(2, continuation);
        this.$active = z;
        this.this$0 = communalAppWidgetHostStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalAppWidgetHostStartable$updateAppWidgetHostActive$2(this.$active, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalAppWidgetHostStartable$updateAppWidgetHostActive$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (this.$active) {
            CommunalWidgetHost communalWidgetHost = (CommunalWidgetHost) this.this$0.communalWidgetHost$delegate.getValue();
            CommunalAppWidgetHost communalAppWidgetHost = communalWidgetHost.appWidgetHost;
            synchronized (communalAppWidgetHost.observers) {
                communalAppWidgetHost.observers.add(communalWidgetHost);
            }
            ((CommunalAppWidgetHost) this.this$0.appWidgetHost$delegate.getValue()).startListening();
        } else {
            CommunalAppWidgetHostStartable communalAppWidgetHostStartable = this.this$0;
            int i = CommunalAppWidgetHostStartable.$r8$clinit;
            ((CommunalAppWidgetHost) communalAppWidgetHostStartable.appWidgetHost$delegate.getValue()).stopListening();
            CommunalWidgetHost communalWidgetHost2 = (CommunalWidgetHost) this.this$0.communalWidgetHost$delegate.getValue();
            CommunalAppWidgetHost communalAppWidgetHost2 = communalWidgetHost2.appWidgetHost;
            synchronized (communalAppWidgetHost2.observers) {
                communalAppWidgetHost2.observers.remove(communalWidgetHost2);
            }
        }
        return Unit.INSTANCE;
    }
}
