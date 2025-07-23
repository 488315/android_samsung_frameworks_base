package android.hardware.location;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.chre.flags.Flags;
import android.content.Context;
import android.hardware.contexthub.HubDiscoveryInfo;
import android.hardware.contexthub.HubEndpoint;
import android.hardware.contexthub.HubEndpointDiscoveryCallback;
import android.hardware.contexthub.HubEndpointInfo;
import android.hardware.contexthub.HubServiceInfo;
import android.hardware.contexthub.IContextHubEndpointDiscoveryCallback;
import android.hardware.location.ContextHubManager;
import android.hardware.location.ContextHubTransaction;
import android.hardware.location.IContextHubCallback;
import android.hardware.location.IContextHubClientCallback;
import android.hardware.location.IContextHubTransactionCallback;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes2.dex */
public final class ContextHubManager {
    public static final int AUTHORIZATION_DENIED = 0;
    public static final int AUTHORIZATION_DENIED_GRACE_PERIOD = 1;
    public static final int AUTHORIZATION_GRANTED = 2;
    public static final int EVENT_CLIENT_AUTHORIZATION = 7;
    public static final int EVENT_HUB_RESET = 6;
    public static final int EVENT_NANOAPP_ABORTED = 4;
    public static final int EVENT_NANOAPP_DISABLED = 3;
    public static final int EVENT_NANOAPP_ENABLED = 2;
    public static final int EVENT_NANOAPP_LOADED = 0;
    public static final int EVENT_NANOAPP_MESSAGE = 5;
    public static final int EVENT_NANOAPP_UNLOADED = 1;
    public static final String EXTRA_CLIENT_AUTHORIZATION_STATE = "android.hardware.location.extra.CLIENT_AUTHORIZATION_STATE";
    public static final String EXTRA_CONTEXT_HUB_INFO = "android.hardware.location.extra.CONTEXT_HUB_INFO";
    public static final String EXTRA_EVENT_TYPE = "android.hardware.location.extra.EVENT_TYPE";
    public static final String EXTRA_MESSAGE = "android.hardware.location.extra.MESSAGE";
    public static final String EXTRA_NANOAPP_ABORT_CODE = "android.hardware.location.extra.NANOAPP_ABORT_CODE";
    public static final String EXTRA_NANOAPP_ID = "android.hardware.location.extra.NANOAPP_ID";
    private static final String TAG = "ContextHubManager";
    private Callback mCallback;
    private Handler mCallbackHandler;
    private final IContextHubCallback.Stub mClientCallback;
    private Map<HubEndpointDiscoveryCallback, IContextHubEndpointDiscoveryCallback> mDiscoveryCallbacks = new ConcurrentHashMap();

    @Deprecated
    private ICallback mLocalCallback;
    private final Looper mMainLooper;
    private final IContextHubService mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthorizationState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Event {
    }

    @Deprecated
    public interface ICallback {
        void onMessageReceipt(int i, int i2, ContextHubMessage contextHubMessage);
    }

    @Deprecated
    public static abstract class Callback {
        public abstract void onMessageReceipt(int i, int i2, ContextHubMessage contextHubMessage);

        protected Callback() {
        }
    }

    @Deprecated
    public int[] getContextHubHandles() {
        if (Flags.removeOldContextHubApis()) {
            return null;
        }
        try {
            return this.mService.getContextHubHandles();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public ContextHubInfo getContextHubInfo(int i) {
        if (Flags.removeOldContextHubApis()) {
            return null;
        }
        try {
            return this.mService.getContextHubInfo(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int loadNanoApp(int i, NanoApp nanoApp) {
        if (Flags.removeOldContextHubApis()) {
            return -1;
        }
        try {
            return this.mService.loadNanoApp(i, nanoApp);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int unloadNanoApp(int i) {
        if (Flags.removeOldContextHubApis()) {
            return -1;
        }
        try {
            return this.mService.unloadNanoApp(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public NanoAppInstanceInfo getNanoAppInstanceInfo(int i) {
        if (Flags.removeOldContextHubApis()) {
            return null;
        }
        try {
            return this.mService.getNanoAppInstanceInfo(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int[] findNanoAppOnHub(int i, NanoAppFilter nanoAppFilter) {
        if (Flags.removeOldContextHubApis()) {
            return null;
        }
        try {
            return this.mService.findNanoAppOnHub(i, nanoAppFilter);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int sendMessage(int i, int i2, ContextHubMessage contextHubMessage) {
        if (Flags.removeOldContextHubApis()) {
            return -1;
        }
        try {
            return this.mService.sendMessage(i, i2, contextHubMessage);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ContextHubInfo> getContextHubs() {
        try {
            return this.mService.getContextHubs();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<HubInfo> getHubs() {
        try {
            return this.mService.getHubs();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private IContextHubTransactionCallback createQueryCallback(final ContextHubTransaction<List<NanoAppState>> contextHubTransaction) {
        return new IContextHubTransactionCallback.Stub(this) { // from class: android.hardware.location.ContextHubManager.1
            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onQueryResponse(int i, List<NanoAppState> list) {
                contextHubTransaction.setResponse(new ContextHubTransaction.Response(i, list));
            }

            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onTransactionComplete(int i) {
                Log.e(ContextHubManager.TAG, "Received a non-query callback on a query request");
                contextHubTransaction.setResponse(new ContextHubTransaction.Response(7, null));
            }
        };
    }

    public ContextHubTransaction<Void> loadNanoApp(ContextHubInfo contextHubInfo, NanoAppBinary nanoAppBinary) {
        Objects.requireNonNull(contextHubInfo, "ContextHubInfo cannot be null");
        Objects.requireNonNull(nanoAppBinary, "NanoAppBinary cannot be null");
        ContextHubTransaction<Void> contextHubTransaction = new ContextHubTransaction<>(0);
        try {
            this.mService.loadNanoAppOnHub(contextHubInfo.getId(), ContextHubTransactionHelper.createTransactionCallback(contextHubTransaction), nanoAppBinary);
            return contextHubTransaction;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ContextHubTransaction<Void> unloadNanoApp(ContextHubInfo contextHubInfo, long j) {
        Objects.requireNonNull(contextHubInfo, "ContextHubInfo cannot be null");
        ContextHubTransaction<Void> contextHubTransaction = new ContextHubTransaction<>(1);
        try {
            this.mService.unloadNanoAppFromHub(contextHubInfo.getId(), ContextHubTransactionHelper.createTransactionCallback(contextHubTransaction), j);
            return contextHubTransaction;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ContextHubTransaction<Void> enableNanoApp(ContextHubInfo contextHubInfo, long j) {
        Objects.requireNonNull(contextHubInfo, "ContextHubInfo cannot be null");
        ContextHubTransaction<Void> contextHubTransaction = new ContextHubTransaction<>(2);
        try {
            this.mService.enableNanoApp(contextHubInfo.getId(), ContextHubTransactionHelper.createTransactionCallback(contextHubTransaction), j);
            return contextHubTransaction;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ContextHubTransaction<Void> disableNanoApp(ContextHubInfo contextHubInfo, long j) {
        Objects.requireNonNull(contextHubInfo, "ContextHubInfo cannot be null");
        ContextHubTransaction<Void> contextHubTransaction = new ContextHubTransaction<>(3);
        try {
            this.mService.disableNanoApp(contextHubInfo.getId(), ContextHubTransactionHelper.createTransactionCallback(contextHubTransaction), j);
            return contextHubTransaction;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ContextHubTransaction<List<NanoAppState>> queryNanoApps(ContextHubInfo contextHubInfo) {
        Objects.requireNonNull(contextHubInfo, "ContextHubInfo cannot be null");
        ContextHubTransaction<List<NanoAppState>> contextHubTransaction = new ContextHubTransaction<>(4);
        try {
            this.mService.queryNanoApps(contextHubInfo.getId(), createQueryCallback(contextHubTransaction));
            return contextHubTransaction;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<HubDiscoveryInfo> findEndpoints(long j) {
        try {
            List<HubEndpointInfo> findEndpoints = this.mService.findEndpoints(j);
            ArrayList arrayList = new ArrayList(findEndpoints.size());
            Iterator<HubEndpointInfo> it = findEndpoints.iterator();
            while (it.hasNext()) {
                arrayList.add(new HubDiscoveryInfo(it.next()));
            }
            return arrayList;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<HubDiscoveryInfo> findEndpoints(String str) {
        if (str.isBlank()) {
            throw new IllegalArgumentException("Invalid service descriptor: " + str);
        }
        try {
            List<HubEndpointInfo> findEndpointsWithService = this.mService.findEndpointsWithService(str);
            ArrayList arrayList = new ArrayList(findEndpointsWithService.size());
            for (HubEndpointInfo hubEndpointInfo : findEndpointsWithService) {
                for (HubServiceInfo hubServiceInfo : hubEndpointInfo.getServiceInfoCollection()) {
                    if (hubServiceInfo.getServiceDescriptor().equals(str)) {
                        arrayList.add(new HubDiscoveryInfo(hubEndpointInfo, hubServiceInfo));
                    }
                }
            }
            return arrayList;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.hardware.location.ContextHubManager$2, reason: invalid class name */
    class AnonymousClass2 extends IContextHubEndpointDiscoveryCallback.Stub {
        final /* synthetic */ HubEndpointDiscoveryCallback val$callback;
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ IContextHubService val$service;
        final /* synthetic */ String val$serviceDescriptor;

        AnonymousClass2(IContextHubService iContextHubService, Executor executor, String str, HubEndpointDiscoveryCallback hubEndpointDiscoveryCallback) {
            this.val$service = iContextHubService;
            this.val$executor = executor;
            this.val$serviceDescriptor = str;
            this.val$callback = hubEndpointDiscoveryCallback;
        }

        @Override // android.hardware.contexthub.IContextHubEndpointDiscoveryCallback
        public void onEndpointsStarted(final HubEndpointInfo[] hubEndpointInfoArr) {
            if (hubEndpointInfoArr.length == 0) {
                Log.w(ContextHubManager.TAG, "onEndpointsStarted: received empty discovery list");
                invokeCallbackFinished(this.val$service);
                return;
            }
            Executor executor = this.val$executor;
            final String str = this.val$serviceDescriptor;
            final HubEndpointDiscoveryCallback hubEndpointDiscoveryCallback = this.val$callback;
            final IContextHubService iContextHubService = this.val$service;
            executor.execute(new Runnable() { // from class: android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ContextHubManager.AnonymousClass2.this.lambda$onEndpointsStarted$0(hubEndpointInfoArr, str, hubEndpointDiscoveryCallback, iContextHubService);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEndpointsStarted$0(HubEndpointInfo[] hubEndpointInfoArr, String str, HubEndpointDiscoveryCallback hubEndpointDiscoveryCallback, IContextHubService iContextHubService) {
            List<HubDiscoveryInfo> matchingEndpointDiscoveryList = ContextHubManager.this.getMatchingEndpointDiscoveryList(hubEndpointInfoArr, str);
            if (matchingEndpointDiscoveryList.isEmpty()) {
                Log.w(ContextHubManager.TAG, "onEndpointsStarted: no matching service descriptor");
            } else {
                hubEndpointDiscoveryCallback.onEndpointsStarted(matchingEndpointDiscoveryList);
            }
            invokeCallbackFinished(iContextHubService);
        }

        @Override // android.hardware.contexthub.IContextHubEndpointDiscoveryCallback
        public void onEndpointsStopped(final HubEndpointInfo[] hubEndpointInfoArr, final int i) {
            if (hubEndpointInfoArr.length == 0) {
                Log.w(ContextHubManager.TAG, "onEndpointsStopped: received empty discovery list");
                invokeCallbackFinished(this.val$service);
                return;
            }
            Executor executor = this.val$executor;
            final String str = this.val$serviceDescriptor;
            final HubEndpointDiscoveryCallback hubEndpointDiscoveryCallback = this.val$callback;
            final IContextHubService iContextHubService = this.val$service;
            executor.execute(new Runnable() { // from class: android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ContextHubManager.AnonymousClass2.this.lambda$onEndpointsStopped$1(hubEndpointInfoArr, str, hubEndpointDiscoveryCallback, i, iContextHubService);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEndpointsStopped$1(HubEndpointInfo[] hubEndpointInfoArr, String str, HubEndpointDiscoveryCallback hubEndpointDiscoveryCallback, int i, IContextHubService iContextHubService) {
            List<HubDiscoveryInfo> matchingEndpointDiscoveryList = ContextHubManager.this.getMatchingEndpointDiscoveryList(hubEndpointInfoArr, str);
            if (matchingEndpointDiscoveryList.isEmpty()) {
                Log.w(ContextHubManager.TAG, "onEndpointsStopped: no matching service descriptor");
            } else {
                hubEndpointDiscoveryCallback.onEndpointsStopped(matchingEndpointDiscoveryList, i);
            }
            invokeCallbackFinished(iContextHubService);
        }

        private void invokeCallbackFinished(IContextHubService iContextHubService) {
            try {
                iContextHubService.onDiscoveryCallbackFinished();
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    private IContextHubEndpointDiscoveryCallback createDiscoveryCallback(IContextHubService iContextHubService, Executor executor, HubEndpointDiscoveryCallback hubEndpointDiscoveryCallback, String str) {
        return new AnonymousClass2(iContextHubService, executor, str, hubEndpointDiscoveryCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<HubDiscoveryInfo> getMatchingEndpointDiscoveryList(HubEndpointInfo[] hubEndpointInfoArr, String str) {
        ArrayList arrayList = new ArrayList(hubEndpointInfoArr.length);
        for (HubEndpointInfo hubEndpointInfo : hubEndpointInfoArr) {
            if (str != null) {
                for (HubServiceInfo hubServiceInfo : hubEndpointInfo.getServiceInfoCollection()) {
                    if (hubServiceInfo.getServiceDescriptor().equals(str)) {
                        arrayList.add(new HubDiscoveryInfo(hubEndpointInfo, hubServiceInfo));
                    }
                }
            } else {
                arrayList.add(new HubDiscoveryInfo(hubEndpointInfo));
            }
        }
        return arrayList;
    }

    public void registerEndpointDiscoveryCallback(HubEndpointDiscoveryCallback hubEndpointDiscoveryCallback, long j) {
        registerEndpointDiscoveryCallback(new HandlerExecutor(Handler.getMain()), hubEndpointDiscoveryCallback, j);
    }

    public void registerEndpointDiscoveryCallback(Executor executor, HubEndpointDiscoveryCallback hubEndpointDiscoveryCallback, long j) {
        Objects.requireNonNull(executor, "executor cannot be null");
        Objects.requireNonNull(hubEndpointDiscoveryCallback, "callback cannot be null");
        IContextHubEndpointDiscoveryCallback createDiscoveryCallback = createDiscoveryCallback(this.mService, executor, hubEndpointDiscoveryCallback, null);
        try {
            this.mService.registerEndpointDiscoveryCallbackId(j, createDiscoveryCallback);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        this.mDiscoveryCallbacks.put(hubEndpointDiscoveryCallback, createDiscoveryCallback);
    }

    public void registerEndpointDiscoveryCallback(HubEndpointDiscoveryCallback hubEndpointDiscoveryCallback, String str) {
        registerEndpointDiscoveryCallback(new HandlerExecutor(Handler.getMain()), hubEndpointDiscoveryCallback, str);
    }

    public void registerEndpointDiscoveryCallback(Executor executor, HubEndpointDiscoveryCallback hubEndpointDiscoveryCallback, String str) {
        Objects.requireNonNull(executor, "executor cannot be null");
        Objects.requireNonNull(hubEndpointDiscoveryCallback, "callback cannot be null");
        Objects.requireNonNull(str, "serviceDescriptor cannot be null");
        if (str.isBlank()) {
            throw new IllegalArgumentException("Invalid service descriptor: " + str);
        }
        IContextHubEndpointDiscoveryCallback createDiscoveryCallback = createDiscoveryCallback(this.mService, executor, hubEndpointDiscoveryCallback, str);
        try {
            this.mService.registerEndpointDiscoveryCallbackDescriptor(str, createDiscoveryCallback);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        this.mDiscoveryCallbacks.put(hubEndpointDiscoveryCallback, createDiscoveryCallback);
    }

    public void unregisterEndpointDiscoveryCallback(HubEndpointDiscoveryCallback hubEndpointDiscoveryCallback) {
        Objects.requireNonNull(hubEndpointDiscoveryCallback, "callback cannot be null");
        IContextHubEndpointDiscoveryCallback remove = this.mDiscoveryCallbacks.remove(hubEndpointDiscoveryCallback);
        if (remove == null) {
            throw new IllegalArgumentException("Callback not previously registered");
        }
        try {
            this.mService.unregisterEndpointDiscoveryCallback(remove);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int registerCallback(Callback callback) {
        if (Flags.removeOldContextHubApis()) {
            return -1;
        }
        return registerCallback(callback, null);
    }

    @Deprecated
    public int registerCallback(ICallback iCallback) {
        if (Flags.removeOldContextHubApis()) {
            return -1;
        }
        if (this.mLocalCallback != null) {
            Log.w(TAG, "Max number of local callbacks reached!");
            return -1;
        }
        this.mLocalCallback = iCallback;
        return 0;
    }

    @Deprecated
    public int registerCallback(Callback callback, Handler handler) {
        if (Flags.removeOldContextHubApis()) {
            return -1;
        }
        synchronized (this) {
            if (this.mCallback != null) {
                Log.w(TAG, "Max number of callbacks reached!");
                return -1;
            }
            this.mCallback = callback;
            if (handler == null) {
                handler = new Handler(this.mMainLooper);
            }
            this.mCallbackHandler = handler;
            return 0;
        }
    }

    /* renamed from: android.hardware.location.ContextHubManager$3, reason: invalid class name */
    class AnonymousClass3 extends IContextHubClientCallback.Stub {
        final /* synthetic */ ContextHubClientCallback val$callback;
        final /* synthetic */ ContextHubClient val$client;
        final /* synthetic */ Executor val$executor;

        AnonymousClass3(ContextHubManager contextHubManager, Executor executor, ContextHubClientCallback contextHubClientCallback, ContextHubClient contextHubClient) {
            this.val$executor = executor;
            this.val$callback = contextHubClientCallback;
            this.val$client = contextHubClient;
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onMessageFromNanoApp(final NanoAppMessage nanoAppMessage) {
            Executor executor = this.val$executor;
            final ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final ContextHubClient contextHubClient = this.val$client;
            executor.execute(new Runnable() { // from class: android.hardware.location.ContextHubManager$3$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    ContextHubManager.AnonymousClass3.lambda$onMessageFromNanoApp$0(ContextHubClientCallback.this, contextHubClient, nanoAppMessage);
                }
            });
        }

        static /* synthetic */ void lambda$onMessageFromNanoApp$0(ContextHubClientCallback contextHubClientCallback, ContextHubClient contextHubClient, NanoAppMessage nanoAppMessage) {
            contextHubClientCallback.onMessageFromNanoApp(contextHubClient, nanoAppMessage);
            if (nanoAppMessage.isReliable()) {
                contextHubClient.reliableMessageCallbackFinished(nanoAppMessage.getMessageSequenceNumber(), (byte) 0);
            } else {
                contextHubClient.callbackFinished();
            }
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onHubReset() {
            Executor executor = this.val$executor;
            final ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final ContextHubClient contextHubClient = this.val$client;
            executor.execute(new Runnable() { // from class: android.hardware.location.ContextHubManager$3$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ContextHubManager.AnonymousClass3.lambda$onHubReset$1(ContextHubClientCallback.this, contextHubClient);
                }
            });
        }

        static /* synthetic */ void lambda$onHubReset$1(ContextHubClientCallback contextHubClientCallback, ContextHubClient contextHubClient) {
            contextHubClientCallback.onHubReset(contextHubClient);
            contextHubClient.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppAborted(final long j, final int i) {
            Executor executor = this.val$executor;
            final ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final ContextHubClient contextHubClient = this.val$client;
            executor.execute(new Runnable() { // from class: android.hardware.location.ContextHubManager$3$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ContextHubManager.AnonymousClass3.lambda$onNanoAppAborted$2(ContextHubClientCallback.this, contextHubClient, j, i);
                }
            });
        }

        static /* synthetic */ void lambda$onNanoAppAborted$2(ContextHubClientCallback contextHubClientCallback, ContextHubClient contextHubClient, long j, int i) {
            contextHubClientCallback.onNanoAppAborted(contextHubClient, j, i);
            contextHubClient.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppLoaded(final long j) {
            Executor executor = this.val$executor;
            final ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final ContextHubClient contextHubClient = this.val$client;
            executor.execute(new Runnable() { // from class: android.hardware.location.ContextHubManager$3$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    ContextHubManager.AnonymousClass3.lambda$onNanoAppLoaded$3(ContextHubClientCallback.this, contextHubClient, j);
                }
            });
        }

        static /* synthetic */ void lambda$onNanoAppLoaded$3(ContextHubClientCallback contextHubClientCallback, ContextHubClient contextHubClient, long j) {
            contextHubClientCallback.onNanoAppLoaded(contextHubClient, j);
            contextHubClient.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppUnloaded(final long j) {
            Executor executor = this.val$executor;
            final ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final ContextHubClient contextHubClient = this.val$client;
            executor.execute(new Runnable() { // from class: android.hardware.location.ContextHubManager$3$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    ContextHubManager.AnonymousClass3.lambda$onNanoAppUnloaded$4(ContextHubClientCallback.this, contextHubClient, j);
                }
            });
        }

        static /* synthetic */ void lambda$onNanoAppUnloaded$4(ContextHubClientCallback contextHubClientCallback, ContextHubClient contextHubClient, long j) {
            contextHubClientCallback.onNanoAppUnloaded(contextHubClient, j);
            contextHubClient.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppEnabled(final long j) {
            Executor executor = this.val$executor;
            final ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final ContextHubClient contextHubClient = this.val$client;
            executor.execute(new Runnable() { // from class: android.hardware.location.ContextHubManager$3$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    ContextHubManager.AnonymousClass3.lambda$onNanoAppEnabled$5(ContextHubClientCallback.this, contextHubClient, j);
                }
            });
        }

        static /* synthetic */ void lambda$onNanoAppEnabled$5(ContextHubClientCallback contextHubClientCallback, ContextHubClient contextHubClient, long j) {
            contextHubClientCallback.onNanoAppEnabled(contextHubClient, j);
            contextHubClient.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppDisabled(final long j) {
            Executor executor = this.val$executor;
            final ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final ContextHubClient contextHubClient = this.val$client;
            executor.execute(new Runnable() { // from class: android.hardware.location.ContextHubManager$3$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    ContextHubManager.AnonymousClass3.lambda$onNanoAppDisabled$6(ContextHubClientCallback.this, contextHubClient, j);
                }
            });
        }

        static /* synthetic */ void lambda$onNanoAppDisabled$6(ContextHubClientCallback contextHubClientCallback, ContextHubClient contextHubClient, long j) {
            contextHubClientCallback.onNanoAppDisabled(contextHubClient, j);
            contextHubClient.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onClientAuthorizationChanged(final long j, final int i) {
            Executor executor = this.val$executor;
            final ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final ContextHubClient contextHubClient = this.val$client;
            executor.execute(new Runnable() { // from class: android.hardware.location.ContextHubManager$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ContextHubManager.AnonymousClass3.lambda$onClientAuthorizationChanged$7(ContextHubClientCallback.this, contextHubClient, j, i);
                }
            });
        }

        static /* synthetic */ void lambda$onClientAuthorizationChanged$7(ContextHubClientCallback contextHubClientCallback, ContextHubClient contextHubClient, long j, int i) {
            contextHubClientCallback.onClientAuthorizationChanged(contextHubClient, j, i);
            contextHubClient.callbackFinished();
        }
    }

    private IContextHubClientCallback createClientCallback(ContextHubClient contextHubClient, ContextHubClientCallback contextHubClientCallback, Executor executor) {
        return new AnonymousClass3(this, executor, contextHubClientCallback, contextHubClient);
    }

    public ContextHubClient createClient(Context context, ContextHubInfo contextHubInfo, Executor executor, ContextHubClientCallback contextHubClientCallback) {
        String currentPackageName;
        Objects.requireNonNull(contextHubClientCallback, "Callback cannot be null");
        Objects.requireNonNull(contextHubInfo, "ContextHubInfo cannot be null");
        Objects.requireNonNull(executor, "Executor cannot be null");
        ContextHubClient contextHubClient = new ContextHubClient(contextHubInfo, false);
        IContextHubClientCallback createClientCallback = createClientCallback(contextHubClient, contextHubClientCallback, executor);
        String attributionTag = context != null ? context.getAttributionTag() : null;
        if (context != null) {
            currentPackageName = context.getPackageName();
        } else {
            currentPackageName = ActivityThread.currentPackageName();
        }
        try {
            contextHubClient.setClientProxy(this.mService.createClient(contextHubInfo.getId(), createClientCallback, attributionTag, currentPackageName));
            return contextHubClient;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ContextHubClient createClient(ContextHubInfo contextHubInfo, ContextHubClientCallback contextHubClientCallback, Executor executor) {
        return createClient((Context) null, contextHubInfo, executor, contextHubClientCallback);
    }

    public ContextHubClient createClient(ContextHubInfo contextHubInfo, ContextHubClientCallback contextHubClientCallback) {
        return createClient((Context) null, contextHubInfo, new HandlerExecutor(Handler.getMain()), contextHubClientCallback);
    }

    public ContextHubClient createClient(Context context, ContextHubInfo contextHubInfo, PendingIntent pendingIntent, long j) {
        Objects.requireNonNull(pendingIntent);
        Objects.requireNonNull(contextHubInfo);
        if (pendingIntent.isImmutable()) {
            throw new IllegalArgumentException("PendingIntent must be mutable");
        }
        ContextHubClient contextHubClient = new ContextHubClient(contextHubInfo, true);
        try {
            contextHubClient.setClientProxy(this.mService.createPendingIntentClient(contextHubInfo.getId(), pendingIntent, j, context != null ? context.getAttributionTag() : null));
            return contextHubClient;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ContextHubClient createClient(ContextHubInfo contextHubInfo, PendingIntent pendingIntent, long j) {
        return createClient((Context) null, contextHubInfo, pendingIntent, j);
    }

    public void registerEndpoint(HubEndpoint hubEndpoint) {
        hubEndpoint.register(this.mService);
    }

    public void openSession(HubEndpoint hubEndpoint, HubEndpointInfo hubEndpointInfo) {
        hubEndpoint.openSession(hubEndpointInfo, null);
    }

    public void openSession(HubEndpoint hubEndpoint, HubEndpointInfo hubEndpointInfo, String str) {
        hubEndpoint.openSession(hubEndpointInfo, str);
    }

    public void unregisterEndpoint(HubEndpoint hubEndpoint) {
        hubEndpoint.unregister();
    }

    public long[] getPreloadedNanoAppIds(ContextHubInfo contextHubInfo) {
        Objects.requireNonNull(contextHubInfo, "hubInfo cannot be null");
        try {
            long[] preloadedNanoAppIds = this.mService.getPreloadedNanoAppIds(contextHubInfo);
            return preloadedNanoAppIds == null ? new long[0] : preloadedNanoAppIds;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean enableTestMode() {
        try {
            return this.mService.setTestMode(true);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean disableTestMode() {
        try {
            return this.mService.setTestMode(false);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int unregisterCallback(Callback callback) {
        if (Flags.removeOldContextHubApis()) {
            return -1;
        }
        synchronized (this) {
            if (callback != this.mCallback) {
                Log.w(TAG, "Cannot recognize callback!");
                return -1;
            }
            this.mCallback = null;
            this.mCallbackHandler = null;
            return 0;
        }
    }

    @Deprecated
    public synchronized int unregisterCallback(ICallback iCallback) {
        if (Flags.removeOldContextHubApis()) {
            return -1;
        }
        if (iCallback != this.mLocalCallback) {
            Log.w(TAG, "Cannot recognize local callback!");
            return -1;
        }
        this.mLocalCallback = null;
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void invokeOnMessageReceiptCallback(int i, int i2, ContextHubMessage contextHubMessage) {
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onMessageReceipt(i, i2, contextHubMessage);
        }
    }

    /* renamed from: android.hardware.location.ContextHubManager$4, reason: invalid class name */
    class AnonymousClass4 extends IContextHubCallback.Stub {
        AnonymousClass4() {
        }

        @Override // android.hardware.location.IContextHubCallback
        public void onMessageReceipt(final int i, final int i2, final ContextHubMessage contextHubMessage) {
            synchronized (ContextHubManager.this) {
                if (ContextHubManager.this.mCallback != null) {
                    ContextHubManager.this.mCallbackHandler.post(new Runnable() { // from class: android.hardware.location.ContextHubManager$4$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ContextHubManager.AnonymousClass4.this.lambda$onMessageReceipt$0(i, i2, contextHubMessage);
                        }
                    });
                } else if (ContextHubManager.this.mLocalCallback != null) {
                    ContextHubManager.this.mLocalCallback.onMessageReceipt(i, i2, contextHubMessage);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageReceipt$0(int i, int i2, ContextHubMessage contextHubMessage) {
            ContextHubManager.this.invokeOnMessageReceiptCallback(i, i2, contextHubMessage);
        }
    }

    public ContextHubManager(IContextHubService iContextHubService, Looper looper) {
        AnonymousClass4 anonymousClass4 = new AnonymousClass4();
        this.mClientCallback = anonymousClass4;
        Objects.requireNonNull(iContextHubService, "service cannot be null");
        Objects.requireNonNull(looper, "mainLooper cannot be null");
        this.mService = iContextHubService;
        this.mMainLooper = looper;
        try {
            iContextHubService.registerCallback(anonymousClass4);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
