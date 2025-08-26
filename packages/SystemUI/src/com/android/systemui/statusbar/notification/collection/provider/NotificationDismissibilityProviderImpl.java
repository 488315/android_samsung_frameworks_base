package com.android.systemui.statusbar.notification.collection.provider;

import android.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.EmptySet;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class NotificationDismissibilityProviderImpl implements NotificationDismissibilityProvider, Dumpable {
    public volatile Set nonDismissableEntryKeys;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public NotificationDismissibilityProviderImpl(DumpManager dumpManager) {
        dumpManager.registerNormalDumpable("NotificationDismissibilityProvider", this);
        this.nonDismissableEntryKeys = EmptySet.INSTANCE;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        Set set = this.nonDismissableEntryKeys;
        indentingPrintWriterAsIndenting.append("non-dismissible entries").append((CharSequence) ": ").println(set.size());
        indentingPrintWriterAsIndenting.increaseIndent();
        try {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                indentingPrintWriterAsIndenting.println(it.next());
            }
        } finally {
            indentingPrintWriterAsIndenting.decreaseIndent();
        }
    }

    public static /* synthetic */ void getNonDismissableEntryKeys$annotations() {
    }
}
