package com.android.server.ibs.sleepmode;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IDeviceIdleController;
import android.os.Looper;
import android.os.Message;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class SleepModePolicyController {
  public AlarmManager mAlarmManager;
  public final SleepModeReceiver mBootCompleteReceiver;
  public final Context mContext;
  public LocalTime mEndTime;
  public final SleepModeHandler mHandler;
  public final HandlerThread mHandlerThread;
  public SleepModeLogger mLogger;
  public final SleepModeAction mSleepModeAction;
  public boolean mSleepModeEnabled;
  public LocalTime mStartTime;
  public final Object mActionsLock = new Object();
  public final AlarmManager.OnAlarmListener mTimeoutAlarmListener =
      new AlarmManager
          .OnAlarmListener() { // from class:
                               // com.android.server.ibs.sleepmode.SleepModePolicyController$$ExternalSyntheticLambda1
        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
          SleepModePolicyController.this.lambda$new$0();
        }
      };
  public boolean mInited = false;
  public int mSysState = 0;
  public IDeviceIdleController mDeviceIdleController =
      IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle"));
  public ArrayList mEntryArrayList = new ArrayList();
  public final SleepModeReceiver mReceiver = new SleepModeReceiver();
  public final SleepModeReceiver mDeviceStatusReceiver = new SleepModeReceiver();

  public interface SleepModeCallBack {
    void cancelAction();

    void triggerAction();
  }

  public SleepModePolicyController(
      Context context, HandlerThread handlerThread, SleepModeLogger sleepModeLogger) {
    this.mContext = context;
    this.mHandlerThread = handlerThread;
    this.mLogger = sleepModeLogger;
    this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
    SleepModeReceiver sleepModeReceiver = new SleepModeReceiver();
    this.mBootCompleteReceiver = sleepModeReceiver;
    SleepModeAction sleepModeAction = new SleepModeAction(context);
    this.mSleepModeAction = sleepModeAction;
    sleepModeAction.registerAction();
    this.mHandler = new SleepModeHandler(handlerThread.getLooper());
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
    context.registerReceiver(sleepModeReceiver, intentFilter);
  }

  public final void handlePackageRemovedEvent() {
    if (this.mInited) {
      initBroadcast(false);
      this.mInited = false;
    }
    if (isSleepModeActivated()) {
      exitSleepMode("reason_package_removed");
    }
    initAlarm(false);
    this.mSleepModeEnabled = false;
    SharePrefUtils.clear(this.mContext);
  }

  public final void handleBeforeBedtimeEvent() {
    Slog.i("SleepModePolicyController", "handleBeforeBedtimeEvent");
    setPreIdleTimeoutMode();
  }

  public final void handleProbablyAsleepEvent() {
    Slog.i("SleepModePolicyController", "handleProbablyAsleepEvent");
    if (!isIdleStatus() || isSleepModeActivated()) {
      return;
    }
    enterSleepMode();
  }

  public final void handleWakeupEvent() {
    Slog.i("SleepModePolicyController", "handleWakeupEvent");
    if (isSleepModeActivated()) {
      exitSleepMode("reason_wakeup");
    }
    resetPreIdleTimeoutMode();
  }

  public final void setPreIdleTimeoutMode() {
    try {
      initDeviceIdleController();
      this.mDeviceIdleController.setPreIdleTimeoutMode(2);
      SharePrefUtils.putBoolean(this.mContext, "pref_sleep_mode_idle_timeout_key", true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void resetPreIdleTimeoutMode() {
    if (SharePrefUtils.getBoolean(this.mContext, "pref_sleep_mode_idle_timeout_key", false)) {
      try {
        initDeviceIdleController();
        this.mDeviceIdleController.resetPreIdleTimeoutMode();
        SharePrefUtils.putBoolean(this.mContext, "pref_sleep_mode_idle_timeout_key", false);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public final void handleBootCompleteEvent() {
    Slog.d("SleepModePolicyController", "handleBootCompleteEvent");
    recoverSleepMode();
    boolean isSleepModeEnabled = isSleepModeEnabled();
    this.mSleepModeEnabled = isSleepModeEnabled;
    if (isSleepModeEnabled) {
      Slog.d("SleepModePolicyController", "sleep mode enabled!");
      this.mStartTime = getCustomStartTime();
      this.mEndTime = getCustomEndTime();
      this.mInited = true;
      initBroadcast(true);
      initAlarm(true);
    }
  }

  public final void initBroadcast(boolean z) {
    if (z) {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.TIME_SET");
      intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
      intentFilter.addAction("android.intent.action.REBOOT");
      intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
      this.mContext.registerReceiver(this.mReceiver, intentFilter);
      IntentFilter intentFilter2 = new IntentFilter();
      intentFilter2.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
      intentFilter2.addDataScheme("package");
      this.mContext.registerReceiver(this.mReceiver, intentFilter2);
      return;
    }
    this.mContext.unregisterReceiver(this.mReceiver);
  }

  public final void initDeviceStatusBroadcast(boolean z) {
    if (z) {
      IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_ON");
      intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
      this.mContext.registerReceiver(this.mDeviceStatusReceiver, intentFilter);
      return;
    }
    this.mContext.unregisterReceiver(this.mDeviceStatusReceiver);
  }

  public final boolean isIdleStatus() {
    boolean z =
        (!SleepModeUtil.isDeviceIdleMode(this.mContext)
                || SleepModeUtil.isScreenOn(this.mContext)
                || SleepModeUtil.isPowerConnected(this.mContext))
            ? false
            : true;
    Slog.d("SleepModePolicyController", "current idle status is " + z);
    return z;
  }

  public final boolean isSleepModeActivated() {
    boolean z = SharePrefUtils.getBoolean(this.mContext, "pref_sleep_mode_activated_key", false);
    Slog.d("SleepModePolicyController", "isSleepModeActivated to be " + z);
    return z;
  }

  public final boolean needRecover() {
    boolean z =
        !SleepModeUtil.isDeviceIdleMode(this.mContext)
            && (SleepModeUtil.isScreenOn(this.mContext)
                || SleepModeUtil.isPowerConnected(this.mContext));
    Slog.d("SleepModePolicyController", "revort state is " + z);
    return z;
  }

  public final void enterSleepMode() {
    if (!this.mSleepModeEnabled) {
      Slog.d("SleepModePolicyController", "UI switch off disable the sleep mode restriction.");
      return;
    }
    synchronized (this.mActionsLock) {
      this.mLogger.add("enterSleepMode");
      this.mSysState = 0;
      this.mEntryArrayList.forEach(
          new Consumer() { // from class:
                           // com.android.server.ibs.sleepmode.SleepModePolicyController$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              SleepModePolicyController.lambda$enterSleepMode$1(
                  (SleepModePolicyController.ActionEntry) obj);
            }
          });
      SharePrefUtils.putBoolean(this.mContext, "pref_sleep_mode_activated_key", true);
      SharePrefUtils.putLong(
          this.mContext, "pref_sleep_mode_trigger_time_key", SleepModeUtil.getCurTime());
      SharePrefUtils.putInt(this.mContext, "pref_sleep_mode_policy_state_key", this.mSysState);
      initDeviceStatusBroadcast(true);
    }
  }

  public static /* synthetic */ void lambda$enterSleepMode$1(ActionEntry actionEntry) {
    Slog.d("SleepModePolicyController", " do sleep mode restriction " + actionEntry.tag);
    actionEntry.callBack.triggerAction();
  }

  public final void recoverSleepMode() {
    if (isSleepModeActivated()) {
      synchronized (this.mActionsLock) {
        this.mLogger.add("recoverSleepMode");
        this.mEntryArrayList.forEach(
            new Consumer() { // from class:
                             // com.android.server.ibs.sleepmode.SleepModePolicyController$$ExternalSyntheticLambda3
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                SleepModePolicyController.lambda$recoverSleepMode$2(
                    (SleepModePolicyController.ActionEntry) obj);
              }
            });
        SharePrefUtils.putBoolean(this.mContext, "pref_sleep_mode_activated_key", false);
        SharePrefUtils.putLong(
            this.mContext, "pref_sleep_mode_cancel_time_key", SleepModeUtil.getCurTime());
      }
    }
  }

  public static /* synthetic */ void lambda$recoverSleepMode$2(ActionEntry actionEntry) {
    Slog.d("SleepModePolicyController", " cancel sleep mode restriction " + actionEntry.tag);
    actionEntry.callBack.cancelAction();
  }

  public final void exitSleepMode(String str) {
    if (!this.mSleepModeEnabled) {
      Slog.d(
          "SleepModePolicyController", "UI switch off disable the cancel sleep mode restriction.");
      return;
    }
    synchronized (this.mActionsLock) {
      this.mLogger.add("exitSleepMode " + str);
      this.mEntryArrayList.forEach(
          new Consumer() { // from class:
                           // com.android.server.ibs.sleepmode.SleepModePolicyController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              SleepModePolicyController.lambda$exitSleepMode$3(
                  (SleepModePolicyController.ActionEntry) obj);
            }
          });
      SharePrefUtils.putBoolean(this.mContext, "pref_sleep_mode_activated_key", false);
      SharePrefUtils.putLong(
          this.mContext, "pref_sleep_mode_cancel_time_key", SleepModeUtil.getCurTime());
      initDeviceStatusBroadcast(false);
    }
  }

  public static /* synthetic */ void lambda$exitSleepMode$3(ActionEntry actionEntry) {
    Slog.d("SleepModePolicyController", " cancel sleep mode restriction " + actionEntry.tag);
    actionEntry.callBack.cancelAction();
  }

  public final void sendExitSleepModeMessage(String str) {
    Slog.d("SleepModePolicyController", "sendExitSleepModeMessage");
    SleepModeHandler sleepModeHandler = this.mHandler;
    if (sleepModeHandler != null) {
      Message obtainMessage = sleepModeHandler.obtainMessage(9);
      obtainMessage.obj = str;
      this.mHandler.sendMessage(obtainMessage);
    }
  }

  public final void sendAlarmStartMessage() {
    Slog.d("SleepModePolicyController", "sendAlarmStartMessage");
    SleepModeHandler sleepModeHandler = this.mHandler;
    if (sleepModeHandler != null) {
      this.mHandler.sendMessage(sleepModeHandler.obtainMessage(3));
    }
  }

  public final void sendTimeChangedMessage() {
    Slog.d("SleepModePolicyController", "sendTimeChangedMessage");
    SleepModeHandler sleepModeHandler = this.mHandler;
    if (sleepModeHandler != null) {
      this.mHandler.sendMessage(sleepModeHandler.obtainMessage(8));
    }
  }

  public final void sendSetTimeMessage(long j, long j2) {
    Slog.d("SleepModePolicyController", "sendSetTimeMessage");
    if (this.mHandler != null) {
      ArrayMap arrayMap = new ArrayMap();
      arrayMap.put("start_time", LocalTime.valueOf(j));
      arrayMap.put("end_time", LocalTime.valueOf(j2));
      this.mHandler.sendMessage(this.mHandler.obtainMessage(7, arrayMap));
    }
  }

  public final void sendAlarmEndMessage() {
    Slog.d("SleepModePolicyController", "sendAlarmEndMessage");
    SleepModeHandler sleepModeHandler = this.mHandler;
    if (sleepModeHandler != null) {
      this.mHandler.sendMessage(sleepModeHandler.obtainMessage(4));
    }
  }

  public final void sendCheckStatusMessage(String str) {
    Slog.d("SleepModePolicyController", "sendCheckStatusMessage");
    SleepModeHandler sleepModeHandler = this.mHandler;
    if (sleepModeHandler != null) {
      Message obtainMessage = sleepModeHandler.obtainMessage(6);
      obtainMessage.obj = str;
      this.mHandler.sendMessage(obtainMessage);
    }
  }

  public final void sendBootCompleteMessage() {
    Slog.d("SleepModePolicyController", "sendBootCompleteMessage");
    SleepModeHandler sleepModeHandler = this.mHandler;
    if (sleepModeHandler != null) {
      this.mHandler.sendMessage(sleepModeHandler.obtainMessage(14));
    }
  }

  public final void sendIdleChangedMessage() {
    Slog.d("SleepModePolicyController", "sendIdleChangedMessage");
    SleepModeHandler sleepModeHandler = this.mHandler;
    if (sleepModeHandler != null) {
      this.mHandler.sendMessage(sleepModeHandler.obtainMessage(5));
    }
  }

  public final void sendPackageRemovedMessage(Intent intent) {
    Optional.ofNullable(intent.getData())
        .ifPresent(
            new Consumer() { // from class:
                             // com.android.server.ibs.sleepmode.SleepModePolicyController$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                SleepModePolicyController.this.lambda$sendPackageRemovedMessage$4((Uri) obj);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendPackageRemovedMessage$4(Uri uri) {
    if (!"com.samsung.android.statsd".equals(uri.getSchemeSpecificPart())
        || this.mHandler == null) {
      return;
    }
    Slog.d("SleepModePolicyController", "sendPackageRemovedMessage");
    this.mHandler.sendMessage(this.mHandler.obtainMessage(10));
  }

  public final LocalTime getCustomStartTime() {
    long j = SharePrefUtils.getLong(this.mContext, "pref_sleep_mode_start_time_key", 0L);
    if (j != 0) {
      return LocalTime.valueOf(j);
    }
    return null;
  }

  public final LocalTime getCustomEndTime() {
    long j = SharePrefUtils.getLong(this.mContext, "pref_sleep_mode_end_time_key", 0L);
    if (j != 0) {
      return LocalTime.valueOf(j);
    }
    return null;
  }

  public final boolean isSleepModeEnabled() {
    return SharePrefUtils.getBoolean(this.mContext, "pref_sleep_mode_enabled_key", false);
  }

  public final void initDeviceIdleController() {
    if (this.mDeviceIdleController == null) {
      try {
        this.mDeviceIdleController =
            IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle"));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /* renamed from: updateActivated, reason: merged with bridge method [inline-methods] */
  public final void lambda$new$0() {
    Slog.d("SleepModePolicyController", "updateActivated");
    if (this.mStartTime == null || this.mEndTime == null) {
      return;
    }
    Calendar calendar = Calendar.getInstance();
    Calendar dateTimeBefore = this.mStartTime.getDateTimeBefore(calendar);
    LocalTime localTime = this.mEndTime;
    updateNextAlarm(
        Boolean.valueOf(
            calendar.before(
                localTime.getDateTimeAfter(dateTimeBefore, this.mStartTime.equals(localTime)))),
        calendar);
  }

  public final void updateNextAlarm(Boolean bool, Calendar calendar) {
    Calendar dateTimeAfter;
    if (bool != null) {
      if (bool.booleanValue()) {
        LocalTime localTime = this.mEndTime;
        dateTimeAfter = localTime.getDateTimeAfter(calendar, this.mStartTime.equals(localTime));
      } else {
        dateTimeAfter = this.mStartTime.getDateTimeAfter(calendar, false);
      }
      if (bool.booleanValue()) {
        sendAlarmStartMessage();
        Slog.d(
            "SleepModePolicyController",
            "In Active Duration, set inactive alarm at "
                + dateTimeAfter.get(11)
                + XmlUtils.STRING_ARRAY_SEPARATOR
                + dateTimeAfter.get(12));
      } else {
        sendAlarmEndMessage();
        Slog.d(
            "SleepModePolicyController",
            "Out Active Duration, set active alarm at "
                + dateTimeAfter.get(11)
                + XmlUtils.STRING_ARRAY_SEPARATOR
                + dateTimeAfter.get(12));
      }
      this.mAlarmManager.setExact(
          0,
          dateTimeAfter.getTimeInMillis(),
          "SleepModePolicyController",
          this.mTimeoutAlarmListener,
          null);
    }
  }

  public final void initAlarm(boolean z) {
    AlarmManager.OnAlarmListener onAlarmListener = this.mTimeoutAlarmListener;
    if (onAlarmListener != null) {
      this.mAlarmManager.cancel(onAlarmListener);
    }
    if (z) {
      lambda$new$0();
    }
  }

  public void setSleepModeEnable(boolean z) {
    Slog.d("SleepModePolicyController", "setSleepModeEnable >> " + z);
    if (z) {
      startSleepModePolicy();
    } else {
      stopSleepModePolicy();
    }
    this.mSleepModeEnabled = z;
    SharePrefUtils.putBoolean(this.mContext, "pref_sleep_mode_enabled_key", z);
  }

  public void setSleepTime(long j, long j2) {
    Slog.d("SleepModePolicyController", "setSleepTime");
    sendSetTimeMessage(j, j2);
    SharePrefUtils.putLong(this.mContext, "pref_sleep_mode_start_time_key", j);
    SharePrefUtils.putLong(this.mContext, "pref_sleep_mode_end_time_key", j2);
  }

  public void setRubinEvent(String str) {
    if (!this.mSleepModeEnabled) {
      Slog.d("SleepModePolicyController", "sleep mode off, doesn't deal with runstone event!");
      return;
    }
    if (str.equalsIgnoreCase("BEFORE_BEDTIME")) {
      Slog.i("SleepModePolicyController", "BEFORE_BEDTIME");
      this.mHandler.sendEmptyMessage(11);
    } else if (str.equalsIgnoreCase("PROBABLY_ASLEEP")) {
      Slog.i("SleepModePolicyController", "PROBABLY_ASLEEP");
      this.mHandler.sendEmptyMessage(12);
    } else if (str.equalsIgnoreCase("WAKEUP")) {
      Slog.i("SleepModePolicyController", "WAKEUP");
      this.mHandler.sendEmptyMessage(13);
    }
  }

  public boolean isEnableSerive() {
    Slog.d("SleepModePolicyController", "isEnableSerive: " + this.mSleepModeEnabled);
    return this.mSleepModeEnabled;
  }

  public Bundle getOperationHistory() {
    Slog.d("SleepModePolicyController", "getOperationHistory");
    long j = SharePrefUtils.getLong(this.mContext, "pref_sleep_mode_trigger_time_key", 0L);
    long j2 = SharePrefUtils.getLong(this.mContext, "pref_sleep_mode_cancel_time_key", 0L);
    if (j == 0 || j2 == 0) {
      return null;
    }
    this.mSysState = SharePrefUtils.getInt(this.mContext, "pref_sleep_mode_policy_state_key", 0);
    Bundle bundle = new Bundle();
    bundle.putLong("bundle_start_time_key", j);
    bundle.putLong("bundle_end_time_key", j2);
    bundle.putBoolean("bundle_psm_key", testState(1));
    bundle.putBoolean("bundle_gps_key", testState(4));
    bundle.putBoolean("bundle_bt_key", testState(8));
    bundle.putBoolean("bundle_wifi_key", testState(2));
    bundle.putBoolean("bundle_nearby_key", testState(16));
    bundle.putBoolean("bundle_master_sync_key", testState(32));
    bundle.putBoolean("bundle_notification_key", testState(64));
    bundle.putBoolean("bundle_camera_flash_notification_key", testState(128));
    return bundle;
  }

  public Bundle getSleepTime() {
    Slog.d("SleepModePolicyController", "getSleepTime");
    long j = SharePrefUtils.getLong(this.mContext, "pref_sleep_mode_start_time_key", -1L);
    long j2 = SharePrefUtils.getLong(this.mContext, "pref_sleep_mode_end_time_key", -1L);
    if (j == -1 || j2 == -1) {
      return null;
    }
    Bundle bundle = new Bundle();
    bundle.putLong("bundle_start_sleep_time_key", j);
    bundle.putLong("bundle_end_sleep_time_key", j2);
    return bundle;
  }

  public final boolean isDuringUserSetupTime() {
    boolean z;
    if (this.mStartTime == null || this.mEndTime == null) {
      z = false;
    } else {
      Calendar calendar = Calendar.getInstance();
      Calendar dateTimeBefore = this.mStartTime.getDateTimeBefore(calendar);
      LocalTime localTime = this.mEndTime;
      z =
          calendar.before(
              localTime.getDateTimeAfter(dateTimeBefore, this.mStartTime.equals(localTime)));
    }
    Slog.d("SleepModePolicyController", "isDuringUserSetupTime: isInSetupTime = " + z);
    return z;
  }

  public final void setSysState(int i) {
    int i2 = i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    if (i2 != this.mSysState) {
      this.mSysState = i2;
    }
  }

  public final boolean testState(int i) {
    return (this.mSysState & i) == i;
  }

  public final void startSleepModePolicy() {
    Slog.d("SleepModePolicyController", "startSleepModePolicy");
    this.mHandler.sendEmptyMessage(1);
  }

  public final void stopSleepModePolicy() {
    Slog.d("SleepModePolicyController", "stopSleepModePolicy");
    this.mHandler.sendEmptyMessage(2);
  }

  public final void registerSleepModeAction(String str, SleepModeCallBack sleepModeCallBack) {
    synchronized (this.mActionsLock) {
      this.mEntryArrayList.add(new ActionEntry(str, sleepModeCallBack));
    }
  }

  public void dump(PrintWriter printWriter, String[] strArr) {
    printWriter.println("");
    printWriter.println("SleepModePolicy ");
    printWriter.println("get current state :" + this.mSleepModeEnabled);
    if (this.mSleepModeEnabled) {
      if (SleepModeUtil.getTime(this.mStartTime.toString())
          > SleepModeUtil.getTime(this.mEndTime.toString())) {
        printWriter.println("Set time from " + this.mStartTime + " to next day " + this.mEndTime);
      } else {
        printWriter.println("Set time from " + this.mStartTime + " to " + this.mEndTime);
      }
    }
    if (strArr.length == 2 && "sleepMode".equals(strArr[0])) {
      int parseInt = Integer.parseInt(strArr[1]);
      printWriter.println("set new state :" + parseInt);
      if (parseInt == 0) {
        setSleepModeEnable(false);
        printWriter.println("diable sleep mode");
        return;
      }
      return;
    }
    if (strArr.length == 4 && "sleepMode".equals(strArr[0])) {
      int parseInt2 = Integer.parseInt(strArr[1]);
      printWriter.println("set new state :" + parseInt2);
      if (parseInt2 == 1) {
        setSleepModeEnable(true);
        printWriter.println("enable sleep mode");
        setSleepTime(SleepModeUtil.getTime(strArr[2]), SleepModeUtil.getTime(strArr[3]));
        printWriter.println("set sleep time");
        return;
      }
      return;
    }
    if (strArr.length == 1 && "getSleepTime".equals(strArr[0])) {
      printWriter.println("get sleep time");
      Bundle sleepTime = getSleepTime();
      if (sleepTime != null) {
        printWriter.println("start time is: " + sleepTime.getLong("bundle_start_sleep_time_key"));
        printWriter.println("end time is: " + sleepTime.getLong("bundle_end_sleep_time_key"));
        return;
      }
      return;
    }
    if (strArr.length == 2 && "rubinEvent".equals(strArr[0])) {
      setRubinEvent(strArr[1]);
      printWriter.println("set runbin event");
    }
  }

  public class SleepModeReceiver extends BroadcastReceiver {
    public SleepModeReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      if (intent == null || intent.getAction() == null) {
        return;
      }
      Slog.d("SleepModePolicyController", "action is >> " + intent.getAction());
      String action = intent.getAction();
      action.hashCode();
      switch (action) {
        case "android.intent.action.SCREEN_ON":
          SleepModePolicyController.this.sendCheckStatusMessage("reason_screen_on");
          break;
        case "android.intent.action.TIME_SET":
          SleepModePolicyController.this.sendTimeChangedMessage();
          break;
        case "android.intent.action.BOOT_COMPLETED":
          SleepModePolicyController.this.sendBootCompleteMessage();
          break;
        case "android.os.action.DEVICE_IDLE_MODE_CHANGED":
          SleepModePolicyController.this.sendIdleChangedMessage();
          break;
        case "android.intent.action.ACTION_POWER_CONNECTED":
          SleepModePolicyController.this.sendCheckStatusMessage("reason_charging");
          break;
        case "android.intent.action.PACKAGE_FULLY_REMOVED":
          SleepModePolicyController.this.sendPackageRemovedMessage(intent);
          break;
        case "android.intent.action.ACTION_SHUTDOWN":
          SleepModePolicyController.this.sendExitSleepModeMessage("reason_shutdown");
          break;
        case "android.intent.action.REBOOT":
          SleepModePolicyController.this.sendExitSleepModeMessage("reason_reboot");
          break;
      }
    }
  }

  public class ActionEntry {
    public SleepModeCallBack callBack;
    public String tag;

    public ActionEntry(String str, SleepModeCallBack sleepModeCallBack) {
      this.tag = str;
      this.callBack = sleepModeCallBack;
    }

    public boolean equals(Object obj) {
      if (obj instanceof ActionEntry) {
        ActionEntry actionEntry = (ActionEntry) obj;
        return this.tag.equals(actionEntry.tag) && this.callBack == actionEntry.callBack;
      }
      return super.equals(obj);
    }

    public int hashCode() {
      String str = this.tag;
      return (str == null ? 0 : str.hashCode()) + 31;
    }
  }

  public class SleepModeHandler extends Handler {
    public SleepModeHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      switch (message.what) {
        case 1:
          SleepModePolicyController.this.handleSleepModeStartEvent();
          break;
        case 2:
          SleepModePolicyController.this.handleSleepModeStopEvent();
          break;
        case 3:
          SleepModePolicyController.this.handleAlarmStartEvent();
          break;
        case 4:
          SleepModePolicyController.this.handleAlarmEndEvent();
          break;
        case 5:
          SleepModePolicyController.this.handleIdleChangedEvent();
          break;
        case 6:
          SleepModePolicyController.this.handleStatusCheckEvent((String) message.obj);
          break;
        case 7:
          SleepModePolicyController.this.handleSetTimeEvent(message);
          break;
        case 8:
          SleepModePolicyController.this.handleTimeChangedEvent();
          break;
        case 9:
          SleepModePolicyController.this.handleSleepModeExitEvent((String) message.obj);
          break;
        case 10:
          SleepModePolicyController.this.handlePackageRemovedEvent();
          break;
        case 11:
          SleepModePolicyController.this.handleBeforeBedtimeEvent();
          break;
        case 12:
          SleepModePolicyController.this.handleProbablyAsleepEvent();
          break;
        case 13:
          SleepModePolicyController.this.handleWakeupEvent();
          break;
        case 14:
          SleepModePolicyController.this.handleBootCompleteEvent();
          break;
      }
    }
  }

  public final void handleSleepModeExitEvent(String str) {
    Slog.i("SleepModePolicyController", "handleSleepModeExitEvent");
    if (isSleepModeActivated()) {
      exitSleepMode(str);
    }
  }

  public final void handleIdleChangedEvent() {
    Slog.i("SleepModePolicyController", "handleIdleChangedEvent");
    boolean isSleepModeActivated = isSleepModeActivated();
    if (isIdleStatus()) {
      if (!isDuringUserSetupTime() || isSleepModeActivated) {
        return;
      }
      enterSleepMode();
      return;
    }
    if (isSleepModeActivated && needRecover()) {
      exitSleepMode("reason_idle_change");
    }
  }

  public final void handleStatusCheckEvent(String str) {
    Slog.i("SleepModePolicyController", "handleStatusCheckEvent");
    if (!isSleepModeActivated() || isIdleStatus()) {
      return;
    }
    exitSleepMode(str);
  }

  public final void handleTimeChangedEvent() {
    Slog.i("SleepModePolicyController", "handleTimeChangedEvent");
    initAlarm(true);
  }

  public final void handleSetTimeEvent(Message message) {
    Slog.i("SleepModePolicyController", "handleSetTimeEvent");
    ArrayMap arrayMap = (ArrayMap) message.obj;
    this.mStartTime = (LocalTime) arrayMap.get("start_time");
    this.mEndTime = (LocalTime) arrayMap.get("end_time");
    initAlarm(true);
  }

  public final void handleAlarmEndEvent() {
    Slog.i("SleepModePolicyController", "handleAlarmEndEvent");
    if (isSleepModeActivated()) {
      exitSleepMode("reason_alarm_end");
    }
    resetPreIdleTimeoutMode();
  }

  public final void handleAlarmStartEvent() {
    Slog.i("SleepModePolicyController", "handleAlarmStartEvent");
    if (isSleepModeActivated()) {
      return;
    }
    if (isIdleStatus()) {
      enterSleepMode();
    } else {
      setPreIdleTimeoutMode();
    }
  }

  public final void handleSleepModeStopEvent() {
    Slog.i("SleepModePolicyController", "handleSleepModeStopEvent");
    if (this.mInited) {
      initBroadcast(false);
      this.mInited = false;
    }
    initAlarm(false);
  }

  public final void handleSleepModeStartEvent() {
    Slog.i("SleepModePolicyController", "handleSleepModeStartEvent");
    if (this.mInited) {
      return;
    }
    initBroadcast(true);
    this.mInited = true;
  }

  public class LocalTime {
    public final int hourOfDay;
    public final int minute;

    public LocalTime(int i, int i2) {
      if (i < 0 || i > 23) {
        throw new IllegalArgumentException("Invalid hourOfDay: " + i);
      }
      if (i2 < 0 || i2 > 59) {
        throw new IllegalArgumentException("Invalid minute: " + i2);
      }
      this.hourOfDay = i;
      this.minute = i2;
    }

    public static LocalTime valueOf(long j) {
      return new LocalTime((int) (j / 60), (int) (j % 60));
    }

    public Calendar getDateTimeBefore(Calendar calendar) {
      Calendar calendar2 = Calendar.getInstance();
      calendar2.set(1, calendar.get(1));
      calendar2.set(6, calendar.get(6));
      calendar2.set(11, this.hourOfDay);
      calendar2.set(12, this.minute);
      calendar2.set(13, 0);
      calendar2.set(14, 0);
      if (calendar2.after(calendar)) {
        calendar2.add(5, -1);
      }
      return calendar2;
    }

    public Calendar getDateTimeAfter(Calendar calendar, boolean z) {
      Calendar calendar2 = Calendar.getInstance();
      calendar2.set(1, calendar.get(1));
      if (z) {
        calendar2.set(6, calendar.get(6) + 1);
      } else {
        calendar2.set(6, calendar.get(6));
      }
      calendar2.set(11, this.hourOfDay);
      calendar2.set(12, this.minute);
      calendar2.set(13, 0);
      calendar2.set(14, 0);
      if (calendar2.before(calendar)) {
        calendar2.add(5, 1);
      }
      return calendar2;
    }

    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      LocalTime localTime = (LocalTime) obj;
      return this.hourOfDay == localTime.hourOfDay && this.minute == localTime.minute;
    }

    public int hashCode() {
      return ((this.hourOfDay + 31) * 31) + this.minute;
    }

    public String toString() {
      return String.format(
          Locale.US, "%02d:%02d", Integer.valueOf(this.hourOfDay), Integer.valueOf(this.minute));
    }
  }

  public class SleepModeAction {
    public final SleepModeCallBack btCallBack;
    public final SleepModeCallBack cameraFlashNotificationCallBack;
    public final SleepModeCallBack gpsCallBack;
    public final Context mContext;
    public final SleepModeCallBack masterSyncCallBack;
    public final SleepModeCallBack nearbyCallBack;
    public final SleepModeCallBack notificationCallBack;
    public final SleepModeCallBack psmCallBack;
    public final SleepModeCallBack wifiCallBack;

    public SleepModeAction(final Context context) {
      this.mContext = context;
      this.psmCallBack =
          new SleepModeCallBack() { // from class:
                                    // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeAction.1
            @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
            public void triggerAction() {
              Slog.d("SleepModePolicyController", "PSM triggerAction");
              boolean isPsmEnabled = SleepModeUtil.isPsmEnabled(SleepModeAction.this.mContext);
              boolean isUpsmEnabled = SleepModeUtil.isUpsmEnabled(SleepModeAction.this.mContext);
              boolean isEmergencyModeEnabled =
                  SleepModeUtil.isEmergencyModeEnabled(SleepModeAction.this.mContext);
              boolean isLimitAppsAndHome = SleepModeUtil.isLimitAppsAndHome(context);
              if (isLimitAppsAndHome || isPsmEnabled || isUpsmEnabled || isEmergencyModeEnabled) {
                SharePrefUtils.putBoolean(
                    SleepModeAction.this.mContext, "pref_sleep_mode_psm_key", false);
                if (isLimitAppsAndHome) {
                  Slog.d(
                      "SleepModePolicyController",
                      ": limit app and home enabled. It will not enable PSM control by SleepPSM");
                  return;
                } else {
                  Slog.d(
                      "SleepModePolicyController",
                      ": already PSM enabled. It will not enable PSM control by SleepPSM");
                  return;
                }
              }
              if (SleepModeUtil.handlePowerSavingModeViaApi(SleepModeAction.this.mContext, true)) {
                SharePrefUtils.putBoolean(
                    SleepModeAction.this.mContext, "pref_sleep_mode_psm_key", true);
                SleepModePolicyController sleepModePolicyController =
                    SleepModePolicyController.this;
                sleepModePolicyController.setSysState(1 | sleepModePolicyController.mSysState);
                SleepModePolicyController.this.mLogger.add("Enable low power mode");
              }
            }

            @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
            public void cancelAction() {
              Slog.d("SleepModePolicyController", "PSM cancelAction");
              if (SharePrefUtils.getBoolean(context, "pref_sleep_mode_psm_key", false)) {
                SleepModeUtil.handlePowerSavingModeViaApi(SleepModeAction.this.mContext, false);
                SleepModePolicyController.this.mLogger.add("Disable low power mode");
              }
              SharePrefUtils.putBoolean(
                  SleepModeAction.this.mContext, "pref_sleep_mode_psm_key", false);
            }
          };
      this.gpsCallBack =
          new SleepModeCallBack() { // from class:
                                    // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeAction.2
            @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
            public void triggerAction() {
              int i;
              boolean z;
              Slog.d("SleepModePolicyController", "GPS triggerAction");
              LocationManager locationManager =
                  (LocationManager) SleepModeAction.this.mContext.getSystemService("location");
              if (locationManager != null) {
                z = locationManager.isLocationEnabled();
                i =
                    Settings.Secure.getInt(
                        SleepModeAction.this.mContext.getContentResolver(), "location_mode", 0);
              } else {
                i =
                    Settings.Secure.getInt(
                        SleepModeAction.this.mContext.getContentResolver(), "location_mode", 0);
                z = i != 0;
              }
              if (z) {
                Settings.Secure.putInt(
                    SleepModeAction.this.mContext.getContentResolver(), "location_mode", 0);
                SharePrefUtils.putInt(
                    SleepModeAction.this.mContext, "pref_sleep_mode_location_key", i);
                SleepModePolicyController.this.mLogger.add("Disable GPS");
                SleepModePolicyController sleepModePolicyController =
                    SleepModePolicyController.this;
                sleepModePolicyController.setSysState(sleepModePolicyController.mSysState | 4);
                return;
              }
              SharePrefUtils.putInt(
                  SleepModeAction.this.mContext, "pref_sleep_mode_location_key", 0);
            }

            @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
            public void cancelAction() {
              Slog.d("SleepModePolicyController", "GPS cancelAction");
              int i =
                  SharePrefUtils.getInt(
                      SleepModeAction.this.mContext, "pref_sleep_mode_location_key", 0);
              if (i != 0) {
                Settings.Secure.putInt(
                    SleepModeAction.this.mContext.getContentResolver(), "location_mode", i);
                SleepModePolicyController.this.mLogger.add("Enable GPS");
              }
              SharePrefUtils.putInt(
                  SleepModeAction.this.mContext, "pref_sleep_mode_location_key", 0);
            }
          };
      this.wifiCallBack =
          new SleepModeCallBack() { // from class:
                                    // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeAction.3
            @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
            public void triggerAction() {
              Slog.d("SleepModePolicyController", "Wifi triggerAction");
              if (Settings.Global.getInt(
                      SleepModeAction.this.mContext.getContentResolver(),
                      "wifi_scan_always_enabled",
                      0)
                  == 1) {
                Settings.Global.putInt(
                    SleepModeAction.this.mContext.getContentResolver(),
                    "wifi_scan_always_enabled",
                    0);
                SharePrefUtils.putBoolean(
                    SleepModeAction.this.mContext, "pref_sleep_mode_wifi_key", true);
                SleepModePolicyController sleepModePolicyController =
                    SleepModePolicyController.this;
                sleepModePolicyController.setSysState(sleepModePolicyController.mSysState | 2);
                SleepModePolicyController.this.mLogger.add("Disable Wifi scan");
              }
            }

            @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
            public void cancelAction() {
              Slog.d("SleepModePolicyController", "Wifi cancelAction");
              if (SharePrefUtils.getBoolean(
                  SleepModeAction.this.mContext, "pref_sleep_mode_wifi_key", false)) {
                Settings.Global.putInt(
                    SleepModeAction.this.mContext.getContentResolver(),
                    "wifi_scan_always_enabled",
                    1);
                SleepModePolicyController.this.mLogger.add("Enable Wifi scan");
              }
              SharePrefUtils.putBoolean(
                  SleepModeAction.this.mContext, "pref_sleep_mode_wifi_key", false);
            }
          };
      this.btCallBack =
          new SleepModeCallBack() { // from class:
                                    // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeAction.4
            @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
            public void triggerAction() {
              Slog.d("SleepModePolicyController", "BlueTooth triggerAction");
              if (Settings.Global.getInt(context.getContentResolver(), "ble_scan_always_enabled", 0)
                  == 1) {
                Settings.Global.putInt(context.getContentResolver(), "ble_scan_always_enabled", 0);
                SharePrefUtils.putBoolean(context, "pref_sleep_mode_bt_key", true);
                SleepModePolicyController sleepModePolicyController =
                    SleepModePolicyController.this;
                sleepModePolicyController.setSysState(sleepModePolicyController.mSysState | 8);
                SleepModePolicyController.this.mLogger.add("Disable BT scan");
              }
            }

            @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
            public void cancelAction() {
              Slog.d("SleepModePolicyController", "BlueTooth cancelAction");
              if (SharePrefUtils.getBoolean(context, "pref_sleep_mode_bt_key", false)) {
                Settings.Global.putInt(context.getContentResolver(), "ble_scan_always_enabled", 1);
                SleepModePolicyController.this.mLogger.add("Enable BT scan");
              }
              SharePrefUtils.putBoolean(context, "pref_sleep_mode_bt_key", false);
            }
          };
      this.nearbyCallBack =
          new SleepModeCallBack() { // from class:
                                    // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeAction.5
            @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
            public void triggerAction() {
              Slog.d("SleepModePolicyController", "Nearby triggerAction");
              if (Settings.System.getInt(context.getContentResolver(), "nearby_scanning_enabled", 0)
                  == 1) {
                Settings.System.putInt(context.getContentResolver(), "nearby_scanning_enabled", 0);
                SharePrefUtils.putBoolean(context, "pref_sleep_mode_nearby_key", true);
                SleepModePolicyController sleepModePolicyController =
                    SleepModePolicyController.this;
                sleepModePolicyController.setSysState(sleepModePolicyController.mSysState | 16);
                SleepModePolicyController.this.mLogger.add("Disable nearby");
                return;
              }
              SharePrefUtils.putBoolean(context, "pref_sleep_mode_nearby_key", false);
            }

            @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
            public void cancelAction() {
              Slog.d("SleepModePolicyController", "Nearby cancelAction");
              if (SharePrefUtils.getBoolean(context, "pref_sleep_mode_nearby_key", false)) {
                Settings.System.putInt(context.getContentResolver(), "nearby_scanning_enabled", 1);
                SleepModePolicyController.this.mLogger.add("Enable nearby");
              }
              SharePrefUtils.putBoolean(context, "pref_sleep_mode_nearby_key", false);
            }
          };
      this.masterSyncCallBack =
          new SleepModeCallBack() { // from class:
                                    // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeAction.6
            @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
            public void triggerAction() {
              Slog.d("SleepModePolicyController", "MasterSync triggerAction");
              if (ContentResolver.getMasterSyncAutomatically()) {
                ContentResolver.setMasterSyncAutomatically(false);
                SharePrefUtils.putBoolean(context, "pref_sleep_mode_master_sync_key", true);
                SleepModePolicyController sleepModePolicyController =
                    SleepModePolicyController.this;
                sleepModePolicyController.setSysState(sleepModePolicyController.mSysState | 32);
                SleepModePolicyController.this.mLogger.add("Disable master sync");
                return;
              }
              SharePrefUtils.putBoolean(context, "pref_sleep_mode_master_sync_key", false);
            }

            @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
            public void cancelAction() {
              Slog.d("SleepModePolicyController", "MasterSync cancelAction");
              if (SharePrefUtils.getBoolean(context, "pref_sleep_mode_master_sync_key", false)) {
                ContentResolver.setMasterSyncAutomatically(true);
                SleepModePolicyController.this.mLogger.add("Enable master sync");
              }
              SharePrefUtils.putBoolean(context, "pref_sleep_mode_master_sync_key", false);
            }
          };
      this.notificationCallBack =
          new SleepModeCallBack() { // from class:
                                    // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeAction.7
            @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
            public void triggerAction() {
              Slog.d("SleepModePolicyController", "Notification triggerAction");
              if (Settings.System.getInt(
                      context.getContentResolver(), "edge_lighting_show_condition", 0)
                  == 0) {
                Settings.System.putInt(
                    context.getContentResolver(), "edge_lighting_show_condition", 1);
                SharePrefUtils.putBoolean(context, "pref_sleep_mode_notification_key", true);
                SleepModePolicyController sleepModePolicyController =
                    SleepModePolicyController.this;
                sleepModePolicyController.setSysState(sleepModePolicyController.mSysState | 64);
                SleepModePolicyController.this.mLogger.add(
                    "Set edge lighting condition to be screen on");
                return;
              }
              SharePrefUtils.putBoolean(context, "pref_sleep_mode_notification_key", false);
            }

            @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
            public void cancelAction() {
              Slog.d("SleepModePolicyController", "Notification cancelAction");
              if (SharePrefUtils.getBoolean(context, "pref_sleep_mode_notification_key", false)) {
                Settings.System.putInt(
                    context.getContentResolver(), "edge_lighting_show_condition", 0);
                SleepModePolicyController.this.mLogger.add(
                    "Set edge lighting condition to be always");
              }
              SharePrefUtils.putBoolean(
                  SleepModeAction.this.mContext, "pref_sleep_mode_notification_key", false);
            }
          };
      this.cameraFlashNotificationCallBack =
          new SleepModeCallBack() { // from class:
                                    // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeAction.8
            @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
            public void triggerAction() {
              Slog.d("SleepModePolicyController", "CameraFlashNotification triggerAction");
              if (Settings.System.getInt(
                      context.getContentResolver(), "camera_flash_notification", 0)
                  == 1) {
                Settings.System.putInt(
                    context.getContentResolver(), "camera_flash_notification", 0);
                SharePrefUtils.putBoolean(
                    context, "pref_sleep_mode_camera_flash_notification_key", true);
                SleepModePolicyController sleepModePolicyController =
                    SleepModePolicyController.this;
                sleepModePolicyController.setSysState(sleepModePolicyController.mSysState | 128);
                SleepModePolicyController.this.mLogger.add("Disable camera flash notification");
              }
            }

            @Override // com.android.server.ibs.sleepmode.SleepModePolicyController.SleepModeCallBack
            public void cancelAction() {
              Slog.d("SleepModePolicyController", "CameraFlashNotification cancelAction");
              if (SharePrefUtils.getBoolean(
                  context, "pref_sleep_mode_camera_flash_notification_key", false)) {
                Settings.System.putInt(
                    context.getContentResolver(), "camera_flash_notification", 1);
                SleepModePolicyController.this.mLogger.add("Enable camera flash notification");
              }
              SharePrefUtils.putBoolean(
                  context, "pref_sleep_mode_camera_flash_notification_key", false);
            }
          };
    }

    public void registerAction() {
      SleepModePolicyController.this.registerSleepModeAction(
          "PMS_SleepModeAction", this.psmCallBack);
      SleepModePolicyController.this.registerSleepModeAction(
          "GPS_SleepModeAction", this.gpsCallBack);
      SleepModePolicyController.this.registerSleepModeAction(
          "Wifi_SleepModeAction", this.wifiCallBack);
      SleepModePolicyController.this.registerSleepModeAction(
          "BlueTooth_SleepModeAction", this.btCallBack);
      SleepModePolicyController.this.registerSleepModeAction(
          "Nearby_SleepModeAction", this.nearbyCallBack);
      SleepModePolicyController.this.registerSleepModeAction(
          "MasterSync_SleepModeAction", this.masterSyncCallBack);
      SleepModePolicyController.this.registerSleepModeAction(
          "Notification_SleepModeAction", this.notificationCallBack);
      SleepModePolicyController.this.registerSleepModeAction(
          "CF_Notification_SleepModeAction", this.cameraFlashNotificationCallBack);
    }
  }
}
