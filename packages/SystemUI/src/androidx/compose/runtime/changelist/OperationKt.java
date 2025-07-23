package androidx.compose.runtime.changelist;

import androidx.compose.runtime.Applier;
import androidx.compose.runtime.SlotWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class OperationKt {
    public static final void positionToParentOf(SlotWriter slotWriter, Applier applier, int i) {
        while (true) {
            int i2 = slotWriter.parent;
            if (i > i2 && i < slotWriter.currentGroupEnd) {
                return;
            }
            if (i2 == 0 && i == 0) {
                return;
            }
            slotWriter.skipToGroupEnd();
            if (slotWriter.isNode(slotWriter.parent)) {
                applier.up();
            }
            slotWriter.endGroup();
        }
    }
}
