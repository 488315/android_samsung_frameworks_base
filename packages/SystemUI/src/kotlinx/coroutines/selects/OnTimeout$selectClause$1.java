package kotlinx.coroutines.selects;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.DelayKt;

/* loaded from: classes4.dex */
final /* synthetic */ class OnTimeout$selectClause$1 extends FunctionReferenceImpl implements Function3 {
    public static final OnTimeout$selectClause$1 INSTANCE = new OnTimeout$selectClause$1();

    public OnTimeout$selectClause$1() {
        super(3, OnTimeout.class, "register", "register(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        final OnTimeout onTimeout = (OnTimeout) obj;
        final SelectInstance selectInstance = (SelectInstance) obj2;
        long j = onTimeout.timeMillis;
        if (j <= 0) {
            ((SelectImplementation) selectInstance).internalResult = Unit.INSTANCE;
        } else {
            Runnable runnable = new Runnable() { // from class: kotlinx.coroutines.selects.OnTimeout$register$action$1
                @Override // java.lang.Runnable
                public final void run() {
                    ((SelectImplementation) selectInstance).trySelectInternal(onTimeout, Unit.INSTANCE);
                }
            };
            SelectImplementation selectImplementation = (SelectImplementation) selectInstance;
            CoroutineContext coroutineContext = selectImplementation.context;
            selectImplementation.disposableHandleOrSegment = DelayKt.getDelay(coroutineContext).invokeOnTimeout(j, runnable, coroutineContext);
        }
        return Unit.INSTANCE;
    }
}
