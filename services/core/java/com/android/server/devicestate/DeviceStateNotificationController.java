package com.android.server.devicestate;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.util.Slog;
import android.util.SparseArray;
import java.util.Locale;

/* loaded from: classes2.dex */
public class DeviceStateNotificationController extends BroadcastReceiver {
  static final String CHANNEL_ID = "DeviceStateManager";
  static final String INTENT_ACTION_CANCEL_STATE =
      "com.android.server.devicestate.INTENT_ACTION_CANCEL_STATE";
  static final int NOTIFICATION_ID = 1;
  static final String NOTIFICATION_TAG = "DeviceStateManager";
  public final Runnable mCancelStateRunnable;
  public final Context mContext;
  public final Handler mHandler;
  public final NotificationInfoProvider mNotificationInfoProvider;
  public final NotificationManager mNotificationManager;
  public final PackageManager mPackageManager;

  public DeviceStateNotificationController(Context context, Handler handler, Runnable runnable) {
    this(
        context,
        handler,
        runnable,
        new NotificationInfoProvider(context),
        context.getPackageManager(),
        (NotificationManager) context.getSystemService(NotificationManager.class));
  }

  public DeviceStateNotificationController(
      Context context,
      Handler handler,
      Runnable runnable,
      NotificationInfoProvider notificationInfoProvider,
      PackageManager packageManager,
      NotificationManager notificationManager) {
    this.mContext = context;
    this.mHandler = handler;
    this.mCancelStateRunnable = runnable;
    this.mNotificationInfoProvider = notificationInfoProvider;
    this.mPackageManager = packageManager;
    this.mNotificationManager = notificationManager;
    context.registerReceiver(
        this,
        new IntentFilter(INTENT_ACTION_CANCEL_STATE),
        "android.permission.CONTROL_DEVICE_STATE",
        handler,
        4);
  }

  public void showStateActiveNotificationIfNeeded(int i, int i2) {
    NotificationInfo notificationInfo = (NotificationInfo) getNotificationInfos().get(i);
    if (notificationInfo == null || !notificationInfo.hasActiveNotification()) {
      return;
    }
    String applicationLabel = getApplicationLabel(i2);
    if (applicationLabel != null) {
      showNotification(
          notificationInfo.name,
          notificationInfo.activeNotificationTitle,
          String.format(notificationInfo.activeNotificationContent, applicationLabel),
          true,
          R.drawable.ic_dialog_time,
          PendingIntent.getBroadcast(
              this.mContext,
              0,
              new Intent(INTENT_ACTION_CANCEL_STATE).setPackage(this.mContext.getPackageName()),
              67108864),
          this.mContext.getString(R.string.foreground_service_app_in_background));
    } else {
      Slog.e(
          "DeviceStateNotificationController",
          "Cannot determine the requesting app name when showing state active notification. uid="
              + i2
              + ", state="
              + i);
    }
  }

  public void showThermalCriticalNotificationIfNeeded(int i) {
    NotificationInfo notificationInfo = (NotificationInfo) getNotificationInfos().get(i);
    if (notificationInfo == null || !notificationInfo.hasThermalCriticalNotification()) {
      return;
    }
    showNotification(
        notificationInfo.name,
        notificationInfo.thermalCriticalNotificationTitle,
        notificationInfo.thermalCriticalNotificationContent,
        false,
        R.drawable.ic_suggestions_add,
        null,
        null);
  }

  public void showPowerSaveNotificationIfNeeded(int i) {
    NotificationInfo notificationInfo = (NotificationInfo) getNotificationInfos().get(i);
    if (notificationInfo == null || !notificationInfo.hasPowerSaveModeNotification()) {
      return;
    }
    showNotification(
        notificationInfo.name,
        notificationInfo.powerSaveModeNotificationTitle,
        notificationInfo.powerSaveModeNotificationContent,
        false,
        R.drawable.ic_suggestions_add,
        PendingIntent.getActivity(
            this.mContext, 0, new Intent("android.settings.BATTERY_SAVER_SETTINGS"), 67108864),
        this.mContext.getString(R.string.force_close));
  }

  public void cancelNotification(int i) {
    if (getNotificationInfos().get(i) == null) {
      return;
    }
    this.mNotificationManager.cancel("DeviceStateManager", 1);
  }

  @Override // android.content.BroadcastReceiver
  public void onReceive(Context context, Intent intent) {
    if (intent == null || !INTENT_ACTION_CANCEL_STATE.equals(intent.getAction())) {
      return;
    }
    this.mCancelStateRunnable.run();
  }

  public final void showNotification(
      String str,
      String str2,
      String str3,
      boolean z,
      int i,
      PendingIntent pendingIntent,
      String str4) {
    NotificationChannel notificationChannel = new NotificationChannel("DeviceStateManager", str, 4);
    Notification.Builder category =
        new Notification.Builder(this.mContext, "DeviceStateManager")
            .setSmallIcon(i)
            .setContentTitle(str2)
            .setContentText(str3)
            .setSubText(str)
            .setLocalOnly(true)
            .setOngoing(z)
            .setCategory("sys");
    if (pendingIntent != null && str4 != null) {
      category.addAction(new Notification.Action.Builder((Icon) null, str4, pendingIntent).build());
    }
    this.mNotificationManager.createNotificationChannel(notificationChannel);
    this.mNotificationManager.notify("DeviceStateManager", 1, category.build());
  }

  public final SparseArray getNotificationInfos() {
    return this.mNotificationInfoProvider.getNotificationInfos(
        this.mContext.getResources().getConfiguration().getLocales().get(0));
  }

  public class NotificationInfoProvider {
    Locale mCachedLocale;
    public SparseArray mCachedNotificationInfos;
    public final Context mContext;
    public final Object mLock = new Object();

    public NotificationInfoProvider(Context context) {
      this.mContext = context;
    }

    public SparseArray getNotificationInfos(Locale locale) {
      SparseArray sparseArray;
      synchronized (this.mLock) {
        if (!locale.equals(this.mCachedLocale)) {
          refreshNotificationInfos(locale);
        }
        sparseArray = this.mCachedNotificationInfos;
      }
      return sparseArray;
    }

    public Locale getCachedLocale() {
      Locale locale;
      synchronized (this.mLock) {
        locale = this.mCachedLocale;
      }
      return locale;
    }

    public void refreshNotificationInfos(Locale locale) {
      synchronized (this.mLock) {
        this.mCachedLocale = locale;
        this.mCachedNotificationInfos = loadNotificationInfos();
      }
    }

    public SparseArray loadNotificationInfos() {
      SparseArray sparseArray = new SparseArray();
      int[] intArray = this.mContext.getResources().getIntArray(17236424);
      String[] stringArray = this.mContext.getResources().getStringArray(17236421);
      String[] stringArray2 = this.mContext.getResources().getStringArray(17236420);
      String[] stringArray3 = this.mContext.getResources().getStringArray(17236419);
      String[] stringArray4 = this.mContext.getResources().getStringArray(17236426);
      String[] stringArray5 = this.mContext.getResources().getStringArray(17236425);
      String[] stringArray6 = this.mContext.getResources().getStringArray(17236423);
      String[] stringArray7 = this.mContext.getResources().getStringArray(17236422);
      if (intArray.length != stringArray.length
          || intArray.length != stringArray2.length
          || intArray.length != stringArray3.length
          || intArray.length != stringArray4.length
          || intArray.length != stringArray5.length
          || intArray.length != stringArray6.length
          || intArray.length != stringArray7.length) {
        throw new IllegalStateException(
            "The length of state identifiers and notification texts must match!");
      }
      for (int i = 0; i < intArray.length; i++) {
        int i2 = intArray[i];
        if (i2 != -1) {
          sparseArray.put(
              i2,
              new NotificationInfo(
                  stringArray[i],
                  stringArray2[i],
                  stringArray3[i],
                  stringArray4[i],
                  stringArray5[i],
                  stringArray6[i],
                  stringArray7[i]));
        }
      }
      return sparseArray;
    }
  }

  public final String getApplicationLabel(int i) {
    try {
      return this.mPackageManager
          .getApplicationInfo(
              this.mPackageManager.getNameForUid(i), PackageManager.ApplicationInfoFlags.of(0L))
          .loadLabel(this.mPackageManager)
          .toString();
    } catch (PackageManager.NameNotFoundException unused) {
      return null;
    }
  }

  class NotificationInfo {
    public final String activeNotificationContent;
    public final String activeNotificationTitle;
    public final String name;
    public final String powerSaveModeNotificationContent;
    public final String powerSaveModeNotificationTitle;
    public final String thermalCriticalNotificationContent;
    public final String thermalCriticalNotificationTitle;

    public NotificationInfo(
        String str, String str2, String str3, String str4, String str5, String str6, String str7) {
      this.name = str;
      this.activeNotificationTitle = str2;
      this.activeNotificationContent = str3;
      this.thermalCriticalNotificationTitle = str4;
      this.thermalCriticalNotificationContent = str5;
      this.powerSaveModeNotificationTitle = str6;
      this.powerSaveModeNotificationContent = str7;
    }

    public boolean hasActiveNotification() {
      String str = this.activeNotificationTitle;
      return str != null && str.length() > 0;
    }

    public boolean hasThermalCriticalNotification() {
      String str = this.thermalCriticalNotificationTitle;
      return str != null && str.length() > 0;
    }

    public boolean hasPowerSaveModeNotification() {
      String str = this.powerSaveModeNotificationTitle;
      return str != null && str.length() > 0;
    }
  }
}
