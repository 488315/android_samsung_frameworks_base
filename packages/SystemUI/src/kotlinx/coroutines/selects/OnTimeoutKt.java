package kotlinx.coroutines.selects;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.selects.SelectImplementation.ClauseData;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class OnTimeoutKt {
    public static final void onTimeout(SelectImplementation selectImplementation, long j, Function1 function1) {
        OnTimeout onTimeout = new OnTimeout(j);
        OnTimeout$selectClause$1 onTimeout$selectClause$1 = OnTimeout$selectClause$1.INSTANCE;
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(3, onTimeout$selectClause$1);
        SelectClause0Impl selectClause0Impl = new SelectClause0Impl(onTimeout, onTimeout$selectClause$1, null, 4, null);
        selectImplementation.register(selectImplementation.new ClauseData(selectClause0Impl.clauseObject, selectClause0Impl.regFunc, selectClause0Impl.processResFunc, SelectKt.PARAM_CLAUSE_0, function1, selectClause0Impl.onCancellationConstructor), false);
    }
}
