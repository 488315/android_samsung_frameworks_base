package com.android.server.enterprise.adapterlayer;

import android.content.Intent;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.RemoteException;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import com.android.server.enterprise.adapter.IWindowManagerAdapter;
import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;
import com.samsung.android.view.SemWindowManager;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class WindowManagerAdapter implements IWindowManagerAdapter {
  public static final List PRESS_TYPES_TO_BLOCK = Arrays.asList(2, 1);
  public static WindowManagerAdapter sInstance;
  public final IWindowManager mWindowManagerService = WindowManagerGlobal.getWindowManagerService();

  public static synchronized WindowManagerAdapter getInstance() {
    WindowManagerAdapter windowManagerAdapter;
    synchronized (WindowManagerAdapter.class) {
      if (sInstance == null) {
        sInstance = new WindowManagerAdapter();
      }
      windowManagerAdapter = sInstance;
    }
    return windowManagerAdapter;
  }

  public final void putKeyCustomizationInfo(
      int i, int i2, int i3, Intent intent, int i4, int i5, int i6) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo =
            new SemWindowManager.KeyCustomizationInfo(i5, i, i2, i3, intent, i4, i6);
        KnoxsdkFileLog.d(
            "WindowManagerAdapter",
            "putKeyCustomizationInfo with pressType : "
                + i5
                + ", "
                + keyCustomizationInfoToString(keyCustomizationInfo));
        this.mWindowManagerService.putKeyCustomizationInfo(keyCustomizationInfo);
      } catch (Exception e) {
        KnoxsdkFileLog.e("WindowManagerAdapter", "fail to putKeyCustomizationInfo " + e);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final String keyCustomizationInfoToString(
      SemWindowManager.KeyCustomizationInfo keyCustomizationInfo) {
    StringBuilder sb =
        new StringBuilder(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
    sb.append("priority/keyCode/action/dispatching/intent : ");
    sb.append(keyCustomizationInfo.id);
    sb.append("/");
    sb.append(keyCustomizationInfo.keyCode);
    sb.append("/");
    sb.append(keyCustomizationInfo.action);
    sb.append("/");
    sb.append(keyCustomizationInfo.dispatching);
    sb.append("/");
    sb.append(keyCustomizationInfo.intent);
    return sb.toString();
  }

  @Override // com.android.server.enterprise.adapter.IWindowManagerAdapter
  public void clearAllConfiguration(int i) {
    clearAllConfiguration(i, 10);
  }

  public void clearAllConfiguration(int i, int i2) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        KnoxsdkFileLog.d(
            "WindowManagerAdapter",
            "clearKeyCustomizationInfoByKeyCode with priority/keyCode : " + i2 + "/" + i);
        this.mWindowManagerService.clearKeyCustomizationInfoByAction(i2, i, 4);
      } catch (Exception e) {
        KnoxsdkFileLog.e("WindowManagerAdapter", "fail to clearAllConfiguration " + e);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  @Override // com.android.server.enterprise.adapter.IWindowManagerAdapter
  public void blockKeyCode(int i) {
    putKeyCustomizationInfo(10, i, 4, null, -1, 3, -2);
  }

  @Override // com.android.server.enterprise.adapter.IWindowManagerAdapter
  public boolean hasConfigurations(int i) {
    return hasConfigurations(i, 10);
  }

  public final boolean hasConfigurations(int i, int i2) {
    Iterator it = PRESS_TYPES_TO_BLOCK.iterator();
    while (it.hasNext()) {
      SemWindowManager.KeyCustomizationInfo keyCustomizationInfo =
          getKeyCustomizationInfo(i, (Integer) it.next(), i2);
      if (keyCustomizationInfo != null
          && keyCustomizationInfo.id == i2
          && keyCustomizationInfo.action == 4) {
        return true;
      }
    }
    return false;
  }

  public final SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfo(
      int i, Integer num, int i2) {
    SemWindowManager.KeyCustomizationInfo keyCustomizationInfo;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        keyCustomizationInfo =
            this.mWindowManagerService.getKeyCustomizationInfo(i2, num.intValue(), i);
      } catch (RemoteException e) {
        KnoxsdkFileLog.e("WindowManagerAdapter", "fail to getKeyCustomizationInfo " + e);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        keyCustomizationInfo = null;
      }
      return keyCustomizationInfo;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }
}
