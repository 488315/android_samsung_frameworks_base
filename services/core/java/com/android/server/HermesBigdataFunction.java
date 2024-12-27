package com.android.server;

import android.util.Slog;

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
import java.util.regex.Pattern;

public abstract class HermesBigdataFunction {
    public static final String[] PARSING_TAG = {"DrmLibFs"};
    public static final String[] CHECK_CHIPSET_LISTS = {"SM8550"};

    /* renamed from: com.android.server.HermesBigdataFunction$1, reason: invalid class name */
    public final class AnonymousClass1 implements FileFilter {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ AnonymousClass1(int i) {
            this.$r8$classId = i;
        }

        @Override // java.io.FileFilter
        public final boolean accept(File file) {
            switch (this.$r8$classId) {
                case 0:
                    if (!file.isFile()
                            || !file.getName().startsWith("dumpstate_skeymaster")
                            || !file.getName().endsWith("zip")) {}
                    break;
                case 1:
                    if (!file.isFile()
                            || !file.getName().startsWith("dumpstate_skeymaster")
                            || !file.getName().endsWith("txt")) {}
                    break;
                case 2:
                    if (!file.isFile()
                            || !file.getName().startsWith("dumpstate_skeymaster")
                            || !file.getName().endsWith("zip")) {}
                    break;
                default:
                    if (!file.isFile() || !file.getName().startsWith("dumpstate_skeymaster")) {}
                    break;
            }
            return false;
        }
    }

    public enum BigdataError {
        EF0("NO_ERROR", "No Error"),
        ERR_NOT_SUPPORTED("ERR_NOT_SUPPORTED", "Bigdata function is not supported"),
        ERR_SEND_DIAGMON("ERR_SEND_DIAGMON", "sending diagmon agent is failed"),
        ERR_UNKNOWN("ERR_UNKNOWN", "Unkonwn error"),
        ERR_FILE_NOT_FOUND("ERR_FILE_NOT_FOUND", "File is not found"),
        ERR_FILE_CREATED_FAILED("ERR_FILE_CREATED_FAILED", "File creation has failed"),
        ERR_PERMISSION_DENIED("ERR_PERMISSION_DENIED", "File permission denied"),
        ERR_ZIP_EXCEPTION("ERR_ZIP_EXCEPTION", "Zip API is failed"),
        ERR_IO_EXCEPTION("ERR_IO_EXCEPTION", "Some I/O operation is failed"),
        ERR_NULLPOINTER_EXCEPTION(
                "ERR_NULLPOINTER_EXCEPTION", "Null pointer exception has occured"),
        ERR_INTERRUPTION_EXCEPTION("ERR_INTERRUPTION_EXCEPTION", "Some interrupt has occured");

        private final int errCode;
        private final String reason;

        BigdataError(String str, String str2) {
            this.reason = str2;
            this.errCode = r2;
        }

        public final int errCode() {
            return this.errCode;
        }

        public final String reason() {
            return this.reason;
        }
    }

    public static void cleanBigdataLogFiles() {
        BigdataError bigdataError = BigdataError.ERR_PERMISSION_DENIED;
        BigdataError bigdataError2 = BigdataError.ERR_UNKNOWN;
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
                        "HERMES#BigDataFunction",
                        "[cleanBigdataLogFiles] There is no collected bigdata log");
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
                                    new BufferedReader(
                                            new InputStreamReader(fileInputStream, "EUCKR"));
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
                } catch (Throwable th) {
                    try {
                        fileOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (FileNotFoundException unused) {
                throw new BigdataException(BigdataError.ERR_FILE_NOT_FOUND);
            } catch (IOException unused2) {
                throw new BigdataException(BigdataError.ERR_IO_EXCEPTION);
            } catch (SecurityException unused3) {
                throw new BigdataException(bigdataError);
            } catch (Exception unused4) {
                throw new BigdataException(bigdataError2);
            }
        } catch (NullPointerException unused5) {
            throw new BigdataException(BigdataError.ERR_NULLPOINTER_EXCEPTION);
        } catch (SecurityException unused6) {
            throw new BigdataException(bigdataError);
        } catch (Exception unused7) {
            throw new BigdataException(bigdataError2);
        }
    }

    public static void cleanDumpstateFiles() {
        try {
            for (File file : new File("/data/log/").listFiles(new AnonymousClass1(2))) {
                if (file.renameTo(
                        new File(
                                file.getAbsolutePath()
                                        .replace(
                                                "dumpstate_skeymaster",
                                                "dumpstate_skeymaster".toUpperCase())))) {
                    Slog.d(
                            "HERMES#BigDataFunction",
                            "[cleanDumpstateFiles] zipfile rename is success");
                }
            }
            for (File file2 :
                    new File("/data/log/sepunion/hermes/").listFiles(new AnonymousClass1(3))) {
                if (!file2.delete()) {
                    Slog.d(
                            "HERMES#BigDataFunction",
                            "[cleanDumpstateFiles] filtered txt file deletion failed");
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

    public static void parseDumpstate() {
        BigdataError bigdataError = BigdataError.ERR_PERMISSION_DENIED;
        BigdataError bigdataError2 = BigdataError.ERR_UNKNOWN;
        Slog.d("HERMES#BigDataFunction", "[parseDumpstate] started");
        try {
            File[] listFiles =
                    new File("/data/log/sepunion/hermes/").listFiles(new AnonymousClass1(1));
            File file = new File("/data/log/sepunion/hermes/parsed_skeymast.txt");
            if (file.exists() && file.length() > 1048576 && file.delete()) {
                Slog.d("HERMES#BigDataFunction", "[parseDumpstate] saved log is cleared");
            }
            int length = listFiles.length;
            char c = 0;
            int i = 0;
            while (i < length) {
                File file2 = listFiles[i];
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                    try {
                        BufferedWriter bufferedWriter =
                                new BufferedWriter(
                                        new OutputStreamWriter(fileOutputStream, "EUCKR"));
                        try {
                            FileInputStream fileInputStream = new FileInputStream(file2);
                            try {
                                BufferedReader bufferedReader =
                                        new BufferedReader(
                                                new InputStreamReader(fileInputStream, "EUCKR"));
                                try {
                                    Pattern compile =
                                            Pattern.compile(
                                                    "^\\s*\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}\\.\\d+\\s+(\\d+|root)\\s+\\d+\\s+\\d+\\s.\\s+"
                                                            + (PARSING_TAG[c])
                                                            + ":.*");
                                    long length2 = file.length();
                                    while (true) {
                                        String readLine = bufferedReader.readLine();
                                        if (readLine == null) {
                                            break;
                                        } else if (compile.matcher(readLine).find()) {
                                            bufferedWriter.write(readLine);
                                            bufferedWriter.newLine();
                                        }
                                    }
                                    bufferedWriter.flush();
                                    if (file.length() - length2 != 0) {
                                        bufferedWriter.write("-----------------------------");
                                        bufferedWriter.newLine();
                                    }
                                    bufferedReader.close();
                                    fileInputStream.close();
                                    bufferedWriter.close();
                                    fileOutputStream.close();
                                    i++;
                                    c = 0;
                                } catch (Throwable th) {
                                    try {
                                        bufferedReader.close();
                                        throw th;
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                        throw th;
                                    }
                                }
                            } finally {
                            }
                        } finally {
                        }
                    } catch (Throwable th3) {
                        try {
                            fileOutputStream.close();
                            throw th3;
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                            throw th3;
                        }
                    }
                } catch (FileNotFoundException unused) {
                    throw new BigdataException(BigdataError.ERR_FILE_NOT_FOUND);
                } catch (IOException unused2) {
                    throw new BigdataException(BigdataError.ERR_IO_EXCEPTION);
                } catch (SecurityException unused3) {
                    throw new BigdataException(bigdataError);
                } catch (Exception unused4) {
                    throw new BigdataException(bigdataError2);
                }
            }
            Slog.d("HERMES#BigDataFunction", "[unZipDumpstate] done");
        } catch (SecurityException unused5) {
            throw new BigdataException(bigdataError);
        } catch (Exception unused6) {
            throw new BigdataException(bigdataError2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0059, code lost:

       r12 = new java.io.File("/data/log/sepunion/hermes/", r10.getName());
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0062, code lost:

       r10 = new java.io.BufferedOutputStream(new java.io.FileOutputStream(r12));
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x006c, code lost:

       android.util.Slog.d("HERMES#BigDataFunction", "[unZipDumpstate] extract files : " + r12.getAbsolutePath());
       r12 = new byte[4096];
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0088, code lost:

       r13 = r11.read(r12);
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x008d, code lost:

       if (r13 == (-1)) goto L89;
    */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008f, code lost:

       r10.write(r12, 0, r13);
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0095, code lost:

       r10.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0093, code lost:

       r2 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00a3, code lost:

       throw r2;
    */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00af, code lost:

       throw new com.android.server.BigdataException(r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00a9, code lost:

       throw new com.android.server.BigdataException(r1);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void unZipDumpstate() {
        /*
            Method dump skipped, instructions count: 267
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.HermesBigdataFunction.unZipDumpstate():void");
    }
}
