package com.android.server.chimera.heimdall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.util.Log;
import com.android.server.am.ActivityManagerService;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class Heimdall {
  public static int SCAN_VERSION = 1;
  public boolean DISABLED;
  public HeimdallPhaseManager mHeimdallPhaseManager;
  public PhaseHandler mPhaseHandler;
  public ScpmManager mScpmManager;
  public boolean KILL_DISABLED =
      "1".equals(SystemProperties.get("persist.sys.heimdalld.kill_disable", "0"));
  public final boolean IS_SHIP_BUILD =
      "true".equals(SystemProperties.get("ro.product_ship", "false"));

  public static native long[] readMemtrackMemory(int i);

  public Heimdall(Looper looper, Context context, ActivityManagerService activityManagerService) {
    this.DISABLED = "true".equals(SystemProperties.get("persist.sys.heimdalld.disable", "false"));
    try {
      this.mHeimdallPhaseManager = new HeimdallPhaseManager(context, activityManagerService);
      this.mPhaseHandler = new PhaseHandler(looper);
      this.mScpmManager = new ScpmManager(context);
      if (this.KILL_DISABLED) {
        this.mHeimdallPhaseManager.setHeimdallKillDisabled();
      }
    } catch (Exception e) {
      log("Heimdall init failed " + e.getMessage());
      e.printStackTrace();
      this.DISABLED = true;
    }
    log("DISABLED=" + this.DISABLED + " KILL_DISABLED=" + this.KILL_DISABLED);
    if (this.DISABLED) {
      return;
    }
    log("SCAN_VERSION=" + SCAN_VERSION);
  }

  public void checkProcess(int i, String str, String str2) {
    if (this.DISABLED
        || this.mHeimdallPhaseManager == null
        || this.mPhaseHandler == null
        || str == null
        || str2 == null) {
      return;
    }
    HeimdallProcessData obtainData = HeimdallProcessData.obtainData();
    obtainData.pid = i;
    obtainData.processName = str;
    obtainData.firstAppPackageName = str2;
    this.mPhaseHandler.requestNextPhase(obtainData);
  }

  public final void processNextPhase(HeimdallProcessData heimdallProcessData) {
    boolean processKillAndReportPhase;
    heimdallProcessData.moveNextPhaseIfNotRetry();
    int currentPhase = heimdallProcessData.currentPhase();
    if (currentPhase == 4) {
      processKillAndReportPhase =
          this.mHeimdallPhaseManager.processKillAndReportPhase(heimdallProcessData);
    } else if (currentPhase == 8) {
      processKillAndReportPhase =
          this.mHeimdallPhaseManager.processRescanPhase(heimdallProcessData);
    } else if (currentPhase == 16) {
      processKillAndReportPhase = this.mHeimdallPhaseManager.processGcPhase(heimdallProcessData);
    } else if (currentPhase != 32) {
      processKillAndReportPhase =
          currentPhase != 64
              ? false
              : this.mHeimdallPhaseManager.processStartPhase(heimdallProcessData);
    } else {
      processKillAndReportPhase = this.mHeimdallPhaseManager.processScanPhase(heimdallProcessData);
    }
    if (!processKillAndReportPhase) {
      heimdallProcessData.recycle();
    } else {
      if (heimdallProcessData.currentPhase() == 4) {
        return;
      }
      this.mPhaseHandler.requestNextPhase(heimdallProcessData);
    }
  }

  public class PhaseHandler extends Handler {
    public final AtomicInteger mNumberOfPendingMessages;

    public void requestNextPhase(HeimdallProcessData heimdallProcessData) {
      Message obtainMessage = obtainMessage(1, heimdallProcessData);
      this.mNumberOfPendingMessages.incrementAndGet();
      sendMessageDelayed(obtainMessage, heimdallProcessData.delayPhase);
    }

    public int getNumberOfPendingMessages() {
      return this.mNumberOfPendingMessages.get();
    }

    public PhaseHandler(Looper looper) {
      super(looper);
      this.mNumberOfPendingMessages = new AtomicInteger(0);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      this.mNumberOfPendingMessages.decrementAndGet();
      if (message.what != 1) {
        return;
      }
      Heimdall.this.processNextPhase((HeimdallProcessData) message.obj);
    }
  }

  public class ScpmManager extends BroadcastReceiver {
    public final Uri AUTHORITY_URI;
    public final Uri CONTENT_URI;
    public final Uri POLICY_ITEM_URI;

    public ScpmManager(Context context) {
      Uri parse = Uri.parse("content://com.samsung.android.sm.policy");
      this.AUTHORITY_URI = parse;
      Uri withAppendedPath = Uri.withAppendedPath(parse, "policy_item");
      this.CONTENT_URI = withAppendedPath;
      this.POLICY_ITEM_URI = Uri.withAppendedPath(withAppendedPath, "heimdall");
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("sec.app.policy.UPDATE.heimdall");
      context.registerReceiver(this, intentFilter);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      if ("sec.app.policy.UPDATE.heimdall".equals(intent.getAction())) {
        onScpmUpdate(context);
      }
    }

    public final void onScpmUpdate(Context context) {
      Cursor query;
      String str;
      String str2;
      String trim = SystemProperties.get("ro.product.device", "").trim();
      if ("".equals(trim)
          || (query =
                  context.getContentResolver().query(this.POLICY_ITEM_URI, null, null, null, null))
              == null) {
        return;
      }
      while (true) {
        if (!query.moveToNext()) {
          break;
        }
        try {
          str = query.getString(1);
          str2 = query.getString(2);
        } catch (Exception unused) {
          str = null;
          str2 = null;
        }
        if (str != null && str2 != null && str.equals(trim)) {
          try {
            if ("0".equals(str2)) {
              SystemProperties.set("persist.sys.heimdalld.disable", "0");
              Heimdall.this.DISABLED = false;
              Heimdall.log("DISABLED set false");
            } else if ("1".equals(str2)) {
              SystemProperties.set("persist.sys.heimdalld.disable", "1");
              Heimdall.this.DISABLED = true;
              Heimdall.log("DISABLED set true");
            }
          } catch (Exception e) {
            Heimdall.log(e.getMessage());
            query.close();
            return;
          }
        }
      }
      query.close();
    }
  }

  public void dumpInfo(PrintWriter printWriter, String[] strArr) {
    if (printWriter == null || strArr == null) {
      return;
    }
    printWriter.println("== heimdall dump start ==");
    try {
      if (strArr.length == 1) {
        printWriter.println("Configurations");
        StringBuilder sb = new StringBuilder();
        sb.append("  feature enable: ");
        sb.append(!this.DISABLED);
        printWriter.println(sb.toString());
        if (!this.DISABLED && this.mHeimdallPhaseManager != null) {
          StringBuilder sb2 = new StringBuilder();
          sb2.append("  kill: ");
          sb2.append(this.KILL_DISABLED ? false : true);
          printWriter.println(sb2.toString());
          printWriter.println("  SCAN_VERSION: " + SCAN_VERSION);
          this.mHeimdallPhaseManager.dumpKillStatus(printWriter);
          this.mHeimdallPhaseManager.dumpProcessList(printWriter);
          printWriter.println("\nOthers");
          if (this.mPhaseHandler != null) {
            printWriter.println(
                "  The number of pending message in phase handler: "
                    + this.mPhaseHandler.getNumberOfPendingMessages());
          }
        }
      } else if (strArr.length >= 2) {
        if (!this.DISABLED && this.mHeimdallPhaseManager != null) {
          if (this.IS_SHIP_BUILD) {
            printWriter.println("available in noship binary");
          } else if ("kill_disable".equals(strArr[1])) {
            SystemProperties.set("persist.sys.heimdalld.kill_disable", "1");
            this.KILL_DISABLED = true;
            this.mHeimdallPhaseManager.setHeimdallKillDisabled();
          } else if ("kill_enable".equals(strArr[1])) {
            SystemProperties.set("persist.sys.heimdalld.kill_disable", "0");
            this.KILL_DISABLED = false;
            this.mHeimdallPhaseManager.setHeimdallKillEnabled();
          } else if ("spec".equals(strArr[1])) {
            printWriter.println("\nSpec list");
            this.mHeimdallPhaseManager.dumpSpecList(printWriter);
          } else {
            printWriter.println("Please input the correct heimdall option.");
          }
        }
        printWriter.println("heimdall disabled");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    printWriter.println("== heimdall dump end ==");
  }

  public static void log(String str) {
    Log.i("Heimdall", str);
  }
}
