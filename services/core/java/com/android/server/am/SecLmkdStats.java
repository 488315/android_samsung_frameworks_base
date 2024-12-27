package com.android.server.am;

import android.util.IndentingPrintWriter;

import com.android.internal.util.FrameworkStatsLog;

import java.util.Arrays;

public abstract class SecLmkdStats {
    public static final int[] LMKD_SLOT_ADJ_VALUES;
    public static int sInvalidCountMemPressure;
    public static int sInvalidCountReason;
    public static int sInvalidCountSlot;
    public static final int[] sKillCountCustomReason;
    public static final int[] sKillCountInterval;
    public static final int[] sKillCountMemPressure;
    public static final int[] sKillCountReason;
    public static final int[] sKillCountSlot;

    static {
        int[] iArr = {
            0,
            50,
            100,
            200,
            225,
            FrameworkStatsLog
                    .CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_MACRO_RAW_SR_MERGE,
            300,
            350,
            400,
            500,
            600,
            700,
            800,
            FrameworkStatsLog.VPN_CONNECTION_STATE_CHANGED,
            FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT,
            999,
            1001
        };
        LMKD_SLOT_ADJ_VALUES = iArr;
        sKillCountSlot = new int[iArr.length];
        sKillCountInterval = new int[iArr.length];
        sKillCountMemPressure = new int[4];
        sKillCountReason = new int[10];
        sKillCountCustomReason = new int[4];
    }

    public static void dumpInvalidCount(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("[invalid_count]");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("sInvalidCountSlot=");
        indentingPrintWriter.println(sInvalidCountSlot);
        indentingPrintWriter.print("sInvalidCountMemPressure=");
        indentingPrintWriter.println(sInvalidCountMemPressure);
        indentingPrintWriter.print("sInvalidCountReason=");
        indentingPrintWriter.println(sInvalidCountReason);
        indentingPrintWriter.decreaseIndent();
    }

    public static void dumpKillCountByMemPressure(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("[kill_count_by_mem_pressure]");
        indentingPrintWriter.increaseIndent();
        int[] iArr = sKillCountMemPressure;
        int i = 0;
        while (i < 4) {
            indentingPrintWriter.print(i);
            indentingPrintWriter.print(" ");
            indentingPrintWriter.print(iArr[i]);
            indentingPrintWriter.print(" ");
            indentingPrintWriter.println(
                    i != 0
                            ? i != 1
                                    ? i != 2 ? i != 3 ? "undefined" : "super_critical" : "critical"
                                    : "medium"
                            : "low");
            i++;
        }
        indentingPrintWriter.decreaseIndent();
    }

    public static void dumpKillCountBySlot(IndentingPrintWriter indentingPrintWriter) {
        String str;
        indentingPrintWriter.println("[kill_count_by_adj]");
        indentingPrintWriter.increaseIndent();
        int[] iArr = sKillCountInterval;
        int[] iArr2 = sKillCountSlot;
        int i = 0;
        for (int i2 = 0; i2 < iArr2.length; i2++) {
            int[] iArr3 = LMKD_SLOT_ADJ_VALUES;
            if (i2 > 0) {
                int i3 = i2 - 1;
                int i4 = iArr3[i3] + 1;
                int i5 = iArr3[i2] - 1;
                if (i4 <= i5) {
                    i += iArr[i3];
                    indentingPrintWriter.print(i4);
                    indentingPrintWriter.print("~");
                    indentingPrintWriter.print(i5);
                    indentingPrintWriter.print(" ");
                    indentingPrintWriter.print(iArr[i3]);
                    indentingPrintWriter.print(" ");
                    indentingPrintWriter.println(i);
                }
            }
            i += iArr2[i2];
            indentingPrintWriter.print(iArr3[i2]);
            indentingPrintWriter.print("~");
            indentingPrintWriter.print(iArr3[i2]);
            indentingPrintWriter.print(" ");
            indentingPrintWriter.print(iArr2[i2]);
            indentingPrintWriter.print(" ");
            indentingPrintWriter.print(i);
            indentingPrintWriter.print(" ");
            switch (i2) {
                case -1:
                    str = "invalid";
                    break;
                case 0:
                    str = "foreground";
                    break;
                case 1:
                    str = "perceptible_recent_foreground";
                    break;
                case 2:
                    str = "visible";
                    break;
                case 3:
                    str = "perceptible";
                    break;
                case 4:
                    str = "perceptible_medium";
                    break;
                case 5:
                    str = "perceptible_low";
                    break;
                case 6:
                    str = "backup";
                    break;
                case 7:
                    str = "bora";
                    break;
                case 8:
                    str = "heavy_weight";
                    break;
                case 9:
                    str = "service";
                    break;
                case 10:
                    str = "home";
                    break;
                case 11:
                    str = "previous";
                    break;
                case 12:
                    str = "service_b";
                    break;
                case 13:
                    str = "picked";
                    break;
                case 14:
                    str = "cached";
                    break;
                case 15:
                    str = "cached_max";
                    break;
                case 16:
                    str = "unknown";
                    break;
                default:
                    str = "undefined";
                    break;
            }
            indentingPrintWriter.println(str);
        }
        indentingPrintWriter.decreaseIndent();
    }

    public static int getKillCountFromSlotRange(int i, int i2, boolean z, boolean z2) {
        if (i <= -1 || i2 > 16) {
            return 0;
        }
        int i3 = 0;
        for (int i4 = i; i4 < i2; i4++) {
            try {
                i3 += sKillCountInterval[i4];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int i5 = i2 + (z2 ? -1 : 0);
        for (int i6 = i + (z ? 1 : 0); i6 <= i5; i6++) {
            i3 += sKillCountSlot[i6];
        }
        return i3;
    }

    public static int getTotalCriticalKillCount() {
        int[] iArr = sKillCountMemPressure;
        return iArr[2] + iArr[3];
    }

    public static String killReasonToString(int i) {
        switch (i) {
            case -1:
                return "none";
            case 0:
                return "pressure_after_kill";
            case 1:
                return "not_responding";
            case 2:
                return "low_swap_and_thrashing";
            case 3:
                return "low_mem_and_swap";
            case 4:
                return "low_mem_and_thrashing";
            case 5:
                return "direct_recl_and_thrashing";
            case 6:
                return "low_mem_and_swap_util";
            case 7:
                return "low_filecache_after_thrashing";
            case 8:
                return "low_mem";
            case 9:
                return "direct_recl_stuck";
            default:
                switch (i) {
                    case 100:
                        return "custom_pressure_after_kill_drkill";
                    case 101:
                        return "custom_emergency_kill";
                    case 102:
                        return "custom_reserved";
                    case 103:
                        return "custom_camera_kill_mode";
                    default:
                        return "undefined";
                }
        }
    }

    public static void noteLmkKillOccurred(int i, int i2, int i3) {
        try {
            int[] iArr = LMKD_SLOT_ADJ_VALUES;
            int i4 = 16;
            if (i <= iArr[16] && (i4 = Arrays.binarySearch(iArr, i)) < 0) {
                i4 = (-(i4 + 1)) - 1;
            }
            boolean z = false;
            boolean z2 = i4 > -1;
            boolean z3 = i2 >= 0 && i2 < 4;
            boolean z4 = i3 >= 0 && i3 < 10;
            if (i3 >= 100 && i3 < 104) {
                z = true;
            }
            if (!z2) {
                sInvalidCountSlot++;
            } else if (i == iArr[i4]) {
                int[] iArr2 = sKillCountSlot;
                iArr2[i4] = iArr2[i4] + 1;
            } else {
                int[] iArr3 = sKillCountInterval;
                iArr3[i4] = iArr3[i4] + 1;
            }
            if (z3) {
                int[] iArr4 = sKillCountMemPressure;
                iArr4[i2] = iArr4[i2] + 1;
            } else {
                sInvalidCountMemPressure++;
            }
            if (z4) {
                int[] iArr5 = sKillCountReason;
                iArr5[i3] = iArr5[i3] + 1;
            } else {
                if (!z) {
                    sInvalidCountReason++;
                    return;
                }
                int[] iArr6 = sKillCountCustomReason;
                int i5 = i3 - 100;
                iArr6[i5] = iArr6[i5] + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
