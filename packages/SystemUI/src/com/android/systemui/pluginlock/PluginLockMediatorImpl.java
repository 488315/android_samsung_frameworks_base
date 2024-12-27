package com.android.systemui.pluginlock;

import android.app.PendingIntent;
import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.ViewGroup;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.SecLockIconViewController;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.settingslib.satellite.SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.facewidget.plugin.ExternalClockProvider;
import com.android.systemui.facewidget.plugin.FaceWidgetPluginLockManagerWrapper;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.pluginlock.component.PluginHomeWallpaper;
import com.android.systemui.pluginlock.component.PluginLockFaceWidget;
import com.android.systemui.pluginlock.component.PluginLockHelpText;
import com.android.systemui.pluginlock.component.PluginLockLockIcon;
import com.android.systemui.pluginlock.component.PluginLockMusic;
import com.android.systemui.pluginlock.component.PluginLockNotification;
import com.android.systemui.pluginlock.component.PluginLockSecure;
import com.android.systemui.pluginlock.component.PluginLockShortcut;
import com.android.systemui.pluginlock.component.PluginLockShortcutDnd;
import com.android.systemui.pluginlock.component.PluginLockShortcutFlashLight;
import com.android.systemui.pluginlock.component.PluginLockStatusBar;
import com.android.systemui.pluginlock.component.PluginLockStatusBarCallback;
import com.android.systemui.pluginlock.component.PluginLockSwipe;
import com.android.systemui.pluginlock.component.PluginLockWallpaper;
import com.android.systemui.pluginlock.listener.KeyguardListener;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.pluginlock.model.FingerPrintData;
import com.android.systemui.pluginlock.model.IndicationData;
import com.android.systemui.pluginlock.model.MusicData;
import com.android.systemui.pluginlock.model.NotificationData;
import com.android.systemui.pluginlock.model.ServiceBoxData;
import com.android.systemui.pluginlock.model.ShortcutData;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.KeyguardBatteryStatus;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.google.gson.Gson;
import com.samsung.android.cover.CoverState;
import com.samsung.android.sdk.cover.ScoverManager;
import com.samsung.systemui.splugins.SPluginListener;
import com.samsung.systemui.splugins.SPluginManager;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import dagger.Lazy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PluginLockMediatorImpl implements PluginLockMediator, SPluginListener<PluginLock> {
    private static final String FACE_WIDGET = "face_widget";
    private static final String INDICATION_TEXT = "indication_text_view";
    private static final String LOCKSTAR_FACEWIDGET_AREA = "lockstar_facewidget_area";
    private static final String NOTIFICATION_ICON_ONLY = "notification_icon_only";
    public static final String TAG = "PluginLockMediatorImpl";
    private static int sScreenType;
    private int mBarState;
    private KeyguardListener.Basic mBasicListener;
    private PluginLockFaceWidget mClock;
    private final ExternalClockProvider mClockProvider;
    private final Context mContext;
    private final DozeParameters mDozeParameters;
    String mDynamicLockData;
    private final Handler mHandler;
    private PluginLockHelpText mHelpText;
    private final PluginHomeWallpaper mHomeWallpaper;
    private final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    private PluginLockLockIcon mLockIcon;
    private final LockPatternUtils mLockPatternUtils;
    private final PluginLockWallpaper mLockWallpaper;
    private PluginLockMusic mMusic;
    private PluginLockNotification mNotification;
    private Context mPluginContext;
    private KeyguardListener.SPlugin mSPluginListener;
    private final SPluginManager mSPluginManager;
    private PluginLockSecure mSecure;
    private final SelectedUserInteractor mSelectedUserInteractor;
    private final SettingsHelper mSettingsHelper;
    private ShadeExpansionStateManager mShadeExpansionStateManager;
    private final KeyguardShortcutManager mShortcurManager;
    private PluginLockShortcut mShortcut;
    private PluginLockStatusBar mStatusBar;
    private PluginLockStatusBarCallback mStatusBarCallback;
    private PluginLockSwipe mSwipe;
    private PluginLockShortcutDnd mTaskDnd;
    private PluginLockShortcutFlashLight mTaskFlashLight;
    private final PluginLockUtils mUtils;
    private int mViewMode;
    private PluginLockListener.Window mWindowListener;
    private final List<WeakReference<KeyguardListener.UserSwitch>> mUserSwitchListenerList = new ArrayList();
    private final List<WeakReference<PluginLockListener.State>> mStateListenerList = new ArrayList();
    private DynamicLockData mCurrentDynamicLockData = null;
    private boolean mIsEnabled = false;
    private boolean mIsDynamicLockData = true;
    private boolean mIsRotateMenuHide = false;
    private boolean mIsLockScreenEnabled = true;
    private boolean mIsSecureWindow = false;
    private boolean mIsCoverAttached = false;
    private boolean mIsWallpaperPaused = false;
    private int OFF_BECAUSE_OF_FOLD = 4;
    private ShadeExpansionListener mShadeExpansionListener = new ShadeExpansionListener() { // from class: com.android.systemui.pluginlock.PluginLockMediatorImpl.1
        @Override // com.android.systemui.shade.ShadeExpansionListener
        public void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
            if (PluginLockMediatorImpl.this.mIsWallpaperPaused && shadeExpansionChangeEvent.fraction < 0.1f) {
                PluginLockMediatorImpl.this.sendWallpaperCommand(false);
            }
            if (shadeExpansionChangeEvent.expanded && PluginLockMediatorImpl.this.mBarState == 2) {
                PluginLockMediatorImpl.this.mBasicListener.setQsExpansion(shadeExpansionChangeEvent.fraction);
            }
        }
    };
    private final KeyguardUpdateMonitorCallback mMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.pluginlock.PluginLockMediatorImpl.2
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onKeyguardVisibilityChanged(boolean z) {
            if (PluginLockMediatorImpl.this.mViewMode == 0 || z) {
                return;
            }
            PluginLockMediatorImpl.this.onViewModeChanged(0);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onLocaleChanged() {
            if (PluginLockMediatorImpl.this.mBasicListener != null) {
                PluginLockMediatorImpl.this.mBasicListener.onLocaleChanged();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onStartedGoingToSleep(int i) {
            LogUtil.i(PluginLockMediatorImpl.TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onStartedGoingToSleep why :"), new Object[0]);
            if (PluginLockMediatorImpl.this.isWindowSecured()) {
                PluginLockMediatorImpl.this.updateWindowSecureState(false);
            }
            if (i == PluginLockMediatorImpl.this.OFF_BECAUSE_OF_FOLD) {
                PluginLockMediatorImpl.this.onEventReceived(AbsAdapter$1$$ExternalSyntheticOutline0.m("action", PluginLock.ACTION_LID_SWITCH));
            }
            boolean isLockScreenEnabled = PluginLockMediatorImpl.this.isLockScreenEnabled();
            if (PluginLockMediatorImpl.this.mIsLockScreenEnabled != isLockScreenEnabled) {
                PluginLockMediatorImpl.this.mIsLockScreenEnabled = isLockScreenEnabled;
                Bundle bundle = new Bundle();
                bundle.putString("action", PluginLock.ACTION_LOCK_STYLE_CHANGED);
                bundle.putBoolean("value", PluginLockMediatorImpl.this.mIsLockScreenEnabled);
                PluginLockMediatorImpl.this.onEventReceived(bundle);
            }
            if (PluginLockMediatorImpl.this.mBasicListener != null) {
                PluginLockMediatorImpl.this.mBasicListener.onStartedGoingToSleep(i, PluginLockMediatorImpl.this.mDozeParameters.mControlScreenOffAnimation);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onStartedWakingUp() {
            if (PluginLockMediatorImpl.this.mBasicListener != null) {
                PluginLockMediatorImpl.this.mBasicListener.onStartedWakingUp();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onUpdateCoverState(CoverState coverState) {
            PluginLockMediatorImpl.this.mIsCoverAttached = coverState.attached;
            boolean z = !coverState.switchState;
            Log.d(PluginLockMediatorImpl.TAG, "onUpdateCoverState, mViewMode: " + PluginLockMediatorImpl.this.mViewMode + ", state: " + coverState);
            if (z && PluginLockMediatorImpl.this.mViewMode == 1) {
                PluginLockMediatorImpl.this.onEventReceived(AbsAdapter$1$$ExternalSyntheticOutline0.m("action", PluginLock.ACTION_COVER_CLOSED));
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onUserSwitchComplete(int i) {
            KeyguardListener.UserSwitch userSwitch;
            synchronized (PluginLockMediatorImpl.this.mUserSwitchListenerList) {
                for (int i2 = 0; i2 < PluginLockMediatorImpl.this.mUserSwitchListenerList.size(); i2++) {
                    try {
                        WeakReference weakReference = (WeakReference) PluginLockMediatorImpl.this.mUserSwitchListenerList.get(i2);
                        if (weakReference != null && (userSwitch = (KeyguardListener.UserSwitch) weakReference.get()) != null) {
                            userSwitch.onUserSwitchComplete(i);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onUserSwitching(int i) {
            KeyguardListener.UserSwitch userSwitch;
            synchronized (PluginLockMediatorImpl.this.mUserSwitchListenerList) {
                for (int i2 = 0; i2 < PluginLockMediatorImpl.this.mUserSwitchListenerList.size(); i2++) {
                    try {
                        WeakReference weakReference = (WeakReference) PluginLockMediatorImpl.this.mUserSwitchListenerList.get(i2);
                        if (weakReference != null && (userSwitch = (KeyguardListener.UserSwitch) weakReference.get()) != null) {
                            userSwitch.onUserSwitching(i);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onUserUnlocked() {
            PluginLockMediatorImpl.this.mUtils.checkSafeMode();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onFailedUnlockAttemptChanged() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onLockModeChanged() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onOfflineStateChanged() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onOwnerInfoChanged() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onRemoteLockInfoChanged() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onSystemDialogsShowing() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onUdfpsFingerDown() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onUdfpsFingerUp() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onUnlocking() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onBiometricLockoutChanged(boolean z) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onDlsViewModeChanged(int i) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onDualDARInnerLockscreenRequirementChanged(int i) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onEmergencyStateChanged(int i) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onFaceWidgetFullscreenModeChanged(boolean z) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onLockDisabledChanged(boolean z) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onPackageAdded(String str) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onPackageChanged(String str) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onPackageDataCleared(String str) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onPrimaryBouncerVisibilityChanged(boolean z) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onRefreshBatteryInfo(KeyguardBatteryStatus keyguardBatteryStatus) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onSimulationFailToUnlock(int i) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onTableModeChanged(boolean z) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onUSBRestrictionChanged(boolean z) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onDualDarInnerLockScreenStateChanged(int i, boolean z) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public /* bridge */ /* synthetic */ void onPackageRemoved(String str, boolean z) {
        }
    };

    public PluginLockMediatorImpl(Context context, SelectedUserInteractor selectedUserInteractor, SPluginManager sPluginManager, KeyguardUpdateMonitor keyguardUpdateMonitor, DozeParameters dozeParameters, ExternalClockProvider externalClockProvider, Lazy lazy, SettingsHelper settingsHelper, PluginLockUtils pluginLockUtils, ShadeExpansionStateManager shadeExpansionStateManager) {
        this.mSPluginManager = sPluginManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mClockProvider = externalClockProvider;
        this.mShortcurManager = (KeyguardShortcutManager) lazy.get();
        this.mSettingsHelper = settingsHelper;
        this.mContext = context;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mUtils = pluginLockUtils;
        this.mShadeExpansionStateManager = shadeExpansionStateManager;
        shadeExpansionStateManager.addExpansionListener(this.mShadeExpansionListener);
        pluginLockUtils.addDump(TAG, "## PluginLockMediatorImpl ##, " + this);
        this.mDozeParameters = dozeParameters;
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mHomeWallpaper = new PluginHomeWallpaper(context);
        this.mLockWallpaper = new PluginLockWallpaper(context, null, settingsHelper);
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    private String getItemLocation(int i) {
        int i2 = ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).getRealSize().y;
        int i3 = i2 / 3;
        if (i3 > i) {
            return "top";
        }
        if (i3 * 2 > i) {
            return "background";
        }
        if (i2 >= i) {
            return "bottom";
        }
        return null;
    }

    private int getLockStarItemLocationInfoToInt(String str) {
        String lockStarItemLocationInfo = getLockStarItemLocationInfo(str);
        if (lockStarItemLocationInfo != null) {
            switch (lockStarItemLocationInfo) {
                case "bottom":
                    return 3;
                case "background":
                    return 2;
                case "top":
                    return 1;
            }
        }
        return 0;
    }

    private boolean isCoverAttached() {
        ScoverManager scoverManager = new ScoverManager(this.mContext);
        if (scoverManager.getCoverState() != null) {
            return scoverManager.getCoverState().attached;
        }
        return false;
    }

    public boolean isLockScreenEnabled() {
        return !this.mLockPatternUtils.isLockScreenDisabled(this.mSelectedUserInteractor.getSelectedUserId());
    }

    public /* synthetic */ void lambda$onBarStateChanged$5(int i) {
        this.mBasicListener.onBarStateChanged(i);
    }

    public /* synthetic */ void lambda$onBarStateChanged$6() {
        this.mBasicListener.onBarStateChanged(this.mBarState);
    }

    public /* synthetic */ void lambda$onViewModeChanged$0(int i) {
        this.mWindowListener.onViewModeChanged(i);
    }

    public /* synthetic */ void lambda$setLockscreenTimer$1(long j) {
        this.mWindowListener.onScreenTimeoutChanged(j);
    }

    public /* synthetic */ void lambda$setPluginWallpaperHint$7(SemWallpaperColors semWallpaperColors) {
        this.mWindowListener.onViewModePageChanged(semWallpaperColors);
    }

    public /* synthetic */ void lambda$setScreenOrientation$8(boolean z) {
        this.mWindowListener.onScreenOrientationChangeRequired(z);
    }

    public /* synthetic */ void lambda$updateBiometricRecognition$4(boolean z) {
        this.mWindowListener.updateBiometricRecognition(z);
    }

    public /* synthetic */ void lambda$updateOverlayUserTimeout$3(boolean z) {
        this.mWindowListener.updateOverlayUserTimeout(z);
    }

    public /* synthetic */ void lambda$updateWindowSecureState$2(boolean z) {
        this.mWindowListener.updateWindowSecureState(z);
    }

    private void publishLockStarState() {
        LogUtil.d(TAG, "publishLockStarState mIsDynamicLockData: " + this.mIsDynamicLockData, new Object[0]);
        for (int i = 0; i < this.mStateListenerList.size(); i++) {
            if (this.mStateListenerList.get(i).get() != null) {
                Log.d(TAG, "publishLockStarState : " + this.mStateListenerList.get(i).get());
                try {
                    this.mStateListenerList.get(i).get().onLockStarEnabled(!this.mIsDynamicLockData);
                } catch (Exception e) {
                    EmergencyButton$$ExternalSyntheticOutline0.m("publishLockStarState Exception: ", e, TAG);
                }
            }
        }
        Context context = this.mContext;
        if (context == null || context.getContentResolver() == null) {
            return;
        }
        Settings.Secure.putInt(this.mContext.getContentResolver(), LOCKSTAR_FACEWIDGET_AREA, this.mIsDynamicLockData ? 0 : getLockStarItemLocationInfoToInt(FACE_WIDGET));
    }

    public void sendWallpaperCommand(boolean z) {
        this.mIsWallpaperPaused = z;
        ((WallpaperManager) this.mContext.getSystemService("wallpaper")).semSendWallpaperCommand(2, z ? "samsung.android.wallpaper.pause" : "samsung.android.wallpaper.resume", AbsAdapter$1$$ExternalSyntheticOutline0.m("sender", "quick_panel_wallpaper"));
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void addDump(String str) {
        this.mUtils.addDump(TAG, str);
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public int getCurrentScreenType() {
        return sScreenType;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public String getDynamicLockData() {
        DynamicLockData dynamicLockData = new DynamicLockData();
        Bundle bundle = new Bundle();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = new Bundle();
        Bundle bundle4 = new Bundle();
        Bundle bundle5 = new Bundle();
        for (int i = 0; i < this.mStateListenerList.size(); i++) {
            WeakReference<PluginLockListener.State> weakReference = this.mStateListenerList.get(i);
            if (weakReference != null) {
                PluginLockListener.State state = weakReference.get();
                Log.d(TAG, "getDynamicLockData() listener: " + state);
                if (state != null) {
                    if (state instanceof NotificationPanelViewController) {
                        Bundle onUiInfoRequested = state.onUiInfoRequested(false);
                        if (onUiInfoRequested != null) {
                            bundle = onUiInfoRequested;
                        }
                        Bundle onUiInfoRequested2 = state.onUiInfoRequested(true);
                        if (onUiInfoRequested2 != null) {
                            bundle4 = onUiInfoRequested2;
                        }
                        Log.d(TAG, "getDynamicLockData() bottom: " + bundle);
                        Log.d(TAG, "getDynamicLockData() bottom_land: " + bundle4);
                    } else if (state instanceof FaceWidgetPluginLockManagerWrapper.FaceWidgetLockStarStateCallbackWrapper) {
                        Bundle onUiInfoRequested3 = state.onUiInfoRequested(true);
                        if (onUiInfoRequested3 != null) {
                            bundle2 = onUiInfoRequested3;
                        }
                        Log.d(TAG, "getDynamicLockData() faceWidget: " + bundle2);
                    } else if (state instanceof SecLockIconViewController) {
                        Bundle onUiInfoRequested4 = state.onUiInfoRequested(false);
                        if (onUiInfoRequested4 != null) {
                            bundle3 = onUiInfoRequested4;
                        }
                        Bundle onUiInfoRequested5 = state.onUiInfoRequested(true);
                        if (onUiInfoRequested5 != null) {
                            bundle5 = onUiInfoRequested5;
                        }
                        Log.d(TAG, "getDynamicLockData() lockIcon: " + bundle3);
                        Log.d(TAG, "getDynamicLockData() lockIcon_land: " + bundle5);
                    }
                }
            }
        }
        ShortcutData shortcutData = dynamicLockData.getShortcutData();
        shortcutData.setShortcutInfo(bundle.getString("shortcut_info"));
        shortcutData.setVisibility(Integer.valueOf(bundle.getInt("shortcut_enable")));
        shortcutData.setPaddingBottom(Integer.valueOf(bundle.getInt("shortcut_bottom")));
        shortcutData.setPaddingSide(Integer.valueOf(bundle.getInt("shortcut_side")));
        shortcutData.setPaddingBottomLand(Integer.valueOf(bundle4.getInt("shortcut_bottom")));
        shortcutData.setPaddingSideLand(Integer.valueOf(bundle4.getInt("shortcut_side")));
        shortcutData.setImageSize(Integer.valueOf(bundle.getInt("shortcut_size")));
        FingerPrintData fingerPrintData = dynamicLockData.getFingerPrintData();
        fingerPrintData.setHeight(Integer.valueOf(bundle.getInt("finger_print_height")));
        fingerPrintData.setImageSize(Integer.valueOf(bundle.getInt("finger_print_image_size")));
        fingerPrintData.setPaddingBottom(Integer.valueOf(bundle.getInt("finger_print_margin")));
        fingerPrintData.setEnabled(Boolean.valueOf(bundle.getBoolean("finger_print_enabled")));
        NotificationData notificationData = dynamicLockData.getNotificationData();
        int i2 = bundle2.getInt("nio_gravity", 17);
        int i3 = bundle2.getInt("nio_gravity_land", 17);
        int i4 = bundle.getInt("noti_type");
        notificationData.setNotiType(Integer.valueOf(i4 + 1));
        notificationData.setVisibility(Integer.valueOf(bundle.getInt("noti_visibility")));
        notificationData.getCardData().setTopY(Integer.valueOf(bundle.getInt("noti_top")));
        notificationData.getCardData().setTopYLand(Integer.valueOf(bundle4.getInt("noti_top")));
        if (i4 == 0) {
            notificationData.getCardData().setNotiCardNumbers(Integer.valueOf(bundle.getInt("noti_number")));
        }
        notificationData.getIconOnlyData().setGravity(Integer.valueOf(i2));
        if (i2 == 8388611) {
            notificationData.getIconOnlyData().setPaddingStart(Integer.valueOf(bundle2.getInt("nio_start", 0)));
        } else if (i2 == 8388613) {
            notificationData.getIconOnlyData().setPaddingEnd(Integer.valueOf(bundle2.getInt("nio_start", 0)));
        }
        notificationData.getIconOnlyData().setTopY(Integer.valueOf(bundle.getInt("noti_top", 0)));
        notificationData.getIconOnlyData().setTopYLand(Integer.valueOf(bundle4.getInt("noti_top", 0)));
        notificationData.getIconOnlyData().setGravityLand(Integer.valueOf(i3));
        if (i3 == 8388611) {
            notificationData.getIconOnlyData().setPaddingStartLand(Integer.valueOf(bundle2.getInt("nio_start_land", 0)));
        } else if (i3 == 8388613) {
            notificationData.getIconOnlyData().setPaddingEndLand(Integer.valueOf(bundle2.getInt("nio_start_land", 0)));
        }
        if (i4 == 0) {
            notificationData.getCardData().setNotiCardNumbersLand(Integer.valueOf(bundle4.getInt("noti_number")));
        }
        IndicationData.HelpTextData helpTextData = dynamicLockData.getIndicationData().getHelpTextData();
        helpTextData.setHeight(Integer.valueOf(bundle.getInt("help_text_height", 0)));
        helpTextData.setPaddingBottom(Integer.valueOf(bundle.getInt("help_text_margin", 0)));
        helpTextData.setVisibility(Integer.valueOf(bundle.getInt("help_text_visibility", 0)));
        helpTextData.setPaddingBottomLand(Integer.valueOf(bundle4.getInt("help_text_margin", 0)));
        helpTextData.setVisibilityLand(Integer.valueOf(bundle4.getInt("help_text_visibility", 0)));
        ServiceBoxData serviceBoxData = dynamicLockData.getServiceBoxData();
        serviceBoxData.getClockInfo().setClockType(bundle2.getInt("clock_type", 2));
        serviceBoxData.getClockInfo().setGravity(Integer.valueOf(bundle2.getInt("clock_gravity", 17)));
        serviceBoxData.setVisibility(Integer.valueOf(bundle2.getInt("clock_visibility", 0)));
        serviceBoxData.getClockInfo().setScale(bundle2.getFloat("clock_scale", 1.0f));
        serviceBoxData.getClockInfo().setPaddingStart(Integer.valueOf(bundle2.getInt("clock_side_padding", 0)));
        serviceBoxData.getClockInfo().setPaddingEnd(Integer.valueOf(bundle2.getInt("clock_side_padding", 0)));
        serviceBoxData.setTopY(Integer.valueOf(bundle2.getInt("clock_top", 0)));
        serviceBoxData.setBottomY(Integer.valueOf(bundle2.getInt("clock_bottom", 0)));
        serviceBoxData.getClockInfo().setGravityLand(Integer.valueOf(bundle2.getInt("clock_gravity_land", 17)));
        serviceBoxData.setVisibilityLand(Integer.valueOf(bundle2.getInt("clock_visibility_land", 0)));
        serviceBoxData.getClockInfo().setScaleLand(bundle2.getFloat("clock_scale_land", 1.0f));
        serviceBoxData.getClockInfo().setPaddingStartLand(Integer.valueOf(bundle2.getInt("clock_side_padding_land", 0)));
        serviceBoxData.getClockInfo().setPaddingEndLand(Integer.valueOf(bundle2.getInt("clock_side_padding_land", 0)));
        serviceBoxData.setTopYLand(Integer.valueOf(bundle2.getInt("clock_top_land", 0)));
        serviceBoxData.setBottomYLand(Integer.valueOf(bundle2.getInt("clock_bottom_land", 0)));
        MusicData musicData = dynamicLockData.getMusicData();
        musicData.setTopY(Integer.valueOf(bundle2.getInt("music_top", 0)));
        musicData.setHeight(Integer.valueOf(bundle2.getInt("music_height", 0)));
        musicData.setWidth(Integer.valueOf(bundle2.getInt("music_width", 0)));
        musicData.setPaddingStart(Integer.valueOf(bundle2.getInt("clock_side_padding", 0)));
        musicData.setPaddingEnd(Integer.valueOf(bundle2.getInt("clock_side_padding", 0)));
        musicData.setGravity(Integer.valueOf(bundle2.getInt("music_gravity", 17)));
        musicData.setVisibility(Integer.valueOf(bundle2.getInt("music_visibility", 0)));
        musicData.setTopYLand(Integer.valueOf(bundle2.getInt("music_top_land", 0)));
        musicData.setHeightLand(Integer.valueOf(bundle2.getInt("music_height_land", 0)));
        musicData.setWidthLand(Integer.valueOf(bundle2.getInt("music_width_land", 0)));
        musicData.setPaddingStartLand(Integer.valueOf(bundle2.getInt("clock_side_padding_land", 0)));
        musicData.setPaddingEndLand(Integer.valueOf(bundle2.getInt("clock_side_padding_land", 0)));
        musicData.setGravityLand(Integer.valueOf(bundle2.getInt("music_gravity_land", 17)));
        musicData.setVisibilityLand(Integer.valueOf(bundle2.getInt("music_visibility_land", 0)));
        dynamicLockData.getIndicationData().getLockIconData().setVisibility(Integer.valueOf(bundle3.getInt("lock_icon_visibility", 0)));
        dynamicLockData.getIndicationData().getLockIconData().setVisibilityLand(Integer.valueOf(bundle5.getInt("lock_icon_visibility", 0)));
        Log.d(TAG, "getDynamicLockData() dlsData: " + dynamicLockData.toJsonString());
        return dynamicLockData.toJsonString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x004d, code lost:
    
        if (r9.equals(com.android.systemui.pluginlock.PluginLockMediatorImpl.FACE_WIDGET) == false) goto L14;
     */
    @Override // com.android.systemui.pluginlock.PluginLockMediator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getLockStarItemLocationInfo(java.lang.String r9) {
        /*
            Method dump skipped, instructions count: 344
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.pluginlock.PluginLockMediatorImpl.getLockStarItemLocationInfo(java.lang.String):java.lang.String");
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public PluginHomeWallpaper getPluginHomeWallpaper() {
        return this.mHomeWallpaper;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public PluginLockSwipe getPluginLockSwipe() {
        return this.mSwipe;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public PluginLockWallpaper getPluginLockWallpaper() {
        return this.mLockWallpaper;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public int getSecureMode() {
        PluginLockSecure pluginLockSecure = this.mSecure;
        if (pluginLockSecure != null) {
            return pluginLockSecure.getSecureMode();
        }
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void goToLockedShade() {
        PluginLockListener.State state;
        for (int i = 0; i < this.mStateListenerList.size(); i++) {
            WeakReference<PluginLockListener.State> weakReference = this.mStateListenerList.get(i);
            if (weakReference != null && (state = weakReference.get()) != null) {
                state.goToLockedShade();
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public boolean isDynamicLockEnabled() {
        return this.mIsEnabled && !this.mUtils.isGoingToRescueParty();
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public boolean isHomeWallpaperRequired(int i) {
        return this.mHomeWallpaper.getWallpaperType(i) > 10;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public boolean isRotateMenuHide() {
        StringBuilder sb = new StringBuilder("isRotateMenuHide mIsEnabled: ");
        sb.append(this.mIsEnabled);
        sb.append(", mIsRotateMenuHide: ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, this.mIsRotateMenuHide, TAG);
        return this.mIsEnabled && this.mIsRotateMenuHide;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public boolean isSecure() {
        PluginLockListener.State state;
        for (int i = 0; i < this.mStateListenerList.size(); i++) {
            WeakReference<PluginLockListener.State> weakReference = this.mStateListenerList.get(i);
            if (weakReference != null && (state = weakReference.get()) != null) {
                return state.isSecure();
            }
        }
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public boolean isTouchAndHoldToEditEnabled() {
        return this.mSettingsHelper.isSupportTouchAndHoldToEdit();
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public boolean isTouchConsumablePosition(float f, float f2) {
        KeyguardListener.Basic basic = this.mBasicListener;
        if (basic != null) {
            return basic.isTouchConsumablePosition(f, f2);
        }
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public boolean isWindowSecured() {
        return this.mIsSecureWindow;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void makeExpandedInvisible() {
        PluginLockListener.State state;
        for (int i = 0; i < this.mStateListenerList.size(); i++) {
            WeakReference<PluginLockListener.State> weakReference = this.mStateListenerList.get(i);
            if (weakReference != null && (state = weakReference.get()) != null) {
                state.makeExpandedInvisible();
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onAodTransitionEnd() {
        KeyguardListener.Basic basic = this.mBasicListener;
        if (basic != null) {
            basic.onAodTransitionEnd();
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onBarStateChanged(int i) {
        this.mBarState = i;
        if (this.mBasicListener != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.mBasicListener.onBarStateChanged(i);
            } else {
                this.mHandler.post(new PluginLockMediatorImpl$$ExternalSyntheticLambda1(this, i, 0));
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onDataCleared() {
        this.mLockWallpaper.getCallback().onDataCleared();
        this.mHomeWallpaper.getCallback().onDataCleared();
        this.mHomeWallpaper.clearWallpaper();
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onDensityOrFontScaleChanged() {
        KeyguardListener.Basic basic = this.mBasicListener;
        if (basic != null) {
            basic.onDensityOrFontScaleChanged();
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onEventReceived(Bundle bundle) {
        KeyguardListener.Basic basic = this.mBasicListener;
        if (basic != null) {
            basic.onEventReceived(bundle);
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onFolderStateChanged(boolean z, boolean z2) {
        KeyguardListener.Basic basic = this.mBasicListener;
        if (basic != null) {
            basic.onFolderStateChanged(z, z2);
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onReady() {
        this.mLockWallpaper.getCallback().onReady();
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onRootViewAttached(ViewGroup viewGroup) {
        Log.d(TAG, "onRootViewAttached: ");
        SPluginManager sPluginManager = this.mSPluginManager;
        if (sPluginManager != null) {
            sPluginManager.addPluginListener((SPluginListener) this, PluginLock.class, true);
        } else {
            Log.d(TAG, "onRootViewAttached: mSPluginManager is null.");
        }
        KeyguardListener.SPlugin sPlugin = this.mSPluginListener;
        if (sPlugin != null) {
            sPlugin.onRootViewAttached(viewGroup);
        } else {
            Log.d(TAG, "onRootViewAttached: mSPluginListener is null.");
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onRootViewDetached() {
        Log.d(TAG, "onRootViewDetached");
        SPluginManager sPluginManager = this.mSPluginManager;
        if (sPluginManager != null) {
            sPluginManager.removePluginListener(this);
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onUserActivity() {
        PluginLockListener.State state;
        for (int i = 0; i < this.mStateListenerList.size(); i++) {
            WeakReference<PluginLockListener.State> weakReference = this.mStateListenerList.get(i);
            if (weakReference != null && (state = weakReference.get()) != null) {
                state.onUserActivity();
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onViewModeChanged(int i) {
        PluginLockListener.State state;
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onViewModeChanged mode: ", ", mStateListenerList.size(): ");
        m.append(this.mStateListenerList.size());
        m.append(", mViewMode:");
        m.append(this.mViewMode);
        LogUtil.d(TAG, m.toString(), new Object[0]);
        if (this.mViewMode == i) {
            return;
        }
        this.mViewMode = i;
        if (1 != i && isWindowSecured()) {
            updateWindowSecureState(false);
        }
        for (int i2 = 0; i2 < this.mStateListenerList.size(); i2++) {
            WeakReference<PluginLockListener.State> weakReference = this.mStateListenerList.get(i2);
            if (weakReference != null && (state = weakReference.get()) != null) {
                state.onViewModeChanged(i);
            }
        }
        if (this.mWindowListener != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.mWindowListener.onViewModeChanged(i);
            } else {
                this.mHandler.post(new PluginLockMediatorImpl$$ExternalSyntheticLambda1(this, i, 1));
            }
        }
        if (i == 1) {
            this.mShadeExpansionStateManager.addExpansionListener(this.mShadeExpansionListener);
        } else if (i == 0) {
            this.mShadeExpansionStateManager.removeExpansionListener(this.mShadeExpansionListener);
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onWallpaperChanged(int i) {
        KeyguardListener.Basic basic = this.mBasicListener;
        if (basic != null) {
            basic.onWallpaperChanged(i);
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void recoverItem(int i) {
        PluginLockSecure pluginLockSecure;
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "recoverItem() type:", TAG);
        if (i != 0) {
            if (i == 1 && (pluginLockSecure = this.mSecure) != null) {
                pluginLockSecure.recover();
                return;
            }
            return;
        }
        PluginLockFaceWidget pluginLockFaceWidget = this.mClock;
        if (pluginLockFaceWidget != null) {
            pluginLockFaceWidget.recover();
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void registerStateCallback(PluginLockListener.State state) {
        LogUtil.d(TAG, "registerStateCallback: " + state, new Object[0]);
        synchronized (this.mStateListenerList) {
            for (int i = 0; i < this.mStateListenerList.size(); i++) {
                try {
                    if (this.mStateListenerList.get(i).get() == state) {
                        return;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mStateListenerList.add(new WeakReference<>(state));
            if (state instanceof FaceWidgetPluginLockManagerWrapper.FaceWidgetLockStarStateCallbackWrapper) {
                StringBuilder sb = new StringBuilder();
                sb.append("registerStateCallback isLockStar: ");
                sb.append(!this.mIsDynamicLockData);
                LogUtil.i(TAG, sb.toString(), new Object[0]);
                ((FaceWidgetPluginLockManagerWrapper.FaceWidgetLockStarStateCallbackWrapper) state).onLockStarEnabled(!this.mIsDynamicLockData);
                DynamicLockData dynamicLockData = this.mCurrentDynamicLockData;
                if (dynamicLockData != null) {
                    PluginLockFaceWidget pluginLockFaceWidget = this.mClock;
                    if (pluginLockFaceWidget != null) {
                        pluginLockFaceWidget.loadClockData(null, dynamicLockData);
                    }
                    PluginLockMusic pluginLockMusic = this.mMusic;
                    if (pluginLockMusic != null) {
                        pluginLockMusic.loadMusicData();
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void registerStatusBarCallback(PluginLockStatusBarCallback pluginLockStatusBarCallback) {
        this.mStatusBarCallback = pluginLockStatusBarCallback;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void registerUpdateMonitor() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mMonitorCallback);
        this.mKeyguardUpdateMonitor.registerCallback(this.mMonitorCallback);
        this.mIsCoverAttached = isCoverAttached();
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void registerWindowListener(PluginLockListener.Window window) {
        this.mWindowListener = window;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void removeStateCallback(PluginLockListener.State state) {
        synchronized (this.mStateListenerList) {
            for (int i = 0; i < this.mStateListenerList.size(); i++) {
                try {
                    if (this.mStateListenerList.get(i).get() == state) {
                        this.mStateListenerList.remove(i);
                        return;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void requestDismissKeyguard(Intent intent) {
        boolean z;
        PluginLockListener.State state;
        PluginLockListener.State state2;
        if (intent != null) {
            if (intent.getData() == null && intent.getComponent() == null && intent.getAction() == null) {
                return;
            }
            ComponentName component = intent.getComponent();
            int i = 0;
            if (component != null) {
                z = false;
                for (int i2 = 0; i2 < this.mStateListenerList.size(); i2++) {
                    WeakReference<PluginLockListener.State> weakReference = this.mStateListenerList.get(i2);
                    if (weakReference != null && (state2 = weakReference.get()) != null && (state2 instanceof NotificationPanelViewController)) {
                        z = state2.isNoUnlockNeed(component.getPackageName());
                    }
                }
            } else {
                z = false;
            }
            LogUtil.d(TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("requestDismissKeyguard isNoUnlockNeedApp: ", z), new Object[0]);
            if (z) {
                while (i < this.mStateListenerList.size()) {
                    WeakReference<PluginLockListener.State> weakReference2 = this.mStateListenerList.get(i);
                    if (weakReference2 != null) {
                        PluginLockListener.State state3 = weakReference2.get();
                        if (state3 instanceof NotificationPanelViewController) {
                            ((NotificationPanelViewController) state3).onUnNeedLockAppStarted(component);
                        }
                    }
                    i++;
                }
                return;
            }
            PendingIntent activity = PendingIntent.getActivity(this.mContext, intent.getComponent() != null ? intent.getComponent().getClassName().hashCode() : intent.getAction() != null ? intent.getAction().hashCode() : 0, intent, 201326592);
            KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_PLUGIN_LOCK);
            while (i < this.mStateListenerList.size()) {
                WeakReference<PluginLockListener.State> weakReference3 = this.mStateListenerList.get(i);
                if (weakReference3 != null && (state = weakReference3.get()) != null) {
                    state.startPendingIntentDismissingKeyguard(activity);
                }
                i++;
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void resetConfigs() {
        PluginLockListener.State state;
        Log.e(TAG, "resetConfig mIsDynamicLockData: " + this.mIsDynamicLockData);
        if (DeviceState.isTablet() || !WallpaperUtils.isVideoWallpaper(this.mContext)) {
            setScreenOrientation(false, false);
        } else {
            this.mIsRotateMenuHide = false;
        }
        PluginLockListener.Window window = this.mWindowListener;
        if (window != null) {
            window.updateOverlayUserTimeout(false);
        }
        for (int i = 0; i < this.mStateListenerList.size(); i++) {
            WeakReference<PluginLockListener.State> weakReference = this.mStateListenerList.get(i);
            if (weakReference != null && (state = weakReference.get()) != null) {
                state.onViewModeChanged(0);
                if (!this.mIsDynamicLockData) {
                    state.onLockStarEnabled(false);
                }
            }
        }
        this.mIsDynamicLockData = true;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void resetDynamicLock() {
        PluginLockListener.State state;
        Log.e(TAG, "resetDynamicLock");
        for (int i = 0; i < this.mStateListenerList.size(); i++) {
            WeakReference<PluginLockListener.State> weakReference = this.mStateListenerList.get(i);
            if (weakReference != null && (state = weakReference.get()) != null) {
                state.resetDynamicLock();
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void resetDynamicLockData(boolean z) {
        PluginLockListener.State state;
        this.mCurrentDynamicLockData = null;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("resetDynamicLockData() reconnectReq: ", TAG, z);
        PluginLockNotification pluginLockNotification = this.mNotification;
        if (pluginLockNotification != null) {
            pluginLockNotification.reset(z);
        }
        PluginLockSecure pluginLockSecure = this.mSecure;
        if (pluginLockSecure != null) {
            pluginLockSecure.reset(z);
        }
        PluginLockShortcut pluginLockShortcut = this.mShortcut;
        if (pluginLockShortcut != null) {
            pluginLockShortcut.reset(z);
            this.mShortcurManager.updateShortcuts();
        }
        PluginLockStatusBar pluginLockStatusBar = this.mStatusBar;
        if (pluginLockStatusBar != null) {
            pluginLockStatusBar.reset(z);
        }
        PluginLockSwipe pluginLockSwipe = this.mSwipe;
        if (pluginLockSwipe != null) {
            pluginLockSwipe.reset(z);
        }
        PluginLockWallpaper pluginLockWallpaper = this.mLockWallpaper;
        if (pluginLockWallpaper != null) {
            if (!LsRune.LOCKUI_SUB_DISPLAY_LOCK || !LsRune.PLUGIN_LOCK_LSM) {
                pluginLockWallpaper.reset(z);
            } else if (z) {
                pluginLockWallpaper.reset(true);
            } else {
                pluginLockWallpaper.resetAll();
            }
        }
        PluginLockFaceWidget pluginLockFaceWidget = this.mClock;
        if (pluginLockFaceWidget != null) {
            pluginLockFaceWidget.reset(z);
        }
        PluginLockMusic pluginLockMusic = this.mMusic;
        if (pluginLockMusic != null) {
            pluginLockMusic.reset(z);
        }
        PluginLockLockIcon pluginLockLockIcon = this.mLockIcon;
        if (pluginLockLockIcon != null) {
            pluginLockLockIcon.reset(z);
        }
        for (int i = 0; i < this.mStateListenerList.size(); i++) {
            WeakReference<PluginLockListener.State> weakReference = this.mStateListenerList.get(i);
            if (weakReference != null && (state = weakReference.get()) != null) {
                state.onPluginLockReset();
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void resetItem(int i, boolean z) {
        PluginLockSecure pluginLockSecure;
        KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("resetItem() type:", i, ", reconnectReq:", z, TAG);
        if (i != 0) {
            if (i == 1 && (pluginLockSecure = this.mSecure) != null) {
                pluginLockSecure.reset(z);
                return;
            }
            return;
        }
        PluginLockFaceWidget pluginLockFaceWidget = this.mClock;
        if (pluginLockFaceWidget != null) {
            pluginLockFaceWidget.reset(z);
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setDynamicLockData(String str) {
        PluginLockListener.State state;
        this.mDynamicLockData = str;
        for (int i = 0; i < this.mStateListenerList.size(); i++) {
            WeakReference<PluginLockListener.State> weakReference = this.mStateListenerList.get(i);
            if (weakReference != null && (state = weakReference.get()) != null) {
                state.setDynamicLockData(str);
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setEnabled(boolean z) {
        LogUtil.d(TAG, "setEnabled: " + z + ", " + this, new Object[0]);
        this.mIsEnabled = z;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setInstanceState(int i, PluginLockInstanceState pluginLockInstanceState) {
        Log.d(TAG, "setInstanceState, screen: " + i + ", state: " + pluginLockInstanceState);
        if (this.mClock == null) {
            PluginLockFaceWidget pluginLockFaceWidget = new PluginLockFaceWidget(this.mContext, pluginLockInstanceState, this.mClockProvider, this.mSettingsHelper, this);
            this.mClock = pluginLockFaceWidget;
            pluginLockFaceWidget.registerStateCallback(this.mStateListenerList);
        }
        this.mClock.setInstanceState(pluginLockInstanceState);
        if (this.mMusic == null) {
            PluginLockMusic pluginLockMusic = new PluginLockMusic(this.mContext, pluginLockInstanceState, this.mSettingsHelper);
            this.mMusic = pluginLockMusic;
            pluginLockMusic.registerStateCallback(this.mStateListenerList);
        }
        this.mMusic.setInstanceState(pluginLockInstanceState);
        if (this.mHelpText == null) {
            this.mHelpText = new PluginLockHelpText(this.mContext, pluginLockInstanceState, this.mSettingsHelper);
        }
        this.mHelpText.setInstanceState(pluginLockInstanceState);
        if (this.mLockIcon == null) {
            this.mLockIcon = new PluginLockLockIcon(this.mContext, pluginLockInstanceState, this.mSettingsHelper);
        }
        this.mLockIcon.setInstanceState(pluginLockInstanceState);
        if (this.mNotification == null) {
            this.mNotification = new PluginLockNotification(this.mContext, pluginLockInstanceState, this.mSettingsHelper, this);
        }
        this.mNotification.setInstanceState(pluginLockInstanceState);
        if (this.mSecure == null) {
            this.mSecure = new PluginLockSecure(this.mContext, pluginLockInstanceState, this.mSettingsHelper);
        }
        this.mSecure.setInstanceState(pluginLockInstanceState);
        if (this.mShortcut == null) {
            this.mShortcut = new PluginLockShortcut(this.mContext, pluginLockInstanceState, this.mSettingsHelper, this);
        }
        this.mShortcut.setInstanceState(pluginLockInstanceState);
        if (this.mStatusBar == null) {
            PluginLockStatusBar pluginLockStatusBar = new PluginLockStatusBar(this.mContext, pluginLockInstanceState, this.mSettingsHelper);
            this.mStatusBar = pluginLockStatusBar;
            pluginLockStatusBar.setCallback(this.mStatusBarCallback);
        }
        this.mStatusBar.setInstanceState(pluginLockInstanceState);
        if (this.mSwipe == null) {
            this.mSwipe = new PluginLockSwipe(this.mContext, pluginLockInstanceState, this.mSettingsHelper);
        }
        this.mSwipe.setInstanceState(pluginLockInstanceState);
        this.mLockWallpaper.setInstanceState(i, pluginLockInstanceState);
        if (pluginLockInstanceState != null) {
            this.mPluginContext = pluginLockInstanceState.getPluginLockContext();
        } else {
            this.mPluginContext = null;
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setKeyguardBasicListener(KeyguardListener.Basic basic) {
        this.mBasicListener = basic;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setKeyguardSPluginListener(KeyguardListener.SPlugin sPlugin) {
        this.mSPluginListener = sPlugin;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setKeyguardUserSwitchListener(KeyguardListener.UserSwitch userSwitch) {
        LogUtil.d(TAG, "setKeyguardUserSwitchListener: " + userSwitch, new Object[0]);
        synchronized (this.mUserSwitchListenerList) {
            for (int i = 0; i < this.mUserSwitchListenerList.size(); i++) {
                try {
                    if (this.mUserSwitchListenerList.get(i).get() == userSwitch) {
                        return;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mUserSwitchListenerList.add(new WeakReference<>(userSwitch));
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setLockscreenEnabled(boolean z) {
        this.mIsLockScreenEnabled = z;
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setLockscreenTimer(final long j) {
        if (this.mWindowListener != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.mWindowListener.onScreenTimeoutChanged(j);
            } else {
                this.mHandler.post(new Runnable() { // from class: com.android.systemui.pluginlock.PluginLockMediatorImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PluginLockMediatorImpl.this.lambda$setLockscreenTimer$1(j);
                    }
                });
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setNoSensorConsumer(Consumer<Boolean> consumer) {
        this.mLockWallpaper.setNoSensorConsumer(consumer);
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setPluginLock(PluginLock pluginLock) {
        PluginLockListener.State state;
        Log.d(TAG, "setPluginLock() " + this.mStateListenerList.size());
        for (int i = 0; i < this.mStateListenerList.size(); i++) {
            WeakReference<PluginLockListener.State> weakReference = this.mStateListenerList.get(i);
            if (weakReference != null && (state = weakReference.get()) != null) {
                state.setPluginLock(pluginLock);
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setPluginLockItem(PluginLockInstanceState pluginLockInstanceState) {
        DynamicLockData dynamicLockData;
        ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("setPluginLockItem() mDynamicLockData:"), this.mDynamicLockData, TAG);
        try {
            dynamicLockData = (DynamicLockData) new Gson().fromJson(DynamicLockData.class, this.mDynamicLockData);
        } catch (Throwable th) {
            this.mUtils.addDump(TAG, "[parse, apply] " + th.toString());
            th.printStackTrace();
            dynamicLockData = null;
        }
        Log.d(TAG, "setPluginLockItem() currData:" + this.mCurrentDynamicLockData);
        Log.d(TAG, "setPluginLockItem() newData:" + dynamicLockData);
        if (dynamicLockData != null) {
            try {
                this.mSwipe.apply(this.mCurrentDynamicLockData, dynamicLockData);
                this.mSecure.apply(this.mCurrentDynamicLockData, dynamicLockData);
                this.mLockWallpaper.apply(this.mCurrentDynamicLockData, dynamicLockData);
                this.mHelpText.apply(this.mCurrentDynamicLockData, dynamicLockData);
                this.mLockIcon.apply(this.mCurrentDynamicLockData, dynamicLockData);
            } catch (Throwable th2) {
                this.mUtils.addDump(TAG, "[basic, apply] " + th2.toString());
                th2.printStackTrace();
            }
            try {
                this.mClock.apply(this.mCurrentDynamicLockData, dynamicLockData);
            } catch (Throwable th3) {
                this.mUtils.addDump(TAG, "[clock, apply] " + th3.toString());
                th3.printStackTrace();
            }
            try {
                this.mMusic.apply(this.mCurrentDynamicLockData, dynamicLockData);
            } catch (Throwable th4) {
                this.mUtils.addDump(TAG, "[music, apply] " + th4.toString());
                th4.printStackTrace();
            }
            try {
                this.mNotification.apply(this.mCurrentDynamicLockData, dynamicLockData);
            } catch (Throwable th5) {
                this.mUtils.addDump(TAG, "[notification, apply] " + th5.toString());
                th5.printStackTrace();
            }
            try {
                this.mShortcut.apply(this.mCurrentDynamicLockData, dynamicLockData);
            } catch (Throwable th6) {
                this.mUtils.addDump(TAG, "[shortcut, apply] " + th6.toString());
                th6.printStackTrace();
            }
            try {
                this.mStatusBar.apply(this.mCurrentDynamicLockData, dynamicLockData);
            } catch (Throwable th7) {
                this.mUtils.addDump(TAG, "[statusbar, apply] " + th7.toString());
                th7.printStackTrace();
            }
            this.mCurrentDynamicLockData = dynamicLockData;
            this.mIsDynamicLockData = dynamicLockData.isDlsData();
            publishLockStarState();
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setPluginWallpaper(int i, int i2, String str) {
        setPluginWallpaper(sScreenType, i, i2, str);
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setPluginWallpaperHint(String str) {
        setPluginWallpaperHint(sScreenType, str);
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setPluginWallpaperState(int i, int i2) {
        if (this.mBasicListener != null) {
            Bundle bundle = new Bundle();
            bundle.putString("action", PluginLock.ACTION_WALLPAPER_STATE_CHANGED);
            bundle.putInt(PluginLock.KEY_SCREEN, i);
            bundle.putInt("state", i2);
            this.mBasicListener.onEventReceived(bundle);
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setQsExpansion(float f) {
        if (f >= 0.1f && !this.mIsWallpaperPaused) {
            sendWallpaperCommand(true);
        } else if (this.mIsWallpaperPaused && f < 0.1f) {
            sendWallpaperCommand(false);
        }
        KeyguardListener.Basic basic = this.mBasicListener;
        if (basic == null || !this.mIsEnabled) {
            return;
        }
        basic.setQsExpansion(f);
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setScreenOrientation(boolean z, boolean z2) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("setScreenOrientation noSensor: ", ", hideMenu : ", TAG, z, z2);
        if (!z && WallpaperUtils.isVideoWallpaper(this.mContext)) {
            Log.d(TAG, "setScreenOrientation ignore, video wallpaper");
            return;
        }
        if (z && DeviceState.isTablet()) {
            Log.d(TAG, "setScreenOrientation ignore, tablet");
            return;
        }
        if (this.mWindowListener != null) {
            if (z) {
                this.mIsRotateMenuHide = z2;
            } else {
                this.mIsRotateMenuHide = false;
            }
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.mWindowListener.onScreenOrientationChangeRequired(z);
            } else {
                this.mHandler.post(new PluginLockMediatorImpl$$ExternalSyntheticLambda2(this, z, 1));
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setScreenTypeChanged(int i) {
        KeyguardSecPatternView$$ExternalSyntheticOutline0.m(i, "setScreenTypeChanged() type: ", TAG);
        sScreenType = (LsRune.LOCKUI_SUB_DISPLAY_LOCK || LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY) ? i : 0;
        PluginLockWallpaper.setScreenTypeChanged(i);
        PluginHomeWallpaper.setScreenTypeChanged(i);
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void unregisterUpdateMonitor() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mMonitorCallback);
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void updateBiometricRecognition(boolean z) {
        if (this.mWindowListener != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.mWindowListener.updateBiometricRecognition(z);
            } else {
                this.mHandler.post(new PluginLockMediatorImpl$$ExternalSyntheticLambda2(this, z, 3));
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void updateDynamicLockData(String str) {
        DynamicLockData dynamicLockData;
        PluginLockListener.State state;
        if ((LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY) && sScreenType == 1) {
            LogUtil.w(TAG, "updateDynamicLockData skip", new Object[0]);
            return;
        }
        LogUtil.d(TAG, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("updateDynamicLockData dynamicLockData: ", str), new Object[0]);
        try {
            dynamicLockData = (DynamicLockData) new Gson().fromJson(DynamicLockData.class, str);
        } catch (Throwable th) {
            this.mUtils.addDump(TAG, "[parse, update] " + th.toString());
            th.printStackTrace();
            dynamicLockData = null;
        }
        Log.d(TAG, "updateDynamicLockData() currData:" + this.mCurrentDynamicLockData + ", newData:" + dynamicLockData);
        if (dynamicLockData != null) {
            try {
                this.mSwipe.update(this.mCurrentDynamicLockData, dynamicLockData);
                this.mSecure.update(this.mCurrentDynamicLockData, dynamicLockData);
                this.mLockWallpaper.update(this.mCurrentDynamicLockData, dynamicLockData);
                this.mHelpText.update(this.mCurrentDynamicLockData, dynamicLockData);
                this.mLockIcon.update(this.mCurrentDynamicLockData, dynamicLockData);
            } catch (Throwable th2) {
                this.mUtils.addDump(TAG, "[basic, update] " + th2.toString());
                th2.printStackTrace();
            }
            try {
                this.mClock.update(this.mCurrentDynamicLockData, dynamicLockData);
            } catch (Throwable th3) {
                this.mUtils.addDump(TAG, "[clock, update] " + th3.toString());
                th3.printStackTrace();
            }
            try {
                this.mMusic.update(this.mCurrentDynamicLockData, dynamicLockData);
            } catch (Throwable th4) {
                this.mUtils.addDump(TAG, "[music, update] " + th4.toString());
                th4.printStackTrace();
            }
            try {
                this.mNotification.update(this.mCurrentDynamicLockData, dynamicLockData);
            } catch (Throwable th5) {
                this.mUtils.addDump(TAG, "[notification, update] " + th5.toString());
                th5.printStackTrace();
            }
            try {
                this.mStatusBar.update(this.mCurrentDynamicLockData, dynamicLockData);
            } catch (Throwable th6) {
                this.mUtils.addDump(TAG, "[statusbar, update] " + th6.toString());
                th6.printStackTrace();
            }
            try {
                this.mShortcut.update(this.mCurrentDynamicLockData, dynamicLockData);
            } catch (Throwable th7) {
                this.mUtils.addDump(TAG, "[shortcut, update] " + th7.toString());
                th7.printStackTrace();
            }
            this.mCurrentDynamicLockData = dynamicLockData;
            this.mIsDynamicLockData = dynamicLockData.isDlsData();
            publishLockStarState();
        }
        for (int i = 0; i < this.mStateListenerList.size(); i++) {
            WeakReference<PluginLockListener.State> weakReference = this.mStateListenerList.get(i);
            if (weakReference != null && (state = weakReference.get()) != null) {
                state.updateDynamicLockData(str);
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void updateOverlayUserTimeout(boolean z) {
        if (this.mWindowListener != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.mWindowListener.updateOverlayUserTimeout(z);
            } else {
                this.mHandler.post(new PluginLockMediatorImpl$$ExternalSyntheticLambda2(this, z, 2));
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void updateWindowSecureState(boolean z) {
        SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("updateWindowSecureState() : ", TAG, z);
        this.mIsSecureWindow = z;
        if (this.mWindowListener != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.mWindowListener.updateWindowSecureState(z);
            } else {
                this.mHandler.post(new PluginLockMediatorImpl$$ExternalSyntheticLambda2(this, z, 0));
            }
        }
    }

    @Override // com.samsung.systemui.splugins.SPluginListener
    public void onPluginConnected(PluginLock pluginLock, Context context) {
        Log.d(TAG, "onPluginConnected");
        KeyguardListener.SPlugin sPlugin = this.mSPluginListener;
        if (sPlugin != null) {
            sPlugin.onPluginConnected(pluginLock, context);
        }
    }

    @Override // com.samsung.systemui.splugins.SPluginListener
    public void onPluginDisconnected(PluginLock pluginLock, int i) {
        Log.d(TAG, "onPluginDisconnected");
        KeyguardListener.SPlugin sPlugin = this.mSPluginListener;
        if (sPlugin != null) {
            sPlugin.onPluginDisconnected(pluginLock, i);
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setPluginWallpaper(int i, int i2, int i3, String str) {
        setPluginWallpaper(i, i2, i3, str, null);
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setPluginWallpaperHint(int i, String str) {
        final SemWallpaperColors semWallpaperColors;
        if (str != null) {
            if (str.equals("white")) {
                SemWallpaperColors.Builder builder = new SemWallpaperColors.Builder();
                builder.setColorType(0);
                semWallpaperColors = builder.build();
            } else if (str.equals("black")) {
                SemWallpaperColors.Builder builder2 = new SemWallpaperColors.Builder();
                builder2.setColorType(1);
                semWallpaperColors = builder2.build();
            } else if (!str.equals("")) {
                semWallpaperColors = SemWallpaperColors.fromXml(str);
            }
            if ((!LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY) && isHomeWallpaperRequired(1) && this.mHomeWallpaper.getCurrentScreen() == 1) {
                if (!LsRune.WALLPAPER_VIRTUAL_DISPLAY && !this.mIsCoverAttached) {
                    Log.w(TAG, "setPluginWallpaperHint() cover is not attached");
                    return;
                } else {
                    this.mHomeWallpaper.setWallpaperHints(semWallpaperColors);
                    this.mHomeWallpaper.updateHint();
                }
            }
            this.mLockWallpaper.setWallpaperHints(semWallpaperColors);
            this.mLockWallpaper.updateHint();
            if (this.mViewMode != 1 || this.mWindowListener == null) {
                return;
            }
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.mWindowListener.onViewModePageChanged(semWallpaperColors);
                return;
            } else {
                this.mHandler.post(new Runnable() { // from class: com.android.systemui.pluginlock.PluginLockMediatorImpl$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        PluginLockMediatorImpl.this.lambda$setPluginWallpaperHint$7(semWallpaperColors);
                    }
                });
                return;
            }
        }
        semWallpaperColors = null;
        if (LsRune.WALLPAPER_SUB_WATCHFACE) {
        }
        if (!LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
        }
        this.mHomeWallpaper.setWallpaperHints(semWallpaperColors);
        this.mHomeWallpaper.updateHint();
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void setPluginWallpaper(int i, int i2, int i3, String str, String str2) {
        if ((LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY) && i == 1 && (i2 == -2 || i2 >= 10)) {
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(i2, i, "setPluginWallpaper() Home, type: ", ", screen: ", TAG);
            if (LsRune.WALLPAPER_VIRTUAL_DISPLAY && !this.mIsCoverAttached) {
                Log.w(TAG, "setPluginWallpaper() cover is not attached");
                return;
            }
            int i4 = i2 >= 20 ? 1 : 0;
            PluginHomeWallpaper pluginHomeWallpaper = this.mHomeWallpaper;
            if (pluginHomeWallpaper != null) {
                pluginHomeWallpaper.setWallpaper(i4, i2, i3, str, str2);
                return;
            }
            return;
        }
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(i2, i, "setPluginWallpaper() Lock, type: ", ", screen: ", TAG);
        if (LsRune.LOCKUI_SUB_DISPLAY_LOCK && i2 == -3) {
            this.mLockWallpaper.resetAll();
        } else if (i2 == -2) {
            this.mLockWallpaper.reset(false);
        } else {
            this.mLockWallpaper.update(this.mPluginContext, i2, i3, str, str2);
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockMediator
    public void onBarStateChanged() {
        if (this.mBasicListener != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.mBasicListener.onBarStateChanged(this.mBarState);
            } else {
                this.mHandler.post(new Runnable() { // from class: com.android.systemui.pluginlock.PluginLockMediatorImpl$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        PluginLockMediatorImpl.this.lambda$onBarStateChanged$6();
                    }
                });
            }
        }
    }
}
