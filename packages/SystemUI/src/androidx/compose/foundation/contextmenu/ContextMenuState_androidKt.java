package androidx.compose.foundation.contextmenu;

import androidx.compose.foundation.contextmenu.ContextMenuState;
import androidx.compose.runtime.SnapshotMutableStateImpl;

/* loaded from: classes.dex */
public abstract class ContextMenuState_androidKt {
    public static final void close(ContextMenuState contextMenuState) {
        ((SnapshotMutableStateImpl) contextMenuState.status$delegate).setValue(ContextMenuState.Status.Closed.INSTANCE);
    }
}
