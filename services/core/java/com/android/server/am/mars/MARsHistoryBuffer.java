package com.android.server.am.mars;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class MARsHistoryBuffer {
    public String[] buffer;
    public int pointer;
    public int size;

    public abstract class MARsHistoryBufferHolder {
        public static final MARsHistoryBuffer INSTANCE = new MARsHistoryBuffer();
    }

    public MARsHistoryBuffer() {
        this.size = 0;
        this.pointer = 0;
    }

    public static MARsHistoryBuffer getInstance() {
        return MARsHistoryBufferHolder.INSTANCE;
    }

    public synchronized void put(String str) {
        String[] strArr = this.buffer;
        int i = this.pointer;
        int i2 = i + 1;
        this.pointer = i2;
        strArr[i] = str;
        if (i2 >= this.size) {
            MARsHistoryLog.getInstance().saveLogToFile(true, false);
            this.pointer = 0;
        }
    }

    public void init() {
        this.size = 1000;
        this.buffer = new String[1000];
    }

    public synchronized ArrayList getLog() {
        return MARsHistoryLog.getInstance().getLog();
    }

    public String getBufferLine(int i) {
        return this.buffer[i];
    }

    public int getPointer() {
        return this.pointer;
    }
}
