package android.app;

import android.accessibilityservice.AccessibilityGestureEvent;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.accessibilityservice.IAccessibilityServiceConnection;
import android.accessibilityservice.MagnificationConfig;
import android.app.UiAutomation;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.HardwareBuffer;
import android.hardware.display.DisplayManagerGlobal;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowAnimationFrameStats;
import android.view.WindowContentFrameStats;
import android.view.accessibility.AccessibilityCache;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityInteractionClient;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import android.view.inputmethod.EditorInfo;
import android.window.ScreenCapture;
import com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback;
import com.android.internal.inputmethod.RemoteAccessibilityInputConnection;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import libcore.io.IoUtils;

/* loaded from: classes.dex */
public final class UiAutomation {
    public static final Set<String> ALL_PERMISSIONS = Set.of("_ALL_PERMISSIONS_");
    private static final int CONNECTION_ID_UNDEFINED = -1;
    private static final long CONNECT_TIMEOUT_MILLIS = 5000;
    private static final boolean DEBUG = false;
    public static final int FLAG_DONT_SUPPRESS_ACCESSIBILITY_SERVICES = 1;
    public static final int FLAG_DONT_USE_ACCESSIBILITY = 2;
    public static final int FLAG_NOT_ACCESSIBILITY_TOOL = 4;
    private static final String LOG_TAG = "UiAutomation";
    public static final int ROTATION_FREEZE_0 = 0;
    public static final int ROTATION_FREEZE_180 = 2;
    public static final int ROTATION_FREEZE_270 = 3;
    public static final int ROTATION_FREEZE_90 = 1;
    public static final int ROTATION_FREEZE_CURRENT = -1;
    public static final int ROTATION_UNFREEZE = -2;
    private static final boolean VERBOSE = false;
    private IAccessibilityServiceClient mClient;
    private int mConnectionId;
    private int mConnectionState;
    private int mCurrentEventWatchersCount;
    private final int mDisplayId;
    private final ArrayList<AccessibilityEvent> mEventQueue;
    private int mFlags;
    private int mGenerationId;
    private boolean mIsDestroyed;
    private long mLastEventTimeMillis;
    private final Handler mLocalCallbackHandler;
    private final Object mLock;
    private OnAccessibilityEventListener mOnAccessibilityEventListener;
    private HandlerThread mRemoteCallbackThread;
    private final IUiAutomationConnection mUiAutomationConnection;

    public interface AccessibilityEventFilter {
        boolean accept(AccessibilityEvent accessibilityEvent);
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface ConnectionState {
        public static final int CONNECTED = 2;
        public static final int CONNECTING = 1;
        public static final int DISCONNECTED = 0;
        public static final int FAILED = 3;
    }

    public interface OnAccessibilityEventListener {
        void onAccessibilityEvent(AccessibilityEvent accessibilityEvent);
    }

    public UiAutomation(Context context, IUiAutomationConnection iUiAutomationConnection) {
        this(getDisplayId(context), context.getMainLooper(), iUiAutomationConnection);
    }

    @Deprecated
    public UiAutomation(Looper looper, IUiAutomationConnection iUiAutomationConnection) {
        this(0, looper, iUiAutomationConnection);
        Log.w(LOG_TAG, "Created with deprecatead constructor, assumes DEFAULT_DISPLAY");
    }

    private UiAutomation(int i, Looper looper, IUiAutomationConnection iUiAutomationConnection) {
        this.mLock = new Object();
        this.mEventQueue = new ArrayList<>();
        this.mConnectionId = -1;
        this.mCurrentEventWatchersCount = 0;
        this.mConnectionState = 0;
        this.mGenerationId = 0;
        Preconditions.checkArgument(looper != null, "Looper cannot be null!");
        Preconditions.checkArgument(iUiAutomationConnection != null, "Connection cannot be null!");
        this.mLocalCallbackHandler = new Handler(looper);
        this.mUiAutomationConnection = iUiAutomationConnection;
        this.mDisplayId = i;
        Log.i(LOG_TAG, "Initialized for user " + Process.myUserHandle().getIdentifier() + " on display " + i);
    }

    public void connect() {
        try {
            connectWithTimeout(0, 5000L);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void connect(int i) {
        try {
            connectWithTimeout(i, 5000L);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void connectWithTimeout(int i, long j) throws TimeoutException {
        synchronized (this.mLock) {
            throwIfConnectedLocked();
            if (this.mConnectionState == 1) {
                return;
            }
            this.mConnectionState = 1;
            HandlerThread handlerThread = new HandlerThread(LOG_TAG);
            this.mRemoteCallbackThread = handlerThread;
            handlerThread.start();
            Looper looper = this.mRemoteCallbackThread.getLooper();
            int i2 = this.mGenerationId + 1;
            this.mGenerationId = i2;
            IAccessibilityServiceClientImpl iAccessibilityServiceClientImpl = new IAccessibilityServiceClientImpl(this, looper, i2);
            this.mClient = iAccessibilityServiceClientImpl;
            try {
                this.mUiAutomationConnection.connect(iAccessibilityServiceClientImpl, i);
                this.mFlags = i;
                if (!useAccessibility()) {
                    this.mConnectionState = 0;
                    return;
                }
                synchronized (this.mLock) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    while (this.mConnectionState != 2) {
                        long uptimeMillis2 = j - (SystemClock.uptimeMillis() - uptimeMillis);
                        if (uptimeMillis2 <= 0) {
                            this.mConnectionState = 3;
                            throw new TimeoutException("Timeout while connecting " + this);
                        }
                        try {
                            this.mLock.wait(uptimeMillis2);
                        } catch (InterruptedException unused) {
                        }
                    }
                }
            } catch (RemoteException e) {
                throw new RuntimeException("Error while connecting " + this, e);
            }
        }
    }

    public int getFlags() {
        return this.mFlags;
    }

    public void disconnect() {
        synchronized (this.mLock) {
            if (this.mConnectionState == 1) {
                throw new IllegalStateException("Cannot call disconnect() while connecting " + this);
            }
            if (useAccessibility() && this.mConnectionState == 0) {
                return;
            }
            this.mConnectionState = 0;
            this.mConnectionId = -1;
            this.mGenerationId++;
            try {
                try {
                    this.mUiAutomationConnection.disconnect();
                } catch (RemoteException e) {
                    throw new RuntimeException("Error while disconnecting " + this, e);
                }
            } finally {
                HandlerThread handlerThread = this.mRemoteCallbackThread;
                if (handlerThread != null) {
                    handlerThread.quit();
                    this.mRemoteCallbackThread = null;
                }
            }
        }
    }

    public int getConnectionId() {
        int i;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            i = this.mConnectionId;
        }
        return i;
    }

    public boolean isDestroyed() {
        return this.mIsDestroyed;
    }

    public void setOnAccessibilityEventListener(OnAccessibilityEventListener onAccessibilityEventListener) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            this.mOnAccessibilityEventListener = onAccessibilityEventListener;
        }
    }

    public void destroy() {
        disconnect();
        this.mIsDestroyed = true;
    }

    public boolean clearCache() {
        int i;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            i = this.mConnectionId;
        }
        AccessibilityCache cache = AccessibilityInteractionClient.getCache(i);
        if (cache == null) {
            return false;
        }
        cache.clear();
        return true;
    }

    public boolean isNodeInCache(AccessibilityNodeInfo accessibilityNodeInfo) {
        int i;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            i = this.mConnectionId;
        }
        AccessibilityCache cache = AccessibilityInteractionClient.getCache(i);
        if (cache == null) {
            return false;
        }
        return cache.isNodeInCache(accessibilityNodeInfo);
    }

    public AccessibilityCache getCache() {
        int i;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            i = this.mConnectionId;
        }
        return AccessibilityInteractionClient.getCache(i);
    }

    public void adoptShellPermissionIdentity() {
        try {
            this.mUiAutomationConnection.adoptShellPermissionIdentity(Process.myUid(), null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void adoptShellPermissionIdentity(String... strArr) {
        try {
            this.mUiAutomationConnection.adoptShellPermissionIdentity(Process.myUid(), strArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void dropShellPermissionIdentity() {
        try {
            this.mUiAutomationConnection.dropShellPermissionIdentity();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Set<String> getAdoptedShellPermissions() {
        try {
            List<String> adoptedShellPermissions = this.mUiAutomationConnection.getAdoptedShellPermissions();
            return adoptedShellPermissions == null ? ALL_PERMISSIONS : new ArraySet(adoptedShellPermissions);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addOverridePermissionState(int i, String str, int i2) {
        try {
            this.mUiAutomationConnection.addOverridePermissionState(i, str, i2);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void removeOverridePermissionState(int i, String str) {
        try {
            this.mUiAutomationConnection.removeOverridePermissionState(i, str);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void clearOverridePermissionStates(int i) {
        try {
            this.mUiAutomationConnection.clearOverridePermissionStates(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void clearAllOverridePermissionStates() {
        try {
            this.mUiAutomationConnection.clearAllOverridePermissionStates();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public final boolean performGlobalAction(int i) {
        IAccessibilityServiceConnection connection;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            AccessibilityInteractionClient.getInstance();
            connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        }
        if (connection == null) {
            return false;
        }
        try {
            return connection.performGlobalAction(i);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, "Error while calling performGlobalAction", e);
            return false;
        }
    }

    public AccessibilityNodeInfo findFocus(int i) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        return AccessibilityInteractionClient.getInstance().findFocus(this.mConnectionId, -2, AccessibilityNodeInfo.ROOT_NODE_ID, i);
    }

    public final AccessibilityServiceInfo getServiceInfo() {
        IAccessibilityServiceConnection connection;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            AccessibilityInteractionClient.getInstance();
            connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        }
        if (connection == null) {
            return null;
        }
        try {
            return connection.getServiceInfo();
        } catch (RemoteException e) {
            Log.w(LOG_TAG, "Error while getting AccessibilityServiceInfo", e);
            return null;
        }
    }

    public final void setServiceInfo(AccessibilityServiceInfo accessibilityServiceInfo) {
        IAccessibilityServiceConnection connection;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            AccessibilityInteractionClient.getInstance().clearCache(this.mConnectionId);
            AccessibilityInteractionClient.getInstance();
            connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        }
        if (connection != null) {
            try {
                connection.setServiceInfo(accessibilityServiceInfo);
            } catch (RemoteException e) {
                Log.w(LOG_TAG, "Error while setting AccessibilityServiceInfo", e);
            }
        }
    }

    public List<AccessibilityWindowInfo> getWindows() {
        int i;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            i = this.mConnectionId;
        }
        return AccessibilityInteractionClient.getInstance().getWindowsOnDisplay(i, this.mDisplayId);
    }

    public SparseArray<List<AccessibilityWindowInfo>> getWindowsOnAllDisplays() {
        int i;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            i = this.mConnectionId;
        }
        return AccessibilityInteractionClient.getInstance().getWindowsOnAllDisplays(i);
    }

    public AccessibilityNodeInfo getRootInActiveWindow() {
        return getRootInActiveWindow(4);
    }

    public AccessibilityNodeInfo getRootInActiveWindow(int i) {
        int i2;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            i2 = this.mConnectionId;
        }
        return AccessibilityInteractionClient.getInstance().getRootInActiveWindow(i2, i);
    }

    public boolean injectInputEvent(InputEvent inputEvent, boolean z) {
        return injectInputEvent(inputEvent, z, true);
    }

    @Deprecated
    public boolean injectInputEvent(InputEvent inputEvent, boolean z, boolean z2) {
        try {
            return this.mUiAutomationConnection.injectInputEvent(inputEvent, z, z2);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error while injecting input event!", e);
            return false;
        }
    }

    @Deprecated
    public void injectInputEventToInputFilter(InputEvent inputEvent) {
        try {
            this.mUiAutomationConnection.injectInputEventToInputFilter(inputEvent);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error while injecting input event to input filter", e);
        }
    }

    public void setAnimationScale(float f) {
        AccessibilityInteractionClient.getInstance();
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                connection.setAnimationScale(f);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void syncInputTransactions() {
        try {
            this.mUiAutomationConnection.syncInputTransactions(true);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error while syncing input transactions!", e);
        }
    }

    public void syncInputTransactions(boolean z) {
        try {
            this.mUiAutomationConnection.syncInputTransactions(z);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error while syncing input transactions!", e);
        }
    }

    public boolean setRotation(int i) {
        if (i == -2 || i == -1 || i == 0 || i == 1 || i == 2 || i == 3) {
            try {
                this.mUiAutomationConnection.setRotation(i);
                return true;
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error while setting rotation!", e);
                return false;
            }
        }
        throw new IllegalArgumentException("Invalid rotation.");
    }

    public AccessibilityEvent executeAndWaitForEvent(Runnable runnable, AccessibilityEventFilter accessibilityEventFilter, long j) throws TimeoutException {
        int i;
        long uptimeMillis;
        int size;
        int size2;
        int i2;
        AccessibilityEvent accessibilityEvent;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            i = this.mCurrentEventWatchersCount + 1;
            this.mCurrentEventWatchersCount = i;
            uptimeMillis = SystemClock.uptimeMillis();
            size = this.mEventQueue.size();
        }
        try {
            runnable.run();
            synchronized (this.mLock) {
                if (i != this.mCurrentEventWatchersCount) {
                    throw new IllegalStateException("Unexpected event watchers count, expected: " + i + ", actual: " + this.mCurrentEventWatchersCount);
                }
            }
            long uptimeMillis2 = SystemClock.uptimeMillis();
            ArrayList arrayList = new ArrayList();
            long j2 = 0;
            int i3 = 0;
            while (j > j2) {
                synchronized (this.mLock) {
                    size2 = this.mEventQueue.size();
                    if (size < size2) {
                        i2 = size + 1;
                        accessibilityEvent = this.mEventQueue.get(size);
                    } else {
                        try {
                            this.mLock.wait(j - j2);
                        } catch (InterruptedException unused) {
                        }
                        i2 = size;
                        accessibilityEvent = null;
                    }
                }
                long uptimeMillis3 = SystemClock.uptimeMillis() - uptimeMillis2;
                if (accessibilityEvent != null && accessibilityEvent.getEventTime() >= uptimeMillis) {
                    if (!accessibilityEventFilter.accept(accessibilityEvent)) {
                        arrayList.add(accessibilityEvent);
                        size = i2;
                        i3 = size2;
                        j2 = uptimeMillis3;
                    } else {
                        synchronized (this.mLock) {
                            int i4 = this.mCurrentEventWatchersCount - 1;
                            this.mCurrentEventWatchersCount = i4;
                            if (i4 == 0) {
                                this.mEventQueue.clear();
                            }
                            this.mLock.notifyAll();
                        }
                        return accessibilityEvent;
                    }
                }
                size = i2;
                i3 = size2;
                j2 = uptimeMillis3;
            }
            if (size < i3) {
                Log.w(LOG_TAG, "Timed out before reading all events from the queue");
            }
            throw new TimeoutException("Expected event not received before timeout, events: " + arrayList);
        } catch (Throwable th) {
            synchronized (this.mLock) {
                int i5 = this.mCurrentEventWatchersCount - 1;
                this.mCurrentEventWatchersCount = i5;
                if (i5 == 0) {
                    this.mEventQueue.clear();
                }
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public void waitForIdle(long j, long j2) throws TimeoutException {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            long uptimeMillis = SystemClock.uptimeMillis();
            if (this.mLastEventTimeMillis <= 0) {
                this.mLastEventTimeMillis = uptimeMillis;
            }
            while (true) {
                long uptimeMillis2 = SystemClock.uptimeMillis();
                if (j2 - (uptimeMillis2 - uptimeMillis) <= 0) {
                    throw new TimeoutException("No idle state with idle timeout: " + j + " within global timeout: " + j2);
                }
                long j3 = j - (uptimeMillis2 - this.mLastEventTimeMillis);
                if (j3 > 0) {
                    try {
                        this.mLock.wait(j3);
                    } catch (InterruptedException unused) {
                    }
                }
            }
        }
    }

    public Bitmap takeScreenshot() {
        Display realDisplay = DisplayManagerGlobal.getInstance().getRealDisplay(this.mDisplayId);
        Point point = new Point();
        realDisplay.getRealSize(point);
        ScreenCapture.SynchronousScreenCaptureListener createSyncCaptureListener = ScreenCapture.createSyncCaptureListener();
        try {
            if (!this.mUiAutomationConnection.takeScreenshot(new Rect(0, 0, point.x, point.y), createSyncCaptureListener, this.mDisplayId)) {
                return null;
            }
            ScreenCapture.ScreenshotHardwareBuffer buffer = createSyncCaptureListener.getBuffer();
            if (buffer == null) {
                Log.e(LOG_TAG, "Failed to take screenshot for display=" + this.mDisplayId);
                return null;
            }
            Bitmap asBitmap = buffer.asBitmap();
            if (asBitmap == null) {
                Log.e(LOG_TAG, "Failed to take screenshot for display=" + this.mDisplayId);
                return null;
            }
            HardwareBuffer hardwareBuffer = buffer.getHardwareBuffer();
            try {
                Bitmap copy = asBitmap.copy(Bitmap.Config.ARGB_8888, false);
                if (hardwareBuffer != null) {
                    hardwareBuffer.close();
                }
                asBitmap.recycle();
                copy.setHasAlpha(false);
                return copy;
            } catch (Throwable th) {
                if (hardwareBuffer != null) {
                    try {
                        hardwareBuffer.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error while taking screenshot of display " + this.mDisplayId, e);
            return null;
        }
    }

    public Bitmap takeScreenshot(Window window) {
        View peekDecorView;
        ViewRootImpl viewRootImpl;
        if (window == null || (peekDecorView = window.peekDecorView()) == null || (viewRootImpl = peekDecorView.getViewRootImpl()) == null) {
            return null;
        }
        SurfaceControl surfaceControl = viewRootImpl.getSurfaceControl();
        if (!surfaceControl.isValid()) {
            return null;
        }
        new SurfaceControl.Transaction().apply(true);
        ScreenCapture.SynchronousScreenCaptureListener createSyncCaptureListener = ScreenCapture.createSyncCaptureListener();
        try {
            if (!this.mUiAutomationConnection.takeSurfaceControlScreenshot(surfaceControl, createSyncCaptureListener)) {
                Log.e(LOG_TAG, "Failed to take screenshot for window=" + window);
                return null;
            }
            ScreenCapture.ScreenshotHardwareBuffer buffer = createSyncCaptureListener.getBuffer();
            if (buffer == null) {
                Log.e(LOG_TAG, "Failed to take screenshot for window=" + window);
                return null;
            }
            Bitmap asBitmap = buffer.asBitmap();
            if (asBitmap == null) {
                Log.e(LOG_TAG, "Failed to take screenshot for window=" + window);
                return null;
            }
            HardwareBuffer hardwareBuffer = buffer.getHardwareBuffer();
            try {
                Bitmap copy = asBitmap.copy(Bitmap.Config.ARGB_8888, false);
                if (hardwareBuffer != null) {
                    hardwareBuffer.close();
                }
                asBitmap.recycle();
                return copy;
            } catch (Throwable th) {
                if (hardwareBuffer != null) {
                    try {
                        hardwareBuffer.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error while taking screenshot!", e);
            return null;
        }
    }

    public void setRunAsMonkey(boolean z) {
        try {
            ActivityManager.getService().setUserIsMonkey(z);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error while setting run as monkey!", e);
        }
    }

    public boolean clearWindowContentFrameStats(int i) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            return this.mUiAutomationConnection.clearWindowContentFrameStats(i);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error clearing window content frame stats!", e);
            return false;
        }
    }

    public WindowContentFrameStats getWindowContentFrameStats(int i) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            return this.mUiAutomationConnection.getWindowContentFrameStats(i);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error getting window content frame stats!", e);
            return null;
        }
    }

    @Deprecated
    public void clearWindowAnimationFrameStats() {
        try {
            this.mUiAutomationConnection.clearWindowAnimationFrameStats();
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error clearing window animation frame stats!", e);
        }
    }

    @Deprecated
    public WindowAnimationFrameStats getWindowAnimationFrameStats() {
        try {
            return this.mUiAutomationConnection.getWindowAnimationFrameStats();
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error getting window animation frame stats!", e);
            return null;
        }
    }

    public void grantRuntimePermission(String str, String str2) {
        grantRuntimePermissionAsUser(str, str2, Process.myUserHandle());
    }

    @Deprecated
    public boolean grantRuntimePermission(String str, String str2, UserHandle userHandle) {
        grantRuntimePermissionAsUser(str, str2, userHandle);
        return true;
    }

    public void grantRuntimePermissionAsUser(String str, String str2, UserHandle userHandle) {
        try {
            this.mUiAutomationConnection.grantRuntimePermission(str, str2, userHandle.getIdentifier());
        } catch (Exception e) {
            throw new SecurityException("Error granting runtime permission", e);
        }
    }

    public void revokeRuntimePermission(String str, String str2) {
        revokeRuntimePermissionAsUser(str, str2, Process.myUserHandle());
    }

    @Deprecated
    public boolean revokeRuntimePermission(String str, String str2, UserHandle userHandle) {
        revokeRuntimePermissionAsUser(str, str2, userHandle);
        return true;
    }

    public void revokeRuntimePermissionAsUser(String str, String str2, UserHandle userHandle) {
        try {
            this.mUiAutomationConnection.revokeRuntimePermission(str, str2, userHandle.getIdentifier());
        } catch (Exception e) {
            throw new SecurityException("Error granting runtime permission", e);
        }
    }

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x002b: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:36:0x002b */
    public ParcelFileDescriptor executeShellCommand(String str) {
        ParcelFileDescriptor parcelFileDescriptor;
        ParcelFileDescriptor parcelFileDescriptor2;
        ParcelFileDescriptor parcelFileDescriptor3;
        warnIfBetterCommand(str);
        ParcelFileDescriptor parcelFileDescriptor4 = null;
        try {
        } catch (Throwable th) {
            th = th;
            parcelFileDescriptor4 = parcelFileDescriptor;
        }
        try {
            try {
                ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
                parcelFileDescriptor2 = createPipe[0];
                try {
                    parcelFileDescriptor3 = createPipe[1];
                } catch (RemoteException | IOException e) {
                    e = e;
                } catch (IllegalArgumentException | NullPointerException | SecurityException e2) {
                    e = e2;
                }
            } catch (RemoteException | IOException e3) {
                e = e3;
                parcelFileDescriptor2 = null;
            } catch (IllegalArgumentException | NullPointerException | SecurityException e4) {
                e = e4;
            }
            try {
                this.mUiAutomationConnection.executeShellCommand(str, parcelFileDescriptor3, null);
                IoUtils.closeQuietly(parcelFileDescriptor3);
                return parcelFileDescriptor2;
            } catch (RemoteException | IOException e5) {
                e = e5;
                parcelFileDescriptor4 = parcelFileDescriptor3;
                Log.e(LOG_TAG, "Error executing shell command!", e);
                IoUtils.closeQuietly(parcelFileDescriptor4);
                return parcelFileDescriptor2;
            } catch (IllegalArgumentException | NullPointerException | SecurityException e6) {
                e = e6;
                parcelFileDescriptor4 = parcelFileDescriptor2;
                IoUtils.closeQuietly(parcelFileDescriptor4);
                throw e;
            }
        } catch (Throwable th2) {
            th = th2;
            IoUtils.closeQuietly(parcelFileDescriptor4);
            throw th;
        }
    }

    public ParcelFileDescriptor[] executeShellCommandRw(String str) {
        return executeShellCommandInternal(str, false);
    }

    public ParcelFileDescriptor[] executeShellCommandRwe(String str) {
        return executeShellCommandInternal(str, true);
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0098  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.os.ParcelFileDescriptor[] executeShellCommandInternal(java.lang.String r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 175
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.UiAutomation.executeShellCommandInternal(java.lang.String, boolean):android.os.ParcelFileDescriptor[]");
    }

    public String toString() {
        return "UiAutomation@" + Integer.toHexString(hashCode()) + "[id=" + this.mConnectionId + ", displayId=" + this.mDisplayId + ", flags=" + this.mFlags + NavigationBarInflaterView.SIZE_MOD_END;
    }

    private void throwIfConnectedLocked() {
        if (this.mConnectionState != 2) {
            return;
        }
        throw new IllegalStateException("UiAutomation connected, " + this);
    }

    private void throwIfNotConnectedLocked() {
        String str;
        if (this.mConnectionState != 2) {
            if (useAccessibility()) {
                str = "UiAutomation not connected, ";
            } else {
                str = "UiAutomation not connected: Accessibility-dependent method called with FLAG_DONT_USE_ACCESSIBILITY set, ";
            }
            throw new IllegalStateException(str + this);
        }
    }

    private void warnIfBetterCommand(String str) {
        if (str.startsWith("pm grant ")) {
            Log.w(LOG_TAG, "UiAutomation.grantRuntimePermission() is more robust and should be used instead of 'pm grant'");
        } else if (str.startsWith("pm revoke ")) {
            Log.w(LOG_TAG, "UiAutomation.revokeRuntimePermission() is more robust and should be used instead of 'pm revoke'");
        }
    }

    private boolean useAccessibility() {
        return (this.mFlags & 2) == 0;
    }

    private static int getDisplayId(Context context) {
        Preconditions.checkArgument(context != null, "Context cannot be null!");
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        if (!userManager.isVisibleBackgroundUsersSupported()) {
            return 0;
        }
        int displayId = context.getDisplayId();
        if (displayId != -1) {
            return displayId != 0 ? displayId : getMainDisplayIdAssignedToUser(context, userManager);
        }
        Log.e(LOG_TAG, "UiAutomation created UI context with invalid display id, assuming it's running in the display assigned to the user");
        return getMainDisplayIdAssignedToUser(context, userManager);
    }

    private static int getMainDisplayIdAssignedToUser(Context context, UserManager userManager) {
        if (!userManager.isUserVisible()) {
            Log.e(LOG_TAG, "User (" + context.getUserId() + ") is not visible, using DEFAULT_DISPLAY");
            return 0;
        }
        return userManager.getMainDisplayIdAssignedToUser();
    }

    private class IAccessibilityServiceClientImpl extends AccessibilityService.IAccessibilityServiceClientWrapper {
        public IAccessibilityServiceClientImpl(UiAutomation uiAutomation, Looper looper, int i) {
            super((Context) null, looper, new AccessibilityService.Callbacks(i) { // from class: android.app.UiAutomation.IAccessibilityServiceClientImpl.1
                private final int mGenerationId;
                final /* synthetic */ int val$generationId;

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void createImeSession(IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onAccessibilityButtonAvailabilityChanged(boolean z) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onAccessibilityButtonClicked(int i2) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onFingerprintCapturingGesturesChanged(boolean z) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onFingerprintGesture(int i2) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public boolean onGesture(AccessibilityGestureEvent accessibilityGestureEvent) {
                    return false;
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onInterrupt() {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public boolean onKeyEvent(KeyEvent keyEvent) {
                    return false;
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onMagnificationChanged(int i2, Region region, MagnificationConfig magnificationConfig) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onMotionEvent(MotionEvent motionEvent) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onPerformGestureResult(int i2, boolean z) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onServiceConnected() {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onSoftKeyboardShowModeChanged(int i2) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onSystemActionsChanged() {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onTouchStateChanged(int i2, int i3) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void startInput(RemoteAccessibilityInputConnection remoteAccessibilityInputConnection, EditorInfo editorInfo, boolean z) {
                }

                {
                    this.val$generationId = i;
                    this.mGenerationId = i;
                }

                private boolean isGenerationChangedLocked() {
                    return this.mGenerationId != UiAutomation.this.mGenerationId;
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void init(int i2, IBinder iBinder) {
                    synchronized (UiAutomation.this.mLock) {
                        if (isGenerationChangedLocked()) {
                            return;
                        }
                        UiAutomation.this.mConnectionState = 2;
                        UiAutomation.this.mConnectionId = i2;
                        UiAutomation.this.mLock.notifyAll();
                        if (Build.IS_DEBUGGABLE) {
                            Log.v(UiAutomation.LOG_TAG, "Init " + UiAutomation.this);
                        }
                    }
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
                    synchronized (UiAutomation.this.mLock) {
                        if (isGenerationChangedLocked()) {
                            return;
                        }
                        UiAutomation uiAutomation2 = UiAutomation.this;
                        uiAutomation2.mLastEventTimeMillis = Math.max(uiAutomation2.mLastEventTimeMillis, accessibilityEvent.getEventTime());
                        if (UiAutomation.this.mCurrentEventWatchersCount > 0) {
                            UiAutomation.this.mEventQueue.add(AccessibilityEvent.obtain(accessibilityEvent));
                        }
                        UiAutomation.this.mLock.notifyAll();
                        OnAccessibilityEventListener onAccessibilityEventListener = UiAutomation.this.mOnAccessibilityEventListener;
                        if (onAccessibilityEventListener != null) {
                            UiAutomation.this.mLocalCallbackHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.app.UiAutomation$IAccessibilityServiceClientImpl$1$$ExternalSyntheticLambda0
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    ((UiAutomation.OnAccessibilityEventListener) obj).onAccessibilityEvent((AccessibilityEvent) obj2);
                                }
                            }, onAccessibilityEventListener, AccessibilityEvent.obtain(accessibilityEvent)));
                        }
                    }
                }
            });
        }
    }
}
