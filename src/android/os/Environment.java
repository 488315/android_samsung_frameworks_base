package android.os;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.Application;
import android.compat.Compatibility;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.common.AsProperty;
import com.samsung.android.knox.SemPersonaManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/* loaded from: classes3.dex */
public class Environment {
    private static final long DEFAULT_SCOPED_STORAGE = 149924527;
    public static String DIRECTORY_ALARMS = null;

    @Deprecated
    public static final String DIRECTORY_ANDROID = "Android";
    public static String DIRECTORY_AUDIOBOOKS = null;
    public static String DIRECTORY_DCIM = null;
    public static String DIRECTORY_DOCUMENTS = null;
    public static String DIRECTORY_DOWNLOADS = null;
    public static String DIRECTORY_MOVIES = null;
    public static String DIRECTORY_MUSIC = null;
    public static String DIRECTORY_NOTIFICATIONS = null;
    public static String DIRECTORY_PICTURES = null;
    public static String DIRECTORY_PODCASTS = null;
    public static String DIRECTORY_RECORDINGS = null;
    public static String DIRECTORY_RINGTONES = null;
    public static String DIRECTORY_SCREENSHOTS = null;
    public static final String DIR_ANDROID = "Android";
    private static final File DIR_ANDROID_DATA;
    private static final String DIR_ANDROID_DATA_PATH;
    private static final File DIR_ANDROID_EXPAND;
    private static final File DIR_ANDROID_STORAGE;
    private static final File DIR_APEX_ROOT;
    private static final String DIR_CACHE = "cache";
    private static final String DIR_DATA = "data";
    private static final File DIR_DOWNLOAD_CACHE;
    private static final String DIR_FILES = "files";
    private static final String DIR_MEDIA = "media";
    private static final File DIR_METADATA;
    private static final String DIR_OBB = "obb";
    private static final File DIR_ODM_ROOT;
    private static final File DIR_OEM_ROOT;
    private static final File DIR_PRODUCT_ROOT;
    private static final File DIR_SYSTEM_EXT_ROOT;
    public static final String DIR_USER_CE = "user";
    public static final String DIR_USER_DE = "user_de";
    private static final File DIR_VENDOR_ROOT;
    private static final String ENV_ANDROID_DATA = "ANDROID_DATA";
    private static final String ENV_ANDROID_EXPAND = "ANDROID_EXPAND";
    private static final String ENV_ANDROID_STORAGE = "ANDROID_STORAGE";
    private static final String ENV_APEX_ROOT = "APEX_ROOT";
    private static final String ENV_DOWNLOAD_CACHE = "DOWNLOAD_CACHE";
    private static final String ENV_EXTERNAL_STORAGE = "EXTERNAL_STORAGE";
    private static final String ENV_ODM_ROOT = "ODM_ROOT";
    private static final String ENV_OEM_ROOT = "OEM_ROOT";
    private static final String ENV_PRODUCT_ROOT = "PRODUCT_ROOT";
    private static final String ENV_SYSTEM_EXT_ROOT = "SYSTEM_EXT_ROOT";
    private static final String ENV_VENDOR_ROOT = "VENDOR_ROOT";
    private static final long FORCE_ENABLE_SCOPED_STORAGE = 132649864;
    public static final int HAS_ALARMS = 8;
    public static final int HAS_ANDROID = 65536;
    public static final int HAS_AUDIOBOOKS = 1024;
    public static final int HAS_DCIM = 256;
    public static final int HAS_DOCUMENTS = 512;
    public static final int HAS_DOWNLOADS = 128;
    public static final int HAS_MOVIES = 64;
    public static final int HAS_MUSIC = 1;
    public static final int HAS_NOTIFICATIONS = 16;
    public static final int HAS_OTHER = 131072;
    public static final int HAS_PICTURES = 32;
    public static final int HAS_PODCASTS = 2;
    public static final int HAS_RECORDINGS = 2048;
    public static final int HAS_RINGTONES = 4;
    public static final String MEDIA_BAD_REMOVAL = "bad_removal";
    public static final String MEDIA_CHECKING = "checking";
    public static final String MEDIA_EJECTING = "ejecting";
    public static final String MEDIA_MOUNTED = "mounted";
    public static final String MEDIA_MOUNTED_READ_ONLY = "mounted_ro";
    public static final String MEDIA_NOFS = "nofs";
    public static final String MEDIA_REMOVED = "removed";
    public static final String MEDIA_SHARED = "shared";
    public static final String MEDIA_UNKNOWN = "unknown";
    public static final String MEDIA_UNMOUNTABLE = "unmountable";
    public static final String MEDIA_UNMOUNTED = "unmounted";
    public static final String[] STANDARD_DIRECTORIES;
    private static final String TAG = "Environment";
    private static UserEnvironment sCurrentUser;
    private static Boolean sLegacyStorageAppOp;
    private static Boolean sNoIsolatedStorageAppOp;
    private static boolean sUserRequired;
    private static final String ENV_ANDROID_ROOT = "ANDROID_ROOT";
    private static final File DIR_ANDROID_ROOT = getDirectory(ENV_ANDROID_ROOT, "/system");

    private static boolean isScopedStorageDisabled(boolean z, boolean z2) {
        return (z || z2) ? false : true;
    }

    private static boolean isScopedStorageEnforced(boolean z, boolean z2) {
        return z && z2;
    }

    static {
        String directoryPath = getDirectoryPath(ENV_ANDROID_DATA, "/data");
        DIR_ANDROID_DATA_PATH = directoryPath;
        DIR_ANDROID_DATA = new File(directoryPath);
        DIR_ANDROID_EXPAND = getDirectory(ENV_ANDROID_EXPAND, "/mnt/expand");
        DIR_ANDROID_STORAGE = getDirectory(ENV_ANDROID_STORAGE, "/storage");
        DIR_DOWNLOAD_CACHE = getDirectory(ENV_DOWNLOAD_CACHE, "/cache");
        DIR_METADATA = new File("/metadata");
        DIR_OEM_ROOT = getDirectory(ENV_OEM_ROOT, "/oem");
        DIR_ODM_ROOT = getDirectory(ENV_ODM_ROOT, "/odm");
        DIR_VENDOR_ROOT = getDirectory(ENV_VENDOR_ROOT, "/vendor");
        DIR_PRODUCT_ROOT = getDirectory(ENV_PRODUCT_ROOT, "/product");
        DIR_SYSTEM_EXT_ROOT = getDirectory(ENV_SYSTEM_EXT_ROOT, "/system_ext");
        DIR_APEX_ROOT = getDirectory(ENV_APEX_ROOT, "/apex");
        initForCurrentUser();
        DIRECTORY_MUSIC = "Music";
        DIRECTORY_PODCASTS = "Podcasts";
        DIRECTORY_RINGTONES = "Ringtones";
        DIRECTORY_ALARMS = "Alarms";
        DIRECTORY_NOTIFICATIONS = SemPersonaManager.NOTIFICATIONS;
        DIRECTORY_PICTURES = "Pictures";
        DIRECTORY_MOVIES = "Movies";
        DIRECTORY_DOWNLOADS = "Download";
        DIRECTORY_DCIM = "DCIM";
        DIRECTORY_DOCUMENTS = "Documents";
        DIRECTORY_SCREENSHOTS = "Screenshots";
        DIRECTORY_AUDIOBOOKS = "Audiobooks";
        DIRECTORY_RECORDINGS = "Recordings";
        STANDARD_DIRECTORIES = new String[]{"Music", "Podcasts", "Ringtones", "Alarms", SemPersonaManager.NOTIFICATIONS, "Pictures", "Movies", "Download", "DCIM", "Documents", "Audiobooks", "Recordings"};
    }

    public static void initForCurrentUser() {
        sCurrentUser = new UserEnvironment(UserHandle.myUserId());
    }

    public static class UserEnvironment {
        private final int mUserId;

        public UserEnvironment(int i) {
            this.mUserId = i;
        }

        public File[] getExternalDirs() {
            StorageVolume[] volumeList = StorageManager.getVolumeList(this.mUserId, 256);
            File[] fileArr = new File[volumeList.length];
            for (int i = 0; i < volumeList.length; i++) {
                fileArr[i] = volumeList[i].getPathFile();
            }
            return fileArr;
        }

        public File getExternalStorageDirectory() {
            return getExternalDirs()[0];
        }

        public File getExternalStoragePublicDirectory(String str) {
            return buildExternalStoragePublicDirs(str)[0];
        }

        public File[] buildExternalStoragePublicDirs(String str) {
            return Environment.buildPaths(getExternalDirs(), str);
        }

        public File[] buildExternalStorageAndroidDataDirs() {
            return Environment.buildPaths(getExternalDirs(), "Android", "data");
        }

        public File[] buildExternalStorageAndroidObbDirs() {
            return Environment.buildPaths(getExternalDirs(), "Android", "obb");
        }

        public File[] buildExternalStorageAppDataDirs(String str) {
            return Environment.buildPaths(getExternalDirs(), "Android", "data", str);
        }

        public File[] buildExternalStorageAppMediaDirs(String str) {
            return Environment.buildPaths(getExternalDirs(), "Android", "media", str);
        }

        public File[] buildExternalStorageAppObbDirs(String str) {
            return Environment.buildPaths(getExternalDirs(), "Android", "obb", str);
        }

        public File[] buildExternalStorageAppFilesDirs(String str) {
            return Environment.buildPaths(getExternalDirs(), "Android", "data", str, Environment.DIR_FILES);
        }

        public File[] buildExternalStorageAppCacheDirs(String str) {
            return Environment.buildPaths(getExternalDirs(), "Android", "data", str, Environment.DIR_CACHE);
        }
    }

    public static File getRootDirectory() {
        return DIR_ANDROID_ROOT;
    }

    public static File getStorageDirectory() {
        return DIR_ANDROID_STORAGE;
    }

    @SystemApi
    public static File getOemDirectory() {
        return DIR_OEM_ROOT;
    }

    @SystemApi
    public static File getOdmDirectory() {
        return DIR_ODM_ROOT;
    }

    @SystemApi
    public static File getVendorDirectory() {
        return DIR_VENDOR_ROOT;
    }

    @SystemApi
    public static File getProductDirectory() {
        return DIR_PRODUCT_ROOT;
    }

    @SystemApi
    @Deprecated
    public static File getProductServicesDirectory() {
        return getDirectory("PRODUCT_SERVICES_ROOT", "/product_services");
    }

    @SystemApi
    public static File getSystemExtDirectory() {
        return DIR_SYSTEM_EXT_ROOT;
    }

    public static File getApexDirectory() {
        return DIR_APEX_ROOT;
    }

    @Deprecated
    public static File getUserSystemDirectory(int i) {
        return new File(new File(getDataSystemDirectory(), "users"), Integer.toString(i));
    }

    @Deprecated
    public static File getUserConfigDirectory(int i) {
        return new File(new File(new File(getDataDirectory(), "misc"), "user"), Integer.toString(i));
    }

    public static File getDataDirectory() {
        return DIR_ANDROID_DATA;
    }

    public static String getDataDirectoryPath() {
        return DIR_ANDROID_DATA_PATH;
    }

    public static File getDataDirectory(String str) {
        if (TextUtils.isEmpty(str)) {
            return DIR_ANDROID_DATA;
        }
        return new File("/mnt/expand/" + str);
    }

    public static String getDataDirectoryPath(String str) {
        if (TextUtils.isEmpty(str)) {
            return DIR_ANDROID_DATA_PATH;
        }
        return getExpandDirectory().getAbsolutePath() + File.separator + str;
    }

    public static File getExpandDirectory() {
        return DIR_ANDROID_EXPAND;
    }

    public static File getDataSystemDirectory() {
        return new File(getDataDirectory(), "system");
    }

    @SystemApi
    public static File getDataSystemDeviceProtectedDirectory() {
        return buildPath(getDataDirectory(), "system_de");
    }

    @Deprecated
    public static File getDataSystemDeDirectory() {
        return buildPath(getDataDirectory(), "system_de");
    }

    public static File getDataSystemCeDirectory() {
        return buildPath(getDataDirectory(), "system_ce");
    }

    public static File getDataSystemCeDirectory(int i) {
        return buildPath(getDataDirectory(), "system_ce", String.valueOf(i));
    }

    public static File getDataSystemDeDirectory(int i) {
        return buildPath(getDataDirectory(), "system_de", String.valueOf(i));
    }

    public static File getDataMiscDirectory() {
        return new File(getDataDirectory(), "misc");
    }

    public static File getDataMiscCeDirectory() {
        return buildPath(getDataDirectory(), "misc_ce");
    }

    public static File getDataMiscCeDirectory(int i) {
        return buildPath(getDataDirectory(), "misc_ce", String.valueOf(i));
    }

    private static File getDataMiscCeDirectory(String str, int i) {
        return buildPath(getDataDirectory(str), "misc_ce", String.valueOf(i));
    }

    public static File getDataMiscCeSharedSdkSandboxDirectory(String str, int i, String str2) {
        return buildPath(getDataMiscCeDirectory(str, i), "sdksandbox", str2, "shared");
    }

    public static File getDataMiscDeDirectory(int i) {
        return buildPath(getDataDirectory(), "misc_de", String.valueOf(i));
    }

    private static File getDataMiscDeDirectory(String str, int i) {
        return buildPath(getDataDirectory(str), "misc_de", String.valueOf(i));
    }

    public static File getDataMiscDeSharedSdkSandboxDirectory(String str, int i, String str2) {
        return buildPath(getDataMiscDeDirectory(str, i), "sdksandbox", str2, "shared");
    }

    private static File getDataProfilesDeDirectory(int i) {
        return buildPath(getDataDirectory(), "misc", "profiles", "cur", String.valueOf(i));
    }

    public static File getDataVendorCeDirectory(int i) {
        return buildPath(getDataDirectory(), "vendor_ce", String.valueOf(i));
    }

    public static File getDataVendorDeDirectory(int i) {
        return buildPath(getDataDirectory(), "vendor_de", String.valueOf(i));
    }

    public static File getDataRefProfilesDePackageDirectory(String str) {
        return buildPath(getDataDirectory(), "misc", "profiles", "ref", str);
    }

    public static File getDataProfilesDePackageDirectory(int i, String str) {
        return buildPath(getDataProfilesDeDirectory(i), str);
    }

    public static File getDataAppDirectory(String str) {
        return new File(getDataDirectory(str), "app");
    }

    public static File getDataStagingDirectory(String str) {
        return new File(getDataDirectory(str), "app-staging");
    }

    public static File getDataUserCeDirectory(String str) {
        return new File(getDataDirectory(str), "user");
    }

    public static File getDataUserCeDirectory(String str, int i) {
        return new File(getDataUserCeDirectory(str), String.valueOf(i));
    }

    public static File getDataUserCePackageDirectory(String str, int i, String str2) {
        return new File(getDataUserCeDirectory(str, i), str2);
    }

    @SystemApi
    public static File getDataCePackageDirectoryForUser(UUID uuid, UserHandle userHandle, String str) {
        return getDataUserCePackageDirectory(StorageManager.convert(uuid), userHandle.getIdentifier(), str);
    }

    public static File getDataUserDeDirectory(String str) {
        return new File(getDataDirectory(str), DIR_USER_DE);
    }

    public static File getDataUserDeDirectory(String str, int i) {
        return new File(getDataUserDeDirectory(str), String.valueOf(i));
    }

    public static File getDataUserDePackageDirectory(String str, int i, String str2) {
        return new File(getDataUserDeDirectory(str, i), str2);
    }

    @SystemApi
    public static File getDataDePackageDirectoryForUser(UUID uuid, UserHandle userHandle, String str) {
        return getDataUserDePackageDirectory(StorageManager.convert(uuid), userHandle.getIdentifier(), str);
    }

    public static File getDataPreloadsDirectory() {
        return new File(getDataDirectory(), "preloads");
    }

    public static File getDataPreloadsDemoDirectory() {
        return new File(getDataPreloadsDirectory(), "demo");
    }

    public static File getDataPreloadsAppsDirectory() {
        return new File(getDataPreloadsDirectory(), "apps");
    }

    public static File getDataPreloadsMediaDirectory() {
        return new File(getDataPreloadsDirectory(), "media");
    }

    public static File getDataPreloadsFileCacheDirectory(String str) {
        return new File(getDataPreloadsFileCacheDirectory(), str);
    }

    public static File getDataPreloadsFileCacheDirectory() {
        return new File(getDataPreloadsDirectory(), "file_cache");
    }

    public static File getPackageCacheDirectory() {
        return new File(getDataSystemDirectory(), "package_cache");
    }

    @SystemApi
    public static Collection<File> getInternalMediaDirectories() {
        ArrayList arrayList = new ArrayList();
        addCanonicalFile(arrayList, new File(getRootDirectory(), "media"));
        addCanonicalFile(arrayList, new File(getOemDirectory(), "media"));
        addCanonicalFile(arrayList, new File(getProductDirectory(), "media"));
        String str = SystemProperties.get(AsProperty.PROP_CFG_OMC_RESPATH, "");
        if (str.length() > 0) {
            addCanonicalFile(arrayList, new File(str));
        }
        addCanonicalFile(arrayList, new File(getDataDirectory(), "overlays/media"));
        return arrayList;
    }

    private static void addCanonicalFile(List<File> list, File file) {
        try {
            list.add(file.getCanonicalFile());
        } catch (IOException e) {
            Log.w(TAG, "Failed to resolve " + file + ": " + e);
            list.add(file);
        }
    }

    public static File getExternalStorageDirectory() {
        throwIfUserRequired();
        return sCurrentUser.getExternalDirs()[0];
    }

    public static File getLegacyExternalStorageDirectory() {
        return new File(System.getenv(ENV_EXTERNAL_STORAGE));
    }

    public static File getLegacyExternalStorageObbDirectory() {
        return buildPath(getLegacyExternalStorageDirectory(), "Android", "obb");
    }

    public static boolean isStandardDirectory(String str) {
        for (String str2 : STANDARD_DIRECTORIES) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0019  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int classifyExternalStorageDirectory(File file) {
        int i = 0;
        for (File file2 : FileUtils.listFilesOrEmpty(file)) {
            if (!file2.isFile() || !isInterestingFile(file2)) {
                if (file2.isDirectory() && hasInterestingFiles(file2)) {
                    String name = file2.getName();
                    if (DIRECTORY_MUSIC.equals(name)) {
                        i |= 1;
                    } else if (DIRECTORY_PODCASTS.equals(name)) {
                        i |= 2;
                    } else if (DIRECTORY_RINGTONES.equals(name)) {
                        i |= 4;
                    } else if (DIRECTORY_ALARMS.equals(name)) {
                        i |= 8;
                    } else if (DIRECTORY_NOTIFICATIONS.equals(name)) {
                        i |= 16;
                    } else if (DIRECTORY_PICTURES.equals(name)) {
                        i |= 32;
                    } else if (DIRECTORY_MOVIES.equals(name)) {
                        i |= 64;
                    } else if (DIRECTORY_DOWNLOADS.equals(name)) {
                        i |= 128;
                    } else if (DIRECTORY_DCIM.equals(name)) {
                        i |= 256;
                    } else if (DIRECTORY_DOCUMENTS.equals(name)) {
                        i |= 512;
                    } else if (DIRECTORY_AUDIOBOOKS.equals(name)) {
                        i |= 1024;
                    } else if (DIRECTORY_RECORDINGS.equals(name)) {
                        i |= 2048;
                    } else {
                        i = "Android".equals(name) ? i | 65536 : i | 131072;
                    }
                }
            }
        }
        return i;
    }

    private static boolean hasInterestingFiles(File file) {
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.add(file);
        while (true) {
            if (arrayDeque.isEmpty()) {
                return false;
            }
            for (File file2 : FileUtils.listFilesOrEmpty((File) arrayDeque.pop())) {
                if (isInterestingFile(file2)) {
                    return true;
                }
                if (file2.isDirectory()) {
                    arrayDeque.add(file2);
                }
            }
        }
    }

    private static boolean isInterestingFile(File file) {
        if (file.isFile()) {
            String lowerCase = file.getName().toLowerCase();
            if (!lowerCase.endsWith(".exe") && !lowerCase.equals("autorun.inf") && !lowerCase.equals("launchpad.zip") && !lowerCase.equals(".nomedia")) {
                return true;
            }
        }
        return false;
    }

    public static File getExternalStoragePublicDirectory(String str) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStoragePublicDirs(str)[0];
    }

    public static File[] buildExternalStorageAndroidDataDirs() {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAndroidDataDirs();
    }

    public static File[] buildExternalStorageAndroidObbDirs() {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAndroidObbDirs();
    }

    public static File[] buildExternalStorageAppDataDirs(String str) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppDataDirs(str);
    }

    public static File[] buildExternalStorageAppMediaDirs(String str) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppMediaDirs(str);
    }

    public static File[] buildExternalStorageAppObbDirs(String str) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppObbDirs(str);
    }

    public static File[] buildExternalStorageAppFilesDirs(String str) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppFilesDirs(str);
    }

    public static File[] buildExternalStorageAppCacheDirs(String str) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppCacheDirs(str);
    }

    public static File[] buildExternalStoragePublicDirs(String str) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStoragePublicDirs(str);
    }

    public static File getDownloadCacheDirectory() {
        return DIR_DOWNLOAD_CACHE;
    }

    public static File getMetadataDirectory() {
        return DIR_METADATA;
    }

    public static String getExternalStorageState() {
        return getExternalStorageState(sCurrentUser.getExternalDirs()[0]);
    }

    @Deprecated
    public static String getStorageState(File file) {
        return getExternalStorageState(file);
    }

    public static String getExternalStorageState(File file) {
        StorageVolume storageVolume = StorageManager.getStorageVolume(file, UserHandle.myUserId());
        if (storageVolume != null) {
            return storageVolume.getState();
        }
        return "unknown";
    }

    public static boolean isExternalStorageRemovable() {
        return isExternalStorageRemovable(sCurrentUser.getExternalDirs()[0]);
    }

    public static boolean isExternalStorageRemovable(File file) {
        StorageVolume storageVolume = StorageManager.getStorageVolume(file, UserHandle.myUserId());
        if (storageVolume != null) {
            return storageVolume.isRemovable();
        }
        throw new IllegalArgumentException("Failed to find storage device at " + file);
    }

    public static boolean isExternalStorageEmulated() {
        return isExternalStorageEmulated(sCurrentUser.getExternalDirs()[0]);
    }

    public static boolean isExternalStorageEmulated(File file) {
        StorageVolume storageVolume = StorageManager.getStorageVolume(file, UserHandle.myUserId());
        if (storageVolume != null) {
            return storageVolume.isEmulated();
        }
        throw new IllegalArgumentException("Failed to find storage device at " + file);
    }

    public static boolean isExternalStorageLegacy() {
        return isExternalStorageLegacy(sCurrentUser.getExternalDirs()[0]);
    }

    public static boolean isExternalStorageLegacy(File file) {
        Application initialApplication = AppGlobals.getInitialApplication();
        int i = initialApplication.getApplicationInfo().uid;
        if (Process.isIsolated(i) || Process.isSdkSandboxUid(i)) {
            return false;
        }
        PackageManager packageManager = initialApplication.getPackageManager();
        if (packageManager.isInstantApp()) {
            return false;
        }
        try {
            PackageManager.Property property = packageManager.getProperty(PackageManager.PROPERTY_NO_APP_DATA_STORAGE, AppGlobals.getInitialPackage());
            if (property != null) {
                if (property.getBoolean()) {
                    return false;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        boolean zIsChangeEnabled = Compatibility.isChangeEnabled(DEFAULT_SCOPED_STORAGE);
        boolean zIsChangeEnabled2 = Compatibility.isChangeEnabled(FORCE_ENABLE_SCOPED_STORAGE);
        if (isScopedStorageEnforced(zIsChangeEnabled, zIsChangeEnabled2)) {
            return false;
        }
        if (isScopedStorageDisabled(zIsChangeEnabled, zIsChangeEnabled2)) {
            return true;
        }
        AppOpsManager appOpsManager = (AppOpsManager) initialApplication.getSystemService(AppOpsManager.class);
        String opPackageName = initialApplication.getOpPackageName();
        if (sLegacyStorageAppOp == null) {
            sLegacyStorageAppOp = Boolean.valueOf(appOpsManager.checkOpNoThrow(87, i, opPackageName) == 0);
        }
        if (sLegacyStorageAppOp.booleanValue()) {
            return sLegacyStorageAppOp.booleanValue();
        }
        if (sNoIsolatedStorageAppOp == null) {
            sNoIsolatedStorageAppOp = Boolean.valueOf(appOpsManager.checkOpNoThrow(99, i, opPackageName) == 0);
        }
        return sNoIsolatedStorageAppOp.booleanValue();
    }

    public static boolean isExternalStorageManager() {
        return isExternalStorageManager(sCurrentUser.getExternalDirs()[0]);
    }

    public static boolean isExternalStorageManager(File file) {
        Context context = (Context) Objects.requireNonNull(AppGlobals.getInitialApplication());
        String str = (String) Objects.requireNonNull(context.getPackageName());
        int i = context.getApplicationInfo().uid;
        int iCheckOpNoThrow = ((AppOpsManager) context.getSystemService(AppOpsManager.class)).checkOpNoThrow(92, i, str);
        if (iCheckOpNoThrow == 0) {
            return true;
        }
        if (iCheckOpNoThrow == 1 || iCheckOpNoThrow == 2) {
            return false;
        }
        if (iCheckOpNoThrow == 3) {
            return context.checkPermission(Manifest.permission.MANAGE_EXTERNAL_STORAGE, Process.myPid(), i) == 0;
        }
        throw new IllegalStateException("Unknown AppOpsManager mode " + iCheckOpNoThrow);
    }

    static File getDirectory(String str, String str2) {
        String str3 = System.getenv(str);
        return str3 == null ? new File(str2) : new File(str3);
    }

    static String getDirectoryPath(String str, String str2) {
        String str3 = System.getenv(str);
        return str3 == null ? str2 : str3;
    }

    public static void setUserRequired(boolean z) {
        sUserRequired = z;
    }

    private static void throwIfUserRequired() {
        if (sUserRequired) {
            Log.wtf(TAG, "Path requests must specify a user by using UserEnvironment", new Throwable());
        }
    }

    public static File[] buildPaths(File[] fileArr, String... strArr) {
        File[] fileArr2 = new File[fileArr.length];
        for (int i = 0; i < fileArr.length; i++) {
            fileArr2[i] = buildPath(fileArr[i], strArr);
        }
        return fileArr2;
    }

    public static File buildPath(File file, String... strArr) {
        for (String str : strArr) {
            if (file == null) {
                file = new File(str);
            } else {
                file = new File(file, str);
            }
        }
        return file;
    }

    @Deprecated
    public static File maybeTranslateEmulatedPathToInternal(File file) {
        return StorageManager.maybeTranslateEmulatedPathToInternal(file);
    }
}
