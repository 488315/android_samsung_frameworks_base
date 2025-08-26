package io.reactivex.internal.functions;

import androidx.media3.session.MediaControllerImplBase$$ExternalSyntheticOutline0;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Comparator;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class Functions {
    public static final EmptyAction EMPTY_ACTION;
    public static final EmptyConsumer EMPTY_CONSUMER;
    public static final EmptyRunnable EMPTY_RUNNABLE;
    public static final OnErrorMissingConsumer ON_ERROR_MISSING;

    public final class EmptyLongConsumer {
    }

    public final class ErrorConsumer implements Consumer {
        @Override // io.reactivex.functions.Consumer
        public final void accept(Object obj) {
            RxJavaPlugins.onError((Throwable) obj);
        }
    }

    public final class FalsePredicate {
    }

    public final class Identity {
        public final String toString() {
            return "IdentityFunction";
        }
    }

    public final class MaxRequestSubscription implements Consumer {
        @Override // io.reactivex.functions.Consumer
        public final void accept(Object obj) {
            throw MediaControllerImplBase$$ExternalSyntheticOutline0.m(obj);
        }
    }

    public final class NaturalObjectComparator implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((Comparable) obj).compareTo(obj2);
        }
    }

    public final class NullCallable implements Callable {
        @Override // java.util.concurrent.Callable
        public final Object call() {
            return null;
        }
    }

    public final class OnErrorMissingConsumer implements Consumer {
        @Override // io.reactivex.functions.Consumer
        public final void accept(Object obj) {
            RxJavaPlugins.onError(new OnErrorNotImplementedException((Throwable) obj));
        }
    }

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

    public final class EmptyAction implements Action {
        public final String toString() {
            return "EmptyAction";
        }

        @Override // io.reactivex.functions.Action
        public final void run() {
        }
    }

    public final class EmptyRunnable implements Runnable {
        public final String toString() {
            return "EmptyRunnable";
        }

        @Override // java.lang.Runnable
        public final void run() {
        }
    }

    public final class EmptyConsumer implements Consumer {
        public final String toString() {
            return "EmptyConsumer";
        }

        @Override // io.reactivex.functions.Consumer
        public final void accept(Object obj) {
        }
    }
}
