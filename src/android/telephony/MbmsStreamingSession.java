package android.telephony;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.mbms.InternalStreamingServiceCallback;
import android.telephony.mbms.InternalStreamingSessionCallback;
import android.telephony.mbms.MbmsStreamingSessionCallback;
import android.telephony.mbms.MbmsUtils;
import android.telephony.mbms.StreamingService;
import android.telephony.mbms.StreamingServiceCallback;
import android.telephony.mbms.StreamingServiceInfo;
import android.telephony.mbms.vendor.IMbmsStreamingService;
import android.util.ArraySet;
import android.util.Log;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public class MbmsStreamingSession implements AutoCloseable {
    private static final String LOG_TAG = "MbmsStreamingSession";

    @SystemApi
    public static final String MBMS_STREAMING_SERVICE_ACTION = "android.telephony.action.EmbmsStreaming";
    public static final String MBMS_STREAMING_SERVICE_OVERRIDE_METADATA = "mbms-streaming-service-override";
    private static AtomicBoolean sIsInitialized = new AtomicBoolean(false);
    private final Context mContext;
    private InternalStreamingSessionCallback mInternalCallback;
    private ServiceConnection mServiceConnection;
    private int mSubscriptionId;
    private AtomicReference<IMbmsStreamingService> mService = new AtomicReference<>(null);
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: android.telephony.MbmsStreamingSession.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            MbmsStreamingSession.sIsInitialized.set(false);
            MbmsStreamingSession.this.sendErrorToApp(3, "Received death notification");
        }
    };
    private Set<StreamingService> mKnownActiveStreamingServices = new ArraySet();

    private MbmsStreamingSession(Context context, Executor executor, int i, MbmsStreamingSessionCallback mbmsStreamingSessionCallback) {
        this.mContext = context;
        this.mSubscriptionId = i;
        this.mInternalCallback = new InternalStreamingSessionCallback(mbmsStreamingSessionCallback, executor);
    }

    public static MbmsStreamingSession create(Context context, Executor executor, int i, final MbmsStreamingSessionCallback mbmsStreamingSessionCallback) {
        if (!sIsInitialized.compareAndSet(false, true)) {
            throw new IllegalStateException("Cannot create two instances of MbmsStreamingSession");
        }
        MbmsStreamingSession mbmsStreamingSession = new MbmsStreamingSession(context, executor, i, mbmsStreamingSessionCallback);
        final int iBindAndInitialize = mbmsStreamingSession.bindAndInitialize();
        if (iBindAndInitialize == 0) {
            return mbmsStreamingSession;
        }
        sIsInitialized.set(false);
        executor.execute(new Runnable() { // from class: android.telephony.MbmsStreamingSession.2
            @Override // java.lang.Runnable
            public void run() {
                mbmsStreamingSessionCallback.onError(iBindAndInitialize, null);
            }
        });
        return null;
    }

    public static MbmsStreamingSession create(Context context, Executor executor, MbmsStreamingSessionCallback mbmsStreamingSessionCallback) {
        return create(context, executor, SubscriptionManager.getDefaultSubscriptionId(), mbmsStreamingSessionCallback);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        try {
            IMbmsStreamingService iMbmsStreamingService = this.mService.get();
            if (iMbmsStreamingService != null && this.mServiceConnection != null) {
                iMbmsStreamingService.dispose(this.mSubscriptionId);
                Iterator<StreamingService> it = this.mKnownActiveStreamingServices.iterator();
                while (it.hasNext()) {
                    it.next().getCallback().stop();
                }
                this.mKnownActiveStreamingServices.clear();
                this.mContext.unbindService(this.mServiceConnection);
            }
        } catch (RemoteException unused) {
        } finally {
            this.mService.set(null);
            sIsInitialized.set(false);
            this.mServiceConnection = null;
            this.mInternalCallback.stop();
        }
    }

    public void requestUpdateStreamingServices(List<String> list) {
        IMbmsStreamingService iMbmsStreamingService = this.mService.get();
        if (iMbmsStreamingService == null) {
            throw new IllegalStateException("Middleware not yet bound");
        }
        try {
            int iRequestUpdateStreamingServices = iMbmsStreamingService.requestUpdateStreamingServices(this.mSubscriptionId, list);
            if (iRequestUpdateStreamingServices == -1) {
                close();
                throw new IllegalStateException("Middleware must not return an unknown error code");
            }
            if (iRequestUpdateStreamingServices != 0) {
                sendErrorToApp(iRequestUpdateStreamingServices, null);
            }
        } catch (RemoteException unused) {
            Log.w(LOG_TAG, "Remote process died");
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    public StreamingService startStreaming(StreamingServiceInfo streamingServiceInfo, Executor executor, StreamingServiceCallback streamingServiceCallback) {
        IMbmsStreamingService iMbmsStreamingService = this.mService.get();
        if (iMbmsStreamingService == null) {
            throw new IllegalStateException("Middleware not yet bound");
        }
        InternalStreamingServiceCallback internalStreamingServiceCallback = new InternalStreamingServiceCallback(streamingServiceCallback, executor);
        StreamingService streamingService = new StreamingService(this.mSubscriptionId, iMbmsStreamingService, this, streamingServiceInfo, internalStreamingServiceCallback);
        this.mKnownActiveStreamingServices.add(streamingService);
        try {
            int iStartStreaming = iMbmsStreamingService.startStreaming(this.mSubscriptionId, streamingServiceInfo.getServiceId(), internalStreamingServiceCallback);
            if (iStartStreaming == -1) {
                close();
                throw new IllegalStateException("Middleware must not return an unknown error code");
            }
            if (iStartStreaming == 0) {
                return streamingService;
            }
            sendErrorToApp(iStartStreaming, null);
            return null;
        } catch (RemoteException unused) {
            Log.w(LOG_TAG, "Remote process died");
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
            return null;
        }
    }

    public void onStreamingServiceStopped(StreamingService streamingService) {
        this.mKnownActiveStreamingServices.remove(streamingService);
    }

    private int bindAndInitialize() {
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: android.telephony.MbmsStreamingSession.3
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                IMbmsStreamingService iMbmsStreamingServiceAsInterface = IMbmsStreamingService.Stub.asInterface(iBinder);
                try {
                    int iInitialize = iMbmsStreamingServiceAsInterface.initialize(MbmsStreamingSession.this.mInternalCallback, MbmsStreamingSession.this.mSubscriptionId);
                    if (iInitialize == -1) {
                        MbmsStreamingSession.this.close();
                        throw new IllegalStateException("Middleware must not return an unknown error code");
                    }
                    if (iInitialize != 0) {
                        MbmsStreamingSession.this.sendErrorToApp(iInitialize, "Error returned during initialization");
                        MbmsStreamingSession.sIsInitialized.set(false);
                        return;
                    }
                    try {
                        iMbmsStreamingServiceAsInterface.asBinder().linkToDeath(MbmsStreamingSession.this.mDeathRecipient, 0);
                        MbmsStreamingSession.this.mService.set(iMbmsStreamingServiceAsInterface);
                    } catch (RemoteException unused) {
                        MbmsStreamingSession.this.sendErrorToApp(3, "Middleware lost during initialization");
                        MbmsStreamingSession.sIsInitialized.set(false);
                    }
                } catch (RemoteException e) {
                    Log.e(MbmsStreamingSession.LOG_TAG, "Service died before initialization");
                    MbmsStreamingSession.this.sendErrorToApp(103, e.toString());
                    MbmsStreamingSession.sIsInitialized.set(false);
                } catch (RuntimeException e2) {
                    Log.e(MbmsStreamingSession.LOG_TAG, "Runtime exception during initialization");
                    MbmsStreamingSession.this.sendErrorToApp(103, e2.toString());
                    MbmsStreamingSession.sIsInitialized.set(false);
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                MbmsStreamingSession.sIsInitialized.set(false);
                MbmsStreamingSession.this.mService.set(null);
            }

            @Override // android.content.ServiceConnection
            public void onNullBinding(ComponentName componentName) {
                Log.w(MbmsStreamingSession.LOG_TAG, "bindAndInitialize: Remote service returned null");
                MbmsStreamingSession.this.sendErrorToApp(3, "Middleware service binding returned null");
                MbmsStreamingSession.sIsInitialized.set(false);
                MbmsStreamingSession.this.mService.set(null);
                MbmsStreamingSession.this.mContext.unbindService(this);
            }
        };
        this.mServiceConnection = serviceConnection;
        return MbmsUtils.startBinding(this.mContext, MBMS_STREAMING_SERVICE_ACTION, serviceConnection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendErrorToApp(int i, String str) {
        try {
            this.mInternalCallback.onError(i, str);
        } catch (RemoteException unused) {
        }
    }
}
