package com.android.systemui.dump;

import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.ProtoDumpable;
import com.android.systemui.dump.DumpHandler;
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
import kotlin.io.CloseableKt;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$sortedWith$1;
import kotlin.text.StringsKt___StringsKt;

public final class DumpHandler {
    public static final Companion Companion = new Companion(null);
    public final SystemUIConfigDumpable config;
    public final DumpManager dumpManager;
    public final LogBufferEulogizer logBufferEulogizer;

    public final class Companion {
        private Companion() {
        }

        public static final DumpsysEntry access$findBestTargetMatch(Companion companion, Collection collection, final String str) {
            Object next;
            companion.getClass();
            FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(collection), new Function1() { // from class: com.android.systemui.dump.DumpHandler$Companion$findBestTargetMatch$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    DumpHandler.Companion companion2 = DumpHandler.Companion;
                    String str2 = str;
                    companion2.getClass();
                    return Boolean.valueOf(((DumpsysEntry) obj).getName().endsWith(str2));
                }
            }));
            if (filteringSequence$iterator$1.hasNext()) {
                next = filteringSequence$iterator$1.next();
                if (filteringSequence$iterator$1.hasNext()) {
                    int length = ((DumpsysEntry) next).getName().length();
                    do {
                        Object next2 = filteringSequence$iterator$1.next();
                        int length2 = ((DumpsysEntry) next2).getName().length();
                        if (length > length2) {
                            next = next2;
                            length = length2;
                        }
                    } while (filteringSequence$iterator$1.hasNext());
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

        public static void dumpBuffer(DumpsysEntry.LogBufferEntry logBufferEntry, PrintWriter printWriter, int i) {
            Trace.beginSection(StringsKt___StringsKt.take(127, logBufferEntry.name));
            preamble(printWriter, logBufferEntry);
            long currentTimeMillis = System.currentTimeMillis();
            LogBuffer logBuffer = logBufferEntry.buffer;
            synchronized (logBuffer) {
                int size = logBuffer.buffer.getSize();
                for (int max = i > 0 ? Math.max(0, logBuffer.buffer.getSize() - i) : 0; max < size; max++) {
                    ((LogMessageImpl) logBuffer.buffer.get(max)).dump(printWriter);
                }
            }
            footer(printWriter, logBufferEntry, System.currentTimeMillis() - currentTimeMillis);
            Trace.endSection();
        }

        public static void dumpDumpable(DumpsysEntry.DumpableEntry dumpableEntry, PrintWriter printWriter, String[] strArr) {
            Trace.beginSection(StringsKt___StringsKt.take(127, dumpableEntry.name));
            preamble(printWriter, dumpableEntry);
            long currentTimeMillis = System.currentTimeMillis();
            dumpableEntry.dumpable.dump(printWriter, strArr);
            footer(printWriter, dumpableEntry, System.currentTimeMillis() - currentTimeMillis);
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
                } else if (dumpsysEntry instanceof DumpsysEntry.TableLogBufferEntry) {
                    dumpTableBuffer((DumpsysEntry.TableLogBufferEntry) dumpsysEntry, printWriter, new String[0]);
                }
            }
        }

        public static void dumpTableBuffer(DumpsysEntry.TableLogBufferEntry tableLogBufferEntry, PrintWriter printWriter, String[] strArr) {
            Trace.beginSection(StringsKt___StringsKt.take(127, tableLogBufferEntry.name));
            preamble(printWriter, tableLogBufferEntry);
            long currentTimeMillis = System.currentTimeMillis();
            tableLogBufferEntry.table.dump(printWriter, strArr);
            footer(printWriter, tableLogBufferEntry, System.currentTimeMillis() - currentTimeMillis);
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
            if (dumpsysEntry instanceof DumpsysEntry.DumpableEntry ? true : dumpsysEntry instanceof DumpsysEntry.TableLogBufferEntry) {
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

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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

    public static ParsedArgs parseArgs(String[] strArr) {
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
                        parsedArgs.tailLength = ((Number) readArgument(it, str, new Function1() { // from class: com.android.systemui.dump.DumpHandler$parseArgs$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                return Integer.valueOf(Integer.parseInt((String) obj));
                            }
                        })).intValue();
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
                        parsedArgs.dumpPriority = (String) readArgument(it, "--dump-priority", new Function1() { // from class: com.android.systemui.dump.DumpHandler$parseArgs$1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                String str2 = (String) obj;
                                if (ArraysKt___ArraysKt.contains(DumpHandlerKt.PRIORITY_OPTIONS, str2)) {
                                    return str2;
                                }
                                throw new IllegalArgumentException();
                            }
                        });
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
                        parsedArgs.tailLength = ((Number) readArgument(it, str, new Function1() { // from class: com.android.systemui.dump.DumpHandler$parseArgs$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                return Integer.valueOf(Integer.parseInt((String) obj));
                            }
                        })).intValue();
                        break;
                    default:
                        throw new ArgParseException("Unknown flag: ".concat(str));
                }
            }
        }
        if (parsedArgs.command == null && (!arrayList.isEmpty()) && ArraysKt___ArraysKt.contains(DumpHandlerKt.COMMANDS, arrayList.get(0))) {
            parsedArgs.command = (String) arrayList.remove(0);
        }
        return parsedArgs;
    }

    public static Object readArgument(Iterator it, String str, Function1 function1) {
        if (!it.hasNext()) {
            throw new ArgParseException("Missing argument for ".concat(str));
        }
        String str2 = (String) it.next();
        try {
            Object invoke = function1.invoke(str2);
            it.remove();
            return invoke;
        } catch (Exception unused) {
            throw new ArgParseException(FontProvider$$ExternalSyntheticOutline0.m("Invalid argument '", str2, "' for flag ", str));
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        ?? arrayList;
        Object next;
        Object next2;
        Trace.beginSection("DumpManager#dump()");
        long uptimeMillis = SystemClock.uptimeMillis();
        try {
            ParsedArgs parseArgs = parseArgs(strArr);
            printWriter.print("Dump starting: ");
            printWriter.println(DumpHandlerKt.DATE_FORMAT.format(Long.valueOf(System.currentTimeMillis())));
            if (Intrinsics.areEqual(parseArgs.dumpPriority, "CRITICAL")) {
                dumpCritical(printWriter, parseArgs);
            } else if (!Intrinsics.areEqual(parseArgs.dumpPriority, "NORMAL") || parseArgs.proto) {
                String str = parseArgs.command;
                DumpManager dumpManager = this.dumpManager;
                if (str != null) {
                    switch (str.hashCode()) {
                        case -1354792126:
                            if (str.equals("config")) {
                                this.config.dump(printWriter, new String[0]);
                                break;
                            }
                            break;
                        case -1353714459:
                            if (str.equals("dumpables")) {
                                listOrDumpEntries(dumpManager.getDumpables(), printWriter, parseArgs);
                                break;
                            }
                            break;
                        case -1045369428:
                            if (str.equals("bugreport-normal")) {
                                dumpNormal(printWriter, parseArgs);
                                break;
                            }
                            break;
                        case -881377691:
                            if (str.equals("tables")) {
                                listOrDumpEntries(dumpManager.getTableLogBuffers(), printWriter, parseArgs);
                                break;
                            }
                            break;
                        case 96673:
                            if (str.equals(SystemUIAnalytics.QPNE_VID_COVER_ALL)) {
                                listOrDumpEntries(dumpManager.getDumpables(), printWriter, parseArgs);
                                listOrDumpEntries(dumpManager.getLogBuffers(), printWriter, parseArgs);
                                listOrDumpEntries(dumpManager.getTableLogBuffers(), printWriter, parseArgs);
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
                                printWriter.println("$ <invocation> buffers");
                                printWriter.println("$ <invocation> tables");
                                printWriter.println("$ <invocation> bugreport-critical");
                                printWriter.println("$ <invocation> bugreport-normal");
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
                                listOrDumpEntries(dumpManager.getLogBuffers(), printWriter, parseArgs);
                                break;
                            }
                            break;
                        case 842828580:
                            if (str.equals("bugreport-critical")) {
                                dumpCritical(printWriter, parseArgs);
                                break;
                            }
                            break;
                    }
                }
                if (parseArgs.proto) {
                    List<String> list = parseArgs.nonFlagArgs;
                    SystemUIProtoDump systemUIProtoDump = new SystemUIProtoDump();
                    Collection dumpables = dumpManager.getDumpables();
                    if (!list.isEmpty()) {
                        for (final String str2 : list) {
                            Companion.getClass();
                            FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(dumpables), new Function1() { // from class: com.android.systemui.dump.DumpHandler$Companion$findBestProtoTargetMatch$1
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    DumpHandler.Companion companion = DumpHandler.Companion;
                                    String str3 = str2;
                                    companion.getClass();
                                    return Boolean.valueOf(((DumpsysEntry.DumpableEntry) obj).getName().endsWith(str3));
                                }
                            }), new Function1() { // from class: com.android.systemui.dump.DumpHandler$Companion$findBestProtoTargetMatch$2
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    return Boolean.valueOf(((DumpsysEntry.DumpableEntry) obj).dumpable instanceof ProtoDumpable);
                                }
                            }));
                            if (filteringSequence$iterator$1.hasNext()) {
                                next2 = filteringSequence$iterator$1.next();
                                if (filteringSequence$iterator$1.hasNext()) {
                                    int length = ((DumpsysEntry.DumpableEntry) next2).name.length();
                                    do {
                                        Object next3 = filteringSequence$iterator$1.next();
                                        int length2 = ((DumpsysEntry.DumpableEntry) next3).name.length();
                                        if (length > length2) {
                                            next2 = next3;
                                            length = length2;
                                        }
                                    } while (filteringSequence$iterator$1.hasNext());
                                }
                            } else {
                                next2 = null;
                            }
                            DumpsysEntry.DumpableEntry dumpableEntry = (DumpsysEntry.DumpableEntry) next2;
                            Dumpable dumpable = dumpableEntry != null ? dumpableEntry.dumpable : null;
                            ProtoDumpable protoDumpable = dumpable instanceof ProtoDumpable ? (ProtoDumpable) dumpable : null;
                            if (protoDumpable != null) {
                                protoDumpable.dumpProto(systemUIProtoDump);
                            }
                        }
                    } else {
                        Iterator it = dumpables.iterator();
                        while (it.hasNext()) {
                            Dumpable dumpable2 = ((DumpsysEntry.DumpableEntry) it.next()).dumpable;
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
                        CloseableKt.closeFinally(bufferedOutputStream, null);
                    } finally {
                    }
                } else {
                    List list2 = parseArgs.nonFlagArgs;
                    if (!list2.isEmpty()) {
                        Collection dumpables2 = dumpManager.getDumpables();
                        Collection logBuffers = dumpManager.getLogBuffers();
                        Collection tableLogBuffers = dumpManager.getTableLogBuffers();
                        if (parseArgs.matchAll) {
                            arrayList = SequencesKt___SequencesKt.toList(new SequencesKt___SequencesKt$sortedWith$1(new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new DumpHandler$findAllMatchesInCollection$1(dumpables2, logBuffers, tableLogBuffers, list2, null)), new Comparator() { // from class: com.android.systemui.dump.DumpHandler$findAllMatchesInCollection$$inlined$sortedBy$1
                                @Override // java.util.Comparator
                                public final int compare(Object obj, Object obj2) {
                                    return ComparisonsKt__ComparisonsKt.compareValues(((DumpsysEntry) obj).getName(), ((DumpsysEntry) obj2).getName());
                                }
                            }));
                        } else {
                            arrayList = new ArrayList();
                            Iterator it2 = list2.iterator();
                            while (it2.hasNext()) {
                                Iterator it3 = new SequencesKt___SequencesKt$sortedWith$1(new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new DumpHandler$findTargetInCollection$1(dumpables2, (String) it2.next(), logBuffers, tableLogBuffers, null)), new Comparator() { // from class: com.android.systemui.dump.DumpHandler$findTargetInCollection$$inlined$sortedBy$1
                                    @Override // java.util.Comparator
                                    public final int compare(Object obj, Object obj2) {
                                        return ComparisonsKt__ComparisonsKt.compareValues(((DumpsysEntry) obj).getName(), ((DumpsysEntry) obj2).getName());
                                    }
                                }).iterator();
                                if (it3.hasNext()) {
                                    next = it3.next();
                                    if (it3.hasNext()) {
                                        int length3 = ((DumpsysEntry) next).getName().length();
                                        do {
                                            Object next4 = it3.next();
                                            int length4 = ((DumpsysEntry) next4).getName().length();
                                            if (length3 > length4) {
                                                next = next4;
                                                length3 = length4;
                                            }
                                        } while (it3.hasNext());
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
                        Iterator it4 = ((Iterable) arrayList).iterator();
                        while (it4.hasNext()) {
                            dump((DumpsysEntry) it4.next(), printWriter, parseArgs);
                        }
                    } else if (parseArgs.listOnly) {
                        Collection dumpables3 = dumpManager.getDumpables();
                        Collection logBuffers2 = dumpManager.getLogBuffers();
                        Collection tableLogBuffers2 = dumpManager.getTableLogBuffers();
                        printWriter.println("Dumpables:");
                        listTargetNames(dumpables3, printWriter);
                        printWriter.println();
                        printWriter.println("Buffers:");
                        listTargetNames(logBuffers2, printWriter);
                        printWriter.println();
                        printWriter.println("TableBuffers:");
                        listTargetNames(tableLogBuffers2, printWriter);
                    } else {
                        printWriter.println("Nothing to dump :(");
                    }
                }
            } else {
                dumpNormal(printWriter, parseArgs);
            }
            printWriter.println();
            printWriter.println("Dump took " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms");
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

    public final void dumpNormal(final PrintWriter printWriter, ParsedArgs parsedArgs) {
        String[] strArr;
        Companion companion;
        DumpManager dumpManager = this.dumpManager;
        Iterator it = dumpManager.getDumpables().iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            strArr = parsedArgs.rawArgs;
            companion = Companion;
            if (!hasNext) {
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
            Stream<String> lines = logBufferEulogizer.files.lines(logBufferEulogizer.logPath);
            try {
                printWriter.println();
                printWriter.println();
                printWriter.println("=============== BUFFERS FROM MOST RECENT CRASH ===============");
                lines.forEach(new Consumer() { // from class: com.android.systemui.dump.LogBufferEulogizer$readEulogyIfPresent$1$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        printWriter.println((String) obj);
                    }
                });
                Unit unit = Unit.INSTANCE;
                AutoCloseableKt.closeFinally(lines, null);
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
