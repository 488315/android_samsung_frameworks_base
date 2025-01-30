package com.samsung.android.server.util;

import android.util.Slog;
import com.android.server.wm.WindowManagerServiceExt;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class CoreLogger {
    public final List mBuffer;
    public final boolean mBufferOverflowAllowed;
    public final int mBufferSize;
    public final String mDumpTitle;
    public final String mTag;
    public final boolean mUseTimeline;

    public static Builder getBuilder() {
        return new Builder();
    }

    public class Builder {
        public boolean mBufferOverflowAllowed;
        public int mBufferSize;
        public String mDumpTitle;
        public String mTag;
        public boolean mUseTimeline;

        public Builder() {
            String simpleName = CoreLogger.class.getSimpleName();
            this.mTag = simpleName;
            this.mDumpTitle = simpleName;
            this.mBufferOverflowAllowed = true;
            this.mUseTimeline = true;
        }

        public CoreLogger build() {
            return new CoreLogger(this.mTag, this.mDumpTitle, this.mBufferSize, this.mBufferOverflowAllowed, this.mUseTimeline);
        }

        public Builder setTag(String str) {
            this.mTag = str;
            return this;
        }

        public Builder setDumpTitle(String str) {
            this.mDumpTitle = str;
            return this;
        }

        public Builder setBufferSize(int i) {
            this.mBufferSize = i;
            return this;
        }

        public Builder setBufferOverflowAllowed(boolean z) {
            this.mBufferOverflowAllowed = z;
            return this;
        }

        public Builder setUseTimeline(boolean z) {
            this.mUseTimeline = z;
            return this;
        }
    }

    public CoreLogger(String str, String str2, int i, boolean z, boolean z2) {
        this.mTag = str;
        this.mDumpTitle = str2;
        this.mBufferSize = i;
        this.mBufferOverflowAllowed = z;
        this.mBuffer = i > 0 ? new ArrayList(i) : null;
        this.mUseTimeline = z2;
    }

    public void log(int i, String str) {
        log(i, str, null);
    }

    public void log(int i, String str, Throwable th) {
        if (this.mBuffer == null || CoreRune.SAFE_DEBUG) {
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
                if (isBufferFull()) {
                    return;
                }
            }
        }
        if (this.mUseTimeline) {
            Calendar calendar = Calendar.getInstance();
            str = String.format(Locale.US, "%02d-%02d %02d:%02d:%02d.%03d %s", Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14)), str);
        }
        try {
            synchronized (this.mBuffer) {
                if (isBufferFull()) {
                    this.mBuffer.remove(0);
                }
                this.mBuffer.add(str);
            }
        } catch (Exception e) {
            Slog.w(this.mTag, "Fail to add logs", e);
        }
    }

    public final boolean isBufferFull() {
        return this.mBuffer.size() > this.mBufferSize;
    }

    public void print(final PrintWriter printWriter, final String str) {
        List list = this.mBuffer;
        if (list == null) {
            return;
        }
        synchronized (list) {
            if (this.mBuffer.isEmpty()) {
                return;
            }
            printWriter.println(str + this.mDumpTitle);
            this.mBuffer.forEach(new Consumer() { // from class: com.samsung.android.server.util.CoreLogger$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    CoreLogger.lambda$print$0(printWriter, str, (String) obj);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$print$0(PrintWriter printWriter, String str, String str2) {
        printWriter.println(str + str2);
    }

    public final List getBuffer() {
        List list = this.mBuffer;
        if (list == null) {
            return null;
        }
        synchronized (list) {
            if (this.mBuffer.isEmpty()) {
                return null;
            }
            return new ArrayList(this.mBuffer);
        }
    }

    public void toDumpCriticalInfo() {
        List buffer = getBuffer();
        if (buffer == null) {
            return;
        }
        WindowManagerServiceExt.logCriticalInfo(this.mDumpTitle, buffer);
    }
}
