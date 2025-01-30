package com.android.systemui.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationChannels implements CoreStartable {
    public final Context mContext;

    public NotificationChannels(Context context) {
        this.mContext = context;
    }

    public static void createAll(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        NotificationChannel notificationChannel = new NotificationChannel("BAT", context.getString(R.string.notification_channel_battery), 5);
        notificationChannel.setSound(Uri.parse("file://" + Settings.Global.getString(context.getContentResolver(), "low_battery_sound")), new AudioAttributes.Builder().setContentType(4).setUsage(10).build());
        notificationChannel.setBlockable(true);
        NotificationChannel notificationChannel2 = new NotificationChannel("LOWBAT", context.getString(R.string.battery_low_sec_noti_channel), 5);
        notificationChannel2.setSound(null, null);
        NotificationChannel notificationChannel3 = new NotificationChannel("ALR", context.getString(R.string.notification_channel_alerts), 4);
        NotificationChannel notificationChannel4 = new NotificationChannel("INS", context.getString(R.string.notification_channel_instant), 1);
        NotificationChannel notificationChannel5 = new NotificationChannel("STP", context.getString(R.string.notification_channel_setup), 3);
        notificationChannel5.setSound(null, null);
        NotificationChannel notificationChannel6 = new NotificationChannel("DSK", context.getString(R.string.notification_channel_storage), context.getPackageManager().hasSystemFeature("android.software.leanback") ? 3 : 2);
        NotificationChannel notificationChannel7 = new NotificationChannel("HNT", context.getString(R.string.notification_channel_hints), 3);
        NotificationChannel notificationChannel8 = new NotificationChannel("CHR", context.getString(R.string.notification_channel_battery), 1);
        notificationChannel8.setSound(null, null);
        notificationManager.createNotificationChannels(Arrays.asList(notificationChannel3, notificationChannel4, notificationChannel5, notificationChannel6, createScreenshotChannel(context.getString(R.string.notification_channel_screenshot)), notificationChannel2, notificationChannel8, notificationChannel, notificationChannel7, createZenChannel(context.getString(R.string.noti_dnd_channel_name)), createOngoingChannel(context.getString(R.string.notification_channel_ongoing))));
        notificationManager.deleteNotificationChannel("BAT");
        notificationManager.deleteNotificationChannel("LBT");
        notificationManager.deleteNotificationChannel("LBAT");
        notificationManager.deleteNotificationChannel("NLBAT");
        if (context.getPackageManager().hasSystemFeature("android.software.leanback")) {
            notificationManager.createNotificationChannel(new NotificationChannel("TVPIP", context.getString(R.string.notification_channel_tv_pip), 5));
        }
    }

    public static NotificationChannel createOngoingChannel(String str) {
        return new NotificationChannel("ONGOING", str, 2);
    }

    public static NotificationChannel createScreenshotChannel(String str) {
        NotificationChannel notificationChannel = new NotificationChannel("SCN_HEADSUP", str, 4);
        notificationChannel.setSound(null, new AudioAttributes.Builder().setUsage(5).build());
        notificationChannel.setBlockable(true);
        return notificationChannel;
    }

    public static NotificationChannel createZenChannel(String str) {
        NotificationChannel notificationChannel = new NotificationChannel("ZEN_ONGOING", str, 3);
        notificationChannel.setSound(null, null);
        return notificationChannel;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Context context = this.mContext;
        createAll(context);
        ((NotificationManager) context.getSystemService(NotificationManager.class)).deleteNotificationChannel("GEN");
    }
}
