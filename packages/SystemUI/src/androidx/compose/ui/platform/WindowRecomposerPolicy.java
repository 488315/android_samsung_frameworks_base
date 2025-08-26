package androidx.compose.ui.platform;

import androidx.compose.ui.platform.WindowRecomposerFactory;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class WindowRecomposerPolicy {
    public static final WindowRecomposerPolicy INSTANCE = new WindowRecomposerPolicy();
    public static final AtomicReference factory;

    static {
        WindowRecomposerFactory.Companion.getClass();
        factory = new AtomicReference(WindowRecomposerFactory.Companion.LifecycleAware);
    }

    private WindowRecomposerPolicy() {
    }
}
