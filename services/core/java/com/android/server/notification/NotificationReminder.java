package com.android.server.notification;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.IRingtonePlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.VibrationEffect;
import android.os.IInstalld;
import android.provider.Settings;
import android.provider.Telephony;
import android.telecom.TelecomManager;
import android.util.Log;
import android.util.Slog;
import android.view.HapticFeedbackConstants;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.sepunion.SemGoodCatchManager;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class NotificationReminder {
  public AlarmManager mAlarmManager;
  public String mAppSettingList;
  public AudioManager mAudioManager;
  public Context mContext;
  public boolean mEnableReminder;
  public boolean mEnableVibrate;
  public final Handler mHandler;
  public int mInterval;
  public boolean mNeedMigration;
  public final NotificationReminderObserver mNotificationReminderObserver;
  public PreferencesHelper mPreferencesHelper;
  public PendingIntent mReminderIntent;
  public final BroadcastReceiver mReminderReceiver;
  public VibratorHelper mVibratorHelper;
  public static final Intent notificationIntent =
      new Intent("com.samsung.action.Notification_Reminder_Alarm").setPackage("android");
  public static final VibrationEffect REMINDER_VIBRATION_EFFECT =
      VibrationEffect.semCreateWaveform(
          HapticFeedbackConstants.semGetVibrationIndex(7),
          -1,
          VibrationEffect.SemMagnitudeType.TYPE_NOTIFICATION);
  public boolean isAlarmWorking = false;
  public final ArrayList mActiveNotiList = new ArrayList();
  public int mReminderType = -1;

  public NotificationReminder(
      Context context,
      Looper looper,
      PreferencesHelper preferencesHelper,
      AlarmManager alarmManager,
      VibratorHelper vibratorHelper) {
    BroadcastReceiver broadcastReceiver =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.notification.NotificationReminder.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            String action = intent.getAction();
            Slog.d("NotificationReminder", "mReminderReceiver, action = " + action);
            if (action.equals("com.samsung.action.Notification_Reminder_Alarm")) {
              NotificationReminder.this.mHandler.post(
                  new Runnable() { // from class:
                                   // com.android.server.notification.NotificationReminder.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                      synchronized (NotificationReminder.this.mActiveNotiList) {
                        NotificationReminder notificationReminder = NotificationReminder.this;
                        if (notificationReminder.isRemindNeeded(
                            notificationReminder.mActiveNotiList)) {
                          NotificationReminder.this.playSoundVibration();
                          NotificationReminder.this.cancelAlarm();
                          NotificationReminder.this.setReminderAlarm();
                        } else {
                          NotificationReminder.this.cancelAlarm();
                        }
                      }
                    }
                  });
            }
          }
        };
    this.mReminderReceiver = broadcastReceiver;
    this.mContext = context;
    Handler handler =
        new Handler(looper) { // from class: com.android.server.notification.NotificationReminder.2
          @Override // android.os.Handler
          public void handleMessage(Message message) {
            switch (message.what) {
              case 1000:
              case 1002:
                if (NotificationReminder.this.mEnableReminder) {
                  NotificationReminder notificationReminder = NotificationReminder.this;
                  if (notificationReminder.isRemindNeeded(notificationReminder.mActiveNotiList)) {
                    NotificationReminder.this.setReminderAlarm();
                    break;
                  }
                }
                break;
              case 1001:
              case 1003:
                if (NotificationReminder.this.mEnableReminder) {
                  NotificationReminder notificationReminder2 = NotificationReminder.this;
                  if (!notificationReminder2.isRemindNeeded(
                      notificationReminder2.mActiveNotiList)) {
                    NotificationReminder.this.cancelAlarm();
                    break;
                  }
                }
                break;
              case 1004:
                if (NotificationReminder.this.mEnableReminder) {
                  NotificationReminder.this.cancelAlarm();
                  NotificationReminder.this.setReminderAlarm();
                  break;
                }
                break;
            }
          }
        };
    this.mHandler = handler;
    this.mPreferencesHelper = preferencesHelper;
    this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
    this.mVibratorHelper = vibratorHelper;
    this.mAlarmManager = alarmManager;
    NotificationReminderObserver notificationReminderObserver =
        new NotificationReminderObserver(handler);
    this.mNotificationReminderObserver = notificationReminderObserver;
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Settings.System.getUriFor("notification_reminder_selectable"),
            false,
            notificationReminderObserver,
            -1);
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Settings.System.getUriFor("notification_reminder_type"),
            false,
            notificationReminderObserver,
            -1);
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Settings.System.getUriFor("notification_reminder_app_list"),
            false,
            notificationReminderObserver,
            -1);
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Settings.System.getUriFor("notification_reminder_vibrate"),
            false,
            notificationReminderObserver,
            -1);
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Settings.System.getUriFor("time_key"), false, notificationReminderObserver, -1);
    notificationReminderObserver.update(null);
    IntentFilter intentFilter = new IntentFilter("com.samsung.action.Notification_Reminder_Alarm");
    intentFilter.addAction("android.intent.action.USER_SWITCHED");
    this.mContext.registerReceiver(broadcastReceiver, intentFilter);
    startMigrationIfNeeded();
  }

  public void addNotificationRecord(final NotificationRecord notificationRecord) {
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.notification.NotificationReminder.3
          @Override // java.lang.Runnable
          public void run() {
            synchronized (NotificationReminder.this.mActiveNotiList) {
              Slog.d(
                  "NotificationReminder",
                  "addNotificationRecord record " + notificationRecord.getSbn().getPackageName());
              NotificationReminder.this.mActiveNotiList.add(notificationRecord);
              NotificationReminder.this.sendMessage(1000);
            }
          }
        });
  }

  public void removeFromNotificationList(final NotificationRecord notificationRecord) {
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.notification.NotificationReminder.4
          @Override // java.lang.Runnable
          public void run() {
            synchronized (NotificationReminder.this.mActiveNotiList) {
              Slog.d(
                  "NotificationReminder",
                  "removeFromNotificationList record "
                      + notificationRecord.getSbn().getPackageName());
              NotificationReminder notificationReminder = NotificationReminder.this;
              NotificationReminder.this.mActiveNotiList.remove(
                  notificationReminder.findNotificationByListLocked(
                      notificationReminder.mActiveNotiList, notificationRecord.getKey()));
              NotificationReminder.this.sendMessage(1001);
            }
          }
        });
  }

  public void setReminderAppEnabled(String str, int i, boolean z) {
    Log.d(
        "NotificationReminder",
        "setReminderAppEnabled - pkg:" + str + " uid:" + i + " enabled:" + z);
    if (z) {
      sendMessage(1002);
    } else {
      sendMessage(1003);
    }
  }

  public void dump(PrintWriter printWriter) {
    int size = this.mActiveNotiList.size();
    printWriter.println("\n  NotificationReminder :");
    printWriter.println("      EnableReminder = " + this.mEnableReminder);
    printWriter.println("      Interval = " + this.mInterval);
    printWriter.println("      EnableVibrate = " + this.mEnableVibrate);
    if (this.mEnableReminder) {
      printWriter.println("      mActiveNotiList, size = " + size);
      if (size > 0) {
        for (int i = 0; i < size; i++) {
          printWriter.println(
              "      key : " + ((NotificationRecord) this.mActiveNotiList.get(i)).getKey());
        }
        printWriter.println("  ");
      }
    }
  }

  public void updatePackageListForRestore() {
    this.mHandler.postDelayed(
        new Runnable() { // from class: com.android.server.notification.NotificationReminder.5
          @Override // java.lang.Runnable
          public void run() {
            new UpdateEnablePackageTask().execute(new Void[0]);
          }
        },
        30000L);
  }

  public final void startMigrationIfNeeded() {
    if (this.mReminderType != 1) {
      this.mPreferencesHelper.removeAllReminderSetting(ActivityManager.getCurrentUser());
    }
    boolean z =
        Settings.Secure.getIntForUser(
                this.mContext.getContentResolver(), "reminder_migration_finished", 0, -2)
            == 0;
    this.mNeedMigration = z;
    if (z) {
      this.mHandler.postDelayed(
          new Runnable() { // from class: com.android.server.notification.NotificationReminder.6
            @Override // java.lang.Runnable
            public void run() {
              new UpdateEnablePackageTask().execute(new Void[0]);
            }
          },
          30000L);
    }
  }

  public final NotificationRecord findNotificationByListLocked(ArrayList arrayList, String str) {
    int size = arrayList.size();
    for (int i = 0; i < size; i++) {
      if (str.equals(((NotificationRecord) arrayList.get(i)).getKey())) {
        return (NotificationRecord) arrayList.get(i);
      }
    }
    return null;
  }

  public final void setReminderAlarm() {
    if (this.mReminderIntent != null) {
      Slog.e("NotificationReminder", "Reminder alarm is exist before calling setReminder Alarm.");
      return;
    }
    Slog.i("NotificationReminder", "setReminderAlarm");
    this.mReminderIntent =
        PendingIntent.getBroadcast(this.mContext, 0, notificationIntent, 301989888);
    if (this.mInterval < 180) {
      this.mInterval = 180;
      Settings.System.putIntForUser(
          this.mContext.getContentResolver(), "time_key", this.mInterval, -2);
      Slog.w(
          "NotificationReminder",
          "The interval is set to the wrong value, so it is reset to the default interval"
              + " value.(180 sec).");
    }
    if (this.mAlarmManager.canScheduleExactAlarms()) {
      this.mAlarmManager.setExactAndAllowWhileIdle(
          2, SystemClock.elapsedRealtime() + (this.mInterval * 1000), this.mReminderIntent);
    } else {
      this.mAlarmManager.setAndAllowWhileIdle(
          2, SystemClock.elapsedRealtime() + (this.mInterval * 1000), this.mReminderIntent);
    }
  }

  public final void cancelAlarm() {
    Log.d("NotificationReminder", "cancelAlarm");
    if (this.mReminderIntent != null) {
      Log.d("NotificationReminder", "cancelAlarm - cancel Alarm");
      this.mAlarmManager.cancel(this.mReminderIntent);
      this.mReminderIntent = null;
    }
  }

  public final void playSoundVibration() {
    Uri defaultUri;
    long clearCallingIdentity;
    if (this.mAudioManager.getRingerModeInternal() == 1) {
      clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        Log.d("NotificationReminder", "playVibration only");
        this.mVibratorHelper.vibrate(
            REMINDER_VIBRATION_EFFECT,
            Notification.AUDIO_ATTRIBUTES_DEFAULT,
            "NotificationReminder(com.android.settings)");
        return;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
    if (this.mAudioManager.getRingerModeInternal() != 2
        || this.mAudioManager.getStreamVolume(
                AudioAttributes.toLegacyStreamType(Notification.AUDIO_ATTRIBUTES_DEFAULT))
            <= 0) {
      return;
    }
    if (RingtoneManager.getActualDefaultRingtoneUri(this.mContext, 2) != null) {
      defaultUri = RingtoneManager.getActualDefaultRingtoneUri(this.mContext, 2);
    } else {
      defaultUri = RingtoneManager.getDefaultUri(2);
    }
    clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      IRingtonePlayer ringtonePlayer = this.mAudioManager.getRingtonePlayer();
      if (ringtonePlayer != null) {
        Log.d("NotificationReminder", "playSound");
        ringtonePlayer.playAsync(
            defaultUri, UserHandle.ALL, false, Notification.AUDIO_ATTRIBUTES_DEFAULT);
        SemGoodCatchManager semGoodCatchManager =
            NotificationManagerService.mSemAudioGoodCatchManager;
        if (semGoodCatchManager != null) {
          semGoodCatchManager.update(
              "playback",
              "com.android.settings",
              AudioAttributes.toLegacyStreamType(Notification.AUDIO_ATTRIBUTES_DEFAULT),
              "",
              "repeat_notification_alerts");
        } else {
          Log.w("NotificationReminder", "SemAudioGoodCatchManager has not created");
        }
      }
      if (this.mEnableVibrate) {
        Log.d("NotificationReminder", "playVibration");
        this.mVibratorHelper.vibrate(
            REMINDER_VIBRATION_EFFECT,
            Notification.AUDIO_ATTRIBUTES_DEFAULT,
            "NotificationReminder(com.android.settings)");
      }
    } catch (RemoteException unused) {
    } catch (Throwable th) {
      throw th;
    }
  }

  public final boolean isRemindNeeded(ArrayList arrayList) {
    if (arrayList.isEmpty()) {
      Log.d("NotificationReminder", "The active notification list is empty.");
      return false;
    }
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      NotificationRecord notificationRecord = (NotificationRecord) it.next();
      Notification notification = notificationRecord.getSbn().getNotification();
      if (!isGroupSummaryNotification(notification)
          && !isSuppressedBubbleNotification(notification)
          && notificationRecord.getSbn().isClearable()
          && !notificationRecord.isIntercepted()
          && this.mPreferencesHelper.isReminderEnabled(
              notificationRecord.getSbn().getPackageName(), notificationRecord.getUid())) {
        Log.d(
            "NotificationReminder",
            "Clearable checked item found: " + notificationRecord.getSbn().getPackageName());
        return true;
      }
    }
    return false;
  }

  public final boolean isGroupSummaryNotification(Notification notification) {
    return (notification.flags & 512) != 0;
  }

  public final boolean isSuppressedBubbleNotification(Notification notification) {
    if ((notification.flags & IInstalld.FLAG_USE_QUOTA) != 0) {
      if (notification.getBubbleMetadata() != null) {
        return notification.getBubbleMetadata().isNotificationSuppressed();
      }
      Log.w("NotificationReminder", "bubbleMetadata is null.");
    }
    return false;
  }

  public final String getCallAndMsgPackage(Context context) {
    ArrayList<String> arrayList = new ArrayList();
    arrayList.add("com.samsung.android.messaging");
    arrayList.add("com.samsung.android.dialer");
    String defaultSmsPackage = Telephony.Sms.getDefaultSmsPackage(context);
    Log.d("NotificationReminder", "defaultSms : " + defaultSmsPackage);
    arrayList.add(defaultSmsPackage);
    try {
      String defaultDialerPackage =
          ((TelecomManager) context.getSystemService(TelecomManager.class))
              .getDefaultDialerPackage();
      Log.d("NotificationReminder", "defaultDialer : " + defaultDialerPackage);
      arrayList.add(defaultDialerPackage);
    } catch (Exception e) {
      Log.e(
          "NotificationReminder",
          "Unexpected exception occurred when getting default dialer package.",
          e);
    }
    StringBuffer stringBuffer = new StringBuffer();
    for (String str : arrayList) {
      if (stringBuffer.length() != 0) {
        stringBuffer.append(KnoxVpnFirewallHelper.DELIMITER);
      }
      stringBuffer.append(str);
    }
    return stringBuffer.toString();
  }

  public class UpdateEnablePackageTask extends AsyncTask {
    public UpdateEnablePackageTask() {}

    @Override // android.os.AsyncTask
    public Void doInBackground(Void... voidArr) {
      Log.d("NotificationReminder", "updateEnablePackageList start");
      NotificationReminder.this.updateEnablePackageList();
      Log.d("NotificationReminder", "updateEnablePackageList finish");
      return null;
    }
  }

  public final void updateEnablePackageList() {
    String str;
    int i = this.mReminderType;
    if (i == 0) {
      str = getCallAndMsgPackage(this.mContext);
    } else {
      str = i == 1 ? this.mAppSettingList : "";
    }
    PackageManager packageManager = this.mContext.getPackageManager();
    Intent intent = new Intent("android.intent.action.MAIN");
    intent.addCategory("android.intent.category.LAUNCHER");
    List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
    if ("selectAll".equalsIgnoreCase(str)) {
      Iterator<ResolveInfo> it = queryIntentActivities.iterator();
      while (it.hasNext()) {
        ActivityInfo activityInfo = it.next().activityInfo;
        String str2 = activityInfo.name;
        String str3 = activityInfo.packageName;
        Log.d("NotificationReminder", "updateEnablePackageList SELECT_All pkg = " + str3);
        if (str2 != null) {
          try {
            this.mPreferencesHelper.setReminderEnabled(
                str3,
                packageManager.getPackageUidAsUser(str3, ActivityManager.getCurrentUser()),
                true);
          } catch (PackageManager.NameNotFoundException unused) {
          }
        }
      }
    } else if (str != null) {
      ArrayList arrayList = new ArrayList();
      Iterator<ResolveInfo> it2 = queryIntentActivities.iterator();
      while (it2.hasNext()) {
        arrayList.add(it2.next().activityInfo.packageName);
      }
      List<String> asList = Arrays.asList(str.split(KnoxVpnFirewallHelper.DELIMITER));
      if (asList.size() > 0) {
        this.mPreferencesHelper.removeAllReminderSetting(ActivityManager.getCurrentUser());
        for (String str4 : asList) {
          Log.d("NotificationReminder", "updateEnablePackageList : " + str4);
          try {
            if (arrayList.contains(str4)) {
              Log.d("NotificationReminder", "installPackages : " + str4);
              this.mPreferencesHelper.setReminderEnabled(
                  str4,
                  this.mContext
                      .getPackageManager()
                      .getPackageUidAsUser(str4, ActivityManager.getCurrentUser()),
                  true);
            }
          } catch (PackageManager.NameNotFoundException unused2) {
          }
        }
      }
    }
    if (this.mNeedMigration) {
      Settings.Secure.putIntForUser(
          this.mContext.getContentResolver(), "reminder_migration_finished", 1, -2);
    }
  }

  public final void sendMessage(int i) {
    if (this.mHandler.hasMessages(i)) {
      this.mHandler.removeMessages(i);
    }
    this.mHandler.sendMessage(this.mHandler.obtainMessage(i));
  }

  public final class NotificationReminderObserver extends ContentObserver {
    public final Uri NOTIFICATION_REMINDER_APP_LIST;
    public final Uri NOTIFICATION_REMINDER_SELECTABLE;
    public final Uri NOTIFICATION_REMINDER_VIBRATE;
    public final Uri REMINDER_TYPE;
    public final Uri TIME_KEY;

    public NotificationReminderObserver(Handler handler) {
      super(handler);
      this.NOTIFICATION_REMINDER_SELECTABLE =
          Settings.System.getUriFor("notification_reminder_selectable");
      this.REMINDER_TYPE = Settings.System.getUriFor("notification_reminder_type");
      this.NOTIFICATION_REMINDER_APP_LIST =
          Settings.System.getUriFor("notification_reminder_app_list");
      this.NOTIFICATION_REMINDER_VIBRATE =
          Settings.System.getUriFor("notification_reminder_vibrate");
      this.TIME_KEY = Settings.System.getUriFor("time_key");
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri, int i) {
      update(uri);
    }

    public void update(Uri uri) {
      Slog.d("NotificationReminder", "update uri: " + uri);
      ContentResolver contentResolver = NotificationReminder.this.mContext.getContentResolver();
      if (uri == null || this.NOTIFICATION_REMINDER_SELECTABLE.equals(uri)) {
        NotificationReminder.this.mEnableReminder =
            Settings.System.getIntForUser(
                    contentResolver, "notification_reminder_selectable", 0, -2)
                != 0;
        Slog.d(
            "NotificationReminder",
            "update mEnableReminder: " + NotificationReminder.this.mEnableReminder);
        if (NotificationReminder.this.mEnableReminder) {
          NotificationReminder notificationReminder = NotificationReminder.this;
          if (notificationReminder.isRemindNeeded(notificationReminder.mActiveNotiList)) {
            NotificationReminder.this.setReminderAlarm();
          }
        } else {
          NotificationReminder.this.cancelAlarm();
        }
      }
      if (uri == null || this.NOTIFICATION_REMINDER_VIBRATE.equals(uri)) {
        NotificationReminder.this.mEnableVibrate =
            Settings.System.getIntForUser(contentResolver, "notification_reminder_vibrate", 0, -2)
                != 0;
        Slog.d(
            "NotificationReminder",
            "update mEnableVibrate: " + NotificationReminder.this.mEnableVibrate);
      }
      if (uri == null || this.TIME_KEY.equals(uri)) {
        NotificationReminder.this.mInterval =
            Settings.System.getIntForUser(contentResolver, "time_key", 180, -2);
        Slog.d("NotificationReminder", "update mInterval: " + NotificationReminder.this.mInterval);
        if (uri != null) {
          NotificationReminder.this.mHandler.post(
              new Runnable() { // from class:
                               // com.android.server.notification.NotificationReminder.NotificationReminderObserver.1
                @Override // java.lang.Runnable
                public void run() {
                  synchronized (NotificationReminder.this.mActiveNotiList) {
                    NotificationReminder.this.sendMessage(1004);
                  }
                }
              });
        }
      }
      if (uri == null || this.REMINDER_TYPE.equals(uri)) {
        NotificationReminder.this.mReminderType =
            Settings.System.getIntForUser(contentResolver, "notification_reminder_type", -1, -2);
        Slog.d(
            "NotificationReminder",
            "update mReminderType: " + NotificationReminder.this.mReminderType);
      }
      if (uri == null || this.NOTIFICATION_REMINDER_APP_LIST.equals(uri)) {
        NotificationReminder.this.mAppSettingList =
            Settings.System.getStringForUser(contentResolver, "notification_reminder_app_list", -2);
        Slog.d(
            "NotificationReminder",
            "update mAppSettingList: " + NotificationReminder.this.mAppSettingList);
      }
    }
  }
}
