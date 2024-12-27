package com.android.server.enterprise.location;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
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

import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.Utils;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.location.ILocationPolicy;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LocationPolicy extends ILocationPolicy.Stub
        implements EnterpriseServiceCallback {
    public final Context mContext;
    public EnterpriseDeviceManager mEDM = null;
    public final EdmStorageProvider mEdmStorageProvider;
    public LocationManager mLM;
    public final AnonymousClass1 mReceiver;

    public LocationPolicy(Context context) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class:
                    // com.android.server.enterprise.location.LocationPolicy.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL"
                                .equals(intent.getAction())) {
                            LocationPolicy.this.updateSystemUIMonitor$4(
                                    intent.getIntExtra(
                                            "com.samsung.android.knox.intent.extra.USER_ID_INTERNAL",
                                            0));
                        }
                    }
                };
        this.mContext = context;
        context.getContentResolver();
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        context.registerReceiver(
                broadcastReceiver,
                new IntentFilter(
                        "com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL"),
                2);
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
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
                        sb.append(
                                "    "
                                        + str
                                        + " - "
                                        + (!isLocationProviderBlockedAsUser(str, 0))
                                        + System.lineSeparator());
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

    public final ContextInfo enforceLocationPermission(ContextInfo contextInfo) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM.enforceActiveAdminPermissionByContext(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_LOCATION")));
    }

    public final List getAllLocationProviders(ContextInfo contextInfo) {
        enforceLocationPermission(contextInfo);
        LocationManager locationManager =
                (LocationManager) this.mContext.getSystemService("location");
        this.mLM = locationManager;
        return locationManager.getAllProviders();
    }

    public final boolean getIndividualLocationProvider(ContextInfo contextInfo, String str) {
        Log.w(
                "LocationPolicyService",
                "LocationPolicy.getIndividualLocationProvider - Deprecated API LEVEL 30");
        return true;
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
            clearCallingIdentity = new StringBuilder("isGPSOn() ret = ");
            clearCallingIdentity.append(z);
            clearCallingIdentity.append(" , userId = ");
            clearCallingIdentity.append(i);
            Log.i("LocationPolicyService", clearCallingIdentity.toString());
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isGPSOn(ContextInfo contextInfo) {
        return isGPSOn(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isGPSStateChangeAllowed(ContextInfo contextInfo) {
        return isGPSStateChangeAllowedAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isGPSStateChangeAllowedAsUser(int i) {
        boolean z = false;
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getIntListAsUser(0, i, "LOCATION", "forceProviders")
                            .iterator();
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
            Log.e(
                    "LocationPolicyService",
                    "isGPSStateChangeAllowedAsUser() : failed to get value.",
                    e);
        }
        if (z) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i,
                    "isGPSStateChangeAllowedAsUser() : blocked. userId = ",
                    "LocationPolicyService");
        }
        return !z;
    }

    public final boolean isLocationProviderBlocked(String str) {
        return isLocationProviderBlockedAsUser(
                str, Utils.getCallingOrCurrentUserId(new ContextInfo(Process.myUid())));
    }

    public final boolean isLocationProviderBlockedAsUser(String str, int i) {
        List stringListAsUser =
                this.mEdmStorageProvider.getStringListAsUser(i, "LOCATION", "blockedProviders");
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) stringListAsUser).iterator();
        while (it.hasNext()) {
            arrayList.addAll(Arrays.asList(((String) it.next()).split(",")));
        }
        ArrayList arrayList2 = new ArrayList();
        if (arrayList2.size() > 0) {
            return arrayList2.contains(str);
        }
        return false;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            updateSystemUIMonitor$4(callingOrCurrentUserId);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {}

    public final boolean setGPSStateChangeAllowed(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceLocationPermission = enforceLocationPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceLocationPermission);
        ((PersonaManagerAdapter)
                        ((IPersonaManagerAdapter)
                                AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class)))
                .getClass();
        if (SemPersonaManager.isKnoxId(callingOrCurrentUserId)) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    callingOrCurrentUserId,
                    "setGPSStateChangeAllowed() :failed because userid ",
                    "LocationPolicyService");
            return false;
        }
        boolean putInt =
                this.mEdmStorageProvider.putInt(
                        enforceLocationPermission.mCallerUid,
                        0,
                        !z ? 1 : 0,
                        "LOCATION",
                        "forceProviders");
        boolean isGPSStateChangeAllowedAsUser =
                isGPSStateChangeAllowedAsUser(callingOrCurrentUserId);
        if (putInt) {
            setGPSStateChangeAllowedSystemUI(callingOrCurrentUserId, isGPSStateChangeAllowedAsUser);
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "setGPSStateChangeAllowed() ret = ", "LocationPolicyService", putInt);
        return putInt;
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

    public final boolean setIndividualLocationProvider(
            ContextInfo contextInfo, String str, boolean z) {
        Log.w(
                "LocationPolicyService",
                "LocationPolicy.setLocationProviderState - Deprecated API LEVEL 30");
        return false;
    }

    public final void setLocationProviderAllowedSystemUI(int i, String str, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext)
                        .setLocationProviderAllowedAsUser(i, str, z);
            } catch (Exception e) {
                Log.e("LocationPolicyService", "setLocationProviderAllowedSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean startGPS(ContextInfo contextInfo, boolean z) {
        long clearCallingIdentity;
        ContextInfo enforceLocationPermission = enforceLocationPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceLocationPermission);
        List allLocationProviders = getAllLocationProviders(enforceLocationPermission);
        boolean z2 = false;
        if (allLocationProviders == null || !allLocationProviders.contains("gps")) {
            Log.i("LocationPolicyService", "startGPS() failed. invalid provider = gps");
            return false;
        }
        ((PersonaManagerAdapter)
                        ((IPersonaManagerAdapter)
                                AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class)))
                .getClass();
        if (SemPersonaManager.isKnoxId(callingOrCurrentUserId)
                && !isGPSStateChangeAllowedAsUser(0)) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    callingOrCurrentUserId,
                    "startGPS() failed in container. userId = ",
                    "LocationPolicyService");
            return false;
        }
        if (!isGPSStateChangeAllowed(enforceLocationPermission)) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "startGPS() failed. start = ", "LocationPolicyService", z);
            return false;
        }
        boolean isGPSOn = isGPSOn(enforceLocationPermission);
        if ((z && isGPSOn) || (!z && !isGPSOn)) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "startGPS() failed. same state has requested. = ", "LocationPolicyService", z);
            return false;
        }
        synchronized (this) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    this.mLM.setLocationEnabledForUser(z, new UserHandle(callingOrCurrentUserId));
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    z2 = true;
                } finally {
                }
            } catch (Exception e) {
                Log.e("LocationPolicyService", "startGPS() failed.", e);
            }
        }
        clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logEventAsUser(
                    callingOrCurrentUserId,
                    z ? 40 : 41,
                    new Object[] {Integer.valueOf(enforceLocationPermission.mCallerUid)});
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.i("LocationPolicyService", "startGPS() ret = " + z2);
            return z2;
        } finally {
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {}

    public final void updateSystemUIMonitor$4(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                setGPSStateChangeAllowedSystemUI(i, isGPSStateChangeAllowedAsUser(i));
                List allLocationProviders = getAllLocationProviders(null);
                if (allLocationProviders != null) {
                    Iterator it = allLocationProviders.iterator();
                    while (it.hasNext()) {
                        setLocationProviderAllowedSystemUI(
                                i, (String) it.next(), !isLocationProviderBlockedAsUser(r3, i));
                    }
                }
            } catch (Exception e) {
                Log.e("LocationPolicyService", "updateSystemUIMonitor() failed.", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
