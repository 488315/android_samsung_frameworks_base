package android.app;

import android.Manifest;
import android.companion.virtual.VirtualDevice;
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
import android.permission.PermissionControllerManager;
import android.permission.PermissionManager;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.kioskmode.KioskMode;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayAdjustments;
import android.view.autofill.AutofillManager;
import android.window.WindowContext;
import android.window.WindowTokenClient;
import android.window.WindowTokenClientController;

import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.Preconditions;

import dalvik.system.BlockGuard;

import libcore.io.Memory;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.common.AsPackageName;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.sepunion.SemUnionManager;
import com.samsung.android.sepunion.UnionUtils;

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
    private Display mDisplay;
    private File mFilesDir;
    private final int mFlags;
    private boolean mForceDisplayOverrideInResources;
    private boolean mIsConfigurationBasedContext;
    private boolean mIsExplicitDeviceId;
    final ActivityThread mMainThread;
    private File mNoBackupFilesDir;
    private final String mOpPackageName;
    final LoadedApk mPackageInfo;
    private PackageManager mPackageManager;
    private final ContextParams mParams;
    private File mPreferencesDir;
    private Resources mResources;
    private final ResourcesManager mResourcesManager;
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
    final Object[] mServiceCache = SystemServiceRegistry.createServiceCache();
    final int[] mServiceInitializationStateArray = new int[this.mServiceCache.length];
    private final Object mDeviceIdListenerLock = new Object();
    private Context mOuterContext = this;

    @Retention(RetentionPolicy.SOURCE)
    private @interface ContextType {}

    @Retention(RetentionPolicy.SOURCE)
    @interface ServiceInitializationState {}

    /* JADX INFO: Access modifiers changed from: private */
    static class DeviceIdChangeListenerDelegate {
        final Executor mExecutor;
        final IntConsumer mListener;

        DeviceIdChangeListenerDelegate(IntConsumer listener, Executor executor) {
            this.mListener = listener;
            this.mExecutor = executor;
        }
    }

    static ContextImpl getImpl(Context context) {
        Context nextContext;
        while ((context instanceof ContextWrapper)
                && (nextContext = ((ContextWrapper) context).getBaseContext()) != null) {
            context = nextContext;
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
        if (this.mPackageManager != null) {
            return this.mPackageManager;
        }
        IPackageManager pm = ActivityThread.getPackageManager();
        if (pm != null) {
            ApplicationPackageManager applicationPackageManager =
                    new ApplicationPackageManager(this, pm);
            this.mPackageManager = applicationPackageManager;
            return applicationPackageManager;
        }
        return null;
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
        return this.mPackageInfo != null
                ? this.mPackageInfo.getApplication()
                : this.mMainThread.getApplication();
    }

    @Override // android.content.Context
    public void setTheme(int resId) {
        synchronized (this.mThemeLock) {
            if (this.mThemeResource != resId) {
                this.mThemeResource = resId;
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
            if (this.mTheme != null) {
                return this.mTheme;
            }
            this.mThemeResource =
                    Resources.selectDefaultTheme(
                            this.mThemeResource,
                            getOuterContext().getApplicationInfo().targetSdkVersion);
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
        return this.mClassLoader != null
                ? this.mClassLoader
                : this.mPackageInfo != null
                        ? this.mPackageInfo.getClassLoader()
                        : ClassLoader.getSystemClassLoader();
    }

    @Override // android.content.Context
    public String getPackageName() {
        if (this.mPackageInfo != null) {
            return this.mPackageInfo.getPackageName();
        }
        return "android";
    }

    @Override // android.content.Context
    public String getBasePackageName() {
        return this.mBasePackageName != null ? this.mBasePackageName : getPackageName();
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
        if (this.mPackageInfo != null) {
            return this.mPackageInfo.getApplicationInfo();
        }
        throw new RuntimeException("Not supported in system context");
    }

    @Override // android.content.Context
    public String getPackageResourcePath() {
        if (this.mPackageInfo != null) {
            return this.mPackageInfo.getResDir();
        }
        throw new RuntimeException("Not supported in system context");
    }

    @Override // android.content.Context
    public String getPackageCodePath() {
        if (this.mPackageInfo != null) {
            return this.mPackageInfo.getAppDir();
        }
        throw new RuntimeException("Not supported in system context");
    }

    @Override // android.content.Context
    public SharedPreferences getSharedPreferences(String name, int mode) {
        File file;
        if (this.mPackageInfo.getApplicationInfo().targetSdkVersion < 19 && name == null) {
            name = "null";
        }
        synchronized (ContextImpl.class) {
            if (this.mSharedPrefsPaths == null) {
                this.mSharedPrefsPaths = new ArrayMap<>();
            }
            file = this.mSharedPrefsPaths.get(name);
            if (file == null) {
                file = getSharedPreferencesPath(name);
                this.mSharedPrefsPaths.put(name, file);
            }
        }
        return getSharedPreferences(file, mode);
    }

    @Override // android.content.Context
    public SharedPreferences getSharedPreferences(File file, int mode) {
        synchronized (ContextImpl.class) {
            ArrayMap<File, SharedPreferencesImpl> cache = getSharedPreferencesCacheLocked();
            SharedPreferencesImpl sp = cache.get(file);
            if (sp == null) {
                checkMode(mode);
                if (getApplicationInfo().targetSdkVersion >= 26) {
                    boolean keepGoing =
                            "android".equals(getPackageName())
                                    && file != null
                                    && file.getName().equals("multiwindow.property.xml");
                    if (!keepGoing && isCredentialProtectedStorage()) {
                        UserManager um = (UserManager) getSystemService(UserManager.class);
                        if (um == null) {
                            throw new IllegalStateException(
                                    "SharedPreferences cannot be accessed if UserManager is not"
                                            + " available. (e.g. from inside an isolated process)");
                        }
                        if (!um.isUserUnlockingOrUnlocked(UserHandle.myUserId())) {
                            throw new IllegalStateException(
                                    "SharedPreferences in credential encrypted storage are not"
                                            + " available until after user (id "
                                            + UserHandle.myUserId()
                                            + ") is unlocked");
                        }
                    }
                }
                SharedPreferencesImpl sp2 = new SharedPreferencesImpl(file, mode);
                cache.put(file, sp2);
                return sp2;
            }
            if ((mode & 4) != 0 || getApplicationInfo().targetSdkVersion < 11) {
                sp.startReloadIfChangedUnexpectedly();
            }
            return sp;
        }
    }

    private ArrayMap<File, SharedPreferencesImpl> getSharedPreferencesCacheLocked() {
        if (sSharedPrefsCache == null) {
            sSharedPrefsCache = new ArrayMap<>();
        }
        String packageName = getPackageName();
        ArrayMap<File, SharedPreferencesImpl> packagePrefs = sSharedPrefsCache.get(packageName);
        if (packagePrefs == null) {
            ArrayMap<File, SharedPreferencesImpl> packagePrefs2 = new ArrayMap<>();
            sSharedPrefsCache.put(packageName, packagePrefs2);
            return packagePrefs2;
        }
        return packagePrefs;
    }

    @Override // android.content.Context
    public void reloadSharedPreferences() {
        ArrayList<SharedPreferencesImpl> spImpls = new ArrayList<>();
        synchronized (ContextImpl.class) {
            ArrayMap<File, SharedPreferencesImpl> cache = getSharedPreferencesCacheLocked();
            for (int i = 0; i < cache.size(); i++) {
                SharedPreferencesImpl sp = cache.valueAt(i);
                if (sp != null) {
                    spImpls.add(sp);
                }
            }
        }
        for (int i2 = 0; i2 < spImpls.size(); i2++) {
            spImpls.get(i2).startReloadIfChangedUnexpectedly();
        }
    }

    private static int moveFiles(File sourceDir, File targetDir, final String prefix) {
        File[] sourceFiles =
                FileUtils.listFilesOrEmpty(
                        sourceDir,
                        new FilenameFilter() { // from class: android.app.ContextImpl.1
                            @Override // java.io.FilenameFilter
                            public boolean accept(File dir, String name) {
                                return name.startsWith(prefix);
                            }
                        });
        int res = 0;
        for (File sourceFile : sourceFiles) {
            File targetFile = new File(targetDir, sourceFile.getName());
            Log.d(TAG, "Migrating " + sourceFile + " to " + targetFile);
            try {
                FileUtils.copyFileOrThrow(sourceFile, targetFile);
                FileUtils.copyPermissions(sourceFile, targetFile);
            } catch (IOException e) {
                Log.w(TAG, "Failed to migrate " + sourceFile + ": " + e);
                res = -1;
            }
            if (!sourceFile.delete()) {
                throw new IOException("Failed to clean up " + sourceFile);
            }
            if (res != -1) {
                res++;
            }
        }
        return res;
    }

    @Override // android.content.Context
    public boolean moveSharedPreferencesFrom(Context sourceContext, String name) {
        boolean z;
        synchronized (ContextImpl.class) {
            File source = sourceContext.getSharedPreferencesPath(name);
            File target = getSharedPreferencesPath(name);
            int res = moveFiles(source.getParentFile(), target.getParentFile(), source.getName());
            if (res > 0) {
                ArrayMap<File, SharedPreferencesImpl> cache = getSharedPreferencesCacheLocked();
                cache.remove(source);
                cache.remove(target);
            }
            z = res != -1;
        }
        return z;
    }

    @Override // android.content.Context
    public boolean deleteSharedPreferences(String name) {
        boolean z;
        synchronized (ContextImpl.class) {
            File prefs = getSharedPreferencesPath(name);
            File prefsBackup = SharedPreferencesImpl.makeBackupFile(prefs);
            ArrayMap<File, SharedPreferencesImpl> cache = getSharedPreferencesCacheLocked();
            cache.remove(prefs);
            prefs.delete();
            prefsBackup.delete();
            z = (prefs.exists() || prefsBackup.exists()) ? false : true;
        }
        return z;
    }

    private File getPreferencesDir() {
        File ensurePrivateDirExists;
        synchronized (this.mPreferencesDirLock) {
            if (this.mPreferencesDir == null) {
                this.mPreferencesDir = new File(getDataDir(), "shared_prefs");
            }
            ensurePrivateDirExists = ensurePrivateDirExists(this.mPreferencesDir);
        }
        return ensurePrivateDirExists;
    }

    @Override // android.content.Context
    public FileInputStream openFileInput(String name) throws FileNotFoundException {
        File f = makeFilename(getFilesDir(), name);
        return new FileInputStream(f);
    }

    @Override // android.content.Context
    public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
        checkMode(mode);
        boolean append = (32768 & mode) != 0;
        File f = makeFilename(getFilesDir(), name);
        try {
            FileOutputStream fos = new FileOutputStream(f, append);
            setFilePermissionsFromMode(f.getPath(), mode, 0);
            return fos;
        } catch (FileNotFoundException e) {
            File parent = f.getParentFile();
            parent.mkdir();
            FileUtils.setPermissions(parent.getPath(), 505, -1, -1);
            FileOutputStream fos2 = new FileOutputStream(f, append);
            setFilePermissionsFromMode(f.getPath(), mode, 0);
            return fos2;
        }
    }

    @Override // android.content.Context
    public boolean deleteFile(String name) {
        File f = makeFilename(getFilesDir(), name);
        return f.delete();
    }

    private static File ensurePrivateDirExists(File file) {
        return ensurePrivateDirExists(file, 505, -1, null);
    }

    private static File ensurePrivateCacheDirExists(File file, String xattr) {
        int gid = UserHandle.getCacheAppGid(Process.myUid());
        return ensurePrivateDirExists(
                file, MetricsProto.MetricsEvent.FIELD_PROCESS_RECORD_PROCESS_NAME, gid, xattr);
    }

    private static File ensurePrivateDirExists(File file, int mode, int gid, String xattr) {
        if (!file.exists()) {
            String path = file.getAbsolutePath();
            try {
                Os.mkdir(path, mode);
                Os.chmod(path, mode);
                if (gid != -1) {
                    Os.chown(path, -1, gid);
                }
            } catch (ErrnoException e) {
                if (e.errno != OsConstants.EEXIST) {
                    Log.w(TAG, "Failed to ensure " + file + ": " + e.getMessage());
                }
            }
            if (xattr != null) {
                try {
                    StructStat stat = Os.stat(file.getAbsolutePath());
                    byte[] value = new byte[8];
                    Memory.pokeLong(value, 0, stat.st_ino, ByteOrder.nativeOrder());
                    Os.setxattr(file.getParentFile().getAbsolutePath(), xattr, value, 0);
                } catch (ErrnoException e2) {
                    Log.w(TAG, "Failed to update " + xattr + ": " + e2.getMessage());
                }
            }
        }
        return file;
    }

    @Override // android.content.Context
    public File getFilesDir() {
        File ensurePrivateDirExists;
        synchronized (this.mFilesDirLock) {
            if (this.mFilesDir == null) {
                this.mFilesDir = new File(getDataDir(), "files");
            }
            ensurePrivateDirExists = ensurePrivateDirExists(this.mFilesDir);
        }
        return ensurePrivateDirExists;
    }

    @Override // android.content.Context
    public File getCrateDir(String crateId) {
        Preconditions.checkArgument(FileUtils.isValidExtFilename(crateId), "invalidated crateId");
        Path cratesRootPath = getDataDir().toPath().resolve("crates");
        Path absoluteNormalizedCratePath =
                cratesRootPath.resolve(crateId).toAbsolutePath().normalize();
        synchronized (this.mCratesDirLock) {
            if (this.mCratesDir == null) {
                this.mCratesDir = cratesRootPath.toFile();
            }
            ensurePrivateDirExists(this.mCratesDir);
        }
        File cratedDir = absoluteNormalizedCratePath.toFile();
        return ensurePrivateDirExists(cratedDir);
    }

    @Override // android.content.Context
    public File getNoBackupFilesDir() {
        File ensurePrivateDirExists;
        synchronized (this.mNoBackupFilesDirLock) {
            if (this.mNoBackupFilesDir == null) {
                this.mNoBackupFilesDir = new File(getDataDir(), "no_backup");
            }
            ensurePrivateDirExists = ensurePrivateDirExists(this.mNoBackupFilesDir);
        }
        return ensurePrivateDirExists;
    }

    @Override // android.content.Context
    public File getExternalFilesDir(String type) {
        File[] dirs = getExternalFilesDirs(type);
        if (dirs == null || dirs.length <= 0) {
            return null;
        }
        return dirs[0];
    }

    @Override // android.content.Context
    public File[] getExternalFilesDirs(String type) {
        File[] ensureExternalDirsExistOrFilter;
        synchronized (this.mMiscDirsLock) {
            File[] dirs = Environment.buildExternalStorageAppFilesDirs(getPackageName());
            if (type != null) {
                dirs = Environment.buildPaths(dirs, type);
            }
            ensureExternalDirsExistOrFilter = ensureExternalDirsExistOrFilter(dirs, true);
        }
        return ensureExternalDirsExistOrFilter;
    }

    @Override // android.content.Context
    public File getObbDir() {
        File[] dirs = getObbDirs();
        if (dirs == null || dirs.length <= 0) {
            return null;
        }
        return dirs[0];
    }

    @Override // android.content.Context
    public File[] getObbDirs() {
        File[] ensureExternalDirsExistOrFilter;
        synchronized (this.mMiscDirsLock) {
            File[] dirs = Environment.buildExternalStorageAppObbDirs(getPackageName());
            ensureExternalDirsExistOrFilter = ensureExternalDirsExistOrFilter(dirs, true);
        }
        return ensureExternalDirsExistOrFilter;
    }

    @Override // android.content.Context
    public File getCacheDir() {
        File ensurePrivateCacheDirExists;
        synchronized (this.mCacheDirLock) {
            if (this.mCacheDir == null) {
                this.mCacheDir = new File(getDataDir(), "cache");
            }
            ensurePrivateCacheDirExists =
                    ensurePrivateCacheDirExists(this.mCacheDir, XATTR_INODE_CACHE);
        }
        return ensurePrivateCacheDirExists;
    }

    @Override // android.content.Context
    public File getCodeCacheDir() {
        File ensurePrivateCacheDirExists;
        synchronized (this.mCodeCacheDirLock) {
            if (this.mCodeCacheDir == null) {
                this.mCodeCacheDir = getCodeCacheDirBeforeBind(getDataDir());
            }
            ensurePrivateCacheDirExists =
                    ensurePrivateCacheDirExists(this.mCodeCacheDir, XATTR_INODE_CODE_CACHE);
        }
        return ensurePrivateCacheDirExists;
    }

    static File getCodeCacheDirBeforeBind(File dataDir) {
        return new File(dataDir, "code_cache");
    }

    @Override // android.content.Context
    public File getExternalCacheDir() {
        File[] dirs = getExternalCacheDirs();
        if (dirs == null || dirs.length <= 0) {
            return null;
        }
        return dirs[0];
    }

    @Override // android.content.Context
    public File[] getExternalCacheDirs() {
        File[] ensureExternalDirsExistOrFilter;
        synchronized (this.mMiscDirsLock) {
            File[] dirs = Environment.buildExternalStorageAppCacheDirs(getPackageName());
            ensureExternalDirsExistOrFilter = ensureExternalDirsExistOrFilter(dirs, false);
        }
        return ensureExternalDirsExistOrFilter;
    }

    @Override // android.content.Context
    public File[] getExternalMediaDirs() {
        File[] ensureExternalDirsExistOrFilter;
        synchronized (this.mMiscDirsLock) {
            File[] dirs = Environment.buildExternalStorageAppMediaDirs(getPackageName());
            ensureExternalDirsExistOrFilter = ensureExternalDirsExistOrFilter(dirs, true);
        }
        return ensureExternalDirsExistOrFilter;
    }

    @Override // android.content.Context
    public File getPreloadsFileCache() {
        return Environment.getDataPreloadsFileCacheDirectory(getPackageName());
    }

    @Override // android.content.Context
    public File getFileStreamPath(String name) {
        return makeFilename(getFilesDir(), name);
    }

    @Override // android.content.Context
    public File getSharedPreferencesPath(String name) {
        File makeFilename;
        if ("android".equals(getPackageName())
                && MultiWindowCoreState.MW_SHARED_PREF_NAME.equals(name)) {
            synchronized (this.mSync) {
                if (this.mPreferencesDir == null) {
                    String dataDir = this.mPackageInfo.getApplicationInfo().dataDir;
                    this.mPreferencesDir = new File(dataDir, "shared_prefs");
                }
                makeFilename =
                        makeFilename(ensurePrivateDirExists(this.mPreferencesDir), name + ".xml");
            }
            return makeFilename;
        }
        return makeFilename(getPreferencesDir(), name + ".xml");
    }

    @Override // android.content.Context
    public String[] fileList() {
        return FileUtils.listOrEmpty(getFilesDir());
    }

    @Override // android.content.Context
    public SQLiteDatabase openOrCreateDatabase(
            String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return openOrCreateDatabase(name, mode, factory, null);
    }

    @Override // android.content.Context
    public SQLiteDatabase openOrCreateDatabase(
            String name,
            int mode,
            SQLiteDatabase.CursorFactory factory,
            DatabaseErrorHandler errorHandler) {
        checkMode(mode);
        File f = getDatabasePath(name);
        int flags = (mode & 8) != 0 ? 268435456 | 536870912 : 268435456;
        if ((mode & 16) != 0) {
            flags |= 16;
        }
        SQLiteDatabase db = SQLiteDatabase.openDatabase(f.getPath(), factory, flags, errorHandler);
        setFilePermissionsFromMode(f.getPath(), mode, 0);
        return db;
    }

    @Override // android.content.Context
    public boolean moveDatabaseFrom(Context sourceContext, String name) {
        boolean z;
        synchronized (ContextImpl.class) {
            File source = sourceContext.getDatabasePath(name);
            File target = getDatabasePath(name);
            z = moveFiles(source.getParentFile(), target.getParentFile(), source.getName()) != -1;
        }
        return z;
    }

    @Override // android.content.Context
    public boolean deleteDatabase(String name) {
        try {
            File f = getDatabasePath(name);
            return SQLiteDatabase.deleteDatabase(f);
        } catch (Exception e) {
            return false;
        }
    }

    @Override // android.content.Context
    public File getDatabasePath(String name) {
        if (name.charAt(0) == File.separatorChar) {
            String dirPath = name.substring(0, name.lastIndexOf(File.separatorChar));
            File dir = new File(dirPath);
            File f = new File(dir, name.substring(name.lastIndexOf(File.separatorChar)));
            if (!dir.isDirectory() && dir.mkdir()) {
                FileUtils.setPermissions(dir.getPath(), 505, -1, -1);
                return f;
            }
            return f;
        }
        return makeFilename(getDatabasesDir(), name);
    }

    @Override // android.content.Context
    public String[] databaseList() {
        return FileUtils.listOrEmpty(getDatabasesDir());
    }

    private File getDatabasesDir() {
        File ensurePrivateDirExists;
        synchronized (this.mDatabasesDirLock) {
            if (this.mDatabasesDir == null) {
                if ("android".equals(getPackageName())) {
                    this.mDatabasesDir = new File("/data/system");
                } else {
                    this.mDatabasesDir = new File(getDataDir(), "databases");
                }
            }
            ensurePrivateDirExists = ensurePrivateDirExists(this.mDatabasesDir);
        }
        return ensurePrivateDirExists;
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
    public void setWallpaper(InputStream data) throws IOException {
        getWallpaperManager().setStream(data);
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
    public void startActivityAsUser(Intent intent, UserHandle user) {
        startActivityAsUser(intent, null, user);
    }

    @Override // android.content.Context
    public void startActivity(Intent intent, Bundle options) {
        warnIfCallingFromSystemProcess();
        int targetSdkVersion = getApplicationInfo().targetSdkVersion;
        if ((intent.getFlags() & 268435456) == 0
                && ((targetSdkVersion < 24 || targetSdkVersion >= 28)
                        && (options == null
                                || ActivityOptions.fromBundle(options).getLaunchTaskId() == -1))) {
            throw new AndroidRuntimeException(
                    "Calling startActivity() from outside of an Activity context requires the"
                            + " FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?");
        }
        this.mMainThread
                .getInstrumentation()
                .execStartActivity(
                        getOuterContext(),
                        this.mMainThread.getApplicationThread(),
                        (IBinder) null,
                        (Activity) null,
                        intent,
                        -1,
                        options);
    }

    @Override // android.content.Context
    public void startActivityAsUser(Intent intent, Bundle options, UserHandle user) {
        try {
        } catch (RemoteException e) {
            e = e;
        }
        try {
        } catch (RemoteException e2) {
            e = e2;
            throw e.rethrowFromSystemServer();
        }
        try {
            ActivityTaskManager.getService()
                    .startActivityAsUser(
                            this.mMainThread.getApplicationThread(),
                            getOpPackageName(),
                            getAttributionTag(),
                            intent,
                            intent.resolveTypeIfNeeded(getContentResolver()),
                            null,
                            null,
                            0,
                            268435456,
                            null,
                            options,
                            user.getIdentifier());
        } catch (RemoteException e3) {
            e = e3;
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void startActivities(Intent[] intents) {
        warnIfCallingFromSystemProcess();
        startActivities(intents, null);
    }

    @Override // android.content.Context
    public int startActivitiesAsUser(Intent[] intents, Bundle options, UserHandle userHandle) {
        if ((intents[0].getFlags() & 268435456) == 0) {
            throw new AndroidRuntimeException(
                    "Calling startActivities() from outside of an Activity context requires the"
                        + " FLAG_ACTIVITY_NEW_TASK flag on first Intent. Is this really what you"
                        + " want?");
        }
        return this.mMainThread
                .getInstrumentation()
                .execStartActivitiesAsUser(
                        getOuterContext(),
                        this.mMainThread.getApplicationThread(),
                        null,
                        null,
                        intents,
                        options,
                        userHandle.getIdentifier());
    }

    @Override // android.content.Context
    public void startActivities(Intent[] intents, Bundle options) {
        warnIfCallingFromSystemProcess();
        if ((intents[0].getFlags() & 268435456) == 0) {
            throw new AndroidRuntimeException(
                    "Calling startActivities() from outside of an Activity context requires the"
                        + " FLAG_ACTIVITY_NEW_TASK flag on first Intent. Is this really what you"
                        + " want?");
        }
        this.mMainThread
                .getInstrumentation()
                .execStartActivities(
                        getOuterContext(),
                        this.mMainThread.getApplicationThread(),
                        null,
                        null,
                        intents,
                        options);
    }

    @Override // android.content.Context
    public void startIntentSender(
            IntentSender intent,
            Intent fillInIntent,
            int flagsMask,
            int flagsValues,
            int extraFlags)
            throws IntentSender.SendIntentException {
        startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags, null);
    }

    @Override // android.content.Context
    public void startIntentSender(
            IntentSender intent,
            Intent fillInIntent,
            int flagsMask,
            int flagsValues,
            int extraFlags,
            Bundle options)
            throws IntentSender.SendIntentException {
        String resolvedType = null;
        if (fillInIntent != null) {
            try {
                fillInIntent.migrateExtraStreamToClipData(this);
                fillInIntent.prepareToLeaveProcess(this);
                resolvedType = fillInIntent.resolveTypeIfNeeded(getContentResolver());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        int result =
                ActivityTaskManager.getService()
                        .startActivityIntentSender(
                                this.mMainThread.getApplicationThread(),
                                intent != null ? intent.getTarget() : null,
                                intent != null ? intent.getWhitelistToken() : null,
                                fillInIntent,
                                resolvedType,
                                null,
                                null,
                                0,
                                flagsMask,
                                flagsValues,
                                options);
        if (result == -96) {
            throw new IntentSender.SendIntentException();
        }
        Instrumentation.checkStartActivityResult(result, null);
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissionsAsUser(
            Intent intent, String[] receiverPermissions, UserHandle user) {
        warnIfCallingFromSystemProcess();
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManagerNative.getDefault()
                    .broadcastIntent(
                            this.mMainThread.getApplicationThread(),
                            intent,
                            resolvedType,
                            null,
                            -1,
                            null,
                            null,
                            receiverPermissions,
                            -1,
                            null,
                            false,
                            false,
                            user.getIdentifier());
        } catch (RemoteException e) {
            throw new RuntimeException("Failure from system", e);
        }
    }

    @Override // android.content.Context
    public void sendBroadcast(Intent intent) {
        warnIfCallingFromSystemProcess();
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            null,
                            -1,
                            null,
                            null,
                            null,
                            null,
                            null,
                            -1,
                            null,
                            false,
                            false,
                            getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcast(Intent intent, String receiverPermission) {
        warnIfCallingFromSystemProcess();
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        String[] receiverPermissions =
                receiverPermission == null ? null : new String[] {receiverPermission};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            null,
                            -1,
                            null,
                            null,
                            receiverPermissions,
                            null,
                            null,
                            -1,
                            null,
                            false,
                            false,
                            getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissions(Intent intent, String[] receiverPermissions) {
        warnIfCallingFromSystemProcess();
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            null,
                            -1,
                            null,
                            null,
                            receiverPermissions,
                            null,
                            null,
                            -1,
                            null,
                            false,
                            false,
                            getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissions(
            Intent intent, String[] receiverPermissions, Bundle options) {
        warnIfCallingFromSystemProcess();
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            null,
                            -1,
                            null,
                            null,
                            receiverPermissions,
                            null,
                            null,
                            -1,
                            options,
                            false,
                            false,
                            getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastAsUserMultiplePermissions(
            Intent intent, UserHandle user, String[] receiverPermissions) {
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            null,
                            -1,
                            null,
                            null,
                            receiverPermissions,
                            null,
                            null,
                            -1,
                            null,
                            false,
                            false,
                            user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissions(
            Intent intent,
            String[] receiverPermissions,
            String[] excludedPermissions,
            String[] excludedPackages,
            BroadcastOptions options) {
        warnIfCallingFromSystemProcess();
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            null,
                            -1,
                            null,
                            null,
                            receiverPermissions,
                            excludedPermissions,
                            excludedPackages,
                            -1,
                            options == null ? null : options.toBundle(),
                            false,
                            false,
                            getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcast(Intent intent, String receiverPermission, Bundle options) {
        String[] receiverPermissions;
        String[] excludedPermissions;
        warnIfCallingFromSystemProcess();
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        String[] receiverPermissions2 =
                receiverPermission == null ? null : new String[] {receiverPermission};
        if (options == null) {
            receiverPermissions = receiverPermissions2;
            excludedPermissions = null;
        } else {
            String[] receiverPermissionsBundle =
                    options.getStringArray(BroadcastOptions.KEY_REQUIRE_ALL_OF_PERMISSIONS);
            if (receiverPermissionsBundle != null) {
                receiverPermissions2 = receiverPermissionsBundle;
            }
            String[] excludedPermissions2 =
                    options.getStringArray(BroadcastOptions.KEY_REQUIRE_NONE_OF_PERMISSIONS);
            receiverPermissions = receiverPermissions2;
            excludedPermissions = excludedPermissions2;
        }
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            null,
                            -1,
                            null,
                            null,
                            receiverPermissions,
                            excludedPermissions,
                            null,
                            -1,
                            options,
                            false,
                            false,
                            getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcast(Intent intent, String receiverPermission, int appOp) {
        warnIfCallingFromSystemProcess();
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        String[] receiverPermissions =
                receiverPermission == null ? null : new String[] {receiverPermission};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            null,
                            -1,
                            null,
                            null,
                            receiverPermissions,
                            null,
                            null,
                            appOp,
                            null,
                            false,
                            false,
                            getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(Intent intent, String receiverPermission) {
        sendOrderedBroadcast(intent, receiverPermission, null);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(Intent intent, String receiverPermission, Bundle options) {
        warnIfCallingFromSystemProcess();
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        String[] receiverPermissions =
                receiverPermission == null ? null : new String[] {receiverPermission};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            null,
                            -1,
                            null,
                            null,
                            receiverPermissions,
                            null,
                            null,
                            -1,
                            options,
                            true,
                            false,
                            getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(
            Intent intent,
            String receiverPermission,
            BroadcastReceiver resultReceiver,
            Handler scheduler,
            int initialCode,
            String initialData,
            Bundle initialExtras) {
        sendOrderedBroadcast(
                intent,
                receiverPermission,
                -1,
                resultReceiver,
                scheduler,
                initialCode,
                initialData,
                initialExtras,
                (Bundle) null);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(
            Intent intent,
            String receiverPermission,
            Bundle options,
            BroadcastReceiver resultReceiver,
            Handler scheduler,
            int initialCode,
            String initialData,
            Bundle initialExtras) {
        sendOrderedBroadcast(
                intent,
                receiverPermission,
                -1,
                resultReceiver,
                scheduler,
                initialCode,
                initialData,
                initialExtras,
                options);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(
            Intent intent,
            String receiverPermission,
            int appOp,
            BroadcastReceiver resultReceiver,
            Handler scheduler,
            int initialCode,
            String initialData,
            Bundle initialExtras) {
        sendOrderedBroadcast(
                intent,
                receiverPermission,
                appOp,
                resultReceiver,
                scheduler,
                initialCode,
                initialData,
                initialExtras,
                (Bundle) null);
    }

    void sendOrderedBroadcast(
            Intent intent,
            String receiverPermission,
            int appOp,
            BroadcastReceiver resultReceiver,
            Handler scheduler,
            int initialCode,
            String initialData,
            Bundle initialExtras,
            Bundle options) {
        IIntentReceiver rd;
        Handler scheduler2;
        Handler scheduler3;
        warnIfCallingFromSystemProcess();
        if (resultReceiver == null) {
            rd = null;
        } else if (this.mPackageInfo != null) {
            if (scheduler != null) {
                scheduler3 = scheduler;
            } else {
                scheduler3 = this.mMainThread.getHandler();
            }
            IIntentReceiver rd2 =
                    this.mPackageInfo.getReceiverDispatcher(
                            resultReceiver,
                            getOuterContext(),
                            scheduler3,
                            this.mMainThread.getInstrumentation(),
                            false);
            rd = rd2;
        } else {
            if (scheduler != null) {
                scheduler2 = scheduler;
            } else {
                scheduler2 = this.mMainThread.getHandler();
            }
            IIntentReceiver rd3 =
                    new LoadedApk.ReceiverDispatcher(
                                    this.mMainThread.getApplicationThread(),
                                    resultReceiver,
                                    getOuterContext(),
                                    scheduler2,
                                    null,
                                    false)
                            .getIIntentReceiver();
            rd = rd3;
        }
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        String[] receiverPermissions =
                receiverPermission == null ? null : new String[] {receiverPermission};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            rd,
                            initialCode,
                            initialData,
                            initialExtras,
                            receiverPermissions,
                            null,
                            null,
                            appOp,
                            options,
                            true,
                            false,
                            getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(Intent intent, UserHandle user) {
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            null,
                            -1,
                            null,
                            null,
                            null,
                            null,
                            null,
                            -1,
                            null,
                            false,
                            false,
                            user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(Intent intent, UserHandle user, String receiverPermission) {
        sendBroadcastAsUser(intent, user, receiverPermission, -1);
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(
            Intent intent, UserHandle user, String receiverPermission, Bundle options) {
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        String[] receiverPermissions =
                receiverPermission == null ? null : new String[] {receiverPermission};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            null,
                            -1,
                            null,
                            null,
                            receiverPermissions,
                            null,
                            null,
                            -1,
                            options,
                            false,
                            false,
                            user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(
            Intent intent, UserHandle user, String receiverPermission, int appOp) {
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        String[] receiverPermissions =
                receiverPermission == null ? null : new String[] {receiverPermission};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            null,
                            -1,
                            null,
                            null,
                            receiverPermissions,
                            null,
                            null,
                            appOp,
                            null,
                            false,
                            false,
                            user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastAsUser(
            Intent intent,
            UserHandle user,
            String receiverPermission,
            BroadcastReceiver resultReceiver,
            Handler scheduler,
            int initialCode,
            String initialData,
            Bundle initialExtras) {
        sendOrderedBroadcastAsUser(
                intent,
                user,
                receiverPermission,
                -1,
                null,
                resultReceiver,
                scheduler,
                initialCode,
                initialData,
                initialExtras);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastAsUser(
            Intent intent,
            UserHandle user,
            String receiverPermission,
            int appOp,
            BroadcastReceiver resultReceiver,
            Handler scheduler,
            int initialCode,
            String initialData,
            Bundle initialExtras) {
        sendOrderedBroadcastAsUser(
                intent,
                user,
                receiverPermission,
                appOp,
                null,
                resultReceiver,
                scheduler,
                initialCode,
                initialData,
                initialExtras);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastAsUser(
            Intent intent,
            UserHandle user,
            String receiverPermission,
            int appOp,
            Bundle options,
            BroadcastReceiver resultReceiver,
            Handler scheduler,
            int initialCode,
            String initialData,
            Bundle initialExtras) {
        IIntentReceiver rd;
        Handler scheduler2;
        Handler scheduler3;
        if (resultReceiver == null) {
            rd = null;
        } else if (this.mPackageInfo != null) {
            if (scheduler != null) {
                scheduler3 = scheduler;
            } else {
                scheduler3 = this.mMainThread.getHandler();
            }
            IIntentReceiver rd2 =
                    this.mPackageInfo.getReceiverDispatcher(
                            resultReceiver,
                            getOuterContext(),
                            scheduler3,
                            this.mMainThread.getInstrumentation(),
                            false);
            rd = rd2;
        } else {
            if (scheduler != null) {
                scheduler2 = scheduler;
            } else {
                scheduler2 = this.mMainThread.getHandler();
            }
            IIntentReceiver rd3 =
                    new LoadedApk.ReceiverDispatcher(
                                    this.mMainThread.getApplicationThread(),
                                    resultReceiver,
                                    getOuterContext(),
                                    scheduler2,
                                    null,
                                    false)
                            .getIIntentReceiver();
            rd = rd3;
        }
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        String[] receiverPermissions =
                receiverPermission == null ? null : new String[] {receiverPermission};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            rd,
                            initialCode,
                            initialData,
                            initialExtras,
                            receiverPermissions,
                            null,
                            null,
                            appOp,
                            options,
                            true,
                            false,
                            user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(
            Intent intent,
            String receiverPermission,
            String receiverAppOp,
            BroadcastReceiver resultReceiver,
            Handler scheduler,
            int initialCode,
            String initialData,
            Bundle initialExtras) {
        int intAppOp = -1;
        if (!TextUtils.isEmpty(receiverAppOp)) {
            intAppOp = AppOpsManager.strOpToOp(receiverAppOp);
        }
        sendOrderedBroadcastAsUser(
                intent,
                getUser(),
                receiverPermission,
                intAppOp,
                resultReceiver,
                scheduler,
                initialCode,
                initialData,
                initialExtras);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(
            Intent intent,
            int initialCode,
            String receiverPermission,
            String receiverAppOp,
            BroadcastReceiver resultReceiver,
            Handler scheduler,
            String initialData,
            Bundle initialExtras,
            Bundle options) {
        int intAppOp = -1;
        if (!TextUtils.isEmpty(receiverAppOp)) {
            intAppOp = AppOpsManager.strOpToOp(receiverAppOp);
        }
        sendOrderedBroadcastAsUser(
                intent,
                getUser(),
                receiverPermission,
                intAppOp,
                options,
                resultReceiver,
                scheduler,
                initialCode,
                initialData,
                initialExtras);
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyBroadcast(Intent intent) {
        warnIfCallingFromSystemProcess();
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            null,
                            -1,
                            null,
                            null,
                            null,
                            null,
                            null,
                            -1,
                            null,
                            false,
                            true,
                            getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyBroadcast(Intent intent, Bundle options) {
        warnIfCallingFromSystemProcess();
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            null,
                            -1,
                            null,
                            null,
                            null,
                            null,
                            null,
                            -1,
                            options,
                            false,
                            true,
                            getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyOrderedBroadcast(
            Intent intent,
            BroadcastReceiver resultReceiver,
            Handler scheduler,
            int initialCode,
            String initialData,
            Bundle initialExtras) {
        IIntentReceiver rd;
        Handler scheduler2;
        Handler scheduler3;
        warnIfCallingFromSystemProcess();
        if (resultReceiver == null) {
            rd = null;
        } else if (this.mPackageInfo != null) {
            if (scheduler != null) {
                scheduler3 = scheduler;
            } else {
                scheduler3 = this.mMainThread.getHandler();
            }
            IIntentReceiver rd2 =
                    this.mPackageInfo.getReceiverDispatcher(
                            resultReceiver,
                            getOuterContext(),
                            scheduler3,
                            this.mMainThread.getInstrumentation(),
                            false);
            rd = rd2;
        } else {
            if (scheduler != null) {
                scheduler2 = scheduler;
            } else {
                scheduler2 = this.mMainThread.getHandler();
            }
            IIntentReceiver rd3 =
                    new LoadedApk.ReceiverDispatcher(
                                    this.mMainThread.getApplicationThread(),
                                    resultReceiver,
                                    getOuterContext(),
                                    scheduler2,
                                    null,
                                    false)
                            .getIIntentReceiver();
            rd = rd3;
        }
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            rd,
                            initialCode,
                            initialData,
                            initialExtras,
                            null,
                            null,
                            null,
                            -1,
                            null,
                            true,
                            true,
                            getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @Deprecated
    public void removeStickyBroadcast(Intent intent) {
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        if (resolvedType != null) {
            intent = new Intent(intent);
            intent.setDataAndType(intent.getData(), resolvedType);
        }
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .unbroadcastIntent(
                            this.mMainThread.getApplicationThread(), intent, getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyBroadcastAsUser(Intent intent, UserHandle user) {
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            null,
                            -1,
                            null,
                            null,
                            null,
                            null,
                            null,
                            -1,
                            null,
                            false,
                            true,
                            user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyBroadcastAsUser(Intent intent, UserHandle user, Bundle options) {
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            null,
                            -1,
                            null,
                            null,
                            null,
                            null,
                            null,
                            -1,
                            options,
                            false,
                            true,
                            user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @Deprecated
    public void sendStickyOrderedBroadcastAsUser(
            Intent intent,
            UserHandle user,
            BroadcastReceiver resultReceiver,
            Handler scheduler,
            int initialCode,
            String initialData,
            Bundle initialExtras) {
        IIntentReceiver rd;
        Handler scheduler2;
        Handler scheduler3;
        if (resultReceiver == null) {
            rd = null;
        } else if (this.mPackageInfo != null) {
            if (scheduler != null) {
                scheduler3 = scheduler;
            } else {
                scheduler3 = this.mMainThread.getHandler();
            }
            IIntentReceiver rd2 =
                    this.mPackageInfo.getReceiverDispatcher(
                            resultReceiver,
                            getOuterContext(),
                            scheduler3,
                            this.mMainThread.getInstrumentation(),
                            false);
            rd = rd2;
        } else {
            if (scheduler != null) {
                scheduler2 = scheduler;
            } else {
                scheduler2 = this.mMainThread.getHandler();
            }
            IIntentReceiver rd3 =
                    new LoadedApk.ReceiverDispatcher(
                                    this.mMainThread.getApplicationThread(),
                                    resultReceiver,
                                    getOuterContext(),
                                    scheduler2,
                                    null,
                                    false)
                            .getIIntentReceiver();
            rd = rd3;
        }
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .broadcastIntentWithFeature(
                            this.mMainThread.getApplicationThread(),
                            getAttributionTag(),
                            intent,
                            resolvedType,
                            rd,
                            initialCode,
                            initialData,
                            initialExtras,
                            null,
                            null,
                            null,
                            -1,
                            null,
                            true,
                            true,
                            user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @Deprecated
    public void removeStickyBroadcastAsUser(Intent intent, UserHandle user) {
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        if (resolvedType != null) {
            intent = new Intent(intent);
            intent.setDataAndType(intent.getData(), resolvedType);
        }
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService()
                    .unbroadcastIntent(
                            this.mMainThread.getApplicationThread(), intent, user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return registerReceiver(receiver, filter, null, null);
    }

    @Override // android.content.Context
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, int flags) {
        return registerReceiver(receiver, filter, null, null, flags);
    }

    @Override // android.content.Context
    public Intent registerReceiver(
            BroadcastReceiver receiver,
            IntentFilter filter,
            String broadcastPermission,
            Handler scheduler) {
        return registerReceiverInternal(
                receiver,
                getUserId(),
                filter,
                broadcastPermission,
                scheduler,
                getOuterContext(),
                0);
    }

    @Override // android.content.Context
    public Intent registerReceiver(
            BroadcastReceiver receiver,
            IntentFilter filter,
            String broadcastPermission,
            Handler scheduler,
            int flags) {
        return registerReceiverInternal(
                receiver,
                getUserId(),
                filter,
                broadcastPermission,
                scheduler,
                getOuterContext(),
                flags);
    }

    @Override // android.content.Context
    public Intent registerReceiverForAllUsers(
            BroadcastReceiver receiver,
            IntentFilter filter,
            String broadcastPermission,
            Handler scheduler) {
        return registerReceiverAsUser(
                receiver, UserHandle.ALL, filter, broadcastPermission, scheduler);
    }

    @Override // android.content.Context
    public Intent registerReceiverForAllUsers(
            BroadcastReceiver receiver,
            IntentFilter filter,
            String broadcastPermission,
            Handler scheduler,
            int flags) {
        return registerReceiverAsUser(
                receiver, UserHandle.ALL, filter, broadcastPermission, scheduler, flags);
    }

    @Override // android.content.Context
    public Intent registerReceiverAsUser(
            BroadcastReceiver receiver,
            UserHandle user,
            IntentFilter filter,
            String broadcastPermission,
            Handler scheduler) {
        return registerReceiverInternal(
                receiver,
                user.getIdentifier(),
                filter,
                broadcastPermission,
                scheduler,
                getOuterContext(),
                0);
    }

    @Override // android.content.Context
    public Intent registerReceiverAsUser(
            BroadcastReceiver receiver,
            UserHandle user,
            IntentFilter filter,
            String broadcastPermission,
            Handler scheduler,
            int flags) {
        return registerReceiverInternal(
                receiver,
                user.getIdentifier(),
                filter,
                broadcastPermission,
                scheduler,
                getOuterContext(),
                flags);
    }

    private Intent registerReceiverInternal(
            BroadcastReceiver receiver,
            int userId,
            IntentFilter filter,
            String broadcastPermission,
            Handler scheduler,
            Context context,
            int flags) {
        IIntentReceiver rd;
        Handler scheduler2;
        Handler scheduler3;
        if (receiver == null) {
            rd = null;
        } else if (this.mPackageInfo != null && context != null) {
            if (scheduler != null) {
                scheduler3 = scheduler;
            } else {
                scheduler3 = this.mMainThread.getHandler();
            }
            IIntentReceiver rd2 =
                    this.mPackageInfo.getReceiverDispatcher(
                            receiver,
                            context,
                            scheduler3,
                            this.mMainThread.getInstrumentation(),
                            true);
            rd = rd2;
        } else {
            if (scheduler != null) {
                scheduler2 = scheduler;
            } else {
                scheduler2 = this.mMainThread.getHandler();
            }
            IIntentReceiver rd3 =
                    new LoadedApk.ReceiverDispatcher(
                                    this.mMainThread.getApplicationThread(),
                                    receiver,
                                    context,
                                    scheduler2,
                                    null,
                                    true)
                            .getIIntentReceiver();
            rd = rd3;
        }
        try {
            Intent intent =
                    ActivityManager.getService()
                            .registerReceiverWithFeature(
                                    this.mMainThread.getApplicationThread(),
                                    this.mBasePackageName,
                                    getAttributionTag(),
                                    AppOpsManager.toReceiverId(receiver),
                                    rd,
                                    filter,
                                    broadcastPermission,
                                    userId,
                                    flags);
            if (intent != null) {
                intent.setExtrasClassLoader(getClassLoader());
                intent.prepareToEnterProcess(
                        ActivityThread.isProtectedBroadcast(intent), getAttributionSource());
            }
            return intent;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void unregisterReceiver(BroadcastReceiver receiver) {
        if (this.mPackageInfo != null) {
            IIntentReceiver rd =
                    this.mPackageInfo.forgetReceiverDispatcher(getOuterContext(), receiver);
            try {
                ActivityManager.getService().unregisterReceiver(rd);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        throw new RuntimeException("Not supported in system context");
    }

    private void validateServiceIntent(Intent service) {
        if (service.getComponent() == null && service.getPackage() == null) {
            if (getApplicationInfo().targetSdkVersion >= 21) {
                IllegalArgumentException ex =
                        new IllegalArgumentException("Service Intent must be explicit: " + service);
                throw ex;
            }
            Log.w(
                    TAG,
                    "Implicit intents with startService are not safe: "
                            + service
                            + " "
                            + Debug.getCallers(2, 3));
        }
    }

    @Override // android.content.Context
    public ComponentName startService(Intent service) {
        warnIfCallingFromSystemProcess();
        return startServiceCommon(service, false, this.mUser);
    }

    @Override // android.content.Context
    public ComponentName startForegroundService(Intent service) {
        warnIfCallingFromSystemProcess();
        return startServiceCommon(service, true, this.mUser);
    }

    @Override // android.content.Context
    public boolean stopService(Intent service) {
        warnIfCallingFromSystemProcess();
        return stopServiceCommon(service, this.mUser);
    }

    @Override // android.content.Context
    public ComponentName startServiceAsUser(Intent service, UserHandle user) {
        return startServiceCommon(service, false, user);
    }

    @Override // android.content.Context
    public ComponentName startForegroundServiceAsUser(Intent service, UserHandle user) {
        return startServiceCommon(service, true, user);
    }

    private ComponentName startServiceCommon(
            Intent service, boolean requireForeground, UserHandle user) {
        try {
            ComponentName aux = service.getComponent();
            if (aux != null
                    && aux.getPackageName() != null
                    && KioskMode.MINI_TASK_MANAGER_PKGNAME.equals(aux.getPackageName())
                    && !EnterpriseDeviceManager.getInstance()
                            .getKioskMode()
                            .isTaskManagerAllowed(true)) {
                return null;
            }
            validateServiceIntent(service);
            service.prepareToLeaveProcess(this);
            ComponentName cn =
                    ActivityManager.getService()
                            .startService(
                                    this.mMainThread.getApplicationThread(),
                                    service,
                                    service.resolveTypeIfNeeded(getContentResolver()),
                                    requireForeground,
                                    getOpPackageName(),
                                    getAttributionTag(),
                                    user.getIdentifier());
            if (cn != null) {
                if (cn.getPackageName().equals("!")) {
                    throw new SecurityException(
                            "Not allowed to start service "
                                    + service
                                    + " without permission "
                                    + cn.getClassName());
                }
                if (cn.getPackageName().equals("!!")) {
                    throw new SecurityException(
                            "Unable to start service " + service + ": " + cn.getClassName());
                }
                if (cn.getPackageName().equals("?")) {
                    throw ServiceStartNotAllowedException.newInstance(
                            requireForeground,
                            "Not allowed to start service " + service + ": " + cn.getClassName());
                }
            }
            if (cn != null && requireForeground && cn.getPackageName().equals(getOpPackageName())) {
                Service.setStartForegroundServiceStackTrace(
                        cn.getClassName(),
                        new StackTrace(
                                "Last startServiceCommon() call for this service was made here"));
            }
            return cn;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public boolean stopServiceAsUser(Intent service, UserHandle user) {
        return stopServiceCommon(service, user);
    }

    private boolean stopServiceCommon(Intent service, UserHandle user) {
        try {
            validateServiceIntent(service);
            service.prepareToLeaveProcess(this);
            int res =
                    ActivityManager.getService()
                            .stopService(
                                    this.mMainThread.getApplicationThread(),
                                    service,
                                    service.resolveTypeIfNeeded(getContentResolver()),
                                    user.getIdentifier());
            if (res >= 0) {
                return res != 0;
            }
            throw new SecurityException("Not allowed to stop service " + service);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        warnIfCallingFromSystemProcess();
        return bindServiceCommon(
                service,
                conn,
                Integer.toUnsignedLong(flags),
                null,
                this.mMainThread.getHandler(),
                null,
                getUser());
    }

    @Override // android.content.Context
    public boolean bindService(
            Intent service, ServiceConnection conn, Context.BindServiceFlags flags) {
        warnIfCallingFromSystemProcess();
        return bindServiceCommon(
                service,
                conn,
                flags.getValue(),
                null,
                this.mMainThread.getHandler(),
                null,
                getUser());
    }

    @Override // android.content.Context
    public boolean bindService(
            Intent service, int flags, Executor executor, ServiceConnection conn) {
        return bindServiceCommon(
                service, conn, Integer.toUnsignedLong(flags), null, null, executor, getUser());
    }

    @Override // android.content.Context
    public boolean bindService(
            Intent service,
            Context.BindServiceFlags flags,
            Executor executor,
            ServiceConnection conn) {
        return bindServiceCommon(service, conn, flags.getValue(), null, null, executor, getUser());
    }

    @Override // android.content.Context
    public boolean bindIsolatedService(
            Intent service,
            int flags,
            String instanceName,
            Executor executor,
            ServiceConnection conn) {
        warnIfCallingFromSystemProcess();
        if (instanceName == null) {
            throw new NullPointerException("null instanceName");
        }
        return bindServiceCommon(
                service,
                conn,
                Integer.toUnsignedLong(flags),
                instanceName,
                null,
                executor,
                getUser());
    }

    @Override // android.content.Context
    public boolean bindIsolatedService(
            Intent service,
            Context.BindServiceFlags flags,
            String instanceName,
            Executor executor,
            ServiceConnection conn) {
        warnIfCallingFromSystemProcess();
        if (instanceName == null) {
            throw new NullPointerException("null instanceName");
        }
        return bindServiceCommon(
                service, conn, flags.getValue(), instanceName, null, executor, getUser());
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(
            Intent service, ServiceConnection conn, int flags, UserHandle user) {
        return bindServiceCommon(
                service,
                conn,
                Integer.toUnsignedLong(flags),
                null,
                this.mMainThread.getHandler(),
                null,
                user);
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(
            Intent service,
            ServiceConnection conn,
            Context.BindServiceFlags flags,
            UserHandle user) {
        return bindServiceCommon(
                service, conn, flags.getValue(), null, this.mMainThread.getHandler(), null, user);
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(
            Intent service, ServiceConnection conn, int flags, Handler handler, UserHandle user) {
        if (handler == null) {
            throw new IllegalArgumentException("handler must not be null.");
        }
        return bindServiceCommon(
                service, conn, Integer.toUnsignedLong(flags), null, handler, null, user);
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(
            Intent service,
            ServiceConnection conn,
            Context.BindServiceFlags flags,
            Handler handler,
            UserHandle user) {
        if (handler == null) {
            throw new IllegalArgumentException("handler must not be null.");
        }
        return bindServiceCommon(service, conn, flags.getValue(), null, handler, null, user);
    }

    @Override // android.content.Context
    public IServiceConnection getServiceDispatcher(
            ServiceConnection conn, Handler handler, long flags) {
        return this.mPackageInfo.getServiceDispatcher(conn, getOuterContext(), handler, flags);
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

    /* JADX WARN: Removed duplicated region for block: B:27:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x009a A[Catch: RemoteException -> 0x00b3, TryCatch #0 {RemoteException -> 0x00b3, blocks: (B:25:0x0067, B:31:0x009a, B:32:0x00b2), top: B:24:0x0067 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean bindServiceCommon(
            android.content.Intent r18,
            android.content.ServiceConnection r19,
            long r20,
            java.lang.String r22,
            android.os.Handler r23,
            java.util.concurrent.Executor r24,
            android.os.UserHandle r25) {
        /*
            r17 = this;
            r1 = r17
            r13 = r18
            if (r19 == 0) goto Lc5
            if (r23 == 0) goto L13
            if (r24 != 0) goto Lb
            goto L13
        Lb:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "Handler and Executor both supplied"
            r0.<init>(r2)
            throw r0
        L13:
            android.app.LoadedApk r0 = r1.mPackageInfo
            if (r0 == 0) goto Lbd
            if (r24 == 0) goto L2b
            android.app.LoadedApk r2 = r1.mPackageInfo
            android.content.Context r4 = r17.getOuterContext()
            r3 = r19
            r5 = r24
            r6 = r20
            android.app.IServiceConnection r0 = r2.getServiceDispatcher(r3, r4, r5, r6)
            r14 = r0
            goto L3c
        L2b:
            android.app.LoadedApk r2 = r1.mPackageInfo
            android.content.Context r4 = r17.getOuterContext()
            r3 = r19
            r5 = r23
            r6 = r20
            android.app.IServiceConnection r0 = r2.getServiceDispatcher(r3, r4, r5, r6)
            r14 = r0
        L3c:
            r17.validateServiceIntent(r18)
            android.os.IBinder r0 = r17.getActivityToken()     // Catch: android.os.RemoteException -> Lb5
            if (r0 != 0) goto L65
            r2 = 1
            long r2 = r20 & r2
            r4 = 0
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 != 0) goto L65
            android.app.LoadedApk r2 = r1.mPackageInfo     // Catch: android.os.RemoteException -> Lb5
            if (r2 == 0) goto L65
            android.app.LoadedApk r2 = r1.mPackageInfo     // Catch: android.os.RemoteException -> Lb5
            android.content.pm.ApplicationInfo r2 = r2.getApplicationInfo()     // Catch: android.os.RemoteException -> Lb5
            int r2 = r2.targetSdkVersion     // Catch: android.os.RemoteException -> Lb5
            r3 = 14
            if (r2 >= r3) goto L65
            r2 = 32
            long r2 = r20 | r2
            r15 = r2
            goto L67
        L65:
            r15 = r20
        L67:
            r13.prepareToLeaveProcess(r1)     // Catch: android.os.RemoteException -> Lb3
            android.app.IActivityManager r2 = android.app.ActivityManager.getService()     // Catch: android.os.RemoteException -> Lb3
            android.app.ActivityThread r3 = r1.mMainThread     // Catch: android.os.RemoteException -> Lb3
            android.app.ActivityThread$ApplicationThread r3 = r3.getApplicationThread()     // Catch: android.os.RemoteException -> Lb3
            android.os.IBinder r4 = r17.getActivityToken()     // Catch: android.os.RemoteException -> Lb3
            android.content.ContentResolver r5 = r17.getContentResolver()     // Catch: android.os.RemoteException -> Lb3
            java.lang.String r6 = r13.resolveTypeIfNeeded(r5)     // Catch: android.os.RemoteException -> Lb3
            java.lang.String r11 = r17.getOpPackageName()     // Catch: android.os.RemoteException -> Lb3
            int r12 = r25.getIdentifier()     // Catch: android.os.RemoteException -> Lb3
            r5 = r18
            r7 = r14
            r8 = r15
            r10 = r22
            int r2 = r2.bindServiceInstance(r3, r4, r5, r6, r7, r8, r10, r11, r12)     // Catch: android.os.RemoteException -> Lb3
            if (r2 < 0) goto L9a
            if (r2 == 0) goto L98
            r3 = 1
            goto L99
        L98:
            r3 = 0
        L99:
            return r3
        L9a:
            java.lang.SecurityException r3 = new java.lang.SecurityException     // Catch: android.os.RemoteException -> Lb3
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> Lb3
            r4.<init>()     // Catch: android.os.RemoteException -> Lb3
            java.lang.String r5 = "Not allowed to bind to service "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch: android.os.RemoteException -> Lb3
            java.lang.StringBuilder r4 = r4.append(r13)     // Catch: android.os.RemoteException -> Lb3
            java.lang.String r4 = r4.toString()     // Catch: android.os.RemoteException -> Lb3
            r3.<init>(r4)     // Catch: android.os.RemoteException -> Lb3
            throw r3     // Catch: android.os.RemoteException -> Lb3
        Lb3:
            r0 = move-exception
            goto Lb8
        Lb5:
            r0 = move-exception
            r15 = r20
        Lb8:
            java.lang.RuntimeException r2 = r0.rethrowFromSystemServer()
            throw r2
        Lbd:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r2 = "Not supported in system context"
            r0.<init>(r2)
            throw r0
        Lc5:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "connection is null"
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                        + " android.app.ContextImpl.bindServiceCommon(android.content.Intent,"
                        + " android.content.ServiceConnection, long, java.lang.String,"
                        + " android.os.Handler, java.util.concurrent.Executor,"
                        + " android.os.UserHandle):boolean");
    }

    @Override // android.content.Context
    public void updateServiceGroup(ServiceConnection conn, int group, int importance) {
        if (conn == null) {
            throw new IllegalArgumentException("connection is null");
        }
        if (this.mPackageInfo != null) {
            IServiceConnection sd =
                    this.mPackageInfo.lookupServiceDispatcher(conn, getOuterContext());
            if (sd == null) {
                throw new IllegalArgumentException(
                        "ServiceConnection not currently bound: " + conn);
            }
            try {
                ActivityManager.getService().updateServiceGroup(sd, group, importance);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        throw new RuntimeException("Not supported in system context");
    }

    @Override // android.content.Context
    public void unbindService(ServiceConnection conn) {
        if (conn == null) {
            throw new IllegalArgumentException("connection is null");
        }
        if (this.mPackageInfo != null) {
            IServiceConnection sd =
                    this.mPackageInfo.forgetServiceDispatcher(getOuterContext(), conn);
            try {
                ActivityManager.getService().unbindService(sd);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        throw new RuntimeException("Not supported in system context");
    }

    @Override // android.content.Context
    public boolean startInstrumentation(
            ComponentName className, String profileFile, Bundle arguments) {
        if (arguments != null) {
            try {
                arguments.setAllowFds(false);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return ActivityManager.getService()
                .startInstrumentation(
                        className, profileFile, 0, arguments, null, null, getUserId(), null);
    }

    @Override // android.content.Context
    public Object getSystemService(String name) {
        SemUnionManager semUnionManager;
        if (StrictMode.vmIncorrectContextUseEnabled()
                && Context.WINDOW_SERVICE.equals(name)
                && !isUiContext()) {
            String errorMessage =
                    "Tried to access visual service "
                            + SystemServiceRegistry.getSystemServiceClassName(name)
                            + " from a non-visual Context:"
                            + getOuterContext();
            Exception exception = new IllegalAccessException(errorMessage);
            StrictMode.onIncorrectContextUsed(
                    "WindowManager should be accessed from Activity or other visual Context. Use an"
                        + " Activity or a Context created with Context#createWindowContext(int,"
                        + " Bundle), which are adjusted to the configuration and visual bounds of"
                        + " an area on screen.",
                    exception);
            Log.e(
                    TAG,
                    errorMessage
                            + " WindowManager should be accessed from Activity or other visual"
                            + " Context. Use an Activity or a Context created with"
                            + " Context#createWindowContext(int, Bundle), which are adjusted to the"
                            + " configuration and visual bounds of an area on screen.",
                    exception);
        }
        Object service = SystemServiceRegistry.getSystemService(this, name);
        if (service != null) {
            return service;
        }
        if (UnionUtils.FEATURE_ENABLED
                && SemUnionManager.isUnionService(name)
                && (semUnionManager =
                                (SemUnionManager)
                                        SystemServiceRegistry.getSystemService(
                                                this, Context.SEP_UNION_SERVICE))
                        != null) {
            return semUnionManager.getUnionService(name);
        }
        return null;
    }

    @Override // android.content.Context
    public String getSystemServiceName(Class<?> serviceClass) {
        return SystemServiceRegistry.getSystemServiceName(serviceClass);
    }

    @Override // android.content.Context
    public boolean isUiContext() {
        switch (this.mContextType) {
        }
        return false;
    }

    @Override // android.content.Context
    public boolean isConfigurationContext() {
        return isUiContext() || this.mIsConfigurationBasedContext;
    }

    private static boolean isSystemOrSystemUI(Context context) {
        return ActivityThread.isSystem()
                || context.checkPermission(
                                Manifest.permission.STATUS_BAR_SERVICE,
                                Binder.getCallingPid(),
                                Binder.getCallingUid())
                        == 0;
    }

    @Override // android.content.Context
    public int checkPermission(String permission, int pid, int uid) {
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }
        if (this.mParams.isRenouncedPermission(permission)
                && pid == Process.myPid()
                && uid == Process.myUid()) {
            Log.v(TAG, "Treating renounced permission " + permission + " as denied");
            return -1;
        }
        int deviceId = getDeviceId();
        if (deviceId != 0
                && !this.mIsExplicitDeviceId
                && PermissionManager.DEVICE_AWARE_PERMISSIONS.contains(permission)) {
            VirtualDeviceManager virtualDeviceManager =
                    (VirtualDeviceManager) getSystemService(VirtualDeviceManager.class);
            VirtualDevice virtualDevice = virtualDeviceManager.getVirtualDevice(deviceId);
            if (virtualDevice != null) {
                if ((Objects.equals(permission, Manifest.permission.RECORD_AUDIO)
                                && !virtualDevice.hasCustomAudioInputSupport())
                        || (Objects.equals(permission, Manifest.permission.CAMERA)
                                && !virtualDevice.hasCustomCameraSupport())) {
                    deviceId = 0;
                }
            } else {
                Slog.e(
                        TAG,
                        "virtualDevice is not found when device id is not default. deviceId = "
                                + deviceId);
            }
        }
        return PermissionManager.checkPermission(permission, pid, uid, deviceId);
    }

    @Override // android.content.Context
    public int checkPermission(String permission, int pid, int uid, IBinder callerToken) {
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }
        if (this.mParams.isRenouncedPermission(permission)
                && pid == Process.myPid()
                && uid == Process.myUid()) {
            Log.v(TAG, "Treating renounced permission " + permission + " as denied");
            return -1;
        }
        return checkPermission(permission, pid, uid);
    }

    @Override // android.content.Context
    public void revokeSelfPermissionsOnKill(Collection<String> permissions) {
        ((PermissionControllerManager) getSystemService(PermissionControllerManager.class))
                .revokeSelfPermissionsOnKill(getPackageName(), new ArrayList(permissions));
    }

    @Override // android.content.Context
    public int checkCallingPermission(String permission) {
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }
        int pid = Binder.getCallingPid();
        if (pid != Process.myPid()) {
            return checkPermission(permission, pid, Binder.getCallingUid());
        }
        return -1;
    }

    @Override // android.content.Context
    public int checkCallingOrSelfPermission(String permission) {
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }
        return checkPermission(permission, Binder.getCallingPid(), Binder.getCallingUid());
    }

    @Override // android.content.Context
    public int checkSelfPermission(String permission) {
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }
        if (this.mParams.isRenouncedPermission(permission)) {
            Log.v(TAG, "Treating renounced permission " + permission + " as denied");
            return -1;
        }
        return checkPermission(permission, Process.myPid(), Process.myUid());
    }

    private void enforce(
            String permission, int resultOfCheck, boolean selfToo, int uid, String message) {
        String str;
        if (resultOfCheck != 0) {
            StringBuilder append =
                    new StringBuilder().append(message != null ? message + ": " : "");
            if (selfToo) {
                str = "Neither user " + uid + " nor current process has ";
            } else {
                str = "uid " + uid + " does not have ";
            }
            throw new SecurityException(
                    append.append(str)
                            .append(permission)
                            .append(MediaMetrics.SEPARATOR)
                            .toString());
        }
    }

    @Override // android.content.Context
    public void enforcePermission(String permission, int pid, int uid, String message) {
        enforce(permission, checkPermission(permission, pid, uid), false, uid, message);
    }

    @Override // android.content.Context
    public void enforceCallingPermission(String permission, String message) {
        enforce(
                permission,
                checkCallingPermission(permission),
                false,
                Binder.getCallingUid(),
                message);
    }

    @Override // android.content.Context
    public void enforceCallingOrSelfPermission(String permission, String message) {
        enforce(
                permission,
                checkCallingOrSelfPermission(permission),
                true,
                Binder.getCallingUid(),
                message);
    }

    @Override // android.content.Context
    public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {
        try {
            ActivityManager.getService()
                    .grantUriPermission(
                            this.mMainThread.getApplicationThread(),
                            toPackage,
                            ContentProvider.getUriWithoutUserId(uri),
                            modeFlags,
                            resolveUserId(uri));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void revokeUriPermission(Uri uri, int modeFlags) {
        try {
            ActivityManager.getService()
                    .revokeUriPermission(
                            this.mMainThread.getApplicationThread(),
                            null,
                            ContentProvider.getUriWithoutUserId(uri),
                            modeFlags,
                            resolveUserId(uri));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void revokeUriPermission(String targetPackage, Uri uri, int modeFlags) {
        try {
            ActivityManager.getService()
                    .revokeUriPermission(
                            this.mMainThread.getApplicationThread(),
                            targetPackage,
                            ContentProvider.getUriWithoutUserId(uri),
                            modeFlags,
                            resolveUserId(uri));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
        try {
            return ActivityManager.getService()
                    .checkUriPermission(
                            ContentProvider.getUriWithoutUserId(uri),
                            pid,
                            uid,
                            modeFlags,
                            resolveUserId(uri),
                            null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public int checkContentUriPermissionFull(Uri uri, int pid, int uid, int modeFlags) {
        try {
            return ActivityManager.getService()
                    .checkContentUriPermissionFull(
                            ContentProvider.getUriWithoutUserId(uri),
                            pid,
                            uid,
                            modeFlags,
                            resolveUserId(uri));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public int[] checkUriPermissions(List<Uri> uris, int pid, int uid, int modeFlags) {
        try {
            return ActivityManager.getService()
                    .checkUriPermissions(uris, pid, uid, modeFlags, getUserId(), null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags, IBinder callerToken) {
        try {
            return ActivityManager.getService()
                    .checkUriPermission(
                            ContentProvider.getUriWithoutUserId(uri),
                            pid,
                            uid,
                            modeFlags,
                            resolveUserId(uri),
                            callerToken);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int resolveUserId(Uri uri) {
        return ContentProvider.getUserIdFromUri(uri, getUserId());
    }

    @Override // android.content.Context
    public int checkCallingUriPermission(Uri uri, int modeFlags) {
        int pid = Binder.getCallingPid();
        if (pid != Process.myPid()) {
            return checkUriPermission(uri, pid, Binder.getCallingUid(), modeFlags);
        }
        return -1;
    }

    @Override // android.content.Context
    public int[] checkCallingUriPermissions(List<Uri> uris, int modeFlags) {
        int pid = Binder.getCallingPid();
        if (pid != Process.myPid()) {
            return checkUriPermissions(uris, pid, Binder.getCallingUid(), modeFlags);
        }
        int[] res = new int[uris.size()];
        Arrays.fill(res, -1);
        return res;
    }

    @Override // android.content.Context
    public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
        return checkUriPermission(uri, Binder.getCallingPid(), Binder.getCallingUid(), modeFlags);
    }

    @Override // android.content.Context
    public int[] checkCallingOrSelfUriPermissions(List<Uri> uris, int modeFlags) {
        return checkUriPermissions(uris, Binder.getCallingPid(), Binder.getCallingUid(), modeFlags);
    }

    @Override // android.content.Context
    public int checkUriPermission(
            Uri uri,
            String readPermission,
            String writePermission,
            int pid,
            int uid,
            int modeFlags) {
        if ((modeFlags & 1) != 0
                && (readPermission == null || checkPermission(readPermission, pid, uid) == 0)) {
            return 0;
        }
        if ((modeFlags & 2) != 0
                && (writePermission == null || checkPermission(writePermission, pid, uid) == 0)) {
            return 0;
        }
        if (uri != null) {
            return checkUriPermission(uri, pid, uid, modeFlags);
        }
        return -1;
    }

    private String uriModeFlagToString(int uriModeFlags) {
        StringBuilder builder = new StringBuilder();
        if ((uriModeFlags & 1) != 0) {
            builder.append("read and ");
        }
        if ((uriModeFlags & 2) != 0) {
            builder.append("write and ");
        }
        if ((uriModeFlags & 64) != 0) {
            builder.append("persistable and ");
        }
        if ((uriModeFlags & 128) != 0) {
            builder.append("prefix and ");
        }
        if (builder.length() > 5) {
            builder.setLength(builder.length() - 5);
            return builder.toString();
        }
        throw new IllegalArgumentException("Unknown permission mode flags: " + uriModeFlags);
    }

    private void enforceForUri(
            int modeFlags, int resultOfCheck, boolean selfToo, int uid, Uri uri, String message) {
        String str;
        if (resultOfCheck != 0) {
            StringBuilder append =
                    new StringBuilder().append(message != null ? message + ": " : "");
            if (selfToo) {
                str = "Neither user " + uid + " nor current process has ";
            } else {
                str = "User " + uid + " does not have ";
            }
            throw new SecurityException(
                    append.append(str)
                            .append(uriModeFlagToString(modeFlags))
                            .append(" permission on ")
                            .append(uri)
                            .append(MediaMetrics.SEPARATOR)
                            .toString());
        }
    }

    @Override // android.content.Context
    public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message) {
        enforceForUri(
                modeFlags, checkUriPermission(uri, pid, uid, modeFlags), false, uid, uri, message);
    }

    @Override // android.content.Context
    public void enforceCallingUriPermission(Uri uri, int modeFlags, String message) {
        enforceForUri(
                modeFlags,
                checkCallingUriPermission(uri, modeFlags),
                false,
                Binder.getCallingUid(),
                uri,
                message);
    }

    @Override // android.content.Context
    public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message) {
        enforceForUri(
                modeFlags,
                checkCallingOrSelfUriPermission(uri, modeFlags),
                true,
                Binder.getCallingUid(),
                uri,
                message);
    }

    @Override // android.content.Context
    public void enforceUriPermission(
            Uri uri,
            String readPermission,
            String writePermission,
            int pid,
            int uid,
            int modeFlags,
            String message) {
        enforceForUri(
                modeFlags,
                checkUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags),
                false,
                uid,
                uri,
                message);
    }

    private void warnIfCallingFromSystemProcess() {
        if (Process.myUid() == 1000) {
            Slog.w(
                    TAG,
                    "Calling a method in the system process without a qualified user: "
                            + Debug.getCallers(5));
        }
    }

    private static Resources createResources(
            IBinder activityToken,
            LoadedApk pi,
            String splitName,
            Integer overrideDisplayId,
            Configuration overrideConfig,
            CompatibilityInfo compatInfo,
            List<ResourcesLoader> resourcesLoader) {
        return createResources(
                activityToken,
                pi,
                splitName,
                overrideDisplayId,
                overrideConfig,
                compatInfo,
                resourcesLoader,
                false,
                false);
    }

    private static Resources createResources(
            IBinder activityToken,
            LoadedApk pi,
            String splitName,
            Integer overrideDisplayId,
            Configuration overrideConfig,
            CompatibilityInfo compatInfo,
            List<ResourcesLoader> resourcesLoader,
            boolean creatingDisplayContext,
            boolean creatingOriginConfigContext) {
        try {
            String[] splitResDirs = pi.getSplitPaths(splitName);
            ClassLoader classLoader = pi.getSplitClassLoader(splitName);
            return ResourcesManager.getInstance()
                    .getResources(
                            activityToken,
                            pi.getResDir(),
                            splitResDirs,
                            pi.getOverlayDirs(),
                            pi.getOverlayPaths(),
                            pi.getApplicationInfo().sharedLibraryFiles,
                            overrideDisplayId,
                            overrideConfig,
                            compatInfo,
                            classLoader,
                            resourcesLoader,
                            creatingDisplayContext,
                            creatingOriginConfigContext);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // android.content.Context
    public Context createApplicationContext(ApplicationInfo application, int flags)
            throws PackageManager.NameNotFoundException {
        UserHandle user = new UserHandle(UserHandle.getUserId(application.uid));
        return createApplicationContextAsUser(application, flags, user);
    }

    private Context createApplicationContextAsUser(
            ApplicationInfo application, int flags, UserHandle user)
            throws PackageManager.NameNotFoundException {
        LoadedApk pi =
                this.mMainThread.getPackageInfo(
                        application, this.mResources.getCompatibilityInfo(), flags | 1073741824);
        if (pi != null) {
            ContextImpl c =
                    new ContextImpl(
                            this,
                            this.mMainThread,
                            pi,
                            ContextParams.EMPTY,
                            this.mAttributionSource.getAttributionTag(),
                            this.mAttributionSource.getNext(),
                            null,
                            this.mToken,
                            user,
                            flags,
                            null,
                            null,
                            this.mDeviceId,
                            this.mIsExplicitDeviceId);
            int displayId = getDisplayId();
            Integer overrideDisplayId =
                    this.mForceDisplayOverrideInResources ? Integer.valueOf(displayId) : null;
            c.setResources(
                    createResources(
                            this.mToken,
                            pi,
                            null,
                            overrideDisplayId,
                            null,
                            getDisplayAdjustments(displayId).getCompatibilityInfo(),
                            null));
            if (c.mResources != null) {
                return c;
            }
        }
        throw new PackageManager.NameNotFoundException(
                "Application package " + application.packageName + " not found");
    }

    @Override // android.content.Context
    public Context createContextForSdkInSandbox(ApplicationInfo sdkInfo, int flags)
            throws PackageManager.NameNotFoundException {
        if (!Process.isSdkSandbox()) {
            throw new SecurityException("API can only be called from SdkSandbox process");
        }
        UserHandle user =
                sdkInfo.uid >= 0
                        ? new UserHandle(UserHandle.getUserId(sdkInfo.uid))
                        : Process.myUserHandle();
        ContextImpl ctx = (ContextImpl) createApplicationContextAsUser(sdkInfo, flags, user);
        ctx.mPackageInfo.makeApplicationInner(false, null);
        return ctx;
    }

    @Override // android.content.Context
    public Context createPackageContext(String packageName, int flags)
            throws PackageManager.NameNotFoundException {
        return createPackageContextAsUser(packageName, flags, this.mUser);
    }

    @Override // android.content.Context
    public Context createPackageContextAsUser(String packageName, int flags, UserHandle user)
            throws PackageManager.NameNotFoundException {
        LoadedApk pi;
        if (packageName.equals("system") || packageName.equals("android")) {
            return new ContextImpl(
                    this,
                    this.mMainThread,
                    this.mPackageInfo,
                    this.mParams,
                    this.mAttributionSource.getAttributionTag(),
                    this.mAttributionSource.getNext(),
                    null,
                    this.mToken,
                    user,
                    flags,
                    null,
                    null,
                    this.mDeviceId,
                    this.mIsExplicitDeviceId);
        }
        if (packageName.equals(AsPackageName.SYSTEMUI)
                && SemDualAppManager.isDualAppId(user.getIdentifier())) {
            LoadedApk pi2 =
                    this.mMainThread.getPackageInfo(
                            packageName,
                            this.mResources.getCompatibilityInfo(),
                            flags | 1073741824,
                            0);
            pi = pi2;
        } else {
            LoadedApk pi3 =
                    this.mMainThread.getPackageInfo(
                            packageName,
                            this.mResources.getCompatibilityInfo(),
                            flags | 1073741824,
                            user.getIdentifier());
            pi = pi3;
        }
        if (pi != null) {
            ContextImpl c =
                    new ContextImpl(
                            this,
                            this.mMainThread,
                            pi,
                            this.mParams,
                            this.mAttributionSource.getAttributionTag(),
                            this.mAttributionSource.getNext(),
                            null,
                            this.mToken,
                            user,
                            flags,
                            null,
                            null,
                            this.mDeviceId,
                            this.mIsExplicitDeviceId);
            int displayId = getDisplayId();
            Integer overrideDisplayId =
                    this.mForceDisplayOverrideInResources ? Integer.valueOf(displayId) : null;
            c.setResources(
                    createResources(
                            this.mToken,
                            pi,
                            null,
                            overrideDisplayId,
                            null,
                            getDisplayAdjustments(displayId).getCompatibilityInfo(),
                            null));
            if (c.mResources != null) {
                return c;
            }
        }
        throw new PackageManager.NameNotFoundException(
                "Application package " + packageName + " not found");
    }

    @Override // android.content.Context
    public Context createContextAsUser(UserHandle user, int flags) {
        try {
            return createPackageContextAsUser(getPackageName(), flags, user);
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException(
                    "Own package not found for user "
                            + user.getIdentifier()
                            + ": package="
                            + getPackageName());
        }
    }

    @Override // android.content.Context
    public Context createContextForSplit(String splitName)
            throws PackageManager.NameNotFoundException {
        if (!this.mPackageInfo.getApplicationInfo().requestsIsolatedSplitLoading()) {
            return this;
        }
        ClassLoader classLoader = this.mPackageInfo.getSplitClassLoader(splitName);
        String[] paths = this.mPackageInfo.getSplitPaths(splitName);
        ContextImpl context =
                new ContextImpl(
                        this,
                        this.mMainThread,
                        this.mPackageInfo,
                        this.mParams,
                        this.mAttributionSource.getAttributionTag(),
                        this.mAttributionSource.getNext(),
                        splitName,
                        this.mToken,
                        this.mUser,
                        this.mFlags,
                        classLoader,
                        null,
                        this.mDeviceId,
                        this.mIsExplicitDeviceId);
        context.setResources(
                ResourcesManager.getInstance()
                        .getResources(
                                this.mToken,
                                this.mPackageInfo.getResDir(),
                                paths,
                                this.mPackageInfo.getOverlayDirs(),
                                this.mPackageInfo.getOverlayPaths(),
                                this.mPackageInfo.getApplicationInfo().sharedLibraryFiles,
                                this.mForceDisplayOverrideInResources
                                        ? Integer.valueOf(getDisplayId())
                                        : null,
                                null,
                                this.mPackageInfo.getCompatibilityInfo(),
                                classLoader,
                                this.mResources.getLoaders()));
        return context;
    }

    @Override // android.content.Context
    public Context createConfigurationContext(Configuration overrideConfiguration) {
        Configuration overrideConfiguration2;
        if (overrideConfiguration == null) {
            throw new IllegalArgumentException("overrideConfiguration must not be null");
        }
        if (!this.mForceDisplayOverrideInResources) {
            overrideConfiguration2 = overrideConfiguration;
        } else {
            Configuration displayAdjustedConfig = new Configuration();
            displayAdjustedConfig.setTo(
                    this.mDisplay.getDisplayAdjustments().getConfiguration(), 536870912, 1);
            displayAdjustedConfig.updateFrom(overrideConfiguration);
            overrideConfiguration2 = displayAdjustedConfig;
        }
        ContextImpl context =
                new ContextImpl(
                        this,
                        this.mMainThread,
                        this.mPackageInfo,
                        this.mParams,
                        this.mAttributionSource.getAttributionTag(),
                        this.mAttributionSource.getNext(),
                        this.mSplitName,
                        this.mToken,
                        this.mUser,
                        this.mFlags,
                        this.mClassLoader,
                        null,
                        this.mDeviceId,
                        this.mIsExplicitDeviceId);
        context.mIsConfigurationBasedContext = true;
        int displayId = getDisplayId();
        Integer overrideDisplayId =
                this.mForceDisplayOverrideInResources ? Integer.valueOf(displayId) : null;
        if (!this.mForceDisplayOverrideInResources
                && ResourcesManager.isOriginDisplayId(displayId)) {
            context.setResources(
                    createResources(
                            this.mToken,
                            this.mPackageInfo,
                            this.mSplitName,
                            overrideDisplayId,
                            overrideConfiguration2,
                            getDisplayAdjustments(displayId).getCompatibilityInfo(),
                            this.mResources.getLoaders(),
                            false,
                            true));
        } else {
            context.setResources(
                    createResources(
                            this.mToken,
                            this.mPackageInfo,
                            this.mSplitName,
                            overrideDisplayId,
                            overrideConfiguration2,
                            getDisplayAdjustments(displayId).getCompatibilityInfo(),
                            this.mResources.getLoaders()));
        }
        return context;
    }

    @Override // android.content.Context
    public Context createDisplayContext(Display display) {
        if (display == null) {
            throw new IllegalArgumentException("display must not be null");
        }
        ContextImpl context =
                new ContextImpl(
                        this,
                        this.mMainThread,
                        this.mPackageInfo,
                        this.mParams,
                        this.mAttributionSource.getAttributionTag(),
                        this.mAttributionSource.getNext(),
                        this.mSplitName,
                        this.mToken,
                        this.mUser,
                        this.mFlags,
                        this.mClassLoader,
                        null,
                        this.mDeviceId,
                        this.mIsExplicitDeviceId);
        int displayId = display.getDisplayId();
        Configuration overrideConfig = new Configuration();
        overrideConfig.setTo(display.getDisplayAdjustments().getConfiguration(), 536870912, 1);
        context.setResources(
                createResources(
                        this.mToken,
                        this.mPackageInfo,
                        this.mSplitName,
                        Integer.valueOf(displayId),
                        overrideConfig,
                        display.getDisplayAdjustments().getCompatibilityInfo(),
                        this.mResources.getLoaders(),
                        true,
                        false));
        context.setDisplay(display);
        context.mContextType = this.mContextType != 4 ? 1 : 4;
        context.mForceDisplayOverrideInResources = true;
        context.mIsConfigurationBasedContext = false;
        return context;
    }

    private void setDisplay(Display display) {
        this.mDisplay = display;
        if (display != null) {
            updateDeviceIdIfChanged(display.getDisplayId());
        }
    }

    @Override // android.content.Context
    public Context createDeviceContext(int deviceId) {
        VirtualDeviceManager vdm;
        if (deviceId != 0
                && ((vdm = (VirtualDeviceManager) getSystemService(VirtualDeviceManager.class))
                                == null
                        || !vdm.isValidVirtualDeviceId(deviceId))) {
            throw new IllegalArgumentException(
                    "Not a valid ID of the default device or any virtual device: " + deviceId);
        }
        return new ContextImpl(
                this,
                this.mMainThread,
                this.mPackageInfo,
                this.mParams,
                this.mAttributionSource.getAttributionTag(),
                this.mAttributionSource.getNext(),
                this.mSplitName,
                this.mToken,
                this.mUser,
                this.mFlags,
                this.mClassLoader,
                null,
                deviceId,
                true);
    }

    @Override // android.content.Context
    public WindowContext createWindowContext(int type, Bundle options) {
        if (getDisplay() == null) {
            throw new UnsupportedOperationException(
                    "Please call this API with context associated with a display instance, such as"
                        + " Activity or context created via Context#createDisplayContext(Display),"
                        + " or try to invoke Context#createWindowContext(Display, int, Bundle)");
        }
        return createWindowContextInternal(getDisplay(), type, options);
    }

    @Override // android.content.Context
    public WindowContext createWindowContext(Display display, int type, Bundle options) {
        if (display == null) {
            throw new IllegalArgumentException("Display must not be null");
        }
        return createWindowContextInternal(display, type, options);
    }

    private WindowContext createWindowContextInternal(Display display, int type, Bundle options) {
        WindowTokenClient windowTokenClient = new WindowTokenClient();
        ContextImpl windowContextBase =
                createWindowContextBase(windowTokenClient, display.getDisplayId());
        WindowContext windowContext = new WindowContext(windowContextBase, type, options);
        windowContextBase.setOuterContext(windowContext);
        windowTokenClient.attachContext(windowContext);
        windowContext.attachToDisplayArea();
        return windowContext;
    }

    @Override // android.content.Context
    public Context createTokenContext(IBinder token, Display display) {
        if (display == null) {
            throw new IllegalArgumentException("Display must not be null");
        }
        return createWindowContextBase(token, display.getDisplayId());
    }

    ContextImpl createWindowContextBase(IBinder token, int displayId) {
        ContextImpl baseContext =
                new ContextImpl(
                        this,
                        this.mMainThread,
                        this.mPackageInfo,
                        this.mParams,
                        this.mAttributionSource.getAttributionTag(),
                        this.mAttributionSource.getNext(),
                        this.mSplitName,
                        token,
                        this.mUser,
                        this.mFlags,
                        this.mClassLoader,
                        null,
                        this.mDeviceId,
                        this.mIsExplicitDeviceId);
        baseContext.mForceDisplayOverrideInResources = false;
        baseContext.mContextType = 3;
        Resources windowContextResources = createWindowContextResources(baseContext);
        baseContext.setResources(windowContextResources);
        baseContext.setDisplay(
                ResourcesManager.getInstance()
                        .getAdjustedDisplay(displayId, windowContextResources));
        return baseContext;
    }

    private static Resources createWindowContextResources(ContextImpl windowContextBase) {
        CompatibilityInfo compatInfo;
        LoadedApk packageInfo = windowContextBase.mPackageInfo;
        ClassLoader classLoader = windowContextBase.getClassLoader();
        IBinder token = windowContextBase.getWindowContextToken();
        String resDir = packageInfo.getResDir();
        String[] splitResDirs = packageInfo.getSplitResDirs();
        String[] legacyOverlayDirs = packageInfo.getOverlayDirs();
        String[] overlayPaths = packageInfo.getOverlayPaths();
        String[] libDirs = packageInfo.getApplicationInfo().sharedLibraryFiles;
        int displayId = windowContextBase.getDisplayId();
        if (displayId == 0) {
            compatInfo = packageInfo.getCompatibilityInfo();
        } else {
            compatInfo = CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
        }
        List<ResourcesLoader> loaders = windowContextBase.mResources.getLoaders();
        return windowContextBase.mResourcesManager.createBaseTokenResources(
                token,
                resDir,
                splitResDirs,
                legacyOverlayDirs,
                overlayPaths,
                libDirs,
                displayId,
                null,
                compatInfo,
                classLoader,
                loaders);
    }

    @Override // android.content.Context
    public Context createContext(ContextParams contextParams) {
        return new ContextImpl(
                this,
                this.mMainThread,
                this.mPackageInfo,
                contextParams,
                contextParams.getAttributionTag(),
                contextParams.getNextAttributionSource(),
                this.mSplitName,
                this.mToken,
                this.mUser,
                this.mFlags,
                this.mClassLoader,
                null,
                this.mDeviceId,
                this.mIsExplicitDeviceId);
    }

    @Override // android.content.Context
    public Context createAttributionContext(String attributionTag) {
        return createContext(
                new ContextParams.Builder(this.mParams).setAttributionTag(attributionTag).build());
    }

    @Override // android.content.Context
    public Context createDeviceProtectedStorageContext() {
        int flags = (this.mFlags & (-17)) | 8;
        return new ContextImpl(
                this,
                this.mMainThread,
                this.mPackageInfo,
                this.mParams,
                this.mAttributionSource.getAttributionTag(),
                this.mAttributionSource.getNext(),
                this.mSplitName,
                this.mToken,
                this.mUser,
                flags,
                this.mClassLoader,
                null,
                this.mDeviceId,
                this.mIsExplicitDeviceId);
    }

    @Override // android.content.Context
    public Context createCredentialProtectedStorageContext() {
        int flags = (this.mFlags & (-9)) | 16;
        return new ContextImpl(
                this,
                this.mMainThread,
                this.mPackageInfo,
                this.mParams,
                this.mAttributionSource.getAttributionTag(),
                this.mAttributionSource.getNext(),
                this.mSplitName,
                this.mToken,
                this.mUser,
                flags,
                this.mClassLoader,
                null,
                this.mDeviceId,
                this.mIsExplicitDeviceId);
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
            throw new UnsupportedOperationException(
                    "Tried to obtain display from a Context not associated with one. Only visual"
                        + " Contexts (such as Activity or one created with"
                        + " Context#createWindowContext) or ones created with"
                        + " Context#createDisplayContext are associated with displays. Other types"
                        + " of Contexts are typically related to background entities and may return"
                        + " an arbitrary display.");
        }
        return getDisplayNoVerify();
    }

    private boolean isAssociatedWithDisplay() {
        switch (this.mContextType) {
            case 1:
            case 2:
            case 3:
            case 4:
                return true;
            default:
                return false;
        }
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
        if (this.mDisplay == null) {
            return this.mResourcesManager.getAdjustedDisplay(0, this.mResources);
        }
        return this.mDisplay;
    }

    @Override // android.content.Context
    public int getDisplayId() {
        Display display = getDisplayNoVerify();
        if (display != null) {
            return display.getDisplayId();
        }
        return 0;
    }

    @Override // android.content.Context
    public void updateDisplay(int displayId) {
        if (ActivityThread.isFixedAppContextDisplay()) {
            Context appCtx = getApplicationContext();
            if (appCtx == null) {
                appCtx = this.mMainThread.getApplication();
            }
            if (this.mOuterContext.equals(appCtx) && displayId != 0) {
                throw new IllegalArgumentException("bad display id : " + displayId);
            }
        }
        setDisplay(this.mResourcesManager.getAdjustedDisplay(displayId, this.mResources));
        if (this.mContextType == 0) {
            this.mContextType = 1;
        }
    }

    private void updateDeviceIdIfChanged(int displayId) {
        VirtualDeviceManager vdm;
        int deviceId;
        if (this.mIsExplicitDeviceId) {
            return;
        }
        if (((displayId != 0 && displayId != -1) || this.mDeviceId != 0)
                && (vdm = (VirtualDeviceManager) getSystemService(VirtualDeviceManager.class))
                        != null
                && (deviceId = vdm.getDeviceIdForDisplayId(displayId)) != this.mDeviceId) {
            this.mDeviceId = deviceId;
            this.mAttributionSource =
                    createAttributionSourceWithDeviceId(this.mAttributionSource, this.mDeviceId);
            notifyOnDeviceChangedListeners(this.mDeviceId);
        }
    }

    @Override // android.content.Context
    public void updateDeviceId(int updatedDeviceId) {
        if (updatedDeviceId != 0) {
            VirtualDeviceManager vdm =
                    (VirtualDeviceManager) getSystemService(VirtualDeviceManager.class);
            if (!vdm.isValidVirtualDeviceId(updatedDeviceId)) {
                throw new IllegalArgumentException(
                        "Not a valid ID of the default device or any virtual device: "
                                + updatedDeviceId);
            }
        }
        if (this.mIsExplicitDeviceId) {
            throw new UnsupportedOperationException(
                    "Cannot update device ID on a Context created with createDeviceContext()");
        }
        if (this.mDeviceId != updatedDeviceId) {
            this.mDeviceId = updatedDeviceId;
            this.mAttributionSource =
                    createAttributionSourceWithDeviceId(this.mAttributionSource, this.mDeviceId);
            notifyOnDeviceChangedListeners(updatedDeviceId);
        }
    }

    @Override // android.content.Context
    public int getDeviceId() {
        return this.mDeviceId;
    }

    @Override // android.content.Context
    public void registerDeviceIdChangeListener(Executor executor, IntConsumer listener) {
        Objects.requireNonNull(executor, "executor cannot be null");
        Objects.requireNonNull(listener, "listener cannot be null");
        synchronized (this.mDeviceIdListenerLock) {
            if (getDeviceIdListener(listener) != null) {
                throw new IllegalArgumentException(
                        "attempt to call registerDeviceIdChangeListener() on a previously"
                                + " registered listener");
            }
            if (this.mDeviceIdChangeListeners == null) {
                this.mDeviceIdChangeListeners = new ArrayList<>();
            }
            this.mDeviceIdChangeListeners.add(
                    new DeviceIdChangeListenerDelegate(listener, executor));
        }
    }

    @Override // android.content.Context
    public void unregisterDeviceIdChangeListener(IntConsumer listener) {
        Objects.requireNonNull(listener, "listener cannot be null");
        synchronized (this.mDeviceIdListenerLock) {
            DeviceIdChangeListenerDelegate listenerToRemove = getDeviceIdListener(listener);
            if (listenerToRemove != null) {
                this.mDeviceIdChangeListeners.remove(listenerToRemove);
            }
        }
    }

    private DeviceIdChangeListenerDelegate getDeviceIdListener(IntConsumer listener) {
        if (this.mDeviceIdChangeListeners == null) {
            return null;
        }
        for (int i = 0; i < this.mDeviceIdChangeListeners.size(); i++) {
            DeviceIdChangeListenerDelegate delegate = this.mDeviceIdChangeListeners.get(i);
            if (delegate.mListener == listener) {
                return delegate;
            }
        }
        return null;
    }

    private void notifyOnDeviceChangedListeners(final int deviceId) {
        synchronized (this.mDeviceIdListenerLock) {
            if (this.mDeviceIdChangeListeners != null) {
                Iterator<DeviceIdChangeListenerDelegate> it =
                        this.mDeviceIdChangeListeners.iterator();
                while (it.hasNext()) {
                    final DeviceIdChangeListenerDelegate delegate = it.next();
                    delegate.mExecutor.execute(
                            new Runnable() { // from class:
                                // android.app.ContextImpl$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ContextImpl.DeviceIdChangeListenerDelegate.this.mListener
                                            .accept(deviceId);
                                }
                            });
                }
            }
        }
    }

    @Override // android.content.Context
    public DisplayAdjustments getDisplayAdjustments(int displayId) {
        return this.mResources.getDisplayAdjustments();
    }

    @Override // android.content.Context
    public File getDataDir() {
        File res;
        if (this.mPackageInfo != null) {
            if (isCredentialProtectedStorage()) {
                res = this.mPackageInfo.getCredentialProtectedDataDirFile();
            } else if (isDeviceProtectedStorage()) {
                res = this.mPackageInfo.getDeviceProtectedDataDirFile();
            } else {
                res = this.mPackageInfo.getDataDirFile();
            }
            if (res != null) {
                if (!res.exists() && Process.myUid() == 1000) {
                    Log.wtf(
                            TAG,
                            "Data directory doesn't exist for package " + getPackageName(),
                            new Throwable());
                }
                return res;
            }
            throw new RuntimeException("No data directory found for package " + getPackageName());
        }
        throw new RuntimeException("No package details found for package " + getPackageName());
    }

    @Override // android.content.Context
    public File getDir(String name, int mode) {
        checkMode(mode);
        File file = makeFilename(getDataDir(), "app_" + name);
        if (!file.exists()) {
            file.mkdir();
            setFilePermissionsFromMode(file.getPath(), mode, 505);
        }
        return file;
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
    public void setAutofillClient(AutofillManager.AutofillClient client) {
        this.mAutofillClient = client;
    }

    @Override // android.content.Context
    public AutofillOptions getAutofillOptions() {
        return this.mAutofillOptions;
    }

    @Override // android.content.Context
    public void setAutofillOptions(AutofillOptions options) {
        this.mAutofillOptions = options;
    }

    @Override // android.content.Context
    public ContentCaptureOptions getContentCaptureOptions() {
        return this.mContentCaptureOptions;
    }

    @Override // android.content.Context
    public void setContentCaptureOptions(ContentCaptureOptions options) {
        this.mContentCaptureOptions = options;
    }

    protected void finalize() throws Throwable {
        if ((this.mToken instanceof WindowTokenClient) && this.mOwnsToken) {
            WindowTokenClientController.getInstance()
                    .detachIfNeeded((WindowTokenClient) this.mToken);
        }
        super.finalize();
    }

    static ContextImpl createSystemContext(ActivityThread mainThread) {
        LoadedApk packageInfo = new LoadedApk(mainThread);
        ContextImpl context =
                new ContextImpl(
                        null,
                        mainThread,
                        packageInfo,
                        ContextParams.EMPTY,
                        null,
                        null,
                        null,
                        null,
                        null,
                        0,
                        null,
                        null,
                        0,
                        false);
        context.setResources(packageInfo.getResources());
        context.mResources.updateConfiguration(
                context.mResourcesManager.getConfiguration(),
                context.mResourcesManager.getDisplayMetrics());
        context.mContextType = 4;
        return context;
    }

    static ContextImpl createSystemUiContext(ContextImpl systemContext, int displayId) {
        WindowTokenClient token = new WindowTokenClient();
        ContextImpl context = systemContext.createWindowContextBase(token, displayId);
        token.attachContext(context);
        WindowTokenClientController.getInstance().attachToDisplayContent(token, displayId);
        context.mContextType = 4;
        context.mOwnsToken = true;
        return context;
    }

    static ContextImpl createAppContext(ActivityThread mainThread, LoadedApk packageInfo) {
        return createAppContext(mainThread, packageInfo, null);
    }

    static ContextImpl createAppContext(
            ActivityThread mainThread, LoadedApk packageInfo, String opPackageName) {
        if (packageInfo == null) {
            throw new IllegalArgumentException("packageInfo");
        }
        ContextImpl context =
                new ContextImpl(
                        null,
                        mainThread,
                        packageInfo,
                        ContextParams.EMPTY,
                        null,
                        null,
                        null,
                        null,
                        null,
                        0,
                        null,
                        opPackageName,
                        0,
                        false);
        context.setResources(packageInfo.getResources());
        context.mContextType = isSystemOrSystemUI(context) ? 4 : 0;
        return context;
    }

    static ContextImpl createActivityContext(
            ActivityThread mainThread,
            LoadedApk packageInfo,
            ActivityInfo activityInfo,
            IBinder activityToken,
            int displayId,
            Configuration overrideConfiguration) {
        String[] splitDirs;
        ClassLoader classLoader;
        String attributionTag;
        CompatibilityInfo compatInfo;
        if (packageInfo == null) {
            throw new IllegalArgumentException("packageInfo");
        }
        String[] splitDirs2 = packageInfo.getSplitResDirs();
        ClassLoader classLoader2 = packageInfo.getClassLoader();
        if (!packageInfo.getApplicationInfo().requestsIsolatedSplitLoading()) {
            splitDirs = splitDirs2;
            classLoader = classLoader2;
        } else {
            Trace.traceBegin(8192L, "SplitDependencies");
            try {
                try {
                    ClassLoader classLoader3 =
                            packageInfo.getSplitClassLoader(activityInfo.splitName);
                    splitDirs = packageInfo.getSplitPaths(activityInfo.splitName);
                    Trace.traceEnd(8192L);
                    classLoader = classLoader3;
                } catch (PackageManager.NameNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                Trace.traceEnd(8192L);
                throw th;
            }
        }
        String[] splitDirs3 = activityInfo.attributionTags;
        if (splitDirs3 != null && activityInfo.attributionTags.length > 0) {
            attributionTag = activityInfo.attributionTags[0];
        } else {
            attributionTag = null;
        }
        ContextImpl context =
                new ContextImpl(
                        null,
                        mainThread,
                        packageInfo,
                        ContextParams.EMPTY,
                        attributionTag,
                        null,
                        activityInfo.splitName,
                        activityToken,
                        null,
                        0,
                        classLoader,
                        null,
                        0,
                        false);
        context.mContextType = 2;
        context.mIsConfigurationBasedContext = true;
        int displayId2 = displayId != -1 ? displayId : 0;
        if (displayId2 == 0) {
            compatInfo = packageInfo.getCompatibilityInfo();
        } else {
            compatInfo = CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
        }
        ResourcesManager resourcesManager = ResourcesManager.getInstance();
        context.setResources(
                resourcesManager.createBaseTokenResources(
                        activityToken,
                        packageInfo.getResDir(),
                        splitDirs,
                        packageInfo.getOverlayDirs(),
                        packageInfo.getOverlayPaths(),
                        packageInfo.getApplicationInfo().sharedLibraryFiles,
                        displayId2,
                        overrideConfiguration,
                        compatInfo,
                        classLoader,
                        packageInfo.getApplication() == null
                                ? null
                                : packageInfo.getApplication().getResources().getLoaders(),
                        displayId2));
        context.setDisplay(resourcesManager.getAdjustedDisplay(displayId2, context.getResources()));
        return context;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x00ae  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private ContextImpl(
            android.app.ContextImpl r19,
            android.app.ActivityThread r20,
            android.app.LoadedApk r21,
            android.content.ContextParams r22,
            java.lang.String r23,
            android.content.AttributionSource r24,
            java.lang.String r25,
            android.os.IBinder r26,
            android.os.UserHandle r27,
            int r28,
            java.lang.ClassLoader r29,
            java.lang.String r30,
            int r31,
            boolean r32) {
        /*
            Method dump skipped, instructions count: 341
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.app.ContextImpl.<init>(android.app.ContextImpl,"
                    + " android.app.ActivityThread, android.app.LoadedApk,"
                    + " android.content.ContextParams, java.lang.String,"
                    + " android.content.AttributionSource, java.lang.String, android.os.IBinder,"
                    + " android.os.UserHandle, int, java.lang.ClassLoader, java.lang.String, int,"
                    + " boolean):void");
    }

    private AttributionSource createAttributionSource(
            String attributionTag,
            AttributionSource nextAttributionSource,
            Set<String> renouncedPermissions,
            boolean shouldRegister,
            int deviceId) {
        AttributionSource attributionSource =
                new AttributionSource(
                        Process.myUid(),
                        Process.myPid(),
                        this.mOpPackageName,
                        attributionTag,
                        renouncedPermissions != null
                                ? (String[]) renouncedPermissions.toArray(new String[0])
                                : null,
                        deviceId,
                        nextAttributionSource);
        return registerAttributionSourceIfNeeded(attributionSource, shouldRegister);
    }

    private AttributionSource createAttributionSourceWithDeviceId(
            AttributionSource oldSource, int deviceId) {
        boolean shouldRegister = false;
        if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags
                .shouldRegisterAttributionSource()) {
            shouldRegister = this.mParams.shouldRegisterAttributionSource();
        }
        return registerAttributionSourceIfNeeded(oldSource.withDeviceId(deviceId), shouldRegister);
    }

    private AttributionSource registerAttributionSourceIfNeeded(
            AttributionSource attributionSource, boolean shouldRegister) {
        if (shouldRegister || attributionSource.getNext() != null) {
            return ((PermissionManager) getSystemService(PermissionManager.class))
                    .registerAttributionSource(attributionSource);
        }
        return attributionSource;
    }

    void setResources(Resources r) {
        if (r instanceof CompatResources) {
            ((CompatResources) r).setContext(this);
        }
        this.mResources = r;
        if (r != null
                && android.content.res.Flags.defaultLocale()
                && r.getConfiguration().getLocales().size() > 1) {
            LocaleConfig lc = LocaleConfig.fromContextIgnoringOverride(this);
            this.mResourcesManager.setLocaleConfig(lc);
        }
    }

    void installSystemApplicationInfo(ApplicationInfo info, ClassLoader classLoader) {
        this.mPackageInfo.installSystemApplicationInfo(info, classLoader);
    }

    final void scheduleFinalCleanup(String who, String what) {
        this.mMainThread.scheduleContextCleanup(this, who, what);
    }

    final void performFinalCleanup(String who, String what) {
        this.mPackageInfo.removeContextRegistrations(getOuterContext(), who, what);
        if (this.mContextType == 4 && (this.mToken instanceof WindowTokenClient)) {
            this.mMainThread.onSystemUiContextCleanup(this);
        }
    }

    final Context getReceiverRestrictedContext() {
        if (this.mReceiverRestrictedContext != null) {
            return this.mReceiverRestrictedContext;
        }
        ReceiverRestrictedContext receiverRestrictedContext =
                new ReceiverRestrictedContext(getOuterContext());
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
        switch (this.mContextType) {
            case 3:
            case 4:
                return this.mToken;
            default:
                return null;
        }
    }

    private void checkMode(int mode) {
        if (getApplicationInfo().targetSdkVersion >= 24) {
            if ((mode & 1) != 0) {
                throw new SecurityException("MODE_WORLD_READABLE no longer supported");
            }
            if ((mode & 2) != 0) {
                throw new SecurityException("MODE_WORLD_WRITEABLE no longer supported");
            }
        }
    }

    static void setFilePermissionsFromMode(String name, int mode, int extraPermissions) {
        int perms = extraPermissions | 432;
        if ((mode & 1) != 0) {
            perms |= 4;
        }
        if ((mode & 2) != 0) {
            perms |= 2;
        }
        FileUtils.setPermissions(name, perms, -1, -1);
    }

    private File makeFilename(File base, String name) {
        if (name.indexOf(File.separatorChar) < 0) {
            File res = new File(base, name);
            BlockGuard.getVmPolicy().onPathAccess(res.getPath());
            return res;
        }
        throw new IllegalArgumentException("File " + name + " contains a path separator");
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x001d, code lost:

       if (r3.mkdirs() == false) goto L10;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.io.File[] ensureExternalDirsExistOrFilter(java.io.File[] r8, boolean r9) {
        /*
            r7 = this;
            java.lang.Class<android.os.storage.StorageManager> r0 = android.os.storage.StorageManager.class
            java.lang.Object r0 = r7.getSystemService(r0)
            android.os.storage.StorageManager r0 = (android.os.storage.StorageManager) r0
            int r1 = r8.length
            java.io.File[] r1 = new java.io.File[r1]
            r2 = 0
        Lc:
            int r3 = r8.length
            if (r2 >= r3) goto L5d
            r3 = r8[r2]
            boolean r4 = r3.exists()
            if (r4 != 0) goto L4d
            if (r9 == 0) goto L1f
            boolean r4 = r3.mkdirs()     // Catch: java.lang.Exception -> L29
            if (r4 != 0) goto L28
        L1f:
            boolean r4 = r3.exists()     // Catch: java.lang.Exception -> L29
            if (r4 != 0) goto L28
            r0.mkdirs(r3)     // Catch: java.lang.Exception -> L29
        L28:
            goto L4d
        L29:
            r4 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Failed to ensure "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r3)
            java.lang.String r6 = ": "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r4)
            java.lang.String r5 = r5.toString()
            java.lang.String r6 = "ContextImpl"
            android.util.Log.w(r6, r5)
            r3 = 0
        L4d:
            if (r3 == 0) goto L58
            boolean r4 = r3.canWrite()
            if (r4 != 0) goto L58
            r0.fixupAppDir(r3)
        L58:
            r1[r2] = r3
            int r2 = r2 + 1
            goto Lc
        L5d:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                        + " android.app.ContextImpl.ensureExternalDirsExistOrFilter(java.io.File[],"
                        + " boolean):java.io.File[]");
    }

    @Override // android.content.Context
    public void destroy() {
        scheduleFinalCleanup(getClass().getName(), getOuterContext().getClass().getSimpleName());
    }

    @Override // android.content.Context
    public void closeSystemDialogs() {
        Intent intent = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS).addFlags(268435456);
        Bundle options =
                BroadcastOptions.makeBasic()
                        .setDeliveryGroupPolicy(1)
                        .setDeferralPolicy(2)
                        .toBundle();
        sendBroadcast(intent, (String) null, options);
    }

    private static final class ApplicationContentResolver extends ContentResolver {
        private final ActivityThread mMainThread;

        public ApplicationContentResolver(Context context, ActivityThread mainThread) {
            super(context);
            this.mMainThread = (ActivityThread) Objects.requireNonNull(mainThread);
        }

        @Override // android.content.ContentResolver
        protected IContentProvider acquireProvider(Context context, String auth) {
            return this.mMainThread.acquireProvider(
                    context,
                    ContentProvider.getAuthorityWithoutUserId(auth),
                    resolveUserIdFromAuthority(auth),
                    true);
        }

        @Override // android.content.ContentResolver
        protected IContentProvider acquireExistingProvider(Context context, String auth) {
            return this.mMainThread.acquireExistingProvider(
                    context,
                    ContentProvider.getAuthorityWithoutUserId(auth),
                    resolveUserIdFromAuthority(auth),
                    true);
        }

        @Override // android.content.ContentResolver
        public boolean releaseProvider(IContentProvider provider) {
            return this.mMainThread.releaseProvider(provider, true);
        }

        @Override // android.content.ContentResolver
        protected IContentProvider acquireUnstableProvider(Context c, String auth) {
            return this.mMainThread.acquireProvider(
                    c,
                    ContentProvider.getAuthorityWithoutUserId(auth),
                    resolveUserIdFromAuthority(auth),
                    false);
        }

        @Override // android.content.ContentResolver
        public boolean releaseUnstableProvider(IContentProvider icp) {
            return this.mMainThread.releaseProvider(icp, false);
        }

        @Override // android.content.ContentResolver
        public void unstableProviderDied(IContentProvider icp) {
            this.mMainThread.handleUnstableProviderDied(icp.asBinder(), true);
        }

        @Override // android.content.ContentResolver
        public void appNotRespondingViaProvider(IContentProvider icp) {
            this.mMainThread.appNotRespondingViaProvider(icp.asBinder());
        }

        protected int resolveUserIdFromAuthority(String auth) {
            return ContentProvider.getUserIdFromAuthority(auth, getUserId());
        }
    }
}
