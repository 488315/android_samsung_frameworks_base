package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects$ToStringHelper;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.AbstractTransformFuture;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/* loaded from: classes4.dex */
public final class Futures extends GwtFuturesCatchingSpecialization {

    public final class CallbackListener implements Runnable {
        public final FutureCallback callback;
        public final Future future;

        public CallbackListener(Future<Object> future, FutureCallback futureCallback) {
            this.future = future;
            this.callback = futureCallback;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Throwable thTryInternalFastPathGetFailure;
            Object obj = this.future;
            if ((obj instanceof InternalFutureFailureAccess) && (thTryInternalFastPathGetFailure = ((InternalFutureFailureAccess) obj).tryInternalFastPathGetFailure()) != null) {
                this.callback.onFailure(thTryInternalFastPathGetFailure);
                return;
            }
            try {
                this.callback.onSuccess(Futures.getDone(this.future));
            } catch (ExecutionException e) {
                this.callback.onFailure(e.getCause());
            } catch (Throwable th) {
                this.callback.onFailure(th);
            }
        }

        public final String toString() {
            MoreObjects$ToStringHelper moreObjects$ToStringHelper = new MoreObjects$ToStringHelper(CallbackListener.class.getSimpleName());
            FutureCallback futureCallback = this.callback;
            MoreObjects$ToStringHelper.ValueHolder valueHolder = new MoreObjects$ToStringHelper.ValueHolder();
            moreObjects$ToStringHelper.holderTail.next = valueHolder;
            moreObjects$ToStringHelper.holderTail = valueHolder;
            valueHolder.value = futureCallback;
            return moreObjects$ToStringHelper.toString();
        }
    }

    private Futures() {
    }

    public static Object getDone(Future future) {
        if (future.isDone()) {
            return Uninterruptibles.getUninterruptibly(future);
        }
        throw new IllegalStateException(Strings.lenientFormat("Future was expected to be done: %s", future));
    }

    public static ImmediateFuture immediateFuture(Object obj) {
        return obj == null ? ImmediateFuture.NULL : new ImmediateFuture(obj);
    }

    public static AbstractTransformFuture.TransformFuture transform(ListenableFuture listenableFuture, Function function, Executor executor) {
        int i = AbstractTransformFuture.$r8$clinit;
        AbstractTransformFuture.TransformFuture transformFuture = new AbstractTransformFuture.TransformFuture(listenableFuture, function);
        listenableFuture.addListener(transformFuture, MoreExecutors.rejectionPropagatingExecutor(executor, transformFuture));
        return transformFuture;
    }
}
