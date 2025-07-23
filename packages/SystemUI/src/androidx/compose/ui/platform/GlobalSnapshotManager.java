package androidx.compose.ui.platform;

import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class GlobalSnapshotManager {
    public static final GlobalSnapshotManager INSTANCE = new GlobalSnapshotManager();
    public static final AtomicBoolean started = new AtomicBoolean(false);
    public static final AtomicBoolean sent = new AtomicBoolean(false);

    private GlobalSnapshotManager() {
    }
}
