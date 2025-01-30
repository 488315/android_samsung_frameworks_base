package com.android.server.bgslotmanager;

import android.os.Bundle;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.am.BGProtectManager;
import com.android.server.am.DynamicHiddenApp;
import com.android.server.am.ProcessList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class BgAppPropManager {
    public DynamicHiddenApp mDynamicHiddenApp;
    public ProcessList mProcessList;
    public static final long mTotalMemMb = MemInfoGetter.getTotalMemoryMB();
    public static int TOTAL_MEMORY_2ND = Integer.parseInt(SystemProperties.get("ro.slmk.dha_2ndprop_thMB", "4096"));
    public static int TOTAL_MEMORY_3RD = Integer.parseInt(SystemProperties.get("ro.slmk.3rd.over_thMB", "9999999"));

    public BgAppPropManager(ProcessList processList, DynamicHiddenApp dynamicHiddenApp) {
        this.mProcessList = processList;
        this.mDynamicHiddenApp = dynamicHiddenApp;
    }

    public static void setSystemPropertyString(String str, String str2) {
        SystemProperties.set(str, str2);
    }

    public static String getSystemPropertyString(String str, String str2) {
        return SystemProperties.get(str, str2);
    }

    public static int getSemSystemPropertyInt(String str, String str2) {
        return SemSystemProperties.getInt(str, Integer.parseInt(str2));
    }

    public static String getSlmkPropertyString(String str, String str2) {
        String str3 = SystemProperties.get("ro.slmk." + str, str2);
        long j = mTotalMemMb;
        if (j > TOTAL_MEMORY_2ND) {
            str3 = SystemProperties.get("ro.slmk.2nd." + str, str3);
        }
        if (j <= TOTAL_MEMORY_3RD) {
            return str3;
        }
        return SystemProperties.get("ro.slmk.3rd." + str, str3);
    }

    public static boolean getSlmkPropertyBool(String str, String str2) {
        return Boolean.parseBoolean(getSlmkPropertyString(str, str2));
    }

    public static int getSlmkPropertyInt(String str, String str2) {
        return Integer.parseInt(getSlmkPropertyString(str, str2));
    }

    public static float getSlmkPropertyFloat(String str, String str2) {
        return Float.parseFloat(getSlmkPropertyString(str, str2));
    }

    public void dumpLMKDParameter(PrintWriter printWriter) {
        printWriter.println("  DHA_CACHE_MIN: " + BGSlotManager.MIN_CACHED_APPS);
        printWriter.println("  DHA_CACHE_MAX: " + BGSlotManager.MAX_CACHED_APPS);
        printWriter.println("  DHA_EMPTY_MIN: " + BGSlotManager.MIN_EMPTY_APPS);
        printWriter.println("  DHA_EMPTY_MAX: " + BGSlotManager.MAX_EMPTY_APPS);
        this.mDynamicHiddenApp.printAmcCachedEmpty(printWriter);
        printWriter.println();
        printWriter.println("  LMKD_enable_userspace_lmk " + DynamicHiddenApp.LMK_ENABLE_USERSPACE_LMK);
        printWriter.println("  LMKD_use_minfree_levels " + DynamicHiddenApp.LMK_USE_MINFREE_LEVELS);
        printWriter.println("  LMKD_enable_upgrade_criadj " + DynamicHiddenApp.LMK_ENABLE_UPGRADE_CRIADJ);
        printWriter.println("  LMKD_freelimit_enable " + DynamicHiddenApp.LMK_FREELIMIT_ENABLE);
        printWriter.println("  LMKD_freelimit_val " + DynamicHiddenApp.LMK_FREELIMIT_VAL);
        printWriter.println("  LMKD_upgrade_pressure " + DynamicHiddenApp.LMK_UPGRADE_PRESSURE);
        printWriter.println("  LMKD_custom_sw_limit " + DynamicHiddenApp.LMK_CUSTOM_SW_LIMIT);
        printWriter.println("  LMKD_custom_tm_limit " + DynamicHiddenApp.LMK_CUSTOM_TM_LIMIT);
        printWriter.println("  LMKD_psi_medium_th " + DynamicHiddenApp.LMK_PSI_MEDIUM_TH);
        printWriter.println("  LMKD_psi_critical_th " + DynamicHiddenApp.LMK_PSI_CRITICAL_TH);
        printWriter.println("  LMKD_use_lowmem_keep_except " + DynamicHiddenApp.LMK_LOW_MEM_KEEP_ENABLE);
        this.mDynamicHiddenApp.printLowMemDectectorEnable(printWriter);
        this.mDynamicHiddenApp.printAppCompactorEnable(printWriter);
        printWriter.println();
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:51:0x0082 -> B:18:0x008e). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:53:0x007d -> B:18:0x008e). Please report as a decompilation issue!!! */
    public void updateParamsFile() {
        String readLine;
        BufferedReader bufferedReader = null;
        BufferedReader bufferedReader2 = null;
        BufferedReader bufferedReader3 = null;
        BufferedReader bufferedReader4 = null;
        bufferedReader = null;
        bufferedReader = null;
        try {
            try {
                try {
                    Slog.i("DynamicHiddenApp_BgAppPropManager", "Start updateParamsFile");
                    FileReader fileReader = new FileReader("/data/log/dha_parameter.dat");
                    Slog.i("DynamicHiddenApp_BgAppPropManager", "updateParamsFile dha_parameter.dat exist");
                    BufferedReader bufferedReader5 = new BufferedReader(fileReader);
                    while (true) {
                        try {
                            readLine = bufferedReader5.readLine();
                            if (readLine == null) {
                                break;
                            }
                            if (!readLine.isEmpty() && !readLine.trim().equals("") && !readLine.trim().equals(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE)) {
                                updateTuningParameter(readLine.split("[=:]")[0].trim(), readLine.split("[=:]")[1].trim());
                            }
                        } catch (FileNotFoundException unused) {
                            bufferedReader2 = bufferedReader5;
                            Slog.w("DynamicHiddenApp_BgAppPropManager", "file does not exist");
                            bufferedReader2.close();
                            bufferedReader = bufferedReader2;
                        } catch (IOException e) {
                            e = e;
                            bufferedReader3 = bufferedReader5;
                            e.printStackTrace();
                            bufferedReader3.close();
                            bufferedReader = bufferedReader3;
                        } catch (ArrayIndexOutOfBoundsException e2) {
                            e = e2;
                            bufferedReader4 = bufferedReader5;
                            e.printStackTrace();
                            bufferedReader4.close();
                            bufferedReader = bufferedReader4;
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader5;
                            try {
                                bufferedReader.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            } catch (NullPointerException e4) {
                                e4.printStackTrace();
                            }
                            throw th;
                        }
                    }
                    bufferedReader5.close();
                    bufferedReader = readLine;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (FileNotFoundException unused2) {
            } catch (IOException e5) {
                e = e5;
            } catch (ArrayIndexOutOfBoundsException e6) {
                e = e6;
            }
        } catch (IOException e7) {
            e7.printStackTrace();
            bufferedReader = bufferedReader;
        } catch (NullPointerException e8) {
            e8.printStackTrace();
            bufferedReader = bufferedReader;
        }
    }

    public void updateParamsIntent(Bundle bundle) {
        Slog.i("DynamicHiddenApp_BgAppPropManager", "Start updateParamsIntent");
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj != null) {
                updateTuningParameter(str, obj.toString());
            } else {
                Slog.w("DynamicHiddenApp_BgAppPropManager", str + " - value is null");
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0413  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x040e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateTuningParameter(String str, String str2) {
        int i;
        BGSlotManager bGSlotManagerInstance = this.mDynamicHiddenApp.getBGSlotManagerInstance();
        BGProtectManager bGProtectManagerInstance = this.mDynamicHiddenApp.getBGProtectManagerInstance();
        Slog.i("DynamicHiddenApp_BgAppPropManager", "name = [ " + str + " ], value = [ " + str2 + " ]");
        try {
            boolean z = false;
            if ("ro.slmk.dha_cached_max".equals(str)) {
                bGSlotManagerInstance.setOriginCachedMax(Integer.parseInt(str2));
            } else if ("ro.slmk.dha_cached_min".equals(str)) {
                bGSlotManagerInstance.setOriginCachedMin(Integer.parseInt(str2));
            } else if ("ro.slmk.dha_empty_max".equals(str)) {
                bGSlotManagerInstance.setOriginEmptyMax(Integer.parseInt(str2));
            } else if ("ro.slmk.dha_empty_min".equals(str)) {
                bGSlotManagerInstance.setOriginEmptyMin(Integer.parseInt(str2));
            } else {
                i = 1;
                if ("ro.slmk.dha_lmk_scale".equals(str)) {
                    DynamicHiddenApp.mLMKScale = Float.parseFloat(str2);
                } else if ("ro.slmk.dha_lmk_array".equals(str)) {
                    DynamicHiddenApp.mLMKArray = str2;
                    Slog.i("DynamicHiddenApp_BgAppPropManager", "dha_lmk_array mLMKArray =" + DynamicHiddenApp.mLMKArray);
                } else {
                    if ("ro.slmk.dha_pwhl_key".equals(str)) {
                        BGProtectManager.dha_keepempty_key = Integer.parseInt(str2);
                    } else if ("ro.slmk.dha_pwhl_key_knox".equals(str)) {
                        BGProtectManager.dha_keepempty_key_knox = Integer.parseInt(str2);
                    } else if ("ro.slmk.ams_exception_enable".equals(str)) {
                        BGProtectManager.mAMSExceptionEnable = Boolean.parseBoolean(str2);
                    } else if ("ro.slmk.fha_cached_max".equals(str)) {
                        this.mDynamicHiddenApp.updateMaxCachedProcessesNumFHA(Integer.parseInt(str2));
                    } else if ("ro.slmk.fha_empty_rate".equals(str)) {
                        this.mDynamicHiddenApp.updateEmptyRate(Float.parseFloat(str2));
                    } else if ("ro.slmk.cam_dha_ver".equals(str)) {
                        CameraKillModeManager.CAMERA_DHA_VER = Integer.parseInt(str2);
                    } else if ("ro.slmk.enable_picked_adj".equals(str)) {
                        DynamicHiddenApp.PICKED_ADJ_ENABLE = Boolean.parseBoolean(str2);
                    } else if ("ro.slmk.dha_2ndprop_thMB".equals(str)) {
                        TOTAL_MEMORY_2ND = Integer.parseInt(str2);
                    } else if ("ro.slmk.low".equals(str)) {
                        ProcessList.setLmkdParameter(DynamicHiddenApp.LmkdParameter.LMK_LOW_ADJ.ordinal(), Integer.parseInt(str2));
                        DynamicHiddenApp.LMK_LOW_ADJ = Integer.parseInt(str2);
                    } else if ("ro.slmk.medium".equals(str)) {
                        ProcessList.setLmkdParameter(DynamicHiddenApp.LmkdParameter.LMK_MEDIUM_ADJ.ordinal(), Integer.parseInt(str2));
                        DynamicHiddenApp.LMK_MEDIUM_ADJ = Integer.parseInt(str2);
                    } else if ("ro.slmk.critical".equals(str)) {
                        ProcessList.setLmkdParameter(DynamicHiddenApp.LmkdParameter.LMK_CRITICAL_ADJ.ordinal(), Integer.parseInt(str2));
                        DynamicHiddenApp.LMK_CRITICAL_ADJ = Integer.parseInt(str2);
                    } else if ("ro.slmk.debug".equals(str)) {
                        int ordinal = DynamicHiddenApp.LmkdParameter.LMK_DEBUG.ordinal();
                        if (!Boolean.parseBoolean(str2)) {
                            i = 0;
                        }
                        ProcessList.setLmkdParameter(ordinal, i);
                        DynamicHiddenApp.LMK_DEBUG = Boolean.parseBoolean(str2);
                    } else if ("ro.slmk.critical_upgrade".equals(str)) {
                        int ordinal2 = DynamicHiddenApp.LmkdParameter.LMK_CRITICAL_UPGRADE.ordinal();
                        if (!Boolean.parseBoolean(str2)) {
                            i = 0;
                        }
                        ProcessList.setLmkdParameter(ordinal2, i);
                        DynamicHiddenApp.LMK_CRITICAL_UPGRADE = Boolean.parseBoolean(str2);
                    } else if ("ro.slmk.upgrade_pressure".equals(str)) {
                        ProcessList.setLmkdParameter(DynamicHiddenApp.LmkdParameter.LMK_UPGRADE_PRESSURE.ordinal(), Integer.parseInt(str2));
                        DynamicHiddenApp.LMK_UPGRADE_PRESSURE = Integer.parseInt(str2);
                    } else if ("ro.slmk.downgrade_pressure".equals(str)) {
                        ProcessList.setLmkdParameter(DynamicHiddenApp.LmkdParameter.LMK_DOWNGRADE_PRESSURE.ordinal(), Integer.parseInt(str2));
                        DynamicHiddenApp.LMK_DOWNGRADE_PRESSURE = Integer.parseInt(str2);
                        DynamicHiddenApp.LMK_CUSTOM_SW_LIMIT = Integer.parseInt(str2) * 5;
                    } else if ("ro.slmk.kill_heaviest_task".equals(str)) {
                        int ordinal3 = DynamicHiddenApp.LmkdParameter.LMK_KILL_HEAVIEST_TASK.ordinal();
                        if (!Boolean.parseBoolean(str2)) {
                            i = 0;
                        }
                        ProcessList.setLmkdParameter(ordinal3, i);
                        DynamicHiddenApp.LMK_KILL_HEAVIEST_TASK = Boolean.parseBoolean(str2);
                    } else if ("ro.slmk.kill_timeout_ms".equals(str)) {
                        ProcessList.setLmkdParameter(DynamicHiddenApp.LmkdParameter.LMK_KILL_TIMEOUT_MS.ordinal(), Integer.parseInt(str2));
                        DynamicHiddenApp.LMK_KILL_TIMEOUT_MS = Integer.parseInt(str2);
                        DynamicHiddenApp.LMK_CUSTOM_TM_LIMIT = Integer.parseInt(str2) * 10;
                    } else if ("ro.slmk.use_minfree_levels".equals(str)) {
                        int ordinal4 = DynamicHiddenApp.LmkdParameter.LMK_USE_MINFREE_LEVELS.ordinal();
                        if (!Boolean.parseBoolean(str2)) {
                            i = 0;
                        }
                        ProcessList.setLmkdParameter(ordinal4, i);
                        DynamicHiddenApp.LMK_USE_MINFREE_LEVELS = Boolean.parseBoolean(str2);
                    } else if ("ro.slmk.enable_cmarbinfree_sub".equals(str)) {
                        int ordinal5 = DynamicHiddenApp.LmkdParameter.LMK_ENABLE_CMARBINFREE_SUB.ordinal();
                        if (!Boolean.parseBoolean(str2)) {
                            i = 0;
                        }
                        ProcessList.setLmkdParameter(ordinal5, i);
                        DynamicHiddenApp.LMK_ENABLE_CMARBINFREE_SUB = Boolean.parseBoolean(str2);
                    } else if ("ro.slmk.enable_upgrade_criadj".equals(str)) {
                        int ordinal6 = DynamicHiddenApp.LmkdParameter.LMK_ENABLE_UPGRADE_CRIADJ.ordinal();
                        if (!Boolean.parseBoolean(str2)) {
                            i = 0;
                        }
                        ProcessList.setLmkdParameter(ordinal6, i);
                        DynamicHiddenApp.LMK_ENABLE_UPGRADE_CRIADJ = Boolean.parseBoolean(str2);
                    } else if ("ro.slmk.freelimit_enable".equals(str)) {
                        int ordinal7 = DynamicHiddenApp.LmkdParameter.LMK_FREELIMIT_ENABLE.ordinal();
                        if (!Boolean.parseBoolean(str2)) {
                            i = 0;
                        }
                        ProcessList.setLmkdParameter(ordinal7, i);
                        DynamicHiddenApp.LMK_FREELIMIT_ENABLE = Boolean.parseBoolean(str2);
                    } else if ("ro.slmk.freelimit_val".equals(str)) {
                        ProcessList.setLmkdParameter(DynamicHiddenApp.LmkdParameter.LMK_FREELIMIT_VAL.ordinal(), Integer.parseInt(str2));
                        DynamicHiddenApp.LMK_FREELIMIT_VAL = Integer.parseInt(str2);
                    } else if ("ro.slmk.custom_sw_limit".equals(str)) {
                        ProcessList.setLmkdParameter(DynamicHiddenApp.LmkdParameter.LMK_DOWNGRADE_PRESSURE.ordinal(), Integer.parseInt(str2) / 5);
                        DynamicHiddenApp.LMK_DOWNGRADE_PRESSURE = Integer.parseInt(str2) / 5;
                        DynamicHiddenApp.LMK_CUSTOM_SW_LIMIT = Integer.parseInt(str2);
                    } else if ("ro.slmk.custom_tm_limit".equals(str)) {
                        ProcessList.setLmkdParameter(DynamicHiddenApp.LmkdParameter.LMK_KILL_TIMEOUT_MS.ordinal(), Integer.parseInt(str2) / 10);
                        DynamicHiddenApp.LMK_KILL_TIMEOUT_MS = Integer.parseInt(str2) / 10;
                        DynamicHiddenApp.LMK_CUSTOM_TM_LIMIT = Integer.parseInt(str2);
                    } else if ("ro.slmk.psi_low".equals(str)) {
                        ProcessList.setLmkdParameter(DynamicHiddenApp.LmkdParameter.LMK_PSI_LOW_TH.ordinal(), Integer.parseInt(str2));
                        DynamicHiddenApp.LMK_PSI_LOW_TH = Integer.parseInt(str2);
                    } else if ("ro.slmk.psi_medium".equals(str)) {
                        ProcessList.setLmkdParameter(DynamicHiddenApp.LmkdParameter.LMK_PSI_MEDIUM_TH.ordinal(), Integer.parseInt(str2));
                        DynamicHiddenApp.LMK_PSI_MEDIUM_TH = Integer.parseInt(str2);
                    } else if ("ro.slmk.psi_critical".equals(str)) {
                        ProcessList.setLmkdParameter(DynamicHiddenApp.LmkdParameter.LMK_PSI_CRITICAL_TH.ordinal(), Integer.parseInt(str2));
                        DynamicHiddenApp.LMK_PSI_CRITICAL_TH = Integer.parseInt(str2);
                    } else if ("ro.slmk.swappiness".equals(str)) {
                        SystemProperties.set("sys.sysctl.swappiness", str2);
                    } else if ("ro.slmk.mmap_readaround_limit".equals(str)) {
                        SystemProperties.set("sys.sysctl.mmap_readaround_limit", str2);
                    } else if ("ro.slmk.fault_around_bytes".equals(str)) {
                        SystemProperties.set("sys.sysctl.fault_around_bytes", str2);
                    } else if ("ro.slmk.use_bg_keeping_policy".equals(str)) {
                        ProcessList.setLmkdParameter(DynamicHiddenApp.LmkdParameter.LMK_SET_BG_KEEPING.ordinal(), Integer.parseInt(str2));
                    } else if (this.mDynamicHiddenApp.setKpmParams(str, str2)) {
                        Slog.w("DynamicHiddenApp_BgAppPropManager", "setKpmParams");
                    } else {
                        Slog.w("DynamicHiddenApp_BgAppPropManager", str + " - cannot matched parameter");
                        return;
                    }
                    i = 0;
                    z = true;
                }
                if (z) {
                    bGProtectManagerInstance.initBGProtectManagerPostBoot();
                }
                if (i != 0) {
                    this.mProcessList.updateLMKThreshold();
                }
                bGSlotManagerInstance.updateDefaultCachedMAX();
            }
            i = 0;
            if (z) {
            }
            if (i != 0) {
            }
            bGSlotManagerInstance.updateDefaultCachedMAX();
        } catch (NumberFormatException e) {
            Slog.e("DynamicHiddenApp_BgAppPropManager", "Error occurred not correct data format [" + str + "]  [" + str2 + "]");
            e.printStackTrace();
        }
    }
}
