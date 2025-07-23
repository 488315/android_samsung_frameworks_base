package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.app.ActivityOptions;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda23 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DesktopModeWindowDecorViewModel f$0;
    public final /* synthetic */ DesktopModeWindowDecoration f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda23(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, DesktopModeWindowDecoration desktopModeWindowDecoration, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = desktopModeWindowDecorViewModel;
        this.f$1 = desktopModeWindowDecoration;
        this.f$2 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.f$0;
                DesktopModeWindowDecoration desktopModeWindowDecoration = this.f$1;
                Runnable runnable = (Runnable) this.f$2;
                desktopModeWindowDecorViewModel.mTransitionDragActive = false;
                desktopModeWindowDecorViewModel.mMoveToDesktopAnimator = null;
                View view = desktopModeWindowDecoration.mResult.mRootView;
                if (view != null) {
                    View findViewById = ((WindowDecorLinearLayout) view).findViewById(R.id.caption_handle);
                    findViewById.setHovered(false);
                    findViewById.setPressed(false);
                }
                runnable.run();
                break;
            default:
                final DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel2 = this.f$0;
                final DesktopModeWindowDecoration desktopModeWindowDecoration2 = this.f$1;
                ArrayList arrayList = (ArrayList) this.f$2;
                desktopModeWindowDecorViewModel2.getClass();
                Function1 function1 = new Function1() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda32
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel3 = DesktopModeWindowDecorViewModel.this;
                        desktopModeWindowDecorViewModel3.getClass();
                        DesktopModeWindowDecoration desktopModeWindowDecoration3 = desktopModeWindowDecoration2;
                        desktopModeWindowDecoration3.closeManageWindowsMenu();
                        ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration3.mTaskInfo;
                        int intValue = ((Integer) obj).intValue();
                        DesktopTasksController desktopTasksController = desktopModeWindowDecorViewModel3.mDesktopTasksController;
                        desktopTasksController.getClass();
                        if (runningTaskInfo.isFreeform()) {
                            ActivityManager.RunningTaskInfo runningTaskInfo2 = desktopTasksController.shellTaskOrganizer.getRunningTaskInfo(intValue);
                            if (runningTaskInfo2 == null || !runningTaskInfo2.isFreeform()) {
                                Integer orCreateDefaultDeskId = desktopTasksController.getOrCreateDefaultDeskId(runningTaskInfo.displayId, false);
                                if (orCreateDefaultDeskId != null) {
                                    DesktopTasksController.moveTaskToDesk$default(desktopTasksController, intValue, orCreateDefaultDeskId.intValue(), new WindowContainerTransaction(), DesktopModeTransitionSource.APP_HANDLE_MENU_BUTTON, null, 48);
                                }
                            } else {
                                desktopTasksController.moveTaskToFront(intValue, (RemoteTransition) null, DesktopModeEventLogger.Companion.UnminimizeReason.APP_HANDLE_MENU_BUTTON);
                            }
                        } else {
                            ActivityOptions createNewWindowOptions = desktopTasksController.createNewWindowOptions(runningTaskInfo);
                            SplitScreenController splitScreenController = desktopTasksController.splitScreenController;
                            if (splitScreenController == null) {
                                splitScreenController = null;
                            }
                            int determineNewInstancePosition = splitScreenController.determineNewInstancePosition(runningTaskInfo);
                            SplitScreenController splitScreenController2 = desktopTasksController.splitScreenController;
                            (splitScreenController2 != null ? splitScreenController2 : null).startTask(intValue, determineNewInstancePosition, createNewWindowOptions.toBundle());
                        }
                        desktopModeWindowDecorViewModel3.mDesktopModeUiEventLogger.log(desktopModeWindowDecoration3.mTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_MULTI_INSTANCE_MANAGE_WINDOWS_ICON_CLICK);
                        return Unit.INSTANCE;
                    }
                };
                if (!desktopModeWindowDecoration2.mTaskInfo.isFreeform()) {
                    ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration2.mTaskInfo;
                    SplitScreenController splitScreenController = desktopModeWindowDecoration2.mSplitScreenController;
                    WindowDecoration.RelayoutResult relayoutResult = desktopModeWindowDecoration2.mResult;
                    desktopModeWindowDecoration2.mManageWindowsMenu = new DesktopHandleManageWindowsMenu(runningTaskInfo, splitScreenController, relayoutResult.mCaptionX, relayoutResult.mCaptionWidth, desktopModeWindowDecoration2.mWindowManagerWrapper, desktopModeWindowDecoration2.mDesktopState, desktopModeWindowDecoration2.mContext, arrayList, function1, new DesktopModeWindowDecoration$$ExternalSyntheticLambda8(desktopModeWindowDecoration2, 7));
                    break;
                } else {
                    Rect bounds = desktopModeWindowDecoration2.mTaskInfo.configuration.windowConfiguration.getBounds();
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = desktopModeWindowDecoration2.mTaskInfo;
                    int i = bounds.left;
                    WindowDecoration.RelayoutResult relayoutResult2 = desktopModeWindowDecoration2.mResult;
                    desktopModeWindowDecoration2.mManageWindowsMenu = new DesktopHeaderManageWindowsMenu(runningTaskInfo2, i + relayoutResult2.mCaptionX, bounds.top + relayoutResult2.mCaptionY + relayoutResult2.mCaptionTopPadding, desktopModeWindowDecoration2.mDisplayController, desktopModeWindowDecoration2.mRootTaskDisplayAreaOrganizer, desktopModeWindowDecoration2.mContext, desktopModeWindowDecoration2.mDesktopUserRepositories, desktopModeWindowDecoration2.mSurfaceControlBuilderSupplier, desktopModeWindowDecoration2.mSurfaceControlTransactionSupplier, arrayList, function1, new DesktopModeWindowDecoration$$ExternalSyntheticLambda8(desktopModeWindowDecoration2, 6));
                    break;
                }
        }
    }
}
