package com.android.systemui.dump;

import android.util.ArrayMap;
import com.android.systemui.Dumpable;
import com.android.systemui.ProtoDumpable;
import com.android.systemui.log.LogBuffer;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DumpManager {
    public final Map dumpables = new TreeMap();
    public final Map buffers = new TreeMap();
    public final Map nsDumpables = new ArrayMap();

    public static final Object access$findBestTargetMatch(DumpManager dumpManager, Map map, final String str) {
        Object next;
        dumpManager.getClass();
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(map.entrySet()), new Function1() { // from class: com.android.systemui.dump.DumpManager$findBestTargetMatch$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((String) ((Map.Entry) obj).getKey()).endsWith(str));
            }
        }));
        if (filteringSequence$iterator$1.hasNext()) {
            next = filteringSequence$iterator$1.next();
            if (filteringSequence$iterator$1.hasNext()) {
                int length = ((String) ((Map.Entry) next).getKey()).length();
                do {
                    Object next2 = filteringSequence$iterator$1.next();
                    int length2 = ((String) ((Map.Entry) next2).getKey()).length();
                    if (length > length2) {
                        next = next2;
                        length = length2;
                    }
                } while (filteringSequence$iterator$1.hasNext());
            }
        } else {
            next = null;
        }
        Map.Entry entry = (Map.Entry) next;
        if (entry != null) {
            return entry.getValue();
        }
        return null;
    }

    public static void dumpBuffer(RegisteredDumpable registeredDumpable, PrintWriter printWriter, int i) {
        printWriter.println();
        printWriter.println();
        printWriter.println("BUFFER " + registeredDumpable.name + ":");
        printWriter.println("============================================================================");
        ((LogBuffer) registeredDumpable.dumpable).dump(printWriter, i);
    }

    public static void dumpDumpable(RegisteredDumpable registeredDumpable, PrintWriter printWriter, String[] strArr) {
        printWriter.println();
        printWriter.println(registeredDumpable.name + ":");
        printWriter.println("----------------------------------------------------------------------------");
        ((Dumpable) registeredDumpable.dumpable).dump(printWriter, strArr);
    }

    public static ProtoDumpable findBestProtoTargetMatch(final String str, Map map) {
        Object next;
        RegisteredDumpable registeredDumpable;
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(((TreeMap) map).entrySet()), new Function1() { // from class: com.android.systemui.dump.DumpManager$findBestProtoTargetMatch$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((String) ((Map.Entry) obj).getKey()).endsWith(str));
            }
        }), new Function1() { // from class: com.android.systemui.dump.DumpManager$findBestProtoTargetMatch$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((RegisteredDumpable) ((Map.Entry) obj).getValue()).dumpable instanceof ProtoDumpable);
            }
        }));
        if (filteringSequence$iterator$1.hasNext()) {
            next = filteringSequence$iterator$1.next();
            if (filteringSequence$iterator$1.hasNext()) {
                int length = ((String) ((Map.Entry) next).getKey()).length();
                do {
                    Object next2 = filteringSequence$iterator$1.next();
                    int length2 = ((String) ((Map.Entry) next2).getKey()).length();
                    if (length > length2) {
                        next = next2;
                        length = length2;
                    }
                } while (filteringSequence$iterator$1.hasNext());
            }
        } else {
            next = null;
        }
        Map.Entry entry = (Map.Entry) next;
        Dumpable dumpable = (entry == null || (registeredDumpable = (RegisteredDumpable) entry.getValue()) == null) ? null : (Dumpable) registeredDumpable.dumpable;
        if (dumpable instanceof ProtoDumpable) {
            return (ProtoDumpable) dumpable;
        }
        return null;
    }

    public static /* synthetic */ void registerDumpable$default(DumpManager dumpManager, String str, Dumpable dumpable) {
        dumpManager.registerDumpable(str, dumpable, DumpPriority.CRITICAL);
    }

    public final boolean canAssignToNameLocked(Object obj, String str) {
        Object obj2;
        RegisteredDumpable registeredDumpable = (RegisteredDumpable) ((TreeMap) this.dumpables).get(str);
        if (registeredDumpable == null || (obj2 = (Dumpable) registeredDumpable.dumpable) == null) {
            RegisteredDumpable registeredDumpable2 = (RegisteredDumpable) ((TreeMap) this.buffers).get(str);
            if (registeredDumpable2 != null) {
                obj2 = registeredDumpable2.dumpable;
            } else {
                RegisteredDumpable registeredDumpable3 = (RegisteredDumpable) ((ArrayMap) this.nsDumpables).get(str);
                obj2 = registeredDumpable3 != null ? (Dumpable) registeredDumpable3.dumpable : null;
            }
        }
        return obj2 == null || Intrinsics.areEqual(obj, obj2);
    }

    public final synchronized void dumpBuffers(PrintWriter printWriter, int i) {
        Iterator it = ((TreeMap) this.buffers).values().iterator();
        while (it.hasNext()) {
            dumpBuffer((RegisteredDumpable) it.next(), printWriter, i);
        }
    }

    public final synchronized void dumpNsDumpables(PrintWriter printWriter, String[] strArr) {
        Iterator it = ((ArrayMap) this.nsDumpables).values().iterator();
        while (it.hasNext()) {
            dumpDumpable((RegisteredDumpable) it.next(), printWriter, strArr);
        }
    }

    public final synchronized void listBuffers(PrintWriter printWriter) {
        Iterator it = ((TreeMap) this.buffers).values().iterator();
        while (it.hasNext()) {
            printWriter.println(((RegisteredDumpable) it.next()).name);
        }
    }

    public final synchronized void listDumpables(PrintWriter printWriter) {
        Iterator it = ((TreeMap) this.dumpables).values().iterator();
        while (it.hasNext()) {
            printWriter.println(((RegisteredDumpable) it.next()).name);
        }
        Iterator it2 = ((ArrayMap) this.nsDumpables).values().iterator();
        while (it2.hasNext()) {
            printWriter.println(((RegisteredDumpable) it2.next()).name);
        }
    }

    public final synchronized void registerBuffer(LogBuffer logBuffer, String str) {
        if (!canAssignToNameLocked(logBuffer, str)) {
            throw new IllegalArgumentException("'" + str + "' is already registered");
        }
        ((TreeMap) this.buffers).put(str, new RegisteredDumpable(str, logBuffer, DumpPriority.NORMAL));
    }

    public final void registerCriticalDumpable(String str, Dumpable dumpable) {
        registerDumpable(str, dumpable, DumpPriority.CRITICAL);
    }

    public final void registerDumpable(String str, Dumpable dumpable) {
        registerDumpable$default(this, str, dumpable);
    }

    public final void registerNormalDumpable(Dumpable dumpable) {
        registerNormalDumpable(dumpable.getClass().getSimpleName(), dumpable);
    }

    public final synchronized void registerNsDumpable(String str, Dumpable dumpable) {
        if (!canAssignToNameLocked(dumpable, str)) {
            throw new IllegalArgumentException("'" + str + "' is already registered");
        }
        ((ArrayMap) this.nsDumpables).put(str, new RegisteredDumpable(str, dumpable, DumpPriority.NORMAL));
    }

    public final synchronized void unregisterDumpable(String str) {
        ((TreeMap) this.dumpables).remove(str);
    }

    public final synchronized void registerDumpable(String str, Dumpable dumpable, DumpPriority dumpPriority) {
        if (!canAssignToNameLocked(dumpable, str)) {
            throw new IllegalArgumentException("'" + str + "' is already registered");
        }
        ((TreeMap) this.dumpables).put(str, new RegisteredDumpable(str, dumpable, dumpPriority));
    }

    public final void registerNormalDumpable(String str, Dumpable dumpable) {
        registerDumpable(str, dumpable, DumpPriority.NORMAL);
    }

    public final synchronized void registerDumpable(Dumpable dumpable) {
        registerDumpable$default(this, dumpable.getClass().getSimpleName(), dumpable);
    }
}
