package com.android.server.enterprise.location;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.location.LocationManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.Utils;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.location.ILocationPolicy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class LocationPolicy extends ILocationPolicy.Stub implements EnterpriseServiceCallback {
  public Context mContext;
  public EnterpriseDeviceManager mEDM = null;
  public EdmStorageProvider mEdmStorageProvider;
  public LocationManager mLM;
  public final BroadcastReceiver mReceiver;
  public ContentResolver mResolver;

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void notifyToAddSystemService(String str, IBinder iBinder) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminAdded(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onPreAdminRemoval(int i) {}

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void systemReady() {}

  public LocationPolicy(Context context) {
    BroadcastReceiver broadcastReceiver =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.enterprise.location.LocationPolicy.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL"
                .equals(intent.getAction())) {
              LocationPolicy.this.updateSystemUIMonitor(
                  intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0));
            }
          }
        };
    this.mReceiver = broadcastReceiver;
    this.mContext = context;
    this.mResolver = context.getContentResolver();
    this.mEdmStorageProvider = new EdmStorageProvider(this.mContext);
    this.mContext.registerReceiver(
        broadcastReceiver,
        new IntentFilter(
            "com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL"));
  }

  public final EnterpriseDeviceManager getEDM() {
    if (this.mEDM == null) {
      this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
    }
    return this.mEDM;
  }

  public final void setLocationManager() {
    this.mLM = (LocationManager) this.mContext.getSystemService("location");
  }

  public final ContextInfo enforceLocationPermission(ContextInfo contextInfo) {
    return getEDM()
        .enforceActiveAdminPermissionByContext(
            contextInfo,
            new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_LOCATION")));
  }

  public boolean setIndividualLocationProvider(ContextInfo contextInfo, String str, boolean z) {
    Log.w(
        "LocationPolicyService",
        "LocationPolicy.setLocationProviderState - Deprecated API LEVEL 30");
    return false;
  }

  public boolean getIndividualLocationProvider(ContextInfo contextInfo, String str) {
    Log.w(
        "LocationPolicyService",
        "LocationPolicy.getIndividualLocationProvider - Deprecated API LEVEL 30");
    return true;
  }

  public boolean isLocationProviderBlocked(String str) {
    return isLocationProviderBlockedAsUser(
        str, Utils.getCallingOrCurrentUserId(new ContextInfo(Process.myUid())));
  }

  public List getAllLocationProviders(ContextInfo contextInfo) {
    enforceLocationPermission(contextInfo);
    setLocationManager();
    return this.mLM.getAllProviders();
  }

  public List getAllBlockedProvidersInUser(int i) {
    List stringListAsUser =
        this.mEdmStorageProvider.getStringListAsUser("LOCATION", "blockedProviders", i);
    ArrayList arrayList = new ArrayList();
    if (stringListAsUser != null) {
      Iterator it = stringListAsUser.iterator();
      while (it.hasNext()) {
        arrayList.addAll(Arrays.asList(((String) it.next()).split(",")));
      }
    } else {
      arrayList.add("");
    }
    return new ArrayList();
  }

  public boolean setGPSStateChangeAllowed(ContextInfo contextInfo, boolean z) {
    ContextInfo enforceLocationPermission = enforceLocationPermission(contextInfo);
    int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceLocationPermission);
    if (getPersonaManagerAdapter().isValidKnoxId(callingOrCurrentUserId)) {
      Log.d(
          "LocationPolicyService",
          "setGPSStateChangeAllowed() :failed because userid " + callingOrCurrentUserId);
      return false;
    }
    boolean putInt =
        this.mEdmStorageProvider.putInt(
            enforceLocationPermission.mCallerUid, "LOCATION", "forceProviders", !z ? 1 : 0);
    boolean isGPSStateChangeAllowedAsUser = isGPSStateChangeAllowedAsUser(callingOrCurrentUserId);
    if (putInt) {
      setGPSStateChangeAllowedSystemUI(callingOrCurrentUserId, isGPSStateChangeAllowedAsUser);
    }
    Log.d("LocationPolicyService", "setGPSStateChangeAllowed() ret = " + putInt);
    return putInt;
  }

  public final IPersonaManagerAdapter getPersonaManagerAdapter() {
    return (IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class);
  }

  public boolean isGPSStateChangeAllowed(ContextInfo contextInfo) {
    return isGPSStateChangeAllowedAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
  }

  public final boolean isGPSStateChangeAllowedAsUser(int i) {
    boolean z = false;
    try {
      Iterator it =
          this.mEdmStorageProvider.getIntListAsUser("LOCATION", "forceProviders", i).iterator();
      while (true) {
        if (!it.hasNext()) {
          break;
        }
        if (((Integer) it.next()).intValue() != 0) {
          z = true;
          break;
        }
      }
    } catch (Exception e) {
      Log.e("LocationPolicyService", "isGPSStateChangeAllowedAsUser() : failed to get value.", e);
    }
    if (z) {
      Log.i("LocationPolicyService", "isGPSStateChangeAllowedAsUser() : blocked. userId = " + i);
    }
    return !z;
  }

  public boolean startGPS(ContextInfo contextInfo, boolean z) {
    long clearCallingIdentity;
    boolean z2;
    ContextInfo enforceLocationPermission = enforceLocationPermission(contextInfo);
    int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceLocationPermission);
    List allLocationProviders = getAllLocationProviders(enforceLocationPermission);
    if (allLocationProviders == null || !allLocationProviders.contains("gps")) {
      Log.i("LocationPolicyService", "startGPS() failed. invalid provider = gps");
      return false;
    }
    if (getPersonaManagerAdapter().isValidKnoxId(callingOrCurrentUserId)
        && !isGPSStateChangeAllowedAsUser(0)) {
      Log.i(
          "LocationPolicyService",
          "startGPS() failed in container. userId = " + callingOrCurrentUserId);
      return false;
    }
    if (!isGPSStateChangeAllowed(enforceLocationPermission)) {
      Log.i("LocationPolicyService", "startGPS() failed. start = " + z);
      return false;
    }
    boolean isGPSOn = isGPSOn(enforceLocationPermission);
    if ((z && isGPSOn) || (!z && !isGPSOn)) {
      Log.i("LocationPolicyService", "startGPS() failed. same state has requested. = " + z);
      return false;
    }
    synchronized (this) {
      clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        try {
          this.mLM.setLocationEnabledForUser(z, new UserHandle(callingOrCurrentUserId));
          z2 = true;
        } catch (Exception e) {
          Log.e("LocationPolicyService", "startGPS() failed.", e);
          Binder.restoreCallingIdentity(clearCallingIdentity);
          z2 = false;
        }
      } finally {
      }
    }
    clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      AuditLog.logAsUser(
          5,
          1,
          true,
          Process.myPid(),
          "LocationPolicy",
          String.format(
              z ? "Admin %d has started GPS" : "Admin %d has stopped GPS",
              Integer.valueOf(enforceLocationPermission.mCallerUid)),
          callingOrCurrentUserId);
      Binder.restoreCallingIdentity(clearCallingIdentity);
      Log.i("LocationPolicyService", "startGPS() ret = " + z2);
      return z2;
    } finally {
    }
  }

  public boolean isGPSOn(ContextInfo contextInfo) {
    return isGPSOn(Utils.getCallingOrCurrentUserId(contextInfo));
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Type inference failed for: r1v0, types: [long] */
  /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.StringBuilder] */
  public final boolean isGPSOn(int i) {
    boolean z;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        z = this.mLM.isLocationEnabledForUser(new UserHandle(i));
      } catch (Exception e) {
        Log.e("LocationPolicyService", "isGPSOn() failed. ", e);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        z = false;
      }
      clearCallingIdentity = new StringBuilder();
      clearCallingIdentity.append("isGPSOn() ret = ");
      clearCallingIdentity.append(z);
      clearCallingIdentity.append(" , userId = ");
      clearCallingIdentity.append(i);
      Log.i("LocationPolicyService", clearCallingIdentity.toString());
      return z;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean isLocationProviderBlockedAsUser(String str, int i) {
    List allBlockedProvidersInUser = getAllBlockedProvidersInUser(i);
    if (allBlockedProvidersInUser == null || allBlockedProvidersInUser.size() <= 0) {
      return false;
    }
    return allBlockedProvidersInUser.contains(str);
  }

  @Override // com.android.server.enterprise.EnterpriseServiceCallback
  public void onAdminRemoved(int i) {
    int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
    if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
      updateSystemUIMonitor(callingOrCurrentUserId);
    }
  }

  public final void updateSystemUIMonitor(int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        setGPSStateChangeAllowedSystemUI(i, isGPSStateChangeAllowedAsUser(i));
        List<String> allLocationProviders = getAllLocationProviders(null);
        if (allLocationProviders != null) {
          for (String str : allLocationProviders) {
            setLocationProviderAllowedSystemUI(i, str, !isLocationProviderBlockedAsUser(str, i));
          }
        }
      } catch (Exception e) {
        Log.e("LocationPolicyService", "updateSystemUIMonitor() failed.", e);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void setLocationProviderAllowedSystemUI(int i, String str, boolean z) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        SystemUIAdapter.getInstance(this.mContext).setLocationProviderAllowedAsUser(i, str, z);
      } catch (Exception e) {
        Log.e("LocationPolicyService", "setLocationProviderAllowedSystemUI() failed. ", e);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void setGPSStateChangeAllowedSystemUI(int i, boolean z) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        SystemUIAdapter.getInstance(this.mContext).setGPSStateChangeAllowedAsUser(i, z);
      } catch (Exception e) {
        Log.e("LocationPolicyService", "setGPSStateChangeAllowedSystemUI() failed. ", e);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
      printWriter.println("Permission Denial: can't dump LocationPolicyService");
      return;
    }
    StringBuilder sb = new StringBuilder();
    sb.append("[LocationPolicyService]" + System.lineSeparator());
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      List users = PackageManagerAdapter.getUsers(true);
      List<String> allLocationProviders = getAllLocationProviders(null);
      Iterator it = users.iterator();
      while (it.hasNext()) {
        int i = ((UserInfo) it.next()).id;
        sb.append("  User " + i + " : " + System.lineSeparator());
        if (allLocationProviders != null && allLocationProviders.size() > 0) {
          sb.append("   LocationProviderState : " + System.lineSeparator());
          for (String str : allLocationProviders) {
            boolean z = false;
            if (!isLocationProviderBlockedAsUser(str, 0)) {
              z = true;
            }
            sb.append("    " + str + " - " + z + System.lineSeparator());
          }
        }
        sb.append(
            "   isGPSStateChangeAllowed : "
                + isGPSStateChangeAllowedAsUser(i)
                + System.lineSeparator());
        sb.append("   isGPSOn : " + isGPSOn(i) + System.lineSeparator());
      }
    } catch (Exception unused) {
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
    printWriter.write(sb.toString());
  }
}
