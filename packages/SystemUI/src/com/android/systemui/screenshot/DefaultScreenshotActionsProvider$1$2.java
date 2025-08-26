package com.android.systemui.screenshot;

import android.content.Intent;
import android.net.Uri;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
final class DefaultScreenshotActionsProvider$1$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ DefaultScreenshotActionsProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultScreenshotActionsProvider$1$2(DefaultScreenshotActionsProvider defaultScreenshotActionsProvider, Continuation continuation) {
        super(2, continuation);
        this.this$0 = defaultScreenshotActionsProvider;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DefaultScreenshotActionsProvider$1$2 defaultScreenshotActionsProvider$1$2 = new DefaultScreenshotActionsProvider$1$2(this.this$0, continuation);
        defaultScreenshotActionsProvider$1$2.L$0 = obj;
        return defaultScreenshotActionsProvider$1$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DefaultScreenshotActionsProvider$1$2) create((ScreenshotSavedResult) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ScreenshotSavedResult screenshotSavedResult;
        ActionExecutor actionExecutor;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ScreenshotSavedResult screenshotSavedResult2 = (ScreenshotSavedResult) this.L$0;
            DefaultScreenshotActionsProvider defaultScreenshotActionsProvider = this.this$0;
            ActionExecutor actionExecutor2 = defaultScreenshotActionsProvider.actionExecutor;
            Uri uri = screenshotSavedResult2.uri;
            this.L$0 = screenshotSavedResult2;
            this.L$1 = actionExecutor2;
            this.label = 1;
            Object objCreateEdit = defaultScreenshotActionsProvider.actionIntentCreator.createEdit(uri, this);
            if (objCreateEdit == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj = objCreateEdit;
            screenshotSavedResult = screenshotSavedResult2;
            actionExecutor = actionExecutor2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            actionExecutor = (ActionExecutor) this.L$1;
            screenshotSavedResult = (ScreenshotSavedResult) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        actionExecutor.startSharedTransition((Intent) obj, screenshotSavedResult.user, true);
        return Unit.INSTANCE;
    }
}
