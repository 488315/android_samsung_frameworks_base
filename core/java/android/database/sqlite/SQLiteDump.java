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
      String createDumpDir = createDumpDir();
      this.mDumpDirPath = createDumpDir;
      boolean ret = createCorruptFile(createDumpDir);
      if (ret) {
        this.mOutPutStream =
            new BufferedOutputStream(new FileOutputStream(this.mDumpFile.getAbsoluteFile()));
        this.mDumpFilePrinter = new PrintStream((OutputStream) this.mOutPutStream, true);
        Printer printer = new LogPrinter(5, TAG);
        SimpleDateFormat opDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedCorruptTime = opDF.format(new Date(System.currentTimeMillis()));
        this.mTeePrinter = new TeePrinter(printer, this.mDumpFilePrinter);
        PrintStream printStream = this.mDumpFilePrinter;
        if (printStream != null) {
          printStream.println(
              "===== corrupt db name: " + new File(this.mDbPath).getName() + " =====");
          this.mDumpFilePrinter.println(
              "===== corrupt time:    " + formattedCorruptTime + " =====");
          this.mDumpFilePrinter.println(
              "===== dump file name:  " + this.mDumpFile.getName() + " =====");
        }
      }
      deleteOldDumpFiles();
      this.isReady.set(ret);
    } catch (Exception e) {
      Log.m97e(TAG, "prepare dump file failed.", e);
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
      PrintStream printStream = this.mDumpFilePrinter;
      if (printStream != null) {
        printStream.println(msg.toString());
      }
    } catch (Exception e) {
    }
  }

  public void logAndDump(String tag, Object... args) {
    if (args == null) {
      return;
    }
    if (args.length == 1) {
      Log.m96e(tag, args[0].toString());
    } else if (args.length == 2 && (args[1] instanceof Throwable)) {
      Log.m97e(tag, args[0].toString(), (Exception) args[1]);
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
      } catch (Exception e) {
      }
    } catch (Exception e2) {
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
      } catch (Exception e3) {
      }
      throw th;
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
                path, (Class<BasicFileAttributes>) BasicFileAttributes.class, new LinkOption[0]);
        return sdf.format(Long.valueOf(attr.lastAccessTime().toMillis()));
      } catch (Exception e) {
        return null;
      }
    }
    return null;
  }

  private File getDumpFile(String dumpDir) {
    String dbCreateTime = getDbCreateTime(this.mDbPath);
    String str = this.mDbPath;
    String dbName = str.substring(str.lastIndexOf(47) + 1).replace('.', '_');
    String corruptFileName =
        "dbcorrupt_dump_" + dbName + Session.SESSION_SEPARATION_CHAR_CHILD + dbCreateTime + ".log";
    File file = new File(dumpDir, corruptFileName);
    this.mDumpFile = file;
    return file;
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

    /* renamed from: p1 */
    Printer f57p1;

    /* renamed from: p2 */
    PrintStream f58p2;

    public TeePrinter(Printer printer1, PrintStream printer2) {
      this.f57p1 = printer1;
      this.f58p2 = printer2;
    }

    @Override // android.util.Printer
    public void println(String x) {
      try {
        Printer printer = this.f57p1;
        if (printer != null) {
          printer.println(x);
        }
        PrintStream printStream = this.f58p2;
        if (printStream != null) {
          printStream.println(x);
        }
      } catch (Exception e) {
      }
    }
  }
}
