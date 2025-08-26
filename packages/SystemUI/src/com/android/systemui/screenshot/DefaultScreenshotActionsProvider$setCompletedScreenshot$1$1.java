package com.android.systemui.screenshot;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class DefaultScreenshotActionsProvider$setCompletedScreenshot$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $it;
    final /* synthetic */ ScreenshotSavedResult $result;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultScreenshotActionsProvider$setCompletedScreenshot$1$1(Function2 function2, ScreenshotSavedResult screenshotSavedResult, Continuation continuation) {
        super(2, continuation);
        this.$it = function2;
        this.$result = screenshotSavedResult;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DefaultScreenshotActionsProvider$setCompletedScreenshot$1$1(this.$it, this.$result, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DefaultScreenshotActionsProvider$setCompletedScreenshot$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Function2 function2 = this.$it;
            ScreenshotSavedResult screenshotSavedResult = this.$result;
            this.label = 1;
            if (function2.invoke(screenshotSavedResult, this) == coroutineSingletons) {
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
