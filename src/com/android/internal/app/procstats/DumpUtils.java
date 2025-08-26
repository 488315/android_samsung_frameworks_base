package com.android.internal.app.procstats;

import android.app.backup.FullBackup;
import android.app.blob.XmlTags;
import android.content.Context;
import android.os.UserHandle;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.internal.content.NativeLibraryHelper;
import com.samsung.android.core.CoreSaConstant;
import com.samsung.android.wallpaperbackup.GenerateXML;
import java.io.PrintWriter;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public final class DumpUtils {
    static final String CSV_SEP = "\t";
    public static final String STATE_LABEL_TOTAL = "     TOTAL";
    public static final String[] STATE_NAMES = {"Persist", "Top", "BTop", "Fgs", "BFgs", "ImpFg", "ImpBg", "Backup", "Service", "ServRst", "Receivr", "HeavyWt", CoreSaConstant.VALUE_HOME, "LastAct", "Cached", "Frozen"};
    public static final String STATE_LABEL_CACHED = "  (Cached)";
    public static final String[] STATE_LABELS = {"Persistent", "       Top", "   Bnd Top", "       Fgs", "   Bnd Fgs", "    Imp Fg", "    Imp Bg", "    Backup", "   Service", "Service Rs", "  Receiver", " Heavy Wgt", "    (Home)", "(Last Act)", STATE_LABEL_CACHED, "    Frozen"};
    public static final String[] STATE_NAMES_CSV = {"pers", GenerateXML.TOP, "btop", "fgs", "bfgs", "impfg", "impbg", Context.BACKUP_SERVICE, "service", "service-rs", "receiver", "heavy", "home", "lastact", "cached", "frzn"};
    static final String[] STATE_TAGS = {"p", "t", "z", "g", "y", FullBackup.FILES_TREE_TOKEN, XmlTags.TAG_BLOB, XmlTags.ATTR_UID, XmlTags.TAG_SESSION, "x", "r", "w", "h", XmlTags.TAG_LEASEE, FullBackup.APK_TREE_TOKEN, "e"};
    static final int[] STATE_PROTO_ENUMS = {1, 2, 19, 16, 20, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 17};
    private static final int[] PROCESS_STATS_STATE_TO_AGGREGATED_STATE = {1, 2, 3, 4, 3, 5, 6, 6, 6, 0, 7, 6, 8, 8, 8, 8};
    public static final String[] ADJ_SCREEN_NAMES_CSV = {"off", "on"};
    public static final String[] ADJ_MEM_NAMES_CSV = {"norm", "mod", "low", "crit"};
    static final String[] ADJ_SCREEN_TAGS = {"0", "1"};
    static final int[] ADJ_SCREEN_PROTO_ENUMS = {1, 2};
    static final String[] ADJ_MEM_TAGS = {"n", "m", XmlTags.TAG_LEASEE, "c"};
    static final int[] ADJ_MEM_PROTO_ENUMS = {1, 2, 3, 4};

    private DumpUtils() {
    }

    public static void printScreenLabel(PrintWriter printWriter, int i) {
        if (i == -1) {
            printWriter.print("     ");
            return;
        }
        if (i == 0) {
            printWriter.print("SOff/");
        } else if (i == 4) {
            printWriter.print(" SOn/");
        } else {
            printWriter.print("????/");
        }
    }

    public static void printScreenLabelCsv(PrintWriter printWriter, int i) {
        if (i != -1) {
            if (i == 0) {
                printWriter.print(ADJ_SCREEN_NAMES_CSV[0]);
            } else if (i == 4) {
                printWriter.print(ADJ_SCREEN_NAMES_CSV[1]);
            } else {
                printWriter.print("???");
            }
        }
    }

    public static void printMemLabel(PrintWriter printWriter, int i, char c) {
        if (i == -1) {
            printWriter.print("    ");
            if (c != 0) {
                printWriter.print(' ');
                return;
            }
            return;
        }
        if (i == 0) {
            printWriter.print("Norm");
            if (c != 0) {
                printWriter.print(c);
                return;
            }
            return;
        }
        if (i == 1) {
            printWriter.print(" Mod");
            if (c != 0) {
                printWriter.print(c);
                return;
            }
            return;
        }
        if (i == 2) {
            printWriter.print(" Low");
            if (c != 0) {
                printWriter.print(c);
                return;
            }
            return;
        }
        if (i == 3) {
            printWriter.print("Crit");
            if (c != 0) {
                printWriter.print(c);
                return;
            }
            return;
        }
        printWriter.print("????");
        if (c != 0) {
            printWriter.print(c);
        }
    }

    public static void printMemLabelCsv(PrintWriter printWriter, int i) {
        if (i >= 0) {
            if (i <= 3) {
                printWriter.print(ADJ_MEM_NAMES_CSV[i]);
            } else {
                printWriter.print("???");
            }
        }
    }

    public static void printPercent(PrintWriter printWriter, double d) {
        double d2 = d * 100.0d;
        if (d2 < 1.0d) {
            printWriter.print(String.format("%.2f", Double.valueOf(d2)));
        } else if (d2 < 10.0d) {
            printWriter.print(String.format("%.1f", Double.valueOf(d2)));
        } else {
            printWriter.print(String.format("%.0f", Double.valueOf(d2)));
        }
        printWriter.print("%");
    }

    public static void printProcStateTag(PrintWriter printWriter, int i) {
        printArrayEntry(printWriter, STATE_TAGS, printArrayEntry(printWriter, ADJ_MEM_TAGS, printArrayEntry(printWriter, ADJ_SCREEN_TAGS, i, 64), 16), 1);
    }

    public static void printProcStateTagProto(ProtoOutputStream protoOutputStream, long j, long j2, long j3, int i) {
        printProto(protoOutputStream, j3, STATE_PROTO_ENUMS, printProto(protoOutputStream, j2, ADJ_MEM_PROTO_ENUMS, printProto(protoOutputStream, j, ADJ_SCREEN_PROTO_ENUMS, i, 64), 16), 1);
    }

    public static void printAdjTag(PrintWriter printWriter, int i) {
        printArrayEntry(printWriter, ADJ_MEM_TAGS, printArrayEntry(printWriter, ADJ_SCREEN_TAGS, i, 4), 1);
    }

    public static void printProcStateAdjTagProto(ProtoOutputStream protoOutputStream, long j, long j2, int i) {
        printProto(protoOutputStream, j2, ADJ_MEM_PROTO_ENUMS, printProto(protoOutputStream, j, ADJ_SCREEN_PROTO_ENUMS, i, 64), 16);
    }

    public static void printProcStateDurationProto(ProtoOutputStream protoOutputStream, long j, int i, long j2) {
        long jStart = protoOutputStream.start(j);
        printProto(protoOutputStream, 1159641169923L, STATE_PROTO_ENUMS, i, 1);
        protoOutputStream.write(1112396529668L, j2);
        protoOutputStream.end(jStart);
    }

    public static void printProcStateTagAndValue(PrintWriter printWriter, int i, long j) {
        printWriter.print(',');
        printProcStateTag(printWriter, i);
        printWriter.print(ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(j);
    }

    public static void printAdjTagAndValue(PrintWriter printWriter, int i, long j) {
        printWriter.print(',');
        printAdjTag(printWriter, i);
        printWriter.print(ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(j);
    }

    public static long dumpSingleTime(PrintWriter printWriter, String str, long[] jArr, int i, long j, long j2) {
        long j3 = 0;
        int i2 = -1;
        int i3 = 0;
        while (i3 < 8) {
            int i4 = -1;
            int i5 = 0;
            while (i5 < 4) {
                int i6 = i5 + i3;
                long j4 = jArr[i6];
                String str2 = "";
                if (i == i6) {
                    j4 += j2 - j;
                    if (printWriter != null) {
                        str2 = " (running)";
                    }
                }
                if (j4 != 0) {
                    if (printWriter != null) {
                        printWriter.print(str);
                        printScreenLabel(printWriter, i2 != i3 ? i3 : -1);
                        printMemLabel(printWriter, i4 != i5 ? i5 : -1, (char) 0);
                        printWriter.print(": ");
                        TimeUtils.formatDuration(j4, printWriter);
                        printWriter.println(str2);
                        i2 = i3;
                        i4 = i5;
                    }
                    j3 += j4;
                }
                i5++;
            }
            i3 += 4;
        }
        if (j3 != 0 && printWriter != null) {
            printWriter.print(str);
            printWriter.print("    TOTAL: ");
            TimeUtils.formatDuration(j3, printWriter);
            printWriter.println();
        }
        return j3;
    }

    public static void dumpAdjTimesCheckin(PrintWriter printWriter, String str, long[] jArr, int i, long j, long j2) {
        for (int i2 = 0; i2 < 8; i2 += 4) {
            for (int i3 = 0; i3 < 4; i3++) {
                int i4 = i3 + i2;
                long j3 = jArr[i4];
                if (i == i4) {
                    j3 += j2 - j;
                }
                if (j3 != 0) {
                    printAdjTagAndValue(printWriter, i4, j3);
                }
            }
        }
    }

    private static void dumpStateHeadersCsv(PrintWriter printWriter, String str, int[] iArr, int[] iArr2, int[] iArr3) {
        boolean z;
        int length = iArr != null ? iArr.length : 1;
        int length2 = iArr2 != null ? iArr2.length : 1;
        int length3 = iArr3 != null ? iArr3.length : 1;
        for (int i = 0; i < length; i++) {
            for (int i2 = 0; i2 < length2; i2++) {
                for (int i3 = 0; i3 < length3; i3++) {
                    printWriter.print(str);
                    if (iArr == null || iArr.length <= 1) {
                        z = false;
                    } else {
                        printScreenLabelCsv(printWriter, iArr[i]);
                        z = true;
                    }
                    if (iArr2 != null && iArr2.length > 1) {
                        if (z) {
                            printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                        }
                        printMemLabelCsv(printWriter, iArr2[i2]);
                        z = true;
                    }
                    if (iArr3 != null && iArr3.length > 1) {
                        if (z) {
                            printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                        }
                        printWriter.print(STATE_NAMES_CSV[iArr3[i3]]);
                    }
                }
            }
        }
    }

    public static void dumpProcessSummaryLocked(PrintWriter printWriter, String str, String str2, ArrayList<ProcessState> arrayList, int[] iArr, int[] iArr2, int[] iArr3, long j, long j2) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            arrayList.get(size).dumpSummary(printWriter, str, str2, iArr, iArr2, iArr3, j, j2);
        }
    }

    public static void dumpProcessListCsv(PrintWriter printWriter, ArrayList<ProcessState> arrayList, boolean z, int[] iArr, boolean z2, int[] iArr2, boolean z3, int[] iArr3, long j) {
        printWriter.print("process");
        printWriter.print(CSV_SEP);
        printWriter.print("uid");
        printWriter.print(CSV_SEP);
        printWriter.print("vers");
        dumpStateHeadersCsv(printWriter, CSV_SEP, z ? iArr : null, z2 ? iArr2 : null, z3 ? iArr3 : null);
        printWriter.println();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ProcessState processState = arrayList.get(size);
            printWriter.print(processState.getName());
            printWriter.print(CSV_SEP);
            UserHandle.formatUid(printWriter, processState.getUid());
            printWriter.print(CSV_SEP);
            printWriter.print(processState.getVersion());
            processState.dumpCsv(printWriter, z, iArr, z2, iArr2, z3, iArr3, j);
            printWriter.println();
        }
    }

    public static int printArrayEntry(PrintWriter printWriter, String[] strArr, int i, int i2) {
        int i3 = i / i2;
        if (i3 >= 0 && i3 < strArr.length) {
            printWriter.print(strArr[i3]);
        } else {
            printWriter.print('?');
        }
        return i - (i3 * i2);
    }

    public static int printProto(ProtoOutputStream protoOutputStream, long j, int[] iArr, int i, int i2) {
        int i3 = i / i2;
        if (i3 >= 0 && i3 < iArr.length) {
            protoOutputStream.write(j, iArr[i3]);
        }
        return i - (i3 * i2);
    }

    public static String collapseString(String str, String str2) {
        if (str2.startsWith(str)) {
            int length = str2.length();
            int length2 = str.length();
            if (length == length2) {
                return "";
            }
            if (length >= length2 && str2.charAt(length2) == '.') {
                return str2.substring(length2);
            }
        }
        return str2;
    }

    public static int aggregateCurrentProcessState(int i) {
        int i2;
        int i3 = i / 64;
        try {
            i2 = PROCESS_STATS_STATE_TO_AGGREGATED_STATE[i % 16];
        } catch (IndexOutOfBoundsException unused) {
            i2 = 0;
        }
        return (i2 << 15) | i3;
    }

    public static void printAggregatedProcStateTagProto(ProtoOutputStream protoOutputStream, long j, long j2, int i) {
        try {
            protoOutputStream.write(j2, i >> 15);
        } catch (IndexOutOfBoundsException unused) {
            protoOutputStream.write(j2, 0);
        }
        try {
            protoOutputStream.write(j, ADJ_SCREEN_PROTO_ENUMS[i & 15]);
        } catch (IndexOutOfBoundsException unused2) {
            protoOutputStream.write(j, 0);
        }
    }
}
