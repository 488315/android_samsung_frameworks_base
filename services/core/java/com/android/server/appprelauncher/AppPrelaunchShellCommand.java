package com.android.server.appprelauncher;

import android.app.ActivityManager;
import android.content.pm.PackageManagerInternal;
import android.os.ShellCommand;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class AppPrelaunchShellCommand extends ShellCommand {
  public final AppPrelaunchService mService;

  public AppPrelaunchShellCommand(AppPrelaunchService appPrelaunchService) {
    this.mService = appPrelaunchService;
  }

  /* JADX WARN: Removed duplicated region for block: B:14:0x0031  */
  /* JADX WARN: Removed duplicated region for block: B:19:0x003d A[Catch: Exception -> 0x0042, TRY_LEAVE, TryCatch #0 {Exception -> 0x0042, blocks: (B:7:0x0008, B:15:0x0033, B:17:0x0038, B:19:0x003d, B:21:0x0018, B:24:0x0023), top: B:6:0x0008 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public int onCommand(String str) {
    boolean z;
    if (str == null) {
      return handleDefaultCommands(str);
    }
    try {
      int hashCode = str.hashCode();
      if (hashCode != -1125838826) {
        if (hashCode == 3291998 && str.equals("kill")) {
          z = true;
          if (z) {
            return prelaunch();
          }
          if (z) {
            return kill();
          }
          return handleDefaultCommands(str);
        }
        z = -1;
        if (z) {}
      } else {
        if (str.equals("prelaunch")) {
          z = false;
          if (z) {}
        }
        z = -1;
        if (z) {}
      }
    } catch (Exception e) {
      getErrPrintWriter().println("Failed to use prelauncher: " + e);
      return -1;
    }
  }

  public void onHelp() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    outPrintWriter.println("AppPrelauncher (prelauncher) commands:");
    outPrintWriter.println("  help");
    outPrintWriter.println("    Print this help message.");
    outPrintWriter.println("");
    outPrintWriter.println("  prelaunch PACKAGE [--uid UID] [--stage STAGE] [--debug]");
    outPrintWriter.println("    Runs PACKAGE for UID in background until STAGE if specified");
    outPrintWriter.println("  kill PACKAGE [--uid UID]");
    outPrintWriter.println("    Kills PACKAGE for UID if it is still prelaunched");
    outPrintWriter.println("");
  }

  public class Args {
    public String packageName;
    public int stage;
    public int uid;

    public Args() {
      this.stage = 40;
      this.uid = -1;
    }
  }

  public final Args parseOptions() {
    Args args = new Args();
    while (true) {
      String nextArg = getNextArg();
      if (nextArg == null) {
        return args;
      }
      if (nextArg.equals("--stage")) {
        args.stage = Integer.parseInt(getNextArgRequired());
      } else if (nextArg.equals("--uid")) {
        args.uid = Integer.parseInt(getNextArgRequired());
      } else {
        if (args.packageName != null) {
          throw new IllegalArgumentException("Unknown option '" + nextArg + "'");
        }
        args.packageName = nextArg;
      }
    }
  }

  public final Args prepareArgs() {
    Args parseOptions = parseOptions();
    String str = parseOptions.packageName;
    if (str == null) {
      throw new IllegalArgumentException("Package name is not specified");
    }
    if (parseOptions.uid == -1) {
      parseOptions.uid = getUid(str);
    }
    return parseOptions;
  }

  public final int getUid(String str) {
    int packageUid =
        ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
            .getPackageUid(str, 0L, ActivityManager.getCurrentUser());
    if (packageUid != -1) {
      return packageUid;
    }
    throw new IllegalArgumentException(
        "Package '" + str + "' not found for userId " + ActivityManager.getCurrentUser());
  }

  public int prelaunch() {
    try {
      Args prepareArgs = prepareArgs();
      getOutPrintWriter()
          .println(
              prepareArgs.packageName
                  + XmlUtils.STRING_ARRAY_SEPARATOR
                  + prepareArgs.uid
                  + ", "
                  + prepareArgs.stage);
      if (this.mService.prelaunchAppTillStage(
          prepareArgs.packageName, prepareArgs.uid, prepareArgs.stage)) {
        return 0;
      }
      getErrPrintWriter().println("Error: could not prelaunch " + prepareArgs.packageName);
      return 1;
    } catch (IllegalArgumentException e) {
      getErrPrintWriter().println("Error: cannot parse arguments (" + e + ")");
      return 1;
    }
  }

  public int kill() {
    try {
      Args prepareArgs = prepareArgs();
      PrintWriter outPrintWriter = getOutPrintWriter();
      outPrintWriter.println(
          "Killing " + prepareArgs.packageName + XmlUtils.STRING_ARRAY_SEPARATOR + prepareArgs.uid);
      if (!this.mService.killApp(prepareArgs.uid, "Killed from shell")) {
        getErrPrintWriter()
            .println(
                "Error: app "
                    + prepareArgs.packageName
                    + XmlUtils.STRING_ARRAY_SEPARATOR
                    + prepareArgs.uid
                    + " was not killed");
        return 1;
      }
      outPrintWriter.println("Success");
      return 0;
    } catch (IllegalArgumentException e) {
      getErrPrintWriter().println("Error: cannot parse arguments (" + e + ")");
      return 1;
    }
  }
}
