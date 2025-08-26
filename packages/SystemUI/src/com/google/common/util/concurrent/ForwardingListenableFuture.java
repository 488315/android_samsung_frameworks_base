package com.google.common.util.concurrent;

import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public abstract class ForwardingListenableFuture extends ForwardingFuture implements ListenableFuture {

    public abstract class SimpleForwardingListenableFuture extends ForwardingListenableFuture {
        public final ListenableFuture delegate;

        public SimpleForwardingListenableFuture(ListenableFuture listenableFuture) {
            listenableFuture.getClass();
            this.delegate = listenableFuture;
        }

        @Override // com.google.common.util.concurrent.ForwardingListenableFuture, com.google.common.util.concurrent.ForwardingFuture, com.google.common.collect.ForwardingObject
        public final ListenableFuture delegate() {
            return this.delegate;
        }

        @Override // com.google.common.util.concurrent.ForwardingListenableFuture
        /* renamed from: delegate$1 */
        public final ListenableFuture delegate() {
            return this.delegate;
        }

        @Override // com.google.common.util.concurrent.ForwardingListenableFuture, com.google.common.util.concurrent.ForwardingFuture, com.google.common.collect.ForwardingObject
        public final Object delegate() {
            return this.delegate;
        }
    }

    @Override // com.google.common.util.concurrent.ListenableFuture
    public final void addListener(Runnable runnable, Executor executor) {
        delegate().addListener(runnable, executor);
    }

    @Override // com.google.common.util.concurrent.ForwardingFuture, com.google.common.collect.ForwardingObject
    /* renamed from: delegate$1, reason: merged with bridge method [inline-methods] */
    public abstract ListenableFuture delegate();
}
