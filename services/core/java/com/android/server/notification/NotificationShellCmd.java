package com.android.server.notification;

import android.R;
import android.app.ActivityManager;
import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.Person;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.INetd;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.net.util.NetworkConstants;
import android.os.Binder;
import android.os.ShellCommand;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class NotificationShellCmd extends ShellCommand {
  public final INotificationManager mBinderService;
  public final NotificationManagerService mDirectService;
  public final PackageManager mPm;

  public boolean checkShellCommandPermission(int i) {
    return i == 0 || i == 2000;
  }

  public NotificationShellCmd(NotificationManagerService notificationManagerService) {
    this.mDirectService = notificationManagerService;
    this.mBinderService = notificationManagerService.getBinderService();
    this.mPm = notificationManagerService.getContext().getPackageManager();
  }

  /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
  public int onCommand(String str) {
    String str2;
    int i;
    char c;
    char c2;
    boolean z;
    String str3;
    long j;
    if (str == null) {
      return handleDefaultCommands(str);
    }
    int callingUid = Binder.getCallingUid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      if (callingUid == 0) {
        str2 = "root";
      } else {
        try {
          String[] packagesForUid = this.mPm.getPackagesForUid(callingUid);
          str2 = (packagesForUid == null || packagesForUid.length <= 0) ? null : packagesForUid[0];
        } catch (Exception e) {
          Slog.e("NotifShellCmd", "failed to get caller pkg", e);
          Binder.restoreCallingIdentity(clearCallingIdentity);
          str2 = null;
        }
      }
      PrintWriter outPrintWriter = getOutPrintWriter();
      if (!checkShellCommandPermission(callingUid)) {
        Slog.e(
            "NotifShellCmd",
            "error: permission denied: callingUid=" + callingUid + " callingPackage=" + str2);
        outPrintWriter.println(
            "error: permission denied: callingUid=" + callingUid + " callingPackage=" + str2);
        return IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
      }
      try {
        String replace = str.replace('-', '_');
        i = 4;
        c = 65535;
        switch (replace.hashCode()) {
          case -2056114370:
            if (replace.equals("snoozed")) {
              c2 = 15;
              break;
            }
            c2 = 65535;
            break;
          case -1736066994:
            if (replace.equals("set_bubbles_channel")) {
              c2 = '\n';
              break;
            }
            c2 = 65535;
            break;
          case -1325770982:
            if (replace.equals("disallow_assistant")) {
              c2 = 6;
              break;
            }
            c2 = 65535;
            break;
          case -1039689911:
            if (replace.equals("notify")) {
              c2 = '\f';
              break;
            }
            c2 = 65535;
            break;
          case -897610266:
            if (replace.equals("snooze")) {
              c2 = 17;
              break;
            }
            c2 = 65535;
            break;
          case -432999190:
            if (replace.equals("allow_listener")) {
              c2 = 3;
              break;
            }
            c2 = 65535;
            break;
          case -429832618:
            if (replace.equals("disallow_dnd")) {
              c2 = 2;
              break;
            }
            c2 = 65535;
            break;
          case -414550305:
            if (replace.equals("get_approved_assistant")) {
              c2 = '\b';
              break;
            }
            c2 = 65535;
            break;
          case -11106881:
            if (replace.equals("unsnooze")) {
              c2 = 16;
              break;
            }
            c2 = 65535;
            break;
          case 102230:
            if (replace.equals("get")) {
              c2 = 14;
              break;
            }
            c2 = 65535;
            break;
          case 3322014:
            if (replace.equals("list")) {
              c2 = '\r';
              break;
            }
            c2 = 65535;
            break;
          case 3446944:
            if (replace.equals("post")) {
              c2 = 11;
              break;
            }
            c2 = 65535;
            break;
          case 212123274:
            if (replace.equals("set_bubbles")) {
              c2 = '\t';
              break;
            }
            c2 = 65535;
            break;
          case 372345636:
            if (replace.equals("allow_dnd")) {
              c2 = 1;
              break;
            }
            c2 = 65535;
            break;
          case 683492127:
            if (replace.equals("reset_assistant_user_set")) {
              c2 = 7;
              break;
            }
            c2 = 65535;
            break;
          case 1257269496:
            if (replace.equals("disallow_listener")) {
              c2 = 4;
              break;
            }
            c2 = 65535;
            break;
          case 1985310653:
            if (replace.equals("set_dnd")) {
              c2 = 0;
              break;
            }
            c2 = 65535;
            break;
          case 2110474600:
            if (replace.equals("allow_assistant")) {
              c2 = 5;
              break;
            }
            c2 = 65535;
            break;
          default:
            c2 = 65535;
            break;
        }
      } catch (Exception e2) {
        outPrintWriter.println("Error occurred. Check logcat for details. " + e2.getMessage());
        Slog.e("NotificationService", "Error running shell command", e2);
      }
      switch (c2) {
        case 0:
          String nextArgRequired = getNextArgRequired();
          switch (nextArgRequired.hashCode()) {
            case -1415196606:
              if (nextArgRequired.equals("alarms")) {
                c = 3;
                break;
              }
              break;
            case -1165461084:
              if (nextArgRequired.equals("priority")) {
                c = 2;
                break;
              }
              break;
            case 3551:
              if (nextArgRequired.equals("on")) {
                c = 1;
                break;
              }
              break;
            case 96673:
              if (nextArgRequired.equals("all")) {
                c = 4;
                break;
              }
              break;
            case 109935:
              if (nextArgRequired.equals("off")) {
                c = 5;
                break;
              }
              break;
            case 3387192:
              if (nextArgRequired.equals("none")) {
                c = 0;
                break;
              }
              break;
          }
          if (c == 0 || c == 1) {
            i = 3;
          } else if (c == 2) {
            i = 2;
          } else if (c != 3) {
            i = (c == 4 || c == 5) ? 1 : 0;
          }
          this.mBinderService.setInterruptionFilter(str2, i);
          return 0;
        case 1:
          String nextArgRequired2 = getNextArgRequired();
          int currentUser = ActivityManager.getCurrentUser();
          if (peekNextArg() != null) {
            currentUser = Integer.parseInt(getNextArgRequired());
          }
          this.mBinderService.setNotificationPolicyAccessGrantedForUser(
              nextArgRequired2, currentUser, true);
          return 0;
        case 2:
          String nextArgRequired3 = getNextArgRequired();
          int currentUser2 = ActivityManager.getCurrentUser();
          if (peekNextArg() != null) {
            currentUser2 = Integer.parseInt(getNextArgRequired());
          }
          this.mBinderService.setNotificationPolicyAccessGrantedForUser(
              nextArgRequired3, currentUser2, false);
          return 0;
        case 3:
          ComponentName unflattenFromString =
              ComponentName.unflattenFromString(getNextArgRequired());
          if (unflattenFromString == null) {
            outPrintWriter.println("Invalid listener - must be a ComponentName");
            return -1;
          }
          int currentUser3 = ActivityManager.getCurrentUser();
          if (peekNextArg() != null) {
            currentUser3 = Integer.parseInt(getNextArgRequired());
          }
          this.mBinderService.setNotificationListenerAccessGrantedForUser(
              unflattenFromString, currentUser3, true, true);
          return 0;
        case 4:
          ComponentName unflattenFromString2 =
              ComponentName.unflattenFromString(getNextArgRequired());
          if (unflattenFromString2 == null) {
            outPrintWriter.println("Invalid listener - must be a ComponentName");
            return -1;
          }
          int currentUser4 = ActivityManager.getCurrentUser();
          if (peekNextArg() != null) {
            currentUser4 = Integer.parseInt(getNextArgRequired());
          }
          this.mBinderService.setNotificationListenerAccessGrantedForUser(
              unflattenFromString2, currentUser4, false, true);
          return 0;
        case 5:
          ComponentName unflattenFromString3 =
              ComponentName.unflattenFromString(getNextArgRequired());
          if (unflattenFromString3 == null) {
            outPrintWriter.println("Invalid assistant - must be a ComponentName");
            return -1;
          }
          int currentUser5 = ActivityManager.getCurrentUser();
          if (peekNextArg() != null) {
            currentUser5 = Integer.parseInt(getNextArgRequired());
          }
          this.mBinderService.setNotificationAssistantAccessGrantedForUser(
              unflattenFromString3, currentUser5, true);
          return 0;
        case 6:
          ComponentName unflattenFromString4 =
              ComponentName.unflattenFromString(getNextArgRequired());
          if (unflattenFromString4 == null) {
            outPrintWriter.println("Invalid assistant - must be a ComponentName");
            return -1;
          }
          int currentUser6 = ActivityManager.getCurrentUser();
          if (peekNextArg() != null) {
            currentUser6 = Integer.parseInt(getNextArgRequired());
          }
          this.mBinderService.setNotificationAssistantAccessGrantedForUser(
              unflattenFromString4, currentUser6, false);
          return 0;
        case 7:
          int currentUser7 = ActivityManager.getCurrentUser();
          if (peekNextArg() != null) {
            currentUser7 = Integer.parseInt(getNextArgRequired());
          }
          this.mDirectService.resetAssistantUserSet(currentUser7);
          return 0;
        case '\b':
          int currentUser8 = ActivityManager.getCurrentUser();
          if (peekNextArg() != null) {
            currentUser8 = Integer.parseInt(getNextArgRequired());
          }
          ComponentName approvedAssistant = this.mDirectService.getApprovedAssistant(currentUser8);
          if (approvedAssistant == null) {
            outPrintWriter.println("null");
          } else {
            outPrintWriter.println(approvedAssistant.flattenToString());
          }
          return 0;
        case '\t':
          String nextArgRequired4 = getNextArgRequired();
          int parseInt = Integer.parseInt(getNextArgRequired());
          if (parseInt <= 3 && parseInt >= 0) {
            int currentUser9 = ActivityManager.getCurrentUser();
            if (peekNextArg() != null) {
              currentUser9 = Integer.parseInt(getNextArgRequired());
            }
            this.mBinderService.setBubblesAllowed(
                nextArgRequired4,
                UserHandle.getUid(currentUser9, this.mPm.getPackageUid(nextArgRequired4, 0)),
                parseInt);
            return 0;
          }
          outPrintWriter.println(
              "Invalid preference - must be between 0-3 (0=none 1=all 2=selected)");
          return -1;
        case '\n':
          String nextArgRequired5 = getNextArgRequired();
          String nextArgRequired6 = getNextArgRequired();
          boolean parseBoolean = Boolean.parseBoolean(getNextArgRequired());
          int currentUser10 = ActivityManager.getCurrentUser();
          if (peekNextArg() != null) {
            currentUser10 = Integer.parseInt(getNextArgRequired());
          }
          NotificationChannel notificationChannel =
              this.mBinderService.getNotificationChannel(
                  str2, currentUser10, nextArgRequired5, nextArgRequired6);
          notificationChannel.setAllowBubbles(parseBoolean);
          this.mBinderService.updateNotificationChannelForPackage(
              nextArgRequired5,
              UserHandle.getUid(currentUser10, this.mPm.getPackageUid(nextArgRequired5, 0)),
              notificationChannel);
          return 0;
        case 11:
        case '\f':
          doNotify(outPrintWriter, str2, callingUid);
          return 0;
        case '\r':
          Iterator it = this.mDirectService.mNotificationsByKey.keySet().iterator();
          while (it.hasNext()) {
            outPrintWriter.println((String) it.next());
          }
          return 0;
        case 14:
          String nextArgRequired7 = getNextArgRequired();
          NotificationRecord notificationRecord =
              this.mDirectService.getNotificationRecord(nextArgRequired7);
          if (notificationRecord != null) {
            notificationRecord.dump(outPrintWriter, "", this.mDirectService.getContext(), false);
            return 0;
          }
          outPrintWriter.println("error: no active notification matching key: " + nextArgRequired7);
          return 1;
        case 15:
          SnoozeHelper snoozeHelper = this.mDirectService.mSnoozeHelper;
          for (NotificationRecord notificationRecord2 : snoozeHelper.getSnoozed()) {
            String packageName = notificationRecord2.getSbn().getPackageName();
            String key = notificationRecord2.getKey();
            outPrintWriter.println(
                key
                    + " snoozed, time="
                    + snoozeHelper.getSnoozeTimeForUnpostedNotification(
                        notificationRecord2.getUserId(), packageName, key)
                    + " context="
                    + snoozeHelper.getSnoozeContextForUnpostedNotification(
                        notificationRecord2.getUserId(), packageName, key));
          }
          return 0;
        case 16:
          String nextArgRequired8 = getNextArgRequired();
          if ("--mute".equals(nextArgRequired8)) {
            nextArgRequired8 = getNextArgRequired();
            z = true;
          } else {
            z = false;
          }
          if (this.mDirectService.mSnoozeHelper.getNotification(nextArgRequired8) != null) {
            outPrintWriter.println("unsnoozing: " + nextArgRequired8);
            this.mDirectService.unsnoozeNotificationInt(nextArgRequired8, null, z);
            return 0;
          }
          outPrintWriter.println("error: no snoozed otification matching key: " + nextArgRequired8);
          return 1;
        case 17:
          String nextArg = getNextArg();
          String str4 = "help";
          if (nextArg == null) {
            nextArg = "help";
          } else if (nextArg.startsWith("--")) {
            nextArg = nextArg.substring(2);
          }
          String nextArg2 = getNextArg();
          String nextArg3 = getNextArg();
          if (nextArg3 != null) {
            str4 = nextArg;
          }
          switch (str4.hashCode()) {
            case -1992012396:
              if (str4.equals("duration")) {
                c = 5;
                break;
              }
              break;
            case -861311717:
              if (str4.equals("condition")) {
                c = 1;
                break;
              }
              break;
            case 101577:
              if (str4.equals("for")) {
                c = 4;
                break;
              }
              break;
            case 111443806:
              if (str4.equals("until")) {
                c = 3;
                break;
              }
              break;
            case 383913633:
              if (str4.equals("criterion")) {
                c = 2;
                break;
              }
              break;
            case 951530927:
              if (str4.equals("context")) {
                c = 0;
                break;
              }
              break;
          }
          if (c == 0 || c == 1 || c == 2) {
            str3 = nextArg2;
            j = 0;
          } else if (c == 3 || c == 4 || c == 5) {
            j = Long.parseLong(nextArg2);
            str3 = null;
          } else {
            outPrintWriter.println(
                "usage: cmd notification snooze (--for <msec> | --context <snooze-criterion-id>)"
                    + " <key>");
            return 1;
          }
          if (j <= 0 && str3 == null) {
            outPrintWriter.println("error: invalid value for --" + str4 + ": " + nextArg2);
            return 1;
          }
          ShellNls shellNls = new ShellNls();
          shellNls.registerAsSystemService(
              this.mDirectService.getContext(),
              new ComponentName(ShellNls.class.getPackageName(), ShellNls.class.getName()),
              ActivityManager.getCurrentUser());
          if (!waitForBind(shellNls)) {
            outPrintWriter.println("error: could not bind a listener in time");
            return 1;
          }
          if (j > 0) {
            outPrintWriter.println(
                String.format(
                    "snoozing <%s> until time: %s",
                    nextArg3, new Date(System.currentTimeMillis() + j)));
            shellNls.snoozeNotification(nextArg3, j);
          } else {
            outPrintWriter.println(
                String.format("snoozing <%s> until criterion: %s", nextArg3, str3));
            shellNls.snoozeNotification(nextArg3, str3);
          }
          waitForSnooze(shellNls, nextArg3);
          shellNls.unregisterAsSystemService();
          waitForUnbind(shellNls);
          return 0;
        default:
          return handleDefaultCommands(str);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void ensureChannel(String str, int i) {
    this.mBinderService.createNotificationChannels(
        str,
        new ParceledListSlice(
            Collections.singletonList(new NotificationChannel("shell_cmd", "Shell command", 3))));
    Slog.v(
        "NotificationService",
        "created channel: "
            + this.mBinderService.getNotificationChannel(
                str, UserHandle.getUserId(i), str, "shell_cmd"));
  }

  public Icon parseIcon(Resources resources, String str) {
    if (TextUtils.isEmpty(str)) {
      return null;
    }
    if (str.startsWith("/")) {
      str = "file://" + str;
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
  /* JADX WARN: Code restructure failed: missing block: B:38:0x02a9, code lost:

     if (r3.equals("inbox") == false) goto L137;
  */
  /* JADX WARN: Removed duplicated region for block: B:132:0x041b  */
  /* JADX WARN: Removed duplicated region for block: B:135:0x0405 A[SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int doNotify(PrintWriter printWriter, String str, int i) {
    char c;
    Notification.MessagingStyle messagingStyle;
    Notification.InboxStyle inboxStyle;
    Notification.BigTextStyle bigTextStyle;
    Icon parseIcon;
    char c2;
    String nextArg;
    Notification.MessagingStyle messagingStyle2;
    Notification.InboxStyle inboxStyle2;
    PendingIntent activityAsUser;
    String str2;
    Context context = this.mDirectService.getContext();
    Resources resources = context.getResources();
    Notification.Builder builder = new Notification.Builder(context, "shell_cmd");
    Notification.MessagingStyle messagingStyle3 = null;
    Notification.InboxStyle inboxStyle3 = null;
    Notification.BigTextStyle bigTextStyle2 = null;
    Icon icon = null;
    Notification.BigPictureStyle bigPictureStyle = null;
    boolean z = false;
    while (true) {
      String nextOption = getNextOption();
      int i2 = 3;
      if (nextOption != null) {
        boolean z2 = true;
        switch (nextOption.hashCode()) {
          case -1954060697:
            if (nextOption.equals("--message")) {
              c = 25;
              break;
            }
            c = 65535;
            break;
          case -1613915119:
            if (nextOption.equals("--style")) {
              c = 19;
              break;
            }
            c = 65535;
            break;
          case -1613324104:
            if (nextOption.equals("--title")) {
              c = 3;
              break;
            }
            c = 65535;
            break;
          case -1210178960:
            if (nextOption.equals("content-intent")) {
              c = 15;
              break;
            }
            c = 65535;
            break;
          case -1183762788:
            if (nextOption.equals(KnoxCustomManagerService.INTENT)) {
              c = 17;
              break;
            }
            c = 65535;
            break;
          case -853380573:
            if (nextOption.equals("--conversation")) {
              c = 26;
              break;
            }
            c = 65535;
            break;
          case -45879957:
            if (nextOption.equals("--large-icon")) {
              c = 6;
              break;
            }
            c = 65535;
            break;
          case 1468:
            if (nextOption.equals("-I")) {
              c = 5;
              break;
            }
            c = 65535;
            break;
          case 1478:
            if (nextOption.equals("-S")) {
              c = 18;
              break;
            }
            c = 65535;
            break;
          case 1494:
            if (nextOption.equals("-c")) {
              c = '\r';
              break;
            }
            c = 65535;
            break;
          case 1499:
            if (nextOption.equals("-h")) {
              c = 27;
              break;
            }
            c = 65535;
            break;
          case NetworkConstants.ETHER_MTU /* 1500 */:
            if (nextOption.equals("-i")) {
              c = '\n';
              break;
            }
            c = 65535;
            break;
          case 1511:
            if (nextOption.equals("-t")) {
              c = 2;
              break;
            }
            c = 65535;
            break;
          case 1513:
            if (nextOption.equals("-v")) {
              c = 0;
              break;
            }
            c = 65535;
            break;
          case 3226745:
            if (nextOption.equals(KnoxCustomManagerService.ICON)) {
              c = '\f';
              break;
            }
            c = 65535;
            break;
          case 43017097:
            if (nextOption.equals("--wtf")) {
              c = 29;
              break;
            }
            c = 65535;
            break;
          case 110371416:
            if (nextOption.equals(KnoxCustomManagerService.SHORTCUT_TITLE)) {
              c = 4;
              break;
            }
            c = 65535;
            break;
          case 704999290:
            if (nextOption.equals("--big-text")) {
              c = 22;
              break;
            }
            c = 65535;
            break;
          case 705941520:
            if (nextOption.equals("--content-intent")) {
              c = 14;
              break;
            }
            c = 65535;
            break;
          case 758833716:
            if (nextOption.equals("largeicon")) {
              c = '\b';
              break;
            }
            c = 65535;
            break;
          case 808239966:
            if (nextOption.equals("--picture")) {
              c = 23;
              break;
            }
            c = 65535;
            break;
          case 1216250940:
            if (nextOption.equals("--intent")) {
              c = 16;
              break;
            }
            c = 65535;
            break;
          case 1247228052:
            if (nextOption.equals("--largeicon")) {
              c = 7;
              break;
            }
            c = 65535;
            break;
          case 1270815917:
            if (nextOption.equals("--bigText")) {
              c = 20;
              break;
            }
            c = 65535;
            break;
          case 1271769229:
            if (nextOption.equals("--bigtext")) {
              c = 21;
              break;
            }
            c = 65535;
            break;
          case 1333069025:
            if (nextOption.equals("--help")) {
              c = 28;
              break;
            }
            c = 65535;
            break;
          case 1333096985:
            if (nextOption.equals("--icon")) {
              c = 11;
              break;
            }
            c = 65535;
            break;
          case 1333192084:
            if (nextOption.equals("--line")) {
              c = 24;
              break;
            }
            c = 65535;
            break;
          case 1737088994:
            if (nextOption.equals("--verbose")) {
              c = 1;
              break;
            }
            c = 65535;
            break;
          case 1993764811:
            if (nextOption.equals("large-icon")) {
              c = '\t';
              break;
            }
            c = 65535;
            break;
          default:
            c = 65535;
            break;
        }
        switch (c) {
          case 0:
          case 1:
            z = true;
          case 2:
          case 3:
          case 4:
            messagingStyle = messagingStyle3;
            inboxStyle = inboxStyle3;
            bigTextStyle = bigTextStyle2;
            builder.setContentTitle(getNextArgRequired());
            messagingStyle3 = messagingStyle;
            inboxStyle3 = inboxStyle;
            bigTextStyle2 = bigTextStyle;
          case 5:
          case 6:
          case 7:
          case '\b':
          case '\t':
            messagingStyle = messagingStyle3;
            inboxStyle = inboxStyle3;
            bigTextStyle = bigTextStyle2;
            String nextArgRequired = getNextArgRequired();
            parseIcon = parseIcon(resources, nextArgRequired);
            if (parseIcon != null) {
              printWriter.println("error: invalid icon: " + nextArgRequired);
              return -1;
            }
            if (z2) {
              builder.setLargeIcon(parseIcon);
            } else {
              icon = parseIcon;
            }
            messagingStyle3 = messagingStyle;
            inboxStyle3 = inboxStyle;
            bigTextStyle2 = bigTextStyle;
          case '\n':
          case 11:
          case '\f':
            messagingStyle = messagingStyle3;
            inboxStyle = inboxStyle3;
            bigTextStyle = bigTextStyle2;
            z2 = false;
            String nextArgRequired2 = getNextArgRequired();
            parseIcon = parseIcon(resources, nextArgRequired2);
            if (parseIcon != null) {}
            break;
          case '\r':
          case 14:
          case 15:
          case 16:
          case 17:
            String peekNextArg = peekNextArg();
            peekNextArg.hashCode();
            switch (peekNextArg.hashCode()) {
              case -1655966961:
                if (peekNextArg.equals("activity")) {
                  c2 = 0;
                  break;
                }
                c2 = 65535;
                break;
              case -1618876223:
                if (peekNextArg.equals(INetd.IF_FLAG_BROADCAST)) {
                  c2 = 1;
                  break;
                }
                c2 = 65535;
                break;
              case 1984153269:
                if (peekNextArg.equals("service")) {
                  c2 = 2;
                  break;
                }
                c2 = 65535;
                break;
              default:
                c2 = 65535;
                break;
            }
            switch (c2) {
              case 0:
              case 1:
              case 2:
                nextArg = getNextArg();
                break;
              default:
                nextArg = null;
                break;
            }
            Intent parseCommandArgs = Intent.parseCommandArgs(this, null);
            if (parseCommandArgs.getData() == null) {
              StringBuilder sb = new StringBuilder();
              messagingStyle2 = messagingStyle3;
              sb.append("xyz:");
              inboxStyle2 = inboxStyle3;
              sb.append(System.currentTimeMillis());
              parseCommandArgs.setData(Uri.parse(sb.toString()));
            } else {
              messagingStyle2 = messagingStyle3;
              inboxStyle2 = inboxStyle3;
            }
            if (INetd.IF_FLAG_BROADCAST.equals(nextArg)) {
              activityAsUser =
                  PendingIntent.getBroadcastAsUser(
                      context, 0, parseCommandArgs, 201326592, UserHandle.CURRENT);
            } else if ("service".equals(nextArg)) {
              activityAsUser = PendingIntent.getService(context, 0, parseCommandArgs, 201326592);
            } else {
              messagingStyle = messagingStyle2;
              inboxStyle = inboxStyle2;
              bigTextStyle = bigTextStyle2;
              activityAsUser =
                  PendingIntent.getActivityAsUser(
                      context, 0, parseCommandArgs, 201326592, null, UserHandle.CURRENT);
              builder.setContentIntent(activityAsUser);
              messagingStyle3 = messagingStyle;
              inboxStyle3 = inboxStyle;
              bigTextStyle2 = bigTextStyle;
            }
            messagingStyle = messagingStyle2;
            inboxStyle = inboxStyle2;
            bigTextStyle = bigTextStyle2;
            builder.setContentIntent(activityAsUser);
            messagingStyle3 = messagingStyle;
            inboxStyle3 = inboxStyle;
            bigTextStyle2 = bigTextStyle;
          case 18:
          case 19:
            String lowerCase = getNextArgRequired().toLowerCase();
            lowerCase.hashCode();
            switch (lowerCase.hashCode()) {
              case -1440008444:
                if (lowerCase.equals("messaging")) {
                  i2 = 0;
                  break;
                }
                i2 = -1;
                break;
              case -114212307:
                if (lowerCase.equals("bigtext")) {
                  i2 = 1;
                  break;
                }
                i2 = -1;
                break;
              case -44548098:
                if (lowerCase.equals("bigpicture")) {
                  i2 = 2;
                  break;
                }
                i2 = -1;
                break;
              case 100344454:
                break;
              case 103772132:
                if (lowerCase.equals("media")) {
                  i2 = 4;
                  break;
                }
                i2 = -1;
                break;
              default:
                i2 = -1;
                break;
            }
            switch (i2) {
              case 0:
                if ("--user".equals(peekNextArg())) {
                  getNextArg();
                  str2 = getNextArgRequired();
                } else {
                  str2 = "You";
                }
                messagingStyle3 =
                    new Notification.MessagingStyle(new Person.Builder().setName(str2).build());
                builder.setStyle(messagingStyle3);
                break;
              case 1:
                bigTextStyle2 = new Notification.BigTextStyle();
                builder.setStyle(bigTextStyle2);
                break;
              case 2:
                bigPictureStyle = new Notification.BigPictureStyle();
                builder.setStyle(bigPictureStyle);
                break;
              case 3:
                inboxStyle3 = new Notification.InboxStyle();
                builder.setStyle(inboxStyle3);
                break;
              case 4:
                builder.setStyle(new Notification.MediaStyle());
                break;
              default:
                throw new IllegalArgumentException("unrecognized notification style: " + lowerCase);
            }
          case 20:
          case 21:
          case 22:
            if (bigTextStyle2 == null) {
              throw new IllegalArgumentException("--bigtext requires --style bigtext");
            }
            bigTextStyle2.bigText(getNextArgRequired());
            messagingStyle = messagingStyle3;
            inboxStyle = inboxStyle3;
            bigTextStyle = bigTextStyle2;
            messagingStyle3 = messagingStyle;
            inboxStyle3 = inboxStyle;
            bigTextStyle2 = bigTextStyle;
          case 23:
            if (bigPictureStyle == null) {
              throw new IllegalArgumentException("--picture requires --style bigpicture");
            }
            String nextArgRequired3 = getNextArgRequired();
            Icon parseIcon2 = parseIcon(resources, nextArgRequired3);
            if (parseIcon2 == null) {
              throw new IllegalArgumentException("bad picture spec: " + nextArgRequired3);
            }
            Drawable loadDrawable = parseIcon2.loadDrawable(context);
            if (loadDrawable instanceof BitmapDrawable) {
              bigPictureStyle.bigPicture(((BitmapDrawable) loadDrawable).getBitmap());
              messagingStyle = messagingStyle3;
              inboxStyle = inboxStyle3;
              bigTextStyle = bigTextStyle2;
              messagingStyle3 = messagingStyle;
              inboxStyle3 = inboxStyle;
              bigTextStyle2 = bigTextStyle;
            } else {
              throw new IllegalArgumentException("not a bitmap: " + nextArgRequired3);
            }
          case 24:
            if (inboxStyle3 == null) {
              throw new IllegalArgumentException("--line requires --style inbox");
            }
            inboxStyle3.addLine(getNextArgRequired());
            messagingStyle = messagingStyle3;
            inboxStyle = inboxStyle3;
            bigTextStyle = bigTextStyle2;
            messagingStyle3 = messagingStyle;
            inboxStyle3 = inboxStyle;
            bigTextStyle2 = bigTextStyle;
          case 25:
            if (messagingStyle3 == null) {
              throw new IllegalArgumentException("--message requires --style messaging");
            }
            String[] split = getNextArgRequired().split(XmlUtils.STRING_ARRAY_SEPARATOR, 2);
            if (split.length > 1) {
              messagingStyle3.addMessage(split[1], System.currentTimeMillis(), split[0]);
            } else {
              messagingStyle3.addMessage(
                  split[0],
                  System.currentTimeMillis(),
                  new String[] {messagingStyle3.getUserDisplayName().toString(), "Them"}
                      [messagingStyle3.getMessages().size() % 2]);
            }
            messagingStyle = messagingStyle3;
            inboxStyle = inboxStyle3;
            bigTextStyle = bigTextStyle2;
            messagingStyle3 = messagingStyle;
            inboxStyle3 = inboxStyle;
            bigTextStyle2 = bigTextStyle;
          case 26:
            if (messagingStyle3 == null) {
              throw new IllegalArgumentException("--conversation requires --style messaging");
            }
            messagingStyle3.setConversationTitle(getNextArgRequired());
            messagingStyle = messagingStyle3;
            inboxStyle = inboxStyle3;
            bigTextStyle = bigTextStyle2;
            messagingStyle3 = messagingStyle;
            inboxStyle3 = inboxStyle;
            bigTextStyle2 = bigTextStyle;
          default:
            printWriter.println(
                "usage: cmd notification post [flags] <tag> <text>\n\n"
                    + "flags:\n"
                    + "  -h|--help\n"
                    + "  -v|--verbose\n"
                    + "  -t|--title <text>\n"
                    + "  -i|--icon <iconspec>\n"
                    + "  -I|--large-icon <iconspec>\n"
                    + "  -S|--style <style> [styleargs]\n"
                    + "  -c|--content-intent <intentspec>\n\n"
                    + "styles: (default none)\n"
                    + "  bigtext\n"
                    + "  bigpicture --picture <iconspec>\n"
                    + "  inbox --line <text> --line <text> ...\n"
                    + "  messaging --conversation <title> --message <who>:<text> ...\n"
                    + "  media\n\n"
                    + "an <iconspec> is one of\n"
                    + "  file:///data/local/tmp/<img.png>\n"
                    + "  content://<provider>/<path>\n"
                    + "  @[<package>:]drawable/<img>\n"
                    + "  data:base64,<B64DATA==>\n\n"
                    + "an <intentspec> is (broadcast|service|activity) <args>\n"
                    + "  <args> are as described in `am start`");
            return 0;
        }
      } else {
        String nextArg2 = getNextArg();
        String nextArg3 = getNextArg();
        if (nextArg2 == null || nextArg3 == null) {
          printWriter.println(
              "usage: cmd notification post [flags] <tag> <text>\n\n"
                  + "flags:\n"
                  + "  -h|--help\n"
                  + "  -v|--verbose\n"
                  + "  -t|--title <text>\n"
                  + "  -i|--icon <iconspec>\n"
                  + "  -I|--large-icon <iconspec>\n"
                  + "  -S|--style <style> [styleargs]\n"
                  + "  -c|--content-intent <intentspec>\n\n"
                  + "styles: (default none)\n"
                  + "  bigtext\n"
                  + "  bigpicture --picture <iconspec>\n"
                  + "  inbox --line <text> --line <text> ...\n"
                  + "  messaging --conversation <title> --message <who>:<text> ...\n"
                  + "  media\n\n"
                  + "an <iconspec> is one of\n"
                  + "  file:///data/local/tmp/<img.png>\n"
                  + "  content://<provider>/<path>\n"
                  + "  @[<package>:]drawable/<img>\n"
                  + "  data:base64,<B64DATA==>\n\n"
                  + "an <intentspec> is (broadcast|service|activity) <args>\n"
                  + "  <args> are as described in `am start`");
          return -1;
        }
        builder.setContentText(nextArg3);
        if (icon == null) {
          builder.setSmallIcon(R.drawable.stat_notify_chat);
        } else {
          builder.setSmallIcon(icon);
        }
        ensureChannel(str, i);
        Notification build = builder.build();
        printWriter.println("posting:\n  " + build);
        Slog.v("NotificationManager", "posting: " + build);
        this.mBinderService.enqueueNotificationWithTag(
            str, str, nextArg2, 2020, build, UserHandle.getUserId(i));
        if (z) {
          NotificationRecord findNotificationLocked =
              this.mDirectService.findNotificationLocked(
                  str, nextArg2, 2020, UserHandle.getUserId(i));
          while (true) {
            int i3 = i2 - 1;
            if (i2 > 0 && findNotificationLocked == null) {
              try {
                printWriter.println("waiting for notification to post...");
                Thread.sleep(500L);
              } catch (InterruptedException unused) {
              }
              findNotificationLocked =
                  this.mDirectService.findNotificationLocked(
                      str, nextArg2, 2020, UserHandle.getUserId(i));
              i2 = i3;
            }
          }
          if (findNotificationLocked == null) {
            printWriter.println("warning: couldn't find notification after enqueueing");
          } else {
            printWriter.println("posted: ");
            findNotificationLocked.dump(printWriter, "  ", context, false);
            return 0;
          }
        }
        return 0;
      }
    }
  }

  public final void waitForSnooze(ShellNls shellNls, String str) {
    for (int i = 0; i < 20; i++) {
      for (StatusBarNotification statusBarNotification : shellNls.getSnoozedNotifications()) {
        if (statusBarNotification.getKey().equals(str)) {
          return;
        }
      }
      try {
        Thread.sleep(100L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public final boolean waitForBind(ShellNls shellNls) {
    for (int i = 0; i < 20; i++) {
      if (shellNls.isConnected) {
        Slog.i("NotifShellCmd", "Bound Shell NLS");
        return true;
      }
      try {
        Thread.sleep(100L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  public final void waitForUnbind(ShellNls shellNls) {
    for (int i = 0; i < 10; i++) {
      if (!shellNls.isConnected) {
        Slog.i("NotifShellCmd", "Unbound Shell NLS");
        return;
      }
      try {
        Thread.sleep(100L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void onHelp() {
    getOutPrintWriter()
        .println(
            "usage: cmd notification SUBCMD [args]\n\n"
                + "SUBCMDs:\n"
                + "  allow_listener COMPONENT [user_id (current user if not specified)]\n"
                + "  disallow_listener COMPONENT [user_id (current user if not specified)]\n"
                + "  allow_assistant COMPONENT [user_id (current user if not specified)]\n"
                + "  remove_assistant COMPONENT [user_id (current user if not specified)]\n"
                + "  set_dnd [on|none (same as on)|priority|alarms|all|off (same as all)] "
                + " allow_dnd PACKAGE [user_id (current user if not specified)]\n"
                + "  disallow_dnd PACKAGE [user_id (current user if not specified)]\n"
                + "  reset_assistant_user_set [user_id (current user if not specified)]\n"
                + "  get_approved_assistant [user_id (current user if not specified)]\n"
                + "  post [--help | flags] TAG TEXT\n"
                + "  set_bubbles PACKAGE PREFERENCE (0=none 1=all 2=selected) [user_id (current"
                + " user if not specified)]\n"
                + "  set_bubbles_channel PACKAGE CHANNEL_ID ALLOW [user_id (current user if not"
                + " specified)]\n"
                + "  list\n"
                + "  get <notification-key>\n"
                + "  snooze --for <msec> <notification-key>\n"
                + "  unsnooze <notification-key>\n");
  }

  public class ShellNls extends NotificationListenerService {
    public static ShellNls sNotificationListenerInstance;
    public boolean isConnected;

    public ShellNls() {}

    @Override // android.service.notification.NotificationListenerService
    public void onListenerConnected() {
      super.onListenerConnected();
      sNotificationListenerInstance = this;
      this.isConnected = true;
    }

    @Override // android.service.notification.NotificationListenerService
    public void onListenerDisconnected() {
      this.isConnected = false;
    }
  }
}
