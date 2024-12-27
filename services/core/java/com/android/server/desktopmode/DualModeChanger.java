package com.android.server.desktopmode;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;

import com.android.internal.statusbar.IStatusBarService;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerInternal;

import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.DesktopModeUiConstants;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.multiwindow.MultiWindowManager;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DualModeChanger extends ModeChanger {
    public final CoverStateManager mCoverStateManager;
    public Context mDesktopDisplayContext;
    public int mDesktopDisplayId;
    public boolean mDisplayUnsupportDialogShown;
    public boolean mDualModeEnabled;
    public final KeyguardManager mKeyguardManager;
    public boolean mLoadingScreenAnimationComplete;
    public ModeChanger.ModeToModeChangeInfo mModeToModeChangeInfo;
    public final PowerManager mPowerManager;
    public final PowerManagerInternal mPowerManagerInternal;
    public int mPrevDesktopDisplayId;
    public ModeChangeReceiver mReceiver;
    public final AnonymousClass1 mStabilizerModeChangedListener;
    public ModeChangeStateListener mStateListener;
    public final IStatusBarService mStatusBarService;
    public int mTopTaskId;
    public boolean mTopTaskIdValid;
    public final TouchpadManager mTouchpadManager;
    public boolean mTouchpadRequestedByNotification;
    public final AnonymousClass1 mTouchpadSettingChangedListener;
    public boolean mWallpaperShown;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.desktopmode.DualModeChanger$3, reason: invalid class name */
    public final class AnonymousClass3 extends UiManager.InternalUiCallback {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ DualModeChanger this$0;

        public /* synthetic */ AnonymousClass3(DualModeChanger dualModeChanger, int i) {
            this.$r8$classId = i;
            this.this$0 = dualModeChanger;
        }

        @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
        public void onAnimationComplete() {
            switch (this.$r8$classId) {
                case 0:
                    DualModeChanger dualModeChanger = this.this$0;
                    if (!dualModeChanger.mLoadingScreenAnimationComplete) {
                        if (DesktopModeFeature.DEBUG) {
                            Log.d("[DMS]DualModeChanger", "onAnimationComplete()");
                        }
                        dualModeChanger.mLoadingScreenAnimationComplete = true;
                        dualModeChanger.scheduleStopLoadingScreenIfPossible(true);
                        break;
                    }
                    break;
            }
        }

        @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
        public void onClickButtonPositive() {
            switch (this.$r8$classId) {
                case 1:
                    if (DesktopModeFeature.DEBUG) {
                        Log.d("[DMS]DualModeChanger", "onClickButtonPositive()");
                    }
                    DesktopModeSettings.setSettings(
                            this.this$0.mResolver, "stabilizer_mode_consent", true);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModeChangeHandler extends Handler {
        public ModeChangeHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            DualModeChanger dualModeChanger = DualModeChanger.this;
            if (i != 2) {
                if (i == 3 && dualModeChanger.mStateManager != null) {
                    if (DesktopModeFeature.DEBUG) {
                        Log.d("[DMS]DualModeChanger", "mStateListener unregistered");
                    }
                    ((StateManager) dualModeChanger.mStateManager)
                            .unregisterListener(dualModeChanger.mStateListener);
                    dualModeChanger.mStateListener = null;
                    return;
                }
                return;
            }
            boolean booleanValue = ((Boolean) message.obj).booleanValue();
            int i2 = message.arg1;
            StateManager stateManager = (StateManager) dualModeChanger.mStateManager;
            StateManager.InternalState state = stateManager.getState();
            if (i2 == -1) {
                boolean z = DesktopModeFeature.DEBUG;
                if (z) {
                    Log.w("[DMS]DualModeChanger", "Loading screen timeout! enter=" + booleanValue);
                }
                dualModeChanger.mWallpaperShown = true;
                dualModeChanger.mLoadingScreenAnimationComplete = true;
                if (!dualModeChanger.hasPackageTask(state.mDesktopDisplayId)) {
                    if (z) {
                        Log.d(
                                "[DMS]DualModeChanger",
                                "DeX Home task does not exist in DeX display");
                    }
                    int i3 = dualModeChanger.mDesktopDisplayId;
                    if (i3 != -1) {
                        dualModeChanger.startHome(state, i3);
                    }
                }
            }
            if (state.mDesktopModeState.state != 50) {
                if (DesktopModeFeature.DEBUG) {
                    StringBuilder m =
                            BatteryService$$ExternalSyntheticOutline0.m(
                                    "Failed to stop loading screen. onDesktopDisplayConfigured() is"
                                        + " not called! enter=",
                                    ", desktopModeState=",
                                    booleanValue);
                    m.append(state.mDesktopModeState);
                    Log.w("[DMS]DualModeChanger", m.toString());
                    return;
                }
                return;
            }
            boolean z2 = DesktopModeFeature.DEBUG;
            if (z2) {
                Log.i(
                        "[DMS]DualModeChanger",
                        "Stopping loading screen... enter=" + booleanValue + " state=" + state);
            }
            if (dualModeChanger.mTopTaskIdValid && booleanValue) {
                dualModeChanger.bringTaskToForeground(
                        dualModeChanger.mTopTaskId, dualModeChanger.mDesktopDisplayId, 5);
                dualModeChanger.mTopTaskIdValid = false;
            }
            UiManager uiManager = dualModeChanger.mUiManager;
            uiManager.dismissOverlay(103, 111);
            ((StateManager) dualModeChanger.mStateManager).setModeChangeLocked(false);
            dualModeChanger.setDesktopModeState(booleanValue ? 4 : 2, 0);
            if (z2) {
                DesktopModeService$$ExternalSyntheticOutline0.m(
                        "notifyDualStopLoadingScreen(enter=",
                        ")",
                        "[DMS]StateManager",
                        booleanValue);
            }
            Iterator it = stateManager.mStateListeners.iterator();
            while (it.hasNext()) {
                ((StateManager.StateListener) it.next()).onDualModeStopLoadingScreen(booleanValue);
            }
            if (booleanValue) {
                dualModeChanger.mDisplayUnsupportDialogShown = false;
                if (state.mDisplayResolutionUnsupported) {
                    dualModeChanger.mUiManager.showDialog(state.mDesktopDisplayId, 2, null);
                    dualModeChanger.mDisplayUnsupportDialogShown = true;
                }
                if (dualModeChanger.mDesktopDisplayContext != null
                        && DesktopModeSettings.getSettingsAsUser(
                                dualModeChanger.mResolver,
                                "auto_open_last_app",
                                false,
                                DesktopModeSettings.sCurrentUserId)) {
                    Context context = dualModeChanger.mDesktopDisplayContext;
                    List list = ToastManager.sToasts;
                    ToastManager.showToast(context, context.getString(R.string.hardware), 1);
                }
                dualModeChanger.showDexMirroringTipsNotification(
                        dualModeChanger.mContext.getString(
                                R.string.heavy_weight_notification_detail),
                        dualModeChanger.mContext.getString(R.string.heavy_weight_notification));
                if (state.isWirelessDexConnected()) {
                    uiManager.showNotification(204);
                }
                dualModeChanger.showStabilizerModeDialogIfNeeded();
            } else {
                ModeChangeReceiver modeChangeReceiver = dualModeChanger.mReceiver;
                DualModeChanger.this.mContext.unregisterReceiver(modeChangeReceiver);
                dualModeChanger.mReceiver = null;
                dualModeChanger.mHandler.removeMessages(3);
                dualModeChanger.mHandler.sendEmptyMessageDelayed(3, 2000L);
                dualModeChanger.mActivityManagerInternal.killProcessWhenDexExit();
            }
            stateManager.notifyScheduleUpdateDesktopMode(!booleanValue);
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
                Log.d("[DMS]DualModeChanger", "onReceive(), action=" + action);
            }
            StateManager.InternalState state =
                    ((StateManager) DualModeChanger.this.mStateManager).getState();
            if ("com.samsung.android.intent.action.WALLPAPER_ENGINE_SHOWN".equals(action)) {
                DualModeChanger dualModeChanger = DualModeChanger.this;
                if (dualModeChanger.mWallpaperShown) {
                    return;
                }
                dualModeChanger.mWallpaperShown = true;
                dualModeChanger.scheduleStopLoadingScreenIfPossible(true);
                return;
            }
            if ("android.intent.action.TIME_TICK".equals(action)) {
                long freeMemory = Process.getFreeMemory();
                if (freeMemory < 419430400) {
                    if (z) {
                        Log.w(
                                "[DMS]DualModeChanger",
                                "Low memory warning: " + (freeMemory / 1048576) + "MB");
                    }
                    Context context2 = DualModeChanger.this.mDesktopDisplayContext;
                    if (context2 != null) {
                        List list = ToastManager.sToasts;
                        ToastManager.showToast(
                                context2, context2.getString(R.string.httpErrorFileNotFound), 1);
                        return;
                    }
                    return;
                }
                return;
            }
            if ("com.samsung.android.desktopmode.action.SPEN_NOTIFICATION_PRESSED".equals(action)) {
                int intExtra = intent.getIntExtra("ACTION_CLICK_DISPLAYID", 0);
                if (z) {
                    DesktopModeService$$ExternalSyntheticOutline0.m(
                            intExtra,
                            "SPen notification clicked, displayID=",
                            "[DMS]DualModeChanger");
                }
                DualModeChanger.m408$$Nest$mcollapsePanelsToDisplay(DualModeChanger.this, intExtra);
                DualModeChanger.this.mUiManager.showDialog(intExtra, 6, null);
                return;
            }
            if ("com.samsung.android.desktopmode.action.TOUCHPAD_NOTIFICATION_PRESSED"
                    .equals(action)) {
                int intExtra2 = intent.getIntExtra("CLICK_DISPLAYID", 0);
                DualModeChanger.m408$$Nest$mcollapsePanelsToDisplay(
                        DualModeChanger.this, intExtra2);
                if (z) {
                    DesktopModeService$$ExternalSyntheticOutline0.m(
                            intExtra2,
                            "Touchpad notification clicked, displayID=",
                            "[DMS]DualModeChanger");
                }
                DualModeChanger.this.mUiManager.showDialog(intExtra2, 1, null);
                return;
            }
            if ("com.samsung.android.desktopmode.action.SPEN_NOTIFICATION_CHANGE_MODE_PRESSED"
                    .equals(action)) {
                int intExtra3 = intent.getIntExtra("ACTION_CLICK_DISPLAYID", 0);
                if (z) {
                    DesktopModeService$$ExternalSyntheticOutline0.m(
                            intExtra3,
                            "SPen notification change mode clicked, displayID=",
                            "[DMS]DualModeChanger");
                }
                DualModeChanger.m408$$Nest$mcollapsePanelsToDisplay(
                        DualModeChanger.this, intExtra3);
                DualModeChanger dualModeChanger2 = DualModeChanger.this;
                dualModeChanger2.getClass();
                Intent flags =
                        new Intent()
                                .setComponent(
                                        new ComponentName(
                                                "com.sec.android.app.desktoplauncher",
                                                "com.android.launcher3.settings.SPenInputModePreferenceActivity"))
                                .setFlags(337641472);
                if (state.mDesktopDisplayId != -1) {
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setLaunchDisplayId(state.mDesktopDisplayId);
                    dualModeChanger2.mContext.startActivityAsUser(
                            flags, makeBasic.toBundle(), UserHandle.of(state.mCurrentUserId));
                    return;
                }
                return;
            }
            if (!"com.samsung.android.desktopmode.action.TOUCHPAD_AVAILABLE_NOTIFICATION_PRESSED"
                    .equals(action)) {
                if ("android.intent.action.ACTION_SHUTDOWN".equals(action)
                        && getSendingUserId() == -1
                        && state.mDesktopDisplayId != -1) {
                    DualModeChanger.this.mMultiResolutionManager.setDualDisplayResolutionDensity(
                            false);
                    return;
                }
                return;
            }
            DualModeChanger.m408$$Nest$mcollapsePanelsToDisplay(
                    DualModeChanger.this, intent.getIntExtra("CLICK_DISPLAYID", 0));
            DualModeChanger dualModeChanger3 = DualModeChanger.this;
            TouchpadManager touchpadManager = dualModeChanger3.mTouchpadManager;
            dualModeChanger3.mUiManager.startActivity(0, 300, touchpadManager.mInternalUiCallback);
            dualModeChanger3.mActivityTaskManagerInternal.minimizeAllTasks(0, true);
            if (dualModeChanger3.mKeyguardManager.isKeyguardLocked()
                    && dualModeChanger3.mKeyguardManager.isKeyguardSecure()
                    && !state.mIsNavBarGestureEnabled
                    && dualModeChanger3
                            .mContext
                            .getResources()
                            .getBoolean(R.bool.config_sms_decode_gsm_8bit_data)) {
                dualModeChanger3.mKeyguardManager.semDismissKeyguard();
                return;
            }
            if (!touchpadManager.mTouchpadRequested.get()) {
                int settingsAsUser =
                        DesktopModeSettings.getSettingsAsUser(
                                touchpadManager.mContext.getContentResolver(),
                                "touchpad_auto_run_guide_count",
                                0,
                                DesktopModeSettings.sCurrentUserId);
                if (settingsAsUser < 3) {
                    DesktopModeSettings.setSettings(
                            touchpadManager.mContext.getContentResolver(),
                            "touchpad_auto_run_guide_count",
                            settingsAsUser + 1);
                }
                touchpadManager.mTouchpadRequested.set(true);
            }
            dualModeChanger3.mTouchpadRequestedByNotification = true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModeChangeStateListener extends StateManager.StateListener {
        public ModeChangeStateListener() {}

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public final void onExternalDisplayConnectionChanged(
                StateManager.InternalState internalState) {
            if (internalState.mIsExternalDisplayConnected) {
                DualModeChanger dualModeChanger = DualModeChanger.this;
                if (!dualModeChanger.mDisplayUnsupportDialogShown
                        && internalState.mDesktopModeState.enabled == 4
                        && internalState.mDisplayResolutionUnsupported) {
                    dualModeChanger.mUiManager.showDialog(internalState.mDesktopDisplayId, 2, null);
                    dualModeChanger.mDisplayUnsupportDialogShown = true;
                }
            }
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public final void onNavBarGestureEnabled(StateManager.InternalState internalState) {
            if (DesktopModeFeature.DEBUG) {
                Log.d("[DMS]DualModeChanger", "onNavBarGestureEnabled(), state=" + internalState);
            }
            DualModeChanger.m411$$Nest$mupdateTouchpadNotification(
                    DualModeChanger.this, internalState);
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public final void onSpenEnabled(StateManager.InternalState internalState) {
            DualModeChanger.m410$$Nest$mupdateSpenNotification(DualModeChanger.this, internalState);
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public final void onTouchpadAvailabilityChanged(StateManager.InternalState internalState) {
            if (DesktopModeFeature.DEBUG) {
                Log.d(
                        "[DMS]DualModeChanger",
                        "onTouchpadAvailabilityChanged(), state=" + internalState);
            }
            DualModeChanger dualModeChanger = DualModeChanger.this;
            DualModeChanger.m411$$Nest$mupdateTouchpadNotification(dualModeChanger, internalState);
            DualModeChanger.m409$$Nest$mupdateNavBarIcon(dualModeChanger, internalState);
            if (!internalState.mTouchpadAvailable) {
                dualModeChanger.mUiManager.finishActivity(300);
                return;
            }
            if (DesktopModeSettings.getSettingsAsUser(
                    dualModeChanger.mResolver,
                    "touchpad_auto_run",
                    false,
                    DesktopModeSettings.sCurrentUserId)) {
                dualModeChanger.mUiManager.startActivity(
                        0, 300, dualModeChanger.mTouchpadManager.mInternalUiCallback);
            }
            if (DesktopModeSettings.getSettingsAsUser(
                    dualModeChanger.mResolver,
                    "how_to_open_touchpad_shown",
                    false,
                    DesktopModeSettings.sCurrentUserId)) {
                return;
            }
            dualModeChanger.mUiManager.showDialog(
                    0, !dualModeChanger.canShowTouchpadNotification(internalState) ? 9 : 7, null);
            DesktopModeSettings.setSettings(
                    dualModeChanger.mResolver, "how_to_open_touchpad_shown", true);
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public final void onTouchpadEnabled(StateManager.InternalState internalState) {
            if (DesktopModeFeature.DEBUG) {
                Log.d("[DMS]DualModeChanger", "onTouchpadEnabled(), state=" + internalState);
            }
            DualModeChanger dualModeChanger = DualModeChanger.this;
            DualModeChanger.m411$$Nest$mupdateTouchpadNotification(dualModeChanger, internalState);
            DualModeChanger.m410$$Nest$mupdateSpenNotification(dualModeChanger, internalState);
            DualModeChanger.m409$$Nest$mupdateNavBarIcon(dualModeChanger, internalState);
        }
    }

    /* renamed from: -$$Nest$mcollapsePanelsToDisplay, reason: not valid java name */
    public static void m408$$Nest$mcollapsePanelsToDisplay(DualModeChanger dualModeChanger, int i) {
        dualModeChanger.getClass();
        try {
            IStatusBarService iStatusBarService = dualModeChanger.mStatusBarService;
            if (iStatusBarService != null) {
                iStatusBarService.collapsePanelsToType(i == 0 ? 0 : 1);
            }
        } catch (RemoteException e) {
            Log.e("[DMS]DualModeChanger", "collapsePanelsToDisplay failed.", e);
        }
    }

    /* renamed from: -$$Nest$mupdateNavBarIcon, reason: not valid java name */
    public static void m409$$Nest$mupdateNavBarIcon(
            DualModeChanger dualModeChanger, StateManager.InternalState internalState) {
        dualModeChanger.getClass();
        boolean z = DesktopModeFeature.DEBUG;
        if (z) {
            Log.d("[DMS]DualModeChanger", "updateNavBarIcon(), state=" + internalState);
        }
        if (!dualModeChanger
                .mContext
                .getResources()
                .getBoolean(R.bool.config_sms_decode_gsm_8bit_data)) {
            if (z) {
                Log.i("[DMS]DualModeChanger", "Skip updateNavBarIcon");
                return;
            }
            return;
        }
        boolean z2 = internalState.mTouchpadAvailable;
        UiManager uiManager = dualModeChanger.mUiManager;
        if (z2) {
            uiManager.getClass();
            if (z) {
                Log.d(
                        "[DMS]UiManager",
                        "showNavBarIcon(), type=" + DesktopModeUiConstants.typeToString(400));
            }
            uiManager.removeUnbindServiceRunnable();
            uiManager.mHandler.schedule(new UiManager$$ExternalSyntheticLambda0(uiManager, 9), 400);
            return;
        }
        uiManager.getClass();
        if (z) {
            Log.d(
                    "[DMS]UiManager",
                    "removeNavBarIcon(), type=" + DesktopModeUiConstants.typeToString(400));
        }
        uiManager.removeUnbindServiceRunnable();
        uiManager.mHandler.schedule(new UiManager$$ExternalSyntheticLambda0(uiManager, 10), 400);
    }

    /* renamed from: -$$Nest$mupdateSpenNotification, reason: not valid java name */
    public static void m410$$Nest$mupdateSpenNotification(
            DualModeChanger dualModeChanger, StateManager.InternalState internalState) {
        dualModeChanger.getClass();
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]DualModeChanger", "updateSpenNotification(), state=" + internalState);
        }
        boolean z = internalState.mTouchpadEnabled;
        UiManager uiManager = dualModeChanger.mUiManager;
        if (z && internalState.mSpenEnabled) {
            uiManager.showNotification(203);
        } else {
            uiManager.removeNotification(203);
        }
    }

    /* renamed from: -$$Nest$mupdateTouchpadNotification, reason: not valid java name */
    public static void m411$$Nest$mupdateTouchpadNotification(
            DualModeChanger dualModeChanger, StateManager.InternalState internalState) {
        dualModeChanger.getClass();
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]DualModeChanger", "updateTouchpadNotification(), state=" + internalState);
        }
        boolean canShowTouchpadNotification =
                dualModeChanger.canShowTouchpadNotification(internalState);
        UiManager uiManager = dualModeChanger.mUiManager;
        if (canShowTouchpadNotification) {
            uiManager.showNotification(
                    (!DesktopModeFeature.SUPPORT_SPEN || internalState.mDockState.isDexStation())
                            ? 201
                            : 202);
        } else {
            uiManager.removeNotification(200);
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.desktopmode.DualModeChanger$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.desktopmode.DualModeChanger$1] */
    public DualModeChanger(
            Context context,
            IStateManager iStateManager,
            SemDesktopModeStateNotifier semDesktopModeStateNotifier,
            ServiceThread serviceThread,
            IStatusBarService iStatusBarService,
            UiManager uiManager,
            SettingsHelper settingsHelper,
            MultiResolutionManager multiResolutionManager,
            ActivityTaskManagerService activityTaskManagerService,
            ActivityTaskManagerInternal activityTaskManagerInternal,
            ActivityManagerInternal activityManagerInternal,
            WindowManagerInternal windowManagerInternal,
            MultiWindowManager multiWindowManager,
            CoverStateManager coverStateManager,
            KeyguardManager keyguardManager,
            TouchpadManager touchpadManager,
            PowerManager powerManager,
            PowerManagerInternal powerManagerInternal) {
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
        this.mDualModeEnabled = false;
        this.mLoadingScreenAnimationComplete = false;
        this.mWallpaperShown = false;
        this.mDisplayUnsupportDialogShown = false;
        this.mTouchpadRequestedByNotification = false;
        this.mTopTaskIdValid = false;
        this.mDesktopDisplayId = -1;
        this.mPrevDesktopDisplayId = -1;
        this.mTopTaskId = -1;
        this.mTouchpadSettingChangedListener =
                new SettingsHelper.OnSettingChangedListener(
                        this, 0) { // from class: com.android.server.desktopmode.DualModeChanger.1
                    public final /* synthetic */ int $r8$classId;
                    public final /* synthetic */ DualModeChanger this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super("touchpad_enabled");
                        this.$r8$classId = r2;
                        switch (r2) {
                            case 1:
                                this.this$0 = this;
                                super("stabilizer_mode");
                                break;
                            default:
                                this.this$0 = this;
                                break;
                        }
                    }

                    @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
                    public final void onSettingChanged(String str) {
                        switch (this.$r8$classId) {
                            case 0:
                                DualModeChanger dualModeChanger = this.this$0;
                                if (!dualModeChanger.mTouchpadRequestedByNotification
                                        && Boolean.parseBoolean(str)) {
                                    dualModeChanger.mUiManager.startActivity(
                                            0,
                                            300,
                                            dualModeChanger.mTouchpadManager.mInternalUiCallback);
                                    dualModeChanger.mTouchpadRequestedByNotification = true;
                                    break;
                                }
                                break;
                            default:
                                this.this$0.showStabilizerModeDialogIfNeeded();
                                break;
                        }
                    }
                };
        this.mStabilizerModeChangedListener =
                new SettingsHelper.OnSettingChangedListener(
                        this, 1) { // from class: com.android.server.desktopmode.DualModeChanger.1
                    public final /* synthetic */ int $r8$classId;
                    public final /* synthetic */ DualModeChanger this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super("touchpad_enabled");
                        this.$r8$classId = r2;
                        switch (r2) {
                            case 1:
                                this.this$0 = this;
                                super("stabilizer_mode");
                                break;
                            default:
                                this.this$0 = this;
                                break;
                        }
                    }

                    @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
                    public final void onSettingChanged(String str) {
                        switch (this.$r8$classId) {
                            case 0:
                                DualModeChanger dualModeChanger = this.this$0;
                                if (!dualModeChanger.mTouchpadRequestedByNotification
                                        && Boolean.parseBoolean(str)) {
                                    dualModeChanger.mUiManager.startActivity(
                                            0,
                                            300,
                                            dualModeChanger.mTouchpadManager.mInternalUiCallback);
                                    dualModeChanger.mTouchpadRequestedByNotification = true;
                                    break;
                                }
                                break;
                            default:
                                this.this$0.showStabilizerModeDialogIfNeeded();
                                break;
                        }
                    }
                };
        this.mHandler = new ModeChangeHandler(serviceThread.getLooper());
        this.mStatusBarService = iStatusBarService;
        this.mCoverStateManager = coverStateManager;
        this.mKeyguardManager = keyguardManager;
        this.mTouchpadManager = touchpadManager;
        this.mPowerManager = powerManager;
        this.mPowerManagerInternal = powerManagerInternal;
        this.mSettingsHelper = settingsHelper;
    }

    public final boolean canShowTouchpadNotification(StateManager.InternalState internalState) {
        boolean z = internalState.mIsNavBarGestureEnabled;
        boolean z2 =
                this.mContext.getResources().getBoolean(R.bool.config_sms_decode_gsm_8bit_data);
        boolean z3 = internalState.mTouchpadAvailable;
        boolean z4 = internalState.mTouchpadEnabled;
        StringBuilder m =
                FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                        "canShowTouchpadNotification navBarGestureEnabled=",
                        z,
                        ", hasNavigationBar=",
                        z2,
                        ", isTouchpadAvailable=");
        m.append(z3);
        m.append(", isTouchpadEnabled=");
        m.append(z4);
        Log.d("[DMS]DualModeChanger", m.toString());
        return (z || !z2) && z3 && !z4;
    }

    public final void scheduleStopLoadingScreenIfPossible(boolean z) {
        SemDesktopModeState semDesktopModeState =
                ((StateManager) this.mStateManager).getState().mDesktopModeState;
        if (DesktopModeFeature.DEBUG) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "scheduleStopLoadingScreenIfPossible(), enter=",
                            ", mWallpaperShown=",
                            z);
            m.append(this.mWallpaperShown);
            m.append(", mLoadingScreenAnimationComplete=");
            m.append(this.mLoadingScreenAnimationComplete);
            m.append(", desktopModeState=");
            m.append(semDesktopModeState);
            Log.d("[DMS]DualModeChanger", m.toString());
        }
        if (this.mWallpaperShown && this.mLoadingScreenAnimationComplete) {
            if (semDesktopModeState.compareTo(z ? 4 : 2, 50, 102)) {
                this.mHandler.removeMessages(2);
                this.mHandler.obtainMessage(2, 0, 0, Boolean.valueOf(z)).sendToTarget();
            }
        }
    }

    public final void setDesktopDisplayId(int i) {
        this.mPrevDesktopDisplayId = this.mDesktopDisplayId;
        this.mDesktopDisplayId = i;
        StateManager stateManager = (StateManager) this.mStateManager;
        stateManager.getClass();
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]StateManager", "setDesktopDisplayId(displayId=" + i + ")");
        }
        synchronized (stateManager.mLock) {
            try {
                if (stateManager.mInternalState.mDesktopDisplayId != i) {
                    stateManager.mInternalState.mDesktopDisplayId = i;
                    stateManager.mHandler.post(
                            new StateManager$$ExternalSyntheticLambda2(
                                    stateManager,
                                    stateManager.copyInternalStateLocked(
                                            stateManager.mInternalState),
                                    3));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mDesktopDisplayContext =
                i != -1 ? Utils.getDisplayContext(this.mContext, this.mDesktopDisplayId) : null;
    }

    public final void setDesktopMode(
            StateManager.InternalState internalState,
            boolean z,
            ModeChanger.ModeToModeChangeInfo modeToModeChangeInfo) {
        boolean z2;
        int i;
        int i2;
        boolean z3 = DesktopModeFeature.DEBUG;
        if (z3) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "setDesktopMode(), enter=", ", isModeChangeLocked=", z);
            m.append(internalState.mModeChangeLocked);
            Log.d("[DMS]DualModeChanger", m.toString());
        }
        IStateManager iStateManager = this.mStateManager;
        StateManager stateManager = (StateManager) iStateManager;
        stateManager.getClass();
        if (z3) {
            DesktopModeService$$ExternalSyntheticOutline0.m(
                    "notifyDualSetDesktopMode(enter=", ")", "[DMS]StateManager", z);
        }
        Iterator it = stateManager.mStateListeners.iterator();
        while (it.hasNext()) {
            ((StateManager.StateListener) it.next()).onDualModeSetDesktopMode(z);
        }
        ((StateManager) iStateManager).setModeChangeLocked(true);
        DesktopModeSettings.setSettings(this.mResolver, "display_type", z ? 102 : 0);
        this.mModeToModeChangeInfo = modeToModeChangeInfo;
        int settingsAsUser =
                (internalState.mDockState.mIsAdapter
                                || internalState.isDexOnPcOrWirelessDexConnected())
                        ? DesktopModeSettings.getSettingsAsUser(
                                this.mResolver,
                                "max_visible_freeform_count",
                                5,
                                DesktopModeSettings.sCurrentUserId)
                        : -1;
        this.mMultiWindowManager.setMaxVisibleFreeformCountForDex(settingsAsUser, settingsAsUser);
        if (!this.mTopTaskIdValid) {
            if (modeToModeChangeInfo == null || modeToModeChangeInfo.mModeToMode != 2) {
                this.mTopTaskId =
                        this.mActivityTaskManagerInternal.getForegroundTaskId(
                                z ? 0 : this.mDesktopDisplayId);
            } else {
                this.mTopTaskId = modeToModeChangeInfo.mTopTaskId;
            }
            this.mTopTaskIdValid = true;
        }
        if (modeToModeChangeInfo != null && modeToModeChangeInfo.mModeToMode == 1) {
            modeToModeChangeInfo.mTopTaskId = this.mTopTaskId;
        }
        UiManager uiManager = this.mUiManager;
        uiManager.finishActivity(301);
        setDesktopModeState(z ? 3 : 1, 20);
        IBinder service = ServiceManager.getService("SurfaceFlinger");
        Parcel obtain = Parcel.obtain();
        if (service != null) {
            try {
                try {
                    obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
                    obtain.writeInt(z ? 1 : 0);
                    service.transact(1103, obtain, null, 0);
                } catch (RemoteException e) {
                    Log.e("[DMS]ModeChanger", "Failed to set desktop mode for SF", e);
                }
            } finally {
                obtain.recycle();
            }
        }
        MultiResolutionManager multiResolutionManager = this.mMultiResolutionManager;
        if (z) {
            this.mActivityManagerInternal.cancelKillProcessWhenDexExit();
            ModeChangeReceiver modeChangeReceiver = new ModeChangeReceiver();
            this.mReceiver = modeChangeReceiver;
            IntentFilter m2 =
                    VcnManagementService$$ExternalSyntheticOutline0.m(
                            "android.intent.action.TIME_TICK",
                            "com.samsung.android.intent.action.WALLPAPER_ENGINE_SHOWN",
                            "com.samsung.android.desktopmode.action.SPEN_NOTIFICATION_PRESSED",
                            "com.samsung.android.desktopmode.action.SPEN_NOTIFICATION_CHANGE_MODE_PRESSED",
                            "com.samsung.android.desktopmode.action.TOUCHPAD_NOTIFICATION_PRESSED");
            m2.addAction(
                    "com.samsung.android.desktopmode.action.TOUCHPAD_AVAILABLE_NOTIFICATION_PRESSED");
            m2.addAction("android.intent.action.ACTION_SHUTDOWN");
            this.mContext.registerReceiverAsUser(
                    modeChangeReceiver, UserHandle.ALL, m2, null, this.mHandler, 2);
            if (this.mStateListener == null) {
                this.mStateListener = new ModeChangeStateListener();
                if (DesktopModeFeature.DEBUG) {
                    Log.d("[DMS]DualModeChanger", "mStateListener registered");
                }
                stateManager.registerListener(this.mStateListener);
            } else {
                this.mHandler.removeMessages(3);
            }
            switchKnoxToFolderMode(internalState);
            setDesktopDisplayId(multiResolutionManager.setDualDisplayResolutionDensity(true));
            z2 = false;
        } else {
            CoverStateManager coverStateManager = this.mCoverStateManager;
            CoverState coverState = coverStateManager.mCoverState;
            if (DesktopModeFeature.DEBUG) {
                Log.d(
                        "[DMS]CoverStateManager",
                        "goToSleepIfFlipTypeCoverClosed(), coverState=" + coverState);
            }
            if (coverState.attached
                    && CoverStateManager.isFlipTypeCover(coverState)
                    && (i = coverState.type) != 1
                    && i != 3
                    && i != 6
                    && i != 8
                    && i != 15
                    && !coverState.switchState) {
                coverStateManager.mPowerManager.goToSleep(SystemClock.uptimeMillis());
            } else if (this.mPowerManagerInternal.isInternalDisplayOff()) {
                this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 114, "android.server:DeX");
            }
            if (this.mTopTaskIdValid
                    && (modeToModeChangeInfo == null || modeToModeChangeInfo.mModeToMode == 2)) {
                z2 = false;
                bringTaskToForeground(this.mTopTaskId, 0, 1);
                this.mTopTaskIdValid = false;
            } else {
                z2 = false;
            }
            multiResolutionManager.setDualDisplayResolutionDensity(z2);
            setDesktopDisplayId(-1);
        }
        this.mLoadingScreenAnimationComplete = z2;
        this.mWallpaperShown = z2;
        boolean z4 = DesktopModeFeature.DEBUG;
        if (z4) {
            Log.d("[DMS]DualModeChanger", "scheduleStartLoadingScreen(), enter=" + z);
        }
        this.mHandler.removeMessages(2);
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(2, -1, 0, Boolean.valueOf(z)), 10000L);
        if (z4) {
            Log.i("[DMS]DualModeChanger", "Starting loading screen... enter=" + z);
        }
        AnonymousClass1 anonymousClass1 = this.mStabilizerModeChangedListener;
        AnonymousClass1 anonymousClass12 = this.mTouchpadSettingChangedListener;
        if (z) {
            if (internalState.isDexOnPcConnected()) {
                i2 = 1;
                this.mLoadingScreenAnimationComplete = true;
            } else {
                i2 = 1;
                ModeChanger.ModeToModeChangeInfo modeToModeChangeInfo2 = this.mModeToModeChangeInfo;
                uiManager.showOverlay(
                        103,
                        (modeToModeChangeInfo2 == null
                                        || !modeToModeChangeInfo2.mShowModeChangeScreen)
                                ? 113
                                : 117,
                        new AnonymousClass3(this, 0));
            }
            this.mSettingsHelper.registerListener(anonymousClass12);
            this.mSettingsHelper.registerListener(anonymousClass1);
        } else {
            i2 = 1;
            this.mSettingsHelper.unregisterListener(anonymousClass12);
            this.mSettingsHelper.unregisterListener(anonymousClass1);
            this.mTouchpadRequestedByNotification = false;
        }
        stateManager.getClass();
        if (z4) {
            DesktopModeService$$ExternalSyntheticOutline0.m(
                    "notifyDualStartLoadingScreen(enter=", ")", "[DMS]StateManager", z);
        }
        Iterator it2 = stateManager.mStateListeners.iterator();
        while (it2.hasNext()) {
            ((StateManager.StateListener) it2.next()).onDualModeStartLoadingScreen(z);
        }
        boolean z5 = DesktopModeFeature.DEBUG;
        if (z5) {
            Log.d("[DMS]DualModeChanger", "setDesktopModeInternal(), enter=" + z);
        }
        this.mUiManager.dismissDialog(0, new int[] {0}[0]);
        setDesktopModeState(z ? 3 : i2, 30);
        if (z5) {
            DesktopModeService$$ExternalSyntheticOutline0.m(
                    "notifyDualModeSetDesktopModeInternal(enter=", ")", "[DMS]StateManager", z);
        }
        Iterator it3 = stateManager.mStateListeners.iterator();
        while (it3.hasNext()) {
            ((StateManager.StateListener) it3.next()).onDualModeSetDesktopModeInternal(z);
        }
        backupLockTaskModeEnabledAndSecured(stateManager.getState());
        if (z) {
            try {
                this.mActivityTaskManager.stopSystemLockTaskMode();
            } catch (RemoteException e2) {
                e2.rethrowFromSystemServer();
            }
        }
    }

    @Override // com.android.server.desktopmode.ModeChanger
    public final void setDesktopModeState(int i, int i2) {
        SemDesktopModeState semDesktopModeState =
                new SemDesktopModeState(i, i2, (i == 2 && i2 == 0) ? 0 : 102);
        Log.d("[DMS]DualModeChanger", "setDesktopModeState(), newState=" + semDesktopModeState);
        StateManager stateManager = (StateManager) this.mStateManager;
        if (stateManager.getState().mDesktopModeState.equals(semDesktopModeState)) {
            return;
        }
        stateManager.setDesktopModeState(semDesktopModeState);
        this.mSemDesktopModeStateNotifier.notifyDesktopModeStateChanged(semDesktopModeState);
    }

    public final void showStabilizerModeDialogIfNeeded() {
        boolean settingsAsUser =
                DesktopModeSettings.getSettingsAsUser(
                        this.mResolver,
                        "stabilizer_mode_consent",
                        false,
                        DesktopModeSettings.sCurrentUserId);
        if (!DesktopModeSettings.getSettingsAsUser(
                        this.mResolver,
                        "stabilizer_mode",
                        false,
                        DesktopModeSettings.sCurrentUserId)
                || settingsAsUser) {
            return;
        }
        AnonymousClass3 anonymousClass3 = new AnonymousClass3(this, 1);
        IStateManager iStateManager = this.mStateManager;
        if (((StateManager) iStateManager).getState().mDesktopDisplayId != 0) {
            this.mUiManager.showDialog(
                    ((StateManager) iStateManager).getState().mDesktopDisplayId,
                    10,
                    anonymousClass3);
        }
    }

    public final void startHome(StateManager.InternalState internalState, int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.d(
                    "[DMS]DualModeChanger",
                    ActivityManagerService$$ExternalSyntheticOutline0.m(
                            internalState.mCurrentUserId,
                            i,
                            ", displayId=",
                            ")",
                            new StringBuilder("startHome(userId=")));
        }
        Intent intent =
                new Intent("android.intent.action.MAIN")
                        .addCategory("android.intent.category.HOME")
                        .setFlags(270598144)
                        .putExtra("DesktopModeService", true)
                        .setPackage("com.sec.android.app.desktoplauncher");
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchDisplayId(i);
        this.mContext.startActivityAsUser(
                intent, makeBasic.toBundle(), UserHandle.of(internalState.mCurrentUserId));
    }
}
