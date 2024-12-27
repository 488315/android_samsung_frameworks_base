package com.android.server.pm;

import android.content.pm.parsing.ApkLiteParseUtils;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.pm.parsing.pkg.AndroidPackageHidden;
import com.android.internal.util.ArrayUtils;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageAbiHelper;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import dalvik.system.VMRuntime;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageAbiHelperImpl implements PackageAbiHelper {
    public static String[] sNativelySupported32BitAbis;
    public static String[] sNativelySupported64BitAbis;

    public static String calculateBundledApkRoot(String str) {
        File canonicalFile;
        File file = new File(str);
        if (FileUtils.contains(Environment.getRootDirectory(), file)) {
            canonicalFile = Environment.getRootDirectory();
        } else if (FileUtils.contains(Environment.getOemDirectory(), file)) {
            canonicalFile = Environment.getOemDirectory();
        } else if (FileUtils.contains(Environment.getVendorDirectory(), file)) {
            canonicalFile = Environment.getVendorDirectory();
        } else if (FileUtils.contains(Environment.getOdmDirectory(), file)) {
            canonicalFile = Environment.getOdmDirectory();
        } else if (FileUtils.contains(Environment.getProductDirectory(), file)) {
            canonicalFile = Environment.getProductDirectory();
        } else if (FileUtils.contains(Environment.getSystemExtDirectory(), file)) {
            canonicalFile = Environment.getSystemExtDirectory();
        } else if (FileUtils.contains(Environment.getOdmDirectory(), file)) {
            canonicalFile = Environment.getOdmDirectory();
        } else if (FileUtils.contains(Environment.getApexDirectory(), file)) {
            String absolutePath = file.getAbsolutePath();
            String str2 = File.separator;
            String[] split = absolutePath.split(str2);
            if (split.length > 2) {
                canonicalFile = new File(split[1] + str2 + split[2]);
            } else {
                Slog.w("PackageManager", "Can't canonicalize code path " + file);
                canonicalFile = Environment.getApexDirectory();
            }
        } else {
            try {
                canonicalFile = file.getCanonicalFile();
                File parentFile = canonicalFile.getParentFile();
                while (true) {
                    File parentFile2 = parentFile.getParentFile();
                    if (parentFile2 == null) {
                        break;
                    }
                    canonicalFile = parentFile;
                    parentFile = parentFile2;
                }
                Slog.w("PackageManager", "Unrecognized code path " + file + " - using " + canonicalFile);
            } catch (IOException unused) {
                Slog.w("PackageManager", "Can't canonicalize code path " + file);
                return Environment.getRootDirectory().getPath();
            }
        }
        return canonicalFile.getPath();
    }

    public static String deriveCodePathName(String str) {
        if (str == null) {
            return null;
        }
        File file = new File(str);
        String name = file.getName();
        if (file.isDirectory()) {
            return name;
        }
        if (name.endsWith(".apk") || name.endsWith(".tmp")) {
            return name.substring(0, name.lastIndexOf(46));
        }
        PinnerService$$ExternalSyntheticOutline0.m("Odd, ", str, " doesn't look like an APK", "PackageManager");
        return null;
    }

    public static PackageAbiHelper.NativeLibraryPaths deriveNativeLibraryPaths(PackageAbiHelper.Abis abis, File file, String str, String str2, boolean z, boolean z2) {
        String absolutePath;
        String absolutePath2;
        File file2 = new File(str);
        boolean z3 = false;
        boolean z4 = z && !z2;
        boolean isApkFile = ApkLiteParseUtils.isApkFile(file2);
        String str3 = abis.secondary;
        String str4 = abis.primary;
        if (isApkFile) {
            if (z4) {
                String calculateBundledApkRoot = calculateBundledApkRoot(str2);
                String str5 = InstructionSets.PREFERRED_INSTRUCTION_SET;
                boolean is64BitInstructionSet = VMRuntime.is64BitInstructionSet(str4 == null ? InstructionSets.PREFERRED_INSTRUCTION_SET : VMRuntime.getInstructionSet(str4));
                String deriveCodePathName = deriveCodePathName(str);
                absolutePath = Environment.buildPath(new File(calculateBundledApkRoot), new String[]{is64BitInstructionSet ? "lib64" : "lib", deriveCodePathName}).getAbsolutePath();
                if (str3 != null) {
                    r4 = Environment.buildPath(new File(calculateBundledApkRoot), new String[]{is64BitInstructionSet ? "lib" : "lib64", deriveCodePathName}).getAbsolutePath();
                }
            } else {
                absolutePath = new File(file, deriveCodePathName(str)).getAbsolutePath();
            }
            absolutePath2 = absolutePath;
        } else {
            absolutePath = new File(file2, "lib").getAbsolutePath();
            String str6 = InstructionSets.PREFERRED_INSTRUCTION_SET;
            absolutePath2 = new File(absolutePath, str4 == null ? InstructionSets.PREFERRED_INSTRUCTION_SET : VMRuntime.getInstructionSet(str4)).getAbsolutePath();
            r4 = str3 != null ? new File(absolutePath, VMRuntime.getInstructionSet(str3)).getAbsolutePath() : null;
            z3 = true;
        }
        return new PackageAbiHelper.NativeLibraryPaths(absolutePath, z3, absolutePath2, r4);
    }

    public static String[] getNativelySupportedAbis(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (TextUtils.isEmpty(SystemProperties.get("ro.dalvik.vm.isa." + VMRuntime.getInstructionSet(str)))) {
                arrayList.add(str);
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public static void maybeThrowExceptionForMultiArchCopy(int i, String str, boolean z) {
        if (i < 0) {
            if (i != -114 && i != -113) {
                throw new PackageManagerException(i, str);
            }
            if (z && i == -113) {
                throw new PackageManagerException(-131, "The multiArch app's native libs don't support all the natively supported ABIs of the device.");
            }
        }
    }

    @Override // com.android.server.pm.PackageAbiHelper
    public final PackageAbiHelper.NativeLibraryPaths deriveNativeLibraryPaths(AndroidPackage androidPackage, boolean z, boolean z2, File file) {
        AndroidPackageHidden androidPackageHidden = (AndroidPackageHidden) androidPackage;
        return deriveNativeLibraryPaths(new PackageAbiHelper.Abis(androidPackageHidden.getPrimaryCpuAbi(), androidPackageHidden.getSecondaryCpuAbi()), file, androidPackage.getPath(), androidPackage.getBaseApkPath(), z, z2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:131:0x01d9, code lost:
    
        if (com.android.server.pm.parsing.pkg.AndroidPackageUtils.isLibrary(r20) != false) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x01db, code lost:
    
        r0 = r4[r6];
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x01e8, code lost:
    
        throw new com.android.server.pm.PackageManagerException(-110, "Shared library with native libs must be multiarch");
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.pm.PackageAbiHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.util.Pair derivePackageAbi(com.android.server.pm.pkg.AndroidPackage r20, boolean r21, boolean r22, java.lang.String r23, java.io.File r24) {
        /*
            Method dump skipped, instructions count: 576
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageAbiHelperImpl.derivePackageAbi(com.android.server.pm.pkg.AndroidPackage, boolean, boolean, java.lang.String, java.io.File):android.util.Pair");
    }

    @Override // com.android.server.pm.PackageAbiHelper
    public final String getAdjustedAbiForSharedUser(ArraySet arraySet, AndroidPackage androidPackage) {
        String primaryCpuAbi;
        String instructionSet = (androidPackage == null || (primaryCpuAbi = ((AndroidPackageHidden) androidPackage).getPrimaryCpuAbi()) == null) ? null : VMRuntime.getInstructionSet(primaryCpuAbi);
        Iterator it = arraySet.iterator();
        PackageStateInternal packageStateInternal = null;
        while (it.hasNext()) {
            PackageStateInternal packageStateInternal2 = (PackageStateInternal) it.next();
            if (androidPackage == null || !androidPackage.getPackageName().equals(packageStateInternal2.getPackageName())) {
                if (packageStateInternal2.getPrimaryCpuAbiLegacy() != null) {
                    String instructionSet2 = VMRuntime.getInstructionSet(packageStateInternal2.getPrimaryCpuAbiLegacy());
                    if (instructionSet != null && !instructionSet.equals(instructionSet2)) {
                        StringBuilder sb = new StringBuilder("Instruction set mismatch, ");
                        sb.append(packageStateInternal == null ? "[caller]" : packageStateInternal);
                        sb.append(" requires ");
                        sb.append(instructionSet);
                        sb.append(" whereas ");
                        sb.append(packageStateInternal2);
                        sb.append(" requires ");
                        sb.append(instructionSet2);
                        Slog.w("PackageManager", sb.toString());
                    }
                    if (instructionSet == null) {
                        packageStateInternal = packageStateInternal2;
                        instructionSet = instructionSet2;
                    }
                }
            }
        }
        if (instructionSet == null) {
            return null;
        }
        return packageStateInternal != null ? packageStateInternal.getPrimaryCpuAbiLegacy() : ((AndroidPackageHidden) androidPackage).getPrimaryCpuAbi();
    }

    @Override // com.android.server.pm.PackageAbiHelper
    public final PackageAbiHelper.Abis getBundledAppAbis(AndroidPackage androidPackage) {
        boolean exists;
        boolean exists2;
        String str;
        String str2;
        String deriveCodePathName = deriveCodePathName(androidPackage.getPath());
        String calculateBundledApkRoot = calculateBundledApkRoot(androidPackage.getBaseApkPath());
        File file = new File(androidPackage.getPath());
        if (ApkLiteParseUtils.isApkFile(file)) {
            exists = new File(calculateBundledApkRoot, new File("lib64", deriveCodePathName).getPath()).exists();
            exists2 = new File(calculateBundledApkRoot, new File("lib", deriveCodePathName).getPath()).exists();
        } else {
            File file2 = new File(file, "lib");
            String[] strArr = Build.SUPPORTED_64_BIT_ABIS;
            exists = (ArrayUtils.isEmpty(strArr) || TextUtils.isEmpty(strArr[0])) ? false : new File(file2, VMRuntime.getInstructionSet(strArr[0])).exists();
            String[] strArr2 = Build.SUPPORTED_32_BIT_ABIS;
            exists2 = (ArrayUtils.isEmpty(strArr2) || TextUtils.isEmpty(strArr2[0])) ? false : new File(file2, VMRuntime.getInstructionSet(strArr2[0])).exists();
        }
        String str3 = null;
        if (exists && !exists2) {
            str2 = Build.SUPPORTED_64_BIT_ABIS[0];
        } else {
            if (!exists2 || exists) {
                if (exists2 && exists) {
                    if (!androidPackage.isMultiArch()) {
                        Slog.e("PackageManager", "Package " + androidPackage + " has multiple bundled libs, but is not multiarch.");
                    }
                    if (VMRuntime.is64BitInstructionSet(InstructionSets.PREFERRED_INSTRUCTION_SET)) {
                        str3 = Build.SUPPORTED_64_BIT_ABIS[0];
                        str = Build.SUPPORTED_32_BIT_ABIS[0];
                    } else {
                        str3 = Build.SUPPORTED_32_BIT_ABIS[0];
                        str = Build.SUPPORTED_64_BIT_ABIS[0];
                    }
                } else {
                    str = null;
                }
                return new PackageAbiHelper.Abis(str3, str);
            }
            str2 = Build.SUPPORTED_32_BIT_ABIS[0];
        }
        str3 = str2;
        str = null;
        return new PackageAbiHelper.Abis(str3, str);
    }
}
