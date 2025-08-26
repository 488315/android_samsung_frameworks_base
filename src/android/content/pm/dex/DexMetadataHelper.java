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
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;

/* loaded from: classes.dex */
public class DexMetadataHelper {
    private static final String DEX_METADATA_FILE_EXTENSION = ".dm";
    private static final String PROPERTY_DM_FSVERITY_REQUIRED = "pm.dexopt.dm.require_fsverity";
    private static final String PROPERTY_DM_JSON_MANIFEST_REQUIRED = "pm.dexopt.dm.require_manifest";
    public static final String TAG = "DexMetadataHelper";
    public static final boolean DEBUG = Log.isLoggable(TAG, 3);

    private DexMetadataHelper() {
    }

    public static boolean isDexMetadataFile(File file) {
        return isDexMetadataPath(file.getName());
    }

    private static boolean isDexMetadataPath(String str) {
        return str.endsWith(".dm");
    }

    public static boolean isFsVerityRequired() {
        return VerityUtils.isFsVeritySupported() && SystemProperties.getBoolean(PROPERTY_DM_FSVERITY_REQUIRED, false);
    }

    public static long getPackageDexMetadataSize(PackageLite packageLite) {
        Iterator<String> it = getPackageDexMetadata(packageLite).values().iterator();
        long length = 0;
        while (it.hasNext()) {
            length += new File(it.next()).length();
        }
        return length;
    }

    public static File findDexMetadataForFile(File file) {
        File file2 = new File(buildDexMetadataPathForFile(file));
        if (file2.exists()) {
            return file2;
        }
        return null;
    }

    private static Map<String, String> getPackageDexMetadata(PackageLite packageLite) {
        return buildPackageApkToDexMetadataMap(packageLite.getAllApkPaths());
    }

    public static Map<String, String> buildPackageApkToDexMetadataMap(List<String> list) {
        ArrayMap arrayMap = new ArrayMap();
        for (int size = list.size() - 1; size >= 0; size--) {
            String str = list.get(size);
            String strBuildDexMetadataPathForFile = buildDexMetadataPathForFile(new File(str));
            if (Files.exists(Paths.get(strBuildDexMetadataPathForFile, new String[0]), new LinkOption[0])) {
                arrayMap.put(str, strBuildDexMetadataPathForFile);
            }
        }
        return arrayMap;
    }

    public static String buildDexMetadataPathForApk(String str) {
        if (!ApkLiteParseUtils.isApkPath(str)) {
            throw new IllegalStateException("Corrupted package. Code path is not an apk " + str);
        }
        return str.substring(0, str.length() - 4) + ".dm";
    }

    private static String buildDexMetadataPathForFile(File file) {
        if (ApkLiteParseUtils.isApkFile(file)) {
            return buildDexMetadataPathForApk(file.getPath());
        }
        return file.getPath() + ".dm";
    }

    public static ParseResult validateDexMetadataFile(ParseInput parseInput, String str, String str2, long j) {
        return validateDexMetadataFile(parseInput, str, str2, j, SystemProperties.getBoolean(PROPERTY_DM_JSON_MANIFEST_REQUIRED, false));
    }

    public static ParseResult validateDexMetadataFile(ParseInput parseInput, String str, String str2, long j, boolean z) throws Throwable {
        Throwable th;
        ParseInput parseInput2;
        String str3;
        IOException iOException;
        StrictJarFile strictJarFile;
        if (DEBUG) {
            Log.v(TAG, "validateDexMetadataFile: " + str + ", " + str2 + ", " + j);
        }
        StrictJarFile strictJarFile2 = null;
        try {
            try {
                strictJarFile = new StrictJarFile(str, false, false);
                parseInput2 = parseInput;
                str3 = str;
            } catch (IOException e) {
                parseInput2 = parseInput;
                str3 = str;
                iOException = e;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            ParseResult parseResultValidateDexMetadataManifest = validateDexMetadataManifest(parseInput2, str3, strictJarFile, str2, j, z);
            try {
                strictJarFile.close();
            } catch (IOException unused) {
            }
            return parseResultValidateDexMetadataManifest;
        } catch (IOException e2) {
            iOException = e2;
            strictJarFile2 = strictJarFile;
            ParseResult parseResultError = parseInput2.error(PackageManager.INSTALL_FAILED_BAD_DEX_METADATA, "Error opening " + str3, iOException);
            if (strictJarFile2 != null) {
                try {
                    strictJarFile2.close();
                } catch (IOException unused2) {
                }
            }
            return parseResultError;
        } catch (Throwable th3) {
            th = th3;
            strictJarFile2 = strictJarFile;
            if (strictJarFile2 != null) {
                try {
                    strictJarFile2.close();
                    throw th;
                } catch (IOException unused3) {
                    throw th;
                }
            }
            throw th;
        }
    }

    private static ParseResult validateDexMetadataManifest(ParseInput parseInput, String str, StrictJarFile strictJarFile, String str2, long j, boolean z) throws IOException, NumberFormatException {
        if (!z) {
            if (DEBUG) {
                Log.v(TAG, "validateDexMetadataManifest: " + str + " manifest.json check skipped");
            }
            return parseInput.success(null);
        }
        ZipEntry zipEntryFindEntry = strictJarFile.findEntry("manifest.json");
        if (zipEntryFindEntry == null) {
            return parseInput.error(PackageManager.INSTALL_FAILED_BAD_DEX_METADATA, "Missing manifest.json in " + str);
        }
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(strictJarFile.getInputStream(zipEntryFindEntry), "UTF-8"));
            jsonReader.beginObject();
            String strNextString = null;
            long jNextLong = -1;
            while (jsonReader.hasNext()) {
                String strNextName = jsonReader.nextName();
                if (strNextName.equals("packageName")) {
                    strNextString = jsonReader.nextString();
                } else if (strNextName.equals(SmLib_IafdConstant.KEY_VERSION_CODE)) {
                    jNextLong = jsonReader.nextLong();
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
            if (strNextString == null || jNextLong == -1) {
                return parseInput.error(PackageManager.INSTALL_FAILED_BAD_DEX_METADATA, "manifest.json in " + str + " is missing 'packageName' and/or 'versionCode'");
            }
            if (!strNextString.equals(str2)) {
                return parseInput.error(PackageManager.INSTALL_FAILED_BAD_DEX_METADATA, "manifest.json in " + str + " has invalid packageName: " + strNextString + ", expected: " + str2);
            }
            if (j != jNextLong) {
                return parseInput.error(PackageManager.INSTALL_FAILED_BAD_DEX_METADATA, "manifest.json in " + str + " has invalid versionCode: " + jNextLong + ", expected: " + j);
            }
            if (DEBUG) {
                Log.v(TAG, "validateDexMetadataManifest: " + str + ", " + str2 + ", " + j + ": successful");
            }
            return parseInput.success(null);
        } catch (UnsupportedEncodingException e) {
            return parseInput.error(PackageManager.INSTALL_FAILED_BAD_DEX_METADATA, "Error opening manifest.json in " + str, e);
        }
    }

    public static void validateDexPaths(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < strArr.length; i++) {
            if (ApkLiteParseUtils.isApkPath(strArr[i])) {
                arrayList.add(strArr[i]);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (String str : strArr) {
            if (isDexMetadataPath(str)) {
                int size = arrayList.size() - 1;
                while (true) {
                    if (size >= 0) {
                        if (str.equals(buildDexMetadataPathForFile(new File((String) arrayList.get(size))))) {
                            break;
                        } else {
                            size--;
                        }
                    } else {
                        arrayList2.add(str);
                        break;
                    }
                }
            }
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        throw new IllegalStateException("Unmatched .dm files: " + arrayList2);
    }
}
