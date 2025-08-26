package android.media;

import android.app.ActivityThread;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaDrm;
import android.media.metrics.LogSessionId;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.os.Messenger;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.text.format.DateFormat;
import android.util.Log;
import dalvik.system.CloseGuard;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes2.dex */
public final class MediaDrm implements AutoCloseable {
    public static final int CERTIFICATE_TYPE_NONE = 0;
    public static final int CERTIFICATE_TYPE_X509 = 1;
    private static final int DRM_EVENT = 200;
    public static final int EVENT_KEY_EXPIRED = 3;
    public static final int EVENT_KEY_REQUIRED = 2;
    public static final int EVENT_PROVISION_REQUIRED = 1;
    public static final int EVENT_SESSION_RECLAIMED = 5;
    public static final int EVENT_VENDOR_DEFINED = 4;
    private static final int EXPIRATION_UPDATE = 201;
    public static final int HDCP_LEVEL_UNKNOWN = 0;
    public static final int HDCP_NONE = 1;
    public static final int HDCP_NO_DIGITAL_OUTPUT = Integer.MAX_VALUE;
    public static final int HDCP_V1 = 2;
    public static final int HDCP_V2 = 3;
    public static final int HDCP_V2_1 = 4;
    public static final int HDCP_V2_2 = 5;
    public static final int HDCP_V2_3 = 6;
    private static final int KEY_STATUS_CHANGE = 202;
    public static final int KEY_TYPE_OFFLINE = 2;
    public static final int KEY_TYPE_RELEASE = 3;
    public static final int KEY_TYPE_STREAMING = 1;
    public static final int OFFLINE_LICENSE_STATE_RELEASED = 2;
    public static final int OFFLINE_LICENSE_STATE_UNKNOWN = 0;
    public static final int OFFLINE_LICENSE_STATE_USABLE = 1;
    private static final String PERMISSION = "android.permission.ACCESS_DRM_CERTIFICATES";
    public static final String PROPERTY_ALGORITHMS = "algorithms";
    public static final String PROPERTY_DESCRIPTION = "description";
    public static final String PROPERTY_DEVICE_UNIQUE_ID = "deviceUniqueId";
    public static final String PROPERTY_VENDOR = "vendor";
    public static final String PROPERTY_VERSION = "version";
    public static final int SECURITY_LEVEL_HW_SECURE_ALL = 5;
    public static final int SECURITY_LEVEL_HW_SECURE_CRYPTO = 3;
    public static final int SECURITY_LEVEL_HW_SECURE_DECODE = 4;
    public static final int SECURITY_LEVEL_MAX = 6;
    public static final int SECURITY_LEVEL_SW_SECURE_CRYPTO = 1;
    public static final int SECURITY_LEVEL_SW_SECURE_DECODE = 2;
    public static final int SECURITY_LEVEL_UNKNOWN = 0;
    private static final int SESSION_LOST_STATE = 203;
    private static final String TAG = "MediaDrm";
    private final String mAppPackageName;
    private final CloseGuard mCloseGuard;
    private final AtomicBoolean mClosed = new AtomicBoolean();
    private ServiceConnection mConnection;
    private Context mContext;
    private final Map<Integer, ListenerWithExecutor> mListenerMap;
    private long mNativeContext;
    private final Map<ByteBuffer, PlaybackComponent> mPlaybackComponentMap;
    Messenger mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ArrayProperty {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CertificateType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DrmEvent {
    }

    @Retention(RetentionPolicy.SOURCE)
    @Deprecated
    public @interface HdcpLevel {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaDrmErrorCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OfflineLicenseState {
    }

    public interface OnEventListener {
        void onEvent(MediaDrm mediaDrm, byte[] bArr, int i, int i2, byte[] bArr2);
    }

    public interface OnExpirationUpdateListener {
        void onExpirationUpdate(MediaDrm mediaDrm, byte[] bArr, long j);
    }

    public interface OnKeyStatusChangeListener {
        void onKeyStatusChange(MediaDrm mediaDrm, byte[] bArr, List<KeyStatus> list, boolean z);
    }

    public interface OnSessionLostStateListener {
        void onSessionLostState(MediaDrm mediaDrm, byte[] bArr);
    }

    @Retention(RetentionPolicy.SOURCE)
    @Deprecated
    public @interface SecurityLevel {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StringProperty {
    }

    private native void closeSessionNative(byte[] bArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static final native byte[] decryptNative(MediaDrm mediaDrm, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4);

    /* JADX INFO: Access modifiers changed from: private */
    public static final native byte[] encryptNative(MediaDrm mediaDrm, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4);

    private native KeyRequest getKeyRequestNative(byte[] bArr, byte[] bArr2, String str, int i, HashMap<String, String> map) throws NotProvisionedException;

    public static final int getMaxSecurityLevel() {
        return 6;
    }

    private native PersistableBundle getMetricsNative();

    private native ProvisionRequest getProvisionRequestNative(int i, String str);

    private static final native byte[] getSupportedCryptoSchemesNative();

    private static final native boolean isCryptoSchemeSupportedNative(byte[] bArr, String str, int i);

    private static final native void native_init();

    private final native void native_setup(Object obj, byte[] bArr, String str);

    private native byte[] openSessionNative(int i) throws ResourceBusyException, NotProvisionedException;

    private native Certificate provideProvisionResponseNative(byte[] bArr) throws DeniedByServerException;

    /* JADX INFO: Access modifiers changed from: private */
    public static final native void setCipherAlgorithmNative(MediaDrm mediaDrm, byte[] bArr, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static final native void setMacAlgorithmNative(MediaDrm mediaDrm, byte[] bArr, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native void setPlaybackId(byte[] bArr, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static final native byte[] signNative(MediaDrm mediaDrm, byte[] bArr, byte[] bArr2, byte[] bArr3);

    private static final native byte[] signRSANative(MediaDrm mediaDrm, byte[] bArr, String str, byte[] bArr2, byte[] bArr3);

    /* JADX INFO: Access modifiers changed from: private */
    public static final native boolean verifyNative(MediaDrm mediaDrm, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4);

    public native int getConnectedHdcpLevel();

    public native List<LogMessage> getLogMessages();

    public native int getMaxHdcpLevel();

    public native int getMaxSessionCount();

    public native List<byte[]> getOfflineLicenseKeySetIds();

    public native int getOfflineLicenseState(byte[] bArr);

    public native int getOpenSessionCount();

    public native byte[] getPropertyByteArray(String str);

    public native String getPropertyString(String str);

    public native byte[] getSecureStop(byte[] bArr);

    public native List<byte[]> getSecureStopIds();

    public native List<byte[]> getSecureStops();

    public native int getSecurityLevel(byte[] bArr);

    public final native void native_release();

    public native byte[] provideKeyResponse(byte[] bArr, byte[] bArr2) throws DeniedByServerException, NotProvisionedException;

    public native HashMap<String, String> queryKeyStatus(byte[] bArr);

    public native void releaseSecureStops(byte[] bArr);

    public native void removeAllSecureStops();

    public native void removeKeys(byte[] bArr);

    public native void removeOfflineLicense(byte[] bArr);

    public native void removeSecureStop(byte[] bArr);

    public native boolean requiresSecureDecoder(String str, int i);

    public native void restoreKeys(byte[] bArr, byte[] bArr2);

    public native void setPropertyByteArray(String str, byte[] bArr);

    public native void setPropertyString(String str, String str2);

    public static final boolean isCryptoSchemeSupported(UUID uuid) {
        return isCryptoSchemeSupportedNative(getByteArrayFromUUID(uuid), null, 0);
    }

    public static final boolean isCryptoSchemeSupported(UUID uuid, String str) {
        return isCryptoSchemeSupportedNative(getByteArrayFromUUID(uuid), str, 0);
    }

    public static final boolean isCryptoSchemeSupported(UUID uuid, String str, int i) {
        return isCryptoSchemeSupportedNative(getByteArrayFromUUID(uuid), str, i);
    }

    public static final List<UUID> getSupportedCryptoSchemes() {
        return getUUIDsFromByteArray(getSupportedCryptoSchemesNative());
    }

    private static final byte[] getByteArrayFromUUID(UUID uuid) {
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        byte[] bArr = new byte[16];
        for (int i = 0; i < 8; i++) {
            int i2 = (7 - i) * 8;
            bArr[i] = (byte) (mostSignificantBits >>> i2);
            bArr[i + 8] = (byte) (leastSignificantBits >>> i2);
        }
        return bArr;
    }

    private static final UUID getUUIDFromByteArray(byte[] bArr, int i) {
        long j = 0;
        long j2 = 0;
        for (int i2 = 0; i2 < 8; i2++) {
            int i3 = i + i2;
            j = (j << 8) | (bArr[i3] & 255);
            j2 = (j2 << 8) | (bArr[i3 + 8] & 255);
        }
        return new UUID(j, j2);
    }

    private static final List<UUID> getUUIDsFromByteArray(byte[] bArr) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (int i = 0; i < bArr.length; i += 16) {
            linkedHashSet.add(getUUIDFromByteArray(bArr, i));
        }
        return new ArrayList(linkedHashSet);
    }

    private Handler createHandler() {
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper != null) {
            return new Handler(looperMyLooper);
        }
        Looper mainLooper = Looper.getMainLooper();
        if (mainLooper != null) {
            return new Handler(mainLooper);
        }
        return null;
    }

    public MediaDrm(UUID uuid) throws UnsupportedSchemeException {
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        this.mService = null;
        this.mConnection = new ServiceConnection() { // from class: android.media.MediaDrm.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MediaDrm.this.mService = new Messenger(iBinder);
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                MediaDrm.this.mService = null;
                MediaDrm.this.mContext = null;
            }
        };
        this.mListenerMap = new ConcurrentHashMap();
        this.mPlaybackComponentMap = new ConcurrentHashMap();
        String strCurrentOpPackageName = ActivityThread.currentOpPackageName();
        this.mAppPackageName = strCurrentOpPackageName;
        native_setup(new WeakReference(this), getByteArrayFromUUID(uuid), strCurrentOpPackageName);
        this.mContext = ActivityThread.currentApplication();
        if (!strCurrentOpPackageName.contains("rkpdapp")) {
            Intent intent = new Intent();
            intent.setPackage("com.samsung.drmboost");
            intent.setAction("com.samsung.intent.action.START_MY_SERVICE");
            this.mContext.bindService(intent, this.mConnection, 1);
        }
        closeGuard.open("release");
    }

    public static final class ErrorCodes {
        public static final int ERROR_CERTIFICATE_MALFORMED = 10;
        public static final int ERROR_CERTIFICATE_MISSING = 11;
        public static final int ERROR_CRYPTO_LIBRARY = 12;
        public static final int ERROR_FRAME_TOO_LARGE = 8;
        public static final int ERROR_GENERIC_OEM = 13;
        public static final int ERROR_GENERIC_PLUGIN = 14;
        public static final int ERROR_INIT_DATA = 15;
        public static final int ERROR_INSUFFICIENT_OUTPUT_PROTECTION = 4;
        public static final int ERROR_INSUFFICIENT_SECURITY = 7;
        public static final int ERROR_KEY_EXPIRED = 2;
        public static final int ERROR_KEY_NOT_LOADED = 16;
        public static final int ERROR_LICENSE_PARSE = 17;
        public static final int ERROR_LICENSE_POLICY = 18;
        public static final int ERROR_LICENSE_RELEASE = 19;
        public static final int ERROR_LICENSE_REQUEST_REJECTED = 20;
        public static final int ERROR_LICENSE_RESTORE = 21;
        public static final int ERROR_LICENSE_STATE = 22;
        public static final int ERROR_LOST_STATE = 9;
        public static final int ERROR_MEDIA_FRAMEWORK = 23;
        public static final int ERROR_NO_KEY = 1;
        public static final int ERROR_PROVISIONING_CERTIFICATE = 24;
        public static final int ERROR_PROVISIONING_CONFIG = 25;
        public static final int ERROR_PROVISIONING_PARSE = 26;
        public static final int ERROR_PROVISIONING_REQUEST_REJECTED = 27;
        public static final int ERROR_PROVISIONING_RETRY = 28;
        public static final int ERROR_RESOURCE_BUSY = 3;
        public static final int ERROR_RESOURCE_CONTENTION = 29;
        public static final int ERROR_SECURE_STOP_RELEASE = 30;
        public static final int ERROR_SESSION_NOT_OPENED = 5;
        public static final int ERROR_STORAGE_READ = 31;
        public static final int ERROR_STORAGE_WRITE = 32;
        public static final int ERROR_UNKNOWN = 0;
        public static final int ERROR_UNSUPPORTED_OPERATION = 6;
        public static final int ERROR_ZERO_SUBSAMPLES = 33;

        private ErrorCodes() {
        }
    }

    public static final class MediaDrmStateException extends IllegalStateException implements MediaDrmThrowable {
        private final String mDiagnosticInfo;
        private final int mErrorCode;
        private final int mErrorContext;
        private final int mOemError;
        private final int mVendorError;

        public MediaDrmStateException(int i, String str) {
            this(str, i, 0, 0, 0);
        }

        public MediaDrmStateException(String str, int i, int i2, int i3, int i4) {
            super(str);
            this.mErrorCode = i;
            this.mVendorError = i2;
            this.mOemError = i3;
            this.mErrorContext = i4;
            this.mDiagnosticInfo = "android.media.MediaDrm.error_" + (i < 0 ? "neg_" : "") + Math.abs(i);
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        @Override // android.media.MediaDrmThrowable
        public int getVendorError() {
            return this.mVendorError;
        }

        @Override // android.media.MediaDrmThrowable
        public int getOemError() {
            return this.mOemError;
        }

        @Override // android.media.MediaDrmThrowable
        public int getErrorContext() {
            return this.mErrorContext;
        }

        public boolean isTransient() {
            int i = this.mErrorCode;
            return i == 28 || i == 29;
        }

        public String getDiagnosticInfo() {
            return this.mDiagnosticInfo;
        }
    }

    public static final class SessionException extends RuntimeException implements MediaDrmThrowable {
        public static final int ERROR_RESOURCE_CONTENTION = 1;
        public static final int ERROR_UNKNOWN = 0;
        private final int mErrorCode;
        private final int mErrorContext;
        private final int mOemError;
        private final int mVendorError;

        @Retention(RetentionPolicy.SOURCE)
        public @interface SessionErrorCode {
        }

        public SessionException(int i, String str) {
            this(str, i, 0, 0, 0);
        }

        public SessionException(String str, int i, int i2, int i3, int i4) {
            super(str);
            this.mErrorCode = i;
            this.mVendorError = i2;
            this.mOemError = i3;
            this.mErrorContext = i4;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        @Override // android.media.MediaDrmThrowable
        public int getVendorError() {
            return this.mVendorError;
        }

        @Override // android.media.MediaDrmThrowable
        public int getOemError() {
            return this.mOemError;
        }

        @Override // android.media.MediaDrmThrowable
        public int getErrorContext() {
            return this.mErrorContext;
        }

        public boolean isTransient() {
            return this.mErrorCode == 1;
        }
    }

    public void setOnExpirationUpdateListener(OnExpirationUpdateListener onExpirationUpdateListener, Handler handler) {
        setListenerWithHandler(201, handler, onExpirationUpdateListener, new MediaDrm$$ExternalSyntheticLambda0(this));
    }

    public void setOnExpirationUpdateListener(Executor executor, OnExpirationUpdateListener onExpirationUpdateListener) {
        setListenerWithExecutor(201, executor, onExpirationUpdateListener, new MediaDrm$$ExternalSyntheticLambda0(this));
    }

    public void clearOnExpirationUpdateListener() {
        clearGenericListener(201);
    }

    public void setOnKeyStatusChangeListener(OnKeyStatusChangeListener onKeyStatusChangeListener, Handler handler) {
        setListenerWithHandler(202, handler, onKeyStatusChangeListener, new MediaDrm$$ExternalSyntheticLambda1(this));
    }

    public void setOnKeyStatusChangeListener(Executor executor, OnKeyStatusChangeListener onKeyStatusChangeListener) {
        setListenerWithExecutor(202, executor, onKeyStatusChangeListener, new MediaDrm$$ExternalSyntheticLambda1(this));
    }

    public void clearOnKeyStatusChangeListener() {
        clearGenericListener(202);
    }

    public void setOnSessionLostStateListener(OnSessionLostStateListener onSessionLostStateListener, Handler handler) {
        setListenerWithHandler(203, handler, onSessionLostStateListener, new MediaDrm$$ExternalSyntheticLambda5(this));
    }

    public void setOnSessionLostStateListener(Executor executor, OnSessionLostStateListener onSessionLostStateListener) {
        setListenerWithExecutor(203, executor, onSessionLostStateListener, new MediaDrm$$ExternalSyntheticLambda5(this));
    }

    public void clearOnSessionLostStateListener() {
        clearGenericListener(203);
    }

    public static final class KeyStatus {
        public static final int STATUS_EXPIRED = 1;
        public static final int STATUS_INTERNAL_ERROR = 4;
        public static final int STATUS_OUTPUT_NOT_ALLOWED = 2;
        public static final int STATUS_PENDING = 3;
        public static final int STATUS_USABLE = 0;
        public static final int STATUS_USABLE_IN_FUTURE = 5;
        private final byte[] mKeyId;
        private final int mStatusCode;

        @Retention(RetentionPolicy.SOURCE)
        public @interface KeyStatusCode {
        }

        KeyStatus(byte[] bArr, int i) {
            this.mKeyId = bArr;
            this.mStatusCode = i;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public byte[] getKeyId() {
            return this.mKeyId;
        }
    }

    public void setOnEventListener(OnEventListener onEventListener) {
        setOnEventListener(onEventListener, (Handler) null);
    }

    public void setOnEventListener(OnEventListener onEventListener, Handler handler) {
        setListenerWithHandler(200, handler, onEventListener, new MediaDrm$$ExternalSyntheticLambda3(this));
    }

    public void setOnEventListener(Executor executor, OnEventListener onEventListener) {
        setListenerWithExecutor(200, executor, onEventListener, new MediaDrm$$ExternalSyntheticLambda3(this));
    }

    public void clearOnEventListener() {
        clearGenericListener(200);
    }

    private <T> void setListenerWithHandler(int i, Handler handler, T t, Function<T, Consumer<ListenerArgs>> function) {
        if (t == null) {
            clearGenericListener(i);
            return;
        }
        if (handler == null) {
            handler = createHandler();
        }
        setGenericListener(i, new HandlerExecutor(handler), t, function);
    }

    private <T> void setListenerWithExecutor(int i, Executor executor, T t, Function<T, Consumer<ListenerArgs>> function) {
        if (executor == null || t == null) {
            throw new IllegalArgumentException(String.format("executor %s listener %s", executor, t));
        }
        setGenericListener(i, executor, t, function);
    }

    private <T> void setGenericListener(int i, Executor executor, T t, Function<T, Consumer<ListenerArgs>> function) {
        this.mListenerMap.put(Integer.valueOf(i), new ListenerWithExecutor(executor, function.apply(t)));
    }

    private void clearGenericListener(int i) {
        this.mListenerMap.remove(Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Consumer<ListenerArgs> createOnEventListener(final OnEventListener onEventListener) {
        return new Consumer() { // from class: android.media.MediaDrm$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$createOnEventListener$0(onEventListener, (MediaDrm.ListenerArgs) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createOnEventListener$0(OnEventListener onEventListener, ListenerArgs listenerArgs) {
        byte[] bArr = listenerArgs.sessionId;
        byte[] bArr2 = bArr.length == 0 ? null : bArr;
        byte[] bArr3 = listenerArgs.data;
        byte[] bArr4 = (bArr3 == null || bArr3.length != 0) ? bArr3 : null;
        Log.i(TAG, "Drm event (" + listenerArgs.arg1 + "," + listenerArgs.arg2 + NavigationBarInflaterView.KEY_CODE_END);
        onEventListener.onEvent(this, bArr2, listenerArgs.arg1, listenerArgs.arg2, bArr4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Consumer<ListenerArgs> createOnKeyStatusChangeListener(final OnKeyStatusChangeListener onKeyStatusChangeListener) {
        return new Consumer() { // from class: android.media.MediaDrm$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$createOnKeyStatusChangeListener$1(onKeyStatusChangeListener, (MediaDrm.ListenerArgs) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createOnKeyStatusChangeListener$1(OnKeyStatusChangeListener onKeyStatusChangeListener, ListenerArgs listenerArgs) {
        byte[] bArr = listenerArgs.sessionId;
        if (bArr.length > 0) {
            List<KeyStatus> list = listenerArgs.keyStatusList;
            boolean z = listenerArgs.hasNewUsableKey;
            Log.i(TAG, "Drm key status changed");
            onKeyStatusChangeListener.onKeyStatusChange(this, bArr, list, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Consumer<ListenerArgs> createOnExpirationUpdateListener(final OnExpirationUpdateListener onExpirationUpdateListener) {
        return new Consumer() { // from class: android.media.MediaDrm$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$createOnExpirationUpdateListener$2(onExpirationUpdateListener, (MediaDrm.ListenerArgs) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createOnExpirationUpdateListener$2(OnExpirationUpdateListener onExpirationUpdateListener, ListenerArgs listenerArgs) {
        byte[] bArr = listenerArgs.sessionId;
        if (bArr.length > 0) {
            long j = listenerArgs.expirationTime;
            Log.i(TAG, "Drm key expiration update: " + j);
            onExpirationUpdateListener.onExpirationUpdate(this, bArr, j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Consumer<ListenerArgs> createOnSessionLostStateListener(final OnSessionLostStateListener onSessionLostStateListener) {
        return new Consumer() { // from class: android.media.MediaDrm$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$createOnSessionLostStateListener$3(onSessionLostStateListener, (MediaDrm.ListenerArgs) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createOnSessionLostStateListener$3(OnSessionLostStateListener onSessionLostStateListener, ListenerArgs listenerArgs) {
        byte[] bArr = listenerArgs.sessionId;
        Log.i(TAG, "Drm session lost state event: ");
        onSessionLostStateListener.onSessionLostState(this, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ListenerArgs {
        private final int arg1;
        private final int arg2;
        private final byte[] data;
        private final long expirationTime;
        private final boolean hasNewUsableKey;
        private final List<KeyStatus> keyStatusList;
        private final byte[] sessionId;

        public ListenerArgs(int i, int i2, byte[] bArr, byte[] bArr2, long j, List<KeyStatus> list, boolean z) {
            this.arg1 = i;
            this.arg2 = i2;
            this.sessionId = bArr;
            this.data = bArr2;
            this.expirationTime = j;
            this.keyStatusList = list;
            this.hasNewUsableKey = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ListenerWithExecutor {
        private final Consumer<ListenerArgs> mConsumer;
        private final Executor mExecutor;

        public ListenerWithExecutor(Executor executor, Consumer<ListenerArgs> consumer) {
            this.mExecutor = executor;
            this.mConsumer = consumer;
        }
    }

    private List<KeyStatus> keyStatusListFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        ArrayList arrayList = new ArrayList(i);
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return arrayList;
            }
            arrayList.add(new KeyStatus(parcel.createByteArray(), parcel.readInt()));
            i = i2;
        }
    }

    private static void postEventFromNative(Object obj, int i, final int i2, final int i3, final byte[] bArr, final byte[] bArr2, final long j, final List<KeyStatus> list, final boolean z) {
        final MediaDrm mediaDrm = (MediaDrm) ((WeakReference) obj).get();
        if (mediaDrm == null) {
            return;
        }
        switch (i) {
            case 200:
            case 201:
            case 202:
            case 203:
                final ListenerWithExecutor listenerWithExecutor = mediaDrm.mListenerMap.get(Integer.valueOf(i));
                if (listenerWithExecutor != null) {
                    listenerWithExecutor.mExecutor.execute(new Runnable() { // from class: android.media.MediaDrm$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaDrm.lambda$postEventFromNative$4(this.f$0, i2, i3, bArr, bArr2, j, list, z, listenerWithExecutor);
                        }
                    });
                    break;
                }
                break;
            default:
                Log.e(TAG, "Unknown message type " + i);
                break;
        }
    }

    static /* synthetic */ void lambda$postEventFromNative$4(MediaDrm mediaDrm, int i, int i2, byte[] bArr, byte[] bArr2, long j, List list, boolean z, ListenerWithExecutor listenerWithExecutor) {
        if (mediaDrm.mNativeContext == 0) {
            Log.w(TAG, "MediaDrm went away with unhandled events");
        } else {
            listenerWithExecutor.mConsumer.accept(new ListenerArgs(i, i2, bArr, bArr2, j, list, z));
        }
    }

    public byte[] openSession() throws ResourceBusyException, NotProvisionedException {
        return openSession(getMaxSecurityLevel());
    }

    public byte[] openSession(int i) throws ResourceBusyException, NotProvisionedException {
        byte[] bArrOpenSessionNative = openSessionNative(i);
        this.mPlaybackComponentMap.put(ByteBuffer.wrap(bArrOpenSessionNative), new PlaybackComponent(bArrOpenSessionNative));
        return bArrOpenSessionNative;
    }

    public void closeSession(byte[] bArr) {
        closeSessionNative(bArr);
        this.mPlaybackComponentMap.remove(ByteBuffer.wrap(bArr));
    }

    public static final class KeyRequest {
        public static final int REQUEST_TYPE_INITIAL = 0;
        public static final int REQUEST_TYPE_NONE = 3;
        public static final int REQUEST_TYPE_RELEASE = 2;
        public static final int REQUEST_TYPE_RENEWAL = 1;
        public static final int REQUEST_TYPE_UPDATE = 4;
        private byte[] mData;
        private String mDefaultUrl;
        private int mRequestType;

        @Retention(RetentionPolicy.SOURCE)
        public @interface RequestType {
        }

        KeyRequest() {
        }

        public byte[] getData() {
            byte[] bArr = this.mData;
            if (bArr != null) {
                return bArr;
            }
            throw new RuntimeException("KeyRequest is not initialized");
        }

        public String getDefaultUrl() {
            String str = this.mDefaultUrl;
            if (str != null) {
                return str;
            }
            throw new RuntimeException("KeyRequest is not initialized");
        }

        public int getRequestType() {
            return this.mRequestType;
        }
    }

    public KeyRequest getKeyRequest(byte[] bArr, byte[] bArr2, String str, int i, HashMap<String, String> map) throws NotProvisionedException {
        HashMap<String, String> map2;
        if (map == null) {
            map2 = new HashMap<>();
        } else {
            map2 = new HashMap<>(map);
        }
        byte[] newestAvailablePackageCertificateRawBytes = getNewestAvailablePackageCertificateRawBytes();
        byte[] digestBytes = newestAvailablePackageCertificateRawBytes != null ? getDigestBytes(newestAvailablePackageCertificateRawBytes, "SHA-256") : null;
        if (digestBytes != null) {
            map2.put("package_certificate_hash_bytes", Base64.getEncoder().encodeToString(digestBytes));
        }
        return getKeyRequestNative(bArr, bArr2, str, i, map2);
    }

    private byte[] getNewestAvailablePackageCertificateRawBytes() {
        PackageInfo packageInfo;
        Application applicationCurrentApplication = ActivityThread.currentApplication();
        if (applicationCurrentApplication == null) {
            Log.w(TAG, "pkg cert: Application is null");
            return null;
        }
        PackageManager packageManager = applicationCurrentApplication.getPackageManager();
        if (packageManager == null) {
            Log.w(TAG, "pkg cert: PackageManager is null");
            return null;
        }
        try {
            packageInfo = packageManager.getPackageInfo(this.mAppPackageName, 134217728);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, this.mAppPackageName, e);
            packageInfo = null;
        }
        if (packageInfo == null || packageInfo.signingInfo == null) {
            Log.w(TAG, "pkg cert: PackageInfo or SigningInfo is null");
            return null;
        }
        Signature[] apkContentsSigners = packageInfo.signingInfo.getApkContentsSigners();
        if (apkContentsSigners != null && apkContentsSigners.length == 1) {
            return apkContentsSigners[0].toByteArray();
        }
        Log.w(TAG, "pkg cert: " + apkContentsSigners.length + " signers");
        return null;
    }

    private static byte[] getDigestBytes(byte[] bArr, String str) {
        try {
            return MessageDigest.getInstance(str).digest(bArr);
        } catch (NoSuchAlgorithmException e) {
            Log.w(TAG, str, e);
            return null;
        }
    }

    public static final class ProvisionRequest {
        private byte[] mData;
        private String mDefaultUrl;

        ProvisionRequest() {
        }

        public byte[] getData() {
            byte[] bArr = this.mData;
            if (bArr != null) {
                return bArr;
            }
            throw new RuntimeException("ProvisionRequest is not initialized");
        }

        public String getDefaultUrl() {
            String str = this.mDefaultUrl;
            if (str != null) {
                return str;
            }
            throw new RuntimeException("ProvisionRequest is not initialized");
        }
    }

    public ProvisionRequest getProvisionRequest() {
        return getProvisionRequestNative(0, "");
    }

    public void provideProvisionResponse(byte[] bArr) throws DeniedByServerException {
        provideProvisionResponseNative(bArr);
    }

    public void releaseAllSecureStops() {
        removeAllSecureStops();
    }

    public PersistableBundle getMetrics() {
        return getMetricsNative();
    }

    public final class CryptoSession {
        private byte[] mSessionId;

        CryptoSession(byte[] bArr, String str, String str2) {
            this.mSessionId = bArr;
            MediaDrm.setCipherAlgorithmNative(MediaDrm.this, bArr, str);
            MediaDrm.setMacAlgorithmNative(MediaDrm.this, bArr, str2);
        }

        public byte[] encrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
            return MediaDrm.encryptNative(MediaDrm.this, this.mSessionId, bArr, bArr2, bArr3);
        }

        public byte[] decrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
            return MediaDrm.decryptNative(MediaDrm.this, this.mSessionId, bArr, bArr2, bArr3);
        }

        public byte[] sign(byte[] bArr, byte[] bArr2) {
            return MediaDrm.signNative(MediaDrm.this, this.mSessionId, bArr, bArr2);
        }

        public boolean verify(byte[] bArr, byte[] bArr2, byte[] bArr3) {
            return MediaDrm.verifyNative(MediaDrm.this, this.mSessionId, bArr, bArr2, bArr3);
        }
    }

    public CryptoSession getCryptoSession(byte[] bArr, String str, String str2) {
        return new CryptoSession(bArr, str, str2);
    }

    public static final class CertificateRequest {
        private byte[] mData;
        private String mDefaultUrl;

        CertificateRequest(byte[] bArr, String str) {
            this.mData = bArr;
            this.mDefaultUrl = str;
        }

        public byte[] getData() {
            return this.mData;
        }

        public String getDefaultUrl() {
            return this.mDefaultUrl;
        }
    }

    public CertificateRequest getCertificateRequest(int i, String str) {
        ProvisionRequest provisionRequestNative = getProvisionRequestNative(i, str);
        return new CertificateRequest(provisionRequestNative.getData(), provisionRequestNative.getDefaultUrl());
    }

    public static final class Certificate {
        private byte[] mCertificateData;
        private byte[] mWrappedKey;

        Certificate() {
        }

        public byte[] getWrappedPrivateKey() {
            byte[] bArr = this.mWrappedKey;
            if (bArr != null) {
                return bArr;
            }
            throw new RuntimeException("Certificate is not initialized");
        }

        public byte[] getContent() {
            byte[] bArr = this.mCertificateData;
            if (bArr != null) {
                return bArr;
            }
            throw new RuntimeException("Certificate is not initialized");
        }
    }

    public Certificate provideCertificateResponse(byte[] bArr) throws DeniedByServerException {
        return provideProvisionResponseNative(bArr);
    }

    public byte[] signRSA(byte[] bArr, String str, byte[] bArr2, byte[] bArr3) {
        return signRSANative(this, bArr, str, bArr2, bArr3);
    }

    public boolean requiresSecureDecoder(String str) {
        return requiresSecureDecoder(str, getMaxSecurityLevel());
    }

    protected void finalize() throws Throwable {
        try {
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            release();
        } finally {
            super.finalize();
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        release();
    }

    @Deprecated
    public void release() {
        this.mCloseGuard.close();
        if (this.mClosed.compareAndSet(false, true)) {
            native_release();
            this.mPlaybackComponentMap.clear();
        }
        try {
            if (this.mAppPackageName.contains("rkpdapp")) {
                return;
            }
            this.mContext.unbindService(this.mConnection);
        } catch (IllegalArgumentException unused) {
            Log.e(TAG, "Object Already unbinded ");
        }
    }

    static {
        System.loadLibrary("media_jni");
        native_init();
    }

    public static final class MetricsConstants {
        public static final String CLOSE_SESSION_ERROR_COUNT = "drm.mediadrm.close_session.error.count";
        public static final String CLOSE_SESSION_ERROR_LIST = "drm.mediadrm.close_session.error.list";
        public static final String CLOSE_SESSION_OK_COUNT = "drm.mediadrm.close_session.ok.count";
        public static final String EVENT_KEY_EXPIRED_COUNT = "drm.mediadrm.event.KEY_EXPIRED.count";
        public static final String EVENT_KEY_NEEDED_COUNT = "drm.mediadrm.event.KEY_NEEDED.count";
        public static final String EVENT_PROVISION_REQUIRED_COUNT = "drm.mediadrm.event.PROVISION_REQUIRED.count";
        public static final String EVENT_SESSION_RECLAIMED_COUNT = "drm.mediadrm.event.SESSION_RECLAIMED.count";
        public static final String EVENT_VENDOR_DEFINED_COUNT = "drm.mediadrm.event.VENDOR_DEFINED.count";
        public static final String GET_DEVICE_UNIQUE_ID_ERROR_COUNT = "drm.mediadrm.get_device_unique_id.error.count";
        public static final String GET_DEVICE_UNIQUE_ID_ERROR_LIST = "drm.mediadrm.get_device_unique_id.error.list";
        public static final String GET_DEVICE_UNIQUE_ID_OK_COUNT = "drm.mediadrm.get_device_unique_id.ok.count";
        public static final String GET_KEY_REQUEST_ERROR_COUNT = "drm.mediadrm.get_key_request.error.count";
        public static final String GET_KEY_REQUEST_ERROR_LIST = "drm.mediadrm.get_key_request.error.list";
        public static final String GET_KEY_REQUEST_OK_COUNT = "drm.mediadrm.get_key_request.ok.count";
        public static final String GET_KEY_REQUEST_OK_TIME_MICROS = "drm.mediadrm.get_key_request.ok.average_time_micros";
        public static final String GET_PROVISION_REQUEST_ERROR_COUNT = "drm.mediadrm.get_provision_request.error.count";
        public static final String GET_PROVISION_REQUEST_ERROR_LIST = "drm.mediadrm.get_provision_request.error.list";
        public static final String GET_PROVISION_REQUEST_OK_COUNT = "drm.mediadrm.get_provision_request.ok.count";
        public static final String KEY_STATUS_EXPIRED_COUNT = "drm.mediadrm.key_status.EXPIRED.count";
        public static final String KEY_STATUS_INTERNAL_ERROR_COUNT = "drm.mediadrm.key_status.INTERNAL_ERROR.count";
        public static final String KEY_STATUS_OUTPUT_NOT_ALLOWED_COUNT = "drm.mediadrm.key_status_change.OUTPUT_NOT_ALLOWED.count";
        public static final String KEY_STATUS_PENDING_COUNT = "drm.mediadrm.key_status_change.PENDING.count";
        public static final String KEY_STATUS_USABLE_COUNT = "drm.mediadrm.key_status_change.USABLE.count";
        public static final String OPEN_SESSION_ERROR_COUNT = "drm.mediadrm.open_session.error.count";
        public static final String OPEN_SESSION_ERROR_LIST = "drm.mediadrm.open_session.error.list";
        public static final String OPEN_SESSION_OK_COUNT = "drm.mediadrm.open_session.ok.count";
        public static final String PROVIDE_KEY_RESPONSE_ERROR_COUNT = "drm.mediadrm.provide_key_response.error.count";
        public static final String PROVIDE_KEY_RESPONSE_ERROR_LIST = "drm.mediadrm.provide_key_response.error.list";
        public static final String PROVIDE_KEY_RESPONSE_OK_COUNT = "drm.mediadrm.provide_key_response.ok.count";
        public static final String PROVIDE_KEY_RESPONSE_OK_TIME_MICROS = "drm.mediadrm.provide_key_response.ok.average_time_micros";
        public static final String PROVIDE_PROVISION_RESPONSE_ERROR_COUNT = "drm.mediadrm.provide_provision_response.error.count";
        public static final String PROVIDE_PROVISION_RESPONSE_ERROR_LIST = "drm.mediadrm.provide_provision_response.error.list";
        public static final String PROVIDE_PROVISION_RESPONSE_OK_COUNT = "drm.mediadrm.provide_provision_response.ok.count";
        public static final String SESSION_END_TIMES_MS = "drm.mediadrm.session_end_times_ms";
        public static final String SESSION_START_TIMES_MS = "drm.mediadrm.session_start_times_ms";

        private MetricsConstants() {
        }
    }

    public PlaybackComponent getPlaybackComponent(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("sessionId is null");
        }
        return this.mPlaybackComponentMap.get(ByteBuffer.wrap(bArr));
    }

    public final class PlaybackComponent {
        private LogSessionId mLogSessionId = LogSessionId.LOG_SESSION_ID_NONE;
        private final byte[] mSessionId;

        public PlaybackComponent(byte[] bArr) {
            this.mSessionId = bArr;
        }

        public void setLogSessionId(LogSessionId logSessionId) {
            Objects.requireNonNull(logSessionId);
            if (logSessionId.getStringId() == null) {
                throw new IllegalArgumentException("playbackId is null");
            }
            MediaDrm.this.setPlaybackId(this.mSessionId, logSessionId.getStringId());
            this.mLogSessionId = logSessionId;
        }

        public LogSessionId getLogSessionId() {
            return this.mLogSessionId;
        }
    }

    public static final class LogMessage {
        private final String message;
        private final int priority;
        private final long timestampMillis;

        public final long getTimestampMillis() {
            return this.timestampMillis;
        }

        public final int getPriority() {
            return this.priority;
        }

        public final String getMessage() {
            return this.message;
        }

        private LogMessage(long j, int i, String str) {
            this.timestampMillis = j;
            if (i < 2 || i > 7) {
                throw new IllegalArgumentException("invalid log priority " + i);
            }
            this.priority = i;
            this.message = str;
        }

        private char logPriorityChar() {
            switch (this.priority) {
                case 2:
                    return 'V';
                case 3:
                    return 'D';
                case 4:
                    return 'I';
                case 5:
                    return 'W';
                case 6:
                    return DateFormat.DAY;
                case 7:
                    return 'F';
                default:
                    return 'U';
            }
        }

        public String toString() {
            return String.format("LogMessage{%s %c %s}", Instant.ofEpochMilli(this.timestampMillis), Character.valueOf(logPriorityChar()), this.message);
        }
    }
}
