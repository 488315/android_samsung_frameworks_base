package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.window.RemoteTransition;
import android.window.WindowContainerTransaction;
import com.android.systemui.R;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.shared.desktopmode.DesktopModeTransitionSource;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.windowdecor.WindowDecoration;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda31 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DesktopModeWindowDecorViewModel f$0;
    public final /* synthetic */ DesktopModeWindowDecoration f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda31(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, DesktopModeWindowDecoration desktopModeWindowDecoration, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = desktopModeWindowDecorViewModel;
        this.f$1 = desktopModeWindowDecoration;
        this.f$2 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                final DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.f$0;
                final DesktopModeWindowDecoration desktopModeWindowDecoration = this.f$1;
                ArrayList arrayList = (ArrayList) this.f$2;
                Function1 function1 = new Function1() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda34
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
                        DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel2 = desktopModeWindowDecorViewModel;
                        DesktopModeWindowDecoration desktopModeWindowDecoration2 = desktopModeWindowDecoration;
                        desktopModeWindowDecoration2.closeManageWindowsMenu();
                        ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration2.mTaskInfo;
                        int iIntValue = ((Integer) obj).intValue();
                        DesktopTasksController desktopTasksController = desktopModeWindowDecorViewModel2.mDesktopTasksController;
                        desktopTasksController.getClass();
                        if (runningTaskInfo.isFreeform()) {
                            ActivityManager.RunningTaskInfo runningTaskInfo2 = desktopTasksController.shellTaskOrganizer.getRunningTaskInfo(iIntValue);
                            if (runningTaskInfo2 == null || !runningTaskInfo2.isFreeform()) {
                                Integer orCreateDefaultDeskId = desktopTasksController.getOrCreateDefaultDeskId(runningTaskInfo.displayId, false);
                                if (orCreateDefaultDeskId != null) {
                                    DesktopTasksController.moveTaskToDesk$default(desktopTasksController, iIntValue, orCreateDefaultDeskId.intValue(), new WindowContainerTransaction(), DesktopModeTransitionSource.APP_HANDLE_MENU_BUTTON, null, 48);
                                }
                            } else {
                                desktopTasksController.moveTaskToFront(iIntValue, (RemoteTransition) null, DesktopModeEventLogger.Companion.UnminimizeReason.APP_HANDLE_MENU_BUTTON);
                            }
                        } else {
                            ActivityOptions activityOptionsCreateNewWindowOptions = desktopTasksController.createNewWindowOptions(runningTaskInfo);
                            SplitScreenController splitScreenController = desktopTasksController.splitScreenController;
                            if (splitScreenController == null) {
                                splitScreenController = null;
                            }
                            int iDetermineNewInstancePosition = splitScreenController.determineNewInstancePosition(runningTaskInfo);
                            SplitScreenController splitScreenController2 = desktopTasksController.splitScreenController;
                            (splitScreenController2 != null ? splitScreenController2 : null).startTask(iIntValue, iDetermineNewInstancePosition, activityOptionsCreateNewWindowOptions.toBundle());
                        }
                        desktopModeWindowDecorViewModel2.mDesktopModeUiEventLogger.log(desktopModeWindowDecoration2.mTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_MULTI_INSTANCE_MANAGE_WINDOWS_ICON_CLICK);
                        return Unit.INSTANCE;
                    }
                };
                if (!desktopModeWindowDecoration.mTaskInfo.isFreeform()) {
                    ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration.mTaskInfo;
                    SplitScreenController splitScreenController = desktopModeWindowDecoration.mSplitScreenController;
                    WindowDecoration.RelayoutResult relayoutResult = desktopModeWindowDecoration.mResult;
                    desktopModeWindowDecoration.mManageWindowsMenu = new DesktopHandleManageWindowsMenu(runningTaskInfo, splitScreenController, relayoutResult.mCaptionX, relayoutResult.mCaptionWidth, desktopModeWindowDecoration.mWindowManagerWrapper, desktopModeWindowDecoration.mDesktopState, desktopModeWindowDecoration.mContext, arrayList, function1, new DesktopModeWindowDecoration$$ExternalSyntheticLambda6(desktopModeWindowDecoration, 8));
                    break;
                } else {
                    Rect bounds = desktopModeWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds();
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = desktopModeWindowDecoration.mTaskInfo;
                    int i = bounds.left;
                    WindowDecoration.RelayoutResult relayoutResult2 = desktopModeWindowDecoration.mResult;
                    desktopModeWindowDecoration.mManageWindowsMenu = new DesktopHeaderManageWindowsMenu(runningTaskInfo2, i + relayoutResult2.mCaptionX, bounds.top + relayoutResult2.mCaptionY + relayoutResult2.mCaptionTopPadding, desktopModeWindowDecoration.mDisplayController, desktopModeWindowDecoration.mRootTaskDisplayAreaOrganizer, desktopModeWindowDecoration.mContext, desktopModeWindowDecoration.mDesktopUserRepositories, desktopModeWindowDecoration.mSurfaceControlBuilderSupplier, desktopModeWindowDecoration.mSurfaceControlTransactionSupplier, arrayList, function1, new DesktopModeWindowDecoration$$ExternalSyntheticLambda6(desktopModeWindowDecoration, 7));
                    break;
                }
            default:
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel2 = this.f$0;
                DesktopModeWindowDecoration desktopModeWindowDecoration2 = this.f$1;
                Runnable runnable = (Runnable) this.f$2;
                desktopModeWindowDecorViewModel2.mTransitionDragActive = false;
                desktopModeWindowDecorViewModel2.mMoveToDesktopAnimator = null;
                View view = desktopModeWindowDecoration2.mResult.mRootView;
                if (view != null) {
                    View viewFindViewById = ((WindowDecorLinearLayout) view).findViewById(R.id.caption_handle);
                    viewFindViewById.setHovered(false);
                    viewFindViewById.setPressed(false);
                }
                runnable.run();
                break;
        }
    }
}
