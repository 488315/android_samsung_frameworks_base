package com.android.server.am.mars;

import android.os.SystemProperties;

/* loaded from: classes.dex */
public class HistoryBuffer {
    public String[] buffer;
    public int totalSize;
    public int size = 0;
    public int pointer = 0;

    public HistoryBuffer() {
        String[] split;
        int i = 0;
        this.totalSize = 0;
        try {
            String str = SystemProperties.get("dalvik.vm.heapsize", "");
            if (str != null && (split = str.split("m")) != null && split.length >= 1) {
                i = Integer.parseInt(split[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i2 = i >= 128 ? 5000 : 1000;
        this.totalSize = i2;
        this.buffer = new String[i2];
    }

    public synchronized void put(String str) {
        String[] strArr = this.buffer;
        int i = this.pointer;
        strArr[i] = str;
        int i2 = this.totalSize;
        this.pointer = (i + 1) % i2;
        int i3 = this.size;
        if (i3 < i2) {
            this.size = i3 + 1;
        }
    }

    public synchronized String[] getBuffer() {
        int i = this.size;
        if (i < this.totalSize) {
            return this.buffer;
        }
        String[] strArr = new String[i];
        int i2 = this.pointer;
        int i3 = 0;
        int i4 = 0;
        while (i2 < this.size) {
            strArr[i4] = this.buffer[i2];
            i2++;
            i4++;
        }
        while (i3 < this.pointer) {
            int i5 = i4 + 1;
            strArr[i4] = this.buffer[i3];
            i3++;
            i4 = i5;
        }
        return strArr;
    }

    public int getSize() {
        return this.size;
    }
}
