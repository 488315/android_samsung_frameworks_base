package io.reactivex.internal.functions;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Comparator;
import java.util.concurrent.Callable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Functions {
    public static final EmptyAction EMPTY_ACTION;
    public static final EmptyConsumer EMPTY_CONSUMER;
    public static final EmptyRunnable EMPTY_RUNNABLE;
    public static final OnErrorMissingConsumer ON_ERROR_MISSING;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EmptyLongConsumer {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ErrorConsumer implements Consumer {
        @Override // io.reactivex.functions.Consumer
        public final void accept(Object obj) {
            RxJavaPlugins.onError((Throwable) obj);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FalsePredicate {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Identity {
        public final String toString() {
            return "IdentityFunction";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MaxRequestSubscription implements Consumer {
        @Override // io.reactivex.functions.Consumer
        public final void accept(Object obj) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(obj);
            throw null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NaturalObjectComparator implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((Comparable) obj).compareTo(obj2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NullCallable implements Callable {
        @Override // java.util.concurrent.Callable
        public final Object call() {
            return null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OnErrorMissingConsumer implements Consumer {
        @Override // io.reactivex.functions.Consumer
        public final void accept(Object obj) {
            RxJavaPlugins.onError(new OnErrorNotImplementedException((Throwable) obj));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TruePredicate {
    }

    static {
        new Identity();
        EMPTY_RUNNABLE = new EmptyRunnable();
        EMPTY_ACTION = new EmptyAction();
        EMPTY_CONSUMER = new EmptyConsumer();
        new ErrorConsumer();
        ON_ERROR_MISSING = new OnErrorMissingConsumer();
        new EmptyLongConsumer();
        new TruePredicate();
        new FalsePredicate();
        new NullCallable();
        new NaturalObjectComparator();
        new MaxRequestSubscription();
    }

    private Functions() {
        throw new IllegalStateException("No instances!");
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EmptyAction implements Action {
        public final String toString() {
            return "EmptyAction";
        }

        @Override // io.reactivex.functions.Action
        public final void run() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EmptyRunnable implements Runnable {
        public final String toString() {
            return "EmptyRunnable";
        }

        @Override // java.lang.Runnable
        public final void run() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EmptyConsumer implements Consumer {
        public final String toString() {
            return "EmptyConsumer";
        }

        @Override // io.reactivex.functions.Consumer
        public final void accept(Object obj) {
        }
    }
}
