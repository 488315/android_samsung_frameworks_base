package com.android.server.desktopmode;

import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.DexController;
import com.android.server.wm.StartActivityFromRecentsInfo;
import com.android.server.wm.Task;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WindowManagerService;

import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.knox.ContainerProxy;
import com.samsung.android.knox.custom.PrivateCustomDeviceManager;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.multiwindow.MultiWindowManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class ModeChanger {
    public final ActivityManagerInternal mActivityManagerInternal;
    public final ActivityTaskManagerService mActivityTaskManager;
    public final ActivityTaskManagerInternal mActivityTaskManagerInternal;
    public final Context mContext;
    public Handler mHandler;
    public boolean mIsLockTaskModeEnabledAndSecured = false;
    public final MultiResolutionManager mMultiResolutionManager;
    public final MultiWindowManager mMultiWindowManager;
    public final ContentResolver mResolver;
    public final SemDesktopModeStateNotifier mSemDesktopModeStateNotifier;
    public SettingsHelper mSettingsHelper;
    public final IStateManager mStateManager;
    public final UiManager mUiManager;
    public final WindowManagerInternal mWindowManagerInternal;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModeToModeChangeInfo {
        public final int mModeToMode;
        public final boolean mShowModeChangeScreen;
        public int mTopTaskId = -1;

        public ModeToModeChangeInfo(int i, boolean z) {
            this.mShowModeChangeScreen = z;
            this.mModeToMode = i;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(ModeToModeChangeInfo.class.getSimpleName());
            sb.append("(mShowModeChangeScreen=");
            sb.append(this.mShowModeChangeScreen);
            sb.append(", mModeToMode=");
            int i = this.mModeToMode;
            sb.append(
                    i != -1
                            ? i != 1
                                    ? i != 2
                                            ? VibrationParam$1$$ExternalSyntheticOutline0.m(
                                                    i, "Unknown=")
                                            : "STANDALONE_TO_DUAL"
                                    : "DUAL_TO_STANDALONE"
                            : "NONE");
            sb.append(", mTopTaskId=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mTopTaskId, sb, ")");
        }
    }

    public ModeChanger(
            Context context,
            IStateManager iStateManager,
            SemDesktopModeStateNotifier semDesktopModeStateNotifier,
            UiManager uiManager,
            SettingsHelper settingsHelper,
            MultiResolutionManager multiResolutionManager,
            ActivityTaskManagerService activityTaskManagerService,
            ActivityTaskManagerInternal activityTaskManagerInternal,
            ActivityManagerInternal activityManagerInternal,
            WindowManagerInternal windowManagerInternal,
            MultiWindowManager multiWindowManager) {
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        this.mStateManager = iStateManager;
        this.mSemDesktopModeStateNotifier = semDesktopModeStateNotifier;
        this.mUiManager = uiManager;
        this.mSettingsHelper = settingsHelper;
        this.mMultiResolutionManager = multiResolutionManager;
        this.mActivityTaskManager = activityTaskManagerService;
        this.mActivityTaskManagerInternal = activityTaskManagerInternal;
        this.mActivityManagerInternal = activityManagerInternal;
        this.mWindowManagerInternal = windowManagerInternal;
        this.mMultiWindowManager = multiWindowManager;
    }

    public final void backupLockTaskModeEnabledAndSecured(
            StateManager.InternalState internalState) {
        boolean z = false;
        if (this.mActivityTaskManager.mLockTaskController.mLockTaskModeState != 0
                && Settings.Secure.getIntForUser(
                                this.mContext.getContentResolver(),
                                "lock_to_app_exit_locked",
                                0,
                                internalState.mCurrentUserId)
                        != 0) {
            z = true;
        }
        this.mIsLockTaskModeEnabledAndSecured = z;
    }

    public final void bringTaskToForeground(int i, int i2, int i3) {
        ComponentName componentName;
        if (i < 0) {
            return;
        }
        PrivateCustomDeviceManager privateCustomDeviceManager =
                PrivateCustomDeviceManager.getInstance();
        if (privateCustomDeviceManager != null
                && privateCustomDeviceManager.isAutoOpenLastAppAllowed() == 8) {
            boolean z = DesktopModeFeature.DEBUG;
            if (z) {
                Log.d(
                        "[DMS]ModeChanger",
                        "canTaskBeBroughtToForeground(), auto_open_last_app is forcibly disabled by"
                            + " Knox Custom SDK");
            }
            DesktopModeSettings.setSettings(
                    this.mContext.getContentResolver(), "auto_open_last_app", false);
            ActivityTaskManagerService.LocalService localService =
                    (ActivityTaskManagerService.LocalService) this.mActivityTaskManagerInternal;
            WindowManagerGlobalLock windowManagerGlobalLock =
                    ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task anyTaskForId =
                            ActivityTaskManagerService.this.mExt.mService.mRootWindowContainer
                                    .anyTaskForId(i);
                    componentName = anyTaskForId == null ? null : anyTaskForId.realActivity;
                } finally {
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            if (!privateCustomDeviceManager.stayInForeground(componentName)) {
                if (z) {
                    android.util.Log.d(
                            "[DMS]ModeChanger",
                            "canTaskBeBroughtToForeground(), componentName="
                                    + componentName
                                    + " is not allowed.");
                    return;
                }
                return;
            }
        } else if (!DesktopModeSettings.getSettingsAsUser(
                this.mContext.getContentResolver(),
                "auto_open_last_app",
                false,
                DesktopModeSettings.sCurrentUserId)) {
            if (DesktopModeFeature.DEBUG) {
                Log.d(
                        "[DMS]ModeChanger",
                        "canTaskBeBroughtToForeground(), auto_open_last_app is not enabled");
                return;
            }
            return;
        }
        boolean z2 = DesktopModeFeature.DEBUG;
        if (z2) {
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i,
                            i2,
                            "bringTaskToForeground(), taskId=",
                            ", targetDisplayId=",
                            ", targetWindowingMode=");
            m.append(WindowConfiguration.windowingModeToString(i3));
            Log.i("[DMS]ModeChanger", m.toString());
        }
        DexController dexController = ActivityTaskManagerService.this.mDexController;
        dexController.getClass();
        if (z2) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i,
                            i2,
                            "bringTaskToForeground(), taskId=",
                            ", targetDisplayId=",
                            ", targetWindowingMode="),
                    i3,
                    "DexController");
        }
        WindowManagerGlobalLock windowManagerGlobalLock2 = dexController.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock2) {
            try {
                Task anyTaskForId2 = dexController.mAtm.mRootWindowContainer.anyTaskForId(i);
                if (anyTaskForId2 != null && anyTaskForId2.isActivityTypeStandard()) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setLaunchDisplayId(i2);
                    makeBasic.setLaunchWindowingMode(i3);
                    WindowManagerGlobalLock windowManagerGlobalLock3 = dexController.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock3) {
                        try {
                            ActivityRecord rootActivity =
                                    anyTaskForId2.getRootActivity(true, false);
                            if (rootActivity != null) {
                                String str = rootActivity.processName;
                                int uid = rootActivity.getUid();
                                ApplicationInfo applicationInfo = rootActivity.info.applicationInfo;
                                StartActivityFromRecentsInfo startActivityFromRecentsInfo =
                                        new StartActivityFromRecentsInfo(
                                                str, uid, anyTaskForId2, null, makeBasic, i2);
                                DexController.H h = dexController.mH;
                                h.sendMessage(
                                        h.obtainMessage(1, i2, 0, startActivityFromRecentsInfo));
                                WindowManagerService.resetPriorityAfterLockedSection();
                            } else {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                Slog.d(
                                        "DexController",
                                        "bringTaskToForeground(): rootActivity is null.");
                                try {
                                    dexController.mAtm.startActivityFromRecents(
                                            i, makeBasic.toBundle());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } finally {
                        }
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } finally {
            }
        }
    }

    public final boolean hasPackageTask(int i) {
        boolean hasPackageTask;
        ActivityTaskManagerService.LocalService localService =
                (ActivityTaskManagerService.LocalService) this.mActivityTaskManagerInternal;
        WindowManagerGlobalLock windowManagerGlobalLock =
                ActivityTaskManagerService.this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                hasPackageTask = ActivityTaskManagerService.this.mExt.hasPackageTask(i);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return hasPackageTask;
    }

    public abstract void setDesktopModeState(int i, int i2);

    public final void showDexMirroringTipsNotification(String str, String str2) {
        if (DesktopModeFeature.SUPPORT_STANDALONE
                && DesktopModeFeature.SUPPORT_DUAL
                && !DesktopModeSettings.getSettingsAsUser(
                        this.mResolver,
                        "dex_mirroring_tips_notification_shown",
                        false,
                        DesktopModeSettings.sCurrentUserId)) {
            if (DesktopModeFeature.DEBUG) {
                Log.d("[DMS]ModeChanger", "Show DeX mirroring Tips Notification.");
            }
            DesktopModeSettings.setSettings(
                    this.mResolver, "dex_mirroring_tips_notification_shown", true);
            Context context = this.mContext;
            Intent intent = new Intent();
            Intent intent2 = new Intent();
            intent2.setClassName(
                    "com.samsung.android.app.tips",
                    "com.samsung.android.app.tips.TipsIntentService");
            intent2.putExtra("tips_extras", 7);
            intent2.putExtra("tips_extras2", "DEXX_0004");
            intent2.setFlags(268435456);
            intent.setClassName(
                    "com.samsung.android.app.tips",
                    "com.samsung.android.app.tips.TipsIntentService");
            intent.putExtra("tips_extras", 9);
            intent.putExtra("tips_id", "0008");
            intent.putExtra("tips_noti_category", "recommendation");
            intent.putExtra("tips_app_name", context.getPackageName());
            intent.putExtra("tips_title", str);
            intent.putExtra("tips_text", str2);
            intent.putExtra("tips_action_type", 1);
            intent.putExtra("tips_action", intent2);
            intent.putExtra("tips_app_primary_color", "#ff6c39");
            intent.putExtra("tips_condition", 1);
            context.startForegroundService(intent);
        }
    }

    public final void switchKnoxToFolderMode(StateManager.InternalState internalState) {
        ResolveInfo resolveActivityAsUser =
                this.mContext
                        .getPackageManager()
                        .resolveActivityAsUser(
                                new Intent("android.intent.action.MAIN")
                                        .addCategory("android.intent.category.HOME"),
                                EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT,
                                internalState.mCurrentUserId);
        if (resolveActivityAsUser == null
                || !resolveActivityAsUser.activityInfo.name.startsWith(
                        "com.android.internal.app")) {
            return;
        }
        if (DesktopModeFeature.DEBUG) {
            Log.v("[DMS]ModeChanger", "isKnoxLauncherMode()=true");
        }
        Bundle bundle = new Bundle(1);
        bundle.putInt("android.intent.extra.user_handle", internalState.mCurrentUserId);
        ContainerProxy.sendCommand("knox.container.proxy.COMMAND_SWITCH_PROFILE", bundle);
    }
}
