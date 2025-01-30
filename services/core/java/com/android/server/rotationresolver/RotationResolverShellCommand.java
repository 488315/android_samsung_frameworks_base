package com.android.server.rotationresolver;

import android.content.ComponentName;
import android.os.ShellCommand;
import android.rotationresolver.RotationResolverInternal;
import android.service.rotationresolver.RotationResolutionRequest;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public final class RotationResolverShellCommand extends ShellCommand {
  public static final TestableRotationCallbackInternal sTestableRotationCallbackInternal =
      new TestableRotationCallbackInternal();
  public final RotationResolverManagerService mService;

  public RotationResolverShellCommand(
      RotationResolverManagerService rotationResolverManagerService) {
    this.mService = rotationResolverManagerService;
  }

  public class TestableRotationCallbackInternal
      implements RotationResolverInternal.RotationResolverCallbackInternal {
    public int mLastCallbackResultCode = -1;

    public void onSuccess(int i) {
      this.mLastCallbackResultCode = i;
    }

    public void onFailure(int i) {
      this.mLastCallbackResultCode = i;
    }

    public int getLastCallbackCode() {
      return this.mLastCallbackResultCode;
    }
  }

  public int onCommand(String str) {
    if (str == null) {
      return handleDefaultCommands(str);
    }
    switch (str) {
    }
    return handleDefaultCommands(str);
  }

  public final int getBoundPackageName() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    ComponentName componentNameShellCommand =
        this.mService.getComponentNameShellCommand(Integer.parseInt(getNextArgRequired()));
    outPrintWriter.println(
        componentNameShellCommand == null ? "" : componentNameShellCommand.getPackageName());
    return 0;
  }

  public final int setTemporaryService() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    int parseInt = Integer.parseInt(getNextArgRequired());
    String nextArg = getNextArg();
    if (nextArg == null) {
      this.mService.resetTemporaryService(parseInt);
      outPrintWriter.println("RotationResolverService temporary reset. ");
      return 0;
    }
    int parseInt2 = Integer.parseInt(getNextArgRequired());
    this.mService.setTemporaryService(parseInt, nextArg, parseInt2);
    outPrintWriter.println(
        "RotationResolverService temporarily set to " + nextArg + " for " + parseInt2 + "ms");
    return 0;
  }

  public final int runResolveRotation() {
    this.mService.resolveRotationShellCommand(
        Integer.parseInt(getNextArgRequired()),
        sTestableRotationCallbackInternal,
        new RotationResolutionRequest("", 0, 0, true, 2000L));
    return 0;
  }

  public final int getLastResolution() {
    getOutPrintWriter().println(sTestableRotationCallbackInternal.getLastCallbackCode());
    return 0;
  }

  public void onHelp() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    outPrintWriter.println("Rotation Resolver commands: ");
    outPrintWriter.println("  help");
    outPrintWriter.println("    Print this help text.");
    outPrintWriter.println();
    outPrintWriter.println("  resolve-rotation USER_ID: request a rotation resolution.");
    outPrintWriter.println("  get-last-resolution: show the last rotation resolution result.");
    outPrintWriter.println("  get-bound-package USER_ID:");
    outPrintWriter.println("    Print the bound package that implements the service.");
    outPrintWriter.println("  set-temporary-service USER_ID [COMPONENT_NAME DURATION]");
    outPrintWriter.println("    Temporarily (for DURATION ms) changes the service implementation.");
    outPrintWriter.println("    To reset, call with just the USER_ID argument.");
  }
}
