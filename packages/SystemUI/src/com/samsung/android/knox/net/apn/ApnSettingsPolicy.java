package com.samsung.android.knox.net.apn;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.net.apn.IApnSettingsPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* loaded from: classes4.dex */
public class ApnSettingsPolicy {
    public static int MAXIMUM_APNS_OVER_IPC = 1000;
    public static String TAG = "ApnSettingsPolicy";
    public IApnSettingsPolicy lService;
    public ContextInfo mContextInfo;

    public ApnSettingsPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public static int generateToken(int i, int i2) {
        return new Random().nextInt((i2 - i) + 1) + i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0028, code lost:
    
        if (r2.equals(com.samsung.android.knox.net.apn.ApnSettings.PROTOCOL_IPV4) == false) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long createApnSettings(ApnSettings apnSettings) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApnSettingsPolicy.createApnSettings");
        long jAddUpdateApn = -1;
        try {
            if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 17 && apnSettings != null) {
                String str = apnSettings.protocol;
                if (str == null || str.equals(ApnSettings.PROTOCOL_IPV4)) {
                    String str2 = apnSettings.roamingProtocol;
                    if (str2 != null) {
                    }
                }
                return -1L;
            }
            if (getService() != null) {
                jAddUpdateApn = this.lService.addUpdateApn(this.mContextInfo, true, apnSettings);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at update APN Settings policy ", e);
        }
        Log.i(TAG, "createApnSettings: " + jAddUpdateApn);
        return jAddUpdateApn;
    }

    public boolean deleteApn(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApnSettingsPolicy.deleteApn");
        boolean zDeleteApn = false;
        try {
            if (getService() != null) {
                zDeleteApn = this.lService.deleteApn(this.mContextInfo, j);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at APN Settings policy API deleteApn()", e);
        }
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("deleteApn: ", TAG, zDeleteApn);
        return zDeleteApn;
    }

    public List<ApnSettings> getApnList() {
        List<ApnSettings> apnList;
        ArrayList arrayList = null;
        try {
            if (getService() == null) {
                return null;
            }
            ArrayList arrayList2 = new ArrayList();
            try {
                int iGenerateToken = generateToken(0, 100);
                do {
                    apnList = this.lService.getApnList(this.mContextInfo, iGenerateToken);
                    arrayList2.addAll(apnList);
                } while (apnList.size() == MAXIMUM_APNS_OVER_IPC);
                if (arrayList2.isEmpty()) {
                    return null;
                }
                return arrayList2;
            } catch (RemoteException e) {
                e = e;
                arrayList = arrayList2;
                Log.w(TAG, "Failed at APN Settings policy API getApnList()", e);
                return arrayList;
            }
        } catch (RemoteException e2) {
            e = e2;
        }
    }

    public ApnSettings getApnSettings(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApnSettingsPolicy.getApnSettings");
        try {
            if (getService() != null) {
                return this.lService.getApnSettings(this.mContextInfo, j);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at APN Settings policy API getApnSettings()", e);
            return null;
        }
    }

    public ApnSettings getPreferredApnSettings() {
        try {
            if (getService() != null) {
                return this.lService.getPreferredApn(this.mContextInfo);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at APN Settings policy API getPreferredApnSettings()", e);
            return null;
        }
    }

    public final IApnSettingsPolicy getService() {
        if (this.lService == null) {
            this.lService = IApnSettingsPolicy.Stub.asInterface(ServiceManager.getService("apn_settings_policy"));
        }
        return this.lService;
    }

    public boolean saveApnSettings(ApnSettings apnSettings) {
        return updateApnSettings(apnSettings);
    }

    public boolean setPreferredApn(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApnSettingsPolicy.setPreferredApn");
        boolean preferredApn = false;
        try {
            if (getService() != null) {
                preferredApn = this.lService.setPreferredApn(this.mContextInfo, j);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at APN Settings policy API setPreferredApn()", e);
        }
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("setPreferredApn: ", TAG, preferredApn);
        return preferredApn;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x002f, code lost:
    
        if (r5.equals(com.samsung.android.knox.net.apn.ApnSettings.PROTOCOL_IPV4) == false) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean updateApnSettings(ApnSettings apnSettings) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApnSettingsPolicy.updateApnSettings");
        long jAddUpdateApn = apnSettings != null ? apnSettings.id : -1L;
        try {
            if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 17 && apnSettings != null) {
                String str = apnSettings.protocol;
                if (str == null || str.equals(ApnSettings.PROTOCOL_IPV4)) {
                    String str2 = apnSettings.roamingProtocol;
                    if (str2 != null) {
                    }
                }
                return false;
            }
            if (getService() != null) {
                jAddUpdateApn = this.lService.addUpdateApn(this.mContextInfo, false, apnSettings);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at update APN Settings policy ", e);
        }
        boolean z = jAddUpdateApn != -1;
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("updateApnSettings: ", TAG, z);
        return z;
    }
}
