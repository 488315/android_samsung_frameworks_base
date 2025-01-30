package com.android.server.ibs;

import android.util.Slog;
import com.android.server.LocalManagerRegistry;
import com.android.server.art.ArtManagerLocal;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.dex.DexoptOptions;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class IntelligentBatterySaverDexoptManager {
  public ArtManagerLocal mArtManagerLocal;
  public PackageManagerLocal mPackageManagerLocal;

  public final int getDexoptFlags() {
    return 1541;
  }

  public void init() {
    try {
      this.mArtManagerLocal =
          (ArtManagerLocal) LocalManagerRegistry.getManagerOrThrow(ArtManagerLocal.class);
      this.mPackageManagerLocal =
          (PackageManagerLocal) LocalManagerRegistry.getManagerOrThrow(PackageManagerLocal.class);
    } catch (LocalManagerRegistry.ManagerNotFoundException e) {
      Slog.e(
          "IntelligentBatterySaverDexoptManager", "failed to get local manager " + e.getMessage());
    }
  }

  public List dexoptPackages(List list) {
    ArrayList arrayList = new ArrayList();
    int dexoptFlags = getDexoptFlags();
    try {
      PackageManagerLocal.FilteredSnapshot withFilteredSnapshot =
          this.mPackageManagerLocal.withFilteredSnapshot();
      try {
        Iterator it = list.iterator();
        while (it.hasNext()) {
          String str = (String) it.next();
          int finalStatus =
              this.mArtManagerLocal
                  .dexoptPackage(
                      withFilteredSnapshot,
                      str,
                      new DexoptOptions(str, 25, "speed-profile", null, dexoptFlags)
                          .convertToDexoptParams(0))
                  .getFinalStatus();
          if (finalStatus != 20 && finalStatus != 10) {
            arrayList.add(0);
          }
          arrayList.add(1);
        }
        if (withFilteredSnapshot != null) {
          withFilteredSnapshot.close();
        }
      } finally {
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return arrayList;
  }

  public void dump(PrintWriter printWriter, String[] strArr) {
    if (strArr.length == 3 && "dexopt".equals(strArr[0])) {
      printWriter.println("");
      printWriter.println("do dexopt");
      if ("1".equals(strArr[1])) {
        printWriter.println(
            "result is " + dexoptPackages(Collections.singletonList(strArr[2])).get(0));
        return;
      }
      if ("2".equals(strArr[1])) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(strArr[2]);
        printWriter.println("results are " + dexoptPackages(arrayList));
      }
    }
  }
}
