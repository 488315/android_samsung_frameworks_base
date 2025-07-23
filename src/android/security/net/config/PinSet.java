package android.security.net.config;

import android.util.ArraySet;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes3.dex */
public final class PinSet {
    public static final PinSet EMPTY_PINSET = new PinSet(Collections.EMPTY_SET, Long.MAX_VALUE);
    public final long expirationTime;
    public final Set<Pin> pins;

    public PinSet(Set<Pin> set, long j) {
        if (set == null) {
            throw new NullPointerException("pins must not be null");
        }
        this.pins = set;
        this.expirationTime = j;
    }

    Set<String> getPinAlgorithms() {
        ArraySet arraySet = new ArraySet();
        Iterator<Pin> it = this.pins.iterator();
        while (it.hasNext()) {
            arraySet.add(it.next().digestAlgorithm);
        }
        return arraySet;
    }
}
