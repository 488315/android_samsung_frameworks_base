package android.content.om.wallpapertheme;

import android.graphics.Color;
import android.os.Binder;
import android.util.Log;
import android.util.Slog;
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
    public static Integer adjustAlpha(float f, int i) {
        return Integer.valueOf(Color.argb(Math.round(Color.alpha(i) * f), Color.red(i), Color.green(i), Color.blue(i)));
    }

    public static void saveSWTLog(String str, String str2) {
        LogWrapper.save(str, str2);
    }

    public static StringBuilder getLogText() {
        return LogWrapper.getLogText();
    }

    private static class LogWrapper {
        private static final int LOG_FILE_MAX_COUNT = 2;
        private static final String LOG_FILE_NAME = "/data/log/color_palette_log%g.txt";
        private static final int LOG_FILE_SIZE_LIMIT = 10240;
        private static final String TAG = "SWT_LogWrapper";
        private static FileHandler fileHandler;
        private static Logger logger;
        private static final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss.SSS: ", Locale.getDefault());
        private static final Date date = new Date();

        private LogWrapper() {
        }

        static {
            try {
                FileHandler fileHandler2 = new FileHandler(LOG_FILE_NAME, 10240, 2, true);
                fileHandler = fileHandler2;
                fileHandler2.setFormatter(new Formatter() { // from class: android.content.om.wallpapertheme.ThemeUtil.LogWrapper.1
                    @Override // java.util.logging.Formatter
                    public String format(LogRecord logRecord) {
                        LogWrapper.date.setTime(System.currentTimeMillis());
                        return LogWrapper.formatter.format(LogWrapper.date) + logRecord.getMessage();
                    }
                });
                Logger logger2 = Logger.getLogger(LogWrapper.class.getName());
                logger = logger2;
                logger2.addHandler(fileHandler);
                logger.setLevel(Level.ALL);
                logger.setUseParentHandlers(false);
            } catch (Exception e) {
                Log.i(TAG, "Can not use LogWrapper " + e.toString());
            }
        }

        public static void save(String str, String str2) {
            Logger logger2 = logger;
            if (logger2 != null) {
                logger2.log(Level.INFO, String.format("V %s(%d): %s%n", str, Integer.valueOf(Binder.getCallingPid()), str2));
            }
            Slog.i(str, str2);
        }

        public static StringBuilder getLogText() throws IOException {
            File[] fileArr = {new File("/data/log/color_palette_log1.txt"), new File("/data/log/color_palette_log0.txt")};
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 2; i++) {
                File file = fileArr[i];
                if (file.exists()) {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
                        while (true) {
                            try {
                                String line = bufferedReader.readLine();
                                if (line == null) {
                                    break;
                                }
                                sb.append(line);
                                sb.append('\n');
                            } finally {
                            }
                        }
                        bufferedReader.close();
                        sb.append('\n');
                    } catch (IOException e) {
                        Log.e(TAG, "Can not use getLogText : " + e);
                        return null;
                    }
                }
            }
            return sb;
        }
    }
}
