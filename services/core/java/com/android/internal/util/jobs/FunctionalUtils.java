package com.android.internal.util.jobs;

import android.os.RemoteException;
import android.util.ExceptionUtils;
import com.android.internal.util.jobs.FunctionalUtils;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class FunctionalUtils {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @FunctionalInterface
    public interface RemoteExceptionIgnoringConsumer extends Consumer {
        @Override // java.util.function.Consumer
        default void accept(Object obj) {
            try {
                acceptOrThrow(obj);
            } catch (RemoteException unused) {
            }
        }

        void acceptOrThrow(Object obj) throws RemoteException;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @FunctionalInterface
    public interface ThrowingBiConsumer extends BiConsumer {
        @Override // java.util.function.BiConsumer
        default void accept(Object obj, Object obj2) {
            try {
                acceptOrThrow(obj, obj2);
            } catch (Exception e) {
                throw ExceptionUtils.propagate(e);
            }
        }

        void acceptOrThrow(Object obj, Object obj2) throws Exception;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @FunctionalInterface
    public interface ThrowingBiFunction extends BiFunction {
        @Override // java.util.function.BiFunction
        default Object apply(Object obj, Object obj2) {
            try {
                return applyOrThrow(obj, obj2);
            } catch (Exception e) {
                throw ExceptionUtils.propagate(e);
            }
        }

        Object applyOrThrow(Object obj, Object obj2) throws Exception;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @FunctionalInterface
    public interface ThrowingChecked2Consumer {
        void accept(Object obj) throws Exception, Exception;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @FunctionalInterface
    public interface ThrowingCheckedConsumer {
        void accept(Object obj) throws Exception;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @FunctionalInterface
    public interface ThrowingCheckedFunction {
        Object apply(Object obj) throws Exception;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @FunctionalInterface
    public interface ThrowingCheckedSupplier {
        Object get() throws Exception;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @FunctionalInterface
    public interface ThrowingConsumer extends Consumer {
        @Override // java.util.function.Consumer
        default void accept(Object obj) {
            try {
                acceptOrThrow(obj);
            } catch (Exception e) {
                throw ExceptionUtils.propagate(e);
            }
        }

        void acceptOrThrow(Object obj) throws Exception;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @FunctionalInterface
    public interface ThrowingFunction extends Function {
        @Override // java.util.function.Function
        default Object apply(Object obj) {
            try {
                return applyOrThrow(obj);
            } catch (Exception e) {
                throw ExceptionUtils.propagate(e);
            }
        }

        Object applyOrThrow(Object obj) throws Exception;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @FunctionalInterface
    public interface ThrowingRunnable extends Runnable {
        @Override // java.lang.Runnable
        default void run() {
            try {
                runOrThrow();
            } catch (Exception e) {
                throw ExceptionUtils.propagate(e);
            }
        }

        void runOrThrow() throws Exception;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @FunctionalInterface
    public interface ThrowingSupplier extends Supplier {
        @Override // java.util.function.Supplier
        default Object get() {
            try {
                return getOrThrow();
            } catch (Exception e) {
                throw ExceptionUtils.propagate(e);
            }
        }

        Object getOrThrow() throws Exception;
    }

    private FunctionalUtils() {
    }

    public static String getLambdaName(Object obj) {
        int indexOf;
        String obj2 = obj.toString();
        int indexOf2 = obj2.indexOf("-$$");
        if (indexOf2 == -1 || (indexOf = obj2.indexOf(36, indexOf2 + 3)) == -1) {
            return obj2;
        }
        int i = indexOf + 1;
        int indexOf3 = obj2.indexOf(36, i);
        if (indexOf3 == -1) {
            return obj2.substring(0, indexOf2 - 1) + "$Lambda";
        }
        return obj2.substring(0, indexOf2) + obj2.substring(i, indexOf3) + "$Lambda";
    }

    public static Runnable handleExceptions(final ThrowingRunnable throwingRunnable, final Consumer consumer) {
        return new Runnable() { // from class: com.android.internal.util.jobs.FunctionalUtils$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FunctionalUtils.lambda$handleExceptions$0(FunctionalUtils.ThrowingRunnable.this, consumer);
            }
        };
    }

    public static Consumer ignoreRemoteException(RemoteExceptionIgnoringConsumer remoteExceptionIgnoringConsumer) {
        return remoteExceptionIgnoringConsumer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handleExceptions$0(ThrowingRunnable throwingRunnable, Consumer consumer) {
        try {
            throwingRunnable.run();
        } catch (Throwable th) {
            consumer.accept(th);
        }
    }

    public static Runnable uncheckExceptions(ThrowingRunnable throwingRunnable) {
        return throwingRunnable;
    }

    public static BiConsumer uncheckExceptions(ThrowingBiConsumer throwingBiConsumer) {
        return throwingBiConsumer;
    }

    public static Consumer uncheckExceptions(ThrowingConsumer throwingConsumer) {
        return throwingConsumer;
    }

    public static Function uncheckExceptions(ThrowingFunction throwingFunction) {
        return throwingFunction;
    }

    public static Supplier uncheckExceptions(ThrowingSupplier throwingSupplier) {
        return throwingSupplier;
    }
}
