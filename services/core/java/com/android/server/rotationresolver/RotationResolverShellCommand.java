package com.android.server.rotationresolver;

import android.content.ComponentName;
import android.os.CancellationSignal;
import android.os.ShellCommand;
import android.rotationresolver.RotationResolverInternal;
import android.service.rotationresolver.RotationResolutionRequest;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

public final class RotationResolverShellCommand extends ShellCommand {
    public static final TestableRotationCallbackInternal sTestableRotationCallbackInternal;
    public final RotationResolverManagerService mService;

    public final class TestableRotationCallbackInternal
            implements RotationResolverInternal.RotationResolverCallbackInternal {
        public int mLastCallbackResultCode;

        public final void onFailure(int i) {
            this.mLastCallbackResultCode = i;
        }

        public final void onSuccess(int i) {
            this.mLastCallbackResultCode = i;
        }
    }

    static {
        TestableRotationCallbackInternal testableRotationCallbackInternal =
                new TestableRotationCallbackInternal();
        testableRotationCallbackInternal.mLastCallbackResultCode = -1;
        sTestableRotationCallbackInternal = testableRotationCallbackInternal;
    }

    public RotationResolverShellCommand(
            RotationResolverManagerService rotationResolverManagerService) {
        this.mService = rotationResolverManagerService;
    }

    public final int onCommand(String str) {
        ComponentName componentName;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str) {
            case "get-bound-package":
                PrintWriter outPrintWriter = getOutPrintWriter();
                int parseInt = Integer.parseInt(getNextArgRequired());
                RotationResolverManagerService rotationResolverManagerService = this.mService;
                synchronized (rotationResolverManagerService.mLock) {
                    try {
                        RotationResolverManagerPerUserService
                                rotationResolverManagerPerUserService =
                                        (RotationResolverManagerPerUserService)
                                                rotationResolverManagerService
                                                        .getServiceForUserLocked(parseInt);
                        componentName =
                                rotationResolverManagerPerUserService != null
                                        ? rotationResolverManagerPerUserService.getComponentName()
                                        : null;
                    } finally {
                    }
                }
                outPrintWriter.println(componentName == null ? "" : componentName.getPackageName());
                return 0;
            case "resolve-rotation":
                int parseInt2 = Integer.parseInt(getNextArgRequired());
                RotationResolutionRequest rotationResolutionRequest =
                        new RotationResolutionRequest("", 0, 0, true, 2000L);
                RotationResolverManagerService rotationResolverManagerService2 = this.mService;
                TestableRotationCallbackInternal testableRotationCallbackInternal =
                        sTestableRotationCallbackInternal;
                synchronized (rotationResolverManagerService2.mLock) {
                    try {
                        RotationResolverManagerPerUserService
                                rotationResolverManagerPerUserService2 =
                                        (RotationResolverManagerPerUserService)
                                                rotationResolverManagerService2
                                                        .getServiceForUserLocked(parseInt2);
                        if (rotationResolverManagerPerUserService2 != null) {
                            rotationResolverManagerPerUserService2.resolveRotationLocked(
                                    testableRotationCallbackInternal,
                                    rotationResolutionRequest,
                                    new CancellationSignal());
                        } else {
                            Slog.i(
                                    "RotationResolverManagerService",
                                    "service not available for user_id: " + parseInt2);
                        }
                    } finally {
                    }
                }
                return 0;
            case "set-temporary-service":
                PrintWriter outPrintWriter2 = getOutPrintWriter();
                int parseInt3 = Integer.parseInt(getNextArgRequired());
                String nextArg = getNextArg();
                if (nextArg == null) {
                    this.mService.resetTemporaryService(parseInt3);
                    outPrintWriter2.println("RotationResolverService temporary reset. ");
                } else {
                    int parseInt4 = Integer.parseInt(getNextArgRequired());
                    this.mService.setTemporaryService(parseInt3, nextArg, parseInt4);
                    outPrintWriter2.println(
                            ActiveServices$$ExternalSyntheticOutline0.m(
                                    parseInt4,
                                    nextArg,
                                    " for ",
                                    "ms",
                                    new StringBuilder(
                                            "RotationResolverService temporarily set to ")));
                }
                return 0;
            case "get-last-resolution":
                getOutPrintWriter()
                        .println(sTestableRotationCallbackInternal.mLastCallbackResultCode);
                return 0;
            default:
                return handleDefaultCommands(str);
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Rotation Resolver commands: ");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("  resolve-rotation USER_ID: request a rotation resolution.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  get-last-resolution: show the last rotation resolution result.",
                "  get-bound-package USER_ID:",
                "    Print the bound package that implements the service.",
                "  set-temporary-service USER_ID [COMPONENT_NAME DURATION]");
        outPrintWriter.println(
                "    Temporarily (for DURATION ms) changes the service implementation.");
        outPrintWriter.println("    To reset, call with just the USER_ID argument.");
    }
}
