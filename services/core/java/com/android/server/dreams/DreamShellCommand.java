package com.android.server.dreams;

import android.os.Binder;
import android.os.ShellCommand;
import android.util.Slog;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public class DreamShellCommand extends ShellCommand {
  public final DreamManagerService mService;

  public DreamShellCommand(DreamManagerService dreamManagerService) {
    this.mService = dreamManagerService;
  }

  /* JADX WARN: Removed duplicated region for block: B:10:0x0041  */
  /* JADX WARN: Removed duplicated region for block: B:16:0x0050 A[Catch: SecurityException -> 0x0058, TRY_LEAVE, TryCatch #0 {SecurityException -> 0x0058, blocks: (B:3:0x0018, B:11:0x0043, B:14:0x0048, B:16:0x0050, B:18:0x0028, B:21:0x0033), top: B:2:0x0018 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public int onCommand(String str) {
    boolean z;
    Slog.d("DreamShellCommand", "onCommand:" + str);
    try {
      int hashCode = str.hashCode();
      if (hashCode != -183711126) {
        if (hashCode == 1473640970 && str.equals("start-dreaming")) {
          z = false;
          if (z) {
            enforceCallerIsRoot();
            return startDreaming();
          }
          if (z) {
            enforceCallerIsRoot();
            return stopDreaming();
          }
          return super.handleDefaultCommands(str);
        }
        z = -1;
        if (z) {}
      } else {
        if (str.equals("stop-dreaming")) {
          z = true;
          if (z) {}
        }
        z = -1;
        if (z) {}
      }
    } catch (SecurityException e) {
      getOutPrintWriter().println(e);
      return -1;
    }
  }

  public final int startDreaming() {
    this.mService.requestStartDreamFromShell();
    return 0;
  }

  public final int stopDreaming() {
    this.mService.requestStopDreamFromShell();
    return 0;
  }

  public final void enforceCallerIsRoot() {
    if (Binder.getCallingUid() != 0) {
      throw new SecurityException("Must be root to call Dream shell commands");
    }
  }

  public void onHelp() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    outPrintWriter.println("Dream manager (dreams) commands:");
    outPrintWriter.println("  help");
    outPrintWriter.println("      Print this help text.");
    outPrintWriter.println("  start-dreaming");
    outPrintWriter.println("      Start the currently configured dream.");
    outPrintWriter.println("  stop-dreaming");
    outPrintWriter.println("      Stops any active dream");
  }
}
