package com.android.server.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/* loaded from: classes.dex */
public class BattInfoManager {
  public AsocData asocData;
  public DischargeLevelData dischargeLevelData;
  public FirstUseDateData firstUseDateData;
  public FullStatusUsageData fullStatusUsageData;
  public int mBatteryCount;
  public int mBatteryType;
  public final Context mContext;
  public boolean mIsAuthAvailable;
  public boolean[] mIsQrEquals;
  public QrData qrData;
  public static final String TAG = "[SS]" + BattInfoManager.class.getSimpleName();
  public static int BATTERY_TYPE_ONLY_EFS = 0;
  public static int BATTERY_TYPE_SINGLE_AUTH_IC = 1;
  public static int BATTERY_TYPE_SBP_FG = 2;
  public static int BATTERY_TYPE_DUAL_AUTH_IC = 3;
  public int mCheckStatusCount = 0;
  public Handler mHandler =
      new Handler(Looper.myLooper()) { // from class: com.android.server.battery.BattInfoManager.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
          Slog.i(BattInfoManager.TAG, "[handleMessage]msg:" + message.what);
          int i = message.what;
          if (i == 1) {
            boolean z =
                BattUtils.readNodeAsLong("/sys/class/power_supply/sec_auth_2nd/presence") == 1
                    && BattUtils.readNodeAsLong("/sys/class/power_supply/sec_auth_2nd/batt_auth")
                        == 1;
            Slog.i(
                BattInfoManager.TAG,
                "[handleMessage]isStatusValid:"
                    + z
                    + " ,checkCount:"
                    + BattInfoManager.this.mCheckStatusCount);
            if (z) {
              BattInfoManager.this.mHandler.sendEmptyMessage(2);
              BatteryLogger.writeToFile(
                  "/data/log/battery_service/battery_service_main_history",
                  "Check Dual Auth Status Success",
                  "mCheckStatusCount:" + BattInfoManager.this.mCheckStatusCount);
              return;
            }
            if (BattInfoManager.this.mCheckStatusCount < 60) {
              BattInfoManager.this.mCheckStatusCount++;
              BattInfoManager.this.mHandler.removeCallbacksAndMessages(null);
              BattInfoManager.this.mHandler.sendEmptyMessageDelayed(1, 1000L);
              return;
            }
            Slog.e(BattInfoManager.TAG, "[handleMessage]Check Dual Auth Status Fail");
            BatteryLogger.writeToFile(
                "/data/log/battery_service/battery_service_main_history",
                "Check Dual Auth Status Fail",
                "");
            return;
          }
          if (i != 2) {
            return;
          }
          BattInfoManager battInfoManager = BattInfoManager.this;
          battInfoManager.qrData = battInfoManager.new QrData();
          BattInfoManager battInfoManager2 = BattInfoManager.this;
          battInfoManager2.mIsQrEquals = battInfoManager2.qrData.getIsQrEquals();
          BattInfoManager battInfoManager3 = BattInfoManager.this;
          battInfoManager3.firstUseDateData = battInfoManager3.new FirstUseDateData();
          BattInfoManager battInfoManager4 = BattInfoManager.this;
          battInfoManager4.asocData = battInfoManager4.new AsocData();
          BattInfoManager battInfoManager5 = BattInfoManager.this;
          battInfoManager5.dischargeLevelData = battInfoManager5.new DischargeLevelData();
          boolean z2 = BattFeatures.FEATURE_FULL_BATTERY_CYCLE;
          if (z2) {
            BattInfoManager battInfoManager6 = BattInfoManager.this;
            battInfoManager6.fullStatusUsageData = battInfoManager6.new FullStatusUsageData();
          }
          if (BattInfoManager.this.mBatteryType != BattInfoManager.BATTERY_TYPE_ONLY_EFS) {
            BattInfoManager.this.qrData.syncAuthAndEfs();
            BattInfoManager.this.firstUseDateData.syncAuthAndEfs();
            BattInfoManager.this.asocData.syncAuthAndEfs();
            BattInfoManager.this.dischargeLevelData.syncAuthAndEfs();
            if (z2) {
              BattInfoManager.this.fullStatusUsageData.syncAuthAndEfs();
            }
          }
          BattInfoManager.this.dischargeLevelData.loadValueFromEfs();
          BattInfoManager.this.fullStatusUsageData.loadValueFromEfs();
          BattInfoManager.this.setCycle();
          if (BattInfoManager.this.mBatteryType == BattInfoManager.BATTERY_TYPE_SINGLE_AUTH_IC
              || BattInfoManager.this.mBatteryType == BattInfoManager.BATTERY_TYPE_DUAL_AUTH_IC) {
            BattInfoManager.this.firstUseDateData.activateFaiExpiredCheck();
          }
          BattInfoManager.this.qrData.saveInfoToFile();
          BattInfoManager.this.firstUseDateData.saveInfoToFile();
          BattInfoManager.this.asocData.saveInfoToFile();
          BattInfoManager.this.dischargeLevelData.saveInfoToFile();
          BattInfoManager.this.fullStatusUsageData.saveInfoToFile();
          BattInfoManager.this.mIsAuthAvailable = true;
        }
      };

  public BattInfoManager(Context context) {
    this.mContext = context;
    if (BattUtils.isExist("/sys/class/power_supply/sec_auth_2nd/qr_code")) {
      this.mBatteryType = BATTERY_TYPE_DUAL_AUTH_IC;
      this.mBatteryCount = 2;
    } else if (BattUtils.isExist("/sys/class/power_supply/sec_auth/qr_code")) {
      this.mBatteryType = BATTERY_TYPE_SINGLE_AUTH_IC;
      this.mBatteryCount = 1;
    } else if (BattUtils.isExist("/sys/class/power_supply/sbp-fg/qr_code")) {
      this.mBatteryType = BATTERY_TYPE_SBP_FG;
      this.mBatteryCount = 1;
    } else {
      this.mBatteryType = BATTERY_TYPE_ONLY_EFS;
      this.mBatteryCount = 1;
    }
    Slog.i(
        TAG,
        "[BattInfoManager]mBatteryType:"
            + this.mBatteryType
            + " ,mBatteryCount:"
            + this.mBatteryCount);
    BatteryLogger.writeToFile(
        "/data/log/battery_service/battery_service_main_history",
        "BattInfoManager Created",
        "mBatteryType:" + this.mBatteryType + " ,mBatteryCount:" + this.mBatteryCount);
  }

  public boolean isDualAuth() {
    return this.mBatteryType == BATTERY_TYPE_DUAL_AUTH_IC;
  }

  public boolean isAuthAvailable() {
    return this.mIsAuthAvailable;
  }

  public void init() {
    Slog.d(TAG, "[init]");
    if (this.mBatteryType == BATTERY_TYPE_DUAL_AUTH_IC) {
      this.mHandler.sendEmptyMessage(1);
    } else {
      this.mHandler.sendEmptyMessage(2);
    }
  }

  public void setCycle() {
    String format;
    long asLong = Arrays.stream(this.dischargeLevelData.currentValues).max().getAsLong();
    String str = TAG;
    Slog.d(str, "[setCycle]maxDischargeLevel:" + asLong);
    if (BattFeatures.FEATURE_FULL_BATTERY_CYCLE) {
      long asLong2 = Arrays.stream(this.fullStatusUsageData.currentValues).max().getAsLong();
      Slog.d(str, "[setCycle]maxFullStatusUsage:" + asLong2);
      format = String.format("%d %d", Long.valueOf(asLong / 100), Long.valueOf(asLong2 / 720));
    } else {
      format = String.format("%d", Long.valueOf(asLong / 100));
    }
    Slog.d(str, "[setCycle]cycleStr:" + format);
    BattUtils.writeNode("/sys/class/power_supply/battery/battery_cycle", format);
  }

  public abstract class BaseData {
    public ArrayList efsPaths = new ArrayList();
    public ArrayList authPaths = new ArrayList();
    public String TAG =
        "[SS]" + BattInfoManager.class.getSimpleName() + "_" + getClass().getSimpleName();

    public BaseData() {}

    public void saveInfoToFile() {
      Slog.v(this.TAG, "[saveInfoToFile]");
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < BattInfoManager.this.mBatteryCount; i++) {
        sb.append("efsValue:" + BattUtils.readNode((String) this.efsPaths.get(i)));
        ArrayList arrayList = this.authPaths;
        if (arrayList != null) {
          sb.append(" ,authValue:" + BattUtils.readNode((String) arrayList.get(i)));
        }
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      }
      BatteryLogger.writeToFile(
          "/data/log/battery_service/battery_service_main_history",
          this.TAG + " saveInfoToFile",
          sb.toString());
    }
  }

  public class QrData extends BaseData {
    public QrData() {
      super();
      if (BattInfoManager.this.mBatteryType == BattInfoManager.BATTERY_TYPE_DUAL_AUTH_IC) {
        this.efsPaths.add("/efs/FactoryApp/HwParamBattQR");
        this.efsPaths.add("/efs/FactoryApp/HwParamBattQR_2nd");
        this.authPaths.add("/sys/class/power_supply/sec_auth/qr_code");
        this.authPaths.add("/sys/class/power_supply/sec_auth_2nd/qr_code");
      }
    }

    public boolean[] getIsQrEquals() {
      boolean[] zArr = new boolean[BattInfoManager.this.mBatteryCount];
      for (int i = 0; i < BattInfoManager.this.mBatteryCount; i++) {
        String readNode = BattUtils.readNode((String) this.efsPaths.get(i));
        String readNode2 = BattUtils.readNode((String) this.authPaths.get(i));
        if (!readNode.isEmpty() && readNode.equals(readNode2)) {
          zArr[i] = true;
        } else {
          zArr[i] = false;
        }
        Slog.i(
            this.TAG,
            "[getIsQrEquals]efsQr:" + readNode + " ,authQr:" + readNode2 + " =>Equal:" + zArr[i]);
      }
      BatteryLogger.writeToFile(
          "/data/log/battery_service/battery_service_main_history",
          "Check Auth & Efs QR Equal",
          "isQrEquals:" + Arrays.toString(zArr));
      return zArr;
    }

    public void syncAuthAndEfs() {
      Slog.d(this.TAG, "[syncAuthAndEfs]");
      for (int i = 0; i < BattInfoManager.this.mBatteryCount; i++) {
        if (!BattInfoManager.this.mIsQrEquals[i]) {
          String readNode = BattUtils.readNode((String) this.authPaths.get(i));
          if (!readNode.isEmpty()) {
            Slog.d(this.TAG, "[syncAuthAndEfs]sync efs QR to auth");
            BattUtils.writeNode((String) this.efsPaths.get(i), readNode);
          }
        }
        FileUtils.setPermissions(
            (String) this.efsPaths.get(i),
            FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED,
            1000,
            1000);
      }
    }
  }

  public class FirstUseDateData extends BaseData {
    public ArrayList authFaiExpiredPaths;
    public DateChangedReceiver dateChangedReceiver;
    public boolean shouldCheck;
    public boolean[] shouldCheckFaiExpireds;
    public boolean shouldCheckSetupWizardCompletedByUser;
    public boolean shouldCheckSetupWizardSkipWifi;

    public FirstUseDateData() {
      super();
      if (BattInfoManager.this.mBatteryType == BattInfoManager.BATTERY_TYPE_DUAL_AUTH_IC) {
        this.efsPaths.add("/efs/FactoryApp/batt_beginning_date");
        this.efsPaths.add("/efs/FactoryApp/batt_beginning_date_2nd");
        this.authPaths.add("/sys/class/power_supply/sec_auth/first_use_date");
        this.authPaths.add("/sys/class/power_supply/sec_auth_2nd/first_use_date");
        this.shouldCheck = false;
        this.shouldCheckSetupWizardCompletedByUser = true;
        this.shouldCheckSetupWizardSkipWifi = true;
        ArrayList arrayList = new ArrayList();
        this.authFaiExpiredPaths = arrayList;
        arrayList.add("/sys/class/power_supply/sec_auth/fai_expired");
        this.authFaiExpiredPaths.add("/sys/class/power_supply/sec_auth_2nd/fai_expired");
        this.shouldCheckFaiExpireds = new boolean[BattInfoManager.this.mBatteryCount];
      }
    }

    public void syncAuthAndEfs() {
      for (int i = 0; i < BattInfoManager.this.mBatteryCount; i++) {
        String readNode = BattUtils.readNode((String) this.authPaths.get(i));
        if (isValidDate(readNode)) {
          Slog.d(this.TAG, "[syncAuthAndEfs]sync efs FUD to auth:" + readNode);
          BattUtils.writeNode((String) this.efsPaths.get(i), readNode);
        } else {
          Slog.w(this.TAG, "[syncAuthAndEfs]valid auth fud not exist");
          this.shouldCheck = true;
          Slog.i(this.TAG, "[updateDate]=> shouldCheck:" + this.shouldCheck);
        }
        FileUtils.setPermissions(
            (String) this.efsPaths.get(i),
            FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED,
            1000,
            1000);
      }
    }

    public final class DateChangedReceiver extends BroadcastReceiver {
      public DateChangedReceiver() {}

      @Override // android.content.BroadcastReceiver
      public void onReceive(Context context, Intent intent) {
        Slog.d(FirstUseDateData.this.TAG, "[DateChangedReceiver_onReceive]");
        BattInfoManager.this.mHandler.post(
            new Runnable() { // from class:
                             // com.android.server.battery.BattInfoManager.FirstUseDateData.DateChangedReceiver.1
              @Override // java.lang.Runnable
              public void run() {
                FirstUseDateData.this.checkFaiExpired();
              }
            });
      }
    }

    public final void activateFaiExpiredCheck() {
      for (int i = 0; i < BattInfoManager.this.mBatteryCount; i++) {
        String readNode = BattUtils.readNode((String) this.authFaiExpiredPaths.get(i));
        Slog.d(this.TAG, "[activateFaiExpiredCheck]faiExpiredStr:" + readNode);
        if (!"1".equals(readNode)) {
          this.shouldCheckFaiExpireds[i] = true;
          if (!"0".equals(readNode)) {
            BattUtils.writeNode((String) this.authFaiExpiredPaths.get(i), "0");
          }
        }
      }
      BatteryLogger.writeToFile(
          "/data/log/battery_service/battery_service_main_history",
          "activateFaiExpiredCheck",
          "shouldCheckFaiExpireds:" + Arrays.toString(this.shouldCheckFaiExpireds));
      if (IntStream.range(0, BattInfoManager.this.mBatteryCount)
          .anyMatch(
              new IntPredicate() { // from class:
                                   // com.android.server.battery.BattInfoManager$FirstUseDateData$$ExternalSyntheticLambda1
                @Override // java.util.function.IntPredicate
                public final boolean test(int i2) {
                  boolean lambda$activateFaiExpiredCheck$0;
                  lambda$activateFaiExpiredCheck$0 =
                      BattInfoManager.FirstUseDateData.this.lambda$activateFaiExpiredCheck$0(i2);
                  return lambda$activateFaiExpiredCheck$0;
                }
              })) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.DATE_CHANGED");
        this.dateChangedReceiver = new DateChangedReceiver();
        BattInfoManager.this.mContext.registerReceiver(
            this.dateChangedReceiver, intentFilter, null, BattInfoManager.this.mHandler);
        Slog.i(this.TAG, "[activateFaiExpiredCheck]DateChangedReceiver Registered For FAI Expired");
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$activateFaiExpiredCheck$0(int i) {
      return this.shouldCheckFaiExpireds[i];
    }

    public final void checkFaiExpired() {
      LocalDate convertDateStringToLocalDate;
      for (int i = 0; i < BattInfoManager.this.mBatteryCount; i++) {
        Slog.d(this.TAG, "[checkFaiExpired]index:" + i);
        if (this.shouldCheckFaiExpireds[i]
            && (convertDateStringToLocalDate =
                    BattUtils.convertDateStringToLocalDate(
                        BattUtils.readNode((String) this.authPaths.get(i))))
                != null) {
          LocalDate plusDays = convertDateStringToLocalDate.plusDays(14L);
          LocalDate now = LocalDate.now();
          Slog.d(this.TAG, "[checkFaiExpired]currentDate:" + now + " ,thresholdDate:" + plusDays);
          if (!now.isBefore(plusDays)
              && BattUtils.writeNode((String) this.authFaiExpiredPaths.get(i), "1")) {
            Slog.i(this.TAG, "[checkFaiExpired]FaiExpired Write Success");
            this.shouldCheckFaiExpireds[i] = false;
          }
        }
      }
      if (IntStream.range(0, BattInfoManager.this.mBatteryCount)
              .noneMatch(
                  new IntPredicate() { // from class:
                                       // com.android.server.battery.BattInfoManager$FirstUseDateData$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntPredicate
                    public final boolean test(int i2) {
                      boolean lambda$checkFaiExpired$1;
                      lambda$checkFaiExpired$1 =
                          BattInfoManager.FirstUseDateData.this.lambda$checkFaiExpired$1(i2);
                      return lambda$checkFaiExpired$1;
                    }
                  })
          && this.dateChangedReceiver != null) {
        BattInfoManager.this.mContext.unregisterReceiver(this.dateChangedReceiver);
        this.dateChangedReceiver = null;
        Slog.d(this.TAG, "[checkFaiExpired]disable dateChangedReceiver");
      }
      Slog.i(
          this.TAG,
          "[checkFaiExpired]shouldCheckFaiExpireds:"
              + Arrays.toString(this.shouldCheckFaiExpireds));
      BatteryLogger.writeToFile(
          "/data/log/battery_service/battery_service_main_history",
          "checkFaiExpired",
          "shouldCheckFaiExpireds:" + Arrays.toString(this.shouldCheckFaiExpireds));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$checkFaiExpired$1(int i) {
      return this.shouldCheckFaiExpireds[i];
    }

    public void updateDate() {
      String currentNetworkDateStr;
      String readNode;
      Slog.d(this.TAG, "[updateDate]");
      if (isSetupWizardCompleted()) {
        if (!this.shouldCheckSetupWizardCompletedByUser || isSetupWizardCompletedByUser()) {
          if (this.shouldCheckSetupWizardSkipWifi && isSetupWizardSkipWifi()) {
            return;
          }
          if (BattInfoManager.this.mBatteryType == BattInfoManager.BATTERY_TYPE_ONLY_EFS) {
            currentNetworkDateStr = BattUtils.getCurrentCalenderStr();
          } else {
            currentNetworkDateStr = BattUtils.getCurrentNetworkDateStr();
          }
          Slog.i(this.TAG, "[updateDate]strCurrentDate:" + currentNetworkDateStr);
          if (currentNetworkDateStr == null || currentNetworkDateStr.length() != 8) {
            return;
          }
          String str = SystemProperties.get("ril.rfcal_date");
          if (str != null) {
            str = str.replace(".", "");
          }
          String str2 = SystemProperties.get("ril.manufacturedate");
          Slog.d(this.TAG, "[updateDate]strRfCalDate:" + str + " ,strManufactureDate:" + str2);
          if ((str == null || str.length() != 8) && (str2 == null || str2.length() != 8)) {
            Slog.d(this.TAG, "[updateDate]fail - no date for compare");
            return;
          }
          int parseInt = Integer.parseInt(currentNetworkDateStr);
          int parseInt2 = Integer.parseInt(str);
          Slog.d(this.TAG, "[updateDate]currentDateInt:" + parseInt + " ,refDate:" + parseInt2);
          if (parseInt2 > parseInt) {
            return;
          }
          int i = 0;
          for (int i2 = 0; i2 < BattInfoManager.this.mBatteryCount; i2++) {
            ArrayList arrayList = this.authPaths;
            if (arrayList != null) {
              readNode = BattUtils.readNode((String) arrayList.get(i2));
            } else {
              readNode = BattUtils.readNode((String) this.efsPaths.get(i2));
            }
            boolean isValidDate = isValidDate(readNode);
            Slog.i(this.TAG, "[updateDate]isValidFirstUseDateExist:" + isValidDate);
            if (isValidDate) {
              i++;
            } else {
              try {
                long j = parseInt;
                BattUtils.writeNode((String) this.efsPaths.get(i2), j);
                ArrayList arrayList2 = this.authPaths;
                if (arrayList2 != null) {
                  BattUtils.writeNode((String) arrayList2.get(i2), j);
                }
                i++;
                BatteryLogger.writeToFile(
                    "/data/log/battery_service/battery_service_main_history",
                    "BatteryFirstUseDate",
                    "write batteryDate:" + parseInt);
              } catch (NumberFormatException e) {
                Slog.e(this.TAG, "NumberFormatException");
                e.printStackTrace();
              }
            }
          }
          if (i == BattInfoManager.this.mBatteryCount) {
            this.shouldCheck = false;
            Slog.i(this.TAG, "[updateDate]=> shouldCheck:" + this.shouldCheck);
          }
        }
      }
    }

    public final boolean isValidDate(String str) {
      return str != null && str.length() == 8 && str.startsWith("20");
    }

    public final boolean isSetupWizardCompleted() {
      boolean z = false;
      try {
        if (Settings.Global.getInt(
                BattInfoManager.this.mContext.getContentResolver(), "device_provisioned", 0)
            == 1) {
          z = true;
        }
      } catch (Exception e) {
        Slog.e(this.TAG, "[isSetupWizardCompleted]Exception");
        e.printStackTrace();
      }
      Slog.d(this.TAG, "[isSetupWizardCompleted]result:" + z);
      return z;
    }

    public final boolean isSetupWizardCompletedByUser() {
      boolean z = false;
      try {
        z = SystemProperties.getBoolean("persist.sys.setupwizard.user_setup_complete", false);
      } catch (Exception e) {
        Slog.e(this.TAG, "[isSetupWizardCompletedByUser]Exception");
        e.printStackTrace();
      }
      Slog.d(this.TAG, "[isSetupWizardCompletedByUser]result:" + z);
      return z;
    }

    public final boolean isSetupWizardSkipWifi() {
      boolean z = false;
      try {
        if (SystemProperties.getInt("persist.sys.setupwizard.jig_on_wifisetup", 0) == 1) {
          z = true;
        }
      } catch (Exception e) {
        Slog.e(this.TAG, "[isSetupWizardSkipWifi]Exception");
        e.printStackTrace();
      }
      Slog.d(this.TAG, "[isSetupWizardSkipWifi]result:" + z);
      return z;
    }
  }

  public class AsocData extends BaseData {
    public long[] currentValues;

    public AsocData() {
      super();
      if (BattInfoManager.this.mBatteryType == BattInfoManager.BATTERY_TYPE_DUAL_AUTH_IC) {
        this.efsPaths.add("/efs/FactoryApp/asoc");
        this.efsPaths.add("/efs/FactoryApp/asoc_2nd");
        this.authPaths.add("/sys/class/power_supply/sec_auth/asoc");
        this.authPaths.add("/sys/class/power_supply/sec_auth_2nd/asoc");
        this.currentValues = new long[BattInfoManager.this.mBatteryCount];
      }
    }

    public void syncAuthAndEfs() {
      for (int i = 0; i < BattInfoManager.this.mBatteryCount; i++) {
        long j = 100;
        if (BattInfoManager.this.mIsQrEquals[i]) {
          long readNodeAsLong = BattUtils.readNodeAsLong((String) this.efsPaths.get(i));
          long readNodeAsLong2 = BattUtils.readNodeAsLong((String) this.authPaths.get(i));
          long j2 =
              (readNodeAsLong2 < 0 || readNodeAsLong2 > 100 || readNodeAsLong2 >= readNodeAsLong)
                  ? readNodeAsLong
                  : readNodeAsLong2;
          Slog.d(
              this.TAG,
              "[syncAuthAndEfs]efsAsoc:"
                  + readNodeAsLong
                  + " ,authAsoc:"
                  + readNodeAsLong2
                  + " =>worseAsoc:"
                  + j2);
          BattUtils.writeNode((String) this.efsPaths.get(i), j2);
          BattUtils.writeNode((String) this.authPaths.get(i), j2);
        } else {
          long readNodeAsLong3 = BattUtils.readNodeAsLong((String) this.authPaths.get(i));
          if (readNodeAsLong3 == 65535 || readNodeAsLong3 < 0) {
            Slog.i(this.TAG, "[syncAuthAndEfs]init authAsoc:100");
            BattUtils.writeNode((String) this.authPaths.get(i), 100L);
          } else {
            j = readNodeAsLong3;
          }
          Slog.d(this.TAG, "[syncAuthAndEfs]sync efs asoc to auth");
          BattUtils.writeNode((String) this.efsPaths.get(i), j);
        }
        FileUtils.setPermissions(
            (String) this.efsPaths.get(i),
            FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED,
            1000,
            1007);
      }
    }

    public boolean isValueInvalid() {
      boolean z = false;
      int i = 0;
      while (true) {
        if (i >= BattInfoManager.this.mBatteryCount) {
          break;
        }
        if (BattUtils.readNodeAsLong((String) this.efsPaths.get(i)) < 0) {
          z = true;
          break;
        }
        i++;
      }
      Slog.d(this.TAG, "[isValueInvalid]result:" + z);
      return z;
    }

    public void updateAndSet() {
      long readNodeAsLong = BattUtils.readNodeAsLong("/sys/class/power_supply/battery/fg_asoc");
      Slog.d(this.TAG, "[updateAndSet]asocFromDriver:" + readNodeAsLong);
      for (int i = 0; i < BattInfoManager.this.mBatteryCount; i++) {
        this.currentValues[i] = BattUtils.readNodeAsLong((String) this.efsPaths.get(i));
        Slog.d(this.TAG, "[updateAndSet]currentValue:" + this.currentValues[i]);
        long[] jArr = this.currentValues;
        if (jArr[i] < 0) {
          if (readNodeAsLong < 0) {
            jArr[i] = -1;
          } else {
            jArr[i] = 100;
          }
          BattUtils.writeNode((String) this.efsPaths.get(i), this.currentValues[i]);
          BattUtils.writeNode((String) this.authPaths.get(i), this.currentValues[i]);
        }
        if (readNodeAsLong >= 0) {
          long[] jArr2 = this.currentValues;
          long j = jArr2[i];
          if (readNodeAsLong < j && j - readNodeAsLong < 10) {
            jArr2[i] = j - 1;
            Slog.d(this.TAG, "[updateAndSet]new currentValue:" + this.currentValues[i]);
            BattUtils.writeNode((String) this.efsPaths.get(i), this.currentValues[i]);
            BattUtils.writeNode((String) this.authPaths.get(i), this.currentValues[i]);
          }
        }
      }
      long asLong = Arrays.stream(this.currentValues).min().getAsLong();
      Slog.d(this.TAG, "[updateAndSet]minAsoc:" + asLong);
      BattUtils.writeNode("/sys/class/power_supply/battery/fg_asoc", asLong);
    }
  }

  public abstract class BaseCycleData extends BaseData {
    public long[] currentValues;

    public BaseCycleData() {
      super();
    }

    public void syncAuthAndEfs() {
      for (int i = 0; i < BattInfoManager.this.mBatteryCount; i++) {
        if (BattInfoManager.this.mIsQrEquals[i]) {
          long readNodeAsLong = BattUtils.readNodeAsLong((String) this.efsPaths.get(i));
          long readNodeAsLong2 = BattUtils.readNodeAsLong((String) this.authPaths.get(i));
          long j =
              (readNodeAsLong2 == 16777215 || readNodeAsLong2 <= readNodeAsLong)
                  ? readNodeAsLong
                  : readNodeAsLong2;
          Slog.d(
              this.TAG,
              "[syncAuthAndEfs]efsValue:"
                  + readNodeAsLong
                  + " ,authValue:"
                  + readNodeAsLong2
                  + " =>worseValue:"
                  + j);
          BattUtils.writeNode((String) this.efsPaths.get(i), j);
          BattUtils.writeNode((String) this.authPaths.get(i), j);
        } else {
          long readNodeAsLong3 = BattUtils.readNodeAsLong((String) this.authPaths.get(i));
          if (readNodeAsLong3 == 16777215 || readNodeAsLong3 < 0) {
            String str = this.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("[syncAuthAndEfs]init authValue:");
            readNodeAsLong3 = 1;
            sb.append(1L);
            Slog.i(str, sb.toString());
            BattUtils.writeNode((String) this.authPaths.get(i), 1L);
          }
          Slog.d(this.TAG, "[syncAuthAndEfs]sync efs value to auth");
          BattUtils.writeNode((String) this.efsPaths.get(i), readNodeAsLong3);
        }
        FileUtils.setPermissions(
            (String) this.efsPaths.get(i),
            FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED,
            1000,
            1007);
      }
    }

    public void loadValueFromEfs() {
      for (int i = 0; i < BattInfoManager.this.mBatteryCount; i++) {
        this.currentValues[i] = BattUtils.readNodeAsLong((String) this.efsPaths.get(i));
      }
      Slog.d(this.TAG, "[loadValueFromEfs]currentValues:" + Arrays.toString(this.currentValues));
    }

    public void addValueAndSave(long j) {
      Slog.d(this.TAG, "[addValueAndSave]addtionalValue:" + j);
      for (int i = 0; i < BattInfoManager.this.mBatteryCount; i++) {
        long[] jArr = this.currentValues;
        jArr[i] = jArr[i] + j;
        BattUtils.writeNode((String) this.efsPaths.get(i), this.currentValues[i]);
        ArrayList arrayList = this.authPaths;
        if (arrayList != null) {
          BattUtils.writeNode((String) arrayList.get(i), this.currentValues[i]);
        }
      }
      Slog.d(this.TAG, "[addValueAndSave]currentValues:" + Arrays.toString(this.currentValues));
    }
  }

  public class DischargeLevelData extends BaseCycleData {
    public DischargeLevelData() {
      super();
      if (BattInfoManager.this.mBatteryType == BattInfoManager.BATTERY_TYPE_DUAL_AUTH_IC) {
        this.efsPaths.add("/efs/FactoryApp/batt_discharge_level");
        this.efsPaths.add("/efs/FactoryApp/batt_discharge_level_2nd");
        this.authPaths.add("/sys/class/power_supply/sec_auth/batt_discharge_level");
        this.authPaths.add("/sys/class/power_supply/sec_auth_2nd/batt_discharge_level");
        this.currentValues = new long[BattInfoManager.this.mBatteryCount];
      }
    }
  }

  public class FullStatusUsageData extends BaseCycleData {
    public FullStatusUsageData() {
      super();
      if (BattInfoManager.this.mBatteryType == BattInfoManager.BATTERY_TYPE_DUAL_AUTH_IC) {
        this.efsPaths.add("/efs/FactoryApp/batt_full_status_usage");
        this.efsPaths.add("/efs/FactoryApp/batt_full_status_usage_2nd");
        this.authPaths.add("/sys/class/power_supply/sec_auth/batt_full_status_usage");
        this.authPaths.add("/sys/class/power_supply/sec_auth_2nd/batt_full_status_usage");
        this.currentValues = new long[BattInfoManager.this.mBatteryCount];
      }
    }
  }
}
