package com.google.common.collect;

/* loaded from: classes4.dex */
public abstract class ForwardingObject {
    public abstract Object delegate();

    public final String toString() {
        return delegate().toString();
    }
}
