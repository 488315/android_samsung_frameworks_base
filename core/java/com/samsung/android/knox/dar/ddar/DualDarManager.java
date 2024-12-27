package com.samsung.android.knox.dar.ddar;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;
import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.dar.ddar.proxy.KnoxProxyManager;
import java.util.Optional;
import java.util.function.Function;

/* loaded from: classes6.dex */
public final class DualDarManager {
    public static final String AGENT = "KNOXCORE_PROXY_AGENT";
    public static final String AGENT_PKG = "com.samsung.android.knox.containercore";
    private static final String DDAR_MANAGER_SERVICE = "DDAR_MANAGER_SERVICE";
    private static final String PROP_PERSIST_SYS_DUAL_DAR_DO = "persist.sys.dualdar.do";
    private static final String SYSTEM_PROXY_AGENT = "SYSTEM_PROXY_AGENT";
    private static final String TAG = "DualDarManager";
    private static DualDarManager sInstance;
    private final Context mContext;
    private IDarManagerService mDarManagerService;

    private DualDarManager(Context context) {
        this.mContext = context;
    }

    public static DualDarManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DualDarManager.class) {
                if (sInstance == null) {
                    sInstance = new DualDarManager(context);
                }
            }
        }
        return sInstance;
    }

    public boolean enableOnDeviceOwner(Bundle params) {
        int uid = Binder.getCallingUid();
        boolean result = false;
        if (!isKnoxCore(uid)) {
            Log.e(TAG, "enableOnDeviceOwner - Operation not permitted");
            return false;
        }
        if (isOnDeviceOwnerEnabled()) {
            Log.e(TAG, "enableOnDeviceOwner - Already enabled");
            return false;
        }
        Bundle response = processCommand("ON_DEVICE_OWNER_PROVISIONING", params);
        if (response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
            result = true;
        }
        Log.d(TAG, "enableOnDeviceOwner - result : " + result);
        return result;
    }

    private boolean isKnoxCore(int uid) {
        Log.d(TAG, "isKnoxCore - UID : " + uid);
        return uid == 5250;
    }

    public static boolean isOnDeviceOwnerEnabled() {
        boolean ret = SystemProperties.getInt("persist.sys.dualdar.do", 0) != 0;
        return ret;
    }

    public static boolean isOnDeviceOwner(int userId) {
        return userId == 0 && isOnDeviceOwnerEnabled();
    }

    public boolean isInnerLayerUnlocked(int userId) {
        boolean result = false;
        if (!isOnDeviceOwner(userId)) {
            return false;
        }
        Bundle response = processCommand("IS_INNER_LAYER_UNLOCKED", null);
        if (response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
            result = true;
        }
        Log.d(TAG, "isInnerLayerUnlocked - userId : " + userId + ", ret : " + result);
        return result;
    }

    public boolean isInnerAuthRequired(final int userId) {
        if (isOnDeviceOwner(userId)) {
            return ((Boolean) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.ddar.DualDarManager$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return DualDarManager.lambda$isInnerAuthRequired$0(userId, (IDarManagerService) obj);
                }
            }).orElse(false)).booleanValue();
        }
        return false;
    }

    static /* synthetic */ Boolean lambda$isInnerAuthRequired$0(int userId, IDarManagerService s) {
        try {
            return Boolean.valueOf(s.isInnerAuthRequired(userId));
        } catch (Exception e) {
            Log.e(TAG, "failed to check secondary lock req.", e);
            e.printStackTrace();
            return false;
        }
    }

    public boolean setDualDarInfo(final int userId, final int flag) {
        return ((Boolean) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.ddar.DualDarManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DualDarManager.lambda$setDualDarInfo$1(userId, flag, (IDarManagerService) obj);
            }
        }).orElse(false)).booleanValue();
    }

    static /* synthetic */ Boolean lambda$setDualDarInfo$1(int userId, int flag, IDarManagerService s) {
        try {
            return Boolean.valueOf(s.setDualDarInfo(userId, flag));
        } catch (Exception e) {
            Log.e(TAG, "failed to set dualdar info", e);
            e.printStackTrace();
            return false;
        }
    }

    public void ensureDataUnlockedIfRequired() {
        if (!isOnDeviceOwnerEnabled()) {
            return;
        }
        Bundle response = processCommand("ENSURE_DATA_UNLOCKED", null);
        boolean bResponse = false;
        if (response != null && response.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
            bResponse = true;
        }
        Log.d(TAG, "ensureDataUnlockedIfRequired - response : " + bResponse);
    }

    public void scheduleDataLock(int userId) {
        if (!isOnDeviceOwner(userId)) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", userId);
        processCommand("SCHEDULE_DATA_LOCK", bundle);
    }

    public void cancelDataLock(int userId) {
        if (!isOnDeviceOwner(userId)) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", userId);
        processCommand("CANCEL_DATA_LOCK", bundle);
    }

    public String getClientPackage(int userId) {
        return DualDarCache.getInstance(this.mContext).get(userId, DualDarCache.KEY_CLIENT_PACKAGE_NAME);
    }

    private Bundle processCommand(String command, Bundle params) {
        return KnoxProxyManager.getInstance(this.mContext).relayMessage("SYSTEM_PROXY_AGENT", DDAR_MANAGER_SERVICE, command, params);
    }

    private Optional<IDarManagerService> getDarManagerService() {
        if (this.mDarManagerService == null) {
            this.mDarManagerService = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        }
        return Optional.ofNullable(this.mDarManagerService);
    }
}
