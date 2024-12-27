package com.android.server.battery;

import android.util.Slog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class BatteryLogger {
    public static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static String getFilePathWithSuffix(String str) {
        String concat;
        Path path = Paths.get(str, new String[0]);
        Path parent = path.getParent();
        String path2 = path.getFileName().toString();
        int lastIndexOf = path2.lastIndexOf(46);
        if (lastIndexOf != -1) {
            concat = path2.substring(0, lastIndexOf) + "_1" + path2.substring(lastIndexOf);
        } else {
            concat = path2.concat("_1");
        }
        if (parent != null) {
            concat = parent.resolve(concat).toString();
        }
        Slog.v(
                "[SS]BatteryLogger",
                "[getFilePathWithSuffix]filePath:" + str + "=> newFilePath:" + concat);
        return concat;
    }

    public static void renameForBackupIfExeedsSize(String str) {
        Slog.v(
                "[SS]BatteryLogger",
                "[renameForBackupIfExeedsSize]filePath:" + str + " ,suffix:_1 ,thresholdSizeMb:2");
        if (!BattUtils.isExist(str)) {
            Slog.w(
                    "[SS]BatteryLogger",
                    "[renameForBackupIfExeedsSize]Not Exist - filePath:".concat(str));
            return;
        }
        try {
            Path path = Paths.get(str, new String[0]);
            long size = Files.size(path);
            long j = size / 1048576;
            Slog.d(
                    "[SS]BatteryLogger",
                    "[renameForBackupIfExeedsSize]fileSizeInBytes:" + size + " ,fileSizeInMb:" + j);
            if (j >= 2) {
                Path path2 = Paths.get(getFilePathWithSuffix(str), new String[0]);
                Files.move(
                        path,
                        path2,
                        StandardCopyOption.ATOMIC_MOVE,
                        StandardCopyOption.REPLACE_EXISTING);
                Slog.i("[SS]BatteryLogger", "[renameForBackupIfExeedsSize]File Renamed:" + path2);
            }
        } catch (Exception e) {
            Slog.e("[SS]BatteryLogger", "[renameForBackupIfExeedsSize]Exception");
            e.printStackTrace();
        }
    }

    public static void writeToFile(String str, String str2, String str3) {
        Slog.d("[SS]BatteryLogger", "[writeToFile]path:".concat(str));
        File file = new File(str);
        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                boolean createNewFile = file.createNewFile();
                Slog.i(
                        "[SS]BatteryLogger",
                        "[writeToFile]create file: " + str + " ,result:" + createNewFile);
                if (!createNewFile) {
                    return;
                }
            }
        } catch (Exception e) {
            Slog.e("[SS]BatteryLogger", "[writeToFile]Exception - createNewFile");
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
                        bufferedWriter.write("# ".concat(str2));
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
            Slog.e("[SS]BatteryLogger", "[writeToFile]Exception");
            e2.printStackTrace();
        }
    }
}
