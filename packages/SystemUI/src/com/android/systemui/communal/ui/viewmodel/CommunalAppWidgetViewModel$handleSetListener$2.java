package com.android.systemui.communal.ui.viewmodel;

import android.appwidget.AppWidgetHost;
import com.android.systemui.communal.widgets.CommunalAppWidgetHost;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class CommunalAppWidgetViewModel$handleSetListener$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $appWidgetId;
    final /* synthetic */ AppWidgetHost.AppWidgetHostListener $listener;
    int label;
    final /* synthetic */ CommunalAppWidgetViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalAppWidgetViewModel$handleSetListener$2(CommunalAppWidgetViewModel communalAppWidgetViewModel, int i, AppWidgetHost.AppWidgetHostListener appWidgetHostListener, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalAppWidgetViewModel;
        this.$appWidgetId = i;
        this.$listener = appWidgetHostListener;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalAppWidgetViewModel$handleSetListener$2(this.this$0, this.$appWidgetId, this.$listener, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalAppWidgetViewModel$handleSetListener$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.multiUserHelper.getClass();
        Object obj2 = this.this$0.appWidgetHostLazy.get();
        int i = this.$appWidgetId;
        CommunalAppWidgetViewModel communalAppWidgetViewModel = this.this$0;
        AppWidgetHost.AppWidgetHostListener appWidgetHostListener = this.$listener;
        ((CommunalAppWidgetHost) obj2).setListener(i, communalAppWidgetViewModel.listenerDelegateFactory.create("CommunalAppWidgetViewModel_" + i, appWidgetHostListener));
        return Unit.INSTANCE;
    }
}
