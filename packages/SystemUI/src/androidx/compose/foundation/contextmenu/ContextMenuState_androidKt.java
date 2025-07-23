package androidx.compose.foundation.contextmenu;

import androidx.compose.foundation.contextmenu.ContextMenuState;
import androidx.compose.runtime.SnapshotMutableStateImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ContextMenuState_androidKt {
    public static final void close(ContextMenuState contextMenuState) {
        ((SnapshotMutableStateImpl) contextMenuState.status$delegate).setValue(ContextMenuState.Status.Closed.INSTANCE);
    }
}
