package com.google.common.util.concurrent;

import com.google.common.util.concurrent.AbstractFuture;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class FluentFuture extends GwtFluentFutureCatchingSpecialization {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class TrustedFuture extends FluentFuture implements AbstractFuture.Trusted {
        @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
        public final boolean isCancelled() {
            return this.value instanceof AbstractFuture.Cancellation;
        }
    }
}
