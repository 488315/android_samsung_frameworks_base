package com.android.server.chimera;

import android.os.Debug;
import android.os.SystemProperties;
import android.p005os.IInstalld;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public abstract class ChimeraCommonUtil {
  public static final int[] ADJ_LEVELS = {
    999, 900, 860, 850, 800, 700, 600, 500, 400, 300, FrameworkStatsLog.f446x58c6c32, 200, 100, 0
  };
  public static Map mAppLaunchInfoMap = new ConcurrentHashMap();

  public enum TriggerSource {
    TRIGGER_SOURCE_LMKD("LMKD"),
    TRIGGER_SOURCE_BOTTLENECK_HINT("BottleneckHint"),
    TRIGGER_SOURCE_HOME_IDLE("HomeIdle"),
    TRIGGER_SOURCE_PMM_CRITICAL("PMMCritical"),
    TRIGGER_SOURCE_DEVICE_IDLE("DeviceIdle"),
    TRIGGER_SOURCE_APP_LAUNCH_INTENT("AppLaunchIntent"),
    TRIGGER_SOURCE_QUOTA("Quota");

    String name;

    TriggerSource(String str) {
      this.name = str;
    }
  }

  public static void putAppLaunchInfo(String str, String str2) {
    mAppLaunchInfoMap.put(str, str2);
  }

  public static void clearAppLaunchInfoMap() {
    mAppLaunchInfoMap.clear();
  }

  public static boolean isColdLaunch(String str) {
    String str2 = (String) mAppLaunchInfoMap.get(str);
    if (str2 != null) {
      return str2.startsWith("COLD");
    }
    return false;
  }

  public static boolean isWarmLaunch(String str) {
    String str2 = (String) mAppLaunchInfoMap.get(str);
    if (str2 != null) {
      return str2.startsWith("WARM");
    }
    return false;
  }

  public static boolean doReclaimPageCacheByFilePath(String str) {
    try {
      BufferedWriter bufferedWriter =
          new BufferedWriter(
              new FileWriter("/proc/proc_caches_reclaim"),
              IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES);
      try {
        bufferedWriter.write(str + "\u0000");
        bufferedWriter.close();
        return true;
      } finally {
      }
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static boolean isBubEnabled(SystemRepository systemRepository) {
    return systemRepository.getSystemProperty("persist.sys.bub_onoff", "on").equals("on");
  }

  public static long getAvailableMemoryKb(SystemRepository systemRepository) {
    return systemRepository.getAvailableMemory() / 1024;
  }

  public static long getMemInfoByName(String str) {
    try {
      FileReader fileReader = new FileReader("/proc/meminfo");
      try {
        BufferedReader bufferedReader =
            new BufferedReader(fileReader, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES);
        try {
          String readLine = bufferedReader.readLine();
          if (readLine == null) {
            bufferedReader.close();
            fileReader.close();
            return 0L;
          }
          int indexOf = readLine.indexOf(str + XmlUtils.STRING_ARRAY_SEPARATOR);
          while (indexOf < 0) {
            readLine = bufferedReader.readLine();
            indexOf = readLine.indexOf(str + XmlUtils.STRING_ARRAY_SEPARATOR);
          }
          long parseInt = Integer.parseInt(readLine.substring(indexOf).replaceAll("\\D+", ""));
          bufferedReader.close();
          fileReader.close();
          return parseInt;
        } finally {
        }
      } catch (Throwable th) {
        try {
          fileReader.close();
        } catch (Throwable th2) {
          th.addSuppressed(th2);
        }
        throw th;
      }
    } catch (IOException e) {
      e.printStackTrace();
      return 0L;
    } catch (NullPointerException e2) {
      e2.printStackTrace();
      return 0L;
    } catch (Exception e3) {
      e3.printStackTrace();
      return 0L;
    }
  }

  public static long getTotalMemFree() {
    try {
      FileReader fileReader = new FileReader("/proc/meminfo");
      try {
        BufferedReader bufferedReader =
            new BufferedReader(fileReader, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES);
        try {
          bufferedReader.readLine();
          String readLine = bufferedReader.readLine();
          if (readLine == null) {
            bufferedReader.close();
            fileReader.close();
            return 0L;
          }
          long parseInt =
              Integer.parseInt(
                  readLine.substring(readLine.indexOf("MemFree:")).replaceAll("\\D+", ""));
          bufferedReader.close();
          fileReader.close();
          return parseInt;
        } finally {
        }
      } finally {
      }
    } catch (IOException e) {
      e.printStackTrace();
      return 0L;
    }
  }

  public static long getTotalMemorySizeKb() {
    try {
      FileReader fileReader = new FileReader("/proc/meminfo");
      try {
        BufferedReader bufferedReader =
            new BufferedReader(fileReader, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES);
        try {
          String readLine = bufferedReader.readLine();
          if (readLine == null) {
            bufferedReader.close();
            fileReader.close();
            return 0L;
          }
          long parseInt =
              Integer.parseInt(
                  readLine.substring(readLine.indexOf("MemTotal:")).replaceAll("\\D+", ""));
          bufferedReader.close();
          fileReader.close();
          return parseInt;
        } finally {
        }
      } finally {
      }
    } catch (IOException e) {
      e.printStackTrace();
      return 0L;
    }
  }

  public static int getRamSizeGb() {
    return (int) Math.ceil(getTotalMemorySizeKb() / 1048576.0f);
  }

  public static long getProcPss(SystemRepository systemRepository, int i) {
    return systemRepository.getPss(i, null);
  }

  public static PsiFile getPsiFile(PsiFileType psiFileType, PsiDataType psiDataType) {
    StringBuilder sb;
    StringBuilder sb2 = new StringBuilder();
    BufferedReader bufferedReader = null;
    try {
      try {
        BufferedReader bufferedReader2 = new BufferedReader(new FileReader(psiFileType.getPath()));
        while (true) {
          try {
            String readLine = bufferedReader2.readLine();
            if (readLine != null) {
              sb2.append(readLine);
              sb2.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            } else {
              try {
                break;
              } catch (Exception e) {
                e = e;
                sb = new StringBuilder();
                sb.append("Exception");
                sb.append(e.getMessage());
                Slog.e("ActivityManager", sb.toString());
                return new PsiFile(sb2.toString(), psiDataType);
              }
            }
          } catch (Exception e2) {
            e = e2;
            bufferedReader = bufferedReader2;
            Slog.e("ActivityManager", "getPsiFile Exception" + e.getMessage());
            if (bufferedReader != null) {
              try {
                bufferedReader.close();
              } catch (Exception e3) {
                e = e3;
                sb = new StringBuilder();
                sb.append("Exception");
                sb.append(e.getMessage());
                Slog.e("ActivityManager", sb.toString());
                return new PsiFile(sb2.toString(), psiDataType);
              }
            }
            return new PsiFile(sb2.toString(), psiDataType);
          } catch (Throwable th) {
            th = th;
            bufferedReader = bufferedReader2;
            if (bufferedReader != null) {
              try {
                bufferedReader.close();
              } catch (Exception e4) {
                Slog.e("ActivityManager", "Exception" + e4.getMessage());
              }
            }
            throw th;
          }
        }
        bufferedReader2.close();
      } catch (Exception e5) {
        e = e5;
      }
      return new PsiFile(sb2.toString(), psiDataType);
    } catch (Throwable th2) {
      th = th2;
    }
  }

  public enum PsiDataType {
    AVG10("avg10"),
    TOTAL("total");

    private String mPath;

    PsiDataType(String str) {
      this.mPath = str;
    }
  }

  public enum PsiFileType {
    IO("/proc/pressure/io"),
    MEMORY("/proc/pressure/memory"),
    CPU("/proc/pressure/cpu");

    private String mPath;

    PsiFileType(String str) {
      this.mPath = str;
    }

    public String getPath() {
      return this.mPath;
    }
  }

  public class PsiFile {
    public double mFullAvg10;
    public boolean mIsEmptyFile;
    public long mPsiFullTotal;
    public long mPsiSomeTotal;
    public double mSomeAvg10;

    public PsiFile(String str, final PsiDataType psiDataType) {
      if (str == null || str.length() == 0) {
        this.mIsEmptyFile = true;
      } else {
        Arrays.stream(str.split(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE))
            .forEach(
                new Consumer() { // from class:
                                 // com.android.server.chimera.ChimeraCommonUtil$PsiFile$$ExternalSyntheticLambda0
                  @Override // java.util.function.Consumer
                  public final void accept(Object obj) {
                    ChimeraCommonUtil.PsiFile.this.lambda$new$0(psiDataType, (String) obj);
                  }
                });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(PsiDataType psiDataType, String str) {
      if (str.startsWith("some")) {
        if (psiDataType == PsiDataType.AVG10) {
          this.mSomeAvg10 = Double.parseDouble(getItem(str, "avg10", 1));
          return;
        } else {
          if (psiDataType == PsiDataType.TOTAL) {
            this.mPsiSomeTotal = Long.parseLong(getItem(str, "total", 4));
            return;
          }
          return;
        }
      }
      if (str.startsWith("full")) {
        if (psiDataType == PsiDataType.AVG10) {
          this.mFullAvg10 = Double.parseDouble(getItem(str, "avg10", 1));
        } else if (psiDataType == PsiDataType.TOTAL) {
          this.mPsiFullTotal = Long.parseLong(getItem(str, "total", 4));
        }
      }
    }

    public final String getItem(String str, String str2, int i) {
      String[] split = str.split(" ");
      String str3 = str2 + "=";
      return (split.length <= i || !split[i].startsWith(str3)) ? "0" : split[i].replace(str3, "");
    }

    public long getPsiSomeTotal() {
      return this.mPsiSomeTotal;
    }

    public long getPsiFullTotal() {
      return this.mPsiFullTotal;
    }

    public boolean isEmpty() {
      return this.mIsEmptyFile;
    }
  }

  public static Set getAllRunningPackagePids(int i) {
    String[] list;
    String str = "/acct/uid_" + i;
    File file = new File(str);
    HashSet hashSet = new HashSet();
    if (file.isDirectory() && (list = file.list()) != null) {
      for (int i2 = 0; i2 < list.length; i2++) {
        if (list[i2].contains("pid")) {
          readAcctFile(str + "/" + list[i2] + "/cgroup.procs", hashSet);
        }
      }
    }
    return hashSet;
  }

  public static void readAcctFile(String str, Set set) {
    BufferedReader bufferedReader;
    BufferedReader bufferedReader2 = null;
    bufferedReader2 = null;
    bufferedReader2 = null;
    try {
      try {
        try {
          bufferedReader = new BufferedReader(new FileReader(str));
        } catch (IOException e) {
          e.printStackTrace();
        }
      } catch (IOException e2) {
        e = e2;
      }
    } catch (Throwable th) {
      th = th;
    }
    try {
      String readLine = bufferedReader.readLine();
      while (readLine != null) {
        set.add(Integer.valueOf(readLine));
        readLine = bufferedReader.readLine();
      }
      bufferedReader.close();
      bufferedReader2 = readLine;
    } catch (IOException e3) {
      e = e3;
      bufferedReader2 = bufferedReader;
      Log.e("ChimeraCommonUtil", "can't read " + str + e.getMessage());
      if (bufferedReader2 != null) {
        bufferedReader2.close();
        bufferedReader2 = bufferedReader2;
      }
    } catch (Throwable th2) {
      th = th2;
      bufferedReader2 = bufferedReader;
      if (bufferedReader2 != null) {
        try {
          bufferedReader2.close();
        } catch (IOException e4) {
          e4.printStackTrace();
        }
      }
      throw th;
    }
  }

  public static long[] getAnonSizeAndZramSize(int i) {
    String[] strArr = {"Pss_Anon:", "Swap:", "SwapShared:", "Writeback:"};
    long[] jArr = new long[4];
    try {
      BufferedReader bufferedReader =
          new BufferedReader(
              new FileReader("/proc/" + i + "/smaps_rollup", StandardCharsets.UTF_8));
      int i2 = 0;
      while (true) {
        try {
          String readLine = bufferedReader.readLine();
          if (readLine == null) {
            break;
          }
          if (readLine.startsWith(strArr[i2])) {
            String[] split = readLine.split("\\s+");
            if (split.length == 3) {
              jArr[i2] = Long.parseLong(split[1]);
            }
            i2++;
            if (i2 == 4) {
              break;
            }
          }
        } finally {
        }
      }
      bufferedReader.close();
      return new long[] {
        Math.max(jArr[0], 0L), Math.max((long) (((jArr[1] - jArr[2]) - jArr[3]) * 0.33d), 0L)
      };
    } catch (IOException unused) {
      return new long[] {0, 0};
    }
  }

  public static long getDRAMUsed(int i, long j) {
    long j2;
    boolean z;
    if (j <= 0) {
      j2 = Debug.getPss(i, null, null);
      z = true;
    } else {
      j2 = j;
      z = false;
    }
    String[] strArr = {"SwapPss:", "Writeback:"};
    long[] jArr = new long[2];
    try {
      BufferedReader bufferedReader =
          new BufferedReader(
              new FileReader("/proc/" + i + "/smaps_rollup", StandardCharsets.UTF_8));
      int i2 = 0;
      while (true) {
        try {
          String readLine = bufferedReader.readLine();
          if (readLine == null) {
            break;
          }
          if (readLine.startsWith(strArr[i2])) {
            String[] split = readLine.split("\\s+");
            if (split.length == 3) {
              jArr[i2] = Long.parseLong(split[1]);
            }
            i2++;
            if (i2 == 2) {
              break;
            }
          }
        } finally {
        }
      }
      bufferedReader.close();
      if (!z && j2 - jArr[0] < 0) {
        j2 = Debug.getPss(i, null, null);
      }
      long j3 = jArr[0];
      return Math.max((long) ((j2 - j3) + ((j3 - jArr[1]) * 0.33d)), 0L);
    } catch (IOException unused) {
      return j2;
    }
  }

  public static boolean isQuotaEnable() {
    return SystemProperties.getBoolean("ro.slmk.chimera_quota_enable", false);
  }
}
