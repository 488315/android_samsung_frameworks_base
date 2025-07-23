package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Point;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.window.DesktopModeFlags;
import android.window.InputTransferToken;
import android.window.TaskSnapshot;
import androidx.compose.ui.graphics.ColorKt;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.shared.multiinstance.ManageWindowsViewContainer;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalSystemViewContainer;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewContainer;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewHostViewContainer;
import com.android.wm.shell.windowdecor.common.DecorThemeUtil;
import java.util.List;
import java.util.function.Supplier;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopHeaderManageWindowsMenu extends ManageWindowsViewContainer {
    public final ActivityManager.RunningTaskInfo callerTaskInfo;
    public final DesktopUserRepositories desktopUserRepositories;
    public final DisplayController displayController;
    public AdditionalViewContainer menuViewContainer;
    public final RootTaskDisplayAreaOrganizer rootTdaOrganizer;
    public final Supplier surfaceControlBuilderSupplier;
    public final Supplier surfaceControlTransactionSupplier;
    public final int x;
    public final int y;

    public DesktopHeaderManageWindowsMenu(ActivityManager.RunningTaskInfo runningTaskInfo, int i, int i2, DisplayController displayController, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, Context context, DesktopUserRepositories desktopUserRepositories, Supplier<SurfaceControl.Builder> supplier, Supplier<SurfaceControl.Transaction> supplier2, List<? extends Pair<Integer, ? extends TaskSnapshot>> list, Function1 function1, Function0 function0) {
        super(context, ColorKt.m467toArgb8_81llA(new DecorThemeUtil(context).getColorScheme(runningTaskInfo).background));
        this.callerTaskInfo = runningTaskInfo;
        this.x = i;
        this.y = i2;
        this.displayController = displayController;
        this.rootTdaOrganizer = rootTaskDisplayAreaOrganizer;
        this.desktopUserRepositories = desktopUserRepositories;
        this.surfaceControlBuilderSupplier = supplier;
        this.surfaceControlTransactionSupplier = supplier2;
        createMenu(list, function1, function0);
        ManageWindowsViewContainer.ManageWindowsView manageWindowsView = this.menuView;
        (manageWindowsView == null ? null : manageWindowsView).rootView.setPivotX(0.0f);
        ManageWindowsViewContainer.ManageWindowsView manageWindowsView2 = this.menuView;
        (manageWindowsView2 != null ? manageWindowsView2 : null).rootView.setPivotY(0.0f);
        animateOpen();
    }

    @Override // com.android.wm.shell.shared.multiinstance.ManageWindowsViewContainer
    public final void addToContainer(ManageWindowsViewContainer.ManageWindowsView manageWindowsView) {
        AdditionalViewContainer additionalViewHostViewContainer;
        Point point = new Point(this.x, this.y);
        DesktopRepository profile = this.desktopUserRepositories.getProfile(this.callerTaskInfo.userId);
        if (DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue() && profile.isTaskInFullImmersiveState(this.callerTaskInfo.taskId)) {
            WindowManagerWrapper windowManagerWrapper = new WindowManagerWrapper((WindowManager) this.context.getSystemService(WindowManager.class));
            int i = this.callerTaskInfo.taskId;
            int i2 = point.x;
            int i3 = point.y;
            ManageWindowsViewContainer.ManageWindowsView manageWindowsView2 = this.menuView;
            int i4 = (manageWindowsView2 != null ? manageWindowsView2 : null).menuWidth;
            if (manageWindowsView2 == null) {
                manageWindowsView2 = null;
            }
            int i5 = manageWindowsView2.menuHeight;
            int systemBars = WindowInsets.Type.systemBars();
            ManageWindowsViewContainer.ManageWindowsView manageWindowsView3 = this.menuView;
            additionalViewHostViewContainer = new AdditionalSystemViewContainer(windowManagerWrapper, i, i2, i3, i4, i5, 262152, systemBars, false, (View) (manageWindowsView3 != null ? manageWindowsView3 : null).rootView, 256, (DefaultConstructorMarker) null);
        } else {
            SurfaceControl.Builder builder = (SurfaceControl.Builder) this.surfaceControlBuilderSupplier.get();
            this.rootTdaOrganizer.attachToDisplayArea(this.callerTaskInfo.displayId, builder);
            SurfaceControl build = builder.setName("Manage Windows Menu").setContainerLayer().build();
            ManageWindowsViewContainer.ManageWindowsView manageWindowsView4 = this.menuView;
            int i6 = (manageWindowsView4 != null ? manageWindowsView4 : null).menuWidth;
            if (manageWindowsView4 == null) {
                manageWindowsView4 = null;
            }
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i6, manageWindowsView4.menuHeight, 2, 262152, -2);
            SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(this.context, this.displayController.mDisplayManager.getDisplay(this.callerTaskInfo.displayId), new WindowlessWindowManager(this.callerTaskInfo.configuration, build, (InputTransferToken) null), "MaximizeMenu");
            ManageWindowsViewContainer.ManageWindowsView manageWindowsView5 = this.menuView;
            surfaceControlViewHost.setView((manageWindowsView5 != null ? manageWindowsView5 : null).rootView, layoutParams);
            SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.surfaceControlTransactionSupplier.get();
            transaction.setLayer(build, 70000).setPosition(build, point.x, point.y).show(build);
            transaction.apply();
            additionalViewHostViewContainer = new AdditionalViewHostViewContainer(build, surfaceControlViewHost, this.surfaceControlTransactionSupplier);
        }
        this.menuViewContainer = additionalViewHostViewContainer;
    }

    @Override // com.android.wm.shell.shared.multiinstance.ManageWindowsViewContainer
    public final void removeFromContainer() {
        AdditionalViewContainer additionalViewContainer = this.menuViewContainer;
        if (additionalViewContainer != null) {
            additionalViewContainer.releaseView();
        }
    }

    public static /* synthetic */ void getMenuViewContainer$annotations() {
    }
}
