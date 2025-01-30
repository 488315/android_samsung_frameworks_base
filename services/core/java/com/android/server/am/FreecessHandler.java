package com.android.server.am;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.util.Slog;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.filter.filter.ActiveMusicRecordFilter;
import com.android.server.am.mars.filter.filter.BlueToothConnectedFilter;
import com.samsung.android.knox.analytics.service.EventQueue;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class FreecessHandler {
  public static String TAG = "FreecessHandler";
  public static int mScreenOnQuickFreezeCheckDelay = 5000;
  public static int mScreenOnQuickFreezeDelayInterval = 5000;
  public final String FREECESS_THREAD_NAME;
  public long lastDelayedTime;
  public BluetoothAdapter mBluetoothAdapter;
  public BluetoothHandler mBluetoothHandler;
  public Context mContext;
  public FreecessThread mFreecessThread;
  public MainHandler mMainHandler;
  public NetLinkReceiverThread mNetLinkReceiverThread;

  public abstract class FreecessHandlerHolder {
    public static final FreecessHandler INSTANCE = new FreecessHandler();
  }

  public FreecessHandler() {
    this.FREECESS_THREAD_NAME = "FreecessHandler";
    this.lastDelayedTime = 0L;
  }

  public static FreecessHandler getInstance() {
    return FreecessHandlerHolder.INSTANCE;
  }

  public void init(Context context) {
    this.mContext = context;
    NetLinkReceiverThread netLinkReceiverThread = new NetLinkReceiverThread("Freecess_NLRCT");
    this.mNetLinkReceiverThread = netLinkReceiverThread;
    netLinkReceiverThread.start();
    new BlueToothThread("Freecess_BT", 0).start();
    this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
  }

  public void init(Context context, HandlerThread handlerThread, HandlerThread handlerThread2) {
    this.mContext = context;
    this.mNetLinkReceiverThread = null;
    this.mBluetoothHandler = new BluetoothHandler(handlerThread2.getLooper());
    this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    this.mFreecessThread = null;
    this.mMainHandler = new MainHandler(handlerThread.getLooper());
  }

  public class NetLinkReceiverThread extends Thread {
    public int mNtPriority;

    public NetLinkReceiverThread(String str) {
      super(str);
      this.mNtPriority = 0;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      Process.setThreadPriority(this.mNtPriority);
      int i = 2;
      boolean z = false;
      while (i > 0) {
        z = FreecessController.getInstance().initSendRecvMsgNetLink();
        if (z) {
          break;
        }
        i--;
        try {
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      FreecessController.getInstance().reportSocketResult(z);
      if (z) {
        FreecessHandler.this.mFreecessThread =
            FreecessHandler.this.new FreecessThread("FreecessHandler", 0);
        FreecessHandler.this.mFreecessThread.start();
        FreecessHandler.receiveNetLinkInformationContinously();
      }
    }
  }

  public static void receiveNetLinkInformationContinously() {
    while (true) {
      FreecessController.getInstance().recvNetLinkAction();
    }
  }

  public class FreecessThread extends HandlerThread {
    public FreecessThread(String str, int i) {
      super(str, i);
    }

    @Override // android.os.HandlerThread, java.lang.Thread, java.lang.Runnable
    public void run() {
      super.run();
    }

    @Override // android.os.HandlerThread
    public void onLooperPrepared() {
      FreecessHandler.this.mMainHandler = FreecessHandler.this.new MainHandler(getLooper());
    }
  }

  public class MainHandler extends Handler {
    public Bundle extras;
    public FreecessController mFreecessController;

    public MainHandler(Looper looper) {
      super(looper);
      this.mFreecessController = FreecessController.getInstance();
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      Bundle data = message.getData();
      this.extras = data;
      switch (message.what) {
        case 1:
          handleScreenOnQuickFreeze();
          return;
        case 2:
          handleCheckImportant();
          return;
        case 3:
          handleChangeToFrozen();
          return;
        case 4:
          if (data != null) {
            this.mFreecessController.lcdOnFreezePackage(
                data.getString("packageName", ""), this.extras.getInt("uid", -1));
            return;
          }
          return;
        case 5:
          handleFreecessResetState();
          return;
        case 6:
          handleFreecessResetAllState();
          return;
        case 7:
          handleFreecessSettlementPackage();
          return;
        case 8:
          if (data != null) {
            this.mFreecessController.checkFrozenBinder(data.getInt("uid", -1));
            return;
          }
          return;
        case 9:
          handleOlaf();
          return;
        case 10:
          this.mFreecessController.unFreezeForOLAF("timeout");
          return;
        case 11:
          if (data != null) {
            int i = data.getInt("type", -1);
            this.mFreecessController.setIsDumpstateWorking(false);
            this.mFreecessController.setIsSmartSwitchWorking(false);
            this.mFreecessController.setFreecessEnableForSpecificReason(true, i);
            return;
          }
          return;
        case 12:
          this.mFreecessController.readSysfs();
          return;
        case 13:
          handleUnfreezeActivePackages();
          return;
        case 14:
          handleFreezeStateChanged();
          return;
        case 15:
          this.mFreecessController.postMonitoringFrozenProcs();
          return;
        case 16:
          if (data != null) {
            this.mFreecessController.deleteRemovedPackage(
                data.getString("packageName", ""), this.extras.getInt("uid", -1));
            return;
          }
          return;
        case 17:
          if (data != null) {
            data.getString("packageName", "");
            this.mFreecessController.unFreezeSpecialPackage(
                this.extras.getInt("uid", -1), this.extras.getString("reason", ""));
            return;
          }
          return;
        case 18:
        case 19:
        case 20:
        case 26:
        case 27:
        default:
          throw new IllegalStateException("Unexpected value: " + message.what);
        case 21:
          if (data != null) {
            this.mFreecessController.triggerCalmMode(data.getStringArrayList("list"));
            return;
          }
          return;
        case 22:
          handleRepeatCalmMode();
          return;
        case 23:
          this.mFreecessController.cancelCalmMode();
          return;
        case 24:
          this.mFreecessController.handleUnfreezeRequestFocusPackage();
          return;
        case 25:
          handleReportStatus();
          return;
        case 28:
          handleManualIdlePackage();
          return;
        case 29:
          handleReportBroadcastQueue();
          return;
      }
    }

    public final void handleScreenOnQuickFreeze() {
      if (MARsDebugConfig.DEBUG_ENG) {
        Slog.d(FreecessHandler.TAG, "handle FREECESS_LCD_ON_QUICK_FREEZE_MSG....");
      }
      if (this.extras != null) {
        this.mFreecessController.updateRunningLocationPackages();
        ActiveMusicRecordFilter.getInstance().getUidListUsingAudio();
        this.mFreecessController.triggerLcdOnFreeze(
            this.extras.getInt("uid", -1), this.extras.getString("packageName", null));
      }
    }

    public final void handleCheckImportant() {
      Bundle bundle = this.extras;
      if (bundle != null) {
        String string = bundle.getString("packageName", "");
        int i = this.extras.getInt("userId", -1);
        this.mFreecessController.lcdOnFreezedStateChange(
            1, this.extras.getString("reason", ""), string, i);
      }
    }

    public final void handleChangeToFrozen() {
      Bundle bundle = this.extras;
      if (bundle != null) {
        String string = bundle.getString("packageName", "");
        int i = this.extras.getInt("userId", -1);
        this.mFreecessController.lcdOnFreezedStateChange(
            2, this.extras.getString("reason", ""), string, i);
      }
    }

    public final void handleFreecessResetState() {
      Bundle bundle = this.extras;
      if (bundle != null) {
        String string = bundle.getString("packageName", null);
        int i = this.extras.getInt("userId", -1);
        if (string == null || i == -1) {
          return;
        }
        this.mFreecessController.handleLcdOnResetState(string, i);
      }
    }

    public final void handleFreecessResetAllState() {
      String string;
      Bundle bundle = this.extras;
      if (bundle == null || (string = bundle.getString("reason", null)) == null) {
        return;
      }
      this.mFreecessController.handleResetAllState(string);
    }

    public final void handleUnfreezeActivePackages() {
      String string;
      Bundle bundle = this.extras;
      if (bundle == null || (string = bundle.getString("reason", null)) == null) {
        return;
      }
      this.mFreecessController.handleUnfreezeActivePackages(string);
    }

    public final void handleFreecessSettlementPackage() {
      Bundle bundle = this.extras;
      if (bundle == null) {
        return;
      }
      String string = bundle.getString("packageName", null);
      int i = this.extras.getInt("userId", -1);
      int i2 = this.extras.getInt("uid", -1);
      int i3 = this.extras.getInt("packetMonitorFlag", -1);
      int i4 = this.extras.getInt("disableWakelockFlag", -1);
      boolean z = this.extras.getBoolean("isLcdOnTrigger", false);
      if (this.extras.getBoolean("unrestrictJobs", false)) {
        MARsPolicyManager.getInstance().restrictJobsByUid(i2, false);
      }
      if (i3 == 0) {
        this.mFreecessController.deletePacketMonitoredUid(i2);
      } else if (i3 == 1) {
        this.mFreecessController.configPacketMonitoredUid(i2, string, i);
      }
      if (i4 != -1) {
        this.mFreecessController.setWakeLockEnableDisable(string, i2, i4 == 1, z);
      }
    }

    public final void handleOlaf() {
      Bundle bundle = this.extras;
      if (bundle != null) {
        boolean z = bundle.getBoolean("enterFlag", false);
        String string = this.extras.getString("packageName", null);
        int i = this.extras.getInt("uid", -1);
        if (z) {
          this.mFreecessController.updateTargetPkgForOLAF(true, string, i);
          this.mFreecessController.triggerOLAF(string, i);
          this.mFreecessController.clearTargetPkgForOLAF();
          return;
        }
        this.mFreecessController.unFreezeForOLAF("Force");
      }
    }

    public final void handleFreezeStateChanged() {
      Bundle bundle = this.extras;
      if (bundle != null) {
        this.mFreecessController.onFreezeStateChanged(
            bundle.getBoolean("enabled", false), this.extras.getInt("uid", -1));
      }
    }

    public final void handleRepeatCalmMode() {
      Bundle bundle = this.extras;
      if (bundle != null) {
        this.mFreecessController.quickFreezeForCalmMode(
            bundle.getString("packageName", null),
            this.extras.getInt("userId", -1),
            this.extras.getString("reason", null));
      }
    }

    public final void handleReportStatus() {
      Bundle bundle = this.extras;
      if (bundle != null) {
        String string = bundle.getString("packageName", null);
        int i = this.extras.getInt("userId", -1);
        String string2 = this.extras.getString("reason", "");
        if (string == null || i == -1) {
          return;
        }
        MARsPolicyManager.getInstance().reportStatusWithMARs(string, i, string2, false);
      }
    }

    public final void handleManualIdlePackage() {
      Bundle bundle = this.extras;
      if (bundle != null) {
        String string = bundle.getString("packageName", null);
        int i = this.extras.getInt("uid", -1);
        int i2 = this.extras.getInt("userId", -1);
        String string2 = this.extras.getString("reason", null);
        if (string == null || i == -1 || i2 == -1 || string2 == null) {
          return;
        }
        this.mFreecessController.makePkgIdleForLcdOn(
            string, i, i2, string2, this.extras.getBoolean("isDelay", false));
      }
    }

    public final void handleReportBroadcastQueue() {
      ArrayList<Integer> integerArrayList;
      Bundle bundle = this.extras;
      if (bundle == null || (integerArrayList = bundle.getIntegerArrayList("pids")) == null) {
        return;
      }
      this.mFreecessController.reportProcessFreezableChangedLocked(integerArrayList);
    }
  }

  public class BlueToothThread extends HandlerThread {
    public BlueToothThread(String str, int i) {
      super(str, i);
    }

    @Override // android.os.HandlerThread, java.lang.Thread, java.lang.Runnable
    public void run() {
      super.run();
    }

    @Override // android.os.HandlerThread
    public void onLooperPrepared() {
      FreecessHandler.this.mBluetoothHandler =
          FreecessHandler.this.new BluetoothHandler(getLooper());
    }
  }

  public class BluetoothHandler extends Handler {
    public BluetoothHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      Bundle data = message.getData();
      if (data == null || FreecessHandler.this.mBluetoothAdapter == null) {
        return;
      }
      int i = data.getInt("uid", -1);
      if (BlueToothConnectedFilter.getInstance().isInBTTargetList(i)) {
        int i2 = message.what;
        if (i2 == 26) {
          if (MARsDebugConfig.DEBUG_ENG) {
            Slog.d(FreecessHandler.TAG, "handle FREECESS_FREEZE_BT_SCAN_MSG....");
          }
          FreecessHandler.this.mBluetoothAdapter.onFreeze(i);
        } else if (i2 == 27) {
          if (MARsDebugConfig.DEBUG_ENG) {
            Slog.d(FreecessHandler.TAG, "handle FREECESS_UNFREEZE_BT_SCAN_MSG....");
          }
          FreecessHandler.this.mBluetoothAdapter.onUnFreeze(i);
        } else {
          throw new IllegalStateException("Unexpected value: " + message.what);
        }
      }
    }
  }

  public void sendUpdateBTMsg(int i, int i2) {
    if (this.mBluetoothHandler.hasMessages(i)) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putInt("uid", i2);
    Message obtainMessage = this.mBluetoothHandler.obtainMessage(i);
    obtainMessage.setData(bundle);
    this.mBluetoothHandler.sendMessage(obtainMessage);
  }

  public void sendUidLcdOnQuickFzMsg(int i, String str, boolean z) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putInt("uid", i);
    bundle.putString("packageName", str);
    Message obtainMessage = this.mMainHandler.obtainMessage(1, str);
    obtainMessage.setData(bundle);
    if (!this.mMainHandler.hasMessages(1, str)) {
      if (z) {
        if (FreecessController.getInstance().isQuickFreezeEnabled()) {
          this.mMainHandler.sendMessageDelayed(obtainMessage, mScreenOnQuickFreezeCheckDelay);
          return;
        } else {
          this.mMainHandler.sendMessageDelayed(obtainMessage, 60000L);
          return;
        }
      }
      this.mMainHandler.sendMessage(obtainMessage);
      return;
    }
    if (MARsDebugConfig.DEBUG_ENG) {
      Slog.d(TAG, "packageName: " + str + " already hasMessage");
    }
  }

  public void sendCheckImportantMsg(String str, int i, String str2) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString("packageName", str);
    bundle.putInt("userId", i);
    bundle.putString("reason", str2);
    Message obtainMessage = this.mMainHandler.obtainMessage(2, str);
    obtainMessage.setData(bundle);
    long j =
        FreecessController.getInstance().isQuickFreezeEnabled()
            ? mScreenOnQuickFreezeDelayInterval
            : 10000L;
    if (this.mMainHandler.hasMessages(2, str)) {
      return;
    }
    this.mMainHandler.sendMessageDelayed(obtainMessage, j);
  }

  public void sendMakePkgIdleMsg(String str, int i, int i2, String str2, boolean z) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString("packageName", str);
    bundle.putInt("uid", i);
    bundle.putInt("userId", i2);
    bundle.putString("reason", str2);
    bundle.putBoolean("isDelay", z);
    Message obtainMessage = this.mMainHandler.obtainMessage(28, str);
    obtainMessage.setData(bundle);
    if (!this.mMainHandler.hasMessages(28, str)) {
      if (z) {
        this.mMainHandler.sendMessageDelayed(obtainMessage, 2000L);
        return;
      } else {
        this.mMainHandler.sendMessage(obtainMessage);
        return;
      }
    }
    if (MARsDebugConfig.DEBUG_ENG) {
      Slog.d(TAG, "packageName: " + str + " already hasMessage");
    }
  }

  public void sendReportToBroadcastQueueMsg(ArrayList arrayList) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putIntegerArrayList("pids", arrayList);
    Message obtainMessage = this.mMainHandler.obtainMessage(29);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public static /* synthetic */ void lambda$sendUnpendingScheduleServiceRestartMsg$0(
      int i, boolean z) {
    FreecessController.getInstance().unpendingScheduleServiceRestartUid(i, z);
  }

  public void sendUnpendingScheduleServiceRestartMsg(final int i, final boolean z) {
    this.mMainHandler.postAtFrontOfQueue(
        new Runnable() { // from class:
                         // com.android.server.am.FreecessHandler$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            FreecessHandler.lambda$sendUnpendingScheduleServiceRestartMsg$0(i, z);
          }
        });
  }

  public void sendChangeToFrozenMsg(String str, int i, String str2) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString("packageName", str);
    bundle.putInt("userId", i);
    bundle.putString("reason", str2);
    Message obtainMessage = this.mMainHandler.obtainMessage(3, str);
    obtainMessage.setData(bundle);
    long j =
        FreecessController.getInstance().isQuickFreezeEnabled()
            ? mScreenOnQuickFreezeDelayInterval
            : 10000L;
    if (this.mMainHandler.hasMessages(3, str)) {
      return;
    }
    this.mMainHandler.sendMessageDelayed(obtainMessage, j);
  }

  public void sendLcdOnFreezeTriggerMsg(String str, int i) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString("packageName", str);
    bundle.putInt("uid", i);
    Message obtainMessage = this.mMainHandler.obtainMessage(4);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void sendResetStateMsg(String str, int i) {
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler == null) {
      return;
    }
    mainHandler.removeMessages(5);
    Bundle bundle = new Bundle();
    bundle.putString("packageName", str);
    bundle.putInt("userId", i);
    Message obtainMessage = this.mMainHandler.obtainMessage(5);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void sendResetAllStateMsg(String str) {
    if (this.mMainHandler == null) {
      return;
    }
    boolean equals = "Watchdog_HALF".equals(str);
    if (equals) {
      FreecessController.getInstance().handleResetAllPreAction();
    }
    this.mMainHandler.removeMessages(6);
    Bundle bundle = new Bundle();
    bundle.putString("reason", str);
    Message obtainMessage = this.mMainHandler.obtainMessage(6);
    obtainMessage.setData(bundle);
    if (equals) {
      this.mMainHandler.sendMessageDelayed(obtainMessage, 5000L);
    } else {
      this.mMainHandler.sendMessage(obtainMessage);
    }
  }

  public void sendCalmModeTriggerMsg(ArrayList arrayList) {
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler == null) {
      return;
    }
    mainHandler.removeMessages(21);
    Bundle bundle = new Bundle();
    bundle.putStringArrayList("list", arrayList);
    Message obtainMessage = this.mMainHandler.obtainMessage(21);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void sendCalmModeRepeatMsg(String str, int i, String str2) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString("packageName", str);
    bundle.putInt("userId", i);
    bundle.putString("reason", str2);
    Message obtainMessage = this.mMainHandler.obtainMessage(22, str);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessageDelayed(obtainMessage, 2000L);
  }

  public void sendCalmModeCancelMsg() {
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler == null) {
      return;
    }
    Message obtainMessage = mainHandler.obtainMessage(23);
    this.mMainHandler.removeMessages(23);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void removeCalmModeFreezeMsg() {
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler == null) {
      return;
    }
    mainHandler.removeMessages(22);
  }

  public void sendUnfreezeActivePackagesMsg(String str) {
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler == null) {
      return;
    }
    mainHandler.removeMessages(13);
    Bundle bundle = new Bundle();
    bundle.putString("reason", str);
    Message obtainMessage = this.mMainHandler.obtainMessage(13);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void sendUnfreezeRequestFocusPackageMsg() {
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler == null) {
      return;
    }
    mainHandler.removeMessages(24);
    this.mMainHandler.sendMessage(this.mMainHandler.obtainMessage(24));
  }

  public void sendUnfreezeSpecialPackageMsg(String str, int i, String str2) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString("packageName", str);
    bundle.putInt("uid", i);
    bundle.putString("reason", str2);
    Message obtainMessage = this.mMainHandler.obtainMessage(17);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void sendFreecessSettlementMsg(
      String str, int i, int i2, int i3, int i4, boolean z, boolean z2, boolean z3) {
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler == null) {
      return;
    }
    Message obtainMessage = mainHandler.obtainMessage(7);
    Bundle bundle = new Bundle();
    bundle.putString("packageName", str);
    bundle.putInt("userId", i);
    bundle.putInt("uid", i2);
    bundle.putInt("packetMonitorFlag", i3);
    bundle.putInt("disableWakelockFlag", i4);
    bundle.putBoolean("isLcdOnTrigger", z2);
    bundle.putBoolean("unrestrictJobs", z3);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
    if (z) {
      Message obtainMessage2 = this.mMainHandler.obtainMessage(8);
      Bundle bundle2 = new Bundle();
      bundle2.putInt("uid", i2);
      obtainMessage2.setData(bundle2);
      this.mMainHandler.sendMessageDelayed(obtainMessage2, 2000L);
    }
  }

  public void sendOLAFMsg(boolean z, String str, int i) {
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler == null) {
      return;
    }
    mainHandler.removeMessages(9);
    Bundle bundle = new Bundle();
    bundle.putBoolean("enterFlag", z);
    bundle.putString("packageName", str);
    bundle.putInt("uid", i);
    Message obtainMessage = this.mMainHandler.obtainMessage(9);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessageAtFrontOfQueue(obtainMessage);
  }

  public void sendOLAFTimeOutMsg(long j) {
    int i;
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler == null) {
      return;
    }
    Message obtainMessage = mainHandler.obtainMessage(10);
    if (j != 0) {
      i = (int) (j - SystemClock.uptimeMillis());
      if (i > 5000) {
        i = 5000;
      }
      if (i <= 0) {
        this.mMainHandler.sendMessage(obtainMessage);
        return;
      }
    } else {
      i = 3000;
    }
    this.mMainHandler.sendMessageDelayed(obtainMessage, i);
  }

  public void sendProcPostMonitoringMsg() {
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler == null || mainHandler.hasMessages(15)) {
      return;
    }
    this.mMainHandler.sendMessageDelayed(this.mMainHandler.obtainMessage(15), 1000L);
  }

  public void sendOnFreezeStateChanged(boolean z, int i) {
    if (this.mMainHandler == null) {
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putBoolean("enabled", z);
    bundle.putInt("uid", i);
    Message obtainMessage = this.mMainHandler.obtainMessage(14);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void sendSetFreecessEnableDelayedMsg(int i) {
    if (this.mMainHandler == null) {
      return;
    }
    int i2 = i == 2 ? 660000 : EventQueue.LOG_WAIT_TIME;
    long currentTimeMillis = System.currentTimeMillis();
    long j = i2;
    if (this.lastDelayedTime - currentTimeMillis < j) {
      this.lastDelayedTime = currentTimeMillis + j;
      this.mMainHandler.removeMessages(11);
      Bundle bundle = new Bundle();
      bundle.putInt("type", i);
      Message obtainMessage = this.mMainHandler.obtainMessage(11);
      obtainMessage.setData(bundle);
      this.mMainHandler.sendMessageDelayed(obtainMessage, j);
    }
  }

  public void sendSCPMChangedPkgMsgToDBHandler() {
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler == null) {
      return;
    }
    this.mMainHandler.sendMessageDelayed(mainHandler.obtainMessage(12), 30000L);
  }

  public void sendRemovePackageMsg(String str, int i) {
    FreecessPkgStatus packageStatus;
    if (this.mMainHandler == null
        || (packageStatus = FreecessController.getInstance().getPackageStatus(i)) == null) {
      return;
    }
    removeBgTriggerMsgByObj(1, packageStatus.name);
    removeBgTriggerMsgByObj(2, packageStatus.name);
    removeBgTriggerMsgByObj(28, packageStatus.name);
    removeBgTriggerMsgByObj(3, packageStatus.name);
    removeBgTriggerMsgByObj(4, packageStatus.name);
    Bundle bundle = new Bundle();
    bundle.putString("packageName", str);
    bundle.putInt("uid", i);
    Message obtainMessage = this.mMainHandler.obtainMessage(16);
    obtainMessage.setData(bundle);
    this.mMainHandler.sendMessage(obtainMessage);
  }

  public void removeOLAFTimeOutMsg() {
    if (this.mMainHandler == null) {
      return;
    }
    if (MARsDebugConfig.DEBUG_ENG) {
      Slog.d(TAG, "removeOLAFTimeOutMsg....");
    }
    this.mMainHandler.removeMessages(10);
  }

  public void removeBgTriggerMsgByObj(int i, Object obj) {
    if (this.mMainHandler == null) {
      return;
    }
    if (MARsDebugConfig.DEBUG_ENG) {
      Slog.d(TAG, "removeBgTriggerMsgByObj....what:" + i + ",obj:" + obj);
    }
    this.mMainHandler.removeMessages(i, obj);
  }

  public void removeBgTriggerMsg() {
    if (this.mMainHandler == null) {
      return;
    }
    if (MARsDebugConfig.DEBUG_ENG) {
      Slog.d(TAG, "removeBgTriggerMsg....");
    }
    this.mMainHandler.removeMessages(2);
    this.mMainHandler.removeMessages(3);
    this.mMainHandler.removeMessages(28);
    this.mMainHandler.removeMessages(1);
    this.mMainHandler.removeMessages(4);
  }

  public void removeMessages(int i) {
    MainHandler mainHandler = this.mMainHandler;
    if (mainHandler != null) {
      mainHandler.removeMessages(i);
    }
  }

  public void setQuickFrecessCheckTime(int i) {
    mScreenOnQuickFreezeCheckDelay = i;
  }

  public void setQuickFrecessIntervalTime(int i) {
    mScreenOnQuickFreezeDelayInterval = i;
  }
}
