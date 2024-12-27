package com.android.systemui.statusbar.notification.icon.ui.viewbinder;

import com.android.systemui.CoreStartable;
import java.io.PrintWriter;
import kotlin.collections.EmptyList;

public final class StatusBarIconViewBindingFailureTracker implements CoreStartable {
    public StatusBarIconViewBindingFailureTracker() {
        EmptyList emptyList = EmptyList.INSTANCE;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
    }
}
