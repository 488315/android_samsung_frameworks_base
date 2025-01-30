package kotlinx.coroutines;

import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.internal.DispatchedContinuationKt;
import kotlinx.coroutines.internal.ThreadContextKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class AbstractCoroutine extends JobSupport implements Continuation, CoroutineScope {
    public final CoroutineContext context;

    public AbstractCoroutine(CoroutineContext coroutineContext, boolean z, boolean z2) {
        super(z2);
        if (z) {
            initParentJob((Job) coroutineContext.get(Job.Key));
        }
        this.context = coroutineContext.plus(this);
    }

    @Override // kotlinx.coroutines.JobSupport
    public final String cancellationExceptionMessage() {
        return DebugStringsKt.getClassSimpleName(this).concat(" was cancelled");
    }

    @Override // kotlin.coroutines.Continuation
    public final CoroutineContext getContext() {
        return this.context;
    }

    @Override // kotlinx.coroutines.CoroutineScope
    public final CoroutineContext getCoroutineContext() {
        return this.context;
    }

    @Override // kotlinx.coroutines.JobSupport
    /* renamed from: handleOnCompletionException$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final void mo281x4a3c0b24(CompletionHandlerException completionHandlerException) {
        CoroutineExceptionHandlerKt.handleCoroutineException(completionHandlerException, this.context);
    }

    @Override // kotlinx.coroutines.JobSupport, kotlinx.coroutines.Job
    public boolean isActive() {
        return super.isActive();
    }

    @Override // kotlinx.coroutines.JobSupport
    /* renamed from: nameString$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public String mo282x29b568d4() {
        return DebugStringsKt.getClassSimpleName(this);
    }

    @Override // kotlinx.coroutines.JobSupport
    public final void onCompletionInternal(Object obj) {
        if (!(obj instanceof CompletedExceptionally)) {
            onCompleted(obj);
        } else {
            CompletedExceptionally completedExceptionally = (CompletedExceptionally) obj;
            onCancelled(completedExceptionally.cause, completedExceptionally._handled._value != 0);
        }
    }

    @Override // kotlin.coroutines.Continuation
    public final void resumeWith(Object obj) {
        Throwable m2859exceptionOrNullimpl = Result.m2859exceptionOrNullimpl(obj);
        if (m2859exceptionOrNullimpl != null) {
            obj = new CompletedExceptionally(m2859exceptionOrNullimpl, false, 2, null);
        }
        Object m294xe12a6b8b = m294xe12a6b8b(obj);
        if (m294xe12a6b8b == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return;
        }
        afterResume(m294xe12a6b8b);
    }

    public final void start(CoroutineStart coroutineStart, AbstractCoroutine abstractCoroutine, Function2 function2) {
        int i = CoroutineStart.WhenMappings.$EnumSwitchMapping$0[coroutineStart.ordinal()];
        if (i == 1) {
            try {
                Continuation intercepted = IntrinsicsKt__IntrinsicsJvmKt.intercepted(IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted(this, function2, abstractCoroutine));
                int i2 = Result.$r8$clinit;
                DispatchedContinuationKt.resumeCancellableWith(intercepted, Unit.INSTANCE, null);
                return;
            } finally {
                int i3 = Result.$r8$clinit;
                resumeWith(new Result.Failure(th));
            }
        }
        if (i == 2) {
            Continuation intercepted2 = IntrinsicsKt__IntrinsicsJvmKt.intercepted(IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted(this, function2, abstractCoroutine));
            int i4 = Result.$r8$clinit;
            intercepted2.resumeWith(Unit.INSTANCE);
            return;
        }
        if (i != 3) {
            if (i != 4) {
                throw new NoWhenBranchMatchedException();
            }
            return;
        }
        try {
            CoroutineContext coroutineContext = this.context;
            Object updateThreadContext = ThreadContextKt.updateThreadContext(coroutineContext, null);
            try {
                TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
                Object invoke = function2.invoke(abstractCoroutine, this);
                if (invoke != CoroutineSingletons.COROUTINE_SUSPENDED) {
                    int i5 = Result.$r8$clinit;
                    resumeWith(invoke);
                }
            } finally {
                ThreadContextKt.restoreThreadContext(coroutineContext, updateThreadContext);
            }
        } catch (Throwable th) {
        }
    }

    public void onCompleted(Object obj) {
    }

    public void onCancelled(Throwable th, boolean z) {
    }
}
