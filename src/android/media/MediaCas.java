package android.media;

import android.annotation.SystemApi;
import android.content.Context;
import android.hardware.cas.AidlCasPluginDescriptor;
import android.hardware.cas.ICas;
import android.hardware.cas.ICasListener;
import android.hardware.cas.IMediaCasService;
import android.hardware.cas.V1_0.HidlCasPluginDescriptor;
import android.hardware.cas.V1_0.ICas;
import android.hardware.cas.V1_2.ICas;
import android.hardware.cas.V1_2.ICasListener;
import android.media.MediaCasException;
import android.media.tv.tunerresourcemanager.CasSessionRequest;
import android.media.tv.tunerresourcemanager.ResourceClientProfile;
import android.media.tv.tunerresourcemanager.TunerResourceManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class MediaCas implements AutoCloseable {
    private static final long MEDIA_CAS_HIDL_COOKIE = 394;
    public static final int PLUGIN_STATUS_PHYSICAL_MODULE_CHANGED = 0;
    public static final int PLUGIN_STATUS_SESSION_NUMBER_CHANGED = 1;
    public static final int SCRAMBLING_MODE_AES128 = 9;
    public static final int SCRAMBLING_MODE_AES_CBC = 14;
    public static final int SCRAMBLING_MODE_AES_ECB = 10;
    public static final int SCRAMBLING_MODE_AES_SCTE52 = 11;
    public static final int SCRAMBLING_MODE_DVB_CISSA_V1 = 6;
    public static final int SCRAMBLING_MODE_DVB_CSA1 = 1;
    public static final int SCRAMBLING_MODE_DVB_CSA2 = 2;
    public static final int SCRAMBLING_MODE_DVB_CSA3_ENHANCE = 5;
    public static final int SCRAMBLING_MODE_DVB_CSA3_MINIMAL = 4;
    public static final int SCRAMBLING_MODE_DVB_CSA3_STANDARD = 3;
    public static final int SCRAMBLING_MODE_DVB_IDSA = 7;
    public static final int SCRAMBLING_MODE_MULTI2 = 8;
    public static final int SCRAMBLING_MODE_RESERVED = 0;
    public static final int SCRAMBLING_MODE_TDES_ECB = 12;
    public static final int SCRAMBLING_MODE_TDES_SCTE52 = 13;
    public static final int SESSION_USAGE_LIVE = 0;
    public static final int SESSION_USAGE_PLAYBACK = 1;
    public static final int SESSION_USAGE_RECORD = 2;
    public static final int SESSION_USAGE_TIMESHIFT = 3;
    private static final String TAG = "MediaCas";
    private static IMediaCasService sService;
    private int mCasSystemId;
    private int mClientId;
    private EventHandler mEventHandler;
    private HandlerThread mHandlerThread;
    private EventListener mListener;
    private int mPriorityHint;
    private String mTvInputServiceSessionId;
    private int mUserId;
    private static Object sAidlLock = new Object();
    private static IBinder.DeathRecipient sDeathListener = new IBinder.DeathRecipient() { // from class: android.media.MediaCas.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (MediaCas.sAidlLock) {
                Log.d(MediaCas.TAG, "The service is dead");
                MediaCas.sService.asBinder().unlinkToDeath(MediaCas.sDeathListener, 0);
                MediaCas.sService = null;
            }
        }
    };
    private static android.hardware.cas.V1_0.IMediaCasService sServiceHidl = null;
    private static Object sHidlLock = new Object();
    private static IHwBinder.DeathRecipient sDeathListenerHidl = new IHwBinder.DeathRecipient() { // from class: android.media.MediaCas.2
        @Override // android.os.IHwBinder.DeathRecipient
        public void serviceDied(long j) {
            if (j == MediaCas.MEDIA_CAS_HIDL_COOKIE) {
                synchronized (MediaCas.sHidlLock) {
                    MediaCas.sServiceHidl = null;
                }
            }
        }
    };
    private ICas mICas = null;
    private android.hardware.cas.V1_0.ICas mICasHidl = null;
    private android.hardware.cas.V1_1.ICas mICasHidl11 = null;
    private android.hardware.cas.V1_2.ICas mICasHidl12 = null;
    private TunerResourceManager mTunerResourceManager = null;
    private final Map<Session, Long> mSessionMap = new HashMap();
    private final ICasListener.Stub mBinder = new ICasListener.Stub() { // from class: android.media.MediaCas.3
        @Override // android.hardware.cas.ICasListener
        public int getInterfaceVersion() throws RemoteException {
            return 1;
        }

        @Override // android.hardware.cas.ICasListener
        public void onEvent(int i, int i2, byte[] bArr) throws RemoteException {
            if (MediaCas.this.mEventHandler != null) {
                MediaCas.this.mEventHandler.sendMessage(MediaCas.this.mEventHandler.obtainMessage(0, i, i2, bArr));
            }
        }

        @Override // android.hardware.cas.ICasListener
        public void onSessionEvent(byte[] bArr, int i, int i2, byte[] bArr2) throws RemoteException {
            if (MediaCas.this.mEventHandler != null) {
                Message obtainMessage = MediaCas.this.mEventHandler.obtainMessage();
                obtainMessage.what = 1;
                obtainMessage.arg1 = i;
                obtainMessage.arg2 = i2;
                Bundle bundle = new Bundle();
                bundle.putByteArray("sessionId", bArr);
                bundle.putByteArray("data", bArr2);
                obtainMessage.setData(bundle);
                MediaCas.this.mEventHandler.sendMessage(obtainMessage);
            }
        }

        @Override // android.hardware.cas.ICasListener
        public void onStatusUpdate(byte b, int i) throws RemoteException {
            if (MediaCas.this.mEventHandler != null) {
                MediaCas.this.mEventHandler.sendMessage(MediaCas.this.mEventHandler.obtainMessage(2, b, i));
            }
        }

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        @Override // android.hardware.cas.ICasListener
        public synchronized String getInterfaceHash() throws RemoteException {
            return "bc51d8d70a55ec4723d3f73d0acf7003306bf69f";
        }
    };
    private final ICasListener.Stub mBinderHidl = new ICasListener.Stub() { // from class: android.media.MediaCas.4
        @Override // android.hardware.cas.V1_0.ICasListener
        public void onEvent(int i, int i2, ArrayList<Byte> arrayList) throws RemoteException {
            if (MediaCas.this.mEventHandler != null) {
                MediaCas.this.mEventHandler.sendMessage(MediaCas.this.mEventHandler.obtainMessage(0, i, i2, MediaCas.this.toBytes(arrayList)));
            }
        }

        @Override // android.hardware.cas.V1_1.ICasListener
        public void onSessionEvent(ArrayList<Byte> arrayList, int i, int i2, ArrayList<Byte> arrayList2) throws RemoteException {
            if (MediaCas.this.mEventHandler != null) {
                Message obtainMessage = MediaCas.this.mEventHandler.obtainMessage();
                obtainMessage.what = 1;
                obtainMessage.arg1 = i;
                obtainMessage.arg2 = i2;
                Bundle bundle = new Bundle();
                bundle.putByteArray("sessionId", MediaCas.this.toBytes(arrayList));
                bundle.putByteArray("data", MediaCas.this.toBytes(arrayList2));
                obtainMessage.setData(bundle);
                MediaCas.this.mEventHandler.sendMessage(obtainMessage);
            }
        }

        @Override // android.hardware.cas.V1_2.ICasListener
        public void onStatusUpdate(byte b, int i) throws RemoteException {
            if (MediaCas.this.mEventHandler != null) {
                MediaCas.this.mEventHandler.sendMessage(MediaCas.this.mEventHandler.obtainMessage(2, b, i));
            }
        }
    };
    private final TunerResourceManager.ResourcesReclaimListener mResourceListener = new TunerResourceManager.ResourcesReclaimListener() { // from class: android.media.MediaCas.5
        @Override // android.media.tv.tunerresourcemanager.TunerResourceManager.ResourcesReclaimListener
        public void onReclaimResources() {
            synchronized (MediaCas.this.mSessionMap) {
                Iterator it = new ArrayList(MediaCas.this.mSessionMap.keySet()).iterator();
                while (it.hasNext()) {
                    ((Session) it.next()).close();
                }
            }
            if (MediaCas.this.mEventHandler != null) {
                MediaCas.this.mEventHandler.sendMessage(MediaCas.this.mEventHandler.obtainMessage(3));
            }
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface PluginStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScramblingMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionUsage {
    }

    static IMediaCasService getService() {
        IMediaCasService iMediaCasService;
        synchronized (sAidlLock) {
            IMediaCasService iMediaCasService2 = sService;
            if (iMediaCasService2 == null || !iMediaCasService2.asBinder().isBinderAlive()) {
                try {
                    Log.d(TAG, "Trying to get AIDL service");
                    IMediaCasService asInterface = IMediaCasService.Stub.asInterface(ServiceManager.waitForDeclaredService(IMediaCasService.DESCRIPTOR + "/default"));
                    sService = asInterface;
                    if (asInterface != null) {
                        asInterface.asBinder().linkToDeath(sDeathListener, 0);
                    }
                } catch (Exception unused) {
                    Log.d(TAG, "Failed to get cas AIDL service");
                }
            }
            iMediaCasService = sService;
        }
        return iMediaCasService;
    }

    static android.hardware.cas.V1_0.IMediaCasService getServiceHidl() {
        synchronized (sHidlLock) {
            android.hardware.cas.V1_0.IMediaCasService iMediaCasService = sServiceHidl;
            if (iMediaCasService != null) {
                return iMediaCasService;
            }
            try {
                Log.d(TAG, "Trying to get cas@1.2 service");
                android.hardware.cas.V1_2.IMediaCasService service = android.hardware.cas.V1_2.IMediaCasService.getService(true);
                if (service != null) {
                    sServiceHidl = service;
                    service.linkToDeath(sDeathListenerHidl, MEDIA_CAS_HIDL_COOKIE);
                    return sServiceHidl;
                }
            } catch (Exception unused) {
                Log.d(TAG, "Failed to get cas@1.2 service");
            }
            try {
                Log.d(TAG, "Trying to get cas@1.1 service");
                android.hardware.cas.V1_1.IMediaCasService service2 = android.hardware.cas.V1_1.IMediaCasService.getService(true);
                if (service2 != null) {
                    sServiceHidl = service2;
                    service2.linkToDeath(sDeathListenerHidl, MEDIA_CAS_HIDL_COOKIE);
                    return sServiceHidl;
                }
            } catch (Exception unused2) {
                Log.d(TAG, "Failed to get cas@1.1 service");
            }
            try {
                Log.d(TAG, "Trying to get cas@1.0 service");
                android.hardware.cas.V1_0.IMediaCasService service3 = android.hardware.cas.V1_0.IMediaCasService.getService(true);
                sServiceHidl = service3;
                if (service3 != null) {
                    service3.linkToDeath(sDeathListenerHidl, MEDIA_CAS_HIDL_COOKIE);
                }
                return sServiceHidl;
            } catch (Exception unused3) {
                Log.d(TAG, "Failed to get cas@1.0 service");
                return null;
            }
        }
    }

    private void validateInternalStates() {
        if (this.mICas == null && this.mICasHidl == null) {
            throw new IllegalStateException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanupAndRethrowIllegalState() {
        this.mICas = null;
        this.mICasHidl = null;
        this.mICasHidl11 = null;
        this.mICasHidl12 = null;
        throw new IllegalStateException();
    }

    private class EventHandler extends Handler {
        private static final String DATA_KEY = "data";
        private static final int MSG_CAS_EVENT = 0;
        private static final int MSG_CAS_RESOURCE_LOST = 3;
        private static final int MSG_CAS_SESSION_EVENT = 1;
        private static final int MSG_CAS_STATUS_EVENT = 2;
        private static final String SESSION_KEY = "sessionId";

        public EventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 0) {
                MediaCas.this.mListener.onEvent(MediaCas.this, message.arg1, message.arg2, message.obj == null ? new byte[0] : (byte[]) message.obj);
                return;
            }
            if (message.what == 1) {
                Bundle data = message.getData();
                byte[] byteArray = data.getByteArray("sessionId");
                byte[] byteArray2 = data.getByteArray("data");
                EventListener eventListener = MediaCas.this.mListener;
                MediaCas mediaCas = MediaCas.this;
                eventListener.onSessionEvent(mediaCas, mediaCas.createFromSessionId(byteArray), message.arg1, message.arg2, byteArray2);
                return;
            }
            if (message.what == 2) {
                if (message.arg1 == 1 && MediaCas.this.mTunerResourceManager != null) {
                    MediaCas.this.mTunerResourceManager.updateCasInfo(MediaCas.this.mCasSystemId, message.arg2);
                }
                MediaCas.this.mListener.onPluginStatusUpdate(MediaCas.this, message.arg1, message.arg2);
                return;
            }
            if (message.what == 3) {
                MediaCas.this.mListener.onResourceLost(MediaCas.this);
            }
        }
    }

    public static class PluginDescriptor {
        private final int mCASystemId;
        private final String mName;

        private PluginDescriptor() {
            this.mCASystemId = 65535;
            this.mName = null;
        }

        PluginDescriptor(AidlCasPluginDescriptor aidlCasPluginDescriptor) {
            this.mCASystemId = aidlCasPluginDescriptor.caSystemId;
            this.mName = aidlCasPluginDescriptor.name;
        }

        PluginDescriptor(HidlCasPluginDescriptor hidlCasPluginDescriptor) {
            this.mCASystemId = hidlCasPluginDescriptor.caSystemId;
            this.mName = hidlCasPluginDescriptor.name;
        }

        public int getSystemId() {
            return this.mCASystemId;
        }

        public String getName() {
            return this.mName;
        }

        public String toString() {
            return "PluginDescriptor {" + this.mCASystemId + ", " + this.mName + "}";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList<Byte> toByteArray(byte[] bArr, int i, int i2) {
        ArrayList<Byte> arrayList = new ArrayList<>(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList.add(Byte.valueOf(bArr[i + i3]));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList<Byte> toByteArray(byte[] bArr) {
        if (bArr == null) {
            return new ArrayList<>();
        }
        return toByteArray(bArr, 0, bArr.length);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] toBytes(ArrayList<Byte> arrayList) {
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        byte[] bArr = new byte[size];
        for (int i = 0; i < size; i++) {
            bArr[i] = arrayList.get(i).byteValue();
        }
        return bArr;
    }

    public final class Session implements AutoCloseable {
        boolean mIsClosed = false;
        final byte[] mSessionId;

        Session(byte[] bArr) {
            this.mSessionId = bArr;
        }

        private void validateSessionInternalStates() {
            if (MediaCas.this.mICas == null && MediaCas.this.mICasHidl == null) {
                throw new IllegalStateException();
            }
            if (this.mIsClosed) {
                MediaCasStateException.throwExceptionIfNeeded(3);
            }
        }

        public boolean equals(Object obj) {
            if (obj instanceof Session) {
                return Arrays.equals(this.mSessionId, ((Session) obj).mSessionId);
            }
            return false;
        }

        public void setPrivateData(byte[] bArr) throws MediaCasException {
            validateSessionInternalStates();
            try {
                if (MediaCas.this.mICas != null) {
                    try {
                        MediaCas.this.mICas.setSessionPrivateData(this.mSessionId, bArr);
                    } catch (ServiceSpecificException e) {
                        MediaCasException.throwExceptionIfNeeded(e.errorCode);
                    }
                } else {
                    MediaCasException.throwExceptionIfNeeded(MediaCas.this.mICasHidl.setSessionPrivateData(MediaCas.this.toByteArray(this.mSessionId), MediaCas.this.toByteArray(bArr, 0, bArr.length)));
                }
            } catch (RemoteException unused) {
                MediaCas.this.cleanupAndRethrowIllegalState();
            }
        }

        public void processEcm(byte[] bArr, int i, int i2) throws MediaCasException {
            validateSessionInternalStates();
            try {
                if (MediaCas.this.mICas != null) {
                    try {
                        MediaCas.this.mICas.processEcm(this.mSessionId, Arrays.copyOfRange(bArr, i, i2 + i));
                    } catch (ServiceSpecificException e) {
                        MediaCasException.throwExceptionIfNeeded(e.errorCode);
                    }
                } else {
                    MediaCasException.throwExceptionIfNeeded(MediaCas.this.mICasHidl.processEcm(MediaCas.this.toByteArray(this.mSessionId), MediaCas.this.toByteArray(bArr, i, i2)));
                }
            } catch (RemoteException unused) {
                MediaCas.this.cleanupAndRethrowIllegalState();
            }
        }

        public void processEcm(byte[] bArr) throws MediaCasException {
            processEcm(bArr, 0, bArr.length);
        }

        public void sendSessionEvent(int i, int i2, byte[] bArr) throws MediaCasException {
            validateSessionInternalStates();
            if (MediaCas.this.mICas != null) {
                if (bArr == null) {
                    try {
                        bArr = new byte[0];
                    } catch (RemoteException unused) {
                        MediaCas.this.cleanupAndRethrowIllegalState();
                        return;
                    }
                }
                MediaCas.this.mICas.sendSessionEvent(this.mSessionId, i, i2, bArr);
                return;
            }
            if (MediaCas.this.mICasHidl11 == null) {
                Log.d(MediaCas.TAG, "Send Session Event isn't supported by cas@1.0 interface");
                throw new MediaCasException.UnsupportedCasException("Send Session Event is not supported");
            }
            try {
                MediaCasException.throwExceptionIfNeeded(MediaCas.this.mICasHidl11.sendSessionEvent(MediaCas.this.toByteArray(this.mSessionId), i, i2, MediaCas.this.toByteArray(bArr)));
            } catch (RemoteException unused2) {
                MediaCas.this.cleanupAndRethrowIllegalState();
            }
        }

        public byte[] getSessionId() {
            validateSessionInternalStates();
            return this.mSessionId;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            validateSessionInternalStates();
            try {
                if (MediaCas.this.mICas != null) {
                    MediaCas.this.mICas.closeSession(this.mSessionId);
                } else {
                    MediaCasStateException.throwExceptionIfNeeded(MediaCas.this.mICasHidl.closeSession(MediaCas.this.toByteArray(this.mSessionId)));
                }
                this.mIsClosed = true;
                MediaCas.this.removeSessionFromResourceMap(this);
            } catch (RemoteException unused) {
                MediaCas.this.cleanupAndRethrowIllegalState();
            }
        }
    }

    Session createFromSessionId(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        return new Session(bArr);
    }

    public static boolean isSystemIdSupported(int i) {
        IMediaCasService service = getService();
        if (service != null) {
            try {
                return service.isSystemIdSupported(i);
            } catch (RemoteException unused) {
                return false;
            }
        }
        android.hardware.cas.V1_0.IMediaCasService serviceHidl = getServiceHidl();
        if (serviceHidl != null) {
            try {
                return serviceHidl.isSystemIdSupported(i);
            } catch (RemoteException unused2) {
            }
        }
        return false;
    }

    public static PluginDescriptor[] enumeratePlugins() {
        IMediaCasService service = getService();
        if (service != null) {
            try {
                AidlCasPluginDescriptor[] enumeratePlugins = service.enumeratePlugins();
                if (enumeratePlugins.length == 0) {
                    return null;
                }
                int length = enumeratePlugins.length;
                PluginDescriptor[] pluginDescriptorArr = new PluginDescriptor[length];
                for (int i = 0; i < length; i++) {
                    pluginDescriptorArr[i] = new PluginDescriptor(enumeratePlugins[i]);
                }
                return pluginDescriptorArr;
            } catch (RemoteException unused) {
                Log.e(TAG, "Some exception while enumerating plugins");
            }
        }
        android.hardware.cas.V1_0.IMediaCasService serviceHidl = getServiceHidl();
        if (serviceHidl != null) {
            try {
                ArrayList<HidlCasPluginDescriptor> enumeratePlugins2 = serviceHidl.enumeratePlugins();
                if (enumeratePlugins2.size() == 0) {
                    return null;
                }
                int size = enumeratePlugins2.size();
                PluginDescriptor[] pluginDescriptorArr2 = new PluginDescriptor[size];
                for (int i2 = 0; i2 < size; i2++) {
                    pluginDescriptorArr2[i2] = new PluginDescriptor(enumeratePlugins2.get(i2));
                }
                return pluginDescriptorArr2;
            } catch (RemoteException unused2) {
            }
        }
        return null;
    }

    private void createPlugin(int i) throws MediaCasException.UnsupportedCasException {
        try {
            try {
                this.mCasSystemId = i;
                this.mUserId = Process.myUid();
                IMediaCasService service = getService();
                if (service != null) {
                    Log.d(TAG, "Use CAS AIDL interface to create plugin");
                    this.mICas = service.createPlugin(i, this.mBinder);
                } else {
                    android.hardware.cas.V1_0.IMediaCasService serviceHidl = getServiceHidl();
                    android.hardware.cas.V1_2.IMediaCasService castFrom = android.hardware.cas.V1_2.IMediaCasService.castFrom((IHwInterface) serviceHidl);
                    if (castFrom == null) {
                        android.hardware.cas.V1_1.IMediaCasService castFrom2 = android.hardware.cas.V1_1.IMediaCasService.castFrom((IHwInterface) serviceHidl);
                        if (castFrom2 == null) {
                            Log.d(TAG, "Used cas@1_0 interface to create plugin");
                            this.mICasHidl = serviceHidl.createPlugin(i, this.mBinderHidl);
                        } else {
                            Log.d(TAG, "Used cas@1.1 interface to create plugin");
                            android.hardware.cas.V1_1.ICas createPluginExt = castFrom2.createPluginExt(i, this.mBinderHidl);
                            this.mICasHidl11 = createPluginExt;
                            this.mICasHidl = createPluginExt;
                        }
                    } else {
                        Log.d(TAG, "Used cas@1.2 interface to create plugin");
                        android.hardware.cas.V1_2.ICas castFrom3 = android.hardware.cas.V1_2.ICas.castFrom((IHwInterface) castFrom.createPluginExt(i, this.mBinderHidl));
                        this.mICasHidl12 = castFrom3;
                        this.mICasHidl11 = castFrom3;
                        this.mICasHidl = castFrom3;
                    }
                }
                if (this.mICas == null && this.mICasHidl == null) {
                    throw new MediaCasException.UnsupportedCasException("Unsupported casSystemId " + i);
                }
            } catch (Exception e) {
                Log.e(TAG, "Failed to create plugin: " + e);
                this.mICas = null;
                this.mICasHidl = null;
                throw new MediaCasException.UnsupportedCasException("Unsupported casSystemId " + i);
            }
        } catch (Throwable th) {
            if (this.mICas != null || this.mICasHidl != null) {
                throw th;
            }
            throw new MediaCasException.UnsupportedCasException("Unsupported casSystemId " + i);
        }
    }

    private void registerClient(Context context, String str, int i) {
        TunerResourceManager tunerResourceManager = (TunerResourceManager) context.getSystemService(Context.TV_TUNER_RESOURCE_MGR_SERVICE);
        this.mTunerResourceManager = tunerResourceManager;
        if (tunerResourceManager != null) {
            int[] iArr = new int[1];
            ResourceClientProfile resourceClientProfile = new ResourceClientProfile();
            resourceClientProfile.tvInputSessionId = str;
            resourceClientProfile.useCase = i;
            this.mTunerResourceManager.registerClientProfile(resourceClientProfile, context.getMainExecutor(), this.mResourceListener, iArr);
            this.mClientId = iArr[0];
        }
    }

    public MediaCas(int i) throws MediaCasException.UnsupportedCasException {
        createPlugin(i);
    }

    public MediaCas(Context context, int i, String str, int i2) throws MediaCasException.UnsupportedCasException {
        Objects.requireNonNull(context, "context must not be null");
        createPlugin(i);
        registerClient(context, str, i2);
    }

    public MediaCas(Context context, int i, String str, int i2, Handler handler, EventListener eventListener) throws MediaCasException.UnsupportedCasException {
        Objects.requireNonNull(context, "context must not be null");
        setEventListener(eventListener, handler);
        createPlugin(i);
        registerClient(context, str, i2);
    }

    @SystemApi
    public boolean updateResourcePriority(int i, int i2) {
        TunerResourceManager tunerResourceManager = this.mTunerResourceManager;
        if (tunerResourceManager != null) {
            return tunerResourceManager.updateClientPriority(this.mClientId, i, i2);
        }
        return false;
    }

    @SystemApi
    public void setResourceOwnershipRetention(boolean z) {
        TunerResourceManager tunerResourceManager = this.mTunerResourceManager;
        if (tunerResourceManager != null) {
            tunerResourceManager.setResourceOwnershipRetention(this.mClientId, z);
        }
    }

    IHwBinder getBinder() {
        if (this.mICas != null) {
            return null;
        }
        validateInternalStates();
        return this.mICasHidl.asBinder();
    }

    public boolean isAidlHal() {
        return this.mICas != null;
    }

    public interface EventListener {
        void onEvent(MediaCas mediaCas, int i, int i2, byte[] bArr);

        default void onSessionEvent(MediaCas mediaCas, Session session, int i, int i2, byte[] bArr) {
            Log.d(MediaCas.TAG, "Received MediaCas Session event");
        }

        default void onPluginStatusUpdate(MediaCas mediaCas, int i, int i2) {
            Log.d(MediaCas.TAG, "Received MediaCas Plugin Status event");
        }

        default void onResourceLost(MediaCas mediaCas) {
            Log.d(MediaCas.TAG, "Received MediaCas Resource Reclaim event");
        }
    }

    public void setEventListener(EventListener eventListener, Handler handler) {
        this.mListener = eventListener;
        if (eventListener == null) {
            this.mEventHandler = null;
            return;
        }
        Looper looper = handler != null ? handler.getLooper() : null;
        if (looper == null && (looper = Looper.myLooper()) == null && (looper = Looper.getMainLooper()) == null) {
            HandlerThread handlerThread = this.mHandlerThread;
            if (handlerThread == null || !handlerThread.isAlive()) {
                HandlerThread handlerThread2 = new HandlerThread("MediaCasEventThread", -2);
                this.mHandlerThread = handlerThread2;
                handlerThread2.start();
            }
            looper = this.mHandlerThread.getLooper();
        }
        this.mEventHandler = new EventHandler(looper);
    }

    public void setPrivateData(byte[] bArr) throws MediaCasException {
        validateInternalStates();
        try {
            ICas iCas = this.mICas;
            if (iCas != null) {
                try {
                    iCas.setPrivateData(bArr);
                } catch (ServiceSpecificException e) {
                    MediaCasException.throwExceptionIfNeeded(e.errorCode);
                }
            } else {
                MediaCasException.throwExceptionIfNeeded(this.mICasHidl.setPrivateData(toByteArray(bArr, 0, bArr.length)));
            }
        } catch (RemoteException unused) {
            cleanupAndRethrowIllegalState();
        }
    }

    private class OpenSessionCallback implements ICas.openSessionCallback {
        public Session mSession;
        public int mStatus;

        private OpenSessionCallback() {
        }

        @Override // android.hardware.cas.V1_0.ICas.openSessionCallback
        public void onValues(int i, ArrayList<Byte> arrayList) {
            this.mStatus = i;
            MediaCas mediaCas = MediaCas.this;
            this.mSession = mediaCas.createFromSessionId(mediaCas.toBytes(arrayList));
        }
    }

    private class OpenSession_1_2_Callback implements ICas.openSession_1_2Callback {
        public Session mSession;
        public int mStatus;

        private OpenSession_1_2_Callback() {
        }

        @Override // android.hardware.cas.V1_2.ICas.openSession_1_2Callback
        public void onValues(int i, ArrayList<Byte> arrayList) {
            this.mStatus = i;
            MediaCas mediaCas = MediaCas.this;
            this.mSession = mediaCas.createFromSessionId(mediaCas.toBytes(arrayList));
        }
    }

    private long getSessionResourceHandle() throws MediaCasException {
        validateInternalStates();
        long[] jArr = {-1};
        if (this.mTunerResourceManager != null) {
            CasSessionRequest casSessionRequest = new CasSessionRequest();
            casSessionRequest.clientId = this.mClientId;
            casSessionRequest.casSystemId = this.mCasSystemId;
            if (!this.mTunerResourceManager.requestCasSession(casSessionRequest, jArr)) {
                throw new MediaCasException.InsufficientResourceException("insufficient resource to Open Session");
            }
        }
        return jArr[0];
    }

    private void addSessionToResourceMap(Session session, long j) {
        if (j != -1) {
            synchronized (this.mSessionMap) {
                this.mSessionMap.put(session, Long.valueOf(j));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeSessionFromResourceMap(Session session) {
        synchronized (this.mSessionMap) {
            if (this.mSessionMap.get(session) != null) {
                this.mTunerResourceManager.releaseCasSession(this.mSessionMap.get(session).longValue(), this.mClientId);
                this.mSessionMap.remove(session);
            }
        }
    }

    public Session openSession() throws MediaCasException {
        long sessionResourceHandle = getSessionResourceHandle();
        try {
            android.hardware.cas.ICas iCas = this.mICas;
            if (iCas != null) {
                try {
                    Session createFromSessionId = createFromSessionId(iCas.openSessionDefault());
                    addSessionToResourceMap(createFromSessionId, sessionResourceHandle);
                    Log.d(TAG, "Write Stats Log for succeed to Open Session.");
                    FrameworkStatsLog.write(280, this.mUserId, this.mCasSystemId, 1);
                    return createFromSessionId;
                } catch (ServiceSpecificException e) {
                    MediaCasException.throwExceptionIfNeeded(e.errorCode);
                }
            } else if (this.mICasHidl != null) {
                OpenSessionCallback openSessionCallback = new OpenSessionCallback();
                this.mICasHidl.openSession(openSessionCallback);
                MediaCasException.throwExceptionIfNeeded(openSessionCallback.mStatus);
                addSessionToResourceMap(openSessionCallback.mSession, sessionResourceHandle);
                Log.d(TAG, "Write Stats Log for succeed to Open Session.");
                FrameworkStatsLog.write(280, this.mUserId, this.mCasSystemId, 1);
                return openSessionCallback.mSession;
            }
        } catch (RemoteException unused) {
            cleanupAndRethrowIllegalState();
        }
        Log.d(TAG, "Write Stats Log for fail to Open Session.");
        FrameworkStatsLog.write(280, this.mUserId, this.mCasSystemId, 2);
        return null;
    }

    public Session openSession(int i, int i2) throws MediaCasException {
        long sessionResourceHandle = getSessionResourceHandle();
        android.hardware.cas.ICas iCas = this.mICas;
        if (iCas != null) {
            try {
                Session createFromSessionId = createFromSessionId(iCas.openSession(i, i2));
                addSessionToResourceMap(createFromSessionId, sessionResourceHandle);
                Log.d(TAG, "Write Stats Log for succeed to Open Session.");
                FrameworkStatsLog.write(280, this.mUserId, this.mCasSystemId, 1);
                return createFromSessionId;
            } catch (RemoteException | ServiceSpecificException unused) {
                cleanupAndRethrowIllegalState();
            }
        }
        if (this.mICasHidl12 == null) {
            Log.d(TAG, "Open Session with scrambling mode is only supported by cas@1.2+ interface");
            throw new MediaCasException.UnsupportedCasException("Open Session with scrambling mode is not supported");
        }
        try {
            OpenSession_1_2_Callback openSession_1_2_Callback = new OpenSession_1_2_Callback();
            this.mICasHidl12.openSession_1_2(i, i2, openSession_1_2_Callback);
            MediaCasException.throwExceptionIfNeeded(openSession_1_2_Callback.mStatus);
            addSessionToResourceMap(openSession_1_2_Callback.mSession, sessionResourceHandle);
            Log.d(TAG, "Write Stats Log for succeed to Open Session.");
            FrameworkStatsLog.write(280, this.mUserId, this.mCasSystemId, 1);
            return openSession_1_2_Callback.mSession;
        } catch (RemoteException unused2) {
            this.cleanupAndRethrowIllegalState();
            Log.d(TAG, "Write Stats Log for fail to Open Session.");
            FrameworkStatsLog.write(280, this.mUserId, this.mCasSystemId, 2);
            return null;
        }
    }

    public void processEmm(byte[] bArr, int i, int i2) throws MediaCasException {
        validateInternalStates();
        try {
            android.hardware.cas.ICas iCas = this.mICas;
            if (iCas != null) {
                try {
                    iCas.processEmm(Arrays.copyOfRange(bArr, i, i2));
                } catch (ServiceSpecificException e) {
                    MediaCasException.throwExceptionIfNeeded(e.errorCode);
                }
            } else {
                MediaCasException.throwExceptionIfNeeded(this.mICasHidl.processEmm(toByteArray(bArr, i, i2)));
            }
        } catch (RemoteException unused) {
            cleanupAndRethrowIllegalState();
        }
    }

    public void processEmm(byte[] bArr) throws MediaCasException {
        processEmm(bArr, 0, bArr.length);
    }

    public void sendEvent(int i, int i2, byte[] bArr) throws MediaCasException {
        validateInternalStates();
        try {
            android.hardware.cas.ICas iCas = this.mICas;
            if (iCas != null) {
                if (bArr == null) {
                    try {
                        bArr = new byte[0];
                    } catch (ServiceSpecificException e) {
                        MediaCasException.throwExceptionIfNeeded(e.errorCode);
                        return;
                    }
                }
                iCas.sendEvent(i, i2, bArr);
                return;
            }
            MediaCasException.throwExceptionIfNeeded(this.mICasHidl.sendEvent(i, i2, toByteArray(bArr)));
        } catch (RemoteException unused) {
            cleanupAndRethrowIllegalState();
        }
    }

    public void provision(String str) throws MediaCasException {
        validateInternalStates();
        try {
            android.hardware.cas.ICas iCas = this.mICas;
            if (iCas != null) {
                try {
                    iCas.provision(str);
                } catch (ServiceSpecificException e) {
                    MediaCasException.throwExceptionIfNeeded(e.errorCode);
                }
            } else {
                MediaCasException.throwExceptionIfNeeded(this.mICasHidl.provision(str));
            }
        } catch (RemoteException unused) {
            cleanupAndRethrowIllegalState();
        }
    }

    public void refreshEntitlements(int i, byte[] bArr) throws MediaCasException {
        validateInternalStates();
        try {
            android.hardware.cas.ICas iCas = this.mICas;
            if (iCas != null) {
                if (bArr == null) {
                    try {
                        bArr = new byte[0];
                    } catch (ServiceSpecificException e) {
                        MediaCasException.throwExceptionIfNeeded(e.errorCode);
                        return;
                    }
                }
                iCas.refreshEntitlements(i, bArr);
                return;
            }
            MediaCasException.throwExceptionIfNeeded(this.mICasHidl.refreshEntitlements(i, toByteArray(bArr)));
        } catch (RemoteException unused) {
            cleanupAndRethrowIllegalState();
        }
    }

    public void forceResourceLost() {
        TunerResourceManager.ResourcesReclaimListener resourcesReclaimListener = this.mResourceListener;
        if (resourcesReclaimListener != null) {
            resourcesReclaimListener.onReclaimResources();
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        android.hardware.cas.ICas iCas = this.mICas;
        if (iCas != null) {
            try {
                iCas.release();
            } catch (RemoteException unused) {
            } catch (Throwable th) {
                this.mICas = null;
                throw th;
            }
            this.mICas = null;
        } else {
            android.hardware.cas.V1_0.ICas iCas2 = this.mICasHidl;
            if (iCas2 != null) {
                try {
                    iCas2.release();
                } catch (RemoteException unused2) {
                } catch (Throwable th2) {
                    this.mICasHidl12 = null;
                    this.mICasHidl11 = null;
                    this.mICasHidl = null;
                    throw th2;
                }
                this.mICasHidl12 = null;
                this.mICasHidl11 = null;
                this.mICasHidl = null;
            }
        }
        TunerResourceManager tunerResourceManager = this.mTunerResourceManager;
        if (tunerResourceManager != null) {
            tunerResourceManager.unregisterClientProfile(this.mClientId);
            this.mTunerResourceManager = null;
        }
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quit();
            this.mHandlerThread = null;
        }
    }

    protected void finalize() {
        close();
    }
}
