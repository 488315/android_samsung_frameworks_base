package androidx.compose.ui.platform;

import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class GlobalSnapshotManager {
    public static final GlobalSnapshotManager INSTANCE = new GlobalSnapshotManager();
    public static final AtomicBoolean started = new AtomicBoolean(false);
    public static final AtomicBoolean sent = new AtomicBoolean(false);

    private GlobalSnapshotManager() {
    }
}
