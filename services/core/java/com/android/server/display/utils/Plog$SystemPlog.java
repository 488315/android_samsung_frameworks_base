package com.android.server.display.utils;

import android.util.Slog;

public final class Plog$SystemPlog {
    public long mId;
    public final String mTag = "BrightnessMappingStrategy";

    public final void logCurve(String str, float[] fArr, float[] fArr2) {
        StringBuilder sb = new StringBuilder();
        sb.append("curve: " + str + ": [");
        int length = fArr.length <= fArr2.length ? fArr.length : fArr2.length;
        for (int i = 0; i < length; i++) {
            sb.append("(" + fArr[i] + "," + fArr2[i] + "),");
        }
        sb.append("]");
        write(sb.toString());
    }

    public final void logPoint(String str, float f, float f2) {
        write("point: " + str + ": (" + f + "," + f2 + ")");
    }

    public final void start(String str) {
        this.mId = System.currentTimeMillis();
        write("title: ".concat(str));
    }

    public final void write(String str) {
        Slog.d(this.mTag, "[PLOG " + this.mId + "] " + str);
    }
}
