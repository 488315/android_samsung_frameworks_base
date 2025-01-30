package com.android.server.battery;

import android.os.SystemClock;
import android.util.Slog;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/* loaded from: classes.dex */
public abstract class BattUtils {
    public static final String TAG = "[SS]" + BattUtils.class.getSimpleName();
    public static final DateTimeFormatter FORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.Object, java.time.LocalDateTime] */
    public static String getCurrentNetworkDateStr() {
        try {
            long currentNetworkTimeMillis = SystemClock.currentNetworkTimeMillis();
            ?? localDateTime = Instant.ofEpochMilli(currentNetworkTimeMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
            Slog.v(TAG, "[getCurrentNetworkDateStr]networkTimeMillis:" + currentNetworkTimeMillis + " -> dateTime" + ((Object) localDateTime));
            return localDateTime.format(FORMATTER_yyyyMMdd);
        } catch (Exception e) {
            Slog.w(TAG, "[getCurrentNetworkDateStr]Exception(cannot get network time)");
            e.printStackTrace();
            return "";
        }
    }

    public static String getCurrentCalenderStr() {
        Calendar calendar = Calendar.getInstance();
        return String.format("%04d%02d%02d", Integer.valueOf(calendar.get(1)), Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)));
    }

    public static LocalDate convertDateStringToLocalDate(String str) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(str.trim(), FORMATTER_yyyyMMdd);
        } catch (Exception e) {
            Slog.w(TAG, "[convertDateStringToLocalDate]Exception");
            e.printStackTrace();
            localDate = null;
        }
        Slog.v(TAG, "[convertDateStringToLocalDate]dateString:" + str + " -> date" + localDate);
        return localDate;
    }

    public static String readNode(String str) {
        if (str == null) {
            Slog.e(TAG, "[readNode]path null");
            return "";
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(str));
            try {
                String readLine = bufferedReader.readLine();
                Slog.d(TAG, "[readNode]path:" + str + " ,value:" + readLine);
                if (readLine == null) {
                    readLine = "";
                }
                bufferedReader.close();
                return readLine;
            } finally {
            }
        } catch (IOException e) {
            Slog.e(TAG, "[readNode]Exception - path:" + str);
            e.printStackTrace();
            return "";
        }
    }

    public static int readNodeAsInt(String str) {
        try {
            return Integer.parseInt(readNode(str));
        } catch (NumberFormatException unused) {
            Slog.e(TAG, "[readNodeAsInt]NumberFormatException");
            return -1;
        }
    }

    public static long readNodeAsLong(String str) {
        try {
            return Long.parseLong(readNode(str));
        } catch (NumberFormatException unused) {
            Slog.e(TAG, "[readNodeAsLong]NumberFormatException");
            return -1L;
        }
    }

    public static boolean writeNode(String str, String str2) {
        Slog.d(TAG, "[writeNode]path:" + str + " ,data:" + str2);
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(str), StandardCharsets.UTF_8);
            try {
                outputStreamWriter.write(str2);
                outputStreamWriter.close();
                return true;
            } catch (Throwable th) {
                try {
                    outputStreamWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | NullPointerException e) {
            Slog.e(TAG, "[writeNode]Exception:" + e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean writeNode(String str, long j) {
        return writeNode(str, String.valueOf(j));
    }

    public static boolean isExist(String str) {
        if (str == null) {
            Slog.e(TAG, "[isExist]path null");
            return false;
        }
        boolean exists = Files.exists(Paths.get(str, new String[0]), new LinkOption[0]);
        Slog.d(TAG, "[isExist]path:" + str + " ,result:" + exists);
        return exists;
    }
}
