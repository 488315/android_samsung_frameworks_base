package com.android.server.wearable;

import android.content.ComponentName;
import android.os.Binder;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.ShellCommand;
import android.util.Slog;
import java.io.IOException;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public final class WearableSensingShellCommand extends ShellCommand {
  public static ParcelFileDescriptor[] sPipe;
  public final WearableSensingManagerService mService;
  public static final String TAG = WearableSensingShellCommand.class.getSimpleName();
  public static final TestableCallbackInternal sTestableCallbackInternal =
      new TestableCallbackInternal();

  public WearableSensingShellCommand(WearableSensingManagerService wearableSensingManagerService) {
    this.mService = wearableSensingManagerService;
  }

  public class TestableCallbackInternal {
    public int mLastStatus;

    public int getLastStatus() {
      return this.mLastStatus;
    }

    public final RemoteCallback createRemoteStatusCallback() {
      return new RemoteCallback(
          new RemoteCallback.OnResultListener() { // from class:
            // com.android.server.wearable.WearableSensingShellCommand$TestableCallbackInternal$$ExternalSyntheticLambda0
            public final void onResult(Bundle bundle) {
              WearableSensingShellCommand.TestableCallbackInternal.this
                  .lambda$createRemoteStatusCallback$0(bundle);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createRemoteStatusCallback$0(Bundle bundle) {
      int i = bundle.getInt("android.app.wearable.WearableSensingStatusBundleKey");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        this.mLastStatus = i;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
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

  public void onHelp() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    outPrintWriter.println("WearableSensingCommands commands: ");
    outPrintWriter.println("  help");
    outPrintWriter.println("    Print this help text.");
    outPrintWriter.println();
    outPrintWriter.println("  create-data-stream: Creates a data stream to be provided.");
    outPrintWriter.println(
        "  destroy-data-stream: Destroys a data stream if one was previously created.");
    outPrintWriter.println(
        "  provide-data-stream USER_ID: Provides data stream to WearableSensingService.");
    outPrintWriter.println("  write-to-data-stream STRING: writes string to data stream.");
    outPrintWriter.println("  provide-data USER_ID KEY INTEGER: provide integer as data with key.");
    outPrintWriter.println("  get-last-status-code: Prints the latest request status code.");
    outPrintWriter.println(
        "  get-bound-package USER_ID:     Print the bound package that implements the service.");
    outPrintWriter.println(
        "  set-temporary-service USER_ID [PACKAGE_NAME] [COMPONENT_NAME DURATION]");
    outPrintWriter.println("    Temporarily (for DURATION ms) changes the service implementation.");
    outPrintWriter.println("    To reset, call with just the USER_ID argument.");
  }

  public final int createDataStream() {
    Slog.d(TAG, "createDataStream");
    try {
      sPipe = ParcelFileDescriptor.createPipe();
      return 0;
    } catch (IOException e) {
      Slog.d(TAG, "Failed to createDataStream.", e);
      return 0;
    }
  }

  public final int destroyDataStream() {
    Slog.d(TAG, "destroyDataStream");
    try {
      ParcelFileDescriptor[] parcelFileDescriptorArr = sPipe;
      if (parcelFileDescriptorArr != null) {
        parcelFileDescriptorArr[0].close();
        sPipe[1].close();
      }
    } catch (IOException e) {
      Slog.d(TAG, "Failed to destroyDataStream.", e);
    }
    return 0;
  }

  public final int provideDataStream() {
    Slog.d(TAG, "provideDataStream");
    if (sPipe != null) {
      this.mService.provideDataStream(
          Integer.parseInt(getNextArgRequired()),
          sPipe[0],
          sTestableCallbackInternal.createRemoteStatusCallback());
    }
    return 0;
  }

  public final int writeToDataStream() {
    Slog.d(TAG, "writeToDataStream");
    if (sPipe == null) {
      return 0;
    }
    try {
      new ParcelFileDescriptor.AutoCloseOutputStream(sPipe[1].dup())
          .write(getNextArgRequired().getBytes());
      return 0;
    } catch (IOException e) {
      Slog.d(TAG, "Failed to writeToDataStream.", e);
      return 0;
    }
  }

  public final int provideData() {
    Slog.d(TAG, "provideData");
    int parseInt = Integer.parseInt(getNextArgRequired());
    String nextArgRequired = getNextArgRequired();
    int parseInt2 = Integer.parseInt(getNextArgRequired());
    PersistableBundle persistableBundle = new PersistableBundle();
    persistableBundle.putInt(nextArgRequired, parseInt2);
    this.mService.provideData(
        parseInt, persistableBundle, null, sTestableCallbackInternal.createRemoteStatusCallback());
    return 0;
  }

  public final int getLastStatusCode() {
    Slog.d(TAG, "getLastStatusCode");
    getOutPrintWriter().println(sTestableCallbackInternal.getLastStatus());
    return 0;
  }

  public final int setTemporaryService() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    int parseInt = Integer.parseInt(getNextArgRequired());
    String nextArg = getNextArg();
    if (nextArg == null) {
      this.mService.resetTemporaryService(parseInt);
      outPrintWriter.println("WearableSensingManagerService temporary reset. ");
      return 0;
    }
    int parseInt2 = Integer.parseInt(getNextArgRequired());
    this.mService.setTemporaryService(parseInt, nextArg, parseInt2);
    outPrintWriter.println(
        "WearableSensingService temporarily set to " + nextArg + " for " + parseInt2 + "ms");
    return 0;
  }

  public final int getBoundPackageName() {
    PrintWriter outPrintWriter = getOutPrintWriter();
    ComponentName componentName =
        this.mService.getComponentName(Integer.parseInt(getNextArgRequired()));
    outPrintWriter.println(componentName == null ? "" : componentName.getPackageName());
    return 0;
  }
}
