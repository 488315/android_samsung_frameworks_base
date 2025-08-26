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
import java.io.FileOutputStream;
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
    public void waitForSharedPrefs() throws InterruptedException {
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

    public void onFullBackup(FullBackupDataOutput fullBackupDataOutput) throws IOException, ErrnoException {
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
                Context contextCreateCredentialProtectedStorageContext = createCredentialProtectedStorageContext();
                String canonicalPath = contextCreateCredentialProtectedStorageContext.getDataDir().getCanonicalPath();
                String canonicalPath2 = contextCreateCredentialProtectedStorageContext.getFilesDir().getCanonicalPath();
                String canonicalPath3 = contextCreateCredentialProtectedStorageContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
                String canonicalPath4 = contextCreateCredentialProtectedStorageContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
                Context contextCreateDeviceProtectedStorageContext = createDeviceProtectedStorageContext();
                String canonicalPath5 = contextCreateDeviceProtectedStorageContext.getDataDir().getCanonicalPath();
                String canonicalPath6 = contextCreateDeviceProtectedStorageContext.getFilesDir().getCanonicalPath();
                String canonicalPath7 = contextCreateDeviceProtectedStorageContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
                String canonicalPath8 = contextCreateDeviceProtectedStorageContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
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
                Set<String> extraExcludeDirsIfAny = getExtraExcludeDirsIfAny(contextCreateCredentialProtectedStorageContext);
                Set<String> extraExcludeDirsIfAny2 = getExtraExcludeDirsIfAny(contextCreateDeviceProtectedStorageContext);
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

    public IncludeExcludeRules getIncludeExcludeRules(FullBackup.BackupScheme backupScheme) throws XmlPullParserException, IOException {
        return new IncludeExcludeRules(backupScheme.maybeParseAndGetCanonicalIncludePaths(), backupScheme.maybeParseAndGetCanonicalExcludePaths());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getBackupUserId() {
        UserHandle userHandle = this.mUser;
        return userHandle == null ? super.getUserId() : userHandle.getIdentifier();
    }

    private void applyXmlFiltersAndDoFullBackupForDomain(String str, String str2, Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> map, Set<FullBackup.BackupScheme.PathWithRequiredFlags> set, ArraySet<String> arraySet, FullBackupDataOutput fullBackupDataOutput) throws IOException, ErrnoException {
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

    public final void fullBackupFile(File file, FullBackupDataOutput fullBackupDataOutput) throws IOException {
        String str;
        String canonicalPath;
        String canonicalPath2;
        String canonicalPath3;
        String canonicalPath4;
        String canonicalPath5;
        String canonicalPath6;
        String canonicalPath7;
        String canonicalPath8;
        String canonicalPath9;
        String canonicalPath10;
        String canonicalPath11;
        String canonicalPath12;
        String canonicalPath13;
        String canonicalPath14;
        String canonicalPath15;
        String str2;
        String str3;
        ApplicationInfo applicationInfo = getApplicationInfo();
        try {
            Context contextCreateCredentialProtectedStorageContext = createCredentialProtectedStorageContext();
            canonicalPath = contextCreateCredentialProtectedStorageContext.getDataDir().getCanonicalPath();
            canonicalPath2 = contextCreateCredentialProtectedStorageContext.getFilesDir().getCanonicalPath();
            canonicalPath3 = contextCreateCredentialProtectedStorageContext.getNoBackupFilesDir().getCanonicalPath();
            canonicalPath4 = contextCreateCredentialProtectedStorageContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
            canonicalPath5 = contextCreateCredentialProtectedStorageContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
            canonicalPath6 = contextCreateCredentialProtectedStorageContext.getCacheDir().getCanonicalPath();
            canonicalPath7 = contextCreateCredentialProtectedStorageContext.getCodeCacheDir().getCanonicalPath();
            Context contextCreateDeviceProtectedStorageContext = createDeviceProtectedStorageContext();
            canonicalPath8 = contextCreateDeviceProtectedStorageContext.getDataDir().getCanonicalPath();
            canonicalPath9 = contextCreateDeviceProtectedStorageContext.getFilesDir().getCanonicalPath();
            canonicalPath10 = contextCreateDeviceProtectedStorageContext.getNoBackupFilesDir().getCanonicalPath();
            canonicalPath11 = contextCreateDeviceProtectedStorageContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
            canonicalPath12 = contextCreateDeviceProtectedStorageContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
            canonicalPath13 = contextCreateDeviceProtectedStorageContext.getCacheDir().getCanonicalPath();
            canonicalPath14 = contextCreateDeviceProtectedStorageContext.getCodeCacheDir().getCanonicalPath();
        } catch (IOException unused) {
            str = TAG;
        }
        try {
            String canonicalPath16 = applicationInfo.nativeLibraryDir == null ? null : new File(applicationInfo.nativeLibraryDir).getCanonicalPath();
            if (Process.myUid() != 1000) {
                canonicalPath15 = null;
                File externalFilesDir = getExternalFilesDir(null);
                if (externalFilesDir != null) {
                    canonicalPath15 = externalFilesDir.getCanonicalPath();
                }
            } else {
                canonicalPath15 = null;
            }
            String canonicalPath17 = file.getCanonicalPath();
            if (canonicalPath17.startsWith(canonicalPath6) || canonicalPath17.startsWith(canonicalPath7) || canonicalPath17.startsWith(canonicalPath3) || canonicalPath17.startsWith(canonicalPath13) || canonicalPath17.startsWith(canonicalPath14) || canonicalPath17.startsWith(canonicalPath10) || canonicalPath17.startsWith(canonicalPath16)) {
                Log.w(TAG, "lib, cache, code_cache, and no_backup files are not backed up");
                return;
            }
            if (canonicalPath17.startsWith(canonicalPath4)) {
                str2 = FullBackup.DATABASE_TREE_TOKEN;
                str3 = canonicalPath4;
            } else if (canonicalPath17.startsWith(canonicalPath5)) {
                str2 = FullBackup.SHAREDPREFS_TREE_TOKEN;
                str3 = canonicalPath5;
            } else if (canonicalPath17.startsWith(canonicalPath2)) {
                str2 = FullBackup.FILES_TREE_TOKEN;
                str3 = canonicalPath2;
            } else if (canonicalPath17.startsWith(canonicalPath)) {
                str2 = "r";
                str3 = canonicalPath;
            } else if (canonicalPath17.startsWith(canonicalPath11)) {
                str2 = FullBackup.DEVICE_DATABASE_TREE_TOKEN;
                str3 = canonicalPath11;
            } else if (canonicalPath17.startsWith(canonicalPath12)) {
                str2 = FullBackup.DEVICE_SHAREDPREFS_TREE_TOKEN;
                str3 = canonicalPath12;
            } else if (canonicalPath17.startsWith(canonicalPath9)) {
                str2 = FullBackup.DEVICE_FILES_TREE_TOKEN;
                str3 = canonicalPath9;
            } else if (canonicalPath17.startsWith(canonicalPath8)) {
                str3 = canonicalPath8;
                str2 = FullBackup.DEVICE_ROOT_TREE_TOKEN;
            } else if (canonicalPath15 != null && canonicalPath17.startsWith(canonicalPath15)) {
                str2 = FullBackup.MANAGED_EXTERNAL_TREE_TOKEN;
                str3 = canonicalPath15;
            } else {
                Log.w(TAG, "File " + canonicalPath17 + " is in an unsupported location; skipping");
                return;
            }
            FullBackup.backupToTar(getPackageName(), str2, null, str3, canonicalPath17, fullBackupDataOutput);
        } catch (IOException unused2) {
            str = TAG;
            Log.w(str, "Unable to obtain canonical paths");
        }
    }

    protected final void fullBackupFileTree(String str, String str2, String str3, Set<FullBackup.BackupScheme.PathWithRequiredFlags> set, ArraySet<String> arraySet, FullBackupDataOutput fullBackupDataOutput) throws IOException, ErrnoException {
        StructStat structStatLstat;
        File[] fileArrListFiles;
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
                    structStatLstat = Os.lstat(file2.getPath());
                } catch (ErrnoException e) {
                    if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                        Log.v("BackupXmlParserLogging", "Error scanning file " + file2 + " : " + e);
                    }
                } catch (IOException unused) {
                    if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                        Log.v("BackupXmlParserLogging", "Error canonicalizing path of " + file2);
                    }
                }
                if (OsConstants.S_ISREG(structStatLstat.st_mode) || OsConstants.S_ISDIR(structStatLstat.st_mode)) {
                    String canonicalPath = file2.getCanonicalPath();
                    if (set == null || !manifestExcludesContainFilePath(set, canonicalPath)) {
                        if (arraySet == null || !arraySet.contains(canonicalPath)) {
                            if (OsConstants.S_ISDIR(structStatLstat.st_mode) && (fileArrListFiles = file2.listFiles()) != null) {
                                for (File file3 : fileArrListFiles) {
                                    linkedList.add(0, file3);
                                }
                            }
                            FullBackup.backupToTar(str, str6, null, str5, canonicalPath, fullBackupDataOutput);
                        }
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

    public void onRestoreFile(ParcelFileDescriptor parcelFileDescriptor, long j, File file, int i, long j2, long j3) throws IOException, ErrnoException {
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
            Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> mapMaybeParseAndGetCanonicalIncludePaths = backupScheme.maybeParseAndGetCanonicalIncludePaths();
            ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags> arraySetMaybeParseAndGetCanonicalExcludePaths = backupScheme.maybeParseAndGetCanonicalExcludePaths();
            if (arraySetMaybeParseAndGetCanonicalExcludePaths != null && BackupUtils.isFileSpecifiedInPathList(file, arraySetMaybeParseAndGetCanonicalExcludePaths)) {
                if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                    Log.v("BackupXmlParserLogging", "onRestoreFile: \"" + canonicalPath + "\": listed in excludes; skipping.");
                }
                return false;
            }
            if (mapMaybeParseAndGetCanonicalIncludePaths == null || mapMaybeParseAndGetCanonicalIncludePaths.isEmpty()) {
                return true;
            }
            Iterator<Set<FullBackup.BackupScheme.PathWithRequiredFlags>> it = mapMaybeParseAndGetCanonicalIncludePaths.values().iterator();
            boolean zIsFileSpecifiedInPathList = false;
            while (it.hasNext() && !((zIsFileSpecifiedInPathList = zIsFileSpecifiedInPathList | BackupUtils.isFileSpecifiedInPathList(file, it.next())))) {
            }
            if (zIsFileSpecifiedInPathList) {
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
        String strSubstring = str.substring(0, str.length() - 4);
        Log.i(TAG, "cdf: " + strSubstring);
        this.dbFilesToCheck.add(strSubstring);
    }

    protected void onRestoreFile(ParcelFileDescriptor parcelFileDescriptor, long j, int i, String str, String str2, long j2, long j3) throws IOException, ErrnoException {
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

    private boolean isStartWith(String str, byte[] bArr) throws IOException {
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
        public void doBackup(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, long j, IBackupCallback iBackupCallback, int i) throws InterruptedException, RemoteException {
            BackupDataOutput backupDataOutput = new BackupDataOutput(parcelFileDescriptor2.getFileDescriptor(), j, i);
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.onBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor3);
                    BackupAgent.this.waitForSharedPrefs();
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    try {
                        iBackupCallback.operationComplete(0L);
                    } catch (RemoteException unused) {
                    }
                    if (Binder.getCallingPid() != Process.myPid()) {
                        IoUtils.closeQuietly(parcelFileDescriptor);
                        IoUtils.closeQuietly(parcelFileDescriptor2);
                        IoUtils.closeQuietly(parcelFileDescriptor3);
                    }
                } finally {
                }
            } catch (IOException e) {
                Log.d(TAG, "onBackup (" + BackupAgent.this.getClass().getName() + ") threw", e);
                throw new RuntimeException(e);
            } catch (RuntimeException e2) {
                Log.d(TAG, "onBackup (" + BackupAgent.this.getClass().getName() + ") threw", e2);
                throw e2;
            }
        }

        @Override // android.app.IBackupAgent
        public void doRestore(ParcelFileDescriptor parcelFileDescriptor, long j, ParcelFileDescriptor parcelFileDescriptor2, int i, IBackupManager iBackupManager) throws Throwable {
            doRestoreInternal(parcelFileDescriptor, j, parcelFileDescriptor2, i, iBackupManager, null);
        }

        @Override // android.app.IBackupAgent
        public void doRestoreWithExcludedKeys(ParcelFileDescriptor parcelFileDescriptor, long j, ParcelFileDescriptor parcelFileDescriptor2, int i, IBackupManager iBackupManager, List<String> list) throws Throwable {
            doRestoreInternal(parcelFileDescriptor, j, parcelFileDescriptor2, i, iBackupManager, list);
        }

        /* JADX WARN: Removed duplicated region for block: B:34:0x00c9  */
        /* JADX WARN: Removed duplicated region for block: B:46:? A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void doRestoreInternal(ParcelFileDescriptor parcelFileDescriptor, long j, ParcelFileDescriptor parcelFileDescriptor2, int i, IBackupManager iBackupManager, List<String> list) throws Throwable {
            BackupAgent.this.waitForSharedPrefs();
            BackupDataInput backupDataInput = new BackupDataInput(parcelFileDescriptor.getFileDescriptor());
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                } catch (Throwable th) {
                    th = th;
                    BackupAgent.this.reloadSharedPreferences();
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    try {
                        iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, jClearCallingIdentity);
                    } catch (RemoteException unused) {
                    }
                    if (Binder.getCallingPid() != Process.myPid()) {
                        throw th;
                    }
                    IoUtils.closeQuietly(parcelFileDescriptor);
                    IoUtils.closeQuietly(parcelFileDescriptor2);
                    throw th;
                }
            } catch (IOException e) {
                e = e;
            } catch (RuntimeException e2) {
                e = e2;
            } catch (Throwable th2) {
                th = th2;
                jClearCallingIdentity = 0;
                BackupAgent.this.reloadSharedPreferences();
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, jClearCallingIdentity);
                if (Binder.getCallingPid() != Process.myPid()) {
                }
            }
            try {
                BackupAgent.this.onRestore(backupDataInput, j, parcelFileDescriptor2, list != null ? new HashSet<>(list) : Collections.EMPTY_SET);
                BackupAgent.this.reloadSharedPreferences();
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                try {
                    iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, 0L);
                } catch (RemoteException unused2) {
                }
                if (Binder.getCallingPid() != Process.myPid()) {
                    IoUtils.closeQuietly(parcelFileDescriptor);
                    IoUtils.closeQuietly(parcelFileDescriptor2);
                }
            } catch (IOException e3) {
                e = e3;
                Log.d(TAG, "onRestore (" + BackupAgent.this.getClass().getName() + ") threw", e);
                throw new RuntimeException(e);
            } catch (RuntimeException e4) {
                e = e4;
                Log.d(TAG, "onRestore (" + BackupAgent.this.getClass().getName() + ") threw", e);
                throw e;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:40:0x00da  */
        /* JADX WARN: Removed duplicated region for block: B:57:? A[SYNTHETIC] */
        @Override // android.app.IBackupAgent
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void doFullBackup(ParcelFileDescriptor parcelFileDescriptor, long j, int i, IBackupManager iBackupManager, int i2) throws Throwable {
            BackupAgent.this.waitForSharedPrefs();
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                } catch (Throwable th) {
                    th = th;
                    BackupAgent.this.waitForSharedPrefs();
                    try {
                        new FileOutputStream(parcelFileDescriptor.getFileDescriptor()).write(new byte[4]);
                    } catch (IOException unused) {
                        Log.e(TAG, "Unable to finalize backup stream!");
                    }
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    try {
                        iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, 0L);
                    } catch (RemoteException unused2) {
                    }
                    if (Binder.getCallingPid() != Process.myPid()) {
                        throw th;
                    }
                    IoUtils.closeQuietly(parcelFileDescriptor);
                    throw th;
                }
            } catch (IOException e) {
                e = e;
            } catch (RuntimeException e2) {
                e = e2;
            } catch (Throwable th2) {
                th = th2;
                BackupAgent.this.waitForSharedPrefs();
                new FileOutputStream(parcelFileDescriptor.getFileDescriptor()).write(new byte[4]);
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, 0L);
                if (Binder.getCallingPid() != Process.myPid()) {
                }
            }
            try {
                BackupAgent.this.onFullBackup(new FullBackupDataOutput(parcelFileDescriptor, j, i2));
                BackupAgent.this.waitForSharedPrefs();
                try {
                    new FileOutputStream(parcelFileDescriptor.getFileDescriptor()).write(new byte[4]);
                } catch (IOException unused3) {
                    Log.e(TAG, "Unable to finalize backup stream!");
                }
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                try {
                    iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, 0L);
                } catch (RemoteException unused4) {
                }
                if (Binder.getCallingPid() != Process.myPid()) {
                    IoUtils.closeQuietly(parcelFileDescriptor);
                }
            } catch (IOException e3) {
                e = e3;
                Log.d(TAG, "onFullBackup (" + BackupAgent.this.getClass().getName() + ") threw", e);
                throw new RuntimeException(e);
            } catch (RuntimeException e4) {
                e = e4;
                Log.d(TAG, "onFullBackup (" + BackupAgent.this.getClass().getName() + ") threw", e);
                throw e;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:43:0x00e2  */
        /* JADX WARN: Removed duplicated region for block: B:60:? A[SYNTHETIC] */
        @Override // android.app.IBackupAgent
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void doFullBackupPath(ParcelFileDescriptor parcelFileDescriptor, long j, int i, IBackupManager iBackupManager, int i2, String[] strArr) throws Throwable {
            BackupAgent.this.waitForSharedPrefs();
            if (strArr != null) {
                BackupAgent.this.mSmartSwitchBackupPath = strArr;
            }
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    try {
                        BackupAgent.this.onFullBackup(new FullBackupDataOutput(parcelFileDescriptor, j, i2));
                        BackupAgent.this.waitForSharedPrefs();
                        try {
                            new FileOutputStream(parcelFileDescriptor.getFileDescriptor()).write(new byte[4]);
                        } catch (IOException unused) {
                            Log.e(TAG, "Unable to finalize backup stream!");
                        }
                        Binder.restoreCallingIdentity(jClearCallingIdentity);
                        try {
                            iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, 0L);
                        } catch (RemoteException unused2) {
                        }
                        if (Binder.getCallingPid() != Process.myPid()) {
                            IoUtils.closeQuietly(parcelFileDescriptor);
                        }
                    } catch (IOException e) {
                        e = e;
                        Log.d(TAG, "onFullBackup (" + BackupAgent.this.getClass().getName() + ") threw", e);
                        throw new RuntimeException(e);
                    } catch (RuntimeException e2) {
                        e = e2;
                        Log.d(TAG, "onFullBackup (" + BackupAgent.this.getClass().getName() + ") threw", e);
                        throw e;
                    }
                } catch (Throwable th) {
                    th = th;
                    BackupAgent.this.waitForSharedPrefs();
                    try {
                        new FileOutputStream(parcelFileDescriptor.getFileDescriptor()).write(new byte[4]);
                    } catch (IOException unused3) {
                        Log.e(TAG, "Unable to finalize backup stream!");
                    }
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    try {
                        iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, 0L);
                    } catch (RemoteException unused4) {
                    }
                    if (Binder.getCallingPid() != Process.myPid()) {
                        throw th;
                    }
                    IoUtils.closeQuietly(parcelFileDescriptor);
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
            } catch (RuntimeException e4) {
                e = e4;
            } catch (Throwable th2) {
                th = th2;
                BackupAgent.this.waitForSharedPrefs();
                new FileOutputStream(parcelFileDescriptor.getFileDescriptor()).write(new byte[4]);
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, 0L);
                if (Binder.getCallingPid() != Process.myPid()) {
                }
            }
        }

        @Override // android.app.IBackupAgent
        public void doDisableDataExtractionRules(boolean z) {
            BackupAgent.this.mDisableDataExtractionRule = z;
        }

        @Override // android.app.IBackupAgent
        public void doMeasureFullBackup(long j, int i, IBackupManager iBackupManager, int i2) throws InterruptedException {
            FullBackupDataOutput fullBackupDataOutput = new FullBackupDataOutput(j, i2);
            BackupAgent.this.waitForSharedPrefs();
            long jClearCallingIdentity = Binder.clearCallingIdentity();
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
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                try {
                    iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, fullBackupDataOutput.getSize());
                } catch (RemoteException unused2) {
                }
            }
        }

        @Override // android.app.IBackupAgent
        public void doRestoreFile(ParcelFileDescriptor parcelFileDescriptor, long j, int i, String str, String str2, long j2, long j3, int i2, IBackupManager iBackupManager) throws InterruptedException, IOException, RemoteException {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.onRestoreFile(parcelFileDescriptor, j, i, str, str2, j2, j3);
                    BackupAgent.this.waitForSharedPrefs();
                    BackupAgent.this.reloadSharedPreferences();
                    if (Flags.enableClearPipeAfterRestoreFile()) {
                        clearUnconsumedDataFromPipe(parcelFileDescriptor, j);
                    }
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    try {
                        iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i2, 0L);
                    } catch (RemoteException unused) {
                    }
                    if (Binder.getCallingPid() != Process.myPid()) {
                        IoUtils.closeQuietly(parcelFileDescriptor);
                    }
                } catch (IOException e) {
                    Log.d(TAG, "onRestoreFile (" + BackupAgent.this.getClass().getName() + ") threw", e);
                    throw new RuntimeException(e);
                }
            } finally {
            }
        }

        private static void clearUnconsumedDataFromPipe(ParcelFileDescriptor parcelFileDescriptor, long j) throws IOException {
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
        public void doRestoreFinished(int i, IBackupManager iBackupManager) throws InterruptedException {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.onRestoreFinished();
                    try {
                        iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, 0L);
                    } catch (RemoteException unused) {
                    }
                } finally {
                    BackupAgent.this.waitForSharedPrefs();
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    try {
                        iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, 0L);
                    } catch (RemoteException unused2) {
                    }
                }
            } catch (Exception e) {
                Log.d(TAG, "onRestoreFinished (" + BackupAgent.this.getClass().getName() + ") threw", e);
                throw e;
            }
        }

        @Override // android.app.IBackupAgent
        public void fail(String str) {
            BackupAgent.this.getHandler().post(new FailRunnable(str));
        }

        @Override // android.app.IBackupAgent
        public void doQuotaExceeded(long j, long j2, IBackupCallback iBackupCallback) throws InterruptedException {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.onQuotaExceeded(j, j2);
                    BackupAgent.this.waitForSharedPrefs();
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    try {
                        iBackupCallback.operationComplete(0L);
                    } catch (RemoteException unused) {
                    }
                } catch (Exception e) {
                    Log.d(TAG, "onQuotaExceeded(" + BackupAgent.this.getClass().getName() + ") threw", e);
                    throw e;
                }
            } catch (Throwable th) {
                BackupAgent.this.waitForSharedPrefs();
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                try {
                    iBackupCallback.operationComplete(-1L);
                } catch (RemoteException unused2) {
                }
                throw th;
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
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.clearBackupRestoreEventLogger();
                } catch (Exception e) {
                    Log.d(TAG, "clearBackupRestoreEventLogger (" + BackupAgent.this.getClass().getName() + ") threw", e);
                    throw e;
                }
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
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
