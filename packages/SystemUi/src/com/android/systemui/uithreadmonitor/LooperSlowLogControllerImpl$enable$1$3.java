package com.android.systemui.uithreadmonitor;

import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl$enable$1$3", m277f = "LooperSlowLogControllerImpl.kt", m278l = {112}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class LooperSlowLogControllerImpl$enable$1$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ long $durMs;
    final /* synthetic */ int $type;
    int label;
    final /* synthetic */ LooperSlowLogControllerImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LooperSlowLogControllerImpl$enable$1$3(long j, LooperSlowLogControllerImpl looperSlowLogControllerImpl, int i, Continuation<? super LooperSlowLogControllerImpl$enable$1$3> continuation) {
        super(2, continuation);
        this.$durMs = j;
        this.this$0 = looperSlowLogControllerImpl;
        this.$type = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LooperSlowLogControllerImpl$enable$1$3(this.$durMs, this.this$0, this.$type, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LooperSlowLogControllerImpl$enable$1$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            long j = this.$durMs;
            this.label = 1;
            if (DelayKt.delay(j, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        LooperSlowLogControllerImpl looperSlowLogControllerImpl = this.this$0;
        String m0m = AbstractC0000x2c234b15.m0m("expired type=", this.$type);
        int i2 = LooperSlowLogControllerImpl.$r8$clinit;
        if (looperSlowLogControllerImpl.debug) {
            Log.d("LooperSlow", m0m);
        }
        this.this$0.disable(this.$type);
        return Unit.INSTANCE;
    }
}
