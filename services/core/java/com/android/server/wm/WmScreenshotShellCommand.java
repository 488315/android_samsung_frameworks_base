package com.android.server.wm;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import com.samsung.android.view.ScreenshotResult;

import java.io.PrintWriter;

public final class WmScreenshotShellCommand {
    public WmScreenshotController mController;
    public int mDisplayId;
    public boolean mIgnorePolicy;
    public boolean mScreenshotRotationLayer;
    public WindowManagerService mService;
    public int mWindowType;

    public static void printErrorMessage(PrintWriter printWriter, Exception exc, String str) {
        printWriter.println(str);
        printWriter.println("-------------------");
        printWriter.println("Reason : " + exc.getMessage());
    }

    public static void runDefaultCommands(PrintWriter printWriter, String str) {
        if (str != null) {
            printWriter.println("Unknown Command: ".concat(str));
        }
        BatteryService$$ExternalSyntheticOutline0.m(
                printWriter,
                "Screenshot Commands:",
                "  fullscreen",
                "    Return take screenshot current window of full screen.",
                "  window_type");
        BatteryService$$ExternalSyntheticOutline0.m(
                printWriter,
                "    Return the current window type.",
                "  target_window [WindowType] [DisplayId]",
                "    Return take screenshot of target window and save screenshot.",
                "  focused_task");
        BatteryService$$ExternalSyntheticOutline0.m(
                printWriter,
                "    Return take screenshot of top focused task and save screenshot.",
                "  rotation",
                "    Turn on the option, take screenshot of rotation layer and save screenshot.");
    }

    public final void exec(PrintWriter printWriter, String[] strArr) {
        WmScreenshotController wmScreenshotController;
        WindowManagerService windowManagerService;
        String str = strArr[0];
        if (str == null) {
            runDefaultCommands(printWriter, null);
        }
        wmScreenshotController = this.mController;
        windowManagerService = this.mService;
        switch (str) {
            case "target_window":
                this.mDisplayId = 0;
                this.mWindowType = 2015;
                this.mIgnorePolicy = false;
                String str2 = strArr[1];
                if (str2 != null) {
                    try {
                        this.mWindowType = Integer.parseInt(str2);
                    } catch (NumberFormatException e) {
                        printErrorMessage(printWriter, e, "Number_Format_Error");
                        return;
                    }
                }
                String str3 = strArr[2];
                if (str3 != null) {
                    try {
                        this.mDisplayId = Integer.parseInt(str3);
                    } catch (NumberFormatException e2) {
                        printErrorMessage(printWriter, e2, "Number_Format_Error");
                        return;
                    }
                }
                String str4 = strArr[3];
                if (str4 != null) {
                    try {
                        if (Integer.parseInt(str4) == 1) {
                            this.mIgnorePolicy = true;
                        }
                    } catch (NumberFormatException e3) {
                        printErrorMessage(printWriter, e3, "Number_Format_Error");
                        return;
                    }
                }
                try {
                    DisplayContent displayContent =
                            windowManagerService.mRoot.getDisplayContent(this.mDisplayId);
                    if (displayContent != null) {
                        DisplayMetrics displayMetrics = displayContent.mDisplayMetrics;
                        ScreenshotResult takeScreenshotToTargetWindow =
                                this.mController.takeScreenshotToTargetWindow(
                                        this.mDisplayId,
                                        this.mWindowType,
                                        true,
                                        new Rect(0, 0, 0, 0),
                                        displayMetrics.widthPixels,
                                        displayMetrics.heightPixels,
                                        true,
                                        this.mIgnorePolicy,
                                        false);
                        int failedReason = takeScreenshotToTargetWindow.getFailedReason();
                        String targetWindowName =
                                takeScreenshotToTargetWindow.getTargetWindowName();
                        if (failedReason != 0 && (failedReason & 2) == 0) {
                            printWriter.println("Failed to screenshot");
                            printWriter.println(
                                    "FailedReason:"
                                            + wmScreenshotController.failedReasonToString(
                                                    failedReason));
                            break;
                        }
                        if ((2 & failedReason) != 0) {
                            printWriter.println("Target window type was not found");
                            printWriter.println("Success screenshot full screen");
                        } else {
                            printWriter.println("Success screenshot");
                            printWriter.println("Window_Name:" + targetWindowName);
                        }
                        wmScreenshotController.mFileController.saveBitmapToScreenshotFile(
                                str,
                                takeScreenshotToTargetWindow.getCapturedBitmap(),
                                printWriter,
                                this.mDisplayId);
                        break;
                    } else {
                        printWriter.println("Error : display is null");
                        break;
                    }
                } catch (Exception e4) {
                    printErrorMessage(printWriter, e4, "Error");
                    return;
                }
                break;
            case "rotation":
                try {
                    if (this.mScreenshotRotationLayer) {
                        this.mScreenshotRotationLayer = false;
                        printWriter.println("Turn off the screenshot rotation layer");
                    } else {
                        this.mScreenshotRotationLayer = true;
                        printWriter.println("Turn on the screenshot rotation layer");
                    }
                    break;
                } catch (Exception e5) {
                    printErrorMessage(printWriter, e5, "Error");
                    return;
                }
            case "fullscreen":
                try {
                    wmScreenshotController.sendTakeScreenshotRunnable(1, 1, 0, 1, null);
                    break;
                } catch (Exception e6) {
                    printErrorMessage(printWriter, e6, "Error");
                    return;
                }
            case "focused_task":
                try {
                    DisplayContent topFocusedDisplayContent =
                            windowManagerService.mRoot.getTopFocusedDisplayContent();
                    if (topFocusedDisplayContent == null) {
                        printWriter.println("Error : display is null");
                    } else {
                        Task focusedRootTask = topFocusedDisplayContent.getFocusedRootTask();
                        if (focusedRootTask != null && focusedRootTask.isVisible()) {
                            Bitmap snapshotAsBitmapLocked =
                                    focusedRootTask.getSnapshotAsBitmapLocked();
                            if (snapshotAsBitmapLocked != null) {
                                printWriter.println(
                                        "Success screenshot, focused task=" + focusedRootTask);
                                wmScreenshotController.mFileController.saveBitmapToScreenshotFile(
                                        str,
                                        snapshotAsBitmapLocked,
                                        printWriter,
                                        topFocusedDisplayContent.mDisplayId);
                            } else {
                                printWriter.println("Failed to screenshot");
                            }
                        }
                        printWriter.println("Error : focused task is null or invisible");
                    }
                    break;
                } catch (Exception e7) {
                    printErrorMessage(printWriter, e7, "Error");
                    return;
                }
            case "window_type":
                try {
                    WindowState focusedWindowLocked = windowManagerService.getFocusedWindowLocked();
                    printWriter.println(
                            "focusedWindow:" + ((Object) focusedWindowLocked.getWindowTag()));
                    printWriter.println("windowType:" + focusedWindowLocked.mAttrs.type);
                    break;
                } catch (Exception e8) {
                    printErrorMessage(printWriter, e8, "Error");
                    return;
                }
            default:
                runDefaultCommands(printWriter, str);
                break;
        }
    }
}
