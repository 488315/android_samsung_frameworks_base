package com.android.systemui.kairos;

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
