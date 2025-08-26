package com.android.internal.util;

import android.os.RemoteException;
import android.util.ExceptionUtils;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public class FunctionalUtils {

    @FunctionalInterface
    public interface ThrowingChecked2Consumer<Input, ExceptionOne extends Exception, ExceptionTwo extends Exception> {
        void accept(Input input) throws Exception;
    }

    @FunctionalInterface
    public interface ThrowingCheckedConsumer<Input, ExceptionType extends Exception> {
        void accept(Input input) throws Exception;
    }

    @FunctionalInterface
    public interface ThrowingCheckedFunction<Input, Output, ExceptionType extends Exception> {
        Output apply(Input input) throws Exception;
    }

    @FunctionalInterface
    public interface ThrowingCheckedSupplier<Output, ExceptionType extends Exception> {
        Output get() throws Exception;
    }

    public static <T> Consumer<T> ignoreRemoteException(RemoteExceptionIgnoringConsumer<T> remoteExceptionIgnoringConsumer) {
        return remoteExceptionIgnoringConsumer;
    }

    public static Runnable uncheckExceptions(ThrowingRunnable throwingRunnable) {
        return throwingRunnable;
    }

    public static <A, B> BiConsumer<A, B> uncheckExceptions(ThrowingBiConsumer<A, B> throwingBiConsumer) {
        return throwingBiConsumer;
    }

    public static <T> Consumer<T> uncheckExceptions(ThrowingConsumer<T> throwingConsumer) {
        return throwingConsumer;
    }

    public static <I, O> Function<I, O> uncheckExceptions(ThrowingFunction<I, O> throwingFunction) {
        return throwingFunction;
    }

    public static <T> Supplier<T> uncheckExceptions(ThrowingSupplier<T> throwingSupplier) {
        return throwingSupplier;
    }

    private FunctionalUtils() {
    }

    public static Runnable handleExceptions(final ThrowingRunnable throwingRunnable, final Consumer<Throwable> consumer) {
        return new Runnable() { // from class: com.android.internal.util.FunctionalUtils$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FunctionalUtils.lambda$handleExceptions$0(throwingRunnable, consumer);
            }
        };
    }

    static /* synthetic */ void lambda$handleExceptions$0(ThrowingRunnable throwingRunnable, Consumer consumer) {
        try {
            throwingRunnable.run();
        } catch (Throwable th) {
            consumer.accept(th);
        }
    }

    @FunctionalInterface
    public interface ThrowingRunnable extends Runnable {
        void runOrThrow() throws Exception;

        @Override // java.lang.Runnable
        default void run() {
            try {
                runOrThrow();
            } catch (Exception e) {
                throw ExceptionUtils.propagate(e);
            }
        }
    }

    @FunctionalInterface
    public interface ThrowingSupplier<T> extends Supplier<T> {
        T getOrThrow() throws Exception;

        @Override // java.util.function.Supplier
        default T get() {
            try {
                return getOrThrow();
            } catch (Exception e) {
                throw ExceptionUtils.propagate(e);
            }
        }
    }

    @FunctionalInterface
    public interface ThrowingConsumer<T> extends Consumer<T> {
        void acceptOrThrow(T t) throws Exception;

        @Override // java.util.function.Consumer
        default void accept(T t) {
            try {
                acceptOrThrow(t);
            } catch (Exception e) {
                throw ExceptionUtils.propagate(e);
            }
        }
    }

    @FunctionalInterface
    public interface RemoteExceptionIgnoringConsumer<T> extends Consumer<T> {
        void acceptOrThrow(T t) throws RemoteException;

        @Override // java.util.function.Consumer
        default void accept(T t) {
            try {
                acceptOrThrow(t);
            } catch (RemoteException unused) {
            }
        }
    }

    @FunctionalInterface
    public interface ThrowingFunction<T, R> extends Function<T, R> {
        R applyOrThrow(T t) throws Exception;

        @Override // java.util.function.Function
        default R apply(T t) {
            try {
                return applyOrThrow(t);
            } catch (Exception e) {
                throw ExceptionUtils.propagate(e);
            }
        }
    }

    @FunctionalInterface
    public interface ThrowingBiFunction<T, U, R> extends BiFunction<T, U, R> {
        R applyOrThrow(T t, U u) throws Exception;

        @Override // java.util.function.BiFunction
        default R apply(T t, U u) {
            try {
                return applyOrThrow(t, u);
            } catch (Exception e) {
                throw ExceptionUtils.propagate(e);
            }
        }
    }

    @FunctionalInterface
    public interface ThrowingBiConsumer<A, B> extends BiConsumer<A, B> {
        void acceptOrThrow(A a, B b) throws Exception;

        @Override // java.util.function.BiConsumer
        default void accept(A a, B b) {
            try {
                acceptOrThrow(a, b);
            } catch (Exception e) {
                throw ExceptionUtils.propagate(e);
            }
        }
    }

    public static String getLambdaName(Object obj) {
        int iIndexOf;
        String string = obj.toString();
        int iIndexOf2 = string.indexOf("-$$");
        if (iIndexOf2 == -1 || (iIndexOf = string.indexOf(36, iIndexOf2 + 3)) == -1) {
            return string;
        }
        int i = iIndexOf + 1;
        int iIndexOf3 = string.indexOf(36, i);
        if (iIndexOf3 == -1) {
            return string.substring(0, iIndexOf2 - 1) + "$Lambda";
        }
        return string.substring(0, iIndexOf2) + string.substring(i, iIndexOf3) + "$Lambda";
    }
}
