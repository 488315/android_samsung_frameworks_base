package com.android.systemui.statusbar.core;

import com.android.systemui.statusbar.data.model.StatusBarMode;
import com.android.systemui.statusbar.window.shared.model.StatusBarWindowState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class StatusBarOrchestrator$statusBarVisible$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public StatusBarOrchestrator$statusBarVisible$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        StatusBarOrchestrator$statusBarVisible$1 statusBarOrchestrator$statusBarVisible$1 = new StatusBarOrchestrator$statusBarVisible$1((Continuation) obj3);
        statusBarOrchestrator$statusBarVisible$1.L$0 = (StatusBarMode) obj;
        statusBarOrchestrator$statusBarVisible$1.L$1 = (StatusBarWindowState) obj2;
        return statusBarOrchestrator$statusBarVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        StatusBarMode statusBarMode = (StatusBarMode) this.L$0;
        return Boolean.valueOf((statusBarMode == StatusBarMode.LIGHTS_OUT || statusBarMode == StatusBarMode.LIGHTS_OUT_TRANSPARENT || ((StatusBarWindowState) this.L$1) == StatusBarWindowState.Hidden) ? false : true);
    }
}
