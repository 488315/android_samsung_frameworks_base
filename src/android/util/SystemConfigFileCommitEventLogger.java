package android.util;

import android.annotation.SystemApi;
import android.os.SystemClock;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes4.dex */
public class SystemConfigFileCommitEventLogger {
    private final String mName;
    private long mStartTime;

    public SystemConfigFileCommitEventLogger(String str) {
        this.mName = str;
    }

    public void setStartTime(long j) {
        this.mStartTime = j;
    }

    public void onStartWrite() {
        if (this.mStartTime == 0) {
            this.mStartTime = SystemClock.uptimeMillis();
        }
    }

    public void onFinishWrite() {
        writeLogRecord(SystemClock.uptimeMillis() - this.mStartTime);
    }

    public void writeLogRecord(long j) {
        com.android.internal.logging.EventLogTags.writeCommitSysConfigFile(this.mName, j);
    }
}
