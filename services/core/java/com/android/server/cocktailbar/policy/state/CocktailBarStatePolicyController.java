package com.android.server.cocktailbar.policy.state;

import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.cocktailbar.CocktailBarStateInfo;
import com.samsung.android.cocktailbar.ICocktailBarStateCallback;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class CocktailBarStatePolicyController
    implements CocktailBarStatePolicy.OnCocktailBarStateListener {
  public static final String TAG = "CocktailBarStatePolicyController";
  public CocktailBarStatePolicy mPolicy;
  public static final boolean DEBUG = Debug.semIsProductDev();
  public static CocktailBarStatePolicyController mInstance = null;
  public String mPackageName = null;
  public ArrayList mStateListeners = new ArrayList();

  public static synchronized CocktailBarStatePolicyController getInstance(Context context) {
    CocktailBarStatePolicyController cocktailBarStatePolicyController;
    synchronized (CocktailBarStatePolicyController.class) {
      if (mInstance == null) {
        mInstance = new CocktailBarStatePolicyController(context);
      }
      cocktailBarStatePolicyController = mInstance;
    }
    return cocktailBarStatePolicyController;
  }

  public CocktailBarStatePolicyController(Context context) {
    OverlayCocktailBarStatePolicy overlayCocktailBarStatePolicy =
        new OverlayCocktailBarStatePolicy(context, this);
    this.mPolicy = overlayCocktailBarStatePolicy;
    overlayCocktailBarStatePolicy.initialize();
  }

  public void updateVisibility(int i) {
    if (DEBUG) {
      Slog.i(TAG, "updateVisibility: visibility = " + i);
    }
    this.mPolicy.updateVisibility(i);
  }

  public void updatePosition(int i) {
    if (DEBUG) {
      Slog.i(TAG, "updatePosition: position = " + i);
    }
    this.mPolicy.updatePosition(i);
  }

  public void updateWindowType(String str, int i) {
    int i2 = this.mPolicy.getCocktailBarStateInfo().windowType;
    if (DEBUG) {
      Slog.i(
          TAG,
          "updateWindowType: currentWindowType = "
              + i2
              + " windowType = "
              + i
              + " CP="
              + str
              + " P="
              + this.mPackageName);
    }
    if (str != null) {
      if (i != 1) {
        if (i != 2) {
          return;
        }
        String str2 = this.mPackageName;
        if (str2 == null || !str2.equals(str)) {
          this.mPackageName = str;
          this.mPolicy.updateCocktailBarWindowType(i, str);
          return;
        }
        return;
      }
      if (i2 == i) {
        Slog.w(TAG, "updateWindowType: current window type is requested window type(" + i + ")");
        return;
      }
      String str3 = this.mPackageName;
      if (str3 == null || !str3.equals(str)) {
        return;
      }
      this.mPolicy.updateCocktailBarWindowType(i, str);
      this.mPackageName = null;
    }
  }

  public void clearCocktailWindowType(String str) {
    int i = this.mPolicy.getCocktailBarStateInfo().windowType;
    if (DEBUG) {
      Slog.i(TAG, "clearCocktailWindowType: currentWindowType = " + i + ", callingPkg=" + str);
    }
    String str2 = this.mPackageName;
    if (str2 == null || !str2.equals(str)) {
      return;
    }
    this.mPackageName = null;
    this.mPolicy.updateCocktailBarWindowType(1, null);
  }

  public void activateCocktailBar() {
    if (DEBUG) {
      Slog.i(TAG, "activateCocktailBar");
    }
    this.mPolicy.updateActivate(true);
  }

  public void deactivateCocktailBar() {
    if (DEBUG) {
      Slog.i(TAG, "deactivateCocktailBar");
    }
    this.mPolicy.updateActivate(false);
  }

  public int getWindowType() {
    return this.mPolicy.getWindowType();
  }

  public CocktailBarStateInfo getCocktailBarStateInfo() {
    return this.mPolicy.getCocktailBarStateInfo();
  }

  public void registerCocktailBarStateListenerCallback(
      IBinder iBinder, ComponentName componentName) {
    synchronized (this.mStateListeners) {
      Iterator it = this.mStateListeners.iterator();
      while (it.hasNext()) {
        CocktailBarStateListenerInfo cocktailBarStateListenerInfo =
            (CocktailBarStateListenerInfo) it.next();
        if (cocktailBarStateListenerInfo != null
            && iBinder.equals(cocktailBarStateListenerInfo.token)) {
          Slog.e(TAG, "registerListenerCallback : already registered");
          return;
        }
      }
      CocktailBarStateListenerInfo cocktailBarStateListenerInfo2 =
          new CocktailBarStateListenerInfo(
              iBinder, componentName, Binder.getCallingPid(), Binder.getCallingUid());
      try {
        iBinder.linkToDeath(cocktailBarStateListenerInfo2, 0);
      } catch (RemoteException e) {
        Slog.e(TAG, "registerListenerCallback : exception in linkToDeath " + e);
      }
      this.mStateListeners.add(cocktailBarStateListenerInfo2);
      this.mPolicy.notifyStateToBinder(cocktailBarStateListenerInfo2.token);
    }
  }

  public void unregisterCocktailBarStateListenerCallback(IBinder iBinder) {
    synchronized (this.mStateListeners) {
      Iterator it = this.mStateListeners.iterator();
      CocktailBarStateListenerInfo cocktailBarStateListenerInfo = null;
      while (it.hasNext()) {
        CocktailBarStateListenerInfo cocktailBarStateListenerInfo2 =
            (CocktailBarStateListenerInfo) it.next();
        if (cocktailBarStateListenerInfo2 != null
            && iBinder.equals(cocktailBarStateListenerInfo2.token)) {
          cocktailBarStateListenerInfo = cocktailBarStateListenerInfo2;
        }
      }
      if (cocktailBarStateListenerInfo == null) {
        Slog.e(TAG, "registerListenerCallback : cannot find the matched listener");
        return;
      }
      if (!this.mStateListeners.isEmpty()) {
        this.mStateListeners.remove(cocktailBarStateListenerInfo);
      }
      iBinder.unlinkToDeath(cocktailBarStateListenerInfo, 0);
      this.mStateListeners.notify();
    }
  }

  @Override // com.android.server.cocktailbar.policy.state.CocktailBarStatePolicy.OnCocktailBarStateListener
  public boolean notifyCocktailBarState(CocktailBarStateInfo cocktailBarStateInfo) {
    if (DEBUG) {
      Slog.i(
          TAG,
          "notifyCocktailBarState: visibility = "
              + cocktailBarStateInfo.visibility
              + " position = "
              + cocktailBarStateInfo.position
              + " showtimeout = "
              + cocktailBarStateInfo.showTimeout
              + " mode = "
              + cocktailBarStateInfo.mode
              + " activate = "
              + cocktailBarStateInfo.activate
              + " windowType = "
              + cocktailBarStateInfo.windowType
              + " changeFlag = "
              + cocktailBarStateInfo.changeFlag);
    }
    synchronized (this.mStateListeners) {
      if (cocktailBarStateInfo.changeFlag == 128) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
          CocktailBarStateListenerInfo cocktailBarStateListenerInfo =
              (CocktailBarStateListenerInfo) it.next();
          String str = this.mPackageName;
          if (str == null || !str.equals(cocktailBarStateListenerInfo.component.getPackageName())) {
            cocktailBarStateListenerInfo.onCocktailBarStateChanged(cocktailBarStateInfo);
          }
        }
      } else {
        Iterator it2 = this.mStateListeners.iterator();
        while (it2.hasNext()) {
          ((CocktailBarStateListenerInfo) it2.next())
              .onCocktailBarStateChanged(cocktailBarStateInfo);
        }
      }
    }
    return true;
  }

  @Override // com.android.server.cocktailbar.policy.state.CocktailBarStatePolicy.OnCocktailBarStateListener
  public boolean notifyCocktailBarStateExceptCallingPackage(
      CocktailBarStateInfo cocktailBarStateInfo, String str) {
    if (DEBUG) {
      Slog.i(
          TAG,
          "notifyCocktailBarStateExceptCallingPackage: visibility = "
              + cocktailBarStateInfo.visibility
              + " windowType = "
              + cocktailBarStateInfo.windowType
              + " changeFlag = "
              + cocktailBarStateInfo.changeFlag);
    }
    synchronized (this.mStateListeners) {
      if (cocktailBarStateInfo.changeFlag == 128) {
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
          CocktailBarStateListenerInfo cocktailBarStateListenerInfo =
              (CocktailBarStateListenerInfo) it.next();
          if (str == null || !str.equals(cocktailBarStateListenerInfo.component.getPackageName())) {
            cocktailBarStateListenerInfo.onCocktailBarStateChanged(cocktailBarStateInfo);
          }
        }
      } else {
        Iterator it2 = this.mStateListeners.iterator();
        while (it2.hasNext()) {
          ((CocktailBarStateListenerInfo) it2.next())
              .onCocktailBarStateChanged(cocktailBarStateInfo);
        }
      }
    }
    return true;
  }

  @Override // com.android.server.cocktailbar.policy.state.CocktailBarStatePolicy.OnCocktailBarStateListener
  public boolean notifyCocktailBarStateToBinder(
      IBinder iBinder, CocktailBarStateInfo cocktailBarStateInfo) {
    if (DEBUG) {
      Slog.i(
          TAG,
          "notifyCocktailBarStateToBinder: visibility = "
              + cocktailBarStateInfo.visibility
              + " position = "
              + cocktailBarStateInfo.position
              + " showtimeout = "
              + cocktailBarStateInfo.showTimeout);
    }
    synchronized (this.mStateListeners) {
      Iterator it = this.mStateListeners.iterator();
      while (true) {
        if (!it.hasNext()) {
          break;
        }
        CocktailBarStateListenerInfo cocktailBarStateListenerInfo =
            (CocktailBarStateListenerInfo) it.next();
        if (iBinder.equals(cocktailBarStateListenerInfo.token)) {
          cocktailBarStateListenerInfo.onCocktailBarStateChanged(cocktailBarStateInfo);
          break;
        }
      }
    }
    return true;
  }

  public class CocktailBarStateListenerInfo implements IBinder.DeathRecipient {
    public final ComponentName component;
    public final int pid;
    public final IBinder token;
    public final int uid;

    public CocktailBarStateListenerInfo(
        IBinder iBinder, ComponentName componentName, int i, int i2) {
      this.token = iBinder;
      this.component = componentName;
      this.pid = i;
      this.uid = i2;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      Slog.v(CocktailBarStatePolicyController.TAG, "binderDied : binder = " + this.token);
      synchronized (CocktailBarStatePolicyController.this.mStateListeners) {
        if (CocktailBarStatePolicyController.this.mPackageName != null
            && CocktailBarStatePolicyController.this.mPackageName.equals(
                this.component.getPackageName())) {
          CocktailBarStatePolicyController.this.mPackageName = null;
          CocktailBarStatePolicyController.this.mPolicy.updateCocktailBarWindowType(1, null);
        }
        CocktailBarStatePolicyController.this.mStateListeners.remove(this);
      }
      this.token.unlinkToDeath(this, 0);
    }

    public void onCocktailBarStateChanged(CocktailBarStateInfo cocktailBarStateInfo) {
      IBinder iBinder = this.token;
      if (iBinder == null) {
        Slog.w(CocktailBarStatePolicyController.TAG, "onCocktailBarStateChanged : token is null");
        return;
      }
      try {
        ICocktailBarStateCallback asInterface = ICocktailBarStateCallback.Stub.asInterface(iBinder);
        if (asInterface != null) {
          asInterface.onCocktailBarStateChanged(cocktailBarStateInfo);
        }
      } catch (RemoteException e) {
        Slog.e(
            CocktailBarStatePolicyController.TAG,
            "onCocktailBarStateChanged : RemoteException : ",
            e);
      }
    }

    public String dump() {
      return "[CocktailBarStateListener: component:("
          + this.component.toString()
          + ") pid:("
          + this.pid
          + ") uid:("
          + this.uid
          + ")]";
    }
  }

  public String dump() {
    StringBuilder sb = new StringBuilder();
    sb.append("[CocktailBarStatePolicy] ");
    sb.append(this.mPackageName);
    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    synchronized (this.mStateListeners) {
      Iterator it = this.mStateListeners.iterator();
      while (it.hasNext()) {
        sb.append(((CocktailBarStateListenerInfo) it.next()).dump());
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      }
    }
    sb.append(this.mPolicy.dump());
    return sb.toString();
  }
}
