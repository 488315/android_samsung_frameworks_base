package android.os;

import android.telecom.Logging.Session;
import android.util.Log;
import com.android.internal.util.FastPrintWriter;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public class TransactionTracker {
    private Map<String, ArrayList<String>> mTimestamp;
    private Map<String, Long> mTraces;
    private String packageName;
    private int pid;
    private String processName;
    private int uid;

    private void resetTraces() {
        synchronized (this) {
            this.mTraces = new HashMap();
            this.mTimestamp = new HashMap();
        }
    }

    TransactionTracker() {
        resetTraces();
    }

    public String addTrace(Throwable th) {
        String stackTraceString = Log.getStackTraceString(th);
        synchronized (this) {
            if (this.mTraces.containsKey(stackTraceString)) {
                Map<String, Long> map = this.mTraces;
                map.put(stackTraceString, Long.valueOf(map.get(stackTraceString).longValue() + 1));
            } else {
                this.mTraces.put(stackTraceString, 1L);
            }
        }
        return stackTraceString;
    }

    public void addTimeStamp(Throwable th, long j, long j2, boolean z) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        StringBuilder sb = new StringBuilder(128);
        sb.append(simpleDateFormat.format(new Date(j)));
        sb.append(String.format(", %.6f", Double.valueOf(j2 * 1.0E-6d)));
        if (z) {
            sb.append(", oneway");
        }
        String string = sb.toString();
        synchronized (this) {
            String strAddTrace = addTrace(th);
            ArrayList<String> arrayList = this.mTimestamp.get(strAddTrace);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.mTimestamp.put(strAddTrace, arrayList);
            }
            arrayList.add(string);
        }
    }

    public void writeTracesToFile(ParcelFileDescriptor parcelFileDescriptor) {
        if (this.mTraces.isEmpty()) {
            return;
        }
        FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
        synchronized (this) {
            long size = this.mTraces.keySet().size();
            int i = 1;
            for (String str : this.mTraces.keySet()) {
                if (Binder.isSystemServerBinderTrackerEnabled) {
                    long jLongValue = this.mTraces.get(str).longValue();
                    fastPrintWriter.println("Count: " + jLongValue + "," + i + "/" + size);
                    ArrayList<String> arrayList = this.mTimestamp.get(str);
                    if (arrayList != null) {
                        Iterator<String> it = arrayList.iterator();
                        int i2 = 1;
                        while (it.hasNext()) {
                            fastPrintWriter.println(this.processName + " T:" + i2 + "/" + jLongValue + Session.SUBSESSION_SEPARATION_CHAR + it.next());
                            i2++;
                        }
                    }
                    str = this.processName + "," + this.packageName + "," + this.pid + "," + this.uid + ShaderAssembler.NEWLINE + str;
                } else {
                    fastPrintWriter.println("Count: " + this.mTraces.get(str));
                }
                i++;
                fastPrintWriter.println("Trace: " + str);
                fastPrintWriter.println();
            }
        }
        fastPrintWriter.flush();
    }

    public void clearTraces() {
        resetTraces();
    }

    public void setBinderInfo(int i, int i2, String str, String str2) {
        this.pid = i;
        this.uid = i2;
        this.processName = str;
        this.packageName = str2;
    }
}
