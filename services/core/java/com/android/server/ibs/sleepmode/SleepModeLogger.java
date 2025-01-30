package com.android.server.ibs.sleepmode;

import android.util.LocalLog;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public class SleepModeLogger {
  public boolean mIsUsed;
  public LocalLog mSleepModeLog;

  public abstract class SleepModeLoggerHolder {
    public static final SleepModeLogger INSTANCE = new SleepModeLogger();
  }

  public SleepModeLogger() {
    this.mIsUsed = false;
    this.mSleepModeLog = new LocalLog(3000);
  }

  public static SleepModeLogger getInstance() {
    return SleepModeLoggerHolder.INSTANCE;
  }

  public void add(String str) {
    if (!this.mIsUsed) {
      this.mIsUsed = true;
    }
    this.mSleepModeLog.log(str);
  }

  public void dumpSleepModeHistoryLog(PrintWriter printWriter, String[] strArr) {
    if (this.mIsUsed) {
      printWriter.println();
      printWriter.println("SleepModeLogger history Log:");
      this.mSleepModeLog.dump((FileDescriptor) null, printWriter, (String[]) null);
      printWriter.println();
    }
  }
}
