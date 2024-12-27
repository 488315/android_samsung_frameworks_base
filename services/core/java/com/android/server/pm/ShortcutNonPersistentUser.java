package com.android.server.pm;

import android.util.ArrayMap;
import android.util.ArraySet;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ShortcutNonPersistentUser {
    public final int mUserId;
    public final ArrayMap mHostPackages = new ArrayMap();
    public final ArraySet mHostPackageSet = new ArraySet();

    public ShortcutNonPersistentUser(int i) {
        this.mUserId = i;
    }

    public final void dump(PrintWriter printWriter, ShortcutService.DumpFilter dumpFilter) {
        if (!dumpFilter.mDumpDetails || this.mHostPackages.size() <= 0) {
            return;
        }
        printWriter.print("  ");
        printWriter.print("Non-persistent: user ID:");
        printWriter.println(this.mUserId);
        printWriter.print("  ");
        printWriter.println("  Host packages:");
        for (int i = 0; i < this.mHostPackages.size(); i++) {
            printWriter.print("  ");
            printWriter.print("    ");
            printWriter.print((String) this.mHostPackages.keyAt(i));
            printWriter.print(": ");
            printWriter.println((String) this.mHostPackages.valueAt(i));
        }
        printWriter.println();
    }
}
