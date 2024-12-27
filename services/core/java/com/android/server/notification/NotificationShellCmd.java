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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class NotificationShellCmd extends ShellCommand {
    public final INotificationManager mBinderService;
    public final NotificationManagerService mDirectService;
    public final PackageManager mPm;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0474  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x046e A[SYNTHETIC] */
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

    /* JADX WARN: Removed duplicated region for block: B:85:0x02c1 A[Catch: Exception -> 0x00a3, TryCatch #6 {Exception -> 0x00a3, blocks: (B:19:0x0086, B:20:0x0093, B:25:0x0174, B:27:0x0179, B:31:0x018f, B:34:0x019b, B:35:0x019f, B:47:0x01f2, B:49:0x01f9, B:54:0x0209, B:56:0x0221, B:59:0x0249, B:72:0x024f, B:74:0x0256, B:77:0x0295, B:79:0x029d, B:102:0x02ad, B:105:0x02b6, B:108:0x02b3, B:83:0x02b9, B:85:0x02c1, B:97:0x02c5, B:88:0x02cc, B:94:0x02d2, B:111:0x0272, B:63:0x02da, B:66:0x02e3, B:69:0x02e0, B:113:0x02e7, B:116:0x01a3, B:119:0x01ad, B:122:0x01b7, B:125:0x01c2, B:128:0x01cc, B:131:0x01d6, B:135:0x0183, B:137:0x018b, B:138:0x02ed, B:140:0x02f9, B:141:0x0300, B:142:0x0306, B:147:0x0312, B:149:0x0329, B:154:0x033b, B:156:0x033c, B:157:0x034a, B:159:0x0350, B:162:0x0393, B:164:0x039f, B:166:0x03ac, B:168:0x03bc, B:169:0x03c8, B:171:0x03ce, B:174:0x03d8, B:176:0x03dd, B:178:0x03f7, B:179:0x03ff, B:181:0x041a, B:185:0x042b, B:187:0x0435, B:188:0x043d, B:190:0x044f, B:192:0x0455, B:194:0x045f, B:195:0x0467, B:197:0x046f, B:199:0x0477, B:201:0x0480, B:203:0x048a, B:204:0x0492, B:206:0x0499, B:208:0x04a3, B:210:0x04a7, B:212:0x04b1, B:213:0x04b9, B:215:0x04c1, B:217:0x04cb, B:219:0x04cf, B:221:0x04d9, B:222:0x04e1, B:224:0x04e8, B:226:0x04f2, B:228:0x04f6, B:230:0x0500, B:231:0x0508, B:233:0x0510, B:235:0x051a, B:237:0x051e, B:239:0x0528, B:240:0x0530, B:242:0x0537, B:244:0x0545, B:245:0x054d, B:247:0x0555, B:249:0x0563, B:250:0x056b, B:252:0x0572, B:253:0x057a, B:268:0x05d6, B:270:0x05dc, B:272:0x05e2, B:274:0x057e, B:277:0x0589, B:280:0x0594, B:283:0x059e, B:286:0x05a9, B:289:0x05b4, B:292:0x0098, B:295:0x00a6, B:298:0x00b2, B:301:0x00bd, B:304:0x00c9, B:307:0x00d4, B:310:0x00e1, B:313:0x00ee, B:316:0x00fa, B:319:0x0106, B:322:0x0112, B:325:0x011d, B:328:0x0127, B:331:0x0131, B:334:0x013d, B:337:0x0149, B:340:0x0153, B:343:0x015f, B:144:0x0307, B:145:0x030f), top: B:18:0x0086, inners: #1, #2, #4, #5 }] */
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
