package com.android.systemui.screenshot;

import android.net.Uri;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class DefaultScreenshotActionsProvider$2$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DefaultScreenshotActionsProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultScreenshotActionsProvider$2$2(DefaultScreenshotActionsProvider defaultScreenshotActionsProvider, Continuation continuation) {
        super(2, continuation);
        this.this$0 = defaultScreenshotActionsProvider;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DefaultScreenshotActionsProvider$2$2 defaultScreenshotActionsProvider$2$2 = new DefaultScreenshotActionsProvider$2$2(this.this$0, continuation);
        defaultScreenshotActionsProvider$2$2.L$0 = obj;
        return defaultScreenshotActionsProvider$2$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DefaultScreenshotActionsProvider$2$2) create((ScreenshotSavedResult) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ScreenshotSavedResult screenshotSavedResult = (ScreenshotSavedResult) this.L$0;
        DefaultScreenshotActionsProvider defaultScreenshotActionsProvider = this.this$0;
        Uri uri = defaultScreenshotActionsProvider.webUri;
        Uri uri2 = screenshotSavedResult.uri;
        defaultScreenshotActionsProvider.actionIntentCreator.getClass();
        this.this$0.actionExecutor.startSharedTransition(ActionIntentCreator.createShare(uri2, screenshotSavedResult.subject, null), screenshotSavedResult.user, false);
        return Unit.INSTANCE;
    }
}
