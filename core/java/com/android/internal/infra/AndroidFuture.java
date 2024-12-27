package com.android.internal.infra;

import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.EventLog;
import android.util.Log;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.infra.IAndroidFuture;
import com.android.internal.util.Preconditions;
import java.lang.reflect.Constructor;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class AndroidFuture<T> extends CompletableFuture<T> implements Parcelable {
    private static final boolean DEBUG = false;
    private static Handler sMainHandler;
    private BiConsumer<? super T, ? super Throwable> mListener;
    private Executor mListenerExecutor;
    private final Object mLock;
    private final IAndroidFuture mRemoteOrigin;
    private Handler mTimeoutHandler;
    private static final String LOG_TAG = AndroidFuture.class.getSimpleName();
    private static final Executor DIRECT_EXECUTOR = new PendingIntent$$ExternalSyntheticLambda0();
    private static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];
    public static final Parcelable.Creator<AndroidFuture> CREATOR = new Parcelable.Creator<AndroidFuture>() { // from class: com.android.internal.infra.AndroidFuture.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AndroidFuture createFromParcel(Parcel parcel) {
            return new AndroidFuture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AndroidFuture[] newArray(int size) {
            return new AndroidFuture[size];
        }
    };

    public AndroidFuture() {
        this.mLock = new Object();
        this.mListenerExecutor = DIRECT_EXECUTOR;
        this.mTimeoutHandler = getMainHandler();
        this.mRemoteOrigin = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    AndroidFuture(Parcel in) {
        this.mLock = new Object();
        this.mListenerExecutor = DIRECT_EXECUTOR;
        this.mTimeoutHandler = getMainHandler();
        if (in.readBoolean()) {
            if (in.readBoolean()) {
                completeExceptionally(readThrowable(in));
            } else {
                complete(in.readValue(null));
            }
            this.mRemoteOrigin = null;
            return;
        }
        this.mRemoteOrigin = IAndroidFuture.Stub.asInterface(in.readStrongBinder());
    }

    private static Handler getMainHandler() {
        if (sMainHandler == null) {
            sMainHandler = new Handler(Looper.getMainLooper());
        }
        return sMainHandler;
    }

    public static <U> AndroidFuture<U> completedFuture(U value) {
        AndroidFuture<U> future = new AndroidFuture<>();
        future.complete(value);
        return future;
    }

    @Override // java.util.concurrent.CompletableFuture
    public boolean complete(T value) {
        boolean changed = super.complete(value);
        if (changed) {
            onCompleted(value, null);
        }
        return changed;
    }

    @Override // java.util.concurrent.CompletableFuture
    public boolean completeExceptionally(Throwable ex) {
        boolean changed = super.completeExceptionally(ex);
        if (changed) {
            onCompleted(null, ex);
        }
        return changed;
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.Future
    public boolean cancel(boolean mayInterruptIfRunning) {
        boolean changed = super.cancel(mayInterruptIfRunning);
        if (changed) {
            try {
                get();
                throw new IllegalStateException("Expected CancellationException");
            } catch (CancellationException ex) {
                onCompleted(null, ex);
            } catch (Throwable e) {
                throw new IllegalStateException("Expected CancellationException", e);
            }
        }
        return changed;
    }

    protected void onCompleted(T res, Throwable err) {
        BiConsumer<? super T, ? super Throwable> listener;
        cancelTimeout();
        synchronized (this.mLock) {
            listener = this.mListener;
            this.mListener = null;
        }
        if (listener != null) {
            callListenerAsync(listener, res, err);
        }
        if (this.mRemoteOrigin != null) {
            try {
                this.mRemoteOrigin.complete(this);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Failed to propagate completion", e);
            }
        }
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.CompletionStage
    public AndroidFuture<T> whenComplete(BiConsumer<? super T, ? super Throwable> action) {
        return whenCompleteAsync((BiConsumer) action, DIRECT_EXECUTOR);
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.CompletionStage
    public AndroidFuture<T> whenCompleteAsync(final BiConsumer<? super T, ? super Throwable> action, Executor executor) {
        BiConsumer<? super T, ? super Throwable> biConsumer;
        Preconditions.checkNotNull(action);
        Preconditions.checkNotNull(executor);
        synchronized (this.mLock) {
            if (!isDone()) {
                final BiConsumer<? super T, ? super Throwable> oldListener = this.mListener;
                if (oldListener != null && executor != this.mListenerExecutor) {
                    super.whenCompleteAsync((BiConsumer) action, executor);
                    return this;
                }
                this.mListenerExecutor = executor;
                if (oldListener == null) {
                    biConsumer = action;
                } else {
                    biConsumer = new BiConsumer() { // from class: com.android.internal.infra.AndroidFuture$$ExternalSyntheticLambda1
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            AndroidFuture.lambda$whenCompleteAsync$0(oldListener, action, obj, (Throwable) obj2);
                        }
                    };
                }
                this.mListener = biConsumer;
                return this;
            }
            T res = null;
            Throwable err = null;
            try {
                res = get();
            } catch (ExecutionException e) {
                err = e.getCause();
            } catch (Throwable e2) {
                err = e2;
            }
            callListenerAsync(action, res, err);
            return this;
        }
    }

    static /* synthetic */ void lambda$whenCompleteAsync$0(BiConsumer oldListener, BiConsumer action, Object res, Throwable err) {
        callListener(oldListener, res, err);
        callListener(action, res, err);
    }

    private void callListenerAsync(final BiConsumer<? super T, ? super Throwable> listener, final T res, final Throwable err) {
        if (this.mListenerExecutor == DIRECT_EXECUTOR) {
            callListener(listener, res, err);
        } else {
            this.mListenerExecutor.execute(new Runnable() { // from class: com.android.internal.infra.AndroidFuture$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    AndroidFuture.callListener(listener, res, err);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public static <TT> void callListener(BiConsumer<? super TT, ? super Throwable> listener, TT tt, Throwable err) {
        try {
            listener.accept(tt, err);
        } catch (Throwable t) {
            try {
                if (err == null) {
                    listener.accept(null, t);
                } else {
                    t.addSuppressed(err);
                    throw t;
                }
            } catch (Throwable t2) {
                Log.e(LOG_TAG, "Failed to call whenComplete listener. res = " + tt, t2);
            }
        }
    }

    @Override // java.util.concurrent.CompletableFuture
    public AndroidFuture<T> orTimeout(long timeout, TimeUnit unit) {
        this.mTimeoutHandler.postDelayed(new Runnable() { // from class: com.android.internal.infra.AndroidFuture$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AndroidFuture.this.triggerTimeout();
            }
        }, this, unit.toMillis(timeout));
        return this;
    }

    void triggerTimeout() {
        cancelTimeout();
        if (!isDone()) {
            completeExceptionally(new TimeoutException());
        }
    }

    public AndroidFuture<T> cancelTimeout() {
        this.mTimeoutHandler.removeCallbacksAndMessages(this);
        return this;
    }

    public AndroidFuture<T> setTimeoutHandler(Handler h) {
        cancelTimeout();
        this.mTimeoutHandler = (Handler) Preconditions.checkNotNull(h);
        return this;
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.CompletionStage
    public <U> AndroidFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn) {
        return thenComposeAsync((Function) fn, DIRECT_EXECUTOR);
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.CompletionStage
    public <U> AndroidFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn, Executor executor) {
        return new ThenComposeAsync(this, fn, executor);
    }

    private static class ThenComposeAsync<T, U> extends AndroidFuture<U> implements BiConsumer<Object, Throwable>, Runnable {
        private final Executor mExecutor;
        private volatile Function<? super T, ? extends CompletionStage<U>> mFn;
        private volatile T mSourceResult = null;

        ThenComposeAsync(AndroidFuture<T> source, Function<? super T, ? extends CompletionStage<U>> fn, Executor executor) {
            this.mFn = (Function) Preconditions.checkNotNull(fn);
            this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
            source.whenComplete((BiConsumer) this);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.function.BiConsumer
        public void accept(Object obj, Throwable err) {
            if (err != null) {
                completeExceptionally(err);
            } else if (this.mFn != null) {
                this.mSourceResult = obj;
                this.mExecutor.execute(this);
            } else {
                complete(obj);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                CompletionStage completionStage = (CompletionStage) Preconditions.checkNotNull(this.mFn.apply(this.mSourceResult));
                this.mFn = null;
                completionStage.whenComplete(this);
            } catch (Throwable th) {
                try {
                    completeExceptionally(th);
                } finally {
                    this.mFn = null;
                }
            }
        }
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.CompletionStage
    public <U> AndroidFuture<U> thenApply(Function<? super T, ? extends U> fn) {
        return thenApplyAsync((Function) fn, DIRECT_EXECUTOR);
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.CompletionStage
    public <U> AndroidFuture<U> thenApplyAsync(Function<? super T, ? extends U> fn, Executor executor) {
        return new ThenApplyAsync(this, fn, executor);
    }

    private static class ThenApplyAsync<T, U> extends AndroidFuture<U> implements BiConsumer<T, Throwable>, Runnable {
        private final Executor mExecutor;
        private final Function<? super T, ? extends U> mFn;
        private volatile T mSourceResult = null;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.function.BiConsumer
        public /* bridge */ /* synthetic */ void accept(Object obj, Throwable th) {
            accept2((ThenApplyAsync<T, U>) obj, th);
        }

        ThenApplyAsync(AndroidFuture<T> source, Function<? super T, ? extends U> fn, Executor executor) {
            this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
            this.mFn = (Function) Preconditions.checkNotNull(fn);
            source.whenComplete((BiConsumer) this);
        }

        /* renamed from: accept, reason: avoid collision after fix types in other method */
        public void accept2(T res, Throwable err) {
            if (err != null) {
                completeExceptionally(err);
            } else {
                this.mSourceResult = res;
                this.mExecutor.execute(this);
            }
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Object] */
        @Override // java.lang.Runnable
        public void run() {
            try {
                complete(this.mFn.apply(this.mSourceResult));
            } catch (Throwable th) {
                completeExceptionally(th);
            }
        }
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.CompletionStage
    public <U, V> AndroidFuture<V> thenCombine(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> combineResults) {
        return new ThenCombine(this, other, combineResults);
    }

    static /* synthetic */ Object lambda$thenCombine$2(Object res, Void aVoid) {
        return res;
    }

    public AndroidFuture<T> thenCombine(CompletionStage<Void> completionStage) {
        return (AndroidFuture<T>) thenCombine((CompletionStage) completionStage, (BiFunction) new BiFunction() { // from class: com.android.internal.infra.AndroidFuture$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AndroidFuture.lambda$thenCombine$2(obj, (Void) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ThenCombine<T, U, V> extends AndroidFuture<V> implements BiConsumer<Object, Throwable> {
        private final BiFunction<? super T, ? super U, ? extends V> mCombineResults;
        private volatile T mResultT = null;
        private volatile CompletionStage<? extends U> mSourceU;

        ThenCombine(CompletableFuture<T> sourceT, CompletionStage<? extends U> sourceU, BiFunction<? super T, ? super U, ? extends V> combineResults) {
            this.mSourceU = (CompletionStage) Preconditions.checkNotNull(sourceU);
            this.mCombineResults = (BiFunction) Preconditions.checkNotNull(combineResults);
            sourceT.whenComplete((BiConsumer) this);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Object] */
        @Override // java.util.function.BiConsumer
        public void accept(Object obj, Throwable th) {
            if (th != null) {
                completeExceptionally(th);
                return;
            }
            if (this.mSourceU != null) {
                this.mResultT = obj;
                this.mSourceU.whenComplete(new BiConsumer() { // from class: com.android.internal.infra.AndroidFuture$ThenCombine$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj2, Object obj3) {
                        AndroidFuture.ThenCombine.this.lambda$accept$0(obj2, (Throwable) obj3);
                    }
                });
            } else {
                try {
                    complete(this.mCombineResults.apply(this.mResultT, obj));
                } catch (Throwable th2) {
                    completeExceptionally(th2);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$accept$0(Object r, Throwable e) {
            this.mSourceU = null;
            accept(r, e);
        }
    }

    public static <T> AndroidFuture<T> supply(Supplier<T> supplier) {
        return supplyAsync((Supplier) supplier, DIRECT_EXECUTOR);
    }

    public static <T> AndroidFuture<T> supplyAsync(Supplier<T> supplier, Executor executor) {
        return new SupplyAsync(supplier, executor);
    }

    private static class SupplyAsync<T> extends AndroidFuture<T> implements Runnable {
        private final Supplier<T> mSupplier;

        SupplyAsync(Supplier<T> supplier, Executor executor) {
            this.mSupplier = supplier;
            executor.execute(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                complete(this.mSupplier.get());
            } catch (Throwable t) {
                completeExceptionally(t);
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        boolean done = isDone();
        dest.writeBoolean(done);
        if (done) {
            try {
                T result = get();
                dest.writeBoolean(false);
                dest.writeValue(result);
                return;
            } catch (Throwable t) {
                dest.writeBoolean(true);
                writeThrowable(dest, unwrapExecutionException(t));
                return;
            }
        }
        dest.writeStrongBinder(new IAndroidFuture.Stub() { // from class: com.android.internal.infra.AndroidFuture.1
            @Override // com.android.internal.infra.IAndroidFuture
            public void complete(AndroidFuture resultContainer) {
                boolean changed;
                try {
                    changed = AndroidFuture.this.complete(resultContainer.get());
                } catch (Throwable t2) {
                    changed = AndroidFuture.this.completeExceptionally(AndroidFuture.this.unwrapExecutionException(t2));
                }
                if (!changed) {
                    Log.w(AndroidFuture.LOG_TAG, "Remote result " + resultContainer + " ignored, as local future is already completed: " + AndroidFuture.this);
                }
            }
        }.asBinder());
    }

    Throwable unwrapExecutionException(Throwable t) {
        if (t instanceof ExecutionException) {
            return t.getCause();
        }
        return t;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void writeThrowable(Parcel parcel, Throwable th) {
        boolean hasThrowable = th != 0;
        parcel.writeBoolean(hasThrowable);
        if (!hasThrowable) {
            return;
        }
        boolean isFrameworkParcelable = (th instanceof Parcelable) && th.getClass().getClassLoader() == Parcelable.class.getClassLoader();
        parcel.writeBoolean(isFrameworkParcelable);
        if (isFrameworkParcelable) {
            parcel.writeParcelable((Parcelable) th, 1);
            return;
        }
        parcel.writeString(th.getClass().getName());
        parcel.writeString(th.getMessage());
        StackTraceElement[] stackTrace = th.getStackTrace();
        StringBuilder stackTraceBuilder = new StringBuilder();
        int truncatedStackTraceLength = Math.min(stackTrace != null ? stackTrace.length : 0, 5);
        for (int i = 0; i < truncatedStackTraceLength; i++) {
            if (i > 0) {
                stackTraceBuilder.append('\n');
            }
            stackTraceBuilder.append("\tat ").append(stackTrace[i]);
        }
        parcel.writeString(stackTraceBuilder.toString());
        writeThrowable(parcel, th.getCause());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r9v1, types: [java.lang.StringBuilder] */
    private static Throwable readThrowable(Parcel parcel) {
        ?? r0 = ": ";
        boolean hasThrowable = parcel.readBoolean();
        if (!hasThrowable) {
            return null;
        }
        boolean isFrameworkParcelable = parcel.readBoolean();
        if (isFrameworkParcelable) {
            return (Throwable) parcel.readParcelable(Parcelable.class.getClassLoader());
        }
        String className = parcel.readString();
        String message = parcel.readString();
        String stackTrace = parcel.readString();
        String messageWithStackTrace = message + '\n' + stackTrace;
        try {
            Class<?> clazz = Class.forName(className, true, Parcelable.class.getClassLoader());
            if (Throwable.class.isAssignableFrom(clazz)) {
                Constructor<?> constructor = clazz.getConstructor(String.class);
                r0 = (Throwable) constructor.newInstance(messageWithStackTrace);
            } else {
                EventLog.writeEvent(1397638484, "186530450", -1, "");
                r0 = new RuntimeException(className + ": " + messageWithStackTrace);
            }
        } catch (Throwable t) {
            r0 = new RuntimeException(className + r0 + messageWithStackTrace);
            r0.addSuppressed(t);
        }
        r0.setStackTrace(EMPTY_STACK_TRACE);
        Throwable cause = readThrowable(parcel);
        if (cause != null) {
            r0.initCause(cause);
        }
        return r0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
