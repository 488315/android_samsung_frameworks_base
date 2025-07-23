package android.app.slice;

import android.content.Context;
import android.metrics.LogMaker;
import android.net.Uri;
import com.android.internal.logging.MetricsLogger;

@Deprecated
/* loaded from: classes.dex */
public class SliceMetrics {
    private static final String TAG = "SliceMetrics";
    private LogMaker mLogMaker;
    private MetricsLogger mMetricsLogger = new MetricsLogger();

    public SliceMetrics(Context context, Uri uri) {
        LogMaker logMaker = new LogMaker(0);
        this.mLogMaker = logMaker;
        logMaker.addTaggedData(1402, uri.getAuthority());
        this.mLogMaker.addTaggedData(1403, uri.getPath());
    }

    public void logVisible() {
        synchronized (this.mLogMaker) {
            this.mLogMaker.setCategory(1401).setType(1);
            this.mMetricsLogger.write(this.mLogMaker);
        }
    }

    public void logHidden() {
        synchronized (this.mLogMaker) {
            this.mLogMaker.setCategory(1401).setType(2);
            this.mMetricsLogger.write(this.mLogMaker);
        }
    }

    public void logTouch(int i, Uri uri) {
        synchronized (this.mLogMaker) {
            this.mLogMaker.setCategory(1401).setType(4).addTaggedData(1404, uri.getAuthority()).addTaggedData(1405, uri.getPath());
            this.mMetricsLogger.write(this.mLogMaker);
        }
    }
}
