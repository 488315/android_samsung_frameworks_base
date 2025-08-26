package com.google.common.util.concurrent;

import com.google.common.util.concurrent.AbstractFuture;

/* loaded from: classes4.dex */
public final class SettableFuture extends AbstractFuture.TrustedFuture {
    private SettableFuture() {
    }

    public static SettableFuture create() {
        return new SettableFuture();
    }
}
