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
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.util.SettingsHelper;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.function.Consumer;

public class KeyguardWallpaperEventHandler {
    private static final String ACTION_LAUNCH_FESTIVAL_WALLPAPER = "com.samsung.intent.action.LAUNCH_FESTIVAL_WALLPAPER";
    private static final String FESTIVAL_WALLPAPER_PACKAGE_NAME = "com.samsung.android.festivalwallpaper";
    private static final String PERMISSION_SET_FESTIVAL_WALLPAPER = "com.samsung.android.permission.SET_FESTIVAL_WALLPAPER";
    private static final String TAG = "KeyguardWallpaperEventHandler";
    private final Context mContext;
    private Consumer<Message> mEventConsumer;
    private final KeyguardUpdateMonitorCallback mInfoCallback;
    private final SettingsHelper mSettingsHelper;
    private SettingsHelper.OnChangedCallback mSettingsListener;
    private KeyguardUpdateMonitor mUpdateMonitor;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperEventHandler.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.samsung.android.theme.themecenter.THEME_APPLY_START".equals(action)) {
                Log.d(KeyguardWallpaperEventHandler.TAG, "onOpenThemeChangeStarted: packageName = " + intent.getStringExtra("packageName"));
                KeyguardWallpaperEventHandler.this.sendMessage(729, null, -1, -1);
                return;
            }
            if ("com.samsung.android.theme.themecenter.THEME_REAPPLY".equals(action)) {
                Log.d(KeyguardWallpaperEventHandler.TAG, "onOpenThemeReApply()");
                KeyguardWallpaperEventHandler.this.sendMessage(731, null, -1, -1);
            } else if ("com.samsung.android.theme.themecenter.THEME_APPLY".equals(action)) {
                Log.d(KeyguardWallpaperEventHandler.TAG, "onOpenThemeChanged()");
                KeyguardWallpaperEventHandler.this.sendMessage(730, null, -1, -1);
            }
        }
    };
    private Uri[] mSettingsValueList = {Settings.System.getUriFor(SettingsHelper.INDEX_MINIMAL_BATTERY_USE), Settings.System.getUriFor(SettingsHelper.INDEX_ULTRA_POWERSAVING_MODE), Settings.System.getUriFor(SettingsHelper.INDEX_EMERGENCY_MODE), Settings.System.getUriFor(SettingsHelper.INDEX_ADAPTIVE_COLOR_MODE), Settings.System.getUriFor(SettingsHelper.INDEX_ADAPTIVE_COLOR_MODE_SUB), Settings.System.getUriFor("lockscreen_wallpaper_transparent"), Settings.System.getUriFor("sub_display_lockscreen_wallpaper_transparency"), Settings.System.getUriFor("wallpapertheme_state"), Settings.System.getUriFor(SettingsHelper.INDEX_ROTATION_LOCK_SCREEN)};

    public KeyguardWallpaperEventHandler(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, WakefulnessLifecycle wakefulnessLifecycle, SettingsHelper settingsHelper) {
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperEventHandler.2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
                Log.d(KeyguardWallpaperEventHandler.TAG, "onBiometricError()");
                if (biometricSourceType == BiometricSourceType.FACE && i == 3) {
                    KeyguardWallpaperEventHandler.this.sendMessage(728, null, -1, -1);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onDlsViewModeChanged(int i) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onDlsViewModeChanged mode: ", KeyguardWallpaperEventHandler.TAG);
                KeyguardWallpaperEventHandler.this.sendMessage(732, null, i, -1);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
                Log.d(KeyguardWallpaperEventHandler.TAG, "onKeyguardBouncerFullyShowingChanged(), bouncer: " + z);
                KeyguardWallpaperEventHandler.this.sendMessage(724, Boolean.valueOf(z), -1, -1);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onPackageAdded(String str) {
                if (LsRune.WALLPAPER_FESTIVAL_WALLPAPER && KeyguardWallpaperEventHandler.FESTIVAL_WALLPAPER_PACKAGE_NAME.equals(str)) {
                    Intent intent = new Intent();
                    intent.setAction(KeyguardWallpaperEventHandler.ACTION_LAUNCH_FESTIVAL_WALLPAPER);
                    intent.setPackage(KeyguardWallpaperEventHandler.FESTIVAL_WALLPAPER_PACKAGE_NAME);
                    intent.addFlags(32);
                    KeyguardWallpaperEventHandler.this.mContext.sendBroadcast(intent, KeyguardWallpaperEventHandler.PERMISSION_SET_FESTIVAL_WALLPAPER);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitchComplete(int i) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onUserSwitchComplete(), userId: ", KeyguardWallpaperEventHandler.TAG);
                KeyguardWallpaperEventHandler.this.sendMessage(721, null, i, -1);
            }
        };
        this.mInfoCallback = keyguardUpdateMonitorCallback;
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperEventHandler.3
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (uri == null) {
                    Log.d(KeyguardWallpaperEventHandler.TAG, "onChanged: uri is null. Return!");
                    return;
                }
                Log.d(KeyguardWallpaperEventHandler.TAG, "onChanged: uri = " + uri.toString());
                boolean equals = uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_ULTRA_POWERSAVING_MODE));
                KeyguardWallpaperEventHandler keyguardWallpaperEventHandler = KeyguardWallpaperEventHandler.this;
                if (equals || uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_MINIMAL_BATTERY_USE))) {
                    boolean isUltraPowerSavingMode = keyguardWallpaperEventHandler.mSettingsHelper.isUltraPowerSavingMode();
                    if (WallpaperUtils.mIsUltraPowerSavingMode != isUltraPowerSavingMode) {
                        WallpaperUtils.mIsUltraPowerSavingMode = isUltraPowerSavingMode;
                        keyguardWallpaperEventHandler.sendMessage(VolteConstants.ErrorCode.DECLINE, null, -1, -1);
                        return;
                    }
                    return;
                }
                if (uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_EMERGENCY_MODE))) {
                    boolean isEmergencyMode = keyguardWallpaperEventHandler.mSettingsHelper.isEmergencyMode();
                    if (WallpaperUtils.mIsEmergencyMode != isEmergencyMode) {
                        WallpaperUtils.mIsEmergencyMode = isEmergencyMode;
                        keyguardWallpaperEventHandler.sendMessage(602, null, -1, -1);
                        return;
                    }
                    return;
                }
                if (uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_ADAPTIVE_COLOR_MODE))) {
                    keyguardWallpaperEventHandler.sendMessage(903, null, -1, -1);
                    return;
                }
                if (uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_ADAPTIVE_COLOR_MODE_SUB))) {
                    keyguardWallpaperEventHandler.sendMessage(907, null, -1, -1);
                    return;
                }
                if (uri.equals(Settings.System.getUriFor("lockscreen_wallpaper_transparent"))) {
                    keyguardWallpaperEventHandler.sendMessage(904, Boolean.FALSE, -1, -1);
                    return;
                }
                if (uri.equals(Settings.System.getUriFor("sub_display_lockscreen_wallpaper_transparency"))) {
                    keyguardWallpaperEventHandler.sendMessage(904, Boolean.TRUE, -1, -1);
                    return;
                }
                if (uri.equals(Settings.System.getUriFor("wallpapertheme_state"))) {
                    keyguardWallpaperEventHandler.sendMessage(733, null, -1, -1);
                } else if (uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_ROTATION_LOCK_SCREEN))) {
                    keyguardWallpaperEventHandler.sendMessage(905, null, -1, -1);
                } else {
                    Log.d(KeyguardWallpaperEventHandler.TAG, "onChanged: Unhandled uri.");
                }
            }
        };
        this.mContext = context;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
        this.mSettingsHelper = settingsHelper;
        settingsHelper.registerCallback(this.mSettingsListener, this.mSettingsValueList);
        registerOpenThemeChangeReceiver();
    }

    private void registerOpenThemeChangeReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.theme.themecenter.THEME_APPLY_START");
        intentFilter.addAction("com.samsung.android.theme.themecenter.THEME_APPLY");
        intentFilter.addAction("com.samsung.android.theme.themecenter.THEME_REAPPLY");
        this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessage(int i, Object obj, int i2, int i3) {
        StringBuilder sb = new StringBuilder("sendMessage(), what = ");
        sb.append(i);
        sb.append(" , obj = ");
        sb.append(obj);
        sb.append(" , arg1 = ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, i2, " , arg2 = ", i3, TAG);
        if (this.mEventConsumer != null) {
            Message message = new Message();
            message.what = i;
            if (obj != null) {
                message.obj = obj;
            }
            if (i2 != -1) {
                message.arg1 = i2;
            }
            if (i3 != -1) {
                message.arg2 = i3;
            }
            this.mEventConsumer.accept(message);
        }
    }

    public void setEventReceiver(Consumer<Message> consumer) {
        this.mEventConsumer = consumer;
    }
}
