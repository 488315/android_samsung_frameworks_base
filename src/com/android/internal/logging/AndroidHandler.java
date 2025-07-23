package com.android.internal.logging;

import android.media.MediaMetrics;
import android.util.Log;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.FastPrintWriter;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* loaded from: classes5.dex */
public class AndroidHandler extends Handler {
    private static final Formatter THE_FORMATTER = new Formatter() { // from class: com.android.internal.logging.AndroidHandler.1
        @Override // java.util.logging.Formatter
        public String format(LogRecord logRecord) {
            Throwable thrown = logRecord.getThrown();
            if (thrown != null) {
                StringWriter stringWriter = new StringWriter();
                FastPrintWriter fastPrintWriter = new FastPrintWriter((Writer) stringWriter, false, 256);
                stringWriter.write(logRecord.getMessage());
                stringWriter.write(ShaderAssembler.NEWLINE);
                thrown.printStackTrace(fastPrintWriter);
                fastPrintWriter.flush();
                return stringWriter.toString();
            }
            return logRecord.getMessage();
        }
    };

    @Override // java.util.logging.Handler
    public void close() {
    }

    @Override // java.util.logging.Handler
    public void flush() {
    }

    public AndroidHandler() {
        setFormatter(THE_FORMATTER);
    }

    private static String loggerNameToTag(String str) {
        if (str == null) {
            return PerfettoProtoLogImpl.NULL_STRING;
        }
        int length = str.length();
        if (length <= 23) {
            return str;
        }
        int lastIndexOf = str.lastIndexOf(MediaMetrics.SEPARATOR) + 1;
        if (length - lastIndexOf <= 23) {
            return str.substring(lastIndexOf);
        }
        return str.substring(str.length() - 23);
    }

    @Override // java.util.logging.Handler
    public void publish(LogRecord logRecord) {
        int androidLevel = getAndroidLevel(logRecord.getLevel());
        String loggerNameToTag = loggerNameToTag(logRecord.getLoggerName());
        if (Log.isLoggable(loggerNameToTag, androidLevel)) {
            try {
                Log.println(androidLevel, loggerNameToTag, getFormatter().format(logRecord));
            } catch (RuntimeException e) {
                Log.e("AndroidHandler", "Error logging message.", e);
            }
        }
    }

    public void publish(Logger logger, String str, Level level, String str2) {
        int androidLevel = getAndroidLevel(level);
        if (Log.isLoggable(str, androidLevel)) {
            try {
                Log.println(androidLevel, str, str2);
            } catch (RuntimeException e) {
                Log.e("AndroidHandler", "Error logging message.", e);
            }
        }
    }

    static int getAndroidLevel(Level level) {
        int intValue = level.intValue();
        if (intValue >= 1000) {
            return 6;
        }
        if (intValue >= 900) {
            return 5;
        }
        return intValue >= 800 ? 4 : 3;
    }
}
