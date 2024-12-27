package com.android.server.timezonedetector;

import android.util.IndentingPrintWriter;

public interface Dumpable {
    void dump(IndentingPrintWriter indentingPrintWriter, String[] strArr);
}
