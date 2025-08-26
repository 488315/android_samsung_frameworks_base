package com.samsung.android.knox;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;
import android.util.Log;
import com.sec.android.iaft.SmLib_IafdConstant;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes6.dex */
public class KnoxInfoImpl {
    static final long INTERVAL_NANO_SEC = 3000000000L;
    static final String TAG = "KnoxInfoImpl";
    private static Bundle mKnoxInfo = null;
    private static boolean m_bIsKnoxInfoInitialized = false;
    static HashMap<String, Bundle> cached_knox_info = new HashMap<>();
    static HashMap<String, Long> cachedTime = new HashMap<>();

    public static synchronized Bundle getCachedKnoxInfo(Context context, String str) {
        if (cachedTime.containsKey(str) && System.nanoTime() - cachedTime.get(str).longValue() < INTERVAL_NANO_SEC) {
            return cached_knox_info.get(str);
        }
        Log.d(TAG, "put into cache");
        Bundle knoxInfoForApp = getKnoxInfoForApp(context, str);
        cachedTime.put(str, Long.valueOf(System.nanoTime()));
        cached_knox_info.put(str, knoxInfoForApp);
        return knoxInfoForApp;
    }

    public static Bundle getKnoxInfo() {
        synchronized (KnoxInfoImpl.class) {
            if (mKnoxInfo == null) {
                Bundle bundle = new Bundle();
                mKnoxInfo = bundle;
                try {
                    bundle.putString("version", "2.0");
                    mKnoxInfo.putString("isSupportCallerInfo", "false");
                } catch (Exception e) {
                    Log.e(TAG, "failed to putString to mKnoxInfo", e);
                    mKnoxInfo.putString("version", "");
                }
            }
        }
        return mKnoxInfo;
    }

    private static String getPersonalModeName(int i) {
        if (SemPersonaManager.getPersonaService() == null) {
            return null;
        }
        try {
            return SemPersonaManager.getPersonaService().getPersonalModeName(i);
        } catch (Exception e) {
            Log.e(TAG, "getPersonalModeName failed", e);
            return null;
        }
    }

    public static Bundle getKnoxInfoForApp(Context context, String str) {
        int focusedKnoxId;
        synchronized (KnoxInfoImpl.class) {
            if (mKnoxInfo == null) {
                getKnoxInfo();
            }
            int iMyUserId = UserHandle.myUserId();
            try {
                mKnoxInfo.putInt(SmLib_IafdConstant.KEY_USER_ID, iMyUserId);
                if (!m_bIsKnoxInfoInitialized) {
                    if (SemPersonaManager.isKnoxId(iMyUserId)) {
                        mKnoxInfo.putString("isKnoxMode", "true");
                        IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
                        if (service != null) {
                            if (service.isPackageAllowedToAccessExternalSdcard(iMyUserId, Binder.getCallingUid())) {
                                mKnoxInfo.putString("isBlockExternalSD", "false");
                            } else {
                                mKnoxInfo.putString("isBlockExternalSD", "true");
                            }
                        } else {
                            Log.e(TAG, "getService() returns null, set isBlockExternalSD to true");
                            mKnoxInfo.putString("isBlockExternalSD", "true");
                        }
                        mKnoxInfo.putString("isBlockBluetoothMenu", "true");
                        mKnoxInfo.putString("isSamsungAccountBlocked", "true");
                    }
                    if (SemPersonaManager.isDoEnabled(iMyUserId)) {
                        IEDMProxy service2 = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
                        if (service2 != null) {
                            if (service2.isPackageAllowedToAccessExternalSdcard(iMyUserId, Binder.getCallingUid())) {
                                mKnoxInfo.putString("isBlockExternalSD", "false");
                            } else {
                                mKnoxInfo.putString("isBlockExternalSD", "true");
                            }
                        } else {
                            Log.e(TAG, "getService() returns null, set isBlockExternalSD to false. (DO)");
                            mKnoxInfo.putString("isBlockExternalSD", "false");
                        }
                    }
                    if (SemPersonaManager.isSecureFolderId(iMyUserId)) {
                        mKnoxInfo.putString("isBlockExternalSD", "true");
                    }
                    mKnoxInfo.putString("isKioskModeEnabled", "false");
                    m_bIsKnoxInfoInitialized = true;
                }
                if ("isSupportMoveTo".equals(str)) {
                    Log.e(TAG, "ERROR | invalid request, isSupportMoveTo");
                }
                if ("isKnoxModeActive".equals(str)) {
                    if (SemPersonaManager.isKnoxId(ActivityManager.getCurrentUser())) {
                        mKnoxInfo.putString("isKnoxModeActive", "true");
                    } else {
                        mKnoxInfo.putString("isKnoxModeActive", "false");
                    }
                }
                if ("isSecureFolderExist".equals(str)) {
                    if (SemPersonaManager.getSecureFolderId(context) > 0) {
                        mKnoxInfo.putString("isSecureFolderExist", "true");
                    } else {
                        mKnoxInfo.putString("isSecureFolderExist", "false");
                    }
                }
                if ("isSmartSwitchBnRAvailable".equals(str)) {
                    if (SemPersonaManager.getSecureFolderId(context) > 0) {
                        mKnoxInfo.putString("isSecureFolderExist", "true");
                    } else {
                        mKnoxInfo.putString("isSecureFolderExist", "false");
                    }
                }
                if ("getContainerLabel".equals(str)) {
                    mKnoxInfo.putString("getContainerLabel", SemPersonaManager.getPersonaName(context, iMyUserId == 0 ? getWorkProfileUserId() : iMyUserId));
                }
                if ("getContainerAppIcon".equals(str)) {
                    mKnoxInfo.putByteArray("getContainerAppIcon", SemPersonaManager.getKnoxIcon(iMyUserId));
                }
                if ("getPersonalModeLabel".equals(str)) {
                    String personalModeName = getPersonalModeName(iMyUserId);
                    if (personalModeName != null && personalModeName.length() == 0) {
                        personalModeName = null;
                    }
                    mKnoxInfo.putString("getPersonalModeLabel", personalModeName);
                }
                if ("getActiveUserId".equals(str)) {
                    SemPersonaManager personaService = SemPersonaManager.getPersonaService(context);
                    if (personaService != null) {
                        try {
                            focusedKnoxId = personaService.getFocusedKnoxId();
                        } catch (Exception e) {
                            Log.e(TAG, "failed to get focused Knox id", e);
                        }
                        mKnoxInfo.putInt("getActiveUserId", focusedKnoxId);
                    } else {
                        focusedKnoxId = 0;
                        mKnoxInfo.putInt("getActiveUserId", focusedKnoxId);
                    }
                }
                if ("getWorkInfo".equals(str)) {
                    SemPersonaManager personaService2 = SemPersonaManager.getPersonaService(context);
                    if (!SemPersonaManager.isDoEnabled(0) && personaService2 != null) {
                        try {
                            List<Integer> knoxIds = personaService2.getKnoxIds(false);
                            if (knoxIds != null && knoxIds.size() != 0) {
                                for (int i = 0; i < knoxIds.size(); i++) {
                                    int iIntValue = knoxIds.get(i).intValue();
                                    if (iIntValue != 0 && iIntValue < 150) {
                                        mKnoxInfo.putInt("getWorkId", knoxIds.get(i).intValue());
                                    }
                                }
                            }
                        } catch (Exception e2) {
                            Log.e(TAG, "failed getWorkInfo:", e2);
                        }
                    }
                }
                if ("getAllPersonaInfo".equals(str)) {
                    mKnoxInfo.putInt("getContainerCount", 0);
                    SemPersonaManager personaService3 = SemPersonaManager.getPersonaService(context);
                    if (personaService3 != null) {
                        try {
                            List<Integer> knoxIds2 = personaService3.getKnoxIds(false);
                            if (knoxIds2 != null && knoxIds2.size() != 0) {
                                mKnoxInfo.putInt("getContainerCount", knoxIds2.size());
                                for (int i2 = 0; i2 < knoxIds2.size(); i2++) {
                                    int iIntValue2 = knoxIds2.get(i2).intValue();
                                    byte[] knoxIcon = SemPersonaManager.getKnoxIcon(iIntValue2);
                                    String personaName = SemPersonaManager.getPersonaName(context, iIntValue2);
                                    if (SemPersonaManager.isKnoxId(iIntValue2)) {
                                        mKnoxInfo.putInt("getContainerOrder_" + i2, 1);
                                    } else {
                                        Log.e(TAG, "getUserInfo returns null");
                                        mKnoxInfo.putInt("getContainerOrder_" + i2, 0);
                                    }
                                    mKnoxInfo.putInt("getContainerId_" + i2, iIntValue2);
                                    mKnoxInfo.putString("getContainerLabel_" + i2, personaName);
                                    mKnoxInfo.putByteArray("getContainerAppIcon_" + i2, knoxIcon);
                                    mKnoxInfo.putBoolean("isSecureFolder_" + i2, SemPersonaManager.isSecureFolderId(iIntValue2));
                                }
                            }
                        } catch (Exception e3) {
                            Log.e(TAG, "failed to get container info:", e3);
                            mKnoxInfo.putInt("getContainerCount", 0);
                        }
                    }
                }
                if ("isSupportSecureFolder".equals(str)) {
                    SemPersonaManager personaService4 = SemPersonaManager.getPersonaService(context);
                    if (personaService4 != null && personaService4.isUserManaged()) {
                        mKnoxInfo.putString("isSupportSecureFolder", "true");
                    } else {
                        mKnoxInfo.putString("isSupportSecureFolder", "false");
                    }
                }
            } catch (Exception e4) {
                Log.e(TAG, "failed to get mKnoxInfo", e4);
            }
            if ("isSupportImpKeyguard".equals(str)) {
                mKnoxInfo.putString("isSupportImpKeyguard", "true");
            }
        }
        return mKnoxInfo;
    }

    public static Bundle getKnoxInfoForApp(Context context) {
        if (mKnoxInfo == null) {
            getKnoxInfo();
        }
        try {
            if ("2.0".equals(mKnoxInfo.getString("version"))) {
                getKnoxInfoForApp(context, "isSupportMoveTo");
            }
        } catch (Exception e) {
            Log.e(TAG, "failed to get knox info for APP", e);
        }
        return mKnoxInfo;
    }

    private static int getWorkProfileUserId() throws NumberFormatException {
        String str = SystemProperties.get(SemPersonaManager.PROPERTY_KNOX_CONTAINER_INFO);
        if (str != null && str.length() > 0) {
            for (String str2 : str.split(":")) {
                String[] strArrSplit = str2.split(",");
                if (strArrSplit != null && strArrSplit.length == 2) {
                    int i = Integer.parseInt(strArrSplit[0]);
                    int i2 = Integer.parseInt(strArrSplit[1]);
                    if (!SemPersonaManager.isSecureFolderId(i) && (i2 & 32) > 0) {
                        return i;
                    }
                }
            }
        }
        return 0;
    }
}
