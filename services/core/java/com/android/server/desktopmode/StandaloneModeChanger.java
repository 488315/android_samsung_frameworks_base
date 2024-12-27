package com.android.server.desktopmode;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.IUiModeManager;
import android.app.role.RoleManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.widget.Toast;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.ServiceThread;
import com.android.server.UiModeManagerService;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerInternal;

import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.os.SemDvfsManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class StandaloneModeChanger extends ModeChanger {
    public int mCurrentUiMode;
    public final SemDvfsManager mDvfsManager;
    public ModeChanger.ModeToModeChangeInfo mModeToModeChangeInfo;
    public ModeChangeReceiver mReceiver;
    public boolean mStandaloneModeEnabled;
    public final AnonymousClass1 mStateListener;
    public final TelecomManager mTelecomManager;
    public int mTopTaskId;
    public final IUiModeManager mUiModeManager;
    public final UiModeManagerService.LocalService mUiModeManagerInternal;
    public boolean mWallpaperShown;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModeChangeHandler extends Handler {
        public ModeChangeHandler(Looper looper) {
            super(looper, null, false);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i;
            int i2 = message.what;
            if (i2 == 1) {
                Boolean bool = (Boolean) message.obj;
                boolean booleanValue = bool.booleanValue();
                int i3 = message.arg1;
                boolean z = i3 < 10;
                if (z) {
                    if (DesktopModeFeature.DEBUG) {
                        Log.d(
                                "[DMS]StandaloneModeChanger",
                                "Not all activities are stopped! retryCount="
                                        + i3
                                        + ", waiting more 500ms...");
                    }
                    Handler handler = StandaloneModeChanger.this.mHandler;
                    handler.sendMessageDelayed(handler.obtainMessage(1, i3 + 1, 0, bool), 500L);
                    return;
                }
                if (!z) {
                    Log.e(
                            "[DMS]StandaloneModeChanger",
                            "Not all activities are stopped! timeout!! retryCount=" + i3);
                }
                StandaloneModeChanger standaloneModeChanger = StandaloneModeChanger.this;
                StateManager.InternalState state =
                        ((StateManager) standaloneModeChanger.mStateManager).getState();
                if (state.mDesktopModeState.compareTo(booleanValue ? 3 : 1, 20, 101)) {
                    boolean z2 = DesktopModeFeature.DEBUG;
                    if (z2) {
                        Log.d(
                                "[DMS]StandaloneModeChanger",
                                "setDesktopModeInternal(), enabled=" + booleanValue);
                    }
                    standaloneModeChanger.mHandler.removeMessages(1);
                    standaloneModeChanger.mUiManager.dismissDialog(0, new int[] {0}[0]);
                    List list = ToastManager.sToasts;
                    if (z2) {
                        Log.d("[DMS]ToastManager", "cancelToasts()");
                    }
                    List list2 = ToastManager.sToasts;
                    synchronized (list2) {
                        try {
                            Iterator it = ((ArrayList) list2).iterator();
                            while (it.hasNext()) {
                                Toast toast = (Toast) it.next();
                                it.remove();
                                toast.cancel();
                            }
                        } finally {
                        }
                    }
                    standaloneModeChanger.setDesktopModeState(booleanValue ? 3 : 1, 30);
                    standaloneModeChanger.backupLockTaskModeEnabledAndSecured(state);
                    if (booleanValue) {
                        standaloneModeChanger.switchKnoxToFolderMode(state);
                        try {
                            standaloneModeChanger.mActivityTaskManager.stopSystemLockTaskMode();
                        } catch (RemoteException e) {
                            e.rethrowFromSystemServer();
                        }
                    }
                    standaloneModeChanger.mWallpaperShown = false;
                    try {
                        standaloneModeChanger.mUiModeManager.setDesktopMode(booleanValue);
                        standaloneModeChanger.mCurrentUiMode =
                                (standaloneModeChanger.mUiModeManagerInternal.isNightMode()
                                                ? 32
                                                : 16)
                                        | standaloneModeChanger.mUiModeManager.getCurrentModeType();
                    } catch (RemoteException e2) {
                        Log.e(
                                "[DMS]StandaloneModeChanger",
                                "Failed to set custom configurations",
                                e2);
                    }
                    StateManager stateManager = (StateManager) standaloneModeChanger.mStateManager;
                    stateManager.getClass();
                    if (DesktopModeFeature.DEBUG) {
                        DesktopModeService$$ExternalSyntheticOutline0.m(
                                "notifySetDesktopModeInternal(enter=",
                                ")",
                                "[DMS]StateManager",
                                booleanValue);
                    }
                    Iterator it2 = stateManager.mStateListeners.iterator();
                    while (it2.hasNext()) {
                        ((StateManager.StateListener) it2.next())
                                .onSetDesktopModeInternal(booleanValue);
                    }
                    return;
                }
                return;
            }
            if (i2 == 2) {
                final StandaloneModeChanger standaloneModeChanger2 = StandaloneModeChanger.this;
                final boolean booleanValue2 = ((Boolean) message.obj).booleanValue();
                int i4 = message.arg1;
                standaloneModeChanger2.getClass();
                Trace.asyncTraceBegin(524288L, "loading screen", 0);
                if (i4 == -1) {
                    Log.w(
                            "[DMS]StandaloneModeChanger",
                            "Loading screen shown by in-call UI timeout enter=" + booleanValue2);
                } else if (DesktopModeFeature.DEBUG) {
                    Log.i(
                            "[DMS]StandaloneModeChanger",
                            "Starting loading screen... enter=" + booleanValue2 + ", reason=" + i4);
                }
                Runnable runnable =
                        new Runnable() { // from class:
                                         // com.android.server.desktopmode.StandaloneModeChanger$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                StandaloneModeChanger.this.scheduleStartLoadingScreen(
                                        booleanValue2);
                            }
                        };
                UiManager uiManager = standaloneModeChanger2.mUiManager;
                if (!uiManager.bindUiServiceWithPendingCommand(
                        FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT,
                        -1,
                        -1,
                        runnable)) {
                    Log.w(
                            "[DMS]StandaloneModeChanger",
                            "handleStartLoadingScreen(), Binding DesktopUiService... enter="
                                    + booleanValue2);
                    return;
                }
                if (i4 == 0) {
                    standaloneModeChanger2.setDesktopModeState(booleanValue2 ? 3 : 1, 20);
                    StateManager stateManager2 =
                            (StateManager) standaloneModeChanger2.mStateManager;
                    stateManager2.getClass();
                    if (DesktopModeFeature.DEBUG) {
                        DesktopModeService$$ExternalSyntheticOutline0.m(
                                "notifyStartLoadingScreen(enter=",
                                ")",
                                "[DMS]StateManager",
                                booleanValue2);
                    }
                    Iterator it3 = stateManager2.mStateListeners.iterator();
                    while (it3.hasNext()) {
                        ((StateManager.StateListener) it3.next())
                                .onStartLoadingScreen(booleanValue2);
                    }
                }
                ModeChanger.ModeToModeChangeInfo modeToModeChangeInfo =
                        standaloneModeChanger2.mModeToModeChangeInfo;
                uiManager.showOverlay(
                        100,
                        (modeToModeChangeInfo == null
                                        || !modeToModeChangeInfo.mShowModeChangeScreen)
                                ? booleanValue2 ? 113 : 114
                                : booleanValue2 ? 116 : 117,
                        new UiManager
                                .InternalUiCallback() { // from class:
                                                        // com.android.server.desktopmode.StandaloneModeChanger.2
                            @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
                            public final void onAnimationComplete() {
                                StandaloneModeChanger standaloneModeChanger3 =
                                        StandaloneModeChanger.this;
                                standaloneModeChanger3.mHandler.removeMessages(1);
                                standaloneModeChanger3
                                        .mHandler
                                        .obtainMessage(1, 0, 0, Boolean.valueOf(booleanValue2))
                                        .sendToTarget();
                            }
                        });
                standaloneModeChanger2.mWindowManagerInternal.launchHomeForDesktopMode(0);
                if (booleanValue2) {
                    ModeChangeReceiver modeChangeReceiver =
                            standaloneModeChanger2.new ModeChangeReceiver();
                    standaloneModeChanger2.mReceiver = modeChangeReceiver;
                    IntentFilter intentFilter = new IntentFilter();
                    if (DesktopModeFeature.FEATURE_STANDALONE_MODE_WALLPAPER) {
                        intentFilter.addAction(
                                "com.samsung.android.intent.action.WALLPAPER_ENGINE_SHOWN");
                    }
                    intentFilter.addAction("android.intent.action.TIME_TICK");
                    standaloneModeChanger2.mContext.registerReceiverAsUser(
                            modeChangeReceiver, UserHandle.ALL, intentFilter, null, null, 2);
                    return;
                }
                return;
            }
            if (i2 != 3) {
                return;
            }
            StandaloneModeChanger standaloneModeChanger3 = StandaloneModeChanger.this;
            boolean z3 = standaloneModeChanger3.mStandaloneModeEnabled;
            int i5 = message.arg1;
            IStateManager iStateManager = standaloneModeChanger3.mStateManager;
            if (i5 == -1) {
                boolean z4 = DesktopModeFeature.DEBUG;
                if (z4) {
                    Log.w(
                            "[DMS]StandaloneModeChanger",
                            "Loading screen dismissed by timeout enter=" + z3);
                }
                if (!standaloneModeChanger3.hasPackageTask(0)) {
                    if (z4) {
                        Log.d("[DMS]StandaloneModeChanger", "DeX Home task does not exist");
                    }
                    standaloneModeChanger3.startHome(((StateManager) iStateManager).getState());
                }
            } else if (DesktopModeFeature.DEBUG) {
                Log.i("[DMS]StandaloneModeChanger", "Stopping loading screen... enter=" + z3);
            }
            SemDvfsManager semDvfsManager = standaloneModeChanger3.mDvfsManager;
            if (semDvfsManager != null) {
                semDvfsManager.release();
            }
            Configuration configuration =
                    standaloneModeChanger3.mContext.getResources().getConfiguration();
            StateManager stateManager3 = (StateManager) iStateManager;
            SemDesktopModeState semDesktopModeState = stateManager3.getState().mDesktopModeState;
            int configurationState = standaloneModeChanger3.getConfigurationState(configuration);
            if ((configurationState == 1 && !standaloneModeChanger3.mStandaloneModeEnabled)
                    || ((configurationState == 0 && standaloneModeChanger3.mStandaloneModeEnabled)
                            || configurationState == -1
                            || (((i = semDesktopModeState.enabled) == 3 && configurationState == 0)
                                    || (i == 1 && configurationState == 1)))) {
                StringBuilder sb =
                        new StringBuilder("verifyCurrentState(), Something is wrong! config=");
                sb.append(configuration);
                sb.append(", mStandaloneModeEnabled=");
                sb.append(standaloneModeChanger3.mStandaloneModeEnabled);
                sb.append(", desktopModeDensity=");
                MultiResolutionManager.StandaloneModeDisplayMetrics standaloneModeDisplayMetrics =
                        standaloneModeChanger3
                                .mMultiResolutionManager
                                .mStandaloneModeDisplayMetrics;
                MultiResolutionManager.DisplayMetrics displayMetrics =
                        MultiResolutionManager.this.mCustomDisplayMetrics;
                if (displayMetrics == null) {
                    displayMetrics = standaloneModeDisplayMetrics.mSelectedDisplayMetrics;
                }
                sb.append(displayMetrics.density);
                sb.append(", desktopModeState=");
                sb.append(semDesktopModeState);
                String sb2 = sb.toString();
                boolean z5 = DesktopModeFeature.DEBUG;
                if (z5) {
                    ToastManager.showToast(standaloneModeChanger3.mContext, sb2, 1000);
                }
                Log.e("[DMS]StandaloneModeChanger", sb2);
                int i6 = semDesktopModeState.enabled;
                if ((i6 == 3 && configurationState == 1) || (i6 == 1 && configurationState == 0)) {
                    Log.e(
                            "[DMS]StandaloneModeChanger",
                            "Configuration is changed correctly, but was too late. Calling"
                                + " onConfigurationChanged() directly...");
                    standaloneModeChanger3.handleOnConfigurationChanged(configuration);
                    standaloneModeChanger3.mHandler.removeMessages(3);
                    standaloneModeChanger3.mHandler.sendEmptyMessageDelayed(3, 0L);
                    return;
                }
                if (z5) {
                    throw new IllegalStateException(sb2);
                }
                stateManager3.notifyScheduleUpdateDesktopMode(false);
            }
            standaloneModeChanger3.setDesktopModeState(z3 ? 4 : 2, 0);
            ModeChanger.ModeToModeChangeInfo modeToModeChangeInfo2 =
                    standaloneModeChanger3.mModeToModeChangeInfo;
            if (modeToModeChangeInfo2 == null || modeToModeChangeInfo2.mModeToMode == 1) {
                standaloneModeChanger3.bringTaskToForeground(
                        standaloneModeChanger3.mTopTaskId, 0, z3 ? 5 : 1);
            }
            ModeChanger.ModeToModeChangeInfo modeToModeChangeInfo3 =
                    standaloneModeChanger3.mModeToModeChangeInfo;
            standaloneModeChanger3.mUiManager.dismissOverlay(
                    100,
                    (modeToModeChangeInfo3 == null || !modeToModeChangeInfo3.mShowModeChangeScreen)
                            ? z3 ? 113 : 114
                            : z3 ? 116 : 117);
            if (DesktopModeFeature.DEBUG) {
                DesktopModeService$$ExternalSyntheticOutline0.m(
                        "notifyStopLoadingScreen(enter=", ")", "[DMS]StateManager", z3);
            }
            Iterator it4 = stateManager3.mStateListeners.iterator();
            while (it4.hasNext()) {
                ((StateManager.StateListener) it4.next()).onStopLoadingScreen(z3);
            }
            standaloneModeChanger3.setModeChangeLock$1(false);
            if (!z3) {
                ModeChangeReceiver modeChangeReceiver2 = standaloneModeChanger3.mReceiver;
                if (modeChangeReceiver2 != null) {
                    StandaloneModeChanger.this.mContext.unregisterReceiver(modeChangeReceiver2);
                    standaloneModeChanger3.mReceiver = null;
                } else {
                    Log.e("[DMS]StandaloneModeChanger", "mReceiver is null!");
                }
                standaloneModeChanger3.mActivityManagerInternal.killProcessWhenDexExit();
            }
            stateManager3.notifyScheduleUpdateDesktopMode(!z3);
            standaloneModeChanger3.showDexMirroringTipsNotification(
                    standaloneModeChanger3.mContext.getString(
                            R.string.heavy_weight_notification_detail),
                    standaloneModeChanger3.mContext.getString(R.string.heavy_weight_notification));
            Trace.asyncTraceEnd(524288L, "loading screen", 0);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModeChangeReceiver extends BroadcastReceiver {
        public ModeChangeReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            boolean z = DesktopModeFeature.DEBUG;
            if (z) {
                Log.d("[DMS]StandaloneModeChanger", "onReceive(), action=" + action);
            }
            if ("com.samsung.android.intent.action.WALLPAPER_ENGINE_SHOWN".equals(action)) {
                StandaloneModeChanger standaloneModeChanger = StandaloneModeChanger.this;
                standaloneModeChanger.mWallpaperShown = true;
                standaloneModeChanger.scheduleStopLoadingScreenIfPossible();
            } else if ("android.intent.action.TIME_TICK".equals(action)) {
                long freeMemory = Process.getFreeMemory();
                if (freeMemory < 419430400) {
                    if (z) {
                        Log.w(
                                "[DMS]StandaloneModeChanger",
                                "Low memory warning: " + (freeMemory / 1048576) + "MB");
                    }
                    Context context2 = StandaloneModeChanger.this.mContext;
                    List list = ToastManager.sToasts;
                    ToastManager.showToast(
                            context2, context2.getString(R.string.httpErrorFileNotFound), 1);
                }
            }
        }
    }

    public StandaloneModeChanger(
            Context context,
            IStateManager iStateManager,
            SemDesktopModeStateNotifier semDesktopModeStateNotifier,
            ServiceThread serviceThread,
            UiManager uiManager,
            SettingsHelper settingsHelper,
            MultiResolutionManager multiResolutionManager,
            ActivityTaskManagerService activityTaskManagerService,
            ActivityTaskManagerInternal activityTaskManagerInternal,
            ActivityManagerInternal activityManagerInternal,
            WindowManagerInternal windowManagerInternal,
            MultiWindowManager multiWindowManager,
            SemDvfsManager semDvfsManager,
            TelecomManager telecomManager,
            IUiModeManager iUiModeManager,
            UiModeManagerService.LocalService localService) {
        super(
                context,
                iStateManager,
                semDesktopModeStateNotifier,
                uiManager,
                settingsHelper,
                multiResolutionManager,
                activityTaskManagerService,
                activityTaskManagerInternal,
                activityManagerInternal,
                windowManagerInternal,
                multiWindowManager);
        this.mStandaloneModeEnabled = false;
        this.mWallpaperShown = false;
        this.mTopTaskId = -1;
        this.mCurrentUiMode = -1;
        StateManager.StateListener stateListener =
                new StateManager
                        .StateListener() { // from class:
                                           // com.android.server.desktopmode.StandaloneModeChanger.1
                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onUserChanged(StateManager.InternalState internalState) {
                        StandaloneModeChanger.this.storeDefaultHomePackageName(
                                new StandaloneModeChanger$1$$ExternalSyntheticLambda0(), false);
                    }
                };
        this.mHandler = new ModeChangeHandler(serviceThread.getLooper());
        this.mTelecomManager = telecomManager;
        this.mUiModeManager = iUiModeManager;
        this.mUiModeManagerInternal = localService;
        this.mDvfsManager = semDvfsManager;
        if (semDvfsManager != null) {
            semDvfsManager.setHint(3000);
        }
        ((StateManager) this.mStateManager).registerListener(stateListener);
    }

    public final int getConfigurationState(Configuration configuration) {
        boolean z;
        boolean z2;
        boolean z3 = (configuration.uiMode & 15) == 2;
        boolean z4 = configuration.semDesktopModeEnabled == 1;
        MultiResolutionManager multiResolutionManager = this.mMultiResolutionManager;
        if (multiResolutionManager != null) {
            int i = configuration.densityDpi;
            MultiResolutionManager.StandaloneModeDisplayMetrics standaloneModeDisplayMetrics =
                    multiResolutionManager.mStandaloneModeDisplayMetrics;
            MultiResolutionManager.DisplayMetrics displayMetrics =
                    MultiResolutionManager.this.mCustomDisplayMetrics;
            if (displayMetrics == null) {
                displayMetrics = standaloneModeDisplayMetrics.mSelectedDisplayMetrics;
            }
            if (i == displayMetrics.density) {
                z = true;
                z2 =
                        multiResolutionManager == null
                                && configuration.densityDpi
                                        == multiResolutionManager.mStandaloneModeDisplayMetrics
                                                .getOriginalDisplaySizeDensity()
                                                .density;
                if (!z3 && z4 && z) {
                    return 1;
                }
                return (z4 || !z2) ? -1 : 0;
            }
        }
        z = false;
        if (multiResolutionManager == null) {}
        if (!z3) {}
        if (z4) {
            return -1;
        }
    }

    public final void handleOnConfigurationChanged(Configuration configuration) {
        StateManager stateManager = (StateManager) this.mStateManager;
        StateManager.InternalState state = stateManager.getState();
        SemDesktopModeState semDesktopModeState = state.mDesktopModeState;
        if (semDesktopModeState.state == 30 && semDesktopModeState.getDisplayType() == 101) {
            boolean z = DesktopModeFeature.DEBUG;
            if (z) {
                StringBuilder sb = new StringBuilder("handleOnConfigurationChanged(), newConfig=");
                sb.append(configuration);
                sb.append(", desktopModeDensity=");
                MultiResolutionManager.StandaloneModeDisplayMetrics standaloneModeDisplayMetrics =
                        this.mMultiResolutionManager.mStandaloneModeDisplayMetrics;
                MultiResolutionManager.DisplayMetrics displayMetrics =
                        MultiResolutionManager.this.mCustomDisplayMetrics;
                if (displayMetrics == null) {
                    displayMetrics = standaloneModeDisplayMetrics.mSelectedDisplayMetrics;
                }
                sb.append(displayMetrics.density);
                sb.append(", desktopModeState=");
                sb.append(semDesktopModeState);
                Log.v("[DMS]StandaloneModeChanger", sb.toString());
            }
            int configurationState = getConfigurationState(configuration);
            if ((configurationState == 1 && semDesktopModeState.enabled == 3)
                    || (configurationState == 0 && semDesktopModeState.enabled == 1)) {
                boolean z2 = configurationState == 1;
                if (z) {
                    Log.d(
                            "[DMS]StandaloneModeChanger",
                            "handleOnConfigurationChanged(), enabled=" + z2);
                }
                setDesktopModeState(z2 ? 4 : 2, 40);
                this.mStandaloneModeEnabled = z2;
                ActivityTaskManagerInternal activityTaskManagerInternal =
                        this.mActivityTaskManagerInternal;
                if (configurationState == 1 && semDesktopModeState.enabled == 3) {
                    activityTaskManagerInternal.clearHomeStack(2);
                }
                startHome(state);
                activityTaskManagerInternal.clearHomeStack(0);
                SettingsHelper settingsHelper = this.mSettingsHelper;
                settingsHelper.getClass();
                settingsHelper.backupOrRestoreSettings(z2, state, state.mCurrentUserId);
                if (!z2) {
                    if (this.mTelecomManager.isInCall()) {
                        if (z) {
                            Log.d("[DMS]StandaloneModeChanger", "Ongoing phone call!");
                        }
                        this.mWallpaperShown = true;
                        this.mTelecomManager.showInCallScreen(false);
                    }
                    if (z) {
                        Log.d("[DMS]ModeChanger", "clearSettingsBadgeCount()");
                    }
                    Settings.System.putIntForUser(
                            this.mContext.getContentResolver(), "badge_for_dex", 0, -2);
                } else if (this.mTelecomManager.isInCall()) {
                    int i = stateManager.getState().mCurrentUserId;
                    RoleManager roleManager = (RoleManager) this.mContext.getSystemService("role");
                    if (Settings.System.getIntForUser(this.mResolver, "skt_phone20_settings", -1, i)
                            != 1) {
                        String str = null;
                        if (roleManager != null) {
                            List roleHoldersAsUser =
                                    roleManager.getRoleHoldersAsUser(
                                            "android.app.role.DIALER", Process.myUserHandle());
                            if (!roleHoldersAsUser.isEmpty()) {
                                str = (String) roleHoldersAsUser.get(0);
                            }
                        }
                        if (z) {
                            Log.d(
                                    "[DMS]StandaloneModeChanger",
                                    "usingNativeInCallUi(), package name: " + str);
                        }
                        if (str != null && !"com.samsung.android.contacts".equals(str)) {
                            Iterator<ResolveInfo> it =
                                    this.mContext
                                            .getPackageManager()
                                            .queryIntentServices(
                                                    new Intent("android.telecom.InCallService"),
                                                    131072)
                                            .iterator();
                            while (it.hasNext()) {
                                ServiceInfo serviceInfo = it.next().serviceInfo;
                                if (serviceInfo != null && str.equals(serviceInfo.packageName)) {
                                    if (DesktopModeFeature.DEBUG) {
                                        Log.d(
                                                "[DMS]StandaloneModeChanger",
                                                "usingNativeInCallUi()=false");
                                    }
                                }
                            }
                        }
                        if (DesktopModeFeature.DEBUG) {
                            Log.d("[DMS]StandaloneModeChanger", "usingNativeInCallUi()=true");
                        }
                    } else if (z) {
                        Log.d(
                                "[DMS]StandaloneModeChanger",
                                "usingNativeInCallUi()=false, using T Phone");
                    }
                    if (DesktopModeFeature.DEBUG) {
                        Log.d("[DMS]StandaloneModeChanger", "Ongoing phone call!");
                    }
                    this.mTelecomManager.showInCallScreen(false);
                }
                stateManager.getClass();
                if (DesktopModeFeature.DEBUG) {
                    DesktopModeService$$ExternalSyntheticOutline0.m(
                            "notifyOnConfigurationChanged(enter=", ")", "[DMS]StateManager", z2);
                }
                Iterator it2 = stateManager.mStateListeners.iterator();
                while (it2.hasNext()) {
                    ((StateManager.StateListener) it2.next()).getClass();
                }
                setDesktopModeState(z2 ? 4 : 2, 50);
                scheduleStopLoadingScreenIfPossible();
            }
        }
    }

    public final void scheduleStartLoadingScreen(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            DesktopModeService$$ExternalSyntheticOutline0.m(
                    "scheduleStartLoadingScreen(), enter=",
                    ", reason=0",
                    "[DMS]StandaloneModeChanger",
                    z);
        }
        this.mHandler.removeMessages(2);
        this.mHandler.sendMessageDelayed(
                this.mHandler.obtainMessage(2, 0, 0, Boolean.valueOf(z)), 0);
        this.mHandler.removeMessages(3);
        this.mHandler.sendMessageDelayed(
                this.mHandler.obtainMessage(3, -1, 0, Boolean.valueOf(z)), 15000L);
    }

    public final void scheduleStopLoadingScreenIfPossible() {
        String str;
        SemDesktopModeState semDesktopModeState =
                ((StateManager) this.mStateManager).getState().mDesktopModeState;
        boolean z = DesktopModeFeature.DEBUG;
        UiManager uiManager = this.mUiManager;
        if (z) {
            StringBuilder sb = new StringBuilder("scheduleStopLoadingScreenIfPossible()");
            if (DesktopModeFeature.FEATURE_STANDALONE_MODE_WALLPAPER) {
                str = ", mWallpaperShown=" + this.mWallpaperShown;
            } else {
                str = "";
            }
            sb.append(str);
            sb.append(", desktopModeState=");
            sb.append(semDesktopModeState);
            sb.append(", mStandaloneModeEnabled=");
            sb.append(this.mStandaloneModeEnabled);
            sb.append(", internal overlay=");
            sb.append(uiManager.getCurrentOverlayType(102));
            sb.append(", external overlay=");
            sb.append(uiManager.getCurrentOverlayType(103));
            Log.d("[DMS]StandaloneModeChanger", sb.toString());
        }
        if ((!DesktopModeFeature.FEATURE_STANDALONE_MODE_WALLPAPER || this.mWallpaperShown)
                && semDesktopModeState.state == 50) {
            if ((uiManager.hasOverlay(113) && this.mStandaloneModeEnabled)
                    || ((uiManager.hasOverlay(114) && !this.mStandaloneModeEnabled)
                            || uiManager.hasOverlay(117)
                            || uiManager.hasOverlay(116))) {
                this.mHandler.removeMessages(3);
                this.mHandler.sendEmptyMessageDelayed(3, 0L);
            }
        }
    }

    public final void setDesktopMode(
            StateManager.InternalState internalState,
            final boolean z,
            ModeChanger.ModeToModeChangeInfo modeToModeChangeInfo) {
        boolean z2 = DesktopModeFeature.DEBUG;
        if (z2) {
            Log.d("[DMS]StandaloneModeChanger", "setDesktopMode(), desktopMode=" + z);
        }
        if (z) {
            this.mActivityManagerInternal.cancelKillProcessWhenDexExit();
        }
        setModeChangeLock$1(true);
        DesktopModeSettings.setSettings(this.mResolver, "display_type", z ? 101 : 0);
        this.mModeToModeChangeInfo = modeToModeChangeInfo;
        if (modeToModeChangeInfo == null || modeToModeChangeInfo.mModeToMode != 1) {
            int foregroundTaskId = this.mActivityTaskManagerInternal.getForegroundTaskId(0);
            this.mTopTaskId = foregroundTaskId;
            if (modeToModeChangeInfo != null) {
                modeToModeChangeInfo.mTopTaskId = foregroundTaskId;
            }
        } else {
            this.mTopTaskId = modeToModeChangeInfo.mTopTaskId;
        }
        storeDefaultHomePackageName(
                new Consumer() { // from class:
                                 // com.android.server.desktopmode.StandaloneModeChanger$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        StandaloneModeChanger standaloneModeChanger = StandaloneModeChanger.this;
                        boolean z3 = z;
                        standaloneModeChanger.getClass();
                        if (!((Boolean) obj).booleanValue()) {
                            Log.e(
                                    "[DMS]StandaloneModeChanger",
                                    "Failed to add home role holder. desktopMode=" + z3);
                        }
                        standaloneModeChanger.scheduleStartLoadingScreen(z3);
                    }
                },
                z);
        SemDvfsManager semDvfsManager = this.mDvfsManager;
        if (semDvfsManager != null) {
            semDvfsManager.acquire();
        }
        StateManager stateManager = (StateManager) this.mStateManager;
        stateManager.getClass();
        if (z2) {
            DesktopModeService$$ExternalSyntheticOutline0.m(
                    "notifySetDesktopMode(enter=", ")", "[DMS]StateManager", z);
        }
        Iterator it = stateManager.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateManager.StateListener) it.next()).onSetDesktopMode(z);
        }
    }

    @Override // com.android.server.desktopmode.ModeChanger
    public final void setDesktopModeState(int i, int i2) {
        SemDesktopModeState semDesktopModeState =
                new SemDesktopModeState(i, i2, (i == 2 && i2 == 0) ? 0 : 101);
        Log.d(
                "[DMS]StandaloneModeChanger",
                "setDesktopModeState(), newState=" + semDesktopModeState);
        StateManager stateManager = (StateManager) this.mStateManager;
        if (stateManager.getState().mDesktopModeState.equals(semDesktopModeState)) {
            return;
        }
        stateManager.setDesktopModeState(semDesktopModeState);
        this.mSemDesktopModeStateNotifier.notifyDesktopModeStateChanged(semDesktopModeState);
    }

    public final void setModeChangeLock$1(boolean z) {
        ((StateManager) this.mStateManager).setModeChangeLocked(z);
        UiManager uiManager = this.mUiManager;
        if (uiManager.mChangingStandaloneMode != z) {
            uiManager.mChangingStandaloneMode = z;
            if (DesktopModeFeature.DEBUG) {
                Log.d(
                        "[DMS]UiManager",
                        "setChangingStandaloneMode(), mChangingStandaloneMode=" + z);
            }
            if (z) {
                return;
            }
            uiManager.mPendingUiCommands.flushCommands();
        }
    }

    public final void startHome(StateManager.InternalState internalState) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]StandaloneModeChanger", "startHome()");
        }
        this.mContext.startActivityAsUser(
                new Intent("android.intent.action.MAIN")
                        .addCategory("android.intent.category.HOME")
                        .setFlags(270532608)
                        .putExtra("DesktopModeService", true),
                UserHandle.of(internalState.mCurrentUserId));
    }

    public final void storeDefaultHomePackageName(Consumer consumer, boolean z) {
        RoleManager roleManager = (RoleManager) this.mContext.getSystemService("role");
        if (z) {
            roleManager.addRoleHolderAsUser(
                    "android.app.role.HOME",
                    "com.sec.android.app.desktoplauncher",
                    0,
                    Process.myUserHandle(),
                    this.mContext.getMainExecutor(),
                    consumer);
            ContentResolver contentResolver = this.mResolver;
            List roleHoldersAsUser =
                    roleManager.getRoleHoldersAsUser(
                            "android.app.role.HOME", Process.myUserHandle());
            DesktopModeSettings.setSettings(
                    contentResolver,
                    "default_home_package",
                    roleHoldersAsUser.isEmpty() ? null : (String) roleHoldersAsUser.get(0));
            return;
        }
        String settingsAsUser =
                DesktopModeSettings.getSettingsAsUser(
                        this.mResolver,
                        "default_home_package",
                        (String) null,
                        DesktopModeSettings.sCurrentUserId);
        if (settingsAsUser != null) {
            roleManager.addRoleHolderAsUser(
                    "android.app.role.HOME",
                    settingsAsUser,
                    0,
                    Process.myUserHandle(),
                    this.mContext.getMainExecutor(),
                    consumer);
            DesktopModeSettings.deleteSettingsAsUser(
                    this.mResolver, "default_home_package", DesktopModeSettings.sCurrentUserId);
        } else {
            consumer.accept(Boolean.FALSE);
            Log.w(
                    "[DMS]StandaloneModeChanger",
                    "storeDefaultHomePackageName(), packageName is null");
        }
    }
}
