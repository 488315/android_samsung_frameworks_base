package com.android.systemui.window.ui.viewmodel;

import java.util.Objects;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
final class WindowRootViewModel$Companion$logIfPossible$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $loggingInfo;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WindowRootViewModel$Companion$logIfPossible$1(String str, Continuation continuation) {
        super(2, continuation);
        this.$loggingInfo = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WindowRootViewModel$Companion$logIfPossible$1 windowRootViewModel$Companion$logIfPossible$1 = new WindowRootViewModel$Companion$logIfPossible$1(this.$loggingInfo, continuation);
        windowRootViewModel$Companion$logIfPossible$1.L$0 = obj;
        return windowRootViewModel$Companion$logIfPossible$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WindowRootViewModel$Companion$logIfPossible$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Object obj2 = this.L$0;
        WindowRootViewModel.Companion.getClass();
        if (WindowRootViewModel.isLoggable) {
            Objects.toString(obj2);
        }
        return Unit.INSTANCE;
    }
}
