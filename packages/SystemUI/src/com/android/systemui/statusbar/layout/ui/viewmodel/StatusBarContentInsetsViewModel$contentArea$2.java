package com.android.systemui.statusbar.layout.ui.viewmodel;

import android.graphics.Rect;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProviderImpl;
import com.android.systemui.util.leak.RotationUtils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class StatusBarContentInsetsViewModel$contentArea$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ StatusBarContentInsetsViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StatusBarContentInsetsViewModel$contentArea$2(StatusBarContentInsetsViewModel statusBarContentInsetsViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = statusBarContentInsetsViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        StatusBarContentInsetsViewModel$contentArea$2 statusBarContentInsetsViewModel$contentArea$2 = new StatusBarContentInsetsViewModel$contentArea$2(this.this$0, continuation);
        statusBarContentInsetsViewModel$contentArea$2.L$0 = obj;
        return statusBarContentInsetsViewModel$contentArea$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StatusBarContentInsetsViewModel$contentArea$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            StatusBarContentInsetsProviderImpl statusBarContentInsetsProviderImpl = (StatusBarContentInsetsProviderImpl) this.this$0.statusBarContentInsetsProvider;
            Rect statusBarContentAreaForRotation = statusBarContentInsetsProviderImpl.getStatusBarContentAreaForRotation(RotationUtils.getExactRotation(statusBarContentInsetsProviderImpl.context));
            this.label = 1;
            if (flowCollector.emit(statusBarContentAreaForRotation, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
