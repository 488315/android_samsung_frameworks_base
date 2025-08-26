package android.debug;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.UserHandle;
import android.provider.Settings;
import com.android.internal.R;
import com.android.internal.notification.SystemNotificationChannels;
import java.io.IOException;

/* loaded from: classes.dex */
public final class AdbNotifications {
    private static final String ADB_NOTIFICATION_CHANNEL_ID_TV = "usbdevicemanager.adb.tv";

    public static Notification createNotification(Context context, byte b) throws Resources.NotFoundException, IOException {
        int i;
        int i2;
        Context context2;
        PendingIntent activityAsUser;
        Resources resources = context.getResources();
        if (b == 0) {
            i = R.string.adb_active_notification_title;
            i2 = R.string.adb_active_notification_message;
        } else {
            if (b != 1) {
                throw new IllegalArgumentException("createNotification called with unknown transport type=" + ((int) b));
            }
            i = R.string.adbwifi_active_notification_title;
            i2 = R.string.adbwifi_active_notification_message;
        }
        CharSequence text = resources.getText(i);
        CharSequence text2 = resources.getText(i2);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
        intent.addFlags(268468224);
        ResolveInfo resolveInfoResolveActivity = context.getPackageManager().resolveActivity(intent, 1048576);
        if (resolveInfoResolveActivity != null) {
            intent.setPackage(resolveInfoResolveActivity.activityInfo.packageName);
            context2 = context;
            activityAsUser = PendingIntent.getActivityAsUser(context2, 0, intent, 67108864, null, UserHandle.CURRENT);
        } else {
            context2 = context;
            activityAsUser = null;
        }
        return new Notification.Builder(context2, SystemNotificationChannels.DEVELOPER_IMPORTANT).setSmallIcon(R.drawable.stat_sys_adb).setWhen(0L).setOngoing(true).setTicker(text).setDefaults(0).setColor(context2.getColor(17170460)).setContentTitle(text).setContentText(text2).setContentIntent(activityAsUser).setVisibility(1).extend(new Notification.TvExtender().setChannelId(ADB_NOTIFICATION_CHANNEL_ID_TV)).build();
    }
}
