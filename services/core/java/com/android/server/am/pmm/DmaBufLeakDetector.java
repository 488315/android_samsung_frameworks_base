package com.android.server.am.pmm;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.IInstalld;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.os.KernelAllocationStats;
import com.android.internal.util.MemInfoReader;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.ToIntFunction;

/* loaded from: classes.dex */
public class DmaBufLeakDetector {
  public Context mContext;
  public boolean mIsListenerAppInstalled;
  public boolean mIsReporting;
  public boolean mIsTestMode;
  public long mLastCheckTime;
  public long mLastLeakTime;
  public float mLeakThreshold;
  public long mMemTotal;
  public int mReportCount;
  public static final String[] ISSUE_TRACKER_INTENT_LISTENERS_LIST = {
    "com.salab.issuetracker", "com.sec.salab.voyager"
  };
  public static final float[][] LEAK_THRESHOLD_STRATEGY = {
    new float[] {2.0f, 0.8f},
    new float[] {3.0f, 1.2f},
    new float[] {4.0f, 1.6f},
    new float[] {6.0f, 1.8f},
    new float[] {8.0f, 2.4f},
    new float[] {12.0f, 3.0f}
  };
  public static final String[] MEMINFO_FILEPATH = {"/proc/meminfo", "/proc/meminfo_extra"};
  public static final String[][] MEMINFO_CATEGORY = {
    new String[] {"system", "system-uncached", "mtk_mm", "mtk_mm-uncached"},
    new String[] {"SystemHeap", "Not-Used", "Not-Used", "Not-Used"}
  };

  public DmaBufLeakDetector() {
    MemInfoReader memInfoReader = new MemInfoReader();
    memInfoReader.readMemInfo();
    this.mMemTotal = memInfoReader.getTotalSizeKb();
    int i = 0;
    while (true) {
      float[][] fArr = LEAK_THRESHOLD_STRATEGY;
      if (i >= fArr.length) {
        break;
      }
      float[] fArr2 = fArr[i];
      this.mLeakThreshold = fArr2[1] * 1024.0f * 1024.0f;
      if (fArr2[0] > this.mMemTotal / 1048576.0f) {
        break;
      } else {
        i++;
      }
    }
    String str = SystemProperties.get("persist.sys.dmabuf_leak_threshold_kb", "");
    if (!TextUtils.isEmpty(str)) {
      try {
        int parseInt = Integer.parseInt(str);
        if (parseInt > 0) {
          this.mLeakThreshold = parseInt;
        }
      } catch (Exception unused) {
      }
    }
    Slog.i("DmaBufLeakDetector", "DmaBuf Threshold : " + this.mLeakThreshold);
  }

  public void receiveSource(String str) {
    for (String str2 : ISSUE_TRACKER_INTENT_LISTENERS_LIST) {
      if (str2.equals(str)) {
        Slog.i("DmaBufLeakDetector", "Listener app message received : " + str2);
        this.mIsListenerAppInstalled = true;
        return;
      }
    }
    Slog.e("DmaBufLeakDetector", "Listener app message received but not found");
  }

  public synchronized void onCheckMemoryLeak(Context context) {
    if (this.mContext == null) {
      this.mContext = context;
      this.mIsListenerAppInstalled = isListenerAppInstalled(context);
    }
    if (this.mIsReporting) {
      return;
    }
    long currentTimeMillis = System.currentTimeMillis();
    if (isTestMode()) {
      Slog.d(
          "DmaBufLeakDetector",
          "getDmaBufSizeKb()="
              + getDmaBufSizeKb()
              + ", getLargestDmaBufProcess()="
              + getLargestDmaBufProcess()
              + ", isCameraRunning()="
              + isCameraRunning()
              + ", mLeakThreshold="
              + this.mLeakThreshold
              + ", mIsListenerAppInstalled="
              + this.mIsListenerAppInstalled);
    } else if (this.mIsListenerAppInstalled) {
      if (currentTimeMillis > this.mLastLeakTime + 600000) {
        if (!isCameraRunning() && hasLeak(getDmaBufSizeKb())) {
          String largestDmaBufProcess = getLargestDmaBufProcess();
          if (largestDmaBufProcess == null) {
            return;
          }
          if (!isCameraRunning() && hasLeak(getDmaBufSizeKb())) {
            this.mLastLeakTime = currentTimeMillis;
            reportLeak(context, largestDmaBufProcess);
          }
        }
      }
    } else if (currentTimeMillis > this.mLastCheckTime + 60000) {
      this.mLastCheckTime = currentTimeMillis;
      if (!isCameraRunning() && hasLeak(getDmaBufSizeKb())) {
        getLargestDmaBufProcess();
      }
    }
  }

  public boolean hasLeak(float f) {
    return f > this.mLeakThreshold;
  }

  public boolean isTestMode() {
    return this.mIsTestMode;
  }

  public void setTestMode(boolean z) {
    this.mIsTestMode = z;
  }

  public long getDmaBufSizeKb() {
    String[] strArr;
    int i;
    FileReader fileReader;
    String[] strArr2;
    int i2;
    StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
    String[] strArr3 = MEMINFO_FILEPATH;
    int length = strArr3.length;
    long j = 0;
    int i3 = 0;
    int i4 = 0;
    while (i3 < length) {
      try {
        fileReader = new FileReader(strArr3[i3], StandardCharsets.UTF_8);
      } catch (Exception unused) {
        strArr = strArr3;
        i = length;
      }
      try {
        BufferedReader bufferedReader =
            new BufferedReader(fileReader, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES);
        try {
          String readLine = bufferedReader.readLine();
          while (readLine != null) {
            String[] strArr4 = MEMINFO_CATEGORY[i4];
            int length2 = strArr4.length;
            int i5 = 0;
            while (i5 < length2) {
              if (readLine.startsWith(strArr4[i5] + XmlUtils.STRING_ARRAY_SEPARATOR)) {
                Slog.d("DmaBufLeakDetector", readLine);
                strArr2 = strArr3;
                i2 = length;
                j += Integer.parseInt(readLine.replaceAll("\\D+", ""));
              } else {
                strArr2 = strArr3;
                i2 = length;
              }
              i5++;
              strArr3 = strArr2;
              length = i2;
            }
            strArr = strArr3;
            i = length;
            try {
              readLine = bufferedReader.readLine();
              strArr3 = strArr;
              length = i;
            } catch (Throwable th) {
              th = th;
              Throwable th2 = th;
              try {
                bufferedReader.close();
              } catch (Throwable th3) {
                th2.addSuppressed(th3);
              }
              throw th2;
            }
          }
          strArr = strArr3;
          i = length;
          try {
            bufferedReader.close();
            try {
              fileReader.close();
            } catch (Exception unused2) {
            }
            i4++;
            i3++;
            strArr3 = strArr;
            length = i;
          } catch (Throwable th4) {
            th = th4;
            Throwable th5 = th;
            try {
              fileReader.close();
            } catch (Throwable th6) {
              th5.addSuppressed(th6);
            }
            throw th5;
          }
        } catch (Throwable th7) {
          th = th7;
          strArr = strArr3;
          i = length;
        }
      } catch (Throwable th8) {
        th = th8;
        strArr = strArr3;
        i = length;
      }
    }
    StrictMode.setThreadPolicy(allowThreadDiskReads);
    Slog.i("DmaBufLeakDetector", "DmaBuf : " + j + " KB");
    return j;
  }

  public String getLargestDmaBufProcess() {
    KernelAllocationStats.ProcessDmabuf[] dmabufAllocations =
        KernelAllocationStats.getDmabufAllocations();
    if (dmabufAllocations == null || dmabufAllocations.length == 0) {
      return null;
    }
    Arrays.sort(
        dmabufAllocations,
        Comparator.comparingInt(
            new ToIntFunction() { // from class:
                                  // com.android.server.am.pmm.DmaBufLeakDetector$$ExternalSyntheticLambda1
              @Override // java.util.function.ToIntFunction
              public final int applyAsInt(Object obj) {
                int lambda$getLargestDmaBufProcess$0;
                lambda$getLargestDmaBufProcess$0 =
                    DmaBufLeakDetector.lambda$getLargestDmaBufProcess$0(
                        (KernelAllocationStats.ProcessDmabuf) obj);
                return lambda$getLargestDmaBufProcess$0;
              }
            }));
    StringBuilder sb = new StringBuilder();
    sb.append(
        "\nheaviest process="
            + dmabufAllocations[0].processName
            + ", dmabuf_rss="
            + dmabufAllocations[0].retainedSizeKb
            + "KB");
    sb.append("\n\nName, UID, TotalRss(KB), Count, SFShareRss(KB), SFCount, ADJ");
    int length = dmabufAllocations.length;
    for (int i = 0; i < length; i++) {
      KernelAllocationStats.ProcessDmabuf processDmabuf = dmabufAllocations[i];
      sb.append(
          KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
              + processDmabuf.processName
              + ", "
              + processDmabuf.uid
              + ", "
              + processDmabuf.retainedSizeKb
              + ", "
              + processDmabuf.retainedBuffersCount
              + ", "
              + processDmabuf.surfaceFlingerSizeKb
              + ", "
              + processDmabuf.surfaceFlingerCount
              + ", "
              + processDmabuf.oomScore);
    }
    Slog.i("DmaBufLeakDetector", sb.toString());
    return dmabufAllocations[0].processName;
  }

  public static /* synthetic */ int lambda$getLargestDmaBufProcess$0(
      KernelAllocationStats.ProcessDmabuf processDmabuf) {
    return -processDmabuf.retainedSizeKb;
  }

  public void reportLeak(final Context context, final String str) {
    this.mIsReporting = true;
    Thread thread =
        new Thread(
            new Runnable() { // from class:
                             // com.android.server.am.pmm.DmaBufLeakDetector$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                DmaBufLeakDetector.this.lambda$reportLeak$1(context, str);
              }
            });
    thread.setName("DmaBufLeakDetector");
    thread.start();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$reportLeak$1(Context context, String str) {
    sendIssueTrackerIntent(context, str);
    this.mIsReporting = false;
  }

  public void sendIssueTrackerIntent(Context context, String str) {
    Intent intent = new Intent();
    intent.setAction("com.sec.android.ISSUE_TRACKER_ACTION");
    intent.putExtra("ERRCODE", -134);
    intent.putExtra("ERRPKG", str);
    intent.putExtra("ERRNAME", "DMABUF");
    intent.putExtra("ERRMSG", "DMABUF_leak");
    context.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
    this.mReportCount++;
    Slog.i("DmaBufLeakDetector", "sent intent to issuetracker. Report Count: " + this.mReportCount);
  }

  public boolean isCameraRunning() {
    return !"0".equals(SystemProperties.get("service.camera.running", "0"));
  }

  public boolean isListenerAppInstalled(Context context) {
    boolean z = false;
    int i = 0;
    while (true) {
      String[] strArr = ISSUE_TRACKER_INTENT_LISTENERS_LIST;
      if (i >= strArr.length) {
        break;
      }
      String str = strArr[i];
      try {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager.getApplicationInfo(str, z ? 1 : 0) != null) {
          if (packageManager.checkSignatures(str, "android") == 0) {
            Slog.d("DmaBufLeakDetector", "matched: " + i);
            z = true;
            z = true;
            break;
          }
          Slog.d("DmaBufLeakDetector", "signature not matched: " + i);
        }
      } catch (PackageManager.NameNotFoundException unused) {
        Slog.d("DmaBufLeakDetector", "not found app #" + i);
      }
      i++;
    }
    return z;
  }
}
