package com.android.internal.util.dump;

import android.util.ArrayMap;
import android.util.Dumpable;
import android.util.DumpableContainer;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.internal.accessibility.common.ShortcutConstants;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public final class DumpableContainerImpl implements DumpableContainer {
    private static final boolean DEBUG = false;
    private static final String TAG = "DumpableContainerImpl";
    private final ArrayMap<String, Dumpable> mDumpables = new ArrayMap<>();

    @Override // android.util.DumpableContainer
    public boolean addDumpable(final Dumpable dumpable) {
        Objects.requireNonNull(dumpable, "dumpable");
        String dumpableName = dumpable.getDumpableName();
        Objects.requireNonNull(dumpableName, (Supplier<String>) new Supplier() { // from class: com.android.internal.util.dump.DumpableContainerImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return DumpableContainerImpl.lambda$addDumpable$0(Dumpable.this);
            }
        });
        if (this.mDumpables.containsKey(dumpableName)) {
            return false;
        }
        this.mDumpables.put(dumpableName, dumpable);
        return true;
    }

    static /* synthetic */ String lambda$addDumpable$0(Dumpable dumpable) {
        return "name of" + dumpable;
    }

    @Override // android.util.DumpableContainer
    public boolean removeDumpable(Dumpable dumpable) {
        Dumpable dumpable2;
        Objects.requireNonNull(dumpable, "dumpable");
        String dumpableName = dumpable.getDumpableName();
        if (dumpableName == null || (dumpable2 = this.mDumpables.get(dumpableName)) == null) {
            return false;
        }
        if (dumpable2 != dumpable) {
            Log.w(TAG, "removeDumpable(): passed dumpable (" + dumpable + ") named " + dumpableName + ", but internal dumpable with that name is " + dumpable2);
            return false;
        }
        this.mDumpables.remove(dumpableName);
        return true;
    }

    private int dumpNumberDumpables(IndentingPrintWriter indentingPrintWriter) {
        int size = this.mDumpables.size();
        if (size == 0) {
            indentingPrintWriter.print("No dumpables");
            return size;
        }
        indentingPrintWriter.print(size);
        indentingPrintWriter.print(" dumpables");
        return size;
    }

    public void listDumpables(String str, PrintWriter printWriter) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, str, str);
        int dumpNumberDumpables = dumpNumberDumpables(indentingPrintWriter);
        if (dumpNumberDumpables == 0) {
            indentingPrintWriter.println();
            return;
        }
        indentingPrintWriter.print(": ");
        for (int i = 0; i < dumpNumberDumpables; i++) {
            indentingPrintWriter.print(this.mDumpables.keyAt(i));
            if (i < dumpNumberDumpables - 1) {
                indentingPrintWriter.print(' ');
            }
        }
        indentingPrintWriter.println();
    }

    public void dumpAllDumpables(String str, PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, str, str);
        int dumpNumberDumpables = dumpNumberDumpables(indentingPrintWriter);
        if (dumpNumberDumpables == 0) {
            indentingPrintWriter.println();
            return;
        }
        indentingPrintWriter.println(":");
        for (int i = 0; i < dumpNumberDumpables; i++) {
            String keyAt = this.mDumpables.keyAt(i);
            indentingPrintWriter.print('#');
            indentingPrintWriter.print(i);
            indentingPrintWriter.print(": ");
            indentingPrintWriter.println(keyAt);
            indentAndDump(indentingPrintWriter, this.mDumpables.valueAt(i), strArr);
        }
    }

    private void indentAndDump(IndentingPrintWriter indentingPrintWriter, Dumpable dumpable, String[] strArr) {
        indentingPrintWriter.increaseIndent();
        try {
            dumpable.dump(indentingPrintWriter, strArr);
        } finally {
            indentingPrintWriter.decreaseIndent();
        }
    }

    public void dumpOneDumpable(String str, PrintWriter printWriter, String str2, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, str, str);
        Dumpable dumpable = this.mDumpables.get(str2);
        if (dumpable == null) {
            indentingPrintWriter.print("No ");
            indentingPrintWriter.println(str2);
        } else {
            indentingPrintWriter.print(str2);
            indentingPrintWriter.println(ShortcutConstants.SERVICES_SEPARATOR);
            indentAndDump(indentingPrintWriter, dumpable, strArr);
        }
    }
}
