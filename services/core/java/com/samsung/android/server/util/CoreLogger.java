package com.samsung.android.server.util;

import android.util.Slog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CoreLogger {
    public final List mBuffer;
    public final boolean mBufferOverflowAllowed;
    public final int mBufferSize;
    public final String mDumpTitle;
    public final String mTag;
    public final boolean mUseTimeline;

    public CoreLogger(String str, int i, String str2, boolean z, boolean z2) {
        this.mTag = str;
        this.mDumpTitle = str2;
        this.mBufferSize = i;
        this.mBufferOverflowAllowed = z;
        this.mBuffer = i > 0 ? new ArrayList(i) : null;
        this.mUseTimeline = z2;
    }

    public final void log(int i, String str, Throwable th) {
        if (this.mBuffer == null) {
            Slog.println(i, this.mTag, str);
        }
        if (th != null) {
            th.printStackTrace();
        }
        List list = this.mBuffer;
        if (list == null) {
            return;
        }
        if (!this.mBufferOverflowAllowed) {
            synchronized (list) {
                try {
                    if (this.mBuffer.size() > this.mBufferSize) {
                        return;
                    }
                } finally {
                }
            }
        }
        if (this.mUseTimeline) {
            Calendar calendar = Calendar.getInstance();
            str =
                    String.format(
                            Locale.US,
                            "%02d-%02d %02d:%02d:%02d.%03d %s",
                            Integer.valueOf(calendar.get(2) + 1),
                            Integer.valueOf(calendar.get(5)),
                            Integer.valueOf(calendar.get(11)),
                            Integer.valueOf(calendar.get(12)),
                            Integer.valueOf(calendar.get(13)),
                            Integer.valueOf(calendar.get(14)),
                            str);
        }
        try {
            synchronized (this.mBuffer) {
                try {
                    if (this.mBuffer.size() > this.mBufferSize) {
                        this.mBuffer.remove(0);
                    }
                    this.mBuffer.add(str);
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        } catch (Exception e) {
            Slog.w(this.mTag, "Fail to add logs", e);
        }
    }
}
