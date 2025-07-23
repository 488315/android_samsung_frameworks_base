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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationDismissibilityProviderImpl implements NotificationDismissibilityProvider, Dumpable {
    public volatile Set nonDismissableEntryKeys;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
