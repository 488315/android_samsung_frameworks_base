package com.android.systemui.statusbar.notification.collection;

import android.util.IndentingPrintWriter;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifDismissInterceptor;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PipelineDumper {
    public final IndentingPrintWriter ipw;

    public PipelineDumper(PrintWriter printWriter) {
        this.ipw = DumpUtilsKt.asIndenting(printWriter);
    }

    public final void dump(Object obj, String str) {
        this.ipw.print(str.concat(": "));
        dump(obj);
    }

    public final void println(Object obj) {
        this.ipw.println(obj);
    }

    public final void dump(Object obj) {
        String name;
        String bareClassName;
        IndentingPrintWriter indentingPrintWriter;
        if (obj != null && !(obj instanceof String) && !(obj instanceof Integer)) {
            if (obj instanceof Collection) {
                Collection collection = (Collection) obj;
                this.ipw.println(collection.size());
                indentingPrintWriter = this.ipw;
                indentingPrintWriter.increaseIndent();
                try {
                    Iterator it = collection.iterator();
                    while (it.hasNext()) {
                        dump(it.next());
                    }
                    indentingPrintWriter.decreaseIndent();
                    Unit unit = Unit.INSTANCE;
                    return;
                } finally {
                }
            }
            IndentingPrintWriter indentingPrintWriter2 = this.ipw;
            if (obj instanceof NotifLifetimeExtender) {
                name = ((NotifLifetimeExtender) obj).getName();
            } else if (obj instanceof NotifDismissInterceptor) {
                name = ((NotifDismissInterceptor) obj).getName();
            } else {
                name = obj instanceof Pluggable ? ((Pluggable) obj).getName() : null;
            }
            if (name == null || (bareClassName = MotionLayout$$ExternalSyntheticOutline0.m("\"", name, "\" (", PipelineDumperKt.getBareClassName(obj), ")")) == null) {
                bareClassName = PipelineDumperKt.getBareClassName(obj);
            }
            indentingPrintWriter2.println(bareClassName);
            PipelineDumpable pipelineDumpable = obj instanceof PipelineDumpable ? (PipelineDumpable) obj : null;
            if (pipelineDumpable != null) {
                indentingPrintWriter = this.ipw;
                indentingPrintWriter.increaseIndent();
                try {
                    pipelineDumpable.dumpPipeline(this);
                    indentingPrintWriter.decreaseIndent();
                    Unit unit2 = Unit.INSTANCE;
                    return;
                } finally {
                }
            }
            return;
        }
        this.ipw.println(obj);
        Unit unit3 = Unit.INSTANCE;
    }
}
