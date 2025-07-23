package androidx.core.view;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VelocityTrackerCompat {
    public static final Map sFallbackTrackers = Collections.synchronizedMap(new WeakHashMap());

    private VelocityTrackerCompat() {
    }
}
