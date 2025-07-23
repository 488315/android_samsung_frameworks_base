package com.android.systemui.screenshot;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ScreenshotSoundControllerImpl$playScreenshotSound$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ScreenshotSoundControllerImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenshotSoundControllerImpl$playScreenshotSound$2(ScreenshotSoundControllerImpl screenshotSoundControllerImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = screenshotSoundControllerImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ScreenshotSoundControllerImpl$playScreenshotSound$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenshotSoundControllerImpl$playScreenshotSound$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x005a, code lost:
    
        if (r5 == r0) goto L27;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L1f
            if (r1 == r4) goto L19
            if (r1 != r3) goto L11
            kotlin.ResultKt.throwOnFailure(r6)
            goto L5d
        L11:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L19:
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.IllegalStateException -> L1d
            goto L2f
        L1d:
            r6 = move-exception
            goto L3a
        L1f:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.screenshot.ScreenshotSoundControllerImpl r6 = r5.this$0     // Catch: java.lang.IllegalStateException -> L1d
            kotlinx.coroutines.DeferredCoroutine r6 = r6.player     // Catch: java.lang.IllegalStateException -> L1d
            r5.label = r4     // Catch: java.lang.IllegalStateException -> L1d
            java.lang.Object r6 = r6.awaitInternal(r5)     // Catch: java.lang.IllegalStateException -> L1d
            if (r6 != r0) goto L2f
            goto L5c
        L2f:
            android.media.MediaPlayer r6 = (android.media.MediaPlayer) r6     // Catch: java.lang.IllegalStateException -> L1d
            if (r6 == 0) goto L39
            r6.start()     // Catch: java.lang.IllegalStateException -> L1d
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch: java.lang.IllegalStateException -> L1d
            return r5
        L39:
            return r2
        L3a:
            java.lang.String r1 = "ScreenshotSoundControllerImpl"
            java.lang.String r4 = "Screenshot sound failed to play"
            android.util.Log.w(r1, r4, r6)
            com.android.systemui.screenshot.ScreenshotSoundControllerImpl r6 = r5.this$0
            r5.label = r3
            r6.getClass()
            com.android.systemui.screenshot.ScreenshotSoundControllerImpl$releaseScreenshotSound$2 r1 = new com.android.systemui.screenshot.ScreenshotSoundControllerImpl$releaseScreenshotSound$2
            r1.<init>(r6, r2)
            kotlinx.coroutines.CoroutineDispatcher r6 = r6.bgDispatcher
            java.lang.Object r5 = kotlinx.coroutines.BuildersKt.withContext(r6, r1, r5)
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            if (r5 != r6) goto L58
            goto L5a
        L58:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
        L5a:
            if (r5 != r0) goto L5d
        L5c:
            return r0
        L5d:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.ScreenshotSoundControllerImpl$playScreenshotSound$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
