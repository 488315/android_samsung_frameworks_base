package com.android.server.pm;

import android.util.ArraySet;
import android.util.Pair;
import com.android.server.pm.parsing.pkg.ParsedPackage;
import com.android.server.pm.pkg.AndroidPackage;
import java.io.File;

/* loaded from: classes3.dex */
public interface PackageAbiHelper {
    NativeLibraryPaths deriveNativeLibraryPaths(AndroidPackage androidPackage, boolean z, boolean z2, File file);

    Pair derivePackageAbi(AndroidPackage androidPackage, boolean z, boolean z2, String str, File file);

    String getAdjustedAbiForSharedUser(ArraySet arraySet, AndroidPackage androidPackage);

    Abis getBundledAppAbis(AndroidPackage androidPackage);

    public final class NativeLibraryPaths {
        public final String nativeLibraryDir;
        public final String nativeLibraryRootDir;
        public final boolean nativeLibraryRootRequiresIsa;
        public final String secondaryNativeLibraryDir;

        public NativeLibraryPaths(String str, boolean z, String str2, String str3) {
            this.nativeLibraryRootDir = str;
            this.nativeLibraryRootRequiresIsa = z;
            this.nativeLibraryDir = str2;
            this.secondaryNativeLibraryDir = str3;
        }

        public void applyTo(ParsedPackage parsedPackage) {
            parsedPackage.setNativeLibraryRootDir(this.nativeLibraryRootDir).setNativeLibraryRootRequiresIsa(this.nativeLibraryRootRequiresIsa).setNativeLibraryDir(this.nativeLibraryDir).setSecondaryNativeLibraryDir(this.secondaryNativeLibraryDir);
        }
    }

    public final class Abis {
        public final String primary;
        public final String secondary;

        public Abis(String str, String str2) {
            this.primary = str;
            this.secondary = str2;
        }

        public void applyTo(ParsedPackage parsedPackage) {
            parsedPackage.setPrimaryCpuAbi(this.primary).setSecondaryCpuAbi(this.secondary);
        }

        public void applyTo(PackageSetting packageSetting) {
            if (packageSetting != null) {
                packageSetting.setPrimaryCpuAbi(this.primary);
                packageSetting.setSecondaryCpuAbi(this.secondary);
            }
        }
    }
}
