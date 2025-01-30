package kotlinx.coroutines;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
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
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.atomicfu.TraceBase;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OpDescriptor;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class JobSupport implements Job, ChildJob {
    public final AtomicRef _parentHandle;
    public final AtomicRef _state;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AwaitContinuation extends CancellableContinuationImpl {
        public final JobSupport job;

        public AwaitContinuation(Continuation<Object> continuation, JobSupport jobSupport) {
            super(continuation, 1);
            this.job = jobSupport;
        }

        @Override // kotlinx.coroutines.CancellableContinuationImpl
        public final Throwable getContinuationCancellationCause(JobSupport jobSupport) {
            Throwable rootCause;
            Object m293x8adbf455 = this.job.m293x8adbf455();
            return (!(m293x8adbf455 instanceof Finishing) || (rootCause = ((Finishing) m293x8adbf455).getRootCause()) == null) ? m293x8adbf455 instanceof CompletedExceptionally ? ((CompletedExceptionally) m293x8adbf455).cause : jobSupport.getCancellationException() : rootCause;
        }

        @Override // kotlinx.coroutines.CancellableContinuationImpl
        public final String nameString() {
            return "AwaitContinuation";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            if (th != null && !Intrinsics.areEqual(th, rootCause)) {
                arrayList.add(th);
            }
            this._exceptionsHolder.setValue(JobSupportKt.SEALED);
            return arrayList;
        }

        public final String toString() {
            boolean isCancelling = isCancelling();
            boolean z = this._isCompleting._value != 0;
            Throwable rootCause = getRootCause();
            Object obj = this._exceptionsHolder.value;
            NodeList nodeList = this.list;
            StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("Finishing[cancelling=", isCancelling, ", completing=", z, ", rootCause=");
            m69m.append(rootCause);
            m69m.append(", exceptions=");
            m69m.append(obj);
            m69m.append(", list=");
            m69m.append(nodeList);
            m69m.append("]");
            return m69m.toString();
        }
    }

    public JobSupport(boolean z) {
        this._state = AtomicFU.atomic(z ? JobSupportKt.EMPTY_ACTIVE : JobSupportKt.EMPTY_NEW);
        this._parentHandle = AtomicFU.atomic((Object) null);
    }

    public static ChildHandleNode nextChild(LockFreeLinkedListNode lockFreeLinkedListNode) {
        while (lockFreeLinkedListNode.isRemoved()) {
            lockFreeLinkedListNode = lockFreeLinkedListNode.getPrevNode();
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
        if (obj instanceof Finishing) {
            Finishing finishing = (Finishing) obj;
            if (finishing.isCancelling()) {
                return "Cancelling";
            }
            if (finishing._isCompleting._value != 0) {
                return "Completing";
            }
        } else {
            if (!(obj instanceof Incomplete)) {
                return obj instanceof CompletedExceptionally ? "Cancelled" : "Completed";
            }
            if (!((Incomplete) obj).isActive()) {
                return "New";
            }
        }
        return "Active";
    }

    public void afterResume(Object obj) {
        afterCompletion(obj);
    }

    @Override // kotlinx.coroutines.Job
    public void cancel(CancellationException cancellationException) {
        if (cancellationException == null) {
            cancellationException = new JobCancellationException(cancellationExceptionMessage(), null, this);
        }
        cancelInternal(cancellationException);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00c5 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0045 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* renamed from: cancelImpl$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m292x7bd83b56(Object obj) {
        Symbol symbol;
        ?? r1;
        Object obj2 = JobSupportKt.COMPLETING_ALREADY;
        ?? r2 = 0;
        ?? r22 = 0;
        int i = 2;
        boolean z = false;
        if (mo291x6aca53c8()) {
            do {
                Object m293x8adbf455 = m293x8adbf455();
                if (m293x8adbf455 instanceof Incomplete) {
                    if (m293x8adbf455 instanceof Finishing) {
                        if ((((Finishing) m293x8adbf455)._isCompleting._value != 0) != false) {
                        }
                    }
                    obj2 = tryMakeCompleting(m293x8adbf455, new CompletedExceptionally(createCauseException(obj), z, i, r22 == true ? 1 : 0));
                }
                obj2 = JobSupportKt.COMPLETING_ALREADY;
                break;
            } while (obj2 == JobSupportKt.COMPLETING_RETRY);
            if (obj2 == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
                return true;
            }
        }
        if (obj2 == JobSupportKt.COMPLETING_ALREADY) {
            Throwable th = null;
            while (true) {
                Object m293x8adbf4552 = m293x8adbf455();
                if (!(m293x8adbf4552 instanceof Finishing)) {
                    if (!(m293x8adbf4552 instanceof Incomplete)) {
                        symbol = JobSupportKt.TOO_LATE_TO_CANCEL;
                        break;
                    }
                    if (th == null) {
                        th = createCauseException(obj);
                    }
                    Incomplete incomplete = (Incomplete) m293x8adbf4552;
                    if (incomplete.isActive()) {
                        NodeList orPromoteCancellingList = getOrPromoteCancellingList(incomplete);
                        if (orPromoteCancellingList != null) {
                            if (this._state.compareAndSet(incomplete, new Finishing(orPromoteCancellingList, false, th))) {
                                notifyCancelling(orPromoteCancellingList, th);
                                r1 = true;
                                if (r1 == false) {
                                    symbol = JobSupportKt.COMPLETING_ALREADY;
                                    break;
                                }
                            }
                        }
                        r1 = false;
                        if (r1 == false) {
                        }
                    } else {
                        Object tryMakeCompleting = tryMakeCompleting(m293x8adbf4552, new CompletedExceptionally(th, z, i, r2 == true ? 1 : 0));
                        if (tryMakeCompleting == JobSupportKt.COMPLETING_ALREADY) {
                            throw new IllegalStateException(("Cannot happen in " + m293x8adbf4552).toString());
                        }
                        if (tryMakeCompleting != JobSupportKt.COMPLETING_RETRY) {
                            obj2 = tryMakeCompleting;
                            break;
                        }
                    }
                } else {
                    synchronized (m293x8adbf4552) {
                        if ((((Finishing) m293x8adbf4552)._exceptionsHolder.value == JobSupportKt.SEALED) == true) {
                            symbol = JobSupportKt.TOO_LATE_TO_CANCEL;
                        } else {
                            boolean isCancelling = ((Finishing) m293x8adbf4552).isCancelling();
                            if (obj != null || !isCancelling) {
                                if (th == null) {
                                    th = createCauseException(obj);
                                }
                                ((Finishing) m293x8adbf4552).addExceptionLocked(th);
                            }
                            Throwable rootCause = isCancelling ^ true ? ((Finishing) m293x8adbf4552).getRootCause() : null;
                            if (rootCause != null) {
                                notifyCancelling(((Finishing) m293x8adbf4552).list, rootCause);
                            }
                            symbol = JobSupportKt.COMPLETING_ALREADY;
                        }
                    }
                }
            }
            obj2 = symbol;
        }
        if (obj2 != JobSupportKt.COMPLETING_ALREADY && obj2 != JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            if (obj2 == JobSupportKt.TOO_LATE_TO_CANCEL) {
                return false;
            }
            afterCompletion(obj2);
        }
        return true;
    }

    public void cancelInternal(Throwable th) {
        m292x7bd83b56(th);
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
        return m292x7bd83b56(th) && mo290x3da20e56();
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
                mo281x4a3c0b24(new CompletionHandlerException("Exception in completion handler " + incomplete + " for " + this, th2));
                return;
            }
        }
        NodeList list = incomplete.getList();
        if (list != null) {
            for (LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) list.getNext(); !Intrinsics.areEqual(lockFreeLinkedListNode, list); lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode()) {
                if (lockFreeLinkedListNode instanceof JobNode) {
                    JobNode jobNode = (JobNode) lockFreeLinkedListNode;
                    try {
                        jobNode.invoke(th);
                    } catch (Throwable th3) {
                        if (completionHandlerException != null) {
                            ExceptionsKt__ExceptionsKt.addSuppressed(completionHandlerException, th3);
                        } else {
                            completionHandlerException = new CompletionHandlerException("Exception in completion handler " + jobNode + " for " + this, th3);
                            Unit unit = Unit.INSTANCE;
                        }
                    }
                }
            }
            if (completionHandlerException != null) {
                mo281x4a3c0b24(completionHandlerException);
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
        Object m293x8adbf455 = jobSupport.m293x8adbf455();
        if (m293x8adbf455 instanceof Finishing) {
            cancellationException = ((Finishing) m293x8adbf455).getRootCause();
        } else if (m293x8adbf455 instanceof CompletedExceptionally) {
            cancellationException = ((CompletedExceptionally) m293x8adbf455).cause;
        } else {
            if (m293x8adbf455 instanceof Incomplete) {
                throw new IllegalStateException(("Cannot be cancelling child in this state: " + m293x8adbf455).toString());
            }
            cancellationException = null;
        }
        CancellationException cancellationException2 = cancellationException instanceof CancellationException ? cancellationException : null;
        if (cancellationException2 == null) {
            cancellationException2 = new JobCancellationException("Parent job is ".concat(stateString(m293x8adbf455)), cancellationException, jobSupport);
        }
        return cancellationException2;
    }

    public final Object finalizeFinishingState(Finishing finishing, Object obj) {
        Throwable finalRootCause;
        boolean z;
        DefaultConstructorMarker defaultConstructorMarker = null;
        CompletedExceptionally completedExceptionally = obj instanceof CompletedExceptionally ? (CompletedExceptionally) obj : null;
        Throwable th = completedExceptionally != null ? completedExceptionally.cause : null;
        synchronized (finishing) {
            finishing.isCancelling();
            List<Throwable> sealLocked = finishing.sealLocked(th);
            finalRootCause = getFinalRootCause(finishing, sealLocked);
            z = true;
            if (finalRootCause != null && sealLocked.size() > 1) {
                Set newSetFromMap = Collections.newSetFromMap(new IdentityHashMap(sealLocked.size()));
                for (Throwable th2 : sealLocked) {
                    if (th2 != finalRootCause && th2 != finalRootCause && !(th2 instanceof CancellationException) && newSetFromMap.add(th2)) {
                        ExceptionsKt__ExceptionsKt.addSuppressed(finalRootCause, th2);
                    }
                }
            }
        }
        boolean z2 = false;
        if (finalRootCause != null && finalRootCause != th) {
            obj = new CompletedExceptionally(finalRootCause, z2, 2, defaultConstructorMarker);
        }
        if (finalRootCause != null) {
            if (!cancelParent(finalRootCause) && !handleJobException(finalRootCause)) {
                z = false;
            }
            if (z) {
                ((CompletedExceptionally) obj)._handled.compareAndSet();
            }
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
        if (Intrinsics.areEqual(getKey(), key)) {
            return this;
        }
        return null;
    }

    public final CancellationException getCancellationException() {
        CancellationException cancellationException;
        Object m293x8adbf455 = m293x8adbf455();
        if (!(m293x8adbf455 instanceof Finishing)) {
            if (m293x8adbf455 instanceof Incomplete) {
                throw new IllegalStateException(("Job is still new or active: " + this).toString());
            }
            if (!(m293x8adbf455 instanceof CompletedExceptionally)) {
                return new JobCancellationException(DebugStringsKt.getClassSimpleName(this).concat(" has completed normally"), null, this);
            }
            Throwable th = ((CompletedExceptionally) m293x8adbf455).cause;
            cancellationException = th instanceof CancellationException ? (CancellationException) th : null;
            return cancellationException == null ? new JobCancellationException(cancellationExceptionMessage(), th, this) : cancellationException;
        }
        Throwable rootCause = ((Finishing) m293x8adbf455).getRootCause();
        if (rootCause == null) {
            throw new IllegalStateException(("Job is still new or active: " + this).toString());
        }
        String concat = DebugStringsKt.getClassSimpleName(this).concat(" is cancelling");
        cancellationException = rootCause instanceof CancellationException ? (CancellationException) rootCause : null;
        if (cancellationException != null) {
            return cancellationException;
        }
        if (concat == null) {
            concat = cancellationExceptionMessage();
        }
        return new JobCancellationException(concat, rootCause, this);
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

    /* renamed from: getHandlesException$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public boolean mo290x3da20e56() {
        return true;
    }

    @Override // kotlin.coroutines.CoroutineContext.Element
    public final CoroutineContext.Key getKey() {
        return Job.Key;
    }

    /* renamed from: getOnCancelComplete$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public boolean mo291x6aca53c8() {
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

    /* renamed from: getState$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final Object m293x8adbf455() {
        AtomicRef atomicRef = this._state;
        while (true) {
            Object obj = atomicRef.value;
            if (!(obj instanceof OpDescriptor)) {
                return obj;
            }
            ((OpDescriptor) obj).perform(this);
        }
    }

    public boolean handleJobException(Throwable th) {
        return false;
    }

    public final void initParentJob(Job job) {
        int startInternal;
        AtomicRef atomicRef = this._parentHandle;
        if (job == null) {
            atomicRef.setValue(NonDisposableHandle.INSTANCE);
            return;
        }
        JobSupport jobSupport = (JobSupport) job;
        do {
            startInternal = jobSupport.startInternal(jobSupport.m293x8adbf455());
            if (startInternal == 0) {
                break;
            }
        } while (startInternal != 1);
        ChildHandle childHandle = (ChildHandle) Job.DefaultImpls.invokeOnCompletion$default(jobSupport, true, new ChildHandleNode(this), 2);
        atomicRef.setValue(childHandle);
        if (!(m293x8adbf455() instanceof Incomplete)) {
            childHandle.dispose();
            atomicRef.setValue(NonDisposableHandle.INSTANCE);
        }
    }

    public final DisposableHandle invokeOnCompletion(Function1 function1) {
        return invokeOnCompletion(false, true, function1);
    }

    @Override // kotlinx.coroutines.Job
    public boolean isActive() {
        Object m293x8adbf455 = m293x8adbf455();
        return (m293x8adbf455 instanceof Incomplete) && ((Incomplete) m293x8adbf455).isActive();
    }

    public boolean isScopedCoroutine() {
        return this instanceof BlockingCoroutine;
    }

    public final Object join(Continuation continuation) {
        boolean z;
        while (true) {
            Object m293x8adbf455 = m293x8adbf455();
            if (!(m293x8adbf455 instanceof Incomplete)) {
                z = false;
                break;
            }
            if (startInternal(m293x8adbf455) >= 0) {
                z = true;
                break;
            }
        }
        if (!z) {
            JobKt.ensureActive(continuation.getContext());
            return Unit.INSTANCE;
        }
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        cancellableContinuationImpl.invokeOnCancellation(new DisposeOnCancel(invokeOnCompletion(new ResumeOnCompletion(cancellableContinuationImpl))));
        Object result = cancellableContinuationImpl.getResult();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (result != coroutineSingletons) {
            result = Unit.INSTANCE;
        }
        return result == coroutineSingletons ? result : Unit.INSTANCE;
    }

    /* renamed from: makeCompletingOnce$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final Object m294xe12a6b8b(Object obj) {
        Object tryMakeCompleting;
        do {
            tryMakeCompleting = tryMakeCompleting(m293x8adbf455(), obj);
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
        return Intrinsics.areEqual(getKey(), key) ? EmptyCoroutineContext.INSTANCE : this;
    }

    /* renamed from: nameString$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public String mo282x29b568d4() {
        return DebugStringsKt.getClassSimpleName(this);
    }

    public final void notifyCancelling(NodeList nodeList, Throwable th) {
        CompletionHandlerException completionHandlerException = null;
        for (LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) nodeList.getNext(); !Intrinsics.areEqual(lockFreeLinkedListNode, nodeList); lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode()) {
            if (lockFreeLinkedListNode instanceof JobCancellingNode) {
                JobNode jobNode = (JobNode) lockFreeLinkedListNode;
                try {
                    jobNode.invoke(th);
                } catch (Throwable th2) {
                    if (completionHandlerException != null) {
                        ExceptionsKt__ExceptionsKt.addSuppressed(completionHandlerException, th2);
                    } else {
                        completionHandlerException = new CompletionHandlerException("Exception in completion handler " + jobNode + " for " + this, th2);
                        Unit unit = Unit.INSTANCE;
                    }
                }
            }
        }
        if (completionHandlerException != null) {
            mo281x4a3c0b24(completionHandlerException);
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
            if (jobNode.getNext() != jobNode) {
                break;
            } else if (jobNode._next.compareAndSet(jobNode, nodeList)) {
                nodeList.finishAdd(jobNode);
                break;
            }
        }
        this._state.compareAndSet(jobNode, jobNode.getNextNode());
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
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(mo282x29b568d4() + "{" + stateString(m293x8adbf455()) + "}", "@", DebugStringsKt.getHexAddress(this));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8, types: [T, java.lang.Throwable] */
    public final Object tryMakeCompleting(Object obj, Object obj2) {
        if (!(obj instanceof Incomplete)) {
            return JobSupportKt.COMPLETING_ALREADY;
        }
        if (((obj instanceof Empty) || (obj instanceof JobNode)) && !(obj instanceof ChildHandleNode) && !(obj2 instanceof CompletedExceptionally)) {
            Incomplete incomplete = (Incomplete) obj;
            AtomicRef atomicRef = this._state;
            Symbol symbol = JobSupportKt.COMPLETING_ALREADY;
            if (atomicRef.compareAndSet(incomplete, obj2 instanceof Incomplete ? new IncompleteStateBox((Incomplete) obj2) : obj2)) {
                onCompletionInternal(obj2);
                completeStateFinalization(incomplete, obj2);
                r1 = true;
            }
            return r1 ? obj2 : JobSupportKt.COMPLETING_RETRY;
        }
        Incomplete incomplete2 = (Incomplete) obj;
        NodeList orPromoteCancellingList = getOrPromoteCancellingList(incomplete2);
        if (orPromoteCancellingList == null) {
            return JobSupportKt.COMPLETING_RETRY;
        }
        ChildHandleNode childHandleNode = null;
        Finishing finishing = incomplete2 instanceof Finishing ? (Finishing) incomplete2 : null;
        if (finishing == null) {
            finishing = new Finishing(orPromoteCancellingList, false, null);
        }
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        synchronized (finishing) {
            if (finishing._isCompleting._value != 0) {
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
            ?? rootCause = Boolean.valueOf(isCancelling ^ true).booleanValue() ? finishing.getRootCause() : 0;
            ref$ObjectRef.element = rootCause;
            Unit unit = Unit.INSTANCE;
            if (rootCause != 0) {
                notifyCancelling(orPromoteCancellingList, rootCause);
            }
            ChildHandleNode childHandleNode2 = incomplete2 instanceof ChildHandleNode ? (ChildHandleNode) incomplete2 : null;
            if (childHandleNode2 == null) {
                NodeList list = incomplete2.getList();
                if (list != null) {
                    childHandleNode = nextChild(list);
                }
            } else {
                childHandleNode = childHandleNode2;
            }
            return (childHandleNode == null || !tryWaitForChild(finishing, childHandleNode, obj2)) ? finalizeFinishingState(finishing, obj2) : JobSupportKt.COMPLETING_WAITING_CHILDREN;
        }
    }

    public final boolean tryWaitForChild(Finishing finishing, ChildHandleNode childHandleNode, Object obj) {
        while (Job.DefaultImpls.invokeOnCompletion$default(childHandleNode.childJob, false, new ChildCompletion(this, finishing, childHandleNode, obj), 1) == NonDisposableHandle.INSTANCE) {
            childHandleNode = nextChild(childHandleNode);
            if (childHandleNode == null) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v3, types: [kotlinx.coroutines.InactiveNodeList] */
    public final DisposableHandle invokeOnCompletion(boolean z, boolean z2, Function1 function1) {
        final JobNode jobNode;
        Throwable th;
        boolean z3;
        if (z) {
            jobNode = function1 instanceof JobCancellingNode ? (JobCancellingNode) function1 : null;
            if (jobNode == null) {
                jobNode = new InvokeOnCancelling(function1);
            }
        } else {
            jobNode = function1 instanceof JobNode ? (JobNode) function1 : null;
            if (jobNode == null) {
                jobNode = new InvokeOnCompletion(function1);
            }
        }
        jobNode.job = this;
        while (true) {
            final Object m293x8adbf455 = m293x8adbf455();
            if (m293x8adbf455 instanceof Empty) {
                Empty empty = (Empty) m293x8adbf455;
                if (!empty.isActive) {
                    NodeList nodeList = new NodeList();
                    if (!empty.isActive) {
                        nodeList = new InactiveNodeList(nodeList);
                    }
                    this._state.compareAndSet(empty, nodeList);
                } else if (this._state.compareAndSet(m293x8adbf455, jobNode)) {
                    return jobNode;
                }
            } else {
                if (!(m293x8adbf455 instanceof Incomplete)) {
                    if (z2) {
                        CompletedExceptionally completedExceptionally = m293x8adbf455 instanceof CompletedExceptionally ? (CompletedExceptionally) m293x8adbf455 : null;
                        function1.invoke(completedExceptionally != null ? completedExceptionally.cause : null);
                    }
                    return NonDisposableHandle.INSTANCE;
                }
                NodeList list = ((Incomplete) m293x8adbf455).getList();
                if (list == null) {
                    promoteSingleToNodeList((JobNode) m293x8adbf455);
                } else {
                    DisposableHandle disposableHandle = NonDisposableHandle.INSTANCE;
                    boolean z4 = false;
                    if (z && (m293x8adbf455 instanceof Finishing)) {
                        synchronized (m293x8adbf455) {
                            th = ((Finishing) m293x8adbf455).getRootCause();
                            if (th != null) {
                                if (function1 instanceof ChildHandleNode) {
                                    if (((Finishing) m293x8adbf455)._isCompleting._value != 0) {
                                    }
                                }
                                Unit unit = Unit.INSTANCE;
                            }
                            LockFreeLinkedListNode.CondAddOp condAddOp = new LockFreeLinkedListNode.CondAddOp(jobNode) { // from class: kotlinx.coroutines.JobSupport$addLastAtomic$$inlined$addLastIf$1
                                @Override // kotlinx.coroutines.internal.AtomicOp
                                public final Object prepare(Object obj) {
                                    if (this.m293x8adbf455() == m293x8adbf455) {
                                        return null;
                                    }
                                    return LockFreeLinkedListKt.CONDITION_FALSE;
                                }
                            };
                            while (true) {
                                int tryCondAddNext = list.getPrevNode().tryCondAddNext(jobNode, list, condAddOp);
                                if (tryCondAddNext == 1) {
                                    z3 = true;
                                    break;
                                }
                                if (tryCondAddNext == 2) {
                                    z3 = false;
                                    break;
                                }
                            }
                            if (z3) {
                                if (th == null) {
                                    return jobNode;
                                }
                                disposableHandle = jobNode;
                                Unit unit2 = Unit.INSTANCE;
                            }
                        }
                    } else {
                        th = null;
                    }
                    if (th != null) {
                        if (z2) {
                            function1.invoke(th);
                        }
                        return disposableHandle;
                    }
                    LockFreeLinkedListNode.CondAddOp condAddOp2 = new LockFreeLinkedListNode.CondAddOp(jobNode) { // from class: kotlinx.coroutines.JobSupport$addLastAtomic$$inlined$addLastIf$1
                        @Override // kotlinx.coroutines.internal.AtomicOp
                        public final Object prepare(Object obj) {
                            if (this.m293x8adbf455() == m293x8adbf455) {
                                return null;
                            }
                            return LockFreeLinkedListKt.CONDITION_FALSE;
                        }
                    };
                    while (true) {
                        int tryCondAddNext2 = list.getPrevNode().tryCondAddNext(jobNode, list, condAddOp2);
                        if (tryCondAddNext2 == 1) {
                            z4 = true;
                            break;
                        }
                        if (tryCondAddNext2 == 2) {
                            break;
                        }
                    }
                    if (z4) {
                        return jobNode;
                    }
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

        @Override // kotlinx.coroutines.CompletionHandlerBase
        public final void invoke(Throwable th) {
            JobSupport jobSupport = this.parent;
            jobSupport.getClass();
            ChildHandleNode nextChild = JobSupport.nextChild(this.child);
            Finishing finishing = this.state;
            Object obj = this.proposedUpdate;
            if (nextChild == null || !jobSupport.tryWaitForChild(finishing, nextChild, obj)) {
                jobSupport.afterCompletion(jobSupport.finalizeFinishingState(finishing, obj));
            }
        }

        @Override // kotlin.jvm.functions.Function1
        public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return Unit.INSTANCE;
        }
    }

    public void onStart() {
    }

    public void afterCompletion(Object obj) {
    }

    /* renamed from: handleOnCompletionException$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public void mo281x4a3c0b24(CompletionHandlerException completionHandlerException) {
        throw completionHandlerException;
    }

    public void onCompletionInternal(Object obj) {
    }
}
