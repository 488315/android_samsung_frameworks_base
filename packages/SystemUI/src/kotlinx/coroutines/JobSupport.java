package kotlinx.coroutines;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.atomicfu.TraceBase;
import kotlinx.coroutines.internal.ListClosed;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class JobSupport implements Job, ChildJob {
    public final AtomicRef _parentHandle;
    public final AtomicRef _state;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AwaitContinuation extends CancellableContinuationImpl {
        public final JobSupport job;

        public AwaitContinuation(Continuation continuation, JobSupport jobSupport) {
            super(continuation, 1);
            this.job = jobSupport;
        }

        @Override // kotlinx.coroutines.CancellableContinuationImpl
        public final Throwable getContinuationCancellationCause(JobSupport jobSupport) {
            Throwable rootCause;
            Object obj = this.job._state.value;
            return (!(obj instanceof Finishing) || (rootCause = ((Finishing) obj).getRootCause()) == null) ? obj instanceof CompletedExceptionally ? ((CompletedExceptionally) obj).cause : jobSupport.getCancellationException() : rootCause;
        }

        @Override // kotlinx.coroutines.CancellableContinuationImpl
        public final String nameString() {
            return "AwaitContinuation";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ChildCompletion extends JobNode {
        public final ChildHandleNode child;
        public final JobSupport parent;
        public final Object proposedUpdate;
        public final Finishing state;

        public ChildCompletion(JobSupport jobSupport, Finishing finishing, ChildHandleNode childHandleNode, Object obj) {
            this.parent = jobSupport;
            this.state = finishing;
            this.child = childHandleNode;
            this.proposedUpdate = obj;
        }

        @Override // kotlinx.coroutines.JobNode
        public final boolean getOnCancelling() {
            return false;
        }

        @Override // kotlinx.coroutines.JobNode
        public final void invoke(Throwable th) {
            JobSupport jobSupport = this.parent;
            jobSupport.getClass();
            ChildHandleNode childHandleNode = this.child;
            ChildHandleNode nextChild = JobSupport.nextChild(childHandleNode);
            Finishing finishing = this.state;
            Object obj = this.proposedUpdate;
            if (nextChild == null || !jobSupport.tryWaitForChild(finishing, nextChild, obj)) {
                NodeList nodeList = finishing.list;
                nodeList.getClass();
                nodeList.addLast(new ListClosed(2), 2);
                ChildHandleNode nextChild2 = JobSupport.nextChild(childHandleNode);
                if (nextChild2 == null || !jobSupport.tryWaitForChild(finishing, nextChild2, obj)) {
                    jobSupport.afterCompletion(jobSupport.finalizeFinishingState(finishing, obj));
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Finishing implements Incomplete {
        public final AtomicRef _exceptionsHolder = AtomicFU.atomic((Object) null);
        public final AtomicBoolean _isCompleting;
        public final AtomicRef _rootCause;
        public final NodeList list;

        public Finishing(NodeList nodeList, boolean z, Throwable th) {
            this.list = nodeList;
            this._isCompleting = AtomicFU.atomic(z);
            this._rootCause = AtomicFU.atomic(th);
        }

        public final void addExceptionLocked(Throwable th) {
            Throwable rootCause = getRootCause();
            if (rootCause == null) {
                this._rootCause.setValue(th);
                return;
            }
            if (th == rootCause) {
                return;
            }
            Object obj = this._exceptionsHolder.value;
            if (obj == null) {
                this._exceptionsHolder.setValue(th);
                return;
            }
            if (!(obj instanceof Throwable)) {
                if (obj instanceof ArrayList) {
                    ((ArrayList) obj).add(th);
                    return;
                } else {
                    throw new IllegalStateException(("State is " + obj).toString());
                }
            }
            if (th == obj) {
                return;
            }
            ArrayList arrayList = new ArrayList(4);
            arrayList.add(obj);
            arrayList.add(th);
            this._exceptionsHolder.setValue(arrayList);
        }

        @Override // kotlinx.coroutines.Incomplete
        public final NodeList getList() {
            return this.list;
        }

        public final Throwable getRootCause() {
            return (Throwable) this._rootCause.value;
        }

        @Override // kotlinx.coroutines.Incomplete
        public final boolean isActive() {
            return getRootCause() == null;
        }

        public final boolean isCancelling() {
            return getRootCause() != null;
        }

        public final List sealLocked(Throwable th) {
            ArrayList arrayList;
            Object obj = this._exceptionsHolder.value;
            if (obj == null) {
                arrayList = new ArrayList(4);
            } else if (obj instanceof Throwable) {
                ArrayList arrayList2 = new ArrayList(4);
                arrayList2.add(obj);
                arrayList = arrayList2;
            } else {
                if (!(obj instanceof ArrayList)) {
                    throw new IllegalStateException(("State is " + obj).toString());
                }
                arrayList = (ArrayList) obj;
            }
            Throwable rootCause = getRootCause();
            if (rootCause != null) {
                arrayList.add(0, rootCause);
            }
            if (th != null && !th.equals(rootCause)) {
                arrayList.add(th);
            }
            this._exceptionsHolder.setValue(JobSupportKt.SEALED);
            return arrayList;
        }

        public final String toString() {
            boolean isCancelling = isCancelling();
            boolean value = this._isCompleting.getValue();
            Throwable rootCause = getRootCause();
            Object obj = this._exceptionsHolder.value;
            NodeList nodeList = this.list;
            StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("Finishing[cancelling=", ", completing=", ", rootCause=", isCancelling, value);
            m.append(rootCause);
            m.append(", exceptions=");
            m.append(obj);
            m.append(", list=");
            m.append(nodeList);
            m.append("]");
            return m.toString();
        }
    }

    public JobSupport(boolean z) {
        this._state = AtomicFU.atomic(z ? JobSupportKt.EMPTY_ACTIVE : JobSupportKt.EMPTY_NEW);
        this._parentHandle = AtomicFU.atomic((Object) null);
    }

    public static ChildHandleNode nextChild(LockFreeLinkedListNode lockFreeLinkedListNode) {
        while (lockFreeLinkedListNode.isRemoved()) {
            LockFreeLinkedListNode correctPrev = lockFreeLinkedListNode.correctPrev();
            if (correctPrev == null) {
                Object obj = lockFreeLinkedListNode._prev.value;
                while (true) {
                    lockFreeLinkedListNode = (LockFreeLinkedListNode) obj;
                    if (!lockFreeLinkedListNode.isRemoved()) {
                        break;
                    }
                    obj = lockFreeLinkedListNode._prev.value;
                }
            } else {
                lockFreeLinkedListNode = correctPrev;
            }
        }
        while (true) {
            lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode();
            if (!lockFreeLinkedListNode.isRemoved()) {
                if (lockFreeLinkedListNode instanceof ChildHandleNode) {
                    return (ChildHandleNode) lockFreeLinkedListNode;
                }
                if (lockFreeLinkedListNode instanceof NodeList) {
                    return null;
                }
            }
        }
    }

    public static String stateString(Object obj) {
        if (!(obj instanceof Finishing)) {
            return obj instanceof Incomplete ? ((Incomplete) obj).isActive() ? "Active" : "New" : obj instanceof CompletedExceptionally ? "Cancelled" : "Completed";
        }
        Finishing finishing = (Finishing) obj;
        return finishing.isCancelling() ? "Cancelling" : finishing._isCompleting.getValue() ? "Completing" : "Active";
    }

    public void afterResume(Object obj) {
        afterCompletion(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v3, types: [kotlinx.coroutines.InactiveNodeList] */
    @Override // kotlinx.coroutines.Job
    public final ChildHandle attachChild(JobSupport jobSupport) {
        ChildHandleNode childHandleNode = new ChildHandleNode(jobSupport);
        childHandleNode.job = this;
        while (true) {
            Object obj = this._state.value;
            if (obj instanceof Empty) {
                Empty empty = (Empty) obj;
                if (!empty.isActive) {
                    NodeList nodeList = new NodeList();
                    if (!empty.isActive) {
                        nodeList = new InactiveNodeList(nodeList);
                    }
                    this._state.compareAndSet(empty, nodeList);
                } else if (this._state.compareAndSet(obj, childHandleNode)) {
                    break;
                }
            } else {
                if (!(obj instanceof Incomplete)) {
                    Object obj2 = this._state.value;
                    CompletedExceptionally completedExceptionally = obj2 instanceof CompletedExceptionally ? (CompletedExceptionally) obj2 : null;
                    childHandleNode.invoke(completedExceptionally != null ? completedExceptionally.cause : null);
                    return NonDisposableHandle.INSTANCE;
                }
                NodeList list = ((Incomplete) obj).getList();
                if (list == null) {
                    promoteSingleToNodeList((JobNode) obj);
                } else if (!list.addLast(childHandleNode, 7)) {
                    boolean addLast = list.addLast(childHandleNode, 3);
                    Object obj3 = this._state.value;
                    if (obj3 instanceof Finishing) {
                        r2 = ((Finishing) obj3).getRootCause();
                    } else {
                        CompletedExceptionally completedExceptionally2 = obj3 instanceof CompletedExceptionally ? (CompletedExceptionally) obj3 : null;
                        if (completedExceptionally2 != null) {
                            r2 = completedExceptionally2.cause;
                        }
                    }
                    childHandleNode.invoke(r2);
                    if (!addLast) {
                        return NonDisposableHandle.INSTANCE;
                    }
                }
            }
        }
        return childHandleNode;
    }

    public final Object awaitInternal(Continuation continuation) {
        Object obj;
        do {
            obj = this._state.value;
            if (!(obj instanceof Incomplete)) {
                if (obj instanceof CompletedExceptionally) {
                    throw ((CompletedExceptionally) obj).cause;
                }
                return JobSupportKt.unboxState(obj);
            }
        } while (startInternal(obj) < 0);
        AwaitContinuation awaitContinuation = new AwaitContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), this);
        awaitContinuation.initCancellability();
        awaitContinuation.invokeOnCancellationImpl(new DisposeOnCancel(JobKt.invokeOnCompletion$default(this, new ResumeAwaitOnCompletion(awaitContinuation))));
        Object result = awaitContinuation.getResult();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return result;
    }

    @Override // kotlinx.coroutines.Job
    public void cancel(CancellationException cancellationException) {
        if (cancellationException == null) {
            cancellationException = new JobCancellationException(cancellationExceptionMessage(), null, this);
        }
        cancelInternal(cancellationException);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x003a, code lost:
    
        if (r0 == kotlinx.coroutines.JobSupportKt.COMPLETING_WAITING_CHILDREN) goto L75;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean cancelImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(java.lang.Object r10) {
        /*
            Method dump skipped, instructions count: 261
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.cancelImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(java.lang.Object):boolean");
    }

    public void cancelInternal(Throwable th) {
        cancelImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(th);
    }

    public final boolean cancelParent(Throwable th) {
        if (isScopedCoroutine()) {
            return true;
        }
        boolean z = th instanceof CancellationException;
        ChildHandle childHandle = (ChildHandle) this._parentHandle.value;
        return (childHandle == null || childHandle == NonDisposableHandle.INSTANCE) ? z : childHandle.childCancelled(th) || z;
    }

    public String cancellationExceptionMessage() {
        return "Job was cancelled";
    }

    public boolean childCancelled(Throwable th) {
        if (th instanceof CancellationException) {
            return true;
        }
        return cancelImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(th) && getHandlesException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
    }

    public final void completeStateFinalization(Incomplete incomplete, Object obj) {
        ChildHandle childHandle = (ChildHandle) this._parentHandle.value;
        if (childHandle != null) {
            childHandle.dispose();
            this._parentHandle.setValue(NonDisposableHandle.INSTANCE);
        }
        CompletionHandlerException completionHandlerException = null;
        CompletedExceptionally completedExceptionally = obj instanceof CompletedExceptionally ? (CompletedExceptionally) obj : null;
        Throwable th = completedExceptionally != null ? completedExceptionally.cause : null;
        if (incomplete instanceof JobNode) {
            try {
                ((JobNode) incomplete).invoke(th);
                return;
            } catch (Throwable th2) {
                handleOnCompletionException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(new CompletionHandlerException("Exception in completion handler " + incomplete + " for " + this, th2));
                return;
            }
        }
        NodeList list = incomplete.getList();
        if (list != null) {
            list.addLast(new ListClosed(1), 1);
            for (LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) list._next.value; !Intrinsics.areEqual(lockFreeLinkedListNode, list); lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode()) {
                if (lockFreeLinkedListNode instanceof JobNode) {
                    try {
                        ((JobNode) lockFreeLinkedListNode).invoke(th);
                    } catch (Throwable th3) {
                        if (completionHandlerException != null) {
                            ExceptionsKt__ExceptionsKt.addSuppressed(completionHandlerException, th3);
                        } else {
                            completionHandlerException = new CompletionHandlerException("Exception in completion handler " + lockFreeLinkedListNode + " for " + this, th3);
                            Unit unit = Unit.INSTANCE;
                        }
                    }
                }
            }
            if (completionHandlerException != null) {
                handleOnCompletionException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(completionHandlerException);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v13, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v9, types: [java.lang.Throwable] */
    public final Throwable createCauseException(Object obj) {
        CancellationException cancellationException;
        if (obj == null ? true : obj instanceof Throwable) {
            Throwable th = (Throwable) obj;
            return th == null ? new JobCancellationException(cancellationExceptionMessage(), null, this) : th;
        }
        JobSupport jobSupport = (JobSupport) obj;
        Object obj2 = jobSupport._state.value;
        if (obj2 instanceof Finishing) {
            cancellationException = ((Finishing) obj2).getRootCause();
        } else if (obj2 instanceof CompletedExceptionally) {
            cancellationException = ((CompletedExceptionally) obj2).cause;
        } else {
            if (obj2 instanceof Incomplete) {
                throw new IllegalStateException(("Cannot be cancelling child in this state: " + obj2).toString());
            }
            cancellationException = null;
        }
        CancellationException cancellationException2 = cancellationException instanceof CancellationException ? cancellationException : null;
        return cancellationException2 == null ? new JobCancellationException("Parent job is ".concat(stateString(obj2)), cancellationException, jobSupport) : cancellationException2;
    }

    public final Object finalizeFinishingState(Finishing finishing, Object obj) {
        Throwable finalRootCause;
        DefaultConstructorMarker defaultConstructorMarker = null;
        CompletedExceptionally completedExceptionally = obj instanceof CompletedExceptionally ? (CompletedExceptionally) obj : null;
        Throwable th = completedExceptionally != null ? completedExceptionally.cause : null;
        synchronized (finishing) {
            finishing.isCancelling();
            List<Throwable> sealLocked = finishing.sealLocked(th);
            finalRootCause = getFinalRootCause(finishing, sealLocked);
            if (finalRootCause != null && sealLocked.size() > 1) {
                Set newSetFromMap = Collections.newSetFromMap(new IdentityHashMap(sealLocked.size()));
                for (Throwable th2 : sealLocked) {
                    if (th2 != finalRootCause && th2 != finalRootCause && !(th2 instanceof CancellationException) && newSetFromMap.add(th2)) {
                        ExceptionsKt__ExceptionsKt.addSuppressed(finalRootCause, th2);
                    }
                }
            }
        }
        if (finalRootCause != null && finalRootCause != th) {
            obj = new CompletedExceptionally(finalRootCause, false, 2, defaultConstructorMarker);
        }
        if (finalRootCause != null && (cancelParent(finalRootCause) || handleJobException(finalRootCause))) {
            ((CompletedExceptionally) obj)._handled.compareAndSet();
        }
        onCompletionInternal(obj);
        this._state.compareAndSet(finishing, obj instanceof Incomplete ? new IncompleteStateBox((Incomplete) obj) : obj);
        completeStateFinalization(finishing, obj);
        return obj;
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final Object fold(Object obj, Function2 function2) {
        return function2.invoke(obj, this);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext.Element get(CoroutineContext.Key key) {
        return CoroutineContext.DefaultImpls.get(this, key);
    }

    @Override // kotlinx.coroutines.Job
    public final CancellationException getCancellationException() {
        CancellationException cancellationException;
        Object obj = this._state.value;
        if (!(obj instanceof Finishing)) {
            if (obj instanceof Incomplete) {
                throw new IllegalStateException(("Job is still new or active: " + this).toString());
            }
            if (!(obj instanceof CompletedExceptionally)) {
                return new JobCancellationException(getClass().getSimpleName().concat(" has completed normally"), null, this);
            }
            Throwable th = ((CompletedExceptionally) obj).cause;
            cancellationException = th instanceof CancellationException ? (CancellationException) th : null;
            return cancellationException == null ? new JobCancellationException(cancellationExceptionMessage(), th, this) : cancellationException;
        }
        Throwable rootCause = ((Finishing) obj).getRootCause();
        if (rootCause == null) {
            throw new IllegalStateException(("Job is still new or active: " + this).toString());
        }
        String concat = getClass().getSimpleName().concat(" is cancelling");
        cancellationException = rootCause instanceof CancellationException ? (CancellationException) rootCause : null;
        if (cancellationException == null) {
            if (concat == null) {
                concat = cancellationExceptionMessage();
            }
            cancellationException = new JobCancellationException(concat, rootCause, this);
        }
        return cancellationException;
    }

    public Object getCompleted() {
        Object obj = this._state.value;
        if (obj instanceof Incomplete) {
            throw new IllegalStateException("This job has not completed yet");
        }
        if (obj instanceof CompletedExceptionally) {
            throw ((CompletedExceptionally) obj).cause;
        }
        return JobSupportKt.unboxState(obj);
    }

    public final Throwable getFinalRootCause(Finishing finishing, List list) {
        Object obj;
        Object obj2 = null;
        if (list.isEmpty()) {
            if (finishing.isCancelling()) {
                return new JobCancellationException(cancellationExceptionMessage(), null, this);
            }
            return null;
        }
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (!(((Throwable) obj) instanceof CancellationException)) {
                break;
            }
        }
        Throwable th = (Throwable) obj;
        if (th != null) {
            return th;
        }
        Throwable th2 = (Throwable) list.get(0);
        if (th2 instanceof TimeoutCancellationException) {
            Iterator it2 = list.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Object next = it2.next();
                Throwable th3 = (Throwable) next;
                if (th3 != th2 && (th3 instanceof TimeoutCancellationException)) {
                    obj2 = next;
                    break;
                }
            }
            Throwable th4 = (Throwable) obj2;
            if (th4 != null) {
                return th4;
            }
        }
        return th2;
    }

    public boolean getHandlesException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return true;
    }

    @Override // kotlin.coroutines.CoroutineContext.Element
    public final CoroutineContext.Key getKey() {
        return Job.Key;
    }

    public boolean getOnCancelComplete$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return this instanceof CompletableDeferredImpl;
    }

    public final NodeList getOrPromoteCancellingList(Incomplete incomplete) {
        NodeList list = incomplete.getList();
        if (list != null) {
            return list;
        }
        if (incomplete instanceof Empty) {
            return new NodeList();
        }
        if (incomplete instanceof JobNode) {
            promoteSingleToNodeList((JobNode) incomplete);
            return null;
        }
        throw new IllegalStateException(("State should have list: " + incomplete).toString());
    }

    public boolean handleJobException(Throwable th) {
        return false;
    }

    public final void initParentJob(Job job) {
        AtomicRef atomicRef = this._parentHandle;
        if (job == null) {
            atomicRef.setValue(NonDisposableHandle.INSTANCE);
            return;
        }
        job.start();
        ChildHandle attachChild = job.attachChild(this);
        atomicRef.setValue(attachChild);
        if (isCompleted()) {
            attachChild.dispose();
            atomicRef.setValue(NonDisposableHandle.INSTANCE);
        }
    }

    @Override // kotlinx.coroutines.Job
    public final DisposableHandle invokeOnCompletion(Function1 function1) {
        return invokeOnCompletionInternal$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(true, new InvokeOnCompletion(function1));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v3, types: [kotlinx.coroutines.InactiveNodeList] */
    public final DisposableHandle invokeOnCompletionInternal$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(boolean z, JobNode jobNode) {
        boolean z2;
        boolean addLast;
        jobNode.job = this;
        while (true) {
            Object obj = this._state.value;
            z2 = true;
            if (!(obj instanceof Empty)) {
                if (!(obj instanceof Incomplete)) {
                    z2 = false;
                    break;
                }
                Incomplete incomplete = (Incomplete) obj;
                NodeList list = incomplete.getList();
                if (list == null) {
                    promoteSingleToNodeList((JobNode) obj);
                } else {
                    if (jobNode.getOnCancelling()) {
                        Finishing finishing = incomplete instanceof Finishing ? (Finishing) incomplete : null;
                        Throwable rootCause = finishing != null ? finishing.getRootCause() : null;
                        if (rootCause != null) {
                            if (z) {
                                jobNode.invoke(rootCause);
                            }
                            return NonDisposableHandle.INSTANCE;
                        }
                        addLast = list.addLast(jobNode, 5);
                    } else {
                        addLast = list.addLast(jobNode, 1);
                    }
                    if (addLast) {
                        break;
                    }
                }
            } else {
                Empty empty = (Empty) obj;
                if (!empty.isActive) {
                    NodeList nodeList = new NodeList();
                    if (!empty.isActive) {
                        nodeList = new InactiveNodeList(nodeList);
                    }
                    this._state.compareAndSet(empty, nodeList);
                } else if (this._state.compareAndSet(obj, jobNode)) {
                    break;
                }
            }
        }
        if (z2) {
            return jobNode;
        }
        if (z) {
            Object obj2 = this._state.value;
            CompletedExceptionally completedExceptionally = obj2 instanceof CompletedExceptionally ? (CompletedExceptionally) obj2 : null;
            jobNode.invoke(completedExceptionally != null ? completedExceptionally.cause : null);
        }
        return NonDisposableHandle.INSTANCE;
    }

    @Override // kotlinx.coroutines.Job
    public boolean isActive() {
        Object obj = this._state.value;
        return (obj instanceof Incomplete) && ((Incomplete) obj).isActive();
    }

    @Override // kotlinx.coroutines.Job
    public final boolean isCancelled$1() {
        Object obj = this._state.value;
        if (obj instanceof CompletedExceptionally) {
            return true;
        }
        return (obj instanceof Finishing) && ((Finishing) obj).isCancelling();
    }

    public final boolean isCompleted() {
        return !(this._state.value instanceof Incomplete);
    }

    public boolean isScopedCoroutine() {
        return this instanceof BlockingCoroutine;
    }

    @Override // kotlinx.coroutines.Job
    public final Object join(ContinuationImpl continuationImpl) {
        Object obj;
        do {
            obj = this._state.value;
            if (!(obj instanceof Incomplete)) {
                JobKt.ensureActive(continuationImpl.getContext());
                return Unit.INSTANCE;
            }
        } while (startInternal(obj) < 0);
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuationImpl), 1);
        cancellableContinuationImpl.initCancellability();
        cancellableContinuationImpl.invokeOnCancellationImpl(new DisposeOnCancel(JobKt.invokeOnCompletion$default(this, new ResumeOnCompletion(cancellableContinuationImpl))));
        Object result = cancellableContinuationImpl.getResult();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (result != coroutineSingletons) {
            result = Unit.INSTANCE;
        }
        return result == coroutineSingletons ? result : Unit.INSTANCE;
    }

    public final boolean makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj) {
        Object tryMakeCompleting;
        do {
            tryMakeCompleting = tryMakeCompleting(this._state.value, obj);
            if (tryMakeCompleting == JobSupportKt.COMPLETING_ALREADY) {
                return false;
            }
            if (tryMakeCompleting == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
                return true;
            }
        } while (tryMakeCompleting == JobSupportKt.COMPLETING_RETRY);
        afterCompletion(tryMakeCompleting);
        return true;
    }

    public final Object makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj) {
        Object tryMakeCompleting;
        do {
            tryMakeCompleting = tryMakeCompleting(this._state.value, obj);
            if (tryMakeCompleting == JobSupportKt.COMPLETING_ALREADY) {
                String str = "Job " + this + " is already complete or completing, but is being completed with " + obj;
                CompletedExceptionally completedExceptionally = obj instanceof CompletedExceptionally ? (CompletedExceptionally) obj : null;
                throw new IllegalStateException(str, completedExceptionally != null ? completedExceptionally.cause : null);
            }
        } while (tryMakeCompleting == JobSupportKt.COMPLETING_RETRY);
        return tryMakeCompleting;
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext minusKey(CoroutineContext.Key key) {
        return CoroutineContext.DefaultImpls.minusKey(this, key);
    }

    public String nameString$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return getClass().getSimpleName();
    }

    public final void notifyCancelling(NodeList nodeList, Throwable th) {
        nodeList.getClass();
        nodeList.addLast(new ListClosed(4), 4);
        CompletionHandlerException completionHandlerException = null;
        for (LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) nodeList._next.value; !Intrinsics.areEqual(lockFreeLinkedListNode, nodeList); lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode()) {
            if ((lockFreeLinkedListNode instanceof JobNode) && ((JobNode) lockFreeLinkedListNode).getOnCancelling()) {
                try {
                    ((JobNode) lockFreeLinkedListNode).invoke(th);
                } catch (Throwable th2) {
                    if (completionHandlerException != null) {
                        ExceptionsKt__ExceptionsKt.addSuppressed(completionHandlerException, th2);
                    } else {
                        completionHandlerException = new CompletionHandlerException("Exception in completion handler " + lockFreeLinkedListNode + " for " + this, th2);
                        Unit unit = Unit.INSTANCE;
                    }
                }
            }
        }
        if (completionHandlerException != null) {
            handleOnCompletionException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(completionHandlerException);
        }
        cancelParent(th);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext plus(CoroutineContext coroutineContext) {
        return CoroutineContext.DefaultImpls.plus(this, coroutineContext);
    }

    public final void promoteSingleToNodeList(JobNode jobNode) {
        NodeList nodeList = new NodeList();
        jobNode.getClass();
        nodeList._prev.lazySet(jobNode);
        nodeList._next.lazySet(jobNode);
        while (true) {
            if (jobNode._next.value != jobNode) {
                break;
            } else if (jobNode._next.compareAndSet(jobNode, nodeList)) {
                nodeList.finishAdd(jobNode);
                break;
            }
        }
        this._state.compareAndSet(jobNode, jobNode.getNextNode());
    }

    @Override // kotlinx.coroutines.Job
    public final boolean start() {
        int startInternal;
        do {
            startInternal = startInternal(this._state.value);
            if (startInternal == 0) {
                return false;
            }
        } while (startInternal != 1);
        return true;
    }

    public final int startInternal(Object obj) {
        boolean z = obj instanceof Empty;
        AtomicRef atomicRef = this._state;
        if (z) {
            if (((Empty) obj).isActive) {
                return 0;
            }
            if (!atomicRef.compareAndSet(obj, JobSupportKt.EMPTY_ACTIVE)) {
                return -1;
            }
            onStart();
            return 1;
        }
        if (!(obj instanceof InactiveNodeList)) {
            return 0;
        }
        if (!atomicRef.compareAndSet(obj, ((InactiveNodeList) obj).list)) {
            return -1;
        }
        onStart();
        return 1;
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(nameString$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() + "{" + stateString(this._state.value) + "}", "@", DebugStringsKt.getHexAddress(this));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3, types: [T, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r2v5 */
    public final Object tryMakeCompleting(Object obj, Object obj2) {
        if (!(obj instanceof Incomplete)) {
            return JobSupportKt.COMPLETING_ALREADY;
        }
        if (((obj instanceof Empty) || (obj instanceof JobNode)) && !(obj instanceof ChildHandleNode) && !(obj2 instanceof CompletedExceptionally)) {
            Incomplete incomplete = (Incomplete) obj;
            AtomicRef atomicRef = this._state;
            Symbol symbol = JobSupportKt.COMPLETING_ALREADY;
            if (!atomicRef.compareAndSet(incomplete, obj2 instanceof Incomplete ? new IncompleteStateBox((Incomplete) obj2) : obj2)) {
                return JobSupportKt.COMPLETING_RETRY;
            }
            onCompletionInternal(obj2);
            completeStateFinalization(incomplete, obj2);
            return obj2;
        }
        Incomplete incomplete2 = (Incomplete) obj;
        NodeList orPromoteCancellingList = getOrPromoteCancellingList(incomplete2);
        if (orPromoteCancellingList == null) {
            return JobSupportKt.COMPLETING_RETRY;
        }
        Finishing finishing = incomplete2 instanceof Finishing ? (Finishing) incomplete2 : null;
        if (finishing == null) {
            finishing = new Finishing(orPromoteCancellingList, false, null);
        }
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        synchronized (finishing) {
            if (finishing._isCompleting.getValue()) {
                return JobSupportKt.COMPLETING_ALREADY;
            }
            AtomicBoolean atomicBoolean = finishing._isCompleting;
            atomicBoolean._value = 1;
            TraceBase traceBase = atomicBoolean.trace;
            if (traceBase != TraceBase.None.INSTANCE) {
                traceBase.getClass();
            }
            if (finishing != incomplete2 && !this._state.compareAndSet(incomplete2, finishing)) {
                return JobSupportKt.COMPLETING_RETRY;
            }
            boolean isCancelling = finishing.isCancelling();
            CompletedExceptionally completedExceptionally = obj2 instanceof CompletedExceptionally ? (CompletedExceptionally) obj2 : null;
            if (completedExceptionally != null) {
                finishing.addExceptionLocked(completedExceptionally.cause);
            }
            ?? rootCause = isCancelling ? 0 : finishing.getRootCause();
            ref$ObjectRef.element = rootCause;
            Unit unit = Unit.INSTANCE;
            if (rootCause != 0) {
                notifyCancelling(orPromoteCancellingList, rootCause);
            }
            ChildHandleNode nextChild = nextChild(orPromoteCancellingList);
            if (nextChild != null && tryWaitForChild(finishing, nextChild, obj2)) {
                return JobSupportKt.COMPLETING_WAITING_CHILDREN;
            }
            orPromoteCancellingList.addLast(new ListClosed(2), 2);
            ChildHandleNode nextChild2 = nextChild(orPromoteCancellingList);
            return (nextChild2 == null || !tryWaitForChild(finishing, nextChild2, obj2)) ? finalizeFinishingState(finishing, obj2) : JobSupportKt.COMPLETING_WAITING_CHILDREN;
        }
    }

    public final boolean tryWaitForChild(Finishing finishing, ChildHandleNode childHandleNode, Object obj) {
        DisposableHandle invokeOnCompletion;
        do {
            ChildCompletion childCompletion = new ChildCompletion(this, finishing, childHandleNode, obj);
            ChildJob childJob = childHandleNode.childJob;
            if (childJob instanceof JobSupport) {
                invokeOnCompletion = ((JobSupport) childJob).invokeOnCompletionInternal$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(false, childCompletion);
            } else {
                invokeOnCompletion = ((JobSupport) childJob).invokeOnCompletion(false, false, new JobKt__JobKt$invokeOnCompletion$1(childCompletion));
            }
            if (invokeOnCompletion != NonDisposableHandle.INSTANCE) {
                return true;
            }
            childHandleNode = nextChild(childHandleNode);
        } while (childHandleNode != null);
        return false;
    }

    @Override // kotlinx.coroutines.Job
    public final DisposableHandle invokeOnCompletion(boolean z, boolean z2, Function1 function1) {
        JobNode invokeOnCompletion;
        if (z) {
            invokeOnCompletion = new InvokeOnCancelling(function1);
        } else {
            invokeOnCompletion = new InvokeOnCompletion(function1);
        }
        return invokeOnCompletionInternal$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(z2, invokeOnCompletion);
    }

    public void onStart() {
    }

    public void afterCompletion(Object obj) {
    }

    public void handleOnCompletionException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(CompletionHandlerException completionHandlerException) {
        throw completionHandlerException;
    }

    public void onCompletionInternal(Object obj) {
    }
}
