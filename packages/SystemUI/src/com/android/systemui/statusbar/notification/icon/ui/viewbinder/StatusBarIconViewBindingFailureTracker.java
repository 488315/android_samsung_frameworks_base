package com.android.systemui.statusbar.notification.icon.ui.viewbinder;

import android.util.IndentingPrintWriter;
import com.android.systemui.CoreStartable;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import kotlin.collections.EmptyIterator;
import kotlin.collections.EmptyList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        Collection collection = this.aodFailures;
        asIndenting.append("AOD Icon binding failures:").append((CharSequence) ": ").println(collection.size());
        asIndenting.increaseIndent();
        try {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                asIndenting.println(it.next());
            }
            asIndenting.decreaseIndent();
            Collection collection2 = this.statusBarFailures;
            asIndenting.append("Status Bar Icon binding failures:").append((CharSequence) ": ").println(collection2.size());
            asIndenting.increaseIndent();
            try {
                Iterator it2 = collection2.iterator();
                while (it2.hasNext()) {
                    asIndenting.println(it2.next());
                }
                asIndenting.decreaseIndent();
                EmptyList emptyList = this.shelfFailures;
                PrintWriter append = asIndenting.append("Shelf Icon binding failures:").append((CharSequence) ": ");
                emptyList.getClass();
                append.println(0);
                asIndenting.increaseIndent();
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
