package com.samsung.android.game;

import android.app.PendingIntent;
import android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2;
import android.content.Context;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.samsung.android.game.IGameManagerService;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public class SemGameManager {
    private static final int FPS_PARAM_MAX = 120;
    private static final int FPS_PARAM_MIN = 1;
    private static final String TAG = "SemGameManager";
    private static final int TRANSACT_CODE_GET_DFS = 1124;
    public static final int TUNE_PERFORMANCE_MODE_HIGH_PERFORMANCE = 1;
    public static final int TUNE_PERFORMANCE_MODE_NORMAL_PERFORMANCE = 0;
    public static final int TUNE_PERFORMANCE_MODE_SAVE_POWER = -1;
    private IGameManagerService mService;

    public SemGameManager(Context context) {
        this();
    }

    public SemGameManager() {
        IBinder gMSBinder = getGMSBinder();
        if (gMSBinder != null && this.mService == null) {
            this.mService = IGameManagerService.Stub.asInterface(gMSBinder);
        }
        if (this.mService == null) {
            GmsLog.w(TAG, "SemGameManager(), init mService failed");
        }
    }

    private IGameManagerService getService() {
        return this.mService;
    }

    public static boolean isAvailable() {
        boolean z = getGMSBinder() != null;
        if (!z) {
            GmsLog.w(TAG, "isAvailable(), not available");
        }
        return z;
    }

    public static boolean isGamePackage(String str) throws IllegalStateException {
        IBinder gMSBinder = getGMSBinder();
        if (gMSBinder == null) {
            throw new IllegalStateException("gamemanager system service is not available");
        }
        IGameManagerService iGameManagerServiceAsInterface = IGameManagerService.Stub.asInterface(gMSBinder);
        if (iGameManagerServiceAsInterface == null) {
            throw new IllegalStateException("gamemanager system service is not available");
        }
        try {
            boolean z = iGameManagerServiceAsInterface.identifyGamePackage(str) == 1;
            GmsLog.d(TAG, "isGamePackage(), pkgName=" + str + ", ret=" + z);
            return z;
        } catch (RemoteException unused) {
            throw new IllegalStateException("failed to call gamemanager system service");
        }
    }

    public boolean isForegroundGame() throws IllegalStateException {
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            throw new IllegalStateException("gamemanager system service is not available");
        }
        try {
            boolean z = iGameManagerService.identifyForegroundApp() == 1;
            GmsLog.d(TAG, "isForegroundGame(), ret=" + z);
            return z;
        } catch (RemoteException unused) {
            throw new IllegalStateException("failed to call gamemanager system service");
        }
    }

    public String getForegroundApp() throws IllegalStateException {
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            throw new IllegalStateException("gamemanager system service is not available");
        }
        try {
            String foregroundApp = iGameManagerService.getForegroundApp();
            GmsLog.d(TAG, "getForegroundApp(), ret=" + foregroundApp);
            return foregroundApp;
        } catch (RemoteException unused) {
            throw new IllegalStateException("failed to call gamemanager system service");
        }
    }

    public List<String> getGameList() throws IllegalStateException {
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            throw new IllegalStateException("gamemanager system service is not available");
        }
        try {
            List<String> gameList = iGameManagerService.getGameList();
            GmsLog.d(TAG, "getGameList(), ret=" + gameList);
            return gameList;
        } catch (RemoteException unused) {
            throw new IllegalStateException("failed to call gamemanager system service");
        }
    }

    public void syncGameList(Map map) throws IllegalStateException {
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            throw new IllegalStateException("gamemanager system service is not available");
        }
        try {
            iGameManagerService.syncGameList(map);
        } catch (RemoteException unused) {
            throw new IllegalStateException("failed to call gamemanager system service");
        }
    }

    public String getVersion() throws IllegalStateException {
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            throw new IllegalStateException("gamemanager system service is not available");
        }
        try {
            String version = iGameManagerService.getVersion();
            GmsLog.d(TAG, "getVersion(), ret=" + version);
            return version;
        } catch (RemoteException unused) {
            throw new IllegalStateException("failed to call gamemanager system service");
        }
    }

    public String requestWithJson(String str, String str2) throws IllegalStateException {
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            throw new IllegalStateException("gamemanager system service is not available");
        }
        try {
            String strRequestWithJson = iGameManagerService.requestWithJson(str, str2);
            GmsLog.d(TAG, "requestWithJson(), command=" + str + ", jsonParam=" + str2 + ", ret=" + strRequestWithJson);
            return strRequestWithJson;
        } catch (RemoteException unused) {
            throw new IllegalStateException("failed to call gamemanager system service");
        }
    }

    public String getTopActivityName() throws IllegalStateException {
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            throw new IllegalStateException("gamemanager system service is not available");
        }
        try {
            String topActivityName = iGameManagerService.getTopActivityName();
            GmsLog.d(TAG, "getTopActivityName(), ret=" + topActivityName);
            return topActivityName;
        } catch (RemoteException unused) {
            throw new IllegalStateException("failed to call gamemanager system service");
        }
    }

    public int getTargetFrameRate() throws IllegalStateException {
        IBinder service = ServiceManager.getService("SurfaceFlinger");
        if (service == null) {
            throw new IllegalStateException("failed to get SurfaceFlinger");
        }
        boolean zTransact = false;
        int i = -1;
        try {
            String foregroundApp = getForegroundApp();
            Parcel parcelObtain = Parcel.obtain();
            if (parcelObtain != null) {
                parcelObtain.writeInterfaceToken("android.ui.ISurfaceComposer");
                parcelObtain.writeString16(foregroundApp);
                Parcel parcelObtain2 = Parcel.obtain();
                if (parcelObtain2 != null) {
                    zTransact = service.transact(1124, parcelObtain, parcelObtain2, 0);
                    if (zTransact) {
                        i = parcelObtain2.readInt();
                        GmsLog.d(TAG, "getTargetFrameRate(), transactGetDFS: " + i);
                    } else {
                        GmsLog.e(TAG, "getTargetFrameRate(), transactRet: false");
                    }
                    parcelObtain2.recycle();
                }
                parcelObtain.recycle();
            }
        } catch (RemoteException unused) {
            GmsLog.e(TAG, "getTargetFrameRate(), RemoteException!");
        } catch (SecurityException unused2) {
            GmsLog.e(TAG, "getTargetFrameRate(), SecurityException: Need system privilege");
        }
        if (!zTransact) {
            throw new IllegalStateException("failed to transact SurfaceFlinger");
        }
        GmsLog.d(TAG, "getTargetFrameRate(), ret=" + i);
        return i;
    }

    public boolean setTargetFrameRate(int i) {
        if (this.mService == null) {
            throw new IllegalStateException("gamemanager system service is not available");
        }
        IBinder service = ServiceManager.getService("SurfaceFlinger");
        if (service == null) {
            throw new IllegalStateException("failed to get SurfaceFlinger");
        }
        if (i < 1) {
            GmsLog.e(TAG, "setTargetFrameRate(), given fps is not allowed value. do nothing.");
            return false;
        }
        if (i > 120) {
            GmsLog.w(TAG, "setTargetFrameRate(), use max value 120");
            i = 120;
        }
        try {
            boolean targetFrameRate = this.mService.setTargetFrameRate(service, i);
            GmsLog.d(TAG, "setTargetFrameRate(), fps=" + i + ", ret=" + targetFrameRate);
            return targetFrameRate;
        } catch (RemoteException unused) {
            throw new IllegalStateException("failed to call gamemanager system service");
        }
    }

    public boolean setPackageConfigurations(List<SemPackageConfiguration> list) {
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            throw new IllegalStateException("gamemanager system service is not available");
        }
        try {
            boolean packageConfigurations = iGameManagerService.setPackageConfigurations(list);
            GmsLog.d(TAG, "setPackageConfigurations(), packageConfigurations=" + list + ", ret=" + packageConfigurations);
            return packageConfigurations;
        } catch (RemoteException unused) {
            throw new IllegalStateException("failed to call gamemanager system service");
        }
    }

    public boolean setPerformanceMode(int i, String str) {
        if (str == null || i < -1 || 1 < i) {
            GmsLog.e(TAG, "setPerformanceMode(), unexpected param. tunePerformanceMode: " + i + ", callerPackageName: " + str);
            return false;
        }
        IGameManagerService iGameManagerService = this.mService;
        if (iGameManagerService == null) {
            throw new IllegalStateException("gamemanager system service is not available");
        }
        try {
            boolean performanceMode = iGameManagerService.setPerformanceMode(i, str);
            GmsLog.d(TAG, "setPerformanceMode(), tunePerformanceMode=" + i + ", callerPackageName=" + str + ", ret=" + performanceMode);
            return performanceMode;
        } catch (RemoteException unused) {
            throw new IllegalStateException("failed to call gamemanager system service");
        }
    }

    public boolean isDynamicSurfaceScalingSupported() {
        GmsLog.d(TAG, "isDynamicSurfaceScalingSupported(), ret=true");
        return true;
    }

    public void registerGameEventListener(PendingIntent pendingIntent, List<Integer> list, boolean z, List<String> list2) throws IllegalStateException, SecurityException {
        if (this.mService == null) {
            throw new IllegalStateException("gamemanager system service is not available");
        }
        if (pendingIntent == null || list == null) {
            throw new IllegalArgumentException("pendingIntent or targetEvents is null");
        }
        GmsLog.d(TAG, "registerGameEventListener(), pendingIntentCreator=" + pendingIntent.getCreatorPackage() + " /" + pendingIntent.getCreatorUserHandle().semGetIdentifier() + ", targetEvents=" + list + ", targetAllUsersEnabled=" + z + ", targetPackageNames=" + list2);
        try {
            this.mService.registerGameEventListener(pendingIntent, list.stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray(), z, list2);
        } catch (RemoteException unused) {
            throw new IllegalStateException("failed to call gamemanager system service");
        }
    }

    public void unregisterGameEventListener(PendingIntent pendingIntent) throws IllegalStateException {
        if (this.mService == null) {
            throw new IllegalStateException("gamemanager system service is not available");
        }
        if (pendingIntent == null) {
            throw new IllegalArgumentException("pendingIntent is null");
        }
        GmsLog.d(TAG, "unregisterGameEventListener(), pendingIntentCreator=" + pendingIntent.getCreatorPackage() + " /" + pendingIntent.getCreatorUserHandle().semGetIdentifier());
        try {
            this.mService.unregisterGameEventListener(pendingIntent);
        } catch (RemoteException unused) {
            throw new IllegalStateException("failed to call gamemanager system service");
        }
    }

    public static IBinder getGMSBinder() {
        IBinder service = ServiceManager.getService("gamemanager");
        if (service == null) {
            GmsLog.w(TAG, "getGMSBinder(), failed");
        }
        return service;
    }
}
