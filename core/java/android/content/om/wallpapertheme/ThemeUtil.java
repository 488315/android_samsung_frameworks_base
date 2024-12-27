package android.content.om.wallpapertheme;

import android.graphics.Color;
import android.os.Binder;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* loaded from: classes.dex */
public class ThemeUtil {
    public static Integer adjustAlpha(float factor, int color) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Integer.valueOf(Color.argb(alpha, red, green, blue));
    }

    public static void saveSWTLog(String tag, String msg) {
        LogWrapper.save(tag, msg);
    }

    public static StringBuilder getLogText() {
        return LogWrapper.getLogText();
    }

    private static class LogWrapper {
        private static final int LOG_FILE_MAX_COUNT = 2;
        private static final String LOG_FILE_NAME = "/data/log/color_palette_log%g.txt";
        private static final int LOG_FILE_SIZE_LIMIT = 10240;
        private static final String TAG = "LogWrapper";
        private static FileHandler fileHandler;
        private static Logger logger;
        private static final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss.SSS: ", Locale.getDefault());
        private static final Date date = new Date();

        private LogWrapper() {
        }

        static {
            try {
                fileHandler = new FileHandler(LOG_FILE_NAME, 10240, 2, true);
                fileHandler.setFormatter(new Formatter() { // from class: android.content.om.wallpapertheme.ThemeUtil.LogWrapper.1
                    @Override // java.util.logging.Formatter
                    public String format(LogRecord r) {
                        LogWrapper.date.setTime(System.currentTimeMillis());
                        return LogWrapper.formatter.format(LogWrapper.date) + r.getMessage();
                    }
                });
                logger = Logger.getLogger(LogWrapper.class.getName());
                logger.addHandler(fileHandler);
                logger.setLevel(Level.ALL);
                logger.setUseParentHandlers(false);
            } catch (Exception e) {
                Log.i(TAG, "Can not use LogWrapper " + e.toString());
            }
        }

        public static void save(String tag, String msg) {
            if (logger != null) {
                logger.log(Level.INFO, String.format("V %s(%d): %s%n", tag, Integer.valueOf(Binder.getCallingPid()), msg));
            }
            Log.i(tag, msg);
        }

        public static StringBuilder getLogText() {
            File[] files = {new File("/data/log/color_palette_log0.txt"), new File("/data/log/color_palette_log1.txt")};
            StringBuilder text = new StringBuilder();
            for (File openFile : files) {
                if (openFile.exists()) {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(openFile, StandardCharsets.UTF_8));
                        while (true) {
                            try {
                                String line = br.readLine();
                                if (line == null) {
                                    break;
                                }
                                text.append(line);
                                text.append('\n');
                            } finally {
                            }
                        }
                        br.close();
                        text.append('\n');
                    } catch (IOException e) {
                        Log.e(TAG, "Can not use getLogText : " + e);
                        return null;
                    }
                }
            }
            return text;
        }
    }
}
