package com.android.systemui.screenshot.proxy;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class ScreenshotProxyService$mBinder$1$dismissKeyguard$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ IOnDoneCallback $callback;
    int label;
    final /* synthetic */ ScreenshotProxyService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenshotProxyService$mBinder$1$dismissKeyguard$1(ScreenshotProxyService screenshotProxyService, IOnDoneCallback iOnDoneCallback, Continuation continuation) {
        super(2, continuation);
        this.this$0 = screenshotProxyService;
        this.$callback = iOnDoneCallback;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ScreenshotProxyService$mBinder$1$dismissKeyguard$1(this.this$0, this.$callback, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenshotProxyService$mBinder$1$dismissKeyguard$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ScreenshotProxyService screenshotProxyService = this.this$0;
            IOnDoneCallback iOnDoneCallback = this.$callback;
            this.label = 1;
            Object objWithContext = BuildersKt.withContext(screenshotProxyService.mMainDispatcher, new ScreenshotProxyService$executeAfterDismissing$2(screenshotProxyService, iOnDoneCallback, null), this);
            if (objWithContext != obj2) {
                objWithContext = Unit.INSTANCE;
            }
            if (objWithContext == obj2) {
                return obj2;
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
