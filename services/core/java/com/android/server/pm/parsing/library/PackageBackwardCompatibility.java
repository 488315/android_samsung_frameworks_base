package com.android.server.pm.parsing.library;

import android.util.Log;
import com.android.server.SystemConfig;
import com.android.server.pm.parsing.pkg.ParsedPackage;
import com.android.server.pm.pkg.parsing.ParsingPackage;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PackageBackwardCompatibility extends PackageSharedLibraryUpdater {
  public static final PackageBackwardCompatibility INSTANCE;
  public static final String TAG = "PackageBackwardCompatibility";
  public final boolean mBootClassPathContainsATB;
  public final PackageSharedLibraryUpdater[] mPackageUpdaters;

  static {
    ArrayList arrayList = new ArrayList();
    arrayList.add(new AndroidNetIpSecIkeUpdater());
    arrayList.add(new ComGoogleAndroidMapsUpdater());
    arrayList.add(new OrgApacheHttpLegacyUpdater());
    arrayList.add(new AndroidHidlUpdater());
    arrayList.add(new AndroidTestRunnerSplitUpdater());
    boolean z = !addUpdaterForAndroidTestBase(arrayList);
    arrayList.add(new ApexSharedLibraryUpdater(SystemConfig.getInstance().getSharedLibraries()));
    INSTANCE =
        new PackageBackwardCompatibility(
            z,
            (PackageSharedLibraryUpdater[]) arrayList.toArray(new PackageSharedLibraryUpdater[0]));
  }

  public static boolean addUpdaterForAndroidTestBase(List list) {
    try {
      r1 =
          ParsingPackage.class
                  .getClassLoader()
                  .loadClass("android.content.pm.AndroidTestBaseUpdater")
              != null;
      Log.i(TAG, "Loaded android.content.pm.AndroidTestBaseUpdater");
    } catch (ClassNotFoundException unused) {
      Log.i(TAG, "Could not find android.content.pm.AndroidTestBaseUpdater, ignoring");
    }
    if (r1) {
      list.add(new AndroidTestBaseUpdater());
    } else {
      list.add(new RemoveUnnecessaryAndroidTestBaseLibrary());
    }
    return r1;
  }

  public static PackageSharedLibraryUpdater getInstance() {
    return INSTANCE;
  }

  public PackageSharedLibraryUpdater[] getPackageUpdaters() {
    return this.mPackageUpdaters;
  }

  public PackageBackwardCompatibility(
      boolean z, PackageSharedLibraryUpdater[] packageSharedLibraryUpdaterArr) {
    this.mBootClassPathContainsATB = z;
    this.mPackageUpdaters = packageSharedLibraryUpdaterArr;
  }

  public static void modifySharedLibraries(ParsedPackage parsedPackage, boolean z, boolean z2) {
    INSTANCE.updatePackage(parsedPackage, z, z2);
  }

  @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
  public void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
    for (PackageSharedLibraryUpdater packageSharedLibraryUpdater : this.mPackageUpdaters) {
      packageSharedLibraryUpdater.updatePackage(parsedPackage, z, z2);
    }
  }

  public static boolean bootClassPathContainsATB() {
    return INSTANCE.mBootClassPathContainsATB;
  }

  public class AndroidTestRunnerSplitUpdater extends PackageSharedLibraryUpdater {
    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
      prefixImplicitDependency(parsedPackage, "android.test.runner", "android.test.mock");
    }
  }

  public class RemoveUnnecessaryOrgApacheHttpLegacyLibrary extends PackageSharedLibraryUpdater {
    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
      PackageSharedLibraryUpdater.removeLibrary(parsedPackage, "org.apache.http.legacy");
    }
  }

  public class RemoveUnnecessaryAndroidTestBaseLibrary extends PackageSharedLibraryUpdater {
    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
      PackageSharedLibraryUpdater.removeLibrary(parsedPackage, "android.test.base");
    }
  }
}
