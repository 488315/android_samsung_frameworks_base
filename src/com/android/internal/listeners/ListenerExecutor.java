package com.android.internal.listeners;

import java.util.concurrent.Executor;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public interface ListenerExecutor {

    public interface FailureCallback<TListenerOperation extends ListenerOperation<?>> {
        void onFailure(TListenerOperation tlisteneroperation, Exception exc);
    }

    public interface ListenerOperation<TListener> {
        default void onComplete(boolean z) {
        }

        default void onFailure(Exception exc) {
        }

        default void onPostExecute(boolean z) {
        }

        default void onPreExecute() {
        }

        void operate(TListener tlistener) throws Exception;
    }

    default <TListener> void executeSafely(Executor executor, Supplier<TListener> supplier, ListenerOperation<TListener> listenerOperation) throws Throwable {
        executeSafely(executor, supplier, listenerOperation, null);
    }

    default <TListener, TListenerOperation extends ListenerOperation<TListener>> void executeSafely(Executor executor, final Supplier<TListener> supplier, final TListenerOperation tlisteneroperation, final FailureCallback<TListenerOperation> failureCallback) throws Throwable {
        final TListener tlistener;
        boolean z;
        if (tlisteneroperation == null || (tlistener = supplier.get()) == null) {
            return;
        }
        try {
            tlisteneroperation.onPreExecute();
            z = true;
            try {
                executor.execute(new Runnable() { // from class: com.android.internal.listeners.ListenerExecutor$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ListenerExecutor.lambda$executeSafely$0(tlistener, supplier, tlisteneroperation, failureCallback);
                    }
                });
            } catch (Throwable th) {
                th = th;
                if (z) {
                    tlisteneroperation.onPostExecute(false);
                }
                tlisteneroperation.onComplete(false);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            z = false;
        }
    }

    static /* synthetic */ void lambda$executeSafely$0(Object obj, Supplier supplier, ListenerOperation listenerOperation, FailureCallback failureCallback) {
        boolean z = false;
        try {
            if (obj == supplier.get()) {
                listenerOperation.operate(obj);
                z = true;
            }
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw ((RuntimeException) e);
            }
            listenerOperation.onFailure(e);
            if (failureCallback != null) {
                failureCallback.onFailure(listenerOperation, e);
            }
        } finally {
            listenerOperation.onPostExecute(false);
            listenerOperation.onComplete(false);
        }
    }
}
