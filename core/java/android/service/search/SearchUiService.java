package android.service.search;

import android.annotation.SystemApi;
import android.app.Service;
import android.app.search.ISearchCallback;
import android.app.search.Query;
import android.app.search.SearchContext;
import android.app.search.SearchSessionId;
import android.app.search.SearchTarget;
import android.app.search.SearchTargetEvent;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SystemApi
public abstract class SearchUiService extends Service {
    private static final boolean DEBUG = false;
    public static final String SERVICE_INTERFACE = "android.service.search.SearchUiService";
    private static final String TAG = "SearchUiService";
    private Handler mHandler;
    private final ArrayMap<SearchSessionId, ArrayList<CallbackWrapper>>
            mSessionEmptyQueryResultCallbacks = new ArrayMap<>();
    private final ISearchUiService mInterface = new AnonymousClass1();

    public abstract void onDestroy(SearchSessionId searchSessionId);

    public abstract void onNotifyEvent(
            SearchSessionId searchSessionId, Query query, SearchTargetEvent searchTargetEvent);

    public abstract void onQuery(
            SearchSessionId searchSessionId, Query query, Consumer<List<SearchTarget>> consumer);

    /* renamed from: android.service.search.SearchUiService$1, reason: invalid class name */
    class AnonymousClass1 extends ISearchUiService.Stub {
        AnonymousClass1() {}

        @Override // android.service.search.ISearchUiService
        public void onCreateSearchSession(SearchContext context, SearchSessionId sessionId) {
            SearchUiService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new TriConsumer() { // from class:
                                                // android.service.search.SearchUiService$1$$ExternalSyntheticLambda0
                                @Override // com.android.internal.util.function.TriConsumer
                                public final void accept(Object obj, Object obj2, Object obj3) {
                                    ((SearchUiService) obj)
                                            .onSearchSessionCreated(
                                                    (SearchContext) obj2, (SearchSessionId) obj3);
                                }
                            },
                            SearchUiService.this,
                            context,
                            sessionId));
            SearchUiService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new TriConsumer() { // from class:
                                                // android.service.search.SearchUiService$1$$ExternalSyntheticLambda1
                                @Override // com.android.internal.util.function.TriConsumer
                                public final void accept(Object obj, Object obj2, Object obj3) {
                                    ((SearchUiService) obj)
                                            .onCreateSearchSession(
                                                    (SearchContext) obj2, (SearchSessionId) obj3);
                                }
                            },
                            SearchUiService.this,
                            context,
                            sessionId));
        }

        @Override // android.service.search.ISearchUiService
        public void onQuery(SearchSessionId sessionId, Query input, ISearchCallback callback) {
            SearchUiService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new QuadConsumer() { // from class:
                                                 // android.service.search.SearchUiService$1$$ExternalSyntheticLambda3
                                @Override // com.android.internal.util.function.QuadConsumer
                                public final void accept(
                                        Object obj, Object obj2, Object obj3, Object obj4) {
                                    ((SearchUiService) obj)
                                            .onQuery(
                                                    (SearchSessionId) obj2,
                                                    (Query) obj3,
                                                    (SearchUiService.CallbackWrapper) obj4);
                                }
                            },
                            SearchUiService.this,
                            sessionId,
                            input,
                            new CallbackWrapper(callback, null)));
        }

        @Override // android.service.search.ISearchUiService
        public void onNotifyEvent(SearchSessionId sessionId, Query query, SearchTargetEvent event) {
            SearchUiService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new QuadConsumer() { // from class:
                                                 // android.service.search.SearchUiService$1$$ExternalSyntheticLambda4
                                @Override // com.android.internal.util.function.QuadConsumer
                                public final void accept(
                                        Object obj, Object obj2, Object obj3, Object obj4) {
                                    ((SearchUiService) obj)
                                            .onNotifyEvent(
                                                    (SearchSessionId) obj2,
                                                    (Query) obj3,
                                                    (SearchTargetEvent) obj4);
                                }
                            },
                            SearchUiService.this,
                            sessionId,
                            query,
                            event));
        }

        @Override // android.service.search.ISearchUiService
        public void onRegisterEmptyQueryResultUpdateCallback(
                SearchSessionId sessionId, ISearchCallback callback) {
            SearchUiService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new TriConsumer() { // from class:
                                                // android.service.search.SearchUiService$1$$ExternalSyntheticLambda6
                                @Override // com.android.internal.util.function.TriConsumer
                                public final void accept(Object obj, Object obj2, Object obj3) {
                                    ((SearchUiService) obj)
                                            .doRegisterEmptyQueryResultUpdateCallback(
                                                    (SearchSessionId) obj2, (ISearchCallback) obj3);
                                }
                            },
                            SearchUiService.this,
                            sessionId,
                            callback));
        }

        @Override // android.service.search.ISearchUiService
        public void onUnregisterEmptyQueryResultUpdateCallback(
                SearchSessionId sessionId, ISearchCallback callback) {
            SearchUiService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new TriConsumer() { // from class:
                                                // android.service.search.SearchUiService$1$$ExternalSyntheticLambda2
                                @Override // com.android.internal.util.function.TriConsumer
                                public final void accept(Object obj, Object obj2, Object obj3) {
                                    ((SearchUiService) obj)
                                            .doUnregisterEmptyQueryResultUpdateCallback(
                                                    (SearchSessionId) obj2, (ISearchCallback) obj3);
                                }
                            },
                            SearchUiService.this,
                            sessionId,
                            callback));
        }

        @Override // android.service.search.ISearchUiService
        public void onDestroy(SearchSessionId sessionId) {
            SearchUiService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new BiConsumer() { // from class:
                                               // android.service.search.SearchUiService$1$$ExternalSyntheticLambda5
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    ((SearchUiService) obj).doDestroy((SearchSessionId) obj2);
                                }
                            },
                            SearchUiService.this,
                            sessionId));
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        Slog.w(
                TAG,
                "Tried to bind to wrong intent (should be android.service.search.SearchUiService: "
                        + intent);
        return null;
    }

    @Deprecated
    public void onCreateSearchSession(SearchContext context, SearchSessionId sessionId) {}

    public void onSearchSessionCreated(SearchContext context, SearchSessionId sessionId) {
        this.mSessionEmptyQueryResultCallbacks.put(sessionId, new ArrayList<>());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRegisterEmptyQueryResultUpdateCallback(
            SearchSessionId sessionId, ISearchCallback callback) {
        final ArrayList<CallbackWrapper> callbacks =
                this.mSessionEmptyQueryResultCallbacks.get(sessionId);
        if (callbacks == null) {
            Slog.e(TAG, "Failed to register for updates for unknown session: " + sessionId);
            return;
        }
        CallbackWrapper wrapper = findCallbackWrapper(callbacks, callback);
        if (wrapper == null) {
            callbacks.add(
                    new CallbackWrapper(
                            callback,
                            new Consumer() { // from class:
                                             // android.service.search.SearchUiService$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    SearchUiService.this
                                            .lambda$doRegisterEmptyQueryResultUpdateCallback$1(
                                                    callbacks,
                                                    (SearchUiService.CallbackWrapper) obj);
                                }
                            }));
            if (callbacks.size() == 1) {
                onStartUpdateEmptyQueryResult();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doRegisterEmptyQueryResultUpdateCallback$1(
            final ArrayList callbacks, final CallbackWrapper callbackWrapper) {
        this.mHandler.post(
                new Runnable() { // from class:
                                 // android.service.search.SearchUiService$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SearchUiService.this.lambda$doRegisterEmptyQueryResultUpdateCallback$0(
                                callbacks, callbackWrapper);
                    }
                });
    }

    public void onStartUpdateEmptyQueryResult() {}

    /* JADX INFO: Access modifiers changed from: private */
    public void doUnregisterEmptyQueryResultUpdateCallback(
            SearchSessionId sessionId, ISearchCallback callback) {
        ArrayList<CallbackWrapper> callbacks =
                this.mSessionEmptyQueryResultCallbacks.get(sessionId);
        if (callbacks == null) {
            Slog.e(TAG, "Failed to unregister for updates for unknown session: " + sessionId);
        } else {
            CallbackWrapper wrapper = findCallbackWrapper(callbacks, callback);
            lambda$doRegisterEmptyQueryResultUpdateCallback$0(callbacks, wrapper);
        }
    }

    private CallbackWrapper findCallbackWrapper(
            ArrayList<CallbackWrapper> callbacks, ISearchCallback callback) {
        for (int i = callbacks.size() - 1; i >= 0; i--) {
            if (callbacks.get(i).isCallback(callback)) {
                return callbacks.get(i);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: removeCallbackWrapper, reason: merged with bridge method [inline-methods] */
    public void lambda$doRegisterEmptyQueryResultUpdateCallback$0(
            ArrayList<CallbackWrapper> callbacks, CallbackWrapper wrapper) {
        if (callbacks == null || wrapper == null) {
            return;
        }
        callbacks.remove(wrapper);
        wrapper.destroy();
        if (callbacks.isEmpty()) {
            onStopUpdateEmptyQueryResult();
        }
    }

    public void onStopUpdateEmptyQueryResult() {}

    /* JADX INFO: Access modifiers changed from: private */
    public void doDestroy(SearchSessionId sessionId) {
        super.onDestroy();
        onDestroy(sessionId);
    }

    public final void updateEmptyQueryResult(
            SearchSessionId sessionId, List<SearchTarget> targets) {
        List<CallbackWrapper> callbacks = this.mSessionEmptyQueryResultCallbacks.get(sessionId);
        if (callbacks != null) {
            for (CallbackWrapper callback : callbacks) {
                callback.accept(targets);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CallbackWrapper
            implements Consumer<List<SearchTarget>>, IBinder.DeathRecipient {
        private ISearchCallback mCallback;
        private final Consumer<CallbackWrapper> mOnBinderDied;

        CallbackWrapper(ISearchCallback callback, Consumer<CallbackWrapper> onBinderDied) {
            this.mCallback = callback;
            this.mOnBinderDied = onBinderDied;
            if (this.mOnBinderDied != null) {
                try {
                    this.mCallback.asBinder().linkToDeath(this, 0);
                } catch (RemoteException e) {
                    Slog.e(SearchUiService.TAG, "Failed to link to death:" + e);
                }
            }
        }

        public boolean isCallback(ISearchCallback callback) {
            if (this.mCallback == null) {
                Slog.e(SearchUiService.TAG, "Callback is null, likely the binder has died.");
                return false;
            }
            return this.mCallback.asBinder().equals(callback.asBinder());
        }

        @Override // java.util.function.Consumer
        public void accept(List<SearchTarget> searchTargets) {
            try {
                if (this.mCallback != null) {
                    this.mCallback.onResult(new ParceledListSlice(searchTargets));
                }
            } catch (RemoteException e) {
                Slog.e(SearchUiService.TAG, "Error sending result:" + e);
            }
        }

        public void destroy() {
            if (this.mCallback != null && this.mOnBinderDied != null) {
                this.mCallback.asBinder().unlinkToDeath(this, 0);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            destroy();
            this.mCallback = null;
            if (this.mOnBinderDied != null) {
                this.mOnBinderDied.accept(this);
            }
        }
    }
}
