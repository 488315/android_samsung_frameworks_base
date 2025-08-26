package kotlinx.coroutines.selects;

import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SelectClause0Impl {
    public final Object clauseObject;
    public final Function3 onCancellationConstructor;
    public final SelectKt$DUMMY_PROCESS_RESULT_FUNCTION$1 processResFunc;
    public final Function3 regFunc;

    public SelectClause0Impl(Object obj, Function3 function3, Function3 function32) {
        this.clauseObject = obj;
        this.regFunc = function3;
        this.onCancellationConstructor = function32;
        this.processResFunc = SelectKt.DUMMY_PROCESS_RESULT_FUNCTION;
    }

    public /* synthetic */ SelectClause0Impl(Object obj, Function3 function3, Function3 function32, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, function3, (i & 4) != 0 ? null : function32);
    }
}
