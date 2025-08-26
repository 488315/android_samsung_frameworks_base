package androidx.core.view;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public final class VelocityTrackerCompat {
    public static final Map sFallbackTrackers = Collections.synchronizedMap(new WeakHashMap());

    private VelocityTrackerCompat() {
    }
}
