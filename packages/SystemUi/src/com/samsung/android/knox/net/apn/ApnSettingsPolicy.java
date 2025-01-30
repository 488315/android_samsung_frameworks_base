package com.samsung.android.knox.net.apn;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.net.apn.IApnSettingsPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ApnSettingsPolicy {
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

    public final long createApnSettings(ApnSettings apnSettings) {
        String str;
        EnterpriseLicenseManager.log(this.mContextInfo, "ApnSettingsPolicy.createApnSettings");
        long j = -1;
        try {
            if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 17 && apnSettings != null) {
                String str2 = apnSettings.protocol;
                if ((str2 != null && !str2.equals(ApnSettings.PROTOCOL_IPV4)) || ((str = apnSettings.roamingProtocol) != null && !str.equals(ApnSettings.PROTOCOL_IPV4))) {
                    return -1L;
                }
            }
            if (getService() != null) {
                j = this.lService.addUpdateApn(this.mContextInfo, true, apnSettings);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at update APN Settings policy ", e);
        }
        Log.i(TAG, "createApnSettings: " + j);
        return j;
    }

    public final boolean deleteApn(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApnSettingsPolicy.deleteApn");
        boolean z = false;
        try {
            if (getService() != null) {
                z = this.lService.deleteApn(this.mContextInfo, j);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at APN Settings policy API deleteApn()", e);
        }
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("deleteApn: ", z, TAG);
        return z;
    }

    public final List<ApnSettings> getApnList() {
        List<ApnSettings> apnList;
        ArrayList arrayList = null;
        try {
            if (getService() != null) {
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
            }
        } catch (RemoteException e2) {
            e = e2;
        }
        return arrayList;
    }

    public final ApnSettings getApnSettings(long j) {
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

    public final ApnSettings getPreferredApnSettings() {
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

    public final boolean saveApnSettings(ApnSettings apnSettings) {
        return updateApnSettings(apnSettings);
    }

    public final boolean setPreferredApn(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ApnSettingsPolicy.setPreferredApn");
        boolean z = false;
        try {
            if (getService() != null) {
                z = this.lService.setPreferredApn(this.mContextInfo, j);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at APN Settings policy API setPreferredApn()", e);
        }
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("setPreferredApn: ", z, TAG);
        return z;
    }

    public final boolean updateApnSettings(ApnSettings apnSettings) {
        String str;
        EnterpriseLicenseManager.log(this.mContextInfo, "ApnSettingsPolicy.updateApnSettings");
        long j = apnSettings != null ? apnSettings.f488id : -1L;
        try {
            if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 17 && apnSettings != null) {
                String str2 = apnSettings.protocol;
                if ((str2 != null && !str2.equals(ApnSettings.PROTOCOL_IPV4)) || ((str = apnSettings.roamingProtocol) != null && !str.equals(ApnSettings.PROTOCOL_IPV4))) {
                    return false;
                }
            }
            if (getService() != null) {
                j = this.lService.addUpdateApn(this.mContextInfo, false, apnSettings);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at update APN Settings policy ", e);
        }
        boolean z = j != -1;
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("updateApnSettings: ", z, TAG);
        return z;
    }
}
