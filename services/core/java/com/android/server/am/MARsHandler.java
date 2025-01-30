package com.android.server.am;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.system.Os;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.clipboard.ClipboardService;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.IntPredicate;

/* loaded from: classes.dex */
public class MARsHandler {
  public Context mContext;
  public MainHandler mMainHandler;

  public abstract class MARsHandlerHolder {
    public static final MARsHandler INSTANCE = new MARsHandler();
  }

  public MARsHandler() {}

  public static MARsHandler getInstance() {
    return MARsHandlerHolder.INSTANCE;
  }

  public MainHandler getHandler() {
    return this.mMainHandler;
  }

  public final void setContext(Context context) {
    this.mContext = context;
  }

  public void init(Context context) {
    setContext(context);
    new MainThread("MARsMainThread", -2).start();
  }

  public class MainThread extends Thread {
    public int mPriority;

    public MainThread(String str, int i) {
      super(str);
      this.mPriority = i;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      Process.setThreadPriority(this.mPriority);
      Looper.prepare();
      MARsHandler.this.mMainHandler = MARsHandler.this.new MainHandler();
      Looper.loop();
    }
  }

  public class MainHandler extends Handler {
    public Bundle extras = null;
    public MARsPolicyManager mMARsPolicyManager = MARsPolicyManager.getInstance();

    public static /* synthetic */ boolean lambda$isZygote$0(int i, int i2) {
      return i2 == i;
    }

    public MainHandler() {}

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      Bundle data = message.getData();
      this.extras = data;
      int i = message.what;
      switch (i) {
        case 1:
        case 2:
          handleTrigger(i);
          return;
        case 3:
          handlePackageUpdated();
          return;
        case 4:
          this.mMARsPolicyManager.getBatteryStats();
          MARsHandler.this.sendGetBatteryStatMsgToMainHandler(false);
          return;
        case 5:
          handleSetModeCall();
          return;
        case 6:
          if (data != null) {
            this.mMARsPolicyManager.onAppUsedForTimeChanged(data.getLong("changedTime"));
            return;
          }
          return;
        case 7:
          handleSpecificPackage();
          return;
        case 8:
          handlePolicyCanceled();
          return;
        case 9:
          this.mMARsPolicyManager.triggerAction();
          return;
        case 10:
          handleDisableUpdated();
          return;
        case 11:
          if (data != null) {
            this.mMARsPolicyManager.notifyAppSleepToDC(
                data.getString("extraType", ""),
                this.extras.getStringArrayList("packageList"),
                this.extras.getIntegerArrayList("uidList"));
            return;
          }
          return;
        case 12:
          if (data != null) {
            this.mMARsPolicyManager.initDisabledPackage(
                data.getInt("userId", MARsHandler.this.mContext.getUserId()));
            return;
          }
          return;
        case 13:
          handleChangeAppStandbyBucket();
          return;
        case 14:
          handleKillPackageProcess();
          return;
        case 15:
          handleUpdateFgsRestrictionTarget();
          return;
        case 16:
          handleNotifyAnomaly();
          return;
        case 17:
          handleNotifyTcpuAnomaly();
          return;
        case 18:
          if (data != null) {
            this.mMARsPolicyManager.disablePackageListForSpecific(
                data.getParcelableArrayList("AppRestrictionInfo"), "added_from_mars_auto_specific");
            return;
          }
          return;
        case 19:
          handleResetAbusiveEvents();
          return;
        default:
          throw new IllegalStateException("Unexpected value: " + message.what);
      }
    }

    public final void handleTrigger(int i) {
      if (this.mMARsPolicyManager.getScreenOnState()
          || this.mMARsPolicyManager.getCarModeOnState()
          || FreecessController.getInstance().getIsDumpstateWorking()) {
        return;
      }
      this.mMARsPolicyManager.triggerAction();
      if (i == 1) {
        if (this.mMARsPolicyManager.checkIsChinaModel()) {
          MARsHandler.this.sendRepeatTriggerMsgToMainHandler();
        }
      } else if (i == 2) {
        MARsHandler.this.sendRepeatTriggerMsgToMainHandler();
      }
    }

    public final void handlePackageUpdated() {
      if (this.mMARsPolicyManager.getIsManualMode()) {
        return;
      }
      this.mMARsPolicyManager.doUpdatePackages(false);
      MARsHandler.this.sendUpdatePkgMsgToMainHandler(false);
    }

    public final void handleSetModeCall() {
      Bundle bundle = this.extras;
      if (bundle == null) {
        return;
      }
      String string = bundle.getString("pkgName", null);
      int i = this.extras.getInt("uid", -1);
      if (string == null || i == -1) {
        return;
      }
      this.mMARsPolicyManager.updateFasState(string, i, this.extras.getInt("mode", 0));
    }

    public final void handleSpecificPackage() {
      Bundle bundle = this.extras;
      if (bundle == null) {
        return;
      }
      ArrayList<String> stringArrayList = bundle.getStringArrayList("packageList");
      int i = this.extras.getInt("policy-num", 0);
      int i2 = this.extras.getInt("userId", MARsHandler.this.mContext.getUserId());
      if (i != 0) {
        if (i != 5 && i != 6) {
          if (i == 9) {
            this.mMARsPolicyManager.forceRunPolicyForSpecificPolicy(i, stringArrayList);
            return;
          } else if (i != 10) {
            this.mMARsPolicyManager.forceRunPolicyForSpecificPackage(i, stringArrayList);
            return;
          }
        }
        if (stringArrayList != null) {
          this.mMARsPolicyManager.updateSpecificPolicyTargetPackages(stringArrayList, i, i2);
        }
        this.mMARsPolicyManager.forceRunPolicyForSpecificPolicy(i, null);
      }
    }

    public final void handlePolicyCanceled() {
      Bundle bundle = this.extras;
      if (bundle == null) {
        return;
      }
      ArrayList<String> stringArrayList = bundle.getStringArrayList("packageList");
      int i = this.extras.getInt("policy-num", 0);
      if (stringArrayList != null) {
        int i2 = this.extras.getInt("userId", MARsHandler.this.mContext.getUserId());
        for (int i3 = 0; i3 < stringArrayList.size(); i3++) {
          this.mMARsPolicyManager.cancelPolicy(stringArrayList.get(i3), i, i2);
        }
      } else {
        this.mMARsPolicyManager.cancelPolicy(i);
      }
      if (i == 10 && this.mMARsPolicyManager.isChinaPolicyEnabled()) {
        this.mMARsPolicyManager.setFirstTimeUpdatePkgsState(true);
        MARsHandler.this.sendTriggerPolicyMsgToMainHandler();
      }
    }

    public final void handleDisableUpdated() {
      if (this.mMARsPolicyManager.getIsManualMode()) {
        return;
      }
      this.mMARsPolicyManager.checkUnusedTargetForDeepSleep();
      Bundle bundle = this.extras;
      if (bundle != null) {
        MARsHandler.this.sendUpdateDisableMsgToMainHandler(bundle.getBoolean("debug", false));
      }
    }

    public final void handleChangeAppStandbyBucket() {
      Bundle bundle = this.extras;
      if (bundle != null) {
        String string = bundle.getString("pkgName", null);
        int i = this.extras.getInt("userId", -1);
        if (string == null || i == -1) {
          return;
        }
        this.mMARsPolicyManager.callSetAppStandbyBucket(
            string, i, this.extras.getInt("bucket"), this.extras.getBoolean("byUser", false));
      }
    }

    public final void handleKillPackageProcess() {
      Bundle bundle = this.extras;
      if (bundle != null) {
        String string = bundle.getString("pkgName", null);
        int i = this.extras.getInt("uid", -1);
        int i2 = this.extras.getInt("userId", -1);
        int i3 = this.extras.getInt("minAdj", -1);
        boolean z = this.extras.getBoolean("allowRestart", true);
        String string2 = this.extras.getString("reason", null);
        Slog.d(
            "MARsHandler",
            "kill package processes. pkgName="
                + string
                + ", uid="
                + i
                + ", userId="
                + i2
                + ", minAdj="
                + i3
                + ", allowRestart="
                + z
                + ", reason="
                + string2);
        if (string == null || i == -1 || i2 == -1 || i3 == -1 || string2 == null) {
          return;
        }
        this.mMARsPolicyManager.killPackageProcs(string, i, i2, i3, z, string2);
      }
    }

    public final void handleUpdateFgsRestrictionTarget() {
      Bundle bundle = this.extras;
      if (bundle == null) {
        return;
      }
      String string = bundle.getString("pkgName", null);
      int i = this.extras.getInt("uid", -1);
      if (string == null || i == -1) {
        return;
      }
      this.mMARsPolicyManager.setFGSRestrictionTarget(string, i);
    }

    public final void handleNotifyAnomaly() {
      Bundle bundle = this.extras;
      if (bundle != null) {
        String string = bundle.getString("pkgName", null);
        int i = this.extras.getInt("uid", -1);
        String string2 = this.extras.getString("type");
        if (string == null || i == -1) {
          return;
        }
        this.mMARsPolicyManager.notifyAnomalyApp(string, i, string2);
      }
    }

    public final void handleResetAbusiveEvents() {
      Bundle bundle = this.extras;
      if (bundle != null) {
        String string = bundle.getString("pkgName", null);
        int i = this.extras.getInt("uid", -1);
        if (string == null || i == -1) {
          return;
        }
        this.mMARsPolicyManager.resetAbusiveEvents(string, i);
      }
    }

    public final void handleNotifyTcpuAnomaly() {
      if (this.extras == null) {
        return;
      }
      Slog.d("MARsHandler", "TCPU: case MARS_MH_NOTIFY_ANOMALY_MSG_TCPU");
      ArrayList<Integer> integerArrayList = this.extras.getIntegerArrayList("pidList");
      ArrayList arrayList = new ArrayList();
      ArrayList arrayList2 = new ArrayList();
      ArrayList arrayList3 = new ArrayList();
      Iterator<Integer> it = integerArrayList.iterator();
      while (it.hasNext()) {
        Integer next = it.next();
        int parentPid = Process.getParentPid(next.intValue());
        if (parentPid != 2) {
          if (isZygote(parentPid)) {
            arrayList.add(next);
          } else {
            arrayList2.add(next);
            if (Process.getUidForPid(next.intValue()) >= 10000) {
              arrayList3.add(next);
            }
          }
        }
      }
      try {
        Slog.i("MARsHandler", "TCPU: java pid=" + arrayList + " native pid=" + arrayList2);
        File dumpStackTraces =
            StackTracesDumpHelper.dumpStackTraces(
                arrayList,
                null,
                null,
                CompletableFuture.completedFuture(arrayList2),
                null,
                null,
                null);
        File file = new File("/data/log/TCPU.log");
        if (dumpStackTraces.renameTo(file)) {
          Os.chmod(file.getPath(), FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
          Os.chown(file.getPath(), 1000, 1007);
        } else {
          Slog.w("MARsHandler", "Log file not generated.");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (arrayList3.isEmpty()) {
        return;
      }
      this.mMARsPolicyManager.killPhantomProcessLocked(arrayList3);
    }

    public final boolean isZygote(final int i) {
      return Arrays.stream(Process.getPidsForCommands(new String[] {"zygote", "zygote64"}))
          .anyMatch(
              new IntPredicate() { // from class:
                                   // com.android.server.am.MARsHandler$MainHandler$$ExternalSyntheticLambda0
                @Override // java.util.function.IntPredicate
                public final boolean test(int i2) {
                  boolean lambda$isZygote$0;
                  lambda$isZygote$0 = MARsHandler.MainHandler.lambda$isZygote$0(i, i2);
                  return lambda$isZygote$0;
                }
              });
    }
  }

  public void sendTCPUMsgToMainHandler(ArrayList arrayList) {
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler == null || arrayList == null) {
      return;
    }
    Message obtainMessage = mainHandler.obtainMessage(17);
    Bundle bundle = new Bundle();
    bundle.putIntegerArrayList("pidList", arrayList);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void sendFirstTriggerMsgToMainHandler() {
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler == null) {
      return;
    }
    mainHandler.removeMessages(1);
    Message obtainMessage = this.mMainHandler.obtainMessage(1);
    if (MARsPolicyManager.getInstance().checkIsChinaModel()) {
      this.mMainHandler.sendMessageDelayed(obtainMessage, 5000L);
    } else {
      this.mMainHandler.sendMessage(obtainMessage);
    }
  }

  public void sendRepeatTriggerMsgToMainHandler() {
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler == null) {
      return;
    }
    mainHandler.removeMessages(2);
    this.mMainHandler.sendMessageDelayed(this.mMainHandler.obtainMessage(2), 15000L);
  }

  public void sendUpdatePkgMsgToMainHandler(boolean z) {
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler == null) {
      return;
    }
    mainHandler.removeMessages(3);
    this.mMainHandler.sendMessageDelayed(this.mMainHandler.obtainMessage(3), z ? 0L : 600000L);
  }

  public void sendGetBatteryStatMsgToMainHandler(boolean z) {
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler == null) {
      return;
    }
    mainHandler.removeMessages(4);
    this.mMainHandler.sendMessageDelayed(this.mMainHandler.obtainMessage(4), z ? 0L : 43200000L);
  }

  public void sendCallSetModeMsgToMainHandler(String str, int i, int i2) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString("pkgName", str);
    bundle.putInt("uid", i);
    bundle.putInt("mode", i2);
    Message obtainMessage = this.mMainHandler.obtainMessage(5);
    obtainMessage.setData(bundle);
    if (i2 == 1) {
      this.mMainHandler.sendMessage(obtainMessage);
    } else {
      this.mMainHandler.sendMessageAtFrontOfQueue(obtainMessage);
    }
  }

  public void sendTimeChangedMsgToMainHandler(long j) {
    Bundle bundle = new Bundle();
    bundle.putLong("changedTime", j);
    Message obtainMessage = this.mMainHandler.obtainMessage(6);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void sendRunPolicySpecificPkgMsgToMainHandler(
      ArrayList arrayList, int i, String str, int i2) {
    Bundle bundle = new Bundle();
    bundle.putStringArrayList("packageList", arrayList);
    bundle.putInt("policy-num", i);
    bundle.putString("trigger-reason", str);
    bundle.putInt("userId", i2);
    Message obtainMessage = this.mMainHandler.obtainMessage(7);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void sendCancelPolicyMsgToMainHandler(ArrayList arrayList, int i, int i2) {
    Bundle bundle = new Bundle();
    bundle.putStringArrayList("packageList", arrayList);
    bundle.putInt("policy-num", i);
    bundle.putInt("userId", i2);
    Message obtainMessage = this.mMainHandler.obtainMessage(8);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void sendTriggerPolicyMsgToMainHandler() {
    this.mMainHandler.sendMessage(this.mMainHandler.obtainMessage(9));
  }

  public void sendUpdateDisableMsgToMainHandler(boolean z) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putBoolean("debug", z);
    this.mMainHandler.removeMessages(10);
    Message obtainMessage = this.mMainHandler.obtainMessage(10);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessageDelayed(
        obtainMessage,
        z
            ? ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS
            : BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
  }

  public void sendNotifyDeviceCareMsgToMainHandler(
      String str, ArrayList arrayList, ArrayList arrayList2) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString("extraType", str);
    bundle.putStringArrayList("packageList", arrayList);
    bundle.putIntegerArrayList("uidList", arrayList2);
    Message obtainMessage = this.mMainHandler.obtainMessage(11);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void sendInitDisabledMsgToMainHandler(int i) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putInt("userId", i);
    Message obtainMessage = this.mMainHandler.obtainMessage(12);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void sendCallSetAppStandbyBucketMsgToMainHandler(String str, int i, int i2, boolean z) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString("pkgName", str);
    bundle.putInt("userId", i);
    bundle.putInt("bucket", i2);
    bundle.putBoolean("byUser", z);
    Message obtainMessage = this.mMainHandler.obtainMessage(13);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void sendKillPackageProcsMsgToMainHandler(
      String str, int i, int i2, int i3, boolean z, String str2) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString("pkgName", str);
    bundle.putInt("uid", i);
    bundle.putInt("userId", i2);
    bundle.putInt("minAdj", i3);
    bundle.putBoolean("allowRestart", z);
    bundle.putString("reason", str2);
    Message obtainMessage = this.mMainHandler.obtainMessage(14);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void sendFGSRestrictionTargetMsgToMainHandler(String str, int i) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString("pkgName", str);
    bundle.putInt("uid", i);
    Message obtainMessage = this.mMainHandler.obtainMessage(15);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void sendAnomalyMsgToMainHandler(String str, int i, String str2) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString("pkgName", str);
    bundle.putInt("uid", i);
    bundle.putString("type", str2);
    Message obtainMessage = this.mMainHandler.obtainMessage(16);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void sendSpecificDisableMsgToMainHandler(List list) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putParcelableList("AppRestrictionInfo", list);
    Message obtainMessage = this.mMainHandler.obtainMessage(18);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessageDelayed(obtainMessage, 5000L);
  }

  public void sendResetAbusiveEventsMsgToMainHandler(String str, int i) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString("pkgName", str);
    bundle.putInt("uid", i);
    Message obtainMessage = this.mMainHandler.obtainMessage(19);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void removeMessages(int i) {
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler == null) {
      return;
    }
    mainHandler.removeMessages(i);
  }
}
