package androidx.datastore.core;

import java.util.concurrent.atomic.AtomicInteger;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AtomicInt {
    public final AtomicInteger delegate;

    public AtomicInt(int i) {
        this.delegate = new AtomicInteger(i);
    }

    public /* synthetic */ AtomicInt(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i);
    }
}
