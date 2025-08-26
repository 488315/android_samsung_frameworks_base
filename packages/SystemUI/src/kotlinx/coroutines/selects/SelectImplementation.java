package kotlinx.coroutines.selects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CancelHandler;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.Symbol;

/* loaded from: classes4.dex */
public class SelectImplementation implements CancelHandler, SelectInstance, Waiter {
    public final CoroutineContext context;
    public Object disposableHandleOrSegment;
    public final AtomicRef state = AtomicFU.atomic(SelectKt.STATE_REG);
    public List clauses = new ArrayList(2);
    public int indexInSegment = -1;
    public Object internalResult = SelectKt.NO_RESULT;

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

    /* renamed from: kotlinx.coroutines.selects.SelectImplementation$doSelectSuspend$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SelectImplementation.this.doSelectSuspend(this);
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
        Object objInvoke = clauseData.processResFunc.invoke(clauseData.clauseObject, clauseData.param, obj);
        Symbol symbol = SelectKt.PARAM_CLAUSE_0;
        Object obj3 = clauseData.param;
        Object obj4 = clauseData.block;
        return obj3 == symbol ? ((Function1) obj4).mo781invoke(continuationImpl) : ((Function2) obj4).invoke(objInvoke, continuationImpl);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object doSelectSuspend(ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
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
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 1;
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(anonymousClass1), 1);
            cancellableContinuationImpl.initCancellability();
            AtomicRef atomicRef = this.state;
            while (true) {
                Object obj2 = atomicRef.value;
                Symbol symbol = SelectKt.STATE_REG;
                if (obj2 == symbol) {
                    if (this.state.compareAndSet(obj2, cancellableContinuationImpl)) {
                        cancellableContinuationImpl.invokeOnCancellationImpl(this);
                        break;
                    }
                } else if (obj2 instanceof List) {
                    if (this.state.compareAndSet(obj2, symbol)) {
                        Iterator it = ((Iterable) obj2).iterator();
                        while (it.hasNext()) {
                            ClauseData clauseDataFindClause = findClause(it.next());
                            clauseDataFindClause.getClass();
                            clauseDataFindClause.disposableHandleOrSegment = null;
                            clauseDataFindClause.indexInSegment = -1;
                            register(clauseDataFindClause, true);
                        }
                    }
                } else {
                    if (!(obj2 instanceof ClauseData)) {
                        throw new IllegalStateException(("unexpected state: " + obj2).toString());
                    }
                    Unit unit = Unit.INSTANCE;
                    ClauseData clauseData = (ClauseData) obj2;
                    Object obj3 = this.internalResult;
                    Function3 function3 = clauseData.onCancellationConstructor;
                    cancellableContinuationImpl.resume(unit, function3 != null ? (Function3) function3.invoke(this, clauseData.param, obj3) : null);
                }
            }
            Object result = cancellableContinuationImpl.getResult();
            if (result != CoroutineSingletons.COROUTINE_SUSPENDED) {
                result = Unit.INSTANCE;
            }
            if (result != coroutineSingletons) {
            }
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        this = (SelectImplementation) anonymousClass1.L$0;
        ResultKt.throwOnFailure(obj);
        anonymousClass1.L$0 = null;
        anonymousClass1.label = 2;
        Object objComplete = this.complete(anonymousClass1);
        return objComplete == coroutineSingletons ? coroutineSingletons : objComplete;
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
                ClauseData clauseDataFindClause = findClause(obj);
                if (clauseDataFindClause != null) {
                    Function3 function3 = clauseDataFindClause.onCancellationConstructor;
                    Function3 function32 = function3 != null ? (Function3) function3.invoke(this, clauseDataFindClause.param, obj2) : null;
                    if (this.state.compareAndSet(obj3, clauseDataFindClause)) {
                        CancellableContinuation cancellableContinuation = (CancellableContinuation) obj3;
                        this.internalResult = obj2;
                        SelectKt$DUMMY_PROCESS_RESULT_FUNCTION$1 selectKt$DUMMY_PROCESS_RESULT_FUNCTION$1 = SelectKt.DUMMY_PROCESS_RESULT_FUNCTION;
                        Symbol symbolTryResume = cancellableContinuation.tryResume(Unit.INSTANCE, function32);
                        if (symbolTryResume == null) {
                            this.internalResult = SelectKt.NO_RESULT;
                            return 2;
                        }
                        cancellableContinuation.completeResume(symbolTryResume);
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
