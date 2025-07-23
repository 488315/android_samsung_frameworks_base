package com.google.common.util.concurrent;

import com.google.common.util.concurrent.AbstractFuture;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SettableFuture extends AbstractFuture.TrustedFuture {
    private SettableFuture() {
    }

    public static SettableFuture create() {
        return new SettableFuture();
    }
}
