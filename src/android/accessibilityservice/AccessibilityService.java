package android.accessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.accessibilityservice.MagnificationConfig;
import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.graphics.ColorSpace;
import android.graphics.ParcelableColorSpace;
import android.graphics.Region;
import android.hardware.HardwareBuffer;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.view.WindowManagerImpl;
import android.view.accessibility.AccessibilityCache;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityInteractionClient;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import android.view.inputmethod.EditorInfo;
import com.android.internal.inputmethod.CancellationGroup;
import com.android.internal.inputmethod.IAccessibilityInputMethodSession;
import com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;
import com.android.internal.inputmethod.RemoteAccessibilityInputConnection;
import com.android.internal.util.Preconditions;
import com.samsung.android.knox.zt.internal.IKnoxZtInternalService;
import com.samsung.android.knox.zt.internal.KnoxZtInternalConst;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.IntConsumer;

/* loaded from: classes.dex */
public abstract class AccessibilityService extends Service {
    public static final int ACCESSIBILITY_TAKE_SCREENSHOT_REQUEST_INTERVAL_TIMES_MS = 333;
    public static final int ERROR_TAKE_SCREENSHOT_INTERNAL_ERROR = 1;
    public static final int ERROR_TAKE_SCREENSHOT_INTERVAL_TIME_SHORT = 3;
    public static final int ERROR_TAKE_SCREENSHOT_INVALID_DISPLAY = 4;
    public static final int ERROR_TAKE_SCREENSHOT_INVALID_WINDOW = 5;
    public static final int ERROR_TAKE_SCREENSHOT_NO_ACCESSIBILITY_ACCESS = 2;
    public static final int ERROR_TAKE_SCREENSHOT_SECURE_WINDOW = 6;
    public static final int GESTURE_2_FINGER_DOUBLE_TAP = 20;
    public static final int GESTURE_2_FINGER_DOUBLE_TAP_AND_HOLD = 40;
    public static final int GESTURE_2_FINGER_SINGLE_TAP = 19;
    public static final int GESTURE_2_FINGER_SWIPE_DOWN = 26;
    public static final int GESTURE_2_FINGER_SWIPE_LEFT = 27;
    public static final int GESTURE_2_FINGER_SWIPE_RIGHT = 28;
    public static final int GESTURE_2_FINGER_SWIPE_UP = 25;
    public static final int GESTURE_2_FINGER_TRIPLE_TAP = 21;
    public static final int GESTURE_2_FINGER_TRIPLE_TAP_AND_HOLD = 43;
    public static final int GESTURE_3_FINGER_DOUBLE_TAP = 23;
    public static final int GESTURE_3_FINGER_DOUBLE_TAP_AND_HOLD = 41;
    public static final int GESTURE_3_FINGER_SINGLE_TAP = 22;
    public static final int GESTURE_3_FINGER_SINGLE_TAP_AND_HOLD = 44;
    public static final int GESTURE_3_FINGER_SWIPE_DOWN = 30;
    public static final int GESTURE_3_FINGER_SWIPE_LEFT = 31;
    public static final int GESTURE_3_FINGER_SWIPE_RIGHT = 32;
    public static final int GESTURE_3_FINGER_SWIPE_UP = 29;
    public static final int GESTURE_3_FINGER_TRIPLE_TAP = 24;
    public static final int GESTURE_3_FINGER_TRIPLE_TAP_AND_HOLD = 45;
    public static final int GESTURE_4_FINGER_DOUBLE_TAP = 38;
    public static final int GESTURE_4_FINGER_DOUBLE_TAP_AND_HOLD = 42;
    public static final int GESTURE_4_FINGER_SINGLE_TAP = 37;
    public static final int GESTURE_4_FINGER_SWIPE_DOWN = 34;
    public static final int GESTURE_4_FINGER_SWIPE_LEFT = 35;
    public static final int GESTURE_4_FINGER_SWIPE_RIGHT = 36;
    public static final int GESTURE_4_FINGER_SWIPE_UP = 33;
    public static final int GESTURE_4_FINGER_TRIPLE_TAP = 39;
    public static final int GESTURE_DOUBLE_TAP = 17;
    public static final int GESTURE_DOUBLE_TAP_AND_HOLD = 18;
    public static final int GESTURE_PASSTHROUGH = -1;
    public static final int GESTURE_SWIPE_DOWN = 2;
    public static final int GESTURE_SWIPE_DOWN_AND_LEFT = 15;
    public static final int GESTURE_SWIPE_DOWN_AND_RIGHT = 16;
    public static final int GESTURE_SWIPE_DOWN_AND_UP = 8;
    public static final int GESTURE_SWIPE_LEFT = 3;
    public static final int GESTURE_SWIPE_LEFT_AND_DOWN = 10;
    public static final int GESTURE_SWIPE_LEFT_AND_RIGHT = 5;
    public static final int GESTURE_SWIPE_LEFT_AND_UP = 9;
    public static final int GESTURE_SWIPE_RIGHT = 4;
    public static final int GESTURE_SWIPE_RIGHT_AND_DOWN = 12;
    public static final int GESTURE_SWIPE_RIGHT_AND_LEFT = 6;
    public static final int GESTURE_SWIPE_RIGHT_AND_UP = 11;
    public static final int GESTURE_SWIPE_UP = 1;
    public static final int GESTURE_SWIPE_UP_AND_DOWN = 7;
    public static final int GESTURE_SWIPE_UP_AND_LEFT = 13;
    public static final int GESTURE_SWIPE_UP_AND_RIGHT = 14;
    public static final int GESTURE_TOUCH_EXPLORATION = -2;
    public static final int GESTURE_TO_STOP_TALKBACK = 1000;
    public static final int GESTURE_UNKNOWN = 0;
    public static final int GLOBAL_ACTION_ACCESSIBILITY_ALL_APPS = 14;
    public static final int GLOBAL_ACTION_ACCESSIBILITY_BUTTON = 11;
    public static final int GLOBAL_ACTION_ACCESSIBILITY_BUTTON_CHOOSER = 12;
    public static final int GLOBAL_ACTION_ACCESSIBILITY_SHORTCUT = 13;
    public static final int GLOBAL_ACTION_BACK = 1;
    public static final int GLOBAL_ACTION_DISMISS_NOTIFICATION_SHADE = 15;
    public static final int GLOBAL_ACTION_DPAD_CENTER = 20;
    public static final int GLOBAL_ACTION_DPAD_DOWN = 17;
    public static final int GLOBAL_ACTION_DPAD_LEFT = 18;
    public static final int GLOBAL_ACTION_DPAD_RIGHT = 19;
    public static final int GLOBAL_ACTION_DPAD_UP = 16;
    public static final int GLOBAL_ACTION_HOME = 2;
    public static final int GLOBAL_ACTION_KEYCODE_HEADSETHOOK = 10;
    public static final int GLOBAL_ACTION_LOCK_SCREEN = 8;
    public static final int GLOBAL_ACTION_MEDIA_PLAY_PAUSE = 22;
    public static final int GLOBAL_ACTION_MENU = 21;
    public static final int GLOBAL_ACTION_NOTIFICATIONS = 4;
    public static final int GLOBAL_ACTION_POWER_DIALOG = 6;
    public static final int GLOBAL_ACTION_QUICK_SETTINGS = 5;
    public static final int GLOBAL_ACTION_RECENTS = 3;
    public static final int GLOBAL_ACTION_TAKE_SCREENSHOT = 9;
    public static final int GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN = 7;
    public static final String KEY_ACCESSIBILITY_SCREENSHOT_COLORSPACE = "screenshot_colorSpace";
    public static final String KEY_ACCESSIBILITY_SCREENSHOT_HARDWAREBUFFER = "screenshot_hardwareBuffer";
    public static final String KEY_ACCESSIBILITY_SCREENSHOT_STATUS = "screenshot_status";
    public static final String KEY_ACCESSIBILITY_SCREENSHOT_TIMESTAMP = "screenshot_timestamp";
    private static final String LOG_TAG = "AccessibilityService";
    public static final int OVERLAY_RESULT_INTERNAL_ERROR = 1;
    public static final int OVERLAY_RESULT_INVALID = 2;
    public static final int OVERLAY_RESULT_SUCCESS = 0;
    public static final String SERVICE_INTERFACE = "android.accessibilityservice.AccessibilityService";
    public static final String SERVICE_META_DATA = "android.accessibilityservice";
    public static final int SHOW_MODE_AUTO = 0;
    public static final int SHOW_MODE_HARD_KEYBOARD_ORIGINAL_VALUE = 536870912;
    public static final int SHOW_MODE_HARD_KEYBOARD_OVERRIDDEN = 1073741824;
    public static final int SHOW_MODE_HIDDEN = 1;
    public static final int SHOW_MODE_IGNORE_HARD_KEYBOARD = 2;
    public static final int SHOW_MODE_MASK = 3;
    public static final int TAKE_SCREENSHOT_SUCCESS = 0;
    private BrailleDisplayController mBrailleDisplayController;
    private FingerprintGestureController mFingerprintGestureController;
    private SparseArray<GestureResultCallbackInfo> mGestureStatusCallbackInfos;
    private int mGestureStatusCallbackSequence;
    private AccessibilityServiceInfo mInfo;
    private InputMethod mInputMethod;
    private int mMotionEventSources;
    private SoftKeyboardController mSoftKeyboardController;
    private WindowManager mWindowManager;
    private IBinder mWindowToken;
    private int mConnectionId = -1;
    private final SparseArray<MagnificationController> mMagnificationControllers = new SparseArray<>(0);
    private final SparseArray<TouchInteractionController> mTouchInteractionControllers = new SparseArray<>(0);
    private boolean mInputMethodInitialized = false;
    private final SparseArray<AccessibilityButtonController> mAccessibilityButtonControllers = new SparseArray<>(0);
    private final Object mLock = new Object();

    @Retention(RetentionPolicy.SOURCE)
    public @interface AttachOverlayResult {
    }

    public interface Callbacks {
        void createImeSession(IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback);

        void init(int i, IBinder iBinder);

        void onAccessibilityButtonAvailabilityChanged(boolean z);

        void onAccessibilityButtonClicked(int i);

        void onAccessibilityEvent(AccessibilityEvent accessibilityEvent);

        void onFingerprintCapturingGesturesChanged(boolean z);

        void onFingerprintGesture(int i);

        boolean onGesture(AccessibilityGestureEvent accessibilityGestureEvent);

        void onInterrupt();

        boolean onKeyEvent(KeyEvent keyEvent);

        void onMagnificationChanged(int i, Region region, MagnificationConfig magnificationConfig);

        void onMotionEvent(MotionEvent motionEvent);

        void onPerformGestureResult(int i, boolean z);

        void onServiceConnected();

        void onSoftKeyboardShowModeChanged(int i);

        void onSystemActionsChanged();

        void onTouchStateChanged(int i, int i2);

        void startInput(RemoteAccessibilityInputConnection remoteAccessibilityInputConnection, EditorInfo editorInfo, boolean z);
    }

    public static abstract class GestureResultCallback {
        public void onCancelled(GestureDescription gestureDescription) {
        }

        public void onCompleted(GestureDescription gestureDescription) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScreenshotErrorCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SoftKeyboardShowMode {
    }

    public interface TakeScreenshotCallback {
        void onFailure(int i);

        void onSuccess(ScreenshotResult screenshotResult);
    }

    public abstract void onAccessibilityEvent(AccessibilityEvent accessibilityEvent);

    @Deprecated
    protected boolean onGesture(int i) {
        return false;
    }

    public abstract void onInterrupt();

    protected boolean onKeyEvent(KeyEvent keyEvent) {
        return false;
    }

    public void onMotionEvent(MotionEvent motionEvent) {
    }

    protected void onServiceConnected() {
    }

    public void onSystemActionsChanged() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchServiceConnected() {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mMagnificationControllers.size(); i++) {
                this.mMagnificationControllers.valueAt(i).onServiceConnectedLocked();
            }
            AccessibilityServiceInfo serviceInfo = getServiceInfo();
            if (serviceInfo != null) {
                updateInputMethod(serviceInfo);
                this.mMotionEventSources = serviceInfo.getMotionEventSources();
            }
        }
        SoftKeyboardController softKeyboardController = this.mSoftKeyboardController;
        if (softKeyboardController != null) {
            softKeyboardController.onServiceConnected();
        }
        onServiceConnected();
    }

    private void updateInputMethod(AccessibilityServiceInfo accessibilityServiceInfo) {
        if (accessibilityServiceInfo != null) {
            boolean z = (accessibilityServiceInfo.flags & 32768) != 0;
            if (z && !this.mInputMethodInitialized) {
                this.mInputMethod = onCreateInputMethod();
                this.mInputMethodInitialized = true;
            } else if ((!z) && this.mInputMethodInitialized) {
                this.mInputMethod = null;
                this.mInputMethodInitialized = false;
            }
        }
    }

    public boolean onGesture(AccessibilityGestureEvent accessibilityGestureEvent) {
        if (accessibilityGestureEvent.getDisplayId() != 0) {
            return false;
        }
        onGesture(accessibilityGestureEvent.getGestureId());
        return false;
    }

    public List<AccessibilityWindowInfo> getWindows() {
        notifyKnoxZtInternalService(19);
        return AccessibilityInteractionClient.getInstance(this).getWindows(this.mConnectionId);
    }

    public final SparseArray<List<AccessibilityWindowInfo>> getWindowsOnAllDisplays() {
        notifyKnoxZtInternalService(20);
        return AccessibilityInteractionClient.getInstance(this).getWindowsOnAllDisplays(this.mConnectionId);
    }

    public AccessibilityNodeInfo getRootInActiveWindow() {
        return getRootInActiveWindow(4);
    }

    public AccessibilityNodeInfo getRootInActiveWindow(int i) {
        notifyKnoxZtInternalService(13);
        return AccessibilityInteractionClient.getInstance(this).getRootInActiveWindow(this.mConnectionId, i);
    }

    public final void disableSelf() {
        notifyKnoxZtInternalService(6);
        AccessibilityInteractionClient.getInstance(this);
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                connection.disableSelf();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Context createDisplayContext(Display display) {
        notifyKnoxZtInternalService(4);
        return new AccessibilityContext(super.createDisplayContext(display), this.mConnectionId);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Context createWindowContext(int i, Bundle bundle) {
        notifyKnoxZtInternalService(5);
        Context createWindowContext = super.createWindowContext(i, bundle);
        return i != 2032 ? createWindowContext : new AccessibilityContext(createWindowContext, this.mConnectionId);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Context createWindowContext(Display display, int i, Bundle bundle) {
        notifyKnoxZtInternalService(5);
        Context createWindowContext = super.createWindowContext(display, i, bundle);
        return i != 2032 ? createWindowContext : new AccessibilityContext(createWindowContext, this.mConnectionId);
    }

    public final MagnificationController getMagnificationController() {
        return getMagnificationController(0);
    }

    public final MagnificationController getMagnificationController(int i) {
        MagnificationController magnificationController;
        notifyKnoxZtInternalService(12);
        synchronized (this.mLock) {
            magnificationController = this.mMagnificationControllers.get(i);
            if (magnificationController == null) {
                magnificationController = new MagnificationController(this, this.mLock, i);
                this.mMagnificationControllers.put(i, magnificationController);
            }
        }
        return magnificationController;
    }

    public final FingerprintGestureController getFingerprintGestureController() {
        notifyKnoxZtInternalService(10);
        if (this.mFingerprintGestureController == null) {
            AccessibilityInteractionClient.getInstance(this);
            this.mFingerprintGestureController = new FingerprintGestureController(AccessibilityInteractionClient.getConnection(this.mConnectionId));
        }
        return this.mFingerprintGestureController;
    }

    public final boolean dispatchGesture(GestureDescription gestureDescription, GestureResultCallback gestureResultCallback, Handler handler) {
        notifyKnoxZtInternalService(7);
        AccessibilityInteractionClient.getInstance(this);
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection == null) {
            return false;
        }
        List<GestureDescription.GestureStep> gestureStepsFromGestureDescription = GestureDescription.MotionEventGenerator.getGestureStepsFromGestureDescription(gestureDescription, calculateGestureSampleTimeMs(gestureDescription.getDisplayId()));
        try {
            synchronized (this.mLock) {
                this.mGestureStatusCallbackSequence++;
                if (gestureResultCallback != null) {
                    if (this.mGestureStatusCallbackInfos == null) {
                        this.mGestureStatusCallbackInfos = new SparseArray<>();
                    }
                    this.mGestureStatusCallbackInfos.put(this.mGestureStatusCallbackSequence, new GestureResultCallbackInfo(gestureDescription, gestureResultCallback, handler));
                }
                connection.dispatchGesture(this.mGestureStatusCallbackSequence, new ParceledListSlice(gestureStepsFromGestureDescription), gestureDescription.getDisplayId());
            }
            return true;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private int calculateGestureSampleTimeMs(int i) {
        Display display;
        int refreshRate;
        if (getApplicationInfo().targetSdkVersion > 29 && (display = ((DisplayManager) getSystemService(DisplayManager.class)).getDisplay(i)) != null && (refreshRate = (int) (1000 / display.getRefreshRate())) >= 1) {
            return refreshRate;
        }
        return 100;
    }

    void onPerformGestureResult(int i, final boolean z) {
        final GestureResultCallbackInfo gestureResultCallbackInfo;
        if (this.mGestureStatusCallbackInfos == null) {
            return;
        }
        synchronized (this.mLock) {
            gestureResultCallbackInfo = this.mGestureStatusCallbackInfos.get(i);
            this.mGestureStatusCallbackInfos.remove(i);
        }
        if (gestureResultCallbackInfo == null || gestureResultCallbackInfo.gestureDescription == null || gestureResultCallbackInfo.callback == null) {
            return;
        }
        if (gestureResultCallbackInfo.handler != null) {
            gestureResultCallbackInfo.handler.post(new Runnable(this) { // from class: android.accessibilityservice.AccessibilityService.1
                @Override // java.lang.Runnable
                public void run() {
                    if (z) {
                        gestureResultCallbackInfo.callback.onCompleted(gestureResultCallbackInfo.gestureDescription);
                    } else {
                        gestureResultCallbackInfo.callback.onCancelled(gestureResultCallbackInfo.gestureDescription);
                    }
                }
            });
        } else if (z) {
            gestureResultCallbackInfo.callback.onCompleted(gestureResultCallbackInfo.gestureDescription);
        } else {
            gestureResultCallbackInfo.callback.onCancelled(gestureResultCallbackInfo.gestureDescription);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMagnificationChanged(int i, Region region, MagnificationConfig magnificationConfig) {
        MagnificationController magnificationController;
        synchronized (this.mLock) {
            magnificationController = this.mMagnificationControllers.get(i);
        }
        if (magnificationController != null) {
            magnificationController.dispatchMagnificationChanged(region, magnificationConfig);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFingerprintCapturingGesturesChanged(boolean z) {
        getFingerprintGestureController().onGestureDetectionActiveChanged(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFingerprintGesture(int i) {
        getFingerprintGestureController().onGesture(i);
    }

    public int getConnectionId() {
        return this.mConnectionId;
    }

    public static final class MagnificationController {
        private final int mDisplayId;
        private ArrayMap<OnMagnificationChangedListener, Handler> mListeners;
        private final Object mLock;
        private final AccessibilityService mService;

        MagnificationController(AccessibilityService accessibilityService, Object obj, int i) {
            this.mService = accessibilityService;
            this.mLock = obj;
            this.mDisplayId = i;
        }

        void onServiceConnectedLocked() {
            ArrayMap<OnMagnificationChangedListener, Handler> arrayMap = this.mListeners;
            if (arrayMap == null || arrayMap.isEmpty()) {
                return;
            }
            setMagnificationCallbackEnabled(true);
        }

        public void addListener(OnMagnificationChangedListener onMagnificationChangedListener) {
            addListener(onMagnificationChangedListener, null);
        }

        public void addListener(OnMagnificationChangedListener onMagnificationChangedListener, Handler handler) {
            synchronized (this.mLock) {
                if (this.mListeners == null) {
                    this.mListeners = new ArrayMap<>();
                }
                boolean isEmpty = this.mListeners.isEmpty();
                this.mListeners.put(onMagnificationChangedListener, handler);
                if (isEmpty) {
                    setMagnificationCallbackEnabled(true);
                }
            }
        }

        public boolean removeListener(OnMagnificationChangedListener onMagnificationChangedListener) {
            boolean z;
            if (this.mListeners == null) {
                return false;
            }
            synchronized (this.mLock) {
                int indexOfKey = this.mListeners.indexOfKey(onMagnificationChangedListener);
                z = indexOfKey >= 0;
                if (z) {
                    this.mListeners.removeAt(indexOfKey);
                }
                if (z && this.mListeners.isEmpty()) {
                    setMagnificationCallbackEnabled(false);
                }
            }
            return z;
        }

        private void setMagnificationCallbackEnabled(boolean z) {
            AccessibilityInteractionClient.getInstance(this.mService);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    connection.setMagnificationCallbackEnabled(this.mDisplayId, z);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        void dispatchMagnificationChanged(final Region region, final MagnificationConfig magnificationConfig) {
            synchronized (this.mLock) {
                ArrayMap<OnMagnificationChangedListener, Handler> arrayMap = this.mListeners;
                if (arrayMap != null && !arrayMap.isEmpty()) {
                    ArrayMap arrayMap2 = new ArrayMap(this.mListeners);
                    int size = arrayMap2.size();
                    for (int i = 0; i < size; i++) {
                        final OnMagnificationChangedListener onMagnificationChangedListener = (OnMagnificationChangedListener) arrayMap2.keyAt(i);
                        Handler handler = (Handler) arrayMap2.valueAt(i);
                        if (handler != null) {
                            handler.post(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$MagnificationController$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    AccessibilityService.MagnificationController.this.lambda$dispatchMagnificationChanged$0(onMagnificationChangedListener, region, magnificationConfig);
                                }
                            });
                        } else {
                            onMagnificationChangedListener.onMagnificationChanged(this, region, magnificationConfig);
                        }
                    }
                    return;
                }
                Slog.d("AccessibilityService", "Received magnification changed callback with no listeners registered!");
                setMagnificationCallbackEnabled(false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchMagnificationChanged$0(OnMagnificationChangedListener onMagnificationChangedListener, Region region, MagnificationConfig magnificationConfig) {
            onMagnificationChangedListener.onMagnificationChanged(this, region, magnificationConfig);
        }

        public MagnificationConfig getMagnificationConfig() {
            AccessibilityInteractionClient.getInstance(this.mService);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection == null) {
                return null;
            }
            try {
                return connection.getMagnificationConfig(this.mDisplayId);
            } catch (RemoteException e) {
                Log.w("AccessibilityService", "Failed to obtain magnification config", e);
                e.rethrowFromSystemServer();
                return null;
            }
        }

        @Deprecated
        public float getScale() {
            AccessibilityInteractionClient.getInstance(this.mService);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection == null) {
                return 1.0f;
            }
            try {
                return connection.getMagnificationScale(this.mDisplayId);
            } catch (RemoteException e) {
                Log.w("AccessibilityService", "Failed to obtain scale", e);
                e.rethrowFromSystemServer();
                return 1.0f;
            }
        }

        @Deprecated
        public float getCenterX() {
            AccessibilityInteractionClient.getInstance(this.mService);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection == null) {
                return 0.0f;
            }
            try {
                return connection.getMagnificationCenterX(this.mDisplayId);
            } catch (RemoteException e) {
                Log.w("AccessibilityService", "Failed to obtain center X", e);
                e.rethrowFromSystemServer();
                return 0.0f;
            }
        }

        @Deprecated
        public float getCenterY() {
            AccessibilityInteractionClient.getInstance(this.mService);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection == null) {
                return 0.0f;
            }
            try {
                return connection.getMagnificationCenterY(this.mDisplayId);
            } catch (RemoteException e) {
                Log.w("AccessibilityService", "Failed to obtain center Y", e);
                e.rethrowFromSystemServer();
                return 0.0f;
            }
        }

        @Deprecated
        public Region getMagnificationRegion() {
            AccessibilityInteractionClient.getInstance(this.mService);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    return connection.getMagnificationRegion(this.mDisplayId);
                } catch (RemoteException e) {
                    Log.w("AccessibilityService", "Failed to obtain magnified region", e);
                    e.rethrowFromSystemServer();
                }
            }
            return Region.obtain();
        }

        public Region getCurrentMagnificationRegion() {
            AccessibilityInteractionClient.getInstance(this.mService);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    return connection.getCurrentMagnificationRegion(this.mDisplayId);
                } catch (RemoteException e) {
                    Log.w("AccessibilityService", "Failed to obtain the current magnified region", e);
                    e.rethrowFromSystemServer();
                }
            }
            return Region.obtain();
        }

        public boolean reset(boolean z) {
            AccessibilityInteractionClient.getInstance(this.mService);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection == null) {
                return false;
            }
            try {
                return connection.resetMagnification(this.mDisplayId, z);
            } catch (RemoteException e) {
                Log.w("AccessibilityService", "Failed to reset", e);
                e.rethrowFromSystemServer();
                return false;
            }
        }

        public boolean resetCurrentMagnification(boolean z) {
            AccessibilityInteractionClient.getInstance(this.mService);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection == null) {
                return false;
            }
            try {
                return connection.resetCurrentMagnification(this.mDisplayId, z);
            } catch (RemoteException e) {
                Log.w("AccessibilityService", "Failed to reset", e);
                e.rethrowFromSystemServer();
                return false;
            }
        }

        public boolean setMagnificationConfig(MagnificationConfig magnificationConfig, boolean z) {
            AccessibilityInteractionClient.getInstance(this.mService);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection == null) {
                return false;
            }
            try {
                return connection.setMagnificationConfig(this.mDisplayId, magnificationConfig, z);
            } catch (RemoteException e) {
                Log.w("AccessibilityService", "Failed to set magnification config", e);
                e.rethrowFromSystemServer();
                return false;
            }
        }

        @Deprecated
        public boolean setScale(float f, boolean z) {
            AccessibilityInteractionClient.getInstance(this.mService);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection == null) {
                return false;
            }
            try {
                return connection.setMagnificationConfig(this.mDisplayId, new MagnificationConfig.Builder().setMode(1).setScale(f).build(), z);
            } catch (RemoteException e) {
                Log.w("AccessibilityService", "Failed to set scale", e);
                e.rethrowFromSystemServer();
                return false;
            }
        }

        @Deprecated
        public boolean setCenter(float f, float f2, boolean z) {
            AccessibilityInteractionClient.getInstance(this.mService);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection == null) {
                return false;
            }
            try {
                return connection.setMagnificationConfig(this.mDisplayId, new MagnificationConfig.Builder().setMode(1).setCenterX(f).setCenterY(f2).build(), z);
            } catch (RemoteException e) {
                Log.w("AccessibilityService", "Failed to set center", e);
                e.rethrowFromSystemServer();
                return false;
            }
        }

        public interface OnMagnificationChangedListener {
            @Deprecated
            void onMagnificationChanged(MagnificationController magnificationController, Region region, float f, float f2, float f3);

            default void onMagnificationChanged(MagnificationController magnificationController, Region region, MagnificationConfig magnificationConfig) {
                if (magnificationConfig.getMode() == 1) {
                    onMagnificationChanged(magnificationController, region, magnificationConfig.getScale(), magnificationConfig.getCenterX(), magnificationConfig.getCenterY());
                }
            }
        }
    }

    public final SoftKeyboardController getSoftKeyboardController() {
        SoftKeyboardController softKeyboardController;
        notifyKnoxZtInternalService(15);
        synchronized (this.mLock) {
            if (this.mSoftKeyboardController == null) {
                this.mSoftKeyboardController = new SoftKeyboardController(this, this.mLock);
            }
            softKeyboardController = this.mSoftKeyboardController;
        }
        return softKeyboardController;
    }

    public InputMethod onCreateInputMethod() {
        return new InputMethod(this);
    }

    public final InputMethod getInputMethod() {
        notifyKnoxZtInternalService(11);
        return this.mInputMethod;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSoftKeyboardShowModeChanged(int i) {
        SoftKeyboardController softKeyboardController = this.mSoftKeyboardController;
        if (softKeyboardController != null) {
            softKeyboardController.dispatchSoftKeyboardShowModeChanged(i);
        }
    }

    public static final class SoftKeyboardController {
        public static final int ENABLE_IME_FAIL_BY_ADMIN = 1;
        public static final int ENABLE_IME_FAIL_UNKNOWN = 2;
        public static final int ENABLE_IME_SUCCESS = 0;
        private ArrayMap<OnShowModeChangedListener, Handler> mListeners;
        private final Object mLock;
        private final AccessibilityService mService;

        @Retention(RetentionPolicy.SOURCE)
        public @interface EnableImeResult {
        }

        public interface OnShowModeChangedListener {
            void onShowModeChanged(SoftKeyboardController softKeyboardController, int i);
        }

        SoftKeyboardController(AccessibilityService accessibilityService, Object obj) {
            this.mService = accessibilityService;
            this.mLock = obj;
        }

        void onServiceConnected() {
            synchronized (this.mLock) {
                ArrayMap<OnShowModeChangedListener, Handler> arrayMap = this.mListeners;
                if (arrayMap != null && !arrayMap.isEmpty()) {
                    setSoftKeyboardCallbackEnabled(true);
                }
            }
        }

        public void addOnShowModeChangedListener(OnShowModeChangedListener onShowModeChangedListener) {
            addOnShowModeChangedListener(onShowModeChangedListener, null);
        }

        public void addOnShowModeChangedListener(OnShowModeChangedListener onShowModeChangedListener, Handler handler) {
            synchronized (this.mLock) {
                if (this.mListeners == null) {
                    this.mListeners = new ArrayMap<>();
                }
                boolean isEmpty = this.mListeners.isEmpty();
                this.mListeners.put(onShowModeChangedListener, handler);
                if (isEmpty) {
                    setSoftKeyboardCallbackEnabled(true);
                }
            }
        }

        public boolean removeOnShowModeChangedListener(OnShowModeChangedListener onShowModeChangedListener) {
            boolean z;
            if (this.mListeners == null) {
                return false;
            }
            synchronized (this.mLock) {
                int indexOfKey = this.mListeners.indexOfKey(onShowModeChangedListener);
                z = indexOfKey >= 0;
                if (z) {
                    this.mListeners.removeAt(indexOfKey);
                }
                if (z && this.mListeners.isEmpty()) {
                    setSoftKeyboardCallbackEnabled(false);
                }
            }
            return z;
        }

        private void setSoftKeyboardCallbackEnabled(boolean z) {
            AccessibilityInteractionClient.getInstance(this.mService);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    connection.setSoftKeyboardCallbackEnabled(z);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        void dispatchSoftKeyboardShowModeChanged(final int i) {
            synchronized (this.mLock) {
                ArrayMap<OnShowModeChangedListener, Handler> arrayMap = this.mListeners;
                if (arrayMap != null && !arrayMap.isEmpty()) {
                    ArrayMap arrayMap2 = new ArrayMap(this.mListeners);
                    int size = arrayMap2.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        final OnShowModeChangedListener onShowModeChangedListener = (OnShowModeChangedListener) arrayMap2.keyAt(i2);
                        Handler handler = (Handler) arrayMap2.valueAt(i2);
                        if (handler != null) {
                            handler.post(new Runnable() { // from class: android.accessibilityservice.AccessibilityService.SoftKeyboardController.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    onShowModeChangedListener.onShowModeChanged(SoftKeyboardController.this, i);
                                }
                            });
                        } else {
                            onShowModeChangedListener.onShowModeChanged(this, i);
                        }
                    }
                    return;
                }
                Slog.w("AccessibilityService", "Received soft keyboard show mode changed callback with no listeners registered!");
                setSoftKeyboardCallbackEnabled(false);
            }
        }

        public int getShowMode() {
            AccessibilityInteractionClient.getInstance(this.mService);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection == null) {
                return 0;
            }
            try {
                return connection.getSoftKeyboardShowMode();
            } catch (RemoteException e) {
                Log.w("AccessibilityService", "Failed to set soft keyboard behavior", e);
                e.rethrowFromSystemServer();
                return 0;
            }
        }

        public boolean setShowMode(int i) {
            AccessibilityInteractionClient.getInstance(this.mService);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection == null) {
                return false;
            }
            try {
                return connection.setSoftKeyboardShowMode(i);
            } catch (RemoteException e) {
                Log.w("AccessibilityService", "Failed to set soft keyboard behavior", e);
                e.rethrowFromSystemServer();
                return false;
            }
        }

        public boolean switchToInputMethod(String str) {
            AccessibilityInteractionClient.getInstance(this.mService);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection == null) {
                return false;
            }
            try {
                return connection.switchToInputMethod(str);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        public int setInputMethodEnabled(String str, boolean z) throws SecurityException {
            AccessibilityInteractionClient.getInstance(this.mService);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection == null) {
                return 2;
            }
            try {
                return connection.setInputMethodEnabled(str, z);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public final AccessibilityButtonController getAccessibilityButtonController() {
        return getAccessibilityButtonController(0);
    }

    public final AccessibilityButtonController getAccessibilityButtonController(int i) {
        AccessibilityButtonController accessibilityButtonController;
        notifyKnoxZtInternalService(9);
        synchronized (this.mLock) {
            accessibilityButtonController = this.mAccessibilityButtonControllers.get(i);
            if (accessibilityButtonController == null) {
                AccessibilityInteractionClient.getInstance(this);
                accessibilityButtonController = new AccessibilityButtonController(AccessibilityInteractionClient.getConnection(this.mConnectionId));
                this.mAccessibilityButtonControllers.put(i, accessibilityButtonController);
            }
        }
        return accessibilityButtonController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAccessibilityButtonClicked(int i) {
        getAccessibilityButtonController(i).dispatchAccessibilityButtonClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAccessibilityButtonAvailabilityChanged(boolean z) {
        getAccessibilityButtonController().dispatchAccessibilityButtonAvailabilityChanged(z);
    }

    public boolean setCacheEnabled(boolean z) {
        IAccessibilityServiceConnection connection;
        notifyKnoxZtInternalService(32);
        AccessibilityCache cache = AccessibilityInteractionClient.getCache(this.mConnectionId);
        if (cache == null || (connection = AccessibilityInteractionClient.getConnection(this.mConnectionId)) == null) {
            return false;
        }
        try {
            connection.setCacheEnabled(z);
            cache.setEnabled(z);
            return true;
        } catch (RemoteException e) {
            Log.w("AccessibilityService", "Error while setting status of cache", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean clearCachedSubtree(AccessibilityNodeInfo accessibilityNodeInfo) {
        notifyKnoxZtInternalService(3);
        AccessibilityCache cache = AccessibilityInteractionClient.getCache(this.mConnectionId);
        if (cache == null) {
            return false;
        }
        return cache.clearSubTree(accessibilityNodeInfo);
    }

    public boolean clearCache() {
        notifyKnoxZtInternalService(2);
        AccessibilityCache cache = AccessibilityInteractionClient.getCache(this.mConnectionId);
        if (cache == null) {
            return false;
        }
        cache.clear();
        return true;
    }

    public boolean isNodeInCache(AccessibilityNodeInfo accessibilityNodeInfo) {
        notifyKnoxZtInternalService(22);
        AccessibilityCache cache = AccessibilityInteractionClient.getCache(this.mConnectionId);
        if (cache == null) {
            return false;
        }
        return cache.isNodeInCache(accessibilityNodeInfo);
    }

    public boolean isCacheEnabled() {
        notifyKnoxZtInternalService(21);
        AccessibilityCache cache = AccessibilityInteractionClient.getCache(this.mConnectionId);
        if (cache == null) {
            return false;
        }
        return cache.isEnabled();
    }

    public final List<AccessibilityNodeInfo.AccessibilityAction> getSystemActions() {
        notifyKnoxZtInternalService(16);
        AccessibilityInteractionClient.getInstance(this);
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                return connection.getSystemActions();
            } catch (RemoteException e) {
                Log.w("AccessibilityService", "Error while calling getSystemActions", e);
                e.rethrowFromSystemServer();
            }
        }
        return Collections.EMPTY_LIST;
    }

    public final boolean performGlobalAction(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("action", i);
        notifyKnoxZtInternalService(29, bundle);
        AccessibilityInteractionClient.getInstance(this);
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection == null) {
            return false;
        }
        try {
            return connection.performGlobalAction(i);
        } catch (RemoteException e) {
            Log.w("AccessibilityService", "Error while calling performGlobalAction", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public AccessibilityNodeInfo findFocus(int i) {
        notifyKnoxZtInternalService(8);
        return AccessibilityInteractionClient.getInstance(this).findFocus(this.mConnectionId, -2, AccessibilityNodeInfo.ROOT_NODE_ID, i);
    }

    public final AccessibilityServiceInfo getServiceInfo() {
        notifyKnoxZtInternalService(14);
        AccessibilityInteractionClient.getInstance(this);
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection == null) {
            return null;
        }
        try {
            return connection.getServiceInfo();
        } catch (RemoteException e) {
            Log.w("AccessibilityService", "Error while getting AccessibilityServiceInfo", e);
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public final void setServiceInfo(AccessibilityServiceInfo accessibilityServiceInfo) {
        notifyKnoxZtInternalService(34);
        this.mInfo = accessibilityServiceInfo;
        updateInputMethod(accessibilityServiceInfo);
        this.mMotionEventSources = accessibilityServiceInfo.getMotionEventSources();
        sendServiceInfo();
    }

    private void sendServiceInfo() {
        AccessibilityInteractionClient.getInstance(this);
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        AccessibilityServiceInfo accessibilityServiceInfo = this.mInfo;
        if (accessibilityServiceInfo == null || connection == null) {
            return;
        }
        try {
            connection.setServiceInfo(accessibilityServiceInfo);
            this.mInfo = null;
            AccessibilityInteractionClient.getInstance(this).clearCache(this.mConnectionId);
        } catch (RemoteException e) {
            Log.w("AccessibilityService", "Error while setting AccessibilityServiceInfo", e);
            e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Object getSystemService(String str) {
        if (getBaseContext() == null) {
            throw new IllegalStateException("System services not available to Activities before onCreate()");
        }
        if (Context.WINDOW_SERVICE.equals(str)) {
            if (this.mWindowManager == null) {
                WindowManager windowManager = (WindowManager) getBaseContext().getSystemService(str);
                this.mWindowManager = windowManager;
                ((WindowManagerImpl) windowManager).setDefaultToken(this.mWindowToken);
            }
            return this.mWindowManager;
        }
        return super.getSystemService(str);
    }

    public void takeScreenshot(int i, final Executor executor, final TakeScreenshotCallback takeScreenshotCallback) {
        Preconditions.checkNotNull(executor, "executor cannot be null");
        Preconditions.checkNotNull(takeScreenshotCallback, "callback cannot be null");
        notifyKnoxZtInternalService(36);
        AccessibilityInteractionClient.getInstance(this);
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection == null) {
            sendScreenshotFailure(1, executor, takeScreenshotCallback);
            return;
        }
        try {
            connection.takeScreenshot(i, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: android.accessibilityservice.AccessibilityService$$ExternalSyntheticLambda2
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(Bundle bundle) {
                    AccessibilityService.this.lambda$takeScreenshot$0(executor, takeScreenshotCallback, bundle);
                }
            }));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$takeScreenshot$0(Executor executor, TakeScreenshotCallback takeScreenshotCallback, Bundle bundle) {
        int i = bundle.getInt(KEY_ACCESSIBILITY_SCREENSHOT_STATUS);
        if (i != 0) {
            sendScreenshotFailure(i, executor, takeScreenshotCallback);
        } else {
            sendScreenshotSuccess(new ScreenshotResult((HardwareBuffer) bundle.getParcelable(KEY_ACCESSIBILITY_SCREENSHOT_HARDWAREBUFFER, HardwareBuffer.class), ((ParcelableColorSpace) bundle.getParcelable(KEY_ACCESSIBILITY_SCREENSHOT_COLORSPACE, ParcelableColorSpace.class)).getColorSpace(), bundle.getLong(KEY_ACCESSIBILITY_SCREENSHOT_TIMESTAMP)), executor, takeScreenshotCallback);
        }
    }

    public void takeScreenshotOfWindow(int i, Executor executor, TakeScreenshotCallback takeScreenshotCallback) {
        notifyKnoxZtInternalService(37);
        AccessibilityInteractionClient.getInstance(this).takeScreenshotOfWindow(this.mConnectionId, i, executor, takeScreenshotCallback);
    }

    public void setAccessibilityFocusAppearance(int i, int i2) {
        notifyKnoxZtInternalService(30);
        AccessibilityInteractionClient.getInstance(this);
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                connection.setFocusAppearance(i, i2);
            } catch (RemoteException e) {
                Log.w("AccessibilityService", "Error while setting the strokeWidth and color of the accessibility focus rectangle", e);
                e.rethrowFromSystemServer();
            }
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new IAccessibilityServiceClientWrapper(this, getMainExecutor(), new Callbacks() { // from class: android.accessibilityservice.AccessibilityService.2
            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onServiceConnected() {
                AccessibilityService.this.dispatchServiceConnected();
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onInterrupt() {
                AccessibilityService.this.onInterrupt();
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
                if (accessibilityEvent.getEventType() == 16) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("eventType", accessibilityEvent.getEventType());
                    AccessibilityService.this.notifyKnoxZtInternalService(23, bundle);
                }
                AccessibilityService.this.onAccessibilityEvent(accessibilityEvent);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void init(int i, IBinder iBinder) {
                AccessibilityService.this.mConnectionId = i;
                AccessibilityService.this.mWindowToken = iBinder;
                if (AccessibilityService.this.mWindowManager != null) {
                    ((WindowManagerImpl) AccessibilityService.this.mWindowManager).setDefaultToken(AccessibilityService.this.mWindowToken);
                }
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public boolean onGesture(AccessibilityGestureEvent accessibilityGestureEvent) {
                return AccessibilityService.this.onGesture(accessibilityGestureEvent);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public boolean onKeyEvent(KeyEvent keyEvent) {
                return AccessibilityService.this.onKeyEvent(keyEvent);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onMagnificationChanged(int i, Region region, MagnificationConfig magnificationConfig) {
                AccessibilityService.this.onMagnificationChanged(i, region, magnificationConfig);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onMotionEvent(MotionEvent motionEvent) {
                AccessibilityService.this.sendMotionEventToCallback(motionEvent);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onTouchStateChanged(int i, int i2) {
                AccessibilityService.this.onTouchStateChanged(i, i2);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onSoftKeyboardShowModeChanged(int i) {
                AccessibilityService.this.onSoftKeyboardShowModeChanged(i);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onPerformGestureResult(int i, boolean z) {
                AccessibilityService.this.onPerformGestureResult(i, z);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onFingerprintCapturingGesturesChanged(boolean z) {
                AccessibilityService.this.onFingerprintCapturingGesturesChanged(z);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onFingerprintGesture(int i) {
                AccessibilityService.this.onFingerprintGesture(i);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onAccessibilityButtonClicked(int i) {
                AccessibilityService.this.onAccessibilityButtonClicked(i);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onAccessibilityButtonAvailabilityChanged(boolean z) {
                AccessibilityService.this.onAccessibilityButtonAvailabilityChanged(z);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onSystemActionsChanged() {
                AccessibilityService.this.onSystemActionsChanged();
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void createImeSession(IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) {
                if (AccessibilityService.this.mInputMethod != null) {
                    AccessibilityService.this.mInputMethod.createImeSession(iAccessibilityInputMethodSessionCallback);
                }
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void startInput(RemoteAccessibilityInputConnection remoteAccessibilityInputConnection, EditorInfo editorInfo, boolean z) {
                if (AccessibilityService.this.mInputMethod != null) {
                    if (z) {
                        AccessibilityService.this.mInputMethod.restartInput(remoteAccessibilityInputConnection, editorInfo);
                    } else {
                        AccessibilityService.this.mInputMethod.startInput(remoteAccessibilityInputConnection, editorInfo);
                    }
                }
            }
        });
    }

    public static class IAccessibilityServiceClientWrapper extends IAccessibilityServiceClient.Stub {
        private final Callbacks mCallback;
        CancellationGroup mCancellationGroup;
        private int mConnectionId;
        private final Context mContext;
        private final Executor mExecutor;

        public IAccessibilityServiceClientWrapper(Context context, Executor executor, Callbacks callbacks) {
            this.mConnectionId = -1;
            this.mCancellationGroup = null;
            this.mCallback = callbacks;
            this.mContext = context;
            this.mExecutor = executor;
        }

        public IAccessibilityServiceClientWrapper(Context context, Looper looper, Callbacks callbacks) {
            this(context, new HandlerExecutor(new Handler(looper)), callbacks);
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void init(final IAccessibilityServiceConnection iAccessibilityServiceConnection, final int i, final IBinder iBinder) {
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$init$0(i, iAccessibilityServiceConnection, iBinder);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$init$0(int i, IAccessibilityServiceConnection iAccessibilityServiceConnection, IBinder iBinder) {
            this.mConnectionId = i;
            if (iAccessibilityServiceConnection != null) {
                AccessibilityInteractionClient.getInstance(this.mContext);
                AccessibilityInteractionClient.addConnection(this.mConnectionId, iAccessibilityServiceConnection, true);
                Context context = this.mContext;
                if (context != null) {
                    try {
                        iAccessibilityServiceConnection.setAttributionTag(context.getAttributionTag());
                    } catch (RemoteException e) {
                        Log.w("AccessibilityService", "Error while setting attributionTag", e);
                        e.rethrowFromSystemServer();
                    }
                }
                this.mCallback.init(this.mConnectionId, iBinder);
                this.mCallback.onServiceConnected();
                return;
            }
            AccessibilityInteractionClient.getInstance(this.mContext).clearCache(this.mConnectionId);
            AccessibilityInteractionClient.getInstance(this.mContext);
            AccessibilityInteractionClient.removeConnection(this.mConnectionId);
            this.mConnectionId = -1;
            this.mCallback.init(-1, null);
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onInterrupt() {
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onInterrupt$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInterrupt$1() {
            if (this.mConnectionId != -1) {
                this.mCallback.onInterrupt();
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onAccessibilityEvent(final AccessibilityEvent accessibilityEvent, final boolean z) {
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onAccessibilityEvent$2(accessibilityEvent, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAccessibilityEvent$2(AccessibilityEvent accessibilityEvent, boolean z) {
            if (accessibilityEvent != null) {
                AccessibilityInteractionClient.getInstance(this.mContext).onAccessibilityEvent(accessibilityEvent, this.mConnectionId);
                if (!z || this.mConnectionId == -1) {
                    return;
                }
                this.mCallback.onAccessibilityEvent(accessibilityEvent);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onGesture(final AccessibilityGestureEvent accessibilityGestureEvent) {
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onGesture$3(accessibilityGestureEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGesture$3(AccessibilityGestureEvent accessibilityGestureEvent) {
            if (this.mConnectionId != -1) {
                this.mCallback.onGesture(accessibilityGestureEvent);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void clearAccessibilityCache() {
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$clearAccessibilityCache$4();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$clearAccessibilityCache$4() {
            AccessibilityInteractionClient.getInstance(this.mContext).clearCache(this.mConnectionId);
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onKeyEvent(final KeyEvent keyEvent, final int i) {
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onKeyEvent$5(keyEvent, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onKeyEvent$5(KeyEvent keyEvent, int i) {
            try {
                AccessibilityInteractionClient.getInstance(this.mContext);
                IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
                if (connection != null) {
                    try {
                        connection.setOnKeyEventResult(this.mCallback.onKeyEvent(keyEvent), i);
                    } catch (RemoteException unused) {
                    }
                }
            } finally {
                try {
                    keyEvent.recycle();
                } catch (IllegalStateException unused2) {
                }
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onMagnificationChanged(final int i, final Region region, final MagnificationConfig magnificationConfig) {
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onMagnificationChanged$6(i, region, magnificationConfig);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMagnificationChanged$6(int i, Region region, MagnificationConfig magnificationConfig) {
            if (this.mConnectionId != -1) {
                this.mCallback.onMagnificationChanged(i, region, magnificationConfig);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onSoftKeyboardShowModeChanged(final int i) {
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onSoftKeyboardShowModeChanged$7(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSoftKeyboardShowModeChanged$7(int i) {
            if (this.mConnectionId != -1) {
                this.mCallback.onSoftKeyboardShowModeChanged(i);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onPerformGestureResult(final int i, final boolean z) {
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onPerformGestureResult$8(i, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPerformGestureResult$8(int i, boolean z) {
            if (this.mConnectionId != -1) {
                this.mCallback.onPerformGestureResult(i, z);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onFingerprintCapturingGesturesChanged(final boolean z) {
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onFingerprintCapturingGesturesChanged$9(z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFingerprintCapturingGesturesChanged$9(boolean z) {
            if (this.mConnectionId != -1) {
                this.mCallback.onFingerprintCapturingGesturesChanged(z);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onFingerprintGesture(final int i) {
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onFingerprintGesture$10(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFingerprintGesture$10(int i) {
            if (this.mConnectionId != -1) {
                this.mCallback.onFingerprintGesture(i);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onAccessibilityButtonClicked(final int i) {
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onAccessibilityButtonClicked$11(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAccessibilityButtonClicked$11(int i) {
            if (this.mConnectionId != -1) {
                this.mCallback.onAccessibilityButtonClicked(i);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onAccessibilityButtonAvailabilityChanged(final boolean z) {
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onAccessibilityButtonAvailabilityChanged$12(z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAccessibilityButtonAvailabilityChanged$12(boolean z) {
            if (this.mConnectionId != -1) {
                this.mCallback.onAccessibilityButtonAvailabilityChanged(z);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onSystemActionsChanged() {
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onSystemActionsChanged$13();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSystemActionsChanged$13() {
            if (this.mConnectionId != -1) {
                this.mCallback.onSystemActionsChanged();
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void createImeSession(final IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) {
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$createImeSession$14(iAccessibilityInputMethodSessionCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$createImeSession$14(IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) {
            if (this.mConnectionId != -1) {
                this.mCallback.createImeSession(iAccessibilityInputMethodSessionCallback);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void setImeSessionEnabled(IAccessibilityInputMethodSession iAccessibilityInputMethodSession, final boolean z) {
            try {
                final AccessibilityInputMethodSession session = ((AccessibilityInputMethodSessionWrapper) iAccessibilityInputMethodSession).getSession();
                if (session != null) {
                    this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda11
                        @Override // java.lang.Runnable
                        public final void run() {
                            AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$setImeSessionEnabled$15(session, z);
                        }
                    });
                    return;
                }
                Log.w("AccessibilityService", "Session is already finished: " + iAccessibilityInputMethodSession);
            } catch (ClassCastException e) {
                Log.w("AccessibilityService", "Incoming session not of correct type: " + iAccessibilityInputMethodSession, e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setImeSessionEnabled$15(AccessibilityInputMethodSession accessibilityInputMethodSession, boolean z) {
            if (this.mConnectionId != -1) {
                accessibilityInputMethodSession.setEnabled(z);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void bindInput() {
            if (this.mCancellationGroup != null) {
                Log.e("AccessibilityService", "bindInput must be paired with unbindInput.");
            }
            this.mCancellationGroup = new CancellationGroup();
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void unbindInput() {
            CancellationGroup cancellationGroup = this.mCancellationGroup;
            if (cancellationGroup != null) {
                cancellationGroup.cancelAll();
                this.mCancellationGroup = null;
            } else {
                Log.e("AccessibilityService", "unbindInput must be paired with bindInput.");
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void startInput(final IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, final EditorInfo editorInfo, final boolean z) {
            if (this.mCancellationGroup == null) {
                Log.e("AccessibilityService", "startInput must be called after bindInput.");
                this.mCancellationGroup = new CancellationGroup();
            }
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$startInput$16(iRemoteAccessibilityInputConnection, editorInfo, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startInput$16(IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, EditorInfo editorInfo, boolean z) {
            if (this.mConnectionId != -1) {
                RemoteAccessibilityInputConnection remoteAccessibilityInputConnection = iRemoteAccessibilityInputConnection == null ? null : new RemoteAccessibilityInputConnection(iRemoteAccessibilityInputConnection, this.mCancellationGroup);
                editorInfo.makeCompatible(this.mContext.getApplicationInfo().targetSdkVersion);
                this.mCallback.startInput(remoteAccessibilityInputConnection, editorInfo, z);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onMotionEvent(final MotionEvent motionEvent) {
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onMotionEvent$17(motionEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMotionEvent$17(MotionEvent motionEvent) {
            this.mCallback.onMotionEvent(motionEvent);
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onTouchStateChanged(final int i, final int i2) {
            this.mExecutor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onTouchStateChanged$18(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTouchStateChanged$18(int i, int i2) {
            this.mCallback.onTouchStateChanged(i, i2);
        }
    }

    private static class GestureResultCallbackInfo {
        GestureResultCallback callback;
        GestureDescription gestureDescription;
        Handler handler;

        GestureResultCallbackInfo(GestureDescription gestureDescription, GestureResultCallback gestureResultCallback, Handler handler) {
            this.gestureDescription = gestureDescription;
            this.callback = gestureResultCallback;
            this.handler = handler;
        }
    }

    private void sendScreenshotSuccess(final ScreenshotResult screenshotResult, Executor executor, final TakeScreenshotCallback takeScreenshotCallback) {
        executor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AccessibilityService.TakeScreenshotCallback.this.onSuccess(screenshotResult);
            }
        });
    }

    private void sendScreenshotFailure(final int i, Executor executor, final TakeScreenshotCallback takeScreenshotCallback) {
        executor.execute(new Runnable() { // from class: android.accessibilityservice.AccessibilityService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AccessibilityService.TakeScreenshotCallback.this.onFailure(i);
            }
        });
    }

    public static final class ScreenshotResult {
        private final ColorSpace mColorSpace;
        private final HardwareBuffer mHardwareBuffer;
        private final long mTimestamp;

        public ScreenshotResult(HardwareBuffer hardwareBuffer, ColorSpace colorSpace, long j) {
            Preconditions.checkNotNull(hardwareBuffer, "hardwareBuffer cannot be null");
            Preconditions.checkNotNull(colorSpace, "colorSpace cannot be null");
            this.mHardwareBuffer = hardwareBuffer;
            this.mColorSpace = colorSpace;
            this.mTimestamp = j;
        }

        public ColorSpace getColorSpace() {
            return this.mColorSpace;
        }

        public HardwareBuffer getHardwareBuffer() {
            return this.mHardwareBuffer;
        }

        public long getTimestamp() {
            return this.mTimestamp;
        }
    }

    public void setGestureDetectionPassthroughRegion(int i, Region region) {
        notifyKnoxZtInternalService(33);
        Preconditions.checkNotNull(region, "region cannot be null");
        AccessibilityInteractionClient.getInstance(this);
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                connection.setGestureDetectionPassthroughRegion(i, region);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setTouchExplorationPassthroughRegion(int i, Region region) {
        Preconditions.checkNotNull(region, "region cannot be null");
        notifyKnoxZtInternalService(35);
        AccessibilityInteractionClient.getInstance(this);
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                connection.setTouchExplorationPassthroughRegion(i, region);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setAnimationScale(float f) {
        notifyKnoxZtInternalService(31);
        AccessibilityInteractionClient.getInstance(this);
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                connection.setAnimationScale(f);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class AccessibilityContext extends ContextWrapper {
        private final int mConnectionId;

        private AccessibilityContext(Context context, int i) {
            super(context);
            this.mConnectionId = i;
            setDefaultTokenInternal(this, getDisplayId());
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public Context createDisplayContext(Display display) {
            return new AccessibilityContext(super.createDisplayContext(display), this.mConnectionId);
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public Context createWindowContext(int i, Bundle bundle) {
            Context createWindowContext = super.createWindowContext(i, bundle);
            return i != 2032 ? createWindowContext : new AccessibilityContext(createWindowContext, this.mConnectionId);
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public Context createWindowContext(Display display, int i, Bundle bundle) {
            Context createWindowContext = super.createWindowContext(display, i, bundle);
            return i != 2032 ? createWindowContext : new AccessibilityContext(createWindowContext, this.mConnectionId);
        }

        private void setDefaultTokenInternal(Context context, int i) {
            IBinder iBinder;
            WindowManagerImpl windowManagerImpl = (WindowManagerImpl) context.getSystemService(Context.WINDOW_SERVICE);
            IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
            if (connection != null) {
                try {
                    iBinder = connection.getOverlayWindowToken(i);
                } catch (RemoteException e) {
                    Log.w("AccessibilityService", "Failed to get window token", e);
                    e.rethrowFromSystemServer();
                    iBinder = null;
                }
                windowManagerImpl.setDefaultToken(iBinder);
            }
        }
    }

    public final TouchInteractionController getTouchInteractionController(int i) {
        TouchInteractionController touchInteractionController;
        notifyKnoxZtInternalService(18);
        synchronized (this.mLock) {
            touchInteractionController = this.mTouchInteractionControllers.get(i);
            if (touchInteractionController == null) {
                touchInteractionController = new TouchInteractionController(this, this.mLock, i);
                this.mTouchInteractionControllers.put(i, touchInteractionController);
            }
        }
        return touchInteractionController;
    }

    void sendMotionEventToCallback(MotionEvent motionEvent) {
        boolean z;
        TouchInteractionController touchInteractionController;
        if (motionEvent.isFromSource(4098)) {
            synchronized (this.mLock) {
                touchInteractionController = this.mTouchInteractionControllers.get(motionEvent.getDisplayId());
            }
            if (touchInteractionController != null) {
                touchInteractionController.onMotionEvent(motionEvent);
                z = true;
                if ((motionEvent.getSource() & (-256) & this.mMotionEventSources) != 0 || z) {
                }
                notifyKnoxZtInternalService(27);
                onMotionEvent(motionEvent);
                return;
            }
        }
        z = false;
        if ((motionEvent.getSource() & (-256) & this.mMotionEventSources) != 0) {
        }
    }

    void onTouchStateChanged(int i, int i2) {
        TouchInteractionController touchInteractionController;
        synchronized (this.mLock) {
            touchInteractionController = this.mTouchInteractionControllers.get(i);
        }
        if (touchInteractionController != null) {
            touchInteractionController.onStateChanged(i2);
        }
    }

    public void attachAccessibilityOverlayToDisplay(int i, SurfaceControl surfaceControl) {
        notifyKnoxZtInternalService(0);
        Preconditions.checkNotNull(surfaceControl, "SurfaceControl cannot be null");
        AccessibilityInteractionClient.getInstance(this).attachAccessibilityOverlayToDisplay(this.mConnectionId, i, surfaceControl, null, null);
    }

    public final void attachAccessibilityOverlayToDisplay(int i, SurfaceControl surfaceControl, Executor executor, IntConsumer intConsumer) {
        Preconditions.checkNotNull(surfaceControl, "SurfaceControl cannot be null");
        AccessibilityInteractionClient.getInstance(this).attachAccessibilityOverlayToDisplay(this.mConnectionId, i, surfaceControl, executor, intConsumer);
    }

    public void attachAccessibilityOverlayToWindow(int i, SurfaceControl surfaceControl) {
        notifyKnoxZtInternalService(1);
        Preconditions.checkNotNull(surfaceControl, "SurfaceControl cannot be null");
        AccessibilityInteractionClient.getInstance(this).attachAccessibilityOverlayToWindow(this.mConnectionId, i, surfaceControl, null, null);
    }

    private void notifyKnoxZtInternalService(int i) {
        notifyKnoxZtInternalService(i, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyKnoxZtInternalService(int i, Bundle bundle) {
        try {
            IKnoxZtInternalService asInterface = IKnoxZtInternalService.Stub.asInterface(ServiceManager.getService(KnoxZtInternalConst.SERVICE_KNOX_ZT_INTERNAL));
            if (asInterface != null) {
                asInterface.notifyFrameworkEvent(0, i, bundle);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public final void attachAccessibilityOverlayToWindow(int i, SurfaceControl surfaceControl, Executor executor, IntConsumer intConsumer) {
        Preconditions.checkNotNull(surfaceControl, "SurfaceControl cannot be null");
        AccessibilityInteractionClient.getInstance(this).attachAccessibilityOverlayToWindow(this.mConnectionId, i, surfaceControl, executor, intConsumer);
    }

    public final BrailleDisplayController getBrailleDisplayController() {
        BrailleDisplayController brailleDisplayController;
        BrailleDisplayController.checkApiFlagIsEnabled();
        synchronized (this.mLock) {
            if (this.mBrailleDisplayController == null) {
                this.mBrailleDisplayController = new BrailleDisplayControllerImpl(this, this.mLock);
            }
            brailleDisplayController = this.mBrailleDisplayController;
        }
        return brailleDisplayController;
    }
}
