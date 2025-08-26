package android.app.backup;

import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.system.ErrnoException;
import android.system.Os;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class FullBackup {
    public static final String APK_TREE_TOKEN = "a";
    public static final String APPS_PREFIX = "apps/";
    public static final String CACHE_TREE_TOKEN = "c";
    public static final String CONF_TOKEN_INTENT_EXTRA = "conftoken";
    public static final String DATABASE_TREE_TOKEN = "db";
    public static final String DEVICE_CACHE_TREE_TOKEN = "d_c";
    public static final String DEVICE_DATABASE_TREE_TOKEN = "d_db";
    public static final String DEVICE_FILES_TREE_TOKEN = "d_f";
    public static final String DEVICE_NO_BACKUP_TREE_TOKEN = "d_nb";
    public static final String DEVICE_ROOT_TREE_TOKEN = "d_r";
    public static final String DEVICE_SHAREDPREFS_TREE_TOKEN = "d_sp";
    public static final String FILES_TREE_TOKEN = "f";
    private static final String FLAG_DISABLE_IF_NO_ENCRYPTION_CAPABILITIES = "disableIfNoEncryptionCapabilities";
    public static final String FLAG_REQUIRED_CLIENT_SIDE_ENCRYPTION = "clientSideEncryption";
    public static final String FLAG_REQUIRED_DEVICE_TO_DEVICE_TRANSFER = "deviceToDeviceTransfer";
    public static final String FLAG_REQUIRED_FAKE_CLIENT_SIDE_ENCRYPTION = "fakeClientSideEncryption";
    public static final String FULL_BACKUP_INTENT_ACTION = "fullback";
    public static final String FULL_RESTORE_INTENT_ACTION = "fullrest";
    private static final long IGNORE_FULL_BACKUP_CONTENT_IN_D2D = 180523564;
    public static final String KEY_VALUE_DATA_TOKEN = "k";
    public static final String MANAGED_EXTERNAL_SPECIFIC_TREE_RESTORE_TOKEN = "ef_s";
    public static final String MANAGED_EXTERNAL_SPECIFIC_TREE_TOKEN = "/storage/";
    public static final String MANAGED_EXTERNAL_TREE_TOKEN = "ef";
    public static final String NO_BACKUP_TREE_TOKEN = "nb";
    public static final String OBB_TREE_TOKEN = "obb";
    public static final String ROOT_TREE_TOKEN = "r";
    public static final String SHAREDPREFS_TREE_TOKEN = "sp";
    public static final String SHARED_PREFIX = "shared/";
    public static final String SHARED_STORAGE_TOKEN = "shared";
    static final String TAG = "FullBackup";
    static final String TAG_XML_PARSER = "BackupXmlParserLogging";
    private static final Map<BackupSchemeId, BackupScheme> kPackageBackupSchemeMap = new ArrayMap();

    @interface ConfigSection {
        public static final String CLOUD_BACKUP = "cloud-backup";
        public static final String DEVICE_TRANSFER = "device-transfer";
    }

    public static native int backupToTar(String str, String str2, String str3, String str4, String str5, FullBackupDataOutput fullBackupDataOutput);

    private static class BackupSchemeId {
        final int mBackupDestination;
        final String mPackageName;

        BackupSchemeId(String str, int i) {
            this.mPackageName = str;
            this.mBackupDestination = i;
        }

        public int hashCode() {
            return Objects.hash(this.mPackageName, Integer.valueOf(this.mBackupDestination));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                BackupSchemeId backupSchemeId = (BackupSchemeId) obj;
                if (Objects.equals(this.mPackageName, backupSchemeId.mPackageName) && Objects.equals(Integer.valueOf(this.mBackupDestination), Integer.valueOf(backupSchemeId.mBackupDestination))) {
                    return true;
                }
            }
            return false;
        }
    }

    static synchronized BackupScheme getBackupScheme(Context context, int i) {
        BackupScheme backupScheme;
        BackupSchemeId backupSchemeId = new BackupSchemeId(context.getPackageName(), i);
        Map<BackupSchemeId, BackupScheme> map = kPackageBackupSchemeMap;
        backupScheme = map.get(backupSchemeId);
        if (backupScheme == null) {
            backupScheme = new BackupScheme(context, i);
            map.put(backupSchemeId, backupScheme);
        }
        return backupScheme;
    }

    public static BackupScheme getBackupSchemeForTest(Context context) {
        BackupScheme backupScheme = new BackupScheme(context, 0);
        backupScheme.mExcludes = new ArraySet<>();
        backupScheme.mIncludes = new ArrayMap();
        return backupScheme;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00a3 A[EDGE_INSN: B:50:0x00a3->B:32:0x00a3 BREAK  A[LOOP:0: B:17:0x004e->B:31:0x00a0], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void restoreFile(ParcelFileDescriptor parcelFileDescriptor, long j, int i, long j2, long j3, File file) throws IOException, ErrnoException {
        FileOutputStream fileOutputStream;
        long j4;
        FileOutputStream fileOutputStream2;
        int i2;
        if (i == 2) {
            if (file != null) {
                file.mkdirs();
            }
        } else if (file != null) {
            try {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                fileOutputStream = new FileOutputStream(file);
            } catch (IOException e) {
                Log.e(TAG, "Unable to create/open file " + file.getPath(), e);
            }
            byte[] bArr = new byte[65536];
            FileInputStream fileInputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
            j4 = j;
            fileOutputStream2 = fileOutputStream;
            while (true) {
                if (j4 <= 0) {
                    break;
                }
                i2 = fileInputStream.read(bArr, 0, j4 > ((long) 65536) ? 65536 : (int) j4);
                if (i2 <= 0) {
                    Log.w(TAG, "Incomplete read: expected " + j4 + " but got " + (j - j4));
                    break;
                }
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.write(bArr, 0, i2);
                    } catch (IOException e2) {
                        Log.e(TAG, "Unable to write to file " + file.getPath(), e2);
                        fileOutputStream2.close();
                        file.delete();
                        fileOutputStream2 = null;
                    }
                }
                j4 -= i2;
            }
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
        } else {
            fileOutputStream = null;
            byte[] bArr2 = new byte[65536];
            FileInputStream fileInputStream2 = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
            j4 = j;
            fileOutputStream2 = fileOutputStream;
            while (true) {
                if (j4 <= 0) {
                }
                j4 -= i2;
            }
            if (fileOutputStream2 != null) {
            }
        }
        if (j2 < 0 || file == null) {
            return;
        }
        try {
            Os.chmod(file.getPath(), (int) (j2 & 448));
        } catch (ErrnoException e3) {
            e3.rethrowAsIOException();
        }
        file.setLastModified(j3);
    }

    public static class BackupScheme {
        private static final String TAG_EXCLUDE = "exclude";
        private static final String TAG_INCLUDE = "include";
        private final File CACHE_DIR;
        private final File DATABASE_DIR;
        private final File DEVICE_CACHE_DIR;
        private final File DEVICE_DATABASE_DIR;
        private final File DEVICE_FILES_DIR;
        private final File DEVICE_NOBACKUP_DIR;
        private final File DEVICE_ROOT_DIR;
        private final File DEVICE_SHAREDPREF_DIR;
        private final File EXTERNAL_DIR;
        private final File FILES_DIR;
        private final File NOBACKUP_DIR;
        private final File ROOT_DIR;
        private final File SHAREDPREF_DIR;
        final int mBackupDestination;
        final int mDataExtractionRules;
        ArraySet<PathWithRequiredFlags> mExcludes;
        final int mFullBackupContent;
        Map<String, Set<PathWithRequiredFlags>> mIncludes;
        private Boolean mIsUsingNewScheme;
        final PackageManager mPackageManager;
        final String mPackageName;
        private Integer mRequiredTransportFlags;
        final StorageManager mStorageManager;
        private StorageVolume[] mVolumes = null;
        private boolean mDisableDataExtractionRules = false;

        String tokenToDirectoryPath(String str) {
            try {
                if (str.equals(FullBackup.FILES_TREE_TOKEN)) {
                    return this.FILES_DIR.getCanonicalPath();
                }
                if (str.equals(FullBackup.DATABASE_TREE_TOKEN)) {
                    return this.DATABASE_DIR.getCanonicalPath();
                }
                if (str.equals("r")) {
                    return this.ROOT_DIR.getCanonicalPath();
                }
                if (str.equals(FullBackup.SHAREDPREFS_TREE_TOKEN)) {
                    return this.SHAREDPREF_DIR.getCanonicalPath();
                }
                if (str.equals("c")) {
                    return this.CACHE_DIR.getCanonicalPath();
                }
                if (str.equals("nb")) {
                    return this.NOBACKUP_DIR.getCanonicalPath();
                }
                if (str.equals(FullBackup.DEVICE_FILES_TREE_TOKEN)) {
                    return this.DEVICE_FILES_DIR.getCanonicalPath();
                }
                if (str.equals(FullBackup.DEVICE_DATABASE_TREE_TOKEN)) {
                    return this.DEVICE_DATABASE_DIR.getCanonicalPath();
                }
                if (str.equals(FullBackup.DEVICE_ROOT_TREE_TOKEN)) {
                    return this.DEVICE_ROOT_DIR.getCanonicalPath();
                }
                if (str.equals(FullBackup.DEVICE_SHAREDPREFS_TREE_TOKEN)) {
                    return this.DEVICE_SHAREDPREF_DIR.getCanonicalPath();
                }
                if (str.equals(FullBackup.DEVICE_CACHE_TREE_TOKEN)) {
                    return this.DEVICE_CACHE_DIR.getCanonicalPath();
                }
                if (str.equals(FullBackup.DEVICE_NO_BACKUP_TREE_TOKEN)) {
                    return this.DEVICE_NOBACKUP_DIR.getCanonicalPath();
                }
                if (str.equals(FullBackup.MANAGED_EXTERNAL_TREE_TOKEN)) {
                    File file = this.EXTERNAL_DIR;
                    if (file != null) {
                        return file.getCanonicalPath();
                    }
                    return null;
                }
                if (str.startsWith(FullBackup.SHARED_PREFIX)) {
                    return sharedDomainToPath(str);
                }
                if (str.startsWith(FullBackup.MANAGED_EXTERNAL_SPECIFIC_TREE_TOKEN)) {
                    return str;
                }
                if (!str.startsWith(FullBackup.MANAGED_EXTERNAL_SPECIFIC_TREE_RESTORE_TOKEN)) {
                    Log.i(FullBackup.TAG, "Unrecognized domain " + str);
                    return null;
                }
                String strSubstring = str.substring(4, str.length());
                return (this.EXTERNAL_DIR.getParent() + "/") + strSubstring;
            } catch (Exception unused) {
                Log.i(FullBackup.TAG, "Error reading directory for domain: " + str);
                return null;
            }
        }

        private String sharedDomainToPath(String str) throws NumberFormatException, IOException {
            String strSubstring = str.substring(7);
            StorageVolume[] volumeList = getVolumeList();
            int i = Integer.parseInt(strSubstring);
            if (i < this.mVolumes.length) {
                return volumeList[i].getPathFile().getCanonicalPath();
            }
            return null;
        }

        private StorageVolume[] getVolumeList() {
            StorageManager storageManager = this.mStorageManager;
            if (storageManager != null) {
                if (this.mVolumes == null) {
                    this.mVolumes = storageManager.getVolumeList();
                }
            } else {
                Log.e(FullBackup.TAG, "Unable to access Storage Manager");
            }
            return this.mVolumes;
        }

        public static class PathWithRequiredFlags {
            private final String mPath;
            private final int mRequiredFlags;

            public PathWithRequiredFlags(String str, int i) {
                this.mPath = str;
                this.mRequiredFlags = i;
            }

            public String getPath() {
                return this.mPath;
            }

            public int getRequiredFlags() {
                return this.mRequiredFlags;
            }
        }

        BackupScheme(Context context, int i) {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            this.mDataExtractionRules = applicationInfo.dataExtractionRulesRes;
            this.mFullBackupContent = applicationInfo.fullBackupContent;
            this.mBackupDestination = i;
            this.mStorageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            this.mPackageManager = context.getPackageManager();
            this.mPackageName = context.getPackageName();
            Context contextCreateCredentialProtectedStorageContext = context.createCredentialProtectedStorageContext();
            this.FILES_DIR = contextCreateCredentialProtectedStorageContext.getFilesDir();
            this.DATABASE_DIR = contextCreateCredentialProtectedStorageContext.getDatabasePath("foo").getParentFile();
            this.ROOT_DIR = contextCreateCredentialProtectedStorageContext.getDataDir();
            this.SHAREDPREF_DIR = contextCreateCredentialProtectedStorageContext.getSharedPreferencesPath("foo").getParentFile();
            this.CACHE_DIR = contextCreateCredentialProtectedStorageContext.getCacheDir();
            this.NOBACKUP_DIR = contextCreateCredentialProtectedStorageContext.getNoBackupFilesDir();
            Context contextCreateDeviceProtectedStorageContext = context.createDeviceProtectedStorageContext();
            this.DEVICE_FILES_DIR = contextCreateDeviceProtectedStorageContext.getFilesDir();
            this.DEVICE_DATABASE_DIR = contextCreateDeviceProtectedStorageContext.getDatabasePath("foo").getParentFile();
            this.DEVICE_ROOT_DIR = contextCreateDeviceProtectedStorageContext.getDataDir();
            this.DEVICE_SHAREDPREF_DIR = contextCreateDeviceProtectedStorageContext.getSharedPreferencesPath("foo").getParentFile();
            this.DEVICE_CACHE_DIR = contextCreateDeviceProtectedStorageContext.getCacheDir();
            this.DEVICE_NOBACKUP_DIR = contextCreateDeviceProtectedStorageContext.getNoBackupFilesDir();
            if (Process.myUid() != 1000) {
                this.EXTERNAL_DIR = context.getExternalFilesDir(null);
            } else {
                this.EXTERNAL_DIR = null;
            }
        }

        boolean isFullBackupEnabled(int i) {
            try {
                if (isUsingNewScheme()) {
                    int requiredTransportFlags = getRequiredTransportFlags();
                    return (i & requiredTransportFlags) == requiredTransportFlags;
                }
                return isFullBackupContentEnabled();
            } catch (IOException | XmlPullParserException e) {
                Slog.w(FullBackup.TAG, "Failed to interpret the backup scheme: " + e);
                return false;
            }
        }

        boolean isFullRestoreEnabled() {
            try {
                if (isUsingNewScheme()) {
                    return true;
                }
                return isFullBackupContentEnabled();
            } catch (IOException | XmlPullParserException e) {
                Slog.w(FullBackup.TAG, "Failed to interpret the backup scheme: " + e);
                return false;
            }
        }

        boolean isFullBackupContentEnabled() {
            if (this.mFullBackupContent >= 0) {
                return true;
            }
            if (!Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                return false;
            }
            Log.v(FullBackup.TAG_XML_PARSER, "android:fullBackupContent - \"false\"");
            return false;
        }

        public synchronized Map<String, Set<PathWithRequiredFlags>> maybeParseAndGetCanonicalIncludePaths() throws XmlPullParserException, IOException {
            if (this.mIncludes == null) {
                maybeParseBackupSchemeLocked();
            }
            return this.mIncludes;
        }

        void disableDataExtractionRule(boolean z) {
            Log.d(FullBackup.TAG, "disableDataExtractionRule in FullBackup.java, disable = " + z);
            this.mDisableDataExtractionRules = z;
        }

        public synchronized ArraySet<PathWithRequiredFlags> maybeParseAndGetCanonicalExcludePaths() throws XmlPullParserException, IOException {
            if (this.mExcludes == null) {
                maybeParseBackupSchemeLocked();
            }
            return this.mExcludes;
        }

        public synchronized int getRequiredTransportFlags() throws XmlPullParserException, IOException {
            if (this.mRequiredTransportFlags == null) {
                maybeParseBackupSchemeLocked();
            }
            return this.mRequiredTransportFlags.intValue();
        }

        private synchronized boolean isUsingNewScheme() throws XmlPullParserException, IOException {
            if (this.mIsUsingNewScheme == null) {
                maybeParseBackupSchemeLocked();
            }
            return this.mIsUsingNewScheme.booleanValue();
        }

        private void maybeParseBackupSchemeLocked() throws XmlPullParserException, IOException {
            this.mIncludes = new ArrayMap();
            this.mExcludes = new ArraySet<>();
            this.mRequiredTransportFlags = 0;
            this.mIsUsingNewScheme = false;
            if ((this.mFullBackupContent == 0 && this.mDataExtractionRules == 0) || this.mDisableDataExtractionRules) {
                if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                    Log.v(FullBackup.TAG_XML_PARSER, "android:fullBackupContent - \"true\"");
                    return;
                }
                return;
            }
            if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                Log.v(FullBackup.TAG_XML_PARSER, "Found xml scheme: android:fullBackupContent=" + this.mFullBackupContent + "; android:dataExtractionRules=" + this.mDataExtractionRules);
            }
            try {
                parseSchemeForBackupDestination(this.mBackupDestination);
            } catch (PackageManager.NameNotFoundException e) {
                throw new IOException(e);
            }
        }

        private void parseSchemeForBackupDestination(int i) throws XmlPullParserException, PackageManager.NameNotFoundException, IOException {
            XmlResourceParser parserForResource;
            String configSectionForBackupDestination = getConfigSectionForBackupDestination(i);
            if (configSectionForBackupDestination == null) {
                Slog.w(FullBackup.TAG, "Given backup destination isn't supported by backup scheme: " + i);
                return;
            }
            int i2 = this.mDataExtractionRules;
            if (i2 != 0) {
                parserForResource = getParserForResource(i2);
                try {
                    boolean newBackupSchemeFromXmlLocked = parseNewBackupSchemeFromXmlLocked(parserForResource, configSectionForBackupDestination, this.mExcludes, this.mIncludes);
                    if (parserForResource != null) {
                        parserForResource.close();
                    }
                    if (newBackupSchemeFromXmlLocked) {
                        this.mIsUsingNewScheme = true;
                        return;
                    }
                } finally {
                }
            }
            if (i == 1 && CompatChanges.isChangeEnabled(FullBackup.IGNORE_FULL_BACKUP_CONTENT_IN_D2D)) {
                this.mIsUsingNewScheme = true;
                return;
            }
            int i3 = this.mFullBackupContent;
            if (i3 != 0) {
                parserForResource = getParserForResource(i3);
                try {
                    parseBackupSchemeFromXmlLocked(parserForResource, this.mExcludes, this.mIncludes);
                    if (parserForResource != null) {
                        parserForResource.close();
                    }
                } finally {
                }
            }
        }

        private String getConfigSectionForBackupDestination(int i) {
            if (i == 0) {
                return ConfigSection.CLOUD_BACKUP;
            }
            if (i != 1) {
                return null;
            }
            return ConfigSection.DEVICE_TRANSFER;
        }

        private XmlResourceParser getParserForResource(int i) throws PackageManager.NameNotFoundException {
            return this.mPackageManager.getResourcesForApplication(this.mPackageName).getXml(i);
        }

        public boolean parseNewBackupSchemeFromXmlLocked(XmlPullParser xmlPullParser, String str, Set<PathWithRequiredFlags> set, Map<String, Set<PathWithRequiredFlags>> map) throws XmlPullParserException, IOException {
            verifyTopLevelTag(xmlPullParser, "data-extraction-rules");
            boolean z = false;
            while (true) {
                int next = xmlPullParser.next();
                if (next != 1) {
                    if (next == 2 && str.equals(xmlPullParser.getName())) {
                        this.parseRequiredTransportFlags(xmlPullParser, str);
                        this.parseRules(xmlPullParser, set, map, Optional.of(0), str);
                        z = true;
                    } else {
                        this = this;
                        xmlPullParser = xmlPullParser;
                        set = set;
                        map = map;
                        str = str;
                    }
                } else {
                    this.logParsingResults(set, map);
                    return z;
                }
            }
        }

        private void parseRequiredTransportFlags(XmlPullParser xmlPullParser, String str) {
            if (ConfigSection.CLOUD_BACKUP.equals(str) && "true".equals(xmlPullParser.getAttributeValue(null, FullBackup.FLAG_DISABLE_IF_NO_ENCRYPTION_CAPABILITIES))) {
                this.mRequiredTransportFlags = 1;
            }
        }

        public void parseBackupSchemeFromXmlLocked(XmlPullParser xmlPullParser, Set<PathWithRequiredFlags> set, Map<String, Set<PathWithRequiredFlags>> map) throws XmlPullParserException, IOException {
            verifyTopLevelTag(xmlPullParser, "full-backup-content");
            parseRules(xmlPullParser, set, map, Optional.empty(), "full-backup-content");
            logParsingResults(set, map);
        }

        private void verifyTopLevelTag(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
            int eventType = xmlPullParser.getEventType();
            while (eventType != 2) {
                eventType = xmlPullParser.next();
            }
            if (!str.equals(xmlPullParser.getName())) {
                throw new XmlPullParserException("Xml file didn't start with correct tag (" + str + " ). Found \"" + xmlPullParser.getName() + "\"");
            }
            if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                Log.v(FullBackup.TAG_XML_PARSER, ShaderAssembler.NEWLINE);
                Log.v(FullBackup.TAG_XML_PARSER, "====================================================");
                Log.v(FullBackup.TAG_XML_PARSER, "Found valid " + str + "; parsing xml resource.");
                Log.v(FullBackup.TAG_XML_PARSER, "====================================================");
                Log.v(FullBackup.TAG_XML_PARSER, "");
            }
        }

        private void parseRules(XmlPullParser xmlPullParser, Set<PathWithRequiredFlags> set, Map<String, Set<PathWithRequiredFlags>> map, Optional<Integer> optional, String str) throws XmlPullParserException, IOException {
            while (true) {
                int next = xmlPullParser.next();
                if (next == 1 || xmlPullParser.getName().equals(str)) {
                    return;
                }
                if (next == 2) {
                    validateInnerTagContents(xmlPullParser);
                    String attributeValue = xmlPullParser.getAttributeValue(null, "domain");
                    File directoryForCriteriaDomain = getDirectoryForCriteriaDomain(attributeValue);
                    if (directoryForCriteriaDomain == null) {
                        if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                            Log.v(FullBackup.TAG_XML_PARSER, "...parsing \"" + xmlPullParser.getName() + "\": domain=\"" + attributeValue + "\" invalid; skipping");
                        }
                    } else {
                        File fileExtractCanonicalFile = extractCanonicalFile(directoryForCriteriaDomain, xmlPullParser.getAttributeValue(null, "path"));
                        if (fileExtractCanonicalFile != null) {
                            int requiredFlagsForRule = getRequiredFlagsForRule(xmlPullParser, optional);
                            Set<PathWithRequiredFlags> currentTagForDomain = parseCurrentTagForDomain(xmlPullParser, set, map, attributeValue);
                            currentTagForDomain.add(new PathWithRequiredFlags(fileExtractCanonicalFile.getCanonicalPath(), requiredFlagsForRule));
                            if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                                Log.v(FullBackup.TAG_XML_PARSER, "...parsed " + fileExtractCanonicalFile.getCanonicalPath() + " for domain \"" + attributeValue + "\", requiredFlags + \"" + requiredFlagsForRule + "\"");
                            }
                            if ("database".equals(attributeValue) && !fileExtractCanonicalFile.isDirectory()) {
                                String str2 = fileExtractCanonicalFile.getCanonicalPath() + "-journal";
                                currentTagForDomain.add(new PathWithRequiredFlags(str2, requiredFlagsForRule));
                                if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                                    Log.v(FullBackup.TAG_XML_PARSER, "...automatically generated " + str2 + ". Ignore if nonexistent.");
                                }
                                String str3 = fileExtractCanonicalFile.getCanonicalPath() + "-wal";
                                currentTagForDomain.add(new PathWithRequiredFlags(str3, requiredFlagsForRule));
                                if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                                    Log.v(FullBackup.TAG_XML_PARSER, "...automatically generated " + str3 + ". Ignore if nonexistent.");
                                }
                            }
                            if ("sharedpref".equals(attributeValue) && !fileExtractCanonicalFile.isDirectory() && !fileExtractCanonicalFile.getCanonicalPath().endsWith(".xml")) {
                                String str4 = fileExtractCanonicalFile.getCanonicalPath() + ".xml";
                                currentTagForDomain.add(new PathWithRequiredFlags(str4, requiredFlagsForRule));
                                if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                                    Log.v(FullBackup.TAG_XML_PARSER, "...automatically generated " + str4 + ". Ignore if nonexistent.");
                                }
                            }
                        }
                    }
                }
            }
        }

        private void logParsingResults(Set<PathWithRequiredFlags> set, Map<String, Set<PathWithRequiredFlags>> map) {
            if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                Log.v(FullBackup.TAG_XML_PARSER, ShaderAssembler.NEWLINE);
                Log.v(FullBackup.TAG_XML_PARSER, "Xml resource parsing complete.");
                Log.v(FullBackup.TAG_XML_PARSER, "Final tally.");
                Log.v(FullBackup.TAG_XML_PARSER, "Includes:");
                if (map.isEmpty()) {
                    Log.v(FullBackup.TAG_XML_PARSER, "  ...nothing specified (This means the entirety of app data minus excludes)");
                } else {
                    for (Map.Entry<String, Set<PathWithRequiredFlags>> entry : map.entrySet()) {
                        Log.v(FullBackup.TAG_XML_PARSER, "  domain=" + entry.getKey());
                        for (PathWithRequiredFlags pathWithRequiredFlags : entry.getValue()) {
                            Log.v(FullBackup.TAG_XML_PARSER, " path: " + pathWithRequiredFlags.getPath() + " requiredFlags: " + pathWithRequiredFlags.getRequiredFlags());
                        }
                    }
                }
                Log.v(FullBackup.TAG_XML_PARSER, "Excludes:");
                if (set.isEmpty()) {
                    Log.v(FullBackup.TAG_XML_PARSER, "  ...nothing to exclude.");
                } else {
                    for (PathWithRequiredFlags pathWithRequiredFlags2 : set) {
                        Log.v(FullBackup.TAG_XML_PARSER, " path: " + pathWithRequiredFlags2.getPath() + " requiredFlags: " + pathWithRequiredFlags2.getRequiredFlags());
                    }
                }
                Log.v(FullBackup.TAG_XML_PARSER, "  ");
                Log.v(FullBackup.TAG_XML_PARSER, "====================================================");
                Log.v(FullBackup.TAG_XML_PARSER, ShaderAssembler.NEWLINE);
            }
        }

        private int getRequiredFlagsFromString(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            int i = 0;
            for (String str2 : str.split("\\|")) {
                str2.hashCode();
                switch (str2) {
                    case "fakeClientSideEncryption":
                        i |= Integer.MIN_VALUE;
                        break;
                    case "clientSideEncryption":
                        i |= 1;
                        continue;
                    case "deviceToDeviceTransfer":
                        i |= 2;
                        continue;
                }
                Log.w(FullBackup.TAG, "Unrecognized requiredFlag provided, value: \"" + str2 + "\"");
            }
            return i;
        }

        private int getRequiredFlagsForRule(XmlPullParser xmlPullParser, Optional<Integer> optional) {
            if (optional.isPresent()) {
                return optional.get().intValue();
            }
            if (TAG_INCLUDE.equals(xmlPullParser.getName())) {
                return getRequiredFlagsFromString(xmlPullParser.getAttributeValue(null, "requireFlags"));
            }
            return 0;
        }

        private Set<PathWithRequiredFlags> parseCurrentTagForDomain(XmlPullParser xmlPullParser, Set<PathWithRequiredFlags> set, Map<String, Set<PathWithRequiredFlags>> map, String str) throws XmlPullParserException {
            if (TAG_INCLUDE.equals(xmlPullParser.getName())) {
                String tokenForXmlDomain = getTokenForXmlDomain(str);
                Set<PathWithRequiredFlags> set2 = map.get(tokenForXmlDomain);
                if (set2 != null) {
                    return set2;
                }
                ArraySet arraySet = new ArraySet();
                map.put(tokenForXmlDomain, arraySet);
                return arraySet;
            }
            if (TAG_EXCLUDE.equals(xmlPullParser.getName())) {
                return set;
            }
            if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                Log.v(FullBackup.TAG_XML_PARSER, "Invalid tag found in xml \"" + xmlPullParser.getName() + "\"; aborting operation.");
            }
            throw new XmlPullParserException("Unrecognised tag in backup criteria xml (" + xmlPullParser.getName() + NavigationBarInflaterView.KEY_CODE_END);
        }

        private String getTokenForXmlDomain(String str) {
            if ("root".equals(str)) {
                return "r";
            }
            if ("file".equals(str)) {
                return FullBackup.FILES_TREE_TOKEN;
            }
            if ("database".equals(str)) {
                return FullBackup.DATABASE_TREE_TOKEN;
            }
            if ("sharedpref".equals(str)) {
                return FullBackup.SHAREDPREFS_TREE_TOKEN;
            }
            if ("device_root".equals(str)) {
                return FullBackup.DEVICE_ROOT_TREE_TOKEN;
            }
            if ("device_file".equals(str)) {
                return FullBackup.DEVICE_FILES_TREE_TOKEN;
            }
            if ("device_database".equals(str)) {
                return FullBackup.DEVICE_DATABASE_TREE_TOKEN;
            }
            if ("device_sharedpref".equals(str)) {
                return FullBackup.DEVICE_SHAREDPREFS_TREE_TOKEN;
            }
            if ("external".equals(str)) {
                return FullBackup.MANAGED_EXTERNAL_TREE_TOKEN;
            }
            return null;
        }

        private File extractCanonicalFile(File file, String str) {
            if (str == null) {
                str = "";
            }
            if (str.contains("..")) {
                if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                    Log.v(FullBackup.TAG_XML_PARSER, "...resolved \"" + file.getPath() + " " + str + "\", but the \"..\" path is not permitted; skipping.");
                }
                return null;
            }
            if (str.contains("//")) {
                if (Log.isLoggable(FullBackup.TAG_XML_PARSER, 2)) {
                    Log.v(FullBackup.TAG_XML_PARSER, "...resolved \"" + file.getPath() + " " + str + "\", which contains the invalid \"//\" sequence; skipping.");
                }
                return null;
            }
            return new File(file, str);
        }

        private File getDirectoryForCriteriaDomain(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            if ("file".equals(str)) {
                return this.FILES_DIR;
            }
            if ("database".equals(str)) {
                return this.DATABASE_DIR;
            }
            if ("root".equals(str)) {
                return this.ROOT_DIR;
            }
            if ("sharedpref".equals(str)) {
                return this.SHAREDPREF_DIR;
            }
            if ("device_file".equals(str)) {
                return this.DEVICE_FILES_DIR;
            }
            if ("device_database".equals(str)) {
                return this.DEVICE_DATABASE_DIR;
            }
            if ("device_root".equals(str)) {
                return this.DEVICE_ROOT_DIR;
            }
            if ("device_sharedpref".equals(str)) {
                return this.DEVICE_SHAREDPREF_DIR;
            }
            if ("external".equals(str)) {
                return this.EXTERNAL_DIR;
            }
            return null;
        }

        private void validateInnerTagContents(XmlPullParser xmlPullParser) throws XmlPullParserException {
            if (xmlPullParser == null) {
                return;
            }
            String name = xmlPullParser.getName();
            name.hashCode();
            if (name.equals(TAG_EXCLUDE)) {
                if (xmlPullParser.getAttributeCount() > 2) {
                    throw new XmlPullParserException("At most 2 tag attributes allowed for \"exclude\" tag (\"domain\" & \"path\".");
                }
            } else if (name.equals(TAG_INCLUDE)) {
                if (xmlPullParser.getAttributeCount() > 3) {
                    throw new XmlPullParserException("At most 3 tag attributes allowed for \"include\" tag (\"domain\" & \"path\" & optional \"requiredFlags\").");
                }
            } else {
                throw new XmlPullParserException("A valid tag is one of \"<include/>\" or \"<exclude/>. You provided \"" + xmlPullParser.getName() + "\"");
            }
        }
    }
}
