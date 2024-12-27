package com.android.systemui.subscreen;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.devicestate.DeviceStateRequest;
import android.hardware.display.DisplayManager;
import android.hardware.display.IDisplayManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ServiceManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.SecurityUtils;
import com.android.settingslib.satellite.SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.LsRune;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.phone.PredictiveBackSysUiFlag;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.hardware.display.IRefreshRateToken;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

public final class SubScreenManager implements PluginListener, ScreenLifecycle.Observer, DisplayLifecycle.Observer, Dumpable, WakefulnessLifecycle.Observer {
    public SubHomeActivity mActivity;
    public final ActivityManager mActivityManager;
    public final DelayableExecutor mBackgroundExecutor;
    public final Context mContext;
    public boolean mDeviceInteractive;
    public int mDeviceState;
    public final DeviceStateManager mDeviceStateManager;
    public final DismissCallbackRegistry mDismissCallbackRegistry;
    public final DisplayLifecycle mDisplayLifecycle;
    public final DisplayManager mDisplayManager;
    public final DumpManager mDumpManager;
    public final Lazy mFaceWidgetManagerLazy;
    public SubScreenFallback mFallback;
    public IDisplayManager mIDisplayManager;
    public boolean mIsFolderOpened;
    public boolean mIsPluginConnected;
    public final KeyguardManager mKeyguardManager;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final NotifPipeline mNotifPipeline;
    public final Lazy mPluginAODManagerLazy;
    public Context mPluginContext;
    public final PluginManager mPluginManager;
    public SubScreenPresentation mPresentation;
    public final ScreenLifecycle mScreenLifecycle;
    public final Lazy mSettingsHelperLazy;
    public Display mSubDisplay;
    public PluginSubScreen mSubScreenPlugin;
    public Window mSubScreenWindow;
    public final TaskStackChangeListeners mTaskStackChangeListeners;
    public final WakefulnessLifecycle mWakefulnessLifeCycle;
    public final ConcurrentHashMap mSubRoomMap = new ConcurrentHashMap();
    public final Stack mTaskStack = new Stack();
    public final List mOccludedApps = new ArrayList();
    public IRefreshRateToken mRefreshRateToken = null;
    public final IBinder mToken = new Binder();
    public int mMainDisplayState = 0;
    public boolean mPendingRequestDualState = false;
    public final SubScreenManager$$ExternalSyntheticLambda0 mPluginConnectionRunnable = new SubScreenManager$$ExternalSyntheticLambda0(this, 0);
    public boolean mRequestBouncerForLauncherTask = false;
    public final KeyguardUpdateMonitorCallback mCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.subscreen.SubScreenManager.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
            SubScreenManager subScreenManager = SubScreenManager.this;
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "onBiometricAuthFailed() no plugin");
                return;
            }
            Log.d("SubScreenManager", "onBiometricAuthFailed() " + biometricSourceType);
            subScreenManager.mSubScreenPlugin.onBiometricAuthFailed(SubScreenManager.getBiometricType(biometricSourceType));
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
            SubScreenManager subScreenManager = SubScreenManager.this;
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "onBiometricAuthenticated() no plugin");
                return;
            }
            Log.d("SubScreenManager", "onBiometricAuthenticated() " + z);
            subScreenManager.mSubScreenPlugin.onBiometricAuthenticated(i, SubScreenManager.getBiometricType(biometricSourceType), z);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
            SubScreenManager subScreenManager = SubScreenManager.this;
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "onBiometricError() no plugin");
                return;
            }
            Log.d("SubScreenManager", "onBiometricError() " + str);
            subScreenManager.mSubScreenPlugin.onBiometricError(i, str, SubScreenManager.getBiometricType(biometricSourceType));
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricHelp(int i, String str, BiometricSourceType biometricSourceType) {
            SubScreenManager subScreenManager = SubScreenManager.this;
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "onBiometricHelp() no plugin");
                return;
            }
            Log.d("SubScreenManager", "onBiometricHelp() " + str);
            subScreenManager.mSubScreenPlugin.onBiometricHelp(i, str, SubScreenManager.getBiometricType(biometricSourceType));
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
            SubScreenManager subScreenManager = SubScreenManager.this;
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "onBiometricRunningStateChanged() no plugin");
                return;
            }
            Log.d("SubScreenManager", "onBiometricRunningStateChanged() " + z);
            subScreenManager.mSubScreenPlugin.onBiometricRunningStateChanged(z, SubScreenManager.getBiometricType(biometricSourceType));
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onDualDARInnerLockscreenRequirementChanged(int i) {
            SubScreenManager subScreenManager = SubScreenManager.this;
            boolean isDualDarInnerAuthRequired = subScreenManager.mKeyguardUpdateMonitor.isDualDarInnerAuthRequired(i);
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "onDualDARInnerLockscreenRequirementChanged() no plugin");
            } else {
                SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("onDualDARInnerLockscreenRequirementChanged() ", "SubScreenManager", isDualDarInnerAuthRequired);
                subScreenManager.mSubScreenPlugin.onDualDARInnerLockscreenRequirementChanged(isDualDarInnerAuthRequired);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onLockDisabledChanged(boolean z) {
            SubScreenManager subScreenManager = SubScreenManager.this;
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "onLockDisabledChanged() no plugin");
            } else {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onLockDisabledChanged() ", "SubScreenManager", z);
                subScreenManager.mSubScreenPlugin.onLockDisabledChanged(z);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onPackageAdded(String str) {
            PluginSubScreen pluginSubScreen = SubScreenManager.this.mSubScreenPlugin;
            if (pluginSubScreen == null) {
                Log.w("SubScreenManager", "onPackageAdded() no plugin");
            } else {
                pluginSubScreen.onPackageAdded(str);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onPackageChanged(String str) {
            PluginSubScreen pluginSubScreen = SubScreenManager.this.mSubScreenPlugin;
            if (pluginSubScreen == null) {
                Log.w("SubScreenManager", "onPackageChanged() no plugin");
            } else {
                pluginSubScreen.onPackageChanged(str);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onPackageDataCleared(String str) {
            PluginSubScreen pluginSubScreen = SubScreenManager.this.mSubScreenPlugin;
            if (pluginSubScreen == null) {
                Log.w("SubScreenManager", "onPackageDataCleared() no plugin");
            } else {
                pluginSubScreen.onPackageDataCleared(str);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onPackageRemoved(String str, boolean z) {
            PluginSubScreen pluginSubScreen = SubScreenManager.this.mSubScreenPlugin;
            if (pluginSubScreen == null) {
                Log.w("SubScreenManager", "onPackageRemoved() no plugin");
            } else {
                pluginSubScreen.onPackageRemoved(str, z);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
            int i = AnonymousClass9.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[securityMode.ordinal()];
            boolean z = true;
            if (i != 1 && i != 2) {
                z = false;
            }
            boolean checkFullscreenBouncer = SecurityUtils.checkFullscreenBouncer(securityMode);
            SubScreenManager subScreenManager = SubScreenManager.this;
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "onFullscreenBouncerChanged() no plugin");
            } else {
                EmergencyButtonController$$ExternalSyntheticOutline0.m("onFullscreenBouncerChanged() ", " ", "SubScreenManager", checkFullscreenBouncer, z);
                subScreenManager.mSubScreenPlugin.onFullscreenBouncerChanged(checkFullscreenBouncer, z);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            SubScreenManager.this.updatePluginListener$1();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserUnlocked() {
            SubScreenManager subScreenManager = SubScreenManager.this;
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "onUserUnlocked() no plugin");
            } else {
                Log.d("SubScreenManager", "onUserUnlocked() ");
                subScreenManager.mSubScreenPlugin.onUserUnlocked();
            }
        }
    };
    public final AnonymousClass2 mKeyguardStateCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.subscreen.SubScreenManager.2
        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public final void onKeyguardShowingChanged() {
            SubScreenManager subScreenManager = SubScreenManager.this;
            boolean z = ((KeyguardStateControllerImpl) subScreenManager.mKeyguardStateController).mShowing;
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "onKeyguardShowingChanged() no plugin");
            } else {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onKeyguardShowingChanged() ", "SubScreenManager", z);
                subScreenManager.mSubScreenPlugin.onKeyguardShowingChanged(z);
            }
        }

        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public final void onUnlockedChanged() {
            SubScreenManager subScreenManager = SubScreenManager.this;
            boolean z = ((KeyguardStateControllerImpl) subScreenManager.mKeyguardStateController).mCanDismissLockScreen;
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "onUnlockedChanged() no plugin");
            } else {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onUnlockedChanged() ", "SubScreenManager", z);
                subScreenManager.mSubScreenPlugin.onUnlockedChanged(z);
            }
        }
    };
    public final AnonymousClass3 mDeviceStateRequestCallback = new DeviceStateRequest.Callback(this) { // from class: com.android.systemui.subscreen.SubScreenManager.3
    };
    public final AnonymousClass4 mDeviceStateCallback = new AnonymousClass4();
    public final AnonymousClass7 mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.subscreen.SubScreenManager.7
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            if (i == 0) {
                int state = SubScreenManager.this.mDisplayManager.getDisplay(i).getState();
                SubScreenManager subScreenManager = SubScreenManager.this;
                if (state != subScreenManager.mMainDisplayState) {
                    subScreenManager.mMainDisplayState = state;
                    if (subScreenManager.mPendingRequestDualState && state == 2) {
                        Log.i("SubScreenManager", " request pending Dual state when state on");
                        SubScreenManager.this.requestDualState(true);
                    }
                    SubScreenManager.this.mPendingRequestDualState = false;
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {
        }
    };
    public final AnonymousClass8 mTaskStackChangeListener = new TaskStackChangeListener() { // from class: com.android.systemui.subscreen.SubScreenManager.8
        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public final void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
            ComponentName componentName;
            SubScreenManager subScreenManager = SubScreenManager.this;
            if (subScreenManager.mIsFolderOpened || (componentName = runningTaskInfo.topActivity) == null || subScreenManager.mDeviceInteractive || !SubScreenComponentChecker.isCellBroadCastAlertDialog(componentName)) {
                return;
            }
            subScreenManager.mTaskStack.clear();
            subScreenManager.mTaskStack.push(runningTaskInfo);
            subScreenManager.startSubHomeActivity();
        }
    };
    public final AnonymousClass5 mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.subscreen.SubScreenManager.5
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            SubScreenFallback subScreenFallback;
            TooltipPopup$$ExternalSyntheticOutline0.m(message.what, "SubScreenManager", new StringBuilder("handleMessage : "));
            int i = message.what;
            SubScreenManager subScreenManager = SubScreenManager.this;
            if (i == 1000) {
                if (subScreenManager.mActivity != null) {
                    Log.i("SubScreenManager", "MSG_TURN_OFF_SCREEN_WHEN_SMART_COVER   ");
                    subScreenManager.requestDualState(false);
                    return;
                }
                return;
            }
            if (i == 2000) {
                if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
                    Log.i("SubScreenManager", "MSG_RESET_TOP_TASK_ID remove stack info  ");
                    subScreenManager.mTaskStack.clear();
                    return;
                }
                return;
            }
            if (i == 3000 && LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY && (subScreenFallback = subScreenManager.mFallback) != null) {
                subScreenFallback.finish();
            }
        }
    };

    /* renamed from: com.android.systemui.subscreen.SubScreenManager$4, reason: invalid class name */
    public final class AnonymousClass4 implements DeviceStateManager.DeviceStateCallback {
        public AnonymousClass4() {
        }

        public final void onDeviceStateChanged(DeviceState deviceState) {
            int i = 3;
            Log.i("SubScreenManager", " onDeviceStateChanged " + deviceState);
            PredictiveBackSysUiFlag predictiveBackSysUiFlag = PredictiveBackSysUiFlag.INSTANCE;
            Flags.FEATURE_FLAGS.getClass();
            if (deviceState.getIdentifier() == 3) {
                Log.d("SubScreenManager", "setEnableOnBackInvokedCallback true");
                SubScreenManager.this.mContext.getApplicationInfo().setEnableOnBackInvokedCallback(true);
            } else {
                Log.d("SubScreenManager", "setEnableOnBackInvokedCallback false");
                SubScreenManager.this.mContext.getApplicationInfo().setEnableOnBackInvokedCallback(false);
            }
            if (deviceState.getIdentifier() != 0) {
                int i2 = SubScreenManager.this.mDeviceState;
                if (i2 != 4 && i2 != -1 && deviceState.getIdentifier() == 3) {
                    SubScreenManager subScreenManager = SubScreenManager.this;
                    if (subScreenManager.mSubDisplay != null && subScreenManager.mActivity != null && subScreenManager.isTurnOnWhenUnFolding() && SubScreenManager.this.mKeyguardUpdateMonitor.isUserUnlocked()) {
                        SubScreenManager subScreenManager2 = SubScreenManager.this;
                        if (subScreenManager2.mMainDisplayState != 2) {
                            TooltipPopup$$ExternalSyntheticOutline0.m(SubScreenManager.this.mMainDisplayState, "SubScreenManager", new StringBuilder("main display do not on.So pending request. state : "));
                            SubScreenManager.this.mPendingRequestDualState = true;
                        } else {
                            subScreenManager2.requestDualState(true);
                        }
                        SubScreenManager.this.mBackgroundExecutor.executeDelayed(new SubScreenManager$$ExternalSyntheticLambda0(this, i), 100L);
                    }
                }
            } else if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
                if (hasMessages(1000)) {
                    removeMessages(1000);
                }
                SubScreenManager.this.requestDualState(false);
            }
            SubScreenManager.this.mDeviceState = deviceState.getIdentifier();
            SubScreenManager subScreenManager3 = SubScreenManager.this;
            int identifier = deviceState.getIdentifier();
            if (subScreenManager3.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "onDeviceStateChanged() no plugin");
            } else {
                Log.i("SubScreenManager", "onDeviceStateChanged() ");
                subScreenManager3.mSubScreenPlugin.onDeviceStateChanged(identifier);
            }
        }
    }

    /* renamed from: com.android.systemui.subscreen.SubScreenManager$9, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass9 {
        public static final /* synthetic */ int[] $SwitchMap$android$hardware$biometrics$BiometricSourceType;
        public static final /* synthetic */ int[] $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode;

        static {
            int[] iArr = new int[BiometricSourceType.values().length];
            $SwitchMap$android$hardware$biometrics$BiometricSourceType = iArr;
            try {
                iArr[BiometricSourceType.FACE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$hardware$biometrics$BiometricSourceType[BiometricSourceType.FINGERPRINT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[KeyguardSecurityModel.SecurityMode.values().length];
            $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode = iArr2;
            try {
                iArr2[KeyguardSecurityModel.SecurityMode.SimPin.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SimPuk.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public SubScreenManager(Context context, ScreenLifecycle screenLifecycle, DisplayLifecycle displayLifecycle, DisplayManager displayManager, KeyguardUpdateMonitor keyguardUpdateMonitor, Lazy lazy, Lazy lazy2, PluginManager pluginManager, DelayableExecutor delayableExecutor, DumpManager dumpManager, WakefulnessLifecycle wakefulnessLifecycle, Lazy lazy3, KeyguardStateController keyguardStateController, ActivityManager activityManager, KeyguardManager keyguardManager, DeviceStateManager deviceStateManager, NotifPipeline notifPipeline, DismissCallbackRegistry dismissCallbackRegistry, TaskStackChangeListeners taskStackChangeListeners) {
        this.mContext = context;
        this.mScreenLifecycle = screenLifecycle;
        this.mDisplayLifecycle = displayLifecycle;
        this.mDisplayManager = displayManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mFaceWidgetManagerLazy = lazy;
        this.mPluginAODManagerLazy = lazy2;
        this.mPluginManager = pluginManager;
        this.mBackgroundExecutor = delayableExecutor;
        this.mDumpManager = dumpManager;
        this.mWakefulnessLifeCycle = wakefulnessLifecycle;
        this.mSettingsHelperLazy = lazy3;
        this.mKeyguardStateController = keyguardStateController;
        this.mActivityManager = activityManager;
        this.mKeyguardManager = keyguardManager;
        this.mDeviceStateManager = deviceStateManager;
        this.mNotifPipeline = notifPipeline;
        this.mDismissCallbackRegistry = dismissCallbackRegistry;
        this.mTaskStackChangeListeners = taskStackChangeListeners;
    }

    public static int getBiometricType(BiometricSourceType biometricSourceType) {
        int i = AnonymousClass9.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()];
        if (i != 1) {
            return i != 2 ? -1 : 1002;
        }
        return 1001;
    }

    public static String getRoomName$1(int i) {
        switch (i) {
            case 300:
                return "SUB_ROOM_QUICKPANEL";
            case 301:
                return "SUB_ROOM_NOTIFICATION";
            case 302:
            default:
                return LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "INVALID TYPE [", "]");
            case 303:
                return "SUB_ROOM_NETWORK";
            case 304:
                return "SUB_ROOM_MUSIC_WIDGET";
        }
    }

    public final void addPluginListener$1$1() {
        if (this.mIsPluginConnected) {
            Log.d("SubScreenManager", "addPluginListener() already connected");
            return;
        }
        Log.d("SubScreenManager", "addPluginListener() ");
        this.mPluginManager.addPluginListener(PluginSubScreen.ACTION, this, PluginSubScreen.class, false, true, 1);
        this.mIsPluginConnected = true;
    }

    public final void adjustSubHomeActivityOrder(boolean z) {
        Display display;
        synchronized (this.mTaskStack) {
            try {
                if (!z) {
                    int i = this.mWakefulnessLifeCycle.mLastWakeReason;
                    Log.i("SubScreenManager", "adjustSubHomeActivityOrder lastWakeReason" + i);
                    Iterator it = this.mTaskStack.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            ComponentName componentName = ((ActivityManager.RunningTaskInfo) it.next()).topActivity;
                            if (componentName != null && SubScreenComponentChecker.isCellBroadCastAlertDialog(componentName)) {
                                break;
                            }
                        } else if (i == 2) {
                            this.mTaskStack.clear();
                        }
                    }
                    List<ActivityManager.RunningTaskInfo> runningTasks = this.mActivityManager.getRunningTasks(1);
                    if (runningTasks != null && !runningTasks.isEmpty()) {
                        ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
                        Log.i("SubScreenManager", " adjustSubHomeActivityOrder Current Top Task : " + runningTaskInfo.topActivity);
                        ComponentName componentName2 = runningTaskInfo.topActivity;
                        if (componentName2 != null && moveToFrontCoverLauncherTask(componentName2.getClassName())) {
                            this.mRequestBouncerForLauncherTask = true;
                            requestCoverBouncer();
                        }
                    }
                } else if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
                    this.mBackgroundExecutor.executeDelayed(new SubScreenManager$$ExternalSyntheticLambda0(this, 1), 100L);
                    String str = (this.mDeviceState == 1 && (display = this.mSubDisplay) != null && display.getRotation() == 2) ? SystemUIAnalytics.SID_SUBSCREEN_LARGE_TENT : SystemUIAnalytics.SID_SUBSCREEN_LARGE;
                    int i2 = this.mWakefulnessLifeCycle.mLastSleepReason;
                    String str2 = i2 != 2 ? i2 != 4 ? i2 != 23 ? null : SystemUIAnalytics.DT_COVER_SCREEN_OFF_DOUBLE_TAP : SystemUIAnalytics.DT_COVER_SCREEN_OFF_POWER_KEY : SystemUIAnalytics.DT_COVER_SCREEN_OFF_TIME_OUT;
                    if (str2 != null) {
                        SystemUIAnalytics.sendEventLog(str, SystemUIAnalytics.EID_SCREEN_OFF_COVER_SCREEN, str2);
                    }
                } else {
                    startSubHomeActivity();
                }
            } finally {
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("  mSubDisplay = " + this.mSubDisplay);
        StringBuilder m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("  mIsPluginConnected = "), this.mIsPluginConnected, printWriter, "  mIsFolderOpened = "), this.mIsFolderOpened, printWriter, "  mSubScreenPlugin = ");
        m.append(this.mSubScreenPlugin);
        printWriter.println(m.toString());
        printWriter.println("  getWindow() = " + getWindow$1());
        printWriter.println("  mSubScreenWindow = " + this.mSubScreenWindow);
        printWriter.println("  mSubRoomMap = " + this.mSubRoomMap.toString());
        PluginSubScreen pluginSubScreen = this.mSubScreenPlugin;
        if (pluginSubScreen != null) {
            pluginSubScreen.dump(null, printWriter, strArr);
        }
        printWriter.println(" ----------------------------------------------- ");
    }

    public final Window getWindow$1() {
        SubScreenPresentation subScreenPresentation;
        SubHomeActivity subHomeActivity;
        if (LsRune.SUBSCREEN_WATCHFACE && (subHomeActivity = this.mActivity) != null) {
            return subHomeActivity.getWindow();
        }
        if (LsRune.SUBSCREEN_UI && (subScreenPresentation = this.mPresentation) != null) {
            return subScreenPresentation.getWindow();
        }
        Log.d("SubScreenManager", "getWindow() no window");
        return null;
    }

    public final void initWindow() {
        Display display = this.mSubDisplay;
        if (display == null) {
            Log.w("SubScreenManager", "initWindow() mSubDisplay is not initialized");
            return;
        }
        if (LsRune.SUBSCREEN_WATCHFACE) {
            startSubHomeActivity(display);
            return;
        }
        if (LsRune.SUBSCREEN_UI) {
            SubScreenPresentation subScreenPresentation = new SubScreenPresentation(this.mContext, display);
            try {
                subScreenPresentation.show();
            } catch (WindowManager.InvalidDisplayException e) {
                Log.w("SubScreenManager", "Invalid display: ", e);
                subScreenPresentation = null;
            }
            if (subScreenPresentation != null) {
                this.mPresentation = subScreenPresentation;
                updatePluginListener$1();
            }
        }
    }

    public final boolean isShowWhenCoverLocked(ComponentName componentName) {
        if ("com.android.systemui.subscreen.SubHomeActivity".equals(componentName.getClassName())) {
            Log.d("SubScreenPackageChecker", "SubHome Component");
            return true;
        }
        String packageName = componentName.getPackageName();
        if ("com.samsung.android.spay".equals(packageName)) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Specific package: ", packageName, "SubScreenPackageChecker");
        } else {
            String packageName2 = componentName.getPackageName();
            if ("com.skt.prod.dialer".equals(packageName2)) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Specific package: ", packageName2, "SubScreenPackageChecker");
            } else {
                String packageName3 = componentName.getPackageName();
                if ("com.samsung.android.incallui".equals(packageName3)) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Specific package: ", packageName3, "SubScreenPackageChecker");
                } else {
                    String packageName4 = componentName.getPackageName();
                    if ("com.sec.android.app.camera".equals(packageName4)) {
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Specific package: ", packageName4, "SubScreenPackageChecker");
                    } else {
                        String className = componentName.getClassName();
                        if ("com.android.systemui.qp.flashlight.SubroomFlashLightSettingsActivity".equals(className)) {
                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Specific class: ", className, "SubScreenPackageChecker");
                        } else {
                            String className2 = componentName.getClassName();
                            if ("com.sec.android.app.clockpackage.timer.activity.TimerSubScreenB2AlertActivity".equals(className2)) {
                                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Specific class: ", className2, "SubScreenPackageChecker");
                            } else {
                                String className3 = componentName.getClassName();
                                if ("com.sec.android.app.clockpackage.alarm.activity.AlarmAlertSubScreenB2Activity".equals(className3)) {
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Specific class: ", className3, "SubScreenPackageChecker");
                                } else {
                                    String className4 = componentName.getClassName();
                                    if ("com.samsung.android.dialtacts.common.picker.ContactSelectionActivity".equals(className4)) {
                                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Specific class: ", className4, "SubScreenPackageChecker");
                                    } else {
                                        String className5 = componentName.getClassName();
                                        if ("com.samsung.android.app.calendarnotification.view.SubScreenActivity".equals(className5)) {
                                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Specific class: ", className5, "SubScreenPackageChecker");
                                        } else {
                                            String className6 = componentName.getClassName();
                                            if (!"com.sec.android.app.clockpackage.alarm.activity.AlarmAlertActivity".equals(className6)) {
                                                String className7 = componentName.getClassName();
                                                if (!((ArrayList) this.mOccludedApps).contains(className7)) {
                                                    return false;
                                                }
                                                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Occluded app: ", className7, "SubScreenManager");
                                                return true;
                                            }
                                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Specific class: ", className6, "SubScreenPackageChecker");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public final boolean isTurnOnWhenUnFolding() {
        if (!LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
            return false;
        }
        if (this.mSubScreenPlugin == null) {
            Log.w("SubScreenManager", "isTurnOnWhenUnFolding() no plugin");
            return false;
        }
        Log.i("SubScreenManager", "isTurnOnWhenUnFolding() " + this.mSubScreenPlugin.isTurnOnSmartCase());
        return this.mSubScreenPlugin.isTurnOnSmartCase();
    }

    public final boolean moveToFrontCoverLauncherTask(String str) {
        boolean z = false;
        if ("com.android.systemui.subscreen.SubHomeActivity".equals(str)) {
            while (!this.mTaskStack.isEmpty()) {
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.mTaskStack.pop();
                Log.i("SubScreenManager", " Move to Front task : " + runningTaskInfo.topActivity);
                this.mActivityManager.moveTaskToFront(runningTaskInfo.taskId, 2);
                ComponentName componentName = runningTaskInfo.topActivity;
                z = true;
                if (componentName != null) {
                    z = !SubScreenComponentChecker.isCellBroadCastAlertDialog(componentName);
                }
            }
        }
        return z;
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onFinishedGoingToSleep() {
        if (this.mSubScreenPlugin == null) {
            Log.w("SubScreenManager", "onFinishedGoingToSleep() no plugin");
            return;
        }
        if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY && isTurnOnWhenUnFolding() && this.mIsFolderOpened) {
            TooltipPopup$$ExternalSyntheticOutline0.m(this.mWakefulnessLifeCycle.mLastWakeReason, "SubScreenManager", new StringBuilder("onFinishedGoingToSleep() getLastWakeReason "));
            AnonymousClass5 anonymousClass5 = this.mHandler;
            if (anonymousClass5.hasMessages(1000)) {
                Log.i("SubScreenManager", "onFinishedGoingToSleep() remove MSG_FINISH_SUB_HOME_ACTIVITY ");
                anonymousClass5.removeMessages(1000);
                requestDualState(false);
            }
        }
        this.mRequestBouncerForLauncherTask = false;
        this.mSubScreenPlugin.onFinishedGoingToSleep();
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onFinishedWakingUp() {
        PluginSubScreen pluginSubScreen = this.mSubScreenPlugin;
        if (pluginSubScreen == null) {
            Log.w("SubScreenManager", "onFinishedWakingUp() no plugin");
        } else {
            pluginSubScreen.onFinishedWakingUp();
        }
    }

    @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
    public final void onFolderStateChanged(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onFolderStateChanged() opened = ", "SubScreenManager", z);
        if (this.mIsFolderOpened != z) {
            this.mIsFolderOpened = z;
            if (!z && getWindow$1() == null) {
                Log.d("SubScreenManager", "onFolderStateChanged() no window");
                initWindow();
                return;
            }
            PluginSubScreen pluginSubScreen = this.mSubScreenPlugin;
            if (pluginSubScreen == null) {
                Log.w("SubScreenManager", "onFolderStateChanged() no plugin");
                return;
            }
            pluginSubScreen.onFolderStateChanged(z);
            if (LsRune.SUBSCREEN_WATCHFACE) {
                if (!z) {
                    if (((PluginAODManager) this.mPluginAODManagerLazy.get()).mSysUIConfig.get(2, 0) == 0) {
                        this.mBackgroundExecutor.executeDelayed(new SubScreenManager$$ExternalSyntheticLambda0(this, 2), 100L);
                        return;
                    }
                    return;
                }
                if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
                    this.mTaskStack.clear();
                    return;
                }
                Lazy lazy = this.mSettingsHelperLazy;
                if (((SettingsHelper) lazy.get()).isShowNavigationForSubscreen()) {
                    ((SettingsHelper) lazy.get()).setShowNavigationForSubscreen(false);
                }
            }
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginConnected(Plugin plugin, Context context) {
        PluginSubScreen pluginSubScreen = (PluginSubScreen) plugin;
        if (pluginSubScreen == null || context == null) {
            return;
        }
        Log.d("SubScreenManager", "onPluginConnected() [" + pluginSubScreen + "]");
        PluginSubScreen pluginSubScreen2 = this.mSubScreenPlugin;
        if (pluginSubScreen2 == pluginSubScreen) {
            Log.w("SubScreenManager", "startSubScreen() already started");
            return;
        }
        if (pluginSubScreen2 != null) {
            Log.e("SubScreenManager", "startSubScreen: plugin is changed, stop old plugin");
            stopSubScreen();
        }
        Window window$1 = getWindow$1();
        if (window$1 == null) {
            Log.d("SubScreenManager", "startSubScreen() no window");
            return;
        }
        Log.d("SubScreenManager", "startSubScreen() " + pluginSubScreen);
        this.mSubScreenWindow = window$1;
        this.mSubScreenPlugin = pluginSubScreen;
        this.mPluginContext = context;
        DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
        this.mIsFolderOpened = displayLifecycle.mIsFolderOpened;
        displayLifecycle.addObserver(this);
        this.mScreenLifecycle.addObserver(this);
        this.mWakefulnessLifeCycle.addObserver(this);
        this.mPluginContext.getResources().getConfiguration().updateFrom(this.mSubScreenWindow.getDecorView().getResources().getConfiguration());
        this.mSubScreenPlugin.onSubUIStarted(this.mSubScreenWindow, new Bundle());
        ((PluginAODManager) this.mPluginAODManagerLazy.get()).setSubScreenPlugin(this.mSubScreenPlugin);
        if (LsRune.SUBSCREEN_WATCHFACE) {
            this.mSubScreenPlugin.setSubHomeActivityResumed(this.mActivity.semIsResumed());
        }
        this.mKeyguardUpdateMonitor.registerCallback(this.mCallback);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this.mKeyguardStateCallback);
        if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
            this.mDeviceStateManager.registerCallback(this.mContext.getMainExecutor(), this.mDeviceStateCallback);
            this.mTaskStackChangeListeners.registerTaskStackListener(this.mTaskStackChangeListener);
            this.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mHandler);
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginDisconnected(Plugin plugin) {
        Log.d("SubScreenManager", "onPluginDisconnected() [" + ((PluginSubScreen) plugin) + "]");
        stopSubScreen();
    }

    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
    public final void onScreenTurnedOff() {
        PluginSubScreen pluginSubScreen = this.mSubScreenPlugin;
        if (pluginSubScreen == null) {
            Log.w("SubScreenManager", "onScreenTurnedOff() no plugin");
        } else {
            pluginSubScreen.onScreenTurnedOff();
        }
    }

    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
    public final void onScreenTurnedOn() {
        PluginSubScreen pluginSubScreen = this.mSubScreenPlugin;
        if (pluginSubScreen == null) {
            Log.w("SubScreenManager", "onScreenTurnedOn() no plugin");
        } else {
            pluginSubScreen.onScreenTurnedOn();
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onStartedGoingToSleep() {
        this.mDeviceInteractive = false;
        PluginSubScreen pluginSubScreen = this.mSubScreenPlugin;
        if (pluginSubScreen == null) {
            Log.w("SubScreenManager", "onStartedGoingToSleep() no plugin");
        } else {
            pluginSubScreen.onStartedGoingToSleep();
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onStartedWakingUp() {
        ComponentName componentName;
        this.mDeviceInteractive = true;
        if (this.mSubScreenPlugin == null) {
            Log.w("SubScreenManager", "onStartedWakingUp() no plugin");
            return;
        }
        if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY && !this.mIsFolderOpened) {
            int i = this.mWakefulnessLifeCycle.mLastWakeReason;
            if (i == 2) {
                KeyguardSecPatternView$$ExternalSyntheticOutline0.m(i, " onStartedWakingUp wake up reason  ", "SubScreenManager");
            } else {
                List<ActivityManager.RunningTaskInfo> runningTasks = this.mActivityManager.getRunningTasks(1);
                if (runningTasks != null && runningTasks.size() > 0) {
                    ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
                    StringBuilder sb = new StringBuilder(" onStartedWakingUp Current Top Task : ");
                    sb.append(runningTaskInfo.topActivity);
                    sb.append(" , ");
                    TooltipPopup$$ExternalSyntheticOutline0.m(runningTaskInfo.displayId, "SubScreenManager", sb);
                    if (runningTaskInfo.displayId == 1 && (componentName = runningTaskInfo.topActivity) != null && !isShowWhenCoverLocked(componentName)) {
                        requestCoverBouncer();
                    }
                }
            }
        }
        this.mSubScreenPlugin.onStartedWakingUp();
    }

    public final void requestCoverBouncer() {
        Log.i("SubScreenManager", "requestCoverBouncer");
        Intent intent = new Intent();
        if (!this.mKeyguardManager.isKeyguardSecure()) {
            this.mKeyguardManager.semDismissKeyguard();
            return;
        }
        intent.putExtra("runOnCover", true);
        intent.putExtra("bouncerTimeout", 10000);
        this.mKeyguardManager.semSetPendingIntentAfterUnlock(null, intent);
    }

    public final void requestDualState(boolean z) {
        SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("requestDualState ", "SubScreenManager", z);
        try {
            if (this.mIDisplayManager == null) {
                this.mIDisplayManager = IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
            }
            if (this.mIDisplayManager != null) {
                if (z) {
                    Log.i("SubScreenManager", " updateRefreshRate token " + this.mRefreshRateToken);
                    if (this.mRefreshRateToken == null) {
                        this.mRefreshRateToken = this.mIDisplayManager.acquireRefreshRateMinLimitToken(this.mToken, 120, "subhome");
                    }
                } else {
                    IRefreshRateToken iRefreshRateToken = this.mRefreshRateToken;
                    if (iRefreshRateToken != null) {
                        iRefreshRateToken.release();
                        this.mRefreshRateToken = null;
                    }
                }
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("updateRefreshRate exception "), "SubScreenManager");
        }
        if (!z) {
            this.mDeviceStateManager.cancelStateRequest();
        } else {
            this.mDeviceStateManager.requestState(DeviceStateRequest.newBuilder(4).setFlags(16).build(), this.mContext.getMainExecutor(), this.mDeviceStateRequestCallback);
        }
    }

    public final void setSubHomeActivityResumed(boolean z) {
        if (this.mSubScreenPlugin == null) {
            Log.w("SubScreenManager", "setSubHomeActivityResumed() no plugin");
            return;
        }
        SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("setSubHomeActivityResumed() ", "SubScreenManager", z);
        this.mSubScreenPlugin.setSubHomeActivityResumed(z);
        if (!z || ((ArrayList) this.mOccludedApps).contains("com.android.systemui.subscreen.SubHomeActivity")) {
            return;
        }
        ((ArrayList) this.mOccludedApps).add("com.android.systemui.subscreen.SubHomeActivity");
    }

    public final void setSubRoom(int i, SubRoom subRoom) {
        Log.d("SubScreenManager", "setSubRoom() " + getRoomName$1(i) + ", " + subRoom);
        this.mSubRoomMap.put(Integer.valueOf(i), subRoom);
    }

    public final void startSubHomeActivity() {
        if (this.mSubDisplay == null || this.mDisplayLifecycle.mIsFolderOpened) {
            return;
        }
        Log.d("SubScreenManager", "startSubHomeActivity() ");
        startSubHomeActivity(this.mSubDisplay);
    }

    public final void startSubScreenFallback(Display display) {
        if (this.mKeyguardUpdateMonitor.isUserUnlocked()) {
            Log.d("SubScreenManager", "startSubScreenFallback. Already unlocked ");
            return;
        }
        Log.d("SubScreenManager", "startSubScreenFallback() " + display.getDisplayId());
        Intent intent = new Intent();
        intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.SECONDARY_HOME");
        intent.setClassName("com.android.systemui", "com.android.systemui.subscreen.SubScreenFallback");
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchDisplayId(display.getDisplayId());
        makeBasic.setForceLaunchWindowingMode(1);
        try {
            this.mContext.startActivity(intent, makeBasic.toBundle());
        } catch (ActivityNotFoundException e) {
            Log.w("SubScreenManager", "startSubScreenFallback() " + e);
        }
    }

    public final void stopSubScreen() {
        if (this.mSubScreenPlugin == null) {
            Log.e("SubScreenManager", "stopSubScreen() no plugin");
            return;
        }
        Log.d("SubScreenManager", "stopSubScreen()");
        this.mDisplayLifecycle.removeObserver(this);
        this.mScreenLifecycle.removeObserver(this);
        this.mWakefulnessLifeCycle.removeObserver(this);
        ((PluginAODManager) this.mPluginAODManagerLazy.get()).setSubScreenPlugin(null);
        this.mSubScreenPlugin.onSubUIStopped();
        this.mKeyguardUpdateMonitor.removeCallback(this.mCallback);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).removeCallback(this.mKeyguardStateCallback);
        if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
            this.mDeviceStateManager.unregisterCallback(this.mDeviceStateCallback);
            this.mTaskStackChangeListeners.unregisterTaskStackListener(this.mTaskStackChangeListener);
            this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
        }
        this.mSubScreenPlugin = null;
    }

    public final void updatePluginListener$1() {
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("updatePluginListener() mIsPluginConnected = "), this.mIsPluginConnected, "SubScreenManager");
        boolean z = this.mIsPluginConnected;
        if (z) {
            if (z) {
                Log.d("SubScreenManager", "removePluginListener() ");
                this.mPluginManager.removePluginListener(this);
                this.mIsPluginConnected = false;
            } else {
                Log.d("SubScreenManager", "removePluginListener() already disconnected");
            }
        }
        if (SafeUIState.isSysUiSafeModeEnabled()) {
            Log.i("SubScreenManager", "Do not plug in connection in safe mode");
            return;
        }
        if (DeviceType.isFactoryBinary()) {
            Log.i("SubScreenManager", "Do not plug in connection in factory mode");
            return;
        }
        if (this.mActivity == null && this.mPresentation == null) {
            Log.d("SubScreenManager", "requestPluginConnection() no activity and no presentation");
        } else if (((PluginFaceWidgetManager) this.mFaceWidgetManagerLazy.get()).mIsConnected) {
            addPluginListener$1$1();
        } else {
            Log.w("SubScreenManager", "requestPluginConnection() PluginFaceWidget is not connected, wait connection");
            ((PluginAODManager) this.mPluginAODManagerLazy.get()).addConnectionRunnable(this.mPluginConnectionRunnable);
        }
    }

    public final void startSubHomeActivity(Display display) {
        Log.d("SubScreenManager", "startSubHomeActivity() " + display.getDisplayId());
        Intent intent = new Intent();
        intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.SECONDARY_HOME");
        intent.setClassName("com.android.systemui", "com.android.systemui.subscreen.SubHomeActivity");
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        if (!LsRune.SUBSCREEN_DEBUG_ACTIVITY_ON_MAIN) {
            makeBasic.setLaunchDisplayId(display.getDisplayId());
        }
        makeBasic.setForceLaunchWindowingMode(1);
        try {
            this.mContext.startActivity(intent, makeBasic.toBundle());
        } catch (ActivityNotFoundException e) {
            Log.w("SubScreenManager", "startSubHomeActivity() " + e);
        }
    }
}
