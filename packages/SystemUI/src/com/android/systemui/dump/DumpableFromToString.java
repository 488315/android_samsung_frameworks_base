package com.android.systemui.dump;

import com.android.systemui.Dumpable;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
