package android.hardware;

import android.app.ActivityThread;
import android.companion.virtual.VirtualDeviceManager;
import android.content.AttributionSource;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.IAudioService;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.system.OsConstants;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.android.internal.R;
import com.android.internal.app.IAppOpsCallback;
import com.android.internal.app.IAppOpsService;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

@Deprecated
/* loaded from: classes2.dex */
public class Camera {
    public static final String ACTION_NEW_PICTURE = "android.hardware.action.NEW_PICTURE";
    public static final String ACTION_NEW_VIDEO = "android.hardware.action.NEW_VIDEO";
    public static final int CAMERA_ERROR_DISABLED = 3;
    public static final int CAMERA_ERROR_EVICTED = 2;
    public static final int CAMERA_ERROR_SERVER_DIED = 100;
    public static final int CAMERA_ERROR_UNKNOWN = 1;
    private static final int CAMERA_FACE_DETECTION_HW = 0;
    private static final int CAMERA_FACE_DETECTION_SW = 1;
    public static final int CAMERA_HAL_API_VERSION_1_0 = 256;
    public static final int CAMERA_HAL_API_VERSION_3_0 = 768;
    private static final int CAMERA_MSG_COMPRESSED_IMAGE = 256;
    private static final int CAMERA_MSG_ERROR = 1;
    private static final int CAMERA_MSG_FOCUS = 4;
    private static final int CAMERA_MSG_FOCUS_MOVE = 2048;
    private static final int CAMERA_MSG_POSTVIEW_FRAME = 64;
    private static final int CAMERA_MSG_PREVIEW_FRAME = 16;
    private static final int CAMERA_MSG_PREVIEW_METADATA = 1024;
    private static final int CAMERA_MSG_RAW_IMAGE = 128;
    private static final int CAMERA_MSG_RAW_IMAGE_NOTIFY = 512;
    private static final int CAMERA_MSG_SHUTTER = 2;
    private static final int CAMERA_MSG_VIDEO_FRAME = 32;
    private static final int CAMERA_MSG_ZOOM = 8;
    private static final int NO_ERROR = 0;
    private static final String TAG = "Camera";
    private IAppOpsService mAppOps;
    private IAppOpsCallback mAppOpsCallback;
    private AutoFocusCallback mAutoFocusCallback;
    private AutoFocusMoveCallback mAutoFocusMoveCallback;
    private ErrorCallback mDetailedErrorCallback;
    private ErrorCallback mErrorCallback;
    private EventHandler mEventHandler;
    private FaceDetectionListener mFaceListener;
    private PictureCallback mJpegCallback;
    private long mNativeContext;
    private boolean mOneShot;
    private PictureCallback mPostviewCallback;
    private PreviewCallback mPreviewCallback;
    private PictureCallback mRawImageCallback;
    private ShutterCallback mShutterCallback;
    private boolean mUsingPreviewAllocation;
    private boolean mWithBuffer;
    private OnZoomChangeListener mZoomListener;
    private boolean mFaceDetectionRunning = false;
    private final Object mAutoFocusCallbackLock = new Object();
    private final Object mShutterSoundLock = new Object();
    private boolean mHasAppOpsPlayAudio = true;
    private boolean mShutterSoundEnabledFromApp = true;

    @Deprecated
    public interface AutoFocusCallback {
        void onAutoFocus(boolean z, Camera camera);
    }

    @Deprecated
    public interface AutoFocusMoveCallback {
        void onAutoFocusMoving(boolean z, Camera camera);
    }

    @Deprecated
    public static class CameraInfo {
        public static final int CAMERA_FACING_BACK = 0;
        public static final int CAMERA_FACING_FRONT = 1;
        public boolean canDisableShutterSound;
        public int facing;
        public int orientation;
    }

    @Deprecated
    public interface ErrorCallback {
        void onError(int i, Camera camera);
    }

    @Deprecated
    public static class Face {
        public Rect rect;
        public int score;
        public int id = -1;
        public Point leftEye = null;
        public Point rightEye = null;
        public Point mouth = null;
    }

    @Deprecated
    public interface FaceDetectionListener {
        void onFaceDetection(Face[] faceArr, Camera camera);
    }

    @Deprecated
    public interface OnZoomChangeListener {
        void onZoomChange(int i, boolean z, Camera camera);
    }

    @Deprecated
    public interface PictureCallback {
        void onPictureTaken(byte[] bArr, Camera camera);
    }

    @Deprecated
    public interface PreviewCallback {
        void onPreviewFrame(byte[] bArr, Camera camera);
    }

    @Deprecated
    public interface ShutterCallback {
        void onShutter();
    }

    private final native void _addCallbackBuffer(byte[] bArr, int i);

    private final native boolean _enableShutterSound(boolean z);

    private static native void _getCameraInfo(int i, int i2, Parcel parcel, int i3, CameraInfo cameraInfo);

    private static native int _getNumberOfCameras(Parcel parcel, int i);

    private final native void _startFaceDetection(int i);

    private final native void _stopFaceDetection();

    private final native void _stopPreview();

    public static boolean checkInitErrors(int i) {
        return i != 0;
    }

    private native void enableFocusMoveCallback(int i);

    private final native void native_autoFocus();

    private final native void native_cancelAutoFocus();

    private final native String native_getParameters();

    private final native void native_release();

    private final native void native_setParameters(String str);

    private native int native_setup(Object obj, int i, int i2, boolean z, Parcel parcel, int i3);

    private final native void native_takePicture(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void setHasPreviewCallback(boolean z, boolean z2);

    private final native void setPreviewCallbackSurface(Surface surface);

    public final native int getAudioRestriction();

    public final native void lock();

    public final native boolean previewEnabled();

    public final native void reconnect() throws IOException;

    public final native void setAudioRestriction(int i);

    public final native void setDisplayOrientation(int i);

    public final native void setPreviewSurface(Surface surface) throws IOException;

    public final native void setPreviewTexture(SurfaceTexture surfaceTexture) throws IOException;

    public final native void startPreview();

    public final native void startSmoothZoom(int i);

    public final native void stopSmoothZoom();

    public final native void unlock();

    public static int getNumberOfCameras() {
        return getNumberOfCameras(ActivityThread.currentApplication().getApplicationContext());
    }

    public static int getNumberOfCameras(Context context) {
        AttributionSource.ScopedParcelState scopedParcelStateAsScopedParcelState = context.getAttributionSource().asScopedParcelState();
        try {
            int i_getNumberOfCameras = _getNumberOfCameras(scopedParcelStateAsScopedParcelState.getParcel(), getDevicePolicyFromContext(context));
            if (scopedParcelStateAsScopedParcelState != null) {
                scopedParcelStateAsScopedParcelState.close();
            }
            return i_getNumberOfCameras;
        } catch (Throwable th) {
            if (scopedParcelStateAsScopedParcelState != null) {
                try {
                    scopedParcelStateAsScopedParcelState.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static void getCameraInfo(int i, CameraInfo cameraInfo) {
        Context applicationContext = ActivityThread.currentApplication().getApplicationContext();
        getCameraInfo(i, applicationContext, CameraManager.getRotationOverride(applicationContext), cameraInfo);
    }

    public static void getCameraInfo(int i, Context context, int i2, CameraInfo cameraInfo) {
        AttributionSource.ScopedParcelState scopedParcelStateAsScopedParcelState = context.getAttributionSource().asScopedParcelState();
        try {
            _getCameraInfo(i, i2, scopedParcelStateAsScopedParcelState.getParcel(), getDevicePolicyFromContext(context), cameraInfo);
            if (scopedParcelStateAsScopedParcelState != null) {
                scopedParcelStateAsScopedParcelState.close();
            }
            try {
                if (IAudioService.Stub.asInterface(ServiceManager.getService("audio")).isCameraSoundForced()) {
                    cameraInfo.canDisableShutterSound = false;
                }
            } catch (RemoteException unused) {
                Log.e("Camera", "Audio service is unavailable for queries");
            }
        } catch (Throwable th) {
            if (scopedParcelStateAsScopedParcelState != null) {
                try {
                    scopedParcelStateAsScopedParcelState.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static int getDevicePolicyFromContext(Context context) {
        VirtualDeviceManager virtualDeviceManager;
        if (context.getDeviceId() == 0 || (virtualDeviceManager = (VirtualDeviceManager) context.getSystemService(VirtualDeviceManager.class)) == null) {
            return 0;
        }
        return virtualDeviceManager.getDevicePolicy(context.getDeviceId(), 5);
    }

    public static Camera open(int i) {
        Context applicationContext = ActivityThread.currentApplication().getApplicationContext();
        return open(i, applicationContext, CameraManager.getRotationOverride(applicationContext));
    }

    public static Camera open(int i, Context context, int i2) {
        return new Camera(i, context, i2);
    }

    public static Camera open() {
        int numberOfCameras = getNumberOfCameras();
        CameraInfo cameraInfo = new CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == 0) {
                return open(i);
            }
        }
        return null;
    }

    public static Camera openLegacy(int i, int i2) {
        if (i2 < 768) {
            throw new IllegalArgumentException("Unsupported HAL version " + i2);
        }
        return open(i);
    }

    private int cameraInit(int i, Context context, int i2) throws Resources.NotFoundException {
        this.mShutterCallback = null;
        this.mRawImageCallback = null;
        this.mJpegCallback = null;
        this.mPreviewCallback = null;
        this.mPostviewCallback = null;
        this.mUsingPreviewAllocation = false;
        this.mZoomListener = null;
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper != null) {
            this.mEventHandler = new EventHandler(this, looperMyLooper);
        } else {
            Looper mainLooper = Looper.getMainLooper();
            if (mainLooper != null) {
                this.mEventHandler = new EventHandler(this, mainLooper);
            } else {
                this.mEventHandler = null;
            }
        }
        boolean zShouldForceSlowJpegMode = shouldForceSlowJpegMode();
        AttributionSource.ScopedParcelState scopedParcelStateAsScopedParcelState = context.getAttributionSource().asScopedParcelState();
        try {
            int iNative_setup = native_setup(new WeakReference(this), i, i2, zShouldForceSlowJpegMode, scopedParcelStateAsScopedParcelState.getParcel(), getDevicePolicyFromContext(context));
            if (scopedParcelStateAsScopedParcelState != null) {
                scopedParcelStateAsScopedParcelState.close();
            }
            return iNative_setup;
        } finally {
        }
    }

    private boolean shouldForceSlowJpegMode() throws Resources.NotFoundException {
        Context applicationContext = ActivityThread.currentApplication().getApplicationContext();
        String[] stringArray = applicationContext.getResources().getStringArray(R.array.config_forceSlowJpegModeList);
        String packageName = applicationContext.getPackageName();
        for (String str : stringArray) {
            if (TextUtils.equals(str, packageName)) {
                return true;
            }
        }
        return false;
    }

    Camera(int i, Context context, int i2) throws Resources.NotFoundException {
        Objects.requireNonNull(context);
        int iCameraInit = cameraInit(i, context, i2);
        if (checkInitErrors(iCameraInit)) {
            if (iCameraInit == (-OsConstants.EACCES)) {
                throw new RuntimeException("Fail to connect to camera service");
            }
            if (iCameraInit == (-OsConstants.ENODEV)) {
                throw new RuntimeException("Camera initialization failed");
            }
            throw new RuntimeException("Unknown camera error");
        }
        initAppOps();
    }

    public static Camera openUninitialized() {
        return new Camera();
    }

    Camera() {
    }

    private void initAppOps() {
        this.mAppOps = IAppOpsService.Stub.asInterface(ServiceManager.getService(Context.APP_OPS_SERVICE));
        updateAppOpsPlayAudio();
        this.mAppOpsCallback = new IAppOpsCallbackWrapper(this);
        try {
            this.mAppOps.startWatchingMode(28, ActivityThread.currentPackageName(), this.mAppOpsCallback);
        } catch (RemoteException e) {
            Log.e("Camera", "Error registering appOps callback", e);
            this.mHasAppOpsPlayAudio = false;
        }
    }

    private void releaseAppOps() {
        try {
            IAppOpsService iAppOpsService = this.mAppOps;
            if (iAppOpsService != null) {
                iAppOpsService.stopWatchingMode(this.mAppOpsCallback);
            }
        } catch (Exception unused) {
        }
    }

    protected void finalize() {
        release();
    }

    public final void release() {
        native_release();
        this.mFaceDetectionRunning = false;
        releaseAppOps();
    }

    public final void setPreviewDisplay(SurfaceHolder surfaceHolder) throws IOException {
        if (surfaceHolder != null) {
            setPreviewSurface(surfaceHolder.getSurface());
        } else {
            setPreviewSurface(null);
        }
    }

    public final void stopPreview() {
        _stopPreview();
        this.mFaceDetectionRunning = false;
        this.mShutterCallback = null;
        this.mRawImageCallback = null;
        this.mPostviewCallback = null;
        this.mJpegCallback = null;
        synchronized (this.mAutoFocusCallbackLock) {
            this.mAutoFocusCallback = null;
        }
        this.mAutoFocusMoveCallback = null;
    }

    public final void setPreviewCallback(PreviewCallback previewCallback) {
        this.mPreviewCallback = previewCallback;
        this.mOneShot = false;
        this.mWithBuffer = false;
        if (previewCallback != null) {
            this.mUsingPreviewAllocation = false;
        }
        setHasPreviewCallback(previewCallback != null, false);
    }

    public final void setOneShotPreviewCallback(PreviewCallback previewCallback) {
        this.mPreviewCallback = previewCallback;
        this.mOneShot = true;
        this.mWithBuffer = false;
        if (previewCallback != null) {
            this.mUsingPreviewAllocation = false;
        }
        setHasPreviewCallback(previewCallback != null, false);
    }

    public final void setPreviewCallbackWithBuffer(PreviewCallback previewCallback) {
        this.mPreviewCallback = previewCallback;
        this.mOneShot = false;
        this.mWithBuffer = true;
        if (previewCallback != null) {
            this.mUsingPreviewAllocation = false;
        }
        setHasPreviewCallback(previewCallback != null, true);
    }

    public final void addCallbackBuffer(byte[] bArr) {
        _addCallbackBuffer(bArr, 16);
    }

    public final void addRawImageCallbackBuffer(byte[] bArr) {
        addCallbackBuffer(bArr, 128);
    }

    private final void addCallbackBuffer(byte[] bArr, int i) {
        if (i != 16 && i != 128) {
            throw new IllegalArgumentException("Unsupported message type: " + i);
        }
        _addCallbackBuffer(bArr, i);
    }

    private class EventHandler extends Handler {
        private final Camera mCamera;

        public EventHandler(Camera camera, Looper looper) {
            super(looper);
            this.mCamera = camera;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            AutoFocusCallback autoFocusCallback;
            int i = message.what;
            if (i == 1) {
                Log.e("Camera", "Error " + message.arg1);
                if (Camera.this.mDetailedErrorCallback != null) {
                    Camera.this.mDetailedErrorCallback.onError(message.arg1, this.mCamera);
                    return;
                } else {
                    if (Camera.this.mErrorCallback != null) {
                        if (message.arg1 == 3) {
                            Camera.this.mErrorCallback.onError(2, this.mCamera);
                            return;
                        } else {
                            Camera.this.mErrorCallback.onError(message.arg1, this.mCamera);
                            return;
                        }
                    }
                    return;
                }
            }
            if (i == 2) {
                if (Camera.this.mShutterCallback != null) {
                    Camera.this.mShutterCallback.onShutter();
                    return;
                }
                return;
            }
            if (i == 4) {
                synchronized (Camera.this.mAutoFocusCallbackLock) {
                    autoFocusCallback = Camera.this.mAutoFocusCallback;
                }
                if (autoFocusCallback != null) {
                    autoFocusCallback.onAutoFocus(message.arg1 != 0, this.mCamera);
                    return;
                }
                return;
            }
            if (i == 8) {
                if (Camera.this.mZoomListener != null) {
                    Camera.this.mZoomListener.onZoomChange(message.arg1, message.arg2 != 0, this.mCamera);
                    return;
                }
                return;
            }
            if (i == 16) {
                PreviewCallback previewCallback = Camera.this.mPreviewCallback;
                if (previewCallback != null) {
                    if (Camera.this.mOneShot) {
                        Camera.this.mPreviewCallback = null;
                    } else if (!Camera.this.mWithBuffer) {
                        Camera.this.setHasPreviewCallback(true, false);
                    }
                    previewCallback.onPreviewFrame((byte[]) message.obj, this.mCamera);
                    return;
                }
                return;
            }
            if (i == 64) {
                if (Camera.this.mPostviewCallback != null) {
                    Camera.this.mPostviewCallback.onPictureTaken((byte[]) message.obj, this.mCamera);
                    return;
                }
                return;
            }
            if (i == 128) {
                if (Camera.this.mRawImageCallback != null) {
                    Camera.this.mRawImageCallback.onPictureTaken((byte[]) message.obj, this.mCamera);
                    return;
                }
                return;
            }
            if (i == 256) {
                if (Camera.this.mJpegCallback != null) {
                    Camera.this.mJpegCallback.onPictureTaken((byte[]) message.obj, this.mCamera);
                }
            } else if (i == 1024) {
                if (Camera.this.mFaceListener != null) {
                    Camera.this.mFaceListener.onFaceDetection((Face[]) message.obj, this.mCamera);
                }
            } else if (i == 2048) {
                if (Camera.this.mAutoFocusMoveCallback != null) {
                    Camera.this.mAutoFocusMoveCallback.onAutoFocusMoving(message.arg1 != 0, this.mCamera);
                }
            } else {
                Log.e("Camera", "Unknown message type " + message.what);
            }
        }
    }

    private static void postEventFromNative(Object obj, int i, int i2, int i3, Object obj2) {
        EventHandler eventHandler;
        Camera camera = (Camera) ((WeakReference) obj).get();
        if (camera == null || (eventHandler = camera.mEventHandler) == null) {
            return;
        }
        camera.mEventHandler.sendMessage(eventHandler.obtainMessage(i, i2, i3, obj2));
    }

    public final void autoFocus(AutoFocusCallback autoFocusCallback) {
        synchronized (this.mAutoFocusCallbackLock) {
            this.mAutoFocusCallback = autoFocusCallback;
        }
        native_autoFocus();
    }

    public final void cancelAutoFocus() {
        synchronized (this.mAutoFocusCallbackLock) {
            this.mAutoFocusCallback = null;
        }
        native_cancelAutoFocus();
        this.mEventHandler.removeMessages(4);
    }

    public void setAutoFocusMoveCallback(AutoFocusMoveCallback autoFocusMoveCallback) {
        this.mAutoFocusMoveCallback = autoFocusMoveCallback;
        enableFocusMoveCallback(autoFocusMoveCallback != null ? 1 : 0);
    }

    public final void takePicture(ShutterCallback shutterCallback, PictureCallback pictureCallback, PictureCallback pictureCallback2) {
        takePicture(shutterCallback, pictureCallback, null, pictureCallback2);
    }

    public final void takePicture(ShutterCallback shutterCallback, PictureCallback pictureCallback, PictureCallback pictureCallback2, PictureCallback pictureCallback3) {
        this.mShutterCallback = shutterCallback;
        this.mRawImageCallback = pictureCallback;
        this.mPostviewCallback = pictureCallback2;
        this.mJpegCallback = pictureCallback3;
        int i = shutterCallback != null ? 2 : 0;
        if (pictureCallback != null) {
            i |= 128;
        }
        if (pictureCallback2 != null) {
            i |= 64;
        }
        if (pictureCallback3 != null) {
            i |= 256;
        }
        native_takePicture(i);
        this.mFaceDetectionRunning = false;
    }

    public final boolean enableShutterSound(boolean z) {
        boolean z_enableShutterSound;
        boolean zIsCameraSoundForced = true;
        try {
            zIsCameraSoundForced = true ^ IAudioService.Stub.asInterface(ServiceManager.getService("audio")).isCameraSoundForced();
        } catch (RemoteException unused) {
            Log.e("Camera", "Audio service is unavailable for queries");
        }
        if (!z && !zIsCameraSoundForced) {
            return false;
        }
        synchronized (this.mShutterSoundLock) {
            this.mShutterSoundEnabledFromApp = z;
            z_enableShutterSound = _enableShutterSound(z);
            if (z && !this.mHasAppOpsPlayAudio) {
                Log.i("Camera", "Shutter sound is not allowed by AppOpsManager");
                if (zIsCameraSoundForced) {
                    _enableShutterSound(false);
                }
            }
        }
        return z_enableShutterSound;
    }

    public final boolean disableShutterSound() {
        return _enableShutterSound(false);
    }

    private static class IAppOpsCallbackWrapper extends IAppOpsCallback.Stub {
        private final WeakReference<Camera> mWeakCamera;

        IAppOpsCallbackWrapper(Camera camera) {
            this.mWeakCamera = new WeakReference<>(camera);
        }

        @Override // com.android.internal.app.IAppOpsCallback
        public void opChanged(int i, int i2, String str, String str2) {
            Camera camera;
            if (i != 28 || (camera = this.mWeakCamera.get()) == null) {
                return;
            }
            camera.updateAppOpsPlayAudio();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAppOpsPlayAudio() {
        synchronized (this.mShutterSoundLock) {
            boolean z = this.mHasAppOpsPlayAudio;
            try {
                IAppOpsService iAppOpsService = this.mAppOps;
                boolean z2 = true;
                if ((iAppOpsService != null ? iAppOpsService.checkAudioOperation(28, 13, Process.myUid(), ActivityThread.currentPackageName()) : 1) != 0) {
                    z2 = false;
                }
                this.mHasAppOpsPlayAudio = z2;
            } catch (RemoteException unused) {
                Log.e("Camera", "AppOpsService check audio operation failed");
                this.mHasAppOpsPlayAudio = false;
            }
            boolean z3 = this.mHasAppOpsPlayAudio;
            if (z != z3) {
                if (!z3) {
                    try {
                        if (IAudioService.Stub.asInterface(ServiceManager.getService("audio")).isCameraSoundForced()) {
                            return;
                        }
                    } catch (RemoteException unused2) {
                        Log.e("Camera", "Audio service is unavailable for queries");
                    }
                    _enableShutterSound(false);
                } else {
                    enableShutterSound(this.mShutterSoundEnabledFromApp);
                }
            }
        }
    }

    public final void setZoomChangeListener(OnZoomChangeListener onZoomChangeListener) {
        this.mZoomListener = onZoomChangeListener;
    }

    public final void setFaceDetectionListener(FaceDetectionListener faceDetectionListener) {
        this.mFaceListener = faceDetectionListener;
    }

    public final void startFaceDetection() {
        if (this.mFaceDetectionRunning) {
            throw new RuntimeException("Face detection is already running");
        }
        _startFaceDetection(0);
        this.mFaceDetectionRunning = true;
    }

    public final void stopFaceDetection() {
        _stopFaceDetection();
        this.mFaceDetectionRunning = false;
    }

    public final void setErrorCallback(ErrorCallback errorCallback) {
        this.mErrorCallback = errorCallback;
    }

    public final void setDetailedErrorCallback(ErrorCallback errorCallback) {
        this.mDetailedErrorCallback = errorCallback;
    }

    public void setParameters(Parameters parameters) {
        if (this.mUsingPreviewAllocation) {
            Size previewSize = parameters.getPreviewSize();
            Size previewSize2 = getParameters().getPreviewSize();
            if (previewSize.width != previewSize2.width || previewSize.height != previewSize2.height) {
                throw new IllegalStateException("Cannot change preview size while a preview allocation is configured.");
            }
        }
        native_setParameters(parameters.flatten());
    }

    public Parameters getParameters() {
        Parameters parameters = new Parameters();
        parameters.unflatten(native_getParameters());
        return parameters;
    }

    public static Parameters getEmptyParameters() {
        return new Parameters();
    }

    public static Parameters getParametersCopy(Parameters parameters) {
        if (parameters == null) {
            throw new NullPointerException("parameters must not be null");
        }
        Camera outer = parameters.getOuter();
        Objects.requireNonNull(outer);
        Parameters parameters2 = new Parameters();
        parameters2.copyFrom(parameters);
        return parameters2;
    }

    @Deprecated
    public class Size {
        public int height;
        public int width;

        public Size(Camera camera, int i, int i2) {
            this.width = i;
            this.height = i2;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Size)) {
                return false;
            }
            Size size = (Size) obj;
            return this.width == size.width && this.height == size.height;
        }

        public int hashCode() {
            return (this.width * 32713) + this.height;
        }
    }

    @Deprecated
    public static class Area {
        public Rect rect;
        public int weight;

        public Area(Rect rect, int i) {
            this.rect = rect;
            this.weight = i;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Area)) {
                return false;
            }
            Area area = (Area) obj;
            Rect rect = this.rect;
            if (rect == null) {
                if (area.rect != null) {
                    return false;
                }
            } else if (!rect.equals(area.rect)) {
                return false;
            }
            return this.weight == area.weight;
        }
    }

    @Deprecated
    public class Parameters {
        public static final String ANTIBANDING_50HZ = "50hz";
        public static final String ANTIBANDING_60HZ = "60hz";
        public static final String ANTIBANDING_AUTO = "auto";
        public static final String ANTIBANDING_OFF = "off";
        public static final String EFFECT_AQUA = "aqua";
        public static final String EFFECT_BLACKBOARD = "blackboard";
        public static final String EFFECT_MONO = "mono";
        public static final String EFFECT_NEGATIVE = "negative";
        public static final String EFFECT_NONE = "none";
        public static final String EFFECT_POSTERIZE = "posterize";
        public static final String EFFECT_SEPIA = "sepia";
        public static final String EFFECT_SOLARIZE = "solarize";
        public static final String EFFECT_WHITEBOARD = "whiteboard";
        private static final String FALSE = "false";
        public static final String FLASH_MODE_AUTO = "auto";
        public static final String FLASH_MODE_OFF = "off";
        public static final String FLASH_MODE_ON = "on";
        public static final String FLASH_MODE_RED_EYE = "red-eye";
        public static final String FLASH_MODE_TORCH = "torch";
        public static final int FOCUS_DISTANCE_FAR_INDEX = 2;
        public static final int FOCUS_DISTANCE_NEAR_INDEX = 0;
        public static final int FOCUS_DISTANCE_OPTIMAL_INDEX = 1;
        public static final String FOCUS_MODE_AUTO = "auto";
        public static final String FOCUS_MODE_CONTINUOUS_PICTURE = "continuous-picture";
        public static final String FOCUS_MODE_CONTINUOUS_VIDEO = "continuous-video";
        public static final String FOCUS_MODE_EDOF = "edof";
        public static final String FOCUS_MODE_FIXED = "fixed";
        public static final String FOCUS_MODE_INFINITY = "infinity";
        public static final String FOCUS_MODE_MACRO = "macro";
        private static final String KEY_ANTIBANDING = "antibanding";
        private static final String KEY_AUTO_EXPOSURE_LOCK = "auto-exposure-lock";
        private static final String KEY_AUTO_EXPOSURE_LOCK_SUPPORTED = "auto-exposure-lock-supported";
        private static final String KEY_AUTO_WHITEBALANCE_LOCK = "auto-whitebalance-lock";
        private static final String KEY_AUTO_WHITEBALANCE_LOCK_SUPPORTED = "auto-whitebalance-lock-supported";
        private static final String KEY_EFFECT = "effect";
        private static final String KEY_EXPOSURE_COMPENSATION = "exposure-compensation";
        private static final String KEY_EXPOSURE_COMPENSATION_STEP = "exposure-compensation-step";
        private static final String KEY_FLASH_MODE = "flash-mode";
        private static final String KEY_FOCAL_LENGTH = "focal-length";
        private static final String KEY_FOCUS_AREAS = "focus-areas";
        private static final String KEY_FOCUS_DISTANCES = "focus-distances";
        private static final String KEY_FOCUS_MODE = "focus-mode";
        private static final String KEY_GPS_ALTITUDE = "gps-altitude";
        private static final String KEY_GPS_LATITUDE = "gps-latitude";
        private static final String KEY_GPS_LONGITUDE = "gps-longitude";
        private static final String KEY_GPS_PROCESSING_METHOD = "gps-processing-method";
        private static final String KEY_GPS_TIMESTAMP = "gps-timestamp";
        private static final String KEY_HORIZONTAL_VIEW_ANGLE = "horizontal-view-angle";
        private static final String KEY_JPEG_QUALITY = "jpeg-quality";
        private static final String KEY_JPEG_THUMBNAIL_HEIGHT = "jpeg-thumbnail-height";
        private static final String KEY_JPEG_THUMBNAIL_QUALITY = "jpeg-thumbnail-quality";
        private static final String KEY_JPEG_THUMBNAIL_SIZE = "jpeg-thumbnail-size";
        private static final String KEY_JPEG_THUMBNAIL_WIDTH = "jpeg-thumbnail-width";
        private static final String KEY_MAX_EXPOSURE_COMPENSATION = "max-exposure-compensation";
        private static final String KEY_MAX_NUM_DETECTED_FACES_HW = "max-num-detected-faces-hw";
        private static final String KEY_MAX_NUM_DETECTED_FACES_SW = "max-num-detected-faces-sw";
        private static final String KEY_MAX_NUM_FOCUS_AREAS = "max-num-focus-areas";
        private static final String KEY_MAX_NUM_METERING_AREAS = "max-num-metering-areas";
        private static final String KEY_MAX_ZOOM = "max-zoom";
        private static final String KEY_METERING_AREAS = "metering-areas";
        private static final String KEY_MIN_EXPOSURE_COMPENSATION = "min-exposure-compensation";
        private static final String KEY_PICTURE_FORMAT = "picture-format";
        private static final String KEY_PICTURE_SIZE = "picture-size";
        private static final String KEY_PREFERRED_PREVIEW_SIZE_FOR_VIDEO = "preferred-preview-size-for-video";
        private static final String KEY_PREVIEW_FORMAT = "preview-format";
        private static final String KEY_PREVIEW_FPS_RANGE = "preview-fps-range";
        private static final String KEY_PREVIEW_FRAME_RATE = "preview-frame-rate";
        private static final String KEY_PREVIEW_SIZE = "preview-size";
        private static final String KEY_RECORDING_HINT = "recording-hint";
        private static final String KEY_ROTATION = "rotation";
        private static final String KEY_SCENE_MODE = "scene-mode";
        private static final String KEY_SMOOTH_ZOOM_SUPPORTED = "smooth-zoom-supported";
        private static final String KEY_VERTICAL_VIEW_ANGLE = "vertical-view-angle";
        private static final String KEY_VIDEO_SIZE = "video-size";
        private static final String KEY_VIDEO_SNAPSHOT_SUPPORTED = "video-snapshot-supported";
        private static final String KEY_VIDEO_STABILIZATION = "video-stabilization";
        private static final String KEY_VIDEO_STABILIZATION_SUPPORTED = "video-stabilization-supported";
        private static final String KEY_WHITE_BALANCE = "whitebalance";
        private static final String KEY_ZOOM = "zoom";
        private static final String KEY_ZOOM_RATIOS = "zoom-ratios";
        private static final String KEY_ZOOM_SUPPORTED = "zoom-supported";
        private static final String PIXEL_FORMAT_BAYER_RGGB = "bayer-rggb";
        private static final String PIXEL_FORMAT_JPEG = "jpeg";
        private static final String PIXEL_FORMAT_RGB565 = "rgb565";
        private static final String PIXEL_FORMAT_YUV420P = "yuv420p";
        private static final String PIXEL_FORMAT_YUV420SP = "yuv420sp";
        private static final String PIXEL_FORMAT_YUV422I = "yuv422i-yuyv";
        private static final String PIXEL_FORMAT_YUV422SP = "yuv422sp";
        public static final int PREVIEW_FPS_MAX_INDEX = 1;
        public static final int PREVIEW_FPS_MIN_INDEX = 0;
        public static final String SCENE_MODE_ACTION = "action";
        public static final String SCENE_MODE_AUTO = "auto";
        public static final String SCENE_MODE_BARCODE = "barcode";
        public static final String SCENE_MODE_BEACH = "beach";
        public static final String SCENE_MODE_CANDLELIGHT = "candlelight";
        public static final String SCENE_MODE_FIREWORKS = "fireworks";
        public static final String SCENE_MODE_HDR = "hdr";
        public static final String SCENE_MODE_LANDSCAPE = "landscape";
        public static final String SCENE_MODE_NIGHT = "night";
        public static final String SCENE_MODE_NIGHT_PORTRAIT = "night-portrait";
        public static final String SCENE_MODE_PARTY = "party";
        public static final String SCENE_MODE_PORTRAIT = "portrait";
        public static final String SCENE_MODE_SNOW = "snow";
        public static final String SCENE_MODE_SPORTS = "sports";
        public static final String SCENE_MODE_STEADYPHOTO = "steadyphoto";
        public static final String SCENE_MODE_SUNSET = "sunset";
        public static final String SCENE_MODE_THEATRE = "theatre";
        private static final String SUPPORTED_VALUES_SUFFIX = "-values";
        private static final String TRUE = "true";
        public static final String WHITE_BALANCE_AUTO = "auto";
        public static final String WHITE_BALANCE_CLOUDY_DAYLIGHT = "cloudy-daylight";
        public static final String WHITE_BALANCE_DAYLIGHT = "daylight";
        public static final String WHITE_BALANCE_FLUORESCENT = "fluorescent";
        public static final String WHITE_BALANCE_INCANDESCENT = "incandescent";
        public static final String WHITE_BALANCE_SHADE = "shade";
        public static final String WHITE_BALANCE_TWILIGHT = "twilight";
        public static final String WHITE_BALANCE_WARM_FLUORESCENT = "warm-fluorescent";
        private final LinkedHashMap<String, String> mMap;

        private Parameters() {
            this.mMap = new LinkedHashMap<>(64);
        }

        public void copyFrom(Parameters parameters) {
            if (parameters == null) {
                throw new NullPointerException("other must not be null");
            }
            this.mMap.putAll(parameters.mMap);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Camera getOuter() {
            return Camera.this;
        }

        public boolean same(Parameters parameters) {
            if (this == parameters) {
                return true;
            }
            return parameters != null && this.mMap.equals(parameters.mMap);
        }

        @Deprecated
        public void dump() {
            Log.e("Camera", "dump: size=" + this.mMap.size());
            for (String str : this.mMap.keySet()) {
                Log.e("Camera", "dump: " + str + "=" + this.mMap.get(str));
            }
        }

        public String flatten() {
            StringBuilder sb = new StringBuilder(128);
            for (String str : this.mMap.keySet()) {
                sb.append(str);
                sb.append("=");
                sb.append(this.mMap.get(str));
                sb.append(NavigationBarInflaterView.GRAVITY_SEPARATOR);
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }

        public void unflatten(String str) {
            this.mMap.clear();
            TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(';');
            simpleStringSplitter.setString(str);
            for (String str2 : simpleStringSplitter) {
                int iIndexOf = str2.indexOf(61);
                if (iIndexOf != -1) {
                    this.mMap.put(str2.substring(0, iIndexOf), str2.substring(iIndexOf + 1));
                }
            }
        }

        public void remove(String str) {
            this.mMap.remove(str);
        }

        public void set(String str, String str2) {
            if (str.indexOf(61) != -1 || str.indexOf(59) != -1 || str.indexOf(0) != -1) {
                Log.e("Camera", "Key \"" + str + "\" contains invalid character (= or ; or \\0)");
                return;
            }
            if (str2.indexOf(61) != -1 || str2.indexOf(59) != -1 || str2.indexOf(0) != -1) {
                Log.e("Camera", "Value \"" + str2 + "\" contains invalid character (= or ; or \\0)");
                return;
            }
            put(str, str2);
        }

        public void set(String str, int i) {
            put(str, Integer.toString(i));
        }

        private void put(String str, String str2) {
            this.mMap.remove(str);
            this.mMap.put(str, str2);
        }

        private void set(String str, List<Area> list) {
            if (list == null) {
                set(str, "(0,0,0,0,0)");
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                Area area = list.get(i);
                Rect rect = area.rect;
                sb.append('(');
                sb.append(rect.left);
                sb.append(',');
                sb.append(rect.top);
                sb.append(',');
                sb.append(rect.right);
                sb.append(',');
                sb.append(rect.bottom);
                sb.append(',');
                sb.append(area.weight);
                sb.append(')');
                if (i != list.size() - 1) {
                    sb.append(',');
                }
            }
            set(str, sb.toString());
        }

        public String get(String str) {
            return this.mMap.get(str);
        }

        public int getInt(String str) {
            return Integer.parseInt(this.mMap.get(str));
        }

        public void setPreviewSize(int i, int i2) {
            set(KEY_PREVIEW_SIZE, Integer.toString(i) + "x" + Integer.toString(i2));
        }

        public Size getPreviewSize() {
            return strToSize(get(KEY_PREVIEW_SIZE));
        }

        public List<Size> getSupportedPreviewSizes() {
            return splitSize(get("preview-size-values"));
        }

        public List<Size> getSupportedVideoSizes() {
            return splitSize(get("video-size-values"));
        }

        public Size getPreferredPreviewSizeForVideo() {
            return strToSize(get(KEY_PREFERRED_PREVIEW_SIZE_FOR_VIDEO));
        }

        public void setJpegThumbnailSize(int i, int i2) {
            set(KEY_JPEG_THUMBNAIL_WIDTH, i);
            set(KEY_JPEG_THUMBNAIL_HEIGHT, i2);
        }

        public Size getJpegThumbnailSize() {
            return new Size(Camera.this, getInt(KEY_JPEG_THUMBNAIL_WIDTH), getInt(KEY_JPEG_THUMBNAIL_HEIGHT));
        }

        public List<Size> getSupportedJpegThumbnailSizes() {
            return splitSize(get("jpeg-thumbnail-size-values"));
        }

        public void setJpegThumbnailQuality(int i) {
            set(KEY_JPEG_THUMBNAIL_QUALITY, i);
        }

        public int getJpegThumbnailQuality() {
            return getInt(KEY_JPEG_THUMBNAIL_QUALITY);
        }

        public void setJpegQuality(int i) {
            set(KEY_JPEG_QUALITY, i);
        }

        public int getJpegQuality() {
            return getInt(KEY_JPEG_QUALITY);
        }

        @Deprecated
        public void setPreviewFrameRate(int i) {
            set(KEY_PREVIEW_FRAME_RATE, i);
        }

        @Deprecated
        public int getPreviewFrameRate() {
            return getInt(KEY_PREVIEW_FRAME_RATE);
        }

        @Deprecated
        public List<Integer> getSupportedPreviewFrameRates() {
            return splitInt(get("preview-frame-rate-values"));
        }

        public void setPreviewFpsRange(int i, int i2) {
            set(KEY_PREVIEW_FPS_RANGE, "" + i + "," + i2);
        }

        public void getPreviewFpsRange(int[] iArr) {
            if (iArr == null || iArr.length != 2) {
                throw new IllegalArgumentException("range must be an array with two elements.");
            }
            splitInt(get(KEY_PREVIEW_FPS_RANGE), iArr);
        }

        public List<int[]> getSupportedPreviewFpsRange() {
            return splitRange(get("preview-fps-range-values"));
        }

        public void setPreviewFormat(int i) {
            String strCameraFormatForPixelFormat = cameraFormatForPixelFormat(i);
            if (strCameraFormatForPixelFormat == null) {
                throw new IllegalArgumentException("Invalid pixel_format=" + i);
            }
            set(KEY_PREVIEW_FORMAT, strCameraFormatForPixelFormat);
        }

        public int getPreviewFormat() {
            return pixelFormatForCameraFormat(get(KEY_PREVIEW_FORMAT));
        }

        public List<Integer> getSupportedPreviewFormats() {
            String str = get("preview-format-values");
            ArrayList arrayList = new ArrayList();
            Iterator<String> it = split(str).iterator();
            while (it.hasNext()) {
                int iPixelFormatForCameraFormat = pixelFormatForCameraFormat(it.next());
                if (iPixelFormatForCameraFormat != 0) {
                    arrayList.add(Integer.valueOf(iPixelFormatForCameraFormat));
                }
            }
            return arrayList;
        }

        public void setPictureSize(int i, int i2) {
            set(KEY_PICTURE_SIZE, Integer.toString(i) + "x" + Integer.toString(i2));
        }

        public Size getPictureSize() {
            return strToSize(get(KEY_PICTURE_SIZE));
        }

        public List<Size> getSupportedPictureSizes() {
            return splitSize(get("picture-size-values"));
        }

        public void setPictureFormat(int i) {
            String strCameraFormatForPixelFormat = cameraFormatForPixelFormat(i);
            if (strCameraFormatForPixelFormat == null) {
                throw new IllegalArgumentException("Invalid pixel_format=" + i);
            }
            set(KEY_PICTURE_FORMAT, strCameraFormatForPixelFormat);
        }

        public int getPictureFormat() {
            return pixelFormatForCameraFormat(get(KEY_PICTURE_FORMAT));
        }

        public List<Integer> getSupportedPictureFormats() {
            String str = get("picture-format-values");
            ArrayList arrayList = new ArrayList();
            Iterator<String> it = split(str).iterator();
            while (it.hasNext()) {
                int iPixelFormatForCameraFormat = pixelFormatForCameraFormat(it.next());
                if (iPixelFormatForCameraFormat != 0) {
                    arrayList.add(Integer.valueOf(iPixelFormatForCameraFormat));
                }
            }
            return arrayList;
        }

        private String cameraFormatForPixelFormat(int i) {
            if (i == 4) {
                return PIXEL_FORMAT_RGB565;
            }
            if (i == 20) {
                return PIXEL_FORMAT_YUV422I;
            }
            if (i == 256) {
                return PIXEL_FORMAT_JPEG;
            }
            if (i == 842094169) {
                return PIXEL_FORMAT_YUV420P;
            }
            if (i == 16) {
                return PIXEL_FORMAT_YUV422SP;
            }
            if (i != 17) {
                return null;
            }
            return PIXEL_FORMAT_YUV420SP;
        }

        private int pixelFormatForCameraFormat(String str) {
            if (str == null) {
                return 0;
            }
            if (str.equals(PIXEL_FORMAT_YUV422SP)) {
                return 16;
            }
            if (str.equals(PIXEL_FORMAT_YUV420SP)) {
                return 17;
            }
            if (str.equals(PIXEL_FORMAT_YUV422I)) {
                return 20;
            }
            if (str.equals(PIXEL_FORMAT_YUV420P)) {
                return 842094169;
            }
            if (str.equals(PIXEL_FORMAT_RGB565)) {
                return 4;
            }
            return str.equals(PIXEL_FORMAT_JPEG) ? 256 : 0;
        }

        public void setRotation(int i) {
            if (i == 0 || i == 90 || i == 180 || i == 270) {
                set("rotation", Integer.toString(i));
            } else {
                throw new IllegalArgumentException("Invalid rotation=" + i);
            }
        }

        public void setGpsLatitude(double d) {
            set(KEY_GPS_LATITUDE, Double.toString(d));
        }

        public void setGpsLongitude(double d) {
            set(KEY_GPS_LONGITUDE, Double.toString(d));
        }

        public void setGpsAltitude(double d) {
            set(KEY_GPS_ALTITUDE, Double.toString(d));
        }

        public void setGpsTimestamp(long j) {
            set(KEY_GPS_TIMESTAMP, Long.toString(j));
        }

        public void setGpsProcessingMethod(String str) {
            set(KEY_GPS_PROCESSING_METHOD, str);
        }

        public void removeGpsData() {
            remove(KEY_GPS_LATITUDE);
            remove(KEY_GPS_LONGITUDE);
            remove(KEY_GPS_ALTITUDE);
            remove(KEY_GPS_TIMESTAMP);
            remove(KEY_GPS_PROCESSING_METHOD);
        }

        public String getWhiteBalance() {
            return get(KEY_WHITE_BALANCE);
        }

        public void setWhiteBalance(String str) {
            if (same(str, get(KEY_WHITE_BALANCE))) {
                return;
            }
            set(KEY_WHITE_BALANCE, str);
            set(KEY_AUTO_WHITEBALANCE_LOCK, "false");
        }

        public List<String> getSupportedWhiteBalance() {
            return split(get("whitebalance-values"));
        }

        public String getColorEffect() {
            return get(KEY_EFFECT);
        }

        public void setColorEffect(String str) {
            set(KEY_EFFECT, str);
        }

        public List<String> getSupportedColorEffects() {
            return split(get("effect-values"));
        }

        public String getAntibanding() {
            return get(KEY_ANTIBANDING);
        }

        public void setAntibanding(String str) {
            set(KEY_ANTIBANDING, str);
        }

        public List<String> getSupportedAntibanding() {
            return split(get("antibanding-values"));
        }

        public String getSceneMode() {
            return get(KEY_SCENE_MODE);
        }

        public void setSceneMode(String str) {
            set(KEY_SCENE_MODE, str);
        }

        public List<String> getSupportedSceneModes() {
            return split(get("scene-mode-values"));
        }

        public String getFlashMode() {
            return get(KEY_FLASH_MODE);
        }

        public void setFlashMode(String str) {
            set(KEY_FLASH_MODE, str);
        }

        public List<String> getSupportedFlashModes() {
            return split(get("flash-mode-values"));
        }

        public String getFocusMode() {
            return get(KEY_FOCUS_MODE);
        }

        public void setFocusMode(String str) {
            set(KEY_FOCUS_MODE, str);
        }

        public List<String> getSupportedFocusModes() {
            return split(get("focus-mode-values"));
        }

        public float getFocalLength() {
            return Float.parseFloat(get(KEY_FOCAL_LENGTH));
        }

        public float getHorizontalViewAngle() {
            return Float.parseFloat(get(KEY_HORIZONTAL_VIEW_ANGLE));
        }

        public float getVerticalViewAngle() {
            return Float.parseFloat(get(KEY_VERTICAL_VIEW_ANGLE));
        }

        public int getExposureCompensation() {
            return getInt(KEY_EXPOSURE_COMPENSATION, 0);
        }

        public void setExposureCompensation(int i) {
            set(KEY_EXPOSURE_COMPENSATION, i);
        }

        public int getMaxExposureCompensation() {
            return getInt(KEY_MAX_EXPOSURE_COMPENSATION, 0);
        }

        public int getMinExposureCompensation() {
            return getInt(KEY_MIN_EXPOSURE_COMPENSATION, 0);
        }

        public float getExposureCompensationStep() {
            return getFloat(KEY_EXPOSURE_COMPENSATION_STEP, 0.0f);
        }

        public void setAutoExposureLock(boolean z) {
            set(KEY_AUTO_EXPOSURE_LOCK, z ? "true" : "false");
        }

        public boolean getAutoExposureLock() {
            return "true".equals(get(KEY_AUTO_EXPOSURE_LOCK));
        }

        public boolean isAutoExposureLockSupported() {
            return "true".equals(get(KEY_AUTO_EXPOSURE_LOCK_SUPPORTED));
        }

        public void setAutoWhiteBalanceLock(boolean z) {
            set(KEY_AUTO_WHITEBALANCE_LOCK, z ? "true" : "false");
        }

        public boolean getAutoWhiteBalanceLock() {
            return "true".equals(get(KEY_AUTO_WHITEBALANCE_LOCK));
        }

        public boolean isAutoWhiteBalanceLockSupported() {
            return "true".equals(get(KEY_AUTO_WHITEBALANCE_LOCK_SUPPORTED));
        }

        public int getZoom() {
            return getInt(KEY_ZOOM, 0);
        }

        public void setZoom(int i) {
            set(KEY_ZOOM, i);
        }

        public boolean isZoomSupported() {
            return "true".equals(get(KEY_ZOOM_SUPPORTED));
        }

        public int getMaxZoom() {
            return getInt(KEY_MAX_ZOOM, 0);
        }

        public List<Integer> getZoomRatios() {
            return splitInt(get(KEY_ZOOM_RATIOS));
        }

        public boolean isSmoothZoomSupported() {
            return "true".equals(get(KEY_SMOOTH_ZOOM_SUPPORTED));
        }

        public void getFocusDistances(float[] fArr) {
            if (fArr == null || fArr.length != 3) {
                throw new IllegalArgumentException("output must be a float array with three elements.");
            }
            splitFloat(get(KEY_FOCUS_DISTANCES), fArr);
        }

        public int getMaxNumFocusAreas() {
            return getInt(KEY_MAX_NUM_FOCUS_AREAS, 0);
        }

        public List<Area> getFocusAreas() {
            return splitArea(get(KEY_FOCUS_AREAS));
        }

        public void setFocusAreas(List<Area> list) {
            set(KEY_FOCUS_AREAS, list);
        }

        public int getMaxNumMeteringAreas() {
            return getInt(KEY_MAX_NUM_METERING_AREAS, 0);
        }

        public List<Area> getMeteringAreas() {
            return splitArea(get(KEY_METERING_AREAS));
        }

        public void setMeteringAreas(List<Area> list) {
            set(KEY_METERING_AREAS, list);
        }

        public int getMaxNumDetectedFaces() {
            return getInt(KEY_MAX_NUM_DETECTED_FACES_HW, 0);
        }

        public void setRecordingHint(boolean z) {
            set(KEY_RECORDING_HINT, z ? "true" : "false");
        }

        public boolean isVideoSnapshotSupported() {
            return "true".equals(get(KEY_VIDEO_SNAPSHOT_SUPPORTED));
        }

        public void setVideoStabilization(boolean z) {
            set(KEY_VIDEO_STABILIZATION, z ? "true" : "false");
        }

        public boolean getVideoStabilization() {
            return "true".equals(get(KEY_VIDEO_STABILIZATION));
        }

        public boolean isVideoStabilizationSupported() {
            return "true".equals(get(KEY_VIDEO_STABILIZATION_SUPPORTED));
        }

        private ArrayList<String> split(String str) {
            if (str == null) {
                return null;
            }
            TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(',');
            simpleStringSplitter.setString(str);
            ArrayList<String> arrayList = new ArrayList<>();
            Iterator<String> it = simpleStringSplitter.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
            return arrayList;
        }

        private ArrayList<Integer> splitInt(String str) {
            if (str == null) {
                return null;
            }
            TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(',');
            simpleStringSplitter.setString(str);
            ArrayList<Integer> arrayList = new ArrayList<>();
            Iterator<String> it = simpleStringSplitter.iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(Integer.parseInt(it.next())));
            }
            if (arrayList.size() == 0) {
                return null;
            }
            return arrayList;
        }

        private void splitInt(String str, int[] iArr) {
            if (str == null) {
                return;
            }
            TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(',');
            simpleStringSplitter.setString(str);
            Iterator<String> it = simpleStringSplitter.iterator();
            int i = 0;
            while (it.hasNext()) {
                iArr[i] = Integer.parseInt(it.next());
                i++;
            }
        }

        private void splitFloat(String str, float[] fArr) {
            if (str == null) {
                return;
            }
            TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(',');
            simpleStringSplitter.setString(str);
            Iterator<String> it = simpleStringSplitter.iterator();
            int i = 0;
            while (it.hasNext()) {
                fArr[i] = Float.parseFloat(it.next());
                i++;
            }
        }

        private float getFloat(String str, float f) {
            try {
                return Float.parseFloat(this.mMap.get(str));
            } catch (NumberFormatException unused) {
                return f;
            }
        }

        private int getInt(String str, int i) {
            try {
                return Integer.parseInt(this.mMap.get(str));
            } catch (NumberFormatException unused) {
                return i;
            }
        }

        private ArrayList<Size> splitSize(String str) {
            if (str == null) {
                return null;
            }
            TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(',');
            simpleStringSplitter.setString(str);
            ArrayList<Size> arrayList = new ArrayList<>();
            Iterator<String> it = simpleStringSplitter.iterator();
            while (it.hasNext()) {
                Size sizeStrToSize = strToSize(it.next());
                if (sizeStrToSize != null) {
                    arrayList.add(sizeStrToSize);
                }
            }
            if (arrayList.size() == 0) {
                return null;
            }
            return arrayList;
        }

        private Size strToSize(String str) {
            if (str == null) {
                return null;
            }
            int iIndexOf = str.indexOf(120);
            if (iIndexOf != -1) {
                return new Size(Camera.this, Integer.parseInt(str.substring(0, iIndexOf)), Integer.parseInt(str.substring(iIndexOf + 1)));
            }
            Log.e("Camera", "Invalid size parameter string=" + str);
            return null;
        }

        private ArrayList<int[]> splitRange(String str) {
            int iIndexOf;
            if (str == null || str.charAt(0) != '(' || str.charAt(str.length() - 1) != ')') {
                Log.e("Camera", "Invalid range list string=" + str);
                return null;
            }
            ArrayList<int[]> arrayList = new ArrayList<>();
            int i = 1;
            do {
                int[] iArr = new int[2];
                iIndexOf = str.indexOf("),(", i);
                if (iIndexOf == -1) {
                    iIndexOf = str.length() - 1;
                }
                splitInt(str.substring(i, iIndexOf), iArr);
                arrayList.add(iArr);
                i = iIndexOf + 3;
            } while (iIndexOf != str.length() - 1);
            if (arrayList.size() == 0) {
                return null;
            }
            return arrayList;
        }

        private ArrayList<Area> splitArea(String str) {
            int iIndexOf;
            if (str == null || str.charAt(0) != '(' || str.charAt(str.length() - 1) != ')') {
                Log.e("Camera", "Invalid area string=" + str);
                return null;
            }
            ArrayList<Area> arrayList = new ArrayList<>();
            int[] iArr = new int[5];
            int i = 1;
            do {
                iIndexOf = str.indexOf("),(", i);
                if (iIndexOf == -1) {
                    iIndexOf = str.length() - 1;
                }
                splitInt(str.substring(i, iIndexOf), iArr);
                arrayList.add(new Area(new Rect(iArr[0], iArr[1], iArr[2], iArr[3]), iArr[4]));
                i = iIndexOf + 3;
            } while (iIndexOf != str.length() - 1);
            if (arrayList.size() == 0) {
                return null;
            }
            if (arrayList.size() == 1) {
                Area area = arrayList.get(0);
                Rect rect = area.rect;
                if (rect.left == 0 && rect.top == 0 && rect.right == 0 && rect.bottom == 0 && area.weight == 0) {
                    return null;
                }
            }
            return arrayList;
        }

        private boolean same(String str, String str2) {
            if (str == null && str2 == null) {
                return true;
            }
            return str != null && str.equals(str2);
        }
    }
}
