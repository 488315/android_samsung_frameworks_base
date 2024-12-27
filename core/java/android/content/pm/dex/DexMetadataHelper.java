package android.content.pm.dex;

import android.content.pm.PackageManager;
import android.content.pm.parsing.ApkLiteParseUtils;
import android.content.pm.parsing.PackageLite;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.JsonReader;
import android.util.Log;
import android.util.jar.StrictJarFile;

import com.android.internal.security.VerityUtils;

import com.sec.android.iaft.SmLib_IafdConstant;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;

public class DexMetadataHelper {
    private static final String DEX_METADATA_FILE_EXTENSION = ".dm";
    private static final String PROPERTY_DM_FSVERITY_REQUIRED = "pm.dexopt.dm.require_fsverity";
    private static final String PROPERTY_DM_JSON_MANIFEST_REQUIRED =
            "pm.dexopt.dm.require_manifest";
    public static final String TAG = "DexMetadataHelper";
    public static final boolean DEBUG = Log.isLoggable(TAG, 3);

    private DexMetadataHelper() {}

    public static boolean isDexMetadataFile(File file) {
        return isDexMetadataPath(file.getName());
    }

    private static boolean isDexMetadataPath(String path) {
        return path.endsWith(".dm");
    }

    public static boolean isFsVerityRequired() {
        return VerityUtils.isFsVeritySupported()
                && SystemProperties.getBoolean(PROPERTY_DM_FSVERITY_REQUIRED, false);
    }

    public static long getPackageDexMetadataSize(PackageLite pkg) {
        long sizeBytes = 0;
        Collection<String> dexMetadataList = getPackageDexMetadata(pkg).values();
        for (String dexMetadata : dexMetadataList) {
            sizeBytes += new File(dexMetadata).length();
        }
        return sizeBytes;
    }

    public static File findDexMetadataForFile(File targetFile) {
        String dexMetadataPath = buildDexMetadataPathForFile(targetFile);
        File dexMetadataFile = new File(dexMetadataPath);
        if (dexMetadataFile.exists()) {
            return dexMetadataFile;
        }
        return null;
    }

    private static Map<String, String> getPackageDexMetadata(PackageLite pkg) {
        return buildPackageApkToDexMetadataMap(pkg.getAllApkPaths());
    }

    public static Map<String, String> buildPackageApkToDexMetadataMap(List<String> codePaths) {
        ArrayMap<String, String> result = new ArrayMap<>();
        for (int i = codePaths.size() - 1; i >= 0; i--) {
            String codePath = codePaths.get(i);
            String dexMetadataPath = buildDexMetadataPathForFile(new File(codePath));
            if (Files.exists(Paths.get(dexMetadataPath, new String[0]), new LinkOption[0])) {
                result.put(codePath, dexMetadataPath);
            }
        }
        return result;
    }

    public static String buildDexMetadataPathForApk(String codePath) {
        if (!ApkLiteParseUtils.isApkPath(codePath)) {
            throw new IllegalStateException(
                    "Corrupted package. Code path is not an apk " + codePath);
        }
        return codePath.substring(0, codePath.length() - ".apk".length()) + ".dm";
    }

    private static String buildDexMetadataPathForFile(File targetFile) {
        if (ApkLiteParseUtils.isApkFile(targetFile)) {
            return buildDexMetadataPathForApk(targetFile.getPath());
        }
        return targetFile.getPath() + ".dm";
    }

    public static ParseResult validateDexMetadataFile(
            ParseInput input, String dmaPath, String packageName, long versionCode) {
        return validateDexMetadataFile(
                input,
                dmaPath,
                packageName,
                versionCode,
                SystemProperties.getBoolean(PROPERTY_DM_JSON_MANIFEST_REQUIRED, false));
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.pm.parsing.result.ParseResult validateDexMetadataFile(
            android.content.pm.parsing.result.ParseInput r10,
            java.lang.String r11,
            java.lang.String r12,
            long r13,
            boolean r15) {
        /*
            r0 = 0
            boolean r1 = android.content.pm.dex.DexMetadataHelper.DEBUG
            if (r1 == 0) goto L30
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "validateDexMetadataFile: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r2 = ", "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r13)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "DexMetadataHelper"
            android.util.Log.v(r2, r1)
        L30:
            android.util.jar.StrictJarFile r5 = new android.util.jar.StrictJarFile     // Catch: java.lang.Throwable -> L48 java.io.IOException -> L4c
            r1 = 0
            r5.<init>(r11, r1, r1)     // Catch: java.lang.Throwable -> L48 java.io.IOException -> L4c
            r3 = r10
            r4 = r11
            r6 = r12
            r7 = r13
            r9 = r15
            android.content.pm.parsing.result.ParseResult r0 = validateDexMetadataManifest(r3, r4, r5, r6, r7, r9)     // Catch: java.io.IOException -> L46 java.lang.Throwable -> L70
            r5.close()     // Catch: java.io.IOException -> L44
            goto L45
        L44:
            r1 = move-exception
        L45:
            return r0
        L46:
            r0 = move-exception
            goto L4f
        L48:
            r1 = move-exception
            r5 = r0
            r0 = r1
            goto L71
        L4c:
            r1 = move-exception
            r5 = r0
            r0 = r1
        L4f:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L70
            r1.<init>()     // Catch: java.lang.Throwable -> L70
            java.lang.String r2 = "Error opening "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L70
            java.lang.StringBuilder r1 = r1.append(r11)     // Catch: java.lang.Throwable -> L70
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L70
            r2 = -117(0xffffffffffffff8b, float:NaN)
            android.content.pm.parsing.result.ParseResult r1 = r10.error(r2, r1, r0)     // Catch: java.lang.Throwable -> L70
            if (r5 == 0) goto L6f
            r5.close()     // Catch: java.io.IOException -> L6e
            goto L6f
        L6e:
            r2 = move-exception
        L6f:
            return r1
        L70:
            r0 = move-exception
        L71:
            if (r5 == 0) goto L78
            r5.close()     // Catch: java.io.IOException -> L77
            goto L78
        L77:
            r1 = move-exception
        L78:
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.content.pm.dex.DexMetadataHelper.validateDexMetadataFile(android.content.pm.parsing.result.ParseInput,"
                    + " java.lang.String, java.lang.String, long,"
                    + " boolean):android.content.pm.parsing.result.ParseResult");
    }

    private static ParseResult validateDexMetadataManifest(
            ParseInput input,
            String dmaPath,
            StrictJarFile jarFile,
            String packageName,
            long versionCode,
            boolean requireManifest)
            throws IOException {
        if (!requireManifest) {
            if (DEBUG) {
                Log.v(
                        TAG,
                        "validateDexMetadataManifest: " + dmaPath + " manifest.json check skipped");
            }
            return input.success(null);
        }
        ZipEntry zipEntry = jarFile.findEntry("manifest.json");
        if (zipEntry == null) {
            return input.error(
                    PackageManager.INSTALL_FAILED_BAD_DEX_METADATA,
                    "Missing manifest.json in " + dmaPath);
        }
        InputStream inputStream = jarFile.getInputStream(zipEntry);
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            String jsonPackageName = null;
            long jsonVersionCode = -1;
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("packageName")) {
                    jsonPackageName = reader.nextString();
                } else if (name.equals(SmLib_IafdConstant.KEY_VERSION_CODE)) {
                    jsonVersionCode = reader.nextLong();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            if (jsonPackageName == null || jsonVersionCode == -1) {
                return input.error(
                        PackageManager.INSTALL_FAILED_BAD_DEX_METADATA,
                        "manifest.json in "
                                + dmaPath
                                + " is missing 'packageName' and/or 'versionCode'");
            }
            if (!jsonPackageName.equals(packageName)) {
                return input.error(
                        PackageManager.INSTALL_FAILED_BAD_DEX_METADATA,
                        "manifest.json in "
                                + dmaPath
                                + " has invalid packageName: "
                                + jsonPackageName
                                + ", expected: "
                                + packageName);
            }
            if (versionCode != jsonVersionCode) {
                return input.error(
                        PackageManager.INSTALL_FAILED_BAD_DEX_METADATA,
                        "manifest.json in "
                                + dmaPath
                                + " has invalid versionCode: "
                                + jsonVersionCode
                                + ", expected: "
                                + versionCode);
            }
            if (DEBUG) {
                Log.v(
                        TAG,
                        "validateDexMetadataManifest: "
                                + dmaPath
                                + ", "
                                + packageName
                                + ", "
                                + versionCode
                                + ": successful");
            }
            return input.success(null);
        } catch (UnsupportedEncodingException e) {
            return input.error(
                    PackageManager.INSTALL_FAILED_BAD_DEX_METADATA,
                    "Error opening manifest.json in " + dmaPath,
                    e);
        }
    }

    public static void validateDexPaths(String[] paths) {
        ArrayList<String> apks = new ArrayList<>();
        for (int i = 0; i < paths.length; i++) {
            if (ApkLiteParseUtils.isApkPath(paths[i])) {
                apks.add(paths[i]);
            }
        }
        ArrayList<String> unmatchedDmFiles = new ArrayList<>();
        for (String dmPath : paths) {
            if (isDexMetadataPath(dmPath)) {
                boolean valid = false;
                int j = apks.size() - 1;
                while (true) {
                    if (j < 0) {
                        break;
                    }
                    if (dmPath.equals(buildDexMetadataPathForFile(new File(apks.get(j))))) {
                        valid = true;
                        break;
                    }
                    j--;
                }
                if (!valid) {
                    unmatchedDmFiles.add(dmPath);
                }
            }
        }
        if (!unmatchedDmFiles.isEmpty()) {
            throw new IllegalStateException("Unmatched .dm files: " + unmatchedDmFiles);
        }
    }
}
