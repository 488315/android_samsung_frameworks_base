package androidx.compose.ui.platform;

import androidx.compose.ui.platform.WindowRecomposerFactory;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
