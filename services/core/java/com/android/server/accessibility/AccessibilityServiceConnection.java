package com.android.server.accessibility;

import android.R;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityTrace;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.accessibilityservice.IBrailleDisplayController;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.inputmethod.IAccessibilityInputMethodSession;
import com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.WindowManagerInternal;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class AccessibilityServiceConnection extends AbstractAccessibilityServiceConnection {
    public final ActivityTaskManagerInternal mActivityTaskManagerService;
    public BrailleDisplayConnection mBrailleDisplayConnection;
    public final Intent mIntent;
    public final Handler mMainHandler;
    public List mTestBrailleDisplays;
    public final int mUserId;
    public final WeakReference mUserStateWeakReference;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AccessibilityInputMethodSessionCallback extends IAccessibilityInputMethodSessionCallback.Stub {
        public final int mUserId;

        public AccessibilityInputMethodSessionCallback(int i) {
        }

        public final void sessionCreated(IAccessibilityInputMethodSession iAccessibilityInputMethodSession, int i) {
            Trace.traceBegin(32L, "ASC.sessionCreated");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                InputMethodManagerInternal.get().onSessionForAccessibilityCreated(iAccessibilityInputMethodSession, i);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                Trace.traceEnd(32L);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public AccessibilityServiceConnection(AccessibilityUserState accessibilityUserState, Context context, ComponentName componentName, AccessibilityServiceInfo accessibilityServiceInfo, int i, Handler handler, Object obj, AccessibilitySecurityPolicy accessibilitySecurityPolicy, AbstractAccessibilityServiceConnection.SystemSupport systemSupport, AccessibilityTrace accessibilityTrace, WindowManagerInternal windowManagerInternal, SystemActionPerformer systemActionPerformer, AccessibilityWindowManager accessibilityWindowManager, ActivityTaskManagerInternal activityTaskManagerInternal) {
        super(context, componentName, accessibilityServiceInfo, i, handler, obj, accessibilitySecurityPolicy, systemSupport, accessibilityTrace, windowManagerInternal, systemActionPerformer, accessibilityWindowManager);
        this.mTestBrailleDisplays = null;
        this.mUserStateWeakReference = new WeakReference(accessibilityUserState);
        this.mUserId = accessibilityUserState == null ? -10000 : accessibilityUserState.mUserId;
        Intent component = new Intent().setComponent(this.mComponentName);
        this.mIntent = component;
        this.mMainHandler = handler;
        component.putExtra("android.intent.extra.client_label", R.string.accessibility_system_action_on_screen_a11y_shortcut_chooser_label);
        this.mActivityTaskManagerService = activityTaskManagerInternal;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AbstractAccessibilityServiceConnection.SystemSupport systemSupport2 = this.mSystemSupport;
            Context context2 = this.mContext;
            Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
            ((AccessibilityManagerService) systemSupport2).getClass();
            component.putExtra("android.intent.extra.client_intent", PendingIntent.getActivity(context2, 0, intent, 67108864));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        synchronized (this.mLock) {
            try {
                if (this.mService != null) {
                    if (this.mRequestImeApis) {
                        ((AccessibilityManagerService) this.mSystemSupport).unbindImeLocked(this);
                    }
                    this.mAccessibilityServiceInfo.crashed = true;
                    AccessibilityUserState accessibilityUserState = (AccessibilityUserState) this.mUserStateWeakReference.get();
                    if (accessibilityUserState != null) {
                        accessibilityUserState.removeServiceLocked(this);
                        ((HashSet) accessibilityUserState.mCrashedServices).add(this.mComponentName);
                    }
                    resetLocked();
                    ((AccessibilityManagerService) this.mSystemSupport).mMagnificationProcessor.resetAllIfNeeded(this.mId);
                    ((AccessibilityManagerService) this.mSystemSupport).onClientChangeLocked(false, false);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void connectBluetoothBrailleDisplay(final String str, IBrailleDisplayController iBrailleDisplayController) {
        connectBluetoothBrailleDisplay_enforcePermission();
        if (!android.view.accessibility.Flags.brailleDisplayHid()) {
            throw new IllegalStateException("Flag BRAILLE_DISPLAY_HID not enabled");
        }
        Objects.requireNonNull(str);
        Objects.requireNonNull(iBrailleDisplayController);
        if (!BluetoothAdapter.checkBluetoothAddress(str)) {
            throw new IllegalArgumentException(str.concat(" is not a valid Bluetooth address"));
        }
        BluetoothManager bluetoothManager = (BluetoothManager) this.mContext.getSystemService(BluetoothManager.class);
        String str2 = bluetoothManager != null ? (String) bluetoothManager.getAdapter().getBondedDevices().stream().filter(new Predicate() { // from class: com.android.server.accessibility.AccessibilityServiceConnection$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((BluetoothDevice) obj).getAddress().equalsIgnoreCase(str);
            }
        }).map(new AccessibilityServiceConnection$$ExternalSyntheticLambda3()).findFirst().orElse(null) : null;
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked() || !this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    throw new SecurityException("Caller does not have accessibility access");
                }
                if (this.mBrailleDisplayConnection != null) {
                    throw new IllegalStateException("This service already has a connected Braille display");
                }
                BrailleDisplayConnection brailleDisplayConnection = new BrailleDisplayConnection(this.mLock, this);
                List list = this.mTestBrailleDisplays;
                if (list != null) {
                    brailleDisplayConnection.setTestData(list);
                }
                brailleDisplayConnection.connectLocked(str, str2, 5, iBrailleDisplayController);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void connectUsbBrailleDisplay(UsbDevice usbDevice, IBrailleDisplayController iBrailleDisplayController) {
        if (!android.view.accessibility.Flags.brailleDisplayHid()) {
            throw new IllegalStateException("Flag BRAILLE_DISPLAY_HID not enabled");
        }
        Objects.requireNonNull(usbDevice);
        Objects.requireNonNull(iBrailleDisplayController);
        UsbManager usbManager = (UsbManager) this.mContext.getSystemService("usb");
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (usbManager != null) {
            try {
                if (usbManager.hasPermission(usbDevice, this.mComponentName.getPackageName(), callingPid, callingUid)) {
                    String serialNumber = usbDevice.getSerialNumber();
                    if (TextUtils.isEmpty(serialNumber)) {
                        try {
                            iBrailleDisplayController.onConnectionFailed(2);
                        } catch (RemoteException e) {
                            Slog.e("AccessibilityServiceConnection", "Error calling onConnectionFailed", e);
                        }
                        return;
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    synchronized (this.mLock) {
                        try {
                            if (!hasRightsToCurrentUserLocked() || !this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                                throw new SecurityException("Caller does not have accessibility access");
                            }
                            if (this.mBrailleDisplayConnection != null) {
                                throw new IllegalStateException("This service already has a connected Braille display");
                            }
                            BrailleDisplayConnection brailleDisplayConnection = new BrailleDisplayConnection(this.mLock, this);
                            List list = this.mTestBrailleDisplays;
                            if (list != null) {
                                brailleDisplayConnection.setTestData(list);
                            }
                            brailleDisplayConnection.connectLocked(serialNumber, usbDevice.getProductName(), 3, iBrailleDisplayController);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        throw new SecurityException("Caller does not have permission to access this UsbDevice");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void createImeSessionInternal() {
        IAccessibilityServiceClient serviceInterfaceSafely = getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null) {
            try {
                if (svcClientTracingEnabled()) {
                    logTraceSvcClient("createImeSession", "");
                }
                serviceInterfaceSafely.createImeSession(new AccessibilityInputMethodSessionCallback(this.mUserId));
            } catch (RemoteException e) {
                Slog.e("AccessibilityServiceConnection", "Error requesting IME session from " + this.mService, e);
            }
        }
    }

    public void disableSelf() {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("disableSelf", "");
        }
        synchronized (this.mLock) {
            try {
                AccessibilityUserState accessibilityUserState = (AccessibilityUserState) this.mUserStateWeakReference.get();
                if (accessibilityUserState == null) {
                    return;
                }
                if (((HashSet) accessibilityUserState.mEnabledServices).remove(this.mComponentName)) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        AbstractAccessibilityServiceConnection.SystemSupport systemSupport = this.mSystemSupport;
                        AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) systemSupport;
                        accessibilityManagerService.persistComponentNamesToSettingLocked(accessibilityUserState.mUserId, "enabled_accessibility_services", accessibilityUserState.mEnabledServices);
                        ((AccessibilityManagerService) this.mSystemSupport).onClientChangeLocked(false, false);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void dispatchGesture(int i, ParceledListSlice parceledListSlice, int i2) {
        MotionEventInjector motionEventInjector;
        synchronized (this.mLock) {
            try {
                if (this.mServiceInterface != null) {
                    this.mSecurityPolicy.getClass();
                    if ((getCapabilities() & 32) != 0) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) this.mSystemSupport;
                            accessibilityManagerService.getClass();
                            long uptimeMillis = SystemClock.uptimeMillis() + 1000;
                            while (accessibilityManagerService.mMotionEventInjectors == null && SystemClock.uptimeMillis() < uptimeMillis) {
                                try {
                                    accessibilityManagerService.mLock.wait(uptimeMillis - SystemClock.uptimeMillis());
                                } catch (InterruptedException unused) {
                                }
                            }
                            SparseArray sparseArray = accessibilityManagerService.mMotionEventInjectors;
                            if (sparseArray == null) {
                                Slog.e("AccessibilityManagerService", "MotionEventInjector installation timed out");
                                motionEventInjector = null;
                            } else {
                                motionEventInjector = (MotionEventInjector) sparseArray.get(i2);
                            }
                            if (this.mTrace.isA11yTracingEnabledForTypes(512L)) {
                                logTraceWM("isTouchOrFaketouchDevice", "");
                            }
                            if (motionEventInjector == null || !this.mWindowManagerService.isTouchOrFaketouchDevice()) {
                                try {
                                    if (svcClientTracingEnabled()) {
                                        logTraceSvcClient("onPerformGestureResult", i + ", false");
                                    }
                                    this.mServiceInterface.onPerformGestureResult(i, false);
                                } catch (RemoteException e) {
                                    Slog.e("AccessibilityServiceConnection", "Error sending motion event injection failure to " + this.mServiceInterface, e);
                                }
                            } else {
                                List list = parceledListSlice.getList();
                                IAccessibilityServiceClient iAccessibilityServiceClient = this.mServiceInterface;
                                SomeArgs obtain = SomeArgs.obtain();
                                obtain.arg1 = list;
                                obtain.arg2 = iAccessibilityServiceClient;
                                obtain.argi1 = i;
                                obtain.argi2 = i2;
                                Handler handler = motionEventInjector.mHandler;
                                handler.sendMessage(handler.obtainMessage(2, obtain));
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final AccessibilityServiceInfo getServiceInfo() {
        return this.mAccessibilityServiceInfo;
    }

    public int getSoftKeyboardShowMode() {
        int i;
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getSoftKeyboardShowMode", "");
        }
        AccessibilityUserState accessibilityUserState = (AccessibilityUserState) this.mUserStateWeakReference.get();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (accessibilityUserState != null) {
            try {
                i = accessibilityUserState.mSoftKeyboardShowMode;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } else {
            i = 0;
        }
        return i;
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public boolean hasRightsToCurrentUserLocked() {
        int callingUid = Binder.getCallingUid();
        return callingUid == 0 || callingUid == 1000 || callingUid == 2000 || this.mSecurityPolicy.resolveProfileParentLocked(UserHandle.getUserId(callingUid)) == ((AccessibilityManagerService) this.mSystemSupport).mCurrentUserId || this.mSecurityPolicy.mContext.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS") == 0 || this.mSecurityPolicy.mContext.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL") == 0;
    }

    public boolean isAccessibilityButtonAvailable() {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("isAccessibilityButtonAvailable", "");
        }
        synchronized (this.mLock) {
            try {
                boolean z = false;
                if (!hasRightsToCurrentUserLocked()) {
                    return false;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (((AccessibilityUserState) this.mUserStateWeakReference.get()) != null) {
                        if (isAccessibilityButtonAvailableLocked()) {
                            z = true;
                        }
                    }
                    return z;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isAccessibilityButtonAvailableLocked() {
        return this.mRequestAccessibilityButton && ((AccessibilityManagerService) this.mSystemSupport).mIsAccessibilityButtonShown;
    }

    @Override // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
    public boolean isCapturingFingerprintGestures() {
        if (this.mServiceInterface != null) {
            this.mSecurityPolicy.getClass();
            if ((getCapabilities() & 64) != 0 && this.mCaptureFingerprintGestures) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
    public void onFingerprintGesture(int i) {
        IAccessibilityServiceClient iAccessibilityServiceClient;
        if (isCapturingFingerprintGestures()) {
            synchronized (this.mLock) {
                iAccessibilityServiceClient = this.mServiceInterface;
            }
            if (iAccessibilityServiceClient != null) {
                try {
                    if (svcClientTracingEnabled()) {
                        logTraceSvcClient("onFingerprintGesture", String.valueOf(i));
                    }
                    this.mServiceInterface.onFingerprintGesture(i);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    @Override // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
    public void onFingerprintGestureDetectionActiveChanged(boolean z) {
        IAccessibilityServiceClient iAccessibilityServiceClient;
        if (isCapturingFingerprintGestures()) {
            synchronized (this.mLock) {
                iAccessibilityServiceClient = this.mServiceInterface;
            }
            if (iAccessibilityServiceClient != null) {
                try {
                    if (svcClientTracingEnabled()) {
                        logTraceSvcClient("onFingerprintCapturingGesturesChanged", String.valueOf(z));
                    }
                    this.mServiceInterface.onFingerprintCapturingGesturesChanged(z);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        AccessibilityUserState accessibilityUserState = (AccessibilityUserState) this.mUserStateWeakReference.get();
        if (accessibilityUserState != null) {
            addWindowTokensForAllDisplays();
        }
        synchronized (this.mLock) {
            try {
                IBinder iBinder2 = this.mService;
                if (iBinder2 != iBinder) {
                    if (iBinder2 != null) {
                        iBinder2.unlinkToDeath(this, 0);
                    }
                    this.mService = iBinder;
                    try {
                        iBinder.linkToDeath(this, 0);
                    } catch (RemoteException unused) {
                        Slog.e("AccessibilityServiceConnection", "Failed registering death link");
                        binderDied();
                        return;
                    }
                }
                this.mServiceInterface = IAccessibilityServiceClient.Stub.asInterface(iBinder);
                if (accessibilityUserState == null) {
                    return;
                }
                if (!accessibilityUserState.mBoundServices.contains(this)) {
                    accessibilityUserState.mBoundServices.add(this);
                    ((HashMap) accessibilityUserState.mComponentNameToServiceMap).put(this.mComponentName, this);
                    ((AccessibilityManagerService) accessibilityUserState.mServiceInfoChangeListener).onServiceInfoChangedLocked(accessibilityUserState);
                }
                ((AccessibilityManagerService) this.mSystemSupport).onClientChangeLocked(false, false);
                this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityServiceConnection$$ExternalSyntheticLambda1(), this));
                if (this.mRequestImeApis) {
                    ((AccessibilityManagerService) this.mSystemSupport).requestImeLocked(this);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        binderDied();
        AccessibilityUserState accessibilityUserState = (AccessibilityUserState) this.mUserStateWeakReference.get();
        if (accessibilityUserState != null) {
            this.mActivityTaskManagerService.setAllowAppSwitches(-1, accessibilityUserState.mUserId, this.mComponentName.flattenToString());
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void resetLocked() {
        BrailleDisplayConnection brailleDisplayConnection;
        super.resetLocked();
        if (!android.view.accessibility.Flags.brailleDisplayHid() || (brailleDisplayConnection = this.mBrailleDisplayConnection) == null) {
            return;
        }
        brailleDisplayConnection.disconnect();
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void setFocusAppearance(int i, int i2) {
        AccessibilityUserState accessibilityUserState = (AccessibilityUserState) this.mUserStateWeakReference.get();
        if (accessibilityUserState == null) {
            return;
        }
        synchronized (this.mLock) {
            try {
                if (hasRightsToCurrentUserLocked()) {
                    if (this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                        if (accessibilityUserState.mFocusStrokeWidth == i && accessibilityUserState.mFocusColor == i2) {
                            return;
                        }
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            accessibilityUserState.mFocusStrokeWidth = i;
                            accessibilityUserState.mFocusColor = i2;
                            ((AccessibilityManagerService) this.mSystemSupport).onClientChangeLocked(false, false);
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public int setInputMethodEnabled(String str, boolean z) {
        int canEnableDisableInputMethod;
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("switchToInputMethod", "imeId=" + str);
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return 2;
                }
                int callingUserId = UserHandle.getCallingUserId();
                InputMethodManagerInternal inputMethodManagerInternal = InputMethodManagerInternal.get();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (this.mLock) {
                        canEnableDisableInputMethod = this.mSecurityPolicy.canEnableDisableInputMethod(str, this);
                    }
                    if (canEnableDisableInputMethod != 0) {
                        return canEnableDisableInputMethod;
                    }
                    if (!inputMethodManagerInternal.setInputMethodEnabled(callingUserId, str, z)) {
                        return 2;
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return 0;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean setSoftKeyboardShowMode(int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setSoftKeyboardShowMode", "showMode=" + i);
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return false;
                }
                AccessibilityUserState accessibilityUserState = (AccessibilityUserState) this.mUserStateWeakReference.get();
                if (accessibilityUserState == null) {
                    return false;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return accessibilityUserState.setSoftKeyboardModeLocked(i, this.mComponentName);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public final void setTestBrailleDisplayData(List list) {
        setTestBrailleDisplayData_enforcePermission();
        this.mTestBrailleDisplays = list;
    }

    public boolean switchToInputMethod(String str) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("switchToInputMethod", "imeId=" + str);
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return false;
                }
                int callingUserId = UserHandle.getCallingUserId();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return InputMethodManagerInternal.get().switchToInputMethod(callingUserId, str);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unbindLocked() {
        if (this.mRequestImeApis) {
            ((AccessibilityManagerService) this.mSystemSupport).unbindImeLocked(this);
        }
        this.mContext.unbindService(this);
        AccessibilityUserState accessibilityUserState = (AccessibilityUserState) this.mUserStateWeakReference.get();
        if (accessibilityUserState == null) {
            return;
        }
        accessibilityUserState.removeServiceLocked(this);
        ((AccessibilityManagerService) this.mSystemSupport).mMagnificationProcessor.resetAllIfNeeded(this.mId);
        this.mActivityTaskManagerService.setAllowAppSwitches(-1, accessibilityUserState.mUserId, this.mComponentName.flattenToString());
        resetLocked();
    }
}
