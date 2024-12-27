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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationDismissibilityProviderImpl implements NotificationDismissibilityProvider, Dumpable {
    public volatile Set nonDismissableEntryKeys;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public NotificationDismissibilityProviderImpl(DumpManager dumpManager) {
        dumpManager.registerNormalDumpable("NotificationDismissibilityProvider", this);
        this.nonDismissableEntryKeys = EmptySet.INSTANCE;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        Set set = this.nonDismissableEntryKeys;
        asIndenting.append("non-dismissible entries").append((CharSequence) ": ").println(set.size());
        asIndenting.increaseIndent();
        try {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                asIndenting.println(it.next());
            }
        } finally {
            asIndenting.decreaseIndent();
        }
    }

    public static /* synthetic */ void getNonDismissableEntryKeys$annotations() {
    }
}
