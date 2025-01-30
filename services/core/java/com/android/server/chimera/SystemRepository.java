package com.android.server.chimera;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

/* loaded from: classes.dex */
public interface SystemRepository {

    public interface ChimeraProcessObserver {
        void onForegroundActivitiesChanged(int i, int i2, boolean z, int i3, String[] strArr, boolean z2);
    }

    public interface ChimeraUidObserver {
        void onUidGone(int i, boolean z);
    }

    public class RunningAppProcessInfo {
        public long DRAMUsed;
        public long avgPss;
        public int flags;
        public int importance;
        public long initialIdlePss;
        public boolean isFocused;
        public boolean isProtectedInPicked;
        public long lastActivityTime;
        public long lastPss;
        public long lastSwapPss;
        public int lru;
        public long maxPss;
        public long minPss;
        public int pid;
        public String[] pkgList;
        public String processName;
        public int processState;
        public int uid;
    }

    long currentTimeMillis();

    List dumpProcessListForPPN(int i, BiFunction biFunction);

    void forceGc(int i);

    void forceStop(String str, int i);

    List getAccessibilityServicePackages();

    List getAppFilePathsByPackageName(String str);

    int getAppStandbyBucket(String str, int i, long j);

    Map getAppStandbyBuckets();

    long getAvailableMemory();

    ICollectionCache$BigGameAppsCache getBigGameApps();

    ICollectionCache$CameraAppsCache getCameraApps();

    CameraManager.SemCameraDeviceStateCallback getCameraDeviceStateCallback();

    String getCurrentHomePackageName();

    List getFullPowerWhitelist();

    ICollectionCache$GameAppsCache getGameApps();

    List getLongLiveProcessesForUser(int i);

    long getMemmoryOfPid(int i);

    ActivityManager.MemoryInfo getMemoryInfo();

    List getNativeProcesses(Set set);

    String getPackageNameByPid(int i);

    String[] getPackageNameFromUid(int i);

    List getPkgsTypeForChimera(List list);

    int[] getProcStateAndOomScoreForPid(int i);

    Pair getProcessStatesAndOomScoresForPIDs(int[] iArr);

    long getPss(int i, long[] jArr);

    long[] getRss(int i);

    List getRunningAppProcesses();

    SharedPreferences getSharedPreferences();

    int getSystemPid();

    String getSystemProperty(String str, String str2);

    int getUserId(int i);

    String[] getWakeLockPackageList();

    int hasChimeraProtectedProc(String str, int i);

    boolean hasConnectionProvider(String str, int i);

    boolean hasMessages(Handler handler, int i, Object obj);

    boolean hasPkgIcon(String str, int i);

    boolean hasRestartService(String str, int i);

    boolean isApp(int i);

    boolean isCharging();

    boolean isHomeHubDocked();

    boolean isInCall();

    boolean isKilledByRecentTask(int i, String str);

    boolean isLockTaskPackage(String str);

    boolean isOnScreenWindow(int i);

    boolean isPackageInstalled(String str);

    boolean isScreenOff();

    boolean isSmartSwitchWorking();

    boolean isThreadGroupLeader(int i);

    boolean isUserShipBuild();

    void killProcessForChimera(String str, int i, String str2);

    void log(String str, String str2);

    void logDebug(String str, String str2);

    void registerBroadcastReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter);

    void registerProcessObserver(ChimeraProcessObserver chimeraProcessObserver);

    void registerUidObserver(ChimeraUidObserver chimeraUidObserver);

    void removeMessages(Handler handler, int i, Object obj);

    void sendBroadcast(Intent intent);

    void sendHqmBigData(String str, String str2);

    void sendMessage(Handler handler, int i, Object obj);

    void sendMessageDelayed(Handler handler, int i, Object obj, long j);

    void setSystemEventListenerHandler(Handler handler);

    void setSystemProperty(String str, String str2);

    boolean useCompaction();

    public class CameraProcInfo {
        public long closeRss;
        public String name;
        public long openRss;
        public int pid;

        public CameraProcInfo(String str) {
            this.name = str;
        }
    }
}
