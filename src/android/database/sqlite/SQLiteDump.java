package android.database.sqlite;

import android.os.Process;
import android.telecom.Logging.Session;
import android.util.Log;
import android.util.LogPrinter;
import android.util.Printer;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class SQLiteDump {
    public static final String DB_INFO_DUMP_DIR_NAME = "sqlite_dump";
    public static final SQLiteDump DUMMY_DB_DUMP = new SQLiteDump();
    private static final String TAG = "SQLiteDump";
    private AtomicBoolean isReady;
    private String mDbPath;
    private String mDumpDirPath;
    private File mDumpFile;
    private PrintStream mDumpFilePrinter;
    private final String mLineSeparator;
    private final int mMaxDumpFiles;
    private BufferedOutputStream mOutPutStream;
    public TeePrinter mTeePrinter;

    private SQLiteDump() {
        this.mMaxDumpFiles = 5;
        this.mLineSeparator = System.getProperty("line.separator");
    }

    public SQLiteDump(String str) {
        this.mMaxDumpFiles = 5;
        this.mLineSeparator = System.getProperty("line.separator");
        this.mDbPath = str;
        this.isReady = new AtomicBoolean(false);
        if (this.mDbPath != null) {
            SQLiteGlobal.enableSQLiteDump(true);
        }
    }

    public void prepareDumpFile() {
        if (this.mDbPath == null) {
            return;
        }
        try {
            String createDumpDir = createDumpDir();
            this.mDumpDirPath = createDumpDir;
            boolean createCorruptFile = createCorruptFile(createDumpDir);
            if (createCorruptFile) {
                this.mOutPutStream = new BufferedOutputStream(new FileOutputStream(this.mDumpFile.getAbsoluteFile()));
                this.mDumpFilePrinter = new PrintStream((OutputStream) this.mOutPutStream, true);
                LogPrinter logPrinter = new LogPrinter(5, TAG);
                String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis()));
                this.mTeePrinter = new TeePrinter(logPrinter, this.mDumpFilePrinter);
                PrintStream printStream = this.mDumpFilePrinter;
                if (printStream != null) {
                    printStream.println("===== corrupt db name: " + new File(this.mDbPath).getName() + " =====");
                    this.mDumpFilePrinter.println("===== corrupt time:    " + format + " =====");
                    this.mDumpFilePrinter.println("===== dump file name:  " + this.mDumpFile.getName() + " =====");
                }
            }
            deleteOldDumpFiles();
            this.isReady.set(createCorruptFile);
        } catch (Exception e) {
            Log.e(TAG, "prepare dump file failed.", e);
            reset();
        }
    }

    public void addDumpLog(String str, Object... objArr) {
        if (!isReady() || objArr == null) {
            return;
        }
        try {
            StringBuilder sb = new StringBuilder(64);
            getLogPrefix(sb);
            sb.append(str);
            sb.append(":");
            if (objArr.length > 0) {
                sb.append(" ");
            }
            for (int i = 0; i < objArr.length; i++) {
                Object obj = objArr[i];
                if (obj != null) {
                    if (obj instanceof Throwable) {
                        sb.append(this.mLineSeparator);
                    }
                    sb.append(objArr[i].toString());
                }
            }
            PrintStream printStream = this.mDumpFilePrinter;
            if (printStream != null) {
                printStream.println(sb.toString());
            }
        } catch (Exception unused) {
        }
    }

    public void logAndDump(String str, Object... objArr) {
        if (objArr == null) {
            return;
        }
        if (objArr.length == 1) {
            Log.e(str, objArr[0].toString());
        } else if (objArr.length == 2 && (objArr[1] instanceof Throwable)) {
            Log.e(str, objArr[0].toString(), (Exception) objArr[1]);
        }
        addDumpLog(str, objArr);
    }

    public String getSQLiteDumpLogs(boolean z) {
        if (this.mDbPath == null) {
            return null;
        }
        return SQLiteGlobal.getSQLiteDumpLogs(z);
    }

    public void finishDump() {
        reset();
    }

    private void reset() {
        if (this.mDbPath == null) {
            return;
        }
        try {
            try {
                BufferedOutputStream bufferedOutputStream = this.mOutPutStream;
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.flush();
                }
                this.isReady.set(false);
                this.mTeePrinter = null;
                BufferedOutputStream bufferedOutputStream2 = this.mOutPutStream;
                if (bufferedOutputStream2 != null) {
                    bufferedOutputStream2.close();
                    this.mOutPutStream = null;
                }
                PrintStream printStream = this.mDumpFilePrinter;
                if (printStream != null) {
                    printStream.close();
                    this.mDumpFilePrinter = null;
                }
            } catch (Exception unused) {
                this.isReady.set(false);
                this.mTeePrinter = null;
                BufferedOutputStream bufferedOutputStream3 = this.mOutPutStream;
                if (bufferedOutputStream3 != null) {
                    bufferedOutputStream3.close();
                    this.mOutPutStream = null;
                }
                PrintStream printStream2 = this.mDumpFilePrinter;
                if (printStream2 != null) {
                    printStream2.close();
                    this.mDumpFilePrinter = null;
                }
            } catch (Throwable th) {
                try {
                    this.isReady.set(false);
                    this.mTeePrinter = null;
                    BufferedOutputStream bufferedOutputStream4 = this.mOutPutStream;
                    if (bufferedOutputStream4 != null) {
                        bufferedOutputStream4.close();
                        this.mOutPutStream = null;
                    }
                    PrintStream printStream3 = this.mDumpFilePrinter;
                    if (printStream3 != null) {
                        printStream3.close();
                        this.mDumpFilePrinter = null;
                    }
                } catch (Exception unused2) {
                }
                throw th;
            }
        } catch (Exception unused3) {
        }
    }

    private boolean isReady() {
        AtomicBoolean atomicBoolean = this.isReady;
        if (atomicBoolean == null) {
            return false;
        }
        return atomicBoolean.get();
    }

    private String createDumpDir() {
        File file = new File(new File(this.mDbPath).getParent(), DB_INFO_DUMP_DIR_NAME);
        if (file.exists() || (file.mkdir() && file.exists())) {
            return file.getAbsolutePath();
        }
        return null;
    }

    private boolean createCorruptFile(String str) {
        File dumpFile = getDumpFile(str);
        if (dumpFile == null) {
            return false;
        }
        try {
            if (dumpFile.exists()) {
                return false;
            }
            return dumpFile.createNewFile();
        } catch (IOException unused) {
            return false;
        }
    }

    private String getDbCreateTime(String str) {
        File file = new File(str);
        if (!file.exists()) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyyMMdd_HH_mm_ss").format(Long.valueOf(Files.readAttributes(file.toPath(), BasicFileAttributes.class, new LinkOption[0]).lastAccessTime().toMillis()));
        } catch (Exception unused) {
            return null;
        }
    }

    private File getDumpFile(String str) {
        String dbCreateTime = getDbCreateTime(this.mDbPath);
        String str2 = this.mDbPath;
        File file = new File(str, "dbcorrupt_dump_" + str2.substring(str2.lastIndexOf(47) + 1).replace('.', '_') + Session.SESSION_SEPARATION_CHAR_CHILD + dbCreateTime + ".log");
        this.mDumpFile = file;
        return file;
    }

    private void deleteOldDumpFiles() {
        File[] listFiles = new File(this.mDumpDirPath).listFiles();
        if (listFiles == null || listFiles.length <= 5) {
            return;
        }
        Arrays.sort(listFiles, new Comparator<File>(this) { // from class: android.database.sqlite.SQLiteDump.1
            @Override // java.util.Comparator
            public int compare(File file, File file2) {
                long lastModified = file.lastModified() - file2.lastModified();
                if (lastModified > 0) {
                    return 1;
                }
                return lastModified == 0 ? 0 : -1;
            }
        });
        listFiles[0].delete();
    }

    private void getLogPrefix(StringBuilder sb) {
        sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis())) + " ");
        sb.append(Process.myUid() + " ");
        sb.append(Process.myPid() + " ");
        sb.append(Process.myTid() + " ");
    }

    public static class TeePrinter implements Printer {
        Printer p1;
        PrintStream p2;

        public TeePrinter(Printer printer, PrintStream printStream) {
            this.p1 = printer;
            this.p2 = printStream;
        }

        @Override // android.util.Printer
        public void println(String str) {
            try {
                Printer printer = this.p1;
                if (printer != null) {
                    printer.println(str);
                }
                PrintStream printStream = this.p2;
                if (printStream != null) {
                    printStream.println(str);
                }
            } catch (Exception unused) {
            }
        }
    }
}
