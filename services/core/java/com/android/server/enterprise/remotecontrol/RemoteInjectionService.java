package com.android.server.enterprise.remotecontrol;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.content.Context;
import android.content.pm.UserInfo;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.accessControl.EnterpriseAccessController;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.Utils;
import com.android.server.input.KeyboardMetricsCollector;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.localservice.RemoteInjectionInternal;
import com.samsung.android.knox.remotecontrol.IRemoteInjection;
import com.samsung.android.knox.remotecontrol.IRemoteScreenWatcherCallback;
import com.samsung.android.rune.CoreRune;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RemoteInjectionService extends IRemoteInjection.Stub implements EnterpriseServiceCallback {
    public final Context mContext;
    public int mCurrentDisplayHeight;
    public int mCurrentDisplayWidth;
    public int mDexScreenHeight;
    public int mDexScreenWidth;
    public EnterpriseDeviceManager mEDM;
    public final EdmStorageProvider mEdmStorageProvider;
    public final Injector mInjector;
    public int mKnoxRemoteScreenSessionOwnerUid;
    public final SparseArray mRemoteControlDisabled;
    public int mRemoteScreenHeight;
    public IRemoteScreenWatcherCallback mRemoteScreenWatcherCallback;
    public int mRemoteScreenWidth;
    public WindowManager mWindowManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService extends RemoteInjectionInternal {
        public LocalService() {
        }

        public final boolean isRemoteControlDisabled(int i) {
            return RemoteInjectionService.this.isRemoteControlDisabled(i);
        }
    }

    public RemoteInjectionService(Context context) {
        Injector injector = new Injector(context);
        this.mRemoteControlDisabled = new SparseArray();
        this.mEDM = null;
        this.mEdmStorageProvider = null;
        this.mWindowManager = null;
        this.mContext = context;
        this.mInjector = injector;
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        this.mWindowManager = windowManager;
        Display defaultDisplay = windowManager.getDefaultDisplay();
        updateCurrentDisplayDimensions(0);
        this.mRemoteScreenWidth = isInPortrait(defaultDisplay) ? this.mCurrentDisplayWidth : this.mCurrentDisplayHeight;
        this.mRemoteScreenHeight = isInPortrait(defaultDisplay) ? this.mCurrentDisplayHeight : this.mCurrentDisplayWidth;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        this.mKnoxRemoteScreenSessionOwnerUid = -1;
        this.mDexScreenWidth = 0;
        this.mDexScreenHeight = 0;
        LocalServices.addService(RemoteInjectionInternal.class, new LocalService());
    }

    public static boolean injectKeyEventInternal(KeyEvent keyEvent, boolean z) {
        long downTime = keyEvent.getDownTime();
        long eventTime = keyEvent.getEventTime();
        int action = keyEvent.getAction();
        int keyCode = keyEvent.getKeyCode();
        int repeatCount = keyEvent.getRepeatCount();
        int metaState = keyEvent.getMetaState();
        int deviceId = keyEvent.getDeviceId();
        int scanCode = keyEvent.getScanCode();
        int source = keyEvent.getSource();
        int flags = keyEvent.getFlags();
        int displayId = keyEvent.getDisplayId();
        if (source == 0) {
            source = FrameworkStatsLog.HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP;
        }
        int i = source;
        if (eventTime == 0) {
            eventTime = SystemClock.uptimeMillis();
        }
        if (downTime == 0) {
            downTime = eventTime;
        }
        KeyEvent keyEvent2 = new KeyEvent(downTime, eventTime, action, keyCode, repeatCount, metaState, deviceId, scanCode, flags | 8, i);
        keyEvent2.setDisplayId(displayId);
        return InputManager.getInstance().injectInputEvent(keyEvent2, z ? 2 : 1);
    }

    public static boolean isInPortrait(Display display) {
        int rotation = display.getRotation();
        return rotation == 0 || rotation == 2;
    }

    public final boolean addRemoteScreenWatcherCallback(IRemoteScreenWatcherCallback iRemoteScreenWatcherCallback) {
        this.mRemoteScreenWatcherCallback = iRemoteScreenWatcherCallback;
        return isUsingKnoxRemoteScreen();
    }

    public final boolean allowRemoteControl(ContextInfo contextInfo, boolean z, boolean z2) {
        ContextInfo enforceActiveAdminPermissionByContext;
        if (z2) {
            ArrayList arrayList = new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION"));
            if (this.mEDM == null) {
                this.mEDM = EnterpriseDeviceManager.getInstance(this.mInjector.mContext);
            }
            enforceActiveAdminPermissionByContext = this.mEDM.enforceDoPoOnlyPermissionByContext(contextInfo, arrayList);
        } else {
            ArrayList arrayList2 = new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_REMOTE_CONTROL"));
            if (this.mEDM == null) {
                this.mEDM = EnterpriseDeviceManager.getInstance(this.mInjector.mContext);
            }
            enforceActiveAdminPermissionByContext = this.mEDM.enforceActiveAdminPermissionByContext(contextInfo, arrayList2);
        }
        int i = enforceActiveAdminPermissionByContext.mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z3 = false;
        try {
            try {
                z3 = this.mEdmStorageProvider.putBoolean("RESTRICTION", i, z, 0, "allowRemoteControl");
                if (z3) {
                    this.mRemoteControlDisabled.put(UserHandle.getUserId(i), Boolean.valueOf(!isRemoteControlAllowed(r9)));
                }
            } catch (Exception unused) {
                Slog.w("RemoteInjection", "RemoteInjection.allowRemoteControl() exception : ");
            }
            return z3;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump Enterprise Device Manager Service");
            return;
        }
        List users = PackageManagerAdapter.getUsers(true);
        int size = users.size();
        printWriter.println("RemoteControl disallowed userId : ");
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = ((UserInfo) users.get(i2)).id;
            if (!isRemoteControlAllowed(i3)) {
                printWriter.println(i3);
                i++;
            }
        }
        if (i == 0) {
            printWriter.println(KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
        }
    }

    public final boolean injectKeyEvent(KeyEvent keyEvent, boolean z) {
        long clearCallingIdentity;
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        boolean z2 = false;
        if (keyEvent == null) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 4, false, Process.myPid(), "RemoteInjectionService", "Remotely injecting a keystroke event into the UI failed", userId);
                return false;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        EnterpriseAccessController.enforceCaller(null, "INJECT_KEY_EVENT");
        clearCallingIdentity = Binder.clearCallingIdentity();
        if (isRemoteInjectionDisabled(callingUid)) {
            Slog.d("RemoteInjection", "Remote Control is disabled, couldnt inject key event");
            AuditLog.logAsUser(5, 4, false, Process.myPid(), "RemoteInjectionService", "Remotely injecting a keystroke event into the UI failed", userId);
            return false;
        }
        if (keyEvent.getDisplayId() != 2) {
            keyEvent.setDisplayId(0);
        }
        try {
            z2 = injectKeyEventInternal(keyEvent, z);
        } catch (Exception e) {
            KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(e, "Error injecting key event : ", "RemoteInjection");
        }
        if (z2) {
            AuditLog.logAsUser(5, 4, true, Process.myPid(), "RemoteInjectionService", "Remotely injecting a keystroke event into the UI succeeded", userId);
        } else {
            AuditLog.logAsUser(5, 4, false, Process.myPid(), "RemoteInjectionService", "Remotely injecting a keystroke event into the UI failed", userId);
        }
        return z2;
    }

    public final boolean injectKeyEventDex(KeyEvent keyEvent, boolean z) {
        EnterpriseAccessController.enforceCaller(null, "INJECT_KEY_EVENT_DEX");
        keyEvent.setDisplayId(2);
        boolean injectKeyEvent = injectKeyEvent(keyEvent, z);
        if (!injectKeyEvent) {
            Slog.d("RemoteInjection", "Error injecting Key event in dex screen");
        }
        return injectKeyEvent;
    }

    public final boolean injectPointerEvent(MotionEvent motionEvent, boolean z) {
        MotionEvent motionEvent2 = null;
        EnterpriseAccessController.enforceCaller(null, "INJECT_POINTER_EVENT");
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        if (isRemoteInjectionDisabled(callingUid)) {
            Slog.d("RemoteInjection", "Remote Control is disabled, couldnt inject pointer event");
            AuditLog.logAsUser(5, 4, false, Process.myPid(), "RemoteInjectionService", "Remotely injecting a pointer (touch) event into the UI failed", userId);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        }
        try {
            motionEvent.setDisplayId(0);
            motionEvent2 = transformMotionEvent(motionEvent);
            MotionEvent obtain = MotionEvent.obtain(motionEvent2);
            int i = 2;
            if ((obtain.getSource() & 2) == 0) {
                obtain.setSource(4098);
            }
            InputManager inputManager = InputManager.getInstance();
            if (!z) {
                i = 1;
            }
            z2 = inputManager.injectInputEvent(obtain, i);
        } catch (Exception e) {
            KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(e, "Error injecting trackball event : ", "RemoteInjection");
        }
        if (z2) {
            AuditLog.logAsUser(5, 4, true, Process.myPid(), "RemoteInjectionService", "Remotely injecting a pointer (touch) event into the UI succeeded", userId);
        } else {
            AuditLog.logAsUser(5, 4, false, Process.myPid(), "RemoteInjectionService", "Remotely injecting a pointer (touch) event into the UI failed", userId);
        }
        if (motionEvent2 != null) {
            motionEvent2.recycle();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public final boolean injectPointerEventDex(MotionEvent motionEvent, boolean z) {
        MotionEvent motionEvent2 = null;
        EnterpriseAccessController.enforceCaller(null, "INJECT_POINTER_EVENT_DEX");
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        if (isRemoteInjectionDisabled(callingUid)) {
            Slog.d("RemoteInjection", "Remote Control is disabled, couldnt inject pointer event");
            AuditLog.logAsUser(5, 4, false, Process.myPid(), "RemoteInjectionService", "Remotely injecting a pointer (touch) event into the UI failed", userId);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        }
        int i = 2;
        try {
            motionEvent.setDisplayId(2);
            if (motionEvent.getAction() == 0 && motionEvent.getButtonState() != 2) {
                motionEvent.setButtonState(1);
            }
            motionEvent2 = transformMotionEvent(motionEvent);
            MotionEvent obtain = MotionEvent.obtain(motionEvent2);
            obtain.setSource(8194);
            InputManager inputManager = InputManager.getInstance();
            if (!z) {
                i = 1;
            }
            z2 = inputManager.injectInputEvent(obtain, i);
        } catch (Exception e) {
            KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(e, "Error injecting pointer event in dex screen : ", "RemoteInjection");
        }
        if (z2) {
            AuditLog.logAsUser(5, 4, true, Process.myPid(), "RemoteInjectionService", "Remotely injecting a pointer (touch) event into the UI succeeded", userId);
        } else {
            AuditLog.logAsUser(5, 4, false, Process.myPid(), "RemoteInjectionService", "Remotely injecting a pointer (touch) event into the UI failed", userId);
        }
        if (motionEvent2 != null) {
            motionEvent2.recycle();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public final boolean injectTrackballEvent(MotionEvent motionEvent, boolean z) {
        MotionEvent motionEvent2 = null;
        EnterpriseAccessController.enforceCaller(null, "INJECT_TRACKBALL_EVENT");
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        if (isRemoteInjectionDisabled(callingUid)) {
            Slog.d("RemoteInjection", "Remote Control is disabled, couldnt inject track ball event");
            AuditLog.logAsUser(5, 4, false, Process.myPid(), "RemoteInjectionService", "Remotely injecting a trackball event into the UI failed", userId);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        }
        try {
            motionEvent2 = transformMotionEvent(motionEvent);
            MotionEvent obtain = MotionEvent.obtain(motionEvent2);
            if ((obtain.getSource() & 4) == 0) {
                obtain.setSource(65540);
            }
            z2 = InputManager.getInstance().injectInputEvent(obtain, z ? 2 : 1);
        } catch (Exception e) {
            KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(e, "Error injecting trackball event : ", "RemoteInjection");
        }
        if (z2) {
            AuditLog.logAsUser(5, 4, true, Process.myPid(), "RemoteInjectionService", "Remotely injecting a trackball event into the UI succeeded", userId);
        } else {
            AuditLog.logAsUser(5, 4, false, Process.myPid(), "RemoteInjectionService", "Remotely injecting a trackball event into the UI failed", userId);
        }
        if (motionEvent2 != null) {
            motionEvent2.recycle();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public final boolean isRemoteControlAllowed(int i) {
        try {
            Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(i, "RESTRICTION", "allowRemoteControl").iterator();
            while (it.hasNext()) {
                boolean booleanValue = ((Boolean) it.next()).booleanValue();
                if (!booleanValue) {
                    return booleanValue;
                }
            }
            return true;
        } catch (Exception unused) {
            Slog.w("RemoteInjection", "RemoteInjection.isRemoteControlAllowed() exception : ");
            return true;
        }
    }

    public final boolean isRemoteControlAllowed(ContextInfo contextInfo) {
        return isRemoteControlAllowed(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isRemoteControlDisabled(int i) {
        if (isUsingKnoxRemoteScreen()) {
            return isRemoteControlDisabledInternal(i, this.mKnoxRemoteScreenSessionOwnerUid);
        }
        return false;
    }

    public final boolean isRemoteControlDisabledInternal(int i, int i2) {
        int userId = UserHandle.getUserId(i2);
        if (userId != 0 && i != userId) {
            return true;
        }
        SparseArray sparseArray = this.mRemoteControlDisabled;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((PersonaManagerAdapter) ((IPersonaManagerAdapter) AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class))).getClass();
            if (SemPersonaManager.isAppSeparationUserId(i)) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                i = 0;
            }
            Boolean bool = (Boolean) sparseArray.get(i);
            if (bool == null) {
                return false;
            }
            return bool.booleanValue();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isRemoteInjectionDisabled(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return isRemoteControlDisabledInternal(((ActivityManager.RunningTaskInfo) ActivityManagerNative.getDefault().getTasks(1).get(0)).userId, i);
        } catch (Exception unused) {
            Log.w("RemoteInjection", "Failed to get top activity user id");
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isUsingKnoxRemoteScreen() {
        return this.mKnoxRemoteScreenSessionOwnerUid != -1;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        this.mRemoteControlDisabled.put(UserHandle.getUserId(i), Boolean.valueOf(!isRemoteControlAllowed(UserHandle.getUserId(i))));
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        new Thread(new Runnable() { // from class: com.android.server.enterprise.remotecontrol.RemoteInjectionService.1
            @Override // java.lang.Runnable
            public final void run() {
                List users = PackageManagerAdapter.getUsers(true);
                int size = users.size();
                for (int i = 0; i < size; i++) {
                    RemoteInjectionService.this.mRemoteControlDisabled.put(((UserInfo) users.get(i)).id, Boolean.valueOf(!r5.isRemoteControlAllowed(r4)));
                }
            }
        }).start();
    }

    public final MotionEvent transformMotionEvent(MotionEvent motionEvent) {
        Display defaultDisplay;
        float f;
        float f2;
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
        int i = 1;
        boolean z = semDesktopModeManager != null && semDesktopModeManager.getDesktopModeState().getEnabled() == 4;
        boolean z2 = motionEvent.getDisplayId() != 0;
        boolean z3 = z && z2;
        if (z3) {
            defaultDisplay = ((DisplayManager) this.mContext.getSystemService("display")).getDisplay(2);
        } else {
            WindowManager windowManager = this.mWindowManager;
            if (windowManager == null) {
                windowManager = (WindowManager) this.mInjector.mContext.getSystemService("window");
                this.mWindowManager = windowManager;
            }
            defaultDisplay = windowManager.getDefaultDisplay();
        }
        updateCurrentDisplayDimensions(motionEvent.getDisplayId());
        int pointerCount = motionEvent.getPointerCount();
        MotionEvent.PointerCoords[] pointerCoordsArr = new MotionEvent.PointerCoords[pointerCount];
        MotionEvent.PointerProperties[] pointerPropertiesArr = new MotionEvent.PointerProperties[pointerCount];
        int i2 = 0;
        while (i2 < pointerCount) {
            pointerCoordsArr[i2] = new MotionEvent.PointerCoords();
            pointerPropertiesArr[i2] = new MotionEvent.PointerProperties();
            motionEvent.getPointerCoords(i2, pointerCoordsArr[i2]);
            motionEvent.getPointerProperties(i2, pointerPropertiesArr[i2]);
            MotionEvent.PointerProperties pointerProperties = pointerPropertiesArr[i2];
            pointerProperties.toolType = i;
            if (i2 == 0 && z3) {
                pointerProperties.toolType = 3;
            }
            if (isUsingKnoxRemoteScreen()) {
                if (z3) {
                    f = (this.mDexScreenWidth == 0 || this.mDexScreenHeight == 0) ? isInPortrait(defaultDisplay) ? this.mCurrentDisplayWidth : this.mCurrentDisplayHeight : isInPortrait(defaultDisplay) ? this.mDexScreenWidth : this.mDexScreenHeight;
                    Log.d("RemoteInjection", "getRemoteWidthForDex() : " + f);
                } else {
                    f = isInPortrait(defaultDisplay) ? this.mRemoteScreenWidth : this.mRemoteScreenHeight;
                    Log.d("RemoteInjection", "getRemoteWidth() : " + f);
                }
                if (z3) {
                    f2 = (this.mDexScreenWidth == 0 || this.mDexScreenHeight == 0) ? isInPortrait(defaultDisplay) ? this.mCurrentDisplayHeight : this.mCurrentDisplayWidth : isInPortrait(defaultDisplay) ? this.mDexScreenHeight : this.mDexScreenWidth;
                    Log.d("RemoteInjection", "getRemoteHeightForDex() : " + f2);
                } else {
                    f2 = isInPortrait(defaultDisplay) ? this.mRemoteScreenHeight : this.mRemoteScreenWidth;
                    Log.d("RemoteInjection", "getRemoteHeight() : " + f2);
                }
                MotionEvent.PointerCoords pointerCoords = pointerCoordsArr[i2];
                pointerCoords.x = (this.mCurrentDisplayWidth / f) * pointerCoords.x;
                pointerCoords.y = (this.mCurrentDisplayHeight / f2) * pointerCoords.y;
            }
            i2++;
            i = 1;
        }
        return MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getEventTime(), motionEvent.getAction(), pointerCount, pointerPropertiesArr, pointerCoordsArr, motionEvent.getMetaState(), motionEvent.getButtonState(), motionEvent.getXPrecision(), motionEvent.getYPrecision(), motionEvent.getDeviceId(), motionEvent.getEdgeFlags(), motionEvent.getSource(), !z2 ? defaultDisplay.getDisplayId() : z ? 2 : 0, motionEvent.getFlags());
    }

    public final void updateCurrentDisplayDimensions(int i) {
        Display defaultDisplay;
        if (i != 0) {
            defaultDisplay = ((DisplayManager) this.mContext.getSystemService("display")).getDisplay(2);
        } else {
            WindowManager windowManager = this.mWindowManager;
            if (windowManager == null) {
                windowManager = (WindowManager) this.mInjector.mContext.getSystemService("window");
                this.mWindowManager = windowManager;
            }
            defaultDisplay = windowManager.getDefaultDisplay();
        }
        Point point = new Point();
        if (CoreRune.FW_FLEXIBLE_DUAL_MODE) {
            try {
                WindowManagerGlobal.getWindowManagerService().getInitialDisplaySize(i, point);
            } catch (RemoteException unused) {
                Log.e("RemoteInjection", "getInitialDisplaySize() exception!!");
            }
            this.mCurrentDisplayWidth = isInPortrait(defaultDisplay) ? point.x : point.y;
            this.mCurrentDisplayHeight = isInPortrait(defaultDisplay) ? point.y : point.x;
        } else {
            defaultDisplay.getRealSize(point);
            this.mCurrentDisplayWidth = point.x;
            this.mCurrentDisplayHeight = point.y;
        }
        StringBuilder sb = new StringBuilder("mCurrentDisplayWidth : ");
        sb.append(this.mCurrentDisplayWidth);
        sb.append(", mCurrentDisplayHeight : ");
        GestureWakeup$$ExternalSyntheticOutline0.m(sb, this.mCurrentDisplayHeight, "RemoteInjection");
    }

    public final void updateDexScreenDimensions(int i, int i2, int i3) {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new SecurityException("Can only be called by system user");
        }
        AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(i, i2, "RemoteInjectionService: updateDexScreenDimensions() width : ", ", height : ", "RemoteInjection");
        this.mKnoxRemoteScreenSessionOwnerUid = i3;
        if (isUsingKnoxRemoteScreen()) {
            this.mDexScreenWidth = i;
            this.mDexScreenHeight = i2;
        } else {
            updateCurrentDisplayDimensions(2);
            this.mDexScreenWidth = this.mCurrentDisplayWidth;
            this.mDexScreenHeight = this.mCurrentDisplayHeight;
        }
    }

    public final void updateRemoteScreenDimensionsAndCallerUid(int i, int i2, int i3) {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new SecurityException("Can only be called by system user");
        }
        UiModeManagerService$13$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "RemoteInjectionService: updateRemoteScreenDimensions() width : ", ", height : ", ", uid "), i3, "RemoteInjection");
        this.mKnoxRemoteScreenSessionOwnerUid = i3;
        if (isUsingKnoxRemoteScreen()) {
            this.mRemoteScreenWidth = i;
            this.mRemoteScreenHeight = i2;
        } else {
            updateCurrentDisplayDimensions(0);
            this.mRemoteScreenWidth = this.mCurrentDisplayWidth;
            this.mRemoteScreenHeight = this.mCurrentDisplayHeight;
        }
        try {
            if (this.mRemoteScreenWatcherCallback != null) {
                if (isUsingKnoxRemoteScreen()) {
                    this.mRemoteScreenWatcherCallback.onRemoteScreenStart();
                } else {
                    this.mRemoteScreenWatcherCallback.onRemoteScreenStop();
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.mDexScreenWidth = 0;
        this.mDexScreenHeight = 0;
    }
}
