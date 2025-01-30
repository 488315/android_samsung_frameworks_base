package com.android.server.location.nsflp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.android.server.am.StackTracesDumpHelper$$ExternalSyntheticLambda1;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class NSUtLogger extends Handler {
  public long mDirectorySize;
  public int mFileCount;
  public Map mFileSizeMap;
  public int mOccurredExceptionCount;

  public NSUtLogger(Looper looper) {
    super(looper);
  }

  @Override // android.os.Handler
  public void handleMessage(Message message) {
    if (message.what == 1) {
      try {
        LogInfo logInfo = (LogInfo) message.obj;
        File file = new File(logInfo.path);
        if (!file.exists() && !createFile(file)) {
          Log.e("NSUtLogger", "writeFile, cannot create file");
        } else {
          writeData(logInfo.type, file, logInfo.data);
        }
      } catch (Exception e) {
        Log.e("NSUtLogger", "Failed to writeUtLog, exception=" + e.toString());
      } catch (OutOfMemoryError e2) {
        Log.e("NSUtLogger", "Failed to writeUtLog, error=" + e2.toString());
      }
    }
  }

  public static boolean writeFile(File file, byte[] bArr, boolean z) {
    if (file != null && file.exists() && bArr != null) {
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

  public static boolean deleteFile(File file) {
    if (file.exists()) {
      boolean delete = file.delete();
      Log.v("NSUtLogger", "success deleteFile " + delete);
      return delete;
    }
    Log.v("NSUtLogger", file + "file is not existed");
    return false;
  }

  public final boolean createFile(File file) {
    if (makeDirectory("/data/log/gps/issuetracker/") == null) {
      Log.e("NSUtLogger", "createFile, directory is null");
      return false;
    }
    if (makeFile(file)) {
      return true;
    }
    Log.e("NSUtLogger", "createFile, failed to make file");
    return false;
  }

  public final File makeDirectory(String str) {
    File file = new File(str);
    if (!file.exists()) {
      if (file.mkdirs()) {
        Log.i("NSUtLogger", "Success to create the directory");
      } else {
        Log.e("NSUtLogger", "Failed to create the directory");
        return null;
      }
    } else {
      Log.v("NSUtLogger", "Directory[" + str + "] already exists");
    }
    return file;
  }

  public void writeData(int i, File file, String str) {
    long length = str.length();
    if (file == null) {
      Log.e("NSUtLogger", "writeData, file is null");
      return;
    }
    File file2 = new File("/data/log/gps/issuetracker/");
    if (this.mFileSizeMap == null) {
      this.mFileSizeMap = new HashMap();
      this.mDirectorySize = createFileSizeMap(file2);
    }
    if (this.mFileCount >= 500 || this.mDirectorySize >= 104857600) {
      if (this.mOccurredExceptionCount >= 3) {
        Log.i("NSUtLogger", "writeData, occurred exception count is over so return");
        return;
      }
      deleteOldFiles(length);
    }
    if (writeFile(file, str.getBytes(StandardCharsets.UTF_8), true)) {
      Log.i("NSUtLogger", "writeData, success write data " + file);
      updateFileSizeMap(file);
      if (i != 3 && writeKmzFile(i, file)) {
        updateFileSizeMap(new File(file.toString().replace(".txt", ".kmz")));
        return;
      }
      return;
    }
    Log.e("NSUtLogger", "writeData, failed write data");
  }

  public final long createFileSizeMap(File file) {
    File[] listFiles = file.listFiles();
    long j = 0;
    if (listFiles != null && listFiles.length != 0) {
      for (File file2 : listFiles) {
        long length = file2.length();
        j += length;
        this.mFileSizeMap.put(file2.getName(), Long.valueOf(length));
        this.mFileCount++;
      }
    }
    return j;
  }

  public void deleteOldFiles(long j) {
    File[] listFiles =
        new File("/data/log/gps/issuetracker/")
            .listFiles(
                new FileFilter() { // from class:
                                   // com.android.server.location.nsflp.NSUtLogger$$ExternalSyntheticLambda0
                  @Override // java.io.FileFilter
                  public final boolean accept(File file) {
                    boolean lambda$deleteOldFiles$0;
                    lambda$deleteOldFiles$0 = NSUtLogger.lambda$deleteOldFiles$0(file);
                    return lambda$deleteOldFiles$0;
                  }
                });
    if (listFiles == null) {
      return;
    }
    try {
      Arrays.sort(
          listFiles,
          Comparator.comparingLong(new StackTracesDumpHelper$$ExternalSyntheticLambda1()));
      int length = listFiles.length;
      int i = 0;
      while (true) {
        if (i >= length) {
          break;
        }
        File file = listFiles[i];
        long length2 = file.length();
        String name = file.getName();
        if (deleteFile(file)) {
          this.mDirectorySize -= length2;
          this.mFileSizeMap.remove(name);
          this.mFileCount--;
        }
        if (this.mFileCount < 500 && this.mDirectorySize < 104857600) {
          Log.i(
              "NSUtLogger",
              "deleteOldFiles, file count="
                  + this.mFileCount
                  + ", directory size="
                  + this.mDirectorySize
                  + ",dataLength="
                  + j);
          break;
        }
        i++;
      }
      this.mOccurredExceptionCount = 0;
    } catch (IllegalArgumentException e) {
      this.mOccurredExceptionCount++;
      Log.e(
          "NSUtLogger",
          "deleteOldFiles, " + e.toString() + " , count : " + this.mOccurredExceptionCount);
    }
  }

  public static /* synthetic */ boolean lambda$deleteOldFiles$0(File file) {
    return !file.getName().equals("crash_history.txt");
  }

  public static boolean makeFile(File file) {
    boolean z = false;
    try {
      if (!file.exists()) {
        z = file.createNewFile();
      } else {
        Log.v("NSUtLogger", file + " exists");
      }
      return z;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    } finally {
      Log.v("NSUtLogger", "isSuccess = false");
    }
  }

  public final void updateFileSizeMap(File file) {
    String name = file.getName();
    long length = file.length();
    if (this.mFileSizeMap.containsKey(name)) {
      this.mDirectorySize += length - ((Long) this.mFileSizeMap.get(name)).longValue();
    } else {
      this.mDirectorySize += length;
      this.mFileCount++;
    }
    this.mFileSizeMap.put(name, Long.valueOf(length));
  }

  public final boolean writeKmzFile(int i, File file) {
    return new NSKmlWriter().createKmzFile(i, file);
  }

  public class LogInfo {
    public String data;
    public String path;
    public int type;

    public LogInfo(int i, String str, String str2) {
      this.type = i;
      this.path = str;
      this.data = str2;
    }
  }
}
