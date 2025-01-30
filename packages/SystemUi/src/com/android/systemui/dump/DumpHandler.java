package com.android.systemui.dump;

import android.content.Context;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.ProtoDumpable;
import com.android.systemui.R;
import com.android.systemui.dump.nano.SystemUIProtoDump;
import com.android.systemui.shared.system.UncaughtExceptionPreHandlerManager;
import com.google.protobuf.nano.MessageNano;
import com.samsung.android.knox.p045ex.peripheral.PeripheralConstants;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.inject.Provider;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p055io.CloseableKt;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt___SequencesKt$sortedWith$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DumpHandler {
    public final Context context;
    public final DumpManager dumpManager;
    public final LogBufferEulogizer logBufferEulogizer;
    public final Map startables;
    public final UncaughtExceptionPreHandlerManager uncaughtExceptionPreHandlerManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public DumpHandler(Context context, DumpManager dumpManager, LogBufferEulogizer logBufferEulogizer, Map<Class<?>, Provider> map, UncaughtExceptionPreHandlerManager uncaughtExceptionPreHandlerManager) {
        this.context = context;
        this.dumpManager = dumpManager;
        this.logBufferEulogizer = logBufferEulogizer;
        this.startables = map;
        this.uncaughtExceptionPreHandlerManager = uncaughtExceptionPreHandlerManager;
    }

    public static void dumpServiceList(PrintWriter printWriter, String str, String[] strArr) {
        printWriter.print(str);
        printWriter.print(": ");
        if (strArr == null) {
            printWriter.println(PeripheralConstants.Result.NOT_AVAILABLE);
            return;
        }
        printWriter.print(strArr.length);
        printWriter.println(" services");
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            printWriter.print("  ");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.println(strArr[i]);
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
            throw new ArgParseException(FontProvider$$ExternalSyntheticOutline0.m32m("Invalid argument '", str2, "' for flag ", str));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Object next;
        Function0 function0;
        Trace.beginSection("DumpManager#dump()");
        long uptimeMillis = SystemClock.uptimeMillis();
        try {
            ParsedArgs parseArgs = parseArgs(strArr);
            if (Intrinsics.areEqual(parseArgs.dumpPriority, "CRITICAL")) {
                dumpCritical(printWriter, parseArgs);
            } else if (!Intrinsics.areEqual(parseArgs.dumpPriority, "NORMAL") || parseArgs.proto) {
                String str = parseArgs.command;
                if (str != null) {
                    switch (str.hashCode()) {
                        case -1354792126:
                            if (str.equals("config")) {
                                dumpConfig(printWriter);
                                break;
                            }
                            break;
                        case -1353714459:
                            if (str.equals("dumpables")) {
                                if (parseArgs.listOnly) {
                                    this.dumpManager.listDumpables(printWriter);
                                    break;
                                } else {
                                    DumpManager dumpManager = this.dumpManager;
                                    String[] strArr2 = parseArgs.rawArgs;
                                    synchronized (dumpManager) {
                                        Iterator it = ((TreeMap) dumpManager.dumpables).values().iterator();
                                        while (it.hasNext()) {
                                            DumpManager.dumpDumpable((RegisteredDumpable) it.next(), printWriter, strArr2);
                                        }
                                    }
                                    this.dumpManager.dumpNsDumpables(printWriter, parseArgs.rawArgs);
                                    break;
                                }
                            }
                            break;
                        case -1045369428:
                            if (str.equals("bugreport-normal")) {
                                dumpNormal(printWriter, parseArgs);
                                this.dumpManager.dumpNsDumpables(printWriter, parseArgs.rawArgs);
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
                                printWriter.println("Special commands:");
                                printWriter.println("$ <invocation> dumpables");
                                printWriter.println("$ <invocation> buffers");
                                printWriter.println("$ <invocation> bugreport-critical");
                                printWriter.println("$ <invocation> bugreport-normal");
                                printWriter.println("$ <invocation> config");
                                printWriter.println();
                                printWriter.println("Targets can be listed:");
                                printWriter.println("$ <invocation> --list");
                                printWriter.println("$ <invocation> dumpables --list");
                                printWriter.println("$ <invocation> buffers --list");
                                printWriter.println();
                                printWriter.println("Show only the most recent N lines of buffers");
                                printWriter.println("$ <invocation> NotifLog --tail 30");
                                break;
                            }
                            break;
                        case 227996723:
                            if (str.equals("buffers")) {
                                boolean z = parseArgs.listOnly;
                                DumpManager dumpManager2 = this.dumpManager;
                                if (z) {
                                    dumpManager2.listBuffers(printWriter);
                                    break;
                                } else {
                                    dumpManager2.dumpBuffers(printWriter, parseArgs.tailLength);
                                    break;
                                }
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
                    if (!list.isEmpty()) {
                        for (String str2 : list) {
                            DumpManager dumpManager3 = this.dumpManager;
                            synchronized (dumpManager3) {
                                ProtoDumpable findBestProtoTargetMatch = DumpManager.findBestProtoTargetMatch(str2, dumpManager3.dumpables);
                                if (findBestProtoTargetMatch != null) {
                                    findBestProtoTargetMatch.dumpProto(systemUIProtoDump);
                                }
                            }
                        }
                    } else {
                        DumpManager dumpManager4 = this.dumpManager;
                        synchronized (dumpManager4) {
                            Iterator it2 = ((TreeMap) dumpManager4.dumpables).values().iterator();
                            while (it2.hasNext()) {
                                Object obj = ((RegisteredDumpable) it2.next()).dumpable;
                                if (obj instanceof ProtoDumpable) {
                                    ((ProtoDumpable) obj).dumpProto(systemUIProtoDump);
                                }
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
                    List<String> list2 = parseArgs.nonFlagArgs;
                    if (!list2.isEmpty()) {
                        for (String str3 : list2) {
                            DumpManager dumpManager5 = this.dumpManager;
                            String[] strArr3 = parseArgs.rawArgs;
                            int i = parseArgs.tailLength;
                            synchronized (dumpManager5) {
                                Iterator it3 = new SequencesKt___SequencesKt$sortedWith$1(new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new DumpManager$dumpTarget$1(dumpManager5, str3, printWriter, strArr3, i, null)), new Comparator() { // from class: com.android.systemui.dump.DumpManager$dumpTarget$$inlined$sortedBy$1
                                    @Override // java.util.Comparator
                                    public final int compare(Object obj2, Object obj3) {
                                        return ComparisonsKt__ComparisonsKt.compareValues((String) ((Pair) obj2).getFirst(), (String) ((Pair) obj3).getFirst());
                                    }
                                }).iterator();
                                if (it3.hasNext()) {
                                    next = it3.next();
                                    if (it3.hasNext()) {
                                        int length = ((String) ((Pair) next).getFirst()).length();
                                        do {
                                            Object next2 = it3.next();
                                            int length2 = ((String) ((Pair) next2).getFirst()).length();
                                            if (length > length2) {
                                                next = next2;
                                                length = length2;
                                            }
                                        } while (it3.hasNext());
                                    }
                                } else {
                                    next = null;
                                }
                                Pair pair = (Pair) next;
                                if (pair != null && (function0 = (Function0) pair.getSecond()) != null) {
                                    function0.invoke();
                                }
                            }
                        }
                    } else if (parseArgs.listOnly) {
                        printWriter.println("Dumpables:");
                        this.dumpManager.listDumpables(printWriter);
                        printWriter.println();
                        printWriter.println("Buffers:");
                        this.dumpManager.listBuffers(printWriter);
                    } else {
                        printWriter.println("Nothing to dump :(");
                    }
                }
            } else {
                dumpNormal(printWriter, parseArgs);
                this.dumpManager.dumpNsDumpables(printWriter, parseArgs.rawArgs);
            }
            printWriter.println();
            printWriter.println("Dump took " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms");
            Trace.endSection();
        } catch (ArgParseException e) {
            printWriter.println(e.getMessage());
        }
    }

    public final void dumpConfig(PrintWriter printWriter) {
        printWriter.println("SystemUiServiceComponents configuration:");
        printWriter.print("vendor component: ");
        Context context = this.context;
        printWriter.println(context.getResources().getString(R.string.config_systemUIVendorServiceComponent));
        Set keySet = this.startables.keySet();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(keySet, 10));
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            arrayList.add(((Class) it.next()).getSimpleName());
        }
        ArrayList arrayList2 = new ArrayList(arrayList);
        arrayList2.add(context.getResources().getString(R.string.config_systemUIVendorServiceComponent));
        dumpServiceList(printWriter, "global", (String[]) arrayList2.toArray(new String[0]));
        dumpServiceList(printWriter, "per-user", context.getResources().getStringArray(R.array.config_systemUIServiceComponentsPerUser));
    }

    public final void dumpCritical(PrintWriter printWriter, ParsedArgs parsedArgs) {
        DumpManager dumpManager = this.dumpManager;
        String[] strArr = parsedArgs.rawArgs;
        synchronized (dumpManager) {
            for (RegisteredDumpable registeredDumpable : ((TreeMap) dumpManager.dumpables).values()) {
                if (registeredDumpable.priority == DumpPriority.CRITICAL) {
                    DumpManager.dumpDumpable(registeredDumpable, printWriter, strArr);
                }
            }
        }
        dumpConfig(printWriter);
    }

    public final void dumpNormal(final PrintWriter printWriter, ParsedArgs parsedArgs) {
        DumpManager dumpManager = this.dumpManager;
        String[] strArr = parsedArgs.rawArgs;
        int i = parsedArgs.tailLength;
        synchronized (dumpManager) {
            for (RegisteredDumpable registeredDumpable : ((TreeMap) dumpManager.dumpables).values()) {
                if (registeredDumpable.priority == DumpPriority.NORMAL) {
                    DumpManager.dumpDumpable(registeredDumpable, printWriter, strArr);
                }
            }
            Iterator it = ((TreeMap) dumpManager.buffers).values().iterator();
            while (it.hasNext()) {
                DumpManager.dumpBuffer((RegisteredDumpable) it.next(), printWriter, i);
            }
        }
        LogBufferEulogizer logBufferEulogizer = this.logBufferEulogizer;
        Path path = logBufferEulogizer.logPath;
        try {
            long millisSinceLastWrite = logBufferEulogizer.getMillisSinceLastWrite(path);
            if (millisSinceLastWrite > logBufferEulogizer.maxLogAgeToDump) {
                Log.i("BufferEulogizer", "Not eulogizing buffers; they are " + TimeUnit.HOURS.convert(millisSinceLastWrite, TimeUnit.MILLISECONDS) + " hours old");
                return;
            }
            logBufferEulogizer.files.getClass();
            Stream<String> lines = Files.lines(path);
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
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    AutoCloseableKt.closeFinally(lines, th);
                    throw th2;
                }
            }
        } catch (IOException unused) {
        } catch (UncheckedIOException e) {
            Log.e("BufferEulogizer", "UncheckedIOException while dumping the core", e);
        }
    }
}
