package com.android.server.pm;

import android.util.ArrayMap;
import android.util.ArraySet;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class ShortcutNonPersistentUser {
  public final ShortcutService mService;
  public final int mUserId;
  public final ArrayMap mHostPackages = new ArrayMap();
  public final ArraySet mHostPackageSet = new ArraySet();

  public ShortcutNonPersistentUser(ShortcutService shortcutService, int i) {
    this.mService = shortcutService;
    this.mUserId = i;
  }

  public int getUserId() {
    return this.mUserId;
  }

  public void setShortcutHostPackage(String str, String str2) {
    if (str2 != null) {
      this.mHostPackages.put(str, str2);
    } else {
      this.mHostPackages.remove(str);
    }
    this.mHostPackageSet.clear();
    for (int i = 0; i < this.mHostPackages.size(); i++) {
      this.mHostPackageSet.add((String) this.mHostPackages.valueAt(i));
    }
  }

  public boolean hasHostPackage(String str) {
    return this.mHostPackageSet.contains(str);
  }

  public void dump(PrintWriter printWriter, String str, ShortcutService.DumpFilter dumpFilter) {
    if (!dumpFilter.shouldDumpDetails() || this.mHostPackages.size() <= 0) {
      return;
    }
    printWriter.print(str);
    printWriter.print("Non-persistent: user ID:");
    printWriter.println(this.mUserId);
    printWriter.print(str);
    printWriter.println("  Host packages:");
    for (int i = 0; i < this.mHostPackages.size(); i++) {
      printWriter.print(str);
      printWriter.print("    ");
      printWriter.print((String) this.mHostPackages.keyAt(i));
      printWriter.print(": ");
      printWriter.println((String) this.mHostPackages.valueAt(i));
    }
    printWriter.println();
  }
}
