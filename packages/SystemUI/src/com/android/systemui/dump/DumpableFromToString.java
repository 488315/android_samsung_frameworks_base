package com.android.systemui.dump;

import com.android.systemui.Dumpable;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public final class DumpableFromToString implements Dumpable {
    public final Object instance;

    public DumpableFromToString(Object obj) {
        this.instance = obj;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(String.valueOf(this.instance));
    }
}
