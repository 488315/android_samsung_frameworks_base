package android.hardware.camera2;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.om.OverlayManagerExt$$ExternalSyntheticLambda4;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.extension.IAdvancedExtenderImpl;
import android.hardware.camera2.extension.ICameraExtensionsProxyService;
import android.hardware.camera2.extension.IImageCaptureExtenderImpl;
import android.hardware.camera2.extension.IInitializeSessionCallback;
import android.hardware.camera2.extension.IPreviewExtenderImpl;
import android.hardware.camera2.extension.LatencyRange;
import android.hardware.camera2.extension.SizeList;
import android.hardware.camera2.impl.CameraExtensionUtils;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.ConditionVariable;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.util.Range;
import android.util.Size;
import android.view.SurfaceView;
import com.android.internal.camera.flags.Flags;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

/* loaded from: classes2.dex */
public final class CameraExtensionCharacteristics {
    public static final int EXTENSION_AUTOMATIC = 0;

    @Deprecated
    public static final int EXTENSION_BEAUTY = 1;
    public static final int EXTENSION_BOKEH = 2;
    public static final int EXTENSION_FACE_RETOUCH = 1;
    public static final int EXTENSION_HDR = 3;
    public static final int EXTENSION_NIGHT = 4;
    public static final int NON_PROCESSING_INPUT_FORMAT = 34;
    public static final int PROCESSING_INPUT_FORMAT = 35;
    private static final String TAG = "CameraExtensionCharacteristics";
    private final String mCameraId;
    private final Map<String, CameraCharacteristics> mCharacteristicsMap;
    private final Map<String, CameraMetadataNative> mCharacteristicsMapNative;
    private final Context mContext;
    private static final int[] EXTENSION_LIST = {0, 1, 2, 3, 4};
    private static final List<CameraCharacteristics.Key> SUPPORTED_SYNTHETIC_CAMERA_CHARACTERISTICS = Arrays.asList(CameraCharacteristics.REQUEST_AVAILABLE_DYNAMIC_RANGE_PROFILES, CameraCharacteristics.REQUEST_AVAILABLE_COLOR_SPACE_PROFILES);

    @Retention(RetentionPolicy.SOURCE)
    public @interface Extension {
    }

    public CameraExtensionCharacteristics(Context context, String str, Map<String, CameraCharacteristics> map) {
        this.mContext = context;
        this.mCameraId = str;
        this.mCharacteristicsMap = map;
        this.mCharacteristicsMapNative = CameraExtensionUtils.getCharacteristicsMapNative(map);
    }

    private static ArrayList<Size> getSupportedSizes(List<SizeList> list, Integer num) {
        ArrayList<Size> arrayList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            Iterator<SizeList> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                SizeList next = it.next();
                if (next.format == num.intValue() && !next.sizes.isEmpty()) {
                    for (android.hardware.camera2.extension.Size size : next.sizes) {
                        arrayList.add(new Size(size.width, size.height));
                    }
                }
            }
        }
        return arrayList;
    }

    private static List<Size> generateSupportedSizes(List<SizeList> list, Integer num, StreamConfigurationMap streamConfigurationMap) {
        ArrayList<Size> supportedSizes = getSupportedSizes(list, num);
        if (num.intValue() == 256 || num.intValue() == 35 || num.intValue() == 34) {
            Size[] outputSizes = streamConfigurationMap.getOutputSizes(num.intValue());
            if (supportedSizes.isEmpty() && outputSizes != null) {
                supportedSizes.addAll(Arrays.asList(outputSizes));
            }
        }
        return supportedSizes;
    }

    private static List<Size> generateJpegSupportedSizes(List<SizeList> list, StreamConfigurationMap streamConfigurationMap) {
        HashSet hashSet;
        ArrayList<Size> supportedSizes = getSupportedSizes(list, 35);
        if (supportedSizes.isEmpty()) {
            hashSet = new HashSet(Arrays.asList(streamConfigurationMap.getOutputSizes(35)));
        } else {
            hashSet = new HashSet(supportedSizes);
        }
        hashSet.retainAll(new HashSet(Arrays.asList(streamConfigurationMap.getOutputSizes(256))));
        return new ArrayList(hashSet);
    }

    private static final class CameraExtensionManagerGlobal {
        private static final int FALLBACK_PACKAGE_NAME = 17040230;
        private static final int FALLBACK_SERVICE_NAME = 17040231;
        private static final CameraExtensionManagerGlobal GLOBAL_CAMERA_MANAGER = new CameraExtensionManagerGlobal();
        private static final String PROXY_PACKAGE_NAME = "com.android.cameraextensions";
        private static final String PROXY_SERVICE_NAME = "com.android.cameraextensions.CameraExtensionsProxyService";
        private static final String TAG = "CameraExtensionManagerGlobal";
        private final Object mLock = new Object();
        private final int PROXY_SERVICE_DELAY_MS = 2000;
        private ExtensionConnectionManager mConnectionManager = new ExtensionConnectionManager(this);
        private boolean mPermissionForFallbackEnabled = false;
        private boolean mIsFallbackEnabled = false;

        private CameraExtensionManagerGlobal() {
        }

        public static CameraExtensionManagerGlobal get() {
            return GLOBAL_CAMERA_MANAGER;
        }

        private void releaseProxyConnectionLocked(Context context, int i) {
            if (this.mConnectionManager.getConnection(i) != null) {
                context.unbindService(this.mConnectionManager.getConnection(i));
                this.mConnectionManager.setConnection(i, null);
                this.mConnectionManager.setProxy(i, null);
                this.mConnectionManager.resetConnectionCount(i);
            }
        }

        private void connectToProxyLocked(Context context, final int i, boolean z) {
            if (this.mConnectionManager.getConnection(i) == null) {
                Intent intent = new Intent();
                intent.setClassName(PROXY_PACKAGE_NAME, PROXY_SERVICE_NAME);
                String str = SystemProperties.get("ro.vendor.camera.extensions.package");
                String str2 = SystemProperties.get("ro.vendor.camera.extensions.service");
                if (!str.isEmpty() && !str2.isEmpty()) {
                    Log.v(TAG, "Choosing the vendor camera extensions proxy package: " + str);
                    Log.v(TAG, "Choosing the vendor camera extensions proxy service: " + str2);
                    intent.setClassName(str, str2);
                }
                if (z) {
                    String string = context.getResources().getString(17040230);
                    String string2 = context.getResources().getString(17040231);
                    if (!string.isEmpty() && !string2.isEmpty()) {
                        Log.v(TAG, "Choosing the fallback software implementation package: " + string);
                        Log.v(TAG, "Choosing the fallback software implementation service: " + string2);
                        intent.setClassName(string, string2);
                        this.mIsFallbackEnabled = true;
                    }
                }
                final InitializerFuture initializerFuture = new InitializerFuture();
                ServiceConnection serviceConnection = new ServiceConnection() { // from class: android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.1
                    @Override // android.content.ServiceConnection
                    public void onServiceDisconnected(ComponentName componentName) {
                        CameraExtensionManagerGlobal.this.mConnectionManager.setConnection(i, null);
                        CameraExtensionManagerGlobal.this.mConnectionManager.setProxy(i, null);
                    }

                    @Override // android.content.ServiceConnection
                    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        CameraExtensionManagerGlobal.this.mConnectionManager.setProxy(i, ICameraExtensionsProxyService.Stub.asInterface(iBinder));
                        if (CameraExtensionManagerGlobal.this.mConnectionManager.getProxy(i) == null) {
                            throw new IllegalStateException("Camera Proxy service is null");
                        }
                        try {
                            CameraExtensionManagerGlobal.this.mConnectionManager.setAdvancedExtensionsSupported(i, CameraExtensionManagerGlobal.this.mConnectionManager.getProxy(i).advancedExtensionsSupported());
                        } catch (RemoteException unused) {
                            Log.e(CameraExtensionManagerGlobal.TAG, "Remote IPC failed!");
                        }
                        initializerFuture.setStatus(true);
                    }
                };
                context.bindService(intent, 1073741897, AsyncTask.THREAD_POOL_EXECUTOR, serviceConnection);
                this.mConnectionManager.setConnection(i, serviceConnection);
                try {
                    initializerFuture.get(2000L, TimeUnit.MILLISECONDS);
                } catch (TimeoutException unused) {
                    Log.e(TAG, "Timed out while initializing proxy service!");
                }
            }
        }

        private static class InitializerFuture implements Future<Boolean> {
            ConditionVariable mCondVar;
            private volatile Boolean mStatus;

            @Override // java.util.concurrent.Future
            public boolean cancel(boolean z) {
                return false;
            }

            @Override // java.util.concurrent.Future
            public boolean isCancelled() {
                return false;
            }

            private InitializerFuture() {
                this.mCondVar = new ConditionVariable(false);
            }

            public void setStatus(boolean z) {
                this.mStatus = Boolean.valueOf(z);
                this.mCondVar.open();
            }

            @Override // java.util.concurrent.Future
            public boolean isDone() {
                return this.mStatus != null;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Future
            public Boolean get() {
                this.mCondVar.block();
                return this.mStatus;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Future
            public Boolean get(long j, TimeUnit timeUnit) throws TimeoutException {
                if (!this.mCondVar.block(timeUnit.convert(j, TimeUnit.MILLISECONDS))) {
                    throw new TimeoutException("Failed to receive status after " + j + " " + timeUnit);
                }
                if (this.mStatus == null) {
                    throw new AssertionError();
                }
                return this.mStatus;
            }
        }

        public boolean registerClientHelper(Context context, IBinder iBinder, int i, boolean z) {
            boolean z2;
            synchronized (this.mLock) {
                connectToProxyLocked(context, i, z);
                boolean z3 = false;
                if (this.mConnectionManager.getProxy(i) == null) {
                    return false;
                }
                this.mConnectionManager.incrementConnectionCount(i);
                try {
                    z2 = this.mConnectionManager.getProxy(i).registerClient(iBinder);
                } catch (RemoteException unused) {
                    Log.e(TAG, "Failed to initialize extension! Extension service does  not respond!");
                    z2 = false;
                }
                if (!z2) {
                    this.mConnectionManager.decrementConnectionCount(i);
                }
                if (this.mConnectionManager.getConnectionCount(i) <= 0) {
                    releaseProxyConnectionLocked(context, i);
                }
                if (z2 && z) {
                    try {
                        if (this.mIsFallbackEnabled) {
                            try {
                                initializeSession(new InitializeSessionHandler(context), i);
                                z3 = this.mPermissionForFallbackEnabled;
                            } catch (RemoteException unused2) {
                                Log.e(TAG, "Failed to initialize extension. Extension service does not respond!");
                            }
                            return z3;
                        }
                    } finally {
                        releaseSession(i);
                    }
                }
                z3 = z2;
                return z3;
            }
        }

        public boolean registerClient(Context context, IBinder iBinder, int i, String str, Map<String, CameraMetadataNative> map) {
            if (!SystemProperties.getBoolean("ro.camerax.extensions.enabled", false)) {
                Log.v(TAG, "Disabled camera extension property!");
                return false;
            }
            boolean registerClientHelper = registerClientHelper(context, iBinder, i, false);
            if ((registerClientHelper && this.mConnectionManager.getProxy(i) != null && Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.CAMERA_EXTENSIONS_FALLBACK, 1) == 1) ? CameraExtensionCharacteristics.isExtensionSupported(str, i, map) : true) {
                return registerClientHelper;
            }
            unregisterClient(context, iBinder, i);
            return registerClientHelper(context, iBinder, i, true);
        }

        public void unregisterClient(Context context, IBinder iBinder, int i) {
            synchronized (this.mLock) {
                try {
                    if (this.mConnectionManager.getProxy(i) != null) {
                        try {
                            this.mConnectionManager.getProxy(i).unregisterClient(iBinder);
                        } catch (RemoteException unused) {
                            Log.e(TAG, "Failed to de-initialize extension! Extension service does not respond!");
                            this.mConnectionManager.decrementConnectionCount(i);
                            if (this.mConnectionManager.getConnectionCount(i) <= 0) {
                            }
                        }
                    }
                } finally {
                    this.mConnectionManager.decrementConnectionCount(i);
                    if (this.mConnectionManager.getConnectionCount(i) <= 0) {
                        releaseProxyConnectionLocked(context, i);
                    }
                }
            }
        }

        public void initializeSession(IInitializeSessionCallback iInitializeSessionCallback, int i) throws RemoteException {
            synchronized (this.mLock) {
                if (this.mConnectionManager.getProxy(i) != null && !this.mConnectionManager.isSessionInitialized()) {
                    this.mConnectionManager.getProxy(i).initializeSession(iInitializeSessionCallback);
                    this.mConnectionManager.setSessionInitialized(true);
                } else {
                    iInitializeSessionCallback.onFailure();
                }
            }
        }

        public void releaseSession(int i) {
            synchronized (this.mLock) {
                if (this.mConnectionManager.getProxy(i) != null) {
                    try {
                        this.mConnectionManager.getProxy(i).releaseSession();
                        this.mConnectionManager.setSessionInitialized(false);
                        this.mPermissionForFallbackEnabled = false;
                    } catch (RemoteException unused) {
                        Log.e(TAG, "Failed to release session! Extension service does not respond!");
                    }
                }
            }
        }

        public boolean areAdvancedExtensionsSupported(int i) {
            return this.mConnectionManager.areAdvancedExtensionsSupported(i);
        }

        public IPreviewExtenderImpl initializePreviewExtension(int i) throws RemoteException {
            synchronized (this.mLock) {
                if (this.mConnectionManager.getProxy(i) == null) {
                    return null;
                }
                return this.mConnectionManager.getProxy(i).initializePreviewExtension(i);
            }
        }

        public IImageCaptureExtenderImpl initializeImageExtension(int i) throws RemoteException {
            synchronized (this.mLock) {
                if (this.mConnectionManager.getProxy(i) == null) {
                    return null;
                }
                return this.mConnectionManager.getProxy(i).initializeImageExtension(i);
            }
        }

        public IAdvancedExtenderImpl initializeAdvancedExtension(int i) throws RemoteException {
            synchronized (this.mLock) {
                if (this.mConnectionManager.getProxy(i) == null) {
                    return null;
                }
                return this.mConnectionManager.getProxy(i).initializeAdvancedExtension(i);
            }
        }

        private class InitializeSessionHandler extends IInitializeSessionCallback.Stub {
            private Context mContext;

            public InitializeSessionHandler(Context context) {
                this.mContext = context;
            }

            @Override // android.hardware.camera2.extension.IInitializeSessionCallback
            public void onSuccess() {
                String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid());
                String string = this.mContext.getResources().getString(17040230);
                if (string.isEmpty()) {
                    return;
                }
                Stream stream = Arrays.stream(packagesForUid);
                Objects.requireNonNull(string);
                if (stream.anyMatch(new OverlayManagerExt$$ExternalSyntheticLambda4(string))) {
                    String[] strArr = {Manifest.permission.SYSTEM_CAMERA, Manifest.permission.CAMERA};
                    boolean z = true;
                    for (int i = 0; i < 2; i++) {
                        String str = strArr[i];
                        int checkPermission = this.mContext.checkPermission(str, Binder.getCallingPid(), Binder.getCallingUid());
                        if (checkPermission != 0) {
                            Log.w(CameraExtensionManagerGlobal.TAG, str + " permission not granted for " + string + ", permission check result: " + checkPermission);
                            z = false;
                        }
                    }
                    CameraExtensionManagerGlobal.this.mPermissionForFallbackEnabled = z;
                }
            }

            @Override // android.hardware.camera2.extension.IInitializeSessionCallback
            public void onFailure() {
                Log.e(CameraExtensionManagerGlobal.TAG, "Failed to initialize proxy service session!");
            }
        }

        private class ExtensionConnectionManager {
            private Map<Integer, ExtensionConnection> mConnections = new HashMap();
            private boolean mSessionInitialized = false;

            public ExtensionConnectionManager(CameraExtensionManagerGlobal cameraExtensionManagerGlobal) {
                IntArray intArray = new IntArray(CameraExtensionCharacteristics.EXTENSION_LIST.length);
                intArray.addAll(CameraExtensionCharacteristics.EXTENSION_LIST);
                for (int i : intArray.toArray()) {
                    this.mConnections.put(Integer.valueOf(i), new ExtensionConnection());
                }
            }

            public ICameraExtensionsProxyService getProxy(int i) {
                return this.mConnections.get(Integer.valueOf(i)).mProxy;
            }

            public ServiceConnection getConnection(int i) {
                return this.mConnections.get(Integer.valueOf(i)).mConnection;
            }

            public int getConnectionCount(int i) {
                return this.mConnections.get(Integer.valueOf(i)).mConnectionCount;
            }

            public boolean areAdvancedExtensionsSupported(int i) {
                return this.mConnections.get(Integer.valueOf(i)).mSupportsAdvancedExtensions;
            }

            public boolean isSessionInitialized() {
                return this.mSessionInitialized;
            }

            public void setProxy(int i, ICameraExtensionsProxyService iCameraExtensionsProxyService) {
                this.mConnections.get(Integer.valueOf(i)).mProxy = iCameraExtensionsProxyService;
            }

            public void setConnection(int i, ServiceConnection serviceConnection) {
                this.mConnections.get(Integer.valueOf(i)).mConnection = serviceConnection;
            }

            public void incrementConnectionCount(int i) {
                this.mConnections.get(Integer.valueOf(i)).mConnectionCount++;
            }

            public void decrementConnectionCount(int i) {
                ExtensionConnection extensionConnection = this.mConnections.get(Integer.valueOf(i));
                extensionConnection.mConnectionCount--;
            }

            public void resetConnectionCount(int i) {
                this.mConnections.get(Integer.valueOf(i)).mConnectionCount = 0;
            }

            public void setAdvancedExtensionsSupported(int i, boolean z) {
                this.mConnections.get(Integer.valueOf(i)).mSupportsAdvancedExtensions = z;
            }

            public void setSessionInitialized(boolean z) {
                this.mSessionInitialized = z;
            }

            private class ExtensionConnection {
                public ServiceConnection mConnection;
                public int mConnectionCount;
                public ICameraExtensionsProxyService mProxy;
                public boolean mSupportsAdvancedExtensions;

                private ExtensionConnection(ExtensionConnectionManager extensionConnectionManager) {
                    this.mProxy = null;
                    this.mConnection = null;
                    this.mConnectionCount = 0;
                    this.mSupportsAdvancedExtensions = false;
                }
            }
        }
    }

    public static boolean registerClient(Context context, IBinder iBinder, int i, String str, Map<String, CameraMetadataNative> map) {
        return CameraExtensionManagerGlobal.get().registerClient(context, iBinder, i, str, map);
    }

    public static void unregisterClient(Context context, IBinder iBinder, int i) {
        CameraExtensionManagerGlobal.get().unregisterClient(context, iBinder, i);
    }

    public static void initializeSession(IInitializeSessionCallback iInitializeSessionCallback, int i) throws RemoteException {
        CameraExtensionManagerGlobal.get().initializeSession(iInitializeSessionCallback, i);
    }

    public static void releaseSession(int i) {
        CameraExtensionManagerGlobal.get().releaseSession(i);
    }

    public static boolean areAdvancedExtensionsSupported(int i) {
        return CameraExtensionManagerGlobal.get().areAdvancedExtensionsSupported(i);
    }

    public static boolean isExtensionSupported(String str, int i, Map<String, CameraMetadataNative> map) {
        if (areAdvancedExtensionsSupported(i)) {
            try {
                return initializeAdvancedExtension(i).isExtensionAvailable(str, map);
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to query extension availability! Extension service does not respond!");
                return false;
            }
        }
        try {
            Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
            try {
                if (initializeExtension.first.isExtensionAvailable(str, map.get(str))) {
                    if (initializeExtension.second.isExtensionAvailable(str, map.get(str))) {
                        return true;
                    }
                }
                return false;
            } catch (RemoteException unused2) {
                Log.e(TAG, "Failed to query extension availability! Extension service does not respond!");
                return false;
            }
        } catch (IllegalArgumentException unused3) {
        }
    }

    public static IAdvancedExtenderImpl initializeAdvancedExtension(int i) {
        try {
            IAdvancedExtenderImpl initializeAdvancedExtension = CameraExtensionManagerGlobal.get().initializeAdvancedExtension(i);
            if (initializeAdvancedExtension != null) {
                return initializeAdvancedExtension;
            }
            throw new IllegalArgumentException("Unknown extension: " + i);
        } catch (RemoteException unused) {
            throw new IllegalStateException("Failed to initialize extension: " + i);
        }
    }

    public static Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> initializeExtension(int i) {
        try {
            IPreviewExtenderImpl initializePreviewExtension = CameraExtensionManagerGlobal.get().initializePreviewExtension(i);
            IImageCaptureExtenderImpl initializeImageExtension = CameraExtensionManagerGlobal.get().initializeImageExtension(i);
            if (initializeImageExtension == null || initializePreviewExtension == null) {
                throw new IllegalArgumentException("Unknown extension: " + i);
            }
            return new Pair<>(initializePreviewExtension, initializeImageExtension);
        } catch (RemoteException unused) {
            throw new IllegalStateException("Failed to initialize extension: " + i);
        }
    }

    private static <T> boolean isOutputSupportedFor(Class<T> cls) {
        Objects.requireNonNull(cls, "klass must not be null");
        return cls == SurfaceTexture.class || cls == SurfaceView.class;
    }

    public List<Integer> getSupportedExtensions() {
        ArrayList arrayList = new ArrayList();
        Binder binder = new Binder("CameraExtensionCharacteristics#getSupportedExtensions:" + this.mCameraId);
        int[] iArr = EXTENSION_LIST;
        IntArray intArray = new IntArray(iArr.length);
        intArray.addAll(iArr);
        for (int i : intArray.toArray()) {
            try {
                if (registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative) && isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                    arrayList.add(Integer.valueOf(i));
                }
                unregisterClient(this.mContext, binder, i);
            } catch (Throwable th) {
                unregisterClient(this.mContext, binder, i);
                throw th;
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public <T> T get(int i, CameraCharacteristics.Key<T> key) {
        Binder binder = new Binder("CameraExtensionCharacteristics#get:" + this.mCameraId);
        if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
            throw new IllegalArgumentException("Unsupported extensions");
        }
        try {
            try {
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to query the extension for the specified key! Extension service does not respond!");
            }
            if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                throw new IllegalArgumentException("Unsupported extension");
            }
            if (areAdvancedExtensionsSupported(i) && getKeys(i).contains(key)) {
                IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                CameraMetadataNative availableCharacteristicsKeyValues = initializeAdvancedExtension.getAvailableCharacteristicsKeyValues(this.mCameraId);
                if (availableCharacteristicsKeyValues == null) {
                    return null;
                }
                return (T) new CameraCharacteristics(availableCharacteristicsKeyValues).get(key);
            }
            return null;
        } finally {
            unregisterClient(this.mContext, binder, i);
        }
    }

    public Set<CameraCharacteristics.Key> getKeys(int i) {
        Binder binder = new Binder("CameraExtensionCharacteristics#getKeys:" + this.mCameraId);
        if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
            throw new IllegalArgumentException("Unsupported extensions");
        }
        HashSet hashSet = new HashSet();
        try {
            try {
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to query the extension for all available keys! Extension service does not respond!");
            }
            if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                throw new IllegalArgumentException("Unsupported extension");
            }
            if (areAdvancedExtensionsSupported(i)) {
                IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                CameraMetadataNative availableCharacteristicsKeyValues = initializeAdvancedExtension.getAvailableCharacteristicsKeyValues(this.mCameraId);
                if (availableCharacteristicsKeyValues == null) {
                    return Collections.EMPTY_SET;
                }
                int[] iArr = (int[]) availableCharacteristicsKeyValues.get(CameraCharacteristics.REQUEST_AVAILABLE_CHARACTERISTICS_KEYS);
                if (iArr == null) {
                    throw new AssertionError("android.request.availableCharacteristicsKeys must be non-null in the characteristics");
                }
                CameraCharacteristics cameraCharacteristics = new CameraCharacteristics(availableCharacteristicsKeyValues);
                hashSet.addAll(cameraCharacteristics.getAvailableKeyList(CameraCharacteristics.class, CameraCharacteristics.Key.class, iArr, false));
                for (CameraCharacteristics.Key key : SUPPORTED_SYNTHETIC_CAMERA_CHARACTERISTICS) {
                    if (cameraCharacteristics.get(key) != null) {
                        hashSet.add(key);
                    }
                }
            }
            unregisterClient(this.mContext, binder, i);
            return Collections.unmodifiableSet(hashSet);
        } finally {
            unregisterClient(this.mContext, binder, i);
        }
    }

    public boolean isPostviewAvailable(int i) {
        boolean isPostviewAvailable;
        Binder binder = new Binder("CameraExtensionCharacteristics#isPostviewAvailable:" + this.mCameraId);
        try {
            if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
                throw new IllegalArgumentException("Unsupported extensions");
            }
            try {
                if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                    throw new IllegalArgumentException("Unsupported extension");
                }
                if (areAdvancedExtensionsSupported(i)) {
                    IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                    initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                    isPostviewAvailable = initializeAdvancedExtension.isPostviewAvailable();
                } else {
                    Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
                    IImageCaptureExtenderImpl iImageCaptureExtenderImpl = initializeExtension.second;
                    String str = this.mCameraId;
                    iImageCaptureExtenderImpl.init(str, this.mCharacteristicsMapNative.get(str));
                    isPostviewAvailable = initializeExtension.second.isPostviewAvailable();
                }
                return isPostviewAvailable;
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to query the extension for postview availability! Extension service does not respond!");
                unregisterClient(this.mContext, binder, i);
                return false;
            }
        } finally {
            unregisterClient(this.mContext, binder, i);
        }
    }

    public List<Size> getPostviewSupportedSizes(int i, Size size, int i2) {
        List<Size> list;
        Binder binder = new Binder("CameraExtensionCharacteristics#getPostviewSupportedSizes:" + this.mCameraId);
        try {
            if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
                throw new IllegalArgumentException("Unsupported extensions");
            }
            try {
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to query the extension postview supported sizes! Extension service does not respond!");
                list = Collections.EMPTY_LIST;
            }
            if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                throw new IllegalArgumentException("Unsupported extension");
            }
            android.hardware.camera2.extension.Size size2 = new android.hardware.camera2.extension.Size();
            size2.width = size.getWidth();
            size2.height = size.getHeight();
            if (!areAdvancedExtensionsSupported(i)) {
                Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
                IImageCaptureExtenderImpl iImageCaptureExtenderImpl = initializeExtension.second;
                String str = this.mCameraId;
                iImageCaptureExtenderImpl.init(str, this.mCharacteristicsMapNative.get(str));
                if (initializeExtension.second.getCaptureProcessor() == null || !isPostviewAvailable(i)) {
                    throw new IllegalArgumentException("Extension does not support postview feature");
                }
                if (i2 == 35) {
                    list = getSupportedSizes(initializeExtension.second.getSupportedPostviewResolutions(size2), Integer.valueOf(i2));
                } else if (i2 == 256) {
                    list = getSupportedSizes(initializeExtension.second.getSupportedPostviewResolutions(size2), Integer.valueOf(i2));
                } else {
                    if (i2 != 4101 && i2 != 54 && (!Flags.depthJpegExtensions() || i2 != 1768253795)) {
                        throw new IllegalArgumentException("Unsupported format: " + i2);
                    }
                    list = new ArrayList<>();
                }
            } else {
                if (i2 != 35 && i2 != 54 && i2 != 256 && i2 != 4101 && i2 != 1768253795) {
                    throw new IllegalArgumentException("Unsupported format: " + i2);
                }
                IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                list = getSupportedSizes(initializeAdvancedExtension.getSupportedPostviewResolutions(size2), Integer.valueOf(i2));
            }
            return list;
        } finally {
            unregisterClient(this.mContext, binder, i);
        }
    }

    public <T> List<Size> getExtensionSupportedSizes(int i, Class<T> cls) {
        List<Size> arrayList;
        if (!isOutputSupportedFor(cls)) {
            return new ArrayList();
        }
        Binder binder = new Binder("CameraExtensionCharacteristics#getExtensionSupportedSizes:" + this.mCameraId);
        try {
            if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
                throw new IllegalArgumentException("Unsupported extensions");
            }
            try {
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to query the extension supported sizes! Extension service does not respond!");
                arrayList = new ArrayList<>();
            }
            if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                throw new IllegalArgumentException("Unsupported extension");
            }
            StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) this.mCharacteristicsMap.get(this.mCameraId).get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (areAdvancedExtensionsSupported(i)) {
                IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                arrayList = generateSupportedSizes(initializeAdvancedExtension.getSupportedPreviewOutputResolutions(this.mCameraId), 34, streamConfigurationMap);
            } else {
                Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
                IPreviewExtenderImpl iPreviewExtenderImpl = initializeExtension.first;
                String str = this.mCameraId;
                iPreviewExtenderImpl.init(str, this.mCharacteristicsMapNative.get(str));
                arrayList = generateSupportedSizes(initializeExtension.first.getSupportedResolutions(), 34, streamConfigurationMap);
            }
            return arrayList;
        } finally {
            unregisterClient(this.mContext, binder, i);
        }
    }

    public List<Size> getExtensionSupportedSizes(int i, int i2) {
        List<Size> arrayList;
        try {
            Binder binder = new Binder("CameraExtensionCharacteristics#getExtensionSupportedSizes:" + this.mCameraId);
            if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
                throw new IllegalArgumentException("Unsupported extensions");
            }
            try {
                if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                    throw new IllegalArgumentException("Unsupported extension");
                }
                StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) this.mCharacteristicsMap.get(this.mCameraId).get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (areAdvancedExtensionsSupported(i)) {
                    if (i2 != 35 && i2 != 54 && i2 != 256 && i2 != 4101 && i2 != 1768253795) {
                        throw new IllegalArgumentException("Unsupported format: " + i2);
                    }
                    IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                    initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                    arrayList = generateSupportedSizes(initializeAdvancedExtension.getSupportedCaptureOutputResolutions(this.mCameraId), Integer.valueOf(i2), streamConfigurationMap);
                } else if (i2 == 35) {
                    Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
                    IImageCaptureExtenderImpl iImageCaptureExtenderImpl = initializeExtension.second;
                    String str = this.mCameraId;
                    iImageCaptureExtenderImpl.init(str, this.mCharacteristicsMapNative.get(str));
                    if (initializeExtension.second.getCaptureProcessor() == null) {
                        arrayList = new ArrayList<>();
                    } else {
                        arrayList = generateSupportedSizes(initializeExtension.second.getSupportedResolutions(), Integer.valueOf(i2), streamConfigurationMap);
                    }
                } else if (i2 == 256) {
                    Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> initializeExtension2 = initializeExtension(i);
                    IImageCaptureExtenderImpl iImageCaptureExtenderImpl2 = initializeExtension2.second;
                    String str2 = this.mCameraId;
                    iImageCaptureExtenderImpl2.init(str2, this.mCharacteristicsMapNative.get(str2));
                    if (initializeExtension2.second.getCaptureProcessor() != null) {
                        arrayList = generateJpegSupportedSizes(initializeExtension2.second.getSupportedResolutions(), streamConfigurationMap);
                    } else {
                        arrayList = generateSupportedSizes(null, Integer.valueOf(i2), streamConfigurationMap);
                    }
                } else {
                    if (i2 != 4101 && i2 != 54 && (!Flags.depthJpegExtensions() || i2 != 1768253795)) {
                        throw new IllegalArgumentException("Unsupported format: " + i2);
                    }
                    arrayList = new ArrayList<>();
                }
                return arrayList;
            } finally {
                unregisterClient(this.mContext, binder, i);
            }
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to query the extension supported sizes! Extension service does not respond!");
            return new ArrayList();
        }
    }

    public Range<Long> getEstimatedCaptureLatencyRangeMillis(int i, Size size, int i2) {
        Range<Long> range;
        if (i2 != 35 && i2 != 54 && i2 != 256 && i2 != 4101 && i2 != 1768253795) {
            throw new IllegalArgumentException("Unsupported format: " + i2);
        }
        Binder binder = new Binder("CameraExtensionCharacteristics#getEstimatedCaptureLatencyRangeMillis:" + this.mCameraId);
        if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
            throw new IllegalArgumentException("Unsupported extensions");
        }
        try {
            try {
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to query the extension capture latency! Extension service does not respond!");
            }
            if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                throw new IllegalArgumentException("Unsupported extension");
            }
            android.hardware.camera2.extension.Size size2 = new android.hardware.camera2.extension.Size();
            size2.width = size.getWidth();
            size2.height = size.getHeight();
            if (areAdvancedExtensionsSupported(i)) {
                IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                LatencyRange estimatedCaptureLatencyRange = initializeAdvancedExtension.getEstimatedCaptureLatencyRange(this.mCameraId, size2, i2);
                if (estimatedCaptureLatencyRange != null) {
                    range = new Range<>(Long.valueOf(estimatedCaptureLatencyRange.min), Long.valueOf(estimatedCaptureLatencyRange.max));
                }
                return null;
            }
            Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
            IImageCaptureExtenderImpl iImageCaptureExtenderImpl = initializeExtension.second;
            String str = this.mCameraId;
            iImageCaptureExtenderImpl.init(str, this.mCharacteristicsMapNative.get(str));
            if ((i2 != 35 || initializeExtension.second.getCaptureProcessor() != null) && ((i2 != 256 || initializeExtension.second.getCaptureProcessor() == null) && i2 != 4101 && i2 != 54 && (!Flags.depthJpegExtensions() || i2 != 1768253795))) {
                LatencyRange estimatedCaptureLatencyRange2 = initializeExtension.second.getEstimatedCaptureLatencyRange(size2);
                if (estimatedCaptureLatencyRange2 != null) {
                    range = new Range<>(Long.valueOf(estimatedCaptureLatencyRange2.min), Long.valueOf(estimatedCaptureLatencyRange2.max));
                }
                return null;
            }
            return null;
            return range;
        } finally {
            unregisterClient(this.mContext, binder, i);
        }
    }

    public boolean isCaptureProcessProgressAvailable(int i) {
        boolean isCaptureProcessProgressAvailable;
        Binder binder = new Binder("CameraExtensionCharacteristics#isCaptureProcessProgressAvailable:" + this.mCameraId);
        try {
            if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
                throw new IllegalArgumentException("Unsupported extensions");
            }
            try {
                if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                    throw new IllegalArgumentException("Unsupported extension");
                }
                if (areAdvancedExtensionsSupported(i)) {
                    IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                    initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                    isCaptureProcessProgressAvailable = initializeAdvancedExtension.isCaptureProcessProgressAvailable();
                } else {
                    Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
                    IImageCaptureExtenderImpl iImageCaptureExtenderImpl = initializeExtension.second;
                    String str = this.mCameraId;
                    iImageCaptureExtenderImpl.init(str, this.mCharacteristicsMapNative.get(str));
                    isCaptureProcessProgressAvailable = initializeExtension.second.isCaptureProcessProgressAvailable();
                }
                return isCaptureProcessProgressAvailable;
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to query the extension progress callbacks! Extension service does not respond!");
                unregisterClient(this.mContext, binder, i);
                return false;
            }
        } finally {
            unregisterClient(this.mContext, binder, i);
        }
    }

    public Set<CaptureRequest.Key> getAvailableCaptureRequestKeys(int i) {
        CameraMetadataNative cameraMetadataNative;
        Binder binder = new Binder("CameraExtensionCharacteristics#getAvailableCaptureRequestKeys:" + this.mCameraId);
        if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
            throw new IllegalArgumentException("Unsupported extensions");
        }
        HashSet hashSet = new HashSet();
        try {
            try {
                if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                    throw new IllegalArgumentException("Unsupported extension");
                }
                if (areAdvancedExtensionsSupported(i)) {
                    IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                    initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                    cameraMetadataNative = initializeAdvancedExtension.getAvailableCaptureRequestKeys(this.mCameraId);
                } else {
                    Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
                    IImageCaptureExtenderImpl iImageCaptureExtenderImpl = initializeExtension.second;
                    String str = this.mCameraId;
                    iImageCaptureExtenderImpl.onInit(binder, str, this.mCharacteristicsMapNative.get(str));
                    IImageCaptureExtenderImpl iImageCaptureExtenderImpl2 = initializeExtension.second;
                    String str2 = this.mCameraId;
                    iImageCaptureExtenderImpl2.init(str2, this.mCharacteristicsMapNative.get(str2));
                    CameraMetadataNative availableCaptureRequestKeys = initializeExtension.second.getAvailableCaptureRequestKeys();
                    initializeExtension.second.onDeInit(binder);
                    cameraMetadataNative = availableCaptureRequestKeys;
                }
                if (cameraMetadataNative != null) {
                    int[] iArr = (int[]) cameraMetadataNative.get(CameraCharacteristics.REQUEST_AVAILABLE_REQUEST_KEYS);
                    if (iArr == null) {
                        throw new AssertionError("android.request.availableRequestKeys must be non-null in the characteristics");
                    }
                    hashSet.addAll(new CameraCharacteristics(cameraMetadataNative).getAvailableKeyList(CaptureRequest.class, CaptureRequest.Key.class, iArr, true));
                }
                if (!hashSet.contains(CaptureRequest.JPEG_QUALITY)) {
                    hashSet.add(CaptureRequest.JPEG_QUALITY);
                }
                if (!hashSet.contains(CaptureRequest.JPEG_ORIENTATION)) {
                    hashSet.add(CaptureRequest.JPEG_ORIENTATION);
                }
                unregisterClient(this.mContext, binder, i);
                return Collections.unmodifiableSet(hashSet);
            } catch (RemoteException unused) {
                throw new IllegalStateException("Failed to query the available capture request keys!");
            }
        } catch (Throwable th) {
            unregisterClient(this.mContext, binder, i);
            throw th;
        }
    }

    public Set<CaptureResult.Key> getAvailableCaptureResultKeys(int i) {
        CameraMetadataNative cameraMetadataNative;
        Binder binder = new Binder("CameraExtensionCharacteristics#getAvailableCaptureResultKeys:" + this.mCameraId);
        if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
            throw new IllegalArgumentException("Unsupported extensions");
        }
        HashSet hashSet = new HashSet();
        try {
            try {
                if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                    throw new IllegalArgumentException("Unsupported extension");
                }
                if (areAdvancedExtensionsSupported(i)) {
                    IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                    initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                    cameraMetadataNative = initializeAdvancedExtension.getAvailableCaptureResultKeys(this.mCameraId);
                } else {
                    Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
                    IImageCaptureExtenderImpl iImageCaptureExtenderImpl = initializeExtension.second;
                    String str = this.mCameraId;
                    iImageCaptureExtenderImpl.onInit(binder, str, this.mCharacteristicsMapNative.get(str));
                    IImageCaptureExtenderImpl iImageCaptureExtenderImpl2 = initializeExtension.second;
                    String str2 = this.mCameraId;
                    iImageCaptureExtenderImpl2.init(str2, this.mCharacteristicsMapNative.get(str2));
                    CameraMetadataNative availableCaptureResultKeys = initializeExtension.second.getAvailableCaptureResultKeys();
                    initializeExtension.second.onDeInit(binder);
                    cameraMetadataNative = availableCaptureResultKeys;
                }
                if (cameraMetadataNative != null) {
                    int[] iArr = (int[]) cameraMetadataNative.get(CameraCharacteristics.REQUEST_AVAILABLE_RESULT_KEYS);
                    if (iArr == null) {
                        throw new AssertionError("android.request.availableResultKeys must be non-null in the characteristics");
                    }
                    hashSet.addAll(new CameraCharacteristics(cameraMetadataNative).getAvailableKeyList(CaptureResult.class, CaptureResult.Key.class, iArr, true));
                    if (!hashSet.contains(CaptureResult.JPEG_QUALITY)) {
                        hashSet.add(CaptureResult.JPEG_QUALITY);
                    }
                    if (!hashSet.contains(CaptureResult.JPEG_ORIENTATION)) {
                        hashSet.add(CaptureResult.JPEG_ORIENTATION);
                    }
                    if (!hashSet.contains(CaptureResult.SENSOR_TIMESTAMP)) {
                        hashSet.add(CaptureResult.SENSOR_TIMESTAMP);
                    }
                }
                unregisterClient(this.mContext, binder, i);
                return Collections.unmodifiableSet(hashSet);
            } catch (RemoteException unused) {
                throw new IllegalStateException("Failed to query the available capture result keys!");
            }
        } catch (Throwable th) {
            unregisterClient(this.mContext, binder, i);
            throw th;
        }
    }
}
