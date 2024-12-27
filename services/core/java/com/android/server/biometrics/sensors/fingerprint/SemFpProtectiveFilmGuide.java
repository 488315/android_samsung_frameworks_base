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

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.Utils;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

public final class SemFpProtectiveFilmGuide
        implements SemFpEnrollmentListener, SemFpAuthenticationListener {
    static final int BAD_QUALITY_COUNT_GUIDED = -1;
    static final int BAD_QUALITY_COUNT_THRESHOLDS = 30;
    static final String NOTIFICATION_ACTION_CLOSE_BUTTON = "close";
    static final String NOTIFICATION_ACTION_KEY = "protective_film_notification_action";
    static final String NOTIFICATION_ACTION_SETTINGS_BUTTON = "settings";
    static final String NOTIFICATION_ACTION_USER = "user";
    static final String NOTIFICATION_INTENT_ACTION_NAME =
            "com.samsung.android.server.biometrics.fingerprint.BIOMETRICS_NOTIFICATION_PF_GUIDE";
    public final SparseIntArray mBadQualityCount = new SparseIntArray();
    public final Context mContext;
    public final Injector mInjector;
    BroadcastReceiver mNotificationActionReceiver;
    public final ServiceProvider mServiceProvider;

    public class Injector {
        public static String getContentText(Context context) {
            return context.getString(17042952)
                    + "\n• "
                    + context.getString(17042953)
                    + "\n• "
                    + context.getString(17042951)
                    + "\n• "
                    + context.getString(17042950);
        }
    }

    public SemFpProtectiveFilmGuide(
            Context context, ServiceProvider serviceProvider, Injector injector) {
        this.mContext = context;
        this.mServiceProvider = serviceProvider;
        this.mInjector = injector;
    }

    public final void cancelNotification(int i) {
        ((NotificationManager) this.mContext.getSystemService("notification"))
                .cancelAsUser("FingerprintServiceProtectiveFilm", 1, UserHandle.of(i));
    }

    public int getBadQualityCount(int i) {
        return this.mBadQualityCount.get(i);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener
    public final void onAuthenticationAcquire(int i, int i2, int i3) {
        if (i2 == 1 || (i2 == 6 && i3 == 1003)) {
            int i4 = this.mBadQualityCount.get(i, 0);
            if (Utils.DEBUG) {
                StringBuilder m =
                        ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "onAcquire[", "]: ", ", ");
                m.append(i3);
                m.append(", ");
                m.append(i4);
                Slog.d("SemFpProtectiveFilmGuide", m.toString());
            }
            if (i4 == -1) {
                return;
            }
            int i5 = i4 + 1;
            this.mBadQualityCount.put(i, i5);
            if (i5 == 30) {
                Context context = this.mContext;
                this.mInjector.getClass();
                if (Settings.Secure.getIntForUser(
                                context.getContentResolver(),
                                "fingerprint_protective_film_guideline_displayed",
                                -1,
                                i)
                        < 0) {
                    if (this.mNotificationActionReceiver == null) {
                        this.mNotificationActionReceiver =
                                new BroadcastReceiver() { // from class:
                                    // com.android.server.biometrics.sensors.fingerprint.SemFpProtectiveFilmGuide.1
                                    @Override // android.content.BroadcastReceiver
                                    public final void onReceive(Context context2, Intent intent) {
                                        if (SemFpProtectiveFilmGuide.NOTIFICATION_INTENT_ACTION_NAME
                                                .contentEquals(intent.getAction())) {
                                            int intExtra =
                                                    intent.getIntExtra(
                                                            SemFpProtectiveFilmGuide
                                                                    .NOTIFICATION_ACTION_USER,
                                                            -2);
                                            String stringExtra =
                                                    intent.getStringExtra(
                                                            SemFpProtectiveFilmGuide
                                                                    .NOTIFICATION_ACTION_KEY);
                                            if (Utils.DEBUG) {
                                                Slog.d(
                                                        "SemFpProtectiveFilmGuide",
                                                        "received: "
                                                                + intExtra
                                                                + ", "
                                                                + stringExtra);
                                            }
                                            if (TextUtils.isEmpty(stringExtra)) {
                                                return;
                                            }
                                            if (SemFpProtectiveFilmGuide
                                                    .NOTIFICATION_ACTION_CLOSE_BUTTON
                                                    .contentEquals(stringExtra)) {
                                                SemFpProtectiveFilmGuide.this.cancelNotification(
                                                        intExtra);
                                                return;
                                            }
                                            if (SemFpProtectiveFilmGuide
                                                    .NOTIFICATION_ACTION_SETTINGS_BUTTON
                                                    .contentEquals(stringExtra)) {
                                                SemFpProtectiveFilmGuide semFpProtectiveFilmGuide =
                                                        SemFpProtectiveFilmGuide.this;
                                                semFpProtectiveFilmGuide.cancelNotification(
                                                        intExtra);
                                                try {
                                                    ((SemStatusBarManager)
                                                                    semFpProtectiveFilmGuide
                                                                            .mContext
                                                                            .getSystemService(
                                                                                    "sem_statusbar"))
                                                            .collapsePanels();
                                                } catch (Exception e) {
                                                    MagnificationConnectionManager$$ExternalSyntheticOutline0
                                                            .m(
                                                                    e,
                                                                    new StringBuilder(
                                                                            "closeNotificationPanel:"
                                                                                + " "),
                                                                    "SemFpProtectiveFilmGuide");
                                                }
                                                try {
                                                    semFpProtectiveFilmGuide.mContext
                                                            .startActivityAsUser(
                                                                    ExplicitHealthCheckController$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    "android.settings.FINGERPRINT_SETTINGS",
                                                                                    KnoxCustomManagerService
                                                                                            .SETTING_PKG_NAME),
                                                                    UserHandle.of(intExtra));
                                                } catch (Exception e2) {
                                                    MagnificationConnectionManager$$ExternalSyntheticOutline0
                                                            .m(
                                                                    e2,
                                                                    new StringBuilder(
                                                                            "handleGoToSettingAction:"
                                                                                + " "),
                                                                    "SemFpProtectiveFilmGuide");
                                                }
                                            }
                                        }
                                    }
                                };
                        Utils.registerBroadcastAsUser(
                                this.mContext,
                                this.mNotificationActionReceiver,
                                new IntentFilter(NOTIFICATION_INTENT_ACTION_NAME),
                                UserHandle.ALL,
                                BiometricHandlerProvider.sBiometricHandlerProvider
                                        .getFingerprintHandler());
                    }
                    NotificationManager notificationManager =
                            (NotificationManager) this.mContext.getSystemService("notification");
                    notificationManager.createNotificationChannel(
                            new NotificationChannel(
                                    "FingerprintProtectiveFilmNotificationChannel",
                                    this.mContext.getString(17042949),
                                    4));
                    Context context2 = this.mContext;
                    Notification.Builder category =
                            new Notification.Builder(
                                            context2,
                                            "FingerprintProtectiveFilmNotificationChannel")
                                    .setSmallIcon(17304259)
                                    .setSubText(context2.getString(17042949))
                                    .setContentTitle(context2.getString(17042954))
                                    .setContentText(Injector.getContentText(context2))
                                    .setOnlyAlertOnce(true)
                                    .setLocalOnly(true)
                                    .setOngoing(true)
                                    .setCategory(context2.getString(17042949));
                    String string = context2.getString(17042803);
                    Intent intent = new Intent(NOTIFICATION_INTENT_ACTION_NAME);
                    intent.putExtra(NOTIFICATION_ACTION_KEY, NOTIFICATION_ACTION_CLOSE_BUTTON);
                    intent.putExtra(NOTIFICATION_ACTION_USER, i);
                    Notification.Builder addAction =
                            category.addAction(
                                    new Notification.Action.Builder(
                                                    (Icon) null,
                                                    string,
                                                    PendingIntent.getBroadcastAsUser(
                                                            context2,
                                                            0,
                                                            intent,
                                                            67108864,
                                                            UserHandle.of(i)))
                                            .build());
                    String string2 = context2.getString(17042804);
                    Intent intent2 = new Intent(NOTIFICATION_INTENT_ACTION_NAME);
                    intent2.putExtra(NOTIFICATION_ACTION_KEY, NOTIFICATION_ACTION_SETTINGS_BUTTON);
                    intent2.putExtra(NOTIFICATION_ACTION_USER, i);
                    notificationManager.notifyAsUser(
                            "FingerprintServiceProtectiveFilm",
                            1,
                            addAction
                                    .addAction(
                                            new Notification.Action.Builder(
                                                            (Icon) null,
                                                            string2,
                                                            PendingIntent.getBroadcastAsUser(
                                                                    context2,
                                                                    1,
                                                                    intent2,
                                                                    67108864,
                                                                    UserHandle.of(i)))
                                                    .build())
                                    .setStyle(
                                            new Notification.BigTextStyle()
                                                    .bigText(Injector.getContentText(context2)))
                                    .setColor(
                                            context2.getColor(
                                                    R.color.system_notification_accent_color))
                                    .build(),
                            UserHandle.of(i));
                    Settings.Secure.putIntForUser(
                            this.mContext.getContentResolver(),
                            "fingerprint_protective_film_guideline_displayed",
                            0,
                            i);
                }
                this.mBadQualityCount.put(i, -1);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpEnrollmentListener
    public final void onEnrollFinished(int i, int i2) {
        if (Utils.DEBUG) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i2, "onEnrollFinished[", "]", "SemFpProtectiveFilmGuide");
        }
        Context context = this.mContext;
        this.mInjector.getClass();
        if (Settings.Secure.getIntForUser(
                        context.getContentResolver(),
                        "fingerprint_protective_film_guideline_displayed",
                        -1,
                        i2)
                >= 0) {
            this.mBadQualityCount.put(i2, -1);
        } else {
            this.mBadQualityCount.put(i2, 0);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpEnrollmentListener
    public final void onEnrollStarted(int i, int i2) {
        if (Utils.DEBUG) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i2, "onEnrollStarted[", "]", "SemFpProtectiveFilmGuide");
        }
        cancelNotification(i2);
    }
}
