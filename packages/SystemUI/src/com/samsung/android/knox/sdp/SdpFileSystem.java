package com.samsung.android.knox.sdp;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.Environment;
import android.os.FileUtils;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.sdp.core.SdpEngineInfo;
import com.samsung.android.knox.sdp.core.SdpException;
import java.io.File;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SdpFileSystem {
    private static final String BASE_USER_DATA_DIR = "/data/enc_user";
    private static final String BASE_USER_SDCARD_DIR = "/storage/enc_emulated";
    private static final String CLASS_NAME = "SdpFileSystem";
    private static final String ENC_EMUL_LOWERFS_DIR = "/mnt/user";
    private static final String FUSE_LOWERFS_DIR = "/data/media";
    private static final String FUSE_MOUNTED_DIR = "/storage/emulated";
    private static final String LEGACY_SDCARD_DIR = "/storage/emulated";
    private static final String LEGACY_USER_DATA_DIR = "/data/user";
    private static final String STORAGE_DIR = "/storage";
    private static final String TAG = "SdpFileSystem";
    private static IDarManagerService sService;
    private String mAlias;
    private Context mContext;
    private final ContextInfo mContextInfo;
    private int mEngineId;
    private int mUserId;
    private final Object mSync = new Object();
    private File mDatabasesDir = null;
    private File mFilesDir = null;
    private File mCacheDir = null;
    private File mEmulatedDir = null;

    static {
        try {
            System.loadLibrary("sdp_sdk");
        } catch (Error unused) {
        }
    }

    public SdpFileSystem(Context context, String str) throws SdpException {
        this.mEngineId = -1;
        this.mUserId = -1;
        enforcePermission();
        this.mAlias = str;
        this.mContext = context;
        this.mContextInfo = new ContextInfo(Binder.getCallingUid());
        SdpEngineInfo engineInfo = getEngineInfo(this.mAlias);
        if (engineInfo == null) {
            throw new SdpException(-5);
        }
        this.mEngineId = engineInfo.getId();
        this.mUserId = context.getUserId();
        Log.e("SdpFileSystem", "SdpFileSystem created engine:" + this.mEngineId + " user:" + this.mUserId);
    }

    private static native int Native_Sdp_IsSensitiveFile(String str);

    private static native int Native_Sdp_SetSensitiveFile(int i, String str);

    private static native boolean Native_Sdp_TestSdpIoctl();

    private static File createDirLocked(File file) {
        if (!file.exists()) {
            if (file.mkdirs()) {
                FileUtils.setPermissions(file.getPath(), 505, -1, -1);
            } else if (!file.exists()) {
                Log.e("SdpFileSystem", "Unable to create files subdir " + file.getPath());
                return null;
            }
        }
        return file;
    }

    private static void enforcePermission() throws SdpException {
        int i;
        try {
            i = getSdpService().isLicensed();
        } catch (RemoteException e) {
            Log.e("SdpFileSystem", "Failed to talk with sdp service...", e);
            i = -99;
        }
        if (i != 0) {
            throw new SdpException(-9);
        }
    }

    private File getDatabasesDir() {
        synchronized (this.mSync) {
            try {
                if (this.mDatabasesDir == null) {
                    File encDataDirFile = getEncDataDirFile();
                    if (encDataDirFile == null) {
                        Log.e("SdpFileSystem", "Failed to get enc-package dir " + this.mUserId + " , " + this.mAlias);
                        return null;
                    }
                    this.mDatabasesDir = new File(encDataDirFile, "databases");
                }
                return this.mDatabasesDir;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private File getEncDataDirFile() {
        Context context = this.mContext;
        if (context == null || this.mUserId < 0) {
            Log.e("SdpFileSystem", "getEncPackageDir :: invalid object");
            return null;
        }
        String packageName = context.getPackageName();
        File file = new File("/data/enc_user/" + this.mUserId + "/" + packageName);
        if (!file.exists()) {
            try {
                Log.d("SdpFileSystem", "getFilesDir callihng createEncPkgDir " + this.mUserId + " " + packageName);
                int createEncPkgDir = sService.createEncPkgDir(this.mUserId, packageName);
                StringBuilder sb = new StringBuilder("getFilesDir done createEncPkgDir result ");
                sb.append(createEncPkgDir);
                Log.d("SdpFileSystem", sb.toString());
                if (createEncPkgDir != 0 || !file.exists()) {
                    return null;
                }
            } catch (RemoteException e) {
                Log.e("SdpFileSystem", "RemoteException from call unregisterListener", e);
                return null;
            }
        }
        return file;
    }

    private SdpEngineInfo getEngineInfo(String str) {
        try {
            return sService.getEngineInfo(str);
        } catch (RemoteException e) {
            Log.e("SdpFileSystem", "Failed to talk with sdp service...", e);
            return null;
        }
    }

    public static File getExternalStorageDirectory(int i) {
        try {
            enforcePermission();
            if (isDefaultPathUser(i)) {
                return new File("/storage/emulated/" + Integer.toString(i));
            }
            return new File("/storage/enc_emulated/" + Integer.toString(i));
        } catch (SdpException e) {
            e.printStackTrace();
            return null;
        }
    }

    private File getManagedProfileKnoxDir(int i) {
        File file;
        if (isDefaultPathUser(i)) {
            Log.e("SdpFileSystem", "getManagedProfileKnoxDir :: Not applicable to user " + this.mUserId);
            return null;
        }
        synchronized (this.mSync) {
            try {
                String str = "/storage/emulated/" + Integer.toString(i) + "/Knox";
                Log.i("SdpFileSystem", "getManagedProfileKnoxDir :: Protected knox path : " + str);
                file = new File(str);
                if (file.exists()) {
                    Log.i("SdpFileSystem", "getManagedProfileKnoxDir :: The knox path exists");
                } else {
                    Log.e("SdpFileSystem", "getManagedProfileKnoxDir :: The knox path does not exist for user " + i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return file;
    }

    private static synchronized IDarManagerService getSdpService() throws SdpException {
        IDarManagerService iDarManagerService;
        synchronized (SdpFileSystem.class) {
            try {
                sService = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
            } catch (Exception e) {
                Log.e("SdpFileSystem", "Failed to talk with sdp service...", e);
            }
            iDarManagerService = sService;
            if (iDarManagerService == null) {
                throw new SdpException(-13);
            }
        }
        return iDarManagerService;
    }

    public static File getUserDataDir(int i, String str) {
        try {
            enforcePermission();
            if (str == null || str.isEmpty()) {
                return null;
            }
            if (isDefaultPathUser(i)) {
                return new File("/data/user/" + Integer.toString(i), str);
            }
            return new File("/data/enc_user/" + Integer.toString(i), str);
        } catch (SdpException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isDefaultPathUser(int i) {
        try {
            return sService.isDefaultPathUser(i);
        } catch (RemoteException e) {
            Log.e("SdpFileSystem", "Failed to talk with sdp service...", e);
            return false;
        }
    }

    private File makeFilename(File file, String str) {
        if (str.indexOf(File.separatorChar) < 0) {
            return new File(file, str);
        }
        throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("File ", str, " contains a path separator"));
    }

    public static void setFilePermissionsFromMode(String str, int i, int i2) {
        int i3 = i2 | 432;
        if ((i & 1) != 0) {
            i3 = i2 | 436;
        }
        if ((i & 2) != 0) {
            i3 |= 2;
        }
        FileUtils.setPermissions(str, i3, -1, -1);
    }

    public static boolean testSdpIoctl() {
        boolean Native_Sdp_TestSdpIoctl = Native_Sdp_TestSdpIoctl();
        Log.d("SdpFileSystem", "Test SDP IOCTL :: ".concat(Native_Sdp_TestSdpIoctl ? "Success" : "Failed..."));
        return Native_Sdp_TestSdpIoctl;
    }

    private File validateFilePath(String str, boolean z) {
        File databasesDir;
        File makeFilename;
        char charAt = str.charAt(0);
        char c = File.separatorChar;
        if (charAt == c) {
            databasesDir = new File(str.substring(0, str.lastIndexOf(c)));
            makeFilename = new File(databasesDir, str.substring(str.lastIndexOf(c)));
        } else {
            databasesDir = getDatabasesDir();
            if (databasesDir == null) {
                return null;
            }
            makeFilename = makeFilename(databasesDir, str);
        }
        if (z && !databasesDir.isDirectory() && databasesDir.mkdir()) {
            FileUtils.setPermissions(databasesDir.getPath(), 505, -1, -1);
        }
        return makeFilename;
    }

    public File getCacheDir() {
        if (isDefaultPathUser(this.mUserId)) {
            return this.mContext.getCacheDir();
        }
        synchronized (this.mSync) {
            try {
                if (this.mCacheDir == null) {
                    File encDataDirFile = getEncDataDirFile();
                    if (encDataDirFile == null) {
                        Log.e("SdpFileSystem", "Failed to get enc-package dir " + this.mUserId + " , " + this.mAlias);
                        return null;
                    }
                    this.mCacheDir = new File(encDataDirFile, "cache");
                }
                return createDirLocked(this.mCacheDir);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public File getDatabasePath(String str) {
        return isDefaultPathUser(this.mUserId) ? this.mContext.getDatabasePath(str) : validateFilePath(str, false);
    }

    public File getFilesDir() {
        if (isDefaultPathUser(this.mUserId)) {
            return this.mContext.getFilesDir();
        }
        synchronized (this.mSync) {
            try {
                if (this.mFilesDir == null) {
                    File encDataDirFile = getEncDataDirFile();
                    if (encDataDirFile == null) {
                        Log.e("SdpFileSystem", "Failed to get enc-package dir " + this.mUserId + " , " + this.mAlias);
                        return null;
                    }
                    this.mFilesDir = new File(encDataDirFile, "files");
                }
                return createDirLocked(this.mFilesDir);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean isCryptFsMounted() {
        return false;
    }

    public boolean isSensitive(File file) {
        String replace;
        if (file == null) {
            return false;
        }
        String absolutePath = file.getAbsolutePath();
        boolean startsWith = absolutePath.startsWith("/storage/emulated");
        boolean startsWith2 = absolutePath.startsWith(BASE_USER_SDCARD_DIR);
        if (!startsWith && !startsWith2) {
            try {
            } catch (Exception unused) {
                Log.e("SdpFileSystem", "Error- Exception in setting Policy");
            }
            return Native_Sdp_IsSensitiveFile(absolutePath) == 1;
        }
        if (startsWith) {
            replace = absolutePath.replace("/storage/emulated", FUSE_LOWERFS_DIR);
        } else {
            replace = absolutePath.replace(STORAGE_DIR, "/mnt/user/" + this.mUserId);
        }
        String replace2 = replace.replace("/storage/emulated", FUSE_LOWERFS_DIR);
        IDarManagerService asInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        try {
        } catch (RemoteException e) {
            Log.e("SdpFileSystem", "Failed to talk with sdp service...", e);
        }
        if (asInterface != null) {
            return asInterface.isSensitive(replace2);
        }
        Log.e("SdpFileSystem", "Service not found");
        return false;
    }

    public SQLiteDatabase openOrCreateDatabase(String str, int i, SQLiteDatabase.CursorFactory cursorFactory) {
        return openOrCreateDatabase(str, i, cursorFactory, null);
    }

    public boolean setSensitive(File file) {
        String replace;
        EnterpriseLicenseManager.log(this.mContextInfo, "SdpFileSystem.setSensitive");
        if (file == null) {
            return false;
        }
        String absolutePath = file.getAbsolutePath();
        boolean startsWith = absolutePath.startsWith("/storage/emulated");
        boolean startsWith2 = absolutePath.startsWith(BASE_USER_SDCARD_DIR);
        if (!startsWith && !startsWith2) {
            try {
                if (Native_Sdp_SetSensitiveFile(this.mEngineId, absolutePath) != 0) {
                    return true;
                }
                Log.e("SdpFileSystem", "Error to handle SDP_SetSensitiveFile : ".concat(absolutePath));
                return false;
            } catch (Exception unused) {
                Log.e("SdpFileSystem", "Error- Exception in setting Policy");
                return false;
            }
        }
        if (startsWith) {
            replace = absolutePath.replace("/storage/emulated", FUSE_LOWERFS_DIR);
        } else {
            replace = absolutePath.replace(STORAGE_DIR, "/mnt/user/" + this.mUserId);
        }
        IDarManagerService asInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        try {
        } catch (RemoteException e) {
            Log.e("SdpFileSystem", "Failed to talk with sdp service...", e);
        }
        if (asInterface != null) {
            return asInterface.setSensitive(this.mEngineId, replace);
        }
        Log.e("SdpFileSystem", "Service not found");
        return false;
    }

    public boolean isCryptFsMounted(int i) {
        return false;
    }

    public SQLiteDatabase openOrCreateDatabase(String str, int i, SQLiteDatabase.CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler) {
        if (isDefaultPathUser(this.mUserId)) {
            return this.mContext.openOrCreateDatabase(str, i, cursorFactory, databaseErrorHandler);
        }
        File validateFilePath = validateFilePath(str, true);
        int i2 = (i & 8) != 0 ? 805306368 : 268435456;
        if (validateFilePath == null) {
            return null;
        }
        SQLiteDatabase openDatabase = SQLiteDatabase.openDatabase(validateFilePath.getPath(), cursorFactory, i2, databaseErrorHandler);
        setFilePermissionsFromMode(validateFilePath.getPath(), i, 0);
        return openDatabase;
    }

    public File getExternalStorageDirectory() {
        File createDirLocked;
        synchronized (this.mSync) {
            try {
                if (isDefaultPathUser(this.mUserId)) {
                    if (this.mEmulatedDir == null) {
                        this.mEmulatedDir = Environment.getExternalStorageDirectory();
                    }
                } else if (this.mEmulatedDir == null) {
                    this.mEmulatedDir = new File("/storage/enc_emulated/" + this.mUserId);
                }
                createDirLocked = createDirLocked(this.mEmulatedDir);
            } catch (Throwable th) {
                throw th;
            }
        }
        return createDirLocked;
    }

    public File getUserDataDir() {
        if (isDefaultPathUser(this.mUserId)) {
            String str = this.mContext.getApplicationInfo().dataDir;
            if (str != null) {
                return new File(str);
            }
            return null;
        }
        synchronized (this.mSync) {
            try {
                File encDataDirFile = getEncDataDirFile();
                if (encDataDirFile != null) {
                    return encDataDirFile;
                }
                Log.e("SdpFileSystem", "Failed to get enc-package dir " + this.mUserId + " , " + this.mAlias);
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public File getManagedProfileKnoxDir() {
        Log.e("SdpFileSystem", "getManagedProfileKnoxDir calling for user " + this.mUserId);
        return getManagedProfileKnoxDir(this.mUserId);
    }
}
