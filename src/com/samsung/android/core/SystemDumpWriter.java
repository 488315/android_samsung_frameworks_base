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

/* loaded from: classes6.dex */
public class SystemDumpWriter implements AutoCloseable {
    private static final String FILE_DUMPSYS = "log/dumpsys_";
    private static final int MAX_COUNT_SAVED = 3;
    private static final String TAG_WM = "WindowManager";
    private static HashMap<String, Integer> sTagCountMap = new HashMap<>();
    private String mFileTitle;
    private File mOutputFile;
    private String mTag = "SystemDumpWriter_";
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
    private final LinkedList<String> mDumpRequests = new LinkedList<>();

    public SystemDumpWriter(String str, boolean z) throws Throwable {
        this.mTag += str;
        StringBuilder sb = new StringBuilder(FILE_DUMPSYS);
        sb.append(str);
        sb.append(Session.SESSION_SEPARATION_CHAR_CHILD);
        if (z) {
            sb.append(this.mFormat.format(new Date()));
        } else {
            sb.append(TAG_WM);
        }
        sb.append(".txt");
        this.mFileTitle = sb.toString();
        this.mOutputFile = new File(Environment.getDataDirectory(), this.mFileTitle);
        deleteOutputFileIfNeeded(str);
        addDateFormat(str);
    }

    public void requestDump(String str) {
        this.mDumpRequests.add(str);
    }

    private void deleteOutputFileIfNeeded(String str) {
        if (sTagCountMap.get(str).intValue() == 1 && this.mOutputFile.exists()) {
            this.mOutputFile.delete();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8 */
    private void addDateFormat(String str) throws Throwable {
        boolean zExists = this.mOutputFile.exists();
        OutputStreamWriter outputStreamWriter = null;
        try {
            try {
                try {
                    OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(new FileOutputStream(this.mOutputFile, zExists), StandardCharsets.UTF_8);
                    if (zExists) {
                        try {
                            outputStreamWriter2.write(10);
                            outputStreamWriter2.write(10);
                        } catch (IOException e) {
                            e = e;
                            outputStreamWriter = outputStreamWriter2;
                            Slog.e(this.mTag, "close exception, ", e);
                            this = this;
                            if (outputStreamWriter != null) {
                                outputStreamWriter.close();
                                this = this;
                            }
                        } catch (Throwable th) {
                            th = th;
                            outputStreamWriter = outputStreamWriter2;
                            if (outputStreamWriter != null) {
                                try {
                                    outputStreamWriter.close();
                                } catch (IOException e2) {
                                    Slog.e(this.mTag, "close exception, ", e2);
                                }
                            }
                            throw th;
                        }
                    }
                    outputStreamWriter2.write(str + " #" + sTagCountMap.get(str) + " " + this.mFormat.format(new Date()));
                    outputStreamWriter2.close();
                } catch (IOException e3) {
                    String str2 = this.mTag;
                    Slog.e(str2, "close exception, ", e3);
                    this = str2;
                }
            } catch (IOException e4) {
                e = e4;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() throws Exception {
        long jUptimeMillis = SystemClock.uptimeMillis();
        try {
            File file = this.mOutputFile;
            FileOutputStream fileOutputStream = new FileOutputStream(file, file.exists());
            try {
                Iterator<String> it = this.mDumpRequests.iterator();
                while (it.hasNext()) {
                    Debug.dumpService(it.next(), fileOutputStream.getFD(), new String[]{"-a"});
                }
                Slog.d(this.mTag, "Successful to save dumpsys to " + this.mFileTitle);
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.e(this.mTag, "close exception, " + e);
        }
        Slog.d(this.mTag, "save dumpsys, duration=" + (SystemClock.uptimeMillis() - jUptimeMillis));
    }

    public static void saveDumpsysFiles(String str, boolean z) {
        updateCount(str);
        try {
            SystemDumpWriter systemDumpWriter = new SystemDumpWriter(str, z);
            try {
                systemDumpWriter.requestDump(Context.WINDOW_SERVICE);
                systemDumpWriter.requestDump("activity");
                systemDumpWriter.requestDump(Context.DISPLAY_SERVICE);
                systemDumpWriter.requestDump("input");
                systemDumpWriter.requestDump("SurfaceFlinger");
                systemDumpWriter.close();
            } finally {
            }
        } catch (Exception unused) {
        }
    }

    private static void updateCount(String str) {
        if (sTagCountMap.size() == 0) {
            sTagCountMap.put(str, 0);
        }
        int iIntValue = sTagCountMap.get(str).intValue() + 1;
        sTagCountMap.put(str, Integer.valueOf(iIntValue <= 3 ? iIntValue : 1));
    }
}
