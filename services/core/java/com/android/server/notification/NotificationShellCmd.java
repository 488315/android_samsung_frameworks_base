package com.android.server.notification;

import android.app.INotificationManager;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.ShellCommand;
import android.service.notification.NotificationListenerService;
import android.text.TextUtils;
import android.util.Base64;

public final class NotificationShellCmd extends ShellCommand {
    public final INotificationManager mBinderService;
    public final NotificationManagerService mDirectService;
    public final PackageManager mPm;

    public final class ShellNls extends NotificationListenerService {
        public boolean isConnected;

        @Override // android.service.notification.NotificationListenerService
        public final void onListenerConnected() {
            super.onListenerConnected();
            this.isConnected = true;
        }

        @Override // android.service.notification.NotificationListenerService
        public final void onListenerDisconnected() {
            this.isConnected = false;
        }
    }

    public NotificationShellCmd(NotificationManagerService notificationManagerService) {
        this.mDirectService = notificationManagerService;
        this.mBinderService = notificationManagerService.getBinderService();
        this.mPm = notificationManagerService.getContext().getPackageManager();
    }

    public static Icon parseIcon(Resources resources, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith("/")) {
            str = "file://".concat(str);
        }
        if (str.startsWith("http:")
                || str.startsWith("https:")
                || str.startsWith("content:")
                || str.startsWith("file:")
                || str.startsWith("android.resource:")) {
            return Icon.createWithContentUri(Uri.parse(str));
        }
        if (str.startsWith("@")) {
            int identifier = resources.getIdentifier(str.substring(1), "drawable", "android");
            if (identifier != 0) {
                return Icon.createWithResource(resources, identifier);
            }
        } else if (str.startsWith("data:")) {
            byte[] decode = Base64.decode(str.substring(str.indexOf(44) + 1), 0);
            return Icon.createWithData(decode, 0, decode.length);
        }
        return null;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void doNotify(java.io.PrintWriter r26, java.lang.String r27, int r28) {
        /*
            Method dump skipped, instructions count: 1656
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.NotificationShellCmd.doNotify(java.io.PrintWriter,"
                    + " java.lang.String, int):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onCommand(java.lang.String r21) {
        /*
            Method dump skipped, instructions count: 1714
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.NotificationShellCmd.onCommand(java.lang.String):int");
    }

    public final void onHelp() {
        getOutPrintWriter()
                .println(
                        "usage: cmd notification SUBCMD [args]\n\n"
                            + "SUBCMDs:\n"
                            + "  allow_listener COMPONENT [user_id (current user if not"
                            + " specified)]\n"
                            + "  disallow_listener COMPONENT [user_id (current user if not"
                            + " specified)]\n"
                            + "  allow_assistant COMPONENT [user_id (current user if not"
                            + " specified)]\n"
                            + "  remove_assistant COMPONENT [user_id (current user if not"
                            + " specified)]\n"
                            + "  set_dnd [on|none (same as on)|priority|alarms|all|off (same as"
                            + " all)]\n"
                            + "  allow_dnd PACKAGE [user_id (current user if not specified)]\n"
                            + "  disallow_dnd PACKAGE [user_id (current user if not specified)]\n"
                            + "  reset_assistant_user_set [user_id (current user if not"
                            + " specified)]\n"
                            + "  get_approved_assistant [user_id (current user if not specified)]\n"
                            + "  post [--help | flags] TAG TEXT\n"
                            + "  set_bubbles PACKAGE PREFERENCE (0=none 1=all 2=selected) [user_id"
                            + " (current user if not specified)]\n"
                            + "  set_bubbles_channel PACKAGE CHANNEL_ID ALLOW [user_id (current"
                            + " user if not specified)]\n"
                            + "  list\n"
                            + "  get <notification-key>\n"
                            + "  snooze --for <msec> <notification-key>\n"
                            + "  unsnooze <notification-key>\n");
    }
}
