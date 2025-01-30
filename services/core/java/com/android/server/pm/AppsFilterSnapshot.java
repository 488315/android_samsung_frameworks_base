package com.android.server.pm;

import android.util.ArrayMap;
import android.util.SparseArray;
import com.android.internal.util.function.QuadFunction;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.snapshot.PackageDataSnapshot;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public interface AppsFilterSnapshot {
  boolean canQueryPackage(AndroidPackage androidPackage, String str);

  void dumpQueries(
      PrintWriter printWriter,
      Integer num,
      DumpState dumpState,
      int[] iArr,
      QuadFunction quadFunction);

  SparseArray getVisibilityAllowList(
      PackageDataSnapshot packageDataSnapshot,
      PackageStateInternal packageStateInternal,
      int[] iArr,
      ArrayMap arrayMap);

  boolean shouldFilterApplication(
      PackageDataSnapshot packageDataSnapshot,
      int i,
      Object obj,
      PackageStateInternal packageStateInternal,
      int i2);
}
