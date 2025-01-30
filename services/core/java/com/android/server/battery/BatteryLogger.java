package com.android.server.battery;

import android.util.Slog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/* loaded from: classes.dex */
public abstract class BatteryLogger {
    public static final String TAG = "[SS]" + BatteryLogger.class.getSimpleName();
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void writeToFile(String str, String str2, String str3) {
        String str4 = TAG;
        Slog.d(str4, "[writeToFile]path:" + str);
        File file = new File(str);
        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                boolean createNewFile = file.createNewFile();
                Slog.i(str4, "[writeToFile]create file: " + str + " ,result:" + createNewFile);
                if (!createNewFile) {
                    return;
                }
            }
        } catch (Exception e) {
            Slog.e(TAG, "[writeToFile]Exception - createNewFile");
            e.printStackTrace();
        }
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                try {
                    bufferedWriter.write("@ " + LocalDateTime.now().format(FORMATTER));
                    bufferedWriter.newLine();
                    if (str2 != null && !str2.isEmpty()) {
                        bufferedWriter.write("# " + str2);
                        bufferedWriter.newLine();
                    }
                    if (str3 != null && !str3.isEmpty()) {
                        bufferedWriter.write(str3);
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                    fileWriter.close();
                } finally {
                }
            } finally {
            }
        } catch (Exception e2) {
            Slog.e(TAG, "[writeToFile]Exception");
            e2.printStackTrace();
        }
    }
}
