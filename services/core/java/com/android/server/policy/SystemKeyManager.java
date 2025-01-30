package com.android.server.policy;

import android.content.ComponentName;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.wm.WindowState;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public class SystemKeyManager {
  public static final int[] SUPPORT_KEYCODE = {
    26,
    3,
    FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED,
    6,
    1064,
    224
  };
  public PhoneWindowManager mPolicy;
  public String mFocusedWindow = null;
  public int mFocusedWindowType = -1;
  public int mFocusedDisplayId = -1;
  public HashSet mMetaKeyRequestedComponents = new HashSet();
  public boolean mMetaKeyPass = false;
  public SparseArray mSystemKeyInfoMap = new SparseArray();
  public boolean mIsActivatedRecentKey = false;
  public boolean mIsActivatedHomeKey = false;

  public final class SystemKeyInfo {
    public final ComponentName componentName;
    public final int keyCode;
    public boolean keyPressOld;
    public int press;

    public SystemKeyInfo(int i, ComponentName componentName) {
      this.keyCode = i;
      this.keyPressOld = true;
      this.componentName = componentName;
    }

    public SystemKeyInfo(int i, int i2, ComponentName componentName) {
      this.keyCode = i;
      this.press = i2;
      this.componentName = componentName;
    }

    public int getKeyCode() {
      return this.keyCode;
    }

    public int getPress() {
      return this.press;
    }

    public boolean isKeyPressOld() {
      return this.keyPressOld;
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("keyCode=");
      sb.append(this.keyCode);
      if (isKeyPressOld()) {
        sb.append(" keyPressOld");
      } else {
        sb.append(" press=");
        sb.append(SystemKeyManager.keyPressToString(this.press));
      }
      sb.append(" componentName=");
      sb.append(this.componentName.toString());
      return sb.toString();
    }
  }

  public SystemKeyManager(PhoneWindowManager phoneWindowManager) {
    this.mPolicy = phoneWindowManager;
  }

  public boolean requestSystemKeyEvent(int i, ComponentName componentName, boolean z) {
    Log.v(
        "SystemKeyManager",
        "requestSystemKeyEvent() is called keyCode="
            + i
            + " componentName="
            + componentName
            + " request="
            + z);
    if (!checkValidRequestedDefaultInfo(i, -1, componentName)) {
      return false;
    }
    String flattenToString = componentName.flattenToString();
    synchronized (this) {
      boolean shouldNotifySystemKeyEvent = shouldNotifySystemKeyEvent(i, componentName, z);
      HashMap hashMap = (HashMap) this.mSystemKeyInfoMap.get(i);
      if (hashMap == null) {
        if (!z) {
          return false;
        }
        this.mSystemKeyInfoMap.put(i, new HashMap());
        hashMap = (HashMap) this.mSystemKeyInfoMap.get(i);
      }
      if (z) {
        hashMap.put(flattenToString, new SystemKeyInfo(i, componentName));
      } else {
        hashMap.remove(flattenToString);
      }
      if (shouldNotifySystemKeyEvent) {
        notifyRequestedSystemKey();
      }
      return true;
    }
  }

  public boolean isSystemKeyEventRequested(int i, ComponentName componentName) {
    if (KeyCustomizationConstants.isDebugInput()) {
      Slog.v(
          "SystemKeyManager",
          "isSystemKeyEventRequested() is called keyCode=" + i + " componentName=" + componentName);
    }
    if (componentName == null) {
      return false;
    }
    synchronized (this) {
      HashMap hashMap = (HashMap) this.mSystemKeyInfoMap.get(i);
      if (hashMap == null) {
        return false;
      }
      String findFocusedWindow = findFocusedWindow(i);
      if (TextUtils.isEmpty(findFocusedWindow)) {
        if (KeyCustomizationConstants.isDebugInput()) {
          Slog.i("SystemKeyManager", "isSystemKeyEventRequested() : focusedWindow is empty.");
        }
        return false;
      }
      SystemKeyInfo systemKeyInfo = (SystemKeyInfo) hashMap.get(findFocusedWindow);
      if (systemKeyInfo == null) {
        return false;
      }
      return systemKeyInfo.isKeyPressOld();
    }
  }

  public boolean canDispatchingKeyEvent(int i) {
    return hasSystemKeyInfoWithFocusedWindow(i, 0, true);
  }

  public boolean hasSystemKeyInfoWithKeyPressOld(int i) {
    return hasSystemKeyInfoWithFocusedWindow(i, 0, false);
  }

  public boolean hasSystemKeyInfo(int i, int i2) {
    if (KeyCustomizationConstants.isDebugInput()) {
      Slog.v(
          "SystemKeyManager",
          "hasSystemKeyInfo() is called keyCode="
              + i
              + " press="
              + keyPressToString(i2)
              + " focusedWindow="
              + this.mFocusedWindow);
    }
    return hasSystemKeyInfoWithFocusedWindow(i, i2, false);
  }

  public final boolean hasSystemKeyInfoWithFocusedWindow(int i, int i2, boolean z) {
    if (KeyCustomizationConstants.isDebugInput()) {
      Slog.v(
          "SystemKeyManager",
          "hasSystemKeyInfoWithFocusedWindow() is called keyCode="
              + i
              + " press="
              + keyPressToString(i2));
    }
    synchronized (this) {
      HashMap hashMap = (HashMap) this.mSystemKeyInfoMap.get(i);
      if (hashMap != null && hashMap.size() != 0) {
        String findFocusedWindow = findFocusedWindow(i);
        if (TextUtils.isEmpty(findFocusedWindow)) {
          KeyCustomizationConstants.isDebugInput();
          Slog.i("SystemKeyManager", "isSystemKeyEventRequested() : focusedWindow is empty.");
          return false;
        }
        if (hasPressLocked(i2, (SystemKeyInfo) hashMap.get(findFocusedWindow), z)) {
          KeyCustomizationConstants.isDebugInput();
          Slog.i(
              "SystemKeyManager",
              "hasPress() : keyCode=" + i + " focusedWindow=" + findFocusedWindow);
          return true;
        }
        Log.i(
            "SystemKeyManager",
            "requested systemKeyInfo size="
                + hashMap.size()
                + " focusedWindow="
                + findFocusedWindow);
        return false;
      }
      return false;
    }
  }

  public final String findFocusedWindow(int i) {
    String fakeFocusedWindow = getFakeFocusedWindow(i);
    return !TextUtils.isEmpty(fakeFocusedWindow) ? fakeFocusedWindow : this.mFocusedWindow;
  }

  public final boolean hasPressLocked(int i, SystemKeyInfo systemKeyInfo, boolean z) {
    if (systemKeyInfo == null) {
      return false;
    }
    return systemKeyInfo.isKeyPressOld()
        ? systemKeyInfo.getKeyCode() != 26 || (i & 4) == 0
        : z || (i & systemKeyInfo.getPress()) != 0;
  }

  public boolean isMetaKeyEventRequested(ComponentName componentName) {
    boolean contains;
    if (componentName == null) {
      return false;
    }
    synchronized (this) {
      contains = this.mMetaKeyRequestedComponents.contains(componentName.flattenToString());
    }
    return contains;
  }

  public boolean hasMetaKeyPass() {
    synchronized (this) {
      if (!this.mMetaKeyPass) {
        return false;
      }
      if (KeyCustomizationConstants.isDebugInput()) {
        Slog.i(
            "SystemKeyManager",
            "hasMetaKeyPass() : MetaKey is blocked by componentName=" + this.mFocusedWindow);
      }
      return true;
    }
  }

  public void updateFocusedWindow(String str) {
    updateFocusedWindow(str, -1, 0);
  }

  public void updateFocusedWindow(String str, int i, int i2) {
    KeyCustomizationConstants.isDebugInput();
    Slog.v("SystemKeyManager", "updateFocusedWindow() is called, " + str);
    synchronized (this) {
      this.mFocusedWindow = str;
      this.mFocusedWindowType = i;
      this.mFocusedDisplayId = i2;
      updateMetaKeyPassLocked(str);
      if (this.mSystemKeyInfoMap.size() != 0) {
        notifyRequestedSystemKey();
      }
    }
  }

  public void registerSystemKeyEvent(int i, ComponentName componentName, int i2) {
    Log.v(
        "SystemKeyManager",
        "registerSystemKeyEvent() is called keyCode="
            + i
            + " componentName="
            + componentName
            + " press="
            + keyPressToString(i2));
    checkValidRequestedDefaultInfo(i, i2, componentName);
    checkValidRequestedPress(i, i2);
    String flattenToString = componentName.flattenToString();
    synchronized (this) {
      HashMap hashMap = (HashMap) this.mSystemKeyInfoMap.get(i);
      if (hashMap == null) {
        this.mSystemKeyInfoMap.put(i, new HashMap());
        hashMap = (HashMap) this.mSystemKeyInfoMap.get(i);
      }
      hashMap.put(flattenToString, new SystemKeyInfo(i, i2, componentName));
    }
  }

  public void unregisterSystemKeyEvent(int i, ComponentName componentName) {
    Log.v(
        "SystemKeyManager",
        "unregisterSystemKeyEvent() is called keyCode=" + i + " componentName=" + componentName);
    checkValidRequestedDefaultInfo(i, 0, componentName);
    String flattenToString = componentName.flattenToString();
    synchronized (this) {
      HashMap hashMap = (HashMap) this.mSystemKeyInfoMap.get(i);
      if (hashMap == null) {
        return;
      }
      hashMap.remove(flattenToString);
    }
  }

  public final boolean checkValidRequestedDefaultInfo(int i, int i2, ComponentName componentName) {
    if (componentName == null) {
      throw new IllegalArgumentException("requested component name is null.");
    }
    if (i == 3 || i == 6 || i == 26 || i == 187 || i == 224 || i == 1064) {
      return true;
    }
    if (i2 == -1) {
      Slog.e(
          "SystemKeyManager",
          "requested keyCode was wrong. The keyCode(" + i + ") does not supported.");
      return false;
    }
    throw new IllegalArgumentException(
        "requested keyCode was wrong. The keyCode(" + i + ") does not supported.");
  }

  public final void checkValidRequestedPress(int i, int i2) {
    if ((i2 & 15) == 0 || i2 == -1) {
      throw new IllegalArgumentException(
          "requested press was wrong. The press("
              + keyPressToString(i2)
              + ") type does not supported.");
    }
    if ((i == 6 || i == 224 || i == 1064) && (i2 & 3) == 0) {
      throw new IllegalArgumentException(
          "requested press was wrong. The press("
              + keyPressToString(i2)
              + ") type does not supported.");
    }
  }

  public String getFakeFocusedWindow(int i) {
    WindowState fakeFocusedWindow;
    if ((i != 3 && i != 26 && i != 187 && i != 224)
        || (fakeFocusedWindow = this.mPolicy.mDefaultDisplayPolicy.mExt.getFakeFocusedWindow())
            == null) {
      return null;
    }
    WindowState focusedWindow = this.mPolicy.mDefaultDisplayPolicy.getFocusedWindow();
    if (focusedWindow == null || fakeFocusedWindow.getBaseLayer() > focusedWindow.getBaseLayer()) {
      return fakeFocusedWindow.getAttrs().getTitle().toString();
    }
    return null;
  }

  public final boolean shouldNotifySystemKeyEvent(int i, ComponentName componentName, boolean z) {
    return (i == 3 || i == 187) && z != isSystemKeyEventRequested(i, componentName);
  }

  public final void notifyRequestedSystemKey() {
    boolean z;
    StatusBarManagerInternal statusBarManagerInternal = this.mPolicy.getStatusBarManagerInternal();
    if (statusBarManagerInternal == null) {
      return;
    }
    boolean canDispatchingKeyEvent =
        canDispatchingKeyEvent(
            FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED);
    boolean z2 = true;
    if (this.mIsActivatedRecentKey != canDispatchingKeyEvent) {
      this.mIsActivatedRecentKey = canDispatchingKeyEvent;
      z = true;
    } else {
      z = false;
    }
    boolean canDispatchingKeyEvent2 = canDispatchingKeyEvent(3);
    if (this.mIsActivatedHomeKey != canDispatchingKeyEvent2) {
      this.mIsActivatedHomeKey = canDispatchingKeyEvent2;
    } else {
      z2 = z;
    }
    if (z2) {
      statusBarManagerInternal.notifyRequestedSystemKey(
          this.mIsActivatedRecentKey, this.mIsActivatedHomeKey);
    }
  }

  public void requestMetaKeyEvent(ComponentName componentName, boolean z) {
    Log.v(
        "SystemKeyManager",
        "requestMetaKeyEvent() : componentName=" + componentName + " request=" + z);
    String flattenToString = componentName.flattenToString();
    synchronized (this) {
      if (z) {
        this.mMetaKeyRequestedComponents.add(flattenToString);
      } else {
        this.mMetaKeyRequestedComponents.remove(flattenToString);
      }
      String str = this.mFocusedWindow;
      if (str != null && str.contains(flattenToString)) {
        updateFocusedWindow(flattenToString);
      }
    }
  }

  public final void updateMetaKeyPassLocked(String str) {
    if (this.mMetaKeyRequestedComponents.contains(str)) {
      this.mMetaKeyPass = true;
    } else if (this.mMetaKeyPass) {
      this.mMetaKeyPass = false;
    }
  }

  public void dump(String str, PrintWriter printWriter) {
    synchronized (this) {
      printWriter.print(str);
      printWriter.print("mFocusedWindow=");
      printWriter.println(this.mFocusedWindow);
      printWriter.print(str);
      printWriter.println("SystemKeyInfo=");
      for (int i : SUPPORT_KEYCODE) {
        HashMap hashMap = (HashMap) this.mSystemKeyInfoMap.get(i);
        if (hashMap != null) {
          for (Map.Entry entry : hashMap.entrySet()) {
            if (entry != null) {
              SystemKeyInfo systemKeyInfo = (SystemKeyInfo) entry.getValue();
              printWriter.print(str);
              printWriter.print("      ");
              printWriter.println(systemKeyInfo.toString());
            }
          }
        }
      }
      Iterator it = this.mMetaKeyRequestedComponents.iterator();
      while (it.hasNext()) {
        String str2 = (String) it.next();
        printWriter.print(str);
        printWriter.print("      ");
        printWriter.print("META_KEY=");
        printWriter.println(str2);
      }
    }
  }

  public static String keyPressToString(int i) {
    StringBuilder sb = new StringBuilder();
    if ((i & 3) != 0) {
      sb.append("KEY_PRESS_SINGLE");
    }
    if ((i & 4) != 0) {
      if (sb.length() != 0) {
        sb.append(" | ");
      }
      sb.append("KEY_PRESS_LONG");
    }
    if ((i & 8) != 0) {
      if (sb.length() != 0) {
        sb.append(" | ");
      }
      sb.append("KEY_PRESS_DOUBLE");
    }
    if (sb.length() == 0) {
      sb.append("0");
    }
    return sb.toString();
  }
}
