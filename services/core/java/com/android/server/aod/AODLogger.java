package com.android.server.aod;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AODLogger {
    public final int mCapacity;
    public int mIndex = 0;
    public boolean mIsFull = false;
    public final ArrayList mLogs = new ArrayList();
    public final String mName;
    public static final Object sLock = new Object();
    public static final SimpleDateFormat mDateFormat =
            new SimpleDateFormat("[MM-dd HH:mm:ss.SSS]", Locale.getDefault());

    public AODLogger(String str, int i) {
        this.mName = str;
        this.mCapacity = i;
    }

    public final void add(String str, boolean z) {
        synchronized (sLock) {
            if (z) {
                try {
                    str = mDateFormat.format(Calendar.getInstance().getTime()) + " " + str;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (this.mIsFull || this.mLogs.size() >= this.mCapacity) {
                this.mIsFull = true;
                this.mLogs.set(this.mIndex, str);
                int i = this.mIndex + 1;
                this.mIndex = i;
                if (i >= this.mCapacity) {
                    this.mIndex = 0;
                }
            } else {
                this.mLogs.add(str);
            }
        }
    }

    public final void dump(PrintWriter printWriter) {
        synchronized (sLock) {
            try {
                printWriter.println("---- " + this.mName + " ----");
                Iterator it = this.mLogs.iterator();
                int i = 1;
                while (it.hasNext()) {
                    printWriter.print("[" + i + "] " + ((String) it.next()));
                    printWriter.println();
                    i++;
                }
                printWriter.println();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
