package com.google.common.util.concurrent;

import com.google.common.util.concurrent.AbstractFuture;

/* loaded from: classes4.dex */
public abstract class FluentFuture extends GwtFluentFutureCatchingSpecialization {
    public static final /* synthetic */ int $r8$clinit = 0;

    public abstract class TrustedFuture extends FluentFuture implements AbstractFuture.Trusted {
        @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
        public final boolean isCancelled() {
            return this.value instanceof AbstractFuture.Cancellation;
        }
    }
}
