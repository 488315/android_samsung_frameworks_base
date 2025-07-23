package com.android.systemui.mediaprojection.data.repository;

import android.view.ContentRecordingSession;
import android.view.Display;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MediaProjectionManagerRepository$stateForSession$hostDeviceName$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ContentRecordingSession $session;
    int label;
    final /* synthetic */ MediaProjectionManagerRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaProjectionManagerRepository$stateForSession$hostDeviceName$1(MediaProjectionManagerRepository mediaProjectionManagerRepository, ContentRecordingSession contentRecordingSession, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaProjectionManagerRepository;
        this.$session = contentRecordingSession;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaProjectionManagerRepository$stateForSession$hostDeviceName$1(this.this$0, this.$session, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaProjectionManagerRepository$stateForSession$hostDeviceName$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Display display = this.this$0.displayManager.getDisplay(this.$session.getVirtualDisplayId());
        if (display != null) {
            return display.getName();
        }
        return null;
    }
}
