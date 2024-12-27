package android.hardware.camera2;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.om.OverlayManagerExt$$ExternalSyntheticLambda4;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.extension.IAdvancedExtenderImpl;
import android.hardware.camera2.extension.ICameraExtensionsProxyService;
import android.hardware.camera2.extension.IImageCaptureExtenderImpl;
import android.hardware.camera2.extension.IInitializeSessionCallback;
import android.hardware.camera2.extension.IPreviewExtenderImpl;
import android.hardware.camera2.extension.LatencyRange;
import android.hardware.camera2.extension.SizeList;
import android.hardware.camera2.impl.CameraExtensionUtils;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.impl.ExtensionKey;
import android.hardware.camera2.impl.PublicKey;
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

    @Deprecated public static final int EXTENSION_BEAUTY = 1;
    public static final int EXTENSION_BOKEH = 2;
    public static final int EXTENSION_EYES_FREE_VIDEOGRAPHY = 5;
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
    private static final List<CameraCharacteristics.Key>
            SUPPORTED_SYNTHETIC_CAMERA_CHARACTERISTICS =
                    Arrays.asList(
                            CameraCharacteristics.REQUEST_AVAILABLE_DYNAMIC_RANGE_PROFILES,
                            CameraCharacteristics.REQUEST_AVAILABLE_COLOR_SPACE_PROFILES);

    @ExtensionKey @PublicKey
    public static final CameraCharacteristics.Key<Range<Float>> EFV_PADDING_ZOOM_FACTOR_RANGE =
            CameraCharacteristics.EFV_PADDING_ZOOM_FACTOR_RANGE;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Extension {}

    public CameraExtensionCharacteristics(
            Context context,
            String cameraId,
            Map<String, CameraCharacteristics> characteristicsMap) {
        this.mContext = context;
        this.mCameraId = cameraId;
        this.mCharacteristicsMap = characteristicsMap;
        this.mCharacteristicsMapNative =
                CameraExtensionUtils.getCharacteristicsMapNative(characteristicsMap);
    }

    private static ArrayList<Size> getSupportedSizes(List<SizeList> sizesList, Integer format) {
        ArrayList<Size> ret = new ArrayList<>();
        if (sizesList != null && !sizesList.isEmpty()) {
            for (SizeList entry : sizesList) {
                if (entry.format == format.intValue() && !entry.sizes.isEmpty()) {
                    for (android.hardware.camera2.extension.Size sz : entry.sizes) {
                        ret.add(new Size(sz.width, sz.height));
                    }
                    return ret;
                }
            }
        }
        return ret;
    }

    private static List<Size> generateSupportedSizes(
            List<SizeList> sizesList, Integer format, StreamConfigurationMap streamMap) {
        ArrayList<Size> ret = getSupportedSizes(sizesList, format);
        if (format.intValue() == 256 || format.intValue() == 35 || format.intValue() == 34) {
            Size[] supportedSizes = streamMap.getOutputSizes(format.intValue());
            if (ret.isEmpty() && supportedSizes != null) {
                ret.addAll(Arrays.asList(supportedSizes));
            }
        }
        return ret;
    }

    private static List<Size> generateJpegSupportedSizes(
            List<SizeList> sizesList, StreamConfigurationMap streamMap) {
        HashSet<Size> hashSet;
        ArrayList<Size> extensionSizes = getSupportedSizes(sizesList, 35);
        if (extensionSizes.isEmpty()) {
            hashSet = new HashSet<>(Arrays.asList(streamMap.getOutputSizes(35)));
        } else {
            hashSet = new HashSet<>(extensionSizes);
        }
        HashSet<Size> supportedSizes = hashSet;
        HashSet<Size> supportedJpegSizes =
                new HashSet<>(Arrays.asList(streamMap.getOutputSizes(256)));
        supportedSizes.retainAll(supportedJpegSizes);
        return new ArrayList(supportedSizes);
    }

    private static final class CameraExtensionManagerGlobal {
        private static final int FALLBACK_PACKAGE_NAME = 17040212;
        private static final int FALLBACK_SERVICE_NAME = 17040213;
        private static final CameraExtensionManagerGlobal GLOBAL_CAMERA_MANAGER =
                new CameraExtensionManagerGlobal();
        private static final String PROXY_PACKAGE_NAME = "com.android.cameraextensions";
        private static final String PROXY_SERVICE_NAME =
                "com.android.cameraextensions.CameraExtensionsProxyService";
        private static final String TAG = "CameraExtensionManagerGlobal";
        private final Object mLock = new Object();
        private final int PROXY_SERVICE_DELAY_MS = 2000;
        private ExtensionConnectionManager mConnectionManager = new ExtensionConnectionManager();
        private boolean mPermissionForFallbackEnabled = false;
        private boolean mIsFallbackEnabled = false;

        private CameraExtensionManagerGlobal() {}

        public static CameraExtensionManagerGlobal get() {
            return GLOBAL_CAMERA_MANAGER;
        }

        private void releaseProxyConnectionLocked(Context ctx, int extension) {
            if (this.mConnectionManager.getConnection(extension) != null) {
                ctx.unbindService(this.mConnectionManager.getConnection(extension));
                this.mConnectionManager.setConnection(extension, null);
                this.mConnectionManager.setProxy(extension, null);
                this.mConnectionManager.resetConnectionCount(extension);
            }
        }

        private void connectToProxyLocked(Context ctx, final int extension, boolean useFallback) {
            if (this.mConnectionManager.getConnection(extension) == null) {
                Intent intent = new Intent();
                intent.setClassName(PROXY_PACKAGE_NAME, PROXY_SERVICE_NAME);
                String vendorProxyPackage =
                        SystemProperties.get("ro.vendor.camera.extensions.package");
                String vendorProxyService =
                        SystemProperties.get("ro.vendor.camera.extensions.service");
                if (!vendorProxyPackage.isEmpty() && !vendorProxyService.isEmpty()) {
                    Log.v(
                            TAG,
                            "Choosing the vendor camera extensions proxy package: "
                                    + vendorProxyPackage);
                    Log.v(
                            TAG,
                            "Choosing the vendor camera extensions proxy service: "
                                    + vendorProxyService);
                    intent.setClassName(vendorProxyPackage, vendorProxyService);
                }
                if (Flags.concertMode() && useFallback) {
                    String packageName = ctx.getResources().getString(17040212);
                    String serviceName = ctx.getResources().getString(17040213);
                    if (!packageName.isEmpty() && !serviceName.isEmpty()) {
                        Log.v(
                                TAG,
                                "Choosing the fallback software implementation package: "
                                        + packageName);
                        Log.v(
                                TAG,
                                "Choosing the fallback software implementation service: "
                                        + serviceName);
                        intent.setClassName(packageName, serviceName);
                        this.mIsFallbackEnabled = true;
                    }
                }
                final InitializerFuture initFuture = new InitializerFuture();
                ServiceConnection connection = new ServiceConnection() { // from class:
                            // android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.1
                            @Override // android.content.ServiceConnection
                            public void onServiceDisconnected(ComponentName component) {
                                CameraExtensionManagerGlobal.this.mConnectionManager.setConnection(
                                        extension, null);
                                CameraExtensionManagerGlobal.this.mConnectionManager.setProxy(
                                        extension, null);
                            }

                            @Override // android.content.ServiceConnection
                            public void onServiceConnected(
                                    ComponentName component, IBinder binder) {
                                ICameraExtensionsProxyService proxy =
                                        ICameraExtensionsProxyService.Stub.asInterface(binder);
                                CameraExtensionManagerGlobal.this.mConnectionManager.setProxy(
                                        extension, proxy);
                                if (CameraExtensionManagerGlobal.this.mConnectionManager.getProxy(
                                                extension)
                                        == null) {
                                    throw new IllegalStateException("Camera Proxy service is null");
                                }
                                try {
                                    CameraExtensionManagerGlobal.this.mConnectionManager
                                            .setAdvancedExtensionsSupported(
                                                    extension,
                                                    CameraExtensionManagerGlobal.this
                                                            .mConnectionManager
                                                            .getProxy(extension)
                                                            .advancedExtensionsSupported());
                                } catch (RemoteException e) {
                                    Log.e(CameraExtensionManagerGlobal.TAG, "Remote IPC failed!");
                                }
                                initFuture.setStatus(true);
                            }
                        };
                ctx.bindService(intent, 1073741897, AsyncTask.THREAD_POOL_EXECUTOR, connection);
                this.mConnectionManager.setConnection(extension, connection);
                try {
                    initFuture.get(2000L, TimeUnit.MILLISECONDS);
                } catch (TimeoutException e) {
                    Log.e(TAG, "Timed out while initializing proxy service!");
                }
            }
        }

        private static class InitializerFuture implements Future<Boolean> {
            ConditionVariable mCondVar;
            private volatile Boolean mStatus;

            private InitializerFuture() {
                this.mCondVar = new ConditionVariable(false);
            }

            public void setStatus(boolean status) {
                this.mStatus = Boolean.valueOf(status);
                this.mCondVar.open();
            }

            @Override // java.util.concurrent.Future
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override // java.util.concurrent.Future
            public boolean isCancelled() {
                return false;
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
            public Boolean get(long timeout, TimeUnit unit) throws TimeoutException {
                long timeoutMs = unit.convert(timeout, TimeUnit.MILLISECONDS);
                if (!this.mCondVar.block(timeoutMs)) {
                    throw new TimeoutException(
                            "Failed to receive status after " + timeout + " " + unit);
                }
                if (this.mStatus == null) {
                    throw new AssertionError();
                }
                return this.mStatus;
            }
        }

        public boolean registerClientHelper(
                Context ctx, IBinder token, int extension, boolean useFallback) {
            synchronized (this.mLock) {
                boolean ret = false;
                connectToProxyLocked(ctx, extension, useFallback);
                if (this.mConnectionManager.getProxy(extension) == null) {
                    return false;
                }
                this.mConnectionManager.incrementConnectionCount(extension);
                try {
                    ret = this.mConnectionManager.getProxy(extension).registerClient(token);
                } catch (RemoteException e) {
                    Log.e(
                            TAG,
                            "Failed to initialize extension! Extension service does  not respond!");
                }
                if (!ret) {
                    this.mConnectionManager.decrementConnectionCount(extension);
                }
                if (this.mConnectionManager.getConnectionCount(extension) <= 0) {
                    releaseProxyConnectionLocked(ctx, extension);
                }
                if (Flags.concertMode() && ret && useFallback && this.mIsFallbackEnabled) {
                    try {
                        try {
                            InitializeSessionHandler cb = new InitializeSessionHandler(ctx);
                            initializeSession(cb, extension);
                            ret = this.mPermissionForFallbackEnabled;
                        } catch (RemoteException e2) {
                            Log.e(
                                    TAG,
                                    "Failed to initialize extension. Extension service does not"
                                            + " respond!");
                            ret = false;
                        }
                    } finally {
                        releaseSession(extension);
                    }
                }
                return ret;
            }
        }

        public boolean registerClient(
                Context ctx,
                IBinder token,
                int extension,
                String cameraId,
                Map<String, CameraMetadataNative> characteristicsMapNative) {
            boolean ret = registerClientHelper(ctx, token, extension, false);
            if (Flags.concertMode()) {
                ContentResolver resolver = ctx.getContentResolver();
                int userEnabled =
                        Settings.Secure.getInt(
                                resolver, Settings.Secure.CAMERA_EXTENSIONS_FALLBACK, 1);
                boolean vendorImpl = true;
                if (ret
                        && this.mConnectionManager.getProxy(extension) != null
                        && userEnabled == 1) {
                    vendorImpl =
                            CameraExtensionCharacteristics.isExtensionSupported(
                                    cameraId, extension, characteristicsMapNative);
                }
                if (!vendorImpl) {
                    unregisterClient(ctx, token, extension);
                    return registerClientHelper(ctx, token, extension, true);
                }
                return ret;
            }
            return ret;
        }

        public void unregisterClient(Context ctx, IBinder token, int extension) {
            synchronized (this.mLock) {
                try {
                    if (this.mConnectionManager.getProxy(extension) != null) {
                        try {
                            this.mConnectionManager.getProxy(extension).unregisterClient(token);
                        } catch (RemoteException e) {
                            Log.e(
                                    TAG,
                                    "Failed to de-initialize extension! Extension service does not"
                                            + " respond!");
                            this.mConnectionManager.decrementConnectionCount(extension);
                            if (this.mConnectionManager.getConnectionCount(extension) <= 0) {}
                        }
                    }
                } finally {
                    this.mConnectionManager.decrementConnectionCount(extension);
                    if (this.mConnectionManager.getConnectionCount(extension) <= 0) {
                        releaseProxyConnectionLocked(ctx, extension);
                    }
                }
            }
        }

        public void initializeSession(IInitializeSessionCallback cb, int extension)
                throws RemoteException {
            synchronized (this.mLock) {
                if (this.mConnectionManager.getProxy(extension) != null
                        && !this.mConnectionManager.isSessionInitialized()) {
                    this.mConnectionManager.getProxy(extension).initializeSession(cb);
                    this.mConnectionManager.setSessionInitialized(true);
                } else {
                    cb.onFailure();
                }
            }
        }

        public void releaseSession(int extension) {
            synchronized (this.mLock) {
                if (this.mConnectionManager.getProxy(extension) != null) {
                    try {
                        this.mConnectionManager.getProxy(extension).releaseSession();
                        this.mConnectionManager.setSessionInitialized(false);
                        this.mPermissionForFallbackEnabled = false;
                    } catch (RemoteException e) {
                        Log.e(
                                TAG,
                                "Failed to release session! Extension service does not respond!");
                    }
                }
            }
        }

        public boolean areAdvancedExtensionsSupported(int extension) {
            return this.mConnectionManager.areAdvancedExtensionsSupported(extension);
        }

        public IPreviewExtenderImpl initializePreviewExtension(int extension)
                throws RemoteException {
            synchronized (this.mLock) {
                if (this.mConnectionManager.getProxy(extension) == null) {
                    return null;
                }
                return this.mConnectionManager
                        .getProxy(extension)
                        .initializePreviewExtension(extension);
            }
        }

        public IImageCaptureExtenderImpl initializeImageExtension(int extension)
                throws RemoteException {
            synchronized (this.mLock) {
                if (this.mConnectionManager.getProxy(extension) == null) {
                    return null;
                }
                return this.mConnectionManager
                        .getProxy(extension)
                        .initializeImageExtension(extension);
            }
        }

        public IAdvancedExtenderImpl initializeAdvancedExtension(int extension)
                throws RemoteException {
            synchronized (this.mLock) {
                if (this.mConnectionManager.getProxy(extension) == null) {
                    return null;
                }
                return this.mConnectionManager
                        .getProxy(extension)
                        .initializeAdvancedExtension(extension);
            }
        }

        private class InitializeSessionHandler extends IInitializeSessionCallback.Stub {
            private Context mContext;

            public InitializeSessionHandler(Context context) {
                this.mContext = context;
            }

            @Override // android.hardware.camera2.extension.IInitializeSessionCallback
            public void onSuccess() {
                String[] callingUidPackages =
                        this.mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid());
                String fallbackPackageName = this.mContext.getResources().getString(17040212);
                if (!fallbackPackageName.isEmpty()) {
                    Stream stream = Arrays.stream(callingUidPackages);
                    Objects.requireNonNull(fallbackPackageName);
                    if (stream.anyMatch(
                            new OverlayManagerExt$$ExternalSyntheticLambda4(fallbackPackageName))) {
                        String[] cameraPermissions = {
                            Manifest.permission.SYSTEM_CAMERA, Manifest.permission.CAMERA
                        };
                        boolean allPermissionsGranted = true;
                        for (String permission : cameraPermissions) {
                            int permissionResult =
                                    this.mContext.checkPermission(
                                            permission,
                                            Binder.getCallingPid(),
                                            Binder.getCallingUid());
                            if (permissionResult != 0) {
                                Log.w(
                                        CameraExtensionManagerGlobal.TAG,
                                        permission
                                                + " permission not granted for "
                                                + fallbackPackageName
                                                + ", permission check result: "
                                                + permissionResult);
                                allPermissionsGranted = false;
                            }
                        }
                        CameraExtensionManagerGlobal.this.mPermissionForFallbackEnabled =
                                allPermissionsGranted;
                    }
                }
            }

            @Override // android.hardware.camera2.extension.IInitializeSessionCallback
            public void onFailure() {
                Log.e(
                        CameraExtensionManagerGlobal.TAG,
                        "Failed to initialize proxy service session!");
            }
        }

        private class ExtensionConnectionManager {
            private Map<Integer, ExtensionConnection> mConnections = new HashMap();
            private boolean mSessionInitialized = false;

            public ExtensionConnectionManager() {
                IntArray extensionList =
                        new IntArray(CameraExtensionCharacteristics.EXTENSION_LIST.length);
                extensionList.addAll(CameraExtensionCharacteristics.EXTENSION_LIST);
                if (Flags.concertModeApi()) {
                    extensionList.add(5);
                }
                for (int extensionType : extensionList.toArray()) {
                    this.mConnections.put(
                            Integer.valueOf(extensionType), new ExtensionConnection());
                }
            }

            public ICameraExtensionsProxyService getProxy(int extension) {
                return this.mConnections.get(Integer.valueOf(extension)).mProxy;
            }

            public ServiceConnection getConnection(int extension) {
                return this.mConnections.get(Integer.valueOf(extension)).mConnection;
            }

            public int getConnectionCount(int extension) {
                return this.mConnections.get(Integer.valueOf(extension)).mConnectionCount;
            }

            public boolean areAdvancedExtensionsSupported(int extension) {
                return this.mConnections.get(Integer.valueOf(extension))
                        .mSupportsAdvancedExtensions;
            }

            public boolean isSessionInitialized() {
                return this.mSessionInitialized;
            }

            public void setProxy(int extension, ICameraExtensionsProxyService proxy) {
                this.mConnections.get(Integer.valueOf(extension)).mProxy = proxy;
            }

            public void setConnection(int extension, ServiceConnection connection) {
                this.mConnections.get(Integer.valueOf(extension)).mConnection = connection;
            }

            public void incrementConnectionCount(int extension) {
                this.mConnections.get(Integer.valueOf(extension)).mConnectionCount++;
            }

            public void decrementConnectionCount(int extension) {
                ExtensionConnection extensionConnection =
                        this.mConnections.get(Integer.valueOf(extension));
                extensionConnection.mConnectionCount--;
            }

            public void resetConnectionCount(int extension) {
                this.mConnections.get(Integer.valueOf(extension)).mConnectionCount = 0;
            }

            public void setAdvancedExtensionsSupported(
                    int extension, boolean advancedExtSupported) {
                this.mConnections.get(Integer.valueOf(extension)).mSupportsAdvancedExtensions =
                        advancedExtSupported;
            }

            public void setSessionInitialized(boolean initialized) {
                this.mSessionInitialized = initialized;
            }

            private class ExtensionConnection {
                public ServiceConnection mConnection;
                public int mConnectionCount;
                public ICameraExtensionsProxyService mProxy;
                public boolean mSupportsAdvancedExtensions;

                private ExtensionConnection() {
                    this.mProxy = null;
                    this.mConnection = null;
                    this.mConnectionCount = 0;
                    this.mSupportsAdvancedExtensions = false;
                }
            }
        }
    }

    public static boolean registerClient(
            Context ctx,
            IBinder token,
            int extension,
            String cameraId,
            Map<String, CameraMetadataNative> characteristicsMapNative) {
        return CameraExtensionManagerGlobal.get()
                .registerClient(ctx, token, extension, cameraId, characteristicsMapNative);
    }

    public static void unregisterClient(Context ctx, IBinder token, int extension) {
        CameraExtensionManagerGlobal.get().unregisterClient(ctx, token, extension);
    }

    public static void initializeSession(IInitializeSessionCallback cb, int extension)
            throws RemoteException {
        CameraExtensionManagerGlobal.get().initializeSession(cb, extension);
    }

    public static void releaseSession(int extension) {
        CameraExtensionManagerGlobal.get().releaseSession(extension);
    }

    public static boolean areAdvancedExtensionsSupported(int extension) {
        return CameraExtensionManagerGlobal.get().areAdvancedExtensionsSupported(extension);
    }

    public static boolean isExtensionSupported(
            String cameraId,
            int extensionType,
            Map<String, CameraMetadataNative> characteristicsMap) {
        if (areAdvancedExtensionsSupported(extensionType)) {
            try {
                IAdvancedExtenderImpl extender = initializeAdvancedExtension(extensionType);
                return extender.isExtensionAvailable(cameraId, characteristicsMap);
            } catch (RemoteException e) {
                Log.e(
                        TAG,
                        "Failed to query extension availability! Extension service does not"
                                + " respond!");
                return false;
            }
        }
        try {
            Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders =
                    initializeExtension(extensionType);
            try {
                if (extenders.first.isExtensionAvailable(
                        cameraId, characteristicsMap.get(cameraId))) {
                    return extenders.second.isExtensionAvailable(
                            cameraId, characteristicsMap.get(cameraId));
                }
                return false;
            } catch (RemoteException e2) {
                Log.e(
                        TAG,
                        "Failed to query extension availability! Extension service does not"
                                + " respond!");
                return false;
            }
        } catch (IllegalArgumentException e3) {
            return false;
        }
    }

    public static IAdvancedExtenderImpl initializeAdvancedExtension(int extensionType) {
        try {
            IAdvancedExtenderImpl extender =
                    CameraExtensionManagerGlobal.get().initializeAdvancedExtension(extensionType);
            if (extender == null) {
                throw new IllegalArgumentException("Unknown extension: " + extensionType);
            }
            return extender;
        } catch (RemoteException e) {
            throw new IllegalStateException("Failed to initialize extension: " + extensionType);
        }
    }

    public static Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> initializeExtension(
            int extensionType) {
        try {
            IPreviewExtenderImpl previewExtender =
                    CameraExtensionManagerGlobal.get().initializePreviewExtension(extensionType);
            IImageCaptureExtenderImpl imageExtender =
                    CameraExtensionManagerGlobal.get().initializeImageExtension(extensionType);
            if (imageExtender == null || previewExtender == null) {
                throw new IllegalArgumentException("Unknown extension: " + extensionType);
            }
            return new Pair<>(previewExtender, imageExtender);
        } catch (RemoteException e) {
            throw new IllegalStateException("Failed to initialize extension: " + extensionType);
        }
    }

    private static <T> boolean isOutputSupportedFor(Class<T> klass) {
        Objects.requireNonNull(klass, "klass must not be null");
        if (klass == SurfaceTexture.class || klass == SurfaceView.class) {
            return true;
        }
        return false;
    }

    public List<Integer> getSupportedExtensions() {
        ArrayList<Integer> ret = new ArrayList<>();
        IBinder token =
                new Binder(
                        "CameraExtensionCharacteristics#getSupportedExtensions:" + this.mCameraId);
        IntArray extensionList = new IntArray(EXTENSION_LIST.length);
        extensionList.addAll(EXTENSION_LIST);
        if (Flags.concertModeApi()) {
            extensionList.add(5);
        }
        for (int extensionType : extensionList.toArray()) {
            try {
                boolean success =
                        registerClient(
                                this.mContext,
                                token,
                                extensionType,
                                this.mCameraId,
                                this.mCharacteristicsMapNative);
                if (success
                        && isExtensionSupported(
                                this.mCameraId, extensionType, this.mCharacteristicsMapNative)) {
                    ret.add(Integer.valueOf(extensionType));
                }
                unregisterClient(this.mContext, token, extensionType);
            } catch (Throwable th) {
                unregisterClient(this.mContext, token, extensionType);
                throw th;
            }
        }
        return Collections.unmodifiableList(ret);
    }

    public <T> T get(int i, CameraCharacteristics.Key<T> key) {
        Binder binder = new Binder("CameraExtensionCharacteristics#get:" + this.mCameraId);
        if (!registerClient(
                this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
            throw new IllegalArgumentException("Unsupported extensions");
        }
        try {
            try {
            } catch (RemoteException e) {
                Log.e(
                        TAG,
                        "Failed to query the extension for the specified key! Extension service"
                                + " does not respond!");
            }
            if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                throw new IllegalArgumentException("Unsupported extension");
            }
            if (areAdvancedExtensionsSupported(i) && getKeys(i).contains(key)) {
                IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                CameraMetadataNative availableCharacteristicsKeyValues =
                        initializeAdvancedExtension.getAvailableCharacteristicsKeyValues(
                                this.mCameraId);
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

    public Set<CameraCharacteristics.Key> getKeys(int extension) {
        IBinder token = new Binder("CameraExtensionCharacteristics#getKeys:" + this.mCameraId);
        boolean success =
                registerClient(
                        this.mContext,
                        token,
                        extension,
                        this.mCameraId,
                        this.mCharacteristicsMapNative);
        if (!success) {
            throw new IllegalArgumentException("Unsupported extensions");
        }
        HashSet<CameraCharacteristics.Key> ret = new HashSet<>();
        try {
            try {
            } catch (RemoteException e) {
                Log.e(
                        TAG,
                        "Failed to query the extension for all available keys! Extension service"
                                + " does not respond!");
            }
            if (!isExtensionSupported(this.mCameraId, extension, this.mCharacteristicsMapNative)) {
                throw new IllegalArgumentException("Unsupported extension");
            }
            if (areAdvancedExtensionsSupported(extension)) {
                IAdvancedExtenderImpl extender = initializeAdvancedExtension(extension);
                extender.init(this.mCameraId, this.mCharacteristicsMapNative);
                CameraMetadataNative metadata =
                        extender.getAvailableCharacteristicsKeyValues(this.mCameraId);
                if (metadata == null) {
                    return Collections.emptySet();
                }
                int[] keys =
                        (int[])
                                metadata.get(
                                        CameraCharacteristics
                                                .REQUEST_AVAILABLE_CHARACTERISTICS_KEYS);
                if (keys == null) {
                    throw new AssertionError(
                            "android.request.availableCharacteristicsKeys must be non-null in the"
                                    + " characteristics");
                }
                CameraCharacteristics chars = new CameraCharacteristics(metadata);
                ret.addAll(
                        chars.getAvailableKeyList(
                                CameraCharacteristics.class, keyTyped, keys, false));
                for (CameraCharacteristics.Key charKey :
                        SUPPORTED_SYNTHETIC_CAMERA_CHARACTERISTICS) {
                    if (chars.get(charKey) != null) {
                        ret.add(charKey);
                    }
                }
            }
            unregisterClient(this.mContext, token, extension);
            return Collections.unmodifiableSet(ret);
        } finally {
            unregisterClient(this.mContext, token, extension);
        }
    }

    public boolean isPostviewAvailable(int extension) {
        IBinder token =
                new Binder("CameraExtensionCharacteristics#isPostviewAvailable:" + this.mCameraId);
        boolean success =
                registerClient(
                        this.mContext,
                        token,
                        extension,
                        this.mCameraId,
                        this.mCharacteristicsMapNative);
        try {
            if (!success) {
                throw new IllegalArgumentException("Unsupported extensions");
            }
            try {
                if (!isExtensionSupported(
                        this.mCameraId, extension, this.mCharacteristicsMapNative)) {
                    throw new IllegalArgumentException("Unsupported extension");
                }
                if (areAdvancedExtensionsSupported(extension)) {
                    IAdvancedExtenderImpl extender = initializeAdvancedExtension(extension);
                    extender.init(this.mCameraId, this.mCharacteristicsMapNative);
                    return extender.isPostviewAvailable();
                }
                Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders =
                        initializeExtension(extension);
                extenders.second.init(
                        this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
                return extenders.second.isPostviewAvailable();
            } catch (RemoteException e) {
                Log.e(
                        TAG,
                        "Failed to query the extension for postview availability! Extension service"
                                + " does not respond!");
                unregisterClient(this.mContext, token, extension);
                return false;
            }
        } finally {
            unregisterClient(this.mContext, token, extension);
        }
    }

    public List<Size> getPostviewSupportedSizes(int extension, Size captureSize, int format) {
        IBinder token =
                new Binder(
                        "CameraExtensionCharacteristics#getPostviewSupportedSizes:"
                                + this.mCameraId);
        boolean success =
                registerClient(
                        this.mContext,
                        token,
                        extension,
                        this.mCameraId,
                        this.mCharacteristicsMapNative);
        try {
            if (!success) {
                throw new IllegalArgumentException("Unsupported extensions");
            }
            if (!isExtensionSupported(this.mCameraId, extension, this.mCharacteristicsMapNative)) {
                throw new IllegalArgumentException("Unsupported extension");
            }
            android.hardware.camera2.extension.Size sz =
                    new android.hardware.camera2.extension.Size();
            sz.width = captureSize.getWidth();
            sz.height = captureSize.getHeight();
            if (areAdvancedExtensionsSupported(extension)) {
                switch (format) {
                    case 35:
                    case 54:
                    case 256:
                    case 4101:
                        IAdvancedExtenderImpl extender = initializeAdvancedExtension(extension);
                        extender.init(this.mCameraId, this.mCharacteristicsMapNative);
                        return getSupportedSizes(
                                extender.getSupportedPostviewResolutions(sz),
                                Integer.valueOf(format));
                    default:
                        throw new IllegalArgumentException("Unsupported format: " + format);
                }
            }
            Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders =
                    initializeExtension(extension);
            extenders.second.init(
                    this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
            if (extenders.second.getCaptureProcessor() == null || !isPostviewAvailable(extension)) {
                throw new IllegalArgumentException("Extension does not support postview feature");
            }
            if (format == 35) {
                return getSupportedSizes(
                        extenders.second.getSupportedPostviewResolutions(sz),
                        Integer.valueOf(format));
            }
            if (format == 256) {
                return getSupportedSizes(
                        extenders.second.getSupportedPostviewResolutions(sz),
                        Integer.valueOf(format));
            }
            if (format == 4101 || format == 54) {
                return new ArrayList();
            }
            throw new IllegalArgumentException("Unsupported format: " + format);
        } catch (RemoteException e) {
            Log.e(
                    TAG,
                    "Failed to query the extension postview supported sizes! Extension service does"
                            + " not respond!");
            return Collections.emptyList();
        } finally {
            unregisterClient(this.mContext, token, extension);
        }
    }

    public <T> List<Size> getExtensionSupportedSizes(int extension, Class<T> klass) {
        if (!isOutputSupportedFor(klass)) {
            return new ArrayList();
        }
        IBinder token =
                new Binder(
                        "CameraExtensionCharacteristics#getExtensionSupportedSizes:"
                                + this.mCameraId);
        boolean success =
                registerClient(
                        this.mContext,
                        token,
                        extension,
                        this.mCameraId,
                        this.mCharacteristicsMapNative);
        try {
            if (!success) {
                throw new IllegalArgumentException("Unsupported extensions");
            }
            if (!isExtensionSupported(this.mCameraId, extension, this.mCharacteristicsMapNative)) {
                throw new IllegalArgumentException("Unsupported extension");
            }
            StreamConfigurationMap streamMap =
                    (StreamConfigurationMap)
                            this.mCharacteristicsMap
                                    .get(this.mCameraId)
                                    .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (areAdvancedExtensionsSupported(extension)) {
                IAdvancedExtenderImpl extender = initializeAdvancedExtension(extension);
                extender.init(this.mCameraId, this.mCharacteristicsMapNative);
                return generateSupportedSizes(
                        extender.getSupportedPreviewOutputResolutions(this.mCameraId),
                        34,
                        streamMap);
            }
            Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders =
                    initializeExtension(extension);
            extenders.first.init(
                    this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
            return generateSupportedSizes(extenders.first.getSupportedResolutions(), 34, streamMap);
        } catch (RemoteException e) {
            Log.e(
                    TAG,
                    "Failed to query the extension supported sizes! Extension service does not"
                            + " respond!");
            return new ArrayList();
        } finally {
            unregisterClient(this.mContext, token, extension);
        }
    }

    public List<Size> getExtensionSupportedSizes(int extension, int format) {
        try {
            IBinder token =
                    new Binder(
                            "CameraExtensionCharacteristics#getExtensionSupportedSizes:"
                                    + this.mCameraId);
            boolean success =
                    registerClient(
                            this.mContext,
                            token,
                            extension,
                            this.mCameraId,
                            this.mCharacteristicsMapNative);
            if (!success) {
                throw new IllegalArgumentException("Unsupported extensions");
            }
            try {
                if (!isExtensionSupported(
                        this.mCameraId, extension, this.mCharacteristicsMapNative)) {
                    throw new IllegalArgumentException("Unsupported extension");
                }
                StreamConfigurationMap streamMap =
                        (StreamConfigurationMap)
                                this.mCharacteristicsMap
                                        .get(this.mCameraId)
                                        .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (areAdvancedExtensionsSupported(extension)) {
                    switch (format) {
                        case 35:
                        case 54:
                        case 256:
                        case 4101:
                            IAdvancedExtenderImpl extender = initializeAdvancedExtension(extension);
                            extender.init(this.mCameraId, this.mCharacteristicsMapNative);
                            return generateSupportedSizes(
                                    extender.getSupportedCaptureOutputResolutions(this.mCameraId),
                                    Integer.valueOf(format),
                                    streamMap);
                        default:
                            throw new IllegalArgumentException("Unsupported format: " + format);
                    }
                }
                if (format == 35) {
                    Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders =
                            initializeExtension(extension);
                    extenders.second.init(
                            this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
                    return extenders.second.getCaptureProcessor() == null
                            ? new ArrayList()
                            : generateSupportedSizes(
                                    extenders.second.getSupportedResolutions(),
                                    Integer.valueOf(format),
                                    streamMap);
                }
                if (format == 256) {
                    Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders2 =
                            initializeExtension(extension);
                    extenders2.second.init(
                            this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
                    return extenders2.second.getCaptureProcessor() != null
                            ? generateJpegSupportedSizes(
                                    extenders2.second.getSupportedResolutions(), streamMap)
                            : generateSupportedSizes(null, Integer.valueOf(format), streamMap);
                }
                if (format == 4101 || format == 54) {
                    return new ArrayList();
                }
                throw new IllegalArgumentException("Unsupported format: " + format);
            } finally {
                unregisterClient(this.mContext, token, extension);
            }
        } catch (RemoteException e) {
            Log.e(
                    TAG,
                    "Failed to query the extension supported sizes! Extension service does not"
                            + " respond!");
            return new ArrayList();
        }
    }

    public Range<Long> getEstimatedCaptureLatencyRangeMillis(
            int extension, Size captureOutputSize, int format) {
        switch (format) {
            case 35:
            case 54:
            case 256:
            case 4101:
                IBinder token =
                        new Binder(
                                "CameraExtensionCharacteristics#getEstimatedCaptureLatencyRangeMillis:"
                                        + this.mCameraId);
                boolean success =
                        registerClient(
                                this.mContext,
                                token,
                                extension,
                                this.mCameraId,
                                this.mCharacteristicsMapNative);
                if (!success) {
                    throw new IllegalArgumentException("Unsupported extensions");
                }
                try {
                    try {
                    } catch (RemoteException e) {
                        Log.e(
                                TAG,
                                "Failed to query the extension capture latency! Extension service"
                                        + " does not respond!");
                    }
                    if (!isExtensionSupported(
                            this.mCameraId, extension, this.mCharacteristicsMapNative)) {
                        throw new IllegalArgumentException("Unsupported extension");
                    }
                    android.hardware.camera2.extension.Size sz =
                            new android.hardware.camera2.extension.Size();
                    sz.width = captureOutputSize.getWidth();
                    sz.height = captureOutputSize.getHeight();
                    if (areAdvancedExtensionsSupported(extension)) {
                        IAdvancedExtenderImpl extender = initializeAdvancedExtension(extension);
                        extender.init(this.mCameraId, this.mCharacteristicsMapNative);
                        LatencyRange latencyRange =
                                extender.getEstimatedCaptureLatencyRange(
                                        this.mCameraId, sz, format);
                        if (latencyRange != null) {
                            return new Range<>(
                                    Long.valueOf(latencyRange.min), Long.valueOf(latencyRange.max));
                        }
                    } else {
                        Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders =
                                initializeExtension(extension);
                        extenders.second.init(
                                this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
                        if (format == 35 && extenders.second.getCaptureProcessor() == null) {
                            return null;
                        }
                        if (format == 256 && extenders.second.getCaptureProcessor() != null) {
                            return null;
                        }
                        if (format == 4101 || format == 54) {
                            return null;
                        }
                        LatencyRange latencyRange2 =
                                extenders.second.getEstimatedCaptureLatencyRange(sz);
                        if (latencyRange2 != null) {
                            return new Range<>(
                                    Long.valueOf(latencyRange2.min),
                                    Long.valueOf(latencyRange2.max));
                        }
                    }
                    return null;
                } finally {
                    unregisterClient(this.mContext, token, extension);
                }
            default:
                throw new IllegalArgumentException("Unsupported format: " + format);
        }
    }

    public boolean isCaptureProcessProgressAvailable(int extension) {
        IBinder token =
                new Binder(
                        "CameraExtensionCharacteristics#isCaptureProcessProgressAvailable:"
                                + this.mCameraId);
        boolean success =
                registerClient(
                        this.mContext,
                        token,
                        extension,
                        this.mCameraId,
                        this.mCharacteristicsMapNative);
        try {
            if (!success) {
                throw new IllegalArgumentException("Unsupported extensions");
            }
            try {
                if (!isExtensionSupported(
                        this.mCameraId, extension, this.mCharacteristicsMapNative)) {
                    throw new IllegalArgumentException("Unsupported extension");
                }
                if (areAdvancedExtensionsSupported(extension)) {
                    IAdvancedExtenderImpl extender = initializeAdvancedExtension(extension);
                    extender.init(this.mCameraId, this.mCharacteristicsMapNative);
                    return extender.isCaptureProcessProgressAvailable();
                }
                Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders =
                        initializeExtension(extension);
                extenders.second.init(
                        this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
                return extenders.second.isCaptureProcessProgressAvailable();
            } catch (RemoteException e) {
                Log.e(
                        TAG,
                        "Failed to query the extension progress callbacks! Extension service does"
                                + " not respond!");
                unregisterClient(this.mContext, token, extension);
                return false;
            }
        } finally {
            unregisterClient(this.mContext, token, extension);
        }
    }

    public Set<CaptureRequest.Key> getAvailableCaptureRequestKeys(int extension) {
        CameraMetadataNative captureRequestMeta;
        IBinder token =
                new Binder(
                        "CameraExtensionCharacteristics#getAvailableCaptureRequestKeys:"
                                + this.mCameraId);
        boolean success =
                registerClient(
                        this.mContext,
                        token,
                        extension,
                        this.mCameraId,
                        this.mCharacteristicsMapNative);
        if (!success) {
            throw new IllegalArgumentException("Unsupported extensions");
        }
        HashSet<CaptureRequest.Key> ret = new HashSet<>();
        try {
            try {
                if (!isExtensionSupported(
                        this.mCameraId, extension, this.mCharacteristicsMapNative)) {
                    throw new IllegalArgumentException("Unsupported extension");
                }
                if (areAdvancedExtensionsSupported(extension)) {
                    IAdvancedExtenderImpl extender = initializeAdvancedExtension(extension);
                    extender.init(this.mCameraId, this.mCharacteristicsMapNative);
                    captureRequestMeta = extender.getAvailableCaptureRequestKeys(this.mCameraId);
                } else {
                    Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders =
                            initializeExtension(extension);
                    extenders.second.onInit(
                            token,
                            this.mCameraId,
                            this.mCharacteristicsMapNative.get(this.mCameraId));
                    extenders.second.init(
                            this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
                    captureRequestMeta = extenders.second.getAvailableCaptureRequestKeys();
                    extenders.second.onDeInit(token);
                }
                if (captureRequestMeta != null) {
                    int[] requestKeys =
                            (int[])
                                    captureRequestMeta.get(
                                            CameraCharacteristics.REQUEST_AVAILABLE_REQUEST_KEYS);
                    if (requestKeys == null) {
                        throw new AssertionError(
                                "android.request.availableRequestKeys must be non-null in the"
                                        + " characteristics");
                    }
                    CameraCharacteristics requestChars =
                            new CameraCharacteristics(captureRequestMeta);
                    ret.addAll(
                            requestChars.getAvailableKeyList(
                                    CaptureRequest.class, crKeyTyped, requestKeys, true));
                }
                if (!ret.contains(CaptureRequest.JPEG_QUALITY)) {
                    ret.add(CaptureRequest.JPEG_QUALITY);
                }
                if (!ret.contains(CaptureRequest.JPEG_ORIENTATION)) {
                    ret.add(CaptureRequest.JPEG_ORIENTATION);
                }
                unregisterClient(this.mContext, token, extension);
                return Collections.unmodifiableSet(ret);
            } catch (RemoteException e) {
                throw new IllegalStateException(
                        "Failed to query the available capture request keys!");
            }
        } catch (Throwable th) {
            unregisterClient(this.mContext, token, extension);
            throw th;
        }
    }

    public Set<CaptureResult.Key> getAvailableCaptureResultKeys(int extension) {
        CameraMetadataNative captureResultMeta;
        IBinder token =
                new Binder(
                        "CameraExtensionCharacteristics#getAvailableCaptureResultKeys:"
                                + this.mCameraId);
        boolean success =
                registerClient(
                        this.mContext,
                        token,
                        extension,
                        this.mCameraId,
                        this.mCharacteristicsMapNative);
        if (!success) {
            throw new IllegalArgumentException("Unsupported extensions");
        }
        HashSet<CaptureResult.Key> ret = new HashSet<>();
        try {
            try {
                if (!isExtensionSupported(
                        this.mCameraId, extension, this.mCharacteristicsMapNative)) {
                    throw new IllegalArgumentException("Unsupported extension");
                }
                if (areAdvancedExtensionsSupported(extension)) {
                    IAdvancedExtenderImpl extender = initializeAdvancedExtension(extension);
                    extender.init(this.mCameraId, this.mCharacteristicsMapNative);
                    captureResultMeta = extender.getAvailableCaptureResultKeys(this.mCameraId);
                } else {
                    Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders =
                            initializeExtension(extension);
                    extenders.second.onInit(
                            token,
                            this.mCameraId,
                            this.mCharacteristicsMapNative.get(this.mCameraId));
                    extenders.second.init(
                            this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
                    captureResultMeta = extenders.second.getAvailableCaptureResultKeys();
                    extenders.second.onDeInit(token);
                }
                if (captureResultMeta != null) {
                    int[] resultKeys =
                            (int[])
                                    captureResultMeta.get(
                                            CameraCharacteristics.REQUEST_AVAILABLE_RESULT_KEYS);
                    if (resultKeys == null) {
                        throw new AssertionError(
                                "android.request.availableResultKeys must be non-null in the"
                                        + " characteristics");
                    }
                    CameraCharacteristics resultChars =
                            new CameraCharacteristics(captureResultMeta);
                    ret.addAll(
                            resultChars.getAvailableKeyList(
                                    CaptureResult.class, crKeyTyped, resultKeys, true));
                    if (!ret.contains(CaptureResult.JPEG_QUALITY)) {
                        ret.add(CaptureResult.JPEG_QUALITY);
                    }
                    if (!ret.contains(CaptureResult.JPEG_ORIENTATION)) {
                        ret.add(CaptureResult.JPEG_ORIENTATION);
                    }
                    if (!ret.contains(CaptureResult.SENSOR_TIMESTAMP)) {
                        ret.add(CaptureResult.SENSOR_TIMESTAMP);
                    }
                }
                unregisterClient(this.mContext, token, extension);
                return Collections.unmodifiableSet(ret);
            } catch (RemoteException e) {
                throw new IllegalStateException(
                        "Failed to query the available capture result keys!");
            }
        } catch (Throwable th) {
            unregisterClient(this.mContext, token, extension);
            throw th;
        }
    }
}
