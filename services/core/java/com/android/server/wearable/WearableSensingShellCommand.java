package com.android.server.wearable;

import android.content.ComponentName;
import android.os.Binder;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.ShellCommand;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;

public final class WearableSensingShellCommand extends ShellCommand {
    public static ParcelFileDescriptor[] sPipe;
    public static final TestableCallbackInternal sTestableCallbackInternal =
            new TestableCallbackInternal();
    public final WearableSensingManagerService mService;

    public final class TestableCallbackInternal {
        public int mLastStatus;
    }

    public WearableSensingShellCommand(
            WearableSensingManagerService wearableSensingManagerService) {
        this.mService = wearableSensingManagerService;
    }

    public final int onCommand(String str) {
        ComponentName componentName;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        componentName = null;
        switch (str) {
            case "get-bound-package":
                PrintWriter outPrintWriter = getOutPrintWriter();
                int parseInt = Integer.parseInt(getNextArgRequired());
                WearableSensingManagerService wearableSensingManagerService = this.mService;
                synchronized (wearableSensingManagerService.mLock) {
                    try {
                        WearableSensingManagerPerUserService wearableSensingManagerPerUserService =
                                (WearableSensingManagerPerUserService)
                                        wearableSensingManagerService.getServiceForUserLocked(
                                                parseInt);
                        if (wearableSensingManagerPerUserService != null) {
                            componentName = wearableSensingManagerPerUserService.getComponentName();
                        }
                    } finally {
                    }
                }
                outPrintWriter.println(componentName == null ? "" : componentName.getPackageName());
                return 0;
            case "write-to-data-stream":
                Slog.d("WearableSensingShellCommand", "writeToDataStream");
                if (sPipe != null) {
                    try {
                        new ParcelFileDescriptor.AutoCloseOutputStream(sPipe[1].dup())
                                .write(getNextArgRequired().getBytes());
                    } catch (IOException e) {
                        Slog.d("WearableSensingShellCommand", "Failed to writeToDataStream.", e);
                    }
                }
                return 0;
            case "destroy-data-stream":
                Slog.d("WearableSensingShellCommand", "destroyDataStream");
                try {
                    ParcelFileDescriptor[] parcelFileDescriptorArr = sPipe;
                    if (parcelFileDescriptorArr != null) {
                        parcelFileDescriptorArr[0].close();
                        sPipe[1].close();
                    }
                } catch (IOException e2) {
                    Slog.d("WearableSensingShellCommand", "Failed to destroyDataStream.", e2);
                }
                return 0;
            case "set-data-request-rate-limit-window-size":
                Slog.d("WearableSensingShellCommand", "setDataRequestRateLimitWindowSize");
                int parseInt2 = Integer.parseInt(getNextArgRequired());
                if (parseInt2 <= 0) {
                    this.mService.resetDataRequestRateLimitWindowSize();
                } else {
                    if (parseInt2 < 20) {
                        parseInt2 = 20;
                    }
                    this.mService.setDataRequestRateLimitWindowSize(Duration.ofSeconds(parseInt2));
                }
                return 0;
            case "provide-data":
                Slog.d("WearableSensingShellCommand", "provideData");
                int parseInt3 = Integer.parseInt(getNextArgRequired());
                String nextArgRequired = getNextArgRequired();
                int parseInt4 = Integer.parseInt(getNextArgRequired());
                PersistableBundle persistableBundle = new PersistableBundle();
                persistableBundle.putInt(nextArgRequired, parseInt4);
                this.mService.provideData(
                        parseInt3,
                        persistableBundle,
                        null,
                        new RemoteCallback(
                                new RemoteCallback.OnResultListener() { // from class:
                                    // com.android.server.wearable.WearableSensingShellCommand$TestableCallbackInternal$$ExternalSyntheticLambda0
                                    public final /* synthetic */ WearableSensingShellCommand
                                                    .TestableCallbackInternal
                                            f$0 =
                                                    WearableSensingShellCommand
                                                            .sTestableCallbackInternal;

                                    public final void onResult(Bundle bundle) {
                                        WearableSensingShellCommand.TestableCallbackInternal
                                                testableCallbackInternal = this.f$0;
                                        testableCallbackInternal.getClass();
                                        int i =
                                                bundle.getInt(
                                                        "android.app.wearable.WearableSensingStatusBundleKey");
                                        long clearCallingIdentity = Binder.clearCallingIdentity();
                                        try {
                                            testableCallbackInternal.mLastStatus = i;
                                        } finally {
                                            Binder.restoreCallingIdentity(clearCallingIdentity);
                                        }
                                    }
                                }));
                return 0;
            case "create-data-stream":
                Slog.d("WearableSensingShellCommand", "createDataStream");
                try {
                    sPipe = ParcelFileDescriptor.createPipe();
                } catch (IOException e3) {
                    Slog.d("WearableSensingShellCommand", "Failed to createDataStream.", e3);
                }
                return 0;
            case "set-temporary-service":
                PrintWriter outPrintWriter2 = getOutPrintWriter();
                int parseInt5 = Integer.parseInt(getNextArgRequired());
                String nextArg = getNextArg();
                if (nextArg == null) {
                    this.mService.resetTemporaryService(parseInt5);
                    outPrintWriter2.println("WearableSensingManagerService temporary reset. ");
                } else {
                    int parseInt6 = Integer.parseInt(getNextArgRequired());
                    this.mService.setTemporaryService(parseInt5, nextArg, parseInt6);
                    outPrintWriter2.println(
                            ActiveServices$$ExternalSyntheticOutline0.m(
                                    parseInt6,
                                    nextArg,
                                    " for ",
                                    "ms",
                                    new StringBuilder(
                                            "WearableSensingService temporarily set to ")));
                }
                return 0;
            case "provide-data-stream":
                Slog.d("WearableSensingShellCommand", "provideDataStream");
                if (sPipe != null) {
                    this.mService.provideDataStream(
                            Integer.parseInt(getNextArgRequired()),
                            sPipe[0],
                            new RemoteCallback(
                                    new RemoteCallback.OnResultListener() { // from class:
                                        // com.android.server.wearable.WearableSensingShellCommand$TestableCallbackInternal$$ExternalSyntheticLambda0
                                        public final /* synthetic */ WearableSensingShellCommand
                                                        .TestableCallbackInternal
                                                f$0 =
                                                        WearableSensingShellCommand
                                                                .sTestableCallbackInternal;

                                        public final void onResult(Bundle bundle) {
                                            WearableSensingShellCommand.TestableCallbackInternal
                                                    testableCallbackInternal = this.f$0;
                                            testableCallbackInternal.getClass();
                                            int i =
                                                    bundle.getInt(
                                                            "android.app.wearable.WearableSensingStatusBundleKey");
                                            long clearCallingIdentity =
                                                    Binder.clearCallingIdentity();
                                            try {
                                                testableCallbackInternal.mLastStatus = i;
                                            } finally {
                                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                            }
                                        }
                                    }));
                }
                return 0;
            case "get-last-status-code":
                Slog.d("WearableSensingShellCommand", "getLastStatusCode");
                getOutPrintWriter().println(sTestableCallbackInternal.mLastStatus);
                return 0;
            default:
                return handleDefaultCommands(str);
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("WearableSensingCommands commands: ");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("  create-data-stream: Creates a data stream to be provided.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  destroy-data-stream: Destroys a data stream if one was previously created.",
                "  provide-data-stream USER_ID: Provides data stream to WearableSensingService.",
                "  write-to-data-stream STRING: writes string to data stream.",
                "  provide-data USER_ID KEY INTEGER: provide integer as data with key.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  get-last-status-code: Prints the latest request status code.",
                "  get-bound-package USER_ID:     Print the bound package that implements the"
                    + " service.",
                "  set-temporary-service USER_ID [PACKAGE_NAME] [COMPONENT_NAME DURATION]",
                "    Temporarily (for DURATION ms) changes the service implementation.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    To reset, call with just the USER_ID argument.",
                "  set-data-request-rate-limit-window-size WINDOW_SIZE",
                "    Set the window size used in data request rate limiting to WINDOW_SIZE"
                    + " seconds.",
                "    positive WINDOW_SIZE smaller than 20 will be automatically set to 20.");
        outPrintWriter.println("    To reset, call with 0 or a negative WINDOW_SIZE.");
    }
}
