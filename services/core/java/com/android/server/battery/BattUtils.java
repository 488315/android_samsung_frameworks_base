package com.android.server.battery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Slog;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import org.json.JSONObject;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BattUtils {
    public static final DateTimeFormatter FORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static long getCurrentNetworkTimeMillis() {
        long j;
        try {
            j = SystemClock.currentNetworkTimeMillis();
        } catch (Exception e) {
            Slog.w("[SS]BattUtils", "[getCurrentNetworkTimeMillis]Exception(cannot get network time)");
            e.printStackTrace();
            j = -1;
        }
        Slog.d("[SS]BattUtils", "[getCurrentNetworkTimeMillis]networkTimeMillis:" + j);
        return j;
    }

    public static String getValueFromJson(String str) {
        try {
            String string = new JSONObject(str).getString("HIGH_SWELLING_CNT");
            Slog.d("[SS]BattUtils", "[getValueFromJson]key:HIGH_SWELLING_CNT ,value:" + string);
            return string;
        } catch (Exception e) {
            Slog.e("[SS]BattUtils", "[getValueFromJson]Exception - jsonData:" + str);
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isExist(String str) {
        if (str == null) {
            Slog.e("[SS]BattUtils", "[isExist]path null");
            return false;
        }
        boolean exists = Files.exists(Paths.get(str, new String[0]), new LinkOption[0]);
        Slog.d("[SS]BattUtils", "[isExist]path:" + str + " ,result:" + exists);
        return exists;
    }

    public static String readNode(String str, boolean z) {
        BufferedReader bufferedReader;
        String readLine;
        if (str == null) {
            Slog.e("[SS]BattUtils", "[readNode]path null");
            return "";
        }
        try {
            bufferedReader = new BufferedReader(new FileReader(str));
            try {
                readLine = bufferedReader.readLine();
                if (z) {
                    Slog.d("[SS]BattUtils", "[readNode]path:" + str + " ,value:" + readLine);
                }
            } finally {
            }
        } catch (IOException e) {
            Slog.e("[SS]BattUtils", "[readNode]Exception - path:".concat(str), e);
        }
        if (readLine != null) {
            bufferedReader.close();
            return readLine;
        }
        bufferedReader.close();
        return "";
    }

    public static double readNodeAsDouble() {
        try {
            return Double.parseDouble(readNode("/efs/FactoryApp/bsoh", true));
        } catch (NumberFormatException unused) {
            Slog.e("[SS]BattUtils", "[readNodeAsDouble]NumberFormatException");
            return -1.0d;
        }
    }

    public static int readNodeAsInt(String str) {
        try {
            return Integer.parseInt(readNode(str, true));
        } catch (NumberFormatException unused) {
            Slog.e("[SS]BattUtils", "[readNodeAsInt]NumberFormatException");
            return -1;
        }
    }

    public static long readNodeAsLong(String str) {
        try {
            return Long.parseLong(readNode(str, true));
        } catch (NumberFormatException unused) {
            Slog.e("[SS]BattUtils", "[readNodeAsLong]NumberFormatException");
            return -1L;
        }
    }

    public static long readNodeAsLong$1(String str) {
        try {
            return Long.parseLong(readNode(str, false));
        } catch (NumberFormatException unused) {
            Slog.e("[SS]BattUtils", "[readNodeAsLong]NumberFormatException");
            return -1L;
        }
    }

    public static void saveSharedPreferencesAsLong(Context context, long j) {
        Slog.d("[SS]BattUtils", "[saveSharedPreferencesAsLong]preferenceName:battery_service_prefs ,key:shutdown_time ,value:" + j);
        try {
            SharedPreferences.Editor edit = context.getSharedPreferences("battery_service_prefs", 0).edit();
            edit.putLong("shutdown_time", j);
            edit.apply();
        } catch (Exception e) {
            Slog.e("[SS]BattUtils", "[saveSharedPreferencesAsLong]Exception");
            e.printStackTrace();
        }
    }

    public static void writeNode(long j, String str) {
        writeNode(str, String.valueOf(j));
    }

    public static boolean writeNode(String str, String str2) {
        GmsAlarmManager$$ExternalSyntheticOutline0.m("[writeNode]path:", str, " ,data:", str2, "[SS]BattUtils");
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
            Slog.e("[SS]BattUtils", "[writeNode]Exception:" + e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean writeNode(String str, boolean z) {
        return writeNode(str, z ? "1" : "0");
    }
}
