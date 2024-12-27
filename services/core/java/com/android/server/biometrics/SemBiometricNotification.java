package com.android.server.biometrics;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemBiometricNotification {
    public final int mAuthenticatorType;
    public final Context mContext;
    public int mIcon;
    public String mMessage;
    public final NotificationManager mNotificationManager;
    public final String mNotificationTag;
    public final String mPackageName;
    public String mTitle;

    public SemBiometricNotification(Context context, String str, int i) {
        this.mContext = context;
        this.mAuthenticatorType = i;
        this.mNotificationManager = (NotificationManager) context.getSystemService("notification");
        this.mPackageName = str;
        this.mNotificationTag = AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, "BackgroundBiometricsNotification_", "_", str);
    }

    public final void cancelNotification() {
        StringBuilder sb = new StringBuilder("cancelNotification start, ");
        String str = this.mNotificationTag;
        DeviceIdleController$$ExternalSyntheticOutline0.m(sb, str, "SemBiometricNotification");
        this.mNotificationManager.cancelAsUser(str, this.mAuthenticatorType, UserHandle.CURRENT);
    }

    public final void postNotification(Intent intent) {
        ApplicationInfo applicationInfo;
        StringBuilder sb = new StringBuilder("postNotification start, ");
        String str = this.mNotificationTag;
        DeviceIdleController$$ExternalSyntheticOutline0.m(sb, str, "SemBiometricNotification");
        PackageManager packageManager = this.mContext.getPackageManager();
        String str2 = this.mPackageName;
        try {
            applicationInfo = packageManager.getApplicationInfo(str2, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            applicationInfo = null;
        }
        if (applicationInfo != null) {
            str2 = packageManager.getApplicationLabel(applicationInfo).toString();
        }
        int i = this.mAuthenticatorType;
        if (i == 2) {
            this.mTitle = this.mContext.getString(17042949);
            this.mMessage = this.mContext.getString(17042948, str2);
            this.mIcon = 17304259;
        } else if (i == 8) {
            this.mTitle = this.mContext.getString(17042945);
            this.mMessage = this.mContext.getString(17042944, str2);
            this.mIcon = 17304258;
        } else {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "Unknown authenticator type, ", "SemBiometricNotification");
        }
        if (TextUtils.isEmpty(this.mTitle) || TextUtils.isEmpty(this.mMessage)) {
            Slog.d("SemBiometricNotification", "postNotification: No content title or message");
            return;
        }
        try {
            this.mNotificationManager.notifyAsUser(str, i, new Notification.Builder(this.mContext, SystemNotificationChannels.SECURITY).setOngoing(true).setContentTitle(this.mTitle).setContentText(this.mMessage).setSmallIcon(this.mIcon).setCategory("sys").setColor(this.mContext.getColor(R.color.system_notification_accent_color)).setStyle(new Notification.BigTextStyle().bigText(this.mMessage)).addAction(new Notification.Action.Builder((Icon) null, this.mContext.getString(R.string.cancel), PendingIntent.getBroadcast(this.mContext, 0, intent, 1140850688)).build()).build(), UserHandle.CURRENT);
        } catch (Exception e) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("postNotification: "), "SemBiometricNotification");
        }
    }
}
