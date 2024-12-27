package android.app.backup;

import android.app.IBackupAgent;
import android.app.QueuedWork;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.util.ArraySet;
import android.util.Log;

import com.android.internal.infra.AndroidFuture;
import com.android.server.backup.Flags;

import libcore.io.IoUtils;

import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes.dex */
public abstract class BackupAgent extends ContextWrapper {
    private static final boolean DEBUG = false;
    private static final int DEFAULT_BACKUP_DESTINATION = 0;
    public static final int FLAG_CLIENT_SIDE_ENCRYPTION_ENABLED = 1;
    public static final int FLAG_DEVICE_TO_DEVICE_TRANSFER = 2;
    public static final int FLAG_FAKE_CLIENT_SIDE_ENCRYPTION_ENABLED = Integer.MIN_VALUE;
    public static final int FLAG_SKIP_RESTORE_FOR_LAUNCHED_APPS = 4;
    public static final int RESULT_ERROR = -1;
    public static final int RESULT_SUCCESS = 0;
    private static final String TAG = "BackupAgent";
    public static final int TYPE_DIRECTORY = 2;
    public static final int TYPE_EOF = 0;
    public static final int TYPE_FILE = 1;
    public static final int TYPE_SYMLINK = 3;
    private final ArrayList<String> dbFilesToCheck;
    private volatile int mBackupDestination;
    private final IBinder mBinder;
    boolean mDisableDataExtractionRule;
    Handler mHandler;
    private volatile BackupRestoreEventLogger mLogger;
    String[] mSmartSwitchBackupPath;
    private UserHandle mUser;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BackupTransportFlags {}

    public abstract void onBackup(
            ParcelFileDescriptor parcelFileDescriptor,
            BackupDataOutput backupDataOutput,
            ParcelFileDescriptor parcelFileDescriptor2)
            throws IOException;

    public abstract void onRestore(
            BackupDataInput backupDataInput, int i, ParcelFileDescriptor parcelFileDescriptor)
            throws IOException;

    Handler getHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
        return this.mHandler;
    }

    class SharedPrefsSynchronizer implements Runnable {
        public final CountDownLatch mLatch = new CountDownLatch(1);

        SharedPrefsSynchronizer() {}

        @Override // java.lang.Runnable
        public void run() {
            QueuedWork.waitToFinish();
            this.mLatch.countDown();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void waitForSharedPrefs() {
        Handler h = getHandler();
        SharedPrefsSynchronizer s = new SharedPrefsSynchronizer();
        h.postAtFrontOfQueue(s);
        try {
            s.mLatch.await();
        } catch (InterruptedException e) {
        }
    }

    public BackupRestoreEventLogger getBackupRestoreEventLogger() {
        return this.mLogger;
    }

    public BackupAgent() {
        super(null);
        this.dbFilesToCheck = new ArrayList<>();
        this.mHandler = null;
        this.mLogger = null;
        this.mBackupDestination = 0;
        this.mBinder = new BackupServiceBinder().asBinder();
    }

    public void onCreate() {}

    public void onCreate(UserHandle user) {
        this.mUser = user;
        onCreate();
    }

    @Deprecated
    public void onCreate(UserHandle user, int backupDestination) {
        this.mBackupDestination = backupDestination;
        onCreate(user);
    }

    public void onCreate(UserHandle user, int backupDestination, int operationType) {
        this.mBackupDestination = backupDestination;
        this.mLogger = new BackupRestoreEventLogger(operationType);
        onCreate(user, backupDestination);
    }

    public void onDestroy() {}

    public void onRestore(BackupDataInput data, long appVersionCode, ParcelFileDescriptor newState)
            throws IOException {
        onRestore(data, (int) appVersionCode, newState);
    }

    public void onRestore(
            BackupDataInput data,
            long appVersionCode,
            ParcelFileDescriptor newState,
            Set<String> excludedKeys)
            throws IOException {
        onRestore(data, appVersionCode, newState);
    }

    public void onFullBackup(FullBackupDataOutput data) throws IOException {
        String str;
        boolean z;
        BackupAgent backupAgent;
        FullBackup.BackupScheme backupScheme =
                FullBackup.getBackupScheme(this, this.mBackupDestination);
        if (this.mDisableDataExtractionRule) {
            backupScheme.disableDataExtractionRule(this.mDisableDataExtractionRule);
        }
        if (!backupScheme.isFullBackupEnabled(data.getTransportFlags())) {
            return;
        }
        try {
            IncludeExcludeRules includeExcludeRules = getIncludeExcludeRules(backupScheme);
            Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> manifestIncludeMap =
                    includeExcludeRules.getIncludeMap();
            Set<FullBackup.BackupScheme.PathWithRequiredFlags> manifestExcludeSet =
                    includeExcludeRules.getExcludeSet();
            String packageName = getPackageName();
            ApplicationInfo appInfo = getApplicationInfo();
            Context ceContext = createCredentialProtectedStorageContext();
            String rootDir = ceContext.getDataDir().getCanonicalPath();
            String filesDir = ceContext.getFilesDir().getCanonicalPath();
            String databaseDir =
                    ceContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
            String sharedPrefsDir =
                    ceContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
            Context deContext = createDeviceProtectedStorageContext();
            String deviceRootDir = deContext.getDataDir().getCanonicalPath();
            String deviceFilesDir = deContext.getFilesDir().getCanonicalPath();
            String deviceDatabaseDir =
                    deContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
            String deviceSharedPrefsDir =
                    deContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
            String deviceRootDir2 = appInfo.nativeLibraryDir;
            if (deviceRootDir2 != null) {
                str = new File(appInfo.nativeLibraryDir).getCanonicalPath();
            } else {
                str = null;
            }
            String libDir = str;
            ArraySet<String> traversalExcludeSet = new ArraySet<>();
            traversalExcludeSet.add(filesDir);
            traversalExcludeSet.add(databaseDir);
            traversalExcludeSet.add(sharedPrefsDir);
            traversalExcludeSet.add(deviceFilesDir);
            traversalExcludeSet.add(deviceDatabaseDir);
            traversalExcludeSet.add(deviceSharedPrefsDir);
            if (libDir != null) {
                traversalExcludeSet.add(libDir);
            }
            Set<String> extraExcludedDirs = getExtraExcludeDirsIfAny(ceContext);
            Set<String> extraExcludedDeviceDirs = getExtraExcludeDirsIfAny(deContext);
            traversalExcludeSet.addAll(extraExcludedDirs);
            traversalExcludeSet.addAll(extraExcludedDeviceDirs);
            applyXmlFiltersAndDoFullBackupForDomain(
                    packageName,
                    "r",
                    manifestIncludeMap,
                    manifestExcludeSet,
                    traversalExcludeSet,
                    data);
            traversalExcludeSet.add(rootDir);
            traversalExcludeSet.addAll(extraExcludedDirs);
            applyXmlFiltersAndDoFullBackupForDomain(
                    packageName,
                    FullBackup.DEVICE_ROOT_TREE_TOKEN,
                    manifestIncludeMap,
                    manifestExcludeSet,
                    traversalExcludeSet,
                    data);
            traversalExcludeSet.add(deviceRootDir);
            traversalExcludeSet.addAll(extraExcludedDeviceDirs);
            traversalExcludeSet.remove(filesDir);
            applyXmlFiltersAndDoFullBackupForDomain(
                    packageName,
                    FullBackup.FILES_TREE_TOKEN,
                    manifestIncludeMap,
                    manifestExcludeSet,
                    traversalExcludeSet,
                    data);
            traversalExcludeSet.add(filesDir);
            traversalExcludeSet.remove(deviceFilesDir);
            applyXmlFiltersAndDoFullBackupForDomain(
                    packageName,
                    FullBackup.DEVICE_FILES_TREE_TOKEN,
                    manifestIncludeMap,
                    manifestExcludeSet,
                    traversalExcludeSet,
                    data);
            traversalExcludeSet.add(deviceFilesDir);
            traversalExcludeSet.remove(databaseDir);
            applyXmlFiltersAndDoFullBackupForDomain(
                    packageName,
                    FullBackup.DATABASE_TREE_TOKEN,
                    manifestIncludeMap,
                    manifestExcludeSet,
                    traversalExcludeSet,
                    data);
            traversalExcludeSet.add(databaseDir);
            traversalExcludeSet.remove(deviceDatabaseDir);
            applyXmlFiltersAndDoFullBackupForDomain(
                    packageName,
                    FullBackup.DEVICE_DATABASE_TREE_TOKEN,
                    manifestIncludeMap,
                    manifestExcludeSet,
                    traversalExcludeSet,
                    data);
            traversalExcludeSet.add(deviceDatabaseDir);
            traversalExcludeSet.remove(sharedPrefsDir);
            applyXmlFiltersAndDoFullBackupForDomain(
                    packageName,
                    FullBackup.SHAREDPREFS_TREE_TOKEN,
                    manifestIncludeMap,
                    manifestExcludeSet,
                    traversalExcludeSet,
                    data);
            traversalExcludeSet.add(sharedPrefsDir);
            traversalExcludeSet.remove(deviceSharedPrefsDir);
            applyXmlFiltersAndDoFullBackupForDomain(
                    packageName,
                    FullBackup.DEVICE_SHAREDPREFS_TREE_TOKEN,
                    manifestIncludeMap,
                    manifestExcludeSet,
                    traversalExcludeSet,
                    data);
            traversalExcludeSet.add(deviceSharedPrefsDir);
            if (Process.myUid() != 1000) {
                backupAgent = this;
                File efLocation = backupAgent.getExternalFilesDir(null);
                if (efLocation != null) {
                    z = false;
                    applyXmlFiltersAndDoFullBackupForDomain(
                            packageName,
                            FullBackup.MANAGED_EXTERNAL_TREE_TOKEN,
                            manifestIncludeMap,
                            manifestExcludeSet,
                            traversalExcludeSet,
                            data);
                    if (backupAgent.mSmartSwitchBackupPath != null) {
                        String[] strArr = backupAgent.mSmartSwitchBackupPath;
                        int length = strArr.length;
                        int i = 0;
                        while (i < length) {
                            String path = strArr[i];
                            applyXmlFiltersAndDoFullBackupForDomain(
                                    packageName,
                                    path,
                                    manifestIncludeMap,
                                    manifestExcludeSet,
                                    traversalExcludeSet,
                                    data);
                            i++;
                            length = length;
                            strArr = strArr;
                        }
                        backupAgent.mSmartSwitchBackupPath = null;
                    }
                } else {
                    z = false;
                }
            } else {
                z = false;
                backupAgent = this;
            }
            backupAgent.mDisableDataExtractionRule = z;
        } catch (IOException | XmlPullParserException e) {
            if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                Log.v(
                        "BackupXmlParserLogging",
                        "Exception trying to parse fullBackupContent xml file! Aborting full"
                                + " backup.",
                        e);
            }
        }
    }

    private Set<String> getExtraExcludeDirsIfAny(Context context) throws IOException {
        Set<String> excludedDirs = new HashSet<>();
        excludedDirs.add(context.getCacheDir().getCanonicalPath());
        excludedDirs.add(context.getCodeCacheDir().getCanonicalPath());
        excludedDirs.add(context.getNoBackupFilesDir().getCanonicalPath());
        return Collections.unmodifiableSet(excludedDirs);
    }

    public IncludeExcludeRules getIncludeExcludeRules(FullBackup.BackupScheme backupScheme)
            throws IOException, XmlPullParserException {
        Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> manifestIncludeMap =
                backupScheme.maybeParseAndGetCanonicalIncludePaths();
        ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags> manifestExcludeSet =
                backupScheme.maybeParseAndGetCanonicalExcludePaths();
        return new IncludeExcludeRules(manifestIncludeMap, manifestExcludeSet);
    }

    public void onQuotaExceeded(long backupDataBytes, long quotaBytes) {}

    /* JADX INFO: Access modifiers changed from: private */
    public int getBackupUserId() {
        return this.mUser == null ? super.getUserId() : this.mUser.getIdentifier();
    }

    private void applyXmlFiltersAndDoFullBackupForDomain(
            String packageName,
            String domainToken,
            Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> includeMap,
            Set<FullBackup.BackupScheme.PathWithRequiredFlags> filterSet,
            ArraySet<String> traversalExcludeSet,
            FullBackupDataOutput data)
            throws IOException {
        if (includeMap == null || includeMap.size() == 0) {
            fullBackupFileTree(
                    packageName,
                    domainToken,
                    FullBackup.getBackupScheme(this, this.mBackupDestination)
                            .tokenToDirectoryPath(domainToken),
                    filterSet,
                    traversalExcludeSet,
                    data);
            return;
        }
        if (includeMap.get(domainToken) != null) {
            for (FullBackup.BackupScheme.PathWithRequiredFlags includeFile :
                    includeMap.get(domainToken)) {
                if (areIncludeRequiredTransportFlagsSatisfied(
                        includeFile.getRequiredFlags(), data.getTransportFlags())) {
                    fullBackupFileTree(
                            packageName,
                            domainToken,
                            includeFile.getPath(),
                            filterSet,
                            traversalExcludeSet,
                            data);
                }
            }
        }
    }

    private boolean areIncludeRequiredTransportFlagsSatisfied(
            int includeFlags, int transportFlags) {
        return (transportFlags & includeFlags) == includeFlags;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0123  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void fullBackupFile(
            java.io.File r30, android.app.backup.FullBackupDataOutput r31) {
        /*
            Method dump skipped, instructions count: 533
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.app.backup.BackupAgent.fullBackupFile(java.io.File,"
                        + " android.app.backup.FullBackupDataOutput):void");
    }

    protected final void fullBackupFileTree(
            String packageName,
            String domain,
            String startingPath,
            Set<FullBackup.BackupScheme.PathWithRequiredFlags> manifestExcludes,
            ArraySet<String> systemExcludes,
            FullBackupDataOutput output) {
        String domain2;
        File file;
        File file2;
        StructStat stat;
        File[] contents;
        BackupAgent backupAgent = this;
        String domainPath =
                FullBackup.getBackupScheme(backupAgent, backupAgent.mBackupDestination)
                        .tokenToDirectoryPath(domain);
        if (domainPath == null) {
            return;
        }
        if (!domain.startsWith(FullBackup.MANAGED_EXTERNAL_SPECIFIC_TREE_TOKEN)) {
            domain2 = domain;
        } else {
            int slash = domain.lastIndexOf(47);
            String specificDomain =
                    FullBackup.MANAGED_EXTERNAL_SPECIFIC_TREE_RESTORE_TOKEN
                            + domain.substring(slash + 1, domain.length());
            domain2 = specificDomain;
        }
        File rootFile = new File(startingPath);
        if (rootFile.exists()) {
            LinkedList<File> scanQueue = new LinkedList<>();
            scanQueue.add(rootFile);
            while (scanQueue.size() > 0) {
                File file3 = scanQueue.remove(0);
                try {
                    stat = Os.lstat(file3.getPath());
                } catch (ErrnoException e) {
                    e = e;
                    file2 = file3;
                } catch (IOException e2) {
                    file = file3;
                }
                if (!OsConstants.S_ISREG(stat.st_mode)) {
                    try {
                    } catch (ErrnoException e3) {
                        e = e3;
                        file2 = file3;
                        if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                            Log.v(
                                    "BackupXmlParserLogging",
                                    "Error scanning file " + file2 + " : " + e);
                        }
                        backupAgent = this;
                    } catch (IOException e4) {
                        file = file3;
                        if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                            Log.v("BackupXmlParserLogging", "Error canonicalizing path of " + file);
                        }
                        backupAgent = this;
                    }
                    if (!OsConstants.S_ISDIR(stat.st_mode)) {}
                }
                String filePath = file3.getCanonicalPath();
                if (manifestExcludes == null
                        || !backupAgent.manifestExcludesContainFilePath(
                                manifestExcludes, filePath)) {
                    if (systemExcludes == null || !systemExcludes.contains(filePath)) {
                        if (OsConstants.S_ISDIR(stat.st_mode)
                                && (contents = file3.listFiles()) != null) {
                            for (File entry : contents) {
                                scanQueue.add(0, entry);
                            }
                        }
                        FullBackup.backupToTar(
                                packageName, domain2, null, domainPath, filePath, output);
                        backupAgent = this;
                    }
                }
            }
        }
    }

    private boolean manifestExcludesContainFilePath(
            Set<FullBackup.BackupScheme.PathWithRequiredFlags> manifestExcludes, String filePath) {
        for (FullBackup.BackupScheme.PathWithRequiredFlags exclude : manifestExcludes) {
            String excludePath = exclude.getPath();
            if (excludePath != null && excludePath.equals(filePath)) {
                return true;
            }
        }
        return false;
    }

    public void onRestoreFile(
            ParcelFileDescriptor data, long size, File destination, int type, long mode, long mtime)
            throws IOException {
        Log.i(TAG, "mDisableDataExtractionRule = " + this.mDisableDataExtractionRule);
        if (this.mDisableDataExtractionRule) {
            FullBackup.restoreFile(data, size, type, mode, mtime, destination);
        } else {
            boolean accept = isFileEligibleForRestore(destination);
            FullBackup.restoreFile(data, size, type, mode, mtime, accept ? destination : null);
        }
    }

    private boolean isFileEligibleForRestore(File destination) throws IOException {
        FullBackup.BackupScheme bs = FullBackup.getBackupScheme(this, this.mBackupDestination);
        if (!bs.isFullRestoreEnabled()) {
            if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                Log.v(
                        "BackupXmlParserLogging",
                        "onRestoreFile \""
                                + destination.getCanonicalPath()
                                + "\" : fullBackupContent not enabled for "
                                + getPackageName());
            }
            return false;
        }
        String destinationCanonicalPath = destination.getCanonicalPath();
        try {
            Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> includes =
                    bs.maybeParseAndGetCanonicalIncludePaths();
            ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags> excludes =
                    bs.maybeParseAndGetCanonicalExcludePaths();
            if (excludes != null && BackupUtils.isFileSpecifiedInPathList(destination, excludes)) {
                if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                    Log.v(
                            "BackupXmlParserLogging",
                            "onRestoreFile: \""
                                    + destinationCanonicalPath
                                    + "\": listed in excludes; skipping.");
                }
                return false;
            }
            if (includes != null && !includes.isEmpty()) {
                boolean explicitlyIncluded = false;
                for (Set<FullBackup.BackupScheme.PathWithRequiredFlags> domainIncludes :
                        includes.values()) {
                    explicitlyIncluded |=
                            BackupUtils.isFileSpecifiedInPathList(destination, domainIncludes);
                    if (explicitlyIncluded) {
                        break;
                    }
                }
                if (!explicitlyIncluded) {
                    if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                        Log.v(
                                "BackupXmlParserLogging",
                                "onRestoreFile: Trying to restore \""
                                        + destinationCanonicalPath
                                        + "\" but it isn't specified in the included files;"
                                        + " skipping.");
                    }
                    return false;
                }
                return true;
            }
            return true;
        } catch (XmlPullParserException e) {
            if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                Log.v(
                        "BackupXmlParserLogging",
                        "onRestoreFile \""
                                + destinationCanonicalPath
                                + "\" : Exception trying to parse fullBackupContent xml file!"
                                + " Aborting onRestoreFile.",
                        e);
            }
            return false;
        }
    }

    private void checkDbFiles(String path, long size) {
        if (path.endsWith("-wal") && size > 0) {
            String pathToBeCleaned = path.substring(0, path.length() - "-wal".length());
            Log.i(TAG, "cdf: " + pathToBeCleaned);
            this.dbFilesToCheck.add(pathToBeCleaned);
        }
    }

    protected void onRestoreFile(
            ParcelFileDescriptor data,
            long size,
            int type,
            String domain,
            String path,
            long mode,
            long mtime)
            throws IOException {
        String basePath =
                FullBackup.getBackupScheme(this, this.mBackupDestination)
                        .tokenToDirectoryPath(domain);
        long mode2 =
                (domain.equals(FullBackup.MANAGED_EXTERNAL_TREE_TOKEN)
                                || domain.startsWith(
                                        FullBackup.MANAGED_EXTERNAL_SPECIFIC_TREE_RESTORE_TOKEN))
                        ? -1L
                        : mode;
        if (basePath != null) {
            File outFile = new File(basePath, path);
            String outPath = outFile.getCanonicalPath();
            if (outPath.startsWith(basePath + File.separatorChar)) {
                onRestoreFile(data, size, outFile, type, mode2, mtime);
                if (domain.equals(FullBackup.DATABASE_TREE_TOKEN)) {
                    checkDbFiles(outPath, size);
                    return;
                }
                return;
            }
        }
        FullBackup.restoreFile(data, size, type, mode2, mtime, null);
    }

    private boolean isStartWith(String path, byte[] arr) {
        if (new File(path).length() < arr.length) {
            return false;
        }
        try {
            RandomAccessFile file = new RandomAccessFile(path, "r");
            try {
                byte[] buf = new byte[arr.length];
                file.read(buf, 0, arr.length);
                for (int i = 0; i < buf.length; i++) {
                    if (buf[i] != arr[i]) {
                        file.close();
                        return false;
                    }
                }
                file.close();
                return true;
            } finally {
            }
        } catch (IOException e) {
            Log.d(TAG, "isStartWith threw", e);
            return false;
        }
    }

    private void doCleanDbFiles() {
        byte[] DB_HEADER = {
            83,
            81,
            76,
            105,
            116,
            101,
            32,
            102,
            111,
            114,
            109,
            SprAttributeBase.TYPE_ANIMATOR_SET,
            116,
            32,
            51
        };
        Iterator<String> it = this.dbFilesToCheck.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (isStartWith(s, DB_HEADER)) {
                int result = SQLiteDatabase.cleanDatabaseFile(s);
                Log.i(TAG, "dcdf: " + s + ", result: " + result);
            }
        }
        this.dbFilesToCheck.clear();
    }

    public void onRestoreFinished() {
        doCleanDbFiles();
    }

    public final void clearBackupRestoreEventLogger() {
        if (this.mLogger != null) {
            this.mLogger.clearData();
        }
    }

    public final IBinder onBind() {
        return this.mBinder;
    }

    public void attach(Context context) {
        this.dbFilesToCheck.clear();
        attachBaseContext(context);
    }

    private class BackupServiceBinder extends IBackupAgent.Stub {
        private static final String TAG = "BackupServiceBinder";

        private BackupServiceBinder() {}

        /* JADX WARN: Removed duplicated region for block: B:34:0x00cf  */
        /* JADX WARN: Removed duplicated region for block: B:36:? A[SYNTHETIC] */
        @Override // android.app.IBackupAgent
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void doBackup(
                android.os.ParcelFileDescriptor r18,
                android.os.ParcelFileDescriptor r19,
                android.os.ParcelFileDescriptor r20,
                long r21,
                android.app.backup.IBackupCallback r23,
                int r24)
                throws android.os.RemoteException {
            /*
                r17 = this;
                r1 = r17
                r2 = r23
                java.lang.String r3 = ") threw"
                java.lang.String r4 = "onBackup ("
                java.lang.String r5 = "BackupServiceBinder"
                android.app.backup.BackupDataOutput r0 = new android.app.backup.BackupDataOutput
                java.io.FileDescriptor r6 = r19.getFileDescriptor()
                r7 = r21
                r9 = r24
                r0.<init>(r6, r7, r9)
                r6 = r0
                r10 = -1
                long r12 = android.os.Binder.clearCallingIdentity()
                android.app.backup.BackupAgent r0 = android.app.backup.BackupAgent.this     // Catch: java.lang.Throwable -> L53 java.lang.RuntimeException -> L5c java.io.IOException -> L87
                r14 = r18
                r15 = r20
                r0.onBackup(r14, r6, r15)     // Catch: java.lang.Throwable -> L4b java.lang.RuntimeException -> L4d java.io.IOException -> L4f
                r3 = 0
                android.app.backup.BackupAgent r0 = android.app.backup.BackupAgent.this
                android.app.backup.BackupAgent.m635$$Nest$mwaitForSharedPrefs(r0)
                android.os.Binder.restoreCallingIdentity(r12)
                r2.operationComplete(r3)     // Catch: android.os.RemoteException -> L36
                goto L37
            L36:
                r0 = move-exception
            L37:
                int r0 = android.os.Binder.getCallingPid()
                int r5 = android.os.Process.myPid()
                if (r0 == r5) goto L4a
                libcore.io.IoUtils.closeQuietly(r18)
                libcore.io.IoUtils.closeQuietly(r19)
                libcore.io.IoUtils.closeQuietly(r20)
            L4a:
                return
            L4b:
                r0 = move-exception
                goto L58
            L4d:
                r0 = move-exception
                goto L61
            L4f:
                r0 = move-exception
                r16 = r6
                goto L8e
            L53:
                r0 = move-exception
                r14 = r18
                r15 = r20
            L58:
                r3 = r0
                r16 = r6
                goto Lb8
            L5c:
                r0 = move-exception
                r14 = r18
                r15 = r20
            L61:
                r16 = r6
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb6
                r6.<init>()     // Catch: java.lang.Throwable -> Lb6
                java.lang.StringBuilder r4 = r6.append(r4)     // Catch: java.lang.Throwable -> Lb6
                android.app.backup.BackupAgent r6 = android.app.backup.BackupAgent.this     // Catch: java.lang.Throwable -> Lb6
                java.lang.Class r6 = r6.getClass()     // Catch: java.lang.Throwable -> Lb6
                java.lang.String r6 = r6.getName()     // Catch: java.lang.Throwable -> Lb6
                java.lang.StringBuilder r4 = r4.append(r6)     // Catch: java.lang.Throwable -> Lb6
                java.lang.StringBuilder r3 = r4.append(r3)     // Catch: java.lang.Throwable -> Lb6
                java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> Lb6
                android.util.Log.d(r5, r3, r0)     // Catch: java.lang.Throwable -> Lb6
                throw r0     // Catch: java.lang.Throwable -> Lb6
            L87:
                r0 = move-exception
                r14 = r18
                r15 = r20
                r16 = r6
            L8e:
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb6
                r6.<init>()     // Catch: java.lang.Throwable -> Lb6
                java.lang.StringBuilder r4 = r6.append(r4)     // Catch: java.lang.Throwable -> Lb6
                android.app.backup.BackupAgent r6 = android.app.backup.BackupAgent.this     // Catch: java.lang.Throwable -> Lb6
                java.lang.Class r6 = r6.getClass()     // Catch: java.lang.Throwable -> Lb6
                java.lang.String r6 = r6.getName()     // Catch: java.lang.Throwable -> Lb6
                java.lang.StringBuilder r4 = r4.append(r6)     // Catch: java.lang.Throwable -> Lb6
                java.lang.StringBuilder r3 = r4.append(r3)     // Catch: java.lang.Throwable -> Lb6
                java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> Lb6
                android.util.Log.d(r5, r3, r0)     // Catch: java.lang.Throwable -> Lb6
                java.lang.RuntimeException r3 = new java.lang.RuntimeException     // Catch: java.lang.Throwable -> Lb6
                r3.<init>(r0)     // Catch: java.lang.Throwable -> Lb6
                throw r3     // Catch: java.lang.Throwable -> Lb6
            Lb6:
                r0 = move-exception
                r3 = r0
            Lb8:
                android.app.backup.BackupAgent r0 = android.app.backup.BackupAgent.this
                android.app.backup.BackupAgent.m635$$Nest$mwaitForSharedPrefs(r0)
                android.os.Binder.restoreCallingIdentity(r12)
                r2.operationComplete(r10)     // Catch: android.os.RemoteException -> Lc4
                goto Lc5
            Lc4:
                r0 = move-exception
            Lc5:
                int r0 = android.os.Binder.getCallingPid()
                int r4 = android.os.Process.myPid()
                if (r0 == r4) goto Ld8
                libcore.io.IoUtils.closeQuietly(r18)
                libcore.io.IoUtils.closeQuietly(r19)
                libcore.io.IoUtils.closeQuietly(r20)
            Ld8:
                throw r3
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " android.app.backup.BackupAgent.BackupServiceBinder.doBackup(android.os.ParcelFileDescriptor,"
                        + " android.os.ParcelFileDescriptor, android.os.ParcelFileDescriptor, long,"
                        + " android.app.backup.IBackupCallback, int):void");
        }

        @Override // android.app.IBackupAgent
        public void doRestore(
                ParcelFileDescriptor data,
                long appVersionCode,
                ParcelFileDescriptor newState,
                int token,
                IBackupManager callbackBinder)
                throws RemoteException {
            doRestoreInternal(data, appVersionCode, newState, token, callbackBinder, null);
        }

        @Override // android.app.IBackupAgent
        public void doRestoreWithExcludedKeys(
                ParcelFileDescriptor data,
                long appVersionCode,
                ParcelFileDescriptor newState,
                int token,
                IBackupManager callbackBinder,
                List<String> excludedKeys)
                throws RemoteException {
            doRestoreInternal(data, appVersionCode, newState, token, callbackBinder, excludedKeys);
        }

        /* JADX WARN: Removed duplicated region for block: B:43:0x00e8  */
        /* JADX WARN: Removed duplicated region for block: B:45:? A[SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r4v0, types: [java.util.Collection] */
        /* JADX WARN: Type inference failed for: r4v1 */
        /* JADX WARN: Type inference failed for: r4v6 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void doRestoreInternal(
                android.os.ParcelFileDescriptor r18,
                long r19,
                android.os.ParcelFileDescriptor r21,
                int r22,
                android.app.backup.IBackupManager r23,
                java.util.List<java.lang.String> r24)
                throws android.os.RemoteException {
            /*
                Method dump skipped, instructions count: 239
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " android.app.backup.BackupAgent.BackupServiceBinder.doRestoreInternal(android.os.ParcelFileDescriptor,"
                        + " long, android.os.ParcelFileDescriptor, int,"
                        + " android.app.backup.IBackupManager, java.util.List):void");
        }

        /* JADX WARN: Removed duplicated region for block: B:42:0x00fa  */
        /* JADX WARN: Removed duplicated region for block: B:44:? A[SYNTHETIC] */
        @Override // android.app.IBackupAgent
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void doFullBackup(
                android.os.ParcelFileDescriptor r17,
                long r18,
                int r20,
                android.app.backup.IBackupManager r21,
                int r22) {
            /*
                Method dump skipped, instructions count: 254
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " android.app.backup.BackupAgent.BackupServiceBinder.doFullBackup(android.os.ParcelFileDescriptor,"
                        + " long, int, android.app.backup.IBackupManager, int):void");
        }

        /* JADX WARN: Removed duplicated region for block: B:45:0x0103  */
        /* JADX WARN: Removed duplicated region for block: B:47:? A[SYNTHETIC] */
        @Override // android.app.IBackupAgent
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void doFullBackupPath(
                android.os.ParcelFileDescriptor r17,
                long r18,
                int r20,
                android.app.backup.IBackupManager r21,
                int r22,
                java.lang.String[] r23) {
            /*
                Method dump skipped, instructions count: 263
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " android.app.backup.BackupAgent.BackupServiceBinder.doFullBackupPath(android.os.ParcelFileDescriptor,"
                        + " long, int, android.app.backup.IBackupManager, int,"
                        + " java.lang.String[]):void");
        }

        @Override // android.app.IBackupAgent
        public void doDisableDataExtractionRules(boolean disable) {
            BackupAgent.this.mDisableDataExtractionRule = disable;
        }

        @Override // android.app.IBackupAgent
        public void doMeasureFullBackup(
                long quotaBytes, int token, IBackupManager callbackBinder, int transportFlags) {
            FullBackupDataOutput measureOutput =
                    new FullBackupDataOutput(quotaBytes, transportFlags);
            BackupAgent.this.waitForSharedPrefs();
            long ident = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.onFullBackup(measureOutput);
                    Binder.restoreCallingIdentity(ident);
                    try {
                        callbackBinder.opCompleteForUser(
                                BackupAgent.this.getBackupUserId(), token, measureOutput.getSize());
                    } catch (RemoteException e) {
                    }
                } catch (IOException ex) {
                    Log.d(
                            TAG,
                            "onFullBackup[M] (" + BackupAgent.this.getClass().getName() + ") threw",
                            ex);
                    throw new RuntimeException(ex);
                } catch (RuntimeException ex2) {
                    Log.d(
                            TAG,
                            "onFullBackup[M] (" + BackupAgent.this.getClass().getName() + ") threw",
                            ex2);
                    throw ex2;
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(ident);
                try {
                    callbackBinder.opCompleteForUser(
                            BackupAgent.this.getBackupUserId(), token, measureOutput.getSize());
                } catch (RemoteException e2) {
                }
                throw th;
            }
        }

        @Override // android.app.IBackupAgent
        public void doRestoreFile(
                ParcelFileDescriptor data,
                long size,
                int type,
                String domain,
                String path,
                long mode,
                long mtime,
                int token,
                IBackupManager callbackBinder)
                throws RemoteException {
            long ident = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.onRestoreFile(data, size, type, domain, path, mode, mtime);
                    BackupAgent.this.waitForSharedPrefs();
                    BackupAgent.this.reloadSharedPreferences();
                    if (Flags.enableClearPipeAfterRestoreFile()) {
                        clearUnconsumedDataFromPipe(data, size);
                    }
                    Binder.restoreCallingIdentity(ident);
                    try {
                        callbackBinder.opCompleteForUser(
                                BackupAgent.this.getBackupUserId(), token, 0L);
                    } catch (RemoteException e) {
                    }
                    if (Binder.getCallingPid() != Process.myPid()) {
                        IoUtils.closeQuietly(data);
                    }
                } catch (IOException e2) {
                    Log.d(
                            TAG,
                            "onRestoreFile (" + BackupAgent.this.getClass().getName() + ") threw",
                            e2);
                    throw new RuntimeException(e2);
                }
            } finally {
            }
        }

        private static void clearUnconsumedDataFromPipe(ParcelFileDescriptor data, long size) {
            try {
                FileInputStream in = new FileInputStream(data.getFileDescriptor());
                try {
                    if (in.available() > 0) {
                        in.skip(size);
                    }
                    in.close();
                } finally {
                }
            } catch (IOException e) {
                Log.w(TAG, "Failed to clear unconsumed data from pipe.", e);
            }
        }

        @Override // android.app.IBackupAgent
        public void doRestoreFinished(int token, IBackupManager callbackBinder) {
            long ident = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.onRestoreFinished();
                    BackupAgent.this.waitForSharedPrefs();
                    Binder.restoreCallingIdentity(ident);
                    try {
                        callbackBinder.opCompleteForUser(
                                BackupAgent.this.getBackupUserId(), token, 0L);
                    } catch (RemoteException e) {
                    }
                } catch (Exception e2) {
                    Log.d(
                            TAG,
                            "onRestoreFinished ("
                                    + BackupAgent.this.getClass().getName()
                                    + ") threw",
                            e2);
                    throw e2;
                }
            } catch (Throwable th) {
                BackupAgent.this.waitForSharedPrefs();
                Binder.restoreCallingIdentity(ident);
                try {
                    callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, 0L);
                } catch (RemoteException e3) {
                }
                throw th;
            }
        }

        @Override // android.app.IBackupAgent
        public void fail(String message) {
            BackupAgent.this.getHandler().post(new FailRunnable(message));
        }

        @Override // android.app.IBackupAgent
        public void doQuotaExceeded(
                long backupDataBytes, long quotaBytes, IBackupCallback callbackBinder) {
            long ident = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.onQuotaExceeded(backupDataBytes, quotaBytes);
                    BackupAgent.this.waitForSharedPrefs();
                    Binder.restoreCallingIdentity(ident);
                    try {
                        callbackBinder.operationComplete(0L);
                    } catch (RemoteException e) {
                    }
                } catch (Throwable th) {
                    BackupAgent.this.waitForSharedPrefs();
                    Binder.restoreCallingIdentity(ident);
                    try {
                        callbackBinder.operationComplete(-1L);
                    } catch (RemoteException e2) {
                    }
                    throw th;
                }
            } catch (Exception e3) {
                Log.d(
                        TAG,
                        "onQuotaExceeded(" + BackupAgent.this.getClass().getName() + ") threw",
                        e3);
                throw e3;
            }
        }

        @Override // android.app.IBackupAgent
        public void getLoggerResults(
                AndroidFuture<List<BackupRestoreEventLogger.DataTypeResult>> in) {
            if (BackupAgent.this.mLogger != null) {
                in.complete(BackupAgent.this.mLogger.getLoggingResults());
            } else {
                in.complete(Collections.emptyList());
            }
        }

        @Override // android.app.IBackupAgent
        public void getOperationType(AndroidFuture<Integer> in) {
            in.complete(
                    Integer.valueOf(
                            BackupAgent.this.mLogger == null
                                    ? -1
                                    : BackupAgent.this.mLogger.getOperationType()));
        }

        @Override // android.app.IBackupAgent
        public void clearBackupRestoreEventLogger() {
            long ident = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.clearBackupRestoreEventLogger();
                } catch (Exception e) {
                    Log.d(
                            TAG,
                            "clearBackupRestoreEventLogger ("
                                    + BackupAgent.this.getClass().getName()
                                    + ") threw",
                            e);
                    throw e;
                }
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }
    }

    static class FailRunnable implements Runnable {
        private String mMessage;

        FailRunnable(String message) {
            this.mMessage = message;
        }

        @Override // java.lang.Runnable
        public void run() {
            throw new IllegalStateException(this.mMessage);
        }
    }

    public static class IncludeExcludeRules {
        private final Set<FullBackup.BackupScheme.PathWithRequiredFlags> mManifestExcludeSet;
        private final Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>>
                mManifestIncludeMap;

        public IncludeExcludeRules(
                Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> manifestIncludeMap,
                Set<FullBackup.BackupScheme.PathWithRequiredFlags> manifestExcludeSet) {
            this.mManifestIncludeMap = manifestIncludeMap;
            this.mManifestExcludeSet = manifestExcludeSet;
        }

        public static IncludeExcludeRules emptyRules() {
            return new IncludeExcludeRules(Collections.emptyMap(), new ArraySet());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> getIncludeMap() {
            return this.mManifestIncludeMap;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Set<FullBackup.BackupScheme.PathWithRequiredFlags> getExcludeSet() {
            return this.mManifestExcludeSet;
        }

        public int hashCode() {
            return Objects.hash(this.mManifestIncludeMap, this.mManifestExcludeSet);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            IncludeExcludeRules that = (IncludeExcludeRules) object;
            if (Objects.equals(this.mManifestIncludeMap, that.mManifestIncludeMap)
                    && Objects.equals(this.mManifestExcludeSet, that.mManifestExcludeSet)) {
                return true;
            }
            return false;
        }
    }
}
