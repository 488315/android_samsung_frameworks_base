package com.android.server.biometrics.sensors.fingerprint;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SemStatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseIntArray;
import com.android.server.biometrics.Utils;

/* loaded from: classes.dex */
public class SemFpProtectiveFilmGuide implements SemFpEnrollmentListener, SemFpAuthenticationListener {
    static final int BAD_QUALITY_COUNT_GUIDED = -1;
    static final int BAD_QUALITY_COUNT_THRESHOLDS = 30;
    static final String NOTIFICATION_ACTION_CLOSE_BUTTON = "close";
    static final String NOTIFICATION_ACTION_KEY = "protective_film_notification_action";
    static final String NOTIFICATION_ACTION_SETTINGS_BUTTON = "settings";
    static final String NOTIFICATION_ACTION_USER = "user";
    static final String NOTIFICATION_INTENT_ACTION_NAME = "com.samsung.android.server.biometrics.fingerprint.BIOMETRICS_NOTIFICATION_PF_GUIDE";
    public static final String TAG = "SemFpProtectiveFilmGuide";
    public final SparseIntArray mBadQualityCount;
    public final Context mContext;
    public final Injector mInjector;
    BroadcastReceiver mNotificationActionReceiver;
    public final ServiceProvider mServiceProvider;

    public final boolean isBadQualityEvent(int i, int i2) {
        if (i != 1) {
            return i == 6 && i2 == 1003;
        }
        return true;
    }

    public class Injector {
        public Notification getNotification(Context context, int i) {
            return new Notification.Builder(context, "FingerprintProtectiveFilmNotificationChannel").setSmallIcon(R.drawable.text_cursor_holo_light).setSubText(getSubText(context)).setContentTitle(getContentTitle(context)).setContentText(getContentText(context)).setOnlyAlertOnce(true).setLocalOnly(true).setOngoing(true).setCategory(getCategory(context)).addAction(getCloseNotificationAction(context, i)).addAction(getGoToSettingsNotificationAction(context, i)).setStyle(new Notification.BigTextStyle().bigText(getContentText(context))).setColor(context.getColor(R.color.system_notification_accent_color)).build();
        }

        public boolean isAlreadyGuided(Context context, int i) {
            return Settings.Secure.getIntForUser(context.getContentResolver(), "fingerprint_protective_film_guideline_displayed", -1, i) >= 0;
        }

        public void setGuideStateToNotificationDoneByAuthentication(Context context, int i) {
            Settings.Secure.putIntForUser(context.getContentResolver(), "fingerprint_protective_film_guideline_displayed", 0, i);
        }

        public final Notification.Action getCloseNotificationAction(Context context, int i) {
            return new Notification.Action.Builder((Icon) null, context.getString(17042604), getPendingIntentForAction(context, SemFpProtectiveFilmGuide.NOTIFICATION_ACTION_CLOSE_BUTTON, 0, i)).build();
        }

        public final Notification.Action getGoToSettingsNotificationAction(Context context, int i) {
            return new Notification.Action.Builder((Icon) null, context.getString(17042605), getPendingIntentForAction(context, SemFpProtectiveFilmGuide.NOTIFICATION_ACTION_SETTINGS_BUTTON, 1, i)).build();
        }

        public final PendingIntent getPendingIntentForAction(Context context, String str, int i, int i2) {
            Intent intent = new Intent(SemFpProtectiveFilmGuide.NOTIFICATION_INTENT_ACTION_NAME);
            intent.putExtra(SemFpProtectiveFilmGuide.NOTIFICATION_ACTION_KEY, str);
            intent.putExtra(SemFpProtectiveFilmGuide.NOTIFICATION_ACTION_USER, i2);
            return PendingIntent.getBroadcastAsUser(context, i, intent, 67108864, UserHandle.of(i2));
        }

        public final String getCategory(Context context) {
            return context.getString(17042744);
        }

        public final String getSubText(Context context) {
            return context.getString(17042744);
        }

        public final String getContentTitle(Context context) {
            return context.getString(17042749);
        }

        public final String getContentText(Context context) {
            return context.getString(17042747) + "\n• " + context.getString(17042748) + "\n• " + context.getString(17042746) + "\n• " + context.getString(17042745);
        }
    }

    public SemFpProtectiveFilmGuide(Context context, ServiceProvider serviceProvider) {
        this(context, serviceProvider, new Injector());
    }

    public SemFpProtectiveFilmGuide(Context context, ServiceProvider serviceProvider, Injector injector) {
        this.mContext = context;
        this.mServiceProvider = serviceProvider;
        this.mInjector = injector;
        this.mBadQualityCount = new SparseIntArray();
    }

    public void onUserRemoved(int i) {
        this.mBadQualityCount.delete(i);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener
    public void onAuthenticationAcquire(int i, int i2, int i3, int i4) {
        if (isBadQualityEvent(i3, i4)) {
            int i5 = this.mBadQualityCount.get(i2, 0);
            if (Utils.DEBUG) {
                Slog.d(TAG, "onAcquire[" + i2 + "]: " + i3 + ", " + i4 + ", " + i5);
            }
            if (i5 == -1) {
                return;
            }
            int i6 = i5 + 1;
            this.mBadQualityCount.put(i2, i6);
            if (i6 == 30) {
                if (!this.mInjector.isAlreadyGuided(this.mContext, i2)) {
                    showNotification(i2);
                    this.mInjector.setGuideStateToNotificationDoneByAuthentication(this.mContext, i2);
                }
                this.mBadQualityCount.put(i2, -1);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpEnrollmentListener
    public void onEnrollFinished(int i, int i2) {
        if (Utils.DEBUG) {
            Slog.d(TAG, "onEnrollFinished[" + i2 + "]");
        }
        if (this.mInjector.isAlreadyGuided(this.mContext, i2)) {
            this.mBadQualityCount.put(i2, -1);
        } else {
            this.mBadQualityCount.put(i2, 0);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpEnrollmentListener
    public void onEnrollStarted(int i, int i2) {
        if (Utils.DEBUG) {
            Slog.d(TAG, "onEnrollStarted[" + i2 + "]");
        }
        cancelNotification(i2);
    }

    public final void showNotification(int i) {
        registerBroadcastForNotificationAction();
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        notificationManager.createNotificationChannel(new NotificationChannel("FingerprintProtectiveFilmNotificationChannel", this.mContext.getString(17042744), 4));
        notificationManager.notifyAsUser("FingerprintServiceProtectiveFilm", 1, this.mInjector.getNotification(this.mContext, i), UserHandle.of(i));
    }

    public final void registerBroadcastForNotificationAction() {
        if (this.mNotificationActionReceiver == null) {
            this.mNotificationActionReceiver = new BroadcastReceiver() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpProtectiveFilmGuide.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (SemFpProtectiveFilmGuide.NOTIFICATION_INTENT_ACTION_NAME.contentEquals(intent.getAction())) {
                        int intExtra = intent.getIntExtra(SemFpProtectiveFilmGuide.NOTIFICATION_ACTION_USER, -2);
                        String stringExtra = intent.getStringExtra(SemFpProtectiveFilmGuide.NOTIFICATION_ACTION_KEY);
                        if (Utils.DEBUG) {
                            Slog.d(SemFpProtectiveFilmGuide.TAG, "received: " + intExtra + ", " + stringExtra);
                        }
                        if (TextUtils.isEmpty(stringExtra)) {
                            return;
                        }
                        if (SemFpProtectiveFilmGuide.NOTIFICATION_ACTION_CLOSE_BUTTON.contentEquals(stringExtra)) {
                            SemFpProtectiveFilmGuide.this.cancelNotification(intExtra);
                        } else if (SemFpProtectiveFilmGuide.NOTIFICATION_ACTION_SETTINGS_BUTTON.contentEquals(stringExtra)) {
                            SemFpProtectiveFilmGuide.this.handleGoToSettingAction(intExtra);
                        }
                    }
                }
            };
            Utils.registerBroadcastAsUser(this.mContext, this.mNotificationActionReceiver, new IntentFilter(NOTIFICATION_INTENT_ACTION_NAME), UserHandle.ALL, SemFpMainThread.get().getHandler());
        }
    }

    public final void cancelNotification(int i) {
        ((NotificationManager) this.mContext.getSystemService("notification")).cancelAsUser("FingerprintServiceProtectiveFilm", 1, UserHandle.of(i));
    }

    public final void handleGoToSettingAction(int i) {
        cancelNotification(i);
        closeNotificationPanel();
        Intent intent = new Intent("android.settings.FINGERPRINT_SETTINGS");
        intent.setPackage("com.android.settings");
        try {
            this.mContext.startActivityAsUser(intent, UserHandle.of(i));
        } catch (Exception e) {
            Slog.w(TAG, "handleGoToSettingAction: " + e.getMessage());
        }
    }

    public final void closeNotificationPanel() {
        try {
            ((SemStatusBarManager) this.mContext.getSystemService("sem_statusbar")).collapsePanels();
        } catch (Exception e) {
            Slog.w(TAG, "closeNotificationPanel: " + e.getMessage());
        }
    }

    public void startMonitoring() {
        this.mServiceProvider.semAddAuthenticationListener(this);
        this.mServiceProvider.semAddEnrollmentListener(this);
    }

    public void updateGuideStatus(int i) {
        if (this.mInjector.isAlreadyGuided(this.mContext, i)) {
            this.mBadQualityCount.put(i, -1);
        }
    }

    public int getBadQualityCount(int i) {
        return this.mBadQualityCount.get(i);
    }
}
