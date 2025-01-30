package android.util;

import java.io.PrintWriter;

/* loaded from: classes4.dex */
public interface Dumpable {
  void dump(PrintWriter printWriter, String[] strArr);

  default String getDumpableName() {
    return getClass().getName();
  }
}
