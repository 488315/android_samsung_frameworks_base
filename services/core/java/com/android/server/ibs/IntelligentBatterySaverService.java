package com.android.server.ibs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IIntelligentBatterySaverService;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.server.ibs.sleepmode.SleepModeLogger;
import com.android.server.ibs.sleepmode.SleepModePolicyController;
import com.android.server.ibs.sqd.IbsQuickDim;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class IntelligentBatterySaverService extends IIntelligentBatterySaverService.Stub {
  public static boolean mIBSEnable = false;
  public BootCompleteReceiver mBootCompleteReceiver;
  public final Context mContext;
  public final HandlerThread mHandlerThread;
  public IntelligentBatterySaverDexoptManager mIBSDexoptManager;
  public IntelligentBatterySaverFastDrainPolicy mIBSFastDrainPolicy;
  public IntelligentBatterySaverGather mIBSGather;
  public IntelligentBatterySaverGoogleAppPolicy mIBSGoogleAppPolicy;
  public IntelligentBatterySaverLogger mIBSLogger;
  public final IbsQuickDim mIBSQuickDim;
  public IntelligentBatterySaverScpmManager mIBSScpmManager;
  public IntelligentBatterySaverSettingsObserver mIBSSettingsObserver;
  public IntelligentBatterySaverSurvey mIBSSurvey;
  public SCPMReceiver mSCPMReceiver;
  public ServiceHandler mServiceHandler;
  public SleepModeLogger mSleepModeLogger;
  public SleepModePolicyController mSleepModePolicyController;

  public void setSarrUiControlEnable(boolean z) {}

  public IntelligentBatterySaverService(Context context) {
    this.mContext = context;
    mIBSEnable = Boolean.parseBoolean(SystemProperties.get("sys.config.ibs.enable"));
    HandlerThread handlerThread = new HandlerThread("IntelligentBatterySaverService", 1);
    this.mHandlerThread = handlerThread;
    handlerThread.start();
    if (mIBSEnable) {
      this.mServiceHandler = new ServiceHandler(handlerThread.getLooper());
      this.mIBSLogger = IntelligentBatterySaverLogger.getInstance();
      this.mIBSSurvey = new IntelligentBatterySaverSurvey(context);
      this.mIBSGather = new IntelligentBatterySaverGather(context, this, this.mIBSLogger);
      this.mIBSSettingsObserver = new IntelligentBatterySaverSettingsObserver(context, this);
      this.mIBSFastDrainPolicy =
          new IntelligentBatterySaverFastDrainPolicy(
              context, handlerThread, this.mIBSLogger, this.mIBSSurvey);
      this.mIBSGoogleAppPolicy = new IntelligentBatterySaverGoogleAppPolicy(context, handlerThread);
      this.mIBSScpmManager = IntelligentBatterySaverScpmManager.getInstance(context);
      onBootCompleted();
    }
    IbsQuickDim ibsQuickDim = new IbsQuickDim(context);
    this.mIBSQuickDim = ibsQuickDim;
    ibsQuickDim.init();
    SleepModeLogger sleepModeLogger = SleepModeLogger.getInstance();
    this.mSleepModeLogger = sleepModeLogger;
    this.mSleepModePolicyController =
        new SleepModePolicyController(context, handlerThread, sleepModeLogger);
    IntelligentBatterySaverDexoptManager intelligentBatterySaverDexoptManager =
        new IntelligentBatterySaverDexoptManager();
    this.mIBSDexoptManager = intelligentBatterySaverDexoptManager;
    intelligentBatterySaverDexoptManager.init();
  }

  public final void init() {
    this.mBootCompleteReceiver = new BootCompleteReceiver();
    this.mSCPMReceiver = new SCPMReceiver();
  }

  public final void forceResetEveryPolicy() {
    this.mIBSFastDrainPolicy.sendForceEixtFastDrainRestrictionMessage();
    this.mIBSGoogleAppPolicy.setGoogAppNetworkForceReset();
  }

  public final void onBootCompleted() {
    init();
    this.mIBSGather.init();
    this.mIBSSettingsObserver.init();
    this.mIBSFastDrainPolicy.init();
    this.mIBSGoogleAppPolicy.init();
  }

  public boolean addSqdBlockList(int i, String str) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
    return this.mIBSQuickDim.addBlockList(i, str);
  }

  public boolean removeSqdBlockList(int i, String str) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
    return this.mIBSQuickDim.removeBlockList(i, str);
  }

  public boolean isSqdUiControlEnabled() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
    return this.mIBSQuickDim.getSQDUiControlEnable();
  }

  public boolean isSqdSupport() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
    return this.mIBSQuickDim.isSqdSupport();
  }

  public void setSqdUiControlEnabled(boolean z) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
    this.mIBSQuickDim.setUicontrolEnable(z);
  }

  public Map getSqdBlockList() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
    return this.mIBSQuickDim.getBlockList();
  }

  public long[] getGain() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
    return this.mIBSQuickDim.getGain();
  }

  public void setSleepModeEnabled(boolean z) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
    this.mSleepModePolicyController.setSleepModeEnable(z);
  }

  public void setSleepTime(long j, long j2) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
    this.mSleepModePolicyController.setSleepTime(j, j2);
  }

  public void setRubinEvent(String str) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
    this.mSleepModePolicyController.setRubinEvent(str);
  }

  public boolean isEnableSerive() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
    return this.mSleepModePolicyController.isEnableSerive();
  }

  public Bundle getOperationHistory() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
    return this.mSleepModePolicyController.getOperationHistory();
  }

  public Bundle getSleepTime() {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
    return this.mSleepModePolicyController.getSleepTime();
  }

  public List dexoptPackages(List list) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.CONNECTIVITY_INTERNAL", "IntelligentBatterySaverService");
    return this.mIBSDexoptManager.dexoptPackages(list);
  }

  public class ServiceHandler extends Handler {
    public ServiceHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i = message.what;
      if (i != 1) {
        if (i != 2) {
          return;
        }
        IntelligentBatterySaverService.this.forceResetEveryPolicy();
      } else {
        IntelligentBatterySaverService.this.mServiceHandler.sendEmptyMessage(2);
        if (IntelligentBatterySaverService.this.mIBSScpmManager != null) {
          IntelligentBatterySaverService.this.mIBSScpmManager.updateSCPMParametersFromDB(
              IntelligentBatterySaverService.this.mIBSFastDrainPolicy);
        }
      }
    }
  }

  public class BootCompleteReceiver extends BroadcastReceiver {
    public BootCompleteReceiver() {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
      IntelligentBatterySaverService.this.mContext.registerReceiver(this, intentFilter);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
        Slog.d(
            "IntelligentBatterySaverService",
            "ACTION_BOOT_COMPLETED broadcast received: " + intent.getAction());
        if (IntelligentBatterySaverService.this.mIBSScpmManager != null) {
          IntelligentBatterySaverService.this.mIBSScpmManager.registerScpm();
        }
        Optional.ofNullable(IntelligentBatterySaverService.this.mIBSFastDrainPolicy)
            .ifPresent(
                new Consumer() { // from class:
                  // com.android.server.ibs.IntelligentBatterySaverService$BootCompleteReceiver$$ExternalSyntheticLambda0
                  @Override // java.util.function.Consumer
                  public final void accept(Object obj) {
                    ((IntelligentBatterySaverFastDrainPolicy) obj).sendBootCompletedMessage();
                  }
                });
        IntelligentBatterySaverService.this.mServiceHandler.sendEmptyMessage(1);
      }
    }
  }

  public class SCPMReceiver extends BroadcastReceiver {
    public SCPMReceiver() {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("com.samsung.android.scpm.policy.UPDATE.ibs");
      intentFilter.addAction(KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA);
      IntelligentBatterySaverService.this.mContext.registerReceiver(this, intentFilter);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if ("com.samsung.android.scpm.policy.UPDATE.ibs".equals(action)) {
        Slog.d("IntelligentBatterySaverService", "SCPM update broadcast received!");
        IntelligentBatterySaverService.this.mServiceHandler.sendEmptyMessage(1);
      } else if (KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA.equals(action)) {
        Slog.d(
            "IntelligentBatterySaverService",
            "SCPM clear broadcast received, policy updated 1 min later!");
        IntelligentBatterySaverService.this.mServiceHandler.postDelayed(
            new Runnable() { // from class:
              // com.android.server.ibs.IntelligentBatterySaverService.SCPMReceiver.1
              @Override // java.lang.Runnable
              public void run() {
                if (IntelligentBatterySaverService.this.mIBSScpmManager != null) {
                  IntelligentBatterySaverService.this.mIBSScpmManager.registerScpm();
                }
              }
            },
            60000L);
      }
    }
  }

  public void dump(
      FileDescriptor fileDescriptor, final PrintWriter printWriter, final String[] strArr) {
    if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
      printWriter.println(
          "Permission Denial: can't dump IntelligentBatterySaverService from pid="
              + Binder.getCallingPid()
              + ", uid="
              + Binder.getCallingUid()
              + " without permission android.permission.DUMP");
      return;
    }
    if (mIBSEnable) {
      printWriter.println("");
      printWriter.println("IBS Version: 1.0");
      this.mIBSFastDrainPolicy.dump(printWriter, strArr);
      this.mIBSGoogleAppPolicy.dump(printWriter, strArr);
      this.mIBSLogger.dumpIBSHistoryLog(printWriter, strArr);
    }
    IbsQuickDim ibsQuickDim = this.mIBSQuickDim;
    if (ibsQuickDim != null) {
      ibsQuickDim.dump(printWriter, strArr);
    }
    IntelligentBatterySaverScpmManager intelligentBatterySaverScpmManager = this.mIBSScpmManager;
    if (intelligentBatterySaverScpmManager != null) {
      intelligentBatterySaverScpmManager.dump(printWriter, strArr);
    }
    Optional.ofNullable(this.mSleepModePolicyController)
        .ifPresent(
            new Consumer() { // from class:
              // com.android.server.ibs.IntelligentBatterySaverService$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((SleepModePolicyController) obj).dump(printWriter, strArr);
              }
            });
    Optional.ofNullable(this.mSleepModeLogger)
        .ifPresent(
            new Consumer() { // from class:
              // com.android.server.ibs.IntelligentBatterySaverService$$ExternalSyntheticLambda1
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((SleepModeLogger) obj).dumpSleepModeHistoryLog(printWriter, strArr);
              }
            });
    Optional.ofNullable(this.mIBSDexoptManager)
        .ifPresent(
            new Consumer() { // from class:
              // com.android.server.ibs.IntelligentBatterySaverService$$ExternalSyntheticLambda2
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((IntelligentBatterySaverDexoptManager) obj).dump(printWriter, strArr);
              }
            });
  }
}
