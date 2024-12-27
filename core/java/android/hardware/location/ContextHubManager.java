package android.hardware.location;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.chre.flags.Flags;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

@SystemApi
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
    public static final String EXTRA_CLIENT_AUTHORIZATION_STATE =
            "android.hardware.location.extra.CLIENT_AUTHORIZATION_STATE";
    public static final String EXTRA_CONTEXT_HUB_INFO =
            "android.hardware.location.extra.CONTEXT_HUB_INFO";
    public static final String EXTRA_EVENT_TYPE = "android.hardware.location.extra.EVENT_TYPE";
    public static final String EXTRA_MESSAGE = "android.hardware.location.extra.MESSAGE";
    public static final String EXTRA_NANOAPP_ABORT_CODE =
            "android.hardware.location.extra.NANOAPP_ABORT_CODE";
    public static final String EXTRA_NANOAPP_ID = "android.hardware.location.extra.NANOAPP_ID";
    private static final String TAG = "ContextHubManager";
    private Callback mCallback;
    private Handler mCallbackHandler;
    private final IContextHubCallback.Stub mClientCallback = new AnonymousClass3();

    @Deprecated private ICallback mLocalCallback;
    private final Looper mMainLooper;
    private final IContextHubService mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthorizationState {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface Event {}

    @Deprecated
    public interface ICallback {
        void onMessageReceipt(int i, int i2, ContextHubMessage contextHubMessage);
    }

    @Deprecated
    public abstract static class Callback {
        public abstract void onMessageReceipt(int i, int i2, ContextHubMessage contextHubMessage);

        protected Callback() {}
    }

    @Deprecated
    public int[] getContextHubHandles() {
        try {
            return this.mService.getContextHubHandles();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public ContextHubInfo getContextHubInfo(int hubHandle) {
        try {
            return this.mService.getContextHubInfo(hubHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int loadNanoApp(int hubHandle, NanoApp app) {
        try {
            return this.mService.loadNanoApp(hubHandle, app);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int unloadNanoApp(int nanoAppHandle) {
        try {
            return this.mService.unloadNanoApp(nanoAppHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public NanoAppInstanceInfo getNanoAppInstanceInfo(int nanoAppHandle) {
        try {
            return this.mService.getNanoAppInstanceInfo(nanoAppHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int[] findNanoAppOnHub(int hubHandle, NanoAppFilter filter) {
        try {
            return this.mService.findNanoAppOnHub(hubHandle, filter);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int sendMessage(int hubHandle, int nanoAppHandle, ContextHubMessage message) {
        try {
            return this.mService.sendMessage(hubHandle, nanoAppHandle, message);
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

    private IContextHubTransactionCallback createQueryCallback(
            final ContextHubTransaction<List<NanoAppState>> transaction) {
        return new IContextHubTransactionCallback
                .Stub() { // from class: android.hardware.location.ContextHubManager.1
            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onQueryResponse(int result, List<NanoAppState> nanoappList) {
                transaction.setResponse(new ContextHubTransaction.Response(result, nanoappList));
            }

            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onTransactionComplete(int result) {
                Log.e(ContextHubManager.TAG, "Received a non-query callback on a query request");
                transaction.setResponse(new ContextHubTransaction.Response(7, null));
            }
        };
    }

    public ContextHubTransaction<Void> loadNanoApp(
            ContextHubInfo hubInfo, NanoAppBinary appBinary) {
        Objects.requireNonNull(hubInfo, "ContextHubInfo cannot be null");
        Objects.requireNonNull(appBinary, "NanoAppBinary cannot be null");
        ContextHubTransaction<Void> transaction = new ContextHubTransaction<>(0);
        IContextHubTransactionCallback callback =
                ContextHubTransactionHelper.createTransactionCallback(transaction);
        try {
            this.mService.loadNanoAppOnHub(hubInfo.getId(), callback, appBinary);
            return transaction;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ContextHubTransaction<Void> unloadNanoApp(ContextHubInfo hubInfo, long nanoAppId) {
        Objects.requireNonNull(hubInfo, "ContextHubInfo cannot be null");
        ContextHubTransaction<Void> transaction = new ContextHubTransaction<>(1);
        IContextHubTransactionCallback callback =
                ContextHubTransactionHelper.createTransactionCallback(transaction);
        try {
            this.mService.unloadNanoAppFromHub(hubInfo.getId(), callback, nanoAppId);
            return transaction;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ContextHubTransaction<Void> enableNanoApp(ContextHubInfo hubInfo, long nanoAppId) {
        Objects.requireNonNull(hubInfo, "ContextHubInfo cannot be null");
        ContextHubTransaction<Void> transaction = new ContextHubTransaction<>(2);
        IContextHubTransactionCallback callback =
                ContextHubTransactionHelper.createTransactionCallback(transaction);
        try {
            this.mService.enableNanoApp(hubInfo.getId(), callback, nanoAppId);
            return transaction;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ContextHubTransaction<Void> disableNanoApp(ContextHubInfo hubInfo, long nanoAppId) {
        Objects.requireNonNull(hubInfo, "ContextHubInfo cannot be null");
        ContextHubTransaction<Void> transaction = new ContextHubTransaction<>(3);
        IContextHubTransactionCallback callback =
                ContextHubTransactionHelper.createTransactionCallback(transaction);
        try {
            this.mService.disableNanoApp(hubInfo.getId(), callback, nanoAppId);
            return transaction;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ContextHubTransaction<List<NanoAppState>> queryNanoApps(ContextHubInfo hubInfo) {
        Objects.requireNonNull(hubInfo, "ContextHubInfo cannot be null");
        ContextHubTransaction<List<NanoAppState>> transaction = new ContextHubTransaction<>(4);
        IContextHubTransactionCallback callback = createQueryCallback(transaction);
        try {
            this.mService.queryNanoApps(hubInfo.getId(), callback);
            return transaction;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int registerCallback(Callback callback) {
        return registerCallback(callback, null);
    }

    @Deprecated
    public int registerCallback(ICallback callback) {
        if (this.mLocalCallback != null) {
            Log.w(TAG, "Max number of local callbacks reached!");
            return -1;
        }
        this.mLocalCallback = callback;
        return 0;
    }

    @Deprecated
    public int registerCallback(Callback callback, Handler handler) {
        synchronized (this) {
            if (this.mCallback != null) {
                Log.w(TAG, "Max number of callbacks reached!");
                return -1;
            }
            this.mCallback = callback;
            this.mCallbackHandler = handler == null ? new Handler(this.mMainLooper) : handler;
            return 0;
        }
    }

    /* renamed from: android.hardware.location.ContextHubManager$2, reason: invalid class name */
    class AnonymousClass2 extends IContextHubClientCallback.Stub {
        final /* synthetic */ ContextHubClientCallback val$callback;
        final /* synthetic */ ContextHubClient val$client;
        final /* synthetic */ Executor val$executor;

        AnonymousClass2(
                Executor executor,
                ContextHubClientCallback contextHubClientCallback,
                ContextHubClient contextHubClient) {
            this.val$executor = executor;
            this.val$callback = contextHubClientCallback;
            this.val$client = contextHubClient;
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onMessageFromNanoApp(final NanoAppMessage message) {
            Executor executor = this.val$executor;
            final ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final ContextHubClient contextHubClient = this.val$client;
            executor.execute(
                    new Runnable() { // from class:
                        // android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            ContextHubManager.AnonymousClass2.lambda$onMessageFromNanoApp$0(
                                    ContextHubClientCallback.this, contextHubClient, message);
                        }
                    });
        }

        static /* synthetic */ void lambda$onMessageFromNanoApp$0(
                ContextHubClientCallback callback,
                ContextHubClient client,
                NanoAppMessage message) {
            callback.onMessageFromNanoApp(client, message);
            if (Flags.reliableMessage()
                    && Flags.reliableMessageImplementation()
                    && message.isReliable()) {
                client.reliableMessageCallbackFinished(
                        message.getMessageSequenceNumber(), (byte) 0);
            } else {
                client.callbackFinished();
            }
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onHubReset() {
            Executor executor = this.val$executor;
            final ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final ContextHubClient contextHubClient = this.val$client;
            executor.execute(
                    new Runnable() { // from class:
                        // android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            ContextHubManager.AnonymousClass2.lambda$onHubReset$1(
                                    ContextHubClientCallback.this, contextHubClient);
                        }
                    });
        }

        static /* synthetic */ void lambda$onHubReset$1(
                ContextHubClientCallback callback, ContextHubClient client) {
            callback.onHubReset(client);
            client.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppAborted(final long nanoAppId, final int abortCode) {
            Executor executor = this.val$executor;
            final ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final ContextHubClient contextHubClient = this.val$client;
            executor.execute(
                    new Runnable() { // from class:
                        // android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ContextHubManager.AnonymousClass2.lambda$onNanoAppAborted$2(
                                    ContextHubClientCallback.this,
                                    contextHubClient,
                                    nanoAppId,
                                    abortCode);
                        }
                    });
        }

        static /* synthetic */ void lambda$onNanoAppAborted$2(
                ContextHubClientCallback callback,
                ContextHubClient client,
                long nanoAppId,
                int abortCode) {
            callback.onNanoAppAborted(client, nanoAppId, abortCode);
            client.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppLoaded(final long nanoAppId) {
            Executor executor = this.val$executor;
            final ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final ContextHubClient contextHubClient = this.val$client;
            executor.execute(
                    new Runnable() { // from class:
                        // android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            ContextHubManager.AnonymousClass2.lambda$onNanoAppLoaded$3(
                                    ContextHubClientCallback.this, contextHubClient, nanoAppId);
                        }
                    });
        }

        static /* synthetic */ void lambda$onNanoAppLoaded$3(
                ContextHubClientCallback callback, ContextHubClient client, long nanoAppId) {
            callback.onNanoAppLoaded(client, nanoAppId);
            client.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppUnloaded(final long nanoAppId) {
            Executor executor = this.val$executor;
            final ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final ContextHubClient contextHubClient = this.val$client;
            executor.execute(
                    new Runnable() { // from class:
                        // android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ContextHubManager.AnonymousClass2.lambda$onNanoAppUnloaded$4(
                                    ContextHubClientCallback.this, contextHubClient, nanoAppId);
                        }
                    });
        }

        static /* synthetic */ void lambda$onNanoAppUnloaded$4(
                ContextHubClientCallback callback, ContextHubClient client, long nanoAppId) {
            callback.onNanoAppUnloaded(client, nanoAppId);
            client.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppEnabled(final long nanoAppId) {
            Executor executor = this.val$executor;
            final ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final ContextHubClient contextHubClient = this.val$client;
            executor.execute(
                    new Runnable() { // from class:
                        // android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            ContextHubManager.AnonymousClass2.lambda$onNanoAppEnabled$5(
                                    ContextHubClientCallback.this, contextHubClient, nanoAppId);
                        }
                    });
        }

        static /* synthetic */ void lambda$onNanoAppEnabled$5(
                ContextHubClientCallback callback, ContextHubClient client, long nanoAppId) {
            callback.onNanoAppEnabled(client, nanoAppId);
            client.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppDisabled(final long nanoAppId) {
            Executor executor = this.val$executor;
            final ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final ContextHubClient contextHubClient = this.val$client;
            executor.execute(
                    new Runnable() { // from class:
                        // android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            ContextHubManager.AnonymousClass2.lambda$onNanoAppDisabled$6(
                                    ContextHubClientCallback.this, contextHubClient, nanoAppId);
                        }
                    });
        }

        static /* synthetic */ void lambda$onNanoAppDisabled$6(
                ContextHubClientCallback callback, ContextHubClient client, long nanoAppId) {
            callback.onNanoAppDisabled(client, nanoAppId);
            client.callbackFinished();
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onClientAuthorizationChanged(final long nanoAppId, final int authorization) {
            Executor executor = this.val$executor;
            final ContextHubClientCallback contextHubClientCallback = this.val$callback;
            final ContextHubClient contextHubClient = this.val$client;
            executor.execute(
                    new Runnable() { // from class:
                        // android.hardware.location.ContextHubManager$2$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            ContextHubManager.AnonymousClass2.lambda$onClientAuthorizationChanged$7(
                                    ContextHubClientCallback.this,
                                    contextHubClient,
                                    nanoAppId,
                                    authorization);
                        }
                    });
        }

        static /* synthetic */ void lambda$onClientAuthorizationChanged$7(
                ContextHubClientCallback callback,
                ContextHubClient client,
                long nanoAppId,
                int authorization) {
            callback.onClientAuthorizationChanged(client, nanoAppId, authorization);
            client.callbackFinished();
        }
    }

    private IContextHubClientCallback createClientCallback(
            ContextHubClient client, ContextHubClientCallback callback, Executor executor) {
        return new AnonymousClass2(executor, callback, client);
    }

    public ContextHubClient createClient(
            Context context,
            ContextHubInfo hubInfo,
            Executor executor,
            ContextHubClientCallback callback) {
        String packageName;
        Objects.requireNonNull(callback, "Callback cannot be null");
        Objects.requireNonNull(hubInfo, "ContextHubInfo cannot be null");
        Objects.requireNonNull(executor, "Executor cannot be null");
        ContextHubClient client = new ContextHubClient(hubInfo, false);
        IContextHubClientCallback clientInterface =
                createClientCallback(client, callback, executor);
        String attributionTag = null;
        if (context != null) {
            attributionTag = context.getAttributionTag();
        }
        if (context != null) {
            packageName = context.getPackageName();
        } else {
            packageName = ActivityThread.currentPackageName();
        }
        try {
            IContextHubClient clientProxy =
                    this.mService.createClient(
                            hubInfo.getId(), clientInterface, attributionTag, packageName);
            client.setClientProxy(clientProxy);
            return client;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ContextHubClient createClient(
            ContextHubInfo hubInfo, ContextHubClientCallback callback, Executor executor) {
        return createClient((Context) null, hubInfo, executor, callback);
    }

    public ContextHubClient createClient(
            ContextHubInfo hubInfo, ContextHubClientCallback callback) {
        return createClient(
                (Context) null, hubInfo, new HandlerExecutor(Handler.getMain()), callback);
    }

    public ContextHubClient createClient(
            Context context, ContextHubInfo hubInfo, PendingIntent pendingIntent, long nanoAppId) {
        Objects.requireNonNull(pendingIntent);
        Objects.requireNonNull(hubInfo);
        if (pendingIntent.isImmutable()) {
            throw new IllegalArgumentException("PendingIntent must be mutable");
        }
        ContextHubClient client = new ContextHubClient(hubInfo, true);
        String attributionTag = null;
        if (context != null) {
            attributionTag = context.getAttributionTag();
        }
        try {
            IContextHubClient clientProxy =
                    this.mService.createPendingIntentClient(
                            hubInfo.getId(), pendingIntent, nanoAppId, attributionTag);
            client.setClientProxy(clientProxy);
            return client;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ContextHubClient createClient(
            ContextHubInfo hubInfo, PendingIntent pendingIntent, long nanoAppId) {
        return createClient((Context) null, hubInfo, pendingIntent, nanoAppId);
    }

    public long[] getPreloadedNanoAppIds(ContextHubInfo hubInfo) {
        Objects.requireNonNull(hubInfo, "hubInfo cannot be null");
        try {
            long[] nanoappIds = this.mService.getPreloadedNanoAppIds(hubInfo);
            return nanoappIds == null ? new long[0] : nanoappIds;
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
    public synchronized int unregisterCallback(ICallback callback) {
        if (callback != this.mLocalCallback) {
            Log.w(TAG, "Cannot recognize local callback!");
            return -1;
        }
        this.mLocalCallback = null;
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void invokeOnMessageReceiptCallback(
            int hubId, int nanoAppId, ContextHubMessage message) {
        if (this.mCallback != null) {
            this.mCallback.onMessageReceipt(hubId, nanoAppId, message);
        }
    }

    /* renamed from: android.hardware.location.ContextHubManager$3, reason: invalid class name */
    class AnonymousClass3 extends IContextHubCallback.Stub {
        AnonymousClass3() {}

        @Override // android.hardware.location.IContextHubCallback
        public void onMessageReceipt(
                final int hubId, final int nanoAppId, final ContextHubMessage message) {
            synchronized (ContextHubManager.this) {
                if (ContextHubManager.this.mCallback != null) {
                    ContextHubManager.this.mCallbackHandler.post(
                            new Runnable() { // from class:
                                // android.hardware.location.ContextHubManager$3$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ContextHubManager.AnonymousClass3.this
                                            .lambda$onMessageReceipt$0(hubId, nanoAppId, message);
                                }
                            });
                } else if (ContextHubManager.this.mLocalCallback != null) {
                    ContextHubManager.this.mLocalCallback.onMessageReceipt(
                            hubId, nanoAppId, message);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageReceipt$0(
                int hubId, int nanoAppId, ContextHubMessage message) {
            ContextHubManager.this.invokeOnMessageReceiptCallback(hubId, nanoAppId, message);
        }
    }

    public ContextHubManager(IContextHubService service, Looper mainLooper) {
        Objects.requireNonNull(service, "service cannot be null");
        Objects.requireNonNull(mainLooper, "mainLooper cannot be null");
        this.mService = service;
        this.mMainLooper = mainLooper;
        try {
            this.mService.registerCallback(this.mClientCallback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
