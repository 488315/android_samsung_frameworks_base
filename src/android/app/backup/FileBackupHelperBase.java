package android.app.backup;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;

/* loaded from: classes.dex */
class FileBackupHelperBase {
    private static final String TAG = "FileBackupHelperBase";
    Context mContext;
    boolean mExceptionLogged;
    long mPtr = ctor();

    private static native long ctor();

    private static native void dtor(long j);

    private static native int performBackup_native(FileDescriptor fileDescriptor, long j, FileDescriptor fileDescriptor2, String[] strArr, String[] strArr2);

    private static native int writeFile_native(long j, String str, long j2);

    private static native int writeSnapshot_native(long j, FileDescriptor fileDescriptor);

    FileBackupHelperBase(Context context) {
        this.mContext = context;
    }

    protected void finalize() throws Throwable {
        try {
            dtor(this.mPtr);
        } finally {
            super.finalize();
        }
    }

    static void performBackup_checked(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2, String[] strArr, String[] strArr2) {
        if (strArr.length == 0) {
            return;
        }
        for (String str : strArr) {
            if (str.charAt(0) != '/') {
                throw new RuntimeException("files must have all absolute paths: " + str);
            }
        }
        if (strArr.length != strArr2.length) {
            throw new RuntimeException("files.length=" + strArr.length + " keys.length=" + strArr2.length);
        }
        FileDescriptor fileDescriptor = parcelFileDescriptor != null ? parcelFileDescriptor.getFileDescriptor() : null;
        FileDescriptor fileDescriptor2 = parcelFileDescriptor2.getFileDescriptor();
        fileDescriptor2.getClass();
        int performBackup_native = performBackup_native(fileDescriptor, backupDataOutput.mBackupWriter, fileDescriptor2, strArr, strArr2);
        if (performBackup_native == 0) {
            return;
        }
        throw new RuntimeException("Backup failed 0x" + Integer.toHexString(performBackup_native));
    }

    boolean writeFile(File file, BackupDataInputStream backupDataInputStream) {
        file.getParentFile().mkdirs();
        int writeFile_native = writeFile_native(this.mPtr, file.getAbsolutePath(), backupDataInputStream.mData.mBackupReader);
        if (writeFile_native != 0 && !this.mExceptionLogged) {
            Log.e(TAG, "Failed restoring file '" + file + "' for app '" + this.mContext.getPackageName() + "' result=0x" + Integer.toHexString(writeFile_native));
            this.mExceptionLogged = true;
        }
        return writeFile_native == 0;
    }

    public void writeNewStateDescription(ParcelFileDescriptor parcelFileDescriptor) {
        writeSnapshot_native(this.mPtr, parcelFileDescriptor.getFileDescriptor());
    }

    boolean isKeyInList(String str, String[] strArr) {
        for (String str2 : strArr) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }
}
