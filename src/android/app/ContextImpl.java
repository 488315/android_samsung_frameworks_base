package android.app;

import android.Manifest;
import android.app.LoadedApk;
import android.app.wearsettings.WearSettingsEnums;
import android.companion.virtual.VirtualDeviceManager;
import android.content.AttributionSource;
import android.content.AutofillOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextParams;
import android.content.ContextWrapper;
import android.content.IContentProvider;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.CompatResources;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.loader.ResourcesLoader;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.media.MediaMetrics;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.permission.PermissionControllerManager;
import android.permission.PermissionManager;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.kioskmode.KioskMode;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayAdjustments;
import android.view.DisplayInfo;
import android.view.autofill.AutofillManager;
import android.window.SystemUiContext;
import android.window.WindowContext;
import android.window.WindowTokenClient;
import android.window.WindowTokenClientController;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.Preconditions;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.common.AsPackageName;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.sepunion.SemUnionManager;
import com.samsung.android.sepunion.UnionUtils;
import dalvik.system.BlockGuard;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.IntConsumer;
import libcore.io.Memory;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class ContextImpl extends Context {
    private static final int CONTEXT_TYPE_ACTIVITY = 2;
    private static final int CONTEXT_TYPE_DISPLAY_CONTEXT = 1;
    private static final int CONTEXT_TYPE_NON_UI = 0;
    private static final int CONTEXT_TYPE_SYSTEM_OR_SYSTEM_UI = 4;
    private static final int CONTEXT_TYPE_WINDOW_CONTEXT = 3;
    private static final boolean DEBUG = false;
    static final int STATE_INITIALIZING = 1;
    static final int STATE_NOT_FOUND = 3;
    static final int STATE_READY = 2;
    static final int STATE_UNINITIALIZED = 0;
    private static final String TAG = "ContextImpl";
    private static final String XATTR_INODE_CACHE = "user.inode_cache";
    private static final String XATTR_INODE_CODE_CACHE = "user.inode_code_cache";
    private static ArrayMap<String, ArrayMap<File, SharedPreferencesImpl>> sSharedPrefsCache;
    private AttributionSource mAttributionSource;
    private AutofillOptions mAutofillOptions;
    private final String mBasePackageName;
    private File mCacheDir;
    private ClassLoader mClassLoader;
    private File mCodeCacheDir;
    private ContentCaptureOptions mContentCaptureOptions;
    private final ApplicationContentResolver mContentResolver;
    private int mContextType;
    private File mCratesDir;
    private File mDatabasesDir;
    private int mDeviceId;
    private ArrayList<DeviceIdChangeListenerDelegate> mDeviceIdChangeListeners;
    private final Object mDeviceIdListenerLock;
    private Display mDisplay;
    private File mFilesDir;
    private final int mFlags;
    private boolean mForceDisplayOverrideInResources;
    private boolean mIsConfigurationBasedContext;
    private boolean mIsExplicitDeviceId;
    final ActivityThread mMainThread;
    private File mNoBackupFilesDir;
    private final String mOpPackageName;
    private Context mOuterContext;
    final LoadedApk mPackageInfo;
    private PackageManager mPackageManager;
    private final ContextParams mParams;
    private File mPreferencesDir;
    private Resources mResources;
    private final ResourcesManager mResourcesManager;
    final Object[] mServiceCache;
    final int[] mServiceInitializationStateArray;
    private ArrayMap<String, File> mSharedPrefsPaths;
    private String mSplitName;
    private final IBinder mToken;
    private final UserHandle mUser;
    private final Object mThemeLock = new Object();
    private int mThemeResource = 0;
    private Resources.Theme mTheme = null;
    private Context mReceiverRestrictedContext = null;
    private AutofillManager.AutofillClient mAutofillClient = null;
    private final Object mSync = new Object();
    private boolean mOwnsToken = false;
    private final Object mDatabasesDirLock = new Object();
    private final Object mPreferencesDirLock = new Object();
    private final Object mFilesDirLock = new Object();
    private final Object mCratesDirLock = new Object();
    private final Object mNoBackupFilesDirLock = new Object();
    private final Object mCacheDirLock = new Object();
    private final Object mCodeCacheDirLock = new Object();
    private final Object mMiscDirsLock = new Object();

    @Retention(RetentionPolicy.SOURCE)
    private @interface ContextType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface ServiceInitializationState {
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class DeviceIdChangeListenerDelegate {
        final Executor mExecutor;
        final IntConsumer mListener;

        DeviceIdChangeListenerDelegate(IntConsumer intConsumer, Executor executor) {
            this.mListener = intConsumer;
            this.mExecutor = executor;
        }
    }

    static ContextImpl getImpl(Context context) {
        Context baseContext;
        while ((context instanceof ContextWrapper) && (baseContext = ((ContextWrapper) context).getBaseContext()) != null) {
            context = baseContext;
        }
        return (ContextImpl) context;
    }

    @Override // android.content.Context
    public AssetManager getAssets() {
        return getResources().getAssets();
    }

    @Override // android.content.Context
    public Resources getResources() {
        return this.mResources;
    }

    @Override // android.content.Context
    public PackageManager getPackageManager() {
        PackageManager packageManager = this.mPackageManager;
        if (packageManager != null) {
            return packageManager;
        }
        IPackageManager packageManager2 = ActivityThread.getPackageManager();
        if (packageManager2 == null) {
            return null;
        }
        ApplicationPackageManager applicationPackageManager = new ApplicationPackageManager(this, packageManager2);
        this.mPackageManager = applicationPackageManager;
        return applicationPackageManager;
    }

    @Override // android.content.Context
    public ContentResolver getContentResolver() {
        return this.mContentResolver;
    }

    @Override // android.content.Context
    public Looper getMainLooper() {
        return this.mMainThread.getLooper();
    }

    @Override // android.content.Context
    public Executor getMainExecutor() {
        return this.mMainThread.getExecutor();
    }

    @Override // android.content.Context
    public Context getApplicationContext() {
        LoadedApk loadedApk = this.mPackageInfo;
        return loadedApk != null ? loadedApk.getApplication() : this.mMainThread.getApplication();
    }

    @Override // android.content.Context
    public void setTheme(int i) {
        synchronized (this.mThemeLock) {
            if (this.mThemeResource != i) {
                this.mThemeResource = i;
                initializeTheme();
            }
        }
    }

    @Override // android.content.Context
    public int getThemeResId() {
        int i;
        synchronized (this.mThemeLock) {
            i = this.mThemeResource;
        }
        return i;
    }

    @Override // android.content.Context
    public Resources.Theme getTheme() {
        synchronized (this.mThemeLock) {
            Resources.Theme theme = this.mTheme;
            if (theme != null) {
                return theme;
            }
            this.mThemeResource = Resources.selectDefaultTheme(this.mThemeResource, getOuterContext().getApplicationInfo().targetSdkVersion);
            initializeTheme();
            return this.mTheme;
        }
    }

    private void initializeTheme() {
        if (this.mTheme == null) {
            this.mTheme = this.mResources.newTheme();
        }
        this.mTheme.applyStyle(this.mThemeResource, true);
    }

    @Override // android.content.Context
    public ClassLoader getClassLoader() {
        ClassLoader classLoader = this.mClassLoader;
        if (classLoader != null) {
            return classLoader;
        }
        LoadedApk loadedApk = this.mPackageInfo;
        return loadedApk != null ? loadedApk.getClassLoader() : ClassLoader.getSystemClassLoader();
    }

    @Override // android.content.Context
    public String getPackageName() {
        LoadedApk loadedApk = this.mPackageInfo;
        if (loadedApk != null) {
            return loadedApk.getPackageName();
        }
        return "android";
    }

    @Override // android.content.Context
    public String getBasePackageName() {
        String str = this.mBasePackageName;
        return str != null ? str : getPackageName();
    }

    @Override // android.content.Context
    public String getOpPackageName() {
        return this.mAttributionSource.getPackageName();
    }

    @Override // android.content.Context
    public String getAttributionTag() {
        return this.mAttributionSource.getAttributionTag();
    }

    @Override // android.content.Context
    public ContextParams getParams() {
        return this.mParams;
    }

    @Override // android.content.Context
    public AttributionSource getAttributionSource() {
        return this.mAttributionSource;
    }

    @Override // android.content.Context
    public ApplicationInfo getApplicationInfo() {
        LoadedApk loadedApk = this.mPackageInfo;
        if (loadedApk != null) {
            return loadedApk.getApplicationInfo();
        }
        throw new RuntimeException("Not supported in system context");
    }

    @Override // android.content.Context
    public String getPackageResourcePath() {
        LoadedApk loadedApk = this.mPackageInfo;
        if (loadedApk != null) {
            return loadedApk.getResDir();
        }
        throw new RuntimeException("Not supported in system context");
    }

    @Override // android.content.Context
    public String getPackageCodePath() {
        LoadedApk loadedApk = this.mPackageInfo;
        if (loadedApk != null) {
            return loadedApk.getAppDir();
        }
        throw new RuntimeException("Not supported in system context");
    }

    @Override // android.content.Context
    public SharedPreferences getSharedPreferences(String str, int i) {
        File sharedPreferencesPath;
        if (this.mPackageInfo.getApplicationInfo().targetSdkVersion < 19 && str == null) {
            str = PerfettoProtoLogImpl.NULL_STRING;
        }
        synchronized (ContextImpl.class) {
            if (this.mSharedPrefsPaths == null) {
                this.mSharedPrefsPaths = new ArrayMap<>();
            }
            sharedPreferencesPath = this.mSharedPrefsPaths.get(str);
            if (sharedPreferencesPath == null) {
                sharedPreferencesPath = getSharedPreferencesPath(str);
                this.mSharedPrefsPaths.put(str, sharedPreferencesPath);
            }
        }
        return getSharedPreferences(sharedPreferencesPath, i);
    }

    @Override // android.content.Context
    public SharedPreferences getSharedPreferences(File file, int i) {
        synchronized (ContextImpl.class) {
            ArrayMap<File, SharedPreferencesImpl> sharedPreferencesCacheLocked = getSharedPreferencesCacheLocked();
            SharedPreferencesImpl sharedPreferencesImpl = sharedPreferencesCacheLocked.get(file);
            if (sharedPreferencesImpl == null) {
                checkMode(i);
                if (getApplicationInfo().targetSdkVersion >= 26 && ((!"android".equals(getPackageName()) || file == null || !file.getName().equals("multiwindow.property.xml")) && isCredentialProtectedStorage())) {
                    UserManager userManager = (UserManager) getSystemService(UserManager.class);
                    if (userManager == null) {
                        throw new IllegalStateException("SharedPreferences cannot be accessed if UserManager is not available. (e.g. from inside an isolated process)");
                    }
                    if (!userManager.isUserUnlockingOrUnlocked(UserHandle.myUserId())) {
                        throw new IllegalStateException("SharedPreferences in credential encrypted storage are not available until after user (id " + UserHandle.myUserId() + ") is unlocked");
                    }
                }
                SharedPreferencesImpl sharedPreferencesImpl2 = new SharedPreferencesImpl(file, i);
                sharedPreferencesCacheLocked.put(file, sharedPreferencesImpl2);
                return sharedPreferencesImpl2;
            }
            if ((i & 4) == 0 && getApplicationInfo().targetSdkVersion >= 11) {
                return sharedPreferencesImpl;
            }
            sharedPreferencesImpl.startReloadIfChangedUnexpectedly();
            return sharedPreferencesImpl;
        }
    }

    private ArrayMap<File, SharedPreferencesImpl> getSharedPreferencesCacheLocked() {
        if (sSharedPrefsCache == null) {
            sSharedPrefsCache = new ArrayMap<>();
        }
        String packageName = getPackageName();
        ArrayMap<File, SharedPreferencesImpl> arrayMap = sSharedPrefsCache.get(packageName);
        if (arrayMap != null) {
            return arrayMap;
        }
        ArrayMap<File, SharedPreferencesImpl> arrayMap2 = new ArrayMap<>();
        sSharedPrefsCache.put(packageName, arrayMap2);
        return arrayMap2;
    }

    @Override // android.content.Context
    public void reloadSharedPreferences() {
        int i;
        ArrayList arrayList = new ArrayList();
        synchronized (ContextImpl.class) {
            ArrayMap<File, SharedPreferencesImpl> sharedPreferencesCacheLocked = getSharedPreferencesCacheLocked();
            for (int i2 = 0; i2 < sharedPreferencesCacheLocked.size(); i2++) {
                SharedPreferencesImpl sharedPreferencesImplValueAt = sharedPreferencesCacheLocked.valueAt(i2);
                if (sharedPreferencesImplValueAt != null) {
                    arrayList.add(sharedPreferencesImplValueAt);
                }
            }
        }
        for (i = 0; i < arrayList.size(); i++) {
            ((SharedPreferencesImpl) arrayList.get(i)).startReloadIfChangedUnexpectedly();
        }
    }

    private static int moveFiles(File file, File file2, final String str) throws IOException, ErrnoException {
        int i = 0;
        for (File file3 : FileUtils.listFilesOrEmpty(file, new FilenameFilter() { // from class: android.app.ContextImpl.1
            @Override // java.io.FilenameFilter
            public boolean accept(File file4, String str2) {
                return str2.startsWith(str);
            }
        })) {
            File file4 = new File(file2, file3.getName());
            Log.d(TAG, "Migrating " + file3 + " to " + file4);
            try {
                FileUtils.copyFileOrThrow(file3, file4);
                FileUtils.copyPermissions(file3, file4);
            } catch (IOException e) {
                Log.w(TAG, "Failed to migrate " + file3 + ": " + e);
                i = -1;
            }
            if (!file3.delete()) {
                throw new IOException("Failed to clean up " + file3);
            }
            if (i != -1) {
                i++;
            }
        }
        return i;
    }

    @Override // android.content.Context
    public boolean moveSharedPreferencesFrom(Context context, String str) {
        boolean z;
        synchronized (ContextImpl.class) {
            File sharedPreferencesPath = context.getSharedPreferencesPath(str);
            File sharedPreferencesPath2 = getSharedPreferencesPath(str);
            int iMoveFiles = moveFiles(sharedPreferencesPath.getParentFile(), sharedPreferencesPath2.getParentFile(), sharedPreferencesPath.getName());
            if (iMoveFiles > 0) {
                ArrayMap<File, SharedPreferencesImpl> sharedPreferencesCacheLocked = getSharedPreferencesCacheLocked();
                sharedPreferencesCacheLocked.remove(sharedPreferencesPath);
                sharedPreferencesCacheLocked.remove(sharedPreferencesPath2);
            }
            z = iMoveFiles != -1;
        }
        return z;
    }

    @Override // android.content.Context
    public boolean deleteSharedPreferences(String str) {
        boolean z;
        synchronized (ContextImpl.class) {
            File sharedPreferencesPath = getSharedPreferencesPath(str);
            File fileMakeBackupFile = SharedPreferencesImpl.makeBackupFile(sharedPreferencesPath);
            getSharedPreferencesCacheLocked().remove(sharedPreferencesPath);
            sharedPreferencesPath.delete();
            fileMakeBackupFile.delete();
            z = (sharedPreferencesPath.exists() || fileMakeBackupFile.exists()) ? false : true;
        }
        return z;
    }

    private File getPreferencesDir() {
        File fileEnsurePrivateDirExists;
        synchronized (this.mPreferencesDirLock) {
            if (this.mPreferencesDir == null) {
                this.mPreferencesDir = new File(getDataDir(), "shared_prefs");
            }
            fileEnsurePrivateDirExists = ensurePrivateDirExists(this.mPreferencesDir);
        }
        return fileEnsurePrivateDirExists;
    }

    @Override // android.content.Context
    public FileInputStream openFileInput(String str) throws FileNotFoundException {
        return new FileInputStream(makeFilename(getFilesDir(), str));
    }

    @Override // android.content.Context
    public FileOutputStream openFileOutput(String str, int i) throws ErrnoException, FileNotFoundException {
        checkMode(i);
        boolean z = (32768 & i) != 0;
        File fileMakeFilename = makeFilename(getFilesDir(), str);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileMakeFilename, z);
            setFilePermissionsFromMode(fileMakeFilename.getPath(), i, 0);
            return fileOutputStream;
        } catch (FileNotFoundException unused) {
            File parentFile = fileMakeFilename.getParentFile();
            parentFile.mkdir();
            FileUtils.setPermissions(parentFile.getPath(), 505, -1, -1);
            FileOutputStream fileOutputStream2 = new FileOutputStream(fileMakeFilename, z);
            setFilePermissionsFromMode(fileMakeFilename.getPath(), i, 0);
            return fileOutputStream2;
        }
    }

    @Override // android.content.Context
    public boolean deleteFile(String str) {
        return makeFilename(getFilesDir(), str).delete();
    }

    private static File ensurePrivateDirExists(File file) {
        return ensurePrivateDirExists(file, 505, -1, null);
    }

    private static File ensurePrivateCacheDirExists(File file, String str) {
        return ensurePrivateDirExists(file, MetricsProto.MetricsEvent.FIELD_PROCESS_RECORD_PROCESS_NAME, UserHandle.getCacheAppGid(Process.myUid()), str);
    }

    private static File ensurePrivateDirExists(File file, int i, int i2, String str) throws ErrnoException {
        if (!file.exists()) {
            String absolutePath = file.getAbsolutePath();
            try {
                Os.mkdir(absolutePath, i);
                Os.chmod(absolutePath, i);
                if (i2 != -1) {
                    Os.chown(absolutePath, -1, i2);
                }
            } catch (ErrnoException e) {
                if (e.errno != OsConstants.EEXIST) {
                    Log.w(TAG, "Failed to ensure " + file + ": " + e.getMessage());
                }
            }
            if (str != null) {
                try {
                    byte[] bArr = new byte[8];
                    Memory.pokeLong(bArr, 0, Os.stat(file.getAbsolutePath()).st_ino, ByteOrder.nativeOrder());
                    Os.setxattr(file.getParentFile().getAbsolutePath(), str, bArr, 0);
                } catch (ErrnoException e2) {
                    Log.w(TAG, "Failed to update " + str + ": " + e2.getMessage());
                }
            }
        }
        return file;
    }

    @Override // android.content.Context
    public File getFilesDir() {
        File fileEnsurePrivateDirExists;
        synchronized (this.mFilesDirLock) {
            if (this.mFilesDir == null) {
                this.mFilesDir = new File(getDataDir(), "files");
            }
            fileEnsurePrivateDirExists = ensurePrivateDirExists(this.mFilesDir);
        }
        return fileEnsurePrivateDirExists;
    }

    @Override // android.content.Context
    public File getCrateDir(String str) {
        Preconditions.checkArgument(FileUtils.isValidExtFilename(str), "invalidated crateId");
        Path pathResolve = getDataDir().toPath().resolve("crates");
        Path pathNormalize = pathResolve.resolve(str).toAbsolutePath().normalize();
        synchronized (this.mCratesDirLock) {
            if (this.mCratesDir == null) {
                this.mCratesDir = pathResolve.toFile();
            }
            ensurePrivateDirExists(this.mCratesDir);
        }
        return ensurePrivateDirExists(pathNormalize.toFile());
    }

    @Override // android.content.Context
    public File getNoBackupFilesDir() {
        File fileEnsurePrivateDirExists;
        synchronized (this.mNoBackupFilesDirLock) {
            if (this.mNoBackupFilesDir == null) {
                this.mNoBackupFilesDir = new File(getDataDir(), "no_backup");
            }
            fileEnsurePrivateDirExists = ensurePrivateDirExists(this.mNoBackupFilesDir);
        }
        return fileEnsurePrivateDirExists;
    }

    @Override // android.content.Context
    public File getExternalFilesDir(String str) {
        File[] externalFilesDirs = getExternalFilesDirs(str);
        if (externalFilesDirs == null || externalFilesDirs.length <= 0) {
            return null;
        }
        return externalFilesDirs[0];
    }

    @Override // android.content.Context
    public File[] getExternalFilesDirs(String str) {
        File[] fileArrEnsureExternalDirsExistOrFilter;
        synchronized (this.mMiscDirsLock) {
            File[] fileArrBuildExternalStorageAppFilesDirs = Environment.buildExternalStorageAppFilesDirs(getPackageName());
            if (str != null) {
                fileArrBuildExternalStorageAppFilesDirs = Environment.buildPaths(fileArrBuildExternalStorageAppFilesDirs, str);
            }
            fileArrEnsureExternalDirsExistOrFilter = ensureExternalDirsExistOrFilter(fileArrBuildExternalStorageAppFilesDirs, true);
        }
        return fileArrEnsureExternalDirsExistOrFilter;
    }

    @Override // android.content.Context
    public File getObbDir() {
        File[] obbDirs = getObbDirs();
        if (obbDirs == null || obbDirs.length <= 0) {
            return null;
        }
        return obbDirs[0];
    }

    @Override // android.content.Context
    public File[] getObbDirs() {
        File[] fileArrEnsureExternalDirsExistOrFilter;
        synchronized (this.mMiscDirsLock) {
            fileArrEnsureExternalDirsExistOrFilter = ensureExternalDirsExistOrFilter(Environment.buildExternalStorageAppObbDirs(getPackageName()), true);
        }
        return fileArrEnsureExternalDirsExistOrFilter;
    }

    @Override // android.content.Context
    public File getCacheDir() {
        File fileEnsurePrivateCacheDirExists;
        synchronized (this.mCacheDirLock) {
            if (this.mCacheDir == null) {
                this.mCacheDir = new File(getDataDir(), "cache");
            }
            fileEnsurePrivateCacheDirExists = ensurePrivateCacheDirExists(this.mCacheDir, XATTR_INODE_CACHE);
        }
        return fileEnsurePrivateCacheDirExists;
    }

    @Override // android.content.Context
    public File getCodeCacheDir() {
        File fileEnsurePrivateCacheDirExists;
        synchronized (this.mCodeCacheDirLock) {
            if (this.mCodeCacheDir == null) {
                this.mCodeCacheDir = getCodeCacheDirBeforeBind(getDataDir());
            }
            fileEnsurePrivateCacheDirExists = ensurePrivateCacheDirExists(this.mCodeCacheDir, XATTR_INODE_CODE_CACHE);
        }
        return fileEnsurePrivateCacheDirExists;
    }

    static File getCodeCacheDirBeforeBind(File file) {
        return new File(file, "code_cache");
    }

    @Override // android.content.Context
    public File getExternalCacheDir() {
        File[] externalCacheDirs = getExternalCacheDirs();
        if (externalCacheDirs == null || externalCacheDirs.length <= 0) {
            return null;
        }
        return externalCacheDirs[0];
    }

    @Override // android.content.Context
    public File[] getExternalCacheDirs() {
        File[] fileArrEnsureExternalDirsExistOrFilter;
        synchronized (this.mMiscDirsLock) {
            fileArrEnsureExternalDirsExistOrFilter = ensureExternalDirsExistOrFilter(Environment.buildExternalStorageAppCacheDirs(getPackageName()), false);
        }
        return fileArrEnsureExternalDirsExistOrFilter;
    }

    @Override // android.content.Context
    public File[] getExternalMediaDirs() {
        File[] fileArrEnsureExternalDirsExistOrFilter;
        synchronized (this.mMiscDirsLock) {
            fileArrEnsureExternalDirsExistOrFilter = ensureExternalDirsExistOrFilter(Environment.buildExternalStorageAppMediaDirs(getPackageName()), true);
        }
        return fileArrEnsureExternalDirsExistOrFilter;
    }

    @Override // android.content.Context
    public File getPreloadsFileCache() {
        return Environment.getDataPreloadsFileCacheDirectory(getPackageName());
    }

    @Override // android.content.Context
    public File getFileStreamPath(String str) {
        return makeFilename(getFilesDir(), str);
    }

    @Override // android.content.Context
    public File getSharedPreferencesPath(String str) {
        File fileMakeFilename;
        if ("android".equals(getPackageName()) && MultiWindowCoreState.MW_SHARED_PREF_NAME.equals(str)) {
            synchronized (this.mSync) {
                if (this.mPreferencesDir == null) {
                    this.mPreferencesDir = new File(this.mPackageInfo.getApplicationInfo().dataDir, "shared_prefs");
                }
                fileMakeFilename = makeFilename(ensurePrivateDirExists(this.mPreferencesDir), str + ".xml");
            }
            return fileMakeFilename;
        }
        return makeFilename(getPreferencesDir(), str + ".xml");
    }

    @Override // android.content.Context
    public String[] fileList() {
        return FileUtils.listOrEmpty(getFilesDir());
    }

    @Override // android.content.Context
    public SQLiteDatabase openOrCreateDatabase(String str, int i, SQLiteDatabase.CursorFactory cursorFactory) {
        return openOrCreateDatabase(str, i, cursorFactory, null);
    }

    @Override // android.content.Context
    public SQLiteDatabase openOrCreateDatabase(String str, int i, SQLiteDatabase.CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler) throws ErrnoException {
        checkMode(i);
        File databasePath = getDatabasePath(str);
        int i2 = (i & 8) != 0 ? 805306368 : 268435456;
        if ((i & 16) != 0) {
            i2 |= 16;
        }
        SQLiteDatabase sQLiteDatabaseOpenDatabase = SQLiteDatabase.openDatabase(databasePath.getPath(), cursorFactory, i2, databaseErrorHandler);
        setFilePermissionsFromMode(databasePath.getPath(), i, 0);
        return sQLiteDatabaseOpenDatabase;
    }

    @Override // android.content.Context
    public boolean moveDatabaseFrom(Context context, String str) {
        boolean z;
        synchronized (ContextImpl.class) {
            File databasePath = context.getDatabasePath(str);
            z = moveFiles(databasePath.getParentFile(), getDatabasePath(str).getParentFile(), databasePath.getName()) != -1;
        }
        return z;
    }

    @Override // android.content.Context
    public boolean deleteDatabase(String str) {
        try {
            return SQLiteDatabase.deleteDatabase(getDatabasePath(str));
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // android.content.Context
    public File getDatabasePath(String str) throws ErrnoException {
        if (str.charAt(0) == File.separatorChar) {
            File file = new File(str.substring(0, str.lastIndexOf(File.separatorChar)));
            File file2 = new File(file, str.substring(str.lastIndexOf(File.separatorChar)));
            if (!file.isDirectory() && file.mkdir()) {
                FileUtils.setPermissions(file.getPath(), 505, -1, -1);
            }
            return file2;
        }
        return makeFilename(getDatabasesDir(), str);
    }

    @Override // android.content.Context
    public String[] databaseList() {
        return FileUtils.listOrEmpty(getDatabasesDir());
    }

    private File getDatabasesDir() {
        File fileEnsurePrivateDirExists;
        synchronized (this.mDatabasesDirLock) {
            if (this.mDatabasesDir == null) {
                if ("android".equals(getPackageName())) {
                    this.mDatabasesDir = new File("/data/system");
                } else {
                    this.mDatabasesDir = new File(getDataDir(), "databases");
                }
            }
            fileEnsurePrivateDirExists = ensurePrivateDirExists(this.mDatabasesDir);
        }
        return fileEnsurePrivateDirExists;
    }

    @Override // android.content.Context
    @Deprecated
    public Drawable getWallpaper() {
        return getWallpaperManager().getDrawable();
    }

    @Override // android.content.Context
    @Deprecated
    public Drawable peekWallpaper() {
        return getWallpaperManager().peekDrawable();
    }

    @Override // android.content.Context
    @Deprecated
    public int getWallpaperDesiredMinimumWidth() {
        return getWallpaperManager().getDesiredMinimumWidth();
    }

    @Override // android.content.Context
    @Deprecated
    public int getWallpaperDesiredMinimumHeight() {
        return getWallpaperManager().getDesiredMinimumHeight();
    }

    @Override // android.content.Context
    @Deprecated
    public void setWallpaper(Bitmap bitmap) throws IOException {
        getWallpaperManager().setBitmap(bitmap);
    }

    @Override // android.content.Context
    @Deprecated
    public void setWallpaper(InputStream inputStream) throws IOException {
        getWallpaperManager().setStream(inputStream);
    }

    @Override // android.content.Context
    @Deprecated
    public void clearWallpaper() throws IOException {
        getWallpaperManager().clear();
    }

    private WallpaperManager getWallpaperManager() {
        return (WallpaperManager) getSystemService(WallpaperManager.class);
    }

    @Override // android.content.Context
    public void startActivity(Intent intent) {
        warnIfCallingFromSystemProcess();
        startActivity(intent, null);
    }

    @Override // android.content.Context
    public void startActivityAsUser(Intent intent, UserHandle userHandle) {
        startActivityAsUser(intent, null, userHandle);
    }

    @Override // android.content.Context
    public void startActivity(Intent intent, Bundle bundle) {
        warnIfCallingFromSystemProcess();
        int i = getApplicationInfo().targetSdkVersion;
        if ((intent.getFlags() & 268435456) == 0 && ((i < 24 || i >= 28) && (bundle == null || ActivityOptions.fromBundle(bundle).getLaunchTaskId() == -1))) {
            throw new AndroidRuntimeException("Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?");
        }
        this.mMainThread.getInstrumentation().execStartActivity(getOuterContext(), this.mMainThread.getApplicationThread(), (IBinder) null, (Activity) null, intent, -1, applyLaunchDisplayIfNeeded(bundle));
    }

    @Override // android.content.Context
    public void startActivityAsUser(Intent intent, Bundle bundle, UserHandle userHandle) {
        try {
            intent.collectExtraIntentKeys();
            ActivityTaskManager.getService().startActivityAsUser(this.mMainThread.getApplicationThread(), getOpPackageName(), getAttributionTag(), intent, intent.resolveTypeIfNeeded(getContentResolver()), null, null, 0, 268435456, null, applyLaunchDisplayIfNeeded(bundle), userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void startActivities(Intent[] intentArr) {
        warnIfCallingFromSystemProcess();
        startActivities(intentArr, null);
    }

    @Override // android.content.Context
    public int startActivitiesAsUser(Intent[] intentArr, Bundle bundle, UserHandle userHandle) {
        if ((intentArr[0].getFlags() & 268435456) == 0) {
            throw new AndroidRuntimeException("Calling startActivities() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag on first Intent. Is this really what you want?");
        }
        return this.mMainThread.getInstrumentation().execStartActivitiesAsUser(getOuterContext(), this.mMainThread.getApplicationThread(), null, null, intentArr, applyLaunchDisplayIfNeeded(bundle), userHandle.getIdentifier());
    }

    @Override // android.content.Context
    public void startActivities(Intent[] intentArr, Bundle bundle) {
        warnIfCallingFromSystemProcess();
        if ((intentArr[0].getFlags() & 268435456) == 0) {
            throw new AndroidRuntimeException("Calling startActivities() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag on first Intent. Is this really what you want?");
        }
        this.mMainThread.getInstrumentation().execStartActivities(getOuterContext(), this.mMainThread.getApplicationThread(), null, null, intentArr, applyLaunchDisplayIfNeeded(bundle));
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0011, code lost:
    
        if (android.app.ActivityOptions.hasLaunchTargetContainer(r0) != false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Bundle applyLaunchDisplayIfNeeded(Bundle bundle) {
        ActivityOptions activityOptionsMakeBasic;
        if (isAssociatedWithDisplay()) {
            if (bundle != null) {
                activityOptionsMakeBasic = ActivityOptions.fromBundle(bundle);
            } else {
                activityOptionsMakeBasic = ActivityOptions.makeBasic();
            }
            return activityOptionsMakeBasic.setLaunchDisplayId(getAssociatedDisplayId()).toBundle();
        }
        return bundle;
    }

    @Override // android.content.Context
    public void startIntentSender(IntentSender intentSender, Intent intent, int i, int i2, int i3) throws IntentSender.SendIntentException, IOException {
        startIntentSender(intentSender, intent, i, i2, i3, null);
    }

    @Override // android.content.Context
    public void startIntentSender(IntentSender intentSender, Intent intent, int i, int i2, int i3, Bundle bundle) throws IntentSender.SendIntentException, IOException {
        String strResolveTypeIfNeeded;
        if (intent != null) {
            try {
                intent.migrateExtraStreamToClipData(this);
                intent.prepareToLeaveProcess(this);
                strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            strResolveTypeIfNeeded = null;
        }
        int iStartActivityIntentSender = ActivityTaskManager.getService().startActivityIntentSender(this.mMainThread.getApplicationThread(), intentSender != null ? intentSender.getTarget() : null, intentSender != null ? intentSender.getWhitelistToken() : null, intent, strResolveTypeIfNeeded, null, null, 0, i, i2, bundle);
        if (iStartActivityIntentSender == -96) {
            throw new IntentSender.SendIntentException();
        }
        Instrumentation.checkStartActivityResult(iStartActivityIntentSender, null);
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissionsAsUser(Intent intent, String[] strArr, UserHandle userHandle) {
        warnIfCallingFromSystemProcess();
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManagerNative.getDefault().broadcastIntent(this.mMainThread.getApplicationThread(), intent, strResolveTypeIfNeeded, null, -1, null, null, strArr, -1, null, false, false, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw new RuntimeException("Failure from system", e);
        }
    }

    @Override // android.content.Context
    public void sendBroadcast(Intent intent) {
        warnIfCallingFromSystemProcess();
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, null, -1, null, null, null, null, null, -1, null, false, false, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcast(Intent intent, String str) {
        warnIfCallingFromSystemProcess();
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        String[] strArr = str == null ? null : new String[]{str};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, null, -1, null, null, strArr, null, null, -1, null, false, false, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissions(Intent intent, String[] strArr) {
        warnIfCallingFromSystemProcess();
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, null, -1, null, null, strArr, null, null, -1, null, false, false, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissions(Intent intent, String[] strArr, Bundle bundle) {
        warnIfCallingFromSystemProcess();
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, null, -1, null, null, strArr, null, null, -1, bundle, false, false, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastAsUserMultiplePermissions(Intent intent, UserHandle userHandle, String[] strArr) {
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, null, -1, null, null, strArr, null, null, -1, null, false, false, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissions(Intent intent, String[] strArr, String[] strArr2, String[] strArr3, BroadcastOptions broadcastOptions) {
        warnIfCallingFromSystemProcess();
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, null, -1, null, null, strArr, strArr2, strArr3, -1, broadcastOptions == null ? null : broadcastOptions.toBundle(), false, false, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcast(Intent intent, String str, Bundle bundle) {
        warnIfCallingFromSystemProcess();
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        String[] stringArray = null;
        String[] strArr = str == null ? null : new String[]{str};
        if (bundle != null) {
            String[] stringArray2 = bundle.getStringArray(BroadcastOptions.KEY_REQUIRE_ALL_OF_PERMISSIONS);
            if (stringArray2 != null) {
                strArr = stringArray2;
            }
            stringArray = bundle.getStringArray(BroadcastOptions.KEY_REQUIRE_NONE_OF_PERMISSIONS);
        }
        String[] strArr2 = stringArray;
        String[] strArr3 = strArr;
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, null, -1, null, null, strArr3, strArr2, null, -1, bundle, false, false, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcast(Intent intent, String str, int i) {
        warnIfCallingFromSystemProcess();
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        String[] strArr = str == null ? null : new String[]{str};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, null, -1, null, null, strArr, null, null, i, null, false, false, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(Intent intent, String str) {
        sendOrderedBroadcast(intent, str, null);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(Intent intent, String str, Bundle bundle) {
        warnIfCallingFromSystemProcess();
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        String[] strArr = str == null ? null : new String[]{str};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, null, -1, null, null, strArr, null, null, -1, bundle, true, false, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(Intent intent, String str, BroadcastReceiver broadcastReceiver, Handler handler, int i, String str2, Bundle bundle) {
        sendOrderedBroadcast(intent, str, -1, broadcastReceiver, handler, i, str2, bundle, (Bundle) null);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(Intent intent, String str, Bundle bundle, BroadcastReceiver broadcastReceiver, Handler handler, int i, String str2, Bundle bundle2) {
        sendOrderedBroadcast(intent, str, -1, broadcastReceiver, handler, i, str2, bundle2, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(Intent intent, String str, int i, BroadcastReceiver broadcastReceiver, Handler handler, int i2, String str2, Bundle bundle) {
        sendOrderedBroadcast(intent, str, i, broadcastReceiver, handler, i2, str2, bundle, (Bundle) null);
    }

    void sendOrderedBroadcast(Intent intent, String str, int i, BroadcastReceiver broadcastReceiver, Handler handler, int i2, String str2, Bundle bundle, Bundle bundle2) {
        IIntentReceiver iIntentReceiver;
        IIntentReceiver iIntentReceiver2;
        warnIfCallingFromSystemProcess();
        if (broadcastReceiver != null) {
            if (this.mPackageInfo != null) {
                iIntentReceiver2 = this.mPackageInfo.getReceiverDispatcher(broadcastReceiver, getOuterContext(), handler == null ? this.mMainThread.getHandler() : handler, this.mMainThread.getInstrumentation(), false);
            } else {
                iIntentReceiver2 = new LoadedApk.ReceiverDispatcher(this.mMainThread.getApplicationThread(), broadcastReceiver, getOuterContext(), handler == null ? this.mMainThread.getHandler() : handler, null, false).getIIntentReceiver();
            }
            iIntentReceiver = iIntentReceiver2;
        } else {
            iIntentReceiver = null;
        }
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        String[] strArr = str != null ? new String[]{str} : null;
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, iIntentReceiver, i2, str2, bundle, strArr, null, null, i, bundle2, true, false, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(Intent intent, UserHandle userHandle) {
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, null, -1, null, null, null, null, null, -1, null, false, false, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(Intent intent, UserHandle userHandle, String str) {
        sendBroadcastAsUser(intent, userHandle, str, -1);
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(Intent intent, UserHandle userHandle, String str, Bundle bundle) {
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        String[] strArr = str == null ? null : new String[]{str};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, null, -1, null, null, strArr, null, null, -1, bundle, false, false, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(Intent intent, UserHandle userHandle, String str, int i) {
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        String[] strArr = str == null ? null : new String[]{str};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, null, -1, null, null, strArr, null, null, i, null, false, false, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle userHandle, String str, BroadcastReceiver broadcastReceiver, Handler handler, int i, String str2, Bundle bundle) {
        sendOrderedBroadcastAsUser(intent, userHandle, str, -1, null, broadcastReceiver, handler, i, str2, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle userHandle, String str, int i, BroadcastReceiver broadcastReceiver, Handler handler, int i2, String str2, Bundle bundle) {
        sendOrderedBroadcastAsUser(intent, userHandle, str, i, null, broadcastReceiver, handler, i2, str2, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle userHandle, String str, int i, Bundle bundle, BroadcastReceiver broadcastReceiver, Handler handler, int i2, String str2, Bundle bundle2) {
        sendOrderedBroadcastAsUserMultiplePermissions(intent, userHandle, str == null ? null : new String[]{str}, i, bundle, broadcastReceiver, handler, i2, str2, bundle2);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastAsUserMultiplePermissions(Intent intent, UserHandle userHandle, String[] strArr, int i, Bundle bundle, BroadcastReceiver broadcastReceiver, Handler handler, int i2, String str, Bundle bundle2) {
        IIntentReceiver iIntentReceiver;
        if (broadcastReceiver == null) {
            iIntentReceiver = null;
        } else if (this.mPackageInfo != null) {
            iIntentReceiver = this.mPackageInfo.getReceiverDispatcher(broadcastReceiver, getOuterContext(), handler == null ? this.mMainThread.getHandler() : handler, this.mMainThread.getInstrumentation(), false);
        } else {
            iIntentReceiver = new LoadedApk.ReceiverDispatcher(this.mMainThread.getApplicationThread(), broadcastReceiver, getOuterContext(), handler == null ? this.mMainThread.getHandler() : handler, null, false).getIIntentReceiver();
        }
        IIntentReceiver iIntentReceiver2 = iIntentReceiver;
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, iIntentReceiver2, i2, str, bundle2, strArr, null, null, i, bundle, true, false, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(Intent intent, String str, String str2, BroadcastReceiver broadcastReceiver, Handler handler, int i, String str3, Bundle bundle) {
        sendOrderedBroadcastAsUser(intent, getUser(), str, !TextUtils.isEmpty(str2) ? AppOpsManager.strOpToOp(str2) : -1, broadcastReceiver, handler, i, str3, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastMultiplePermissions(Intent intent, String[] strArr, String str, BroadcastReceiver broadcastReceiver, Handler handler, int i, String str2, Bundle bundle, Bundle bundle2) {
        sendOrderedBroadcastAsUserMultiplePermissions(intent, getUser(), strArr, !TextUtils.isEmpty(str) ? AppOpsManager.strOpToOp(str) : -1, bundle2, broadcastReceiver, handler, i, str2, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(Intent intent, int i, String str, String str2, BroadcastReceiver broadcastReceiver, Handler handler, String str3, Bundle bundle, Bundle bundle2) {
        sendOrderedBroadcastAsUser(intent, getUser(), str, !TextUtils.isEmpty(str2) ? AppOpsManager.strOpToOp(str2) : -1, bundle2, broadcastReceiver, handler, i, str3, bundle);
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyBroadcast(Intent intent) {
        warnIfCallingFromSystemProcess();
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, null, -1, null, null, null, null, null, -1, null, false, true, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyBroadcast(Intent intent, Bundle bundle) {
        warnIfCallingFromSystemProcess();
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, null, -1, null, null, null, null, null, -1, bundle, false, true, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver broadcastReceiver, Handler handler, int i, String str, Bundle bundle) {
        IIntentReceiver iIntentReceiver;
        warnIfCallingFromSystemProcess();
        if (broadcastReceiver == null) {
            iIntentReceiver = null;
        } else if (this.mPackageInfo != null) {
            iIntentReceiver = this.mPackageInfo.getReceiverDispatcher(broadcastReceiver, getOuterContext(), handler == null ? this.mMainThread.getHandler() : handler, this.mMainThread.getInstrumentation(), false);
        } else {
            iIntentReceiver = new LoadedApk.ReceiverDispatcher(this.mMainThread.getApplicationThread(), broadcastReceiver, getOuterContext(), handler == null ? this.mMainThread.getHandler() : handler, null, false).getIIntentReceiver();
        }
        IIntentReceiver iIntentReceiver2 = iIntentReceiver;
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, iIntentReceiver2, i, str, bundle, null, null, null, -1, null, true, true, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @Deprecated
    public void removeStickyBroadcast(Intent intent) {
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        if (strResolveTypeIfNeeded != null) {
            Intent intent2 = new Intent(intent);
            intent2.setDataAndType(intent2.getData(), strResolveTypeIfNeeded);
            intent = intent2;
        }
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().unbroadcastIntent(this.mMainThread.getApplicationThread(), intent, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyBroadcastAsUser(Intent intent, UserHandle userHandle) {
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, null, -1, null, null, null, null, null, -1, null, false, true, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyBroadcastAsUser(Intent intent, UserHandle userHandle, Bundle bundle) {
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, null, -1, null, null, null, null, null, -1, bundle, false, true, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle userHandle, BroadcastReceiver broadcastReceiver, Handler handler, int i, String str, Bundle bundle) {
        IIntentReceiver iIntentReceiver;
        if (broadcastReceiver == null) {
            iIntentReceiver = null;
        } else if (this.mPackageInfo != null) {
            iIntentReceiver = this.mPackageInfo.getReceiverDispatcher(broadcastReceiver, getOuterContext(), handler == null ? this.mMainThread.getHandler() : handler, this.mMainThread.getInstrumentation(), false);
        } else {
            iIntentReceiver = new LoadedApk.ReceiverDispatcher(this.mMainThread.getApplicationThread(), broadcastReceiver, getOuterContext(), handler == null ? this.mMainThread.getHandler() : handler, null, false).getIIntentReceiver();
        }
        IIntentReceiver iIntentReceiver2 = iIntentReceiver;
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, strResolveTypeIfNeeded, iIntentReceiver2, i, str, bundle, null, null, null, -1, null, true, true, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @Deprecated
    public void removeStickyBroadcastAsUser(Intent intent, UserHandle userHandle) {
        String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        if (strResolveTypeIfNeeded != null) {
            Intent intent2 = new Intent(intent);
            intent2.setDataAndType(intent2.getData(), strResolveTypeIfNeeded);
            intent = intent2;
        }
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().unbroadcastIntent(this.mMainThread.getApplicationThread(), intent, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        return registerReceiver(broadcastReceiver, intentFilter, null, null);
    }

    @Override // android.content.Context
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, int i) {
        return registerReceiver(broadcastReceiver, intentFilter, null, null, i);
    }

    @Override // android.content.Context
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, Handler handler) {
        return registerReceiverInternal(broadcastReceiver, getUserId(), intentFilter, str, handler, getOuterContext(), 0);
    }

    @Override // android.content.Context
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, Handler handler, int i) {
        return registerReceiverInternal(broadcastReceiver, getUserId(), intentFilter, str, handler, getOuterContext(), i);
    }

    @Override // android.content.Context
    public Intent registerReceiverForAllUsers(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, Handler handler) {
        return registerReceiverAsUser(broadcastReceiver, UserHandle.ALL, intentFilter, str, handler);
    }

    @Override // android.content.Context
    public Intent registerReceiverForAllUsers(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, Handler handler, int i) {
        return registerReceiverAsUser(broadcastReceiver, UserHandle.ALL, intentFilter, str, handler, i);
    }

    @Override // android.content.Context
    public Intent registerReceiverAsUser(BroadcastReceiver broadcastReceiver, UserHandle userHandle, IntentFilter intentFilter, String str, Handler handler) {
        return registerReceiverInternal(broadcastReceiver, userHandle.getIdentifier(), intentFilter, str, handler, getOuterContext(), 0);
    }

    @Override // android.content.Context
    public Intent registerReceiverAsUser(BroadcastReceiver broadcastReceiver, UserHandle userHandle, IntentFilter intentFilter, String str, Handler handler, int i) {
        return registerReceiverInternal(broadcastReceiver, userHandle.getIdentifier(), intentFilter, str, handler, getOuterContext(), i);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0066 A[Catch: RemoteException -> 0x0098, TryCatch #0 {RemoteException -> 0x0098, blocks: (B:17:0x004a, B:19:0x0050, B:22:0x0085, B:20:0x0066), top: B:27:0x004a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Intent registerReceiverInternal(BroadcastReceiver broadcastReceiver, int i, IntentFilter intentFilter, String str, Handler handler, Context context, int i2) {
        IIntentReceiver iIntentReceiver;
        Intent intent;
        if (broadcastReceiver == null) {
            iIntentReceiver = null;
        } else if (this.mPackageInfo != null && context != null) {
            iIntentReceiver = this.mPackageInfo.getReceiverDispatcher(broadcastReceiver, context, handler == null ? this.mMainThread.getHandler() : handler, this.mMainThread.getInstrumentation(), true);
        } else {
            iIntentReceiver = new LoadedApk.ReceiverDispatcher(this.mMainThread.getApplicationThread(), broadcastReceiver, context, handler == null ? this.mMainThread.getHandler() : handler, null, true).getIIntentReceiver();
        }
        IIntentReceiver iIntentReceiver2 = iIntentReceiver;
        if (broadcastReceiver == null) {
            try {
                if (BroadcastStickyCache.useCache(intentFilter)) {
                    intent = BroadcastStickyCache.getIntent(this.mMainThread.getApplicationThread(), this.mBasePackageName, getAttributionTag(), intentFilter, str, i, i2);
                } else {
                    intent = ActivityManager.getService().registerReceiverWithFeature(this.mMainThread.getApplicationThread(), this.mBasePackageName, getAttributionTag(), AppOpsManager.toReceiverId(broadcastReceiver), iIntentReceiver2, intentFilter, str, i, i2);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        if (intent != null) {
            intent.setExtrasClassLoader(getClassLoader());
            intent.prepareToEnterProcess(ActivityThread.isProtectedBroadcast(intent), getAttributionSource());
        }
        return intent;
    }

    @Override // android.content.Context
    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        LoadedApk loadedApk = this.mPackageInfo;
        if (loadedApk != null) {
            try {
                ActivityManager.getService().unregisterReceiver(loadedApk.forgetReceiverDispatcher(getOuterContext(), broadcastReceiver));
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        throw new RuntimeException("Not supported in system context");
    }

    @Override // android.content.Context
    public List<IntentFilter> getRegisteredIntentFilters(BroadcastReceiver broadcastReceiver) {
        LoadedApk loadedApk = this.mPackageInfo;
        if (loadedApk != null) {
            try {
                List<IntentFilter> registeredIntentFilters = ActivityManager.getService().getRegisteredIntentFilters(loadedApk.findRegisteredReceiverDispatcher(broadcastReceiver, getOuterContext()));
                return registeredIntentFilters == null ? new ArrayList() : registeredIntentFilters;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        throw new RuntimeException("Not supported in system context");
    }

    private void validateServiceIntent(Intent intent) {
        if (intent.getComponent() == null && intent.getPackage() == null) {
            if (getApplicationInfo().targetSdkVersion >= 21) {
                throw new IllegalArgumentException("Service Intent must be explicit: " + intent);
            }
            Log.w(TAG, "Implicit intents with startService are not safe: " + intent + " " + Debug.getCallers(2, 3));
        }
    }

    @Override // android.content.Context
    public ComponentName startService(Intent intent) {
        warnIfCallingFromSystemProcess();
        return startServiceCommon(intent, false, this.mUser);
    }

    @Override // android.content.Context
    public ComponentName startForegroundService(Intent intent) {
        warnIfCallingFromSystemProcess();
        return startServiceCommon(intent, true, this.mUser);
    }

    @Override // android.content.Context
    public boolean stopService(Intent intent) {
        warnIfCallingFromSystemProcess();
        return stopServiceCommon(intent, this.mUser);
    }

    @Override // android.content.Context
    public ComponentName startServiceAsUser(Intent intent, UserHandle userHandle) {
        return startServiceCommon(intent, false, userHandle);
    }

    @Override // android.content.Context
    public ComponentName startForegroundServiceAsUser(Intent intent, UserHandle userHandle) {
        return startServiceCommon(intent, true, userHandle);
    }

    private ComponentName startServiceCommon(Intent intent, boolean z, UserHandle userHandle) {
        try {
            ComponentName component = intent.getComponent();
            if (component != null && component.getPackageName() != null && KioskMode.MINI_TASK_MANAGER_PKGNAME.equals(component.getPackageName()) && !EnterpriseDeviceManager.getInstance().getKioskMode().isTaskManagerAllowed(true)) {
                return null;
            }
            validateServiceIntent(intent);
            intent.prepareToLeaveProcess(this);
            ComponentName componentNameStartService = ActivityManager.getService().startService(this.mMainThread.getApplicationThread(), intent, intent.resolveTypeIfNeeded(getContentResolver()), z, getOpPackageName(), getAttributionTag(), userHandle.getIdentifier());
            if (componentNameStartService != null) {
                if (componentNameStartService.getPackageName().equals("!")) {
                    throw new SecurityException("Not allowed to start service " + intent + " without permission " + componentNameStartService.getClassName());
                }
                if (componentNameStartService.getPackageName().equals("!!")) {
                    throw new SecurityException("Unable to start service " + intent + ": " + componentNameStartService.getClassName());
                }
                if (componentNameStartService.getPackageName().equals("?")) {
                    throw ServiceStartNotAllowedException.newInstance(z, "Not allowed to start service " + intent + ": " + componentNameStartService.getClassName());
                }
            }
            if (componentNameStartService != null && z && componentNameStartService.getPackageName().equals(getOpPackageName())) {
                Service.setStartForegroundServiceStackTrace(componentNameStartService.getClassName(), new StackTrace("Last startServiceCommon() call for this service was made here"));
            }
            return componentNameStartService;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public boolean stopServiceAsUser(Intent intent, UserHandle userHandle) {
        return stopServiceCommon(intent, userHandle);
    }

    private boolean stopServiceCommon(Intent intent, UserHandle userHandle) {
        try {
            validateServiceIntent(intent);
            intent.prepareToLeaveProcess(this);
            int iStopService = ActivityManager.getService().stopService(this.mMainThread.getApplicationThread(), intent, intent.resolveTypeIfNeeded(getContentResolver()), userHandle.getIdentifier());
            if (iStopService >= 0) {
                return iStopService != 0;
            }
            throw new SecurityException("Not allowed to stop service " + intent);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        warnIfCallingFromSystemProcess();
        return bindServiceCommon(intent, serviceConnection, Integer.toUnsignedLong(i), null, this.mMainThread.getHandler(), null, getUser());
    }

    @Override // android.content.Context
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, Context.BindServiceFlags bindServiceFlags) {
        warnIfCallingFromSystemProcess();
        return bindServiceCommon(intent, serviceConnection, bindServiceFlags.getValue(), null, this.mMainThread.getHandler(), null, getUser());
    }

    @Override // android.content.Context
    public boolean bindService(Intent intent, int i, Executor executor, ServiceConnection serviceConnection) {
        return bindServiceCommon(intent, serviceConnection, Integer.toUnsignedLong(i), null, null, executor, getUser());
    }

    @Override // android.content.Context
    public boolean bindService(Intent intent, Context.BindServiceFlags bindServiceFlags, Executor executor, ServiceConnection serviceConnection) {
        return bindServiceCommon(intent, serviceConnection, bindServiceFlags.getValue(), null, null, executor, getUser());
    }

    @Override // android.content.Context
    public boolean bindIsolatedService(Intent intent, int i, String str, Executor executor, ServiceConnection serviceConnection) {
        warnIfCallingFromSystemProcess();
        if (str == null) {
            throw new NullPointerException("null instanceName");
        }
        return bindServiceCommon(intent, serviceConnection, Integer.toUnsignedLong(i), str, null, executor, getUser());
    }

    @Override // android.content.Context
    public boolean bindIsolatedService(Intent intent, Context.BindServiceFlags bindServiceFlags, String str, Executor executor, ServiceConnection serviceConnection) {
        warnIfCallingFromSystemProcess();
        if (str == null) {
            throw new NullPointerException("null instanceName");
        }
        return bindServiceCommon(intent, serviceConnection, bindServiceFlags.getValue(), str, null, executor, getUser());
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(Intent intent, ServiceConnection serviceConnection, int i, UserHandle userHandle) {
        return bindServiceCommon(intent, serviceConnection, Integer.toUnsignedLong(i), null, this.mMainThread.getHandler(), null, userHandle);
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(Intent intent, ServiceConnection serviceConnection, Context.BindServiceFlags bindServiceFlags, UserHandle userHandle) {
        return bindServiceCommon(intent, serviceConnection, bindServiceFlags.getValue(), null, this.mMainThread.getHandler(), null, userHandle);
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(Intent intent, ServiceConnection serviceConnection, int i, Handler handler, UserHandle userHandle) {
        if (handler == null) {
            throw new IllegalArgumentException("handler must not be null.");
        }
        return bindServiceCommon(intent, serviceConnection, Integer.toUnsignedLong(i), null, handler, null, userHandle);
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(Intent intent, ServiceConnection serviceConnection, Context.BindServiceFlags bindServiceFlags, Handler handler, UserHandle userHandle) {
        if (handler == null) {
            throw new IllegalArgumentException("handler must not be null.");
        }
        return bindServiceCommon(intent, serviceConnection, bindServiceFlags.getValue(), null, handler, null, userHandle);
    }

    @Override // android.content.Context
    public IServiceConnection getServiceDispatcher(ServiceConnection serviceConnection, Handler handler, long j) {
        return this.mPackageInfo.getServiceDispatcher(serviceConnection, getOuterContext(), handler, j);
    }

    @Override // android.content.Context
    public IApplicationThread getIApplicationThread() {
        return this.mMainThread.getApplicationThread();
    }

    @Override // android.content.Context
    public IBinder getProcessToken() {
        return getIApplicationThread().asBinder();
    }

    @Override // android.content.Context
    public Handler getMainThreadHandler() {
        return this.mMainThread.getHandler();
    }

    private boolean bindServiceCommon(Intent intent, ServiceConnection serviceConnection, long j, String str, Handler handler, Executor executor, UserHandle userHandle) {
        IServiceConnection serviceDispatcher;
        LoadedApk loadedApk;
        if (serviceConnection == null) {
            throw new IllegalArgumentException("connection is null");
        }
        if (handler != null && executor != null) {
            throw new IllegalArgumentException("Handler and Executor both supplied");
        }
        LoadedApk loadedApk2 = this.mPackageInfo;
        if (loadedApk2 == null) {
            throw new RuntimeException("Not supported in system context");
        }
        if (executor != null) {
            serviceDispatcher = loadedApk2.getServiceDispatcher(serviceConnection, getOuterContext(), executor, j);
        } else {
            serviceDispatcher = loadedApk2.getServiceDispatcher(serviceConnection, getOuterContext(), handler, j);
        }
        IServiceConnection iServiceConnection = serviceDispatcher;
        validateServiceIntent(intent);
        try {
            long j2 = (getActivityToken() != null || (1 & j) != 0 || (loadedApk = this.mPackageInfo) == null || loadedApk.getApplicationInfo().targetSdkVersion >= 14) ? j : 32 | j;
            intent.prepareToLeaveProcess(this);
            int iBindServiceInstance = ActivityManager.getService().bindServiceInstance(this.mMainThread.getApplicationThread(), getActivityToken(), intent, intent.resolveTypeIfNeeded(getContentResolver()), iServiceConnection, j2, str, getOpPackageName(), userHandle.getIdentifier());
            if (iBindServiceInstance >= 0) {
                return iBindServiceInstance != 0;
            }
            throw new SecurityException("Not allowed to bind to service " + intent);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void updateServiceGroup(ServiceConnection serviceConnection, int i, int i2) {
        if (serviceConnection == null) {
            throw new IllegalArgumentException("connection is null");
        }
        LoadedApk loadedApk = this.mPackageInfo;
        if (loadedApk != null) {
            IServiceConnection iServiceConnectionLookupServiceDispatcher = loadedApk.lookupServiceDispatcher(serviceConnection, getOuterContext());
            if (iServiceConnectionLookupServiceDispatcher == null) {
                throw new IllegalArgumentException("ServiceConnection not currently bound: " + serviceConnection);
            }
            try {
                ActivityManager.getService().updateServiceGroup(iServiceConnectionLookupServiceDispatcher, i, i2);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        throw new RuntimeException("Not supported in system context");
    }

    @Override // android.content.Context
    public void unbindService(ServiceConnection serviceConnection) {
        if (serviceConnection == null) {
            throw new IllegalArgumentException("connection is null");
        }
        LoadedApk loadedApk = this.mPackageInfo;
        if (loadedApk != null) {
            try {
                ActivityManager.getService().unbindService(loadedApk.forgetServiceDispatcher(getOuterContext(), serviceConnection));
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        throw new RuntimeException("Not supported in system context");
    }

    @Override // android.content.Context
    public boolean startInstrumentation(ComponentName componentName, String str, Bundle bundle) {
        if (bundle != null) {
            try {
                bundle.setAllowFds(false);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return ActivityManager.getService().startInstrumentation(componentName, str, 0, bundle, null, null, getUserId(), null);
    }

    @Override // android.content.Context
    public Object getSystemService(String str) {
        SemUnionManager semUnionManager;
        if (StrictMode.vmIncorrectContextUseEnabled() && Context.WINDOW_SERVICE.equals(str) && !isUiContext()) {
            String str2 = "Tried to access visual service " + SystemServiceRegistry.getSystemServiceClassName(str) + " from a non-visual Context:" + getOuterContext();
            IllegalAccessException illegalAccessException = new IllegalAccessException(str2);
            StrictMode.onIncorrectContextUsed("WindowManager should be accessed from Activity or other visual Context. Use an Activity or a Context created with Context#createWindowContext(int, Bundle), which are adjusted to the configuration and visual bounds of an area on screen.", illegalAccessException);
            Log.e(TAG, str2 + " WindowManager should be accessed from Activity or other visual Context. Use an Activity or a Context created with Context#createWindowContext(int, Bundle), which are adjusted to the configuration and visual bounds of an area on screen.", illegalAccessException);
        }
        Object systemService = SystemServiceRegistry.getSystemService(this, str);
        if (systemService != null) {
            return systemService;
        }
        if (UnionUtils.FEATURE_ENABLED && SemUnionManager.isUnionService(str) && (semUnionManager = (SemUnionManager) SystemServiceRegistry.getSystemService(this, Context.SEP_UNION_SERVICE)) != null) {
            return semUnionManager.getUnionService(str);
        }
        return null;
    }

    @Override // android.content.Context
    public String getSystemServiceName(Class<?> cls) {
        return SystemServiceRegistry.getSystemServiceName(cls);
    }

    @Override // android.content.Context
    public boolean isUiContext() {
        int i = this.mContextType;
        return i == 2 || i == 3 || i == 4;
    }

    @Override // android.content.Context
    public boolean isConfigurationContext() {
        return isUiContext() || this.mIsConfigurationBasedContext;
    }

    private static boolean isSystemOrSystemUI(Context context) {
        return ActivityThread.isSystem() || context.checkPermission(Manifest.permission.STATUS_BAR_SERVICE, Binder.getCallingPid(), Binder.getCallingUid()) == 0;
    }

    @Override // android.content.Context
    public int checkPermission(String str, int i, int i2) {
        if (str == null) {
            throw new IllegalArgumentException("permission is null");
        }
        if (this.mParams.isRenouncedPermission(str) && i == Process.myPid() && i2 == Process.myUid()) {
            Log.v(TAG, "Treating renounced permission " + str + " as denied");
            return -1;
        }
        return PermissionManager.checkPermission(str, i, i2, PermissionManager.resolveDeviceIdForPermissionCheck(this, getDeviceId(), str));
    }

    @Override // android.content.Context
    public int checkPermission(String str, int i, int i2, IBinder iBinder) {
        if (str == null) {
            throw new IllegalArgumentException("permission is null");
        }
        if (this.mParams.isRenouncedPermission(str) && i == Process.myPid() && i2 == Process.myUid()) {
            Log.v(TAG, "Treating renounced permission " + str + " as denied");
            return -1;
        }
        return checkPermission(str, i, i2);
    }

    @Override // android.content.Context
    public void revokeSelfPermissionsOnKill(Collection<String> collection) {
        ((PermissionControllerManager) getSystemService(PermissionControllerManager.class)).revokeSelfPermissionsOnKill(getPackageName(), new ArrayList(collection));
    }

    @Override // android.content.Context
    public int checkCallingPermission(String str) {
        if (str == null) {
            throw new IllegalArgumentException("permission is null");
        }
        int callingPid = Binder.getCallingPid();
        if (callingPid != Process.myPid()) {
            return checkPermission(str, callingPid, Binder.getCallingUid());
        }
        return -1;
    }

    @Override // android.content.Context
    public int checkCallingOrSelfPermission(String str) {
        if (str == null) {
            throw new IllegalArgumentException("permission is null");
        }
        return checkPermission(str, Binder.getCallingPid(), Binder.getCallingUid());
    }

    @Override // android.content.Context
    public int checkSelfPermission(String str) {
        if (str == null) {
            throw new IllegalArgumentException("permission is null");
        }
        if (this.mParams.isRenouncedPermission(str)) {
            Log.v(TAG, "Treating renounced permission " + str + " as denied");
            return -1;
        }
        return checkPermission(str, Process.myPid(), Process.myUid());
    }

    private void enforce(String str, int i, boolean z, int i2, String str2) {
        String str3;
        String str4;
        if (i != 0) {
            StringBuilder sb = new StringBuilder();
            if (str2 != null) {
                str3 = str2 + ": ";
            } else {
                str3 = "";
            }
            sb.append(str3);
            if (z) {
                str4 = "Neither user " + i2 + " nor current process has ";
            } else {
                str4 = "uid " + i2 + " does not have ";
            }
            sb.append(str4);
            sb.append(str);
            sb.append(MediaMetrics.SEPARATOR);
            throw new SecurityException(sb.toString());
        }
    }

    @Override // android.content.Context
    public void enforcePermission(String str, int i, int i2, String str2) {
        enforce(str, checkPermission(str, i, i2), false, i2, str2);
    }

    @Override // android.content.Context
    public void enforceCallingPermission(String str, String str2) {
        enforce(str, checkCallingPermission(str), false, Binder.getCallingUid(), str2);
    }

    @Override // android.content.Context
    public void enforceCallingOrSelfPermission(String str, String str2) {
        enforce(str, checkCallingOrSelfPermission(str), true, Binder.getCallingUid(), str2);
    }

    @Override // android.content.Context
    public int getPermissionRequestState(String str) {
        Objects.requireNonNull(str, "Permission name can't be null");
        return ((PermissionManager) getSystemService(PermissionManager.class)).getPermissionRequestState(getOpPackageName(), str, getDeviceId());
    }

    @Override // android.content.Context
    public void grantUriPermission(String str, Uri uri, int i) {
        try {
            ActivityManager.getService().grantUriPermission(this.mMainThread.getApplicationThread(), str, ContentProvider.getUriWithoutUserId(uri), i, resolveUserId(uri));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void revokeUriPermission(Uri uri, int i) {
        try {
            ActivityManager.getService().revokeUriPermission(this.mMainThread.getApplicationThread(), null, ContentProvider.getUriWithoutUserId(uri), i, resolveUserId(uri));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void revokeUriPermission(String str, Uri uri, int i) {
        try {
            ActivityManager.getService().revokeUriPermission(this.mMainThread.getApplicationThread(), str, ContentProvider.getUriWithoutUserId(uri), i, resolveUserId(uri));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public int checkUriPermission(Uri uri, int i, int i2, int i3) {
        try {
            return ActivityManager.getService().checkUriPermission(ContentProvider.getUriWithoutUserId(uri), i, i2, i3, resolveUserId(uri), null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public int checkContentUriPermissionFull(Uri uri, int i, int i2, int i3) {
        try {
            return ActivityManager.getService().checkContentUriPermissionFull(ContentProvider.getUriWithoutUserId(uri), i, i2, i3, resolveUserId(uri));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public int[] checkUriPermissions(List<Uri> list, int i, int i2, int i3) {
        try {
            return ActivityManager.getService().checkUriPermissions(list, i, i2, i3, getUserId(), null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public int checkUriPermission(Uri uri, int i, int i2, int i3, IBinder iBinder) {
        try {
            return ActivityManager.getService().checkUriPermission(ContentProvider.getUriWithoutUserId(uri), i, i2, i3, resolveUserId(uri), iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int resolveUserId(Uri uri) {
        return ContentProvider.getUserIdFromUri(uri, getUserId());
    }

    @Override // android.content.Context
    public int checkCallingUriPermission(Uri uri, int i) {
        int callingPid = Binder.getCallingPid();
        if (callingPid != Process.myPid()) {
            return checkUriPermission(uri, callingPid, Binder.getCallingUid(), i);
        }
        return -1;
    }

    @Override // android.content.Context
    public int[] checkCallingUriPermissions(List<Uri> list, int i) {
        int callingPid = Binder.getCallingPid();
        if (callingPid != Process.myPid()) {
            return checkUriPermissions(list, callingPid, Binder.getCallingUid(), i);
        }
        int[] iArr = new int[list.size()];
        Arrays.fill(iArr, -1);
        return iArr;
    }

    @Override // android.content.Context
    public int checkCallingOrSelfUriPermission(Uri uri, int i) {
        return checkUriPermission(uri, Binder.getCallingPid(), Binder.getCallingUid(), i);
    }

    @Override // android.content.Context
    public int[] checkCallingOrSelfUriPermissions(List<Uri> list, int i) {
        return checkUriPermissions(list, Binder.getCallingPid(), Binder.getCallingUid(), i);
    }

    @Override // android.content.Context
    public int checkUriPermission(Uri uri, String str, String str2, int i, int i2, int i3) {
        if ((i3 & 1) != 0 && (str == null || checkPermission(str, i, i2) == 0)) {
            return 0;
        }
        if ((i3 & 2) != 0 && (str2 == null || checkPermission(str2, i, i2) == 0)) {
            return 0;
        }
        if (uri != null) {
            return checkUriPermission(uri, i, i2, i3);
        }
        return -1;
    }

    private String uriModeFlagToString(int i) {
        StringBuilder sb = new StringBuilder();
        if ((i & 1) != 0) {
            sb.append("read and ");
        }
        if ((i & 2) != 0) {
            sb.append("write and ");
        }
        if ((i & 64) != 0) {
            sb.append("persistable and ");
        }
        if ((i & 128) != 0) {
            sb.append("prefix and ");
        }
        if (sb.length() > 5) {
            sb.setLength(sb.length() - 5);
            return sb.toString();
        }
        throw new IllegalArgumentException("Unknown permission mode flags: " + i);
    }

    private void enforceForUri(int i, int i2, boolean z, int i3, Uri uri, String str) {
        String str2;
        String str3;
        if (i2 != 0) {
            StringBuilder sb = new StringBuilder();
            if (str != null) {
                str2 = str + ": ";
            } else {
                str2 = "";
            }
            sb.append(str2);
            if (z) {
                str3 = "Neither user " + i3 + " nor current process has ";
            } else {
                str3 = "User " + i3 + " does not have ";
            }
            sb.append(str3);
            sb.append(uriModeFlagToString(i));
            sb.append(" permission on ");
            sb.append(uri);
            sb.append(MediaMetrics.SEPARATOR);
            throw new SecurityException(sb.toString());
        }
    }

    @Override // android.content.Context
    public void enforceUriPermission(Uri uri, int i, int i2, int i3, String str) {
        enforceForUri(i3, checkUriPermission(uri, i, i2, i3), false, i2, uri, str);
    }

    @Override // android.content.Context
    public void enforceCallingUriPermission(Uri uri, int i, String str) {
        enforceForUri(i, checkCallingUriPermission(uri, i), false, Binder.getCallingUid(), uri, str);
    }

    @Override // android.content.Context
    public void enforceCallingOrSelfUriPermission(Uri uri, int i, String str) {
        enforceForUri(i, checkCallingOrSelfUriPermission(uri, i), true, Binder.getCallingUid(), uri, str);
    }

    @Override // android.content.Context
    public void enforceUriPermission(Uri uri, String str, String str2, int i, int i2, int i3, String str3) {
        enforceForUri(i3, checkUriPermission(uri, str, str2, i, i2, i3), false, i2, uri, str3);
    }

    private void warnIfCallingFromSystemProcess() {
        if (Process.myUid() == 1000) {
            Slog.w(TAG, "Calling a method in the system process without a qualified user: " + Debug.getCallers(5));
        }
    }

    private static Resources createResources(IBinder iBinder, LoadedApk loadedApk, String str, Integer num, Configuration configuration, CompatibilityInfo compatibilityInfo, List<ResourcesLoader> list) {
        return createResources(iBinder, loadedApk, str, num, configuration, compatibilityInfo, list, false, false);
    }

    private static Resources createResources(IBinder iBinder, LoadedApk loadedApk, String str, Integer num, Configuration configuration, CompatibilityInfo compatibilityInfo, List<ResourcesLoader> list, boolean z, boolean z2) {
        try {
            return ResourcesManager.getInstance().getResources(iBinder, loadedApk.getResDir(), loadedApk.getSplitPaths(str), loadedApk.getOverlayDirs(), loadedApk.getOverlayPaths(), loadedApk.getApplicationInfo().sharedLibraryFiles, num, configuration, compatibilityInfo, loadedApk.getSplitClassLoader(str), list);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // android.content.Context
    public Context createApplicationContext(ApplicationInfo applicationInfo, int i) throws PackageManager.NameNotFoundException {
        return createApplicationContextAsUser(applicationInfo, i, new UserHandle(UserHandle.getUserId(applicationInfo.uid)));
    }

    private Context createApplicationContextAsUser(ApplicationInfo applicationInfo, int i, UserHandle userHandle) throws PackageManager.NameNotFoundException {
        LoadedApk packageInfo = this.mMainThread.getPackageInfo(applicationInfo, this.mResources.getCompatibilityInfo(), i | 1073741824);
        if (packageInfo != null) {
            ContextImpl contextImpl = new ContextImpl(this, this.mMainThread, packageInfo, ContextParams.EMPTY, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), null, this.mToken, userHandle, i, null, null, this.mDeviceId, this.mIsExplicitDeviceId);
            int displayId = getDisplayId();
            contextImpl.setResources(createResources(this.mToken, packageInfo, null, this.mForceDisplayOverrideInResources ? Integer.valueOf(displayId) : null, null, getDisplayAdjustments(displayId).getCompatibilityInfo(), null));
            if (contextImpl.mResources != null) {
                return contextImpl;
            }
        }
        throw new PackageManager.NameNotFoundException("Application package " + applicationInfo.packageName + " not found");
    }

    @Override // android.content.Context
    public Context createContextForSdkInSandbox(ApplicationInfo applicationInfo, int i) throws PackageManager.NameNotFoundException {
        if (!Process.isSdkSandbox()) {
            throw new SecurityException("API can only be called from SdkSandbox process");
        }
        ContextImpl contextImpl = (ContextImpl) createApplicationContextAsUser(applicationInfo, i, applicationInfo.uid >= 0 ? new UserHandle(UserHandle.getUserId(applicationInfo.uid)) : Process.myUserHandle());
        contextImpl.mPackageInfo.makeApplicationInner(false, null);
        return contextImpl;
    }

    @Override // android.content.Context
    public Context createPackageContext(String str, int i) throws PackageManager.NameNotFoundException {
        return createPackageContextAsUser(str, i, this.mUser);
    }

    @Override // android.content.Context
    public Context createPackageContextAsUser(String str, int i, UserHandle userHandle) throws PackageManager.NameNotFoundException {
        LoadedApk packageInfo;
        if (str.equals("system") || str.equals("android")) {
            return new ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), null, this.mToken, userHandle, i, null, null, this.mDeviceId, this.mIsExplicitDeviceId);
        }
        if (str.equals(AsPackageName.SYSTEMUI) && SemDualAppManager.isDualAppId(userHandle.getIdentifier())) {
            packageInfo = this.mMainThread.getPackageInfo(str, this.mResources.getCompatibilityInfo(), i | 1073741824, 0);
        } else {
            packageInfo = this.mMainThread.getPackageInfo(str, this.mResources.getCompatibilityInfo(), i | 1073741824, userHandle.getIdentifier());
        }
        LoadedApk loadedApk = packageInfo;
        if (loadedApk != null) {
            ContextImpl contextImpl = new ContextImpl(this, this.mMainThread, loadedApk, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), null, this.mToken, userHandle, i, null, null, this.mDeviceId, this.mIsExplicitDeviceId);
            int displayId = getDisplayId();
            contextImpl.setResources(createResources(this.mToken, loadedApk, null, this.mForceDisplayOverrideInResources ? Integer.valueOf(displayId) : null, null, getDisplayAdjustments(displayId).getCompatibilityInfo(), null));
            if (contextImpl.mResources != null) {
                return contextImpl;
            }
        }
        throw new PackageManager.NameNotFoundException("Application package " + str + " not found");
    }

    @Override // android.content.Context
    public Context createContextAsUser(UserHandle userHandle, int i) {
        try {
            return createPackageContextAsUser(getPackageName(), i, userHandle);
        } catch (PackageManager.NameNotFoundException unused) {
            throw new IllegalStateException("Own package not found for user " + userHandle.getIdentifier() + ": package=" + this.getPackageName());
        }
    }

    @Override // android.content.Context
    public Context createContextForSplit(String str) throws PackageManager.NameNotFoundException {
        if (!this.mPackageInfo.getApplicationInfo().requestsIsolatedSplitLoading()) {
            return this;
        }
        ClassLoader splitClassLoader = this.mPackageInfo.getSplitClassLoader(str);
        String[] splitPaths = this.mPackageInfo.getSplitPaths(str);
        ContextImpl contextImpl = new ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), str, this.mToken, this.mUser, this.mFlags, splitClassLoader, null, this.mDeviceId, this.mIsExplicitDeviceId);
        contextImpl.setResources(ResourcesManager.getInstance().getResources(this.mToken, this.mPackageInfo.getResDir(), splitPaths, this.mPackageInfo.getOverlayDirs(), this.mPackageInfo.getOverlayPaths(), this.mPackageInfo.getApplicationInfo().sharedLibraryFiles, this.mForceDisplayOverrideInResources ? Integer.valueOf(getDisplayId()) : null, null, this.mPackageInfo.getCompatibilityInfo(), splitClassLoader, this.mResources.getLoaders()));
        return contextImpl;
    }

    @Override // android.content.Context
    public Context createConfigurationContext(Configuration configuration) {
        Configuration configuration2;
        if (configuration == null) {
            throw new IllegalArgumentException("overrideConfiguration must not be null");
        }
        if (this.mForceDisplayOverrideInResources) {
            Configuration configuration3 = new Configuration();
            configuration3.setTo(this.mDisplay.getDisplayAdjustments().getConfiguration(), 536870912, 1);
            configuration3.updateFrom(configuration);
            configuration2 = configuration3;
        } else {
            configuration2 = configuration;
        }
        ContextImpl contextImpl = new ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), this.mSplitName, this.mToken, this.mUser, this.mFlags, this.mClassLoader, null, this.mDeviceId, this.mIsExplicitDeviceId);
        contextImpl.mIsConfigurationBasedContext = true;
        int displayId = getDisplayId();
        contextImpl.setResources(createResources(this.mToken, this.mPackageInfo, this.mSplitName, this.mForceDisplayOverrideInResources ? Integer.valueOf(displayId) : null, configuration2, getDisplayAdjustments(displayId).getCompatibilityInfo(), this.mResources.getLoaders()));
        return contextImpl;
    }

    @Override // android.content.Context
    public Context createDisplayContext(Display display) {
        if (display == null) {
            throw new IllegalArgumentException("display must not be null");
        }
        ContextImpl contextImpl = new ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), this.mSplitName, this.mToken, this.mUser, this.mFlags, this.mClassLoader, null, this.mDeviceId, this.mIsExplicitDeviceId);
        int displayId = display.getDisplayId();
        Configuration configuration = new Configuration();
        configuration.setTo(display.getDisplayAdjustments().getConfiguration(), 536870912, 1);
        contextImpl.setResources(createResources(this.mToken, this.mPackageInfo, this.mSplitName, Integer.valueOf(displayId), configuration, display.getDisplayAdjustments().getCompatibilityInfo(), this.mResources.getLoaders()));
        contextImpl.setDisplay(display);
        contextImpl.mContextType = this.mContextType != 4 ? 1 : 4;
        contextImpl.mForceDisplayOverrideInResources = true;
        contextImpl.mIsConfigurationBasedContext = false;
        return contextImpl;
    }

    private void setDisplay(Display display) {
        this.mDisplay = display;
        if (display != null) {
            updateDeviceIdIfChanged(display.getDisplayId());
        }
        updateResourceOverlayConstraints();
    }

    private void updateResourceOverlayConstraints() {
        if (this.mResources != null) {
            Display display = this.mDisplay;
            this.mResources.getAssets().setOverlayConstraints(display != null ? display.getDisplayId() : 0, getDeviceId());
        }
    }

    @Override // android.content.Context
    public Context createDeviceContext(int i) {
        VirtualDeviceManager virtualDeviceManager;
        if (i != 0 && ((virtualDeviceManager = (VirtualDeviceManager) getSystemService(VirtualDeviceManager.class)) == null || !virtualDeviceManager.isValidVirtualDeviceId(i))) {
            throw new IllegalArgumentException("Not a valid ID of the default device or any virtual device: " + i);
        }
        ContextImpl contextImpl = new ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), this.mSplitName, this.mToken, this.mUser, this.mFlags, this.mClassLoader, null, i, true);
        contextImpl.updateResourceOverlayConstraints();
        return contextImpl;
    }

    @Override // android.content.Context
    public WindowContext createWindowContext(int i, Bundle bundle) {
        if (getDisplay() == null) {
            throw new UnsupportedOperationException("Please call this API with context associated with a display instance, such as Activity or context created via Context#createDisplayContext(Display), or try to invoke Context#createWindowContext(Display, int, Bundle)");
        }
        return createWindowContextInternal(getDisplay(), i, bundle);
    }

    @Override // android.content.Context
    public WindowContext createWindowContext(Display display, int i, Bundle bundle) {
        if (display == null) {
            throw new IllegalArgumentException("Display must not be null");
        }
        return createWindowContextInternal(display, i, bundle);
    }

    private WindowContext createWindowContextInternal(Display display, int i, Bundle bundle) {
        WindowTokenClient windowTokenClient = new WindowTokenClient();
        ContextImpl contextImplCreateWindowContextBase = createWindowContextBase(windowTokenClient, display.getDisplayId());
        WindowContext windowContext = new WindowContext(contextImplCreateWindowContextBase, i, bundle);
        contextImplCreateWindowContextBase.setOuterContext(windowContext);
        windowTokenClient.attachContext(windowContext);
        windowContext.attachToDisplayArea();
        return windowContext;
    }

    @Override // android.content.Context
    public Context createTokenContext(IBinder iBinder, Display display) {
        if (display == null) {
            throw new IllegalArgumentException("Display must not be null");
        }
        return createWindowContextBase(iBinder, display.getDisplayId());
    }

    ContextImpl createWindowContextBase(IBinder iBinder, int i) {
        ContextImpl contextImpl = new ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), this.mSplitName, iBinder, this.mUser, this.mFlags, this.mClassLoader, null, this.mDeviceId, this.mIsExplicitDeviceId);
        contextImpl.mForceDisplayOverrideInResources = false;
        contextImpl.mContextType = 3;
        Resources resourcesCreateWindowContextResources = createWindowContextResources(contextImpl);
        contextImpl.setResources(resourcesCreateWindowContextResources);
        contextImpl.setDisplay(ResourcesManager.getInstance().getAdjustedDisplay(i, resourcesCreateWindowContextResources));
        return contextImpl;
    }

    private static Resources createWindowContextResources(ContextImpl contextImpl) {
        CompatibilityInfo compatibilityInfo;
        LoadedApk loadedApk = contextImpl.mPackageInfo;
        ClassLoader classLoader = contextImpl.getClassLoader();
        IBinder windowContextToken = contextImpl.getWindowContextToken();
        String resDir = loadedApk.getResDir();
        String[] splitResDirs = loadedApk.getSplitResDirs();
        String[] overlayDirs = loadedApk.getOverlayDirs();
        String[] overlayPaths = loadedApk.getOverlayPaths();
        String[] strArr = loadedApk.getApplicationInfo().sharedLibraryFiles;
        int displayId = contextImpl.getDisplayId();
        if (displayId == 0) {
            compatibilityInfo = loadedApk.getCompatibilityInfo();
        } else {
            compatibilityInfo = CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
        }
        return contextImpl.mResourcesManager.createBaseTokenResources(windowContextToken, resDir, splitResDirs, overlayDirs, overlayPaths, strArr, displayId, null, compatibilityInfo, classLoader, contextImpl.mResources.getLoaders());
    }

    @Override // android.content.Context
    public Context createContext(ContextParams contextParams) {
        return new ContextImpl(this, this.mMainThread, this.mPackageInfo, contextParams, contextParams.getAttributionTag(), contextParams.getNextAttributionSource(), this.mSplitName, this.mToken, this.mUser, this.mFlags, this.mClassLoader, null, this.mDeviceId, this.mIsExplicitDeviceId);
    }

    @Override // android.content.Context
    public Context createAttributionContext(String str) {
        return createContext(new ContextParams.Builder(this.mParams).setAttributionTag(str).build());
    }

    @Override // android.content.Context
    public Context createDeviceProtectedStorageContext() {
        return new ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), this.mSplitName, this.mToken, this.mUser, (this.mFlags & (-17)) | 8, this.mClassLoader, null, this.mDeviceId, this.mIsExplicitDeviceId);
    }

    @Override // android.content.Context
    public Context createCredentialProtectedStorageContext() {
        return new ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), this.mSplitName, this.mToken, this.mUser, (this.mFlags & (-9)) | 16, this.mClassLoader, null, this.mDeviceId, this.mIsExplicitDeviceId);
    }

    @Override // android.content.Context
    public boolean isRestricted() {
        return (this.mFlags & 4) != 0;
    }

    @Override // android.content.Context
    public boolean isDeviceProtectedStorage() {
        return (this.mFlags & 8) != 0;
    }

    @Override // android.content.Context
    public boolean isCredentialProtectedStorage() {
        return (this.mFlags & 16) != 0;
    }

    @Override // android.content.Context
    public boolean canLoadUnsafeResources() {
        return getPackageName().equals(getOpPackageName()) || (this.mFlags & 2) != 0;
    }

    @Override // android.content.Context
    public Display getDisplay() {
        if (!isAssociatedWithDisplay()) {
            throw new UnsupportedOperationException("Tried to obtain display from a Context not associated with one. Only visual Contexts (such as Activity or one created with Context#createWindowContext) or ones created with Context#createDisplayContext are associated with displays. Other types of Contexts are typically related to background entities and may return an arbitrary display.");
        }
        return getDisplayNoVerify();
    }

    private boolean isAssociatedWithDisplay() {
        int i = this.mContextType;
        return i == 1 || i == 2 || i == 3 || i == 4;
    }

    @Override // android.content.Context
    public int getAssociatedDisplayId() {
        if (isAssociatedWithDisplay()) {
            return getDisplayId();
        }
        return -1;
    }

    @Override // android.content.Context
    public Display getDisplayNoVerify() {
        Display display = this.mDisplay;
        return display == null ? this.mResourcesManager.getAdjustedDisplay(0, this.mResources) : display;
    }

    @Override // android.content.Context
    public int getDisplayId() {
        Display displayNoVerify = getDisplayNoVerify();
        if (displayNoVerify != null) {
            return displayNoVerify.getDisplayId();
        }
        return 0;
    }

    @Override // android.content.Context
    public void updateDisplay(int i) {
        if (ActivityThread.isFixedAppContextDisplay()) {
            Context applicationContext = getApplicationContext();
            if (applicationContext == null) {
                applicationContext = this.mMainThread.getApplication();
            }
            if (this.mOuterContext.equals(applicationContext) && i != 0) {
                throw new IllegalArgumentException("bad display id : " + i);
            }
        }
        setDisplay(this.mResourcesManager.getAdjustedDisplay(i, this.mResources));
        if (this.mContextType == 0) {
            this.mContextType = 1;
        }
    }

    private void updateDeviceIdIfChanged(int i) {
        VirtualDeviceManager virtualDeviceManager;
        int deviceIdForDisplayId;
        if (this.mIsExplicitDeviceId) {
            return;
        }
        if (((i == 0 || i == -1) && this.mDeviceId == 0) || (virtualDeviceManager = (VirtualDeviceManager) getSystemService(VirtualDeviceManager.class)) == null || (deviceIdForDisplayId = virtualDeviceManager.getDeviceIdForDisplayId(i)) == this.mDeviceId) {
            return;
        }
        this.mDeviceId = deviceIdForDisplayId;
        this.mAttributionSource = createAttributionSourceWithDeviceId(this.mAttributionSource, deviceIdForDisplayId);
        notifyOnDeviceChangedListeners(this.mDeviceId);
    }

    @Override // android.content.Context
    public void updateDeviceId(int i) {
        if (i != 0) {
            VirtualDeviceManager virtualDeviceManager = (VirtualDeviceManager) getSystemService(VirtualDeviceManager.class);
            if (virtualDeviceManager == null) {
                throw new IllegalArgumentException("VDM is not enabled when updating to non-default device id: " + i);
            }
            if (!virtualDeviceManager.isValidVirtualDeviceId(i)) {
                throw new IllegalArgumentException("Not a valid ID of the default device or any virtual device: " + i);
            }
        }
        if (this.mIsExplicitDeviceId) {
            throw new UnsupportedOperationException("Cannot update device ID on a Context created with createDeviceContext()");
        }
        if (this.mDeviceId != i) {
            this.mDeviceId = i;
            this.mAttributionSource = createAttributionSourceWithDeviceId(this.mAttributionSource, i);
            notifyOnDeviceChangedListeners(i);
            updateResourceOverlayConstraints();
        }
    }

    @Override // android.content.Context
    public int getDeviceId() {
        return this.mDeviceId;
    }

    @Override // android.content.Context
    public void registerDeviceIdChangeListener(Executor executor, IntConsumer intConsumer) {
        Objects.requireNonNull(executor, "executor cannot be null");
        Objects.requireNonNull(intConsumer, "listener cannot be null");
        synchronized (this.mDeviceIdListenerLock) {
            if (getDeviceIdListener(intConsumer) != null) {
                throw new IllegalArgumentException("attempt to call registerDeviceIdChangeListener() on a previously registered listener");
            }
            if (this.mDeviceIdChangeListeners == null) {
                this.mDeviceIdChangeListeners = new ArrayList<>();
            }
            this.mDeviceIdChangeListeners.add(new DeviceIdChangeListenerDelegate(intConsumer, executor));
        }
    }

    @Override // android.content.Context
    public void unregisterDeviceIdChangeListener(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer, "listener cannot be null");
        synchronized (this.mDeviceIdListenerLock) {
            DeviceIdChangeListenerDelegate deviceIdListener = getDeviceIdListener(intConsumer);
            if (deviceIdListener != null) {
                this.mDeviceIdChangeListeners.remove(deviceIdListener);
            }
        }
    }

    private DeviceIdChangeListenerDelegate getDeviceIdListener(IntConsumer intConsumer) {
        if (this.mDeviceIdChangeListeners == null) {
            return null;
        }
        for (int i = 0; i < this.mDeviceIdChangeListeners.size(); i++) {
            DeviceIdChangeListenerDelegate deviceIdChangeListenerDelegate = this.mDeviceIdChangeListeners.get(i);
            if (deviceIdChangeListenerDelegate.mListener == intConsumer) {
                return deviceIdChangeListenerDelegate;
            }
        }
        return null;
    }

    private void notifyOnDeviceChangedListeners(final int i) {
        synchronized (this.mDeviceIdListenerLock) {
            ArrayList<DeviceIdChangeListenerDelegate> arrayList = this.mDeviceIdChangeListeners;
            if (arrayList != null) {
                Iterator<DeviceIdChangeListenerDelegate> it = arrayList.iterator();
                while (it.hasNext()) {
                    final DeviceIdChangeListenerDelegate next = it.next();
                    next.mExecutor.execute(new Runnable() { // from class: android.app.ContextImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            next.mListener.accept(i);
                        }
                    });
                }
            }
        }
    }

    @Override // android.content.Context
    public DisplayAdjustments getDisplayAdjustments(int i) {
        return this.mResources.getDisplayAdjustments();
    }

    @Override // android.content.Context
    public File getDataDir() {
        File dataDirFile;
        if (this.mPackageInfo != null) {
            if (isCredentialProtectedStorage()) {
                dataDirFile = this.mPackageInfo.getCredentialProtectedDataDirFile();
            } else if (isDeviceProtectedStorage()) {
                dataDirFile = this.mPackageInfo.getDeviceProtectedDataDirFile();
            } else {
                dataDirFile = this.mPackageInfo.getDataDirFile();
            }
            if (dataDirFile != null) {
                if (!dataDirFile.exists() && Process.myUid() == 1000) {
                    Log.wtf(TAG, "Data directory doesn't exist for package " + getPackageName(), new Throwable());
                }
                return dataDirFile;
            }
            throw new RuntimeException("No data directory found for package " + getPackageName());
        }
        throw new RuntimeException("No package details found for package " + getPackageName());
    }

    @Override // android.content.Context
    public File getDir(String str, int i) throws ErrnoException {
        checkMode(i);
        File fileMakeFilename = makeFilename(getDataDir(), "app_" + str);
        if (!fileMakeFilename.exists()) {
            fileMakeFilename.mkdir();
            setFilePermissionsFromMode(fileMakeFilename.getPath(), i, 505);
        }
        return fileMakeFilename;
    }

    @Override // android.content.Context
    public UserHandle getUser() {
        return this.mUser;
    }

    @Override // android.content.Context
    public int getUserId() {
        return this.mUser.getIdentifier();
    }

    @Override // android.content.Context
    public AutofillManager.AutofillClient getAutofillClient() {
        return this.mAutofillClient;
    }

    @Override // android.content.Context
    public void setAutofillClient(AutofillManager.AutofillClient autofillClient) {
        this.mAutofillClient = autofillClient;
    }

    @Override // android.content.Context
    public AutofillOptions getAutofillOptions() {
        return this.mAutofillOptions;
    }

    @Override // android.content.Context
    public void setAutofillOptions(AutofillOptions autofillOptions) {
        this.mAutofillOptions = autofillOptions;
    }

    @Override // android.content.Context
    public ContentCaptureOptions getContentCaptureOptions() {
        return this.mContentCaptureOptions;
    }

    @Override // android.content.Context
    public void setContentCaptureOptions(ContentCaptureOptions contentCaptureOptions) {
        this.mContentCaptureOptions = contentCaptureOptions;
    }

    protected void finalize() throws Throwable {
        if ((this.mToken instanceof WindowTokenClient) && this.mOwnsToken) {
            WindowTokenClientController.getInstance().detachIfNeeded((WindowTokenClient) this.mToken);
        }
        super.finalize();
    }

    static ContextImpl createSystemContext(ActivityThread activityThread) {
        LoadedApk loadedApk = new LoadedApk(activityThread);
        ContextImpl contextImpl = new ContextImpl(null, activityThread, loadedApk, ContextParams.EMPTY, null, null, null, null, null, 0, null, null, 0, false);
        contextImpl.setResources(loadedApk.getResources());
        contextImpl.mResources.updateConfiguration(contextImpl.mResourcesManager.getConfiguration(), contextImpl.mResourcesManager.getDisplayMetrics());
        contextImpl.mContextType = 4;
        return contextImpl;
    }

    static Context createSystemUiContext(ContextImpl contextImpl, int i) {
        Context systemUiContext;
        WindowTokenClient windowTokenClient = new WindowTokenClient();
        ContextImpl contextImplCreateWindowContextBase = contextImpl.createWindowContextBase(windowTokenClient, i);
        if (com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.trackSystemUiContextBeforeWms()) {
            systemUiContext = new SystemUiContext(contextImplCreateWindowContextBase);
            contextImplCreateWindowContextBase.setOuterContext(systemUiContext);
        } else {
            systemUiContext = contextImplCreateWindowContextBase;
        }
        windowTokenClient.attachContext(systemUiContext);
        WindowTokenClientController.getInstance().attachToDisplayContent(windowTokenClient, i);
        contextImplCreateWindowContextBase.mContextType = 4;
        contextImplCreateWindowContextBase.mOwnsToken = true;
        return systemUiContext;
    }

    static ContextImpl createAppContext(ActivityThread activityThread, LoadedApk loadedApk) {
        return createAppContext(activityThread, loadedApk, null);
    }

    static ContextImpl createAppContext(ActivityThread activityThread, LoadedApk loadedApk, String str) {
        if (loadedApk == null) {
            throw new IllegalArgumentException("packageInfo");
        }
        ContextImpl contextImpl = new ContextImpl(null, activityThread, loadedApk, ContextParams.EMPTY, null, null, null, null, null, 0, null, str, 0, false);
        contextImpl.setResources(loadedApk.getResources());
        contextImpl.mContextType = isSystemOrSystemUI(contextImpl) ? 4 : 0;
        return contextImpl;
    }

    static ContextImpl createActivityContext(ActivityThread activityThread, LoadedApk loadedApk, ActivityInfo activityInfo, IBinder iBinder, int i, Configuration configuration) {
        CompatibilityInfo compatibilityInfo;
        if (loadedApk == null) {
            throw new IllegalArgumentException("packageInfo");
        }
        String[] splitResDirs = loadedApk.getSplitResDirs();
        ClassLoader classLoader = loadedApk.getClassLoader();
        if (loadedApk.getApplicationInfo().requestsIsolatedSplitLoading()) {
            Trace.traceBegin(8192L, "SplitDependencies");
            try {
                try {
                    classLoader = loadedApk.getSplitClassLoader(activityInfo.splitName);
                    splitResDirs = loadedApk.getSplitPaths(activityInfo.splitName);
                } catch (PackageManager.NameNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } finally {
                Trace.traceEnd(8192L);
            }
        }
        String[] strArr = splitResDirs;
        ClassLoader classLoader2 = classLoader;
        ContextImpl contextImpl = new ContextImpl(null, activityThread, loadedApk, ContextParams.EMPTY, (activityInfo.attributionTags == null || activityInfo.attributionTags.length <= 0) ? null : activityInfo.attributionTags[0], null, activityInfo.splitName, iBinder, null, 0, classLoader2, null, 0, false);
        contextImpl.mContextType = 2;
        contextImpl.mIsConfigurationBasedContext = true;
        int i2 = i != -1 ? i : 0;
        if (i2 == 0) {
            compatibilityInfo = loadedApk.getCompatibilityInfo();
        } else {
            compatibilityInfo = CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
        }
        ResourcesManager resourcesManager = ResourcesManager.getInstance();
        contextImpl.setResources(resourcesManager.createBaseTokenResources(iBinder, loadedApk.getResDir(), strArr, loadedApk.getOverlayDirs(), loadedApk.getOverlayPaths(), loadedApk.getApplicationInfo().sharedLibraryFiles, i2, configuration, compatibilityInfo, classLoader2, loadedApk.getApplication() != null ? loadedApk.getApplication().getResources().getLoaders() : null));
        contextImpl.setDisplay(resourcesManager.getAdjustedDisplay(i2, contextImpl.getResources()));
        return contextImpl;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0095  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private ContextImpl(ContextImpl contextImpl, ActivityThread activityThread, LoadedApk loadedApk, ContextParams contextParams, String str, AttributionSource attributionSource, String str2, IBinder iBinder, UserHandle userHandle, int i, ClassLoader classLoader, String str3, int i2, boolean z) {
        int i3;
        String strCurrentPackageName;
        this.mDeviceId = 0;
        this.mIsExplicitDeviceId = false;
        this.mSplitName = null;
        this.mContentCaptureOptions = null;
        Object[] objArrCreateServiceCache = SystemServiceRegistry.createServiceCache();
        this.mServiceCache = objArrCreateServiceCache;
        this.mServiceInitializationStateArray = new int[objArrCreateServiceCache.length];
        this.mDeviceIdListenerLock = new Object();
        this.mOuterContext = this;
        if ((i & 24) == 0) {
            File dataDirFile = loadedApk.getDataDirFile();
            if (Objects.equals(dataDirFile, loadedApk.getCredentialProtectedDataDirFile())) {
                i3 = i | 16;
            } else {
                i3 = Objects.equals(dataDirFile, loadedApk.getDeviceProtectedDataDirFile()) ? i | 8 : i;
            }
        }
        this.mMainThread = activityThread;
        this.mToken = iBinder;
        this.mFlags = i3;
        this.mUser = userHandle == null ? Process.myUserHandle() : userHandle;
        this.mPackageInfo = loadedApk;
        this.mSplitName = str2;
        this.mClassLoader = classLoader;
        this.mResourcesManager = ResourcesManager.getInstance();
        this.mDeviceId = i2;
        this.mIsExplicitDeviceId = z;
        if (contextImpl != null) {
            this.mBasePackageName = contextImpl.mBasePackageName;
            strCurrentPackageName = contextImpl.mOpPackageName;
            setResources(contextImpl.mResources);
            this.mDisplay = contextImpl.mDisplay;
            if (!z) {
                this.mIsExplicitDeviceId = contextImpl.mIsExplicitDeviceId;
                this.mDeviceId = contextImpl.mDeviceId;
            }
            this.mForceDisplayOverrideInResources = contextImpl.mForceDisplayOverrideInResources;
            this.mIsConfigurationBasedContext = contextImpl.mIsConfigurationBasedContext;
            this.mContextType = contextImpl.mContextType;
            this.mContentCaptureOptions = contextImpl.mContentCaptureOptions;
            this.mAutofillOptions = contextImpl.mAutofillOptions;
        } else {
            String str4 = loadedApk.mPackageName;
            this.mBasePackageName = str4;
            ApplicationInfo applicationInfo = loadedApk.getApplicationInfo();
            strCurrentPackageName = (applicationInfo.uid != 1000 || applicationInfo.uid == Process.myUid()) ? str4 : ActivityThread.currentPackageName();
        }
        this.mOpPackageName = str3 != null ? str3 : strCurrentPackageName;
        this.mParams = (ContextParams) Objects.requireNonNull(contextParams);
        this.mAttributionSource = createAttributionSource(str, attributionSource, contextParams.getRenouncedPermissions(), contextParams.shouldRegisterAttributionSource(), this.mDeviceId);
        this.mContentResolver = new ApplicationContentResolver(this, activityThread);
    }

    private AttributionSource createAttributionSource(String str, AttributionSource attributionSource, Set<String> set, boolean z, int i) {
        return registerAttributionSourceIfNeeded(new AttributionSource(Process.myUid(), Process.myPid(), this.mOpPackageName, str, set != null ? (String[]) set.toArray(new String[0]) : null, i, attributionSource), z);
    }

    private AttributionSource createAttributionSourceWithDeviceId(AttributionSource attributionSource, int i) {
        return registerAttributionSourceIfNeeded(attributionSource.withDeviceId(i), com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.shouldRegisterAttributionSource() ? this.mParams.shouldRegisterAttributionSource() : false);
    }

    private AttributionSource registerAttributionSourceIfNeeded(AttributionSource attributionSource, boolean z) {
        return (z || attributionSource.getNext() != null) ? ((PermissionManager) getSystemService(PermissionManager.class)).registerAttributionSource(attributionSource) : attributionSource;
    }

    void setResources(Resources resources) {
        if (resources instanceof CompatResources) {
            ((CompatResources) resources).setContext(this);
        }
        this.mResources = resources;
        if (resources != null && android.content.res.Flags.defaultLocale() && resources.getConfiguration().getLocales().size() > 1) {
            this.mResourcesManager.setLocaleConfig(LocaleConfig.fromContextIgnoringOverride(this));
        }
        updateResourceOverlayConstraints();
    }

    void installSystemApplicationInfo(ApplicationInfo applicationInfo, ClassLoader classLoader) {
        this.mPackageInfo.installSystemApplicationInfo(applicationInfo, classLoader);
    }

    final void scheduleFinalCleanup(String str, String str2) {
        this.mMainThread.scheduleContextCleanup(this, str, str2);
    }

    final void performFinalCleanup(String str, String str2) {
        this.mPackageInfo.removeContextRegistrations(getOuterContext(), str, str2);
        if (this.mContextType == 4 && (this.mToken instanceof WindowTokenClient)) {
            this.mMainThread.onSystemUiContextCleanup(this);
        }
    }

    final Context getReceiverRestrictedContext() {
        Context context = this.mReceiverRestrictedContext;
        if (context != null) {
            return context;
        }
        ReceiverRestrictedContext receiverRestrictedContext = new ReceiverRestrictedContext(getOuterContext());
        this.mReceiverRestrictedContext = receiverRestrictedContext;
        return receiverRestrictedContext;
    }

    final void setOuterContext(Context context) {
        this.mOuterContext = context;
    }

    final Context getOuterContext() {
        return this.mOuterContext;
    }

    @Override // android.content.Context
    public IBinder getActivityToken() {
        if (this.mContextType == 2) {
            return this.mToken;
        }
        return null;
    }

    @Override // android.content.Context
    public IBinder getWindowContextToken() {
        int i = this.mContextType;
        if (i == 3 || i == 4) {
            return this.mToken;
        }
        return null;
    }

    private void checkMode(int i) {
        if (getApplicationInfo().targetSdkVersion >= 24) {
            if ((i & 1) != 0) {
                throw new SecurityException("MODE_WORLD_READABLE no longer supported");
            }
            if ((i & 2) != 0) {
                throw new SecurityException("MODE_WORLD_WRITEABLE no longer supported");
            }
        }
    }

    static void setFilePermissionsFromMode(String str, int i, int i2) throws ErrnoException {
        int i3 = i2 | 432;
        if ((i & 1) != 0) {
            i3 = i2 | WearSettingsEnums.DIVIDER_PREFERENCE;
        }
        if ((i & 2) != 0) {
            i3 |= 2;
        }
        FileUtils.setPermissions(str, i3, -1, -1);
    }

    private File makeFilename(File file, String str) {
        if (str.indexOf(File.separatorChar) < 0) {
            File file2 = new File(file, str);
            BlockGuard.getVmPolicy().onPathAccess(file2.getPath());
            return file2;
        }
        throw new IllegalArgumentException("File " + str + " contains a path separator");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001f A[Catch: Exception -> 0x0029, TryCatch #0 {Exception -> 0x0029, blocks: (B:8:0x0019, B:10:0x001f, B:12:0x0025), top: B:22:0x0019 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private File[] ensureExternalDirsExistOrFilter(File[] fileArr, boolean z) {
        StorageManager storageManager = (StorageManager) getSystemService(StorageManager.class);
        File[] fileArr2 = new File[fileArr.length];
        for (int i = 0; i < fileArr.length; i++) {
            File file = fileArr[i];
            if (!file.exists()) {
                if (z) {
                    try {
                        if (!file.mkdirs()) {
                            if (!file.exists()) {
                                storageManager.mkdirs(file);
                            }
                        }
                    } catch (Exception e) {
                        Log.w(TAG, "Failed to ensure " + file + ": " + e);
                        file = null;
                    }
                }
            }
            if (file != null && !file.canWrite()) {
                storageManager.fixupAppDir(file);
            }
            fileArr2[i] = file;
        }
        return fileArr2;
    }

    @Override // android.content.Context
    public void destroy() {
        scheduleFinalCleanup(getClass().getName(), getOuterContext().getClass().getSimpleName());
    }

    @Override // android.content.Context
    public void closeSystemDialogs() {
        sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS).addFlags(268435456), (String) null, BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).setDeferralPolicy(2).toBundle());
    }

    @Override // android.content.Context
    public boolean isExternalDesktopContext() {
        Display displayNoVerify;
        if (!isAssociatedWithDisplay() || (displayNoVerify = getDisplayNoVerify()) == null) {
            return false;
        }
        DisplayInfo displayInfo = new DisplayInfo();
        displayNoVerify.getDisplayInfo(displayInfo);
        return DisplayManager.isExternalDesktopDisplay(displayInfo);
    }

    private static final class ApplicationContentResolver extends ContentResolver {
        private final ActivityThread mMainThread;

        public ApplicationContentResolver(Context context, ActivityThread activityThread) {
            super(context);
            this.mMainThread = (ActivityThread) Objects.requireNonNull(activityThread);
        }

        @Override // android.content.ContentResolver
        protected IContentProvider acquireProvider(Context context, String str) {
            return this.mMainThread.acquireProvider(context, ContentProvider.getAuthorityWithoutUserId(str), resolveUserIdFromAuthority(str), true);
        }

        @Override // android.content.ContentResolver
        protected IContentProvider acquireExistingProvider(Context context, String str) {
            return this.mMainThread.acquireExistingProvider(context, ContentProvider.getAuthorityWithoutUserId(str), resolveUserIdFromAuthority(str), true);
        }

        @Override // android.content.ContentResolver
        public boolean releaseProvider(IContentProvider iContentProvider) {
            return this.mMainThread.releaseProvider(iContentProvider, true);
        }

        @Override // android.content.ContentResolver
        protected IContentProvider acquireUnstableProvider(Context context, String str) {
            return this.mMainThread.acquireProvider(context, ContentProvider.getAuthorityWithoutUserId(str), resolveUserIdFromAuthority(str), false);
        }

        @Override // android.content.ContentResolver
        public boolean releaseUnstableProvider(IContentProvider iContentProvider) {
            return this.mMainThread.releaseProvider(iContentProvider, false);
        }

        @Override // android.content.ContentResolver
        public void unstableProviderDied(IContentProvider iContentProvider) {
            this.mMainThread.handleUnstableProviderDied(iContentProvider.asBinder(), true);
        }

        @Override // android.content.ContentResolver
        public void appNotRespondingViaProvider(IContentProvider iContentProvider) {
            this.mMainThread.appNotRespondingViaProvider(iContentProvider.asBinder());
        }

        protected int resolveUserIdFromAuthority(String str) {
            return ContentProvider.getUserIdFromAuthority(str, getUserId());
        }
    }
}
