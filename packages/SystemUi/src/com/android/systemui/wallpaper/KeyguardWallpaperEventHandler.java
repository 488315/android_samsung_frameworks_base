package com.android.systemui.wallpaper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.biometrics.BiometricSourceType;
import android.net.Uri;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.net.vpn.VpnErrorValues;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardWallpaperEventHandler {
    public final C36691 mBroadcastReceiver;
    public final Context mContext;
    public final C36735 mDesktopCallback;
    public Consumer mEventConsumer;
    public final KeyguardUpdateMonitorCallback mInfoCallback;
    public boolean mOccluded;
    public final SettingsHelper mSettingsHelper;
    public final C36724 mSettingsListener;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final C36713 mWakefulnessObserver;

    /* renamed from: -$$Nest$msendMessage, reason: not valid java name */
    public static void m2731$$Nest$msendMessage(KeyguardWallpaperEventHandler keyguardWallpaperEventHandler, int i, Object obj, int i2) {
        keyguardWallpaperEventHandler.getClass();
        Log.d("KeyguardWallpaperEventHandler", "sendMessage(), what = " + i + " , obj = " + obj + " , arg1 = " + i2 + " , arg2 = -1");
        if (keyguardWallpaperEventHandler.mEventConsumer != null) {
            Message message = new Message();
            message.what = i;
            if (obj != null) {
                message.obj = obj;
            }
            if (i2 != -1) {
                message.arg1 = i2;
            }
            keyguardWallpaperEventHandler.mEventConsumer.accept(message);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.content.BroadcastReceiver, com.android.systemui.wallpaper.KeyguardWallpaperEventHandler$1] */
    /* JADX WARN: Type inference failed for: r7v1, types: [com.android.systemui.wallpaper.KeyguardWallpaperEventHandler$3, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v1, types: [com.android.systemui.util.SettingsHelper$OnChangedCallback, com.android.systemui.wallpaper.KeyguardWallpaperEventHandler$4] */
    /* JADX WARN: Type inference failed for: r9v1, types: [com.android.systemui.util.DesktopManager$Callback, com.android.systemui.wallpaper.KeyguardWallpaperEventHandler$5] */
    public KeyguardWallpaperEventHandler(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, WakefulnessLifecycle wakefulnessLifecycle, SettingsHelper settingsHelper) {
        ?? r4 = new BroadcastReceiver() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperEventHandler.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("com.samsung.android.theme.themecenter.THEME_APPLY_START".equals(action)) {
                    Log.d("KeyguardWallpaperEventHandler", "onOpenThemeChangeStarted: packageName = " + intent.getStringExtra("packageName"));
                    KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(KeyguardWallpaperEventHandler.this, 729, null, -1);
                    return;
                }
                if ("com.samsung.android.theme.themecenter.THEME_REAPPLY".equals(action)) {
                    Log.d("KeyguardWallpaperEventHandler", "onOpenThemeReApply()");
                    KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(KeyguardWallpaperEventHandler.this, 731, null, -1);
                } else if ("com.samsung.android.theme.themecenter.THEME_APPLY".equals(action)) {
                    Log.d("KeyguardWallpaperEventHandler", "onOpenThemeChanged()");
                    KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(KeyguardWallpaperEventHandler.this, 730, null, -1);
                }
            }
        };
        this.mBroadcastReceiver = r4;
        Uri[] uriArr = {Settings.System.getUriFor("minimal_battery_use"), Settings.System.getUriFor("ultra_powersaving_mode"), Settings.System.getUriFor("emergency_mode"), Settings.System.getUriFor("lockscreen_wallpaper"), Settings.System.getUriFor("lockscreen_wallpaper_sub"), Settings.System.getUriFor("lock_adaptive_color"), Settings.System.getUriFor("lock_adaptive_color_sub"), Settings.System.getUriFor("lockscreen_wallpaper_transparent"), Settings.System.getUriFor("sub_display_lockscreen_wallpaper_transparency"), Settings.System.getUriFor("wallpapertheme_state"), Settings.System.getUriFor("lock_screen_allow_rotation")};
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperEventHandler.2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                Log.d("KeyguardWallpaperEventHandler", "onBiometricAuthenticated()");
                if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                    KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(KeyguardWallpaperEventHandler.this, 727, null, -1);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
                Log.d("KeyguardWallpaperEventHandler", "onBiometricError()");
                if (biometricSourceType == BiometricSourceType.FACE && i == 3) {
                    KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(KeyguardWallpaperEventHandler.this, 728, null, -1);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDlsViewModeChanged(int i) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("onDlsViewModeChanged mode: ", i, "KeyguardWallpaperEventHandler");
                KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(KeyguardWallpaperEventHandler.this, 732, null, i);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDreamingStateChanged(boolean z) {
                KeyguardWallpaperEventHandler keyguardWallpaperEventHandler = KeyguardWallpaperEventHandler.this;
                boolean isCarUiMode = WallpaperUtils.isCarUiMode(keyguardWallpaperEventHandler.mContext);
                EmergencyButtonController$$ExternalSyntheticOutline0.m59m("onDreamingStateChanged: ", z, ", isCarUiMode = ", isCarUiMode, "KeyguardWallpaperEventHandler");
                if (isCarUiMode) {
                    if (z) {
                        KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, 835, null, -1);
                    } else {
                        KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, 834, null, -1);
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
                Log.d("KeyguardWallpaperEventHandler", "onKeyguardBouncerFullyShowingChanged(), bouncer: " + z);
                KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(KeyguardWallpaperEventHandler.this, 724, Boolean.valueOf(z), -1);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardGoingAway() {
                Log.d("KeyguardWallpaperEventHandler", "onKeyguardGoingAway()");
                KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(KeyguardWallpaperEventHandler.this, 719, null, -1);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) {
                KeyguardWallpaperEventHandler keyguardWallpaperEventHandler = KeyguardWallpaperEventHandler.this;
                boolean z2 = keyguardWallpaperEventHandler.mUpdateMonitor.mKeyguardOccluded;
                EmergencyButtonController$$ExternalSyntheticOutline0.m59m("onKeyguardVisibilityChanged(), showing: ", z, " , occluded = ", z2, "KeyguardWallpaperEventHandler");
                if (keyguardWallpaperEventHandler.mOccluded != z2) {
                    KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, 725, Boolean.valueOf(z2), -1);
                    keyguardWallpaperEventHandler.mOccluded = z2;
                }
                KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, 723, Boolean.valueOf(z), -1);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onPackageAdded(String str) {
                if (LsRune.WALLPAPER_FESTIVAL_WALLPAPER && "com.samsung.android.festivalwallpaper".equals(str)) {
                    Intent intent = new Intent();
                    intent.setAction("com.samsung.intent.action.LAUNCH_FESTIVAL_WALLPAPER");
                    intent.setPackage("com.samsung.android.festivalwallpaper");
                    intent.addFlags(32);
                    KeyguardWallpaperEventHandler.this.mContext.sendBroadcast(intent, "com.samsung.android.permission.SET_FESTIVAL_WALLPAPER");
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUnlocking() {
                Log.d("KeyguardWallpaperEventHandler", "onUnlocking()");
                KeyguardWallpaperEventHandler keyguardWallpaperEventHandler = KeyguardWallpaperEventHandler.this;
                KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, 726, null, -1);
                keyguardWallpaperEventHandler.mOccluded = false;
                KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, 725, Boolean.FALSE, -1);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitchComplete(int i) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("onUserSwitchComplete(), userId: ", i, "KeyguardWallpaperEventHandler");
                KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(KeyguardWallpaperEventHandler.this, 721, null, i);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitching(int i) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("onUserSwitching(), userId: ", i, "KeyguardWallpaperEventHandler");
                KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(KeyguardWallpaperEventHandler.this, 720, null, i);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUdfpsFingerDown$1() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUdfpsFingerUp$1() {
            }
        };
        this.mInfoCallback = keyguardUpdateMonitorCallback;
        ?? r7 = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperEventHandler.3
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                Log.d("KeyguardWallpaperEventHandler", "onFinishedGoingToSleep()");
                KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(KeyguardWallpaperEventHandler.this, 835, null, -1);
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                Log.d("KeyguardWallpaperEventHandler", "onFinishedWakingUp()");
                KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(KeyguardWallpaperEventHandler.this, 836, null, -1);
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                Log.d("KeyguardWallpaperEventHandler", "onStartedGoingToSleep())");
                KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(KeyguardWallpaperEventHandler.this, 833, null, -1);
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                Log.d("KeyguardWallpaperEventHandler", "onStartedWakingUp()");
                KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(KeyguardWallpaperEventHandler.this, 834, null, -1);
            }
        };
        this.mWakefulnessObserver = r7;
        ?? r8 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperEventHandler.4
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (uri == null) {
                    Log.d("KeyguardWallpaperEventHandler", "onChanged: uri is null. Return!");
                    return;
                }
                Log.d("KeyguardWallpaperEventHandler", "onChanged: uri = " + uri.toString());
                boolean equals = uri.equals(Settings.System.getUriFor("ultra_powersaving_mode"));
                KeyguardWallpaperEventHandler keyguardWallpaperEventHandler = KeyguardWallpaperEventHandler.this;
                if (equals || uri.equals(Settings.System.getUriFor("minimal_battery_use"))) {
                    boolean isUltraPowerSavingMode = keyguardWallpaperEventHandler.mSettingsHelper.isUltraPowerSavingMode();
                    if (WallpaperUtils.mIsUltraPowerSavingMode != isUltraPowerSavingMode) {
                        WallpaperUtils.mIsUltraPowerSavingMode = isUltraPowerSavingMode;
                        KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, VolteConstants.ErrorCode.DECLINE, null, -1);
                        return;
                    }
                    return;
                }
                if (uri.equals(Settings.System.getUriFor("emergency_mode"))) {
                    boolean isEmergencyMode = keyguardWallpaperEventHandler.mSettingsHelper.isEmergencyMode();
                    if (WallpaperUtils.mIsEmergencyMode != isEmergencyMode) {
                        WallpaperUtils.mIsEmergencyMode = isEmergencyMode;
                        KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, 602, null, -1);
                        return;
                    }
                    return;
                }
                if (uri.equals(Settings.System.getUriFor("lockscreen_wallpaper"))) {
                    KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, VpnErrorValues.ERROR_USB_TETHERING_FAILED, Boolean.FALSE, -1);
                    return;
                }
                if (uri.equals(Settings.System.getUriFor("lockscreen_wallpaper_sub"))) {
                    KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, VpnErrorValues.ERROR_USB_TETHERING_FAILED, Boolean.TRUE, -1);
                    return;
                }
                if (uri.equals(Settings.System.getUriFor("lock_adaptive_color"))) {
                    KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, 903, null, -1);
                    return;
                }
                if (uri.equals(Settings.System.getUriFor("lock_adaptive_color_sub"))) {
                    KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, 907, null, -1);
                    return;
                }
                if (uri.equals(Settings.System.getUriFor("lockscreen_wallpaper_transparent"))) {
                    KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, 904, Boolean.FALSE, -1);
                    return;
                }
                if (uri.equals(Settings.System.getUriFor("sub_display_lockscreen_wallpaper_transparency"))) {
                    KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, 904, Boolean.TRUE, -1);
                    return;
                }
                if (uri.equals(Settings.System.getUriFor("wallpapertheme_state"))) {
                    KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, 733, null, -1);
                } else if (uri.equals(Settings.System.getUriFor("lock_screen_allow_rotation"))) {
                    KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, 905, null, -1);
                } else {
                    Log.d("KeyguardWallpaperEventHandler", "onChanged: Unhandled uri.");
                }
            }
        };
        this.mSettingsListener = r8;
        ?? r9 = new DesktopManager.Callback() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperEventHandler.5
            @Override // com.android.systemui.util.DesktopManager.Callback
            public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                Log.d("KeyguardWallpaperEventHandler", "onDesktopModeStateChanged: " + semDesktopModeState);
                if (semDesktopModeState.getDisplayType() != 101) {
                    return;
                }
                int enabled = semDesktopModeState.getEnabled();
                KeyguardWallpaperEventHandler keyguardWallpaperEventHandler = KeyguardWallpaperEventHandler.this;
                if (enabled == 4 && semDesktopModeState.getState() == 50) {
                    KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, 1000, Boolean.TRUE, -1);
                } else if (semDesktopModeState.getEnabled() == 2 && semDesktopModeState.getState() == 50) {
                    KeyguardWallpaperEventHandler.m2731$$Nest$msendMessage(keyguardWallpaperEventHandler, 1000, Boolean.FALSE, -1);
                }
            }
        };
        this.mDesktopCallback = r9;
        this.mContext = context;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
        wakefulnessLifecycle.addObserver(r7);
        this.mSettingsHelper = settingsHelper;
        settingsHelper.registerCallback(r8, uriArr);
        if (LsRune.WALLPAPER_DESKTOP_STANDALONE_MODE_WALLPAPER) {
            ((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).registerCallback(r9);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.theme.themecenter.THEME_APPLY_START");
        intentFilter.addAction("com.samsung.android.theme.themecenter.THEME_APPLY");
        intentFilter.addAction("com.samsung.android.theme.themecenter.THEME_REAPPLY");
        context.registerReceiver(r4, intentFilter, 2);
        this.mOccluded = keyguardUpdateMonitor.mKeyguardOccluded;
    }
}
