package android.app.backup;

import android.app.IBackupAgent;
import android.app.QueuedWork;
import android.app.backup.BackupRestoreEventLogger;
import android.app.backup.FullBackup;
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
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
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
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

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
    public @interface BackupTransportFlags {
    }

    private boolean areIncludeRequiredTransportFlagsSatisfied(int i, int i2) {
        return (i2 & i) == i;
    }

    public abstract void onBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) throws IOException;

    public void onCreate() {
    }

    public void onDestroy() {
    }

    public void onQuotaExceeded(long j, long j2) {
    }

    public abstract void onRestore(BackupDataInput backupDataInput, int i, ParcelFileDescriptor parcelFileDescriptor) throws IOException;

    Handler getHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
        return this.mHandler;
    }

    class SharedPrefsSynchronizer implements Runnable {
        public final CountDownLatch mLatch = new CountDownLatch(1);

        SharedPrefsSynchronizer(BackupAgent backupAgent) {
        }

        @Override // java.lang.Runnable
        public void run() {
            QueuedWork.waitToFinish();
            this.mLatch.countDown();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void waitForSharedPrefs() {
        Handler handler = getHandler();
        SharedPrefsSynchronizer sharedPrefsSynchronizer = new SharedPrefsSynchronizer(this);
        handler.postAtFrontOfQueue(sharedPrefsSynchronizer);
        try {
            sharedPrefsSynchronizer.mLatch.await();
        } catch (InterruptedException unused) {
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

    public void onCreate(UserHandle userHandle) {
        this.mUser = userHandle;
        onCreate();
    }

    @Deprecated
    public void onCreate(UserHandle userHandle, int i) {
        this.mBackupDestination = i;
        onCreate(userHandle);
    }

    public void onCreate(UserHandle userHandle, int i, int i2) {
        this.mBackupDestination = i;
        this.mLogger = new BackupRestoreEventLogger(i2);
        onCreate(userHandle, i);
    }

    public void onRestore(BackupDataInput backupDataInput, long j, ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        onRestore(backupDataInput, (int) j, parcelFileDescriptor);
    }

    public void onRestore(BackupDataInput backupDataInput, long j, ParcelFileDescriptor parcelFileDescriptor, Set<String> set) throws IOException {
        onRestore(backupDataInput, j, parcelFileDescriptor);
    }

    public void onFullBackup(FullBackupDataOutput fullBackupDataOutput) throws IOException {
        FullBackup.BackupScheme backupScheme = FullBackup.getBackupScheme(this, this.mBackupDestination);
        boolean z = this.mDisableDataExtractionRule;
        if (z) {
            backupScheme.disableDataExtractionRule(z);
        }
        if (backupScheme.isFullBackupEnabled(fullBackupDataOutput.getTransportFlags())) {
            try {
                IncludeExcludeRules includeExcludeRules = getIncludeExcludeRules(backupScheme);
                Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> includeMap = includeExcludeRules.getIncludeMap();
                Set<FullBackup.BackupScheme.PathWithRequiredFlags> excludeSet = includeExcludeRules.getExcludeSet();
                String packageName = getPackageName();
                ApplicationInfo applicationInfo = getApplicationInfo();
                Context createCredentialProtectedStorageContext = createCredentialProtectedStorageContext();
                String canonicalPath = createCredentialProtectedStorageContext.getDataDir().getCanonicalPath();
                String canonicalPath2 = createCredentialProtectedStorageContext.getFilesDir().getCanonicalPath();
                String canonicalPath3 = createCredentialProtectedStorageContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
                String canonicalPath4 = createCredentialProtectedStorageContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
                Context createDeviceProtectedStorageContext = createDeviceProtectedStorageContext();
                String canonicalPath5 = createDeviceProtectedStorageContext.getDataDir().getCanonicalPath();
                String canonicalPath6 = createDeviceProtectedStorageContext.getFilesDir().getCanonicalPath();
                String canonicalPath7 = createDeviceProtectedStorageContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
                String canonicalPath8 = createDeviceProtectedStorageContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
                String canonicalPath9 = applicationInfo.nativeLibraryDir != null ? new File(applicationInfo.nativeLibraryDir).getCanonicalPath() : null;
                ArraySet<String> arraySet = new ArraySet<>();
                arraySet.add(canonicalPath2);
                arraySet.add(canonicalPath3);
                arraySet.add(canonicalPath4);
                arraySet.add(canonicalPath6);
                arraySet.add(canonicalPath7);
                arraySet.add(canonicalPath8);
                if (canonicalPath9 != null) {
                    arraySet.add(canonicalPath9);
                }
                Set<String> extraExcludeDirsIfAny = getExtraExcludeDirsIfAny(createCredentialProtectedStorageContext);
                Set<String> extraExcludeDirsIfAny2 = getExtraExcludeDirsIfAny(createDeviceProtectedStorageContext);
                arraySet.addAll(extraExcludeDirsIfAny);
                arraySet.addAll(extraExcludeDirsIfAny2);
                applyXmlFiltersAndDoFullBackupForDomain(packageName, "r", includeMap, excludeSet, arraySet, fullBackupDataOutput);
                arraySet.add(canonicalPath);
                arraySet.addAll(extraExcludeDirsIfAny);
                applyXmlFiltersAndDoFullBackupForDomain(packageName, FullBackup.DEVICE_ROOT_TREE_TOKEN, includeMap, excludeSet, arraySet, fullBackupDataOutput);
                arraySet.add(canonicalPath5);
                arraySet.addAll(extraExcludeDirsIfAny2);
                arraySet.remove(canonicalPath2);
                applyXmlFiltersAndDoFullBackupForDomain(packageName, FullBackup.FILES_TREE_TOKEN, includeMap, excludeSet, arraySet, fullBackupDataOutput);
                arraySet.add(canonicalPath2);
                arraySet.remove(canonicalPath6);
                applyXmlFiltersAndDoFullBackupForDomain(packageName, FullBackup.DEVICE_FILES_TREE_TOKEN, includeMap, excludeSet, arraySet, fullBackupDataOutput);
                arraySet.add(canonicalPath6);
                arraySet.remove(canonicalPath3);
                applyXmlFiltersAndDoFullBackupForDomain(packageName, FullBackup.DATABASE_TREE_TOKEN, includeMap, excludeSet, arraySet, fullBackupDataOutput);
                arraySet.add(canonicalPath3);
                arraySet.remove(canonicalPath7);
                applyXmlFiltersAndDoFullBackupForDomain(packageName, FullBackup.DEVICE_DATABASE_TREE_TOKEN, includeMap, excludeSet, arraySet, fullBackupDataOutput);
                arraySet.add(canonicalPath7);
                arraySet.remove(canonicalPath4);
                applyXmlFiltersAndDoFullBackupForDomain(packageName, FullBackup.SHAREDPREFS_TREE_TOKEN, includeMap, excludeSet, arraySet, fullBackupDataOutput);
                arraySet.add(canonicalPath4);
                arraySet.remove(canonicalPath8);
                applyXmlFiltersAndDoFullBackupForDomain(packageName, FullBackup.DEVICE_SHAREDPREFS_TREE_TOKEN, includeMap, excludeSet, arraySet, fullBackupDataOutput);
                arraySet.add(canonicalPath8);
                if (Process.myUid() != 1000 && getExternalFilesDir(null) != null) {
                    applyXmlFiltersAndDoFullBackupForDomain(packageName, FullBackup.MANAGED_EXTERNAL_TREE_TOKEN, includeMap, excludeSet, arraySet, fullBackupDataOutput);
                    String[] strArr = this.mSmartSwitchBackupPath;
                    if (strArr != null) {
                        for (String str : strArr) {
                            applyXmlFiltersAndDoFullBackupForDomain(packageName, str, includeMap, excludeSet, arraySet, fullBackupDataOutput);
                        }
                        this.mSmartSwitchBackupPath = null;
                    }
                }
                this.mDisableDataExtractionRule = false;
            } catch (IOException | XmlPullParserException e) {
                if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                    Log.v("BackupXmlParserLogging", "Exception trying to parse fullBackupContent xml file! Aborting full backup.", e);
                }
            }
        }
    }

    private Set<String> getExtraExcludeDirsIfAny(Context context) throws IOException {
        HashSet hashSet = new HashSet();
        hashSet.add(context.getCacheDir().getCanonicalPath());
        hashSet.add(context.getCodeCacheDir().getCanonicalPath());
        hashSet.add(context.getNoBackupFilesDir().getCanonicalPath());
        return Collections.unmodifiableSet(hashSet);
    }

    public IncludeExcludeRules getIncludeExcludeRules(FullBackup.BackupScheme backupScheme) throws IOException, XmlPullParserException {
        return new IncludeExcludeRules(backupScheme.maybeParseAndGetCanonicalIncludePaths(), backupScheme.maybeParseAndGetCanonicalExcludePaths());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getBackupUserId() {
        UserHandle userHandle = this.mUser;
        return userHandle == null ? super.getUserId() : userHandle.getIdentifier();
    }

    private void applyXmlFiltersAndDoFullBackupForDomain(String str, String str2, Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> map, Set<FullBackup.BackupScheme.PathWithRequiredFlags> set, ArraySet<String> arraySet, FullBackupDataOutput fullBackupDataOutput) throws IOException {
        if (map == null || map.size() == 0) {
            fullBackupFileTree(str, str2, FullBackup.getBackupScheme(this, this.mBackupDestination).tokenToDirectoryPath(str2), set, arraySet, fullBackupDataOutput);
            return;
        }
        if (map.get(str2) != null) {
            for (FullBackup.BackupScheme.PathWithRequiredFlags pathWithRequiredFlags : map.get(str2)) {
                if (areIncludeRequiredTransportFlagsSatisfied(pathWithRequiredFlags.getRequiredFlags(), fullBackupDataOutput.getTransportFlags())) {
                    fullBackupFileTree(str, str2, pathWithRequiredFlags.getPath(), set, arraySet, fullBackupDataOutput);
                }
            }
        }
    }

    public final void fullBackupFile(File file, FullBackupDataOutput fullBackupDataOutput) {
        String str;
        String str2;
        String str3;
        String str4;
        ApplicationInfo applicationInfo = getApplicationInfo();
        try {
            Context createCredentialProtectedStorageContext = createCredentialProtectedStorageContext();
            String canonicalPath = createCredentialProtectedStorageContext.getDataDir().getCanonicalPath();
            String canonicalPath2 = createCredentialProtectedStorageContext.getFilesDir().getCanonicalPath();
            String canonicalPath3 = createCredentialProtectedStorageContext.getNoBackupFilesDir().getCanonicalPath();
            String canonicalPath4 = createCredentialProtectedStorageContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
            String canonicalPath5 = createCredentialProtectedStorageContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
            String canonicalPath6 = createCredentialProtectedStorageContext.getCacheDir().getCanonicalPath();
            String canonicalPath7 = createCredentialProtectedStorageContext.getCodeCacheDir().getCanonicalPath();
            Context createDeviceProtectedStorageContext = createDeviceProtectedStorageContext();
            String canonicalPath8 = createDeviceProtectedStorageContext.getDataDir().getCanonicalPath();
            String canonicalPath9 = createDeviceProtectedStorageContext.getFilesDir().getCanonicalPath();
            String canonicalPath10 = createDeviceProtectedStorageContext.getNoBackupFilesDir().getCanonicalPath();
            String canonicalPath11 = createDeviceProtectedStorageContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
            String canonicalPath12 = createDeviceProtectedStorageContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
            String canonicalPath13 = createDeviceProtectedStorageContext.getCacheDir().getCanonicalPath();
            String canonicalPath14 = createDeviceProtectedStorageContext.getCodeCacheDir().getCanonicalPath();
            try {
                String canonicalPath15 = applicationInfo.nativeLibraryDir == null ? null : new File(applicationInfo.nativeLibraryDir).getCanonicalPath();
                if (Process.myUid() != 1000) {
                    str2 = null;
                    File externalFilesDir = getExternalFilesDir(null);
                    if (externalFilesDir != null) {
                        str2 = externalFilesDir.getCanonicalPath();
                    }
                } else {
                    str2 = null;
                }
                String canonicalPath16 = file.getCanonicalPath();
                if (canonicalPath16.startsWith(canonicalPath6) || canonicalPath16.startsWith(canonicalPath7) || canonicalPath16.startsWith(canonicalPath3) || canonicalPath16.startsWith(canonicalPath13) || canonicalPath16.startsWith(canonicalPath14) || canonicalPath16.startsWith(canonicalPath10) || canonicalPath16.startsWith(canonicalPath15)) {
                    Log.w(TAG, "lib, cache, code_cache, and no_backup files are not backed up");
                    return;
                }
                if (canonicalPath16.startsWith(canonicalPath4)) {
                    str3 = FullBackup.DATABASE_TREE_TOKEN;
                    str4 = canonicalPath4;
                } else if (canonicalPath16.startsWith(canonicalPath5)) {
                    str3 = FullBackup.SHAREDPREFS_TREE_TOKEN;
                    str4 = canonicalPath5;
                } else if (canonicalPath16.startsWith(canonicalPath2)) {
                    str3 = FullBackup.FILES_TREE_TOKEN;
                    str4 = canonicalPath2;
                } else if (canonicalPath16.startsWith(canonicalPath)) {
                    str3 = "r";
                    str4 = canonicalPath;
                } else if (canonicalPath16.startsWith(canonicalPath11)) {
                    str3 = FullBackup.DEVICE_DATABASE_TREE_TOKEN;
                    str4 = canonicalPath11;
                } else if (canonicalPath16.startsWith(canonicalPath12)) {
                    str3 = FullBackup.DEVICE_SHAREDPREFS_TREE_TOKEN;
                    str4 = canonicalPath12;
                } else if (canonicalPath16.startsWith(canonicalPath9)) {
                    str3 = FullBackup.DEVICE_FILES_TREE_TOKEN;
                    str4 = canonicalPath9;
                } else if (canonicalPath16.startsWith(canonicalPath8)) {
                    str4 = canonicalPath8;
                    str3 = FullBackup.DEVICE_ROOT_TREE_TOKEN;
                } else if (str2 != null && canonicalPath16.startsWith(str2)) {
                    str3 = FullBackup.MANAGED_EXTERNAL_TREE_TOKEN;
                    str4 = str2;
                } else {
                    Log.w(TAG, "File " + canonicalPath16 + " is in an unsupported location; skipping");
                    return;
                }
                FullBackup.backupToTar(getPackageName(), str3, null, str4, canonicalPath16, fullBackupDataOutput);
            } catch (IOException unused) {
                str = TAG;
                Log.w(str, "Unable to obtain canonical paths");
            }
        } catch (IOException unused2) {
            str = TAG;
        }
    }

    protected final void fullBackupFileTree(String str, String str2, String str3, Set<FullBackup.BackupScheme.PathWithRequiredFlags> set, ArraySet<String> arraySet, FullBackupDataOutput fullBackupDataOutput) {
        File[] listFiles;
        String str4 = str2;
        String str5 = FullBackup.getBackupScheme(this, this.mBackupDestination).tokenToDirectoryPath(str4);
        if (str5 == null) {
            return;
        }
        if (str4.startsWith(FullBackup.MANAGED_EXTERNAL_SPECIFIC_TREE_TOKEN)) {
            str4 = FullBackup.MANAGED_EXTERNAL_SPECIFIC_TREE_RESTORE_TOKEN + str4.substring(str4.lastIndexOf(47) + 1, str4.length());
        }
        String str6 = str4;
        File file = new File(str3);
        if (file.exists()) {
            LinkedList linkedList = new LinkedList();
            linkedList.add(file);
            while (linkedList.size() > 0) {
                File file2 = (File) linkedList.remove(0);
                try {
                    StructStat lstat = Os.lstat(file2.getPath());
                    if (OsConstants.S_ISREG(lstat.st_mode) || OsConstants.S_ISDIR(lstat.st_mode)) {
                        String canonicalPath = file2.getCanonicalPath();
                        if (set == null || !manifestExcludesContainFilePath(set, canonicalPath)) {
                            if (arraySet == null || !arraySet.contains(canonicalPath)) {
                                if (OsConstants.S_ISDIR(lstat.st_mode) && (listFiles = file2.listFiles()) != null) {
                                    for (File file3 : listFiles) {
                                        linkedList.add(0, file3);
                                    }
                                }
                                FullBackup.backupToTar(str, str6, null, str5, canonicalPath, fullBackupDataOutput);
                            }
                        }
                    }
                } catch (ErrnoException e) {
                    if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                        Log.v("BackupXmlParserLogging", "Error scanning file " + file2 + " : " + e);
                    }
                } catch (IOException unused) {
                    if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                        Log.v("BackupXmlParserLogging", "Error canonicalizing path of " + file2);
                    }
                }
            }
        }
    }

    private boolean manifestExcludesContainFilePath(Set<FullBackup.BackupScheme.PathWithRequiredFlags> set, String str) {
        Iterator<FullBackup.BackupScheme.PathWithRequiredFlags> it = set.iterator();
        while (it.hasNext()) {
            String path = it.next().getPath();
            if (path != null && path.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void onRestoreFile(ParcelFileDescriptor parcelFileDescriptor, long j, File file, int i, long j2, long j3) throws IOException {
        File file2;
        ParcelFileDescriptor parcelFileDescriptor2;
        long j4;
        int i2;
        long j5;
        long j6;
        Log.i(TAG, "mDisableDataExtractionRule = " + this.mDisableDataExtractionRule);
        if (this.mDisableDataExtractionRule) {
            FullBackup.restoreFile(parcelFileDescriptor, j, i, j2, j3, file);
            return;
        }
        if (isFileEligibleForRestore(file)) {
            parcelFileDescriptor2 = parcelFileDescriptor;
            j4 = j;
            i2 = i;
            j5 = j2;
            j6 = j3;
            file2 = file;
        } else {
            file2 = null;
            parcelFileDescriptor2 = parcelFileDescriptor;
            j4 = j;
            i2 = i;
            j5 = j2;
            j6 = j3;
        }
        FullBackup.restoreFile(parcelFileDescriptor2, j4, i2, j5, j6, file2);
    }

    private boolean isFileEligibleForRestore(File file) throws IOException {
        FullBackup.BackupScheme backupScheme = FullBackup.getBackupScheme(this, this.mBackupDestination);
        if (!backupScheme.isFullRestoreEnabled()) {
            if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                Log.v("BackupXmlParserLogging", "onRestoreFile \"" + file.getCanonicalPath() + "\" : fullBackupContent not enabled for " + getPackageName());
            }
            return false;
        }
        String canonicalPath = file.getCanonicalPath();
        try {
            Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> maybeParseAndGetCanonicalIncludePaths = backupScheme.maybeParseAndGetCanonicalIncludePaths();
            ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags> maybeParseAndGetCanonicalExcludePaths = backupScheme.maybeParseAndGetCanonicalExcludePaths();
            if (maybeParseAndGetCanonicalExcludePaths != null && BackupUtils.isFileSpecifiedInPathList(file, maybeParseAndGetCanonicalExcludePaths)) {
                if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                    Log.v("BackupXmlParserLogging", "onRestoreFile: \"" + canonicalPath + "\": listed in excludes; skipping.");
                }
                return false;
            }
            if (maybeParseAndGetCanonicalIncludePaths == null || maybeParseAndGetCanonicalIncludePaths.isEmpty()) {
                return true;
            }
            Iterator<Set<FullBackup.BackupScheme.PathWithRequiredFlags>> it = maybeParseAndGetCanonicalIncludePaths.values().iterator();
            boolean z = false;
            while (it.hasNext() && !((z = z | BackupUtils.isFileSpecifiedInPathList(file, it.next())))) {
            }
            if (z) {
                return true;
            }
            if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                Log.v("BackupXmlParserLogging", "onRestoreFile: Trying to restore \"" + canonicalPath + "\" but it isn't specified in the included files; skipping.");
            }
            return false;
        } catch (XmlPullParserException e) {
            if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                Log.v("BackupXmlParserLogging", "onRestoreFile \"" + canonicalPath + "\" : Exception trying to parse fullBackupContent xml file! Aborting onRestoreFile.", e);
            }
            return false;
        }
    }

    private void checkDbFiles(String str, long j) {
        if (!str.endsWith("-wal") || j <= 0) {
            return;
        }
        String substring = str.substring(0, str.length() - 4);
        Log.i(TAG, "cdf: " + substring);
        this.dbFilesToCheck.add(substring);
    }

    protected void onRestoreFile(ParcelFileDescriptor parcelFileDescriptor, long j, int i, String str, String str2, long j2, long j3) throws IOException {
        String str3 = FullBackup.getBackupScheme(this, this.mBackupDestination).tokenToDirectoryPath(str);
        long j4 = (str.equals(FullBackup.MANAGED_EXTERNAL_TREE_TOKEN) || str.startsWith(FullBackup.MANAGED_EXTERNAL_SPECIFIC_TREE_RESTORE_TOKEN)) ? -1L : j2;
        if (str3 != null) {
            File file = new File(str3, str2);
            String canonicalPath = file.getCanonicalPath();
            if (canonicalPath.startsWith(str3 + File.separatorChar)) {
                onRestoreFile(parcelFileDescriptor, j, file, i, j4, j3);
                if (str.equals(FullBackup.DATABASE_TREE_TOKEN)) {
                    checkDbFiles(canonicalPath, j);
                    return;
                }
                return;
            }
        }
        FullBackup.restoreFile(parcelFileDescriptor, j, i, j4, j3, null);
    }

    private boolean isStartWith(String str, byte[] bArr) {
        if (new File(str).length() < bArr.length) {
            return false;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
            try {
                int length = bArr.length;
                byte[] bArr2 = new byte[length];
                randomAccessFile.read(bArr2, 0, bArr.length);
                for (int i = 0; i < length; i++) {
                    if (bArr2[i] != bArr[i]) {
                        randomAccessFile.close();
                        return false;
                    }
                }
                randomAccessFile.close();
                return true;
            } finally {
            }
        } catch (IOException e) {
            Log.d(TAG, "isStartWith threw", e);
            return false;
        }
    }

    private void doCleanDbFiles() {
        byte[] bArr = {83, 81, 76, 105, 116, 101, 32, 102, 111, 114, 109, SprAttributeBase.TYPE_ANIMATOR_SET, 116, 32, 51};
        Iterator<String> it = this.dbFilesToCheck.iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (isStartWith(next, bArr)) {
                Log.i(TAG, "dcdf: " + next + ", result: " + SQLiteDatabase.cleanDatabaseFile(next));
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

        private BackupServiceBinder() {
        }

        @Override // android.app.IBackupAgent
        public void doBackup(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, long j, IBackupCallback iBackupCallback, int i) throws RemoteException {
            BackupDataOutput backupDataOutput = new BackupDataOutput(parcelFileDescriptor2.getFileDescriptor(), j, i);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.onBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor3);
                    BackupAgent.this.waitForSharedPrefs();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    try {
                        iBackupCallback.operationComplete(0L);
                    } catch (RemoteException unused) {
                    }
                    if (Binder.getCallingPid() != Process.myPid()) {
                        IoUtils.closeQuietly(parcelFileDescriptor);
                        IoUtils.closeQuietly(parcelFileDescriptor2);
                        IoUtils.closeQuietly(parcelFileDescriptor3);
                    }
                } catch (IOException e) {
                    Log.d(TAG, "onBackup (" + BackupAgent.this.getClass().getName() + ") threw", e);
                    throw new RuntimeException(e);
                } catch (RuntimeException e2) {
                    Log.d(TAG, "onBackup (" + BackupAgent.this.getClass().getName() + ") threw", e2);
                    throw e2;
                }
            } finally {
            }
        }

        @Override // android.app.IBackupAgent
        public void doRestore(ParcelFileDescriptor parcelFileDescriptor, long j, ParcelFileDescriptor parcelFileDescriptor2, int i, IBackupManager iBackupManager) throws RemoteException {
            doRestoreInternal(parcelFileDescriptor, j, parcelFileDescriptor2, i, iBackupManager, null);
        }

        @Override // android.app.IBackupAgent
        public void doRestoreWithExcludedKeys(ParcelFileDescriptor parcelFileDescriptor, long j, ParcelFileDescriptor parcelFileDescriptor2, int i, IBackupManager iBackupManager, List<String> list) throws RemoteException {
            doRestoreInternal(parcelFileDescriptor, j, parcelFileDescriptor2, i, iBackupManager, list);
        }

        /* JADX WARN: Removed duplicated region for block: B:34:0x00c9  */
        /* JADX WARN: Removed duplicated region for block: B:36:? A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void doRestoreInternal(android.os.ParcelFileDescriptor r19, long r20, android.os.ParcelFileDescriptor r22, int r23, android.app.backup.IBackupManager r24, java.util.List<java.lang.String> r25) throws android.os.RemoteException {
            /*
                r18 = this;
                r1 = r18
                r2 = r23
                r3 = r24
                r0 = r25
                java.lang.String r4 = ") threw"
                java.lang.String r5 = "BackupServiceBinder"
                java.lang.String r6 = "onRestore ("
                android.app.backup.BackupAgent r7 = android.app.backup.BackupAgent.this
                android.app.backup.BackupAgent.m683$$Nest$mwaitForSharedPrefs(r7)
                android.app.backup.BackupDataInput r9 = new android.app.backup.BackupDataInput
                java.io.FileDescriptor r7 = r19.getFileDescriptor()
                r9.<init>(r7)
                long r14 = android.os.Binder.clearCallingIdentity()
                r7 = 0
                r10 = r7
                android.app.backup.BackupAgent r8 = android.app.backup.BackupAgent.this     // Catch: java.lang.Throwable -> L61 java.lang.RuntimeException -> L66 java.io.IOException -> L87
                if (r0 == 0) goto L2e
                java.util.HashSet r7 = new java.util.HashSet     // Catch: java.lang.Throwable -> L61 java.lang.RuntimeException -> L66 java.io.IOException -> L87
                r7.<init>(r0)     // Catch: java.lang.Throwable -> L61 java.lang.RuntimeException -> L66 java.io.IOException -> L87
                goto L30
            L2e:
                java.util.Set r7 = java.util.Collections.EMPTY_SET     // Catch: java.lang.Throwable -> L61 java.lang.RuntimeException -> L66 java.io.IOException -> L87
            L30:
                r12 = r22
                r13 = r7
                r16 = r14
                r14 = r10
                r10 = r20
                r8.onRestore(r9, r10, r12, r13)     // Catch: java.lang.RuntimeException -> L5d java.io.IOException -> L5f java.lang.Throwable -> Lad
                android.app.backup.BackupAgent r0 = android.app.backup.BackupAgent.this
                r0.reloadSharedPreferences()
                android.os.Binder.restoreCallingIdentity(r16)
                android.app.backup.BackupAgent r0 = android.app.backup.BackupAgent.this     // Catch: android.os.RemoteException -> L4c
                int r0 = android.app.backup.BackupAgent.m682$$Nest$mgetBackupUserId(r0)     // Catch: android.os.RemoteException -> L4c
                r3.opCompleteForUser(r0, r2, r14)     // Catch: android.os.RemoteException -> L4c
            L4c:
                int r0 = android.os.Binder.getCallingPid()
                int r1 = android.os.Process.myPid()
                if (r0 == r1) goto L5c
                libcore.io.IoUtils.closeQuietly(r19)
                libcore.io.IoUtils.closeQuietly(r22)
            L5c:
                return
            L5d:
                r0 = move-exception
                goto L6a
            L5f:
                r0 = move-exception
                goto L8b
            L61:
                r0 = move-exception
                r16 = r14
                r14 = r10
                goto Lae
            L66:
                r0 = move-exception
                r16 = r14
                r14 = r10
            L6a:
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lad
                r7.<init>(r6)     // Catch: java.lang.Throwable -> Lad
                android.app.backup.BackupAgent r6 = android.app.backup.BackupAgent.this     // Catch: java.lang.Throwable -> Lad
                java.lang.Class r6 = r6.getClass()     // Catch: java.lang.Throwable -> Lad
                java.lang.String r6 = r6.getName()     // Catch: java.lang.Throwable -> Lad
                r7.append(r6)     // Catch: java.lang.Throwable -> Lad
                r7.append(r4)     // Catch: java.lang.Throwable -> Lad
                java.lang.String r4 = r7.toString()     // Catch: java.lang.Throwable -> Lad
                android.util.Log.d(r5, r4, r0)     // Catch: java.lang.Throwable -> Lad
                throw r0     // Catch: java.lang.Throwable -> Lad
            L87:
                r0 = move-exception
                r16 = r14
                r14 = r10
            L8b:
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lad
                r7.<init>(r6)     // Catch: java.lang.Throwable -> Lad
                android.app.backup.BackupAgent r6 = android.app.backup.BackupAgent.this     // Catch: java.lang.Throwable -> Lad
                java.lang.Class r6 = r6.getClass()     // Catch: java.lang.Throwable -> Lad
                java.lang.String r6 = r6.getName()     // Catch: java.lang.Throwable -> Lad
                r7.append(r6)     // Catch: java.lang.Throwable -> Lad
                r7.append(r4)     // Catch: java.lang.Throwable -> Lad
                java.lang.String r4 = r7.toString()     // Catch: java.lang.Throwable -> Lad
                android.util.Log.d(r5, r4, r0)     // Catch: java.lang.Throwable -> Lad
                java.lang.RuntimeException r4 = new java.lang.RuntimeException     // Catch: java.lang.Throwable -> Lad
                r4.<init>(r0)     // Catch: java.lang.Throwable -> Lad
                throw r4     // Catch: java.lang.Throwable -> Lad
            Lad:
                r0 = move-exception
            Lae:
                android.app.backup.BackupAgent r4 = android.app.backup.BackupAgent.this
                r4.reloadSharedPreferences()
                android.os.Binder.restoreCallingIdentity(r16)
                android.app.backup.BackupAgent r1 = android.app.backup.BackupAgent.this     // Catch: android.os.RemoteException -> Lbf
                int r1 = android.app.backup.BackupAgent.m682$$Nest$mgetBackupUserId(r1)     // Catch: android.os.RemoteException -> Lbf
                r3.opCompleteForUser(r1, r2, r14)     // Catch: android.os.RemoteException -> Lbf
            Lbf:
                int r1 = android.os.Binder.getCallingPid()
                int r2 = android.os.Process.myPid()
                if (r1 == r2) goto Lcf
                libcore.io.IoUtils.closeQuietly(r19)
                libcore.io.IoUtils.closeQuietly(r22)
            Lcf:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.backup.BackupAgent.BackupServiceBinder.doRestoreInternal(android.os.ParcelFileDescriptor, long, android.os.ParcelFileDescriptor, int, android.app.backup.IBackupManager, java.util.List):void");
        }

        /* JADX WARN: Removed duplicated region for block: B:40:0x00da  */
        /* JADX WARN: Removed duplicated region for block: B:42:? A[SYNTHETIC] */
        @Override // android.app.IBackupAgent
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void doFullBackup(android.os.ParcelFileDescriptor r17, long r18, int r20, android.app.backup.IBackupManager r21, int r22) {
            /*
                r16 = this;
                r1 = r16
                r2 = r20
                r3 = r21
                java.lang.String r4 = ") threw"
                java.lang.String r5 = "Unable to finalize backup stream!"
                java.lang.String r6 = "BackupServiceBinder"
                java.lang.String r7 = "onFullBackup ("
                android.app.backup.BackupAgent r0 = android.app.backup.BackupAgent.this
                android.app.backup.BackupAgent.m683$$Nest$mwaitForSharedPrefs(r0)
                long r8 = android.os.Binder.clearCallingIdentity()
                r12 = 4
                android.app.backup.BackupAgent r0 = android.app.backup.BackupAgent.this     // Catch: java.lang.Throwable -> L62 java.lang.RuntimeException -> L66 java.io.IOException -> L86
                android.app.backup.FullBackupDataOutput r13 = new android.app.backup.FullBackupDataOutput     // Catch: java.lang.Throwable -> L62 java.lang.RuntimeException -> L66 java.io.IOException -> L86
                r14 = r17
                r10 = r18
                r15 = r22
                r13.<init>(r14, r10, r15)     // Catch: java.lang.Throwable -> L5c java.lang.RuntimeException -> L5e java.io.IOException -> L60
                r0.onFullBackup(r13)     // Catch: java.lang.Throwable -> L5c java.lang.RuntimeException -> L5e java.io.IOException -> L60
                android.app.backup.BackupAgent r0 = android.app.backup.BackupAgent.this
                android.app.backup.BackupAgent.m683$$Nest$mwaitForSharedPrefs(r0)
                java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch: java.io.IOException -> L3d
                java.io.FileDescriptor r4 = r14.getFileDescriptor()     // Catch: java.io.IOException -> L3d
                r0.<init>(r4)     // Catch: java.io.IOException -> L3d
                byte[] r4 = new byte[r12]     // Catch: java.io.IOException -> L3d
                r0.write(r4)     // Catch: java.io.IOException -> L3d
                goto L40
            L3d:
                android.util.Log.e(r6, r5)
            L40:
                android.os.Binder.restoreCallingIdentity(r8)
                android.app.backup.BackupAgent r0 = android.app.backup.BackupAgent.this     // Catch: android.os.RemoteException -> L4e
                int r0 = android.app.backup.BackupAgent.m682$$Nest$mgetBackupUserId(r0)     // Catch: android.os.RemoteException -> L4e
                r4 = 0
                r3.opCompleteForUser(r0, r2, r4)     // Catch: android.os.RemoteException -> L4e
            L4e:
                int r0 = android.os.Binder.getCallingPid()
                int r1 = android.os.Process.myPid()
                if (r0 == r1) goto L5b
                libcore.io.IoUtils.closeQuietly(r14)
            L5b:
                return
            L5c:
                r0 = move-exception
                goto Lab
            L5e:
                r0 = move-exception
                goto L69
            L60:
                r0 = move-exception
                goto L89
            L62:
                r0 = move-exception
                r14 = r17
                goto Lab
            L66:
                r0 = move-exception
                r14 = r17
            L69:
                java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5c
                r10.<init>(r7)     // Catch: java.lang.Throwable -> L5c
                android.app.backup.BackupAgent r7 = android.app.backup.BackupAgent.this     // Catch: java.lang.Throwable -> L5c
                java.lang.Class r7 = r7.getClass()     // Catch: java.lang.Throwable -> L5c
                java.lang.String r7 = r7.getName()     // Catch: java.lang.Throwable -> L5c
                r10.append(r7)     // Catch: java.lang.Throwable -> L5c
                r10.append(r4)     // Catch: java.lang.Throwable -> L5c
                java.lang.String r4 = r10.toString()     // Catch: java.lang.Throwable -> L5c
                android.util.Log.d(r6, r4, r0)     // Catch: java.lang.Throwable -> L5c
                throw r0     // Catch: java.lang.Throwable -> L5c
            L86:
                r0 = move-exception
                r14 = r17
            L89:
                java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5c
                r10.<init>(r7)     // Catch: java.lang.Throwable -> L5c
                android.app.backup.BackupAgent r7 = android.app.backup.BackupAgent.this     // Catch: java.lang.Throwable -> L5c
                java.lang.Class r7 = r7.getClass()     // Catch: java.lang.Throwable -> L5c
                java.lang.String r7 = r7.getName()     // Catch: java.lang.Throwable -> L5c
                r10.append(r7)     // Catch: java.lang.Throwable -> L5c
                r10.append(r4)     // Catch: java.lang.Throwable -> L5c
                java.lang.String r4 = r10.toString()     // Catch: java.lang.Throwable -> L5c
                android.util.Log.d(r6, r4, r0)     // Catch: java.lang.Throwable -> L5c
                java.lang.RuntimeException r4 = new java.lang.RuntimeException     // Catch: java.lang.Throwable -> L5c
                r4.<init>(r0)     // Catch: java.lang.Throwable -> L5c
                throw r4     // Catch: java.lang.Throwable -> L5c
            Lab:
                android.app.backup.BackupAgent r4 = android.app.backup.BackupAgent.this
                android.app.backup.BackupAgent.m683$$Nest$mwaitForSharedPrefs(r4)
                java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: java.io.IOException -> Lbf
                java.io.FileDescriptor r7 = r14.getFileDescriptor()     // Catch: java.io.IOException -> Lbf
                r4.<init>(r7)     // Catch: java.io.IOException -> Lbf
                byte[] r7 = new byte[r12]     // Catch: java.io.IOException -> Lbf
                r4.write(r7)     // Catch: java.io.IOException -> Lbf
                goto Lc2
            Lbf:
                android.util.Log.e(r6, r5)
            Lc2:
                android.os.Binder.restoreCallingIdentity(r8)
                android.app.backup.BackupAgent r1 = android.app.backup.BackupAgent.this     // Catch: android.os.RemoteException -> Ld0
                int r1 = android.app.backup.BackupAgent.m682$$Nest$mgetBackupUserId(r1)     // Catch: android.os.RemoteException -> Ld0
                r4 = 0
                r3.opCompleteForUser(r1, r2, r4)     // Catch: android.os.RemoteException -> Ld0
            Ld0:
                int r1 = android.os.Binder.getCallingPid()
                int r2 = android.os.Process.myPid()
                if (r1 == r2) goto Ldd
                libcore.io.IoUtils.closeQuietly(r14)
            Ldd:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.backup.BackupAgent.BackupServiceBinder.doFullBackup(android.os.ParcelFileDescriptor, long, int, android.app.backup.IBackupManager, int):void");
        }

        /* JADX WARN: Removed duplicated region for block: B:43:0x00e2  */
        /* JADX WARN: Removed duplicated region for block: B:45:? A[SYNTHETIC] */
        @Override // android.app.IBackupAgent
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void doFullBackupPath(android.os.ParcelFileDescriptor r17, long r18, int r20, android.app.backup.IBackupManager r21, int r22, java.lang.String[] r23) {
            /*
                r16 = this;
                r1 = r16
                r2 = r20
                r3 = r21
                r0 = r23
                java.lang.String r4 = ") threw"
                java.lang.String r5 = "Unable to finalize backup stream!"
                java.lang.String r6 = "BackupServiceBinder"
                java.lang.String r7 = "onFullBackup ("
                android.app.backup.BackupAgent r8 = android.app.backup.BackupAgent.this
                android.app.backup.BackupAgent.m683$$Nest$mwaitForSharedPrefs(r8)
                if (r0 == 0) goto L1c
                android.app.backup.BackupAgent r8 = android.app.backup.BackupAgent.this
                r8.mSmartSwitchBackupPath = r0
            L1c:
                long r8 = android.os.Binder.clearCallingIdentity()
                r12 = 4
                android.app.backup.BackupAgent r0 = android.app.backup.BackupAgent.this     // Catch: java.lang.Throwable -> L6a java.lang.RuntimeException -> L6e java.io.IOException -> L8e
                android.app.backup.FullBackupDataOutput r13 = new android.app.backup.FullBackupDataOutput     // Catch: java.lang.Throwable -> L6a java.lang.RuntimeException -> L6e java.io.IOException -> L8e
                r14 = r17
                r10 = r18
                r15 = r22
                r13.<init>(r14, r10, r15)     // Catch: java.lang.Throwable -> L64 java.lang.RuntimeException -> L66 java.io.IOException -> L68
                r0.onFullBackup(r13)     // Catch: java.lang.Throwable -> L64 java.lang.RuntimeException -> L66 java.io.IOException -> L68
                android.app.backup.BackupAgent r0 = android.app.backup.BackupAgent.this
                android.app.backup.BackupAgent.m683$$Nest$mwaitForSharedPrefs(r0)
                java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch: java.io.IOException -> L45
                java.io.FileDescriptor r4 = r14.getFileDescriptor()     // Catch: java.io.IOException -> L45
                r0.<init>(r4)     // Catch: java.io.IOException -> L45
                byte[] r4 = new byte[r12]     // Catch: java.io.IOException -> L45
                r0.write(r4)     // Catch: java.io.IOException -> L45
                goto L48
            L45:
                android.util.Log.e(r6, r5)
            L48:
                android.os.Binder.restoreCallingIdentity(r8)
                android.app.backup.BackupAgent r0 = android.app.backup.BackupAgent.this     // Catch: android.os.RemoteException -> L56
                int r0 = android.app.backup.BackupAgent.m682$$Nest$mgetBackupUserId(r0)     // Catch: android.os.RemoteException -> L56
                r4 = 0
                r3.opCompleteForUser(r0, r2, r4)     // Catch: android.os.RemoteException -> L56
            L56:
                int r0 = android.os.Binder.getCallingPid()
                int r1 = android.os.Process.myPid()
                if (r0 == r1) goto L63
                libcore.io.IoUtils.closeQuietly(r14)
            L63:
                return
            L64:
                r0 = move-exception
                goto Lb3
            L66:
                r0 = move-exception
                goto L71
            L68:
                r0 = move-exception
                goto L91
            L6a:
                r0 = move-exception
                r14 = r17
                goto Lb3
            L6e:
                r0 = move-exception
                r14 = r17
            L71:
                java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L64
                r10.<init>(r7)     // Catch: java.lang.Throwable -> L64
                android.app.backup.BackupAgent r7 = android.app.backup.BackupAgent.this     // Catch: java.lang.Throwable -> L64
                java.lang.Class r7 = r7.getClass()     // Catch: java.lang.Throwable -> L64
                java.lang.String r7 = r7.getName()     // Catch: java.lang.Throwable -> L64
                r10.append(r7)     // Catch: java.lang.Throwable -> L64
                r10.append(r4)     // Catch: java.lang.Throwable -> L64
                java.lang.String r4 = r10.toString()     // Catch: java.lang.Throwable -> L64
                android.util.Log.d(r6, r4, r0)     // Catch: java.lang.Throwable -> L64
                throw r0     // Catch: java.lang.Throwable -> L64
            L8e:
                r0 = move-exception
                r14 = r17
            L91:
                java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L64
                r10.<init>(r7)     // Catch: java.lang.Throwable -> L64
                android.app.backup.BackupAgent r7 = android.app.backup.BackupAgent.this     // Catch: java.lang.Throwable -> L64
                java.lang.Class r7 = r7.getClass()     // Catch: java.lang.Throwable -> L64
                java.lang.String r7 = r7.getName()     // Catch: java.lang.Throwable -> L64
                r10.append(r7)     // Catch: java.lang.Throwable -> L64
                r10.append(r4)     // Catch: java.lang.Throwable -> L64
                java.lang.String r4 = r10.toString()     // Catch: java.lang.Throwable -> L64
                android.util.Log.d(r6, r4, r0)     // Catch: java.lang.Throwable -> L64
                java.lang.RuntimeException r4 = new java.lang.RuntimeException     // Catch: java.lang.Throwable -> L64
                r4.<init>(r0)     // Catch: java.lang.Throwable -> L64
                throw r4     // Catch: java.lang.Throwable -> L64
            Lb3:
                android.app.backup.BackupAgent r4 = android.app.backup.BackupAgent.this
                android.app.backup.BackupAgent.m683$$Nest$mwaitForSharedPrefs(r4)
                java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: java.io.IOException -> Lc7
                java.io.FileDescriptor r7 = r14.getFileDescriptor()     // Catch: java.io.IOException -> Lc7
                r4.<init>(r7)     // Catch: java.io.IOException -> Lc7
                byte[] r7 = new byte[r12]     // Catch: java.io.IOException -> Lc7
                r4.write(r7)     // Catch: java.io.IOException -> Lc7
                goto Lca
            Lc7:
                android.util.Log.e(r6, r5)
            Lca:
                android.os.Binder.restoreCallingIdentity(r8)
                android.app.backup.BackupAgent r1 = android.app.backup.BackupAgent.this     // Catch: android.os.RemoteException -> Ld8
                int r1 = android.app.backup.BackupAgent.m682$$Nest$mgetBackupUserId(r1)     // Catch: android.os.RemoteException -> Ld8
                r4 = 0
                r3.opCompleteForUser(r1, r2, r4)     // Catch: android.os.RemoteException -> Ld8
            Ld8:
                int r1 = android.os.Binder.getCallingPid()
                int r2 = android.os.Process.myPid()
                if (r1 == r2) goto Le5
                libcore.io.IoUtils.closeQuietly(r14)
            Le5:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.backup.BackupAgent.BackupServiceBinder.doFullBackupPath(android.os.ParcelFileDescriptor, long, int, android.app.backup.IBackupManager, int, java.lang.String[]):void");
        }

        @Override // android.app.IBackupAgent
        public void doDisableDataExtractionRules(boolean z) {
            BackupAgent.this.mDisableDataExtractionRule = z;
        }

        @Override // android.app.IBackupAgent
        public void doMeasureFullBackup(long j, int i, IBackupManager iBackupManager, int i2) {
            FullBackupDataOutput fullBackupDataOutput = new FullBackupDataOutput(j, i2);
            BackupAgent.this.waitForSharedPrefs();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.onFullBackup(fullBackupDataOutput);
                    try {
                        iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, fullBackupDataOutput.getSize());
                    } catch (RemoteException unused) {
                    }
                } catch (IOException e) {
                    Log.d(TAG, "onFullBackup[M] (" + BackupAgent.this.getClass().getName() + ") threw", e);
                    throw new RuntimeException(e);
                } catch (RuntimeException e2) {
                    Log.d(TAG, "onFullBackup[M] (" + BackupAgent.this.getClass().getName() + ") threw", e2);
                    throw e2;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                try {
                    iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, fullBackupDataOutput.getSize());
                } catch (RemoteException unused2) {
                }
            }
        }

        @Override // android.app.IBackupAgent
        public void doRestoreFile(ParcelFileDescriptor parcelFileDescriptor, long j, int i, String str, String str2, long j2, long j3, int i2, IBackupManager iBackupManager) throws RemoteException {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.onRestoreFile(parcelFileDescriptor, j, i, str, str2, j2, j3);
                    BackupAgent.this.waitForSharedPrefs();
                    BackupAgent.this.reloadSharedPreferences();
                    if (Flags.enableClearPipeAfterRestoreFile()) {
                        clearUnconsumedDataFromPipe(parcelFileDescriptor, j);
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    try {
                        iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i2, 0L);
                    } catch (RemoteException unused) {
                    }
                    if (Binder.getCallingPid() != Process.myPid()) {
                        IoUtils.closeQuietly(parcelFileDescriptor);
                    }
                } finally {
                }
            } catch (IOException e) {
                Log.d(TAG, "onRestoreFile (" + BackupAgent.this.getClass().getName() + ") threw", e);
                throw new RuntimeException(e);
            }
        }

        private static void clearUnconsumedDataFromPipe(ParcelFileDescriptor parcelFileDescriptor, long j) {
            try {
                FileInputStream fileInputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                try {
                    if (fileInputStream.available() > 0) {
                        fileInputStream.skip(j);
                    }
                    fileInputStream.close();
                } finally {
                }
            } catch (IOException e) {
                Log.w(TAG, "Failed to clear unconsumed data from pipe.", e);
            }
        }

        @Override // android.app.IBackupAgent
        public void doRestoreFinished(int i, IBackupManager iBackupManager) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.onRestoreFinished();
                    try {
                        iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, 0L);
                    } catch (RemoteException unused) {
                    }
                } catch (Exception e) {
                    Log.d(TAG, "onRestoreFinished (" + BackupAgent.this.getClass().getName() + ") threw", e);
                    throw e;
                }
            } finally {
                BackupAgent.this.waitForSharedPrefs();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                try {
                    iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, 0L);
                } catch (RemoteException unused2) {
                }
            }
        }

        @Override // android.app.IBackupAgent
        public void fail(String str) {
            BackupAgent.this.getHandler().post(new FailRunnable(str));
        }

        @Override // android.app.IBackupAgent
        public void doQuotaExceeded(long j, long j2, IBackupCallback iBackupCallback) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.onQuotaExceeded(j, j2);
                    BackupAgent.this.waitForSharedPrefs();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    try {
                        iBackupCallback.operationComplete(0L);
                    } catch (RemoteException unused) {
                    }
                } catch (Throwable th) {
                    BackupAgent.this.waitForSharedPrefs();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    try {
                        iBackupCallback.operationComplete(-1L);
                    } catch (RemoteException unused2) {
                    }
                    throw th;
                }
            } catch (Exception e) {
                Log.d(TAG, "onQuotaExceeded(" + BackupAgent.this.getClass().getName() + ") threw", e);
                throw e;
            }
        }

        @Override // android.app.IBackupAgent
        public void getLoggerResults(AndroidFuture<List<BackupRestoreEventLogger.DataTypeResult>> androidFuture) {
            if (BackupAgent.this.mLogger != null) {
                androidFuture.complete(BackupAgent.this.mLogger.getLoggingResults());
            } else {
                androidFuture.complete(Collections.EMPTY_LIST);
            }
        }

        @Override // android.app.IBackupAgent
        public void getOperationType(AndroidFuture<Integer> androidFuture) {
            androidFuture.complete(Integer.valueOf(BackupAgent.this.mLogger == null ? -1 : BackupAgent.this.mLogger.getOperationType()));
        }

        @Override // android.app.IBackupAgent
        public void clearBackupRestoreEventLogger() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.clearBackupRestoreEventLogger();
                } catch (Exception e) {
                    Log.d(TAG, "clearBackupRestoreEventLogger (" + BackupAgent.this.getClass().getName() + ") threw", e);
                    throw e;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    static class FailRunnable implements Runnable {
        private String mMessage;

        FailRunnable(String str) {
            this.mMessage = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            throw new IllegalStateException(this.mMessage);
        }
    }

    public static class IncludeExcludeRules {
        private final Set<FullBackup.BackupScheme.PathWithRequiredFlags> mManifestExcludeSet;
        private final Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> mManifestIncludeMap;

        public IncludeExcludeRules(Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> map, Set<FullBackup.BackupScheme.PathWithRequiredFlags> set) {
            this.mManifestIncludeMap = map;
            this.mManifestExcludeSet = set;
        }

        public static IncludeExcludeRules emptyRules() {
            return new IncludeExcludeRules(Collections.EMPTY_MAP, new ArraySet());
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

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                IncludeExcludeRules includeExcludeRules = (IncludeExcludeRules) obj;
                if (Objects.equals(this.mManifestIncludeMap, includeExcludeRules.mManifestIncludeMap) && Objects.equals(this.mManifestExcludeSet, includeExcludeRules.mManifestExcludeSet)) {
                    return true;
                }
            }
            return false;
        }
    }
}
