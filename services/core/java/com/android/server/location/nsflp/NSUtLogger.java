package com.android.server.location.nsflp;

import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class NSUtLogger extends Handler {
    public long mDirectorySize;
    public int mFileCount;
    public Map mFileSizeMap;
    public final NSKmlWriter mNsKmlWriter;
    public int mOccurredExceptionCount;

    public final class LogInfo {
        public String data;
        public String path;
        public int type;
    }

    public NSUtLogger(Looper looper) {
        super(looper);
        this.mNsKmlWriter = new NSKmlWriter();
    }

    public static boolean createFile(File file) {
        if (!isCallerSystem()) {
            return false;
        }
        File file2 = null;
        if (isCallerSystem()) {
            File file3 = new File("/data/log/gps/issuetracker/");
            if (file3.exists()) {
                Log.v("NSUtLogger", "Directory[/data/log/gps/issuetracker/] already exists");
            } else if (file3.mkdirs()) {
                Log.i("NSUtLogger", "Success to create the directory");
            } else {
                Log.e("NSUtLogger", "Failed to create the directory");
            }
            file2 = file3;
        }
        if (file2 == null) {
            Log.e("NSUtLogger", "createFile, directory is null");
            return false;
        }
        if (makeFile(file)) {
            return true;
        }
        Log.e("NSUtLogger", "createFile, failed to make file");
        return false;
    }

    public static boolean deleteFile(File file) {
        if (!isCallerSystem()) {
            return false;
        }
        if (file.exists()) {
            boolean delete = file.delete();
            Log.v("NSUtLogger", "success deleteFile " + delete);
            return delete;
        }
        Log.v("NSUtLogger", file + "file is not existed");
        return false;
    }

    public static boolean isCallerSystem() {
        return Binder.getCallingUid() == 1000;
    }

    public static boolean makeFile(File file) {
        boolean z = false;
        if (!isCallerSystem()) {
            return false;
        }
        try {
            if (file.exists()) {
                Log.v("NSUtLogger", file + " exists");
            } else {
                try {
                    z = file.createNewFile();
                    Log.v("NSUtLogger", "isSuccess = " + z);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.v("NSUtLogger", "isSuccess = false");
                    return false;
                }
            }
            return z;
        } catch (Throwable th) {
            Log.v("NSUtLogger", "isSuccess = false");
            throw th;
        }
    }

    public static boolean writeFile(File file, byte[] bArr, boolean z) {
        if (isCallerSystem() && file.exists() && bArr != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file, z);
                try {
                    fileOutputStream.write(bArr);
                    fileOutputStream.close();
                    return true;
                } finally {
                }
            } catch (Exception e) {
                Log.e("NSUtLogger", "Exception occurred while open file" + e.toString());
            }
        }
        return false;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        if (message.what == 1) {
            try {
                LogInfo logInfo = (LogInfo) message.obj;
                File file = new File(logInfo.path);
                if (file.exists() || createFile(file)) {
                    writeData(logInfo.type, file, logInfo.data);
                } else {
                    Log.e("NSUtLogger", "writeFile, cannot create file");
                }
            } catch (Exception e) {
                Log.e("NSUtLogger", "Failed to writeUtLog, exception=" + e.toString());
            } catch (OutOfMemoryError e2) {
                Log.e("NSUtLogger", "Failed to writeUtLog, error=" + e2.toString());
            }
        }
    }

    public final void updateFileSizeMap(File file) {
        String name = file.getName();
        long length = file.length();
        if (((HashMap) this.mFileSizeMap).containsKey(name)) {
            this.mDirectorySize +=
                    length - ((Long) ((HashMap) this.mFileSizeMap).get(name)).longValue();
        } else {
            this.mDirectorySize += length;
            this.mFileCount++;
        }
        ((HashMap) this.mFileSizeMap).put(name, Long.valueOf(length));
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void writeData(int r50, java.io.File r51, java.lang.String r52) {
        /*
            Method dump skipped, instructions count: 2386
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.location.nsflp.NSUtLogger.writeData(int,"
                    + " java.io.File, java.lang.String):void");
    }
}
