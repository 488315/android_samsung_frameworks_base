package androidx.datastore.core;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class State {
    public final int version;

    public /* synthetic */ State(int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(i);
    }

    private State(int i) {
        this.version = i;
    }
}
