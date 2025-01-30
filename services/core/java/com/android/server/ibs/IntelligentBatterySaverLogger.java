package com.android.server.ibs;

import android.util.LocalLog;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public class IntelligentBatterySaverLogger {
    public static IntelligentBatterySaverLogger sInstance;
    public boolean mIsUsed = false;
    public LocalLog mIBSLog = new LocalLog(3000);

    public static synchronized IntelligentBatterySaverLogger getInstance() {
        IntelligentBatterySaverLogger intelligentBatterySaverLogger;
        synchronized (IntelligentBatterySaverLogger.class) {
            if (sInstance == null) {
                sInstance = new IntelligentBatterySaverLogger();
            }
            intelligentBatterySaverLogger = sInstance;
        }
        return intelligentBatterySaverLogger;
    }

    public void add(String str) {
        if (!this.mIsUsed) {
            this.mIsUsed = true;
        }
        this.mIBSLog.log(str);
    }

    public void dumpIBSHistoryLog(PrintWriter printWriter, String[] strArr) {
        if (this.mIsUsed) {
            printWriter.println();
            printWriter.println("IntelligentBatterySaverLogger history Log:");
            this.mIBSLog.dump((FileDescriptor) null, printWriter, (String[]) null);
            printWriter.println();
        }
    }
}
