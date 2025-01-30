package com.android.server.biometrics.sensors.face;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SemStatusBarManager;
import android.app.SemWallpaperColors;
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
import com.android.server.biometrics.Utils;

/* loaded from: classes.dex */
public class SemFaceBrightManager {
    public static SemFaceBrightManager SInstance;
    public Context mContext;
    public DisplayManager mDisplayManager;
    public BroadcastReceiver mNotificationActionReceiver;
    public PowerManager mPowerManager;
    public WallpaperManager mWallpaperManager;
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
    public Handler mHandlerBg = new Handler(SemFaceMainThread.get().getLooper()) { // from class: com.android.server.biometrics.sensors.face.SemFaceBrightManager.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 2) {
                Slog.i("SemFaceBrightManager", "handleMessage: MSG_BRIGHTNESS_LEVEL_UP " + SemFaceBrightManager.this.mScreenFlashBrightnessLevelUpCorrected);
                SemFaceBrightManager semFaceBrightManager = SemFaceBrightManager.this;
                semFaceBrightManager.setBrightness(semFaceBrightManager.mScreenFlashBrightnessLevelUpCorrected);
                SemFaceBrightManager.this.mHandlerBg.sendEmptyMessageDelayed(5, 500L);
                return;
            }
            if (i == 5) {
                Slog.i("SemFaceBrightManager", "handleMessage: MSG_BRIGHTNESS_READY_TO_SET_MAX");
                SemFaceBrightManager.this.mIsReadyToSetMaxBrightness = true;
            } else {
                Slog.w("SemFaceBrightManager", "Unknown message:" + message.what);
            }
        }
    };

    public static synchronized SemFaceBrightManager getInstance(Context context) {
        SemFaceBrightManager semFaceBrightManager;
        synchronized (SemFaceBrightManager.class) {
            if (SInstance == null) {
                SInstance = new SemFaceBrightManager(context);
            }
            semFaceBrightManager = SInstance;
        }
        return semFaceBrightManager;
    }

    public SemFaceBrightManager(Context context) {
        this.mContext = context;
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mDisplayManager = (DisplayManager) this.mContext.getSystemService("display");
        this.mWallpaperManager = (WallpaperManager) this.mContext.getSystemService(WallpaperManager.class);
    }

    public boolean isNeededSetBrightness() {
        return this.mPowerManager.getCurrentBrightness(false) < ((float) this.mScreenFlashBrightnessLevelMaxCorrected);
    }

    public void startBrightness(int i) {
        int currentBrightness = (int) this.mPowerManager.getCurrentBrightness(false);
        this.mHandlerBg.removeMessages(2);
        this.mHandlerBg.removeMessages(5);
        this.mIsReadyToSetMaxBrightness = false;
        try {
            if (Utils.isFlipFolded(this.mContext)) {
                this.mIsScreenAutoBrightnessOn = Settings.System.getInt(this.mContext.getContentResolver(), "sub_screen_brightness_mode") == 1;
            } else {
                this.mIsScreenAutoBrightnessOn = Settings.System.getInt(this.mContext.getContentResolver(), "screen_brightness_mode") == 1;
            }
        } catch (Exception e) {
            Slog.w("SemFaceBrightManager", "startBrightness : ", e);
        }
        initBrightness(currentBrightness);
        if (this.mIsScreenAutoBrightnessOn) {
            setBrightness(this.mScreenFlashBrightnessStartValueCorrected);
            this.mHandlerBg.sendEmptyMessageDelayed(2, 2000L);
            if (currentBrightness < this.mScreenFlashBrightnessGuideLevel) {
                showNotificationIfNeed(i);
            }
        } else if (currentBrightness < this.mScreenFlashBrightnessLevelUpCorrected) {
            if (currentBrightness < this.mScreenFlashBrightnessGuideLevel) {
                setBrightness(this.mScreenFlashBrightnessStartValueCorrected);
                showNotificationIfNeed(i);
            }
            this.mHandlerBg.sendEmptyMessageDelayed(2, 2000L);
        } else if (currentBrightness >= this.mScreenFlashBrightnessLevelMaxCorrected) {
            return;
        } else {
            this.mHandlerBg.sendEmptyMessageDelayed(5, 500L);
        }
        this.mIsSetBrightness = true;
    }

    public final void initBrightness(int i) {
        if (this.mScreenFlashBrightnessStartValue == -1) {
            int convertToBrightness = this.mDisplayManager.convertToBrightness(54.0f);
            int convertToBrightness2 = this.mDisplayManager.convertToBrightness(77.0f);
            int convertToBrightness3 = this.mDisplayManager.convertToBrightness(100.0f);
            if (convertToBrightness > 0 && convertToBrightness2 > 0 && convertToBrightness3 > 0) {
                this.mScreenFlashBrightnessStartValue = convertToBrightness;
                this.mScreenFlashBrightnessLevelUp = convertToBrightness2;
                this.mScreenFlashBrightnessLevelMax = convertToBrightness3;
            } else {
                this.mScreenFlashBrightnessStartValue = 70;
                this.mScreenFlashBrightnessLevelUp = 100;
                this.mScreenFlashBrightnessLevelMax = 130;
            }
        }
        boolean isBlackWallpaper = isBlackWallpaper();
        if (isBlackWallpaper) {
            int i2 = this.mScreenFlashBrightnessStartValue;
            int i3 = this.mScreenFlashBrightnessLevelMax;
            if (i2 > i3 - 20) {
                this.mScreenFlashBrightnessStartValueCorrected = i2;
            } else {
                this.mScreenFlashBrightnessStartValueCorrected = i3 - 20;
            }
            int i4 = this.mScreenFlashBrightnessLevelUp;
            if (i4 > i3 - 10) {
                this.mScreenFlashBrightnessLevelUpCorrected = i4;
            } else {
                this.mScreenFlashBrightnessLevelUpCorrected = i3 - 10;
            }
            this.mScreenFlashBrightnessLevelMaxCorrected = i3;
        } else {
            this.mScreenFlashBrightnessStartValueCorrected = this.mScreenFlashBrightnessStartValue;
            this.mScreenFlashBrightnessLevelUpCorrected = this.mScreenFlashBrightnessLevelUp;
            this.mScreenFlashBrightnessLevelMaxCorrected = this.mScreenFlashBrightnessLevelMax;
        }
        this.mScreenFlashBrightnessGuideLevel = this.mScreenFlashBrightnessStartValueCorrected / 2;
        Slog.i("SemFaceBrightManager", "initBrightness : " + this.mScreenFlashBrightnessStartValue + ", " + this.mScreenFlashBrightnessLevelUp + ", " + this.mScreenFlashBrightnessLevelMax + " : " + i + ", auto = " + this.mIsScreenAutoBrightnessOn + ", blackWallpaper = " + isBlackWallpaper + ", guide = " + this.mScreenFlashBrightnessGuideLevel);
    }

    public final boolean isBlackWallpaper() {
        WallpaperManager wallpaperManager = this.mWallpaperManager;
        if (wallpaperManager == null) {
            Slog.e("SemFaceBrightManager", "mWallpaperManager is null");
            return true;
        }
        SemWallpaperColors semGetWallpaperColors = wallpaperManager.semGetWallpaperColors(2);
        if (semGetWallpaperColors == null) {
            Slog.e("SemFaceBrightManager", "colors is null");
            return true;
        }
        SemWallpaperColors.Item item = semGetWallpaperColors.get(512L);
        SemWallpaperColors.Item item2 = semGetWallpaperColors.get(32L);
        SemWallpaperColors.Item item3 = semGetWallpaperColors.get(64L);
        SemWallpaperColors.Item item4 = semGetWallpaperColors.get(128L);
        if (item == null || item2 == null || item3 == null || item4 == null) {
            Slog.e("SemFaceBrightManager", "items are null");
            return true;
        }
        int fontColor = item.getFontColor();
        int fontColor2 = item2.getFontColor();
        int fontColor3 = item3.getFontColor();
        int fontColor4 = item4.getFontColor();
        boolean z = (fontColor == 1 || fontColor2 == 1 || fontColor3 == 1 || fontColor4 == 1) ? false : true;
        Slog.i("SemFaceBrightManager", "isBlackWallpaper : " + fontColor + ", " + fontColor2 + ", " + fontColor3 + ", " + fontColor4);
        return z;
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

    public void setBrightnessMax() {
        if (this.mIsReadyToSetMaxBrightness) {
            setBrightness(this.mScreenFlashBrightnessLevelMaxCorrected);
            this.mIsReadyToSetMaxBrightness = false;
        }
    }

    public void restoreBrightness() {
        if (this.mIsSetBrightness) {
            this.mIsSetBrightness = false;
            this.mHandlerBg.removeMessages(2);
            this.mHandlerBg.removeMessages(5);
            boolean isFlipFolded = Utils.isFlipFolded(this.mContext);
            if (this.mIsScreenAutoBrightnessOn) {
                this.mPowerManager.semSetAutoBrightnessLimit(-1, -1);
            } else {
                this.mDisplayManager.setTemporaryBrightness(isFlipFolded ? 1 : 0, -1, false);
            }
            Slog.i("SemFaceBrightManager", "restoreBrightness, " + (isFlipFolded ? 1 : 0));
        }
    }

    public final void showNotificationIfNeed(int i) {
        if (isAlreadyGuided(this.mContext, i)) {
            return;
        }
        Slog.i("SemFaceBrightManager", "showNotificationIfNeed " + i);
        setFirstGuide(this.mContext, i);
        registerBroadcastForNotificationAction();
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        notificationManager.createNotificationChannel(new NotificationChannel("FaceServiceScreenFlashNotificationChannel", this.mContext.getString(17042740), 4));
        notificationManager.notifyAsUser("FaceServiceScreenFlash", 1, getNotification(this.mContext, i), UserHandle.of(i));
    }

    public final Notification getNotification(Context context, int i) {
        return new Notification.Builder(context, "FaceServiceScreenFlashNotificationChannel").setSmallIcon(R.drawable.text_cursor_holo_dark).setSubText(context.getString(17042740)).setContentTitle(context.getString(17042742)).setContentText(context.getString(17042741)).setOnlyAlertOnce(true).setLocalOnly(true).setOngoing(true).setCategory(context.getString(17042740)).setContentIntent(getPendingIntentForAction(context, "settings", 1, i)).addAction(getCloseNotificationAction(context, i)).addAction(getGoToSettingsNotificationAction(context, i)).setStyle(new Notification.BigTextStyle().bigText(context.getString(17042741))).setColor(context.getColor(R.color.system_notification_accent_color)).build();
    }

    public final void registerBroadcastForNotificationAction() {
        if (this.mNotificationActionReceiver == null) {
            this.mNotificationActionReceiver = new BroadcastReceiver() { // from class: com.android.server.biometrics.sensors.face.SemFaceBrightManager.2
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if ("com.samsung.android.server.biometrics.BIOMETRICS_FACE_NOTIFICATION_SCREEN_FLASH".contentEquals(intent.getAction())) {
                        int intExtra = intent.getIntExtra("user", -2);
                        String stringExtra = intent.getStringExtra("screen_flash_notification_action");
                        if (Utils.DEBUG) {
                            Slog.d("SemFaceBrightManager", "received: " + intExtra + ", " + stringExtra);
                        }
                        if (TextUtils.isEmpty(stringExtra)) {
                            return;
                        }
                        if ("close".contentEquals(stringExtra)) {
                            SemFaceBrightManager.this.cancelNotification(intExtra);
                        } else if ("settings".contentEquals(stringExtra)) {
                            SemFaceBrightManager.this.handleGoToSettingAction(intExtra);
                        }
                        Utils.unregisterBroadcast(SemFaceBrightManager.this.mContext, SemFaceBrightManager.this.mNotificationActionReceiver);
                        SemFaceBrightManager.this.mNotificationActionReceiver = null;
                    }
                }
            };
            Utils.registerBroadcastAsUser(this.mContext, this.mNotificationActionReceiver, new IntentFilter("com.samsung.android.server.biometrics.BIOMETRICS_FACE_NOTIFICATION_SCREEN_FLASH"), UserHandle.ALL, SemFaceMainThread.get().getHandler());
        }
    }

    public final boolean isAlreadyGuided(Context context, int i) {
        return Settings.Secure.getIntForUser(context.getContentResolver(), "face_screen_flash_guideline_displayed", -1, i) >= 0;
    }

    public final void setFirstGuide(Context context, int i) {
        Settings.Secure.putIntForUser(context.getContentResolver(), "face_screen_flash_guideline_displayed", 0, i);
    }

    public final Notification.Action getCloseNotificationAction(Context context, int i) {
        return new Notification.Action.Builder((Icon) null, context.getString(17042604), getPendingIntentForAction(context, "close", 0, i)).build();
    }

    public final Notification.Action getGoToSettingsNotificationAction(Context context, int i) {
        return new Notification.Action.Builder((Icon) null, context.getString(17042605), getPendingIntentForAction(context, "settings", 1, i)).build();
    }

    public final void cancelNotification(int i) {
        ((NotificationManager) this.mContext.getSystemService("notification")).cancelAsUser("FaceServiceScreenFlash", 1, UserHandle.of(i));
    }

    public final void handleGoToSettingAction(int i) {
        cancelNotification(i);
        closeNotificationPanel();
        Intent intent = new Intent("android.settings.FACE_SETTINGS");
        intent.setPackage("com.android.settings");
        try {
            this.mContext.startActivityAsUser(intent, UserHandle.of(i));
        } catch (Exception e) {
            Slog.w("SemFaceBrightManager", "handleGoToSettingAction: " + e.getMessage());
        }
    }

    public final void closeNotificationPanel() {
        try {
            ((SemStatusBarManager) this.mContext.getSystemService("sem_statusbar")).collapsePanels();
        } catch (Exception e) {
            Slog.w("SemFaceBrightManager", "closeNotificationPanel: " + e.getMessage());
        }
    }

    public final PendingIntent getPendingIntentForAction(Context context, String str, int i, int i2) {
        Intent intent = new Intent("com.samsung.android.server.biometrics.BIOMETRICS_FACE_NOTIFICATION_SCREEN_FLASH");
        intent.putExtra("screen_flash_notification_action", str);
        intent.putExtra("user", i2);
        return PendingIntent.getBroadcastAsUser(context, i, intent, 67108864, UserHandle.of(i2));
    }
}
