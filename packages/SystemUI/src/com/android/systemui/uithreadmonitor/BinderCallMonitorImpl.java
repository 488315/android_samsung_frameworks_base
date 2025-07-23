package com.android.systemui.uithreadmonitor;

import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseArray;
import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.util.SettingsHelper;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BinderCallMonitorImpl implements BinderCallMonitor {
    public static int sSkipCallCount = -1;
    public final CopyOnWriteArrayList mBinderCallHistory;
    private final SettingsHelper.OnChangedCallback mBinderCallMonitorCallback;
    public int mBinderCallMonitorState;
    public SamsungServiceLogger mLogger;
    private SettingsHelper mSettingsHelper;
    public final SparseArray mMonitorInfo = new SparseArray();
    public long mDuration = 1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Item {
        public final long compareDuration;
        public String stackTrace;
        public final long startTime;

        public /* synthetic */ Item(long j, int i) {
            this(j);
        }

        private Item(long j) {
            this.compareDuration = j;
            this.startTime = System.nanoTime();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MonitorInfo {
        public long duration;
        public boolean enabled;
        public boolean infinite;
        public long timeOut;

        public /* synthetic */ MonitorInfo(int i) {
            this();
        }

        private MonitorInfo() {
            this.enabled = false;
            this.infinite = false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v14, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.io.FileWriter, java.io.Writer] */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.io.FileWriter] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.io.FileWriter] */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:63:0x0068 -> B:25:0x0087). Please report as a decompilation issue!!! */
    public static void $r8$lambda$YnL46qd_6WOhuhaXmSdyADsAPU8(BinderCallMonitorImpl binderCallMonitorImpl) {
        ?? hasNext;
        int binderCallMonitor = binderCallMonitorImpl.mSettingsHelper.getBinderCallMonitor();
        if (binderCallMonitor == binderCallMonitorImpl.mBinderCallMonitorState) {
            Log.d("BinderCallMonitor", "Nothing to do for BinderCallMonitor");
            return;
        }
        binderCallMonitorImpl.mBinderCallMonitorState = binderCallMonitor;
        ?? r1 = 1;
        r1 = 1;
        if (binderCallMonitor == 1) {
            binderCallMonitorImpl.mBinderCallHistory.clear();
            new Handler().postDelayed(new Runnable() { // from class: com.android.systemui.uithreadmonitor.BinderCallMonitorImpl.1
                @Override // java.lang.Runnable
                public final void run() {
                    Log.d("BinderCallMonitor", "BinderCallMonitor history saving timeout expired!!!");
                    BinderCallMonitorImpl.this.mSettingsHelper.resetBinderCallMonitor();
                }
            }, 10000L);
            return;
        }
        Log.d("BinderCallMonitor", "writeBinderCallHistory started");
        BufferedWriter bufferedWriter = null;
        bufferedWriter = null;
        bufferedWriter = null;
        bufferedWriter = null;
        bufferedWriter = null;
        try {
            try {
                try {
                    r1 = new FileWriter("/mnt/sdcard/binder_call_history.txt");
                    try {
                        BufferedWriter bufferedWriter2 = new BufferedWriter(r1);
                        try {
                            Iterator it = binderCallMonitorImpl.mBinderCallHistory.iterator();
                            while (true) {
                                hasNext = it.hasNext();
                                if (hasNext != 0) {
                                    bufferedWriter2.write((String) it.next());
                                } else {
                                    try {
                                        break;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            bufferedWriter2.close();
                            r1.close();
                            bufferedWriter = hasNext;
                            r1 = r1;
                        } catch (IOException e2) {
                            e = e2;
                            bufferedWriter = bufferedWriter2;
                            e.printStackTrace();
                            if (bufferedWriter != null) {
                                try {
                                    bufferedWriter.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            if (r1 != 0) {
                                r1.close();
                                bufferedWriter = bufferedWriter;
                                r1 = r1;
                            }
                        } catch (Throwable th) {
                            th = th;
                            bufferedWriter = bufferedWriter2;
                            if (bufferedWriter != null) {
                                try {
                                    bufferedWriter.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            if (r1 == 0) {
                                throw th;
                            }
                            try {
                                r1.close();
                                throw th;
                            } catch (IOException e5) {
                                e5.printStackTrace();
                                throw th;
                            }
                        }
                    } catch (IOException e6) {
                        e = e6;
                    }
                } catch (IOException e7) {
                    e = e7;
                    r1 = 0;
                } catch (Throwable th2) {
                    th = th2;
                    r1 = 0;
                }
            } catch (IOException e8) {
                e8.printStackTrace();
                bufferedWriter = bufferedWriter;
                r1 = r1;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* renamed from: -$$Nest$smgetCallers, reason: not valid java name */
    public static String m3110$$Nest$smgetCallers(Item item) {
        String str;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        if (sSkipCallCount == -1) {
            int length = stackTrace.length;
            int i = 0;
            int i2 = 0;
            StackTraceElement stackTraceElement = null;
            while (true) {
                if (i >= length) {
                    i2 = 2;
                    break;
                }
                StackTraceElement stackTraceElement2 = stackTrace[i];
                i2++;
                if (stackTraceElement != null && stackTraceElement.getMethodName().endsWith("onTransactEnded") && stackTraceElement2 != null && stackTraceElement2.getMethodName().endsWith("transact")) {
                    break;
                }
                i++;
                stackTraceElement = stackTraceElement2;
            }
            sSkipCallCount = i2;
        }
        String str2 = null;
        for (int i3 = 0; i3 < 20; i3++) {
            int i4 = sSkipCallCount + i3;
            if (i4 >= stackTrace.length) {
                str = null;
            } else {
                StackTraceElement stackTraceElement3 = stackTrace[i4];
                str = stackTraceElement3.getClassName() + "." + stackTraceElement3.getMethodName() + ":" + stackTraceElement3.getLineNumber();
            }
            if (str == null) {
                break;
            }
            if (i3 == 0) {
                str2 = str;
            }
            sb.append("    ");
            sb.append(str);
            sb.append('\n');
        }
        item.stackTrace = sb.toString();
        return str2;
    }

    public BinderCallMonitorImpl(SettingsHelper settingsHelper) {
        SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.uithreadmonitor.BinderCallMonitorImpl$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                BinderCallMonitorImpl.$r8$lambda$YnL46qd_6WOhuhaXmSdyADsAPU8(BinderCallMonitorImpl.this);
            }
        };
        this.mBinderCallMonitorCallback = onChangedCallback;
        this.mBinderCallMonitorState = 0;
        this.mBinderCallHistory = new CopyOnWriteArrayList();
        this.mSettingsHelper = settingsHelper;
        settingsHelper.registerCallback(onChangedCallback, Settings.System.getUriFor(SettingsHelper.BINDER_CALL_MONITOR));
    }

    public final boolean startMonitoring(int i, long j, long j2) {
        int i2 = 0;
        if (i < 0 || i >= 6 || j < 1 || j2 < 3000 || j2 > 8000) {
            Log.w("BinderCallMonitor", "not monitoring started");
            return false;
        }
        synchronized (this.mMonitorInfo) {
            try {
                MonitorInfo monitorInfo = (MonitorInfo) this.mMonitorInfo.get(i);
                if (monitorInfo == null) {
                    monitorInfo = new MonitorInfo(i2);
                }
                monitorInfo.duration = j * 1000000;
                if (i == 0) {
                    monitorInfo.infinite = true;
                } else {
                    monitorInfo.timeOut = System.currentTimeMillis() + j2;
                }
                monitorInfo.enabled = true;
                this.mMonitorInfo.put(i, monitorInfo);
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }
}
