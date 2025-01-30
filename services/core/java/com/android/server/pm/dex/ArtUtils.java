package com.android.server.pm.dex;

import com.android.server.pm.InstructionSets;
import com.android.server.pm.PackageDexOptimizer;
import com.android.server.pm.parsing.pkg.AndroidPackageUtils;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import java.io.File;
import java.util.Arrays;

/* loaded from: classes3.dex */
public abstract class ArtUtils {
  public static ArtPackageInfo createArtPackageInfo(
      AndroidPackage androidPackage, PackageState packageState) {
    return new ArtPackageInfo(
        androidPackage.getPackageName(),
        Arrays.asList(
            InstructionSets.getAppDexInstructionSets(
                packageState.getPrimaryCpuAbi(), packageState.getSecondaryCpuAbi())),
        AndroidPackageUtils.getAllCodePaths(androidPackage),
        getOatDir(androidPackage, packageState));
  }

  public static String getOatDir(AndroidPackage androidPackage, PackageState packageState) {
    if (!AndroidPackageUtils.canHaveOatDir(packageState, androidPackage)) {
      return null;
    }
    File file = new File(androidPackage.getPath());
    if (file.isDirectory()) {
      return PackageDexOptimizer.getOatDir(file).getAbsolutePath();
    }
    return null;
  }
}
