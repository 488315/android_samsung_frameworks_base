package com.android.server.accessibility;

import android.accessibilityservice.AccessibilityGestureEvent;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityTrace;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.accessibilityservice.IAccessibilityServiceConnection;
import android.accessibilityservice.IBrailleDisplayController;
import android.accessibilityservice.MagnificationConfig;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.pm.ParceledListSlice;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.ParcelableColorSpace;
import android.graphics.Region;
import android.hardware.HardwareBuffer;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.usb.UsbDevice;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PermissionEnforcer;
import android.os.PowerManager;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MagnificationSpec;
import android.view.SurfaceControl;
import android.view.accessibility.A11yRune;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityWindowInfo;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import android.view.inputmethod.EditorInfo;
import android.window.ScreenCapture;
import com.android.internal.compat.IPlatformCompat;
import com.android.internal.inputmethod.IAccessibilityInputMethodSession;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.UserspaceRebootLogger$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityWindowManager;
import com.android.server.accessibility.FingerprintGestureDispatcher;
import com.android.server.accessibility.KeyEventDispatcher;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accessibility.magnification.MagnificationController;
import com.android.server.accessibility.magnification.MagnificationProcessor;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class AbstractAccessibilityServiceConnection extends IAccessibilityServiceConnection.Stub implements ServiceConnection, IBinder.DeathRecipient, KeyEventDispatcher.KeyEventFilter, FingerprintGestureDispatcher.FingerprintGestureClient {
    public final AccessibilityWindowManager mA11yWindowManager;
    public final AccessibilityServiceInfo mAccessibilityServiceInfo;
    public String mAttributionTag;
    public boolean mCaptureFingerprintGestures;
    public final ComponentName mComponentName;
    public final Context mContext;
    public final DisplayManager mDisplayManager;
    public int mDisplayTypes;
    public final AnonymousClass1 mEventDispatchHandler;
    public int mEventTypes;
    public int mFeedbackType;
    public int mFetchFlags;
    public int mGenericMotionEventSources;
    public final IPlatformCompat mIPlatformCompat;
    public final int mId;
    public final InvocationHandler mInvocationHandler;
    public boolean mIsDefault;
    public boolean mLastAccessibilityButtonCallbackState;
    public final Object mLock;
    public final Handler mMainHandler;
    public long mNotificationTimeout;
    public int mObservedMotionEventSources;
    public final SparseArray mOverlayWindowTokens;
    public final List mOverlays;
    public final Set mPackageNames;
    public final SparseArray mPendingEvents;
    public final PowerManager mPowerManager;
    public boolean mReceivedAccessibilityButtonCallbackSinceBind;
    public boolean mRequestAccessibilityButton;
    public boolean mRequestFilterKeyEvents;
    public boolean mRequestImeApis;
    public boolean mRequestMultiFingerGestures;
    public final SparseArray mRequestTakeScreenshotOfWindowTimestampMs;
    public long mRequestTakeScreenshotTimestampMs;
    public boolean mRequestTouchExplorationMode;
    public boolean mRequestTwoFingerPassthrough;
    public boolean mRetrieveInteractiveWindows;
    public final AccessibilitySecurityPolicy mSecurityPolicy;
    public boolean mSendMotionEvents;
    public IBinder mService;
    public final SparseArray mServiceDetectsGestures;
    public boolean mServiceHandlesDoubleTap;
    public IAccessibilityServiceClient mServiceInterface;
    public final SystemActionPerformer mSystemActionPerformer;
    public final SystemSupport mSystemSupport;
    public final AccessibilityTrace mTrace;
    public boolean mUsesAccessibilityCache;
    public final WindowManagerInternal mWindowManagerService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InvocationHandler extends Handler {
        public boolean mIsSoftKeyboardCallbackEnabled;
        public final SparseArray mMagnificationCallbackState;

        public InvocationHandler(Looper looper) {
            super(looper, null, true);
            this.mMagnificationCallbackState = new SparseArray(0);
            this.mIsSoftKeyboardCallbackEnabled = false;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z;
            int i = message.what;
            AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection = AbstractAccessibilityServiceConnection.this;
            if (i == 1) {
                Object obj = message.obj;
                if (obj instanceof AccessibilityGestureEvent) {
                    AccessibilityGestureEvent accessibilityGestureEvent = (AccessibilityGestureEvent) obj;
                    IAccessibilityServiceClient serviceInterfaceSafely = abstractAccessibilityServiceConnection.getServiceInterfaceSafely();
                    if (serviceInterfaceSafely != null) {
                        try {
                            if (abstractAccessibilityServiceConnection.svcClientTracingEnabled()) {
                                abstractAccessibilityServiceConnection.logTraceSvcClient("onGesture", accessibilityGestureEvent.toString());
                            }
                            serviceInterfaceSafely.onGesture(accessibilityGestureEvent);
                        } catch (RemoteException e) {
                            Slog.e("AbstractAccessibilityServiceConnection", "Error during sending gesture " + accessibilityGestureEvent + " to " + abstractAccessibilityServiceConnection.mService, e);
                        }
                    }
                    if (android.view.accessibility.Flags.copyEventsForGestureDetection()) {
                        accessibilityGestureEvent.recycle();
                        return;
                    }
                    return;
                }
                return;
            }
            if (i == 2) {
                IAccessibilityServiceClient serviceInterfaceSafely2 = abstractAccessibilityServiceConnection.getServiceInterfaceSafely();
                if (serviceInterfaceSafely2 != null) {
                    try {
                        if (abstractAccessibilityServiceConnection.svcClientTracingEnabled()) {
                            abstractAccessibilityServiceConnection.logTraceSvcClient("clearAccessibilityCache", "");
                        }
                        serviceInterfaceSafely2.clearAccessibilityCache();
                        return;
                    } catch (RemoteException e2) {
                        Slog.e("AbstractAccessibilityServiceConnection", "Error during requesting accessibility info cache to be cleared.", e2);
                        return;
                    }
                }
                return;
            }
            switch (i) {
                case 5:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    Region region = (Region) someArgs.arg1;
                    MagnificationConfig magnificationConfig = (MagnificationConfig) someArgs.arg2;
                    int i2 = someArgs.argi1;
                    IAccessibilityServiceClient serviceInterfaceSafely3 = abstractAccessibilityServiceConnection.getServiceInterfaceSafely();
                    if (serviceInterfaceSafely3 != null) {
                        try {
                            if (abstractAccessibilityServiceConnection.svcClientTracingEnabled()) {
                                abstractAccessibilityServiceConnection.logTraceSvcClient("onMagnificationChanged", i2 + ", " + region + ", " + magnificationConfig.toString());
                            }
                            serviceInterfaceSafely3.onMagnificationChanged(i2, region, magnificationConfig);
                        } catch (RemoteException e3) {
                            Slog.e("AbstractAccessibilityServiceConnection", "Error sending magnification changes to " + abstractAccessibilityServiceConnection.mService, e3);
                        }
                    }
                    someArgs.recycle();
                    return;
                case 6:
                    int i3 = message.arg1;
                    IAccessibilityServiceClient serviceInterfaceSafely4 = abstractAccessibilityServiceConnection.getServiceInterfaceSafely();
                    if (serviceInterfaceSafely4 != null) {
                        try {
                            if (abstractAccessibilityServiceConnection.svcClientTracingEnabled()) {
                                abstractAccessibilityServiceConnection.logTraceSvcClient("onSoftKeyboardShowModeChanged", String.valueOf(i3));
                            }
                            serviceInterfaceSafely4.onSoftKeyboardShowModeChanged(i3);
                            return;
                        } catch (RemoteException e4) {
                            Slog.e("AbstractAccessibilityServiceConnection", "Error sending soft keyboard show mode changes to " + abstractAccessibilityServiceConnection.mService, e4);
                            return;
                        }
                    }
                    return;
                case 7:
                    int i4 = message.arg1;
                    IAccessibilityServiceClient serviceInterfaceSafely5 = abstractAccessibilityServiceConnection.getServiceInterfaceSafely();
                    if (serviceInterfaceSafely5 != null) {
                        try {
                            if (abstractAccessibilityServiceConnection.svcClientTracingEnabled()) {
                                abstractAccessibilityServiceConnection.logTraceSvcClient("onAccessibilityButtonClicked", String.valueOf(i4));
                            }
                            serviceInterfaceSafely5.onAccessibilityButtonClicked(i4);
                            return;
                        } catch (RemoteException e5) {
                            Slog.e("AbstractAccessibilityServiceConnection", "Error sending accessibility button click to " + abstractAccessibilityServiceConnection.mService, e5);
                            return;
                        }
                    }
                    return;
                case 8:
                    boolean z2 = message.arg1 != 0;
                    if (abstractAccessibilityServiceConnection.mReceivedAccessibilityButtonCallbackSinceBind && abstractAccessibilityServiceConnection.mLastAccessibilityButtonCallbackState == z2) {
                        return;
                    }
                    abstractAccessibilityServiceConnection.mReceivedAccessibilityButtonCallbackSinceBind = true;
                    abstractAccessibilityServiceConnection.mLastAccessibilityButtonCallbackState = z2;
                    IAccessibilityServiceClient serviceInterfaceSafely6 = abstractAccessibilityServiceConnection.getServiceInterfaceSafely();
                    if (serviceInterfaceSafely6 != null) {
                        try {
                            if (abstractAccessibilityServiceConnection.svcClientTracingEnabled()) {
                                abstractAccessibilityServiceConnection.logTraceSvcClient("onAccessibilityButtonAvailabilityChanged", String.valueOf(z2));
                            }
                            serviceInterfaceSafely6.onAccessibilityButtonAvailabilityChanged(z2);
                            return;
                        } catch (RemoteException e6) {
                            Slog.e("AbstractAccessibilityServiceConnection", "Error sending accessibility button availability change to " + abstractAccessibilityServiceConnection.mService, e6);
                            return;
                        }
                    }
                    return;
                case 9:
                    IAccessibilityServiceClient serviceInterfaceSafely7 = abstractAccessibilityServiceConnection.getServiceInterfaceSafely();
                    if (serviceInterfaceSafely7 != null) {
                        try {
                            if (abstractAccessibilityServiceConnection.svcClientTracingEnabled()) {
                                abstractAccessibilityServiceConnection.logTraceSvcClient("onSystemActionsChanged", "");
                            }
                            serviceInterfaceSafely7.onSystemActionsChanged();
                            return;
                        } catch (RemoteException e7) {
                            Slog.e("AbstractAccessibilityServiceConnection", "Error sending system actions change to " + abstractAccessibilityServiceConnection.mService, e7);
                            return;
                        }
                    }
                    return;
                case 10:
                    abstractAccessibilityServiceConnection.createImeSessionInternal();
                    return;
                case 11:
                    z = message.arg1 != 0;
                    IAccessibilityInputMethodSession iAccessibilityInputMethodSession = (IAccessibilityInputMethodSession) message.obj;
                    IAccessibilityServiceClient serviceInterfaceSafely8 = abstractAccessibilityServiceConnection.getServiceInterfaceSafely();
                    if (serviceInterfaceSafely8 == null || iAccessibilityInputMethodSession == null) {
                        return;
                    }
                    try {
                        if (abstractAccessibilityServiceConnection.svcClientTracingEnabled()) {
                            abstractAccessibilityServiceConnection.logTraceSvcClient("createImeSession", "");
                        }
                        serviceInterfaceSafely8.setImeSessionEnabled(iAccessibilityInputMethodSession, z);
                        return;
                    } catch (RemoteException e8) {
                        Slog.e("AbstractAccessibilityServiceConnection", "Error requesting IME session from " + abstractAccessibilityServiceConnection.mService, e8);
                        return;
                    }
                case 12:
                    IAccessibilityServiceClient serviceInterfaceSafely9 = abstractAccessibilityServiceConnection.getServiceInterfaceSafely();
                    if (serviceInterfaceSafely9 != null) {
                        try {
                            if (abstractAccessibilityServiceConnection.svcClientTracingEnabled()) {
                                abstractAccessibilityServiceConnection.logTraceSvcClient("bindInput", "");
                            }
                            serviceInterfaceSafely9.bindInput();
                            return;
                        } catch (RemoteException e9) {
                            Slog.e("AbstractAccessibilityServiceConnection", "Error binding input to " + abstractAccessibilityServiceConnection.mService, e9);
                            return;
                        }
                    }
                    return;
                case 13:
                    IAccessibilityServiceClient serviceInterfaceSafely10 = abstractAccessibilityServiceConnection.getServiceInterfaceSafely();
                    if (serviceInterfaceSafely10 != null) {
                        try {
                            if (abstractAccessibilityServiceConnection.svcClientTracingEnabled()) {
                                abstractAccessibilityServiceConnection.logTraceSvcClient("unbindInput", "");
                            }
                            serviceInterfaceSafely10.unbindInput();
                            return;
                        } catch (RemoteException e10) {
                            Slog.e("AbstractAccessibilityServiceConnection", "Error unbinding input to " + abstractAccessibilityServiceConnection.mService, e10);
                            return;
                        }
                    }
                    return;
                case 14:
                    z = message.arg1 != 0;
                    SomeArgs someArgs2 = (SomeArgs) message.obj;
                    IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection = (IRemoteAccessibilityInputConnection) someArgs2.arg1;
                    EditorInfo editorInfo = (EditorInfo) someArgs2.arg2;
                    IAccessibilityServiceClient serviceInterfaceSafely11 = abstractAccessibilityServiceConnection.getServiceInterfaceSafely();
                    if (serviceInterfaceSafely11 != null) {
                        try {
                            if (abstractAccessibilityServiceConnection.svcClientTracingEnabled()) {
                                abstractAccessibilityServiceConnection.logTraceSvcClient("startInput", "editorInfo=" + editorInfo + " restarting=" + z);
                            }
                            serviceInterfaceSafely11.startInput(iRemoteAccessibilityInputConnection, editorInfo, z);
                        } catch (RemoteException e11) {
                            Slog.e("AbstractAccessibilityServiceConnection", "Error starting input to " + abstractAccessibilityServiceConnection.mService, e11);
                        }
                    }
                    someArgs2.recycle();
                    return;
                default:
                    throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown message: "));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface SystemSupport {
    }

    /* JADX WARN: Type inference failed for: r3v6, types: [com.android.server.accessibility.AbstractAccessibilityServiceConnection$1] */
    public AbstractAccessibilityServiceConnection(Context context, ComponentName componentName, AccessibilityServiceInfo accessibilityServiceInfo, int i, Handler handler, Object obj, AccessibilitySecurityPolicy accessibilitySecurityPolicy, SystemSupport systemSupport, AccessibilityTrace accessibilityTrace, WindowManagerInternal windowManagerInternal, SystemActionPerformer systemActionPerformer, AccessibilityWindowManager accessibilityWindowManager) {
        super(PermissionEnforcer.fromContext(context));
        this.mDisplayTypes = 1;
        this.mPackageNames = new HashSet();
        this.mServiceDetectsGestures = new SparseArray(0);
        this.mPendingEvents = new SparseArray();
        this.mUsesAccessibilityCache = false;
        this.mOverlayWindowTokens = new SparseArray();
        this.mOverlays = new ArrayList();
        this.mRequestTakeScreenshotOfWindowTimestampMs = new SparseArray();
        this.mContext = context;
        this.mWindowManagerService = windowManagerInternal;
        this.mId = i;
        this.mComponentName = componentName;
        this.mAccessibilityServiceInfo = accessibilityServiceInfo;
        this.mLock = obj;
        this.mSecurityPolicy = accessibilitySecurityPolicy;
        this.mSystemActionPerformer = systemActionPerformer;
        this.mSystemSupport = systemSupport;
        this.mTrace = accessibilityTrace;
        this.mMainHandler = handler;
        this.mInvocationHandler = new InvocationHandler(handler.getLooper());
        this.mA11yWindowManager = accessibilityWindowManager;
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        this.mIPlatformCompat = IPlatformCompat.Stub.asInterface(ServiceManager.getService("platform_compat"));
        this.mEventDispatchHandler = new Handler(handler.getLooper()) { // from class: com.android.server.accessibility.AbstractAccessibilityServiceConnection.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i2 = message.what;
                AccessibilityEvent accessibilityEvent = (AccessibilityEvent) message.obj;
                boolean z = message.arg1 != 0;
                AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection = AbstractAccessibilityServiceConnection.this;
                synchronized (abstractAccessibilityServiceConnection.mLock) {
                    try {
                        IAccessibilityServiceClient iAccessibilityServiceClient = abstractAccessibilityServiceConnection.mServiceInterface;
                        if (iAccessibilityServiceClient == null) {
                            return;
                        }
                        if (accessibilityEvent == null) {
                            accessibilityEvent = (AccessibilityEvent) abstractAccessibilityServiceConnection.mPendingEvents.get(i2);
                            if (accessibilityEvent == null) {
                                return;
                            } else {
                                abstractAccessibilityServiceConnection.mPendingEvents.remove(i2);
                            }
                        }
                        abstractAccessibilityServiceConnection.mSecurityPolicy.getClass();
                        if ((abstractAccessibilityServiceConnection.getCapabilities() & 1) != 0) {
                            accessibilityEvent.setConnectionId(abstractAccessibilityServiceConnection.mId);
                        } else {
                            accessibilityEvent.setSource(null);
                        }
                        accessibilityEvent.setSealed(true);
                        try {
                            try {
                                if (abstractAccessibilityServiceConnection.svcClientTracingEnabled()) {
                                    abstractAccessibilityServiceConnection.logTraceSvcClient("onAccessibilityEvent", accessibilityEvent + ";" + z);
                                }
                                iAccessibilityServiceClient.onAccessibilityEvent(accessibilityEvent, z);
                            } catch (Throwable th) {
                                accessibilityEvent.recycle();
                                throw th;
                            }
                        } catch (RemoteException e) {
                            Slog.e("AbstractAccessibilityServiceConnection", "Error during sending " + accessibilityEvent + " to " + iAccessibilityServiceClient, e);
                        }
                        accessibilityEvent.recycle();
                    } finally {
                    }
                }
            }
        };
        setDynamicallyConfigurableProperties(accessibilityServiceInfo);
    }

    public final void addWindowTokenForDisplay(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Binder binder = new Binder();
            if (this.mTrace.isA11yTracingEnabledForTypes(512L)) {
                logTraceWM("addWindowToken", binder + ";TYPE_ACCESSIBILITY_OVERLAY;" + i + ";null");
            }
            this.mWindowManagerService.addWindowToken(binder, 2032, i, null);
            synchronized (this.mLock) {
                this.mOverlayWindowTokens.put(i, binder);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void addWindowTokensForAllDisplays() {
        for (Display display : this.mDisplayManager.getDisplays()) {
            addWindowTokenForDisplay(display.getDisplayId());
        }
        if (A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP) {
            for (Display display2 : this.mDisplayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN")) {
                if (display2.getDisplayId() == 1) {
                    addWindowTokenForDisplay(display2.getDisplayId());
                    return;
                }
            }
        }
    }

    public final void attachAccessibilityOverlayToDisplay(int i, int i2, SurfaceControl surfaceControl, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) this.mSystemSupport;
            accessibilityManagerService.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda21(), accessibilityManagerService, Integer.valueOf(i), Integer.valueOf(i2), surfaceControl, iAccessibilityInteractionConnectionCallback));
            ((ArrayList) this.mOverlays).add(surfaceControl);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void attachAccessibilityOverlayToWindow(int i, int i2, SurfaceControl surfaceControl, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            transaction.setTrustedOverlay(surfaceControl, true).apply();
            transaction.close();
            synchronized (this.mLock) {
                AccessibilityWindowManager.RemoteAccessibilityConnection connectionLocked = this.mA11yWindowManager.getConnectionLocked(((AccessibilityManagerService) this.mSystemSupport).mCurrentUserId, resolveAccessibilityWindowIdLocked(i2));
                if (connectionLocked == null) {
                    iAccessibilityInteractionConnectionCallback.sendAttachOverlayResult(2, i);
                } else {
                    connectionLocked.mConnection.attachAccessibilityOverlayToWindow(surfaceControl, i, iAccessibilityInteractionConnectionCallback);
                    ((ArrayList) this.mOverlays).add(surfaceControl);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void connectBluetoothBrailleDisplay(String str, IBrailleDisplayController iBrailleDisplayController) {
        connectBluetoothBrailleDisplay_enforcePermission();
        throw new UnsupportedOperationException();
    }

    public void connectUsbBrailleDisplay(UsbDevice usbDevice, IBrailleDisplayController iBrailleDisplayController) {
        throw new UnsupportedOperationException();
    }

    public void createImeSessionInternal() {
    }

    public void dispatchGesture(int i, ParceledListSlice parceledListSlice, int i2) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("dispatchGesture", "sequence=" + i + ";gestureSteps=" + parceledListSlice + ";displayId=" + i2);
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "AbstractAccessibilityServiceConnection", printWriter)) {
            synchronized (this.mLock) {
                printWriter.append((CharSequence) ("Service[label=" + ((Object) this.mAccessibilityServiceInfo.getResolveInfo().loadLabel(this.mContext.getPackageManager()))));
                printWriter.append((CharSequence) (", id=" + this.mAccessibilityServiceInfo.getId()));
                printWriter.append((CharSequence) (", isDefault=" + this.mIsDefault));
                printWriter.append((CharSequence) (", requestTouchExplorationMode=" + this.mRequestTouchExplorationMode));
                printWriter.append((CharSequence) (", requestFilterKeyEvents=" + this.mRequestFilterKeyEvents));
                printWriter.append((CharSequence) (", retrieveInteractiveWindows=" + this.mRetrieveInteractiveWindows));
                printWriter.append((CharSequence) (", captureFingerprintGestures=" + this.mCaptureFingerprintGestures));
                printWriter.append((CharSequence) (", requestAccessibilityButton=" + this.mRequestAccessibilityButton));
                printWriter.append((CharSequence) (", receivedAccessibilityButtonCallbackSinceBind=" + this.mReceivedAccessibilityButtonCallbackSinceBind));
                printWriter.append((CharSequence) (", lastAccessibilityButtonCallbackState=" + this.mLastAccessibilityButtonCallbackState));
                printWriter.append((CharSequence) (", fetchFlags=" + this.mFetchFlags));
                printWriter.append((CharSequence) (", nonInteractiveUiTimeout=" + this.mAccessibilityServiceInfo.getNonInteractiveUiTimeoutMillis()));
                printWriter.append((CharSequence) (", interactiveUiTimeout=" + this.mAccessibilityServiceInfo.getInteractiveUiTimeoutMillis()));
                printWriter.append((CharSequence) (", feedbackType" + AccessibilityServiceInfo.feedbackTypeToString(this.mFeedbackType)));
                printWriter.append((CharSequence) (", capabilities=" + this.mAccessibilityServiceInfo.getCapabilities()));
                printWriter.append((CharSequence) (", eventTypes=" + AccessibilityEvent.eventTypeToString(this.mEventTypes)));
                printWriter.append((CharSequence) (", notificationTimeout=" + this.mNotificationTimeout));
                printWriter.append((CharSequence) (", requestA11yBtn=" + this.mRequestAccessibilityButton));
                printWriter.append("]");
            }
        }
    }

    public final void ensureWindowsAvailableTimedLocked(int i) {
        if (i == -1) {
            return;
        }
        AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver = (AccessibilityWindowManager.DisplayWindowsObserver) this.mA11yWindowManager.mDisplayWindowsObservers.get(i);
        if ((displayWindowsObserver != null ? displayWindowsObserver.mWindows : null) != null) {
            return;
        }
        if (!this.mA11yWindowManager.isTrackingWindowsLocked(i)) {
            ((AccessibilityManagerService) this.mSystemSupport).onClientChangeLocked(false, false);
        }
        if (!this.mA11yWindowManager.isTrackingWindowsLocked(i)) {
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        while (true) {
            AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver2 = (AccessibilityWindowManager.DisplayWindowsObserver) this.mA11yWindowManager.mDisplayWindowsObservers.get(i);
            if ((displayWindowsObserver2 != null ? displayWindowsObserver2.mWindows : null) != null) {
                return;
            }
            long uptimeMillis2 = 5000 - (SystemClock.uptimeMillis() - uptimeMillis);
            if (uptimeMillis2 <= 0) {
                return;
            } else {
                try {
                    this.mLock.wait(uptimeMillis2);
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    public final String[] findAccessibilityNodeInfoByAccessibilityId(int i, long j, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, long j2, Bundle bundle) {
        Region region;
        int i4;
        Region region2;
        AccessibilityWindowManager.RemoteAccessibilityConnection remoteAccessibilityConnection;
        if (svcConnTracingEnabled()) {
            StringBuilder m = UserspaceRebootLogger$$ExternalSyntheticOutline0.m(i, "accessibilityWindowId=", j, ";accessibilityNodeId=");
            m.append(";interactionId=");
            m.append(i2);
            m.append(";callback=");
            m.append(iAccessibilityInteractionConnectionCallback);
            m.append(";flags=");
            m.append(i3);
            m.append(";interrogatingTid=");
            m.append(j2);
            m.append(";arguments=");
            m.append(bundle);
            logTraceSvcConn("findAccessibilityNodeInfoByAccessibilityId", m.toString());
        }
        Region obtain = Region.obtain();
        synchronized (this.mLock) {
            try {
                this.mUsesAccessibilityCache = true;
                if (!hasRightsToCurrentUserLocked()) {
                    return null;
                }
                int resolveAccessibilityWindowIdLocked = resolveAccessibilityWindowIdLocked(i);
                if (!this.mSecurityPolicy.canGetAccessibilityNodeInfoLocked(((AccessibilityManagerService) this.mSystemSupport).mCurrentUserId, this, resolveAccessibilityWindowIdLocked)) {
                    return null;
                }
                AccessibilityWindowManager.RemoteAccessibilityConnection connectionLocked = this.mA11yWindowManager.getConnectionLocked(((AccessibilityManagerService) this.mSystemSupport).mCurrentUserId, resolveAccessibilityWindowIdLocked);
                if (connectionLocked == null) {
                    return null;
                }
                if (this.mA11yWindowManager.computePartialInteractiveRegionForWindowLocked(resolveAccessibilityWindowIdLocked, obtain)) {
                    region = obtain;
                } else {
                    obtain.recycle();
                    region = null;
                }
                Pair windowTransformationMatrixAndMagnificationSpec = ((AccessibilityManagerService) this.mSystemSupport).getWindowTransformationMatrixAndMagnificationSpec(resolveAccessibilityWindowIdLocked);
                float[] fArr = (float[]) windowTransformationMatrixAndMagnificationSpec.first;
                MagnificationSpec magnificationSpec = (MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second;
                if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    return null;
                }
                int callingPid = Binder.getCallingPid();
                Region region3 = region;
                IAccessibilityInteractionConnectionCallback replaceCallbackIfNeeded = replaceCallbackIfNeeded(iAccessibilityInteractionConnectionCallback, resolveAccessibilityWindowIdLocked, i2, callingPid, j2);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                if (intConnTracingEnabled()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(j);
                    sb.append(";");
                    sb.append(region3);
                    sb.append(";");
                    sb.append(i2);
                    sb.append(";");
                    sb.append(replaceCallbackIfNeeded);
                    sb.append(";");
                    i4 = callingPid;
                    ServiceKeeper$$ExternalSyntheticOutline0.m(this.mFetchFlags | i3, i4, ";", ";", sb);
                    sb.append(j2);
                    sb.append(";");
                    sb.append(magnificationSpec);
                    sb.append(";");
                    sb.append(Arrays.toString(fArr));
                    sb.append(";");
                    region2 = region3;
                    sb.append(bundle);
                    logTraceIntConn("findAccessibilityNodeInfoByAccessibilityId", sb.toString());
                } else {
                    i4 = callingPid;
                    region2 = region3;
                }
                try {
                    remoteAccessibilityConnection = connectionLocked;
                    try {
                        connectionLocked.mConnection.findAccessibilityNodeInfoByAccessibilityId(j, region2, i2, replaceCallbackIfNeeded, i3 | this.mFetchFlags, i4, j2, magnificationSpec, fArr, bundle);
                        String[] computeValidReportedPackages = this.mSecurityPolicy.computeValidReportedPackages(remoteAccessibilityConnection.mUid, remoteAccessibilityConnection.mPackageName);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (region2 != null && Binder.isProxy(remoteAccessibilityConnection.mConnection)) {
                            region2.recycle();
                        }
                        return computeValidReportedPackages;
                    } catch (RemoteException unused) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (region2 == null || !Binder.isProxy(remoteAccessibilityConnection.mConnection)) {
                            return null;
                        }
                        region2.recycle();
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (region2 != null && Binder.isProxy(remoteAccessibilityConnection.mConnection)) {
                            region2.recycle();
                        }
                        throw th;
                    }
                } catch (RemoteException unused2) {
                    remoteAccessibilityConnection = connectionLocked;
                } catch (Throwable th2) {
                    th = th2;
                    remoteAccessibilityConnection = connectionLocked;
                }
            } catch (Throwable th3) {
                throw th3;
            }
        }
    }

    public final String[] findAccessibilityNodeInfosByText(int i, long j, String str, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) {
        Region region;
        int i3;
        Region region2;
        AccessibilityWindowManager.RemoteAccessibilityConnection remoteAccessibilityConnection;
        if (svcConnTracingEnabled()) {
            StringBuilder m = UserspaceRebootLogger$$ExternalSyntheticOutline0.m(i, "accessibilityWindowId=", j, ";accessibilityNodeId=");
            m.append(";text=");
            m.append(str);
            m.append(";interactionId=");
            m.append(i2);
            m.append(";callback=");
            m.append(iAccessibilityInteractionConnectionCallback);
            m.append(";interrogatingTid=");
            m.append(j2);
            logTraceSvcConn("findAccessibilityNodeInfosByText", m.toString());
        }
        Region obtain = Region.obtain();
        synchronized (this.mLock) {
            try {
                this.mUsesAccessibilityCache = true;
                if (!hasRightsToCurrentUserLocked()) {
                    return null;
                }
                int resolveAccessibilityWindowIdLocked = resolveAccessibilityWindowIdLocked(i);
                if (!this.mSecurityPolicy.canGetAccessibilityNodeInfoLocked(((AccessibilityManagerService) this.mSystemSupport).mCurrentUserId, this, resolveAccessibilityWindowIdLocked)) {
                    return null;
                }
                AccessibilityWindowManager.RemoteAccessibilityConnection connectionLocked = this.mA11yWindowManager.getConnectionLocked(((AccessibilityManagerService) this.mSystemSupport).mCurrentUserId, resolveAccessibilityWindowIdLocked);
                if (connectionLocked == null) {
                    return null;
                }
                if (this.mA11yWindowManager.computePartialInteractiveRegionForWindowLocked(resolveAccessibilityWindowIdLocked, obtain)) {
                    region = obtain;
                } else {
                    obtain.recycle();
                    region = null;
                }
                Pair windowTransformationMatrixAndMagnificationSpec = ((AccessibilityManagerService) this.mSystemSupport).getWindowTransformationMatrixAndMagnificationSpec(resolveAccessibilityWindowIdLocked);
                float[] fArr = (float[]) windowTransformationMatrixAndMagnificationSpec.first;
                MagnificationSpec magnificationSpec = (MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second;
                if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    return null;
                }
                int callingPid = Binder.getCallingPid();
                Region region3 = region;
                IAccessibilityInteractionConnectionCallback replaceCallbackIfNeeded = replaceCallbackIfNeeded(iAccessibilityInteractionConnectionCallback, resolveAccessibilityWindowIdLocked, i2, callingPid, j2);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                if (intConnTracingEnabled()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(j);
                    sb.append(";");
                    sb.append(str);
                    sb.append(";");
                    sb.append(region3);
                    sb.append(";");
                    sb.append(i2);
                    sb.append(";");
                    sb.append(replaceCallbackIfNeeded);
                    sb.append(";");
                    i3 = callingPid;
                    ServiceKeeper$$ExternalSyntheticOutline0.m(this.mFetchFlags, i3, ";", ";", sb);
                    sb.append(j2);
                    sb.append(";");
                    sb.append(magnificationSpec);
                    sb.append(";");
                    sb.append(Arrays.toString(fArr));
                    logTraceIntConn("findAccessibilityNodeInfosByText", sb.toString());
                } else {
                    i3 = callingPid;
                }
                try {
                    region2 = region3;
                    remoteAccessibilityConnection = connectionLocked;
                } catch (RemoteException unused) {
                    region2 = region3;
                    remoteAccessibilityConnection = connectionLocked;
                } catch (Throwable th) {
                    th = th;
                    region2 = region3;
                    remoteAccessibilityConnection = connectionLocked;
                }
                try {
                    connectionLocked.mConnection.findAccessibilityNodeInfosByText(j, str, region3, i2, replaceCallbackIfNeeded, this.mFetchFlags, i3, j2, magnificationSpec, fArr);
                    String[] computeValidReportedPackages = this.mSecurityPolicy.computeValidReportedPackages(remoteAccessibilityConnection.mUid, remoteAccessibilityConnection.mPackageName);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 != null && Binder.isProxy(remoteAccessibilityConnection.mConnection)) {
                        region2.recycle();
                    }
                    return computeValidReportedPackages;
                } catch (RemoteException unused2) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 == null || !Binder.isProxy(remoteAccessibilityConnection.mConnection)) {
                        return null;
                    }
                    region2.recycle();
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 != null && Binder.isProxy(remoteAccessibilityConnection.mConnection)) {
                        region2.recycle();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                throw th3;
            }
        }
    }

    public final String[] findAccessibilityNodeInfosByViewId(int i, long j, String str, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) {
        Region region;
        int i3;
        Region region2;
        AccessibilityWindowManager.RemoteAccessibilityConnection remoteAccessibilityConnection;
        if (svcConnTracingEnabled()) {
            StringBuilder m = UserspaceRebootLogger$$ExternalSyntheticOutline0.m(i, "accessibilityWindowId=", j, ";accessibilityNodeId=");
            m.append(";viewIdResName=");
            m.append(str);
            m.append(";interactionId=");
            m.append(i2);
            m.append(";callback=");
            m.append(iAccessibilityInteractionConnectionCallback);
            m.append(";interrogatingTid=");
            m.append(j2);
            logTraceSvcConn("findAccessibilityNodeInfosByViewId", m.toString());
        }
        Region obtain = Region.obtain();
        synchronized (this.mLock) {
            try {
                this.mUsesAccessibilityCache = true;
                if (!hasRightsToCurrentUserLocked()) {
                    return null;
                }
                int resolveAccessibilityWindowIdLocked = resolveAccessibilityWindowIdLocked(i);
                if (!this.mSecurityPolicy.canGetAccessibilityNodeInfoLocked(((AccessibilityManagerService) this.mSystemSupport).mCurrentUserId, this, resolveAccessibilityWindowIdLocked)) {
                    return null;
                }
                AccessibilityWindowManager.RemoteAccessibilityConnection connectionLocked = this.mA11yWindowManager.getConnectionLocked(((AccessibilityManagerService) this.mSystemSupport).mCurrentUserId, resolveAccessibilityWindowIdLocked);
                if (connectionLocked == null) {
                    return null;
                }
                if (this.mA11yWindowManager.computePartialInteractiveRegionForWindowLocked(resolveAccessibilityWindowIdLocked, obtain)) {
                    region = obtain;
                } else {
                    obtain.recycle();
                    region = null;
                }
                Pair windowTransformationMatrixAndMagnificationSpec = ((AccessibilityManagerService) this.mSystemSupport).getWindowTransformationMatrixAndMagnificationSpec(resolveAccessibilityWindowIdLocked);
                float[] fArr = (float[]) windowTransformationMatrixAndMagnificationSpec.first;
                MagnificationSpec magnificationSpec = (MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second;
                if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    return null;
                }
                int callingPid = Binder.getCallingPid();
                Region region3 = region;
                IAccessibilityInteractionConnectionCallback replaceCallbackIfNeeded = replaceCallbackIfNeeded(iAccessibilityInteractionConnectionCallback, resolveAccessibilityWindowIdLocked, i2, callingPid, j2);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                if (intConnTracingEnabled()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(j);
                    sb.append(";");
                    sb.append(str);
                    sb.append(";");
                    sb.append(region3);
                    sb.append(";");
                    sb.append(i2);
                    sb.append(";");
                    sb.append(replaceCallbackIfNeeded);
                    sb.append(";");
                    i3 = callingPid;
                    ServiceKeeper$$ExternalSyntheticOutline0.m(this.mFetchFlags, i3, ";", ";", sb);
                    sb.append(j2);
                    sb.append(";");
                    sb.append(magnificationSpec);
                    sb.append(";");
                    sb.append(Arrays.toString(fArr));
                    logTraceIntConn("findAccessibilityNodeInfosByViewId", sb.toString());
                } else {
                    i3 = callingPid;
                }
                try {
                    region2 = region3;
                    remoteAccessibilityConnection = connectionLocked;
                } catch (RemoteException unused) {
                    region2 = region3;
                    remoteAccessibilityConnection = connectionLocked;
                } catch (Throwable th) {
                    th = th;
                    region2 = region3;
                    remoteAccessibilityConnection = connectionLocked;
                }
                try {
                    connectionLocked.mConnection.findAccessibilityNodeInfosByViewId(j, str, region3, i2, replaceCallbackIfNeeded, this.mFetchFlags, i3, j2, magnificationSpec, fArr);
                    String[] computeValidReportedPackages = this.mSecurityPolicy.computeValidReportedPackages(remoteAccessibilityConnection.mUid, remoteAccessibilityConnection.mPackageName);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 != null && Binder.isProxy(remoteAccessibilityConnection.mConnection)) {
                        region2.recycle();
                    }
                    return computeValidReportedPackages;
                } catch (RemoteException unused2) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 == null || !Binder.isProxy(remoteAccessibilityConnection.mConnection)) {
                        return null;
                    }
                    region2.recycle();
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 != null && Binder.isProxy(remoteAccessibilityConnection.mConnection)) {
                        region2.recycle();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                throw th3;
            }
        }
    }

    public final String[] findFocus(int i, long j, int i2, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) {
        Region region;
        int i4;
        Region region2;
        AccessibilityWindowManager.RemoteAccessibilityConnection remoteAccessibilityConnection;
        if (svcConnTracingEnabled()) {
            StringBuilder m = UserspaceRebootLogger$$ExternalSyntheticOutline0.m(i, "accessibilityWindowId=", j, ";accessibilityNodeId=");
            AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(i2, i3, ";focusType=", ";interactionId=", m);
            m.append(";callback=");
            m.append(iAccessibilityInteractionConnectionCallback);
            m.append(";interrogatingTid=");
            m.append(j2);
            logTraceSvcConn("findFocus", m.toString());
        }
        Region obtain = Region.obtain();
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return null;
                }
                int resolveAccessibilityWindowIdForFindFocusLocked = resolveAccessibilityWindowIdForFindFocusLocked(i, i2);
                if (!this.mSecurityPolicy.canGetAccessibilityNodeInfoLocked(((AccessibilityManagerService) this.mSystemSupport).mCurrentUserId, this, resolveAccessibilityWindowIdForFindFocusLocked)) {
                    return null;
                }
                AccessibilityWindowManager.RemoteAccessibilityConnection connectionLocked = this.mA11yWindowManager.getConnectionLocked(((AccessibilityManagerService) this.mSystemSupport).mCurrentUserId, resolveAccessibilityWindowIdForFindFocusLocked);
                if (connectionLocked == null) {
                    return null;
                }
                if (this.mA11yWindowManager.computePartialInteractiveRegionForWindowLocked(resolveAccessibilityWindowIdForFindFocusLocked, obtain)) {
                    region = obtain;
                } else {
                    obtain.recycle();
                    region = null;
                }
                Pair windowTransformationMatrixAndMagnificationSpec = ((AccessibilityManagerService) this.mSystemSupport).getWindowTransformationMatrixAndMagnificationSpec(resolveAccessibilityWindowIdForFindFocusLocked);
                float[] fArr = (float[]) windowTransformationMatrixAndMagnificationSpec.first;
                MagnificationSpec magnificationSpec = (MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second;
                if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    return null;
                }
                int callingPid = Binder.getCallingPid();
                Region region3 = region;
                IAccessibilityInteractionConnectionCallback replaceCallbackIfNeeded = replaceCallbackIfNeeded(iAccessibilityInteractionConnectionCallback, resolveAccessibilityWindowIdForFindFocusLocked, i3, callingPid, j2);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                if (intConnTracingEnabled()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(j);
                    sb.append(";");
                    sb.append(i2);
                    sb.append(";");
                    sb.append(region3);
                    sb.append(";");
                    sb.append(i3);
                    sb.append(";");
                    sb.append(replaceCallbackIfNeeded);
                    sb.append(";");
                    i4 = callingPid;
                    ServiceKeeper$$ExternalSyntheticOutline0.m(this.mFetchFlags, i4, ";", ";", sb);
                    sb.append(j2);
                    sb.append(";");
                    sb.append(magnificationSpec);
                    sb.append(";");
                    sb.append(Arrays.toString(fArr));
                    logTraceIntConn("findFocus", sb.toString());
                } else {
                    i4 = callingPid;
                }
                try {
                    region2 = region3;
                    remoteAccessibilityConnection = connectionLocked;
                    try {
                        connectionLocked.mConnection.findFocus(j, i2, region3, i3, replaceCallbackIfNeeded, this.mFetchFlags, i4, j2, magnificationSpec, fArr);
                        String[] computeValidReportedPackages = this.mSecurityPolicy.computeValidReportedPackages(remoteAccessibilityConnection.mUid, remoteAccessibilityConnection.mPackageName);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (region2 != null && Binder.isProxy(remoteAccessibilityConnection.mConnection)) {
                            region2.recycle();
                        }
                        return computeValidReportedPackages;
                    } catch (RemoteException unused) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (region2 == null || !Binder.isProxy(remoteAccessibilityConnection.mConnection)) {
                            return null;
                        }
                        region2.recycle();
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (region2 != null && Binder.isProxy(remoteAccessibilityConnection.mConnection)) {
                            region2.recycle();
                        }
                        throw th;
                    }
                } catch (RemoteException unused2) {
                    region2 = region3;
                    remoteAccessibilityConnection = connectionLocked;
                } catch (Throwable th2) {
                    th = th2;
                    region2 = region3;
                    remoteAccessibilityConnection = connectionLocked;
                }
            } catch (Throwable th3) {
                throw th3;
            }
        }
    }

    public final String[] focusSearch(int i, long j, int i2, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) {
        Region region;
        int i4;
        Region region2;
        AccessibilityWindowManager.RemoteAccessibilityConnection remoteAccessibilityConnection;
        if (svcConnTracingEnabled()) {
            StringBuilder m = UserspaceRebootLogger$$ExternalSyntheticOutline0.m(i, "accessibilityWindowId=", j, ";accessibilityNodeId=");
            AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(i2, i3, ";direction=", ";interactionId=", m);
            m.append(";callback=");
            m.append(iAccessibilityInteractionConnectionCallback);
            m.append(";interrogatingTid=");
            m.append(j2);
            logTraceSvcConn("focusSearch", m.toString());
        }
        Region obtain = Region.obtain();
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return null;
                }
                int resolveAccessibilityWindowIdLocked = resolveAccessibilityWindowIdLocked(i);
                if (!this.mSecurityPolicy.canGetAccessibilityNodeInfoLocked(((AccessibilityManagerService) this.mSystemSupport).mCurrentUserId, this, resolveAccessibilityWindowIdLocked)) {
                    return null;
                }
                AccessibilityWindowManager.RemoteAccessibilityConnection connectionLocked = this.mA11yWindowManager.getConnectionLocked(((AccessibilityManagerService) this.mSystemSupport).mCurrentUserId, resolveAccessibilityWindowIdLocked);
                if (connectionLocked == null) {
                    return null;
                }
                if (this.mA11yWindowManager.computePartialInteractiveRegionForWindowLocked(resolveAccessibilityWindowIdLocked, obtain)) {
                    region = obtain;
                } else {
                    obtain.recycle();
                    region = null;
                }
                Pair windowTransformationMatrixAndMagnificationSpec = ((AccessibilityManagerService) this.mSystemSupport).getWindowTransformationMatrixAndMagnificationSpec(resolveAccessibilityWindowIdLocked);
                float[] fArr = (float[]) windowTransformationMatrixAndMagnificationSpec.first;
                MagnificationSpec magnificationSpec = (MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second;
                if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    return null;
                }
                int callingPid = Binder.getCallingPid();
                Region region3 = region;
                IAccessibilityInteractionConnectionCallback replaceCallbackIfNeeded = replaceCallbackIfNeeded(iAccessibilityInteractionConnectionCallback, resolveAccessibilityWindowIdLocked, i3, callingPid, j2);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                if (intConnTracingEnabled()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(j);
                    sb.append(";");
                    sb.append(i2);
                    sb.append(";");
                    sb.append(region3);
                    sb.append(";");
                    sb.append(i3);
                    sb.append(";");
                    sb.append(replaceCallbackIfNeeded);
                    sb.append(";");
                    i4 = callingPid;
                    ServiceKeeper$$ExternalSyntheticOutline0.m(this.mFetchFlags, i4, ";", ";", sb);
                    sb.append(j2);
                    sb.append(";");
                    sb.append(magnificationSpec);
                    sb.append(";");
                    sb.append(Arrays.toString(fArr));
                    logTraceIntConn("focusSearch", sb.toString());
                } else {
                    i4 = callingPid;
                }
                try {
                    region2 = region3;
                    remoteAccessibilityConnection = connectionLocked;
                    try {
                        connectionLocked.mConnection.focusSearch(j, i2, region3, i3, replaceCallbackIfNeeded, this.mFetchFlags, i4, j2, magnificationSpec, fArr);
                        String[] computeValidReportedPackages = this.mSecurityPolicy.computeValidReportedPackages(remoteAccessibilityConnection.mUid, remoteAccessibilityConnection.mPackageName);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (region2 != null && Binder.isProxy(remoteAccessibilityConnection.mConnection)) {
                            region2.recycle();
                        }
                        return computeValidReportedPackages;
                    } catch (RemoteException unused) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (region2 == null || !Binder.isProxy(remoteAccessibilityConnection.mConnection)) {
                            return null;
                        }
                        region2.recycle();
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (region2 != null && Binder.isProxy(remoteAccessibilityConnection.mConnection)) {
                            region2.recycle();
                        }
                        throw th;
                    }
                } catch (RemoteException unused2) {
                    region2 = region3;
                    remoteAccessibilityConnection = connectionLocked;
                } catch (Throwable th2) {
                    th = th2;
                    region2 = region3;
                    remoteAccessibilityConnection = connectionLocked;
                }
            } catch (Throwable th3) {
                throw th3;
            }
        }
    }

    public final int getCapabilities() {
        AccessibilityServiceInfo accessibilityServiceInfo = this.mAccessibilityServiceInfo;
        if (accessibilityServiceInfo == null) {
            return 0;
        }
        return accessibilityServiceInfo.getCapabilities();
    }

    public Region getCurrentMagnificationRegion(int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getCurrentMagnificationRegion", "displayId=" + i);
        }
        synchronized (this.mLock) {
            try {
                Region obtain = Region.obtain();
                if (!hasRightsToCurrentUserLocked()) {
                    return obtain;
                }
                MagnificationProcessor magnificationProcessor = ((AccessibilityManagerService) this.mSystemSupport).mMagnificationProcessor;
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mSecurityPolicy.getClass();
                    magnificationProcessor.getCurrentMagnificationRegion(i, obtain, AccessibilitySecurityPolicy.canControlMagnification(this));
                    return obtain;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public List getInstalledAndEnabledServices() {
        return null;
    }

    public float getMagnificationCenterX(int i) {
        boolean z;
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getMagnificationCenterX", "displayId=" + i);
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return FullScreenMagnificationGestureHandler.MAX_SCALE;
                }
                MagnificationProcessor magnificationProcessor = ((AccessibilityManagerService) this.mSystemSupport).mMagnificationProcessor;
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mSecurityPolicy.getClass();
                    boolean canControlMagnification = AccessibilitySecurityPolicy.canControlMagnification(this);
                    MagnificationController magnificationController = magnificationProcessor.mController;
                    if (magnificationController.getFullScreenMagnificationController().isRegistered(i) || !canControlMagnification) {
                        z = false;
                    } else {
                        magnificationController.getFullScreenMagnificationController().register(i);
                        z = true;
                    }
                    try {
                        return magnificationProcessor.mController.getFullScreenMagnificationController().getCenterX(i);
                    } finally {
                        if (z) {
                            magnificationProcessor.unregister(i);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public float getMagnificationCenterY(int i) {
        boolean z;
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getMagnificationCenterY", "displayId=" + i);
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return FullScreenMagnificationGestureHandler.MAX_SCALE;
                }
                MagnificationProcessor magnificationProcessor = ((AccessibilityManagerService) this.mSystemSupport).mMagnificationProcessor;
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mSecurityPolicy.getClass();
                    boolean canControlMagnification = AccessibilitySecurityPolicy.canControlMagnification(this);
                    MagnificationController magnificationController = magnificationProcessor.mController;
                    if (magnificationController.getFullScreenMagnificationController().isRegistered(i) || !canControlMagnification) {
                        z = false;
                    } else {
                        magnificationController.getFullScreenMagnificationController().register(i);
                        z = true;
                    }
                    try {
                        return magnificationProcessor.mController.getFullScreenMagnificationController().getCenterY(i);
                    } finally {
                        if (z) {
                            magnificationProcessor.unregister(i);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public MagnificationConfig getMagnificationConfig(int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getMagnificationConfig", "displayId=" + i);
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return null;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return ((AccessibilityManagerService) this.mSystemSupport).mMagnificationProcessor.getMagnificationConfig(i);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public Region getMagnificationRegion(int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getMagnificationRegion", "displayId=" + i);
        }
        synchronized (this.mLock) {
            try {
                Region obtain = Region.obtain();
                if (!hasRightsToCurrentUserLocked()) {
                    return obtain;
                }
                MagnificationProcessor magnificationProcessor = ((AccessibilityManagerService) this.mSystemSupport).mMagnificationProcessor;
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mSecurityPolicy.getClass();
                    magnificationProcessor.getFullscreenMagnificationRegion(i, obtain, AccessibilitySecurityPolicy.canControlMagnification(this));
                    return obtain;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public float getMagnificationScale(int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getMagnificationScale", "displayId=" + i);
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return 1.0f;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return ((AccessibilityManagerService) this.mSystemSupport).mMagnificationProcessor.mController.getFullScreenMagnificationController().getScale(i);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final IBinder getOverlayWindowToken(int i) {
        IBinder iBinder;
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getOverlayWindowToken", "displayId=" + i);
        }
        synchronized (this.mLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    iBinder = (IBinder) this.mOverlayWindowTokens.get(i);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return iBinder;
    }

    public AccessibilityServiceInfo getServiceInfo() {
        AccessibilityServiceInfo accessibilityServiceInfo;
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getServiceInfo", "");
        }
        synchronized (this.mLock) {
            accessibilityServiceInfo = this.mAccessibilityServiceInfo;
        }
        return accessibilityServiceInfo;
    }

    public final IAccessibilityServiceClient getServiceInterfaceSafely() {
        IAccessibilityServiceClient iAccessibilityServiceClient;
        synchronized (this.mLock) {
            iAccessibilityServiceClient = this.mServiceInterface;
        }
        return iAccessibilityServiceClient;
    }

    public List getSystemActions() {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getSystemActions", "");
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return Collections.emptyList();
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return this.mSystemActionPerformer.getSystemActions();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final AccessibilityWindowInfo getWindow(int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getWindow", "windowId=" + i);
        }
        int displayIdByUserIdAndWindowId = i != -1 ? this.mA11yWindowManager.getDisplayIdByUserIdAndWindowId(((AccessibilityManagerService) this.mSystemSupport).mCurrentUserId, i) : -1;
        synchronized (this.mLock) {
            try {
                ensureWindowsAvailableTimedLocked(displayIdByUserIdAndWindowId);
                if (!hasRightsToCurrentUserLocked()) {
                    return null;
                }
                this.mSecurityPolicy.getClass();
                if ((getCapabilities() & 1) == 0 || !this.mRetrieveInteractiveWindows) {
                    return null;
                }
                if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    return null;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AccessibilityWindowInfo findA11yWindowInfoByIdLocked = this.mA11yWindowManager.findA11yWindowInfoByIdLocked(i);
                    if (findA11yWindowInfoByIdLocked == null) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return null;
                    }
                    AccessibilityWindowInfo obtain = AccessibilityWindowInfo.obtain(findA11yWindowInfoByIdLocked);
                    obtain.setConnectionId(this.mId);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return obtain;
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final int getWindowIdForLeashToken(IBinder iBinder) {
        int indexOfValue;
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getWindowIdForLeashToken", "token=" + iBinder);
        }
        synchronized (this.mLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AccessibilityWindowManager accessibilityWindowManager = this.mA11yWindowManager;
                    indexOfValue = accessibilityWindowManager.mWindowIdMap.indexOfValue(iBinder);
                    if (indexOfValue != -1) {
                        indexOfValue = accessibilityWindowManager.mWindowIdMap.keyAt(indexOfValue);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return indexOfValue;
    }

    public AccessibilityWindowInfo.WindowListSparseArray getWindows() {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getWindows", "");
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return null;
                }
                this.mSecurityPolicy.getClass();
                if ((getCapabilities() & 1) == 0 || !this.mRetrieveInteractiveWindows) {
                    return null;
                }
                if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    return null;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AccessibilityWindowInfo.WindowListSparseArray windowListSparseArray = new AccessibilityWindowInfo.WindowListSparseArray();
                    AccessibilityWindowManager accessibilityWindowManager = this.mA11yWindowManager;
                    int i = this.mDisplayTypes;
                    accessibilityWindowManager.getClass();
                    ArrayList arrayList = new ArrayList();
                    int size = accessibilityWindowManager.mDisplayWindowsObservers.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver = (AccessibilityWindowManager.DisplayWindowsObserver) accessibilityWindowManager.mDisplayWindowsObservers.valueAt(i2);
                        if (displayWindowsObserver != null) {
                            boolean z = displayWindowsObserver.mIsProxy;
                            int i3 = displayWindowsObserver.mDisplayId;
                            if (!z && (i & 1) != 0) {
                                arrayList.add(Integer.valueOf(i3));
                            } else if (z && (i & 2) != 0) {
                                arrayList.add(Integer.valueOf(i3));
                            }
                        }
                    }
                    int size2 = arrayList.size();
                    if (size2 > 0) {
                        for (int i4 = 0; i4 < size2; i4++) {
                            int intValue = ((Integer) arrayList.get(i4)).intValue();
                            ensureWindowsAvailableTimedLocked(intValue);
                            List windowsByDisplayLocked = getWindowsByDisplayLocked(intValue);
                            if (windowsByDisplayLocked != null) {
                                windowListSparseArray.put(intValue, windowsByDisplayLocked);
                            }
                        }
                    }
                    return windowListSparseArray;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List getWindowsByDisplayLocked(int i) {
        AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver = (AccessibilityWindowManager.DisplayWindowsObserver) this.mA11yWindowManager.mDisplayWindowsObservers.get(i);
        List list = displayWindowsObserver != null ? displayWindowsObserver.mWindows : null;
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            AccessibilityWindowInfo obtain = AccessibilityWindowInfo.obtain((AccessibilityWindowInfo) list.get(i2));
            obtain.setConnectionId(this.mId);
            arrayList.add(obtain);
        }
        return arrayList;
    }

    public final List getWindowsMainDisplay(int i) {
        return getWindowsByDisplayLocked(i);
    }

    public abstract boolean hasRightsToCurrentUserLocked();

    public final boolean intConnTracingEnabled() {
        return this.mTrace.isA11yTracingEnabledForTypes(16L);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isFingerprintGestureDetectionAvailable() {
        /*
            r7 = this;
            boolean r0 = r7.svcConnTracingEnabled()
            if (r0 == 0) goto Le
            java.lang.String r0 = "isFingerprintGestureDetectionAvailable"
            java.lang.String r1 = ""
            r7.logTraceSvcConn(r0, r1)
        Le:
            android.content.Context r0 = r7.mContext
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            java.lang.String r1 = "android.hardware.fingerprint"
            boolean r0 = r0.hasSystemFeature(r1)
            r1 = 0
            if (r0 != 0) goto L1e
            return r1
        L1e:
            long r2 = android.os.Binder.clearCallingIdentity()
            boolean r0 = r7.isCapturingFingerprintGestures()     // Catch: java.lang.Throwable -> L56
            if (r0 == 0) goto L58
            com.android.server.accessibility.AbstractAccessibilityServiceConnection$SystemSupport r7 = r7.mSystemSupport     // Catch: java.lang.Throwable -> L56
            com.android.server.accessibility.AccessibilityManagerService r7 = (com.android.server.accessibility.AccessibilityManagerService) r7     // Catch: java.lang.Throwable -> L56
            com.android.server.accessibility.FingerprintGestureDispatcher r7 = r7.mFingerprintGestureDispatcher     // Catch: java.lang.Throwable -> L56
            if (r7 == 0) goto L52
            boolean r0 = r7.mHardwareSupportsGestures     // Catch: java.lang.Throwable -> L56
            r4 = 1
            if (r0 != 0) goto L37
        L35:
            r7 = r1
            goto L4f
        L37:
            long r5 = android.os.Binder.clearCallingIdentity()     // Catch: java.lang.Throwable -> L56
            android.hardware.fingerprint.IFingerprintService r7 = r7.mFingerprintService     // Catch: java.lang.Throwable -> L46 android.os.RemoteException -> L4b
            boolean r7 = r7.isClientActive()     // Catch: java.lang.Throwable -> L46 android.os.RemoteException -> L4b
            r7 = r7 ^ r4
            android.os.Binder.restoreCallingIdentity(r5)     // Catch: java.lang.Throwable -> L56
            goto L4f
        L46:
            r7 = move-exception
            android.os.Binder.restoreCallingIdentity(r5)     // Catch: java.lang.Throwable -> L56
            throw r7     // Catch: java.lang.Throwable -> L56
        L4b:
            android.os.Binder.restoreCallingIdentity(r5)     // Catch: java.lang.Throwable -> L56
            goto L35
        L4f:
            if (r7 == 0) goto L52
            r1 = r4
        L52:
            android.os.Binder.restoreCallingIdentity(r2)
            return r1
        L56:
            r7 = move-exception
            goto L5c
        L58:
            android.os.Binder.restoreCallingIdentity(r2)
            return r1
        L5c:
            android.os.Binder.restoreCallingIdentity(r2)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AbstractAccessibilityServiceConnection.isFingerprintGestureDetectionAvailable():boolean");
    }

    public boolean isMagnificationCallbackEnabled(int i) {
        boolean z;
        InvocationHandler invocationHandler = this.mInvocationHandler;
        synchronized (AbstractAccessibilityServiceConnection.this.mLock) {
            z = invocationHandler.mMagnificationCallbackState.get(i) != null;
        }
        return z;
    }

    public final boolean isServiceDetectsGesturesEnabled(int i) {
        if (this.mServiceDetectsGestures.contains(i)) {
            return ((Boolean) this.mServiceDetectsGestures.get(i)).booleanValue();
        }
        return false;
    }

    public final void logTrace(long j, String str, long j2, String str2, int i, long j3, int i2, Bundle bundle) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mTrace.isA11yTracingEnabledForTypes(j2)) {
                ArrayList arrayList = (ArrayList) bundle.getSerializable("call_stack", ArrayList.class);
                this.mTrace.logTrace(j, str, j2, str2, i, j3, i2, (StackTraceElement[]) arrayList.toArray(new StackTraceElement[arrayList.size()]), (HashSet) bundle.getSerializable("ignore_call_stack", HashSet.class));
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void logTraceIntConn(String str, String str2) {
        this.mTrace.logTrace("AbstractAccessibilityServiceConnection.".concat(str), 16L, str2);
    }

    public final void logTraceSvcClient(String str, String str2) {
        this.mTrace.logTrace("AbstractAccessibilityServiceConnection.IAccessibilityServiceClient.".concat(str), 2L, str2);
    }

    public final void logTraceSvcConn(String str, String str2) {
        this.mTrace.logTrace("AbstractAccessibilityServiceConnection.IAccessibilityServiceConnection.".concat(str), 1L, str2);
    }

    public final void logTraceWM(String str, String str2) {
        this.mTrace.logTrace("WindowManagerInternal.".concat(str), 512L, str2);
    }

    public final void notifyAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Message obtainMessage;
        synchronized (this.mLock) {
            try {
                int eventType = accessibilityEvent.getEventType();
                boolean wantsEventLocked = wantsEventLocked(accessibilityEvent);
                boolean z = this.mUsesAccessibilityCache && (4307005 & eventType) != 0;
                if (wantsEventLocked || z) {
                    if (this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                        AccessibilityEvent obtain = AccessibilityEvent.obtain(accessibilityEvent);
                        if (this.mNotificationTimeout <= 0 || eventType == 2048) {
                            obtainMessage = obtainMessage(eventType, obtain);
                        } else {
                            AccessibilityEvent accessibilityEvent2 = (AccessibilityEvent) this.mPendingEvents.get(eventType);
                            this.mPendingEvents.put(eventType, obtain);
                            if (accessibilityEvent2 != null) {
                                removeMessages(eventType);
                                accessibilityEvent2.recycle();
                            }
                            obtainMessage = obtainMessage(eventType);
                        }
                        obtainMessage.arg1 = wantsEventLocked ? 1 : 0;
                        sendMessageDelayed(obtainMessage, this.mNotificationTimeout);
                    }
                }
            } finally {
            }
        }
    }

    public final void onDisplayRemoved(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (this.mTrace.isA11yTracingEnabledForTypes(512L)) {
            logTraceWM("addWindowToken", this.mOverlayWindowTokens.get(i) + ";true;" + i);
        }
        try {
            this.mWindowManagerService.removeWindowToken((IBinder) this.mOverlayWindowTokens.get(i), true, i);
            synchronized (this.mLock) {
                this.mOverlayWindowTokens.remove(i);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onDoubleTap(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) this.mSystemSupport;
            accessibilityManagerService.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda9(5), accessibilityManagerService, Integer.valueOf(i)));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onDoubleTapAndHold(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) this.mSystemSupport;
            accessibilityManagerService.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda9(1), accessibilityManagerService, Integer.valueOf(i)));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.accessibility.KeyEventDispatcher.KeyEventFilter
    public boolean onKeyEvent(KeyEvent keyEvent, int i) {
        if (!this.mRequestFilterKeyEvents || this.mServiceInterface == null || (this.mAccessibilityServiceInfo.getCapabilities() & 8) == 0 || !this.mSecurityPolicy.checkAccessibilityAccess(this)) {
            return false;
        }
        try {
            if (svcClientTracingEnabled()) {
                logTraceSvcClient("onKeyEvent", keyEvent + ", " + i);
            }
            this.mServiceInterface.onKeyEvent(keyEvent, i);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public final void onRemoved() {
        int i = 0;
        for (Display display : this.mDisplayManager.getDisplays()) {
            onDisplayRemoved(display.getDisplayId());
        }
        if (A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP) {
            Display[] displays = this.mDisplayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
            int length = displays.length;
            while (true) {
                if (i >= length) {
                    break;
                }
                Display display2 = displays[i];
                if (display2.getDisplayId() == 1) {
                    onDisplayRemoved(display2.getDisplayId());
                    break;
                }
                i++;
            }
        }
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        Iterator it = ((ArrayList) this.mOverlays).iterator();
        while (it.hasNext()) {
            SurfaceControl surfaceControl = (SurfaceControl) it.next();
            if (surfaceControl.isValid()) {
                transaction.reparent(surfaceControl, null);
            }
        }
        transaction.apply();
        transaction.close();
        ((ArrayList) this.mOverlays).clear();
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00aa A[Catch: all -> 0x0096, TryCatch #1 {all -> 0x0096, blocks: (B:23:0x008b, B:25:0x0093, B:35:0x00aa, B:37:0x00b5, B:39:0x00bd, B:41:0x00c3, B:45:0x00ce), top: B:22:0x008b }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00f5 A[Catch: all -> 0x00fb, RemoteException -> 0x0178, TryCatch #2 {RemoteException -> 0x0178, blocks: (B:51:0x00e3, B:56:0x00f5, B:57:0x00fe, B:59:0x0104, B:61:0x0158, B:66:0x00ee), top: B:50:0x00e3 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0104 A[Catch: all -> 0x00fb, RemoteException -> 0x0178, TryCatch #2 {RemoteException -> 0x0178, blocks: (B:51:0x00e3, B:56:0x00f5, B:57:0x00fe, B:59:0x0104, B:61:0x0158, B:66:0x00ee), top: B:50:0x00e3 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0155 A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00b3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean performAccessibilityAction(int r21, long r22, int r24, android.os.Bundle r25, int r26, android.view.accessibility.IAccessibilityInteractionConnectionCallback r27, long r28) {
        /*
            Method dump skipped, instructions count: 385
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AbstractAccessibilityServiceConnection.performAccessibilityAction(int, long, int, android.os.Bundle, int, android.view.accessibility.IAccessibilityInteractionConnectionCallback, long):boolean");
    }

    public boolean performGlobalAction(int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("performGlobalAction", "action=" + i);
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return false;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return this.mSystemActionPerformer.performSystemAction(i);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final IAccessibilityInteractionConnectionCallback replaceCallbackIfNeeded(IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i, int i2, int i3, long j) {
        AccessibilityWindowManager.RemoteAccessibilityConnection remoteAccessibilityConnection = this.mA11yWindowManager.mPictureInPictureActionReplacingConnection;
        synchronized (this.mLock) {
            AccessibilityWindowInfo findA11yWindowInfoByIdLocked = this.mA11yWindowManager.findA11yWindowInfoByIdLocked(i);
            if (findA11yWindowInfoByIdLocked != null && findA11yWindowInfoByIdLocked.isInPictureInPictureMode() && remoteAccessibilityConnection != null) {
                return new ActionReplacingCallback(iAccessibilityInteractionConnectionCallback, remoteAccessibilityConnection.mConnection, i2, i3, j);
            }
            return iAccessibilityInteractionConnectionCallback;
        }
    }

    public void requestDelegating(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) this.mSystemSupport;
            accessibilityManagerService.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda9(7), accessibilityManagerService, Integer.valueOf(i)));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void requestDragging(int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) this.mSystemSupport;
            accessibilityManagerService.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda12(3), accessibilityManagerService, Integer.valueOf(i), Integer.valueOf(i2)));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void requestTouchExploration(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) this.mSystemSupport;
            accessibilityManagerService.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda9(2), accessibilityManagerService, Integer.valueOf(i)));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0080, code lost:
    
        if ((r9 == 1 ? r7.getFullScreenMagnificationController().isActivated(r8) : r9 == 2 ? r7.getMagnificationConnectionManager().isWindowMagnifierEnabled(r8) : false) == false) goto L38;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean resetCurrentMagnification(int r8, boolean r9) {
        /*
            r7 = this;
            boolean r0 = r7.svcConnTracingEnabled()
            if (r0 == 0) goto L15
            java.lang.String r0 = "resetCurrentMagnification"
            java.lang.String r1 = "displayId="
            java.lang.String r2 = ";animate="
            java.lang.String r1 = com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(r8, r1, r2, r9)
            r7.logTraceSvcConn(r0, r1)
        L15:
            java.lang.Object r0 = r7.mLock
            monitor-enter(r0)
            boolean r1 = r7.hasRightsToCurrentUserLocked()     // Catch: java.lang.Throwable -> L21
            r2 = 0
            if (r1 != 0) goto L24
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L21
            return r2
        L21:
            r7 = move-exception
            goto L8c
        L24:
            com.android.server.accessibility.AccessibilitySecurityPolicy r1 = r7.mSecurityPolicy     // Catch: java.lang.Throwable -> L21
            r1.getClass()     // Catch: java.lang.Throwable -> L21
            boolean r1 = com.android.server.accessibility.AccessibilitySecurityPolicy.canControlMagnification(r7)     // Catch: java.lang.Throwable -> L21
            if (r1 != 0) goto L31
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L21
            return r2
        L31:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L21
            long r0 = android.os.Binder.clearCallingIdentity()
            com.android.server.accessibility.AbstractAccessibilityServiceConnection$SystemSupport r7 = r7.mSystemSupport     // Catch: java.lang.Throwable -> L87
            com.android.server.accessibility.AccessibilityManagerService r7 = (com.android.server.accessibility.AccessibilityManagerService) r7     // Catch: java.lang.Throwable -> L87
            com.android.server.accessibility.magnification.MagnificationProcessor r7 = r7.mMagnificationProcessor     // Catch: java.lang.Throwable -> L87
            int r3 = r7.getControllingMode(r8)     // Catch: java.lang.Throwable -> L87
            com.android.server.accessibility.magnification.MagnificationController r4 = r7.mController     // Catch: java.lang.Throwable -> L87
            r5 = 2
            r6 = 1
            if (r3 != r6) goto L4f
            com.android.server.accessibility.magnification.FullScreenMagnificationController r3 = r4.getFullScreenMagnificationController()     // Catch: java.lang.Throwable -> L87
            boolean r9 = r3.reset(r8, r9)     // Catch: java.lang.Throwable -> L87
            goto L61
        L4f:
            if (r3 != r5) goto L60
            com.android.server.accessibility.magnification.MagnificationConnectionManager r3 = r4.getMagnificationConnectionManager()     // Catch: java.lang.Throwable -> L87
            if (r9 == 0) goto L5a
            android.view.accessibility.MagnificationAnimationCallback r9 = android.view.accessibility.MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK     // Catch: java.lang.Throwable -> L87
            goto L5b
        L5a:
            r9 = 0
        L5b:
            boolean r9 = r3.disableWindowMagnification(r8, r2, r9)     // Catch: java.lang.Throwable -> L87
            goto L61
        L60:
            r9 = r2
        L61:
            if (r9 != 0) goto L82
            int r9 = r7.getControllingMode(r8)     // Catch: java.lang.Throwable -> L87
            com.android.server.accessibility.magnification.MagnificationController r7 = r7.mController     // Catch: java.lang.Throwable -> L87
            if (r9 != r6) goto L74
            com.android.server.accessibility.magnification.FullScreenMagnificationController r7 = r7.getFullScreenMagnificationController()     // Catch: java.lang.Throwable -> L87
            boolean r7 = r7.isActivated(r8)     // Catch: java.lang.Throwable -> L87
            goto L80
        L74:
            if (r9 != r5) goto L7f
            com.android.server.accessibility.magnification.MagnificationConnectionManager r7 = r7.getMagnificationConnectionManager()     // Catch: java.lang.Throwable -> L87
            boolean r7 = r7.isWindowMagnifierEnabled(r8)     // Catch: java.lang.Throwable -> L87
            goto L80
        L7f:
            r7 = r2
        L80:
            if (r7 != 0) goto L83
        L82:
            r2 = r6
        L83:
            android.os.Binder.restoreCallingIdentity(r0)
            return r2
        L87:
            r7 = move-exception
            android.os.Binder.restoreCallingIdentity(r0)
            throw r7
        L8c:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L21
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AbstractAccessibilityServiceConnection.resetCurrentMagnification(int, boolean):boolean");
    }

    public void resetLocked() {
        this.mAccessibilityServiceInfo.resetDynamicallyConfigurableProperties();
        KeyEventDispatcher keyEventDispatcher = ((AccessibilityManagerService) this.mSystemSupport).getKeyEventDispatcher();
        synchronized (keyEventDispatcher.mLock) {
            try {
                List list = (List) ((ArrayMap) keyEventDispatcher.mPendingEventsMap).get(this);
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        keyEventDispatcher.removeReferenceToPendingEventLocked((KeyEventDispatcher.PendingKeyEvent) list.get(i));
                    }
                    ((ArrayMap) keyEventDispatcher.mPendingEventsMap).remove(this);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        try {
            if (this.mServiceInterface != null) {
                if (svcClientTracingEnabled()) {
                    logTraceSvcClient("init", "null, " + this.mId + ", null");
                }
                this.mServiceInterface.init((IAccessibilityServiceConnection) null, this.mId, (IBinder) null);
            }
        } catch (RemoteException unused) {
        }
        IBinder iBinder = this.mService;
        if (iBinder != null) {
            try {
                iBinder.unlinkToDeath(this, 0);
            } catch (NoSuchElementException unused2) {
                Slog.e("AbstractAccessibilityServiceConnection", "Failed unregistering death link");
            }
            this.mService = null;
        }
        this.mServiceInterface = null;
        this.mReceivedAccessibilityButtonCallbackSinceBind = false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0066, code lost:
    
        if ((r7 == 1 ? r5.getFullScreenMagnificationController().isActivated(r6) : r7 == 2 ? r5.getMagnificationConnectionManager().isWindowMagnifierEnabled(r6) : false) == false) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean resetMagnification(int r6, boolean r7) {
        /*
            r5 = this;
            boolean r0 = r5.svcConnTracingEnabled()
            if (r0 == 0) goto L15
            java.lang.String r0 = "resetMagnification"
            java.lang.String r1 = "displayId="
            java.lang.String r2 = ";animate="
            java.lang.String r1 = com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(r6, r1, r2, r7)
            r5.logTraceSvcConn(r0, r1)
        L15:
            java.lang.Object r0 = r5.mLock
            monitor-enter(r0)
            boolean r1 = r5.hasRightsToCurrentUserLocked()     // Catch: java.lang.Throwable -> L21
            r2 = 0
            if (r1 != 0) goto L23
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L21
            return r2
        L21:
            r5 = move-exception
            goto L72
        L23:
            com.android.server.accessibility.AccessibilitySecurityPolicy r1 = r5.mSecurityPolicy     // Catch: java.lang.Throwable -> L21
            r1.getClass()     // Catch: java.lang.Throwable -> L21
            boolean r1 = com.android.server.accessibility.AccessibilitySecurityPolicy.canControlMagnification(r5)     // Catch: java.lang.Throwable -> L21
            if (r1 != 0) goto L30
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L21
            return r2
        L30:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L21
            long r0 = android.os.Binder.clearCallingIdentity()
            com.android.server.accessibility.AbstractAccessibilityServiceConnection$SystemSupport r5 = r5.mSystemSupport     // Catch: java.lang.Throwable -> L6d
            com.android.server.accessibility.AccessibilityManagerService r5 = (com.android.server.accessibility.AccessibilityManagerService) r5     // Catch: java.lang.Throwable -> L6d
            com.android.server.accessibility.magnification.MagnificationProcessor r5 = r5.mMagnificationProcessor     // Catch: java.lang.Throwable -> L6d
            com.android.server.accessibility.magnification.MagnificationController r3 = r5.mController     // Catch: java.lang.Throwable -> L6d
            com.android.server.accessibility.magnification.FullScreenMagnificationController r3 = r3.getFullScreenMagnificationController()     // Catch: java.lang.Throwable -> L6d
            boolean r7 = r3.reset(r6, r7)     // Catch: java.lang.Throwable -> L6d
            r3 = 1
            if (r7 != 0) goto L68
            int r7 = r5.getControllingMode(r6)     // Catch: java.lang.Throwable -> L6d
            com.android.server.accessibility.magnification.MagnificationController r5 = r5.mController     // Catch: java.lang.Throwable -> L6d
            if (r7 != r3) goto L59
            com.android.server.accessibility.magnification.FullScreenMagnificationController r5 = r5.getFullScreenMagnificationController()     // Catch: java.lang.Throwable -> L6d
            boolean r5 = r5.isActivated(r6)     // Catch: java.lang.Throwable -> L6d
            goto L66
        L59:
            r4 = 2
            if (r7 != r4) goto L65
            com.android.server.accessibility.magnification.MagnificationConnectionManager r5 = r5.getMagnificationConnectionManager()     // Catch: java.lang.Throwable -> L6d
            boolean r5 = r5.isWindowMagnifierEnabled(r6)     // Catch: java.lang.Throwable -> L6d
            goto L66
        L65:
            r5 = r2
        L66:
            if (r5 != 0) goto L69
        L68:
            r2 = r3
        L69:
            android.os.Binder.restoreCallingIdentity(r0)
            return r2
        L6d:
            r5 = move-exception
            android.os.Binder.restoreCallingIdentity(r0)
            throw r5
        L72:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L21
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AbstractAccessibilityServiceConnection.resetMagnification(int, boolean):boolean");
    }

    public int resolveAccessibilityWindowIdForFindFocusLocked(int i, int i2) {
        if (i == -2) {
            i = this.mA11yWindowManager.getDefaultFocus(i2);
            if (!this.mA11yWindowManager.windowIdBelongsToDisplayType(i, this.mDisplayTypes)) {
                return -1;
            }
        }
        return i;
    }

    public final int resolveAccessibilityWindowIdLocked(int i) {
        if (i == Integer.MAX_VALUE) {
            i = this.mA11yWindowManager.getActiveWindowId(((AccessibilityManagerService) this.mSystemSupport).mCurrentUserId);
            if (!this.mA11yWindowManager.windowIdBelongsToDisplayType(i, this.mDisplayTypes)) {
                return -1;
            }
        }
        return i;
    }

    public void sendGesture(int i, ParceledListSlice parceledListSlice) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("sendGesture", "sequence=" + i + ";gestureSteps=" + parceledListSlice);
        }
    }

    public final void sendScreenshotFailure(final int i, final RemoteCallback remoteCallback) {
        this.mMainHandler.post(PooledLambda.obtainRunnable(new Consumer() { // from class: com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                RemoteCallback remoteCallback2 = remoteCallback;
                Bundle bundle = new Bundle();
                bundle.putInt("screenshot_status", i2);
                remoteCallback2.sendResult(bundle);
            }
        }, (Object) null).recycleOnUse());
    }

    public final void sendScreenshotSuccess(final ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer, final RemoteCallback remoteCallback) {
        if (com.android.window.flags.Flags.deleteCaptureDisplay()) {
            this.mMainHandler.post(PooledLambda.obtainRunnable(new Consumer() { // from class: com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer2 = screenshotHardwareBuffer;
                    RemoteCallback remoteCallback2 = remoteCallback;
                    HardwareBuffer hardwareBuffer = screenshotHardwareBuffer2.getHardwareBuffer();
                    ParcelableColorSpace parcelableColorSpace = new ParcelableColorSpace(screenshotHardwareBuffer2.getColorSpace());
                    Bundle bundle = new Bundle();
                    bundle.putInt("screenshot_status", 0);
                    bundle.putParcelable("screenshot_hardwareBuffer", hardwareBuffer);
                    bundle.putParcelable("screenshot_colorSpace", parcelableColorSpace);
                    bundle.putLong("screenshot_timestamp", SystemClock.uptimeMillis());
                    remoteCallback2.sendResult(bundle);
                    hardwareBuffer.close();
                }
            }, (Object) null).recycleOnUse());
            return;
        }
        HardwareBuffer hardwareBuffer = screenshotHardwareBuffer.getHardwareBuffer();
        ParcelableColorSpace parcelableColorSpace = new ParcelableColorSpace(screenshotHardwareBuffer.getColorSpace());
        Bundle bundle = new Bundle();
        bundle.putInt("screenshot_status", 0);
        bundle.putParcelable("screenshot_hardwareBuffer", hardwareBuffer);
        bundle.putParcelable("screenshot_colorSpace", parcelableColorSpace);
        bundle.putLong("screenshot_timestamp", SystemClock.uptimeMillis());
        remoteCallback.sendResult(bundle);
        hardwareBuffer.close();
    }

    public void setAnimationScale(float f) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.Global.putFloat(this.mContext.getContentResolver(), "window_animation_scale", f);
            Settings.Global.putFloat(this.mContext.getContentResolver(), "transition_animation_scale", f);
            Settings.Global.putFloat(this.mContext.getContentResolver(), "animator_duration_scale", f);
            String[] strArr = {"window_animation_scale", "transition_animation_scale", "animator_duration_scale"};
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= 3) {
                    break;
                }
                if (Settings.Global.getString(this.mContext.getContentResolver(), strArr[i2]).equals("0.0")) {
                    i = 1;
                    break;
                }
                i2++;
            }
            Settings.Global.putInt(this.mContext.getContentResolver(), "remove_animations", i);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void setAttributionTag(String str) {
        this.mAttributionTag = str;
    }

    public final void setCacheEnabled(boolean z) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setCacheEnabled", "enabled=" + z);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                this.mUsesAccessibilityCache = z;
                ((AccessibilityManagerService) this.mSystemSupport).onClientChangeLocked(true, false);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setDynamicallyConfigurableProperties(AccessibilityServiceInfo accessibilityServiceInfo) {
        this.mEventTypes = accessibilityServiceInfo.eventTypes;
        this.mFeedbackType = accessibilityServiceInfo.feedbackType;
        String[] strArr = accessibilityServiceInfo.packageNames;
        this.mPackageNames.clear();
        if (strArr != null) {
            this.mPackageNames.addAll(Arrays.asList(strArr));
        }
        this.mNotificationTimeout = accessibilityServiceInfo.notificationTimeout;
        this.mIsDefault = (accessibilityServiceInfo.flags & 1) != 0;
        this.mGenericMotionEventSources = accessibilityServiceInfo.getMotionEventSources();
        if (android.view.accessibility.Flags.motionEventObserving()) {
            if (this.mContext.checkCallingOrSelfPermission("android.permission.ACCESSIBILITY_MOTION_EVENT_OBSERVING") == 0) {
                this.mObservedMotionEventSources = accessibilityServiceInfo.getObservedMotionEventSources();
            } else {
                Slog.e("AbstractAccessibilityServiceConnection", "Observing motion events requires android.Manifest.permission.ACCESSIBILITY_MOTION_EVENT_OBSERVING.");
                this.mObservedMotionEventSources = 0;
            }
        }
        if (supportsFlagForNotImportantViews(accessibilityServiceInfo)) {
            if ((accessibilityServiceInfo.flags & 2) != 0) {
                this.mFetchFlags |= 128;
            } else {
                this.mFetchFlags &= -129;
            }
        }
        if ((accessibilityServiceInfo.flags & 16) != 0) {
            this.mFetchFlags |= 256;
        } else {
            this.mFetchFlags &= -257;
        }
        if (this.mAccessibilityServiceInfo.isAccessibilityTool()) {
            this.mFetchFlags |= 512;
        } else {
            this.mFetchFlags &= -513;
        }
        int i = accessibilityServiceInfo.flags;
        this.mRequestTouchExplorationMode = (i & 4) != 0;
        this.mServiceHandlesDoubleTap = (i & 2048) != 0;
        this.mRequestMultiFingerGestures = (i & 4096) != 0;
        this.mRequestTwoFingerPassthrough = (i & 8192) != 0;
        this.mSendMotionEvents = (i & EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION) != 0;
        this.mRequestFilterKeyEvents = (i & 32) != 0;
        this.mRetrieveInteractiveWindows = (i & 64) != 0;
        this.mCaptureFingerprintGestures = (i & 512) != 0;
        this.mRequestAccessibilityButton = (i & 256) != 0;
        this.mRequestImeApis = (i & 32768) != 0;
    }

    public void setFocusAppearance(int i, int i2) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setFocusAppearance", ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "strokeWidth=", ";color="));
        }
    }

    public void setGestureDetectionPassthroughRegion(int i, Region region) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setGestureDetectionPassthroughRegion", "displayId=" + i + ";region=" + region);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) this.mSystemSupport;
            accessibilityManagerService.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda12(1), accessibilityManagerService, Integer.valueOf(i), region));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setInstalledAndEnabledServices(List list) {
    }

    public void setMagnificationCallbackEnabled(int i, boolean z) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setMagnificationCallbackEnabled", AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(i, "displayId=", ";enabled=", z));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            InvocationHandler invocationHandler = this.mInvocationHandler;
            synchronized (AbstractAccessibilityServiceConnection.this.mLock) {
                try {
                    if (z) {
                        invocationHandler.mMagnificationCallbackState.put(i, Boolean.TRUE);
                    } else {
                        invocationHandler.mMagnificationCallbackState.remove(i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean setMagnificationConfig(int i, MagnificationConfig magnificationConfig, boolean z) {
        if (svcConnTracingEnabled()) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "displayId=", ", config=");
            m.append(magnificationConfig.toString());
            logTraceSvcConn("setMagnificationSpec", m.toString());
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return false;
                }
                this.mSecurityPolicy.getClass();
                if (!AccessibilitySecurityPolicy.canControlMagnification(this)) {
                    return false;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return ((AccessibilityManagerService) this.mSystemSupport).mMagnificationProcessor.setMagnificationConfig(i, magnificationConfig, z, this.mId);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setOnKeyEventResult(boolean z, int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setOnKeyEventResult", "handled=" + z + ";sequence=" + i);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((AccessibilityManagerService) this.mSystemSupport).getKeyEventDispatcher().setOnKeyEventResult(this, z, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setServiceDetectsGesturesEnabled(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mServiceDetectsGestures.put(i, Boolean.valueOf(z));
            AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) this.mSystemSupport;
            accessibilityManagerService.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda12(2), accessibilityManagerService, Integer.valueOf(i), Boolean.valueOf(z)));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setServiceInfo(AccessibilityServiceInfo accessibilityServiceInfo) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setServiceInfo", "info=" + accessibilityServiceInfo);
        }
        if (!accessibilityServiceInfo.isWithinParcelableSize()) {
            throw new IllegalStateException("Cannot update service info: size is larger than safe parcelable limits.");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                try {
                    boolean z = this.mRequestImeApis;
                    AccessibilityServiceInfo accessibilityServiceInfo2 = this.mAccessibilityServiceInfo;
                    if (accessibilityServiceInfo2 != null) {
                        accessibilityServiceInfo2.updateDynamicallyConfigurableProperties(this.mIPlatformCompat, accessibilityServiceInfo);
                        setDynamicallyConfigurableProperties(accessibilityServiceInfo2);
                    } else {
                        setDynamicallyConfigurableProperties(accessibilityServiceInfo);
                    }
                    ((AccessibilityManagerService) this.mSystemSupport).onClientChangeLocked(true, false);
                    if (!z && this.mRequestImeApis) {
                        ((AccessibilityManagerService) this.mSystemSupport).requestImeLocked(this);
                    } else if (z && !this.mRequestImeApis) {
                        ((AccessibilityManagerService) this.mSystemSupport).unbindImeLocked(this);
                    }
                } finally {
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setSoftKeyboardCallbackEnabled(boolean z) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setSoftKeyboardCallbackEnabled", "enabled=" + z);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mInvocationHandler.mIsSoftKeyboardCallbackEnabled = z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setTestBrailleDisplayData(List list) {
        setTestBrailleDisplayData_enforcePermission();
        throw new UnsupportedOperationException();
    }

    public void setTouchExplorationPassthroughRegion(int i, Region region) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setTouchExplorationPassthroughRegion", "displayId=" + i + ";region=" + region);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) this.mSystemSupport;
            accessibilityManagerService.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda12(0), accessibilityManagerService, Integer.valueOf(i), region));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean supportsFlagForNotImportantViews(AccessibilityServiceInfo accessibilityServiceInfo) {
        return accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion >= 16;
    }

    public final boolean svcClientTracingEnabled() {
        return this.mTrace.isA11yTracingEnabledForTypes(2L);
    }

    public final boolean svcConnTracingEnabled() {
        return this.mTrace.isA11yTracingEnabledForTypes(1L);
    }

    public void takeScreenshot(final int i, final RemoteCallback remoteCallback) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("takeScreenshot", "displayId=" + i + ";callback=" + remoteCallback);
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = this.mRequestTakeScreenshotTimestampMs;
        if (j != 0 && uptimeMillis - j <= 333) {
            sendScreenshotFailure(3, remoteCallback);
            return;
        }
        this.mRequestTakeScreenshotTimestampMs = uptimeMillis;
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    sendScreenshotFailure(1, remoteCallback);
                    return;
                }
                this.mSecurityPolicy.getClass();
                if ((getCapabilities() & 128) == 0) {
                    throw new SecurityException("Services don't have the capability of taking the screenshot.");
                }
                if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    sendScreenshotFailure(2, remoteCallback);
                    return;
                }
                Display display = ((DisplayManager) this.mContext.getSystemService("display")).getDisplay(i);
                if (display == null || (display.getType() == 5 && (display.getFlags() & 4) != 0)) {
                    sendScreenshotFailure(4, remoteCallback);
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                if (!com.android.window.flags.Flags.deleteCaptureDisplay()) {
                    try {
                        this.mMainHandler.post(PooledLambda.obtainRunnable(new Consumer() { // from class: com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticLambda3
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection = AbstractAccessibilityServiceConnection.this;
                                int i2 = i;
                                RemoteCallback remoteCallback2 = remoteCallback;
                                abstractAccessibilityServiceConnection.getClass();
                                ScreenCapture.ScreenshotHardwareBuffer userScreenshot = ((DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class)).userScreenshot(i2);
                                if (userScreenshot != null) {
                                    abstractAccessibilityServiceConnection.sendScreenshotSuccess(userScreenshot, remoteCallback2);
                                } else {
                                    abstractAccessibilityServiceConnection.sendScreenshotFailure(4, remoteCallback2);
                                }
                            }
                        }, (Object) null).recycleOnUse());
                    } finally {
                    }
                } else {
                    try {
                        try {
                            this.mWindowManagerService.captureDisplay(i, null, new ScreenCapture.ScreenCaptureListener(new ObjIntConsumer() { // from class: com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticLambda2
                                @Override // java.util.function.ObjIntConsumer
                                public final void accept(Object obj, int i2) {
                                    AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection = AbstractAccessibilityServiceConnection.this;
                                    RemoteCallback remoteCallback2 = remoteCallback;
                                    ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer = (ScreenCapture.ScreenshotHardwareBuffer) obj;
                                    abstractAccessibilityServiceConnection.getClass();
                                    if (screenshotHardwareBuffer == null || i2 != 0) {
                                        abstractAccessibilityServiceConnection.sendScreenshotFailure(4, remoteCallback2);
                                    } else {
                                        abstractAccessibilityServiceConnection.sendScreenshotSuccess(screenshotHardwareBuffer, remoteCallback2);
                                    }
                                }
                            }));
                        } finally {
                        }
                    } catch (Exception unused) {
                        sendScreenshotFailure(4, remoteCallback);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void takeScreenshotOfWindow(int i, int i2, ScreenCapture.ScreenCaptureListener screenCaptureListener, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        long uptimeMillis = SystemClock.uptimeMillis();
        if (uptimeMillis - ((Long) this.mRequestTakeScreenshotOfWindowTimestampMs.get(i, 0L)).longValue() <= 333) {
            iAccessibilityInteractionConnectionCallback.sendTakeScreenshotOfWindowError(3, i2);
            return;
        }
        this.mRequestTakeScreenshotOfWindowTimestampMs.put(i, Long.valueOf(uptimeMillis));
        synchronized (this.mLock) {
            try {
                boolean z = true;
                if (!hasRightsToCurrentUserLocked()) {
                    iAccessibilityInteractionConnectionCallback.sendTakeScreenshotOfWindowError(1, i2);
                    return;
                }
                this.mSecurityPolicy.getClass();
                if ((getCapabilities() & 128) == 0) {
                    z = false;
                }
                if (!z) {
                    iAccessibilityInteractionConnectionCallback.sendTakeScreenshotOfWindowError(2, i2);
                    return;
                }
                if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    iAccessibilityInteractionConnectionCallback.sendTakeScreenshotOfWindowError(2, i2);
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AccessibilityWindowManager.RemoteAccessibilityConnection connectionLocked = this.mA11yWindowManager.getConnectionLocked(((AccessibilityManagerService) this.mSystemSupport).mCurrentUserId, resolveAccessibilityWindowIdLocked(i));
                    if (connectionLocked == null) {
                        iAccessibilityInteractionConnectionCallback.sendTakeScreenshotOfWindowError(5, i2);
                    } else {
                        connectionLocked.mConnection.takeScreenshotOfWindow(i2, screenCaptureListener, iAccessibilityInteractionConnectionCallback);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean wantsEventLocked(AccessibilityEvent accessibilityEvent) {
        if (!((this.mEventTypes == 0 || this.mService == null) ? false : true)) {
            return false;
        }
        boolean z = (this.mFetchFlags & 128) != 0;
        if (accessibilityEvent.getWindowId() != -1 && !accessibilityEvent.isImportantForAccessibility() && !z) {
            return false;
        }
        if (accessibilityEvent.isAccessibilityDataSensitive() && (this.mFetchFlags & 512) == 0) {
            return false;
        }
        int eventType = accessibilityEvent.getEventType();
        if ((this.mEventTypes & eventType) != eventType) {
            return false;
        }
        HashSet hashSet = (HashSet) this.mPackageNames;
        return hashSet.isEmpty() || hashSet.contains(accessibilityEvent.getPackageName() != null ? accessibilityEvent.getPackageName().toString() : null);
    }
}
