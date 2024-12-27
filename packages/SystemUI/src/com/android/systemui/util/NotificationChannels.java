package com.android.systemui.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import java.io.PrintWriter;
import java.util.Arrays;

public class NotificationChannels implements CoreStartable {
    public static String ALERTS = "ALR";
    public static String BATTERY = "BAT";
    public static String CHARGING = "CHR";
    public static String FLASHLIGHT_ONGOING = "FLASHLIGHT_ONGOING";

    @Deprecated
    static String GENERAL = "GEN";
    public static String HINTS = "HNT";
    public static String INSIGNIFICANT = "INSIGNIFICANT";
    public static String INSTANT = "INS";
    public static String LOW_BATTERY = "LBAT";
    public static String NEW_LOW_BATTERY = "NLBAT";
    public static String OLD_LOW_BATTERY = "LBT";
    public static String SCREENSHOTS_HEADSUP = "SCN_HEADSUP";
    public static String SEC_LOW_BATTERY = "LOWBAT";
    public static String SETUP = "STP";
    public static String STORAGE = "DSK";
    public static String TVPIP = "TVPIP";
    public static String ZEN_ONGOING = "ZEN_ONGOING";
    private final Context mContext;

    public NotificationChannels(Context context) {
        this.mContext = context;
    }

    private void cleanUp() {
        ((NotificationManager) this.mContext.getSystemService(NotificationManager.class)).deleteNotificationChannel(GENERAL);
    }

    public static void createAll(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        NotificationChannel notificationChannel = new NotificationChannel(BATTERY, context.getString(R.string.notification_channel_battery), 5);
        notificationChannel.setSound(Uri.parse("file://" + Settings.Global.getString(context.getContentResolver(), "low_battery_sound")), new AudioAttributes.Builder().setContentType(4).setUsage(10).build());
        notificationChannel.setBlockable(true);
        NotificationChannel notificationChannel2 = new NotificationChannel(SEC_LOW_BATTERY, context.getString(R.string.battery_low_sec_noti_channel), 5);
        notificationChannel2.setSound(null, null);
        NotificationChannel notificationChannel3 = new NotificationChannel(ALERTS, context.getString(R.string.notification_channel_alerts), 4);
        NotificationChannel notificationChannel4 = new NotificationChannel(INSTANT, context.getString(R.string.notification_channel_instant), 1);
        NotificationChannel notificationChannel5 = new NotificationChannel(SETUP, context.getString(R.string.notification_channel_setup), 3);
        notificationChannel5.setSound(null, null);
        NotificationChannel notificationChannel6 = new NotificationChannel(STORAGE, context.getString(R.string.notification_channel_storage), isTv(context) ? 3 : 2);
        NotificationChannel notificationChannel7 = new NotificationChannel(HINTS, context.getString(R.string.notification_channel_hints), 3);
        NotificationChannel notificationChannel8 = new NotificationChannel(CHARGING, context.getString(R.string.notification_channel_battery), 1);
        notificationChannel8.setSound(null, null);
        notificationManager.createNotificationChannels(Arrays.asList(notificationChannel3, notificationChannel4, notificationChannel5, notificationChannel6, new NotificationChannel(INSIGNIFICANT, context.getString(R.string.notification_inginificatn_header_text), 2), createScreenshotChannel(context.getString(R.string.notification_channel_screenshot)), notificationChannel2, notificationChannel8, notificationChannel, notificationChannel7, createZenChannel(context.getString(R.string.noti_dnd_channel_name)), createFlashLightChannel(context.getString(R.string.notification_channel_ongoing))));
        notificationManager.deleteNotificationChannel(BATTERY);
        notificationManager.deleteNotificationChannel(OLD_LOW_BATTERY);
        notificationManager.deleteNotificationChannel(LOW_BATTERY);
        notificationManager.deleteNotificationChannel(NEW_LOW_BATTERY);
        if (isTv(context)) {
            notificationManager.createNotificationChannel(new NotificationChannel(TVPIP, context.getString(R.string.notification_channel_tv_pip), 5));
        }
    }

    public static NotificationChannel createFlashLightChannel(String str) {
        return new NotificationChannel(FLASHLIGHT_ONGOING, str, 2);
    }

    public static NotificationChannel createScreenshotChannel(String str) {
        NotificationChannel notificationChannel = new NotificationChannel(SCREENSHOTS_HEADSUP, str, 4);
        notificationChannel.setSound(null, new AudioAttributes.Builder().setUsage(5).build());
        notificationChannel.setBlockable(true);
        return notificationChannel;
    }

    public static NotificationChannel createZenChannel(String str) {
        NotificationChannel notificationChannel = new NotificationChannel(ZEN_ONGOING, str, 3);
        notificationChannel.setSound(null, null);
        return notificationChannel;
    }

    private static boolean isTv(Context context) {
        return context.getPackageManager().hasSystemFeature("android.software.leanback");
    }

    @Override // com.android.systemui.CoreStartable
    public /* bridge */ /* synthetic */ boolean isDumpCritical() {
        return true;
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        createAll(this.mContext);
        cleanUp();
    }

    @Override // com.android.systemui.CoreStartable
    public /* bridge */ /* synthetic */ void onBootCompleted() {
    }

    @Override // com.android.systemui.CoreStartable
    public /* bridge */ /* synthetic */ void onTrimMemory(int i) {
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public /* bridge */ /* synthetic */ void dump(PrintWriter printWriter, String[] strArr) {
    }
}
