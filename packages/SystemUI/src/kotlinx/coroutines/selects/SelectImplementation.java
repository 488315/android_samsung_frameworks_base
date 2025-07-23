package kotlinx.coroutines.selects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CancelHandler;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SelectImplementation implements CancelHandler, SelectInstance, Waiter {
    public final CoroutineContext context;
    public Object disposableHandleOrSegment;
    public final AtomicRef state = AtomicFU.atomic(SelectKt.STATE_REG);
    public List clauses = new ArrayList(2);
    public int indexInSegment = -1;
    public Object internalResult = SelectKt.NO_RESULT;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ClauseData {
        public final Object block;
        public final Object clauseObject;
        public Object disposableHandleOrSegment;
        public int indexInSegment = -1;
        public final Function3 onCancellationConstructor;
        public final Object param;
        public final Function3 processResFunc;
        public final Function3 regFunc;

        public ClauseData(Object obj, Function3 function3, Function3 function32, Object obj2, Object obj3, Function3 function33) {
            this.clauseObject = obj;
            this.regFunc = function3;
            this.processResFunc = function32;
            this.param = obj2;
            this.block = obj3;
            this.onCancellationConstructor = function33;
        }

        public final void dispose() {
            Object obj = this.disposableHandleOrSegment;
            if (obj instanceof Segment) {
                ((Segment) obj).onCancellation(SelectImplementation.this.context, this.indexInSegment);
                return;
            }
            DisposableHandle disposableHandle = obj instanceof DisposableHandle ? (DisposableHandle) obj : null;
            if (disposableHandle != null) {
                disposableHandle.dispose();
            }
        }
    }

    public SelectImplementation(CoroutineContext coroutineContext) {
        this.context = coroutineContext;
    }

    public final Object complete(ContinuationImpl continuationImpl) {
        ClauseData clauseData = (ClauseData) this.state.value;
        Object obj = this.internalResult;
        List list = this.clauses;
        if (list != null) {
            ArrayList arrayList = (ArrayList) list;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                ClauseData clauseData2 = (ClauseData) obj2;
                if (clauseData2 != clauseData) {
                    clauseData2.dispose();
                }
            }
            this.state.setValue(SelectKt.STATE_COMPLETED);
            this.internalResult = SelectKt.NO_RESULT;
            this.clauses = null;
        }
        Object invoke = clauseData.processResFunc.invoke(clauseData.clauseObject, clauseData.param, obj);
        Symbol symbol = SelectKt.PARAM_CLAUSE_0;
        Object obj3 = clauseData.param;
        Object obj4 = clauseData.block;
        return obj3 == symbol ? ((Function1) obj4).mo779invoke(continuationImpl) : ((Function2) obj4).invoke(invoke, continuationImpl);
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b8, code lost:
    
        if (r10 == r1) goto L46;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00c5 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00c6 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object doSelectSuspend(kotlin.coroutines.jvm.internal.ContinuationImpl r10) {
        /*
            Method dump skipped, instructions count: 223
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.selects.SelectImplementation.doSelectSuspend(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final ClauseData findClause(Object obj) {
        List list = this.clauses;
        Object obj2 = null;
        if (list == null) {
            return null;
        }
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            if (((ClauseData) next).clauseObject == obj) {
                obj2 = next;
                break;
            }
        }
        ClauseData clauseData = (ClauseData) obj2;
        if (clauseData != null) {
            return clauseData;
        }
        throw new IllegalStateException(("Clause with object " + obj + " is not found").toString());
    }

    @Override // kotlinx.coroutines.CancelHandler
    public final void invoke(Throwable th) {
        Object obj;
        AtomicRef atomicRef = this.state;
        do {
            obj = atomicRef.value;
            if (obj == SelectKt.STATE_COMPLETED) {
                return;
            }
        } while (!atomicRef.compareAndSet(obj, SelectKt.STATE_CANCELLED));
        List list = this.clauses;
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((ClauseData) it.next()).dispose();
        }
        this.internalResult = SelectKt.NO_RESULT;
        this.clauses = null;
    }

    @Override // kotlinx.coroutines.Waiter
    public final void invokeOnCancellation(Segment segment, int i) {
        this.disposableHandleOrSegment = segment;
        this.indexInSegment = i;
    }

    public final void register(ClauseData clauseData, boolean z) {
        if (this.state.value instanceof ClauseData) {
            return;
        }
        if (!z) {
            Object obj = clauseData.clauseObject;
            List list = this.clauses;
            list.getClass();
            if (!list.isEmpty()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (((ClauseData) it.next()).clauseObject == obj) {
                        throw new IllegalStateException(("Cannot use select clauses on the same object: " + obj).toString());
                    }
                }
            }
        }
        clauseData.regFunc.invoke(clauseData.clauseObject, this, clauseData.param);
        if (this.internalResult != SelectKt.NO_RESULT) {
            this.state.setValue(clauseData);
            return;
        }
        if (!z) {
            List list2 = this.clauses;
            list2.getClass();
            list2.add(clauseData);
        }
        clauseData.disposableHandleOrSegment = this.disposableHandleOrSegment;
        clauseData.indexInSegment = this.indexInSegment;
        this.disposableHandleOrSegment = null;
        this.indexInSegment = -1;
    }

    public final int trySelectInternal(Object obj, Object obj2) {
        while (true) {
            Object obj3 = this.state.value;
            if (obj3 instanceof CancellableContinuation) {
                ClauseData findClause = findClause(obj);
                if (findClause != null) {
                    Function3 function3 = findClause.onCancellationConstructor;
                    Function3 function32 = function3 != null ? (Function3) function3.invoke(this, findClause.param, obj2) : null;
                    if (this.state.compareAndSet(obj3, findClause)) {
                        CancellableContinuation cancellableContinuation = (CancellableContinuation) obj3;
                        this.internalResult = obj2;
                        SelectKt$DUMMY_PROCESS_RESULT_FUNCTION$1 selectKt$DUMMY_PROCESS_RESULT_FUNCTION$1 = SelectKt.DUMMY_PROCESS_RESULT_FUNCTION;
                        Symbol tryResume = cancellableContinuation.tryResume(Unit.INSTANCE, function32);
                        if (tryResume == null) {
                            this.internalResult = SelectKt.NO_RESULT;
                            return 2;
                        }
                        cancellableContinuation.completeResume(tryResume);
                        return 0;
                    }
                } else {
                    continue;
                }
            } else {
                if (Intrinsics.areEqual(obj3, SelectKt.STATE_COMPLETED) || (obj3 instanceof ClauseData)) {
                    return 3;
                }
                if (Intrinsics.areEqual(obj3, SelectKt.STATE_CANCELLED)) {
                    return 2;
                }
                if (Intrinsics.areEqual(obj3, SelectKt.STATE_REG)) {
                    if (this.state.compareAndSet(obj3, Collections.singletonList(obj))) {
                        return 1;
                    }
                } else {
                    if (!(obj3 instanceof List)) {
                        throw new IllegalStateException(("Unexpected state: " + obj3).toString());
                    }
                    if (this.state.compareAndSet(obj3, CollectionsKt___CollectionsKt.plus((Collection) obj3, obj))) {
                        return 1;
                    }
                }
            }
        }
    }
}
