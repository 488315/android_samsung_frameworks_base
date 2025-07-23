package com.android.systemui.communal.widgets;

import android.widget.RemoteViews;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class AppWidgetHostListenerDelegate$updateAppWidget$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ RemoteViews $views;
    int label;
    final /* synthetic */ AppWidgetHostListenerDelegate this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppWidgetHostListenerDelegate$updateAppWidget$1(AppWidgetHostListenerDelegate appWidgetHostListenerDelegate, RemoteViews remoteViews, Continuation continuation) {
        super(2, continuation);
        this.this$0 = appWidgetHostListenerDelegate;
        this.$views = remoteViews;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppWidgetHostListenerDelegate$updateAppWidget$1(this.this$0, this.$views, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppWidgetHostListenerDelegate$updateAppWidget$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.listener.updateAppWidget(this.$views);
        return Unit.INSTANCE;
    }
}
