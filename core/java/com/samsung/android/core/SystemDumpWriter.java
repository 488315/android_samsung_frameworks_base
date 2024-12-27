package com.samsung.android.core;

import android.content.Context;
import android.os.Debug;
import android.os.Environment;
import android.os.SystemClock;
import android.telecom.Logging.Session;
import android.util.Slog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

public class SystemDumpWriter implements AutoCloseable {
    private static final String FILE_DUMPSYS = "log/dumpsys_";
    private static final int MAX_COUNT_SAVED = 3;
    private static final String TAG_WM = "WindowManager";
    private static HashMap<String, Integer> sTagCountMap = new HashMap<>();
    private String mFileTitle;
    private File mOutputFile;
    private String mTag = "SystemDumpWriter_";
    private SimpleDateFormat mFormat =
            new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
    private final LinkedList<String> mDumpRequests = new LinkedList<>();

    public SystemDumpWriter(String tag, boolean savedFileNameDate) {
        this.mTag += tag;
        StringBuilder sb = new StringBuilder();
        sb.append(FILE_DUMPSYS).append(tag).append(Session.SESSION_SEPARATION_CHAR_CHILD);
        if (savedFileNameDate) {
            sb.append(this.mFormat.format(new Date()));
        } else {
            sb.append(TAG_WM);
        }
        this.mFileTitle = sb.append(".txt").toString();
        this.mOutputFile = new File(Environment.getDataDirectory(), this.mFileTitle);
        deleteOutputFileIfNeeded(tag);
        addDateFormat(tag);
    }

    public void requestDump(String serviceName) {
        this.mDumpRequests.add(serviceName);
    }

    private void deleteOutputFileIfNeeded(String tag) {
        if (sTagCountMap.get(tag).intValue() == 1 && this.mOutputFile.exists()) {
            this.mOutputFile.delete();
        }
    }

    private void addDateFormat(String tag) {
        OutputStreamWriter fos = null;
        boolean exists = this.mOutputFile.exists();
        try {
            try {
                try {
                    FileOutputStream out = new FileOutputStream(this.mOutputFile, exists);
                    fos = new OutputStreamWriter(out, StandardCharsets.UTF_8);
                    if (exists) {
                        fos.write(10);
                        fos.write(10);
                    }
                    String startContent =
                            tag
                                    + " #"
                                    + sTagCountMap.get(tag)
                                    + " "
                                    + this.mFormat.format(new Date());
                    fos.write(startContent);
                    fos.close();
                } catch (IOException e) {
                    Slog.e(this.mTag, "close exception, ", e);
                    if (fos != null) {
                        fos.close();
                    }
                }
            } catch (Throwable th) {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e2) {
                        Slog.e(this.mTag, "close exception, ", e2);
                    }
                }
                throw th;
            }
        } catch (IOException e3) {
            Slog.e(this.mTag, "close exception, ", e3);
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() throws Exception {
        long creationStartTime = SystemClock.uptimeMillis();
        try {
            FileOutputStream out =
                    new FileOutputStream(this.mOutputFile, this.mOutputFile.exists());
            try {
                Iterator<String> it = this.mDumpRequests.iterator();
                while (it.hasNext()) {
                    String service = it.next();
                    Debug.dumpService(service, out.getFD(), new String[] {"-a"});
                }
                Slog.d(this.mTag, "Successful to save dumpsys to " + this.mFileTitle);
                out.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.e(this.mTag, "close exception, " + e);
        }
        Slog.d(
                this.mTag,
                "save dumpsys, duration=" + (SystemClock.uptimeMillis() - creationStartTime));
    }

    public static void saveDumpsysFiles(String tag, boolean savedFileNameDate) {
        updateCount(tag);
        try {
            SystemDumpWriter writer = new SystemDumpWriter(tag, savedFileNameDate);
            try {
                writer.requestDump(Context.WINDOW_SERVICE);
                writer.requestDump("activity");
                writer.requestDump(Context.DISPLAY_SERVICE);
                writer.requestDump("input");
                writer.requestDump("SurfaceFlinger");
                writer.close();
            } finally {
            }
        } catch (Exception e) {
        }
    }

    private static void updateCount(String tag) {
        if (sTagCountMap.size() == 0) {
            sTagCountMap.put(tag, 0);
        }
        int count = sTagCountMap.get(tag).intValue() + 1;
        if (count > 3) {
            count = 1;
        }
        sTagCountMap.put(tag, Integer.valueOf(count));
    }
}
