package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.UserProfileContexts;
import com.android.wm.shell.compatui.CompatUIController;
import com.android.wm.shell.compatui.impl.CompatUIRequests;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.shared.desktopmode.DesktopModeTransitionSource;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.windowdecor.MultiTaskingCaptionButtonLogger;
import com.samsung.android.rune.CoreRune;
import java.lang.reflect.Method;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DesktopModeWindowDecorViewModel f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = desktopModeWindowDecorViewModel;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                CompatUIController.launchUserAspectRatioSettings(this.f$0.mContext, (ActivityManager.RunningTaskInfo) this.f$1);
                break;
            case 1:
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.f$1;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.f$0;
                desktopModeWindowDecorViewModel.getClass();
                desktopModeWindowDecorViewModel.mCompatUI.sendCompatUIRequest(new CompatUIRequests.DisplayCompatShowRestartDialog(runningTaskInfo.taskId));
                break;
            case 2:
                ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) this.f$1;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel2 = this.f$0;
                desktopModeWindowDecorViewModel2.getClass();
                if (CoreRune.MW_SA_LOGGING) {
                    ComponentName componentName = runningTaskInfo2.topActivity;
                    String packageName = componentName != null ? componentName.getPackageName() : "";
                    MultiTaskingCaptionButtonLogger multiTaskingCaptionButtonLogger = desktopModeWindowDecorViewModel2.mCaptionButtonLogger;
                    multiTaskingCaptionButtonLogger.getClass();
                    multiTaskingCaptionButtonLogger.invokeLog((Method) MultiTaskingCaptionButtonLogger.sLoggerMethods.get(new MultiTaskingCaptionButtonLogger.CaptionLoggerPairKey(multiTaskingCaptionButtonLogger, 13, 2, true, 5)), packageName);
                }
                desktopModeWindowDecorViewModel2.onEnterOrExitImmersive(runningTaskInfo2);
                break;
            case 3:
                ActivityManager.RunningTaskInfo runningTaskInfo3 = (ActivityManager.RunningTaskInfo) this.f$1;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel3 = this.f$0;
                desktopModeWindowDecorViewModel3.getClass();
                int i = runningTaskInfo3.taskId;
                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel3.mWindowDecorByTaskId.get(i);
                if (desktopModeWindowDecoration != null) {
                    desktopModeWindowDecoration.closeHandleMenu();
                    if (desktopModeWindowDecorViewModel3.isTaskInSplitScreen(i)) {
                        desktopModeWindowDecorViewModel3.mSplitScreenController.moveTaskToFullscreen(i);
                    } else {
                        desktopModeWindowDecorViewModel3.mDesktopTasksController.moveToFullscreen(i, DesktopModeTransitionSource.APP_HANDLE_MENU_BUTTON);
                    }
                    desktopModeWindowDecorViewModel3.mDesktopModeUiEventLogger.log(desktopModeWindowDecoration.mTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_APP_HANDLE_MENU_TAP_TO_FULL_SCREEN);
                }
                break;
            case 4:
                ActivityManager.RunningTaskInfo runningTaskInfo4 = (ActivityManager.RunningTaskInfo) this.f$1;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel4 = this.f$0;
                desktopModeWindowDecorViewModel4.getClass();
                DesktopModeWindowDecoration desktopModeWindowDecoration2 = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel4.mWindowDecorByTaskId.get(runningTaskInfo4.taskId);
                if (desktopModeWindowDecoration2 != null) {
                    desktopModeWindowDecoration2.closeHandleMenu();
                    desktopModeWindowDecorViewModel4.mDesktopTasksController.requestSplit(desktopModeWindowDecoration2.mTaskInfo, false);
                    desktopModeWindowDecorViewModel4.mDesktopModeUiEventLogger.log(desktopModeWindowDecoration2.mTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_APP_HANDLE_MENU_TAP_TO_SPLIT_SCREEN);
                }
                break;
            case 5:
                ActivityManager.RunningTaskInfo runningTaskInfo5 = (ActivityManager.RunningTaskInfo) this.f$1;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel5 = this.f$0;
                desktopModeWindowDecorViewModel5.getClass();
                DesktopModeWindowDecoration desktopModeWindowDecoration3 = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel5.mWindowDecorByTaskId.get(runningTaskInfo5.taskId);
                if (desktopModeWindowDecoration3 != null) {
                    desktopModeWindowDecoration3.closeHandleMenu();
                    desktopModeWindowDecoration3.disposeStatusBarInputLayer();
                    desktopModeWindowDecorViewModel5.mDesktopTasksController.requestFloat(desktopModeWindowDecoration3.mTaskInfo, null);
                }
                break;
            case 6:
                ActivityManager.RunningTaskInfo runningTaskInfo6 = (ActivityManager.RunningTaskInfo) this.f$1;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel6 = this.f$0;
                desktopModeWindowDecorViewModel6.getClass();
                DesktopModeWindowDecoration desktopModeWindowDecoration4 = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel6.mWindowDecorByTaskId.get(runningTaskInfo6.taskId);
                if (desktopModeWindowDecoration4 != null) {
                    desktopModeWindowDecoration4.closeHandleMenu();
                    ActivityManager.RunningTaskInfo runningTaskInfo7 = desktopModeWindowDecoration4.mTaskInfo;
                    DesktopTasksController desktopTasksController = desktopModeWindowDecorViewModel6.mDesktopTasksController;
                    desktopTasksController.getClass();
                    ComponentName componentName2 = runningTaskInfo7.baseActivity;
                    if (componentName2 != null) {
                        UserHandle of = UserHandle.of(runningTaskInfo7.userId);
                        int i2 = runningTaskInfo7.userId;
                        UserProfileContexts userProfileContexts = desktopTasksController.userProfileContexts;
                        Context context = (Context) userProfileContexts.currentProfilesContext.get(i2);
                        if (context == null) {
                            context = userProfileContexts.baseContext.createContextAsUser(UserHandle.of(i2), 0);
                            userProfileContexts.currentProfilesContext.set(i2, context);
                        }
                        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(componentName2.getPackageName());
                        if (launchIntentForPackage != null) {
                            launchIntentForPackage.setFlags(402653184);
                            PendingIntent activityAsUser = PendingIntent.getActivityAsUser(desktopTasksController.context, 0, launchIntentForPackage, 67108864, null, of);
                            ActivityOptions createNewWindowOptions = desktopTasksController.createNewWindowOptions(runningTaskInfo7);
                            int launchWindowingMode = createNewWindowOptions.getLaunchWindowingMode();
                            if (launchWindowingMode == 5) {
                                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                windowContainerTransaction.sendPendingIntent(activityAsUser, launchIntentForPackage, createNewWindowOptions.toBundle());
                                Integer deskIdForTask = desktopTasksController.taskRepository.getDeskIdForTask(runningTaskInfo7.taskId);
                                if (deskIdForTask != null || (deskIdForTask = desktopTasksController.getOrCreateDefaultDeskId(runningTaskInfo7.displayId, false)) != null) {
                                    DesktopTasksController.startLaunchTransition$default(desktopTasksController, windowContainerTransaction, deskIdForTask.intValue(), runningTaskInfo7.displayId);
                                }
                            } else if (launchWindowingMode == 6) {
                                SplitScreenController splitScreenController = desktopTasksController.splitScreenController;
                                if (splitScreenController == null) {
                                    splitScreenController = null;
                                }
                                int determineNewInstancePosition = splitScreenController.determineNewInstancePosition(runningTaskInfo7);
                                SplitScreenController splitScreenController2 = desktopTasksController.splitScreenController;
                                (splitScreenController2 == null ? null : splitScreenController2).startIntent(activityAsUser, desktopTasksController.context.getUserId(), launchIntentForPackage, determineNewInstancePosition, createNewWindowOptions.toBundle(), null, true, -1, -1, 0, false);
                            }
                        }
                    }
                    desktopModeWindowDecorViewModel6.mDesktopModeUiEventLogger.log(desktopModeWindowDecoration4.mTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_MULTI_INSTANCE_NEW_WINDOW_CLICK);
                }
                break;
            default:
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel7 = this.f$0;
                desktopModeWindowDecorViewModel7.getClass();
                DesktopModeWindowDecoration desktopModeWindowDecoration5 = (DesktopModeWindowDecoration) this.f$1;
                desktopModeWindowDecoration5.closeHandleMenu();
                desktopModeWindowDecorViewModel7.mBgExecutor.execute(new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda29(0, desktopModeWindowDecorViewModel7, desktopModeWindowDecoration5));
                break;
        }
        return Unit.INSTANCE;
    }
}
