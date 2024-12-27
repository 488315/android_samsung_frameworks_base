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
    public static final String[] ADJ_MEM_NAMES_CSV;
    static final int[] ADJ_MEM_PROTO_ENUMS;
    static final String[] ADJ_MEM_TAGS;
    public static final String[] ADJ_SCREEN_NAMES_CSV;
    static final int[] ADJ_SCREEN_PROTO_ENUMS;
    static final String[] ADJ_SCREEN_TAGS;
    static final String CSV_SEP = "\t";
    private static final int[] PROCESS_STATS_STATE_TO_AGGREGATED_STATE;
    public static final String[] STATE_LABELS;
    public static final String STATE_LABEL_CACHED;
    public static final String STATE_LABEL_TOTAL;
    public static final String[] STATE_NAMES = new String[16];
    public static final String[] STATE_NAMES_CSV;
    static final int[] STATE_PROTO_ENUMS;
    static final String[] STATE_TAGS;

    static {
        STATE_NAMES[0] = "Persist";
        STATE_NAMES[1] = "Top";
        STATE_NAMES[4] = "BFgs";
        STATE_NAMES[2] = "BTop";
        STATE_NAMES[3] = "Fgs";
        STATE_NAMES[5] = "ImpFg";
        STATE_NAMES[6] = "ImpBg";
        STATE_NAMES[7] = "Backup";
        STATE_NAMES[8] = "Service";
        STATE_NAMES[9] = "ServRst";
        STATE_NAMES[10] = "Receivr";
        STATE_NAMES[11] = "HeavyWt";
        STATE_NAMES[12] = CoreSaConstant.VALUE_HOME;
        STATE_NAMES[13] = "LastAct";
        STATE_NAMES[14] = "Cached";
        STATE_NAMES[15] = "Frozen";
        STATE_LABELS = new String[16];
        STATE_LABELS[0] = "Persistent";
        STATE_LABELS[1] = "       Top";
        STATE_LABELS[4] = "   Bnd Fgs";
        STATE_LABELS[2] = "   Bnd Top";
        STATE_LABELS[3] = "       Fgs";
        STATE_LABELS[5] = "    Imp Fg";
        STATE_LABELS[6] = "    Imp Bg";
        STATE_LABELS[7] = "    Backup";
        STATE_LABELS[8] = "   Service";
        STATE_LABELS[9] = "Service Rs";
        STATE_LABELS[10] = "  Receiver";
        STATE_LABELS[11] = " Heavy Wgt";
        STATE_LABELS[12] = "    (Home)";
        STATE_LABELS[13] = "(Last Act)";
        STATE_LABELS[14] = "  (Cached)";
        STATE_LABELS[15] = "    Frozen";
        STATE_LABEL_CACHED = "  (Cached)";
        STATE_LABEL_TOTAL = "     TOTAL";
        STATE_NAMES_CSV = new String[16];
        STATE_NAMES_CSV[0] = "pers";
        STATE_NAMES_CSV[1] = GenerateXML.TOP;
        STATE_NAMES_CSV[4] = "bfgs";
        STATE_NAMES_CSV[2] = "btop";
        STATE_NAMES_CSV[3] = "fgs";
        STATE_NAMES_CSV[5] = "impfg";
        STATE_NAMES_CSV[6] = "impbg";
        STATE_NAMES_CSV[7] = Context.BACKUP_SERVICE;
        STATE_NAMES_CSV[8] = "service";
        STATE_NAMES_CSV[9] = "service-rs";
        STATE_NAMES_CSV[10] = "receiver";
        STATE_NAMES_CSV[11] = "heavy";
        STATE_NAMES_CSV[12] = "home";
        STATE_NAMES_CSV[13] = "lastact";
        STATE_NAMES_CSV[14] = "cached";
        STATE_NAMES_CSV[15] = "frzn";
        STATE_TAGS = new String[16];
        STATE_TAGS[0] = "p";
        STATE_TAGS[1] = "t";
        STATE_TAGS[4] = "y";
        STATE_TAGS[2] = "z";
        STATE_TAGS[3] = "g";
        STATE_TAGS[5] = FullBackup.FILES_TREE_TOKEN;
        STATE_TAGS[6] = XmlTags.TAG_BLOB;
        STATE_TAGS[7] = XmlTags.ATTR_UID;
        STATE_TAGS[8] = XmlTags.TAG_SESSION;
        STATE_TAGS[9] = "x";
        STATE_TAGS[10] = "r";
        STATE_TAGS[11] = "w";
        STATE_TAGS[12] = "h";
        STATE_TAGS[13] = XmlTags.TAG_LEASEE;
        STATE_TAGS[14] = FullBackup.APK_TREE_TOKEN;
        STATE_TAGS[15] = "e";
        STATE_PROTO_ENUMS = new int[16];
        STATE_PROTO_ENUMS[0] = 1;
        STATE_PROTO_ENUMS[1] = 2;
        STATE_PROTO_ENUMS[4] = 20;
        STATE_PROTO_ENUMS[2] = 19;
        STATE_PROTO_ENUMS[3] = 16;
        STATE_PROTO_ENUMS[5] = 3;
        STATE_PROTO_ENUMS[6] = 4;
        STATE_PROTO_ENUMS[7] = 5;
        STATE_PROTO_ENUMS[8] = 6;
        STATE_PROTO_ENUMS[9] = 7;
        STATE_PROTO_ENUMS[10] = 8;
        STATE_PROTO_ENUMS[11] = 9;
        STATE_PROTO_ENUMS[12] = 10;
        STATE_PROTO_ENUMS[13] = 11;
        STATE_PROTO_ENUMS[14] = 12;
        STATE_PROTO_ENUMS[15] = 17;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE = new int[16];
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[0] = 1;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[1] = 2;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[4] = 3;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[2] = 3;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[3] = 4;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[5] = 5;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[6] = 6;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[7] = 6;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[8] = 6;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[9] = 0;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[10] = 7;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[11] = 6;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[12] = 8;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[13] = 8;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[14] = 8;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[15] = 8;
        ADJ_SCREEN_NAMES_CSV = new String[]{"off", "on"};
        ADJ_MEM_NAMES_CSV = new String[]{"norm", "mod", "low", "crit"};
        ADJ_SCREEN_TAGS = new String[]{"0", "1"};
        ADJ_SCREEN_PROTO_ENUMS = new int[]{1, 2};
        ADJ_MEM_TAGS = new String[]{"n", "m", XmlTags.TAG_LEASEE, "c"};
        ADJ_MEM_PROTO_ENUMS = new int[]{1, 2, 3, 4};
    }

    private DumpUtils() {
    }

    public static void printScreenLabel(PrintWriter pw, int offset) {
        switch (offset) {
            case -1:
                pw.print("     ");
                break;
            case 0:
                pw.print("SOff/");
                break;
            case 4:
                pw.print(" SOn/");
                break;
            default:
                pw.print("????/");
                break;
        }
    }

    public static void printScreenLabelCsv(PrintWriter pw, int offset) {
        switch (offset) {
            case -1:
                break;
            case 0:
                pw.print(ADJ_SCREEN_NAMES_CSV[0]);
                break;
            case 4:
                pw.print(ADJ_SCREEN_NAMES_CSV[1]);
                break;
            default:
                pw.print("???");
                break;
        }
    }

    public static void printMemLabel(PrintWriter pw, int offset, char sep) {
        switch (offset) {
            case -1:
                pw.print("    ");
                if (sep != 0) {
                    pw.print(' ');
                    break;
                }
                break;
            case 0:
                pw.print("Norm");
                if (sep != 0) {
                    pw.print(sep);
                    break;
                }
                break;
            case 1:
                pw.print(" Mod");
                if (sep != 0) {
                    pw.print(sep);
                    break;
                }
                break;
            case 2:
                pw.print(" Low");
                if (sep != 0) {
                    pw.print(sep);
                    break;
                }
                break;
            case 3:
                pw.print("Crit");
                if (sep != 0) {
                    pw.print(sep);
                    break;
                }
                break;
            default:
                pw.print("????");
                if (sep != 0) {
                    pw.print(sep);
                    break;
                }
                break;
        }
    }

    public static void printMemLabelCsv(PrintWriter pw, int offset) {
        if (offset >= 0) {
            if (offset <= 3) {
                pw.print(ADJ_MEM_NAMES_CSV[offset]);
            } else {
                pw.print("???");
            }
        }
    }

    public static void printPercent(PrintWriter pw, double fraction) {
        double fraction2 = fraction * 100.0d;
        if (fraction2 < 1.0d) {
            pw.print(String.format("%.2f", Double.valueOf(fraction2)));
        } else if (fraction2 < 10.0d) {
            pw.print(String.format("%.1f", Double.valueOf(fraction2)));
        } else {
            pw.print(String.format("%.0f", Double.valueOf(fraction2)));
        }
        pw.print("%");
    }

    public static void printProcStateTag(PrintWriter pw, int state) {
        printArrayEntry(pw, STATE_TAGS, printArrayEntry(pw, ADJ_MEM_TAGS, printArrayEntry(pw, ADJ_SCREEN_TAGS, state, 64), 16), 1);
    }

    public static void printProcStateTagProto(ProtoOutputStream proto, long screenId, long memId, long stateId, int state) {
        printProto(proto, stateId, STATE_PROTO_ENUMS, printProto(proto, memId, ADJ_MEM_PROTO_ENUMS, printProto(proto, screenId, ADJ_SCREEN_PROTO_ENUMS, state, 64), 16), 1);
    }

    public static void printAdjTag(PrintWriter pw, int state) {
        printArrayEntry(pw, ADJ_MEM_TAGS, printArrayEntry(pw, ADJ_SCREEN_TAGS, state, 4), 1);
    }

    public static void printProcStateAdjTagProto(ProtoOutputStream proto, long screenId, long memId, int state) {
        printProto(proto, memId, ADJ_MEM_PROTO_ENUMS, printProto(proto, screenId, ADJ_SCREEN_PROTO_ENUMS, state, 64), 16);
    }

    public static void printProcStateDurationProto(ProtoOutputStream proto, long fieldId, int procState, long duration) {
        long stateToken = proto.start(fieldId);
        printProto(proto, 1159641169923L, STATE_PROTO_ENUMS, procState, 1);
        proto.write(1112396529668L, duration);
        proto.end(stateToken);
    }

    public static void printProcStateTagAndValue(PrintWriter pw, int state, long value) {
        pw.print(',');
        printProcStateTag(pw, state);
        pw.print(ShortcutConstants.SERVICES_SEPARATOR);
        pw.print(value);
    }

    public static void printAdjTagAndValue(PrintWriter pw, int state, long value) {
        pw.print(',');
        printAdjTag(pw, state);
        pw.print(ShortcutConstants.SERVICES_SEPARATOR);
        pw.print(value);
    }

    public static long dumpSingleTime(PrintWriter pw, String prefix, long[] durations, int curState, long curStartTime, long now) {
        int i;
        long totalTime = 0;
        int printedScreen = -1;
        for (int iscreen = 0; iscreen < 8; iscreen += 4) {
            int printedMem = -1;
            int imem = 0;
            while (imem < 4) {
                int state = imem + iscreen;
                long time = durations[state];
                String running = "";
                if (curState == state) {
                    time += now - curStartTime;
                    if (pw != null) {
                        running = " (running)";
                    }
                }
                if (time != 0) {
                    if (pw != null) {
                        pw.print(prefix);
                        if (printedScreen == iscreen) {
                            i = -1;
                        } else {
                            i = iscreen;
                        }
                        printScreenLabel(pw, i);
                        printedScreen = iscreen;
                        printMemLabel(pw, printedMem != imem ? imem : -1, (char) 0);
                        printedMem = imem;
                        pw.print(": ");
                        TimeUtils.formatDuration(time, pw);
                        pw.println(running);
                    }
                    totalTime += time;
                }
                imem++;
            }
        }
        if (totalTime != 0 && pw != null) {
            pw.print(prefix);
            pw.print("    TOTAL: ");
            TimeUtils.formatDuration(totalTime, pw);
            pw.println();
        }
        return totalTime;
    }

    public static void dumpAdjTimesCheckin(PrintWriter pw, String sep, long[] durations, int curState, long curStartTime, long now) {
        for (int iscreen = 0; iscreen < 8; iscreen += 4) {
            for (int imem = 0; imem < 4; imem++) {
                int state = imem + iscreen;
                long time = durations[state];
                if (curState == state) {
                    time += now - curStartTime;
                }
                if (time != 0) {
                    printAdjTagAndValue(pw, state, time);
                }
            }
        }
    }

    private static void dumpStateHeadersCsv(PrintWriter pw, String sep, int[] screenStates, int[] memStates, int[] procStates) {
        int NS = screenStates != null ? screenStates.length : 1;
        int NM = memStates != null ? memStates.length : 1;
        int NP = procStates != null ? procStates.length : 1;
        for (int is = 0; is < NS; is++) {
            for (int im = 0; im < NM; im++) {
                for (int ip = 0; ip < NP; ip++) {
                    pw.print(sep);
                    boolean printed = false;
                    if (screenStates != null && screenStates.length > 1) {
                        printScreenLabelCsv(pw, screenStates[is]);
                        printed = true;
                    }
                    if (memStates != null && memStates.length > 1) {
                        if (printed) {
                            pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                        }
                        printMemLabelCsv(pw, memStates[im]);
                        printed = true;
                    }
                    if (procStates != null && procStates.length > 1) {
                        if (printed) {
                            pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                        }
                        pw.print(STATE_NAMES_CSV[procStates[ip]]);
                    }
                }
            }
        }
    }

    public static void dumpProcessSummaryLocked(PrintWriter pw, String prefix, String header, ArrayList<ProcessState> procs, int[] screenStates, int[] memStates, int[] procStates, long now, long totalTime) {
        for (int i = procs.size() - 1; i >= 0; i--) {
            ProcessState proc = procs.get(i);
            proc.dumpSummary(pw, prefix, header, screenStates, memStates, procStates, now, totalTime);
        }
    }

    public static void dumpProcessListCsv(PrintWriter pw, ArrayList<ProcessState> procs, boolean sepScreenStates, int[] screenStates, boolean sepMemStates, int[] memStates, boolean sepProcStates, int[] procStates, long now) {
        pw.print("process");
        pw.print(CSV_SEP);
        pw.print("uid");
        pw.print(CSV_SEP);
        pw.print("vers");
        dumpStateHeadersCsv(pw, CSV_SEP, sepScreenStates ? screenStates : null, sepMemStates ? memStates : null, sepProcStates ? procStates : null);
        pw.println();
        for (int i = procs.size() - 1; i >= 0; i--) {
            ProcessState proc = procs.get(i);
            pw.print(proc.getName());
            pw.print(CSV_SEP);
            UserHandle.formatUid(pw, proc.getUid());
            pw.print(CSV_SEP);
            pw.print(proc.getVersion());
            proc.dumpCsv(pw, sepScreenStates, screenStates, sepMemStates, memStates, sepProcStates, procStates, now);
            pw.println();
        }
    }

    public static int printArrayEntry(PrintWriter pw, String[] array, int value, int mod) {
        int index = value / mod;
        if (index >= 0 && index < array.length) {
            pw.print(array[index]);
        } else {
            pw.print('?');
        }
        return value - (index * mod);
    }

    public static int printProto(ProtoOutputStream proto, long fieldId, int[] enums, int value, int mod) {
        int index = value / mod;
        if (index >= 0 && index < enums.length) {
            proto.write(fieldId, enums[index]);
        }
        return value - (index * mod);
    }

    public static String collapseString(String pkgName, String itemName) {
        if (itemName.startsWith(pkgName)) {
            int ITEMLEN = itemName.length();
            int PKGLEN = pkgName.length();
            if (ITEMLEN == PKGLEN) {
                return "";
            }
            if (ITEMLEN >= PKGLEN && itemName.charAt(PKGLEN) == '.') {
                return itemName.substring(PKGLEN);
            }
        }
        return itemName;
    }

    public static int aggregateCurrentProcessState(int curState) {
        int procStateIndex;
        int screenStateIndex = curState / 64;
        int procStateIndex2 = curState % 16;
        try {
            procStateIndex = PROCESS_STATS_STATE_TO_AGGREGATED_STATE[procStateIndex2];
        } catch (IndexOutOfBoundsException e) {
            procStateIndex = 0;
        }
        return (procStateIndex << 15) | screenStateIndex;
    }

    public static void printAggregatedProcStateTagProto(ProtoOutputStream proto, long screenId, long stateId, int state) {
        try {
            proto.write(stateId, state >> 15);
        } catch (IndexOutOfBoundsException e) {
            proto.write(stateId, 0);
        }
        try {
            proto.write(screenId, ADJ_SCREEN_PROTO_ENUMS[state & 15]);
        } catch (IndexOutOfBoundsException e2) {
            proto.write(screenId, 0);
        }
    }
}
