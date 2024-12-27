package com.android.systemui.pluginlock.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.util.DelayableMarqueeTextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
            DumpUtils.this.writeDump();
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
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            bufferedReader.close();
                            fileInputStream.close();
                            return sb.toString();
                        }
                        sb.append(readLine);
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

    /* JADX WARN: Removed duplicated region for block: B:14:0x0035 A[Catch: all -> 0x001c, TryCatch #0 {all -> 0x001c, blocks: (B:3:0x0005, B:5:0x0010, B:7:0x0019, B:8:0x001f, B:9:0x0025, B:12:0x002d, B:14:0x0035, B:19:0x0041), top: B:2:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getDump() {
        /*
            r7 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r7 = r7.getDumpFromFile()     // Catch: java.lang.Throwable -> L1c
            java.lang.String r1 = com.android.systemui.pluginlock.utils.DumpUtils.SEPARATOR     // Catch: java.lang.Throwable -> L1c
            r2 = 200(0xc8, float:2.8E-43)
            r3 = 0
            if (r1 == 0) goto L1e
            java.lang.String[] r1 = r7.split(r1)     // Catch: java.lang.Throwable -> L1c
            int r1 = r1.length     // Catch: java.lang.Throwable -> L1c
            int r4 = r1 + (-1)
            if (r4 <= r2) goto L1e
            int r1 = r1 + (-201)
            goto L1f
        L1c:
            r7 = move-exception
            goto L49
        L1e:
            r1 = r3
        L1f:
            java.util.Scanner r4 = new java.util.Scanner     // Catch: java.lang.Throwable -> L1c
            r4.<init>(r7)     // Catch: java.lang.Throwable -> L1c
            r7 = r3
        L25:
            boolean r5 = r4.hasNextLine()     // Catch: java.lang.Throwable -> L1c
            if (r5 == 0) goto L41
            if (r3 >= r2) goto L41
            java.lang.String r5 = r4.nextLine()     // Catch: java.lang.Throwable -> L1c
            int r6 = r7 + 1
            if (r7 < r1) goto L3f
            r0.append(r5)     // Catch: java.lang.Throwable -> L1c
            java.lang.String r7 = com.android.systemui.pluginlock.utils.DumpUtils.SEPARATOR     // Catch: java.lang.Throwable -> L1c
            r0.append(r7)     // Catch: java.lang.Throwable -> L1c
            int r3 = r3 + 1
        L3f:
            r7 = r6
            goto L25
        L41:
            r4.close()     // Catch: java.lang.Throwable -> L1c
            java.lang.String r7 = r0.toString()     // Catch: java.lang.Throwable -> L1c
            return r7
        L49:
            java.lang.String r1 = com.android.systemui.pluginlock.utils.DumpUtils.SEPARATOR
            r0.append(r1)
            java.lang.String r7 = r7.toString()
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.pluginlock.utils.DumpUtils.getDump():java.lang.String");
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
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("writeDump, created: ", TAG, file.mkdirs());
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
