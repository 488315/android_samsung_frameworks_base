package android.app.search;

import android.annotation.SystemApi;
import android.app.search.ISearchCallback;
import android.app.search.ISearchUiManager;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.Log;
import dalvik.system.CloseGuard;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes.dex */
public final class SearchSession implements AutoCloseable {
    private static final boolean DEBUG = false;
    private static final String TAG = "SearchSession";
    private final ISearchUiManager mInterface;
    private final ArrayMap<Callback, CallbackWrapper> mRegisteredCallbacks;
    private final SearchSessionId mSessionId;
    private final IBinder mToken;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final AtomicBoolean mIsClosed = new AtomicBoolean(false);

    public interface Callback {
        void onTargetsAvailable(List<SearchTarget> list);
    }

    SearchSession(Context context, SearchContext searchContext) {
        Binder binder = new Binder();
        this.mToken = binder;
        this.mRegisteredCallbacks = new ArrayMap<>();
        ISearchUiManager iSearchUiManagerAsInterface = ISearchUiManager.Stub.asInterface(ServiceManager.getService(Context.SEARCH_UI_SERVICE));
        this.mInterface = iSearchUiManagerAsInterface;
        SearchSessionId searchSessionId = new SearchSessionId(context.getPackageName() + ":" + UUID.randomUUID().toString(), context.getUserId());
        this.mSessionId = searchSessionId;
        searchContext.setPackageName(context.getPackageName());
        try {
            iSearchUiManagerAsInterface.createSearchSession(searchContext, searchSessionId, binder);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to search session", e);
            e.rethrowFromSystemServer();
        }
        this.mCloseGuard.open("SearchSession.close");
    }

    public void notifyEvent(Query query, SearchTargetEvent searchTargetEvent) {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mInterface.notifyEvent(this.mSessionId, query, searchTargetEvent);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to notify event", e);
            e.rethrowFromSystemServer();
        }
    }

    public void query(Query query, Executor executor, Consumer<List<SearchTarget>> consumer) {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mInterface.query(this.mSessionId, query, new CallbackWrapper(executor, consumer));
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to sort targets", e);
            e.rethrowFromSystemServer();
        }
    }

    public void registerEmptyQueryResultUpdateCallback(Executor executor, final Callback callback) {
        synchronized (this.mRegisteredCallbacks) {
            if (this.mIsClosed.get()) {
                throw new IllegalStateException("This client has already been destroyed.");
            }
            if (this.mRegisteredCallbacks.containsKey(callback)) {
                return;
            }
            try {
                Objects.requireNonNull(callback);
                CallbackWrapper callbackWrapper = new CallbackWrapper(executor, new Consumer() { // from class: android.app.search.SearchSession$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        callback.onTargetsAvailable((List) obj);
                    }
                });
                this.mInterface.registerEmptyQueryResultUpdateCallback(this.mSessionId, callbackWrapper);
                this.mRegisteredCallbacks.put(callback, callbackWrapper);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to register for empty query result updates", e);
                e.rethrowAsRuntimeException();
            }
        }
    }

    public void unregisterEmptyQueryResultUpdateCallback(Callback callback) {
        synchronized (this.mRegisteredCallbacks) {
            if (this.mIsClosed.get()) {
                throw new IllegalStateException("This client has already been destroyed.");
            }
            if (this.mRegisteredCallbacks.containsKey(callback)) {
                try {
                    this.mInterface.unregisterEmptyQueryResultUpdateCallback(this.mSessionId, this.mRegisteredCallbacks.remove(callback));
                } catch (RemoteException e) {
                    Log.e(TAG, "Failed to unregister for empty query result updates", e);
                    e.rethrowAsRuntimeException();
                }
            }
        }
    }

    @Deprecated
    public void destroy() {
        if (!this.mIsClosed.getAndSet(true)) {
            this.mCloseGuard.close();
            try {
                this.mInterface.destroySearchSession(this.mSessionId);
                return;
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to notify search target event", e);
                e.rethrowFromSystemServer();
                return;
            }
        }
        throw new IllegalStateException("This client has already been destroyed.");
    }

    protected void finalize() {
        try {
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            if (!this.mIsClosed.get()) {
                destroy();
            }
        } finally {
            try {
                super.finalize();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        try {
            finalize();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    static class CallbackWrapper extends ISearchCallback.Stub {
        private final Consumer<List<SearchTarget>> mCallback;
        private final Executor mExecutor;

        CallbackWrapper(Executor executor, Consumer<List<SearchTarget>> consumer) {
            this.mCallback = consumer;
            this.mExecutor = executor;
        }

        @Override // android.app.search.ISearchCallback
        public void onResult(ParceledListSlice parceledListSlice) {
            Bundle extras;
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                final List list = parceledListSlice.getList();
                if (list.size() > 0 && (extras = ((SearchTarget) list.get(0)).getExtras()) != null) {
                    extras.putLong("key_ipc_start", SystemClock.elapsedRealtime());
                }
                this.mExecutor.execute(new Runnable() { // from class: android.app.search.SearchSession$CallbackWrapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onResult$0(list);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResult$0(List list) {
            this.mCallback.accept(list);
        }
    }
}
