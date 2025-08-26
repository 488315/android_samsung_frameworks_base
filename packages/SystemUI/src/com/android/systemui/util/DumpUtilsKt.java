package com.android.systemui.util;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
public final class DumpUtilsKt {

    /* renamed from: com.android.systemui.util.DumpUtilsKt$printCollection$1, reason: invalid class name */
    public final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function2 {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(2, IndentingPrintWriter.class, "println", "println(Ljava/lang/Object;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            invoke((IndentingPrintWriter) obj, obj2);
            return Unit.INSTANCE;
        }

        public final void invoke(IndentingPrintWriter indentingPrintWriter, Object obj) {
            indentingPrintWriter.println(obj);
        }
    }

    public static final IndentingPrintWriter asIndenting(PrintWriter printWriter) {
        IndentingPrintWriter indentingPrintWriter = printWriter instanceof IndentingPrintWriter ? (IndentingPrintWriter) printWriter : null;
        return indentingPrintWriter == null ? new IndentingPrintWriter(printWriter) : indentingPrintWriter;
    }

    public static final <T extends Dumpable> void dumpCollection(IndentingPrintWriter indentingPrintWriter, String str, Collection<? extends T> collection) {
        indentingPrintWriter.append(str).append((CharSequence) ": ").println(collection.size());
        indentingPrintWriter.increaseIndent();
        try {
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                ((Dumpable) it.next()).dump(indentingPrintWriter, new String[0]);
            }
        } finally {
            indentingPrintWriter.decreaseIndent();
        }
    }

    public static final <T> void printCollection(IndentingPrintWriter indentingPrintWriter, String str, Collection<? extends T> collection, Function2 function2) {
        indentingPrintWriter.append(str).append((CharSequence) ": ").println(collection.size());
        indentingPrintWriter.increaseIndent();
        try {
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                function2.invoke(indentingPrintWriter, it.next());
            }
        } finally {
            indentingPrintWriter.decreaseIndent();
        }
    }

    public static /* synthetic */ void printCollection$default(IndentingPrintWriter indentingPrintWriter, String str, Collection collection, Function2 function2, int i, Object obj) {
        if ((i & 4) != 0) {
            function2 = AnonymousClass1.INSTANCE;
        }
        indentingPrintWriter.append(str).append((CharSequence) ": ").println(collection.size());
        indentingPrintWriter.increaseIndent();
        try {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                function2.invoke(indentingPrintWriter, it.next());
            }
        } finally {
            indentingPrintWriter.decreaseIndent();
        }
    }

    public static final void printSection(IndentingPrintWriter indentingPrintWriter, String str, Function0 function0) {
        indentingPrintWriter.append(str).println(":");
        indentingPrintWriter.increaseIndent();
        try {
            function0.invoke();
        } finally {
            indentingPrintWriter.decreaseIndent();
        }
    }

    public static final void println(IndentingPrintWriter indentingPrintWriter, String str, Object obj) {
        indentingPrintWriter.append(str).append('=').println(obj);
    }

    public static final String visibilityString(int i) {
        return i != 0 ? i != 4 ? i != 8 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "unknown:") : "gone" : "invisible" : "visible";
    }

    public static final void withIncreasedIndent(IndentingPrintWriter indentingPrintWriter, Function0 function0) {
        indentingPrintWriter.increaseIndent();
        try {
            function0.invoke();
        } finally {
            indentingPrintWriter.decreaseIndent();
        }
    }

    public static final void withIncreasedIndent(IndentingPrintWriter indentingPrintWriter, Runnable runnable) {
        indentingPrintWriter.increaseIndent();
        try {
            runnable.run();
        } finally {
            indentingPrintWriter.decreaseIndent();
        }
    }

    public static final <T> void printCollection(IndentingPrintWriter indentingPrintWriter, String str, Collection<? extends T> collection) {
        indentingPrintWriter.append(str).append((CharSequence) ": ").println(collection.size());
        indentingPrintWriter.increaseIndent();
        try {
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                indentingPrintWriter.println(it.next());
            }
        } finally {
            indentingPrintWriter.decreaseIndent();
        }
    }
}
