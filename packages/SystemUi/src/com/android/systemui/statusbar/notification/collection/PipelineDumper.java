package com.android.systemui.statusbar.notification.collection;

import android.util.IndentingPrintWriter;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Unit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        String str;
        String bareClassName;
        boolean z = obj == null ? true : obj instanceof String ? true : obj instanceof Integer;
        IndentingPrintWriter indentingPrintWriter = this.ipw;
        if (z) {
            indentingPrintWriter.println(obj);
            Unit unit = Unit.INSTANCE;
            return;
        }
        if (obj instanceof Collection) {
            Collection collection = (Collection) obj;
            indentingPrintWriter.println(collection.size());
            indentingPrintWriter.increaseIndent();
            try {
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    dump(it.next());
                }
                indentingPrintWriter.decreaseIndent();
                Unit unit2 = Unit.INSTANCE;
                return;
            } finally {
            }
        }
        if (obj instanceof NotifLifetimeExtender) {
            str = ((NotifLifetimeExtender) obj).getName();
        } else if (obj instanceof BubbleCoordinator.C27992) {
            ((BubbleCoordinator.C27992) obj).getClass();
            str = "BubbleCoordinator";
        } else {
            str = obj instanceof Pluggable ? ((Pluggable) obj).mName : null;
        }
        if (str == null || (bareClassName = MotionLayout$$ExternalSyntheticOutline0.m22m("\"", str, "\" (", PipelineDumperKt.getBareClassName(obj), ")")) == null) {
            bareClassName = PipelineDumperKt.getBareClassName(obj);
        }
        indentingPrintWriter.println(bareClassName);
        PipelineDumpable pipelineDumpable = obj instanceof PipelineDumpable ? (PipelineDumpable) obj : null;
        if (pipelineDumpable != null) {
            indentingPrintWriter.increaseIndent();
            try {
                pipelineDumpable.dumpPipeline(this);
                indentingPrintWriter.decreaseIndent();
                Unit unit3 = Unit.INSTANCE;
            } finally {
            }
        }
    }
}
