package com.android.systemui.dump;

import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.ProtoDumpable;
import com.android.systemui.biometrics.AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpsysEntry;
import com.android.systemui.dump.nano.SystemUIProtoDump;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.util.SystemUIAnalytics;
import com.google.protobuf.nano.MessageNano;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Stream;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence.AnonymousClass1;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$sortedWith$1;
import kotlin.text.StringsKt___StringsKt;

/* loaded from: classes2.dex */
public final class DumpHandler {
    public static final Companion Companion = new Companion(null);
    public final SystemUIConfigDumpable config;
    public final DumpManager dumpManager;
    public final LogBufferEulogizer logBufferEulogizer;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final DumpsysEntry access$findBestTargetMatch(Companion companion, Collection collection, String str) {
            Object next;
            companion.getClass();
            FilteringSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(collection), new DumpHandler$Companion$$ExternalSyntheticLambda0(str, 1)).new AnonymousClass1();
            if (anonymousClass1.hasNext()) {
                next = anonymousClass1.next();
                if (anonymousClass1.hasNext()) {
                    int length = ((DumpsysEntry) next).getName().length();
                    do {
                        Object next2 = anonymousClass1.next();
                        int length2 = ((DumpsysEntry) next2).getName().length();
                        if (length > length2) {
                            next = next2;
                            length = length2;
                        }
                    } while (anonymousClass1.hasNext());
                }
            } else {
                next = null;
            }
            return (DumpsysEntry) next;
        }

        public static final boolean access$matchesAny(Companion companion, DumpsysEntry dumpsysEntry, Collection collection) {
            companion.getClass();
            Collection<String> collection2 = collection;
            if ((collection2 instanceof Collection) && collection2.isEmpty()) {
                return false;
            }
            for (String str : collection2) {
                DumpHandler.Companion.getClass();
                if (dumpsysEntry.getName().endsWith(str)) {
                    return true;
                }
            }
            return false;
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x002c A[Catch: all -> 0x003a, TRY_LEAVE, TryCatch #0 {, blocks: (B:7:0x0019, B:8:0x0024, B:10:0x002c), top: B:19:0x0019 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static void dumpBuffer(DumpsysEntry.LogBufferEntry logBufferEntry, PrintWriter printWriter, int i) {
            int size;
            Trace.beginSection(StringsKt___StringsKt.take(127, logBufferEntry.name));
            preamble(printWriter, logBufferEntry);
            long jCurrentTimeMillis = System.currentTimeMillis();
            LogBuffer logBuffer = logBufferEntry.buffer;
            synchronized (logBuffer) {
                int iMax = 0;
                if (i <= 0) {
                    size = logBuffer.buffer.getSize();
                    while (iMax < size) {
                        ((LogMessageImpl) logBuffer.buffer.get(iMax)).dump(printWriter);
                        iMax++;
                    }
                } else {
                    iMax = Math.max(0, logBuffer.buffer.getSize() - i);
                    size = logBuffer.buffer.getSize();
                    while (iMax < size) {
                    }
                }
            }
            footer(printWriter, logBufferEntry, System.currentTimeMillis() - jCurrentTimeMillis);
            Trace.endSection();
        }

        public static void dumpDumpable(DumpsysEntry.DumpableEntry dumpableEntry, PrintWriter printWriter, String[] strArr) {
            Trace.beginSection(StringsKt___StringsKt.take(127, dumpableEntry.name));
            preamble(printWriter, dumpableEntry);
            long jCurrentTimeMillis = System.currentTimeMillis();
            dumpableEntry.dumpable.dump(printWriter, strArr);
            footer(printWriter, dumpableEntry, System.currentTimeMillis() - jCurrentTimeMillis);
            Trace.endSection();
        }

        public static void dumpEntries(Collection collection, PrintWriter printWriter) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                DumpsysEntry dumpsysEntry = (DumpsysEntry) it.next();
                DumpHandler.Companion.getClass();
                if (dumpsysEntry instanceof DumpsysEntry.DumpableEntry) {
                    dumpDumpable((DumpsysEntry.DumpableEntry) dumpsysEntry, printWriter, new String[0]);
                } else if (dumpsysEntry instanceof DumpsysEntry.LogBufferEntry) {
                    dumpBuffer((DumpsysEntry.LogBufferEntry) dumpsysEntry, printWriter, 0);
                } else {
                    if (!(dumpsysEntry instanceof DumpsysEntry.TableLogBufferEntry)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    dumpTableBuffer((DumpsysEntry.TableLogBufferEntry) dumpsysEntry, printWriter, new String[0]);
                }
            }
        }

        public static void dumpTableBuffer(DumpsysEntry.TableLogBufferEntry tableLogBufferEntry, PrintWriter printWriter, String[] strArr) {
            Trace.beginSection(StringsKt___StringsKt.take(127, tableLogBufferEntry.name));
            preamble(printWriter, tableLogBufferEntry);
            long jCurrentTimeMillis = System.currentTimeMillis();
            tableLogBufferEntry.table.dump(printWriter, strArr);
            footer(printWriter, tableLogBufferEntry, System.currentTimeMillis() - jCurrentTimeMillis);
            Trace.endSection();
        }

        public static void footer(PrintWriter printWriter, DumpsysEntry dumpsysEntry, long j) {
            if (dumpsysEntry instanceof DumpsysEntry.DumpableEntry) {
                printWriter.println();
                DumpsysEntry.DumpableEntry dumpableEntry = (DumpsysEntry.DumpableEntry) dumpsysEntry;
                printWriter.print(dumpableEntry.priority);
                printWriter.print(" dump took ");
                printWriter.print(j);
                printWriter.print("ms -- ");
                printWriter.print(dumpableEntry.name);
                if (dumpableEntry.priority == DumpPriority.CRITICAL && j > 25) {
                    printWriter.print(" -- warning: individual dump time exceeds 5% of total CRITICAL dump time!");
                }
                printWriter.println();
            }
        }

        public static void preamble(PrintWriter printWriter, DumpsysEntry dumpsysEntry) {
            if ((dumpsysEntry instanceof DumpsysEntry.DumpableEntry) || (dumpsysEntry instanceof DumpsysEntry.TableLogBufferEntry)) {
                printWriter.println();
                printWriter.println(dumpsysEntry.getName() + ":");
                printWriter.println("----------------------------------------------------------------------------");
                return;
            }
            if (!(dumpsysEntry instanceof DumpsysEntry.LogBufferEntry)) {
                throw new NoWhenBranchMatchedException();
            }
            printWriter.println();
            printWriter.println();
            printWriter.println("BUFFER " + ((DumpsysEntry.LogBufferEntry) dumpsysEntry).name + ":");
            printWriter.println("----------------------------------------------------------------------------");
        }

        private Companion() {
        }
    }

    public DumpHandler(DumpManager dumpManager, LogBufferEulogizer logBufferEulogizer, SystemUIConfigDumpable systemUIConfigDumpable) {
        this.dumpManager = dumpManager;
        this.logBufferEulogizer = logBufferEulogizer;
        this.config = systemUIConfigDumpable;
    }

    public static void listOrDumpEntries(Collection collection, PrintWriter printWriter, ParsedArgs parsedArgs) {
        if (parsedArgs.listOnly) {
            listTargetNames(collection, printWriter);
            return;
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            dump((DumpsysEntry) it.next(), printWriter, parsedArgs);
        }
    }

    public static void listTargetNames(Collection collection, PrintWriter printWriter) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            printWriter.println(((DumpsysEntry) it.next()).getName());
        }
    }

    public static ParsedArgs parseArgs(String[] strArr) throws ArgParseException {
        List mutableList = ArraysKt___ArraysKt.toMutableList(strArr);
        ParsedArgs parsedArgs = new ParsedArgs(strArr, mutableList);
        ArrayList arrayList = (ArrayList) mutableList;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str.startsWith("-")) {
                it.remove();
                switch (str.hashCode()) {
                    case -1616754616:
                        if (!str.equals("--proto")) {
                            throw new ArgParseException("Unknown flag: ".concat(str));
                        }
                        parsedArgs.proto = true;
                        break;
                    case 1492:
                        if (!str.equals("-a")) {
                            throw new ArgParseException("Unknown flag: ".concat(str));
                        }
                        parsedArgs.matchAll = true;
                        break;
                    case 1499:
                        if (!str.equals("-h")) {
                            throw new ArgParseException("Unknown flag: ".concat(str));
                        }
                        parsedArgs.command = "help";
                        break;
                    case VolteConstants.ErrorCode.DNS_FAILURE_HOST /* 1503 */:
                        if (!str.equals("-l")) {
                            throw new ArgParseException("Unknown flag: ".concat(str));
                        }
                        parsedArgs.listOnly = true;
                        break;
                    case 1511:
                        if (!str.equals("-t")) {
                            throw new ArgParseException("Unknown flag: ".concat(str));
                        }
                        parsedArgs.tailLength = ((Number) readArgument(it, str, new DumpHandler$$ExternalSyntheticLambda0(1))).intValue();
                        break;
                    case 42995713:
                        if (!str.equals("--all")) {
                            throw new ArgParseException("Unknown flag: ".concat(str));
                        }
                        parsedArgs.matchAll = true;
                        break;
                    case 1056887741:
                        if (!str.equals("--dump-priority")) {
                            throw new ArgParseException("Unknown flag: ".concat(str));
                        }
                        parsedArgs.dumpPriority = (String) readArgument(it, "--dump-priority", new DumpHandler$$ExternalSyntheticLambda0(0));
                        break;
                    case 1333069025:
                        if (!str.equals("--help")) {
                            throw new ArgParseException("Unknown flag: ".concat(str));
                        }
                        parsedArgs.command = "help";
                        break;
                    case 1333192254:
                        if (!str.equals("--list")) {
                            throw new ArgParseException("Unknown flag: ".concat(str));
                        }
                        parsedArgs.listOnly = true;
                        break;
                    case 1333422576:
                        if (!str.equals("--tail")) {
                            throw new ArgParseException("Unknown flag: ".concat(str));
                        }
                        parsedArgs.tailLength = ((Number) readArgument(it, str, new DumpHandler$$ExternalSyntheticLambda0(1))).intValue();
                        break;
                    default:
                        throw new ArgParseException("Unknown flag: ".concat(str));
                }
            }
        }
        if (parsedArgs.command == null && !arrayList.isEmpty() && ArraysKt___ArraysKt.contains(DumpHandlerKt.COMMANDS, arrayList.get(0))) {
            parsedArgs.command = (String) arrayList.remove(0);
        }
        return parsedArgs;
    }

    public static Object readArgument(Iterator it, String str, Function1 function1) throws ArgParseException {
        if (!it.hasNext()) {
            throw new ArgParseException("Missing argument for ".concat(str));
        }
        String str2 = (String) it.next();
        try {
            Object objMo781invoke = function1.mo781invoke(str2);
            it.remove();
            return objMo781invoke;
        } catch (Exception unused) {
            throw new ArgParseException(AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Invalid argument '", str2, "' for flag ", str));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:49:0x016a  */
    /* JADX WARN: Type inference failed for: r0v13, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v18, types: [java.util.List] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) throws Exception {
        ?? arrayList;
        Object next;
        Object next2;
        Trace.beginSection("DumpManager#dump()");
        long jUptimeMillis = SystemClock.uptimeMillis();
        try {
            ParsedArgs args = parseArgs(strArr);
            printWriter.print("Dump starting: ");
            printWriter.println(DumpHandlerKt.DATE_FORMAT.format(Long.valueOf(System.currentTimeMillis())));
            if (Intrinsics.areEqual(args.dumpPriority, "CRITICAL")) {
                dumpCritical(printWriter, args);
            } else if (!Intrinsics.areEqual(args.dumpPriority, "NORMAL") || args.proto) {
                String str = args.command;
                DumpManager dumpManager = this.dumpManager;
                if (str != null) {
                    switch (str.hashCode()) {
                        case -1354792126:
                            if (str.equals("config")) {
                                this.config.dump(printWriter, new String[0]);
                                break;
                            } else if (!args.proto) {
                                List list = args.nonFlagArgs;
                                if (list.isEmpty()) {
                                    if (args.listOnly) {
                                        Collection dumpables = dumpManager.getDumpables();
                                        Collection logBuffers = dumpManager.getLogBuffers();
                                        Collection tableLogBuffers = dumpManager.getTableLogBuffers();
                                        printWriter.println("Dumpables:");
                                        listTargetNames(dumpables, printWriter);
                                        printWriter.println();
                                        printWriter.println("Buffers:");
                                        listTargetNames(logBuffers, printWriter);
                                        printWriter.println();
                                        printWriter.println("TableBuffers:");
                                        listTargetNames(tableLogBuffers, printWriter);
                                        break;
                                    } else {
                                        printWriter.println("Nothing to dump :(");
                                        break;
                                    }
                                } else {
                                    Collection dumpables2 = dumpManager.getDumpables();
                                    Collection logBuffers2 = dumpManager.getLogBuffers();
                                    Collection tableLogBuffers2 = dumpManager.getTableLogBuffers();
                                    if (args.matchAll) {
                                        arrayList = SequencesKt___SequencesKt.toList(new SequencesKt___SequencesKt$sortedWith$1(new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new DumpHandler$findAllMatchesInCollection$1(dumpables2, logBuffers2, tableLogBuffers2, list, null)), new Comparator() { // from class: com.android.systemui.dump.DumpHandler$findAllMatchesInCollection$$inlined$sortedBy$1
                                            @Override // java.util.Comparator
                                            public final int compare(Object obj, Object obj2) {
                                                return ComparisonsKt__ComparisonsKt.compareValues(((DumpsysEntry) obj).getName(), ((DumpsysEntry) obj2).getName());
                                            }
                                        }));
                                    } else {
                                        arrayList = new ArrayList();
                                        Iterator it = list.iterator();
                                        while (it.hasNext()) {
                                            Iterator it2 = new SequencesKt___SequencesKt$sortedWith$1(new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new DumpHandler$findTargetInCollection$1(dumpables2, (String) it.next(), logBuffers2, tableLogBuffers2, null)), new Comparator() { // from class: com.android.systemui.dump.DumpHandler$findTargetInCollection$$inlined$sortedBy$1
                                                @Override // java.util.Comparator
                                                public final int compare(Object obj, Object obj2) {
                                                    return ComparisonsKt__ComparisonsKt.compareValues(((DumpsysEntry) obj).getName(), ((DumpsysEntry) obj2).getName());
                                                }
                                            }).iterator();
                                            if (it2.hasNext()) {
                                                next = it2.next();
                                                if (it2.hasNext()) {
                                                    int length = ((DumpsysEntry) next).getName().length();
                                                    do {
                                                        Object next3 = it2.next();
                                                        int length2 = ((DumpsysEntry) next3).getName().length();
                                                        if (length > length2) {
                                                            next = next3;
                                                            length = length2;
                                                        }
                                                    } while (it2.hasNext());
                                                }
                                            } else {
                                                next = null;
                                            }
                                            DumpsysEntry dumpsysEntry = (DumpsysEntry) next;
                                            if (dumpsysEntry != null) {
                                                arrayList.add(dumpsysEntry);
                                            }
                                        }
                                    }
                                    Iterator it3 = ((Iterable) arrayList).iterator();
                                    while (it3.hasNext()) {
                                        dump((DumpsysEntry) it3.next(), printWriter, args);
                                    }
                                    break;
                                }
                            } else {
                                List<String> list2 = args.nonFlagArgs;
                                SystemUIProtoDump systemUIProtoDump = new SystemUIProtoDump();
                                Collection dumpables3 = dumpManager.getDumpables();
                                if (list2.isEmpty()) {
                                    Iterator it4 = dumpables3.iterator();
                                    while (it4.hasNext()) {
                                        Dumpable dumpable = ((DumpsysEntry.DumpableEntry) it4.next()).dumpable;
                                        ProtoDumpable protoDumpable = dumpable instanceof ProtoDumpable ? (ProtoDumpable) dumpable : null;
                                        if (protoDumpable != null) {
                                            protoDumpable.dumpProto(systemUIProtoDump);
                                        }
                                    }
                                } else {
                                    for (String str2 : list2) {
                                        Companion.getClass();
                                        FilteringSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(dumpables3), new DumpHandler$Companion$$ExternalSyntheticLambda0(str2, 0)), new DumpHandler$$ExternalSyntheticLambda0(2)).new AnonymousClass1();
                                        if (anonymousClass1.hasNext()) {
                                            next2 = anonymousClass1.next();
                                            if (anonymousClass1.hasNext()) {
                                                int length3 = ((DumpsysEntry.DumpableEntry) next2).name.length();
                                                do {
                                                    Object next4 = anonymousClass1.next();
                                                    int length4 = ((DumpsysEntry.DumpableEntry) next4).name.length();
                                                    if (length3 > length4) {
                                                        next2 = next4;
                                                        length3 = length4;
                                                    }
                                                } while (anonymousClass1.hasNext());
                                            }
                                        } else {
                                            next2 = null;
                                        }
                                        DumpsysEntry.DumpableEntry dumpableEntry = (DumpsysEntry.DumpableEntry) next2;
                                        Dumpable dumpable2 = dumpableEntry != null ? dumpableEntry.dumpable : null;
                                        ProtoDumpable protoDumpable2 = dumpable2 instanceof ProtoDumpable ? (ProtoDumpable) dumpable2 : null;
                                        if (protoDumpable2 != null) {
                                            protoDumpable2.dumpProto(systemUIProtoDump);
                                        }
                                    }
                                }
                                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileDescriptor));
                                try {
                                    bufferedOutputStream.write(MessageNano.toByteArray(systemUIProtoDump));
                                    bufferedOutputStream.flush();
                                    Unit unit = Unit.INSTANCE;
                                    bufferedOutputStream.close();
                                    break;
                                } finally {
                                }
                            }
                        case -1353714459:
                            if (str.equals("dumpables")) {
                                listOrDumpEntries(dumpManager.getDumpables(), printWriter, args);
                                break;
                            }
                            break;
                        case -1045369428:
                            if (str.equals("bugreport-normal")) {
                                dumpNormal(printWriter, args);
                                break;
                            }
                            break;
                        case -881377691:
                            if (str.equals("tables")) {
                                listOrDumpEntries(dumpManager.getTableLogBuffers(), printWriter, args);
                                break;
                            }
                            break;
                        case 96673:
                            if (str.equals(SystemUIAnalytics.QPNE_VID_COVER_ALL)) {
                                listOrDumpEntries(dumpManager.getDumpables(), printWriter, args);
                                listOrDumpEntries(dumpManager.getLogBuffers(), printWriter, args);
                                listOrDumpEntries(dumpManager.getTableLogBuffers(), printWriter, args);
                                break;
                            }
                            break;
                        case 3198785:
                            if (str.equals("help")) {
                                printWriter.println("Let <invocation> be:");
                                printWriter.println("$ adb shell dumpsys activity service com.android.systemui/.SystemUIService");
                                printWriter.println();
                                printWriter.println("Most common usage:");
                                printWriter.println("$ <invocation> <targets>");
                                printWriter.println("$ <invocation> NotifLog");
                                printWriter.println("$ <invocation> StatusBar FalsingManager BootCompleteCacheImpl");
                                printWriter.println("etc.");
                                printWriter.println();
                                printWriter.println("Print all matches, instead of the best match:");
                                printWriter.println("$ <invocation> --all <targets>");
                                printWriter.println("$ <invocation> --all Log");
                                printWriter.println();
                                printWriter.println("Special commands:");
                                printWriter.println("$ <invocation> dumpables");
                                AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "$ <invocation> buffers", "$ <invocation> tables", "$ <invocation> bugreport-critical", "$ <invocation> bugreport-normal");
                                printWriter.println("$ <invocation> config");
                                printWriter.println();
                                printWriter.println("Targets can be listed:");
                                printWriter.println("$ <invocation> --list");
                                printWriter.println("$ <invocation> dumpables --list");
                                printWriter.println("$ <invocation> buffers --list");
                                printWriter.println("$ <invocation> tables --list");
                                printWriter.println();
                                printWriter.println("Show only the most recent N lines of buffers");
                                printWriter.println("$ <invocation> NotifLog --tail 30");
                                break;
                            }
                            break;
                        case 227996723:
                            if (str.equals("buffers")) {
                                listOrDumpEntries(dumpManager.getLogBuffers(), printWriter, args);
                                break;
                            }
                            break;
                        case 842828580:
                            if (str.equals("bugreport-critical")) {
                                dumpCritical(printWriter, args);
                                break;
                            }
                            break;
                    }
                }
            } else {
                dumpNormal(printWriter, args);
            }
            printWriter.println();
            printWriter.println("Dump took " + (SystemClock.uptimeMillis() - jUptimeMillis) + "ms");
            Trace.endSection();
        } catch (ArgParseException e) {
            printWriter.println(e.getMessage());
        }
    }

    public final void dumpCritical(PrintWriter printWriter, ParsedArgs parsedArgs) {
        for (DumpsysEntry.DumpableEntry dumpableEntry : this.dumpManager.getDumpables()) {
            if (dumpableEntry.priority == DumpPriority.CRITICAL) {
                Companion.getClass();
                Companion.dumpDumpable(dumpableEntry, printWriter, parsedArgs.rawArgs);
            }
        }
    }

    public final void dumpNormal(final PrintWriter printWriter, ParsedArgs parsedArgs) throws Exception {
        String[] strArr;
        Companion companion;
        DumpManager dumpManager = this.dumpManager;
        Iterator it = dumpManager.getDumpables().iterator();
        while (true) {
            boolean zHasNext = it.hasNext();
            strArr = parsedArgs.rawArgs;
            companion = Companion;
            if (!zHasNext) {
                break;
            }
            DumpsysEntry.DumpableEntry dumpableEntry = (DumpsysEntry.DumpableEntry) it.next();
            if (dumpableEntry.priority == DumpPriority.NORMAL) {
                companion.getClass();
                Companion.dumpDumpable(dumpableEntry, printWriter, strArr);
            }
        }
        for (DumpsysEntry.LogBufferEntry logBufferEntry : dumpManager.getLogBuffers()) {
            int i = parsedArgs.tailLength;
            companion.getClass();
            Companion.dumpBuffer(logBufferEntry, printWriter, i);
        }
        for (DumpsysEntry.TableLogBufferEntry tableLogBufferEntry : dumpManager.getTableLogBuffers()) {
            companion.getClass();
            Companion.dumpTableBuffer(tableLogBufferEntry, printWriter, strArr);
        }
        LogBufferEulogizer logBufferEulogizer = this.logBufferEulogizer;
        logBufferEulogizer.getClass();
        try {
            long millisSinceLastWrite = logBufferEulogizer.getMillisSinceLastWrite(logBufferEulogizer.logPath);
            if (millisSinceLastWrite > logBufferEulogizer.maxLogAgeToDump) {
                Log.i("BufferEulogizer", "Not eulogizing buffers; they are " + TimeUnit.HOURS.convert(millisSinceLastWrite, TimeUnit.MILLISECONDS) + " hours old");
                return;
            }
            Stream<String> streamLines = logBufferEulogizer.files.lines(logBufferEulogizer.logPath);
            try {
                printWriter.println();
                printWriter.println();
                printWriter.println("=============== BUFFERS FROM MOST RECENT CRASH ===============");
                final Function1 function1 = new Function1() { // from class: com.android.systemui.dump.LogBufferEulogizer$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        printWriter.println((String) obj);
                        return Unit.INSTANCE;
                    }
                };
                streamLines.forEach(new Consumer() { // from class: com.android.systemui.dump.LogBufferEulogizerKt$sam$java_util_function_Consumer$0
                    @Override // java.util.function.Consumer
                    public final /* synthetic */ void accept(Object obj) {
                        function1.mo781invoke(obj);
                    }
                });
                Unit unit = Unit.INSTANCE;
                streamLines.close();
            } finally {
            }
        } catch (IOException unused) {
        } catch (UncheckedIOException e) {
            Log.e("BufferEulogizer", "UncheckedIOException while dumping the core", e);
        }
    }

    public static void dump(DumpsysEntry dumpsysEntry, PrintWriter printWriter, ParsedArgs parsedArgs) {
        boolean z = dumpsysEntry instanceof DumpsysEntry.DumpableEntry;
        String[] strArr = parsedArgs.rawArgs;
        Companion companion = Companion;
        if (z) {
            companion.getClass();
            Companion.dumpDumpable((DumpsysEntry.DumpableEntry) dumpsysEntry, printWriter, strArr);
        } else if (dumpsysEntry instanceof DumpsysEntry.LogBufferEntry) {
            int i = parsedArgs.tailLength;
            companion.getClass();
            Companion.dumpBuffer((DumpsysEntry.LogBufferEntry) dumpsysEntry, printWriter, i);
        } else {
            if (dumpsysEntry instanceof DumpsysEntry.TableLogBufferEntry) {
                companion.getClass();
                Companion.dumpTableBuffer((DumpsysEntry.TableLogBufferEntry) dumpsysEntry, printWriter, strArr);
                return;
            }
            throw new NoWhenBranchMatchedException();
        }
    }
}
