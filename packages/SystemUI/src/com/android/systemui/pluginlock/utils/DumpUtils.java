package com.android.systemui.pluginlock.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.util.DelayableMarqueeTextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

/* loaded from: classes2.dex */
public class DumpUtils {
    private static final String DUMP_DIR = "/data/user_de/0/com.android.systemui/files/pluginlock/";
    private static final String DUMP_FILE = "dump.txt";
    private static final String DUMP_PATH = "/data/user_de/0/com.android.systemui/files/pluginlock/dump.txt";
    private static final int MAX_READ_LINE = 200;
    private static final long MAX_SAVE_BYTE = 102400;
    private static final String PLUGIN_LOCK_EVENT_DUMP = "plugin_lock_event_dump";
    private static final String SEPARATOR = System.getProperty("line.separator");
    private static final String TAG = "DumpUtils";
    private final Context mContext;
    private final HandlerExecutor mHandlerExecutor = new HandlerExecutor();
    private final Runnable mWriteRunnable = new Runnable() { // from class: com.android.systemui.pluginlock.utils.DumpUtils$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.writeDump();
        }
    };
    private String mDumpString = "";

    class HandlerExecutor {
        private final Handler mHandler;

        public HandlerExecutor() {
            HandlerThread handlerThread = new HandlerThread("DumpUtilsThread");
            handlerThread.start();
            this.mHandler = new Handler(handlerThread.getLooper());
        }

        public void execute(Runnable runnable, long j) {
            if (this.mHandler.postDelayed(runnable, j)) {
                return;
            }
            Log.w(DumpUtils.TAG, "execute failed");
        }

        public void remove(Runnable runnable) {
            this.mHandler.removeCallbacks(runnable);
        }
    }

    public DumpUtils(Context context) {
        this.mContext = context;
    }

    private String buildDumpString(String str) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(date) + " | " + str.replace("\n", "");
    }

    private String getDumpFromFile() {
        try {
            File file = new File(DUMP_PATH);
            if (!file.exists()) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            bufferedReader.close();
                            fileInputStream.close();
                            return sb.toString();
                        }
                        sb.append(line);
                        sb.append(SEPARATOR);
                    } finally {
                    }
                }
            } finally {
            }
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    public void addEvent(String str, String str2) {
        if (str2 == null || str2.isEmpty()) {
            return;
        }
        try {
            this.mHandlerExecutor.remove(this.mWriteRunnable);
            this.mDumpString += buildDumpString(str2) + SEPARATOR;
            this.mHandlerExecutor.execute(this.mWriteRunnable, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String getDump() {
        int i;
        StringBuilder sb = new StringBuilder();
        try {
            String dumpFromFile = getDumpFromFile();
            String str = SEPARATOR;
            int i2 = 0;
            if (str != null) {
                int length = dumpFromFile.split(str).length;
                i = length + (-1) > 200 ? length - 201 : 0;
            }
            Scanner scanner = new Scanner(dumpFromFile);
            int i3 = 0;
            while (scanner.hasNextLine() && i2 < 200) {
                String strNextLine = scanner.nextLine();
                int i4 = i3 + 1;
                if (i3 >= i) {
                    sb.append(strNextLine);
                    sb.append(SEPARATOR);
                    i2++;
                }
                i3 = i4;
            }
            scanner.close();
            return sb.toString();
        } catch (Throwable th) {
            sb.append(SEPARATOR);
            sb.append(th.toString());
            return sb.toString();
        }
    }

    public String getDumpLegacy() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        String string = Settings.Secure.getString(contentResolver, PLUGIN_LOCK_EVENT_DUMP);
        if (string != null) {
            Settings.Secure.putString(contentResolver, PLUGIN_LOCK_EVENT_DUMP, null);
        }
        return string;
    }

    public void writeDump() {
        Log.d(TAG, "writeDump");
        File file = new File(DUMP_DIR);
        if (!file.exists()) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("writeDump, created: ", TAG, file.mkdirs());
        }
        String str = this.mDumpString;
        this.mDumpString = "";
        File file2 = new File(DUMP_PATH);
        boolean z = file2.length() < MAX_SAVE_BYTE;
        if (!z) {
            str = getDump() + str;
        }
        try {
            FileWriter fileWriter = new FileWriter(file2, z);
            try {
                fileWriter.append((CharSequence) str);
                fileWriter.flush();
                fileWriter.close();
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
