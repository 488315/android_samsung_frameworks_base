package com.google.common.collect;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ForwardingObject {
    public abstract Object delegate();

    public final String toString() {
        return delegate().toString();
    }
}
