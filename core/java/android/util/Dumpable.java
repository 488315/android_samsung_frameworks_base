package android.util;

import java.io.PrintWriter;

public interface Dumpable {
    void dump(PrintWriter printWriter, String[] strArr);

    default String getDumpableName() {
        return getClass().getName();
    }
}
