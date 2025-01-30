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
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.LocalServices;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.common.EnterprisePermissionChecker;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.Utils;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.localservice.RemoteInjectionInternal;
import com.samsung.android.knox.remotecontrol.IRemoteInjection;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class RemoteInjectionService extends IRemoteInjection.Stub
    implements EnterpriseServiceCallback {
  public static RemoteInjectionService mSelf;
  public final Context mContext;
  public int mCurrentDisplayHeight;
  public int mCurrentDisplayWidth;
  public int mDexScreenHeight;
  public int mDexScreenWidth;
  public EnterpriseDeviceManager mEDM;
  public EdmStorageProvider mEdmStorageProvider;
  public final Injector mInjector;
  public int mKnoxRemoteScreenSessionOwnerUid;
  public SparseArray mRemoteControlDisabled;
  public int mRemoteScreenHeight;
  public int mRemoteScreenWidth;
  public WindowManager mWindowManager;

  public final int getDisplayIdDex() {
    return 2;
  }

  public final boolean isDexDisplay(int i) {
    return i != 0;
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void notifyToAddSystemService(String str, IBinder iBinder) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminAdded(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onPreAdminRemoval(int i) {}

  public RemoteInjectionService(Context context) {
    this(new Injector(context));
  }

  public RemoteInjectionService(Injector injector) {
    this.mRemoteControlDisabled = new SparseArray();
    this.mEDM = null;
    this.mEdmStorageProvider = null;
    this.mWindowManager = null;
    this.mContext = injector.mContext;
    this.mInjector = injector;
    Display defaultDisplay = getWindowManager().getDefaultDisplay();
    updateCurrentDisplayDimensions(0);
    this.mRemoteScreenWidth =
        isInPortrait(defaultDisplay) ? this.mCurrentDisplayWidth : this.mCurrentDisplayHeight;
    this.mRemoteScreenHeight =
        isInPortrait(defaultDisplay) ? this.mCurrentDisplayHeight : this.mCurrentDisplayWidth;
    this.mEdmStorageProvider = injector.getEDMStorageProvider();
    updateKnoxRemoteScreenSessionUid(-1);
    mSelf = this;
    this.mDexScreenWidth = 0;
    this.mDexScreenHeight = 0;
    LocalServices.addService(RemoteInjectionInternal.class, new LocalService());
  }

  public final boolean isInPortrait(Display display) {
    int rotation = display.getRotation();
    return rotation == 0 || rotation == 2;
  }

  public final EnterpriseDeviceManager getEDM() {
    if (this.mEDM == null) {
      this.mEDM = this.mInjector.getEnterpriseDeviceManager();
    }
    return this.mEDM;
  }

  public final ContextInfo enforceRemoteControlPermission(ContextInfo contextInfo, boolean z) {
    if (z) {
      return getEDM()
          .enforceDoPoOnlyPermissionByContext(
              contextInfo,
              new ArrayList(
                  Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION")));
    }
    return getEDM()
        .enforceActiveAdminPermissionByContext(
            contextInfo,
            new ArrayList(
                Arrays.asList("com.samsung.android.knox.permission.KNOX_REMOTE_CONTROL")));
  }

  public final void enforceRemoteControlPermissionNoActiveAdmin() {
    EnterprisePermissionChecker.getInstance(this.mContext)
        .enforceCallingOrSelfPermissions(
            new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_REMOTE_CONTROL")),
            null);
  }

  public final void enforceRemoteDexPermissionNoActiveAdmin() {
    EnterprisePermissionChecker.getInstance(this.mContext)
        .enforceCallingOrSelfPermissions(
            new ArrayList(
                Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION")),
            null);
  }

  public final void enforceSystemUser() {
    if (Binder.getCallingPid() != Process.myPid()) {
      throw new SecurityException("Can only be called by system user");
    }
  }

  public boolean injectKeyEvent(KeyEvent keyEvent, boolean z) {
    long clearCallingIdentity;
    int callingUid = Binder.getCallingUid();
    int userId = UserHandle.getUserId(callingUid);
    boolean z2 = false;
    if (keyEvent == null) {
      clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        AuditLog.logAsUser(
            5,
            4,
            false,
            Process.myPid(),
            "RemoteInjectionService",
            "Remotely injecting a keystroke event into the UI failed",
            userId);
        return false;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
    enforceRemoteControlPermissionNoActiveAdmin();
    clearCallingIdentity = Binder.clearCallingIdentity();
    if (isRemoteInjectionDisabled(callingUid)) {
      Slog.d("RemoteInjection", "Remote Control is disabled, couldnt inject key event");
      AuditLog.logAsUser(
          5,
          4,
          false,
          Process.myPid(),
          "RemoteInjectionService",
          "Remotely injecting a keystroke event into the UI failed",
          userId);
      return false;
    }
    if (keyEvent.getDisplayId() != getDisplayIdDex()) {
      keyEvent.setDisplayId(0);
    }
    try {
      z2 = injectKeyEventInternal(keyEvent, z);
    } catch (Exception e) {
      Slog.d("RemoteInjection", "Error injecting key event : " + e);
    }
    if (z2) {
      AuditLog.logAsUser(
          5,
          4,
          true,
          Process.myPid(),
          "RemoteInjectionService",
          "Remotely injecting a keystroke event into the UI succeeded",
          userId);
    } else {
      AuditLog.logAsUser(
          5,
          4,
          false,
          Process.myPid(),
          "RemoteInjectionService",
          "Remotely injecting a keystroke event into the UI failed",
          userId);
    }
    return z2;
  }

  public boolean injectPointerEvent(MotionEvent motionEvent, boolean z) {
    enforceRemoteControlPermissionNoActiveAdmin();
    int callingUid = Binder.getCallingUid();
    int userId = UserHandle.getUserId(callingUid);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    boolean z2 = false;
    if (isRemoteInjectionDisabled(callingUid)) {
      Slog.d("RemoteInjection", "Remote Control is disabled, couldnt inject pointer event");
      AuditLog.logAsUser(
          5,
          4,
          false,
          Process.myPid(),
          "RemoteInjectionService",
          "Remotely injecting a pointer (touch) event into the UI failed",
          userId);
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return false;
    }
    MotionEvent motionEvent2 = null;
    try {
      motionEvent.setDisplayId(0);
      motionEvent2 = transformMotionEvent(motionEvent);
      z2 = injectPointerEventInternal(motionEvent2, z);
    } catch (Exception e) {
      Slog.d("RemoteInjection", "Error injecting trackball event : " + e);
    }
    if (z2) {
      AuditLog.logAsUser(
          5,
          4,
          true,
          Process.myPid(),
          "RemoteInjectionService",
          "Remotely injecting a pointer (touch) event into the UI succeeded",
          userId);
    } else {
      AuditLog.logAsUser(
          5,
          4,
          false,
          Process.myPid(),
          "RemoteInjectionService",
          "Remotely injecting a pointer (touch) event into the UI failed",
          userId);
    }
    if (motionEvent2 != null) {
      motionEvent2.recycle();
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
    return z2;
  }

  /* JADX WARN: Removed duplicated region for block: B:12:0x0052  */
  /* JADX WARN: Removed duplicated region for block: B:14:0x0071  */
  /* JADX WARN: Removed duplicated region for block: B:17:0x0061  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean injectTrackballEvent(MotionEvent motionEvent, boolean z) {
    MotionEvent motionEvent2;
    enforceRemoteControlPermissionNoActiveAdmin();
    int callingUid = Binder.getCallingUid();
    int userId = UserHandle.getUserId(callingUid);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    boolean z2 = false;
    if (isRemoteInjectionDisabled(callingUid)) {
      Slog.d("RemoteInjection", "Remote Control is disabled, couldnt inject track ball event");
      AuditLog.logAsUser(
          5,
          4,
          false,
          Process.myPid(),
          "RemoteInjectionService",
          "Remotely injecting a trackball event into the UI failed",
          userId);
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return false;
    }
    try {
      motionEvent2 = transformMotionEvent(motionEvent);
    } catch (Exception e) {
      e = e;
      motionEvent2 = null;
    }
    try {
      z2 = injectTrackballEventInternal(motionEvent2, z);
    } catch (Exception e2) {
      e = e2;
      Slog.d("RemoteInjection", "Error injecting trackball event : " + e);
      if (!z2) {}
      if (motionEvent2 != null) {}
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return z2;
    }
    if (!z2) {
      AuditLog.logAsUser(
          5,
          4,
          true,
          Process.myPid(),
          "RemoteInjectionService",
          "Remotely injecting a trackball event into the UI succeeded",
          userId);
    } else {
      AuditLog.logAsUser(
          5,
          4,
          false,
          Process.myPid(),
          "RemoteInjectionService",
          "Remotely injecting a trackball event into the UI failed",
          userId);
    }
    if (motionEvent2 != null) {
      motionEvent2.recycle();
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
    return z2;
  }

  public boolean injectPointerEventDex(MotionEvent motionEvent, boolean z) {
    enforceRemoteDexPermissionNoActiveAdmin();
    int callingUid = Binder.getCallingUid();
    int userId = UserHandle.getUserId(callingUid);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    boolean z2 = false;
    if (isRemoteInjectionDisabled(callingUid)) {
      Slog.d("RemoteInjection", "Remote Control is disabled, couldnt inject pointer event");
      AuditLog.logAsUser(
          5,
          4,
          false,
          Process.myPid(),
          "RemoteInjectionService",
          "Remotely injecting a pointer (touch) event into the UI failed",
          userId);
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return false;
    }
    MotionEvent motionEvent2 = null;
    try {
      motionEvent.setDisplayId(getDisplayIdDex());
      if (motionEvent.getAction() == 0 && motionEvent.getButtonState() != 2) {
        motionEvent.setButtonState(1);
      }
      motionEvent2 = transformMotionEvent(motionEvent);
      z2 = injectPointerEventInternalDex(motionEvent2, z);
    } catch (Exception e) {
      Slog.d("RemoteInjection", "Error injecting pointer event in dex screen : " + e);
    }
    if (z2) {
      AuditLog.logAsUser(
          5,
          4,
          true,
          Process.myPid(),
          "RemoteInjectionService",
          "Remotely injecting a pointer (touch) event into the UI succeeded",
          userId);
    } else {
      AuditLog.logAsUser(
          5,
          4,
          false,
          Process.myPid(),
          "RemoteInjectionService",
          "Remotely injecting a pointer (touch) event into the UI failed",
          userId);
    }
    if (motionEvent2 != null) {
      motionEvent2.recycle();
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
    return z2;
  }

  public boolean injectKeyEventDex(KeyEvent keyEvent, boolean z) {
    enforceRemoteDexPermissionNoActiveAdmin();
    keyEvent.setDisplayId(getDisplayIdDex());
    boolean injectKeyEvent = injectKeyEvent(keyEvent, z);
    if (!injectKeyEvent) {
      Slog.d("RemoteInjection", "Error injecting Key event in dex screen");
    }
    return injectKeyEvent;
  }

  public void updateDexScreenDimensions(int i, int i2, int i3) {
    enforceSystemUser();
    Log.i(
        "RemoteInjection",
        "RemoteInjectionService: updateDexScreenDimensions() width : " + i + ", height : " + i2);
    updateKnoxRemoteScreenSessionUid(i3);
    if (isUsingKnoxRemoteScreen()) {
      this.mDexScreenWidth = i;
      this.mDexScreenHeight = i2;
    } else {
      updateCurrentDisplayDimensions(getDisplayIdDex());
      this.mDexScreenWidth = this.mCurrentDisplayWidth;
      this.mDexScreenHeight = this.mCurrentDisplayHeight;
    }
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminRemoved(int i) {
    this.mRemoteControlDisabled.put(
        UserHandle.getUserId(i), Boolean.valueOf(!isRemoteControlAllowed(UserHandle.getUserId(i))));
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void systemReady() {
    new Thread(
            new Runnable() { // from class:
                             // com.android.server.enterprise.remotecontrol.RemoteInjectionService.1
              @Override // java.lang.Runnable
              public void run() {
                List users = PackageManagerAdapter.getUsers(true);
                int size = users.size();
                for (int i = 0; i < size; i++) {
                  RemoteInjectionService.this.mRemoteControlDisabled.put(
                      ((UserInfo) users.get(i)).id,
                      Boolean.valueOf(!r5.isRemoteControlAllowed(r4)));
                }
              }
            })
        .start();
  }

  public final boolean injectKeyEventInternal(KeyEvent keyEvent, boolean z) {
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
    KeyEvent keyEvent2 =
        new KeyEvent(
            downTime,
            eventTime,
            action,
            keyCode,
            repeatCount,
            metaState,
            deviceId,
            scanCode,
            flags | 8,
            i);
    keyEvent2.setDisplayId(displayId);
    return injectInputManagerKeyEvent(keyEvent2, z);
  }

  public final boolean injectPointerEventInternal(MotionEvent motionEvent, boolean z) {
    MotionEvent obtain = MotionEvent.obtain(motionEvent);
    if ((obtain.getSource() & 2) == 0) {
      obtain.setSource(4098);
    }
    return injectInputManagerMotionEvent(obtain, z);
  }

  public final boolean injectPointerEventInternalDex(MotionEvent motionEvent, boolean z) {
    MotionEvent obtain = MotionEvent.obtain(motionEvent);
    obtain.setSource(12290);
    return injectInputManagerMotionEvent(obtain, z);
  }

  public final boolean injectTrackballEventInternal(MotionEvent motionEvent, boolean z) {
    MotionEvent obtain = MotionEvent.obtain(motionEvent);
    if ((obtain.getSource() & 4) == 0) {
      obtain.setSource(65540);
    }
    return injectInputManagerMotionEvent(obtain, z);
  }

  public final boolean injectInputManagerKeyEvent(KeyEvent keyEvent, boolean z) {
    return InputManager.getInstance().injectInputEvent(keyEvent, z ? 2 : 1);
  }

  public final boolean injectInputManagerMotionEvent(MotionEvent motionEvent, boolean z) {
    return InputManager.getInstance().injectInputEvent(motionEvent, z ? 2 : 1);
  }

  public final WindowManager getWindowManager() {
    WindowManager windowManager = this.mWindowManager;
    if (windowManager != null) {
      return windowManager;
    }
    WindowManager windowManager2 = this.mInjector.getWindowManager();
    this.mWindowManager = windowManager2;
    return windowManager2;
  }

  public final MotionEvent transformMotionEvent(MotionEvent motionEvent) {
    float remoteWidth;
    float remoteHeight;
    boolean isDexModeOn = isDexModeOn();
    boolean isDexDisplay = isDexDisplay(motionEvent.getDisplayId());
    int i = 1;
    int i2 = 0;
    boolean z = isDexModeOn && isDexDisplay;
    Display display = getDisplay(z);
    updateCurrentDisplayDimensions(motionEvent.getDisplayId());
    int pointerCount = motionEvent.getPointerCount();
    MotionEvent.PointerCoords[] pointerCoordsArr = new MotionEvent.PointerCoords[pointerCount];
    MotionEvent.PointerProperties[] pointerPropertiesArr =
        new MotionEvent.PointerProperties[pointerCount];
    int i3 = 0;
    while (i3 < pointerCount) {
      pointerCoordsArr[i3] = new MotionEvent.PointerCoords();
      pointerPropertiesArr[i3] = new MotionEvent.PointerProperties();
      motionEvent.getPointerCoords(i3, pointerCoordsArr[i3]);
      motionEvent.getPointerProperties(i3, pointerPropertiesArr[i3]);
      MotionEvent.PointerProperties pointerProperties = pointerPropertiesArr[i3];
      pointerProperties.toolType = i;
      if (i3 == 0 && z) {
        pointerProperties.toolType = 3;
      }
      if (isUsingKnoxRemoteScreen()) {
        if (z) {
          remoteWidth = getRemoteWidthForDex(display);
        } else {
          remoteWidth = getRemoteWidth(display);
        }
        if (z) {
          remoteHeight = getRemoteHeightForDex(display);
        } else {
          remoteHeight = getRemoteHeight(display);
        }
        MotionEvent.PointerCoords pointerCoords = pointerCoordsArr[i3];
        pointerCoords.x *= this.mCurrentDisplayWidth / remoteWidth;
        pointerCoords.y *= this.mCurrentDisplayHeight / remoteHeight;
      }
      i3++;
      i = 1;
    }
    if (!isDexDisplay) {
      i2 = display.getDisplayId();
    } else if (isDexModeOn) {
      i2 = getDisplayIdDex();
    }
    return MotionEvent.obtain(
        motionEvent.getDownTime(),
        motionEvent.getEventTime(),
        motionEvent.getAction(),
        pointerCount,
        pointerPropertiesArr,
        pointerCoordsArr,
        motionEvent.getMetaState(),
        motionEvent.getButtonState(),
        motionEvent.getXPrecision(),
        motionEvent.getYPrecision(),
        motionEvent.getDeviceId(),
        motionEvent.getEdgeFlags(),
        motionEvent.getSource(),
        i2,
        motionEvent.getFlags());
  }

  public final float getRemoteWidth(Display display) {
    float f = isInPortrait(display) ? this.mRemoteScreenWidth : this.mRemoteScreenHeight;
    Log.d("RemoteInjection", "getRemoteWidth() : " + f);
    return f;
  }

  public final float getRemoteWidthForDex(Display display) {
    int i;
    if (this.mDexScreenWidth == 0 || this.mDexScreenHeight == 0) {
      i = isInPortrait(display) ? this.mCurrentDisplayWidth : this.mCurrentDisplayHeight;
    } else {
      i = isInPortrait(display) ? this.mDexScreenWidth : this.mDexScreenHeight;
    }
    float f = i;
    Log.d("RemoteInjection", "getRemoteWidthForDex() : " + f);
    return f;
  }

  public final float getRemoteHeight(Display display) {
    float f = isInPortrait(display) ? this.mRemoteScreenHeight : this.mRemoteScreenWidth;
    Log.d("RemoteInjection", "getRemoteHeight() : " + f);
    return f;
  }

  public final float getRemoteHeightForDex(Display display) {
    int i;
    if (this.mDexScreenWidth == 0 || this.mDexScreenHeight == 0) {
      i = isInPortrait(display) ? this.mCurrentDisplayHeight : this.mCurrentDisplayWidth;
    } else {
      i = isInPortrait(display) ? this.mDexScreenHeight : this.mDexScreenWidth;
    }
    float f = i;
    Log.d("RemoteInjection", "getRemoteHeightForDex() : " + f);
    return f;
  }

  public final void updateCurrentDisplayDimensions(int i) {
    Display display = getDisplay(isDexDisplay(i));
    Point point = new Point();
    display.getRealSize(point);
    this.mCurrentDisplayWidth = point.x;
    this.mCurrentDisplayHeight = point.y;
    Log.d(
        "RemoteInjection",
        "mCurrentDisplayWidth : "
            + this.mCurrentDisplayWidth
            + ", mCurrentDisplayHeight : "
            + this.mCurrentDisplayHeight);
  }

  public void updateRemoteScreenDimensionsAndCallerUid(int i, int i2, int i3) {
    enforceSystemUser();
    Log.i(
        "RemoteInjection",
        "RemoteInjectionService: updateRemoteScreenDimensions() width : "
            + i
            + ", height : "
            + i2
            + ", uid "
            + i3);
    updateKnoxRemoteScreenSessionUid(i3);
    if (isUsingKnoxRemoteScreen()) {
      this.mRemoteScreenWidth = i;
      this.mRemoteScreenHeight = i2;
    } else {
      updateCurrentDisplayDimensions(0);
      this.mRemoteScreenWidth = this.mCurrentDisplayWidth;
      this.mRemoteScreenHeight = this.mCurrentDisplayHeight;
    }
    this.mDexScreenWidth = 0;
    this.mDexScreenHeight = 0;
  }

  public final void updateKnoxRemoteScreenSessionUid(int i) {
    this.mKnoxRemoteScreenSessionOwnerUid = i;
  }

  public final boolean isUsingKnoxRemoteScreen() {
    return this.mKnoxRemoteScreenSessionOwnerUid != -1;
  }

  public boolean allowRemoteControl(ContextInfo contextInfo, boolean z, boolean z2) {
    int i = enforceRemoteControlPermission(contextInfo, z2).mCallerUid;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        boolean putBoolean =
            this.mEdmStorageProvider.putBoolean(i, "RESTRICTION", "allowRemoteControl", z);
        if (putBoolean) {
          try {
            int userId = UserHandle.getUserId(i);
            this.mRemoteControlDisabled.put(
                userId, Boolean.valueOf(isRemoteControlAllowed(userId) ? false : true));
          } catch (Exception unused) {
            r8 = putBoolean;
            Slog.w("RemoteInjection", "RemoteInjection.allowRemoteControl() exception : ");
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return r8;
          }
        }
        return putBoolean;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    } catch (Exception unused2) {
    }
  }

  public boolean isRemoteControlAllowed(int i) {
    try {
      Iterator it =
          this.mEdmStorageProvider
              .getBooleanListAsUser("RESTRICTION", "allowRemoteControl", i)
              .iterator();
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

  public boolean isRemoteControlAllowed(ContextInfo contextInfo) {
    return isRemoteControlAllowed(Utils.getCallingOrCurrentUserId(contextInfo));
  }

  public boolean isRemoteControlDisabled(int i) {
    if (isUsingKnoxRemoteScreen()) {
      return isRemoteControlDisabledInternal(i, this.mKnoxRemoteScreenSessionOwnerUid);
    }
    return false;
  }

  public final boolean isRemoteInjectionDisabled(int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return isRemoteControlDisabledInternal(
          ((ActivityManager.RunningTaskInfo) ActivityManagerNative.getDefault().getTasks(1).get(0))
              .userId,
          i);
    } catch (Exception unused) {
      Log.w("RemoteInjection", "Failed to get top activity user id");
      return false;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final boolean isRemoteControlDisabledInternal(int i, int i2) {
    int userId = UserHandle.getUserId(i2);
    if (userId != 0 && i != userId) {
      return true;
    }
    Boolean bool = (Boolean) this.mRemoteControlDisabled.get(getUserIdToCheckPolicy(i));
    if (bool == null) {
      return false;
    }
    return bool.booleanValue();
  }

  public final int getUserIdToCheckPolicy(int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      if (!getPersonaManagerAdapter().isAppSeparationUserId(i)) {
        return i;
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return 0;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final IPersonaManagerAdapter getPersonaManagerAdapter() {
    return (IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class);
  }

  public final Display getDisplay(boolean z) {
    if (z) {
      return ((DisplayManager) this.mContext.getSystemService("display"))
          .getDisplay(getDisplayIdDex());
    }
    return getWindowManager().getDefaultDisplay();
  }

  public final boolean isDexModeOn() {
    SemDesktopModeManager semDesktopModeManager =
        (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
    return semDesktopModeManager != null
        && semDesktopModeManager.getDesktopModeState().getEnabled() == 4;
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
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
      printWriter.println("None");
    }
  }

  public class Injector {
    public final Context mContext;

    public Injector(Context context) {
      this.mContext = context;
    }

    public WindowManager getWindowManager() {
      return (WindowManager) this.mContext.getSystemService("window");
    }

    public EdmStorageProvider getEDMStorageProvider() {
      return new EdmStorageProvider(this.mContext);
    }

    public EnterpriseDeviceManager getEnterpriseDeviceManager() {
      return EnterpriseDeviceManager.getInstance(this.mContext);
    }
  }

  public class LocalService extends RemoteInjectionInternal {
    public LocalService() {}

    public boolean isRemoteControlDisabled(int i) {
      return RemoteInjectionService.this.isRemoteControlDisabled(i);
    }
  }
}
