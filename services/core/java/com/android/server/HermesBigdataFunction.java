package com.android.server;

import android.os.SystemProperties;
import android.util.Slog;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

/* loaded from: classes.dex */
public final class HermesBigdataFunction {
  public static final String[] PARSING_TAG = {"DrmLibFs"};
  public static final String[] CHECK_CHIPSET_LISTS = {"SM8550"};

  public enum BigdataError {
    NO_ERROR(0, "No Error"),
    ERR_NOT_SUPPORTED(-100, "Bigdata function is not supported"),
    ERR_SEND_DIAGMON(
        KnoxCustomManagerService.DOCK_SHORTCUT_CONTAINER_ID, "sending diagmon agent is failed"),
    ERR_UNKNOWN(-1000, "Unkonwn error"),
    ERR_FILE_NOT_FOUND(-1001, "File is not found"),
    ERR_FILE_CREATED_FAILED(-1002, "File creation has failed"),
    ERR_PERMISSION_DENIED(-1003, "File permission denied"),
    ERR_ZIP_EXCEPTION(-1004, "Zip API is failed"),
    ERR_IO_EXCEPTION(-1005, "Some I/O operation is failed"),
    ERR_NULLPOINTER_EXCEPTION(-1006, "Null pointer exception has occured"),
    ERR_INTERRUPTION_EXCEPTION(-1007, "Some interrupt has occured");

    private final int errCode;
    private final String reason;

    BigdataError(int i, String str) {
      this.reason = str;
      this.errCode = i;
    }

    public String reason() {
      return this.reason;
    }

    public int errCode() {
      return this.errCode;
    }
  }

  public static Pattern makeRegexPattern(String[] strArr) {
    StringBuilder sb = new StringBuilder();
    String[] strArr2 = PARSING_TAG;
    int length = strArr2.length;
    sb.append(strArr2[0]);
    for (int i = 1; i < length; i++) {
      sb.append("|" + PARSING_TAG[i]);
    }
    String sb2 = sb.toString();
    if (length >= 2) {
      sb2 = "(" + sb2 + ")";
    }
    return Pattern.compile(
        String.format(
            "^\\s*\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}\\.\\d+\\s+(\\d+|root)\\s+\\d+\\s+\\d+\\s.\\s+%s:.*",
            sb2));
  }

  public static boolean makeFolder(String str) {
    File file = new File(str);
    if (file.exists()) {
      return true;
    }
    return file.mkdirs();
  }

  /* JADX WARN: Code restructure failed: missing block: B:23:0x0042, code lost:

     r7 = new java.io.File("/data/log/sepunion/hermes/", r5.getName());
  */
  /* JADX WARN: Code restructure failed: missing block: B:25:0x004b, code lost:

     r5 = new java.io.BufferedOutputStream(new java.io.FileOutputStream(r7));
  */
  /* JADX WARN: Code restructure failed: missing block: B:27:0x0055, code lost:

     android.util.Slog.d("HERMES#BigDataFunction", "[unZipDumpstate] extract files : " + r7.getAbsolutePath());
     r7 = new byte[android.os.IInstalld.FLAG_USE_QUOTA];
  */
  /* JADX WARN: Code restructure failed: missing block: B:28:0x0071, code lost:

     r8 = r6.read(r7);
  */
  /* JADX WARN: Code restructure failed: missing block: B:29:0x0076, code lost:

     if (r8 == (-1)) goto L83;
  */
  /* JADX WARN: Code restructure failed: missing block: B:30:0x0078, code lost:

     r5.write(r7, 0, r8);
  */
  /* JADX WARN: Code restructure failed: missing block: B:32:0x007c, code lost:

     r5.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:36:0x0080, code lost:

     r10 = move-exception;
  */
  /* JADX WARN: Code restructure failed: missing block: B:39:0x0089, code lost:

     throw r10;
  */
  /* JADX WARN: Code restructure failed: missing block: B:45:0x0091, code lost:

     throw new com.android.server.BigdataException(com.android.server.HermesBigdataFunction.BigdataError.ERR_IO_EXCEPTION);
  */
  /* JADX WARN: Code restructure failed: missing block: B:47:0x0099, code lost:

     throw new com.android.server.BigdataException(com.android.server.HermesBigdataFunction.BigdataError.ERR_FILE_NOT_FOUND);
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void unZipDumpstate() {
    Slog.d("HERMES#BigDataFunction", "[unZipDumpstate] started");
    try {
      if (!makeFolder("/data/log/sepunion/hermes/")) {
        throw new BigdataException(BigdataError.ERR_FILE_CREATED_FAILED);
      }
      try {
        for (File file :
            new File("/data/log/")
                .listFiles(
                    new FileFilter() { // from class: com.android.server.HermesBigdataFunction.1
                      @Override // java.io.FileFilter
                      public boolean accept(File file2) {
                        return file2.isFile()
                            && file2.getName().startsWith("dumpstate_skeymaster")
                            && file2.getName().endsWith("zip");
                      }
                    })) {
          try {
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
            try {
              ZipEntry nextEntry = zipInputStream.getNextEntry();
              while (true) {
                if (nextEntry == null) {
                  break;
                } else if (nextEntry.getName().startsWith("dumpstate_skeymaster")) {
                  break;
                } else {
                  nextEntry = zipInputStream.getNextEntry();
                }
              }
              zipInputStream.close();
            } finally {
            }
          } catch (FileNotFoundException unused) {
            throw new BigdataException(BigdataError.ERR_FILE_NOT_FOUND);
          } catch (IOException unused2) {
            throw new BigdataException(BigdataError.ERR_IO_EXCEPTION);
          } catch (SecurityException unused3) {
            throw new BigdataException(BigdataError.ERR_PERMISSION_DENIED);
          } catch (ZipException unused4) {
            throw new BigdataException(BigdataError.ERR_ZIP_EXCEPTION);
          } catch (Exception unused5) {
            throw new BigdataException(BigdataError.ERR_UNKNOWN);
          }
        }
        Slog.d("HERMES#BigDataFunction", "[unZipDumpstate] done");
      } catch (SecurityException unused6) {
        throw new BigdataException(BigdataError.ERR_PERMISSION_DENIED);
      } catch (Exception unused7) {
        throw new BigdataException(BigdataError.ERR_UNKNOWN);
      }
    } catch (SecurityException unused8) {
      throw new BigdataException(BigdataError.ERR_PERMISSION_DENIED);
    } catch (Exception unused9) {
      throw new BigdataException(BigdataError.ERR_UNKNOWN);
    }
  }

  public final void parseDumpstate() {
    Slog.d("HERMES#BigDataFunction", "[parseDumpstate] started");
    try {
      File[] listFiles =
          new File("/data/log/sepunion/hermes/")
              .listFiles(
                  new FileFilter() { // from class: com.android.server.HermesBigdataFunction.2
                    @Override // java.io.FileFilter
                    public boolean accept(File file) {
                      return file.isFile()
                          && file.getName().startsWith("dumpstate_skeymaster")
                          && file.getName().endsWith("txt");
                    }
                  });
      File file = new File("/data/log/sepunion/hermes/parsed_skeymast.txt");
      if (file.exists() && file.length() > 1048576 && file.delete()) {
        Slog.d("HERMES#BigDataFunction", "[parseDumpstate] saved log is cleared");
      }
      for (File file2 : listFiles) {
        try {
          FileOutputStream fileOutputStream = new FileOutputStream(file, true);
          try {
            BufferedWriter bufferedWriter =
                new BufferedWriter(new OutputStreamWriter(fileOutputStream, "EUCKR"));
            try {
              FileInputStream fileInputStream = new FileInputStream(file2);
              try {
                BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(fileInputStream, "EUCKR"));
                try {
                  Pattern makeRegexPattern = makeRegexPattern(PARSING_TAG);
                  long length = file.length();
                  while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                      break;
                    } else if (makeRegexPattern.matcher(readLine).find()) {
                      bufferedWriter.write(readLine);
                      bufferedWriter.newLine();
                    }
                  }
                  bufferedWriter.flush();
                  if (file.length() - length != 0) {
                    bufferedWriter.write("-----------------------------");
                    bufferedWriter.newLine();
                  }
                  bufferedReader.close();
                  fileInputStream.close();
                  bufferedWriter.close();
                  fileOutputStream.close();
                } finally {
                }
              } finally {
              }
            } finally {
            }
          } finally {
          }
        } catch (FileNotFoundException unused) {
          throw new BigdataException(BigdataError.ERR_FILE_NOT_FOUND);
        } catch (IOException unused2) {
          throw new BigdataException(BigdataError.ERR_IO_EXCEPTION);
        } catch (SecurityException unused3) {
          throw new BigdataException(BigdataError.ERR_PERMISSION_DENIED);
        } catch (Exception unused4) {
          throw new BigdataException(BigdataError.ERR_UNKNOWN);
        }
      }
      Slog.d("HERMES#BigDataFunction", "[unZipDumpstate] done");
    } catch (SecurityException unused5) {
      throw new BigdataException(BigdataError.ERR_PERMISSION_DENIED);
    } catch (Exception unused6) {
      throw new BigdataException(BigdataError.ERR_UNKNOWN);
    }
  }

  public final void cleanDumpstateFiles() {
    try {
      for (File file :
          new File("/data/log/")
              .listFiles(
                  new FileFilter() { // from class: com.android.server.HermesBigdataFunction.3
                    @Override // java.io.FileFilter
                    public boolean accept(File file2) {
                      return file2.isFile()
                          && file2.getName().startsWith("dumpstate_skeymaster")
                          && file2.getName().endsWith("zip");
                    }
                  })) {
        if (file.renameTo(
            new File(
                file.getAbsolutePath()
                    .replace("dumpstate_skeymaster", "dumpstate_skeymaster".toUpperCase())))) {
          Slog.d("HERMES#BigDataFunction", "[cleanDumpstateFiles] zipfile rename is success");
        }
      }
      for (File file2 :
          new File("/data/log/sepunion/hermes/")
              .listFiles(
                  new FileFilter() { // from class: com.android.server.HermesBigdataFunction.4
                    @Override // java.io.FileFilter
                    public boolean accept(File file3) {
                      return file3.isFile() && file3.getName().startsWith("dumpstate_skeymaster");
                    }
                  })) {
        if (!file2.delete()) {
          Slog.d(
              "HERMES#BigDataFunction", "[cleanDumpstateFiles] filtered txt file deletion failed");
        }
      }
      Slog.d("HERMES#BigDataFunction", "[CleanDumpstate] done");
    } catch (NullPointerException unused) {
      throw new BigdataException(BigdataError.ERR_NULLPOINTER_EXCEPTION);
    } catch (SecurityException unused2) {
      throw new BigdataException(BigdataError.ERR_PERMISSION_DENIED);
    } catch (Exception unused3) {
      throw new BigdataException(BigdataError.ERR_UNKNOWN);
    }
  }

  public final void cleanBigdataLogFiles() {
    try {
      File file = new File("/data/log/sepunion/hermes/parsed_skeymast.txt");
      File file2 = new File(file.getParent(), file.getName().toUpperCase());
      if (file2.exists() && file2.length() > 1048576 && file2.delete()) {
        Slog.d(
            "HERMES#BigDataFunction",
            "[cleanBigdataLogFiles] exceed size of bigdata log file is deleted");
      }
      if (!file.exists()) {
        Slog.d(
            "HERMES#BigDataFunction", "[cleanBigdataLogFiles] There is no collected bigdata log");
        return;
      }
      try {
        FileOutputStream fileOutputStream = new FileOutputStream(file2, true);
        try {
          BufferedWriter bufferedWriter =
              new BufferedWriter(new OutputStreamWriter(fileOutputStream, "EUCKR"));
          try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
              BufferedReader bufferedReader =
                  new BufferedReader(new InputStreamReader(fileInputStream, "EUCKR"));
              while (true) {
                try {
                  String readLine = bufferedReader.readLine();
                  if (readLine == null) {
                    break;
                  }
                  bufferedWriter.write(readLine);
                  bufferedWriter.newLine();
                } finally {
                }
              }
              bufferedReader.close();
              fileInputStream.close();
              bufferedWriter.close();
              fileOutputStream.close();
              if (file.delete()) {
                Slog.d(
                    "HERMES#BigDataFunction",
                    "[cleanBigdataLogFiles] Collected bigdata log is deleted");
              }
              Slog.d("HERMES#BigDataFunction", "[cleanBigdataLogFiles] done");
            } finally {
            }
          } finally {
          }
        } finally {
        }
      } catch (FileNotFoundException unused) {
        throw new BigdataException(BigdataError.ERR_FILE_NOT_FOUND);
      } catch (IOException unused2) {
        throw new BigdataException(BigdataError.ERR_IO_EXCEPTION);
      } catch (SecurityException unused3) {
        throw new BigdataException(BigdataError.ERR_PERMISSION_DENIED);
      } catch (Exception unused4) {
        throw new BigdataException(BigdataError.ERR_UNKNOWN);
      }
    } catch (NullPointerException unused5) {
      throw new BigdataException(BigdataError.ERR_NULLPOINTER_EXCEPTION);
    } catch (SecurityException unused6) {
      throw new BigdataException(BigdataError.ERR_PERMISSION_DENIED);
    } catch (Exception unused7) {
      throw new BigdataException(BigdataError.ERR_UNKNOWN);
    }
  }

  public void makeSkeymasterDumpstate() {
    String str = SystemProperties.get("ro.soc.model");
    String[] strArr = CHECK_CHIPSET_LISTS;
    if (strArr.length > 0) {
      if (strArr[0].equals(str)) {
        unZipDumpstate();
        parseDumpstate();
        cleanDumpstateFiles();
        return;
      }
      throw new BigdataException(BigdataError.ERR_NOT_SUPPORTED);
    }
  }

  public void finishSkeymasterDumpstate() {
    try {
      TimeUnit.MINUTES.sleep(1L);
      cleanBigdataLogFiles();
    } catch (BigdataException e) {
      throw e;
    } catch (InterruptedException unused) {
      throw new BigdataException(BigdataError.ERR_INTERRUPTION_EXCEPTION);
    }
  }
}
