package com.android.server.am.pds;

import android.util.Slog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PDSHistoryLog {
    public int mLogFileIndex;
    public PDSHistoryBuffer mPDSHistoryBuffer;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class PDSHistoryLogHolder {
        public static final PDSHistoryLog INSTANCE = new PDSHistoryLog();
    }

    public static void readLogFromFile(File file, ArrayList arrayList) {
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(file.getAbsolutePath()), "UTF-8"));
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
                Slog.e("PDSController", "File is not deleted.");
            }
            if (file.createNewFile()) {
                return true;
            }
            Slog.e("PDSController", "File is not created.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final synchronized ArrayList getLog() {
        ArrayList arrayList = new ArrayList();
        if (!recreateLogFile("/data/log/pds/pds" + this.mLogFileIndex + ".log")) {
            Slog.e("PDSController", "recreate Failed\n");
            return null;
        }
        saveLogToFile(false);
        int i = this.mLogFileIndex;
        while (true) {
            i++;
            if (i >= 10) {
                break;
            }
            File file = new File("/data/log/pds/pds" + i + ".log");
            if (file.exists()) {
                readLogFromFile(file, arrayList);
            }
        }
        for (int i2 = 0; i2 <= this.mLogFileIndex; i2++) {
            File file2 = new File("/data/log/pds/pds" + i2 + ".log");
            if (file2.exists()) {
                readLogFromFile(file2, arrayList);
            }
        }
        return arrayList;
    }

    public final synchronized void saveLogToFile(boolean z) {
        BufferedWriter bufferedWriter;
        String str = "/data/log/pds/pds" + this.mLogFileIndex + ".log";
        recreateLogFile(str);
        File file = new File(str);
        if (z) {
            this.mLogFileIndex = (this.mLogFileIndex + 1) % 10;
        }
        try {
            bufferedWriter =
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(file.getAbsolutePath()), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            int i = this.mPDSHistoryBuffer.pointer;
            for (int i2 = 0; i2 < i; i2++) {
                bufferedWriter.write(this.mPDSHistoryBuffer.buffer[i2]);
            }
            bufferedWriter.close();
        } catch (Throwable th) {
            try {
                bufferedWriter.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
