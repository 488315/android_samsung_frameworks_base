package com.android.systemui.kairos;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class EmptyEvents extends Events {
    public static final EmptyEvents INSTANCE = new EmptyEvents();

    private EmptyEvents() {
        super(null);
    }

    public final boolean equals(Object obj) {
        return this == obj || (obj instanceof EmptyEvents);
    }

    public final int hashCode() {
        return -1174985670;
    }

    public final String toString() {
        return "EmptyEvents";
    }
}
