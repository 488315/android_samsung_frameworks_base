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
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

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

    public SQLiteDump(String dbPath) {
        this.mMaxDumpFiles = 5;
        this.mLineSeparator = System.getProperty("line.separator");
        this.mDbPath = dbPath;
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
            this.mDumpDirPath = createDumpDir();
            boolean ret = createCorruptFile(this.mDumpDirPath);
            if (ret) {
                this.mOutPutStream =
                        new BufferedOutputStream(
                                new FileOutputStream(this.mDumpFile.getAbsoluteFile()));
                this.mDumpFilePrinter = new PrintStream((OutputStream) this.mOutPutStream, true);
                Printer printer = new LogPrinter(5, TAG);
                SimpleDateFormat opDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String formattedCorruptTime = opDF.format(new Date(System.currentTimeMillis()));
                this.mTeePrinter = new TeePrinter(printer, this.mDumpFilePrinter);
                if (this.mDumpFilePrinter != null) {
                    this.mDumpFilePrinter.println(
                            "===== corrupt db name: "
                                    + new File(this.mDbPath).getName()
                                    + " =====");
                    this.mDumpFilePrinter.println(
                            "===== corrupt time:    " + formattedCorruptTime + " =====");
                    this.mDumpFilePrinter.println(
                            "===== dump file name:  " + this.mDumpFile.getName() + " =====");
                }
            }
            deleteOldDumpFiles();
            this.isReady.set(ret);
        } catch (Exception e) {
            Log.e(TAG, "prepare dump file failed.", e);
            reset();
        }
    }

    public void addDumpLog(String tag, Object... args) {
        if (!isReady() || args == null) {
            return;
        }
        try {
            StringBuilder msg = new StringBuilder(64);
            getLogPrefix(msg);
            msg.append(tag);
            msg.append(":");
            if (args.length > 0) {
                msg.append(" ");
            }
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    if (args[i] instanceof Throwable) {
                        msg.append(this.mLineSeparator);
                    }
                    msg.append(args[i].toString());
                }
            }
            if (this.mDumpFilePrinter != null) {
                this.mDumpFilePrinter.println(msg.toString());
            }
        } catch (Exception e) {
        }
    }

    public void logAndDump(String tag, Object... args) {
        if (args == null) {
            return;
        }
        if (args.length == 1) {
            Log.e(tag, args[0].toString());
        } else if (args.length == 2 && (args[1] instanceof Throwable)) {
            Log.e(tag, args[0].toString(), (Exception) args[1]);
        }
        addDumpLog(tag, args);
    }

    public String getSQLiteDumpLogs(boolean reset) {
        if (this.mDbPath == null) {
            return null;
        }
        return SQLiteGlobal.getSQLiteDumpLogs(reset);
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
                if (this.mOutPutStream != null) {
                    this.mOutPutStream.flush();
                }
                this.isReady.set(false);
                this.mTeePrinter = null;
                if (this.mOutPutStream != null) {
                    this.mOutPutStream.close();
                    this.mOutPutStream = null;
                }
                if (this.mDumpFilePrinter != null) {
                    this.mDumpFilePrinter.close();
                    this.mDumpFilePrinter = null;
                }
            } catch (Exception e) {
                this.isReady.set(false);
                this.mTeePrinter = null;
                if (this.mOutPutStream != null) {
                    this.mOutPutStream.close();
                    this.mOutPutStream = null;
                }
                if (this.mDumpFilePrinter != null) {
                    this.mDumpFilePrinter.close();
                    this.mDumpFilePrinter = null;
                }
            } catch (Throwable th) {
                try {
                    this.isReady.set(false);
                    this.mTeePrinter = null;
                    if (this.mOutPutStream != null) {
                        this.mOutPutStream.close();
                        this.mOutPutStream = null;
                    }
                    if (this.mDumpFilePrinter != null) {
                        this.mDumpFilePrinter.close();
                        this.mDumpFilePrinter = null;
                    }
                } catch (Exception e2) {
                }
                throw th;
            }
        } catch (Exception e3) {
        }
    }

    private boolean isReady() {
        if (this.isReady == null) {
            return false;
        }
        return this.isReady.get();
    }

    private String createDumpDir() {
        String databaseDir = new File(this.mDbPath).getParent();
        File dumpDir = new File(databaseDir, DB_INFO_DUMP_DIR_NAME);
        if (!dumpDir.exists() && (!dumpDir.mkdir() || !dumpDir.exists())) {
            return null;
        }
        return dumpDir.getAbsolutePath();
    }

    private boolean createCorruptFile(String dumpDir) {
        File dumpFile = getDumpFile(dumpDir);
        if (dumpFile != null) {
            try {
                if (!dumpFile.exists()) {
                    return dumpFile.createNewFile();
                }
                return false;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    private String getDbCreateTime(String dbName) {
        File file = new File(dbName);
        if (file.exists()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
            Path path = file.toPath();
            try {
                BasicFileAttributes attr =
                        Files.readAttributes(
                                path,
                                (Class<BasicFileAttributes>) BasicFileAttributes.class,
                                new LinkOption[0]);
                return sdf.format(Long.valueOf(attr.lastAccessTime().toMillis()));
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private File getDumpFile(String dumpDir) {
        String dbCreateTime = getDbCreateTime(this.mDbPath);
        String dbName = this.mDbPath.substring(this.mDbPath.lastIndexOf(47) + 1).replace('.', '_');
        String corruptFileName =
                "dbcorrupt_dump_"
                        + dbName
                        + Session.SESSION_SEPARATION_CHAR_CHILD
                        + dbCreateTime
                        + ".log";
        this.mDumpFile = new File(dumpDir, corruptFileName);
        return this.mDumpFile;
    }

    private void deleteOldDumpFiles() {
        File dumpDir = new File(this.mDumpDirPath);
        File[] dumpFiles = dumpDir.listFiles();
        if (dumpFiles == null || dumpFiles.length <= 5) {
            return;
        }
        Arrays.sort(
                dumpFiles,
                new Comparator<File>() { // from class: android.database.sqlite.SQLiteDump.1
                    @Override // java.util.Comparator
                    public int compare(File f1, File f2) {
                        long diff = f1.lastModified() - f2.lastModified();
                        if (diff > 0) {
                            return 1;
                        }
                        return diff == 0 ? 0 : -1;
                    }
                });
        dumpFiles[0].delete();
    }

    private void getLogPrefix(StringBuilder msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        msg.append(sdf.format(new Date(System.currentTimeMillis())) + " ");
        msg.append(Process.myUid() + " ");
        msg.append(Process.myPid() + " ");
        msg.append(Process.myTid() + " ");
    }

    public static class TeePrinter implements Printer {
        Printer p1;
        PrintStream p2;

        public TeePrinter(Printer printer1, PrintStream printer2) {
            this.p1 = printer1;
            this.p2 = printer2;
        }

        @Override // android.util.Printer
        public void println(String x) {
            try {
                if (this.p1 != null) {
                    this.p1.println(x);
                }
                if (this.p2 != null) {
                    this.p2.println(x);
                }
            } catch (Exception e) {
            }
        }
    }
}
