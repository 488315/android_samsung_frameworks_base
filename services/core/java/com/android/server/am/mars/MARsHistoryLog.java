package com.android.server.am.mars;

import android.text.format.DateFormat;
import android.util.Slog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.am.mars.MARsHistoryLog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class MARsHistoryLog {
    public final String FILE_EXTENSION;
    public final String FILE_NAME;
    public final int LOG_FILE_MAX_COUNT;
    public final String SAVE_FILE_COUNT;
    public final String SAVE_LOG_PATH;
    public int mLogFileIndex;
    public MARsHistoryBuffer mMARsHistoryBuffer;
    public SaveLogThread mSaveLogThread;

    public abstract class MARsHistoryLogHolder {
        public static final MARsHistoryLog INSTANCE = new MARsHistoryLog();
    }

    public MARsHistoryLog() {
        this.SAVE_LOG_PATH = "/data/log/mars/";
        this.FILE_NAME = "mars";
        this.FILE_EXTENSION = ".log";
        this.SAVE_FILE_COUNT = "/data/log/mars/mars_log_count";
        this.LOG_FILE_MAX_COUNT = 10;
    }

    public static MARsHistoryLog getInstance() {
        return MARsHistoryLogHolder.INSTANCE;
    }

    public void init() {
        this.mSaveLogThread = new SaveLogThread();
        this.mMARsHistoryBuffer = MARsHistoryBuffer.getInstance();
        if (!IsIndexFileExist("/data/log/mars/mars_log_count")) {
            Slog.d("MARsPolicyManager", "There is no log idx file");
            this.mLogFileIndex = 0;
            return;
        }
        try {
            this.mLogFileIndex = Integer.parseInt(readFileIndex("/data/log/mars/mars_log_count"));
            new File("/data/log/mars/mars_log_count").delete();
        } catch (Exception e) {
            Slog.e("MARsPolicyManager", "Failed to read file count");
            this.mLogFileIndex = 0;
            e.printStackTrace();
        }
    }

    public synchronized ArrayList getLog() {
        ArrayList arrayList = new ArrayList();
        if (!recreateLogFile("/data/log/mars/mars" + this.mLogFileIndex + ".log")) {
            Slog.e("MARsPolicyManager", "recreate Failed\n");
            return null;
        }
        saveLogToFile(false, false);
        int i = this.mLogFileIndex;
        while (true) {
            i++;
            if (i >= 10) {
                break;
            }
            File file = new File("/data/log/mars/mars" + i + ".log");
            if (file.exists()) {
                readLogFromFile(file, arrayList);
            }
        }
        for (int i2 = 0; i2 <= this.mLogFileIndex; i2++) {
            File file2 = new File("/data/log/mars/mars" + i2 + ".log");
            if (file2.exists()) {
                readLogFromFile(file2, arrayList);
            }
        }
        return arrayList;
    }

    public void readLogFromFile(File file, ArrayList arrayList) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "UTF-8"));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        arrayList.add(readLine + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    } else {
                        bufferedReader.close();
                        return;
                    }
                } finally {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean recreateLogFile(String str) {
        try {
            File file = new File(str);
            file.mkdirs();
            if (file.exists() && !file.delete()) {
                Slog.e("MARsPolicyManager", "File is not deleted.");
            }
            if (file.createNewFile()) {
                return true;
            }
            Slog.e("MARsPolicyManager", "File is not created.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized void saveLogToFile(boolean z, boolean z2) {
        String str = "/data/log/mars/mars" + this.mLogFileIndex + ".log";
        recreateLogFile(str);
        File file = new File(str);
        if (z) {
            int i = (this.mLogFileIndex + 1) % 10;
            this.mLogFileIndex = i;
            if (z2) {
                try {
                    if (!writeFileIndex("/data/log/mars/mars_log_count", i)) {
                        return;
                    }
                } catch (Exception unused) {
                    Slog.e("MARsPolicyManager", "Failed to sync log file number");
                }
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()), "UTF-8"));
            if (z2) {
                try {
                    String str2 = (String) DateFormat.format("yyyy-MM-dd kk:mm:ss", System.currentTimeMillis());
                    this.mMARsHistoryBuffer.put(str2 + " SHUTDOWN\n");
                } catch (Throwable th) {
                    try {
                        bufferedWriter.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            int pointer = this.mMARsHistoryBuffer.getPointer();
            for (int i2 = 0; i2 < pointer; i2++) {
                bufferedWriter.write(this.mMARsHistoryBuffer.getBufferLine(i2));
            }
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readFileIndex(String str) {
        String str2 = null;
        if (str == null) {
            return null;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(str));
            try {
                str2 = bufferedReader.readLine();
                bufferedReader.close();
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str2 != null ? str2.trim() : str2;
    }

    public boolean writeFileIndex(String str, int i) {
        recreateLogFile("/data/log/mars/mars_log_count");
        try {
            FileWriter fileWriter = new FileWriter(str);
            try {
                fileWriter.write(i + "");
                fileWriter.close();
                return true;
            } finally {
            }
        } catch (Exception e) {
            try {
                e.printStackTrace();
                return false;
            } catch (Exception e2) {
                e2.printStackTrace();
                return false;
            }
        }
    }

    public boolean IsIndexFileExist(String str) {
        try {
            File file = new File(str);
            if (file.exists()) {
                return file.canRead();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public class SaveLogThread {
        public SaveLogThread() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$start$0() {
            MARsHistoryLog.this.saveLogToFile(true, true);
        }

        public void start() {
            Thread thread = new Thread(new Runnable() { // from class: com.android.server.am.mars.MARsHistoryLog$SaveLogThread$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MARsHistoryLog.SaveLogThread.this.lambda$start$0();
                }
            });
            thread.setPriority(1);
            thread.start();
        }
    }
}
