package com.android.systemui.statusbar.notification;

import android.icu.text.SimpleDateFormat;
import android.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class ColorUpdateLogger implements Dumpable {
    public static final Companion Companion = new Companion(null);
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);
    public final List frames = new ArrayList();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class Frame {
        public final List events;
        public final long startTime;
        public final SortedSet triggers;

        public Frame(Event event) {
            this.startTime = event.time;
            this.events = CollectionsKt__CollectionsKt.mutableListOf(event);
            TreeSet treeSet = new TreeSet();
            treeSet.add(event.type);
            this.triggers = treeSet;
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("Frame");
            indentingPrintWriter.increaseIndent();
            try {
                Companion companion = ColorUpdateLogger.Companion;
                long j = this.startTime;
                companion.getClass();
                indentingPrintWriter.println("startTime: " + ColorUpdateLogger.dateFormat.format(Long.valueOf(j)));
                TreeSet treeSet = (TreeSet) this.triggers;
                indentingPrintWriter.append("triggers").append((CharSequence) ": ").println(treeSet.size());
                indentingPrintWriter.increaseIndent();
                Iterator it = treeSet.iterator();
                while (it.hasNext()) {
                    indentingPrintWriter.println(it.next());
                }
                indentingPrintWriter.decreaseIndent();
                StringBuilder sb = new StringBuilder();
                sb.append("trimmedEvents: ");
                int i = 0;
                sb.append(0);
                indentingPrintWriter.println(sb.toString());
                ArrayList arrayList = (ArrayList) this.events;
                indentingPrintWriter.append("events").append((CharSequence) ": ").println(arrayList.size());
                indentingPrintWriter.increaseIndent();
                int size = arrayList.size();
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((Event) obj).dump(indentingPrintWriter);
                }
                indentingPrintWriter.decreaseIndent();
            } catch (Throwable th) {
                throw th;
            } finally {
                indentingPrintWriter.decreaseIndent();
            }
        }
    }

    public ColorUpdateLogger(FeatureFlagsClassic featureFlagsClassic, DumpManager dumpManager) {
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        indentingPrintWriterAsIndenting.println("enabled: false");
        ArrayList arrayList = (ArrayList) this.frames;
        indentingPrintWriterAsIndenting.append("frames").append((CharSequence) ": ").println(arrayList.size());
        indentingPrintWriterAsIndenting.increaseIndent();
        try {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((Frame) obj).dump(indentingPrintWriterAsIndenting);
            }
        } finally {
            indentingPrintWriterAsIndenting.decreaseIndent();
        }
    }

    public final class Event {
        public final String extraValue;
        public final String notificationKey;
        public final long time;
        public final String type;

        public Event(String str, String str2, String str3) {
            this.type = str;
            this.extraValue = str2;
            this.notificationKey = str3;
            this.time = System.currentTimeMillis();
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            ColorUpdateLogger.Companion.getClass();
            indentingPrintWriter.append(ColorUpdateLogger.dateFormat.format(Long.valueOf(this.time))).append((CharSequence) ": ").append((CharSequence) this.type);
            String str = this.extraValue;
            if (str != null) {
                indentingPrintWriter.append(" ").append((CharSequence) str);
            }
            String str2 = this.notificationKey;
            if (str2 != null) {
                indentingPrintWriter.append(" ---- ").append((CharSequence) str2.replace("\n", ""));
            }
            indentingPrintWriter.println();
        }

        public /* synthetic */ Event(String str, String str2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3);
        }
    }
}
