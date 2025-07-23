package com.android.wm.shell.splitscreen;

import android.app.ActivityThread;
import android.content.Intent;
import android.os.Debug;
import android.os.UserHandle;
import android.window.WindowContainerTransaction;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.biometrics.AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SplitScreenShellCommandHandler implements ShellCommandHandler.ShellCommandActionHandler {
    public final SplitScreenController mController;

    public SplitScreenShellCommandHandler(SplitScreenController splitScreenController) {
        this.mController = splitScreenController;
    }

    public static Intent makeBasicIntent(String str) {
        Intent launchIntentForPackage = ActivityThread.currentActivityThread().getApplication().getPackageManager().getLaunchIntentForPackage(str);
        launchIntentForPackage.addFlags(270548992);
        return launchIntentForPackage;
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final boolean onShellCommand(PrintWriter printWriter, String[] strArr) {
        Intent intent;
        SplitScreenController splitScreenController;
        int parseInt;
        boolean z;
        z = false;
        String str = strArr[0];
        str.getClass();
        intent = null;
        splitScreenController = this.mController;
        switch (str) {
            case "startTasks":
                if (strArr.length < 3) {
                    printWriter.println("Error: start multiple tasks should be provided as arguments");
                    return false;
                }
                this.mController.startTasks(Integer.parseInt(strArr[1]), null, Integer.parseInt(strArr[2]), null, 1, 0.5f, null);
                return true;
            case "startTaskWithAllApps":
                if (strArr.length >= 2) {
                    this.mController.startTasks(Integer.parseInt(strArr[1]), null, -1, null, 1, 0.5f, null);
                    return true;
                }
                printWriter.println("Error: start taskId to make split with allapps");
                printWriter.println("$ adb shell ~ WMShell startTaskWithAllApps taskId");
                return false;
            case "switchSplitPosition":
                if (splitScreenController.mStageCoordinator.isSplitScreenVisible()) {
                    splitScreenController.mStageCoordinator.switchSplitPosition("shellCommand");
                }
                return true;
            case "startIntentToCell":
                if (strArr.length < 2) {
                    printWriter.println("Error: start intent should be provided as arguments");
                    return false;
                }
                this.mController.startIntentToCell(null, makeBasicIntent(strArr[1]), null, strArr.length >= 3 ? Integer.parseInt(strArr[2]) : 0, false);
                return true;
            case "openInSplitWithAllApps":
                if (strArr.length >= 4) {
                    splitScreenController.openInSplitWithAllApps(Integer.parseInt(strArr[2]), makeBasicIntent(strArr[3]), null);
                    return true;
                }
                printWriter.println("Error: task id or intent to make split should be provided as arguments");
                printWriter.println("$ adb shell ~ WMShell openInSplitWithAllApps [taskId] [package]");
                return false;
            case "moveToSideStage":
                if (strArr.length < 3) {
                    printWriter.println("Error: task id should be provided as arguments");
                    return false;
                }
                int intValue = new Integer(strArr[1]).intValue();
                int intValue2 = strArr.length > 2 ? new Integer(strArr[2]).intValue() : 1;
                splitScreenController.getClass();
                splitScreenController.moveToStage(intValue, intValue2, new WindowContainerTransaction());
                return true;
            case "setSplitCreateMode":
                if (!CoreRune.MW_MULTI_SPLIT_CREATE_MODE) {
                    return false;
                }
                if (strArr.length < 3) {
                    printWriter.println("Error: A createMode should be provided as an argument");
                    return false;
                }
                splitScreenController.mStageCoordinator.setSplitCreateMode(Integer.parseInt(strArr[2]), true);
                if (CoreRune.MW_MULTI_SPLIT_SHELL_DUMP) {
                    printWriter.println("SplitScreenController");
                    StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
                    if (stageCoordinator != null) {
                        stageCoordinator.dump$2(printWriter, "");
                        return true;
                    }
                }
                return true;
            case "setSideStagePosition":
                if (strArr.length < 2) {
                    printWriter.println("Error: side stage position should be provided as arguments");
                    return false;
                }
                int intValue3 = new Integer(strArr[1]).intValue();
                if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
                    splitScreenController.setSideStagePosition(null, intValue3);
                    return true;
                }
                splitScreenController.mStageCoordinator.setSideStagePosition$1(null, intValue3);
                return true;
            case "startSplitTasks":
                if (strArr.length < 4) {
                    printWriter.println("Error: start multiple tasks should be provided as arguments");
                    return false;
                }
                this.mController.startSplitTasks(Integer.parseInt(strArr[1]), Integer.parseInt(strArr[2]), -1, false, 0, Float.parseFloat(strArr[3]), 0.5f);
                return true;
            case "startIntents":
                if (strArr.length < 4) {
                    printWriter.println("Error: start intents should be provided as arguments");
                    printWriter.println("$ adb shell ~ WMShell splitscreen startIntents pkg1 pkg2 pkg3(optional) [splitDivision] [parallelMultiSplit]");
                    return true;
                }
                Intent makeBasicIntent = makeBasicIntent(strArr[1]);
                Intent makeBasicIntent2 = makeBasicIntent(strArr[2]);
                if (strArr.length > 4) {
                    intent = makeBasicIntent(strArr[3]);
                    parseInt = Integer.parseInt(strArr[4]);
                    z = Boolean.parseBoolean(strArr[5]);
                } else {
                    parseInt = Integer.parseInt(strArr[3]);
                }
                int i = parseInt;
                boolean z2 = z;
                Intent intent2 = intent;
                StageCoordinator stageCoordinator2 = splitScreenController.mStageCoordinator;
                stageCoordinator2.getClass();
                UserHandle userHandle = UserHandle.CURRENT;
                stageCoordinator2.startSplitScreen(-1, null, makeBasicIntent, makeBasicIntent2, intent2, userHandle, userHandle, userHandle, 0, 0, 0.5f, 0.5f, 1, i, z2, null, null);
                return true;
            case "enterSplitByGesture":
                if (strArr.length < 4) {
                    printWriter.println("Error: gestureFrom should be provided");
                    return false;
                }
                if (strArr[2].equals("interface")) {
                    SplitScreenController.SplitScreenImpl splitScreenImpl = splitScreenController.mImpl;
                    int parseInt2 = Integer.parseInt(strArr[3]);
                    splitScreenImpl.getClass();
                    SplitScreenController.this.mMainExecutor.execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda9(splitScreenImpl, parseInt2, Debug.getCaller()));
                    return true;
                }
                return true;
            case "exitSplitScreen":
                if (strArr.length < 2) {
                    printWriter.println("Error: task id should be provided as arguments");
                    return false;
                }
                splitScreenController.exitSplitScreen(Integer.parseInt(strArr[1]), 0);
                return true;
            default:
                CarrierTextController$$ExternalSyntheticOutline0.m(new StringBuilder("Invalid command: "), strArr[0], printWriter);
                return false;
        }
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final void printShellCommandHelp(PrintWriter printWriter, String str) {
        AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "    moveToSideStage <taskId> <SideStagePosition>", "      Move a task with given id in split-screen mode.", "    setSideStagePosition <SideStagePosition>", "      Sets the position of the side-stage.");
        AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "    switchSplitPosition", "      Reverses the split.", "    exitSplitScreen <taskId>", "      Exits split screen and leaves the provided split task on top.");
    }
}
