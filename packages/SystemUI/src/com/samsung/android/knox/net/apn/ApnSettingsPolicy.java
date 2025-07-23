package com.samsung.android.knox.net.apn;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.net.apn.IApnSettingsPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long createApnSettings(com.samsung.android.knox.net.apn.ApnSettings r5) {
        /*
            r4 = this;
            com.samsung.android.knox.ContextInfo r0 = r4.mContextInfo
            java.lang.String r1 = "ApnSettingsPolicy.createApnSettings"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r0, r1)
            r0 = -1
            int r2 = com.samsung.android.knox.KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION     // Catch: android.os.RemoteException -> L1e
            r3 = 17
            if (r2 >= r3) goto L2b
            if (r5 == 0) goto L2b
            java.lang.String r2 = r5.protocol     // Catch: android.os.RemoteException -> L1e
            java.lang.String r3 = "IP"
            if (r2 == 0) goto L20
            boolean r2 = r2.equals(r3)     // Catch: android.os.RemoteException -> L1e
            if (r2 == 0) goto L2a
            goto L20
        L1e:
            r4 = move-exception
            goto L3b
        L20:
            java.lang.String r2 = r5.roamingProtocol     // Catch: android.os.RemoteException -> L1e
            if (r2 == 0) goto L2b
            boolean r2 = r2.equals(r3)     // Catch: android.os.RemoteException -> L1e
            if (r2 != 0) goto L2b
        L2a:
            return r0
        L2b:
            com.samsung.android.knox.net.apn.IApnSettingsPolicy r2 = r4.getService()     // Catch: android.os.RemoteException -> L1e
            if (r2 == 0) goto L42
            com.samsung.android.knox.net.apn.IApnSettingsPolicy r2 = r4.lService     // Catch: android.os.RemoteException -> L1e
            com.samsung.android.knox.ContextInfo r4 = r4.mContextInfo     // Catch: android.os.RemoteException -> L1e
            r3 = 1
            long r0 = r2.addUpdateApn(r4, r3, r5)     // Catch: android.os.RemoteException -> L1e
            goto L42
        L3b:
            java.lang.String r5 = com.samsung.android.knox.net.apn.ApnSettingsPolicy.TAG
            java.lang.String r2 = "Failed at update APN Settings policy "
            android.util.Log.w(r5, r2, r4)
        L42:
            java.lang.String r4 = com.samsung.android.knox.net.apn.ApnSettingsPolicy.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r2 = "createApnSettings: "
            r5.<init>(r2)
            r5.append(r0)
            java.lang.String r5 = r5.toString()
            android.util.Log.i(r4, r5)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.net.apn.ApnSettingsPolicy.createApnSettings(com.samsung.android.knox.net.apn.ApnSettings):long");
    }

    public boolean deleteApn(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApnSettingsPolicy.deleteApn");
        boolean z = false;
        try {
            if (getService() != null) {
                z = this.lService.deleteApn(this.mContextInfo, j);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at APN Settings policy API deleteApn()", e);
        }
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("deleteApn: ", TAG, z);
        return z;
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
                int generateToken = generateToken(0, 100);
                do {
                    apnList = this.lService.getApnList(this.mContextInfo, generateToken);
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
        boolean z = false;
        try {
            if (getService() != null) {
                z = this.lService.setPreferredApn(this.mContextInfo, j);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at APN Settings policy API setPreferredApn()", e);
        }
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("setPreferredApn: ", TAG, z);
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x002f, code lost:
    
        if (r5.equals(com.samsung.android.knox.net.apn.ApnSettings.PROTOCOL_IPV4) == false) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean updateApnSettings(com.samsung.android.knox.net.apn.ApnSettings r8) {
        /*
            r7 = this;
            com.samsung.android.knox.ContextInfo r0 = r7.mContextInfo
            java.lang.String r1 = "ApnSettingsPolicy.updateApnSettings"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r0, r1)
            r0 = -1
            if (r8 == 0) goto Le
            long r2 = r8.id
            goto Lf
        Le:
            r2 = r0
        Lf:
            r4 = 0
            int r5 = com.samsung.android.knox.KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION     // Catch: android.os.RemoteException -> L25
            r6 = 17
            if (r5 >= r6) goto L32
            if (r8 == 0) goto L32
            java.lang.String r5 = r8.protocol     // Catch: android.os.RemoteException -> L25
            java.lang.String r6 = "IP"
            if (r5 == 0) goto L27
            boolean r5 = r5.equals(r6)     // Catch: android.os.RemoteException -> L25
            if (r5 == 0) goto L31
            goto L27
        L25:
            r7 = move-exception
            goto L41
        L27:
            java.lang.String r5 = r8.roamingProtocol     // Catch: android.os.RemoteException -> L25
            if (r5 == 0) goto L32
            boolean r5 = r5.equals(r6)     // Catch: android.os.RemoteException -> L25
            if (r5 != 0) goto L32
        L31:
            return r4
        L32:
            com.samsung.android.knox.net.apn.IApnSettingsPolicy r5 = r7.getService()     // Catch: android.os.RemoteException -> L25
            if (r5 == 0) goto L48
            com.samsung.android.knox.net.apn.IApnSettingsPolicy r5 = r7.lService     // Catch: android.os.RemoteException -> L25
            com.samsung.android.knox.ContextInfo r7 = r7.mContextInfo     // Catch: android.os.RemoteException -> L25
            long r2 = r5.addUpdateApn(r7, r4, r8)     // Catch: android.os.RemoteException -> L25
            goto L48
        L41:
            java.lang.String r8 = com.samsung.android.knox.net.apn.ApnSettingsPolicy.TAG
            java.lang.String r5 = "Failed at update APN Settings policy "
            android.util.Log.w(r8, r5, r7)
        L48:
            int r7 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r7 == 0) goto L4d
            r4 = 1
        L4d:
            java.lang.String r7 = com.samsung.android.knox.net.apn.ApnSettingsPolicy.TAG
            java.lang.String r8 = "updateApnSettings: "
            com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m(r8, r7, r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.net.apn.ApnSettingsPolicy.updateApnSettings(com.samsung.android.knox.net.apn.ApnSettings):boolean");
    }
}
