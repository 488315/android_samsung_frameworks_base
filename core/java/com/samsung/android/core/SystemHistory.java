package com.samsung.android.core;

import android.util.Log;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/* loaded from: classes6.dex */
public class SystemHistory {
    int mLogMaxCount;
    String mTag;
    private final LinkedList<String> mLogQueue = new LinkedList<>();
    private boolean mEnableLog = true;
    private SimpleDateFormat mFormat = new SimpleDateFormat("<< MM-dd HH:mm:ss.SSS >>");

    public SystemHistory(int logMaxCount, String tag) {
        this.mLogMaxCount = logMaxCount;
        this.mTag = tag;
    }

    public void add(String log) {
        if (this.mEnableLog) {
            Log.i(this.mTag, log);
        }
        if (!discardOldest()) {
            return;
        }
        this.mLogQueue.offer(this.mFormat.format(new Date()) + "\n [" + this.mTag + "] " + log);
    }

    public void enableLog(boolean enableLog) {
        this.mEnableLog = enableLog;
    }

    private boolean discardOldest() {
        if (this.mLogMaxCount <= 0) {
            return false;
        }
        while (this.mLogMaxCount < this.mLogQueue.size() + 1) {
            this.mLogQueue.poll();
        }
        return true;
    }

    public void dump(PrintWriter pw) {
        for (int i = this.mLogQueue.size() - 1; i >= 0; i--) {
            pw.println("#" + (i + 1) + " " + this.mLogQueue.get(i));
            pw.println();
        }
    }
}
