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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ColorUpdateLogger implements Dumpable {
    public static final Companion Companion = new Companion(null);
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);
    public final List frames = new ArrayList();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

        /* JADX WARN: Code restructure failed: missing block: B:26:0x0050, code lost:
        
            r5 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x00a7, code lost:
        
            throw r5;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x009e, code lost:
        
            r5 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x00ab, code lost:
        
            throw r5;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void dump(android.util.IndentingPrintWriter r6) {
            /*
                r5 = this;
                java.lang.String r0 = ": "
                java.lang.String r1 = "startTime: "
                java.lang.String r2 = "Frame"
                r6.println(r2)
                r6.increaseIndent()
                com.android.systemui.statusbar.notification.ColorUpdateLogger$Companion r2 = com.android.systemui.statusbar.notification.ColorUpdateLogger.Companion     // Catch: java.lang.Throwable -> L9e
                long r3 = r5.startTime     // Catch: java.lang.Throwable -> L9e
                r2.getClass()     // Catch: java.lang.Throwable -> L9e
                android.icu.text.SimpleDateFormat r2 = com.android.systemui.statusbar.notification.ColorUpdateLogger.dateFormat     // Catch: java.lang.Throwable -> L9e
                java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch: java.lang.Throwable -> L9e
                java.lang.String r2 = r2.format(r3)     // Catch: java.lang.Throwable -> L9e
                java.lang.String r1 = r1.concat(r2)     // Catch: java.lang.Throwable -> L9e
                r6.println(r1)     // Catch: java.lang.Throwable -> L9e
                java.lang.String r1 = "triggers"
                java.util.SortedSet r2 = r5.triggers     // Catch: java.lang.Throwable -> L9e
                java.io.PrintWriter r1 = r6.append(r1)     // Catch: java.lang.Throwable -> L9e
                java.io.PrintWriter r1 = r1.append(r0)     // Catch: java.lang.Throwable -> L9e
                java.util.TreeSet r2 = (java.util.TreeSet) r2     // Catch: java.lang.Throwable -> L9e
                int r3 = r2.size()     // Catch: java.lang.Throwable -> L9e
                r1.println(r3)     // Catch: java.lang.Throwable -> L9e
                r6.increaseIndent()     // Catch: java.lang.Throwable -> L9e
                java.util.Iterator r1 = r2.iterator()     // Catch: java.lang.Throwable -> L50
            L42:
                boolean r2 = r1.hasNext()     // Catch: java.lang.Throwable -> L50
                if (r2 == 0) goto L52
                java.lang.Object r2 = r1.next()     // Catch: java.lang.Throwable -> L50
                r6.println(r2)     // Catch: java.lang.Throwable -> L50
                goto L42
            L50:
                r5 = move-exception
                goto La4
            L52:
                r6.decreaseIndent()     // Catch: java.lang.Throwable -> L9e
                java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L9e
                r1.<init>()     // Catch: java.lang.Throwable -> L9e
                java.lang.String r2 = "trimmedEvents: "
                r1.append(r2)     // Catch: java.lang.Throwable -> L9e
                r2 = 0
                r1.append(r2)     // Catch: java.lang.Throwable -> L9e
                java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L9e
                r6.println(r1)     // Catch: java.lang.Throwable -> L9e
                java.lang.String r1 = "events"
                java.util.List r5 = r5.events     // Catch: java.lang.Throwable -> L9e
                java.io.PrintWriter r1 = r6.append(r1)     // Catch: java.lang.Throwable -> L9e
                java.io.PrintWriter r0 = r1.append(r0)     // Catch: java.lang.Throwable -> L9e
                int r1 = r5.size()     // Catch: java.lang.Throwable -> L9e
                r0.println(r1)     // Catch: java.lang.Throwable -> L9e
                r6.increaseIndent()     // Catch: java.lang.Throwable -> L9e
                java.util.Iterator r5 = r5.iterator()     // Catch: java.lang.Throwable -> L95
            L85:
                boolean r0 = r5.hasNext()     // Catch: java.lang.Throwable -> L95
                if (r0 == 0) goto L97
                java.lang.Object r0 = r5.next()     // Catch: java.lang.Throwable -> L95
                com.android.systemui.statusbar.notification.ColorUpdateLogger$Event r0 = (com.android.systemui.statusbar.notification.ColorUpdateLogger.Event) r0     // Catch: java.lang.Throwable -> L95
                r0.dump(r6)     // Catch: java.lang.Throwable -> L95
                goto L85
            L95:
                r5 = move-exception
                goto La0
            L97:
                r6.decreaseIndent()     // Catch: java.lang.Throwable -> L9e
                r6.decreaseIndent()
                return
            L9e:
                r5 = move-exception
                goto La8
            La0:
                r6.decreaseIndent()     // Catch: java.lang.Throwable -> L9e
                throw r5     // Catch: java.lang.Throwable -> L9e
            La4:
                r6.decreaseIndent()     // Catch: java.lang.Throwable -> L9e
                throw r5     // Catch: java.lang.Throwable -> L9e
            La8:
                r6.decreaseIndent()
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.ColorUpdateLogger.Frame.dump(android.util.IndentingPrintWriter):void");
        }
    }

    public ColorUpdateLogger(FeatureFlagsClassic featureFlagsClassic, DumpManager dumpManager) {
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("enabled: false");
        List list = this.frames;
        asIndenting.append("frames").append((CharSequence) ": ").println(list.size());
        asIndenting.increaseIndent();
        try {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ((Frame) it.next()).dump(asIndenting);
            }
        } finally {
            asIndenting.decreaseIndent();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
