package androidx.compose.runtime.changelist;

import androidx.compose.runtime.Applier;
import androidx.compose.runtime.SlotWriter;
import androidx.compose.runtime.internal.RememberEventDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ChangeList extends OperationsDebugStringFormattable {
    public final Operations operations = new Operations();

    public final void executeAndFlushAllPendingChanges(Applier applier, SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher, OperationErrorContext operationErrorContext) {
        this.operations.executeAndFlushAllPendingOperations(applier, slotWriter, rememberEventDispatcher, operationErrorContext);
    }
}
