package com.android.systemui.subscreen;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricSourceType;
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
import android.os.PowerManager;
import android.os.ServiceManager;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0790xf8f53ce8;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.SecurityUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
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
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.hardware.display.IRefreshRateToken;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubScreenManager implements PluginListener, ScreenLifecycle.Observer, DisplayLifecycle.Observer, Dumpable, WakefulnessLifecycle.Observer {
    public SubHomeActivity mActivity;
    public final ActivityManager mActivityManager;
    public final DelayableExecutor mBackgroundExecutor;
    public final Context mContext;
    public boolean mDeviceInteractive;
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
    public boolean mPendingPluginConnect;
    public Runnable mPendingPluginConnectRunnable;
    public final Lazy mPluginAODManagerLazy;
    public Context mPluginContext;
    public final PluginManager mPluginManager;
    public SubScreenPresentation mPresentation;
    public final ScreenLifecycle mScreenLifecycle;
    public final Lazy mSettingsHelperLazy;
    public Display mSubDisplay;
    public PluginSubScreen mSubScreenPlugin;
    public Window mSubScreenWindow;
    public final WakefulnessLifecycle mWakefulnessLifeCycle;
    public final ConcurrentHashMap mSubRoomMap = new ConcurrentHashMap();
    public final Stack mTaskStack = new Stack();
    public final List mOccludedApps = new ArrayList();
    public IRefreshRateToken mRefreshRateToken = null;
    public final IBinder mToken = new Binder();
    public int mDeviceState = -1;
    public int mMainDisplayState = 0;
    public boolean mPendingRequestDualState = false;
    public final SubScreenManager$$ExternalSyntheticLambda1 mPluginConnectionRunnable = new SubScreenManager$$ExternalSyntheticLambda1(this, 0);
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
        public final void onBiometricRunningStateChanged(BiometricSourceType biometricSourceType, boolean z) {
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
                ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("onDualDARInnerLockscreenRequirementChanged() ", isDualDarInnerAuthRequired, "SubScreenManager");
                subScreenManager.mSubScreenPlugin.onDualDARInnerLockscreenRequirementChanged(isDualDarInnerAuthRequired);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onLockDisabledChanged(boolean z) {
            SubScreenManager subScreenManager = SubScreenManager.this;
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "onLockDisabledChanged() no plugin");
            } else {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onLockDisabledChanged() ", z, "SubScreenManager");
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
            int i = AbstractC34668.f360xdc0e830a[securityMode.ordinal()];
            boolean z = true;
            if (i != 1 && i != 2) {
                z = false;
            }
            boolean checkFullscreenBouncer = SecurityUtils.checkFullscreenBouncer(securityMode);
            SubScreenManager subScreenManager = SubScreenManager.this;
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "onFullscreenBouncerChanged() no plugin");
            } else {
                EmergencyButtonController$$ExternalSyntheticOutline0.m59m("onFullscreenBouncerChanged() ", checkFullscreenBouncer, " ", z, "SubScreenManager");
                subScreenManager.mSubScreenPlugin.onFullscreenBouncerChanged(checkFullscreenBouncer, z);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            SubScreenManager.this.updatePluginListener();
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
    public final C34602 mKeyguardStateCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.subscreen.SubScreenManager.2
        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public final void onKeyguardShowingChanged() {
            SubScreenManager subScreenManager = SubScreenManager.this;
            boolean z = ((KeyguardStateControllerImpl) subScreenManager.mKeyguardStateController).mShowing;
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "onKeyguardShowingChanged() no plugin");
            } else {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onKeyguardShowingChanged() ", z, "SubScreenManager");
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
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onUnlockedChanged() ", z, "SubScreenManager");
                subScreenManager.mSubScreenPlugin.onUnlockedChanged(z);
            }
        }
    };
    public final C34613 mDeviceStateRequestCallback = new DeviceStateRequest.Callback(this) { // from class: com.android.systemui.subscreen.SubScreenManager.3
        public final void onRequestActivated(DeviceStateRequest deviceStateRequest) {
            super.onRequestActivated(deviceStateRequest);
        }

        public final void onRequestCanceled(DeviceStateRequest deviceStateRequest) {
            super.onRequestCanceled(deviceStateRequest);
        }

        public final void onRequestSuspended(DeviceStateRequest deviceStateRequest) {
            super.onRequestSuspended(deviceStateRequest);
        }
    };
    public final C34624 mDeviceStateCallback = new C34624();
    public final C34657 mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.subscreen.SubScreenManager.7
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
    public final HandlerC34635 mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.subscreen.SubScreenManager.5
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            SubScreenFallback subScreenFallback;
            TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("handleMessage : "), message.what, "SubScreenManager");
            int i = message.what;
            if (i == 1000) {
                if (SubScreenManager.this.mActivity != null) {
                    Log.i("SubScreenManager", "MSG_TURN_OFF_SCREEN_WHEN_SMART_COVER   ");
                    SubScreenManager.this.requestDualState(false);
                    return;
                }
                return;
            }
            if (i == 2000) {
                if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
                    Log.i("SubScreenManager", "MSG_RESET_TOP_TASK_ID remove stack info  ");
                    SubScreenManager.this.mTaskStack.clear();
                    return;
                }
                return;
            }
            if (i == 3000 && LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY && (subScreenFallback = SubScreenManager.this.mFallback) != null) {
                subScreenFallback.finish();
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.subscreen.SubScreenManager$4 */
    public final class C34624 implements DeviceStateManager.DeviceStateCallback {
        public C34624() {
        }

        public final void onStateChanged(int i) {
            StringBuilder m1m = AbstractC0000x2c234b15.m1m(" onStateChanged ", i, " , useUnlocked ");
            m1m.append(SubScreenManager.this.mKeyguardUpdateMonitor.isUserUnlocked());
            Log.i("SubScreenManager", m1m.toString());
            if (i != 0) {
                SubScreenManager subScreenManager = SubScreenManager.this;
                int i2 = subScreenManager.mDeviceState;
                if (i2 != 4 && i2 != -1) {
                    int i3 = 3;
                    if (i == 3) {
                        if (subScreenManager.mSubDisplay != null && subScreenManager.mActivity != null && subScreenManager.isTurnOnWhenUnFolding() && SubScreenManager.this.mKeyguardUpdateMonitor.isUserUnlocked()) {
                            SubScreenManager subScreenManager2 = SubScreenManager.this;
                            if (subScreenManager2.mMainDisplayState != 2) {
                                TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("main display do not on.So pending request. state : "), SubScreenManager.this.mMainDisplayState, "SubScreenManager");
                                SubScreenManager.this.mPendingRequestDualState = true;
                            } else {
                                subScreenManager2.requestDualState(true);
                            }
                            SubScreenManager.this.mBackgroundExecutor.executeDelayed(100L, new SubScreenManager$$ExternalSyntheticLambda1(this, i3));
                        } else if (LsRune.SUBSCREEN_PLUGIN_DISCONNECT_WHEN_UNFOLDING) {
                            SubScreenManager subScreenManager3 = SubScreenManager.this;
                            if (subScreenManager3.mIsPluginConnected) {
                                Log.d("SubScreenManager", "removePluginListener() ");
                                subScreenManager3.mPluginManager.removePluginListener(subScreenManager3);
                                subScreenManager3.mIsPluginConnected = false;
                            } else {
                                Log.d("SubScreenManager", "removePluginListener() already disconnected");
                            }
                            Log.d("SubScreenManager", "onStateChanged mPendingPluginConnect set true");
                            SubScreenManager.this.mPendingPluginConnect = true;
                        }
                    }
                }
            } else if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
                if (hasMessages(1000)) {
                    removeMessages(1000);
                }
                SubScreenManager.this.requestDualState(false);
                if (LsRune.SUBSCREEN_PLUGIN_DISCONNECT_WHEN_UNFOLDING) {
                    SubScreenManager.this.updatePluginListener();
                }
            }
            SubScreenManager subScreenManager4 = SubScreenManager.this;
            subScreenManager4.mDeviceState = i;
            if (subScreenManager4.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "onDeviceStateChanged() no plugin");
            } else {
                Log.i("SubScreenManager", "onDeviceStateChanged() ");
                subScreenManager4.mSubScreenPlugin.onDeviceStateChanged(i);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.subscreen.SubScreenManager$8 */
    public abstract /* synthetic */ class AbstractC34668 {
        public static final /* synthetic */ int[] $SwitchMap$android$hardware$biometrics$BiometricSourceType;

        /* renamed from: $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode */
        public static final /* synthetic */ int[] f360xdc0e830a;

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
            f360xdc0e830a = iArr2;
            try {
                iArr2[KeyguardSecurityModel.SecurityMode.SimPin.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f360xdc0e830a[KeyguardSecurityModel.SecurityMode.SimPuk.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.subscreen.SubScreenManager$7] */
    /* JADX WARN: Type inference failed for: r1v30, types: [com.android.systemui.subscreen.SubScreenManager$5] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.subscreen.SubScreenManager$2] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.subscreen.SubScreenManager$3] */
    public SubScreenManager(Context context, ScreenLifecycle screenLifecycle, DisplayLifecycle displayLifecycle, DisplayManager displayManager, KeyguardUpdateMonitor keyguardUpdateMonitor, Lazy lazy, Lazy lazy2, PluginManager pluginManager, DelayableExecutor delayableExecutor, DumpManager dumpManager, WakefulnessLifecycle wakefulnessLifecycle, Lazy lazy3, KeyguardStateController keyguardStateController, ActivityManager activityManager, KeyguardManager keyguardManager, PowerManager powerManager, DeviceStateManager deviceStateManager, NotifPipeline notifPipeline, DismissCallbackRegistry dismissCallbackRegistry) {
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
    }

    public static int getBiometricType(BiometricSourceType biometricSourceType) {
        int i = AbstractC34668.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()];
        if (i != 1) {
            return i != 2 ? -1 : 1002;
        }
        return 1001;
    }

    public final void addPluginListener() {
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
            int i = 2;
            if (!z) {
                int i2 = this.mWakefulnessLifeCycle.mLastWakeReason;
                Log.i("SubScreenManager", "adjustSubHomeActivityOrder lastWakeReason" + i2);
                if (i2 == 2) {
                    this.mTaskStack.clear();
                } else {
                    List<ActivityManager.RunningTaskInfo> runningTasks = this.mActivityManager.getRunningTasks(1);
                    if (runningTasks != null && runningTasks.size() > 0) {
                        ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
                        Log.i("SubScreenManager", " adjustSubHomeActivityOrder Current Top Task : " + runningTaskInfo.topActivity);
                        if (moveToFrontCoverLauncherTask(runningTaskInfo.topActivity)) {
                            this.mRequestBouncerForLauncherTask = true;
                            requestCoverBouncer();
                        }
                    }
                }
            } else if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
                this.mBackgroundExecutor.executeDelayed(100L, new SubScreenManager$$ExternalSyntheticLambda1(this, i));
                String str = (this.mDeviceState == 1 && (display = this.mSubDisplay) != null && display.getRotation() == 2) ? "101_S_R" : "101_S";
                int i3 = this.mWakefulnessLifeCycle.mLastSleepReason;
                String str2 = i3 != 2 ? i3 != 4 ? i3 != 23 ? null : "double tap" : "side key" : "screen timeout";
                if (str2 != null) {
                    SystemUIAnalytics.sendEventLog(str, "CVSE1004", str2);
                }
            } else {
                startSubHomeActivity();
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("  mSubDisplay = " + this.mSubDisplay);
        StringBuilder m64m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("  mIsPluginConnected = "), this.mIsPluginConnected, printWriter, "  mIsFolderOpened = "), this.mIsFolderOpened, printWriter, "  mSubScreenPlugin = ");
        m64m.append(this.mSubScreenPlugin);
        printWriter.println(m64m.toString());
        printWriter.println("  getWindow() = " + getWindow());
        printWriter.println("  mSubScreenWindow = " + this.mSubScreenWindow);
        printWriter.println("  mSubRoomMap = " + this.mSubRoomMap.toString());
        PluginSubScreen pluginSubScreen = this.mSubScreenPlugin;
        if (pluginSubScreen != null) {
            pluginSubScreen.dump(null, printWriter, strArr);
        }
        printWriter.println(" ----------------------------------------------- ");
    }

    public final String getRoomName(int i) {
        switch (i) {
            case 300:
                return "SUB_ROOM_QUICKPANEL";
            case 301:
                return "SUB_ROOM_NOTIFICATION";
            case 302:
            default:
                return LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("INVALID TYPE [", i, "]");
            case 303:
                return "SUB_ROOM_NETWORK";
            case 304:
                return "SUB_ROOM_MUSIC_WIDGET";
        }
    }

    public final Window getWindow() {
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
                updatePluginListener();
            }
        }
    }

    public final boolean isShowWhenCoverLocked(ComponentName componentName) {
        if ("com.android.systemui.subscreen.SubHomeActivity".equals(componentName.getClassName())) {
            Log.i("SubScreenManager", "ignore isShowWhenCoverLocked cause already display");
            return true;
        }
        if ("com.samsung.android.spay".equals(componentName.getPackageName())) {
            Log.i("SubScreenManager", "ignore isShowWhenCoverLocked by samsung pay");
            return true;
        }
        if ("com.skt.prod.dialer".equals(componentName.getPackageName())) {
            Log.i("SubScreenManager", "ignore isShowWhenCoverLocked by T-CALL");
            return true;
        }
        if ("com.samsung.android.incallui".equals(componentName.getPackageName())) {
            Log.i("SubScreenManager", "ignore isShowWhenCoverLocked by Samsung-CALL");
            return true;
        }
        if ("com.android.systemui.qp.flashlight.SubroomFlashLightSettingsActivity".equals(componentName.getClassName())) {
            Log.i("SubScreenManager", "ignore isShowWhenCoverLocked by Flashlight Activity");
            return true;
        }
        if ("com.sec.android.app.clockpackage.timer.activity.TimerSubScreenB2AlertActivity".equals(componentName.getClassName())) {
            Log.i("SubScreenManager", "ignore isShowWhenCoverLocked by Timer Activity");
            return true;
        }
        if ("com.sec.android.app.clockpackage.alarm.activity.AlarmAlertSubScreenB2Activity".equals(componentName.getClassName())) {
            Log.i("SubScreenManager", "ignore isShowWhenCoverLocked by Calendar Activity");
            return true;
        }
        if ("com.samsung.android.dialtacts.common.picker.ContactSelectionActivity".equals(componentName.getClassName())) {
            Log.i("SubScreenManager", "ignore isShowWhenCoverLocked by Direct Call Activity");
            return true;
        }
        if ("com.samsung.android.app.calendarnotification.view.SubScreenActivity".equals(componentName.getClassName())) {
            Log.i("SubScreenManager", "ignore isShowWhenCoverLocked by Calendar Notification Activity");
            return true;
        }
        if ("com.sec.android.app.camera".equals(componentName.getPackageName())) {
            Log.i("SubScreenManager", "ignore isShowWhenCoverLocked by Camera Activity");
            return true;
        }
        if ("com.sec.android.app.clockpackage.alarm.activity.AlarmAlertActivity".equals(componentName.getClassName())) {
            Log.i("SubScreenManager", "ignore isShowWhenCoverLocked by Calendar Activity MAIN");
            return true;
        }
        String className = componentName.getClassName();
        if (!((ArrayList) this.mOccludedApps).contains(className)) {
            return false;
        }
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m70m("ignore isShowWhenCoverLocked: ", className, "SubScreenManager");
        return true;
    }

    public final boolean isTurnOnWhenUnFolding() {
        if (!LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
            return false;
        }
        if (this.mSubScreenPlugin == null) {
            Log.w("SubScreenManager", "isTurnOnWhenUnFolding() no plugin");
            return false;
        }
        Log.i("SubScreenManager", "isTurnOnWhenUnFolding() " + this.mSubScreenPlugin.isTurnOnSmartCase());
        return this.mSubScreenPlugin.isTurnOnSmartCase();
    }

    public final boolean moveToFrontCoverLauncherTask(ComponentName componentName) {
        boolean z = false;
        if (componentName != null && "com.android.systemui.subscreen.SubHomeActivity".equals(componentName.getClassName())) {
            while (true) {
                Stack stack = this.mTaskStack;
                if (stack.isEmpty()) {
                    break;
                }
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) stack.pop();
                Log.i("SubScreenManager", " Move to Front task : " + runningTaskInfo.topActivity);
                this.mActivityManager.moveTaskToFront(runningTaskInfo.taskId, 2);
                z = true;
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
        if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY && isTurnOnWhenUnFolding() && this.mIsFolderOpened) {
            TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("onFinishedGoingToSleep() getLastWakeReason "), this.mWakefulnessLifeCycle.mLastWakeReason, "SubScreenManager");
            HandlerC34635 handlerC34635 = this.mHandler;
            if (handlerC34635.hasMessages(1000)) {
                Log.i("SubScreenManager", "onFinishedGoingToSleep() remove MSG_FINISH_SUB_HOME_ACTIVITY ");
                handlerC34635.removeMessages(1000);
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
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onFolderStateChanged() opened = ", z, "SubScreenManager");
        if (this.mIsFolderOpened != z) {
            this.mIsFolderOpened = z;
            if (!z && getWindow() == null) {
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
            boolean z2 = LsRune.SUBSCREEN_WATCHFACE;
            if (z2) {
                if (!z) {
                    if (((PluginAODManager) this.mPluginAODManagerLazy.get()).mSysUIConfig.get(2, 0) == 0) {
                        this.mBackgroundExecutor.executeDelayed(100L, new SubScreenManager$$ExternalSyntheticLambda1(this, r1));
                        return;
                    }
                    return;
                }
                boolean z3 = LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY;
                if (!z3) {
                    Lazy lazy = this.mSettingsHelperLazy;
                    SettingsHelper settingsHelper = (SettingsHelper) lazy.get();
                    settingsHelper.getClass();
                    if (((!z2 || settingsHelper.mItemLists.get("show_navigation_for_subscreen").getIntValue() == 0) ? 0 : 1) != 0) {
                        SettingsHelper settingsHelper2 = (SettingsHelper) lazy.get();
                        settingsHelper2.getClass();
                        if (z2) {
                            Settings.Secure.putInt(settingsHelper2.mResolver, "show_navigation_for_subscreen", 0);
                        }
                    }
                }
                if (z3) {
                    this.mTaskStack.clear();
                }
            }
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginConnected(Plugin plugin, final Context context) {
        final PluginSubScreen pluginSubScreen = (PluginSubScreen) plugin;
        if (pluginSubScreen == null || context == null) {
            return;
        }
        if (!LsRune.SUBSCREEN_PLUGIN_DISCONNECT_WHEN_UNFOLDING) {
            Log.d("SubScreenManager", "onPluginConnected() [" + pluginSubScreen + "]");
            startSubScreen(context, pluginSubScreen);
            return;
        }
        Log.d("SubScreenManager", "onPluginConnected() mPendingPluginConnect=" + this.mPendingPluginConnect + " mDeviceInteractive=" + this.mDeviceInteractive + " [" + pluginSubScreen + "]");
        if (!this.mDeviceInteractive && this.mPendingPluginConnect) {
            Runnable runnable = new Runnable() { // from class: com.android.systemui.subscreen.SubScreenManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SubScreenManager.this.startSubScreen(context, pluginSubScreen);
                }
            };
            Log.d("SubScreenManager", "setPendingPluginConnectRunnable");
            this.mPendingPluginConnectRunnable = runnable;
        } else {
            Log.d("SubScreenManager", "clearPendingPluginConnectRunnable");
            this.mPendingPluginConnect = false;
            this.mPendingPluginConnectRunnable = null;
            startSubScreen(context, pluginSubScreen);
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
            Log.w("SubScreenManager", "onStartedWakingUp() no plugin mIsFolderOpened=" + this.mIsFolderOpened);
            if (!LsRune.SUBSCREEN_PLUGIN_DISCONNECT_WHEN_UNFOLDING || this.mIsFolderOpened) {
                return;
            }
            Log.d("SubScreenManager", "clearPendingPluginConnectRunnable");
            this.mPendingPluginConnect = false;
            this.mPendingPluginConnectRunnable = null;
            updatePluginListener();
            return;
        }
        if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY && !this.mIsFolderOpened) {
            int i = this.mWakefulnessLifeCycle.mLastWakeReason;
            if (i == 2) {
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m(" onStartedWakingUp wake up reason  ", i, "SubScreenManager");
            } else {
                List<ActivityManager.RunningTaskInfo> runningTasks = this.mActivityManager.getRunningTasks(1);
                if (runningTasks != null && runningTasks.size() > 0) {
                    ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
                    StringBuilder sb = new StringBuilder(" onStartedWakingUp Current Top Task : ");
                    sb.append(runningTaskInfo.topActivity);
                    sb.append(" , ");
                    TooltipPopup$$ExternalSyntheticOutline0.m13m(sb, runningTaskInfo.displayId, "SubScreenManager");
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
        KeyguardManager keyguardManager = this.mKeyguardManager;
        if (!keyguardManager.isKeyguardSecure()) {
            keyguardManager.semDismissKeyguard();
            return;
        }
        intent.putExtra("runOnCover", true);
        intent.putExtra("bouncerTimeout", 10000);
        keyguardManager.semSetPendingIntentAfterUnlock(null, intent);
    }

    public final void requestDualState(boolean z) {
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("requestDualState ", z, "SubScreenManager");
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
            AbstractC0790xf8f53ce8.m80m(e, new StringBuilder("updateRefreshRate exception "), "SubScreenManager");
        }
        DeviceStateManager deviceStateManager = this.mDeviceStateManager;
        if (z) {
            deviceStateManager.requestState(DeviceStateRequest.newBuilder(4).setFlags(16).build(), this.mContext.getMainExecutor(), this.mDeviceStateRequestCallback);
        } else {
            deviceStateManager.cancelStateRequest();
        }
    }

    public final void setSubHomeActivityResumed(boolean z) {
        if (this.mSubScreenPlugin == null) {
            Log.w("SubScreenManager", "setSubHomeActivityResumed() no plugin");
            return;
        }
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("setSubHomeActivityResumed() ", z, "SubScreenManager");
        this.mSubScreenPlugin.setSubHomeActivityResumed(z);
        if (z) {
            List list = this.mOccludedApps;
            if (((ArrayList) list).contains("com.android.systemui.subscreen.SubHomeActivity")) {
                return;
            }
            ((ArrayList) list).add("com.android.systemui.subscreen.SubHomeActivity");
        }
    }

    public final void setSubRoom(int i, SubRoom subRoom) {
        Log.d("SubScreenManager", "setSubRoom() " + getRoomName(i) + ", " + subRoom);
        this.mSubRoomMap.put(Integer.valueOf(i), subRoom);
    }

    public final void startSubHomeActivity() {
        if (this.mSubDisplay == null || this.mDisplayLifecycle.mIsFolderOpened) {
            return;
        }
        Log.d("SubScreenManager", "startSubHomeActivity() ");
        startSubHomeActivity(this.mSubDisplay);
    }

    public final void startSubScreen(Context context, PluginSubScreen pluginSubScreen) {
        if (pluginSubScreen == null) {
            Log.e("SubScreenManager", "startSubScreen() plugin is null");
            return;
        }
        PluginSubScreen pluginSubScreen2 = this.mSubScreenPlugin;
        if (pluginSubScreen2 == pluginSubScreen) {
            Log.w("SubScreenManager", "startSubScreen() already started");
            return;
        }
        if (pluginSubScreen2 != null) {
            Log.e("SubScreenManager", "startSubScreen: plugin is changed, stop old plugin");
            stopSubScreen();
        }
        Window window = getWindow();
        if (window == null) {
            Log.d("SubScreenManager", "startSubScreen() no window");
            return;
        }
        Log.d("SubScreenManager", "startSubScreen() " + pluginSubScreen);
        this.mSubScreenWindow = window;
        this.mSubScreenPlugin = pluginSubScreen;
        this.mPluginContext = context;
        context.getResources().getConfiguration().updateFrom(this.mSubScreenWindow.getDecorView().getResources().getConfiguration());
        this.mSubScreenPlugin.onSubUIStarted(this.mSubScreenWindow, new Bundle());
        ((PluginAODManager) this.mPluginAODManagerLazy.get()).setSubScreenPlugin(this.mSubScreenPlugin);
        if (LsRune.SUBSCREEN_WATCHFACE) {
            this.mSubScreenPlugin.setSubHomeActivityResumed(this.mActivity.semIsResumed());
        }
        this.mKeyguardUpdateMonitor.registerCallback(this.mCallback);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this.mKeyguardStateCallback);
        if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
            this.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mHandler);
        }
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
        ((PluginAODManager) this.mPluginAODManagerLazy.get()).setSubScreenPlugin(null);
        this.mSubScreenPlugin.onSubUIStopped();
        this.mKeyguardUpdateMonitor.removeCallback(this.mCallback);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).removeCallback(this.mKeyguardStateCallback);
        if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
            this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
        }
        this.mSubScreenPlugin = null;
    }

    public final void updatePluginListener() {
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("updatePluginListener() mIsPluginConnected = "), this.mIsPluginConnected, "SubScreenManager");
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
        if (this.mActivity == null && this.mPresentation == null) {
            Log.d("SubScreenManager", "requestPluginConnection() no activity and no presentation");
            return;
        }
        if (((PluginFaceWidgetManager) this.mFaceWidgetManagerLazy.get()).mFaceWidgetPlugin != null) {
            addPluginListener();
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
