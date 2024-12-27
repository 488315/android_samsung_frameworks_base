package com.android.systemui;

import java.io.PrintWriter;

public interface Dumpable {
    void dump(PrintWriter printWriter, String[] strArr);
}
