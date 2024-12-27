package com.android.systemui.screenshot;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

final class ScreenshotSoundControllerImpl$releaseScreenshotSoundAsync$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ScreenshotSoundControllerImpl this$0;

    public ScreenshotSoundControllerImpl$releaseScreenshotSoundAsync$1(ScreenshotSoundControllerImpl screenshotSoundControllerImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = screenshotSoundControllerImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ScreenshotSoundControllerImpl$releaseScreenshotSoundAsync$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenshotSoundControllerImpl$releaseScreenshotSoundAsync$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ScreenshotSoundControllerImpl screenshotSoundControllerImpl = this.this$0;
            this.label = 1;
            screenshotSoundControllerImpl.getClass();
            Object withContext = BuildersKt.withContext(screenshotSoundControllerImpl.bgDispatcher, new ScreenshotSoundControllerImpl$releaseScreenshotSound$2(screenshotSoundControllerImpl, null), this);
            if (withContext != obj2) {
                withContext = Unit.INSTANCE;
            }
            if (withContext == obj2) {
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
