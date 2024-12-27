package com.android.systemui.screenshot;

import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.plugins.ActivityStarter;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class ScreenshotProxyService$executeAfterDismissing$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ IOnDoneCallback $callback;
    int label;
    final /* synthetic */ ScreenshotProxyService this$0;

    public ScreenshotProxyService$executeAfterDismissing$2(ScreenshotProxyService screenshotProxyService, IOnDoneCallback iOnDoneCallback, Continuation continuation) {
        super(2, continuation);
        this.this$0 = screenshotProxyService;
        this.$callback = iOnDoneCallback;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ScreenshotProxyService$executeAfterDismissing$2(this.this$0, this.$callback, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenshotProxyService$executeAfterDismissing$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ActivityStarter activityStarter = this.this$0.activityStarter;
        final IOnDoneCallback iOnDoneCallback = this.$callback;
        activityStarter.executeRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotProxyService$executeAfterDismissing$2.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    IOnDoneCallback.this.onDone(true);
                } catch (RemoteException e) {
                    Log.w("ScreenshotProxyService", "Failed to complete callback transaction", e);
                }
            }
        }, null, true, true, true);
        return Unit.INSTANCE;
    }
}
