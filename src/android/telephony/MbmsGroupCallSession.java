package android.telephony;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.mbms.GroupCall;
import android.telephony.mbms.GroupCallCallback;
import android.telephony.mbms.InternalGroupCallCallback;
import android.telephony.mbms.InternalGroupCallSessionCallback;
import android.telephony.mbms.MbmsGroupCallSessionCallback;
import android.telephony.mbms.MbmsUtils;
import android.telephony.mbms.vendor.IMbmsGroupCallService;
import android.util.ArraySet;
import android.util.Log;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public class MbmsGroupCallSession implements AutoCloseable {
    private static final String LOG_TAG = "MbmsGroupCallSession";

    @SystemApi
    public static final String MBMS_GROUP_CALL_SERVICE_ACTION = "android.telephony.action.EmbmsGroupCall";
    public static final String MBMS_GROUP_CALL_SERVICE_OVERRIDE_METADATA = "mbms-group-call-service-override";
    private static AtomicBoolean sIsInitialized = new AtomicBoolean(false);
    private final Context mContext;
    private InternalGroupCallSessionCallback mInternalCallback;
    private ServiceConnection mServiceConnection;
    private int mSubscriptionId;
    private AtomicReference<IMbmsGroupCallService> mService = new AtomicReference<>(null);
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: android.telephony.MbmsGroupCallSession.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            MbmsGroupCallSession.sIsInitialized.set(false);
            MbmsGroupCallSession.this.mInternalCallback.onError(3, "Received death notification");
        }
    };
    private Set<GroupCall> mKnownActiveGroupCalls = new ArraySet();

    private MbmsGroupCallSession(Context context, Executor executor, int i, MbmsGroupCallSessionCallback mbmsGroupCallSessionCallback) {
        this.mContext = context;
        this.mSubscriptionId = i;
        this.mInternalCallback = new InternalGroupCallSessionCallback(mbmsGroupCallSessionCallback, executor);
    }

    public static MbmsGroupCallSession create(Context context, int i, Executor executor, final MbmsGroupCallSessionCallback mbmsGroupCallSessionCallback) {
        if (!sIsInitialized.compareAndSet(false, true)) {
            throw new IllegalStateException("Cannot create two instances of MbmsGroupCallSession");
        }
        MbmsGroupCallSession mbmsGroupCallSession = new MbmsGroupCallSession(context, executor, i, mbmsGroupCallSessionCallback);
        final int iBindAndInitialize = mbmsGroupCallSession.bindAndInitialize();
        if (iBindAndInitialize == 0) {
            return mbmsGroupCallSession;
        }
        sIsInitialized.set(false);
        executor.execute(new Runnable() { // from class: android.telephony.MbmsGroupCallSession.2
            @Override // java.lang.Runnable
            public void run() {
                mbmsGroupCallSessionCallback.onError(iBindAndInitialize, null);
            }
        });
        return null;
    }

    public static MbmsGroupCallSession create(Context context, Executor executor, MbmsGroupCallSessionCallback mbmsGroupCallSessionCallback) {
        return create(context, SubscriptionManager.getDefaultSubscriptionId(), executor, mbmsGroupCallSessionCallback);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        try {
            IMbmsGroupCallService iMbmsGroupCallService = this.mService.get();
            if (iMbmsGroupCallService != null && this.mServiceConnection != null) {
                iMbmsGroupCallService.dispose(this.mSubscriptionId);
                Iterator<GroupCall> it = this.mKnownActiveGroupCalls.iterator();
                while (it.hasNext()) {
                    it.next().getCallback().stop();
                }
                this.mKnownActiveGroupCalls.clear();
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

    public GroupCall startGroupCall(long j, List<Integer> list, List<Integer> list2, Executor executor, GroupCallCallback groupCallCallback) {
        IMbmsGroupCallService iMbmsGroupCallService = this.mService.get();
        if (iMbmsGroupCallService == null) {
            throw new IllegalStateException("Middleware not yet bound");
        }
        InternalGroupCallCallback internalGroupCallCallback = new InternalGroupCallCallback(groupCallCallback, executor);
        GroupCall groupCall = new GroupCall(this.mSubscriptionId, iMbmsGroupCallService, this, j, internalGroupCallCallback);
        this.mKnownActiveGroupCalls.add(groupCall);
        try {
            int iStartGroupCall = iMbmsGroupCallService.startGroupCall(this.mSubscriptionId, j, list, list2, internalGroupCallCallback);
            if (iStartGroupCall == -1) {
                close();
                throw new IllegalStateException("Middleware must not return an unknown error code");
            }
            if (iStartGroupCall == 0) {
                return groupCall;
            }
            this.mInternalCallback.onError(iStartGroupCall, null);
            return null;
        } catch (RemoteException unused) {
            Log.w(LOG_TAG, "Remote process died");
            this.mService.set(null);
            sIsInitialized.set(false);
            this.mInternalCallback.onError(3, null);
            return null;
        }
    }

    public void onGroupCallStopped(GroupCall groupCall) {
        this.mKnownActiveGroupCalls.remove(groupCall);
    }

    private int bindAndInitialize() {
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: android.telephony.MbmsGroupCallSession.3
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                IMbmsGroupCallService iMbmsGroupCallServiceAsInterface = IMbmsGroupCallService.Stub.asInterface(iBinder);
                try {
                    int iInitialize = iMbmsGroupCallServiceAsInterface.initialize(MbmsGroupCallSession.this.mInternalCallback, MbmsGroupCallSession.this.mSubscriptionId);
                    if (iInitialize == -1) {
                        MbmsGroupCallSession.this.close();
                        throw new IllegalStateException("Middleware must not return an unknown error code");
                    }
                    if (iInitialize != 0) {
                        MbmsGroupCallSession.this.mInternalCallback.onError(iInitialize, "Error returned during initialization");
                        MbmsGroupCallSession.sIsInitialized.set(false);
                        return;
                    }
                    try {
                        iMbmsGroupCallServiceAsInterface.asBinder().linkToDeath(MbmsGroupCallSession.this.mDeathRecipient, 0);
                        MbmsGroupCallSession.this.mService.set(iMbmsGroupCallServiceAsInterface);
                    } catch (RemoteException unused) {
                        MbmsGroupCallSession.this.mInternalCallback.onError(3, "Middleware lost during initialization");
                        MbmsGroupCallSession.sIsInitialized.set(false);
                    }
                } catch (RemoteException e) {
                    Log.e(MbmsGroupCallSession.LOG_TAG, "Service died before initialization");
                    MbmsGroupCallSession.this.mInternalCallback.onError(103, e.toString());
                    MbmsGroupCallSession.sIsInitialized.set(false);
                } catch (RuntimeException e2) {
                    Log.e(MbmsGroupCallSession.LOG_TAG, "Runtime exception during initialization");
                    MbmsGroupCallSession.this.mInternalCallback.onError(103, e2.toString());
                    MbmsGroupCallSession.sIsInitialized.set(false);
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                MbmsGroupCallSession.sIsInitialized.set(false);
                MbmsGroupCallSession.this.mService.set(null);
            }

            @Override // android.content.ServiceConnection
            public void onNullBinding(ComponentName componentName) {
                Log.w(MbmsGroupCallSession.LOG_TAG, "bindAndInitialize: Remote service returned null");
                MbmsGroupCallSession.this.mInternalCallback.onError(3, "Middleware service binding returned null");
                MbmsGroupCallSession.sIsInitialized.set(false);
                MbmsGroupCallSession.this.mService.set(null);
                MbmsGroupCallSession.this.mContext.unbindService(this);
            }
        };
        this.mServiceConnection = serviceConnection;
        return MbmsUtils.startBinding(this.mContext, MBMS_GROUP_CALL_SERVICE_ACTION, serviceConnection);
    }
}
