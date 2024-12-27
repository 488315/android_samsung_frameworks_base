package com.android.server.pm.parsing.pkg;

import android.content.pm.dex.DexMetadataHelper;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;

import com.android.internal.pm.parsing.PackageParserException;
import com.android.internal.pm.parsing.pkg.PackageImpl;
import com.android.internal.pm.pkg.component.ParsedActivity;
import com.android.internal.pm.pkg.component.ParsedInstrumentation;
import com.android.internal.pm.pkg.component.ParsedProvider;
import com.android.internal.pm.pkg.component.ParsedService;
import com.android.internal.util.ArrayUtils;
import com.android.server.SystemConfig;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class AndroidPackageUtils {
    public static List getAllCodePaths(AndroidPackage androidPackage) {
        PackageImpl packageImpl = (PackageImpl) androidPackage;
        ArrayList arrayList = new ArrayList();
        arrayList.add(packageImpl.getBaseApkPath());
        String[] splitCodePaths = packageImpl.getSplitCodePaths();
        if (!ArrayUtils.isEmpty(splitCodePaths)) {
            Collections.addAll(arrayList, splitCodePaths);
        }
        return arrayList;
    }

    public static int getHiddenApiEnforcementPolicy(
            AndroidPackage androidPackage, PackageStateInternal packageStateInternal) {
        if (androidPackage == null) {
            return 2;
        }
        if (androidPackage.isSignedWithPlatformKey()) {
            return 0;
        }
        if (packageStateInternal.isSystem()) {
            return (androidPackage.isNonSdkApiRequested()
                            || SystemConfig.getInstance()
                                    .mHiddenApiPackageWhitelist
                                    .contains(androidPackage.getPackageName()))
                    ? 0
                    : 2;
        }
        return 2;
    }

    public static String getRealPackageOrNull(AndroidPackage androidPackage, boolean z) {
        if (androidPackage.getOriginalPackages().isEmpty() || !z) {
            return null;
        }
        return androidPackage.getManifestPackageName();
    }

    public static boolean hasComponentClassName(AndroidPackage androidPackage, String str) {
        List activities = androidPackage.getActivities();
        int size = activities.size();
        for (int i = 0; i < size; i++) {
            if (Objects.equals(str, ((ParsedActivity) activities.get(i)).getName())) {
                return true;
            }
        }
        List receivers = androidPackage.getReceivers();
        int size2 = receivers.size();
        for (int i2 = 0; i2 < size2; i2++) {
            if (Objects.equals(str, ((ParsedActivity) receivers.get(i2)).getName())) {
                return true;
            }
        }
        List providers = androidPackage.getProviders();
        int size3 = providers.size();
        for (int i3 = 0; i3 < size3; i3++) {
            if (Objects.equals(str, ((ParsedProvider) providers.get(i3)).getName())) {
                return true;
            }
        }
        List services = androidPackage.getServices();
        int size4 = services.size();
        for (int i4 = 0; i4 < size4; i4++) {
            if (Objects.equals(str, ((ParsedService) services.get(i4)).getName())) {
                return true;
            }
        }
        List instrumentations = androidPackage.getInstrumentations();
        int size5 = instrumentations.size();
        for (int i5 = 0; i5 < size5; i5++) {
            if (Objects.equals(str, ((ParsedInstrumentation) instrumentations.get(i5)).getName())) {
                return true;
            }
        }
        return androidPackage.getBackupAgentName() != null
                && Objects.equals(str, androidPackage.getBackupAgentName());
    }

    public static boolean isLibrary(AndroidPackage androidPackage) {
        return (androidPackage.getSdkLibraryName() == null
                        && androidPackage.getStaticSharedLibraryName() == null
                        && androidPackage.getLibraryNames().isEmpty())
                ? false
                : true;
    }

    public static void validatePackageDexMetadata(AndroidPackage androidPackage) {
        Collection values =
                DexMetadataHelper.buildPackageApkToDexMetadataMap(getAllCodePaths(androidPackage))
                        .values();
        String packageName = androidPackage.getPackageName();
        long longVersionCode = androidPackage.getLongVersionCode();
        ParseTypeImpl forDefaultParsing = ParseTypeImpl.forDefaultParsing();
        Iterator it = values.iterator();
        while (it.hasNext()) {
            ParseResult validateDexMetadataFile =
                    DexMetadataHelper.validateDexMetadataFile(
                            forDefaultParsing.reset(),
                            (String) it.next(),
                            packageName,
                            longVersionCode);
            if (validateDexMetadataFile.isError()) {
                throw new PackageParserException(
                        validateDexMetadataFile.getErrorCode(),
                        validateDexMetadataFile.getErrorMessage(),
                        validateDexMetadataFile.getException());
            }
        }
    }
}
