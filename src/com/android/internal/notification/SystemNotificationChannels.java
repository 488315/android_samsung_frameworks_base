package com.android.internal.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.media.AudioAttributes;
import android.os.RemoteException;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public class SystemNotificationChannels {
    public static final String ABUSIVE_BACKGROUND_APPS = "ABUSIVE_BACKGROUND_APPS";
    public static final String ACCESSIBILITY_HEARING_DEVICE = "ACCESSIBILITY_HEARING_DEVICE";
    public static final String ACCESSIBILITY_MAGNIFICATION = "ACCESSIBILITY_MAGNIFICATION";
    public static final String ACCESSIBILITY_SECURITY_POLICY = "ACCESSIBILITY_SECURITY_POLICY";
    public static final String ACCOUNT = "ACCOUNT";
    public static final String ALERTS = "ALERTS";
    public static final String CAR_MODE = "CAR_MODE";
    public static final String DEVELOPER = "DEVELOPER";
    public static final String DEVELOPER_IMPORTANT = "DEVELOPER_IMPORTANT";
    public static final String DEVICE_ADMIN = "DEVICE_ADMIN_ALERTS";

    @Deprecated
    public static final String DEVICE_ADMIN_DEPRECATED = "DEVICE_ADMIN";
    public static String ETHERNET = "ETHERNET";
    public static final String FOREGROUND_SERVICE = "FOREGROUND_SERVICE";
    public static final String HEAVY_WEIGHT_APP = "HEAVY_WEIGHT_APP";
    public static String MDM_DEXPOLICY = "MDM_DEXPOLICY";
    public static final String NETWORK_ALERTS = "NETWORK_ALERTS";
    public static final String NETWORK_AVAILABLE = "NETWORK_AVAILABLE";
    public static final String NETWORK_STATUS = "NETWORK_STATUS";
    static final String OBSOLETE_DO_NOT_DISTURB = "DO_NOT_DISTURB";
    public static final String PHYSICAL_KEYBOARD = "PHYSICAL_KEYBOARD";
    public static final String RETAIL_MODE = "RETAIL_MODE";
    public static final String SECURITY = "SECURITY";
    public static final String SYSTEM_CHANGES = "SYSTEM_CHANGES_ALERTS";

    @Deprecated
    public static final String SYSTEM_CHANGES_DEPRECATED = "SYSTEM_CHANGES";
    public static final String TIME = "TIME";
    public static final String UPDATES = "UPDATES";
    public static final String USB = "USB";

    @Deprecated
    public static String VIRTUAL_KEYBOARD = "VIRTUAL_KEYBOARD";
    public static final String VPN = "VPN";

    public static void createAll(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        ArrayList arrayList = new ArrayList();
        NotificationChannel notificationChannel = new NotificationChannel(PHYSICAL_KEYBOARD, context.getString(R.string.notification_channel_physical_keyboard), 2);
        notificationChannel.setBlockable(true);
        arrayList.add(notificationChannel);
        arrayList.add(new NotificationChannel(SECURITY, context.getString(R.string.notification_channel_security), 2));
        NotificationChannel notificationChannel2 = new NotificationChannel(CAR_MODE, context.getString(R.string.notification_channel_car_mode), 2);
        notificationChannel2.setBlockable(true);
        arrayList.add(notificationChannel2);
        arrayList.add(newAccountChannel(context));
        NotificationChannel notificationChannel3 = new NotificationChannel(DEVELOPER, context.getString(R.string.notification_channel_developer), 2);
        notificationChannel3.setBlockable(true);
        arrayList.add(notificationChannel3);
        NotificationChannel notificationChannel4 = new NotificationChannel(DEVELOPER_IMPORTANT, context.getString(R.string.notification_channel_developer_important), 4);
        notificationChannel3.setBlockable(true);
        arrayList.add(notificationChannel4);
        arrayList.add(new NotificationChannel(UPDATES, context.getString(R.string.notification_channel_updates), 2));
        NotificationChannel notificationChannel5 = new NotificationChannel(NETWORK_STATUS, context.getString(R.string.notification_channel_network_status), 2);
        notificationChannel5.setBlockable(true);
        arrayList.add(notificationChannel5);
        NotificationChannel notificationChannel6 = new NotificationChannel(NETWORK_ALERTS, context.getString(R.string.notification_channel_network_alerts), 4);
        notificationChannel6.setBlockable(true);
        arrayList.add(notificationChannel6);
        NotificationChannel notificationChannel7 = new NotificationChannel(NETWORK_AVAILABLE, context.getString(R.string.notification_channel_network_available), 2);
        notificationChannel7.setBlockable(true);
        arrayList.add(notificationChannel7);
        arrayList.add(new NotificationChannel("VPN", context.getString(R.string.notification_channel_vpn), 2));
        arrayList.add(new NotificationChannel(TIME, context.getString(R.string.notification_channel_system_time), 3));
        arrayList.add(new NotificationChannel(DEVICE_ADMIN, getDeviceAdminNotificationChannelName(context), 4));
        arrayList.add(new NotificationChannel(ALERTS, context.getString(R.string.notification_channel_alerts), 3));
        arrayList.add(new NotificationChannel(USB, context.getString(R.string.notification_channel_usb), 1));
        NotificationChannel notificationChannel8 = new NotificationChannel(FOREGROUND_SERVICE, context.getString(R.string.notification_channel_foreground_service), 2);
        notificationChannel8.setBlockable(true);
        arrayList.add(notificationChannel8);
        NotificationChannel notificationChannel9 = new NotificationChannel(HEAVY_WEIGHT_APP, context.getString(R.string.notification_channel_heavy_weight_app), 3);
        notificationChannel9.setShowBadge(false);
        notificationChannel9.setSound(null, new AudioAttributes.Builder().setContentType(4).setUsage(10).build());
        arrayList.add(notificationChannel9);
        NotificationChannel notificationChannel10 = new NotificationChannel(SYSTEM_CHANGES, context.getString(R.string.notification_channel_system_changes), 3);
        notificationChannel10.setSound(null, new AudioAttributes.Builder().setContentType(4).setUsage(5).build());
        arrayList.add(notificationChannel10);
        NotificationChannel notificationChannel11 = new NotificationChannel(ACCESSIBILITY_MAGNIFICATION, context.getString(R.string.notification_channel_accessibility_magnification), 4);
        notificationChannel11.setBlockable(true);
        arrayList.add(notificationChannel11);
        NotificationChannel notificationChannel12 = new NotificationChannel(ACCESSIBILITY_HEARING_DEVICE, context.getString(R.string.notification_channel_accessibility_hearing_device), 4);
        notificationChannel12.setBlockable(true);
        arrayList.add(notificationChannel12);
        arrayList.add(new NotificationChannel(ACCESSIBILITY_SECURITY_POLICY, context.getString(R.string.notification_channel_accessibility_security_policy), 2));
        arrayList.add(new NotificationChannel(MDM_DEXPOLICY, context.getString(R.string.notification_channel_dex_policy), 4));
        arrayList.add(new NotificationChannel(ABUSIVE_BACKGROUND_APPS, context.getString(R.string.notification_channel_abusive_bg_apps), 2));
        arrayList.add(new NotificationChannel(ETHERNET, context.getString(R.string.ethernet_cable_connected_notification_title), 2));
        notificationManager.createNotificationChannels(arrayList);
        notificationManager.deleteNotificationChannel(OBSOLETE_DO_NOT_DISTURB);
    }

    private static String getDeviceAdminNotificationChannelName(final Context context) {
        return ((DevicePolicyManager) context.getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.NOTIFICATION_CHANNEL_DEVICE_ADMIN, new Supplier() { // from class: com.android.internal.notification.SystemNotificationChannels$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return context.getString(R.string.notification_channel_device_admin);
            }
        });
    }

    public static void removeDeprecated(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        notificationManager.deleteNotificationChannel(VIRTUAL_KEYBOARD);
        notificationManager.deleteNotificationChannel(DEVICE_ADMIN_DEPRECATED);
        notificationManager.deleteNotificationChannel(SYSTEM_CHANGES_DEPRECATED);
        notificationManager.deleteNotificationChannel(RETAIL_MODE);
    }

    public static void createAccountChannelForPackage(String str, int i, Context context) {
        try {
            NotificationManager.getService().createNotificationChannelsForPackage(str, i, new ParceledListSlice(Arrays.asList(newAccountChannel(context))));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static NotificationChannel newAccountChannel(Context context) {
        return new NotificationChannel(ACCOUNT, context.getString(R.string.notification_channel_account), 2);
    }

    private SystemNotificationChannels() {
    }
}
