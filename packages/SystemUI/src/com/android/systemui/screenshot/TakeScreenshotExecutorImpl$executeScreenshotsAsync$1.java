package com.android.systemui.screenshot;

import android.net.Uri;
import com.android.internal.util.ScreenshotRequest;
import com.android.systemui.screenshot.TakeScreenshotService;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class TakeScreenshotExecutorImpl$executeScreenshotsAsync$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Consumer<Uri> $onSaved;
    final /* synthetic */ TakeScreenshotService.RequestCallback $requestCallback;
    final /* synthetic */ ScreenshotRequest $screenshotRequest;
    int label;
    final /* synthetic */ TakeScreenshotExecutorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TakeScreenshotExecutorImpl$executeScreenshotsAsync$1(TakeScreenshotExecutorImpl takeScreenshotExecutorImpl, ScreenshotRequest screenshotRequest, TakeScreenshotService.RequestCallback requestCallback, Consumer<Uri> consumer, Continuation continuation) {
        super(2, continuation);
        this.this$0 = takeScreenshotExecutorImpl;
        this.$screenshotRequest = screenshotRequest;
        this.$requestCallback = requestCallback;
        this.$onSaved = consumer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TakeScreenshotExecutorImpl$executeScreenshotsAsync$1(this.this$0, this.$screenshotRequest, this.$requestCallback, this.$onSaved, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TakeScreenshotExecutorImpl$executeScreenshotsAsync$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            TakeScreenshotExecutorImpl takeScreenshotExecutorImpl = this.this$0;
            ScreenshotRequest screenshotRequest = this.$screenshotRequest;
            final Consumer<Uri> consumer = this.$onSaved;
            Function1 function1 = new Function1() { // from class: com.android.systemui.screenshot.TakeScreenshotExecutorImpl$executeScreenshotsAsync$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    consumer.accept((Uri) obj2);
                    return Unit.INSTANCE;
                }
            };
            TakeScreenshotService.RequestCallback requestCallback = this.$requestCallback;
            this.label = 1;
            if (takeScreenshotExecutorImpl.executeScreenshots(screenshotRequest, function1, requestCallback, this) == coroutineSingletons) {
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
