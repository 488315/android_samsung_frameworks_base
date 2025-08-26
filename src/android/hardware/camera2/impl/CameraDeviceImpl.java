package android.hardware.camera2.impl;

import android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2;
import android.app.compat.CompatChanges;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraExtensionCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadataInfo;
import android.hardware.camera2.CameraOfflineSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.ICameraDeviceCallbacks;
import android.hardware.camera2.ICameraDeviceUser;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.impl.CameraOfflineSessionImpl;
import android.hardware.camera2.params.ExtensionSessionConfiguration;
import android.hardware.camera2.params.InputConfiguration;
import android.hardware.camera2.params.MultiResolutionStreamConfigurationMap;
import android.hardware.camera2.params.MultiResolutionStreamInfo;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.hardware.camera2.params.SharedSessionConfiguration;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.utils.SubmitInfo;
import android.hardware.camera2.utils.SurfaceUtils;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.SystemClock;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.util.SparseArray;
import android.view.Surface;
import com.android.internal.camera.flags.Flags;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

/* loaded from: classes2.dex */
public class CameraDeviceImpl extends CameraDevice implements IBinder.DeathRecipient {
    static final long CHECK_PARAMS_IN_IS_SESSION_CONFIGURATION_SUPPORTED = 320741775;
    private static final long NANO_PER_SECOND = 1000000000;
    private static final int REQUEST_ID_NONE = -1;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.1
        private static final ThreadFactory mFactory = Executors.defaultThreadFactory();

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread threadNewThread = mFactory.newThread(runnable);
            threadNewThread.setName("CameraDeviceExecutor");
            return threadNewThread;
        }
    };
    private final String TAG;
    private final int mAppTargetSdkVersion;
    private final CameraDevice.CameraDeviceSetup mCameraDeviceSetup;
    private final String mCameraId;
    private final CameraManager mCameraManager;
    private final CameraCharacteristics mCharacteristics;
    private final Context mContext;
    private CameraAdvancedExtensionSessionImpl mCurrentAdvancedExtensionSession;
    private CameraExtensionSessionImpl mCurrentExtensionSession;
    private CameraCaptureSessionCore mCurrentSession;
    private final CameraDevice.StateCallback mDeviceCallback;
    private final Executor mDeviceExecutor;
    private long mFMQReader;
    private int[] mFailedRepeatingRequestTypes;
    private boolean mIsPrimaryClient;
    private CameraOfflineSessionImpl mOfflineSessionImpl;
    private ExecutorService mOfflineSwitchService;
    private Map<String, CameraCharacteristics> mPhysicalIdsToChars;
    private ICameraDeviceUserWrapper mRemoteDevice;
    private int[] mRepeatingRequestTypes;
    private volatile StateCallbackKK mSessionStateCallback;
    private boolean mSharedMode;
    private final int mTotalPartialCount;
    private final boolean DEBUG = false;
    private boolean mRemoteDeviceInit = false;
    final Object mInterfaceLock = new Object();
    private final CameraDeviceCallbacks mCallbacks = new CameraDeviceCallbacks();
    private final AtomicBoolean mClosing = new AtomicBoolean();
    private boolean mInError = false;
    private boolean mIdle = true;
    private SparseArray<CaptureCallbackHolder> mCaptureCallbackMap = new SparseArray<>();
    private HashMap<Integer, Integer> mBatchOutputMap = new HashMap<>();
    private int mRepeatingRequestId = -1;
    private int mFailedRepeatingRequestId = -1;
    private AbstractMap.SimpleEntry<Integer, InputConfiguration> mConfiguredInput = new AbstractMap.SimpleEntry<>(-1, null);
    private final SparseArray<OutputConfiguration> mConfiguredOutputs = new SparseArray<>();
    private final HashSet<Integer> mOfflineSupport = new HashSet<>();
    private final List<RequestLastFrameNumbersHolder> mRequestLastFrameNumbersList = new ArrayList();
    private FrameNumberTracker mFrameNumberTracker = new FrameNumberTracker();
    private int mNextSessionId = 0;
    private final Runnable mCallOnOpened = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.2
        @Override // java.lang.Runnable
        public void run() {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
                if (stateCallbackKK != null) {
                    stateCallbackKK.onOpened(CameraDeviceImpl.this);
                }
                CameraDeviceImpl.this.mDeviceCallback.onOpened(CameraDeviceImpl.this);
            }
        }
    };
    private final Runnable mCallOnOpenedInSharedMode = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.3
        @Override // java.lang.Runnable
        public void run() {
            if (Flags.cameraMultiClient()) {
                synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                    if (CameraDeviceImpl.this.mRemoteDevice == null) {
                        return;
                    }
                    StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
                    if (stateCallbackKK != null) {
                        CameraDeviceImpl cameraDeviceImpl = CameraDeviceImpl.this;
                        stateCallbackKK.onOpenedInSharedMode(cameraDeviceImpl, cameraDeviceImpl.mIsPrimaryClient);
                    }
                    CameraDevice.StateCallback stateCallback = CameraDeviceImpl.this.mDeviceCallback;
                    CameraDeviceImpl cameraDeviceImpl2 = CameraDeviceImpl.this;
                    stateCallback.onOpenedInSharedMode(cameraDeviceImpl2, cameraDeviceImpl2.mIsPrimaryClient);
                }
            }
        }
    };
    private final Runnable mCallOnUnconfigured = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.4
        @Override // java.lang.Runnable
        public void run() {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
                if (stateCallbackKK != null) {
                    stateCallbackKK.onUnconfigured(CameraDeviceImpl.this);
                }
            }
        }
    };
    private final Runnable mCallOnActive = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.5
        @Override // java.lang.Runnable
        public void run() {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
                if (stateCallbackKK != null) {
                    stateCallbackKK.onActive(CameraDeviceImpl.this);
                }
            }
        }
    };
    private final Runnable mCallOnBusy = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.6
        @Override // java.lang.Runnable
        public void run() {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
                if (stateCallbackKK != null) {
                    stateCallbackKK.onBusy(CameraDeviceImpl.this);
                }
            }
        }
    };
    private final Runnable mCallOnClosed = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.7
        private boolean mClosedOnce = false;

        @Override // java.lang.Runnable
        public void run() {
            StateCallbackKK stateCallbackKK;
            if (this.mClosedOnce) {
                throw new AssertionError("Don't post #onClosed more than once");
            }
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
            }
            if (stateCallbackKK != null) {
                stateCallbackKK.onClosed(CameraDeviceImpl.this);
            }
            CameraDeviceImpl.this.mDeviceCallback.onClosed(CameraDeviceImpl.this);
            this.mClosedOnce = true;
        }
    };
    private final Runnable mCallOnIdle = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.8
        @Override // java.lang.Runnable
        public void run() {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
                if (stateCallbackKK != null) {
                    stateCallbackKK.onIdle(CameraDeviceImpl.this);
                }
            }
        }
    };
    private final Runnable mCallOnDisconnected = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.9
        @Override // java.lang.Runnable
        public void run() {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
                if (stateCallbackKK != null) {
                    stateCallbackKK.onDisconnected(CameraDeviceImpl.this);
                }
                CameraDeviceImpl.this.mDeviceCallback.onDisconnected(CameraDeviceImpl.this);
            }
        }
    };

    public static abstract class StateCallbackKK extends CameraDevice.StateCallback {
        public void onActive(CameraDevice cameraDevice) {
        }

        public void onBusy(CameraDevice cameraDevice) {
        }

        public void onIdle(CameraDevice cameraDevice) {
        }

        public void onRequestQueueEmpty() {
        }

        public void onSurfacePrepared(Surface surface) {
        }

        public void onUnconfigured(CameraDevice cameraDevice) {
        }
    }

    private boolean isRawFormat(int i) {
        return i == 36 || i == 37 || i == 38 || i == 32;
    }

    private static native void nativeClose(long j);

    private static native long nativeCreateFMQReader(Parcel parcel);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeReadResultMetadata(long j, long j2);

    private class ClientStateCallback extends CameraDevice.StateCallback {
        private final Executor mClientExecutor;
        private final CameraDevice.StateCallback mClientStateCallback;

        private ClientStateCallback(CameraDeviceImpl cameraDeviceImpl, Executor executor, CameraDevice.StateCallback stateCallback) {
            this.mClientExecutor = executor;
            this.mClientStateCallback = stateCallback;
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onClosed(final CameraDevice cameraDevice) {
            this.mClientExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.ClientStateCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    ClientStateCallback.this.mClientStateCallback.onClosed(cameraDevice);
                }
            });
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onOpenedInSharedMode(final CameraDevice cameraDevice, final boolean z) {
            if (Flags.cameraMultiClient()) {
                this.mClientExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.ClientStateCallback.2
                    @Override // java.lang.Runnable
                    public void run() {
                        ClientStateCallback.this.mClientStateCallback.onOpenedInSharedMode(cameraDevice, z);
                    }
                });
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onClientSharedAccessPriorityChanged(final CameraDevice cameraDevice, final boolean z) {
            if (Flags.cameraMultiClient()) {
                this.mClientExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.ClientStateCallback.3
                    @Override // java.lang.Runnable
                    public void run() {
                        ClientStateCallback.this.mClientStateCallback.onClientSharedAccessPriorityChanged(cameraDevice, z);
                    }
                });
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onOpened(final CameraDevice cameraDevice) {
            this.mClientExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.ClientStateCallback.4
                @Override // java.lang.Runnable
                public void run() {
                    ClientStateCallback.this.mClientStateCallback.onOpened(cameraDevice);
                }
            });
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onDisconnected(final CameraDevice cameraDevice) {
            this.mClientExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.ClientStateCallback.5
                @Override // java.lang.Runnable
                public void run() {
                    ClientStateCallback.this.mClientStateCallback.onDisconnected(cameraDevice);
                }
            });
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onError(final CameraDevice cameraDevice, final int i) {
            this.mClientExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.ClientStateCallback.6
                @Override // java.lang.Runnable
                public void run() {
                    ClientStateCallback.this.mClientStateCallback.onError(cameraDevice, i);
                }
            });
        }
    }

    public CameraDeviceImpl(String str, CameraDevice.StateCallback stateCallback, Executor executor, CameraCharacteristics cameraCharacteristics, CameraManager cameraManager, int i, Context context, CameraDevice.CameraDeviceSetup cameraDeviceSetup, boolean z) {
        if (str == null || stateCallback == null || executor == null || cameraCharacteristics == null || cameraManager == null) {
            throw new IllegalArgumentException("Null argument given");
        }
        this.mCameraId = str;
        this.mDeviceCallback = new ClientStateCallback(executor, stateCallback);
        if (Flags.singleThreadExecutorNaming()) {
            this.mDeviceExecutor = Executors.newSingleThreadExecutor(sThreadFactory);
        } else {
            this.mDeviceExecutor = Executors.newSingleThreadExecutor();
        }
        this.mCharacteristics = cameraCharacteristics;
        this.mCameraManager = cameraManager;
        this.mAppTargetSdkVersion = i;
        this.mContext = context;
        this.mCameraDeviceSetup = cameraDeviceSetup;
        this.mSharedMode = z;
        String str2 = String.format("CameraDevice-JV-%s", str);
        this.TAG = str2.length() > 23 ? str2.substring(0, 23) : str2;
        Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.REQUEST_PARTIAL_RESULT_COUNT);
        if (num == null) {
            this.mTotalPartialCount = 1;
        } else {
            this.mTotalPartialCount = num.intValue();
        }
    }

    public boolean isPrimaryClient() {
        boolean z;
        synchronized (this.mInterfaceLock) {
            z = this.mIsPrimaryClient;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map<String, CameraCharacteristics> getPhysicalIdToChars() {
        if (this.mPhysicalIdsToChars == null) {
            try {
                this.mPhysicalIdsToChars = this.mCameraManager.getPhysicalIdToCharsMap(this.mCharacteristics);
            } catch (CameraAccessException unused) {
                Log.e(this.TAG, "Unable to query the physical characteristics map!");
            }
        }
        return this.mPhysicalIdsToChars;
    }

    public CameraDeviceCallbacks getCallbacks() {
        return this.mCallbacks;
    }

    public void setRemoteDevice(ICameraDeviceUser iCameraDeviceUser) throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            if (this.mInError) {
                return;
            }
            this.mRemoteDevice = new ICameraDeviceUserWrapper(iCameraDeviceUser);
            Parcel parcelObtain = Parcel.obtain();
            this.mRemoteDevice.getCaptureResultMetadataQueue().writeToParcel(parcelObtain, 1);
            this.mFMQReader = nativeCreateFMQReader(parcelObtain);
            parcelObtain.recycle();
            IBinder iBinderAsBinder = iCameraDeviceUser.asBinder();
            if (iBinderAsBinder != null) {
                try {
                    iBinderAsBinder.linkToDeath(this, 0);
                } catch (RemoteException unused) {
                    this.mDeviceExecutor.execute(this.mCallOnDisconnected);
                    throw new CameraAccessException(2, "The camera device has encountered a serious error");
                }
            }
            if (Flags.cameraMultiClient() && this.mSharedMode) {
                this.mIsPrimaryClient = this.mRemoteDevice.isPrimaryClient();
                this.mDeviceExecutor.execute(this.mCallOnOpenedInSharedMode);
            } else {
                this.mDeviceExecutor.execute(this.mCallOnOpened);
            }
            this.mDeviceExecutor.execute(this.mCallOnUnconfigured);
            this.mRemoteDeviceInit = true;
        }
    }

    public void setRemoteFailure(ServiceSpecificException serviceSpecificException) {
        final boolean z;
        int i = serviceSpecificException.errorCode;
        final int i2 = 4;
        if (i == 4) {
            z = false;
        } else if (i == 10) {
            z = true;
        } else {
            if (i == 6) {
                i2 = 3;
            } else if (i == 7) {
                z = true;
                i2 = 1;
            } else if (i != 8) {
                Log.e(this.TAG, "Unexpected failure in opening camera device: " + serviceSpecificException.errorCode + serviceSpecificException.getMessage());
            } else {
                i2 = 2;
            }
            z = true;
        }
        synchronized (this.mInterfaceLock) {
            this.mInError = true;
            this.mDeviceExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.10
                @Override // java.lang.Runnable
                public void run() {
                    if (z) {
                        CameraDeviceImpl.this.mDeviceCallback.onError(CameraDeviceImpl.this, i2);
                    } else {
                        CameraDeviceImpl.this.mDeviceCallback.onDisconnected(CameraDeviceImpl.this);
                    }
                }
            });
        }
    }

    @Override // android.hardware.camera2.CameraDevice
    public String getId() {
        return this.mCameraId;
    }

    public void configureOutputs(List<Surface> list) throws CameraAccessException {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<Surface> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new OutputConfiguration(it.next()));
        }
        configureStreamsChecked(null, arrayList, 0, null, SystemClock.uptimeMillis());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00e8 A[Catch: IllegalArgumentException -> 0x016b, all -> 0x016d, CameraAccessException -> 0x016f, LOOP:1: B:42:0x00e2->B:44:0x00e8, LOOP_END, TryCatch #1 {CameraAccessException -> 0x016f, blocks: (B:26:0x0078, B:28:0x007c, B:29:0x007f, B:32:0x0091, B:35:0x0099, B:37:0x00b8, B:39:0x00c8, B:41:0x00de, B:42:0x00e2, B:44:0x00e8, B:45:0x0101, B:46:0x0105, B:48:0x010b, B:50:0x0117, B:52:0x0125, B:54:0x0136, B:56:0x013d, B:58:0x0140, B:60:0x0145, B:53:0x0130), top: B:90:0x0078, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x010b A[Catch: IllegalArgumentException -> 0x016b, all -> 0x016d, CameraAccessException -> 0x016f, TryCatch #1 {CameraAccessException -> 0x016f, blocks: (B:26:0x0078, B:28:0x007c, B:29:0x007f, B:32:0x0091, B:35:0x0099, B:37:0x00b8, B:39:0x00c8, B:41:0x00de, B:42:0x00e2, B:44:0x00e8, B:45:0x0101, B:46:0x0105, B:48:0x010b, B:50:0x0117, B:52:0x0125, B:54:0x0136, B:56:0x013d, B:58:0x0140, B:60:0x0145, B:53:0x0130), top: B:90:0x0078, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0125 A[Catch: IllegalArgumentException -> 0x016b, all -> 0x016d, CameraAccessException -> 0x016f, TryCatch #1 {CameraAccessException -> 0x016f, blocks: (B:26:0x0078, B:28:0x007c, B:29:0x007f, B:32:0x0091, B:35:0x0099, B:37:0x00b8, B:39:0x00c8, B:41:0x00de, B:42:0x00e2, B:44:0x00e8, B:45:0x0101, B:46:0x0105, B:48:0x010b, B:50:0x0117, B:52:0x0125, B:54:0x0136, B:56:0x013d, B:58:0x0140, B:60:0x0145, B:53:0x0130), top: B:90:0x0078, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0130 A[Catch: IllegalArgumentException -> 0x016b, all -> 0x016d, CameraAccessException -> 0x016f, TryCatch #1 {CameraAccessException -> 0x016f, blocks: (B:26:0x0078, B:28:0x007c, B:29:0x007f, B:32:0x0091, B:35:0x0099, B:37:0x00b8, B:39:0x00c8, B:41:0x00de, B:42:0x00e2, B:44:0x00e8, B:45:0x0101, B:46:0x0105, B:48:0x010b, B:50:0x0117, B:52:0x0125, B:54:0x0136, B:56:0x013d, B:58:0x0140, B:60:0x0145, B:53:0x0130), top: B:90:0x0078, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0145 A[Catch: IllegalArgumentException -> 0x016b, all -> 0x016d, CameraAccessException -> 0x016f, TRY_LEAVE, TryCatch #1 {CameraAccessException -> 0x016f, blocks: (B:26:0x0078, B:28:0x007c, B:29:0x007f, B:32:0x0091, B:35:0x0099, B:37:0x00b8, B:39:0x00c8, B:41:0x00de, B:42:0x00e2, B:44:0x00e8, B:45:0x0101, B:46:0x0105, B:48:0x010b, B:50:0x0117, B:52:0x0125, B:54:0x0136, B:56:0x013d, B:58:0x0140, B:60:0x0145, B:53:0x0130), top: B:90:0x0078, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0159 A[Catch: all -> 0x01ae, TryCatch #4 {, blocks: (B:14:0x002f, B:15:0x003d, B:17:0x0045, B:19:0x0059, B:22:0x0060, B:24:0x006b, B:23:0x0064, B:25:0x006e, B:62:0x0153, B:64:0x0159, B:67:0x0169, B:65:0x0161, B:82:0x019d, B:83:0x01a4, B:85:0x01a6, B:86:0x01ad, B:26:0x0078, B:28:0x007c, B:29:0x007f, B:32:0x0091, B:35:0x0099, B:37:0x00b8, B:39:0x00c8, B:41:0x00de, B:42:0x00e2, B:44:0x00e8, B:45:0x0101, B:46:0x0105, B:48:0x010b, B:50:0x0117, B:52:0x0125, B:54:0x0136, B:56:0x013d, B:58:0x0140, B:60:0x0145, B:53:0x0130, B:81:0x0183, B:74:0x0170, B:76:0x0177, B:77:0x017e, B:78:0x017f), top: B:94:0x002f, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0161 A[Catch: all -> 0x01ae, DONT_GENERATE, TryCatch #4 {, blocks: (B:14:0x002f, B:15:0x003d, B:17:0x0045, B:19:0x0059, B:22:0x0060, B:24:0x006b, B:23:0x0064, B:25:0x006e, B:62:0x0153, B:64:0x0159, B:67:0x0169, B:65:0x0161, B:82:0x019d, B:83:0x01a4, B:85:0x01a6, B:86:0x01ad, B:26:0x0078, B:28:0x007c, B:29:0x007f, B:32:0x0091, B:35:0x0099, B:37:0x00b8, B:39:0x00c8, B:41:0x00de, B:42:0x00e2, B:44:0x00e8, B:45:0x0101, B:46:0x0105, B:48:0x010b, B:50:0x0117, B:52:0x0125, B:54:0x0136, B:56:0x013d, B:58:0x0140, B:60:0x0145, B:53:0x0130, B:81:0x0183, B:74:0x0170, B:76:0x0177, B:77:0x017e, B:78:0x017f), top: B:94:0x002f, inners: #3 }] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5, types: [int] */
    /* JADX WARN: Type inference failed for: r3v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean configureStreamsChecked(InputConfiguration inputConfiguration, List<OutputConfiguration> list, int i, CaptureRequest captureRequest, long j) throws CameraAccessException {
        boolean z;
        int[] iArrEndConfigure;
        int length;
        ?? r3;
        List<OutputConfiguration> arrayList = list == null ? new ArrayList<>() : list;
        if (arrayList.size() == 0 && inputConfiguration != null) {
            throw new IllegalArgumentException("cannot configure an input stream without any output streams");
        }
        checkInputConfiguration(inputConfiguration);
        List<OutputConfiguration> listApplyExtensionStreamOption = applyExtensionStreamOption(arrayList, captureRequest);
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            HashSet hashSet = new HashSet(listApplyExtensionStreamOption);
            ArrayList<Integer> arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < this.mConfiguredOutputs.size(); i2++) {
                int iKeyAt = this.mConfiguredOutputs.keyAt(i2);
                OutputConfiguration outputConfigurationValueAt = this.mConfiguredOutputs.valueAt(i2);
                if (!listApplyExtensionStreamOption.contains(outputConfigurationValueAt) || outputConfigurationValueAt.isDeferredConfiguration()) {
                    arrayList2.add(Integer.valueOf(iKeyAt));
                } else {
                    hashSet.remove(outputConfigurationValueAt);
                }
            }
            this.mDeviceExecutor.execute(this.mCallOnBusy);
            stopRepeating();
            try {
                try {
                    try {
                        if (!this.mSharedMode) {
                            waitUntilIdle();
                        }
                        this.mRemoteDevice.beginConfigure();
                        InputConfiguration value = this.mConfiguredInput.getValue();
                        if (inputConfiguration == value || (inputConfiguration != null && inputConfiguration.equals(value))) {
                            z = false;
                            for (Integer num : arrayList2) {
                                this.mRemoteDevice.deleteStream(num.intValue());
                                this.mConfiguredOutputs.delete(num.intValue());
                            }
                            for (OutputConfiguration outputConfiguration : listApplyExtensionStreamOption) {
                                if (hashSet.contains(outputConfiguration)) {
                                    this.mConfiguredOutputs.put(this.mRemoteDevice.createStream(outputConfiguration), outputConfiguration);
                                }
                            }
                            if (captureRequest == null) {
                                iArrEndConfigure = this.mRemoteDevice.endConfigure(i, captureRequest.getNativeCopy(), j);
                            } else {
                                iArrEndConfigure = this.mRemoteDevice.endConfigure(i, null, j);
                            }
                            this.mOfflineSupport.clear();
                            if (iArrEndConfigure != null && iArrEndConfigure.length > 0) {
                                length = iArrEndConfigure.length;
                                for (r3 = z; r3 < length; r3++) {
                                    this.mOfflineSupport.add(Integer.valueOf(iArrEndConfigure[r3]));
                                }
                            }
                            if (listApplyExtensionStreamOption.size() <= 0) {
                                this.mDeviceExecutor.execute(this.mCallOnIdle);
                            }
                            return true;
                        }
                        if (value != null) {
                            this.mRemoteDevice.deleteStream(this.mConfiguredInput.getKey().intValue());
                            this.mConfiguredInput = new AbstractMap.SimpleEntry<>(-1, null);
                        }
                        if (inputConfiguration != null) {
                            z = false;
                            try {
                                this.mConfiguredInput = new AbstractMap.SimpleEntry<>(Integer.valueOf(this.mRemoteDevice.createInputStream(inputConfiguration.getWidth(), inputConfiguration.getHeight(), inputConfiguration.getFormat(), inputConfiguration.isMultiResolution())), inputConfiguration);
                            } catch (IllegalArgumentException e) {
                                e = e;
                                Log.w(this.TAG, "Stream configuration failed due to: " + e.getMessage());
                                return z;
                            }
                        }
                        while (r0.hasNext()) {
                        }
                        while (r0.hasNext()) {
                        }
                        if (captureRequest == null) {
                        }
                        this.mOfflineSupport.clear();
                        if (iArrEndConfigure != null) {
                            length = iArrEndConfigure.length;
                            while (r3 < length) {
                            }
                        }
                        if (listApplyExtensionStreamOption.size() <= 0) {
                        }
                        return true;
                    } catch (IllegalArgumentException e2) {
                        e = e2;
                        z = false;
                    }
                } catch (CameraAccessException e3) {
                    if (e3.getReason() == 4) {
                        throw new IllegalStateException("The camera is currently busy. You must wait until the previous operation completes.", e3);
                    }
                    throw e3;
                }
            } finally {
                this.mDeviceExecutor.execute(this.mCallOnUnconfigured);
            }
        }
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createCaptureSession(List<Surface> list, CameraCaptureSession.StateCallback stateCallback, Handler handler) throws CameraAccessException {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<Surface> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new OutputConfiguration(it.next()));
        }
        createCaptureSessionInternal(null, arrayList, stateCallback, checkAndWrapHandler(handler), 0, null);
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createCaptureSessionByOutputConfigurations(List<OutputConfiguration> list, CameraCaptureSession.StateCallback stateCallback, Handler handler) throws CameraAccessException {
        createCaptureSessionInternal(null, new ArrayList(list), stateCallback, checkAndWrapHandler(handler), 0, null);
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createReprocessableCaptureSession(InputConfiguration inputConfiguration, List<Surface> list, CameraCaptureSession.StateCallback stateCallback, Handler handler) throws CameraAccessException {
        if (inputConfiguration == null) {
            throw new IllegalArgumentException("inputConfig cannot be null when creating a reprocessable capture session");
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<Surface> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new OutputConfiguration(it.next()));
        }
        createCaptureSessionInternal(inputConfiguration, arrayList, stateCallback, checkAndWrapHandler(handler), 0, null);
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createReprocessableCaptureSessionByConfigurations(InputConfiguration inputConfiguration, List<OutputConfiguration> list, CameraCaptureSession.StateCallback stateCallback, Handler handler) throws CameraAccessException {
        if (inputConfiguration == null) {
            throw new IllegalArgumentException("inputConfig cannot be null when creating a reprocessable capture session");
        }
        if (list == null) {
            throw new IllegalArgumentException("Output configurations cannot be null when creating a reprocessable capture session");
        }
        ArrayList arrayList = new ArrayList();
        Iterator<OutputConfiguration> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new OutputConfiguration(it.next()));
        }
        createCaptureSessionInternal(inputConfiguration, arrayList, stateCallback, checkAndWrapHandler(handler), 0, null);
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createConstrainedHighSpeedCaptureSession(List<Surface> list, CameraCaptureSession.StateCallback stateCallback, Handler handler) throws CameraAccessException {
        if (list == null || list.size() == 0 || list.size() > 2) {
            throw new IllegalArgumentException("Output surface list must not be null and the size must be no more than 2");
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<Surface> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new OutputConfiguration(it.next()));
        }
        createCaptureSessionInternal(null, arrayList, stateCallback, checkAndWrapHandler(handler), 1, null);
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createCustomCaptureSession(InputConfiguration inputConfiguration, List<OutputConfiguration> list, int i, CameraCaptureSession.StateCallback stateCallback, Handler handler) throws CameraAccessException {
        ArrayList arrayList = new ArrayList();
        Iterator<OutputConfiguration> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new OutputConfiguration(it.next()));
        }
        createCaptureSessionInternal(inputConfiguration, arrayList, stateCallback, checkAndWrapHandler(handler), i, null);
    }

    private boolean checkSharedOutputConfiguration(OutputConfiguration outputConfiguration) {
        SharedSessionConfiguration sharedSessionConfiguration;
        if (!Flags.cameraMultiClient() || (sharedSessionConfiguration = (SharedSessionConfiguration) this.mCharacteristics.get(CameraCharacteristics.SHARED_SESSION_CONFIGURATION)) == null) {
            return false;
        }
        for (SharedSessionConfiguration.SharedOutputConfiguration sharedOutputConfiguration : sharedSessionConfiguration.getOutputStreamsInformation()) {
            if (outputConfiguration.getSurfaceGroupId() == -1 && outputConfiguration.getMirrorMode() == sharedOutputConfiguration.getMirrorMode() && outputConfiguration.isReadoutTimestampEnabled() == sharedOutputConfiguration.isReadoutTimestampEnabled() && outputConfiguration.getTimestampBase() == sharedOutputConfiguration.getTimestampBase() && outputConfiguration.getStreamUseCase() == sharedOutputConfiguration.getStreamUseCase() && outputConfiguration.getDynamicRangeProfile() == 1 && Objects.equals(outputConfiguration.getPhysicalCameraId(), sharedOutputConfiguration.getPhysicalCameraId()) && outputConfiguration.getSensorPixelModes().isEmpty() && !outputConfiguration.isMultiResolution() && !outputConfiguration.isDeferredConfiguration() && !outputConfiguration.isShared()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSharedSessionConfiguration(List<OutputConfiguration> list) {
        Iterator<OutputConfiguration> it = list.iterator();
        while (it.hasNext()) {
            if (!checkSharedOutputConfiguration(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createCaptureSession(SessionConfiguration sessionConfiguration) throws CameraAccessException {
        if (sessionConfiguration == null) {
            throw new IllegalArgumentException("Invalid session configuration");
        }
        List<OutputConfiguration> outputConfigurations = sessionConfiguration.getOutputConfigurations();
        if (outputConfigurations == null) {
            throw new IllegalArgumentException("Invalid output configurations");
        }
        if (sessionConfiguration.getExecutor() == null) {
            throw new IllegalArgumentException("Invalid executor");
        }
        createCaptureSessionInternal(sessionConfiguration.getInputConfiguration(), outputConfigurations, sessionConfiguration.getStateCallback(), sessionConfiguration.getExecutor(), sessionConfiguration.getSessionType(), sessionConfiguration.getSessionParameters());
    }

    private void createCaptureSessionInternal(InputConfiguration inputConfiguration, List<OutputConfiguration> list, CameraCaptureSession.StateCallback stateCallback, Executor executor, int i, CaptureRequest captureRequest) throws CameraAccessException {
        List<OutputConfiguration> list2;
        CameraAccessException cameraAccessException;
        boolean z;
        Surface inputSurface;
        CameraCaptureSessionCore cameraCaptureSessionImpl;
        long jUptimeMillis = SystemClock.uptimeMillis();
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            boolean z2 = false;
            boolean z3 = i == 2;
            if (Flags.cameraMultiClient() && this.mSharedMode) {
                if (!z3) {
                    throw new IllegalArgumentException("Invalid session type");
                }
                list2 = list;
                if (!checkSharedSessionConfiguration(list2)) {
                    throw new IllegalArgumentException("Invalid output configurations");
                }
                if (inputConfiguration != null) {
                    throw new IllegalArgumentException("Shared capture session doesn't support input configuration yet.");
                }
            } else {
                list2 = list;
            }
            boolean z4 = i == 1;
            if (z4 && inputConfiguration != null) {
                throw new IllegalArgumentException("Constrained high speed session doesn't support input configuration yet.");
            }
            CameraExtensionSessionImpl cameraExtensionSessionImpl = this.mCurrentExtensionSession;
            if (cameraExtensionSessionImpl != null) {
                cameraExtensionSessionImpl.commitStats();
            }
            CameraAdvancedExtensionSessionImpl cameraAdvancedExtensionSessionImpl = this.mCurrentAdvancedExtensionSession;
            if (cameraAdvancedExtensionSessionImpl != null) {
                cameraAdvancedExtensionSessionImpl.commitStats();
            }
            CameraCaptureSessionCore cameraCaptureSessionCore = this.mCurrentSession;
            if (cameraCaptureSessionCore != null) {
                cameraCaptureSessionCore.replaceSessionClose();
            }
            CameraExtensionSessionImpl cameraExtensionSessionImpl2 = this.mCurrentExtensionSession;
            if (cameraExtensionSessionImpl2 != null) {
                cameraExtensionSessionImpl2.release(false);
                this.mCurrentExtensionSession = null;
            }
            CameraAdvancedExtensionSessionImpl cameraAdvancedExtensionSessionImpl2 = this.mCurrentAdvancedExtensionSession;
            if (cameraAdvancedExtensionSessionImpl2 != null) {
                cameraAdvancedExtensionSessionImpl2.release(false);
                this.mCurrentAdvancedExtensionSession = null;
            }
            try {
                boolean zConfigureStreamsChecked = configureStreamsChecked(inputConfiguration, list2, i, captureRequest, jUptimeMillis);
                z = zConfigureStreamsChecked;
                inputSurface = (!zConfigureStreamsChecked || inputConfiguration == null) ? null : this.mRemoteDevice.getInputSurface();
                cameraAccessException = null;
            } catch (CameraAccessException e) {
                cameraAccessException = e;
                z = false;
                inputSurface = null;
            }
            if (z4) {
                ArrayList arrayList = new ArrayList(list.size());
                for (OutputConfiguration outputConfiguration : list) {
                    arrayList.add(outputConfiguration.getSurface());
                    if (outputConfiguration.getOption() > 0) {
                        z2 = true;
                    }
                }
                if (!z2) {
                    SurfaceUtils.checkConstrainedHighSpeedSurfaces(arrayList, null, (StreamConfigurationMap) getCharacteristics().get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP));
                }
                int i2 = this.mNextSessionId;
                this.mNextSessionId = i2 + 1;
                cameraCaptureSessionImpl = new CameraConstrainedHighSpeedCaptureSessionImpl(i2, stateCallback, executor, this, this.mDeviceExecutor, z, this.mCharacteristics);
            } else if (z3) {
                int i3 = this.mNextSessionId;
                this.mNextSessionId = i3 + 1;
                cameraCaptureSessionImpl = new CameraSharedCaptureSessionImpl(i3, stateCallback, executor, this, this.mDeviceExecutor, z);
            } else {
                int i4 = this.mNextSessionId;
                this.mNextSessionId = i4 + 1;
                cameraCaptureSessionImpl = new CameraCaptureSessionImpl(i4, inputSurface, stateCallback, executor, this, this.mDeviceExecutor, z);
            }
            this.mCurrentSession = cameraCaptureSessionImpl;
            if (cameraAccessException != null) {
                throw cameraAccessException;
            }
            this.mSessionStateCallback = cameraCaptureSessionImpl.getDeviceStateCallback();
        }
    }

    @Override // android.hardware.camera2.CameraDevice
    public boolean isSessionConfigurationSupported(SessionConfiguration sessionConfiguration) throws UnsupportedOperationException, CameraAccessException, IllegalArgumentException {
        CameraDevice.CameraDeviceSetup cameraDeviceSetup;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            if (CompatChanges.isChangeEnabled(CHECK_PARAMS_IN_IS_SESSION_CONFIGURATION_SUPPORTED) && Flags.cameraDeviceSetup() && (cameraDeviceSetup = this.mCameraDeviceSetup) != null) {
                return cameraDeviceSetup.isSessionConfigurationSupported(sessionConfiguration);
            }
            return this.mRemoteDevice.isSessionConfigurationSupported(sessionConfiguration);
        }
    }

    @Override // android.hardware.camera2.CameraDevice
    public void setParameters(String str) throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            this.mRemoteDevice.setParameters(str);
        }
    }

    public void setSessionListener(StateCallbackKK stateCallbackKK) {
        synchronized (this.mInterfaceLock) {
            this.mSessionStateCallback = stateCallbackKK;
        }
    }

    public static void disableZslIfNeeded(CameraMetadataNative cameraMetadataNative, int i, int i2) {
        if ((i < 26 || i2 != 2) && ((Boolean) cameraMetadataNative.get(CaptureRequest.CONTROL_ENABLE_ZSL)) != null) {
            cameraMetadataNative.set((CaptureRequest.Key<CaptureRequest.Key<Boolean>>) CaptureRequest.CONTROL_ENABLE_ZSL, (CaptureRequest.Key<Boolean>) false);
        }
    }

    @Override // android.hardware.camera2.CameraDevice
    public CaptureRequest.Builder createCaptureRequest(int i, Set<String> set) throws CameraAccessException {
        CaptureRequest.Builder builder;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            if (Flags.cameraMultiClient() && this.mSharedMode && !this.mIsPrimaryClient) {
                throw new UnsupportedOperationException("In shared session mode,only primary clients can create capture request.");
            }
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                if (Objects.equals(it.next(), getId())) {
                    throw new IllegalStateException("Physical id matches the logical id!");
                }
            }
            CameraMetadataNative cameraMetadataNativeCreateDefaultRequest = this.mRemoteDevice.createDefaultRequest(i);
            disableZslIfNeeded(cameraMetadataNativeCreateDefaultRequest, this.mAppTargetSdkVersion, i);
            builder = new CaptureRequest.Builder(cameraMetadataNativeCreateDefaultRequest, false, -1, getId(), set);
        }
        return builder;
    }

    @Override // android.hardware.camera2.CameraDevice
    public CaptureRequest.Builder createCaptureRequest(int i) throws CameraAccessException {
        CaptureRequest.Builder builder;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            if (Flags.cameraMultiClient() && this.mSharedMode && !this.mIsPrimaryClient) {
                throw new UnsupportedOperationException("In shared session mode,only primary clients can create capture request.");
            }
            CameraMetadataNative cameraMetadataNativeCreateDefaultRequest = this.mRemoteDevice.createDefaultRequest(i);
            disableZslIfNeeded(cameraMetadataNativeCreateDefaultRequest, this.mAppTargetSdkVersion, i);
            builder = new CaptureRequest.Builder(cameraMetadataNativeCreateDefaultRequest, false, -1, getId(), null);
        }
        return builder;
    }

    @Override // android.hardware.camera2.CameraDevice
    public CaptureRequest.Builder createReprocessCaptureRequest(TotalCaptureResult totalCaptureResult) throws CameraAccessException {
        CaptureRequest.Builder builder;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            if (Flags.cameraMultiClient() && this.mSharedMode) {
                throw new UnsupportedOperationException("In shared session mode,reprocess capture requests are not supported.");
            }
            builder = new CaptureRequest.Builder(new CameraMetadataNative(totalCaptureResult.getNativeCopy()), true, totalCaptureResult.getSessionId(), getId(), null);
            builder.set(CaptureRequest.CONTROL_CAPTURE_INTENT, 2);
        }
        return builder;
    }

    public void prepare(Surface surface) throws CameraAccessException {
        int iKeyAt;
        if (surface == null) {
            throw new IllegalArgumentException("Surface is null");
        }
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            int i = 0;
            while (true) {
                if (i >= this.mConfiguredOutputs.size()) {
                    iKeyAt = -1;
                    break;
                } else {
                    if (this.mConfiguredOutputs.valueAt(i).getSurfaces().contains(surface)) {
                        iKeyAt = this.mConfiguredOutputs.keyAt(i);
                        break;
                    }
                    i++;
                }
            }
            if (iKeyAt == -1) {
                throw new IllegalArgumentException("Surface is not part of this session");
            }
            this.mRemoteDevice.prepare(iKeyAt);
        }
    }

    public void prepare(int i, Surface surface) throws CameraAccessException {
        int iKeyAt;
        if (surface == null) {
            throw new IllegalArgumentException("Surface is null");
        }
        if (i <= 0) {
            throw new IllegalArgumentException("Invalid maxCount given: " + i);
        }
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            int i2 = 0;
            while (true) {
                if (i2 >= this.mConfiguredOutputs.size()) {
                    iKeyAt = -1;
                    break;
                } else {
                    if (surface == this.mConfiguredOutputs.valueAt(i2).getSurface()) {
                        iKeyAt = this.mConfiguredOutputs.keyAt(i2);
                        break;
                    }
                    i2++;
                }
            }
            if (iKeyAt == -1) {
                throw new IllegalArgumentException("Surface is not part of this session");
            }
            this.mRemoteDevice.prepare2(i, iKeyAt);
        }
    }

    public void updateOutputConfiguration(OutputConfiguration outputConfiguration) throws CameraAccessException {
        int iKeyAt;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            int i = 0;
            while (true) {
                if (i >= this.mConfiguredOutputs.size()) {
                    iKeyAt = -1;
                    break;
                } else {
                    if (outputConfiguration.getSurface() == this.mConfiguredOutputs.valueAt(i).getSurface()) {
                        iKeyAt = this.mConfiguredOutputs.keyAt(i);
                        break;
                    }
                    i++;
                }
            }
            if (iKeyAt == -1) {
                throw new IllegalArgumentException("Invalid output configuration");
            }
            this.mRemoteDevice.updateOutputConfiguration(iKeyAt, outputConfiguration);
            this.mConfiguredOutputs.put(iKeyAt, outputConfiguration);
        }
    }

    public CameraOfflineSession switchToOffline(Collection<Surface> collection, Executor executor, CameraOfflineSession.CameraOfflineSessionCallback cameraOfflineSessionCallback) throws CameraAccessException {
        CameraOfflineSessionImpl cameraOfflineSessionImpl;
        int iKeyAt;
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("Invalid offline surfaces!");
        }
        final HashSet hashSet = new HashSet();
        SparseArray sparseArray = new SparseArray();
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            if (this.mOfflineSessionImpl != null) {
                throw new IllegalStateException("Switch to offline mode already in progress");
            }
            for (Surface surface : collection) {
                int i = 0;
                while (true) {
                    if (i >= this.mConfiguredOutputs.size()) {
                        iKeyAt = -1;
                        break;
                    }
                    if (surface == this.mConfiguredOutputs.valueAt(i).getSurface()) {
                        iKeyAt = this.mConfiguredOutputs.keyAt(i);
                        sparseArray.append(iKeyAt, this.mConfiguredOutputs.valueAt(i));
                        break;
                    }
                    i++;
                }
                if (iKeyAt == -1) {
                    throw new IllegalArgumentException("Offline surface is not part of this session");
                }
                if (!this.mOfflineSupport.contains(Integer.valueOf(iKeyAt))) {
                    throw new IllegalArgumentException("Surface: " + surface + " does not  support offline mode");
                }
                hashSet.add(Integer.valueOf(iKeyAt));
            }
            stopRepeating();
            cameraOfflineSessionImpl = new CameraOfflineSessionImpl(this.mCameraId, this.mCharacteristics, executor, cameraOfflineSessionCallback, sparseArray, this.mConfiguredInput, this.mConfiguredOutputs, this.mFrameNumberTracker, this.mCaptureCallbackMap, this.mRequestLastFrameNumbersList);
            this.mOfflineSessionImpl = cameraOfflineSessionImpl;
            this.mOfflineSwitchService = Executors.newSingleThreadExecutor();
            this.mConfiguredOutputs.clear();
            this.mConfiguredInput = new AbstractMap.SimpleEntry<>(-1, null);
            this.mIdle = true;
            this.mCaptureCallbackMap = new SparseArray<>();
            this.mBatchOutputMap = new HashMap<>();
            this.mFrameNumberTracker = new FrameNumberTracker();
            this.mCurrentSession.closeWithoutDraining();
            this.mCurrentSession = null;
        }
        this.mOfflineSwitchService.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.11
            @Override // java.lang.Runnable
            public void run() {
                try {
                    ICameraDeviceUserWrapper iCameraDeviceUserWrapper = CameraDeviceImpl.this.mRemoteDevice;
                    CameraOfflineSessionImpl.CameraDeviceCallbacks callbacks = CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks();
                    HashSet hashSet2 = hashSet;
                    CameraDeviceImpl.this.mOfflineSessionImpl.setRemoteSession(iCameraDeviceUserWrapper.switchToOffline(callbacks, Arrays.stream((Integer[]) hashSet2.toArray(new Integer[hashSet2.size()])).mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray()));
                } catch (CameraAccessException unused) {
                    CameraDeviceImpl.this.mOfflineSessionImpl.notifyFailedSwitch();
                } finally {
                    CameraDeviceImpl.this.mOfflineSessionImpl = null;
                }
            }
        });
        return cameraOfflineSessionImpl;
    }

    public boolean supportsOfflineProcessing(Surface surface) {
        int iKeyAt;
        boolean zContains;
        if (surface == null) {
            throw new IllegalArgumentException("Surface is null");
        }
        synchronized (this.mInterfaceLock) {
            int i = 0;
            while (true) {
                if (i >= this.mConfiguredOutputs.size()) {
                    iKeyAt = -1;
                    break;
                }
                if (surface == this.mConfiguredOutputs.valueAt(i).getSurface()) {
                    iKeyAt = this.mConfiguredOutputs.keyAt(i);
                    break;
                }
                i++;
            }
            if (iKeyAt == -1) {
                throw new IllegalArgumentException("Surface is not part of this session");
            }
            zContains = this.mOfflineSupport.contains(Integer.valueOf(iKeyAt));
        }
        return zContains;
    }

    public void tearDown(Surface surface) throws CameraAccessException {
        int iKeyAt;
        if (surface == null) {
            throw new IllegalArgumentException("Surface is null");
        }
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            int i = 0;
            while (true) {
                if (i >= this.mConfiguredOutputs.size()) {
                    iKeyAt = -1;
                    break;
                } else {
                    if (surface == this.mConfiguredOutputs.valueAt(i).getSurface()) {
                        iKeyAt = this.mConfiguredOutputs.keyAt(i);
                        break;
                    }
                    i++;
                }
            }
            if (iKeyAt == -1) {
                throw new IllegalArgumentException("Surface is not part of this session");
            }
            this.mRemoteDevice.tearDown(iKeyAt);
        }
    }

    public void finalizeOutputConfigs(List<OutputConfiguration> list) throws CameraAccessException {
        int iKeyAt;
        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException("deferred config is null or empty");
        }
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            for (OutputConfiguration outputConfiguration : list) {
                int i = 0;
                while (true) {
                    if (i >= this.mConfiguredOutputs.size()) {
                        iKeyAt = -1;
                        break;
                    } else {
                        if (outputConfiguration.equals(this.mConfiguredOutputs.valueAt(i))) {
                            iKeyAt = this.mConfiguredOutputs.keyAt(i);
                            break;
                        }
                        i++;
                    }
                }
                if (iKeyAt == -1) {
                    throw new IllegalArgumentException("Deferred config is not part of this session");
                }
                if (outputConfiguration.getSurfaces().size() == 0) {
                    throw new IllegalArgumentException("The final config for stream " + iKeyAt + " must have at least 1 surface");
                }
                this.mRemoteDevice.finalizeOutputConfigurations(iKeyAt, outputConfiguration);
                this.mConfiguredOutputs.put(iKeyAt, outputConfiguration);
            }
        }
    }

    public int capture(CaptureRequest captureRequest, CaptureCallback captureCallback, Executor executor) throws CameraAccessException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(captureRequest);
        return submitCaptureRequest(arrayList, captureCallback, executor, false);
    }

    public int captureBurst(List<CaptureRequest> list, CaptureCallback captureCallback, Executor executor) throws CameraAccessException {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("At least one request must be given");
        }
        return submitCaptureRequest(list, captureCallback, executor, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkEarlyTriggerSequenceCompleteLocked(final int i, long j, int[] iArr) {
        if (j == -1) {
            int iIndexOfKey = this.mCaptureCallbackMap.indexOfKey(i);
            final CaptureCallbackHolder captureCallbackHolderValueAt = iIndexOfKey >= 0 ? this.mCaptureCallbackMap.valueAt(iIndexOfKey) : null;
            if (captureCallbackHolderValueAt != null) {
                this.mCaptureCallbackMap.removeAt(iIndexOfKey);
                Runnable runnable = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.12
                    @Override // java.lang.Runnable
                    public void run() {
                        if (CameraDeviceImpl.this.isClosed()) {
                            return;
                        }
                        captureCallbackHolderValueAt.getCallback().onCaptureSequenceAborted(CameraDeviceImpl.this, i);
                    }
                };
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    captureCallbackHolderValueAt.getExecutor().execute(runnable);
                    return;
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
            Log.w(this.TAG, String.format("did not register callback to request %d", Integer.valueOf(i)));
            return;
        }
        this.mRequestLastFrameNumbersList.add(new RequestLastFrameNumbersHolder(i, j, iArr));
        checkAndFireSequenceComplete();
    }

    private int[] getRequestTypes(CaptureRequest[] captureRequestArr) {
        int[] iArr = new int[captureRequestArr.length];
        for (int i = 0; i < captureRequestArr.length; i++) {
            iArr[i] = captureRequestArr[i].getRequestType();
        }
        return iArr;
    }

    private boolean hasBatchedOutputs(List<CaptureRequest> list) {
        for (int i = 0; i < list.size(); i++) {
            CaptureRequest captureRequest = list.get(i);
            if (!captureRequest.isPartOfCRequestList()) {
                return false;
            }
            if (i == 0 && captureRequest.getTargets().size() != 2) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTracker(int i, long j, int i2, CaptureResult captureResult, boolean z) {
        if (this.mBatchOutputMap.containsKey(Integer.valueOf(i))) {
            int iIntValue = this.mBatchOutputMap.get(Integer.valueOf(i)).intValue();
            for (int i3 = 0; i3 < iIntValue; i3++) {
                this.mFrameNumberTracker.updateTracker(j - ((iIntValue - 1) - i3), captureResult, z, i2);
            }
            return;
        }
        this.mFrameNumberTracker.updateTracker(j, captureResult, z, i2);
    }

    private int submitCaptureRequest(List<CaptureRequest> list, CaptureCallback captureCallback, Executor executor, boolean z) throws CameraAccessException {
        List<CaptureRequest> list2;
        boolean z2;
        int requestId;
        Executor executorCheckExecutor = checkExecutor(executor, captureCallback);
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            for (CaptureRequest captureRequest : list) {
                if (captureRequest.getTargets().isEmpty()) {
                    throw new IllegalArgumentException("Each request must have at least one Surface target");
                }
                Iterator<Surface> it = captureRequest.getTargets().iterator();
                while (it.hasNext()) {
                    if (it.next() == null) {
                        throw new IllegalArgumentException("Null Surface targets are not allowed");
                    }
                }
            }
            if (z) {
                stopRepeating();
            }
            CaptureRequest[] captureRequestArr = (CaptureRequest[]) list.toArray(new CaptureRequest[list.size()]);
            for (CaptureRequest captureRequest2 : captureRequestArr) {
                captureRequest2.convertSurfaceToStreamId(this.mConfiguredOutputs);
            }
            SubmitInfo submitInfoSubmitRequestList = this.mRemoteDevice.submitRequestList(captureRequestArr, z);
            for (CaptureRequest captureRequest3 : captureRequestArr) {
                captureRequest3.recoverStreamIdToSurface();
            }
            if (hasBatchedOutputs(list)) {
                this.mBatchOutputMap.put(Integer.valueOf(submitInfoSubmitRequestList.getRequestId()), Integer.valueOf(list.size()));
            }
            if (captureCallback != null) {
                list2 = list;
                z2 = z;
                this.mCaptureCallbackMap.put(submitInfoSubmitRequestList.getRequestId(), new CaptureCallbackHolder(captureCallback, list2, executorCheckExecutor, z2, this.mNextSessionId - 1));
            } else {
                list2 = list;
                z2 = z;
            }
            if (z2) {
                int i = this.mRepeatingRequestId;
                if (i != -1) {
                    checkEarlyTriggerSequenceCompleteLocked(i, submitInfoSubmitRequestList.getLastFrameNumber(), this.mRepeatingRequestTypes);
                }
                this.mRepeatingRequestId = submitInfoSubmitRequestList.getRequestId();
                this.mRepeatingRequestTypes = getRequestTypes(captureRequestArr);
            } else {
                this.mRequestLastFrameNumbersList.add(new RequestLastFrameNumbersHolder(list2, submitInfoSubmitRequestList));
            }
            if (this.mIdle) {
                this.mDeviceExecutor.execute(this.mCallOnActive);
            }
            this.mIdle = false;
            requestId = submitInfoSubmitRequestList.getRequestId();
        }
        return requestId;
    }

    public int startStreaming(List<Surface> list, CaptureCallback captureCallback, Executor executor) throws CameraAccessException {
        int requestId;
        Executor executorCheckExecutor = checkExecutor(executor, captureCallback);
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            Iterator<Surface> it = list.iterator();
            while (it.hasNext()) {
                if (it.next() == null) {
                    throw new IllegalArgumentException("Null Surface targets are not allowed");
                }
            }
            stopRepeating();
            CaptureRequest.Builder builder = new CaptureRequest.Builder(this.mRemoteDevice.createDefaultRequest(1), false, -1, getId(), null);
            Iterator<Surface> it2 = list.iterator();
            while (it2.hasNext()) {
                builder.addTarget(it2.next());
            }
            CaptureRequest captureRequestBuild = builder.build();
            captureRequestBuild.convertSurfaceToStreamId(this.mConfiguredOutputs);
            SubmitInfo submitInfoStartStreaming = this.mRemoteDevice.startStreaming(captureRequestBuild.getStreamIds(), captureRequestBuild.getSurfaceIds());
            captureRequestBuild.recoverStreamIdToSurface();
            ArrayList arrayList = new ArrayList();
            arrayList.add(captureRequestBuild);
            if (captureCallback != null) {
                this.mCaptureCallbackMap.put(submitInfoStartStreaming.getRequestId(), new CaptureCallbackHolder(captureCallback, arrayList, executorCheckExecutor, true, this.mNextSessionId - 1));
            }
            int i = this.mRepeatingRequestId;
            if (i != -1) {
                checkEarlyTriggerSequenceCompleteLocked(i, submitInfoStartStreaming.getLastFrameNumber(), this.mRepeatingRequestTypes);
            }
            CaptureRequest[] captureRequestArr = (CaptureRequest[]) arrayList.toArray(new CaptureRequest[arrayList.size()]);
            this.mRepeatingRequestId = submitInfoStartStreaming.getRequestId();
            this.mRepeatingRequestTypes = getRequestTypes(captureRequestArr);
            if (this.mIdle) {
                this.mDeviceExecutor.execute(this.mCallOnActive);
            }
            this.mIdle = false;
            requestId = submitInfoStartStreaming.getRequestId();
        }
        return requestId;
    }

    public int setRepeatingRequest(CaptureRequest captureRequest, CaptureCallback captureCallback, Executor executor) throws CameraAccessException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(captureRequest);
        return submitCaptureRequest(arrayList, captureCallback, executor, true);
    }

    public int setRepeatingBurst(List<CaptureRequest> list, CaptureCallback captureCallback, Executor executor) throws CameraAccessException {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("At least one request must be given");
        }
        return submitCaptureRequest(list, captureCallback, executor, true);
    }

    public void stopRepeating() throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            int i = this.mRepeatingRequestId;
            if (i != -1) {
                this.mRepeatingRequestId = -1;
                this.mFailedRepeatingRequestId = -1;
                int[] iArr = this.mRepeatingRequestTypes;
                this.mRepeatingRequestTypes = null;
                this.mFailedRepeatingRequestTypes = null;
                try {
                    checkEarlyTriggerSequenceCompleteLocked(i, this.mRemoteDevice.cancelRequest(i), iArr);
                } catch (IllegalArgumentException unused) {
                    this.mFailedRepeatingRequestId = i;
                    this.mFailedRepeatingRequestTypes = iArr;
                }
            }
        }
    }

    private void waitUntilIdle() throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            if (this.mRepeatingRequestId != -1) {
                throw new IllegalStateException("Active repeating request ongoing");
            }
            this.mRemoteDevice.waitUntilIdle();
        }
    }

    public void flush() throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            this.mDeviceExecutor.execute(this.mCallOnBusy);
            if (this.mIdle) {
                this.mDeviceExecutor.execute(this.mCallOnIdle);
                Log.w(this.TAG, "flush - transit busy->idle immediately, don't actually flush");
                return;
            }
            long jFlush = this.mRemoteDevice.flush();
            int i = this.mRepeatingRequestId;
            if (i != -1) {
                checkEarlyTriggerSequenceCompleteLocked(i, jFlush, this.mRepeatingRequestTypes);
                this.mRepeatingRequestId = -1;
                this.mRepeatingRequestTypes = null;
            }
        }
    }

    @Override // android.hardware.camera2.CameraDevice, java.lang.AutoCloseable
    public void close() {
        synchronized (this.mInterfaceLock) {
            if (this.mClosing.getAndSet(true)) {
                return;
            }
            ExecutorService executorService = this.mOfflineSwitchService;
            if (executorService != null) {
                executorService.shutdownNow();
                this.mOfflineSwitchService = null;
            }
            CameraExtensionSessionImpl cameraExtensionSessionImpl = this.mCurrentExtensionSession;
            if (cameraExtensionSessionImpl != null) {
                cameraExtensionSessionImpl.commitStats();
            }
            CameraAdvancedExtensionSessionImpl cameraAdvancedExtensionSessionImpl = this.mCurrentAdvancedExtensionSession;
            if (cameraAdvancedExtensionSessionImpl != null) {
                cameraAdvancedExtensionSessionImpl.commitStats();
            }
            ICameraDeviceUserWrapper iCameraDeviceUserWrapper = this.mRemoteDevice;
            if (iCameraDeviceUserWrapper != null) {
                iCameraDeviceUserWrapper.disconnect();
                this.mRemoteDevice.unlinkToDeath(this, 0);
            }
            CameraExtensionSessionImpl cameraExtensionSessionImpl2 = this.mCurrentExtensionSession;
            if (cameraExtensionSessionImpl2 != null) {
                cameraExtensionSessionImpl2.release(true);
                this.mCurrentExtensionSession = null;
            }
            CameraAdvancedExtensionSessionImpl cameraAdvancedExtensionSessionImpl2 = this.mCurrentAdvancedExtensionSession;
            if (cameraAdvancedExtensionSessionImpl2 != null) {
                cameraAdvancedExtensionSessionImpl2.release(true);
                this.mCurrentAdvancedExtensionSession = null;
            }
            if (this.mRemoteDevice != null || this.mInError) {
                this.mDeviceExecutor.execute(this.mCallOnClosed);
            }
            nativeClose(this.mFMQReader);
            this.mRemoteDevice = null;
        }
    }

    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private boolean checkInputConfigurationWithStreamConfigurationsAs(InputConfiguration inputConfiguration, StreamConfigurationMap streamConfigurationMap) {
        int[] inputFormats = streamConfigurationMap.getInputFormats();
        int format = inputConfiguration.getFormat();
        boolean z = false;
        for (int i : inputFormats) {
            if (i == format) {
                z = true;
            }
        }
        if (isRawFormat(format)) {
            return true;
        }
        if (!z) {
            return false;
        }
        Size[] inputSizes = streamConfigurationMap.getInputSizes(format);
        boolean z2 = false;
        for (Size size : inputSizes) {
            if (inputConfiguration.getWidth() == size.getWidth() && inputConfiguration.getHeight() == size.getHeight()) {
                z2 = true;
            }
        }
        return z2;
    }

    private boolean checkInputConfigurationWithStreamConfigurations(InputConfiguration inputConfiguration, boolean z) {
        CameraCharacteristics.Key<StreamConfigurationMap> key = CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP;
        if (z) {
            key = CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP_MAXIMUM_RESOLUTION;
        }
        StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) this.mCharacteristics.get(key);
        if (streamConfigurationMap != null && checkInputConfigurationWithStreamConfigurationsAs(inputConfiguration, streamConfigurationMap)) {
            return true;
        }
        Iterator<Map.Entry<String, CameraCharacteristics>> it = getPhysicalIdToChars().entrySet().iterator();
        while (it.hasNext()) {
            StreamConfigurationMap streamConfigurationMap2 = (StreamConfigurationMap) it.next().getValue().get(key);
            if (streamConfigurationMap2 != null && checkInputConfigurationWithStreamConfigurationsAs(inputConfiguration, streamConfigurationMap2)) {
                return true;
            }
        }
        return false;
    }

    private void checkInputConfiguration(InputConfiguration inputConfiguration) {
        if (inputConfiguration == null) {
            return;
        }
        int format = inputConfiguration.getFormat();
        boolean z = false;
        if (inputConfiguration.isMultiResolution()) {
            MultiResolutionStreamConfigurationMap multiResolutionStreamConfigurationMap = (MultiResolutionStreamConfigurationMap) this.mCharacteristics.get(CameraCharacteristics.SCALER_MULTI_RESOLUTION_STREAM_CONFIGURATION_MAP);
            boolean z2 = false;
            for (int i : multiResolutionStreamConfigurationMap.getInputFormats()) {
                if (i == format) {
                    z2 = true;
                }
            }
            if (Flags.multiResRawReprocessing() && isRawFormat(format)) {
                return;
            }
            if (!z2) {
                throw new IllegalArgumentException("multi-resolution input format " + format + " is not valid");
            }
            for (MultiResolutionStreamInfo multiResolutionStreamInfo : multiResolutionStreamConfigurationMap.getInputInfo(format)) {
                if (inputConfiguration.getWidth() == multiResolutionStreamInfo.getWidth() && inputConfiguration.getHeight() == multiResolutionStreamInfo.getHeight()) {
                    z = true;
                }
            }
            if (z) {
                return;
            }
            throw new IllegalArgumentException("Multi-resolution input size " + inputConfiguration.getWidth() + "x" + inputConfiguration.getHeight() + " is not valid");
        }
        if (checkInputConfigurationWithStreamConfigurations(inputConfiguration, false) || checkInputConfigurationWithStreamConfigurations(inputConfiguration, true)) {
            return;
        }
        throw new IllegalArgumentException("Input config with format " + format + " and size " + inputConfiguration.getWidth() + "x" + inputConfiguration.getHeight() + " not supported by camera id " + this.mCameraId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAndFireSequenceComplete() {
        long completedFrameNumber = this.mFrameNumberTracker.getCompletedFrameNumber();
        long completedReprocessFrameNumber = this.mFrameNumberTracker.getCompletedReprocessFrameNumber();
        long completedZslStillFrameNumber = this.mFrameNumberTracker.getCompletedZslStillFrameNumber();
        Iterator<RequestLastFrameNumbersHolder> it = this.mRequestLastFrameNumbersList.iterator();
        while (it.hasNext()) {
            final RequestLastFrameNumbersHolder next = it.next();
            final int requestId = next.getRequestId();
            if (this.mRemoteDevice == null) {
                Log.w(this.TAG, "Camera closed while checking sequences");
                return;
            }
            if (!next.isSequenceCompleted()) {
                long lastRegularFrameNumber = next.getLastRegularFrameNumber();
                long lastReprocessFrameNumber = next.getLastReprocessFrameNumber();
                long lastZslStillFrameNumber = next.getLastZslStillFrameNumber();
                if (lastRegularFrameNumber <= completedFrameNumber && lastReprocessFrameNumber <= completedReprocessFrameNumber && lastZslStillFrameNumber <= completedZslStillFrameNumber) {
                    next.markSequenceCompleted();
                }
                int iIndexOfKey = this.mCaptureCallbackMap.indexOfKey(requestId);
                final CaptureCallbackHolder captureCallbackHolderValueAt = iIndexOfKey >= 0 ? this.mCaptureCallbackMap.valueAt(iIndexOfKey) : null;
                if (captureCallbackHolderValueAt != null && next.isSequenceCompleted()) {
                    Runnable runnable = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.13
                        @Override // java.lang.Runnable
                        public void run() {
                            if (CameraDeviceImpl.this.isClosed()) {
                                return;
                            }
                            captureCallbackHolderValueAt.getCallback().onCaptureSequenceCompleted(CameraDeviceImpl.this, requestId, next.getLastFrameNumber());
                        }
                    };
                    long jClearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        captureCallbackHolderValueAt.getExecutor().execute(runnable);
                    } finally {
                        Binder.restoreCallingIdentity(jClearCallingIdentity);
                    }
                }
            }
            if (next.isSequenceCompleted() && next.isInflightCompleted()) {
                int iIndexOfKey2 = this.mCaptureCallbackMap.indexOfKey(requestId);
                if (iIndexOfKey2 >= 0) {
                    this.mCaptureCallbackMap.removeAt(iIndexOfKey2);
                }
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeCompletedCallbackHolderLocked(long j, long j2, long j3) {
        Iterator<RequestLastFrameNumbersHolder> it = this.mRequestLastFrameNumbersList.iterator();
        while (it.hasNext()) {
            RequestLastFrameNumbersHolder next = it.next();
            int requestId = next.getRequestId();
            if (this.mRemoteDevice == null) {
                Log.w(this.TAG, "Camera closed while removing completed callback holders");
                return;
            }
            long lastRegularFrameNumber = next.getLastRegularFrameNumber();
            long lastReprocessFrameNumber = next.getLastReprocessFrameNumber();
            long lastZslStillFrameNumber = next.getLastZslStillFrameNumber();
            if (lastRegularFrameNumber <= j && lastReprocessFrameNumber <= j2 && lastZslStillFrameNumber <= j3) {
                if (next.isSequenceCompleted()) {
                    int iIndexOfKey = this.mCaptureCallbackMap.indexOfKey(requestId);
                    if (iIndexOfKey >= 0) {
                        this.mCaptureCallbackMap.removeAt(iIndexOfKey);
                    }
                    it.remove();
                } else {
                    next.markInflightCompleted();
                }
            }
        }
    }

    public void onClientSharedAccessPriorityChanged(boolean z) {
        synchronized (this.mInterfaceLock) {
            if (this.mRemoteDevice == null && this.mRemoteDeviceInit) {
                return;
            }
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mDeviceExecutor.execute(PooledLambda.obtainRunnable(new BiConsumer() { // from class: android.hardware.camera2.impl.CameraDeviceImpl$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((CameraDeviceImpl) obj).notifyClientSharedAccessPriorityChanged(((Boolean) obj2).booleanValue());
                    }
                }, this, Boolean.valueOf(z)).recycleOnUse());
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyClientSharedAccessPriorityChanged(boolean z) {
        if (isClosed()) {
            return;
        }
        this.mIsPrimaryClient = z;
        this.mDeviceCallback.onClientSharedAccessPriorityChanged(this, z);
    }

    public void onDeviceError(int i, CaptureResultExtras captureResultExtras) {
        synchronized (this.mInterfaceLock) {
            if (this.mRemoteDevice == null && this.mRemoteDeviceInit) {
                return;
            }
            CameraOfflineSessionImpl cameraOfflineSessionImpl = this.mOfflineSessionImpl;
            if (cameraOfflineSessionImpl != null) {
                cameraOfflineSessionImpl.getCallbacks().onDeviceError(i, captureResultExtras);
                return;
            }
            if (i == 0) {
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mDeviceExecutor.execute(this.mCallOnDisconnected);
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            } else if (i == 1) {
                scheduleNotifyError(4);
            } else if (i == 3 || i == 4 || i == 5) {
                onCaptureErrorLocked(i, captureResultExtras);
            } else if (i == 6) {
                scheduleNotifyError(3);
            } else {
                Log.e(this.TAG, "Unknown error from camera device: " + i);
                scheduleNotifyError(5);
            }
        }
    }

    private void scheduleNotifyError(int i) {
        this.mInError = true;
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mDeviceExecutor.execute(PooledLambda.obtainRunnable(new BiConsumer() { // from class: android.hardware.camera2.impl.CameraDeviceImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((CameraDeviceImpl) obj).notifyError(((Integer) obj2).intValue());
                }
            }, this, Integer.valueOf(i)).recycleOnUse());
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyError(int i) {
        if (isClosed()) {
            return;
        }
        this.mDeviceCallback.onError(this, i);
    }

    private void onCaptureErrorLocked(int i, CaptureResultExtras captureResultExtras) {
        long jClearCallingIdentity;
        int requestId = captureResultExtras.getRequestId();
        int subsequenceId = captureResultExtras.getSubsequenceId();
        final long frameNumber = captureResultExtras.getFrameNumber();
        String errorPhysicalCameraId = captureResultExtras.getErrorPhysicalCameraId();
        final CaptureCallbackHolder captureCallbackHolder = this.mCaptureCallbackMap.get(requestId);
        if (captureCallbackHolder == null) {
            Log.e(this.TAG, String.format("Receive capture error on unknown request ID %d", Integer.valueOf(requestId)));
            return;
        }
        final CaptureRequest request = captureCallbackHolder.getRequest(subsequenceId);
        if (i == 5) {
            OutputConfiguration outputConfiguration = this.mConfiguredOutputs.get(captureResultExtras.getErrorStreamId());
            if (outputConfiguration == null) {
                Log.v(this.TAG, String.format("Stream %d has been removed. Skipping buffer lost callback", Integer.valueOf(captureResultExtras.getErrorStreamId())));
                return;
            }
            for (final Surface surface : outputConfiguration.getSurfaces()) {
                if (request.containsTarget(surface)) {
                    Runnable runnable = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.14
                        @Override // java.lang.Runnable
                        public void run() {
                            if (CameraDeviceImpl.this.isClosed()) {
                                return;
                            }
                            captureCallbackHolder.getCallback().onCaptureBufferLost(CameraDeviceImpl.this, request, surface, frameNumber);
                        }
                    };
                    CaptureCallbackHolder captureCallbackHolder2 = captureCallbackHolder;
                    CaptureRequest captureRequest = request;
                    jClearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        captureCallbackHolder2.getExecutor().execute(runnable);
                        Binder.restoreCallingIdentity(jClearCallingIdentity);
                        request = captureRequest;
                        captureCallbackHolder = captureCallbackHolder2;
                    } finally {
                    }
                }
            }
            return;
        }
        boolean z = i == 4;
        CameraCaptureSessionCore cameraCaptureSessionCore = this.mCurrentSession;
        final CaptureFailure captureFailure = new CaptureFailure(request, (cameraCaptureSessionCore == null || !cameraCaptureSessionCore.isAborting()) ? 0 : 1, z, requestId, frameNumber, errorPhysicalCameraId);
        Runnable runnable2 = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.15
            @Override // java.lang.Runnable
            public void run() {
                if (CameraDeviceImpl.this.isClosed()) {
                    return;
                }
                captureCallbackHolder.getCallback().onCaptureFailed(CameraDeviceImpl.this, request, captureFailure);
            }
        };
        if (errorPhysicalCameraId == null) {
            if (this.mBatchOutputMap.containsKey(Integer.valueOf(requestId))) {
                for (int i2 = 0; i2 < this.mBatchOutputMap.get(Integer.valueOf(requestId)).intValue(); i2++) {
                    this.mFrameNumberTracker.updateTracker(frameNumber - (subsequenceId - i2), true, request.getRequestType());
                }
            } else {
                this.mFrameNumberTracker.updateTracker(frameNumber, true, request.getRequestType());
            }
            checkAndFireSequenceComplete();
        }
        jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            captureCallbackHolder.getExecutor().execute(runnable2);
        } finally {
        }
    }

    public void onDeviceIdle() {
        synchronized (this.mInterfaceLock) {
            if (this.mRemoteDevice == null) {
                return;
            }
            CameraOfflineSessionImpl cameraOfflineSessionImpl = this.mOfflineSessionImpl;
            if (cameraOfflineSessionImpl != null) {
                cameraOfflineSessionImpl.getCallbacks().onDeviceIdle();
                return;
            }
            removeCompletedCallbackHolderLocked(Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE);
            if (!this.mIdle) {
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mDeviceExecutor.execute(this.mCallOnIdle);
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    throw th;
                }
            }
            this.mIdle = true;
        }
    }

    public class CameraDeviceCallbacks extends ICameraDeviceCallbacks.Stub {
        @Override // android.hardware.camera2.ICameraDeviceCallbacks.Stub, android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public CameraDeviceCallbacks() {
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onDeviceError(int i, CaptureResultExtras captureResultExtras) {
            CameraDeviceImpl.this.onDeviceError(i, captureResultExtras);
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onRepeatingRequestError(long j, int i) {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice != null && CameraDeviceImpl.this.mRepeatingRequestId != -1) {
                    if (CameraDeviceImpl.this.mOfflineSessionImpl != null) {
                        CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks().onRepeatingRequestError(j, i);
                        return;
                    }
                    CameraDeviceImpl cameraDeviceImpl = CameraDeviceImpl.this;
                    cameraDeviceImpl.checkEarlyTriggerSequenceCompleteLocked(cameraDeviceImpl.mRepeatingRequestId, j, CameraDeviceImpl.this.mRepeatingRequestTypes);
                    if (CameraDeviceImpl.this.mRepeatingRequestId == i) {
                        CameraDeviceImpl.this.mRepeatingRequestId = -1;
                        CameraDeviceImpl.this.mRepeatingRequestTypes = null;
                    }
                    return;
                }
                if (CameraDeviceImpl.this.mFailedRepeatingRequestId == i && CameraDeviceImpl.this.mFailedRepeatingRequestTypes != null && CameraDeviceImpl.this.mRemoteDevice != null) {
                    Log.v(CameraDeviceImpl.this.TAG, "Resuming stop of failed repeating request with id: " + CameraDeviceImpl.this.mFailedRepeatingRequestId);
                    CameraDeviceImpl cameraDeviceImpl2 = CameraDeviceImpl.this;
                    cameraDeviceImpl2.checkEarlyTriggerSequenceCompleteLocked(cameraDeviceImpl2.mFailedRepeatingRequestId, j, CameraDeviceImpl.this.mFailedRepeatingRequestTypes);
                    CameraDeviceImpl.this.mFailedRepeatingRequestId = -1;
                    CameraDeviceImpl.this.mFailedRepeatingRequestTypes = null;
                }
            }
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onDeviceIdle() {
            CameraDeviceImpl.this.onDeviceIdle();
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onCaptureStarted(final CaptureResultExtras captureResultExtras, final long j) {
            int requestId = captureResultExtras.getRequestId();
            final long frameNumber = captureResultExtras.getFrameNumber();
            long lastCompletedRegularFrameNumber = captureResultExtras.getLastCompletedRegularFrameNumber();
            long lastCompletedReprocessFrameNumber = captureResultExtras.getLastCompletedReprocessFrameNumber();
            long lastCompletedZslFrameNumber = captureResultExtras.getLastCompletedZslFrameNumber();
            final boolean zHasReadoutTimestamp = captureResultExtras.hasReadoutTimestamp();
            final long readoutTimestamp = captureResultExtras.getReadoutTimestamp();
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                if (CameraDeviceImpl.this.mOfflineSessionImpl != null) {
                    CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks().onCaptureStarted(captureResultExtras, j);
                    return;
                }
                CameraDeviceImpl.this.removeCompletedCallbackHolderLocked(lastCompletedRegularFrameNumber, lastCompletedReprocessFrameNumber, lastCompletedZslFrameNumber);
                final CaptureCallbackHolder captureCallbackHolder = (CaptureCallbackHolder) CameraDeviceImpl.this.mCaptureCallbackMap.get(requestId);
                if (captureCallbackHolder == null) {
                    return;
                }
                if (CameraDeviceImpl.this.isClosed()) {
                    return;
                }
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    captureCallbackHolder.getExecutor().execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.CameraDeviceCallbacks.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (CameraDeviceImpl.this.isClosed()) {
                                return;
                            }
                            int subsequenceId = captureResultExtras.getSubsequenceId();
                            CaptureRequest request = captureCallbackHolder.getRequest(subsequenceId);
                            if (captureCallbackHolder.hasBatchedOutputs()) {
                                Range range = (Range) request.get(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE);
                                for (int i = 0; i < captureCallbackHolder.getRequestCount(); i++) {
                                    long j2 = subsequenceId - i;
                                    long j3 = j2 * 1000000000;
                                    captureCallbackHolder.getCallback().onCaptureStarted(CameraDeviceImpl.this, captureCallbackHolder.getRequest(i), j - (j3 / ((Integer) range.getUpper()).intValue()), frameNumber - j2);
                                    if (zHasReadoutTimestamp) {
                                        captureCallbackHolder.getCallback().onReadoutStarted(CameraDeviceImpl.this, captureCallbackHolder.getRequest(i), readoutTimestamp - (j3 / ((Integer) range.getUpper()).intValue()), frameNumber - j2);
                                    }
                                }
                                return;
                            }
                            captureCallbackHolder.getCallback().onCaptureStarted(CameraDeviceImpl.this, request, j, frameNumber);
                            if (zHasReadoutTimestamp) {
                                captureCallbackHolder.getCallback().onReadoutStarted(CameraDeviceImpl.this, request, readoutTimestamp, frameNumber);
                            }
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
        }

        private PhysicalCaptureResultInfo[] readMetadata(PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr) {
            CameraMetadataNative cameraMetadata;
            PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr2 = new PhysicalCaptureResultInfo[physicalCaptureResultInfoArr.length];
            int i = 0;
            for (PhysicalCaptureResultInfo physicalCaptureResultInfo : physicalCaptureResultInfoArr) {
                if (physicalCaptureResultInfo.getCameraMetadataInfo().getTag() == 0) {
                    cameraMetadata = new CameraMetadataNative(CameraDeviceImpl.nativeReadResultMetadata(CameraDeviceImpl.this.mFMQReader, physicalCaptureResultInfo.getCameraMetadataInfo().getFmqSize()));
                } else {
                    cameraMetadata = physicalCaptureResultInfo.getCameraMetadata();
                }
                physicalCaptureResultInfoArr2[i] = new PhysicalCaptureResultInfo(physicalCaptureResultInfo.getCameraId(), cameraMetadata);
                i++;
            }
            return physicalCaptureResultInfoArr2;
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onResultReceived(CameraMetadataInfo cameraMetadataInfo, final CaptureResultExtras captureResultExtras, PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr) throws RemoteException {
            CameraMetadataNative metadata;
            Runnable runnable;
            final CaptureCallbackHolder captureCallbackHolder;
            CameraDeviceCallbacks cameraDeviceCallbacks;
            CaptureResult captureResult;
            int requestId = captureResultExtras.getRequestId();
            long frameNumber = captureResultExtras.getFrameNumber();
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                if (cameraMetadataInfo.getTag() == 0) {
                    metadata = new CameraMetadataNative(CameraDeviceImpl.nativeReadResultMetadata(CameraDeviceImpl.this.mFMQReader, cameraMetadataInfo.getFmqSize()));
                } else {
                    metadata = cameraMetadataInfo.getMetadata();
                }
                PhysicalCaptureResultInfo[] metadata2 = readMetadata(physicalCaptureResultInfoArr);
                if (CameraDeviceImpl.this.mOfflineSessionImpl != null) {
                    CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks().onResultReceived(CameraMetadataInfo.metadata(metadata), captureResultExtras, metadata2);
                    return;
                }
                metadata.set((CameraCharacteristics.Key<CameraCharacteristics.Key<Size>>) CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE, (CameraCharacteristics.Key<Size>) CameraDeviceImpl.this.getCharacteristics().get(CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE));
                Map physicalIdToChars = CameraDeviceImpl.this.getPhysicalIdToChars();
                for (PhysicalCaptureResultInfo physicalCaptureResultInfo : metadata2) {
                    String cameraId = physicalCaptureResultInfo.getCameraId();
                    CameraMetadataNative cameraMetadata = physicalCaptureResultInfo.getCameraMetadata();
                    CameraCharacteristics cameraCharacteristics = (CameraCharacteristics) physicalIdToChars.get(cameraId);
                    if (cameraCharacteristics != null) {
                        cameraMetadata.set((CameraCharacteristics.Key<CameraCharacteristics.Key<Size>>) CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE, (CameraCharacteristics.Key<Size>) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE));
                    } else {
                        Log.e(CameraDeviceImpl.this.TAG, "Unable to find characteristics for physical camera " + cameraId);
                    }
                }
                final CaptureCallbackHolder captureCallbackHolder2 = (CaptureCallbackHolder) CameraDeviceImpl.this.mCaptureCallbackMap.get(requestId);
                int i = 1;
                boolean z = captureResultExtras.getPartialResultCount() < CameraDeviceImpl.this.mTotalPartialCount;
                if (captureCallbackHolder2 == null) {
                    return;
                }
                final CaptureRequest request = captureCallbackHolder2.getRequest(captureResultExtras.getSubsequenceId());
                int requestType = request.getRequestType();
                if (CameraDeviceImpl.this.isClosed()) {
                    CameraDeviceImpl.this.updateTracker(requestId, frameNumber, requestType, null, z);
                    return;
                }
                boolean z2 = z;
                final CameraMetadataNative cameraMetadataNative = captureCallbackHolder2.hasBatchedOutputs() ? new CameraMetadataNative(metadata) : null;
                if (z2) {
                    final CaptureResult captureResult2 = new CaptureResult(CameraDeviceImpl.this.getId(), metadata, request, captureResultExtras);
                    runnable = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.CameraDeviceCallbacks.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (CameraDeviceImpl.this.isClosed()) {
                                return;
                            }
                            if (captureCallbackHolder2.hasBatchedOutputs()) {
                                for (int i2 = 0; i2 < captureCallbackHolder2.getRequestCount(); i2++) {
                                    captureCallbackHolder2.getCallback().onCaptureProgressed(CameraDeviceImpl.this, captureCallbackHolder2.getRequest(i2), new CaptureResult(CameraDeviceImpl.this.getId(), new CameraMetadataNative(cameraMetadataNative), captureCallbackHolder2.getRequest(i2), captureResultExtras));
                                }
                                return;
                            }
                            captureCallbackHolder2.getCallback().onCaptureProgressed(CameraDeviceImpl.this, request, captureResult2);
                        }
                    };
                    cameraDeviceCallbacks = this;
                    captureResult = captureResult2;
                    captureCallbackHolder = captureCallbackHolder2;
                } else {
                    List<CaptureResult> listPopPartialResults = CameraDeviceImpl.this.mFrameNumberTracker.popPartialResults(frameNumber);
                    if (CameraDeviceImpl.this.mBatchOutputMap.containsKey(Integer.valueOf(requestId))) {
                        for (int iIntValue = ((Integer) CameraDeviceImpl.this.mBatchOutputMap.get(Integer.valueOf(requestId))).intValue(); i < iIntValue; iIntValue = iIntValue) {
                            CameraDeviceImpl.this.mFrameNumberTracker.popPartialResults(frameNumber - (iIntValue - i));
                            i++;
                            listPopPartialResults = listPopPartialResults;
                        }
                    }
                    final List<CaptureResult> list = listPopPartialResults;
                    final long jLongValue = ((Long) metadata.get(CaptureResult.SENSOR_TIMESTAMP)).longValue();
                    final Range range = (Range) request.get(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE);
                    final int subsequenceId = captureResultExtras.getSubsequenceId();
                    final TotalCaptureResult totalCaptureResult = new TotalCaptureResult(CameraDeviceImpl.this.getId(), metadata, request, captureResultExtras, list, captureCallbackHolder2.getSessionId(), metadata2);
                    captureCallbackHolder = captureCallbackHolder2;
                    runnable = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.CameraDeviceCallbacks.3
                        @Override // java.lang.Runnable
                        public void run() {
                            if (CameraDeviceImpl.this.isClosed()) {
                                return;
                            }
                            if (captureCallbackHolder.hasBatchedOutputs()) {
                                for (int i2 = 0; i2 < captureCallbackHolder.getRequestCount(); i2++) {
                                    cameraMetadataNative.set((CaptureResult.Key<CaptureResult.Key<Long>>) CaptureResult.SENSOR_TIMESTAMP, (CaptureResult.Key<Long>) Long.valueOf(jLongValue - (((subsequenceId - i2) * 1000000000) / ((Integer) range.getUpper()).intValue())));
                                    captureCallbackHolder.getCallback().onCaptureCompleted(CameraDeviceImpl.this, captureCallbackHolder.getRequest(i2), new TotalCaptureResult(CameraDeviceImpl.this.getId(), new CameraMetadataNative(cameraMetadataNative), captureCallbackHolder.getRequest(i2), captureResultExtras, list, captureCallbackHolder.getSessionId(), new PhysicalCaptureResultInfo[0]));
                                }
                                return;
                            }
                            captureCallbackHolder.getCallback().onCaptureCompleted(CameraDeviceImpl.this, request, totalCaptureResult);
                        }
                    };
                    cameraDeviceCallbacks = this;
                    captureResult = totalCaptureResult;
                }
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    captureCallbackHolder.getExecutor().execute(runnable);
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    CameraDeviceImpl.this.updateTracker(requestId, frameNumber, requestType, captureResult, z2);
                    if (!z2) {
                        CameraDeviceImpl.this.checkAndFireSequenceComplete();
                    }
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    throw th;
                }
            }
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onClientSharedAccessPriorityChanged(boolean z) {
            CameraDeviceImpl.this.onClientSharedAccessPriorityChanged(z);
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onPrepared(int i) {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mOfflineSessionImpl != null) {
                    CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks().onPrepared(i);
                    return;
                }
                OutputConfiguration outputConfiguration = (OutputConfiguration) CameraDeviceImpl.this.mConfiguredOutputs.get(i);
                StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
                if (stateCallbackKK == null) {
                    return;
                }
                if (outputConfiguration == null) {
                    Log.w(CameraDeviceImpl.this.TAG, "onPrepared invoked for unknown output Surface");
                    return;
                }
                Iterator<Surface> it = outputConfiguration.getSurfaces().iterator();
                while (it.hasNext()) {
                    stateCallbackKK.onSurfacePrepared(it.next());
                }
            }
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onRequestQueueEmpty() {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mOfflineSessionImpl != null) {
                    CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks().onRequestQueueEmpty();
                    return;
                }
                StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
                if (stateCallbackKK == null) {
                    return;
                }
                stateCallbackKK.onRequestQueueEmpty();
            }
        }
    }

    private static class CameraHandlerExecutor implements Executor {
        private final Handler mHandler;

        public CameraHandlerExecutor(Handler handler) {
            this.mHandler = (Handler) Objects.requireNonNull(handler);
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            this.mHandler.post(runnable);
        }
    }

    static Executor checkExecutor(Executor executor) {
        return executor == null ? checkAndWrapHandler(null) : executor;
    }

    public static <T> Executor checkExecutor(Executor executor, T t) {
        return t != null ? checkExecutor(executor) : executor;
    }

    public static Executor checkAndWrapHandler(Handler handler) {
        return new CameraHandlerExecutor(checkHandler(handler));
    }

    static Handler checkHandler(Handler handler) {
        if (handler != null) {
            return handler;
        }
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper == null) {
            throw new IllegalArgumentException("No handler given, and current thread has no looper!");
        }
        return new Handler(looperMyLooper);
    }

    static <T> Handler checkHandler(Handler handler, T t) {
        return t != null ? checkHandler(handler) : handler;
    }

    private void checkIfCameraClosedOrInError() throws CameraAccessException {
        if (this.mRemoteDevice == null) {
            throw new IllegalStateException("CameraDevice was already closed");
        }
        if (this.mInError) {
            throw new CameraAccessException(3, "The camera device has encountered a serious error");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isClosed() {
        return this.mClosing.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CameraCharacteristics getCharacteristics() {
        return this.mCharacteristics;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        Log.w(this.TAG, "CameraDevice " + this.mCameraId + " died unexpectedly");
        if (this.mRemoteDevice == null) {
            return;
        }
        this.mInError = true;
        Runnable runnable = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.16
            @Override // java.lang.Runnable
            public void run() {
                if (CameraDeviceImpl.this.isClosed()) {
                    return;
                }
                CameraDeviceImpl.this.mDeviceCallback.onError(CameraDeviceImpl.this, 5);
            }
        };
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mDeviceExecutor.execute(runnable);
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    @Override // android.hardware.camera2.CameraDevice
    public void setCameraAudioRestriction(int i) throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            this.mRemoteDevice.setCameraAudioRestriction(i);
        }
    }

    @Override // android.hardware.camera2.CameraDevice
    public int getCameraAudioRestriction() throws CameraAccessException {
        int globalAudioRestriction;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            globalAudioRestriction = this.mRemoteDevice.getGlobalAudioRestriction();
        }
        return globalAudioRestriction;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:37:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v3, types: [int] */
    /* JADX WARN: Type inference failed for: r1v4 */
    @Override // android.hardware.camera2.CameraDevice
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void createExtensionSession(ExtensionSessionConfiguration extensionSessionConfiguration) throws Throwable {
        if (Flags.cameraMultiClient() && this.mSharedMode) {
            throw new UnsupportedOperationException("In shared session mode,extension sessions are not supported.");
        }
        HashMap map = new HashMap(getPhysicalIdToChars());
        map.put(this.mCameraId, this.mCharacteristics);
        StringBuilder sb = new StringBuilder();
        sb.append(this.TAG);
        sb.append(" : ");
        CameraDeviceImpl cameraDeviceImpl = this.mNextSessionId;
        this.mNextSessionId = cameraDeviceImpl + 1;
        sb.append((int) cameraDeviceImpl);
        Binder binder = new Binder(sb.toString());
        try {
            try {
                try {
                    if (!CameraExtensionCharacteristics.registerClient(this.mContext, binder, extensionSessionConfiguration.getExtension(), this.mCameraId, CameraExtensionUtils.getCharacteristicsMapNative(map))) {
                        throw new UnsupportedOperationException("Unsupported extension!");
                    }
                    if (CameraExtensionCharacteristics.areAdvancedExtensionsSupported(extensionSessionConfiguration.getExtension())) {
                        this.mCurrentAdvancedExtensionSession = CameraAdvancedExtensionSessionImpl.createCameraAdvancedExtensionSession(this, map, this.mContext, extensionSessionConfiguration, this.mNextSessionId, binder);
                    } else {
                        this.mCurrentExtensionSession = CameraExtensionSessionImpl.createCameraExtensionSession(this, map, this.mContext, extensionSessionConfiguration, this.mNextSessionId, binder);
                    }
                } catch (RemoteException unused) {
                    throw new CameraAccessException(3);
                }
            } catch (Throwable th) {
                th = th;
                Throwable th2 = th;
                if (binder == null) {
                    CameraExtensionCharacteristics.unregisterClient(cameraDeviceImpl.mContext, binder, extensionSessionConfiguration.getExtension());
                    throw th2;
                }
                throw th2;
            }
        } catch (RemoteException unused2) {
        } catch (Throwable th3) {
            th = th3;
            cameraDeviceImpl = this;
            Throwable th22 = th;
            if (binder == null) {
            }
        }
    }

    private List<OutputConfiguration> applyExtensionStreamOption(List<OutputConfiguration> list, CaptureRequest captureRequest) {
        Integer num;
        CaptureRequest.Key key = new CaptureRequest.Key("samsung.android.control.cameraClient", Integer.class);
        if (captureRequest != null) {
            try {
                num = (Integer) captureRequest.get(key);
            } catch (IllegalArgumentException unused) {
                Log.i(this.TAG, "no camera client key, skip");
                num = null;
            }
            if (num != null && num.intValue() == 3) {
                ArrayList arrayList = new ArrayList();
                for (OutputConfiguration outputConfiguration : list) {
                    if (outputConfiguration.getSurfaceGroupId() != -1) {
                        OutputConfiguration outputConfiguration2 = new OutputConfiguration(-1, outputConfiguration.getSurface(), 0, outputConfiguration.getSurfaceGroupId());
                        outputConfiguration2.setTimestampBase(outputConfiguration.getTimestampBase());
                        outputConfiguration2.setReadoutTimestampEnabled(outputConfiguration.isReadoutTimestampEnabled());
                        outputConfiguration2.setPhysicalCameraId(outputConfiguration.getPhysicalCameraId());
                        arrayList.add(outputConfiguration2);
                    } else {
                        arrayList.add(outputConfiguration);
                    }
                }
                return arrayList;
            }
        }
        return list;
    }
}
