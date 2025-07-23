package com.android.systemui.util.kotlin;

import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class JavaAdapter$callSuspend$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Object $arg;
    final /* synthetic */ Function1 $onCancel;
    final /* synthetic */ Function1 $onFailure;
    final /* synthetic */ Function1 $onSuccess;
    final /* synthetic */ Function2 $suspendFunction;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaAdapter$callSuspend$1(Function2 function2, Object obj, Function1 function1, Function1 function12, Function1 function13, Continuation continuation) {
        super(2, continuation);
        this.$suspendFunction = function2;
        this.$arg = obj;
        this.$onCancel = function1;
        this.$onFailure = function12;
        this.$onSuccess = function13;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new JavaAdapter$callSuspend$1(this.$suspendFunction, this.$arg, this.$onCancel, this.$onFailure, this.$onSuccess, continuation);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v8, types: [java.lang.Object, kotlin.Unit] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Function2 function2 = this.$suspendFunction;
                Object obj2 = this.$arg;
                this.label = 1;
                obj = function2.invoke(obj2, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            this.$onSuccess.mo779invoke(obj);
            this = Unit.INSTANCE;
            return this;
        } catch (CancellationException e) {
            this.$onCancel.mo779invoke(e);
            return Unit.INSTANCE;
        } catch (Throwable th) {
            this.$onFailure.mo779invoke(th);
            return Unit.INSTANCE;
        }
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((JavaAdapter$callSuspend$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
