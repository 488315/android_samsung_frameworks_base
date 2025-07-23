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

    public boolean enableOnDeviceOwner(Bundle bundle) {
        boolean z = false;
        if (!isKnoxCore(Binder.getCallingUid())) {
            Log.e(TAG, "enableOnDeviceOwner - Operation not permitted");
            return false;
        }
        if (isOnDeviceOwnerEnabled()) {
            Log.e(TAG, "enableOnDeviceOwner - Already enabled");
            return false;
        }
        Bundle processCommand = processCommand("ON_DEVICE_OWNER_PROVISIONING", bundle);
        if (processCommand != null && processCommand.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
            z = true;
        }
        Log.d(TAG, "enableOnDeviceOwner - result : " + z);
        return z;
    }

    private boolean isKnoxCore(int i) {
        Log.d(TAG, "isKnoxCore - UID : " + i);
        return i == 5250;
    }

    public static boolean isOnDeviceOwnerEnabled() {
        return SystemProperties.getInt("persist.sys.dualdar.do", 0) != 0;
    }

    public static boolean isOnDeviceOwner(int i) {
        return i == 0 && isOnDeviceOwnerEnabled();
    }

    public boolean isInnerLayerUnlocked(int i) {
        boolean z = false;
        if (!isOnDeviceOwner(i)) {
            return false;
        }
        Bundle processCommand = processCommand("IS_INNER_LAYER_UNLOCKED", null);
        if (processCommand != null && processCommand.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
            z = true;
        }
        Log.d(TAG, "isInnerLayerUnlocked - userId : " + i + ", ret : " + z);
        return z;
    }

    public boolean isInnerAuthRequired(final int i) {
        if (isOnDeviceOwner(i)) {
            return ((Boolean) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.ddar.DualDarManager$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return DualDarManager.lambda$isInnerAuthRequired$0(i, (IDarManagerService) obj);
                }
            }).orElse(false)).booleanValue();
        }
        return false;
    }

    static /* synthetic */ Boolean lambda$isInnerAuthRequired$0(int i, IDarManagerService iDarManagerService) {
        try {
            return Boolean.valueOf(iDarManagerService.isInnerAuthRequired(i));
        } catch (Exception e) {
            Log.e(TAG, "failed to check secondary lock req.", e);
            e.printStackTrace();
            return false;
        }
    }

    public boolean setDualDarInfo(final int i, final int i2) {
        return ((Boolean) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.ddar.DualDarManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DualDarManager.lambda$setDualDarInfo$1(i, i2, (IDarManagerService) obj);
            }
        }).orElse(false)).booleanValue();
    }

    static /* synthetic */ Boolean lambda$setDualDarInfo$1(int i, int i2, IDarManagerService iDarManagerService) {
        try {
            return Boolean.valueOf(iDarManagerService.setDualDarInfo(i, i2));
        } catch (Exception e) {
            Log.e(TAG, "failed to set dualdar info", e);
            e.printStackTrace();
            return false;
        }
    }

    public void ensureDataUnlockedIfRequired() {
        if (isOnDeviceOwnerEnabled()) {
            Bundle processCommand = processCommand("ENSURE_DATA_UNLOCKED", null);
            boolean z = false;
            if (processCommand != null && processCommand.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE, false)) {
                z = true;
            }
            Log.d(TAG, "ensureDataUnlockedIfRequired - response : " + z);
        }
    }

    public void scheduleDataLock(int i) {
        if (isOnDeviceOwner(i)) {
            Bundle bundle = new Bundle();
            bundle.putInt("user_id", i);
            processCommand("SCHEDULE_DATA_LOCK", bundle);
        }
    }

    public void cancelDataLock(int i) {
        if (isOnDeviceOwner(i)) {
            Bundle bundle = new Bundle();
            bundle.putInt("user_id", i);
            processCommand("CANCEL_DATA_LOCK", bundle);
        }
    }

    public String getClientPackage(int i) {
        return DualDarCache.getInstance(this.mContext).get(i, DualDarCache.KEY_CLIENT_PACKAGE_NAME);
    }

    private Bundle processCommand(String str, Bundle bundle) {
        return KnoxProxyManager.getInstance(this.mContext).relayMessage("SYSTEM_PROXY_AGENT", DDAR_MANAGER_SERVICE, str, bundle);
    }

    private Optional<IDarManagerService> getDarManagerService() {
        if (this.mDarManagerService == null) {
            this.mDarManagerService = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        }
        return Optional.ofNullable(this.mDarManagerService);
    }
}
