package com.android.server.am.mars;

import android.text.format.DateFormat;
import android.util.Slog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MARsHistoryLog {
    public int mLogFileIndex;
    public MARsHistoryBuffer mMARsHistoryBuffer;
    public SaveLogThread mSaveLogThread;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class MARsHistoryLogHolder {
        public static final MARsHistoryLog INSTANCE = new MARsHistoryLog();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SaveLogThread {
        public final /* synthetic */ MARsHistoryLog this$0 = MARsHistoryLogHolder.INSTANCE;
    }

    public static void readLogFromFile(File file, ArrayList arrayList) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath()), StandardCharsets.UTF_8));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        bufferedReader.close();
                        return;
                    }
                    arrayList.add(readLine + "\n");
                } finally {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean recreateLogFile(String str) {
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

    public static boolean writeFileIndex(int i) {
        recreateLogFile("/data/log/mars/mars_log_count");
        try {
            FileWriter fileWriter = new FileWriter("/data/log/mars/mars_log_count", StandardCharsets.UTF_8);
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

    public final synchronized ArrayList getLog() {
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

    public final synchronized void saveLogToFile(boolean z, boolean z2) {
        String str = "/data/log/mars/mars" + this.mLogFileIndex + ".log";
        recreateLogFile(str);
        File file = new File(str);
        if (z) {
            int i = (this.mLogFileIndex + 1) % 10;
            this.mLogFileIndex = i;
            if (z2) {
                try {
                    if (!writeFileIndex(i)) {
                        return;
                    }
                } catch (Exception unused) {
                    Slog.e("MARsPolicyManager", "Failed to sync log file number");
                }
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()), StandardCharsets.UTF_8));
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
            int i2 = this.mMARsHistoryBuffer.pointer;
            for (int i3 = 0; i3 < i2; i3++) {
                bufferedWriter.write(this.mMARsHistoryBuffer.buffer[i3]);
            }
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
