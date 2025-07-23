package androidx.compose.foundation;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.DelegatableNode;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface OverscrollEffect {
    /* renamed from: applyToFling-BMRW4eQ */
    Object mo19applyToFlingBMRW4eQ(long j, Function2 function2, ContinuationImpl continuationImpl);

    /* renamed from: applyToScroll-Rhakbz0 */
    long mo20applyToScrollRhakbz0(int i, long j, Function1 function1);

    default Modifier getEffectModifier() {
        return Modifier.Companion;
    }

    default DelegatableNode getNode() {
        return new Modifier.Node() { // from class: androidx.compose.foundation.OverscrollEffect$node$1
        };
    }

    boolean isInProgress();
}
