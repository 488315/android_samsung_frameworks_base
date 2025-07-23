package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Point;
import android.window.TaskSnapshot;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.ColorKt;
import com.android.systemui.R;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.multiinstance.ManageWindowsViewContainer;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalSystemViewContainer;
import com.android.wm.shell.windowdecor.common.DecorThemeUtil;
import com.android.wm.shell.windowdecor.common.DesktopMenuPositionUtilityKt;
import java.util.List;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopHandleManageWindowsMenu extends ManageWindowsViewContainer {
    public final ActivityManager.RunningTaskInfo callerTaskInfo;
    public final int captionWidth;
    public final int captionX;
    public final DesktopState desktopState;
    public AdditionalSystemViewContainer menuViewContainer;
    public final SplitScreenController splitScreenController;
    public final WindowManagerWrapper windowManagerWrapper;

    public DesktopHandleManageWindowsMenu(ActivityManager.RunningTaskInfo runningTaskInfo, SplitScreenController splitScreenController, int i, int i2, WindowManagerWrapper windowManagerWrapper, DesktopState desktopState, Context context, List<? extends Pair<Integer, ? extends TaskSnapshot>> list, Function1 function1, Function0 function0) {
        super(context, ColorKt.m467toArgb8_81llA(new DecorThemeUtil(context).getColorScheme(runningTaskInfo).background));
        this.callerTaskInfo = runningTaskInfo;
        this.splitScreenController = splitScreenController;
        this.captionX = i;
        this.captionWidth = i2;
        this.windowManagerWrapper = windowManagerWrapper;
        this.desktopState = desktopState;
        createMenu(list, function1, function0);
        animateOpen();
    }

    @Override // com.android.wm.shell.shared.multiinstance.ManageWindowsViewContainer
    public final void addToContainer(ManageWindowsViewContainer.ManageWindowsView manageWindowsView) {
        ActivityManager.RunningTaskInfo runningTaskInfo = this.callerTaskInfo;
        int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_handle_menu_margin_top);
        ManageWindowsViewContainer.ManageWindowsView manageWindowsView2 = this.menuView;
        if (manageWindowsView2 == null) {
            manageWindowsView2 = null;
        }
        Point calculateMenuPosition = DesktopMenuPositionUtilityKt.calculateMenuPosition(this.splitScreenController, runningTaskInfo, 0, dimensionPixelSize, this.captionX, 0, this.captionWidth, manageWindowsView2.menuWidth, MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.context) == 1);
        this.menuViewContainer = new AdditionalSystemViewContainer(this.windowManagerWrapper, this.callerTaskInfo.taskId, calculateMenuPosition.x, calculateMenuPosition.y, manageWindowsView.menuWidth, manageWindowsView.menuHeight, 262152, 0, this.desktopState.canEnterDesktopModeOrShowAppHandle(), manageWindowsView.rootView, 128, (DefaultConstructorMarker) null);
    }

    @Override // com.android.wm.shell.shared.multiinstance.ManageWindowsViewContainer
    public final void removeFromContainer() {
        AdditionalSystemViewContainer additionalSystemViewContainer = this.menuViewContainer;
        if (additionalSystemViewContainer != null) {
            additionalSystemViewContainer.releaseView();
        }
    }
}
