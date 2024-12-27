package com.android.systemui.bixby2.controller;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.Instrumentation;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.bixby2.util.ActivityLauncher;
import com.android.systemui.bixby2.util.PackageInfoBixby;
import com.android.systemui.bixby2.util.ParamsParser;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.shared.recents.model.Task$TaskKey;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.PackageManagerWrapper;
import com.android.systemui.util.DesktopManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.multiwindow.MultiWindowManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class AppController {
    private static final int INVALID_TASK = -1;
    private static final int MULTI_INSTANCE_CNT = 2;
    private static final int ORIENTATION_LANDSCAPE_270 = 3;
    private static final int ORIENTATION_LANDSCAPE_90 = 1;
    private static final int ORIENTATION_PORTRAIT_0 = 0;
    private static final int ORIENTATION_PORTRAIT_180 = 2;
    private static final int PROJECTION_AFFINITY_NAME_INDEX = 2;
    private static final int PROJECTION_COMPONENT_NAME_INDEX = 1;
    private static final int RESULT_NOT_FOUND = -1;
    private static final String TAG = "AppController";
    private static final String TASKLOCKDB = "content://com.android.quickstep.tasklock.TaskLockDB";
    private final BroadcastDispatcher mBroadcastDispatcher;
    private int mCurOrientation;
    private final DesktopManager mDesktopManager;
    private final DisplayLifecycle mDisplayLifecycle;
    List<String> mExceptionPackages;
    private final ActivityLauncher mLauncher;
    private final MultiWindowManager mMultiWindowManager;
    private final BroadcastReceiver mScreenReceiver;
    private Sensor mSensor;
    private SensorEventListener mSensorListener;
    private SensorManager mSensorManager;
    private final Handler mwHandler;
    private ContentResolver mContentResolver = null;
    private boolean mSensorRegistered = false;
    private final DisplayLifecycle.Observer mDisplayLifeCycleObserver = new DisplayLifecycle.Observer() { // from class: com.android.systemui.bixby2.controller.AppController.2
        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
        public void onFolderStateChanged(boolean z) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isFolderOpened = ", AppController.TAG, z);
            if (z) {
                if (AppController.this.mSensorRegistered) {
                    return;
                }
                Log.d(AppController.TAG, "folderOpened : regist SensorManager ");
                AppController.this.mSensorRegistered = true;
                AppController.this.mSensorManager.registerListener(AppController.this.mSensorListener, AppController.this.mSensor, 3);
                return;
            }
            if (AppController.this.mSensorRegistered) {
                Log.d(AppController.TAG, "folderClosed : unregist SensorManager ");
                AppController.this.mSensorRegistered = false;
                AppController.this.mSensorManager.unregisterListener(AppController.this.mSensorListener);
            }
        }

        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
        public /* bridge */ /* synthetic */ void onDisplayAdded(int i) {
        }

        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
        public /* bridge */ /* synthetic */ void onDisplayChanged(int i) {
        }

        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
        public /* bridge */ /* synthetic */ void onDisplayRemoved(int i) {
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    class AppControlSensorListener implements SensorEventListener {
        public /* synthetic */ AppControlSensorListener(AppController appController, int i) {
            this();
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            int i = (int) sensorEvent.values[0];
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "AppControlSensorListener.onSensorChanged, Rotation: ", AppController.TAG);
            if (i < 0 || i > 3) {
                return;
            }
            AppController.this.mCurOrientation = i;
        }

        private AppControlSensorListener() {
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    }

    public AppController(Context context, ActivityLauncher activityLauncher, DisplayLifecycle displayLifecycle, DesktopManager desktopManager, BroadcastDispatcher broadcastDispatcher) {
        this.mSensorManager = null;
        this.mSensor = null;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.bixby2.controller.AppController.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("android.intent.action.SCREEN_ON".equals(action) || "android.intent.action.ACTION_SCREEN_ON_BY_PROXIMITY".equals(action)) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("screen_on : action = ", action, AppController.TAG);
                    if ((BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && AppController.this.isFolderClosed()) || AppController.this.mSensorRegistered) {
                        return;
                    }
                    Log.d(AppController.TAG, "screen_on : regist SensorManager ");
                    AppController.this.mSensorRegistered = true;
                    AppController.this.mSensorManager.registerListener(AppController.this.mSensorListener, AppController.this.mSensor, 3);
                    return;
                }
                if ("android.intent.action.SCREEN_OFF".equals(action) || "android.intent.action.ACTION_SCREEN_OFF_BY_PROXIMITY".equals(action)) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("screen_off : action = ", action, AppController.TAG);
                    if (!(BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && AppController.this.isFolderClosed()) && AppController.this.mSensorRegistered) {
                        Log.d(AppController.TAG, "screen_off : unregist SensorManager ");
                        AppController.this.mSensorRegistered = false;
                        AppController.this.mSensorManager.unregisterListener(AppController.this.mSensorListener);
                    }
                }
            }
        };
        this.mScreenReceiver = broadcastReceiver;
        ArrayList arrayList = new ArrayList();
        this.mExceptionPackages = arrayList;
        arrayList.add("com.samsung.android.bixby.agent");
        this.mExceptionPackages.add("com.sec.android.app.launcher");
        this.mExceptionPackages.add("com.sec.android.app.desktoplauncher");
        this.mExceptionPackages.add("com.sec.android.app.dexonpc");
        this.mExceptionPackages.add("com.sec.android.dexsystemui");
        this.mExceptionPackages.add("com.sec.android.desktopmode.uiservice");
        this.mLauncher = activityLauncher;
        this.mDesktopManager = desktopManager;
        this.mDisplayLifecycle = displayLifecycle;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mMultiWindowManager = new MultiWindowManager();
        this.mwHandler = new Handler(((Handler) Dependency.sDependency.getDependencyInner(Dependency.MAIN_HANDLER)).getLooper());
        this.mCurOrientation = 0;
        this.mSensorListener = new AppControlSensorListener(this, 0);
        SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
        this.mSensorManager = sensorManager;
        this.mSensor = sensorManager.getDefaultSensor(27);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.ACTION_SCREEN_ON_BY_PROXIMITY");
        intentFilter.addAction("android.intent.action.ACTION_SCREEN_OFF_BY_PROXIMITY");
        broadcastDispatcher.registerReceiver(intentFilter, broadcastReceiver);
    }

    private boolean checkExceptionPackage(List<String> list, ActivityManager.RecentTaskInfo recentTaskInfo) {
        ComponentName componentName = recentTaskInfo.realActivity;
        if (componentName == null || list == null || !list.contains(componentName.getPackageName())) {
            return false;
        }
        Log.d(TAG, "skip removeTask - " + recentTaskInfo.realActivity.getPackageName());
        return true;
    }

    private boolean checkFocusedAppOnDex(int i) {
        SemDesktopModeState semDesktopModeState = this.mDesktopManager.getSemDesktopModeState();
        if (i != 2 || semDesktopModeState == null || semDesktopModeState.getEnabled() != 4 || this.mDesktopManager.isStandalone()) {
            return false;
        }
        Log.d(TAG, "It is dex mode");
        return true;
    }

    private int checkPackageIncludedRecents(String str, ArrayList<String> arrayList, ArrayList<String> arrayList2, Context context) {
        if (str == null) {
            return -1;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if (str.equals(arrayList.get(i))) {
                Log.d(TAG, "found Navi app in Recents List : ".concat(str));
                return i;
            }
        }
        return -1;
    }

    private boolean checkTaskLocked(ActivityManager.RecentTaskInfo recentTaskInfo, Task$TaskKey task$TaskKey) {
        if (task$TaskKey == null || !checkTaskLocked(task$TaskKey)) {
            return false;
        }
        if (recentTaskInfo.realActivity == null) {
            return true;
        }
        Log.d(TAG, "Task is locked, skip removeTask - " + recentTaskInfo.realActivity.getPackageName());
        return true;
    }

    private String getAffinityName(Task$TaskKey task$TaskKey) {
        ComponentName component = task$TaskKey.baseIntent.getComponent();
        PackageManagerWrapper packageManagerWrapper = PackageManagerWrapper.sInstance;
        int myUserId = UserHandle.myUserId();
        packageManagerWrapper.getClass();
        ActivityInfo activityInfo = PackageManagerWrapper.getActivityInfo(component, myUserId);
        if (activityInfo != null) {
            return activityInfo.taskAffinity;
        }
        return null;
    }

    private String getComponentName(Task$TaskKey task$TaskKey) {
        return task$TaskKey.baseIntent.getComponent().flattenToShortString();
    }

    private ActivityTaskManager.RootTaskInfo getFocusedStack() {
        try {
            return ActivityManager.getService().getFocusedRootTaskInfo();
        } catch (RemoteException e) {
            Log.w(TAG, e.toString());
            return null;
        }
    }

    private int getPackageToStartActivityFromRecents(Context context, ArrayList<String> arrayList, ArrayList<String> arrayList2, List<ActivityManager.RecentTaskInfo> list) {
        String str;
        int i = -1;
        for (ActivityManager.RecentTaskInfo recentTaskInfo : list) {
            ComponentName componentName = recentTaskInfo.origActivity;
            recentTaskInfo.configuration.windowConfiguration.getWindowingMode();
            ComponentName componentName2 = recentTaskInfo.realActivity;
            if (componentName2 != null) {
                str = componentName2.getPackageName();
            } else {
                ComponentName componentName3 = recentTaskInfo.origActivity;
                if (componentName3 != null) {
                    str = componentName3.getPackageName();
                } else {
                    RecyclerView$$ExternalSyntheticOutline0.m(recentTaskInfo.taskId, TAG, new StringBuilder("There is no packageName. taskId = "));
                    str = null;
                }
            }
            i = checkPackageIncludedRecents(str, arrayList, arrayList2, context);
            if (i != -1) {
                break;
            }
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "getPackageToStartActivityFromRecents() retCnt = ", TAG);
        return i;
    }

    private boolean isLongLiveApp(String str) {
        List list;
        try {
            list = ActivityManager.getService().getLongLiveApps();
        } catch (RemoteException e) {
            Log.w(TAG, e.toString());
            list = null;
        }
        if (list == null) {
            return false;
        }
        Log.d(TAG, "isLongLiveApp: longLiveApps.size() = " + list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (str.equals((String) it.next())) {
                return true;
            }
        }
        return false;
    }

    private boolean isLongLiveAppDedicatedMemory(int i, int i2, String str) {
        List list;
        try {
            list = ActivityManager.getService().getLongLiveTaskIdsForUser(i);
        } catch (RemoteException e) {
            Log.w(TAG, e.toString());
            list = null;
        }
        return list != null && list.contains(Integer.valueOf(i2));
    }

    private void logTaskIdToRemove(ActivityManager.RecentTaskInfo recentTaskInfo) {
        if (recentTaskInfo.realActivity == null) {
            RecyclerView$$ExternalSyntheticOutline0.m(recentTaskInfo.taskId, TAG, new StringBuilder("removeTask - "));
        } else {
            StringBuilder sb = new StringBuilder("removeTask - ");
            sb.append(recentTaskInfo.realActivity.getPackageName());
            sb.append("(");
            KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(sb, recentTaskInfo.taskId, ")", TAG);
        }
    }

    public boolean checkAvailableCoverLauncher(String str) {
        ArrayList arrayList = new ArrayList();
        ActivityManager.getCurrentUser();
        boolean contains = arrayList.contains(str);
        Log.d(TAG, "contains = " + contains + ", mAllowedPackageList = " + arrayList);
        return contains;
    }

    public boolean checkIncludeCoverLauncher(String str) {
        ActivityManager.getCurrentUser();
        ArrayList arrayList = new ArrayList();
        boolean contains = arrayList.contains(str);
        Log.d(TAG, "appList = " + arrayList);
        Log.d(TAG, "contains = " + contains);
        return contains;
    }

    public boolean checkInstalledApp(Context context, String str) {
        if (str == null) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        ParamsParser.getListInfoFromJson(arrayList, null, str);
        if (arrayList.isEmpty()) {
            return false;
        }
        try {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                context.getPackageManager().getPackageInfo((String) it.next(), 1);
            }
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d(TAG, "not installed! ");
            return false;
        } catch (Exception unused2) {
            Log.d(TAG, "Exception! ");
            return false;
        }
    }

    public boolean checkOrientation() {
        RecyclerView$$ExternalSyntheticOutline0.m(this.mCurOrientation, TAG, new StringBuilder("checkOrientation, mCurOrientation = "));
        int i = this.mCurOrientation;
        return i == 0 || i == 2;
    }

    public boolean checkRunningInRecents(Context context, String str, ArrayList<String> arrayList) {
        int i;
        Log.d(TAG, "checkRunningInRecents()");
        List<ActivityManager.RecentTaskInfo> recentTasks = ((ActivityManager) context.getSystemService("activity")).getRecentTasks(100, 0);
        ArrayList arrayList2 = new ArrayList();
        if (str != null) {
            ParamsParser.getListInfoFromJson(arrayList2, null, str);
            if (arrayList2.isEmpty()) {
                return false;
            }
            arrayList.addAll(arrayList2);
        }
        if (this.mContentResolver == null) {
            this.mContentResolver = context.getContentResolver();
        }
        if (recentTasks != null) {
            i = 0;
            for (ActivityManager.RecentTaskInfo recentTaskInfo : recentTasks) {
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    String str2 = (String) it.next();
                    if (str2.equals(recentTaskInfo.realActivity.getPackageName())) {
                        i++;
                        arrayList.remove(str2);
                    }
                }
            }
        } else {
            i = 0;
        }
        Log.d(TAG, "listPackageInfo.cnt = " + arrayList2.size() + ", matchCount = " + i);
        return arrayList2.size() == i;
    }

    public boolean checkSettingsCoverLauncher(Context context) {
        int i = Settings.System.getInt(context.getContentResolver(), "large_cover_screen_apps", 0);
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "checkSettingsCoverLauncher(), brightnessMode = ", TAG);
        return i > 0;
    }

    public String getPackageNameFromPdss(String str) {
        return ParamsParser.getPackageInfoFromJson(str).PackageName;
    }

    public boolean isDexMode() {
        SemDesktopModeState semDesktopModeState = this.mDesktopManager.getSemDesktopModeState();
        if (semDesktopModeState == null || semDesktopModeState.getEnabled() != 4 || this.mDesktopManager.isStandalone()) {
            return false;
        }
        Log.d(TAG, "It is dex mode");
        return true;
    }

    public boolean isFolderClosed() {
        return !this.mDisplayLifecycle.mIsFolderOpened;
    }

    public boolean launchApplication(Context context, String str) {
        Log.d(TAG, "launchApplication(), newJSONStringValue = " + str);
        PackageInfoBixby packageInfoFromJson = ParamsParser.getPackageInfoFromJson(str);
        String str2 = packageInfoFromJson.PackageName;
        try {
            context.getPackageManager().getPackageInfo(packageInfoFromJson.PackageName, 1);
            if (!TextUtils.isEmpty(packageInfoFromJson.ActivityName) && !TextUtils.isEmpty(packageInfoFromJson.PackageName)) {
                return this.mLauncher.startActivityInBixby(context, packageInfoFromJson.PackageName, packageInfoFromJson.ActivityName, packageInfoFromJson.taskId);
            }
            Log.e(TAG, "wrong parameter was delivered");
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d(TAG, "NameNotFoundException! ");
            return false;
        } catch (Exception e) {
            Log.d(TAG, "Exception! " + e.toString());
            return false;
        }
    }

    public boolean openRecentsApp(Context context) {
        Log.d(TAG, "openRecentsApp()");
        final Instrumentation instrumentation = new Instrumentation();
        if (!isDexMode()) {
            new Thread(new Runnable() { // from class: com.android.systemui.bixby2.controller.AppController.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Thread.sleep(1000L);
                        instrumentation.sendKeyDownUpSync(187);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            return true;
        }
        instrumentation.sendKeySync(new KeyEvent(0L, 0L, 0, 187, 0, 0, -1, 0, 72, 0, 2));
        instrumentation.sendKeySync(new KeyEvent(0L, 0L, 1, 187, 0, 0, -1, 0, 72, 0, 2));
        return true;
    }

    public boolean removeAllTasks(Context context) {
        return removeAllTasks(context, false, null);
    }

    public boolean removeFocusedTask(Context context) {
        Log.d(TAG, "removeFocusedTask()");
        boolean removeFocusedTask = isDexMode() ? this.mMultiWindowManager.removeFocusedTask(2) : (LsRune.SUBSCREEN_UI && isFolderClosed()) ? this.mMultiWindowManager.removeFocusedTask(1) : this.mMultiWindowManager.removeFocusedTask(0);
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("retValue = ", TAG, removeFocusedTask);
        return removeFocusedTask;
    }

    public boolean removeNavigationApp(Context context, String str) {
        Log.d(TAG, "removeNavigationApp");
        ArrayList arrayList = new ArrayList();
        ParamsParser.getListInfoFromJson(arrayList, null, str);
        int size = arrayList.size();
        ListPopupWindow$$ExternalSyntheticOutline0.m(size, "total listPackageInfo's cnt = ", TAG);
        boolean z = false;
        for (int i = 0; i < size; i++) {
            try {
                context.getPackageManager().getPackageInfo((String) arrayList.get(i), 1);
                List<ActivityManager.RecentTaskInfo> taskInfoFromPackageName = this.mMultiWindowManager.getTaskInfoFromPackageName((String) arrayList.get(i));
                if (taskInfoFromPackageName != null) {
                    for (ActivityManager.RecentTaskInfo recentTaskInfo : taskInfoFromPackageName) {
                        Log.d(TAG, "remove taskId = " + recentTaskInfo.taskId + ", removeTask : " + ((String) arrayList.get(i)));
                        ActivityManagerWrapper activityManagerWrapper = ActivityManagerWrapper.sInstance;
                        int i2 = recentTaskInfo.taskId;
                        activityManagerWrapper.getClass();
                        ActivityManagerWrapper.removeTask(i2);
                        z = true;
                    }
                }
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e(TAG, "NameNotFoundException! : " + ((String) arrayList.get(i)));
            } catch (Exception unused2) {
                Log.e(TAG, "Exception! ");
            }
        }
        return z;
    }

    public boolean removeSearchedTask(Context context, String str) {
        List<ActivityManager.RecentTaskInfo> taskInfoFromPackageName;
        Log.d(TAG, "removeSearchedTask()");
        PackageInfoBixby packageInfoFromJson = ParamsParser.getPackageInfoFromJson(str);
        ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("package: "), packageInfoFromJson.PackageName, TAG);
        try {
            context.getPackageManager().getPackageInfo(packageInfoFromJson.PackageName, 1);
            boolean z = false;
            for (int i = 0; i < 2 && (taskInfoFromPackageName = this.mMultiWindowManager.getTaskInfoFromPackageName(packageInfoFromJson.PackageName)) != null; i++) {
                if (i != 0) {
                    SystemClock.sleep(500L);
                }
                for (ActivityManager.RecentTaskInfo recentTaskInfo : taskInfoFromPackageName) {
                    Log.d(TAG, "recentTaskInfo = " + recentTaskInfo);
                    ActivityManagerWrapper activityManagerWrapper = ActivityManagerWrapper.sInstance;
                    int i2 = recentTaskInfo.taskId;
                    activityManagerWrapper.getClass();
                    ActivityManagerWrapper.removeTask(i2);
                    z = true;
                }
            }
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("retValue = ", TAG, z);
            return z;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d(TAG, "NameNotFoundException! ");
            return false;
        } catch (Exception unused2) {
            Log.d(TAG, "Exception! ");
            return false;
        }
    }

    public boolean startNavigationApp(Context context, String str, CommandActionResponse commandActionResponse) {
        Log.d(TAG, "startNavigationApp()");
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        List<ActivityManager.RecentTaskInfo> recentTasks = ((ActivityManager) context.getSystemService("activity")).getRecentTasks(100, 0);
        ParamsParser.getListInfoFromJson(arrayList, arrayList2, str);
        int size = arrayList.size();
        int size2 = arrayList2.size();
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(size, size2, "listPackageInfo's cnt = ", ",  listActivityInfo's cnt", TAG);
        if (size != size2) {
            Log.e(TAG, "packageInfo's cnt and activityInfo's cnt are different!! ");
            return false;
        }
        if (size == 0 || size2 == 0) {
            Log.e(TAG, "packageInfo's cnt or activityInfo's cnt is 0");
            return false;
        }
        int i = 0;
        while (i < arrayList.size()) {
            try {
                context.getPackageManager().getPackageInfo(arrayList.get(i), 1);
                Log.d(TAG, "Exist in the phone : " + arrayList.get(i));
            } catch (PackageManager.NameNotFoundException unused) {
                Log.d(TAG, "NameNotFoundException! : " + arrayList.get(i));
                arrayList.remove(i);
                arrayList2.remove(i);
                i--;
            } catch (Exception unused2) {
                Log.d(TAG, "Exception! ");
            }
            i++;
        }
        int size3 = arrayList.size();
        ListPopupWindow$$ExternalSyntheticOutline0.m(size3, "installed listPackageInfo = ", TAG);
        if (size3 <= 0) {
            Log.d(TAG, "There is no navi app in the phone");
            return false;
        }
        int packageToStartActivityFromRecents = recentTasks != null ? getPackageToStartActivityFromRecents(context, arrayList, arrayList2, recentTasks) : 0;
        if (packageToStartActivityFromRecents == -1) {
            packageToStartActivityFromRecents = 0;
        }
        String str2 = arrayList.get(packageToStartActivityFromRecents);
        String str3 = arrayList2.get(packageToStartActivityFromRecents);
        if (BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && isFolderClosed()) {
            boolean checkSettingsCoverLauncher = checkSettingsCoverLauncher(context);
            boolean checkIncludeCoverLauncher = checkIncludeCoverLauncher(str2);
            boolean checkAvailableCoverLauncher = checkAvailableCoverLauncher(str2);
            commandActionResponse.responseCode = 1;
            if (!checkIncludeCoverLauncher) {
                commandActionResponse.responseMessage = ActionResults.RESULT_NOT_INCLUDE_COVERLAUNCHER;
            } else if (!checkSettingsCoverLauncher) {
                commandActionResponse.responseMessage = ActionResults.RESULT_SET_OFF_COVERLAUNCHER;
            } else if (checkAvailableCoverLauncher) {
                commandActionResponse.responseMessage = "success";
            } else {
                commandActionResponse.responseMessage = ActionResults.RESULT_NOT_AVAILABLE_COVERLAUNCHER;
            }
        }
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("to start app is : ", str2, TAG);
        if (!this.mLauncher.startActivityInBixby(context, str2, str3, 0)) {
            commandActionResponse.responseCode = 2;
            commandActionResponse.responseMessage = ActionResults.RESULT_FAIL;
        }
        return true;
    }

    public boolean removeAllTasks(Context context, boolean z, String str) {
        int i;
        Iterator<ActivityManager.RecentTaskInfo> it;
        Log.d(TAG, "removeAllTasks()");
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        List<ActivityManager.RecentTaskInfo> recentTasks = activityManager.getRecentTasks(100, 0);
        ArrayList arrayList = new ArrayList();
        if (str != null) {
            ParamsParser.getListInfoFromJson(arrayList, null, str);
            Log.d(TAG, "listPackageInfo's cnt = " + arrayList.size());
            if (arrayList.isEmpty()) {
                return false;
            }
        }
        if (this.mContentResolver == null) {
            this.mContentResolver = context.getContentResolver();
        }
        if (recentTasks != null) {
            Log.d(TAG, "recentTaskList.size() = " + recentTasks.size());
            Iterator<ActivityManager.RecentTaskInfo> it2 = recentTasks.iterator();
            i = 0;
            while (it2.hasNext()) {
                ActivityManager.RecentTaskInfo next = it2.next();
                KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder("----- taskID = "), next.taskId, " -----", TAG);
                if (checkExceptionPackage(this.mExceptionPackages, next)) {
                    it = it2;
                } else {
                    ComponentName componentName = next.origActivity;
                    if (componentName == null) {
                        componentName = next.realActivity;
                    }
                    ComponentName componentName2 = componentName;
                    int windowingMode = next.configuration.windowConfiguration.getWindowingMode();
                    if (next.realActivity != null) {
                        Log.d(TAG, "packageName = " + next.realActivity.getPackageName() + ", taskId = " + next.taskId);
                        it = it2;
                        if (!checkTaskLocked(next, new Task$TaskKey(next.taskId, windowingMode, next.baseIntent, componentName2, next.userId, next.lastActiveTime))) {
                            if (isLongLiveApp(next.realActivity.getPackageName())) {
                                Log.d(TAG, "This Task is LongLiveApp, skip removeTask - " + next.realActivity.getPackageName());
                            } else if (isLongLiveAppDedicatedMemory(next.userId, next.taskId, next.realActivity.getPackageName())) {
                                Log.d(TAG, "This Task is LongLiveApp based DedicatedMemory, skip removeTask - " + next.realActivity.getPackageName());
                            } else if (z && next.isVisible) {
                                Log.d(TAG, "skip visible task");
                            } else {
                                if (str != null) {
                                    Iterator it3 = arrayList.iterator();
                                    boolean z2 = false;
                                    while (it3.hasNext()) {
                                        if (((String) it3.next()).equals(next.realActivity.getPackageName())) {
                                            Log.d(TAG, "skip excepted package from bixby - " + next.realActivity.getPackageName());
                                            z2 = true;
                                        }
                                    }
                                    if (z2) {
                                    }
                                }
                                logTaskIdToRemove(next);
                                activityManager.semRemoveTask(next.taskId, 0);
                                i++;
                            }
                        }
                    } else {
                        it = it2;
                        Log.d(TAG, "t.realActivity IS NULL!!!!!!!!!!!!!!!!");
                    }
                }
                it2 = it;
            }
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "removed task : ", TAG);
        } else {
            i = 0;
        }
        return i > 0;
    }

    public boolean checkTaskLocked(Task$TaskKey task$TaskKey) {
        Cursor cursor = null;
        try {
            try {
                cursor = this.mContentResolver.query(Uri.parse(TASKLOCKDB), null, null, null, null);
                String componentName = getComponentName(task$TaskKey);
                String affinityName = getAffinityName(task$TaskKey);
                if (cursor != null && cursor.moveToFirst()) {
                    Log.d(TAG, "isTaskLocked: getCount = " + cursor.getCount());
                    do {
                        Log.d(TAG, "isTaskLocked: ColumnNames = " + cursor.getColumnNames());
                        if (cursor.getString(1) == null || cursor.getString(2) == null) {
                            Log.d(TAG, "Component or Affinity name is null ");
                        } else {
                            if (cursor.getString(1).equals(componentName) && cursor.getString(2).equals(affinityName)) {
                                Log.d(TAG, "isTaskLocked: True " + componentName);
                                cursor.close();
                                return true;
                            }
                            if (cursor.getString(2).equals(affinityName)) {
                                Log.d(TAG, "isTaskLocked: True (only affinity matched)" + componentName);
                                cursor.close();
                                return true;
                            }
                        }
                    } while (cursor.moveToNext());
                }
                if (cursor == null) {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (0 == 0) {
                    return false;
                }
            }
            cursor.close();
            return false;
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }
}
