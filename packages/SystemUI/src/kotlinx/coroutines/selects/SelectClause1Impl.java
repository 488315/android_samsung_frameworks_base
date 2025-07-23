package kotlinx.coroutines.selects;

import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SelectClause1Impl {
    public final Object clauseObject;
    public final Function3 onCancellationConstructor;
    public final Function3 processResFunc;
    public final Function3 regFunc;

    public SelectClause1Impl(Object obj, Function3 function3, Function3 function32, Function3 function33) {
        this.clauseObject = obj;
        this.regFunc = function3;
        this.processResFunc = function32;
        this.onCancellationConstructor = function33;
    }

    public /* synthetic */ SelectClause1Impl(Object obj, Function3 function3, Function3 function32, Function3 function33, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, function3, function32, (i & 8) != 0 ? null : function33);
    }
}
