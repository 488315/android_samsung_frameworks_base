package com.android.server.desktopmode;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.IUiModeManager;
import android.app.StatusBarManager;
import android.app.role.RoleManager;
import android.content.BroadcastReceiver;
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
import android.p005os.IInstalld;
import android.provider.Settings;
import android.telecom.DefaultDialerManager;
import android.telecom.TelecomManager;
import android.util.IndentingPrintWriter;
import com.android.server.ServiceThread;
import com.android.server.UiModeManagerInternal;
import com.android.server.desktopmode.ModeChanger;
import com.android.server.desktopmode.StandaloneModeChanger;
import com.android.server.desktopmode.StateManager;
import com.android.server.desktopmode.UiManager;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.os.SemDvfsManager;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class StandaloneModeChanger extends ModeChanger {
    public static final String TAG = "[DMS]" + StandaloneModeChanger.class.getSimpleName();
    public int mCurrentUiMode;
    public SemDvfsManager mDvfsManager;
    public HardwareManager mHwManager;
    public ModeChanger.ModeToModeChangeInfo mModeToModeChangeInfo;
    public ModeChangeReceiver mReceiver;
    public boolean mStandaloneModeEnabled;
    public final StateManager.StateListener mStateListener;
    public TelecomManager mTelecomManager;
    public int mTopTaskId;
    public IUiModeManager mUiModeManager;
    public UiModeManagerInternal mUiModeManagerInternal;
    public boolean mWallpaperShown;

    /* renamed from: com.android.server.desktopmode.StandaloneModeChanger$1 */
    public class C11421 extends StateManager.StateListener {
        public static /* synthetic */ void lambda$onUserChanged$0(Boolean bool) {
        }

        public C11421() {
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onUserChanged(State state) {
            StandaloneModeChanger.this.storeDefaultHomePackageName(false, new Consumer() { // from class: com.android.server.desktopmode.StandaloneModeChanger$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    StandaloneModeChanger.C11421.lambda$onUserChanged$0((Boolean) obj);
                }
            });
        }
    }

    public StandaloneModeChanger(Context context, IStateManager iStateManager, SemDesktopModeStateNotifier semDesktopModeStateNotifier, ServiceThread serviceThread, UiManager uiManager, SettingsHelper settingsHelper, MultiResolutionManager multiResolutionManager, ActivityTaskManagerService activityTaskManagerService, ActivityTaskManagerInternal activityTaskManagerInternal, ActivityManagerInternal activityManagerInternal, WindowManagerInternal windowManagerInternal, StatusBarManager statusBarManager, MultiWindowManager multiWindowManager, SemDvfsManager semDvfsManager, SemDesktopModeManager semDesktopModeManager, TelecomManager telecomManager, IUiModeManager iUiModeManager, HardwareManager hardwareManager, UiModeManagerInternal uiModeManagerInternal) {
        super(context, iStateManager, semDesktopModeStateNotifier, uiManager, settingsHelper, multiResolutionManager, activityTaskManagerService, activityTaskManagerInternal, activityManagerInternal, windowManagerInternal, statusBarManager, multiWindowManager, semDesktopModeManager);
        this.mStandaloneModeEnabled = false;
        this.mWallpaperShown = false;
        this.mTopTaskId = -1;
        this.mCurrentUiMode = -1;
        C11421 c11421 = new C11421();
        this.mStateListener = c11421;
        this.mHandler = new ModeChangeHandler(serviceThread.getLooper());
        this.mTelecomManager = telecomManager;
        this.mUiModeManager = iUiModeManager;
        this.mUiModeManagerInternal = uiModeManagerInternal;
        this.mHwManager = hardwareManager;
        this.mDvfsManager = semDvfsManager;
        if (semDvfsManager != null) {
            semDvfsManager.setHint(3000);
        }
        this.mStateManager.registerListener(c11421);
    }

    @Override // com.android.server.desktopmode.ModeChanger
    public void setDesktopModeState(int i, int i2) {
        SemDesktopModeState semDesktopModeState = new SemDesktopModeState(i, i2, (i == 2 && i2 == 0) ? 0 : 101);
        Log.m7d(TAG, "setDesktopModeState(), newState=" + semDesktopModeState);
        if (this.mStateManager.getState().getDesktopModeState().equals(semDesktopModeState)) {
            return;
        }
        this.mStateManager.setDesktopModeState(semDesktopModeState);
        this.mSemDesktopModeStateNotifier.notifyDesktopModeStateChanged(semDesktopModeState);
    }

    public final boolean usingNativeInCallUi() {
        int currentUserId = this.mStateManager.getState().getCurrentUserId();
        if (Settings.System.getIntForUser(this.mResolver, "skt_phone20_settings", -1, currentUserId) == 1) {
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(TAG, "usingNativeInCallUi()=false, using T Phone");
            }
            return false;
        }
        String defaultDialerApplication = DefaultDialerManager.getDefaultDialerApplication(this.mContext, currentUserId);
        if (defaultDialerApplication != null && !"com.samsung.android.contacts".equals(defaultDialerApplication)) {
            Iterator<ResolveInfo> it = this.mContext.getPackageManager().queryIntentServices(new Intent("android.telecom.InCallService"), IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES).iterator();
            while (it.hasNext()) {
                ServiceInfo serviceInfo = it.next().serviceInfo;
                if (serviceInfo != null && defaultDialerApplication.equals(serviceInfo.packageName)) {
                    if (DesktopModeFeature.DEBUG) {
                        Log.m7d(TAG, "usingNativeInCallUi()=false");
                    }
                    return false;
                }
            }
        }
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "usingNativeInCallUi()=true");
        }
        return true;
    }

    public void onConfigurationChanged(final Configuration configuration) {
        if (DesktopModeFeature.DEBUG) {
            Log.m11v(TAG, "onConfigurationChanged(), config=" + configuration);
        }
        SemDesktopModeState desktopModeState = this.mStateManager.getState().getDesktopModeState();
        if (desktopModeState.state == 30 && desktopModeState.getDisplayType() == 101) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StandaloneModeChanger$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    StandaloneModeChanger.this.lambda$onConfigurationChanged$0(configuration);
                }
            });
        }
    }

    /* renamed from: handleOnConfigurationChanged, reason: merged with bridge method [inline-methods] */
    public final void lambda$onConfigurationChanged$0(Configuration configuration) {
        State state = this.mStateManager.getState();
        SemDesktopModeState desktopModeState = state.getDesktopModeState();
        if (desktopModeState.state == 30 && desktopModeState.getDisplayType() == 101) {
            if (DesktopModeFeature.DEBUG) {
                Log.m11v(TAG, "handleOnConfigurationChanged(), newConfig=" + configuration + ", desktopModeDensity=" + this.mMultiResolutionManager.getStandaloneModeDensity() + ", desktopModeState=" + desktopModeState);
            }
            int configurationState = getConfigurationState(configuration);
            if ((configurationState == 1 && desktopModeState.enabled == 3) || (configurationState == 0 && desktopModeState.enabled == 1)) {
                boolean z = configurationState == 1;
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(TAG, "handleOnConfigurationChanged(), enabled=" + z);
                }
                setDesktopModeState(z ? 4 : 2, 40);
                this.mStandaloneModeEnabled = z;
                if (configurationState == 1 && desktopModeState.enabled == 3) {
                    this.mActivityTaskManagerInternal.clearHomeStack(2);
                }
                startHome(state);
                this.mActivityTaskManagerInternal.clearHomeStack(0);
                this.mSettingsHelper.backupOrRestoreSettings(z, state);
                if (z) {
                    if (this.mTelecomManager.isInCall() && !usingNativeInCallUi()) {
                        if (DesktopModeFeature.DEBUG) {
                            Log.m7d(TAG, "Ongoing phone call!");
                        }
                        this.mTelecomManager.showInCallScreen(false);
                    }
                } else {
                    if (this.mTelecomManager.isInCall()) {
                        if (DesktopModeFeature.DEBUG) {
                            Log.m7d(TAG, "Ongoing phone call!");
                        }
                        this.mWallpaperShown = true;
                        this.mTelecomManager.showInCallScreen(false);
                    }
                    clearSettingsBadgeCount();
                }
                this.mStateManager.notifyOnConfigurationChanged(z);
                setDesktopModeState(z ? 4 : 2, 50);
                scheduleStopLoadingScreenIfPossible();
            }
        }
    }

    public int getConfigurationState(Configuration configuration) {
        boolean z = (configuration.uiMode & 15) == 2;
        boolean z2 = configuration.semDesktopModeEnabled == 1;
        MultiResolutionManager multiResolutionManager = this.mMultiResolutionManager;
        boolean z3 = multiResolutionManager != null && configuration.densityDpi == multiResolutionManager.getStandaloneModeDensity();
        MultiResolutionManager multiResolutionManager2 = this.mMultiResolutionManager;
        boolean z4 = multiResolutionManager2 != null && configuration.densityDpi == multiResolutionManager2.getStandaloneModeOriginalDensity(0);
        if (z && z2 && z3) {
            return 1;
        }
        return (z2 || !z4) ? -1 : 0;
    }

    public void startHome(State state) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "startHome()");
        }
        this.mContext.startActivityAsUser(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME").setFlags(270532608).putExtra(DesktopModeService.class.getSimpleName(), true), UserHandle.of(state.getCurrentUserId()));
    }

    public final boolean verifyCurrentState() {
        int i;
        Configuration configuration = this.mContext.getResources().getConfiguration();
        SemDesktopModeState desktopModeState = this.mStateManager.getState().getDesktopModeState();
        int configurationState = getConfigurationState(configuration);
        if ((configurationState == 1 && !this.mStandaloneModeEnabled) || ((configurationState == 0 && this.mStandaloneModeEnabled) || configurationState == -1 || (((i = desktopModeState.enabled) == 3 && configurationState == 0) || (i == 1 && configurationState == 1)))) {
            String str = "verifyCurrentState(), Something is wrong! config=" + configuration + ", mStandaloneModeEnabled=" + this.mStandaloneModeEnabled + ", desktopModeDensity=" + this.mMultiResolutionManager.getStandaloneModeDensity() + ", desktopModeState=" + desktopModeState;
            if (DesktopModeFeature.DEBUG) {
                ToastManager.showToast(this.mContext, str, 1000);
            }
            String str2 = TAG;
            Log.m8e(str2, str);
            int i2 = desktopModeState.enabled;
            if ((i2 == 3 && configurationState == 1) || (i2 == 1 && configurationState == 0)) {
                Log.m8e(str2, "Configuration is changed correctly, but was too late. Calling onConfigurationChanged() directly...");
                lambda$onConfigurationChanged$0(configuration);
                this.mHandler.removeMessages(3);
                this.mHandler.sendEmptyMessageDelayed(3, 0L);
                return false;
            }
            if (DesktopModeFeature.DEBUG) {
                throw new IllegalStateException(str);
            }
            this.mStateManager.notifyScheduleUpdateDesktopMode(false);
        }
        return true;
    }

    public ModeChanger.ModeToModeChangeInfo setDesktopMode(State state, final boolean z, ModeChanger.ModeToModeChangeInfo modeToModeChangeInfo) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setDesktopMode(), desktopMode=" + z);
        }
        if (z) {
            cancelKillProcessWhenExit();
        }
        setModeChangeLock(true);
        saveDisplayType(z ? 101 : 0);
        this.mModeToModeChangeInfo = modeToModeChangeInfo;
        if (modeToModeChangeInfo != null && modeToModeChangeInfo.getModeToMode() == 1) {
            this.mTopTaskId = modeToModeChangeInfo.getTopTaskId();
        } else {
            int foregroundTaskId = this.mActivityTaskManagerInternal.getForegroundTaskId(0);
            this.mTopTaskId = foregroundTaskId;
            if (modeToModeChangeInfo != null) {
                modeToModeChangeInfo.setTopTaskId(foregroundTaskId);
            }
        }
        storeDefaultHomePackageName(z, new Consumer() { // from class: com.android.server.desktopmode.StandaloneModeChanger$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                StandaloneModeChanger.this.lambda$setDesktopMode$1(z, (Boolean) obj);
            }
        });
        SemDvfsManager semDvfsManager = this.mDvfsManager;
        if (semDvfsManager != null) {
            semDvfsManager.acquire();
        }
        this.mStateManager.notifySetDesktopMode(state, z);
        return modeToModeChangeInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDesktopMode$1(boolean z, Boolean bool) {
        if (!bool.booleanValue()) {
            Log.m8e(TAG, "Failed to add home role holder. desktopMode=" + z);
        }
        lambda$handleStartLoadingScreen$2(z);
    }

    public final void storeDefaultHomePackageName(boolean z, Consumer consumer) {
        RoleManager roleManager = (RoleManager) this.mContext.getSystemService("role");
        if (z) {
            roleManager.addRoleHolderAsUser("android.app.role.HOME", "com.sec.android.app.desktoplauncher", 0, Process.myUserHandle(), this.mContext.getMainExecutor(), consumer);
            DesktopModeSettings.setSettings(this.mResolver, "default_home_package", getDefaultHomePackageName(roleManager));
            return;
        }
        String settings = DesktopModeSettings.getSettings(this.mResolver, "default_home_package", (String) null);
        if (settings != null) {
            roleManager.addRoleHolderAsUser("android.app.role.HOME", settings, 0, Process.myUserHandle(), this.mContext.getMainExecutor(), consumer);
            DesktopModeSettings.deleteSettings(this.mResolver, "default_home_package");
        } else {
            consumer.accept(Boolean.FALSE);
            Log.m12w(TAG, "storeDefaultHomePackageName(), packageName is null");
        }
    }

    public String getDefaultHomePackageName(RoleManager roleManager) {
        if (roleManager == null) {
            return null;
        }
        List roleHoldersAsUser = roleManager.getRoleHoldersAsUser("android.app.role.HOME", Process.myUserHandle());
        if (roleHoldersAsUser.isEmpty()) {
            return null;
        }
        return (String) roleHoldersAsUser.get(0);
    }

    /* renamed from: scheduleStartLoadingScreen, reason: merged with bridge method [inline-methods] */
    public final void lambda$handleStartLoadingScreen$2(boolean z) {
        scheduleStartLoadingScreen(z, 0);
    }

    public final void scheduleStartLoadingScreen(boolean z, int i) {
        scheduleStartLoadingScreen(z, i, 0);
    }

    public final void scheduleStartLoadingScreen(boolean z, int i, int i2) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "scheduleStartLoadingScreen(), enter=" + z + ", reason=" + i);
        }
        this.mHandler.removeMessages(2);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(2, i, 0, Boolean.valueOf(z)), i2);
        this.mHandler.removeMessages(3);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(3, -1, 0, Boolean.valueOf(z)), 15000L);
    }

    public final void handleStartLoadingScreen(final boolean z, int i) {
        Trace.asyncTraceBegin(524288L, "loading screen", 0);
        if (i == -1) {
            Log.m12w(TAG, "Loading screen shown by in-call UI timeout enter=" + z);
        } else if (DesktopModeFeature.DEBUG) {
            Log.m10i(TAG, "Starting loading screen... enter=" + z + ", reason=" + i);
        }
        if (this.mUiManager.bindUiServiceWithPendingCommand(new Runnable() { // from class: com.android.server.desktopmode.StandaloneModeChanger$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                StandaloneModeChanger.this.lambda$handleStartLoadingScreen$2(z);
            }
        })) {
            if (i == 0) {
                setDesktopModeState(z ? 3 : 1, 20);
                this.mStateManager.notifyStartLoadingScreen(z);
            }
            ModeChanger.ModeToModeChangeInfo modeToModeChangeInfo = this.mModeToModeChangeInfo;
            this.mUiManager.showOverlay(100, (modeToModeChangeInfo == null || !modeToModeChangeInfo.showModeChangeScreen()) ? z ? 113 : 114 : z ? 116 : 117, new UiManager.InternalUiCallback() { // from class: com.android.server.desktopmode.StandaloneModeChanger.2
                @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
                public void onAnimationComplete() {
                    StandaloneModeChanger.this.mHandler.removeMessages(1);
                    StandaloneModeChanger.this.mHandler.obtainMessage(1, 0, 0, Boolean.valueOf(z)).sendToTarget();
                }
            });
            this.mWindowManagerInternal.launchHomeForDesktopMode(0);
            if (z) {
                ModeChangeReceiver modeChangeReceiver = new ModeChangeReceiver();
                this.mReceiver = modeChangeReceiver;
                modeChangeReceiver.register();
                return;
            }
            return;
        }
        Log.m12w(TAG, "handleStartLoadingScreen(), Binding DesktopUiService... enter=" + z);
    }

    public final void setModeChangeLock(boolean z) {
        this.mStateManager.setModeChangeLocked(z);
        this.mUiManager.setChangingStandaloneMode(z);
    }

    public final void setDesktopModeInternal(boolean z) {
        State state = this.mStateManager.getState();
        if (state.getDesktopModeState().compareTo(z ? 3 : 1, 20, 101)) {
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(TAG, "setDesktopModeInternal(), enabled=" + z);
            }
            this.mHandler.removeMessages(1);
            dismissDialog(0);
            ToastManager.cancelToasts();
            setDesktopModeState(z ? 3 : 1, 30);
            backupLockTaskModeEnabledAndSecured(state);
            if (z) {
                switchKnoxToFolderMode(state);
                try {
                    this.mActivityTaskManager.stopSystemLockTaskMode();
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
            this.mWallpaperShown = false;
            setCustomConfigurations(z);
            this.mStateManager.notifySetDesktopModeInternal(z);
        }
    }

    public final void setCustomConfigurations(boolean z) {
        try {
            this.mUiModeManager.setDesktopMode(z);
            this.mCurrentUiMode = (this.mUiModeManagerInternal.isNightMode() ? 32 : 16) | this.mUiModeManager.getCurrentModeType();
        } catch (RemoteException e) {
            Log.m9e(TAG, "Failed to set custom configurations", e);
        }
    }

    public int getCurrentUiMode() {
        return this.mCurrentUiMode;
    }

    public final void scheduleStopLoadingScreenIfPossible() {
        String str;
        SemDesktopModeState desktopModeState = this.mStateManager.getState().getDesktopModeState();
        if (DesktopModeFeature.DEBUG) {
            String str2 = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("scheduleStopLoadingScreenIfPossible()");
            if (DesktopModeFeature.FEATURE_STANDALONE_MODE_WALLPAPER) {
                str = ", mWallpaperShown=" + this.mWallpaperShown;
            } else {
                str = "";
            }
            sb.append(str);
            sb.append(", desktopModeState=");
            sb.append(desktopModeState);
            sb.append(", mStandaloneModeEnabled=");
            sb.append(this.mStandaloneModeEnabled);
            sb.append(", internal overlay=");
            sb.append(this.mUiManager.getCurrentOverlayType(102));
            sb.append(", external overlay=");
            sb.append(this.mUiManager.getCurrentOverlayType(103));
            Log.m7d(str2, sb.toString());
        }
        if ((!DesktopModeFeature.FEATURE_STANDALONE_MODE_WALLPAPER || this.mWallpaperShown) && desktopModeState.state == 50) {
            if ((this.mUiManager.hasOverlay(101, 113) && this.mStandaloneModeEnabled) || ((this.mUiManager.hasOverlay(101, 114) && !this.mStandaloneModeEnabled) || this.mUiManager.hasOverlay(101, 117) || this.mUiManager.hasOverlay(101, 116))) {
                this.mHandler.removeMessages(3);
                this.mHandler.sendEmptyMessageDelayed(3, 0L);
            }
        }
    }

    public final void handleStopLoadingScreen(boolean z, int i) {
        if (i == -1) {
            if (DesktopModeFeature.DEBUG) {
                Log.m12w(TAG, "Loading screen dismissed by timeout enter=" + z);
            }
            if (!hasPackageTask(0, "com.sec.android.app.desktoplauncher", 2)) {
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(TAG, "DeX Home task does not exist");
                }
                startHome(this.mStateManager.getState());
            }
        } else if (DesktopModeFeature.DEBUG) {
            Log.m10i(TAG, "Stopping loading screen... enter=" + z);
        }
        SemDvfsManager semDvfsManager = this.mDvfsManager;
        if (semDvfsManager != null) {
            semDvfsManager.release();
        }
        if (verifyCurrentState()) {
            setDesktopModeState(z ? 4 : 2, 0);
            ModeChanger.ModeToModeChangeInfo modeToModeChangeInfo = this.mModeToModeChangeInfo;
            if (modeToModeChangeInfo == null || modeToModeChangeInfo.getModeToMode() == 1) {
                bringTaskToForeground(this.mTopTaskId, 0, z ? 5 : 1);
            }
            ModeChanger.ModeToModeChangeInfo modeToModeChangeInfo2 = this.mModeToModeChangeInfo;
            this.mUiManager.dismissOverlay(100, (modeToModeChangeInfo2 == null || !modeToModeChangeInfo2.showModeChangeScreen()) ? z ? 113 : 114 : z ? 116 : 117);
            this.mStateManager.notifyStopLoadingScreen(z);
            setModeChangeLock(false);
            if (!z) {
                ModeChangeReceiver modeChangeReceiver = this.mReceiver;
                if (modeChangeReceiver != null) {
                    modeChangeReceiver.unregister();
                    this.mReceiver = null;
                } else {
                    Log.m8e(TAG, "mReceiver is null!");
                }
                forceKillProcessWhenExit();
            }
            this.mStateManager.notifyScheduleUpdateDesktopMode(!z);
            showDexMirroringTipsNotification(this.mContext.getString(R.string.fp_power_button_bp_title), this.mContext.getString(R.string.fp_power_button_bp_positive_button));
            Trace.asyncTraceEnd(524288L, "loading screen", 0);
        }
    }

    public int getModeToModeChangeType() {
        ModeChanger.ModeToModeChangeInfo modeToModeChangeInfo = this.mModeToModeChangeInfo;
        if (modeToModeChangeInfo != null) {
            return modeToModeChangeInfo.getModeToMode();
        }
        return -1;
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Current " + StandaloneModeChanger.class.getSimpleName() + " state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mCurrentUiMode=" + this.mCurrentUiMode);
        indentingPrintWriter.println("mIsLockTaskModeEnabledAndSecured=" + this.mIsLockTaskModeEnabledAndSecured);
        indentingPrintWriter.println("mStandaloneModeEnabled=" + this.mStandaloneModeEnabled);
        indentingPrintWriter.println("mTopTaskId=" + this.mTopTaskId);
        if (DesktopModeFeature.FEATURE_STANDALONE_MODE_WALLPAPER) {
            indentingPrintWriter.println("mWallpaperShown=" + this.mWallpaperShown);
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final class ModeChangeHandler extends Handler {
        public ModeChangeHandler(Looper looper) {
            super(looper, null, false);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i != 1) {
                if (i == 2) {
                    StandaloneModeChanger.this.handleStartLoadingScreen(((Boolean) message.obj).booleanValue(), message.arg1);
                    return;
                } else {
                    if (i != 3) {
                        return;
                    }
                    StandaloneModeChanger standaloneModeChanger = StandaloneModeChanger.this;
                    standaloneModeChanger.handleStopLoadingScreen(standaloneModeChanger.mStandaloneModeEnabled, message.arg1);
                    return;
                }
            }
            boolean booleanValue = ((Boolean) message.obj).booleanValue();
            int i2 = message.arg1;
            boolean z = i2 < 10;
            if (z) {
                ActivityTaskManagerInternal activityTaskManagerInternal = StandaloneModeChanger.this.mActivityTaskManagerInternal;
                if (!booleanValue ? !activityTaskManagerInternal.goodToChangeMode(0, 1) : !activityTaskManagerInternal.goodToChangeMode(1, 0)) {
                    if (DesktopModeFeature.DEBUG) {
                        Log.m7d(StandaloneModeChanger.TAG, "Not all activities are stopped! retryCount=" + i2 + ", waiting more 500ms...");
                    }
                    Handler handler = StandaloneModeChanger.this.mHandler;
                    handler.sendMessageDelayed(handler.obtainMessage(1, i2 + 1, 0, Boolean.valueOf(booleanValue)), 500L);
                    return;
                }
            }
            if (!z) {
                Log.m8e(StandaloneModeChanger.TAG, "Not all activities are stopped! timeout!! retryCount=" + i2);
            }
            StandaloneModeChanger.this.setDesktopModeInternal(booleanValue);
        }
    }

    public class ModeChangeReceiver extends BroadcastReceiver {
        public ModeChangeReceiver() {
        }

        public final void register() {
            IntentFilter intentFilter = new IntentFilter();
            if (DesktopModeFeature.FEATURE_STANDALONE_MODE_WALLPAPER) {
                intentFilter.addAction("com.samsung.android.intent.action.WALLPAPER_ENGINE_SHOWN");
            }
            intentFilter.addAction("android.intent.action.TIME_TICK");
            StandaloneModeChanger.this.mContext.registerReceiverAsUser(this, UserHandle.ALL, intentFilter, null, null);
        }

        public final void unregister() {
            StandaloneModeChanger.this.mContext.unregisterReceiver(this);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(StandaloneModeChanger.TAG, "onReceive(), action=" + action);
            }
            if ("com.samsung.android.intent.action.WALLPAPER_ENGINE_SHOWN".equals(action)) {
                StandaloneModeChanger.this.mWallpaperShown = true;
                StandaloneModeChanger.this.scheduleStopLoadingScreenIfPossible();
                return;
            }
            if ("android.intent.action.TIME_TICK".equals(action)) {
                long freeMemory = Process.getFreeMemory();
                if (freeMemory < 419430400) {
                    if (DesktopModeFeature.DEBUG) {
                        Log.m12w(StandaloneModeChanger.TAG, "Low memory warning: " + (freeMemory / 1048576) + "MB");
                    }
                    ToastManager.showToast(StandaloneModeChanger.this.mContext, R.string.global_action_restart);
                }
            }
        }
    }
}
