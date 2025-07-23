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

    public SystemHistory(int i, String str) {
        this.mLogMaxCount = i;
        this.mTag = str;
    }

    public void add(String str) {
        if (this.mEnableLog) {
            Log.i(this.mTag, str);
        }
        if (discardOldest()) {
            this.mLogQueue.offer(this.mFormat.format(new Date()) + "\n [" + this.mTag + "] " + str);
        }
    }

    public void enableLog(boolean z) {
        this.mEnableLog = z;
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

    public void dump(PrintWriter printWriter) {
        for (int size = this.mLogQueue.size() - 1; size >= 0; size += -1) {
            printWriter.println("#" + (size + 1) + " " + this.mLogQueue.get(size));
            printWriter.println();
        }
    }
}
