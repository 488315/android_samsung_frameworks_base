package com.android.server.biometrics.sensors.face;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SemStatusBarManager;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;

import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.Utils;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

public final class SemFaceBrightManager {
    public static SemFaceBrightManager SInstance;
    public final Context mContext;
    public final DisplayManager mDisplayManager;
    public AnonymousClass2 mNotificationActionReceiver;
    public final PowerManager mPowerManager;
    public final WallpaperManager mWallpaperManager;
    public int mScreenFlashBrightnessStartValue = -1;
    public int mScreenFlashBrightnessLevelUp = -1;
    public int mScreenFlashBrightnessLevelMax = -1;
    public int mScreenFlashBrightnessStartValueCorrected = -1;
    public int mScreenFlashBrightnessLevelUpCorrected = -1;
    public int mScreenFlashBrightnessLevelMaxCorrected = -1;
    public int mScreenFlashBrightnessGuideLevel = 35;
    public boolean mIsScreenAutoBrightnessOn = true;
    public boolean mIsSetBrightness = false;
    public boolean mIsReadyToSetMaxBrightness = false;
    public final AnonymousClass1 mHandlerBg =
            new Handler(
                    BiometricHandlerProvider.sBiometricHandlerProvider
                            .getFaceHandler()
                            .getLooper()) { // from class:
                                            // com.android.server.biometrics.sensors.face.SemFaceBrightManager.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i = message.what;
                    SemFaceBrightManager semFaceBrightManager = SemFaceBrightManager.this;
                    if (i == 2) {
                        SystemServiceManager$$ExternalSyntheticOutline0.m(
                                new StringBuilder("handleMessage: MSG_BRIGHTNESS_LEVEL_UP "),
                                semFaceBrightManager.mScreenFlashBrightnessLevelUpCorrected,
                                "SemFaceBrightManager");
                        semFaceBrightManager.setBrightness(
                                semFaceBrightManager.mScreenFlashBrightnessLevelUpCorrected);
                        semFaceBrightManager.mHandlerBg.sendEmptyMessageDelayed(5, 500L);
                    } else if (i != 5) {
                        HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                                new StringBuilder("Unknown message:"),
                                message.what,
                                "SemFaceBrightManager");
                    } else {
                        Slog.i(
                                "SemFaceBrightManager",
                                "handleMessage: MSG_BRIGHTNESS_READY_TO_SET_MAX");
                        semFaceBrightManager.mIsReadyToSetMaxBrightness = true;
                    }
                }
            };

    public SemFaceBrightManager(Context context) {
        this.mContext = context;
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        this.mWallpaperManager =
                (WallpaperManager) context.getSystemService(WallpaperManager.class);
    }

    public static synchronized SemFaceBrightManager getInstance(Context context) {
        SemFaceBrightManager semFaceBrightManager;
        synchronized (SemFaceBrightManager.class) {
            try {
                if (SInstance == null) {
                    SInstance = new SemFaceBrightManager(context);
                }
                semFaceBrightManager = SInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return semFaceBrightManager;
    }

    public static PendingIntent getPendingIntentForAction(
            Context context, String str, int i, int i2) {
        Intent intent =
                new Intent(
                        "com.samsung.android.server.biometrics.BIOMETRICS_FACE_NOTIFICATION_SCREEN_FLASH");
        intent.putExtra("screen_flash_notification_action", str);
        intent.putExtra("user", i2);
        return PendingIntent.getBroadcastAsUser(context, i, intent, 67108864, UserHandle.of(i2));
    }

    public final void setBrightness(int i) {
        boolean isFlipFolded = Utils.isFlipFolded(this.mContext);
        if (this.mIsScreenAutoBrightnessOn) {
            this.mPowerManager.setAutoBrightnessLimit(i, -1, true);
        } else {
            this.mDisplayManager.setTemporaryBrightness(isFlipFolded ? 1 : 0, i, true);
        }
        Slog.i("SemFaceBrightManager", "setBrightness : " + i + ", " + (isFlipFolded ? 1 : 0));
    }

    public final void showNotificationIfNeed(int i) {
        if (Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(),
                        "face_screen_flash_guideline_displayed",
                        -1,
                        i)
                >= 0) {
            return;
        }
        HermesService$3$$ExternalSyntheticOutline0.m(
                i, "showNotificationIfNeed ", "SemFaceBrightManager");
        Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(), "face_screen_flash_guideline_displayed", 0, i);
        if (this.mNotificationActionReceiver == null) {
            this.mNotificationActionReceiver =
                    new BroadcastReceiver() { // from class:
                                              // com.android.server.biometrics.sensors.face.SemFaceBrightManager.2
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if ("com.samsung.android.server.biometrics.BIOMETRICS_FACE_NOTIFICATION_SCREEN_FLASH"
                                    .contentEquals(intent.getAction())) {
                                int intExtra = intent.getIntExtra("user", -2);
                                String stringExtra =
                                        intent.getStringExtra("screen_flash_notification_action");
                                if (Utils.DEBUG) {
                                    Slog.d(
                                            "SemFaceBrightManager",
                                            "received: " + intExtra + ", " + stringExtra);
                                }
                                if (TextUtils.isEmpty(stringExtra)) {
                                    return;
                                }
                                if ("close".contentEquals(stringExtra)) {
                                    ((NotificationManager)
                                                    SemFaceBrightManager.this.mContext
                                                            .getSystemService("notification"))
                                            .cancelAsUser(
                                                    "FaceServiceScreenFlash",
                                                    1,
                                                    UserHandle.of(intExtra));
                                } else if ("settings".contentEquals(stringExtra)) {
                                    SemFaceBrightManager semFaceBrightManager =
                                            SemFaceBrightManager.this;
                                    ((NotificationManager)
                                                    semFaceBrightManager.mContext.getSystemService(
                                                            "notification"))
                                            .cancelAsUser(
                                                    "FaceServiceScreenFlash",
                                                    1,
                                                    UserHandle.of(intExtra));
                                    try {
                                        ((SemStatusBarManager)
                                                        semFaceBrightManager.mContext
                                                                .getSystemService("sem_statusbar"))
                                                .collapsePanels();
                                    } catch (Exception e) {
                                        MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                                                e,
                                                new StringBuilder("closeNotificationPanel: "),
                                                "SemFaceBrightManager");
                                    }
                                    try {
                                        semFaceBrightManager.mContext.startActivityAsUser(
                                                ExplicitHealthCheckController$$ExternalSyntheticOutline0
                                                        .m(
                                                                "android.settings.FACE_SETTINGS",
                                                                KnoxCustomManagerService
                                                                        .SETTING_PKG_NAME),
                                                UserHandle.of(intExtra));
                                    } catch (Exception e2) {
                                        MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                                                e2,
                                                new StringBuilder("handleGoToSettingAction: "),
                                                "SemFaceBrightManager");
                                    }
                                }
                                SemFaceBrightManager semFaceBrightManager2 =
                                        SemFaceBrightManager.this;
                                Utils.unregisterBroadcast(
                                        semFaceBrightManager2.mContext,
                                        semFaceBrightManager2.mNotificationActionReceiver);
                                SemFaceBrightManager.this.mNotificationActionReceiver = null;
                            }
                        }
                    };
            Utils.registerBroadcastAsUser(
                    this.mContext,
                    this.mNotificationActionReceiver,
                    new IntentFilter(
                            "com.samsung.android.server.biometrics.BIOMETRICS_FACE_NOTIFICATION_SCREEN_FLASH"),
                    UserHandle.ALL,
                    BiometricHandlerProvider.sBiometricHandlerProvider.getFaceHandler());
        }
        NotificationManager notificationManager =
                (NotificationManager) this.mContext.getSystemService("notification");
        notificationManager.createNotificationChannel(
                new NotificationChannel(
                        "FaceServiceScreenFlashNotificationChannel",
                        this.mContext.getString(17042945),
                        4));
        Context context = this.mContext;
        notificationManager.notifyAsUser(
                "FaceServiceScreenFlash",
                1,
                new Notification.Builder(context, "FaceServiceScreenFlashNotificationChannel")
                        .setSmallIcon(17304258)
                        .setSubText(context.getString(17042945))
                        .setContentTitle(context.getString(17042947))
                        .setContentText(context.getString(17042946))
                        .setOnlyAlertOnce(true)
                        .setLocalOnly(true)
                        .setOngoing(true)
                        .setCategory(context.getString(17042945))
                        .setContentIntent(getPendingIntentForAction(context, "settings", 1, i))
                        .addAction(
                                new Notification.Action.Builder(
                                                (Icon) null,
                                                context.getString(17042803),
                                                getPendingIntentForAction(context, "close", 0, i))
                                        .build())
                        .addAction(
                                new Notification.Action.Builder(
                                                (Icon) null,
                                                context.getString(17042804),
                                                getPendingIntentForAction(
                                                        context, "settings", 1, i))
                                        .build())
                        .setStyle(
                                new Notification.BigTextStyle()
                                        .bigText(context.getString(17042946)))
                        .setColor(context.getColor(R.color.system_notification_accent_color))
                        .build(),
                UserHandle.of(i));
    }
}
