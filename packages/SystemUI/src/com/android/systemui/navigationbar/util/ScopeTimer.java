package com.android.systemui.navigationbar.util;

import com.android.systemui.navigationbar.gestural.MotionPauseDetector$$ExternalSyntheticLambda0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes2.dex */
public final class ScopeTimer {
    public StandaloneCoroutine job;
    public final CoroutineScope scope;

    /* renamed from: com.android.systemui.navigationbar.util.ScopeTimer$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function0 $action;
        final /* synthetic */ long $delay;
        int label;
        final /* synthetic */ ScopeTimer this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(long j, Function0 function0, ScopeTimer scopeTimer, Continuation continuation) {
            super(2, continuation);
            this.$delay = j;
            this.$action = function0;
            this.this$0 = scopeTimer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$delay, this.$action, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                long j = this.$delay;
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
            this.$action.invoke();
            this.this$0.cancel();
            return Unit.INSTANCE;
        }
    }

    public ScopeTimer(CoroutineScope coroutineScope) {
        this.scope = coroutineScope;
    }

    public final void cancel() {
        StandaloneCoroutine standaloneCoroutine = this.job;
        if (standaloneCoroutine == null || !standaloneCoroutine.isActive()) {
            return;
        }
        StandaloneCoroutine standaloneCoroutine2 = this.job;
        if (standaloneCoroutine2 != null) {
            standaloneCoroutine2.cancel(null);
        }
        this.job = null;
    }

    public final void start(long j, MotionPauseDetector$$ExternalSyntheticLambda0 motionPauseDetector$$ExternalSyntheticLambda0) {
        cancel();
        this.job = BuildersKt.launch$default(this.scope, null, null, new AnonymousClass1(j, motionPauseDetector$$ExternalSyntheticLambda0, this, null), 3);
    }
}
