package com.android.systemui.statusbar.notification.icon.ui.viewbinder;

import android.util.IndentingPrintWriter;
import com.android.systemui.CoreStartable;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import kotlin.collections.EmptyIterator;
import kotlin.collections.EmptyList;

/* loaded from: classes3.dex */
public final class StatusBarIconViewBindingFailureTracker implements CoreStartable {
    public final Collection aodFailures;
    public final EmptyList shelfFailures;
    public final Collection statusBarFailures;

    public StatusBarIconViewBindingFailureTracker() {
        EmptyList emptyList = EmptyList.INSTANCE;
        this.aodFailures = emptyList;
        this.statusBarFailures = emptyList;
        this.shelfFailures = emptyList;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        Collection collection = this.aodFailures;
        indentingPrintWriterAsIndenting.append("AOD Icon binding failures:").append((CharSequence) ": ").println(collection.size());
        indentingPrintWriterAsIndenting.increaseIndent();
        try {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                indentingPrintWriterAsIndenting.println(it.next());
            }
            indentingPrintWriterAsIndenting.decreaseIndent();
            Collection collection2 = this.statusBarFailures;
            indentingPrintWriterAsIndenting.append("Status Bar Icon binding failures:").append((CharSequence) ": ").println(collection2.size());
            indentingPrintWriterAsIndenting.increaseIndent();
            try {
                Iterator it2 = collection2.iterator();
                while (it2.hasNext()) {
                    indentingPrintWriterAsIndenting.println(it2.next());
                }
                indentingPrintWriterAsIndenting.decreaseIndent();
                EmptyList emptyList = this.shelfFailures;
                PrintWriter printWriterAppend = indentingPrintWriterAsIndenting.append("Shelf Icon binding failures:").append((CharSequence) ": ");
                emptyList.getClass();
                printWriterAppend.println(0);
                indentingPrintWriterAsIndenting.increaseIndent();
                try {
                    emptyList.getClass();
                    EmptyIterator.INSTANCE.getClass();
                } finally {
                }
            } finally {
            }
        } finally {
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
