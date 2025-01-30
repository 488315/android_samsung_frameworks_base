package android.p009os;

import android.telecom.Logging.Session;
import android.util.Log;
import com.android.internal.util.FastPrintWriter;
import java.io.FileOutputStream;
import java.io.PrintWriter;
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

    public String addTrace(Throwable tr) {
        String trace = Log.getStackTraceString(tr);
        synchronized (this) {
            if (this.mTraces.containsKey(trace)) {
                Map<String, Long> map = this.mTraces;
                map.put(trace, Long.valueOf(map.get(trace).longValue() + 1));
            } else {
                this.mTraces.put(trace, 1L);
            }
        }
        return trace;
    }

    public void addTimeStamp(Throwable tr, long time, long duration, boolean isOneway) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        StringBuilder b = new StringBuilder(128);
        b.append(sdf.format(new Date(time)));
        b.append(String.format(", %.6f", Double.valueOf(duration * 1.0E-6d)));
        if (isOneway) {
            b.append(", oneway");
        }
        String timestamp = b.toString();
        synchronized (this) {
            String traceKey = addTrace(tr);
            ArrayList<String> arr = this.mTimestamp.get(traceKey);
            if (arr == null) {
                arr = new ArrayList<>();
                this.mTimestamp.put(traceKey, arr);
            }
            arr.add(timestamp);
        }
    }

    public void writeTracesToFile(ParcelFileDescriptor fd) {
        if (this.mTraces.isEmpty()) {
            return;
        }
        PrintWriter pw = new FastPrintWriter(new FileOutputStream(fd.getFileDescriptor()));
        synchronized (this) {
            long processTotal = this.mTraces.keySet().size();
            int index = 1;
            for (String trace : this.mTraces.keySet()) {
                if (Binder.isSystemServerBinderTrackerEnabled) {
                    long stackTotal = this.mTraces.get(trace).longValue();
                    pw.println("Count: " + stackTotal + "," + index + "/" + processTotal);
                    int timeIndex = 1;
                    ArrayList<String> arr = this.mTimestamp.get(trace);
                    if (arr != null) {
                        Iterator<String> it = arr.iterator();
                        while (it.hasNext()) {
                            String timestamp = it.next();
                            pw.println(this.processName + " T:" + timeIndex + "/" + stackTotal + Session.SUBSESSION_SEPARATION_CHAR + timestamp);
                            timeIndex++;
                        }
                    }
                    trace = this.processName + "," + this.packageName + "," + this.pid + "," + this.uid + "\n" + trace;
                } else {
                    pw.println("Count: " + this.mTraces.get(trace));
                }
                index++;
                pw.println("Trace: " + trace);
                pw.println();
            }
        }
        pw.flush();
    }

    public void clearTraces() {
        resetTraces();
    }

    public void setBinderInfo(int _pid, int _uid, String _processName, String _packageName) {
        this.pid = _pid;
        this.uid = _uid;
        this.processName = _processName;
        this.packageName = _packageName;
    }
}
