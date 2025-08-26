package androidx.compose.runtime.changelist;

import androidx.compose.runtime.SlotWriter;
import androidx.compose.runtime.tooling.ComposeStackTraceBuilderKt;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* loaded from: classes.dex */
public final class OperationKt$withCurrentStackTrace$1 implements OperationErrorContext {
    public final /* synthetic */ OperationErrorContext $parent;
    public final /* synthetic */ SlotWriter $slots;

    public OperationKt$withCurrentStackTrace$1(OperationErrorContext operationErrorContext, SlotWriter slotWriter) {
        this.$parent = operationErrorContext;
        this.$slots = slotWriter;
    }

    @Override // androidx.compose.runtime.changelist.OperationErrorContext
    public final List buildStackTrace(Integer num) {
        List listBuildStackTrace = this.$parent.buildStackTrace(null);
        SlotWriter slotWriter = this.$slots;
        int i = slotWriter.parent;
        if (i < 0) {
            return listBuildStackTrace;
        }
        return CollectionsKt___CollectionsKt.plus((Iterable) listBuildStackTrace, (Collection) ComposeStackTraceBuilderKt.buildTrace(slotWriter, num, i, Integer.valueOf(slotWriter.parent(i, slotWriter.groups))));
    }
}
