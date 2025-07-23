package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.window.DesktopExperienceFlags;
import android.window.RemoteTransition;
import com.android.systemui.biometrics.AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.shared.desktopmode.DesktopModeTransitionSource;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.transition.FocusTransitionObserver;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopModeShellCommandHandler implements ShellCommandHandler.ShellCommandActionHandler {
    public final DesktopTasksController controller;
    public final FocusTransitionObserver focusTransitionObserver;

    public DesktopModeShellCommandHandler(DesktopTasksController desktopTasksController, FocusTransitionObserver focusTransitionObserver) {
        this.controller = desktopTasksController;
        this.focusTransitionObserver = focusTransitionObserver;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final boolean onShellCommand(PrintWriter printWriter, String[] strArr) {
        int parseInt;
        int parseInt2;
        ActivityManager.RunningTaskInfo runningTaskInfo;
        String str = strArr[0];
        int hashCode = str.hashCode();
        int i = -1;
        DesktopTasksController desktopTasksController = this.controller;
        switch (hashCode) {
            case -1839711158:
                if (str.equals("moveTaskToDesk")) {
                    if (strArr.length < 2) {
                        printWriter.println("Error: task id should be provided as arguments");
                    } else {
                        try {
                            int parseInt3 = Integer.parseInt(strArr[1]);
                            if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                                break;
                            } else if (strArr.length < 3) {
                                printWriter.println("Error: desk id should be provided as arguments");
                            } else {
                                try {
                                    DesktopTasksController.moveTaskToDesk$default(this.controller, parseInt3, Integer.parseInt(strArr[2]), null, DesktopModeTransitionSource.UNKNOWN, null, 52);
                                    printWriter.println("Not implemented.");
                                    break;
                                } catch (NumberFormatException unused) {
                                    printWriter.println("Error: desk id should be an integer");
                                }
                            }
                        } catch (NumberFormatException unused2) {
                            printWriter.println("Error: task id should be an integer");
                        }
                    }
                    break;
                }
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", strArr[0]);
                break;
            case -1730550360:
                if (str.equals("moveTaskOutOfDesk")) {
                    if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                        printWriter.println("Not supported.");
                    } else if (strArr.length < 2) {
                        printWriter.println("Error: task id should be provided as arguments");
                    } else {
                        try {
                            desktopTasksController.moveToFullscreen(Integer.parseInt(strArr[1]), DesktopModeTransitionSource.UNKNOWN);
                            break;
                        } catch (NumberFormatException unused3) {
                            printWriter.println("Error: task id should be an integer");
                        }
                    }
                    break;
                }
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", strArr[0]);
                break;
            case -1686444368:
                if (str.equals("getActiveDeskId")) {
                    if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                        printWriter.println("Not supported.");
                    } else if (strArr.length < 2) {
                        printWriter.println("Error: task id should be provided as arguments");
                    } else {
                        try {
                            Integer.parseInt(strArr[1]);
                            printWriter.println("Not implemented.");
                        } catch (NumberFormatException unused4) {
                            printWriter.println("Error: display id should be an integer");
                        }
                    }
                    break;
                }
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", strArr[0]);
                break;
            case -1195207124:
                if (str.equals("activateDesk")) {
                    if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                        printWriter.println("Not supported.");
                    } else if (strArr.length < 2) {
                        printWriter.println("Error: desk id should be provided as arguments");
                    } else {
                        try {
                            int parseInt4 = Integer.parseInt(strArr[1]);
                            if (strArr.length < 3) {
                                printWriter.println("Info: No input provided for display id. No display change for desk.");
                                parseInt = -1;
                            } else {
                                try {
                                    parseInt = Integer.parseInt(strArr[2]);
                                } catch (NumberFormatException unused5) {
                                    printWriter.println("Error: display id should be an integer");
                                }
                            }
                            if (strArr.length < 4) {
                                printWriter.println("Info: No input provided for task id. No task to top.");
                            } else {
                                try {
                                    i = Integer.parseInt(strArr[3]);
                                } catch (NumberFormatException unused6) {
                                    printWriter.println("Error: task id should be an integer");
                                }
                            }
                            DesktopTasksController.activateDesk$default(this.controller, parseInt4, null, parseInt, i, 2);
                            break;
                        } catch (NumberFormatException unused7) {
                            printWriter.println("Error: desk id should be an integer");
                        }
                    }
                    break;
                }
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", strArr[0]);
                break;
            case -1194240360:
                if (str.equals("moveTaskToFront")) {
                    if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                        printWriter.println("Not supported.");
                    } else if (strArr.length < 2) {
                        printWriter.println("Error: task id should be provided as arguments");
                    } else {
                        try {
                            desktopTasksController.moveTaskToFront(Integer.parseInt(strArr[1]), (RemoteTransition) null, DesktopModeEventLogger.Companion.UnminimizeReason.UNKNOWN);
                            break;
                        } catch (NumberFormatException unused8) {
                            printWriter.println("Error: task id should be an integer");
                        }
                    }
                    break;
                }
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", strArr[0]);
                break;
            case -374927963:
                if (str.equals("canCreateDesk")) {
                    if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                        try {
                            Integer.parseInt(strArr[1]);
                            printWriter.println("Not implemented.");
                        } catch (NumberFormatException unused9) {
                            printWriter.println("Error: display id should be an integer");
                        }
                    } else {
                        printWriter.println("Not supported.");
                    }
                    break;
                }
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", strArr[0]);
                break;
            case 3095028:
                if (str.equals("dump")) {
                    desktopTasksController.dump$2(printWriter, "");
                    break;
                }
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", strArr[0]);
                break;
            case 178446461:
                if (str.equals("removeAllDesks")) {
                    if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                        printWriter.println("Not supported.");
                        break;
                    } else {
                        printWriter.println("Not implemented.");
                        break;
                    }
                }
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", strArr[0]);
                break;
            case 442459006:
                if (str.equals("removeAllVisibleRecentTasks")) {
                    if (desktopTasksController.recentTasksController != null) {
                        ActivityTaskManager.getService().removeAllVisibleRecentTasksExt(true, true);
                        break;
                    }
                }
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", strArr[0]);
                break;
            case 677170723:
                if (str.equals("minimizeAll")) {
                    if (strArr.length < 2) {
                        printWriter.println("Error: display id should be provided as arguments");
                    } else {
                        try {
                            desktopTasksController.minimizeAllTasks(Integer.parseInt(strArr[1]));
                            break;
                        } catch (NumberFormatException unused10) {
                            printWriter.println("Error: display id should be an integer");
                        }
                    }
                    break;
                }
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", strArr[0]);
                break;
            case 1098090813:
                if (str.equals("removeDesk")) {
                    if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                        printWriter.println("Not supported.");
                    } else if (strArr.length < 2) {
                        printWriter.println("Error: desk id should be provided as arguments");
                    } else {
                        try {
                            DesktopTasksController.removeDesk$default(desktopTasksController, Integer.parseInt(strArr[1]));
                            break;
                        } catch (NumberFormatException unused11) {
                            printWriter.println("Error: desk id should be an integer");
                        }
                    }
                    break;
                }
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", strArr[0]);
                break;
            case 1363904463:
                if (str.equals("removeAllTasksInDesk")) {
                    if (strArr.length < 2) {
                        printWriter.println("Error: desk id should be provided as arguments");
                    } else {
                        try {
                            desktopTasksController.removeAllTasksInDesk(Integer.parseInt(strArr[1]));
                            break;
                        } catch (NumberFormatException unused12) {
                            printWriter.println("Error: desk id should be an integer");
                        }
                    }
                    break;
                }
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", strArr[0]);
                break;
            case 1368733109:
                if (str.equals("createDesk")) {
                    if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                        printWriter.println("Not supported.");
                    } else if (strArr.length < 2) {
                        printWriter.println("Error: desk id should be provided as arguments");
                    } else {
                        try {
                            DesktopTasksController.createDesk$default(this.controller, Integer.parseInt(strArr[1]), 0, false, null, 62);
                            break;
                        } catch (NumberFormatException unused13) {
                            printWriter.println("Error: display id should be an integer");
                        }
                    }
                    break;
                }
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", strArr[0]);
                break;
            case 1591557251:
                if (str.equals("moveToNextDisplay")) {
                    if (strArr.length < 2) {
                        FocusTransitionObserver focusTransitionObserver = this.focusTransitionObserver;
                        int i2 = focusTransitionObserver.mFocusedDisplayId;
                        parseInt2 = (i2 == -1 || (runningTaskInfo = (ActivityManager.RunningTaskInfo) focusTransitionObserver.mFocusedTaskOnDisplay.get(i2)) == null) ? -1 : runningTaskInfo.taskId;
                    } else {
                        try {
                            parseInt2 = Integer.parseInt(strArr[1]);
                        } catch (NumberFormatException unused14) {
                            printWriter.println("Error: task id should be an integer");
                        }
                    }
                    if (parseInt2 != -1) {
                        desktopTasksController.moveToNextDisplay(parseInt2);
                        break;
                    } else {
                        printWriter.println("Error: no appropriate task found");
                        break;
                    }
                }
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", strArr[0]);
                break;
            default:
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", strArr[0]);
                break;
        }
        return true;
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final void printShellCommandHelp(PrintWriter printWriter, String str) {
        if (!DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "     moveTaskToDesk <taskId> ", "      Move a task with given id to desktop mode.", "     moveToNextDisplay <taskId> ", "      Move a task with given id to next display.");
            return;
        }
        AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "     moveTaskToDesk <taskId> <deskId>", "      Move a task with given id to the given desk and activate it.", "     moveToNextDisplay <taskId>", "      Move a task with given id to next display.");
        AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "     createDesk <displayId>", "      Creates a desk on the given display.", "     activateDesk <deskId> [displayId=-1] [taskId=-1]", "      Activates the given desk.");
        AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "     removeDesk <deskId> ", "      Removes the given desk and all of its windows.", "     removeAllDesks", "      Removes all the desks and their windows across all displays");
        AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "     moveTaskToFront <taskId>", "      Moves a task in front of its siblings.", "     moveTaskOutOfDesk <taskId>", "      Moves the given desktop task out of the desk into fullscreen mode.");
        AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "     canCreateDesk <displayId>", "      Whether creating a new desk in the given display is allowed.", "     getActivateDeskId <displayId>", "      Print the id of the active desk in the given display.");
        AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "     minimizeAll <displayId>", "      Minimize all tasks on the given display.", "     removeAllTasksInDesk <deskId>", "      Remove all tasks in given desk.");
        printWriter.println("     removeAllVisibleRecentTasks");
        printWriter.println("      Remove all visible recent tasks without long live task and desk task.");
    }
}
