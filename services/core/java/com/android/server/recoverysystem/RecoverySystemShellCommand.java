package com.android.server.recoverysystem;

import android.content.IntentSender;
import android.os.IRecoverySystem;
import android.os.ShellCommand;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class RecoverySystemShellCommand extends ShellCommand {
  public final IRecoverySystem mService;

  public RecoverySystemShellCommand(RecoverySystemService recoverySystemService) {
    this.mService = recoverySystemService;
  }

  public int onCommand(String str) {
    char c;
    if (str == null) {
      return handleDefaultCommands(str);
    }
    try {
      switch (str.hashCode()) {
        case -779212638:
          if (str.equals("clear-lskf")) {
            c = 1;
            break;
          }
          c = 65535;
          break;
        case 1214227142:
          if (str.equals("is-lskf-captured")) {
            c = 2;
            break;
          }
          c = 65535;
          break;
        case 1256867232:
          if (str.equals("request-lskf")) {
            c = 0;
            break;
          }
          c = 65535;
          break;
        case 1405182928:
          if (str.equals("reboot-and-apply")) {
            c = 3;
            break;
          }
          c = 65535;
          break;
        default:
          c = 65535;
          break;
      }
      if (c == 0) {
        return requestLskf();
      }
      if (c == 1) {
        return clearLskf();
      }
      if (c == 2) {
        return isLskfCaptured();
      }
      if (c == 3) {
        return rebootAndApply();
      }
      return handleDefaultCommands(str);
    } catch (Exception e) {
      getErrPrintWriter().println("Error while executing command: " + str);
      e.printStackTrace(getErrPrintWriter());
      return -1;
    }
  }

  public final int requestLskf() {
    String nextArgRequired = getNextArgRequired();
    boolean requestLskf = this.mService.requestLskf(nextArgRequired, (IntentSender) null);
    PrintWriter outPrintWriter = getOutPrintWriter();
    Object[] objArr = new Object[2];
    objArr[0] = nextArgRequired;
    objArr[1] = requestLskf ? "success" : "failure";
    outPrintWriter.printf("Request LSKF for packageName: %s, status: %s\n", objArr);
    return 0;
  }

  public final int clearLskf() {
    String nextArgRequired = getNextArgRequired();
    boolean clearLskf = this.mService.clearLskf(nextArgRequired);
    PrintWriter outPrintWriter = getOutPrintWriter();
    Object[] objArr = new Object[2];
    objArr[0] = nextArgRequired;
    objArr[1] = clearLskf ? "success" : "failure";
    outPrintWriter.printf("Clear LSKF for packageName: %s, status: %s\n", objArr);
    return 0;
  }

  public final int isLskfCaptured() {
    String nextArgRequired = getNextArgRequired();
    boolean isLskfCaptured = this.mService.isLskfCaptured(nextArgRequired);
    PrintWriter outPrintWriter = getOutPrintWriter();
    Object[] objArr = new Object[2];
    objArr[0] = nextArgRequired;
    objArr[1] = isLskfCaptured ? "true" : "false";
    outPrintWriter.printf("%s LSKF capture status: %s\n", objArr);
    return 0;
  }

  public final int rebootAndApply() {
    String nextArgRequired = getNextArgRequired();
    boolean z = this.mService.rebootWithLskf(nextArgRequired, getNextArgRequired(), false) == 0;
    PrintWriter outPrintWriter = getOutPrintWriter();
    Object[] objArr = new Object[2];
    objArr[0] = nextArgRequired;
    objArr[1] = z ? "success" : "failure";
    outPrintWriter.printf("%s Reboot and apply status: %s\n", objArr);
    return 0;
  }

  public void onHelp() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    outPrintWriter.println("Recovery system commands:");
    outPrintWriter.println("  request-lskf <package_name>");
    outPrintWriter.println("  clear-lskf");
    outPrintWriter.println("  is-lskf-captured <package_name>");
    outPrintWriter.println("  reboot-and-apply <package_name> <reason>");
  }
}
