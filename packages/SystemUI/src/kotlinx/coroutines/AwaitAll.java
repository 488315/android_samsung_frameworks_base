package kotlinx.coroutines;

import java.util.ArrayList;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class AwaitAll {
    public final Deferred[] deferreds;
    public final AtomicInt notCompletedCount;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AwaitAllNode extends JobNode {
        public final AtomicRef _disposer = AtomicFU.atomic((Object) null);
        public final CancellableContinuation continuation;
        public DisposableHandle handle;

        public AwaitAllNode(CancellableContinuation cancellableContinuation) {
            this.continuation = cancellableContinuation;
        }

        @Override // kotlinx.coroutines.JobNode
        public final boolean getOnCancelling() {
            return false;
        }

        @Override // kotlinx.coroutines.JobNode
        public final void invoke(Throwable th) {
            if (th != null) {
                Symbol tryResumeWithException = this.continuation.tryResumeWithException(th);
                if (tryResumeWithException != null) {
                    this.continuation.completeResume(tryResumeWithException);
                    DisposeHandlersOnCancel disposeHandlersOnCancel = (DisposeHandlersOnCancel) this._disposer.value;
                    if (disposeHandlersOnCancel != null) {
                        disposeHandlersOnCancel.disposeAll();
                        return;
                    }
                    return;
                }
                return;
            }
            if (AwaitAll.this.notCompletedCount.decrementAndGet() == 0) {
                CancellableContinuation cancellableContinuation = this.continuation;
                Deferred[] deferredArr = AwaitAll.this.deferreds;
                ArrayList arrayList = new ArrayList(deferredArr.length);
                for (Deferred deferred : deferredArr) {
                    arrayList.add(deferred.getCompleted());
                }
                int i = Result.$r8$clinit;
                cancellableContinuation.resumeWith(arrayList);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DisposeHandlersOnCancel implements CancelHandler {
        public final AwaitAllNode[] nodes;

        public DisposeHandlersOnCancel(AwaitAll awaitAll, AwaitAllNode[] awaitAllNodeArr) {
            this.nodes = awaitAllNodeArr;
        }

        public final void disposeAll() {
            for (AwaitAllNode awaitAllNode : this.nodes) {
                DisposableHandle disposableHandle = awaitAllNode.handle;
                if (disposableHandle == null) {
                    disposableHandle = null;
                }
                disposableHandle.dispose();
            }
        }

        @Override // kotlinx.coroutines.CancelHandler
        public final void invoke(Throwable th) {
            disposeAll();
        }

        public final String toString() {
            return "DisposeHandlersOnCancel[" + this.nodes + "]";
        }
    }

    public AwaitAll(Deferred[] deferredArr) {
        this.deferreds = deferredArr;
        this.notCompletedCount = AtomicFU.atomic(deferredArr.length);
    }

    public final Object await(SuspendLambda suspendLambda) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(suspendLambda), 1);
        cancellableContinuationImpl.initCancellability();
        Job[] jobArr = this.deferreds;
        int length = jobArr.length;
        AwaitAllNode[] awaitAllNodeArr = new AwaitAllNode[length];
        for (int i = 0; i < length; i++) {
            Job job = jobArr[i];
            ((JobSupport) job).start();
            AwaitAllNode awaitAllNode = new AwaitAllNode(cancellableContinuationImpl);
            awaitAllNode.handle = JobKt.invokeOnCompletion$default(job, awaitAllNode);
            Unit unit = Unit.INSTANCE;
            awaitAllNodeArr[i] = awaitAllNode;
        }
        DisposeHandlersOnCancel disposeHandlersOnCancel = new DisposeHandlersOnCancel(this, awaitAllNodeArr);
        for (int i2 = 0; i2 < length; i2++) {
            awaitAllNodeArr[i2]._disposer.setValue(disposeHandlersOnCancel);
        }
        if (cancellableContinuationImpl.isCompleted()) {
            disposeHandlersOnCancel.disposeAll();
        } else {
            cancellableContinuationImpl.invokeOnCancellationImpl(disposeHandlersOnCancel);
        }
        Object result = cancellableContinuationImpl.getResult();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return result;
    }
}
