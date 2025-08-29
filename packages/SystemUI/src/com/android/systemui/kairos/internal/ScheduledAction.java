package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.util.Maybe;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;

/* loaded from: classes2.dex */
public final class ScheduledAction {
    public final CompletableDeferred onResult;
    public final Function2 onStartTransaction;
    public Maybe result;

    /* renamed from: com.android.systemui.kairos.internal.ScheduledAction$started$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ScheduledAction.this.started(null, this);
        }
    }

    public ScheduledAction(String str, CompletableDeferred completableDeferred, Function2 function2) {
        this.onResult = completableDeferred;
        this.onStartTransaction = function2;
        Maybe.Companion.getClass();
        this.result = Maybe.Companion.absent;
    }

    public final void completed() {
        CompletableDeferred completableDeferred = this.onResult;
        if (completableDeferred != null) {
            Maybe maybe = this.result;
            if (maybe instanceof Maybe.Present) {
                ((CompletableDeferredImpl) completableDeferred).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(((Maybe.Present) maybe).value);
            }
        }
        Maybe.Companion.getClass();
        this.result = Maybe.Companion.absent;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object started(EvalScope evalScope, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        Maybe.Companion companion;
        Object objInvoke;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            companion = Maybe.Companion;
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = companion;
            anonymousClass1.label = 1;
            objInvoke = this.onStartTransaction.invoke(evalScope, anonymousClass1);
            if (objInvoke == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Maybe.Companion companion2 = (Maybe.Companion) anonymousClass1.L$1;
            ScheduledAction scheduledAction = (ScheduledAction) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            companion = companion2;
            this = scheduledAction;
            objInvoke = obj;
        }
        companion.getClass();
        this.result = Maybe.Present.m2588boximpl(objInvoke);
        return Unit.INSTANCE;
    }

    public /* synthetic */ ScheduledAction(String str, CompletableDeferred completableDeferred, Function2 function2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : completableDeferred, function2);
    }
}
