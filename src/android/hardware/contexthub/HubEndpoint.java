package android.hardware.contexthub;

import android.annotation.SystemApi;
import android.content.Context;
import android.hardware.contexthub.IContextHubEndpointCallback;
import android.hardware.location.IContextHubService;
import android.hardware.location.IContextHubTransactionCallback;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes2.dex */
public class HubEndpoint {
    public static final int REASON_CLOSE_ENDPOINT_SESSION_REQUESTED = 4;
    public static final int REASON_ENDPOINT_INVALID = 5;
    public static final int REASON_ENDPOINT_STOPPED = 6;
    public static final int REASON_FAILURE = 0;
    public static final int REASON_OPEN_ENDPOINT_SESSION_REQUEST_REJECTED = 3;
    public static final int REASON_PERMISSION_DENIED = 9;
    private static final String TAG = "HubEndpoint";
    private final SparseArray<HubEndpointSession> mActiveSessions;
    private HubEndpointInfo mAssignedHubEndpointInfo;
    private final HubEndpointLifecycleCallback mLifecycleCallback;
    private final Executor mLifecycleCallbackExecutor;
    private final Object mLock;
    private final HubEndpointMessageCallback mMessageCallback;
    private final Executor mMessageCallbackExecutor;
    private final HubEndpointInfo mPendingHubEndpointInfo;
    private final IContextHubEndpointCallback mServiceCallback;
    private IContextHubEndpoint mServiceToken;

    interface EndpointConsumer {
        void accept(IContextHubEndpoint iContextHubEndpoint) throws RemoteException;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Reason {
    }

    /* renamed from: android.hardware.contexthub.HubEndpoint$1, reason: invalid class name */
    class AnonymousClass1 extends IContextHubEndpointCallback.Stub {
        AnonymousClass1() {
        }

        @Override // android.hardware.contexthub.IContextHubEndpointCallback
        public void onSessionOpenRequest(final int i, final HubEndpointInfo hubEndpointInfo, final String str) throws RemoteException {
            boolean z = getActiveSession(i) != null;
            if (z) {
                Log.w(HubEndpoint.TAG, "onSessionOpenRequest: session already exists, id=" + i);
            }
            if (HubEndpoint.this.mLifecycleCallback == null) {
                Log.w(HubEndpoint.TAG, "onSessionOpenRequest: failed to open session, no lifecycle callback attached", new Exception());
                rejectSession(i);
            }
            if (!z && HubEndpoint.this.mLifecycleCallback != null) {
                HubEndpoint.this.mLifecycleCallbackExecutor.execute(new Runnable() { // from class: android.hardware.contexthub.HubEndpoint$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onSessionOpenRequest$0(i, hubEndpointInfo, str);
                    }
                });
            } else {
                invokeCallbackFinished();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSessionOpenRequest$0(int i, HubEndpointInfo hubEndpointInfo, String str) {
            processSessionOpenRequestResult(i, hubEndpointInfo, str, HubEndpoint.this.mLifecycleCallback.onSessionOpenRequest(hubEndpointInfo, str));
        }

        @Override // android.hardware.contexthub.IContextHubEndpointCallback
        public void onSessionOpenComplete(int i) throws RemoteException {
            final HubEndpointSession activeSession = getActiveSession(i);
            if (activeSession == null) {
                Log.w(HubEndpoint.TAG, "onSessionOpenComplete: no pending session open request? id=" + i);
            } else {
                activeSession.setOpened();
            }
            if (activeSession != null && HubEndpoint.this.mLifecycleCallback != null) {
                HubEndpoint.this.mLifecycleCallbackExecutor.execute(new Runnable() { // from class: android.hardware.contexthub.HubEndpoint$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onSessionOpenComplete$1(activeSession);
                    }
                });
            } else {
                invokeCallbackFinished();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSessionOpenComplete$1(HubEndpointSession hubEndpointSession) {
            HubEndpoint.this.mLifecycleCallback.onSessionOpened(hubEndpointSession);
            invokeCallbackFinished();
        }

        @Override // android.hardware.contexthub.IContextHubEndpointCallback
        public void onSessionClosed(final int i, final int i2) throws RemoteException {
            final HubEndpointSession activeSession = getActiveSession(i);
            if (activeSession == null) {
                Log.w(HubEndpoint.TAG, "onSessionClosed: session not active, id=" + i);
            }
            if (activeSession != null && HubEndpoint.this.mLifecycleCallback != null) {
                HubEndpoint.this.mLifecycleCallbackExecutor.execute(new Runnable() { // from class: android.hardware.contexthub.HubEndpoint$1$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onSessionClosed$2(activeSession, i2, i);
                    }
                });
            } else {
                invokeCallbackFinished();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSessionClosed$2(HubEndpointSession hubEndpointSession, int i, int i2) {
            HubEndpoint.this.mLifecycleCallback.onSessionClosed(hubEndpointSession, i);
            hubEndpointSession.setClosed();
            synchronized (HubEndpoint.this.mLock) {
                HubEndpoint.this.mActiveSessions.remove(i2);
            }
            invokeCallbackFinished();
        }

        @Override // android.hardware.contexthub.IContextHubEndpointCallback
        public void onMessageReceived(final int i, final HubMessage hubMessage) throws RemoteException {
            final HubEndpointSession activeSession = getActiveSession(i);
            if (activeSession == null) {
                Log.w(HubEndpoint.TAG, "onMessageReceived: session not active, id=" + i);
            }
            if (activeSession == null || HubEndpoint.this.mMessageCallback == null) {
                sendMessageDeliveryStatus(i, hubMessage, (byte) 4);
            } else {
                HubEndpoint.this.mMessageCallbackExecutor.execute(new Runnable() { // from class: android.hardware.contexthub.HubEndpoint$1$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onMessageReceived$3(activeSession, hubMessage, i);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageReceived$3(HubEndpointSession hubEndpointSession, HubMessage hubMessage, int i) {
            HubEndpoint.this.mMessageCallback.onMessageReceived(hubEndpointSession, hubMessage);
            sendMessageDeliveryStatus(i, hubMessage, (byte) 0);
        }

        private HubEndpointSession getActiveSession(int i) {
            HubEndpointSession hubEndpointSession;
            synchronized (HubEndpoint.this.mLock) {
                hubEndpointSession = (HubEndpointSession) HubEndpoint.this.mActiveSessions.get(i);
            }
            return hubEndpointSession;
        }

        private void sendMessageDeliveryStatus(final int i, final HubMessage hubMessage, final byte b) {
            if (hubMessage.isResponseRequired()) {
                invokeCallback(new EndpointConsumer() { // from class: android.hardware.contexthub.HubEndpoint$1$$ExternalSyntheticLambda8
                    @Override // android.hardware.contexthub.HubEndpoint.EndpointConsumer
                    public final void accept(IContextHubEndpoint iContextHubEndpoint) throws RemoteException {
                        iContextHubEndpoint.sendMessageDeliveryStatus(i, hubMessage.getMessageSequenceNumber(), b);
                    }
                });
            }
            invokeCallbackFinished();
        }

        private void processSessionOpenRequestResult(int i, HubEndpointInfo hubEndpointInfo, String str, HubEndpointSessionResult hubEndpointSessionResult) {
            if (hubEndpointSessionResult == null) {
                throw new IllegalArgumentException("HubEndpointSessionResult shouldn't be null.");
            }
            if (hubEndpointSessionResult.isAccepted()) {
                acceptSession(i, hubEndpointInfo, str);
            } else {
                Log.e(HubEndpoint.TAG, "Session " + i + " from " + hubEndpointInfo + " was rejected, reason=" + hubEndpointSessionResult.getReason());
                rejectSession(i);
            }
            invokeCallbackFinished();
        }

        private void acceptSession(final int i, HubEndpointInfo hubEndpointInfo, String str) {
            synchronized (HubEndpoint.this.mLock) {
                if (((HubEndpointSession) HubEndpoint.this.mActiveSessions.get(i)) != null) {
                    Log.e(HubEndpoint.TAG, "onSessionOpenRequestResult: session already exists, id=" + i);
                    return;
                }
                HubEndpoint hubEndpoint = HubEndpoint.this;
                final HubEndpointSession hubEndpointSession = new HubEndpointSession(i, hubEndpoint, hubEndpoint.mAssignedHubEndpointInfo, hubEndpointInfo, str);
                invokeCallback(new EndpointConsumer() { // from class: android.hardware.contexthub.HubEndpoint$1$$ExternalSyntheticLambda4
                    @Override // android.hardware.contexthub.HubEndpoint.EndpointConsumer
                    public final void accept(IContextHubEndpoint iContextHubEndpoint) throws RemoteException {
                        iContextHubEndpoint.openSessionRequestComplete(i);
                    }
                });
                HubEndpoint.this.mActiveSessions.put(i, hubEndpointSession);
                hubEndpointSession.setOpened();
                if (HubEndpoint.this.mLifecycleCallback != null) {
                    HubEndpoint.this.mLifecycleCallbackExecutor.execute(new Runnable() { // from class: android.hardware.contexthub.HubEndpoint$1$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$acceptSession$6(hubEndpointSession);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$acceptSession$6(HubEndpointSession hubEndpointSession) {
            HubEndpoint.this.mLifecycleCallback.onSessionOpened(hubEndpointSession);
        }

        private void rejectSession(final int i) {
            invokeCallback(new EndpointConsumer() { // from class: android.hardware.contexthub.HubEndpoint$1$$ExternalSyntheticLambda1
                @Override // android.hardware.contexthub.HubEndpoint.EndpointConsumer
                public final void accept(IContextHubEndpoint iContextHubEndpoint) throws RemoteException {
                    iContextHubEndpoint.closeSession(i, 3);
                }
            });
        }

        private void invokeCallbackFinished() {
            invokeCallback(new EndpointConsumer() { // from class: android.hardware.contexthub.HubEndpoint$1$$ExternalSyntheticLambda7
                @Override // android.hardware.contexthub.HubEndpoint.EndpointConsumer
                public final void accept(IContextHubEndpoint iContextHubEndpoint) throws RemoteException {
                    iContextHubEndpoint.onCallbackFinished();
                }
            });
        }

        private void invokeCallback(EndpointConsumer endpointConsumer) {
            try {
                endpointConsumer.accept(HubEndpoint.this.mServiceToken);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            } catch (IllegalStateException e2) {
                Log.w(HubEndpoint.TAG, "IllegalStateException while calling callback", e2);
            }
        }
    }

    private HubEndpoint(HubEndpointInfo hubEndpointInfo, HubEndpointLifecycleCallback hubEndpointLifecycleCallback, Executor executor, HubEndpointMessageCallback hubEndpointMessageCallback, Executor executor2) {
        this.mLock = new Object();
        this.mActiveSessions = new SparseArray<>();
        this.mServiceCallback = new AnonymousClass1();
        this.mPendingHubEndpointInfo = hubEndpointInfo;
        this.mLifecycleCallback = hubEndpointLifecycleCallback;
        this.mLifecycleCallbackExecutor = executor;
        this.mMessageCallback = hubEndpointMessageCallback;
        this.mMessageCallbackExecutor = executor2;
    }

    public void register(IContextHubService iContextHubService) {
        try {
            HubEndpointInfo hubEndpointInfo = this.mPendingHubEndpointInfo;
            IContextHubEndpoint iContextHubEndpointRegisterEndpoint = iContextHubService.registerEndpoint(hubEndpointInfo, this.mServiceCallback, hubEndpointInfo.getName(), this.mPendingHubEndpointInfo.getTag());
            this.mAssignedHubEndpointInfo = iContextHubEndpointRegisterEndpoint.getAssignedHubEndpointInfo();
            this.mServiceToken = iContextHubEndpointRegisterEndpoint;
        } catch (RemoteException e) {
            Log.e(TAG, "registerEndpoint: failed to register endpoint", e);
            e.rethrowFromSystemServer();
        }
    }

    public void unregister() {
        try {
            synchronized (this.mLock) {
                for (int i = 0; i < this.mActiveSessions.size(); i++) {
                    SparseArray<HubEndpointSession> sparseArray = this.mActiveSessions;
                    sparseArray.get(sparseArray.keyAt(i)).setClosed();
                }
                this.mActiveSessions.clear();
            }
            this.mServiceToken.unregister();
        } catch (RemoteException e) {
            Log.e(TAG, "unregisterEndpoint: failed to unregister endpoint", e);
            e.rethrowFromSystemServer();
        }
    }

    public void openSession(HubEndpointInfo hubEndpointInfo, String str) throws Throwable {
        HubEndpointInfo hubEndpointInfo2;
        try {
            try {
                synchronized (this.mLock) {
                    try {
                        int iOpenSession = this.mServiceToken.openSession(hubEndpointInfo, str);
                        this.mActiveSessions.put(iOpenSession, new HubEndpointSession(iOpenSession, this, hubEndpointInfo, this.mAssignedHubEndpointInfo, str));
                    } catch (Throwable th) {
                        th = th;
                        hubEndpointInfo2 = hubEndpointInfo;
                        Throwable th2 = th;
                        try {
                            throw th2;
                        } catch (RemoteException e) {
                            e = e;
                            RemoteException remoteException = e;
                            Log.e(TAG, "openSession: failed to open session to " + hubEndpointInfo2, remoteException);
                            remoteException.rethrowFromSystemServer();
                        }
                    }
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (RemoteException e2) {
            e = e2;
            hubEndpointInfo2 = hubEndpointInfo;
        }
    }

    public void closeSession(HubEndpointSession hubEndpointSession) {
        synchronized (this.mLock) {
            if (this.mActiveSessions.contains(hubEndpointSession.getId())) {
                hubEndpointSession.setClosed();
                this.mActiveSessions.remove(hubEndpointSession.getId());
                try {
                    this.mServiceToken.closeSession(hubEndpointSession.getId(), 4);
                } catch (RemoteException e) {
                    Log.e(TAG, "closeSession: failed to close session " + hubEndpointSession, e);
                    e.rethrowFromSystemServer();
                }
            }
        }
    }

    void sendMessage(HubEndpointSession hubEndpointSession, HubMessage hubMessage, IContextHubTransactionCallback iContextHubTransactionCallback) {
        try {
            this.mServiceToken.sendMessage(hubEndpointSession.getId(), hubMessage, iContextHubTransactionCallback);
        } catch (RemoteException e) {
            Log.e(TAG, "sendMessage: failed to send message session=" + hubEndpointSession, e);
            e.rethrowFromSystemServer();
        }
    }

    public int getVersion() {
        return this.mPendingHubEndpointInfo.getVersion();
    }

    public String getTag() {
        return this.mPendingHubEndpointInfo.getTag();
    }

    public Collection<HubServiceInfo> getServiceInfoCollection() {
        return this.mPendingHubEndpointInfo.getServiceInfoCollection();
    }

    public HubEndpointLifecycleCallback getLifecycleCallback() {
        return this.mLifecycleCallback;
    }

    public HubEndpointMessageCallback getMessageCallback() {
        return this.mMessageCallback;
    }

    public static final class Builder {
        private HubEndpointLifecycleCallback mLifecycleCallback;
        private Executor mLifecycleCallbackExecutor;
        private final Executor mMainExecutor;
        private HubEndpointMessageCallback mMessageCallback;
        private Executor mMessageCallbackExecutor;
        private final String mPackageName;
        private List<HubServiceInfo> mServiceInfos = Collections.EMPTY_LIST;
        private String mTag;
        private int mVersion;

        public Builder(Context context) {
            this.mPackageName = context.getPackageName();
            this.mTag = context.getAttributionTag();
            this.mVersion = (int) context.getApplicationInfo().longVersionCode;
            this.mMainExecutor = context.getMainExecutor();
        }

        public Builder setVersion(int i) {
            this.mVersion = i;
            return this;
        }

        public Builder setTag(String str) {
            this.mTag = str;
            return this;
        }

        public Builder setLifecycleCallback(HubEndpointLifecycleCallback hubEndpointLifecycleCallback) {
            this.mLifecycleCallbackExecutor = null;
            this.mLifecycleCallback = hubEndpointLifecycleCallback;
            return this;
        }

        public Builder setLifecycleCallback(Executor executor, HubEndpointLifecycleCallback hubEndpointLifecycleCallback) {
            this.mLifecycleCallbackExecutor = executor;
            this.mLifecycleCallback = hubEndpointLifecycleCallback;
            return this;
        }

        public Builder setMessageCallback(HubEndpointMessageCallback hubEndpointMessageCallback) {
            this.mMessageCallbackExecutor = null;
            this.mMessageCallback = hubEndpointMessageCallback;
            return this;
        }

        public Builder setMessageCallback(Executor executor, HubEndpointMessageCallback hubEndpointMessageCallback) {
            this.mMessageCallbackExecutor = executor;
            this.mMessageCallback = hubEndpointMessageCallback;
            return this;
        }

        public Builder setServiceInfoCollection(Collection<HubServiceInfo> collection) {
            this.mServiceInfos = new ArrayList(collection);
            return this;
        }

        public HubEndpoint build() {
            HubEndpointInfo hubEndpointInfo = new HubEndpointInfo(this.mPackageName, this.mVersion, this.mTag, this.mServiceInfos);
            HubEndpointLifecycleCallback hubEndpointLifecycleCallback = this.mLifecycleCallback;
            Executor executor = this.mLifecycleCallbackExecutor;
            if (executor == null) {
                executor = this.mMainExecutor;
            }
            HubEndpointMessageCallback hubEndpointMessageCallback = this.mMessageCallback;
            Executor executor2 = this.mMessageCallbackExecutor;
            if (executor2 == null) {
                executor2 = this.mMainExecutor;
            }
            return new HubEndpoint(hubEndpointInfo, hubEndpointLifecycleCallback, executor, hubEndpointMessageCallback, executor2);
        }
    }
}
