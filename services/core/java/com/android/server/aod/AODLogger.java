package com.android.server.aod;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes.dex */
public class AODLogger {
    public final int mCapacity;
    public int mIndex = 0;
    public boolean mIsFull = false;
    public ArrayList mLogs = new ArrayList();
    public final String mName;
    public static final Object sLock = new Object();
    public static final SimpleDateFormat mDateFormat = new SimpleDateFormat("[MM-dd HH:mm:ss.SSS]", Locale.getDefault());

    public AODLogger(String str, int i) {
        this.mName = str;
        this.mCapacity = i;
    }

    public void add(String str, boolean z) {
        synchronized (sLock) {
            if (z) {
                str = getCurDateTime() + " " + str;
            }
            if (!this.mIsFull && this.mLogs.size() < this.mCapacity) {
                this.mLogs.add(str);
            } else {
                this.mIsFull = true;
                this.mLogs.set(this.mIndex, str);
                int i = this.mIndex + 1;
                this.mIndex = i;
                if (i >= this.mCapacity) {
                    this.mIndex = 0;
                }
            }
        }
    }

    public void dump(PrintWriter printWriter) {
        synchronized (sLock) {
            printWriter.println("---- " + this.mName + " ----");
            Iterator it = this.mLogs.iterator();
            int i = 1;
            while (it.hasNext()) {
                printWriter.print("[" + i + "] " + ((String) it.next()));
                printWriter.println();
                i++;
            }
            printWriter.println();
        }
    }

    public static String getCurDateTime() {
        return mDateFormat.format(Calendar.getInstance().getTime());
    }
}
