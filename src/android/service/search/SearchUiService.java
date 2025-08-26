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
import android.service.search.ISearchUiService;
import android.service.search.SearchUiService;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes3.dex */
public abstract class SearchUiService extends Service {
    private static final boolean DEBUG = false;
    public static final String SERVICE_INTERFACE = "android.service.search.SearchUiService";
    private static final String TAG = "SearchUiService";
    private Handler mHandler;
    private final ArrayMap<SearchSessionId, ArrayList<CallbackWrapper>> mSessionEmptyQueryResultCallbacks = new ArrayMap<>();
    private final ISearchUiService mInterface = new AnonymousClass1();

    @Deprecated
    public void onCreateSearchSession(SearchContext searchContext, SearchSessionId searchSessionId) {
    }

    public abstract void onDestroy(SearchSessionId searchSessionId);

    public abstract void onNotifyEvent(SearchSessionId searchSessionId, Query query, SearchTargetEvent searchTargetEvent);

    public abstract void onQuery(SearchSessionId searchSessionId, Query query, Consumer<List<SearchTarget>> consumer);

    public void onStartUpdateEmptyQueryResult() {
    }

    public void onStopUpdateEmptyQueryResult() {
    }

    /* renamed from: android.service.search.SearchUiService$1, reason: invalid class name */
    class AnonymousClass1 extends ISearchUiService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.search.ISearchUiService
        public void onCreateSearchSession(SearchContext searchContext, SearchSessionId searchSessionId) {
            SearchUiService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.search.SearchUiService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((SearchUiService) obj).onSearchSessionCreated((SearchContext) obj2, (SearchSessionId) obj3);
                }
            }, SearchUiService.this, searchContext, searchSessionId));
            SearchUiService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.search.SearchUiService$1$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((SearchUiService) obj).onCreateSearchSession((SearchContext) obj2, (SearchSessionId) obj3);
                }
            }, SearchUiService.this, searchContext, searchSessionId));
        }

        @Override // android.service.search.ISearchUiService
        public void onQuery(SearchSessionId searchSessionId, Query query, ISearchCallback iSearchCallback) {
            SearchUiService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.service.search.SearchUiService$1$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((SearchUiService) obj).onQuery((SearchSessionId) obj2, (Query) obj3, (SearchUiService.CallbackWrapper) obj4);
                }
            }, SearchUiService.this, searchSessionId, query, new CallbackWrapper(iSearchCallback, null)));
        }

        @Override // android.service.search.ISearchUiService
        public void onNotifyEvent(SearchSessionId searchSessionId, Query query, SearchTargetEvent searchTargetEvent) {
            SearchUiService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.service.search.SearchUiService$1$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((SearchUiService) obj).onNotifyEvent((SearchSessionId) obj2, (Query) obj3, (SearchTargetEvent) obj4);
                }
            }, SearchUiService.this, searchSessionId, query, searchTargetEvent));
        }

        @Override // android.service.search.ISearchUiService
        public void onRegisterEmptyQueryResultUpdateCallback(SearchSessionId searchSessionId, ISearchCallback iSearchCallback) {
            SearchUiService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.search.SearchUiService$1$$ExternalSyntheticLambda6
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((SearchUiService) obj).doRegisterEmptyQueryResultUpdateCallback((SearchSessionId) obj2, (ISearchCallback) obj3);
                }
            }, SearchUiService.this, searchSessionId, iSearchCallback));
        }

        @Override // android.service.search.ISearchUiService
        public void onUnregisterEmptyQueryResultUpdateCallback(SearchSessionId searchSessionId, ISearchCallback iSearchCallback) {
            SearchUiService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.search.SearchUiService$1$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((SearchUiService) obj).doUnregisterEmptyQueryResultUpdateCallback((SearchSessionId) obj2, (ISearchCallback) obj3);
                }
            }, SearchUiService.this, searchSessionId, iSearchCallback));
        }

        @Override // android.service.search.ISearchUiService
        public void onDestroy(SearchSessionId searchSessionId) {
            SearchUiService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.search.SearchUiService$1$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((SearchUiService) obj).doDestroy((SearchSessionId) obj2);
                }
            }, SearchUiService.this, searchSessionId));
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
        Slog.w(TAG, "Tried to bind to wrong intent (should be android.service.search.SearchUiService: " + intent);
        return null;
    }

    public void onSearchSessionCreated(SearchContext searchContext, SearchSessionId searchSessionId) {
        this.mSessionEmptyQueryResultCallbacks.put(searchSessionId, new ArrayList<>());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRegisterEmptyQueryResultUpdateCallback(SearchSessionId searchSessionId, ISearchCallback iSearchCallback) {
        final ArrayList<CallbackWrapper> arrayList = this.mSessionEmptyQueryResultCallbacks.get(searchSessionId);
        if (arrayList == null) {
            Slog.e(TAG, "Failed to register for updates for unknown session: " + searchSessionId);
        } else if (findCallbackWrapper(arrayList, iSearchCallback) == null) {
            arrayList.add(new CallbackWrapper(iSearchCallback, new Consumer() { // from class: android.service.search.SearchUiService$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$doRegisterEmptyQueryResultUpdateCallback$1(arrayList, (SearchUiService.CallbackWrapper) obj);
                }
            }));
            if (arrayList.size() == 1) {
                onStartUpdateEmptyQueryResult();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doRegisterEmptyQueryResultUpdateCallback$1(final ArrayList arrayList, final CallbackWrapper callbackWrapper) {
        this.mHandler.post(new Runnable() { // from class: android.service.search.SearchUiService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$doRegisterEmptyQueryResultUpdateCallback$0(arrayList, callbackWrapper);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doUnregisterEmptyQueryResultUpdateCallback(SearchSessionId searchSessionId, ISearchCallback iSearchCallback) {
        ArrayList<CallbackWrapper> arrayList = this.mSessionEmptyQueryResultCallbacks.get(searchSessionId);
        if (arrayList == null) {
            Slog.e(TAG, "Failed to unregister for updates for unknown session: " + searchSessionId);
            return;
        }
        lambda$doRegisterEmptyQueryResultUpdateCallback$0(arrayList, findCallbackWrapper(arrayList, iSearchCallback));
    }

    private CallbackWrapper findCallbackWrapper(ArrayList<CallbackWrapper> arrayList, ISearchCallback iSearchCallback) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size).isCallback(iSearchCallback)) {
                return arrayList.get(size);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: removeCallbackWrapper, reason: merged with bridge method [inline-methods] */
    public void lambda$doRegisterEmptyQueryResultUpdateCallback$0(ArrayList<CallbackWrapper> arrayList, CallbackWrapper callbackWrapper) {
        if (arrayList == null || callbackWrapper == null) {
            return;
        }
        arrayList.remove(callbackWrapper);
        callbackWrapper.destroy();
        if (arrayList.isEmpty()) {
            onStopUpdateEmptyQueryResult();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDestroy(SearchSessionId searchSessionId) {
        super.onDestroy();
        onDestroy(searchSessionId);
    }

    public final void updateEmptyQueryResult(SearchSessionId searchSessionId, List<SearchTarget> list) {
        ArrayList<CallbackWrapper> arrayList = this.mSessionEmptyQueryResultCallbacks.get(searchSessionId);
        if (arrayList != null) {
            Iterator<CallbackWrapper> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().accept(list);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CallbackWrapper implements Consumer<List<SearchTarget>>, IBinder.DeathRecipient {
        private ISearchCallback mCallback;
        private final Consumer<CallbackWrapper> mOnBinderDied;

        CallbackWrapper(ISearchCallback iSearchCallback, Consumer<CallbackWrapper> consumer) {
            this.mCallback = iSearchCallback;
            this.mOnBinderDied = consumer;
            if (consumer != null) {
                try {
                    iSearchCallback.asBinder().linkToDeath(this, 0);
                } catch (RemoteException e) {
                    Slog.e(SearchUiService.TAG, "Failed to link to death:" + e);
                }
            }
        }

        public boolean isCallback(ISearchCallback iSearchCallback) {
            ISearchCallback iSearchCallback2 = this.mCallback;
            if (iSearchCallback2 == null) {
                Slog.e(SearchUiService.TAG, "Callback is null, likely the binder has died.");
                return false;
            }
            return iSearchCallback2.asBinder().equals(iSearchCallback.asBinder());
        }

        @Override // java.util.function.Consumer
        public void accept(List<SearchTarget> list) {
            try {
                ISearchCallback iSearchCallback = this.mCallback;
                if (iSearchCallback != null) {
                    iSearchCallback.onResult(new ParceledListSlice(list));
                }
            } catch (RemoteException e) {
                Slog.e(SearchUiService.TAG, "Error sending result:" + e);
            }
        }

        public void destroy() {
            ISearchCallback iSearchCallback = this.mCallback;
            if (iSearchCallback == null || this.mOnBinderDied == null) {
                return;
            }
            iSearchCallback.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            destroy();
            this.mCallback = null;
            Consumer<CallbackWrapper> consumer = this.mOnBinderDied;
            if (consumer != null) {
                consumer.accept(this);
            }
        }
    }
}
