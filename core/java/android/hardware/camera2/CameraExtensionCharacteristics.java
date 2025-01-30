package android.hardware.camera2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.SurfaceTexture;
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
import android.p009os.AsyncTask;
import android.p009os.Binder;
import android.p009os.ConditionVariable;
import android.p009os.IBinder;
import android.p009os.RemoteException;
import android.p009os.SystemProperties;
import android.util.Log;
import android.util.Pair;
import android.util.Range;
import android.util.Size;
import android.view.SurfaceView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public final class CameraExtensionCharacteristics {
    public static final int EXTENSION_AUTOMATIC = 0;

    @Deprecated
    public static final int EXTENSION_BEAUTY = 1;
    public static final int EXTENSION_BOKEH = 2;
    public static final int EXTENSION_FACE_RETOUCH = 1;
    public static final int EXTENSION_HDR = 3;
    private static final int[] EXTENSION_LIST = {0, 1, 2, 3, 4};
    public static final int EXTENSION_NIGHT = 4;
    public static final int NON_PROCESSING_INPUT_FORMAT = 34;
    public static final int PROCESSING_INPUT_FORMAT = 35;
    private static final String TAG = "CameraExtensionCharacteristics";
    private final String mCameraId;
    private final Map<String, CameraCharacteristics> mCharacteristicsMap;
    private final Map<String, CameraMetadataNative> mCharacteristicsMapNative;
    private final Context mContext;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Extension {
    }

    public CameraExtensionCharacteristics(Context context, String cameraId, Map<String, CameraCharacteristics> characteristicsMap) {
        this.mContext = context;
        this.mCameraId = cameraId;
        this.mCharacteristicsMap = characteristicsMap;
        this.mCharacteristicsMapNative = CameraExtensionUtils.getCharacteristicsMapNative(characteristicsMap);
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

    private static List<Size> generateSupportedSizes(List<SizeList> sizesList, Integer format, StreamConfigurationMap streamMap) {
        ArrayList<Size> ret = getSupportedSizes(sizesList, format);
        Size[] supportedSizes = streamMap.getOutputSizes(format.intValue());
        if (ret.isEmpty() && supportedSizes != null) {
            ret.addAll(Arrays.asList(supportedSizes));
        }
        return ret;
    }

    private static List<Size> generateJpegSupportedSizes(List<SizeList> sizesList, StreamConfigurationMap streamMap) {
        HashSet<Size> hashSet;
        ArrayList<Size> extensionSizes = getSupportedSizes(sizesList, 35);
        if (extensionSizes.isEmpty()) {
            hashSet = new HashSet<>(Arrays.asList(streamMap.getOutputSizes(35)));
        } else {
            hashSet = new HashSet<>(extensionSizes);
        }
        HashSet<Size> supportedSizes = hashSet;
        HashSet<Size> supportedJpegSizes = new HashSet<>(Arrays.asList(streamMap.getOutputSizes(256)));
        supportedSizes.retainAll(supportedJpegSizes);
        return new ArrayList(supportedSizes);
    }

    private static final class CameraExtensionManagerGlobal {
        private static final CameraExtensionManagerGlobal GLOBAL_CAMERA_MANAGER = new CameraExtensionManagerGlobal();
        private static final String PROXY_PACKAGE_NAME = "com.android.cameraextensions";
        private static final String PROXY_SERVICE_NAME = "com.android.cameraextensions.CameraExtensionsProxyService";
        private static final String TAG = "CameraExtensionManagerGlobal";
        private final Object mLock = new Object();
        private final int PROXY_SERVICE_DELAY_MS = 2000;
        private InitializerFuture mInitFuture = null;
        private ServiceConnection mConnection = null;
        private int mConnectionCount = 0;
        private ICameraExtensionsProxyService mProxy = null;
        private boolean mSupportsAdvancedExtensions = false;

        private CameraExtensionManagerGlobal() {
        }

        public static CameraExtensionManagerGlobal get() {
            return GLOBAL_CAMERA_MANAGER;
        }

        private void releaseProxyConnectionLocked(Context ctx) {
            ServiceConnection serviceConnection = this.mConnection;
            if (serviceConnection != null) {
                ctx.unbindService(serviceConnection);
                this.mConnection = null;
                this.mProxy = null;
                this.mConnectionCount = 0;
            }
        }

        private void connectToProxyLocked(Context ctx) {
            if (this.mConnection == null) {
                Intent intent = new Intent();
                intent.setClassName(PROXY_PACKAGE_NAME, PROXY_SERVICE_NAME);
                String vendorProxyPackage = SystemProperties.get("ro.vendor.camera.extensions.package");
                String vendorProxyService = SystemProperties.get("ro.vendor.camera.extensions.service");
                if (!vendorProxyPackage.isEmpty() && !vendorProxyService.isEmpty()) {
                    Log.m100v(TAG, "Choosing the vendor camera extensions proxy package: " + vendorProxyPackage);
                    Log.m100v(TAG, "Choosing the vendor camera extensions proxy service: " + vendorProxyService);
                    intent.setClassName(vendorProxyPackage, vendorProxyService);
                }
                this.mInitFuture = new InitializerFuture();
                this.mConnection = new ServiceConnection() { // from class: android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.1
                    @Override // android.content.ServiceConnection
                    public void onServiceDisconnected(ComponentName component) {
                        CameraExtensionManagerGlobal.this.mConnection = null;
                        CameraExtensionManagerGlobal.this.mProxy = null;
                    }

                    @Override // android.content.ServiceConnection
                    public void onServiceConnected(ComponentName component, IBinder binder) {
                        CameraExtensionManagerGlobal.this.mProxy = ICameraExtensionsProxyService.Stub.asInterface(binder);
                        if (CameraExtensionManagerGlobal.this.mProxy == null) {
                            throw new IllegalStateException("Camera Proxy service is null");
                        }
                        try {
                            CameraExtensionManagerGlobal cameraExtensionManagerGlobal = CameraExtensionManagerGlobal.this;
                            cameraExtensionManagerGlobal.mSupportsAdvancedExtensions = cameraExtensionManagerGlobal.mProxy.advancedExtensionsSupported();
                        } catch (RemoteException e) {
                            Log.m96e(CameraExtensionManagerGlobal.TAG, "Remote IPC failed!");
                        }
                        CameraExtensionManagerGlobal.this.mInitFuture.setStatus(true);
                    }
                };
                ctx.bindService(intent, 1073741897, AsyncTask.THREAD_POOL_EXECUTOR, this.mConnection);
                try {
                    this.mInitFuture.get(2000L, TimeUnit.MILLISECONDS);
                } catch (TimeoutException e) {
                    Log.m96e(TAG, "Timed out while initializing proxy service!");
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
                    throw new TimeoutException("Failed to receive status after " + timeout + " " + unit);
                }
                if (this.mStatus == null) {
                    throw new AssertionError();
                }
                return this.mStatus;
            }
        }

        public boolean registerClient(Context ctx, IBinder token) {
            synchronized (this.mLock) {
                boolean ret = false;
                connectToProxyLocked(ctx);
                ICameraExtensionsProxyService iCameraExtensionsProxyService = this.mProxy;
                if (iCameraExtensionsProxyService == null) {
                    return false;
                }
                this.mConnectionCount++;
                try {
                    ret = iCameraExtensionsProxyService.registerClient(token);
                } catch (RemoteException e) {
                    Log.m96e(TAG, "Failed to initialize extension! Extension service does  not respond!");
                }
                if (!ret) {
                    this.mConnectionCount--;
                }
                if (this.mConnectionCount <= 0) {
                    releaseProxyConnectionLocked(ctx);
                }
                return ret;
            }
        }

        public void unregisterClient(Context ctx, IBinder token) {
            synchronized (this.mLock) {
                ICameraExtensionsProxyService iCameraExtensionsProxyService = this.mProxy;
                try {
                    if (iCameraExtensionsProxyService != null) {
                        try {
                            iCameraExtensionsProxyService.unregisterClient(token);
                        } catch (RemoteException e) {
                            Log.m96e(TAG, "Failed to de-initialize extension! Extension service does not respond!");
                            int i = this.mConnectionCount - 1;
                            this.mConnectionCount = i;
                            if (i <= 0) {
                            }
                        }
                    }
                } finally {
                    int i2 = this.mConnectionCount - 1;
                    this.mConnectionCount = i2;
                    if (i2 <= 0) {
                        releaseProxyConnectionLocked(ctx);
                    }
                }
            }
        }

        public void initializeSession(IInitializeSessionCallback cb) throws RemoteException {
            synchronized (this.mLock) {
                ICameraExtensionsProxyService iCameraExtensionsProxyService = this.mProxy;
                if (iCameraExtensionsProxyService != null) {
                    iCameraExtensionsProxyService.initializeSession(cb);
                }
            }
        }

        public void releaseSession() {
            synchronized (this.mLock) {
                ICameraExtensionsProxyService iCameraExtensionsProxyService = this.mProxy;
                if (iCameraExtensionsProxyService != null) {
                    try {
                        iCameraExtensionsProxyService.releaseSession();
                    } catch (RemoteException e) {
                        Log.m96e(TAG, "Failed to release session! Extension service does not respond!");
                    }
                }
            }
        }

        public boolean areAdvancedExtensionsSupported() {
            return this.mSupportsAdvancedExtensions;
        }

        public IPreviewExtenderImpl initializePreviewExtension(int extensionType) throws RemoteException {
            synchronized (this.mLock) {
                ICameraExtensionsProxyService iCameraExtensionsProxyService = this.mProxy;
                if (iCameraExtensionsProxyService == null) {
                    return null;
                }
                return iCameraExtensionsProxyService.initializePreviewExtension(extensionType);
            }
        }

        public IImageCaptureExtenderImpl initializeImageExtension(int extensionType) throws RemoteException {
            synchronized (this.mLock) {
                ICameraExtensionsProxyService iCameraExtensionsProxyService = this.mProxy;
                if (iCameraExtensionsProxyService == null) {
                    return null;
                }
                return iCameraExtensionsProxyService.initializeImageExtension(extensionType);
            }
        }

        public IAdvancedExtenderImpl initializeAdvancedExtension(int extensionType) throws RemoteException {
            synchronized (this.mLock) {
                ICameraExtensionsProxyService iCameraExtensionsProxyService = this.mProxy;
                if (iCameraExtensionsProxyService == null) {
                    return null;
                }
                return iCameraExtensionsProxyService.initializeAdvancedExtension(extensionType);
            }
        }
    }

    public static boolean registerClient(Context ctx, IBinder token) {
        return CameraExtensionManagerGlobal.get().registerClient(ctx, token);
    }

    public static void unregisterClient(Context ctx, IBinder token) {
        CameraExtensionManagerGlobal.get().unregisterClient(ctx, token);
    }

    public static void initializeSession(IInitializeSessionCallback cb) throws RemoteException {
        CameraExtensionManagerGlobal.get().initializeSession(cb);
    }

    public static void releaseSession() {
        CameraExtensionManagerGlobal.get().releaseSession();
    }

    public static boolean areAdvancedExtensionsSupported() {
        return CameraExtensionManagerGlobal.get().areAdvancedExtensionsSupported();
    }

    public static boolean isExtensionSupported(String cameraId, int extensionType, Map<String, CameraMetadataNative> characteristicsMap) {
        if (areAdvancedExtensionsSupported()) {
            try {
                IAdvancedExtenderImpl extender = initializeAdvancedExtension(extensionType);
                return extender.isExtensionAvailable(cameraId, characteristicsMap);
            } catch (RemoteException e) {
                Log.m96e(TAG, "Failed to query extension availability! Extension service does not respond!");
                return false;
            }
        }
        try {
            Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders = initializeExtension(extensionType);
            try {
                if (extenders.first.isExtensionAvailable(cameraId, characteristicsMap.get(cameraId))) {
                    return extenders.second.isExtensionAvailable(cameraId, characteristicsMap.get(cameraId));
                }
                return false;
            } catch (RemoteException e2) {
                Log.m96e(TAG, "Failed to query extension availability! Extension service does not respond!");
                return false;
            }
        } catch (IllegalArgumentException e3) {
            return false;
        }
    }

    public static IAdvancedExtenderImpl initializeAdvancedExtension(int extensionType) {
        try {
            IAdvancedExtenderImpl extender = CameraExtensionManagerGlobal.get().initializeAdvancedExtension(extensionType);
            if (extender == null) {
                throw new IllegalArgumentException("Unknown extension: " + extensionType);
            }
            return extender;
        } catch (RemoteException e) {
            throw new IllegalStateException("Failed to initialize extension: " + extensionType);
        }
    }

    public static Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> initializeExtension(int extensionType) {
        try {
            IPreviewExtenderImpl previewExtender = CameraExtensionManagerGlobal.get().initializePreviewExtension(extensionType);
            IImageCaptureExtenderImpl imageExtender = CameraExtensionManagerGlobal.get().initializeImageExtension(extensionType);
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
        IBinder token = new Binder("CameraExtensionCharacteristics#getSupportedExtensions:" + this.mCameraId);
        boolean success = registerClient(this.mContext, token);
        if (!success) {
            return Collections.unmodifiableList(ret);
        }
        try {
            for (int extensionType : EXTENSION_LIST) {
                if (isExtensionSupported(this.mCameraId, extensionType, this.mCharacteristicsMapNative)) {
                    ret.add(Integer.valueOf(extensionType));
                }
            }
            unregisterClient(this.mContext, token);
            return Collections.unmodifiableList(ret);
        } catch (Throwable th) {
            unregisterClient(this.mContext, token);
            throw th;
        }
    }

    public boolean isPostviewAvailable(int extension) {
        IBinder token = new Binder("CameraExtensionCharacteristics#isPostviewAvailable:" + this.mCameraId);
        boolean success = registerClient(this.mContext, token);
        try {
            if (!success) {
                throw new IllegalArgumentException("Unsupported extensions");
            }
            try {
                if (!isExtensionSupported(this.mCameraId, extension, this.mCharacteristicsMapNative)) {
                    throw new IllegalArgumentException("Unsupported extension");
                }
                if (areAdvancedExtensionsSupported()) {
                    IAdvancedExtenderImpl extender = initializeAdvancedExtension(extension);
                    extender.init(this.mCameraId, this.mCharacteristicsMapNative);
                    return extender.isPostviewAvailable();
                }
                Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders = initializeExtension(extension);
                IImageCaptureExtenderImpl iImageCaptureExtenderImpl = extenders.second;
                String str = this.mCameraId;
                iImageCaptureExtenderImpl.init(str, this.mCharacteristicsMapNative.get(str));
                return extenders.second.isPostviewAvailable();
            } catch (RemoteException e) {
                Log.m96e(TAG, "Failed to query the extension for postview availability! Extension service does not respond!");
                unregisterClient(this.mContext, token);
                return false;
            }
        } finally {
            unregisterClient(this.mContext, token);
        }
    }

    public List<Size> getPostviewSupportedSizes(int extension, Size captureSize, int format) {
        IBinder token = new Binder("CameraExtensionCharacteristics#getPostviewSupportedSizes:" + this.mCameraId);
        boolean success = registerClient(this.mContext, token);
        try {
            if (!success) {
                throw new IllegalArgumentException("Unsupported extensions");
            }
            if (!isExtensionSupported(this.mCameraId, extension, this.mCharacteristicsMapNative)) {
                throw new IllegalArgumentException("Unsupported extension");
            }
            android.hardware.camera2.extension.Size sz = new android.hardware.camera2.extension.Size();
            sz.width = captureSize.getWidth();
            sz.height = captureSize.getHeight();
            StreamConfigurationMap streamMap = (StreamConfigurationMap) this.mCharacteristicsMap.get(this.mCameraId).get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (areAdvancedExtensionsSupported()) {
                switch (format) {
                    case 35:
                    case 256:
                        IAdvancedExtenderImpl extender = initializeAdvancedExtension(extension);
                        extender.init(this.mCameraId, this.mCharacteristicsMapNative);
                        return generateSupportedSizes(extender.getSupportedPostviewResolutions(sz), Integer.valueOf(format), streamMap);
                    default:
                        throw new IllegalArgumentException("Unsupported format: " + format);
                }
            }
            Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders = initializeExtension(extension);
            IImageCaptureExtenderImpl iImageCaptureExtenderImpl = extenders.second;
            String str = this.mCameraId;
            iImageCaptureExtenderImpl.init(str, this.mCharacteristicsMapNative.get(str));
            if (extenders.second.getCaptureProcessor() == null || !isPostviewAvailable(extension)) {
                throw new IllegalArgumentException("Extension does not support postview feature");
            }
            if (format == 35) {
                return generateSupportedSizes(extenders.second.getSupportedPostviewResolutions(sz), Integer.valueOf(format), streamMap);
            }
            if (format == 256) {
                return generateJpegSupportedSizes(extenders.second.getSupportedPostviewResolutions(sz), streamMap);
            }
            throw new IllegalArgumentException("Unsupported format: " + format);
        } catch (RemoteException e) {
            Log.m96e(TAG, "Failed to query the extension postview supported sizes! Extension service does not respond!");
            return Collections.emptyList();
        } finally {
            unregisterClient(this.mContext, token);
        }
    }

    public <T> List<Size> getExtensionSupportedSizes(int extension, Class<T> klass) {
        if (!isOutputSupportedFor(klass)) {
            return new ArrayList();
        }
        IBinder token = new Binder("CameraExtensionCharacteristics#getExtensionSupportedSizes:" + this.mCameraId);
        boolean success = registerClient(this.mContext, token);
        try {
            if (!success) {
                throw new IllegalArgumentException("Unsupported extensions");
            }
            if (!isExtensionSupported(this.mCameraId, extension, this.mCharacteristicsMapNative)) {
                throw new IllegalArgumentException("Unsupported extension");
            }
            StreamConfigurationMap streamMap = (StreamConfigurationMap) this.mCharacteristicsMap.get(this.mCameraId).get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (areAdvancedExtensionsSupported()) {
                IAdvancedExtenderImpl extender = initializeAdvancedExtension(extension);
                extender.init(this.mCameraId, this.mCharacteristicsMapNative);
                return generateSupportedSizes(extender.getSupportedPreviewOutputResolutions(this.mCameraId), 34, streamMap);
            }
            Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders = initializeExtension(extension);
            IPreviewExtenderImpl iPreviewExtenderImpl = extenders.first;
            String str = this.mCameraId;
            iPreviewExtenderImpl.init(str, this.mCharacteristicsMapNative.get(str));
            return generateSupportedSizes(extenders.first.getSupportedResolutions(), 34, streamMap);
        } catch (RemoteException e) {
            Log.m96e(TAG, "Failed to query the extension supported sizes! Extension service does not respond!");
            return new ArrayList();
        } finally {
            unregisterClient(this.mContext, token);
        }
    }

    public List<Size> getExtensionSupportedSizes(int extension, int format) {
        try {
            IBinder token = new Binder("CameraExtensionCharacteristics#getExtensionSupportedSizes:" + this.mCameraId);
            boolean success = registerClient(this.mContext, token);
            if (!success) {
                throw new IllegalArgumentException("Unsupported extensions");
            }
            try {
                if (!isExtensionSupported(this.mCameraId, extension, this.mCharacteristicsMapNative)) {
                    throw new IllegalArgumentException("Unsupported extension");
                }
                StreamConfigurationMap streamMap = (StreamConfigurationMap) this.mCharacteristicsMap.get(this.mCameraId).get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (areAdvancedExtensionsSupported()) {
                    switch (format) {
                        case 35:
                        case 256:
                            IAdvancedExtenderImpl extender = initializeAdvancedExtension(extension);
                            extender.init(this.mCameraId, this.mCharacteristicsMapNative);
                            return generateSupportedSizes(extender.getSupportedCaptureOutputResolutions(this.mCameraId), Integer.valueOf(format), streamMap);
                        default:
                            throw new IllegalArgumentException("Unsupported format: " + format);
                    }
                }
                if (format == 35) {
                    Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders = initializeExtension(extension);
                    IImageCaptureExtenderImpl iImageCaptureExtenderImpl = extenders.second;
                    String str = this.mCameraId;
                    iImageCaptureExtenderImpl.init(str, this.mCharacteristicsMapNative.get(str));
                    return extenders.second.getCaptureProcessor() == null ? new ArrayList() : generateSupportedSizes(extenders.second.getSupportedResolutions(), Integer.valueOf(format), streamMap);
                }
                if (format != 256) {
                    throw new IllegalArgumentException("Unsupported format: " + format);
                }
                Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders2 = initializeExtension(extension);
                IImageCaptureExtenderImpl iImageCaptureExtenderImpl2 = extenders2.second;
                String str2 = this.mCameraId;
                iImageCaptureExtenderImpl2.init(str2, this.mCharacteristicsMapNative.get(str2));
                return extenders2.second.getCaptureProcessor() != null ? generateJpegSupportedSizes(extenders2.second.getSupportedResolutions(), streamMap) : generateSupportedSizes(null, Integer.valueOf(format), streamMap);
            } finally {
                unregisterClient(this.mContext, token);
            }
        } catch (RemoteException e) {
            Log.m96e(TAG, "Failed to query the extension supported sizes! Extension service does not respond!");
            return new ArrayList();
        }
    }

    public Range<Long> getEstimatedCaptureLatencyRangeMillis(int extension, Size captureOutputSize, int format) {
        switch (format) {
            case 35:
            case 256:
                IBinder token = new Binder("CameraExtensionCharacteristics#getEstimatedCaptureLatencyRangeMillis:" + this.mCameraId);
                boolean success = registerClient(this.mContext, token);
                if (!success) {
                    throw new IllegalArgumentException("Unsupported extensions");
                }
                try {
                    try {
                    } catch (RemoteException e) {
                        Log.m96e(TAG, "Failed to query the extension capture latency! Extension service does not respond!");
                    }
                    if (!isExtensionSupported(this.mCameraId, extension, this.mCharacteristicsMapNative)) {
                        throw new IllegalArgumentException("Unsupported extension");
                    }
                    android.hardware.camera2.extension.Size sz = new android.hardware.camera2.extension.Size();
                    sz.width = captureOutputSize.getWidth();
                    sz.height = captureOutputSize.getHeight();
                    if (areAdvancedExtensionsSupported()) {
                        IAdvancedExtenderImpl extender = initializeAdvancedExtension(extension);
                        extender.init(this.mCameraId, this.mCharacteristicsMapNative);
                        LatencyRange latencyRange = extender.getEstimatedCaptureLatencyRange(this.mCameraId, sz, format);
                        if (latencyRange != null) {
                            return new Range<>(Long.valueOf(latencyRange.min), Long.valueOf(latencyRange.max));
                        }
                    } else {
                        Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders = initializeExtension(extension);
                        IImageCaptureExtenderImpl iImageCaptureExtenderImpl = extenders.second;
                        String str = this.mCameraId;
                        iImageCaptureExtenderImpl.init(str, this.mCharacteristicsMapNative.get(str));
                        if (format == 35 && extenders.second.getCaptureProcessor() == null) {
                            return null;
                        }
                        if (format == 256 && extenders.second.getCaptureProcessor() != null) {
                            return null;
                        }
                        LatencyRange latencyRange2 = extenders.second.getEstimatedCaptureLatencyRange(sz);
                        if (latencyRange2 != null) {
                            return new Range<>(Long.valueOf(latencyRange2.min), Long.valueOf(latencyRange2.max));
                        }
                    }
                    return null;
                } finally {
                    unregisterClient(this.mContext, token);
                }
            default:
                throw new IllegalArgumentException("Unsupported format: " + format);
        }
    }

    public boolean isCaptureProcessProgressAvailable(int extension) {
        IBinder token = new Binder("CameraExtensionCharacteristics#isCaptureProcessProgressAvailable:" + this.mCameraId);
        boolean success = registerClient(this.mContext, token);
        try {
            if (!success) {
                throw new IllegalArgumentException("Unsupported extensions");
            }
            try {
                if (!isExtensionSupported(this.mCameraId, extension, this.mCharacteristicsMapNative)) {
                    throw new IllegalArgumentException("Unsupported extension");
                }
                if (areAdvancedExtensionsSupported()) {
                    IAdvancedExtenderImpl extender = initializeAdvancedExtension(extension);
                    extender.init(this.mCameraId, this.mCharacteristicsMapNative);
                    return extender.isCaptureProcessProgressAvailable();
                }
                Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders = initializeExtension(extension);
                IImageCaptureExtenderImpl iImageCaptureExtenderImpl = extenders.second;
                String str = this.mCameraId;
                iImageCaptureExtenderImpl.init(str, this.mCharacteristicsMapNative.get(str));
                return extenders.second.isCaptureProcessProgressAvailable();
            } catch (RemoteException e) {
                Log.m96e(TAG, "Failed to query the extension progress callbacks! Extension service does not respond!");
                unregisterClient(this.mContext, token);
                return false;
            }
        } finally {
            unregisterClient(this.mContext, token);
        }
    }

    public Set<CaptureRequest.Key> getAvailableCaptureRequestKeys(int extension) {
        CameraMetadataNative captureRequestMeta;
        IBinder token = new Binder("CameraExtensionCharacteristics#getAvailableCaptureRequestKeys:" + this.mCameraId);
        boolean success = registerClient(this.mContext, token);
        if (!success) {
            throw new IllegalArgumentException("Unsupported extensions");
        }
        HashSet<CaptureRequest.Key> ret = new HashSet<>();
        try {
            try {
                if (!isExtensionSupported(this.mCameraId, extension, this.mCharacteristicsMapNative)) {
                    throw new IllegalArgumentException("Unsupported extension");
                }
                if (areAdvancedExtensionsSupported()) {
                    IAdvancedExtenderImpl extender = initializeAdvancedExtension(extension);
                    extender.init(this.mCameraId, this.mCharacteristicsMapNative);
                    captureRequestMeta = extender.getAvailableCaptureRequestKeys(this.mCameraId);
                } else {
                    Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders = initializeExtension(extension);
                    IImageCaptureExtenderImpl iImageCaptureExtenderImpl = extenders.second;
                    String str = this.mCameraId;
                    iImageCaptureExtenderImpl.onInit(token, str, this.mCharacteristicsMapNative.get(str));
                    IImageCaptureExtenderImpl iImageCaptureExtenderImpl2 = extenders.second;
                    String str2 = this.mCameraId;
                    iImageCaptureExtenderImpl2.init(str2, this.mCharacteristicsMapNative.get(str2));
                    captureRequestMeta = extenders.second.getAvailableCaptureRequestKeys();
                    extenders.second.onDeInit(token);
                }
                if (captureRequestMeta != null) {
                    int[] requestKeys = (int[]) captureRequestMeta.get(CameraCharacteristics.REQUEST_AVAILABLE_REQUEST_KEYS);
                    if (requestKeys == null) {
                        throw new AssertionError("android.request.availableRequestKeys must be non-null in the characteristics");
                    }
                    CameraCharacteristics requestChars = new CameraCharacteristics(captureRequestMeta);
                    ret.addAll(requestChars.getAvailableKeyList(CaptureRequest.class, crKeyTyped, requestKeys, true));
                }
                if (!ret.contains(CaptureRequest.JPEG_QUALITY)) {
                    ret.add(CaptureRequest.JPEG_QUALITY);
                }
                if (!ret.contains(CaptureRequest.JPEG_ORIENTATION)) {
                    ret.add(CaptureRequest.JPEG_ORIENTATION);
                }
                unregisterClient(this.mContext, token);
                return Collections.unmodifiableSet(ret);
            } catch (RemoteException e) {
                throw new IllegalStateException("Failed to query the available capture request keys!");
            }
        } catch (Throwable th) {
            unregisterClient(this.mContext, token);
            throw th;
        }
    }

    public Set<CaptureResult.Key> getAvailableCaptureResultKeys(int extension) {
        CameraMetadataNative captureResultMeta;
        IBinder token = new Binder("CameraExtensionCharacteristics#getAvailableCaptureResultKeys:" + this.mCameraId);
        boolean success = registerClient(this.mContext, token);
        if (!success) {
            throw new IllegalArgumentException("Unsupported extensions");
        }
        HashSet<CaptureResult.Key> ret = new HashSet<>();
        try {
            try {
                if (!isExtensionSupported(this.mCameraId, extension, this.mCharacteristicsMapNative)) {
                    throw new IllegalArgumentException("Unsupported extension");
                }
                if (areAdvancedExtensionsSupported()) {
                    IAdvancedExtenderImpl extender = initializeAdvancedExtension(extension);
                    extender.init(this.mCameraId, this.mCharacteristicsMapNative);
                    captureResultMeta = extender.getAvailableCaptureResultKeys(this.mCameraId);
                } else {
                    Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders = initializeExtension(extension);
                    IImageCaptureExtenderImpl iImageCaptureExtenderImpl = extenders.second;
                    String str = this.mCameraId;
                    iImageCaptureExtenderImpl.onInit(token, str, this.mCharacteristicsMapNative.get(str));
                    IImageCaptureExtenderImpl iImageCaptureExtenderImpl2 = extenders.second;
                    String str2 = this.mCameraId;
                    iImageCaptureExtenderImpl2.init(str2, this.mCharacteristicsMapNative.get(str2));
                    captureResultMeta = extenders.second.getAvailableCaptureResultKeys();
                    extenders.second.onDeInit(token);
                }
                if (captureResultMeta != null) {
                    int[] resultKeys = (int[]) captureResultMeta.get(CameraCharacteristics.REQUEST_AVAILABLE_RESULT_KEYS);
                    if (resultKeys == null) {
                        throw new AssertionError("android.request.availableResultKeys must be non-null in the characteristics");
                    }
                    CameraCharacteristics resultChars = new CameraCharacteristics(captureResultMeta);
                    ret.addAll(resultChars.getAvailableKeyList(CaptureResult.class, crKeyTyped, resultKeys, true));
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
                unregisterClient(this.mContext, token);
                return Collections.unmodifiableSet(ret);
            } catch (RemoteException e) {
                throw new IllegalStateException("Failed to query the available capture result keys!");
            }
        } catch (Throwable th) {
            unregisterClient(this.mContext, token);
            throw th;
        }
    }
}
