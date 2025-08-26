package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.os.UserHandle;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.compatui.CompatUIController;
import com.android.wm.shell.compatui.impl.CompatUIRequests;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.shared.desktopmode.DesktopModeTransitionSource;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.windowdecor.MultiTaskingCaptionButtonLogger;
import com.samsung.android.rune.CoreRune;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DesktopModeWindowDecorViewModel f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = desktopModeWindowDecorViewModel;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException, InvocationTargetException {
        switch (this.$r8$classId) {
            case 0:
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.f$0;
                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.f$1;
                desktopModeWindowDecoration.closeHandleMenu();
                desktopModeWindowDecorViewModel.mBgExecutor.execute(new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda28(0, desktopModeWindowDecorViewModel, desktopModeWindowDecoration));
                break;
            case 1:
                CompatUIController.launchUserAspectRatioSettings(this.f$0.mContext, (ActivityManager.RunningTaskInfo) this.f$1);
                break;
            case 2:
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.f$1;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel2 = this.f$0;
                desktopModeWindowDecorViewModel2.getClass();
                desktopModeWindowDecorViewModel2.mCompatUI.sendCompatUIRequest(new CompatUIRequests.DisplayCompatShowRestartDialog(runningTaskInfo.taskId));
                break;
            case 3:
                ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) this.f$1;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel3 = this.f$0;
                if (CoreRune.MW_SA_LOGGING) {
                    ComponentName componentName = runningTaskInfo2.topActivity;
                    String packageName = componentName != null ? componentName.getPackageName() : "";
                    MultiTaskingCaptionButtonLogger multiTaskingCaptionButtonLogger = desktopModeWindowDecorViewModel3.mCaptionButtonLogger;
                    multiTaskingCaptionButtonLogger.getClass();
                    multiTaskingCaptionButtonLogger.invokeLog((Method) MultiTaskingCaptionButtonLogger.sLoggerMethods.get(new MultiTaskingCaptionButtonLogger.CaptionLoggerPairKey(multiTaskingCaptionButtonLogger, 13, 2, true, 5)), packageName);
                }
                desktopModeWindowDecorViewModel3.onEnterOrExitImmersive(runningTaskInfo2);
                break;
            case 4:
                ActivityManager.RunningTaskInfo runningTaskInfo3 = (ActivityManager.RunningTaskInfo) this.f$1;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel4 = this.f$0;
                int i = runningTaskInfo3.taskId;
                DesktopModeWindowDecoration desktopModeWindowDecoration2 = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel4.mWindowDecorByTaskId.get(i);
                if (desktopModeWindowDecoration2 != null) {
                    desktopModeWindowDecoration2.closeHandleMenu();
                    if (desktopModeWindowDecorViewModel4.isTaskInSplitScreen(i)) {
                        desktopModeWindowDecorViewModel4.mSplitScreenController.moveTaskToFullscreen(i);
                    } else {
                        desktopModeWindowDecorViewModel4.mDesktopTasksController.moveToFullscreen(i, DesktopModeTransitionSource.APP_HANDLE_MENU_BUTTON);
                    }
                    desktopModeWindowDecorViewModel4.mDesktopModeUiEventLogger.log(desktopModeWindowDecoration2.mTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_APP_HANDLE_MENU_TAP_TO_FULL_SCREEN);
                }
                break;
            case 5:
                ActivityManager.RunningTaskInfo runningTaskInfo4 = (ActivityManager.RunningTaskInfo) this.f$1;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel5 = this.f$0;
                desktopModeWindowDecorViewModel5.getClass();
                DesktopModeWindowDecoration desktopModeWindowDecoration3 = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel5.mWindowDecorByTaskId.get(runningTaskInfo4.taskId);
                if (desktopModeWindowDecoration3 != null) {
                    desktopModeWindowDecoration3.closeHandleMenu();
                    desktopModeWindowDecorViewModel5.mDesktopTasksController.requestSplit(desktopModeWindowDecoration3.mTaskInfo, false);
                    desktopModeWindowDecorViewModel5.mDesktopModeUiEventLogger.log(desktopModeWindowDecoration3.mTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_APP_HANDLE_MENU_TAP_TO_SPLIT_SCREEN);
                }
                break;
            case 6:
                ActivityManager.RunningTaskInfo runningTaskInfo5 = (ActivityManager.RunningTaskInfo) this.f$1;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel6 = this.f$0;
                desktopModeWindowDecorViewModel6.getClass();
                DesktopModeWindowDecoration desktopModeWindowDecoration4 = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel6.mWindowDecorByTaskId.get(runningTaskInfo5.taskId);
                if (desktopModeWindowDecoration4 != null) {
                    desktopModeWindowDecoration4.closeHandleMenu();
                    desktopModeWindowDecoration4.disposeStatusBarInputLayer();
                    desktopModeWindowDecorViewModel6.mDesktopTasksController.requestFloat(desktopModeWindowDecoration4.mTaskInfo, null);
                }
                break;
            default:
                ActivityManager.RunningTaskInfo runningTaskInfo6 = (ActivityManager.RunningTaskInfo) this.f$1;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel7 = this.f$0;
                DesktopModeWindowDecoration desktopModeWindowDecoration5 = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel7.mWindowDecorByTaskId.get(runningTaskInfo6.taskId);
                if (desktopModeWindowDecoration5 != null) {
                    desktopModeWindowDecoration5.closeHandleMenu();
                    ActivityManager.RunningTaskInfo runningTaskInfo7 = desktopModeWindowDecoration5.mTaskInfo;
                    DesktopTasksController desktopTasksController = desktopModeWindowDecorViewModel7.mDesktopTasksController;
                    desktopTasksController.getClass();
                    ComponentName componentName2 = runningTaskInfo7.baseActivity;
                    if (componentName2 != null) {
                        UserHandle userHandleOf = UserHandle.of(runningTaskInfo7.userId);
                        Intent launchIntentForPackage = desktopTasksController.userProfileContexts.getOrCreate(runningTaskInfo7.userId).getPackageManager().getLaunchIntentForPackage(componentName2.getPackageName());
                        if (launchIntentForPackage != null) {
                            launchIntentForPackage.setFlags(402653184);
                            PendingIntent activityAsUser = PendingIntent.getActivityAsUser(desktopTasksController.context, 0, launchIntentForPackage, 67108864, null, userHandleOf);
                            ActivityOptions activityOptionsCreateNewWindowOptions = desktopTasksController.createNewWindowOptions(runningTaskInfo7);
                            int launchWindowingMode = activityOptionsCreateNewWindowOptions.getLaunchWindowingMode();
                            if (launchWindowingMode == 5) {
                                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                windowContainerTransaction.sendPendingIntent(activityAsUser, launchIntentForPackage, activityOptionsCreateNewWindowOptions.toBundle());
                                Integer deskIdForTask = desktopTasksController.taskRepository.getDeskIdForTask(runningTaskInfo7.taskId);
                                if (deskIdForTask != null || (deskIdForTask = desktopTasksController.getOrCreateDefaultDeskId(runningTaskInfo7.displayId, false)) != null) {
                                    DesktopTasksController.startLaunchTransition$default(desktopTasksController, windowContainerTransaction, deskIdForTask.intValue(), runningTaskInfo7.displayId);
                                }
                            } else if (launchWindowingMode == 6) {
                                SplitScreenController splitScreenController = desktopTasksController.splitScreenController;
                                if (splitScreenController == null) {
                                    splitScreenController = null;
                                }
                                int iDetermineNewInstancePosition = splitScreenController.determineNewInstancePosition(runningTaskInfo7);
                                SplitScreenController splitScreenController2 = desktopTasksController.splitScreenController;
                                (splitScreenController2 == null ? null : splitScreenController2).startIntent(activityAsUser, desktopTasksController.context.getUserId(), launchIntentForPackage, iDetermineNewInstancePosition, activityOptionsCreateNewWindowOptions.toBundle(), null, true, -1, -1, 0, false);
                            }
                        }
                    }
                    desktopModeWindowDecorViewModel7.mDesktopModeUiEventLogger.log(desktopModeWindowDecoration5.mTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_MULTI_INSTANCE_NEW_WINDOW_CLICK);
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
