package com.android.wm.shell.dagger.pip;

import android.content.Context;
import android.os.Handler;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.WindowManagerShellWrapper;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TabletopModeController;
import com.android.wm.shell.common.TaskStackListenerImpl;
import com.android.wm.shell.common.pip.PhonePipKeepClearAlgorithm;
import com.android.wm.shell.common.pip.PipAppOpsListener;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipMediaController;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipParamsChangedForwarder;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.pip.phone.PhonePipMenuController;
import com.android.wm.shell.pip.phone.PipController;
import com.android.wm.shell.pip.phone.PipMotionHelper;
import com.android.wm.shell.pip.phone.PipTouchHandler;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Provider;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class Pip1Module_ProvidePip1Factory implements Provider {
    public final Provider contextProvider;
    public final Provider displayControllerProvider;
    public final Provider displayInsetsControllerProvider;
    public final Provider handlerProvider;
    public final Provider mainExecutorProvider;
    public final Provider oneHandedControllerProvider;
    public final Provider phonePipMenuControllerProvider;
    public final Provider pipAnimationControllerProvider;
    public final Provider pipAppOpsListenerProvider;
    public final Provider pipBoundsAlgorithmProvider;
    public final Provider pipBoundsStateProvider;
    public final Provider pipDisplayLayoutStateProvider;
    public final Provider pipKeepClearAlgorithmProvider;
    public final Provider pipMediaControllerProvider;
    public final Provider pipMotionHelperProvider;
    public final Provider pipParamsChangedForwarderProvider;
    public final Provider pipTabletopControllerProvider;
    public final Provider pipTaskOrganizerProvider;
    public final Provider pipTouchHandlerProvider;
    public final Provider pipTransitionControllerProvider;
    public final Provider pipTransitionStateProvider;
    public final Provider shellCommandHandlerProvider;
    public final Provider shellControllerProvider;
    public final Provider shellInitProvider;
    public final Provider taskStackListenerProvider;
    public final Provider windowManagerShellWrapperProvider;

    public Pip1Module_ProvidePip1Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26) {
        this.contextProvider = provider;
        this.shellInitProvider = provider2;
        this.shellCommandHandlerProvider = provider3;
        this.shellControllerProvider = provider4;
        this.displayControllerProvider = provider5;
        this.pipAnimationControllerProvider = provider6;
        this.pipAppOpsListenerProvider = provider7;
        this.pipBoundsAlgorithmProvider = provider8;
        this.pipKeepClearAlgorithmProvider = provider9;
        this.pipBoundsStateProvider = provider10;
        this.pipDisplayLayoutStateProvider = provider11;
        this.pipMotionHelperProvider = provider12;
        this.pipMediaControllerProvider = provider13;
        this.phonePipMenuControllerProvider = provider14;
        this.pipTaskOrganizerProvider = provider15;
        this.pipTransitionStateProvider = provider16;
        this.pipTouchHandlerProvider = provider17;
        this.pipTransitionControllerProvider = provider18;
        this.windowManagerShellWrapperProvider = provider19;
        this.taskStackListenerProvider = provider20;
        this.pipParamsChangedForwarderProvider = provider21;
        this.displayInsetsControllerProvider = provider22;
        this.pipTabletopControllerProvider = provider23;
        this.oneHandedControllerProvider = provider24;
        this.mainExecutorProvider = provider25;
        this.handlerProvider = provider26;
    }

    public static Optional providePip1(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellController shellController, DisplayController displayController, PipAnimationController pipAnimationController, PipAppOpsListener pipAppOpsListener, PipBoundsAlgorithm pipBoundsAlgorithm, PhonePipKeepClearAlgorithm phonePipKeepClearAlgorithm, PipBoundsState pipBoundsState, PipDisplayLayoutState pipDisplayLayoutState, PipMotionHelper pipMotionHelper, PipMediaController pipMediaController, PhonePipMenuController phonePipMenuController, PipTaskOrganizer pipTaskOrganizer, PipTransitionState pipTransitionState, PipTouchHandler pipTouchHandler, PipTransitionController pipTransitionController, WindowManagerShellWrapper windowManagerShellWrapper, TaskStackListenerImpl taskStackListenerImpl, PipParamsChangedForwarder pipParamsChangedForwarder, DisplayInsetsController displayInsetsController, TabletopModeController tabletopModeController, Optional optional, ShellExecutor shellExecutor, Handler handler) {
        PipController.PipImpl pipImpl;
        int i = PipController.$r8$clinit;
        if (context.getPackageManager().hasSystemFeature("android.software.picture_in_picture")) {
            pipImpl = new PipController(context, shellInit, shellCommandHandler, shellController, displayController, pipAnimationController, pipAppOpsListener, pipBoundsAlgorithm, phonePipKeepClearAlgorithm, pipBoundsState, pipDisplayLayoutState, pipMotionHelper, pipMediaController, phonePipMenuController, pipTaskOrganizer, pipTransitionState, pipTouchHandler, pipTransitionController, windowManagerShellWrapper, taskStackListenerImpl, pipParamsChangedForwarder, displayInsetsController, tabletopModeController, optional, shellExecutor, handler).mImpl;
        } else {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -784520377779406630L, 0, "PipController");
            }
            pipImpl = null;
        }
        Optional ofNullable = Optional.ofNullable(pipImpl);
        ofNullable.getClass();
        return ofNullable;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providePip1((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellCommandHandler) this.shellCommandHandlerProvider.get(), (ShellController) this.shellControllerProvider.get(), (DisplayController) this.displayControllerProvider.get(), (PipAnimationController) this.pipAnimationControllerProvider.get(), (PipAppOpsListener) this.pipAppOpsListenerProvider.get(), (PipBoundsAlgorithm) this.pipBoundsAlgorithmProvider.get(), (PhonePipKeepClearAlgorithm) this.pipKeepClearAlgorithmProvider.get(), (PipBoundsState) this.pipBoundsStateProvider.get(), (PipDisplayLayoutState) this.pipDisplayLayoutStateProvider.get(), (PipMotionHelper) this.pipMotionHelperProvider.get(), (PipMediaController) this.pipMediaControllerProvider.get(), (PhonePipMenuController) this.phonePipMenuControllerProvider.get(), (PipTaskOrganizer) this.pipTaskOrganizerProvider.get(), (PipTransitionState) this.pipTransitionStateProvider.get(), (PipTouchHandler) this.pipTouchHandlerProvider.get(), (PipTransitionController) this.pipTransitionControllerProvider.get(), (WindowManagerShellWrapper) this.windowManagerShellWrapperProvider.get(), (TaskStackListenerImpl) this.taskStackListenerProvider.get(), (PipParamsChangedForwarder) this.pipParamsChangedForwarderProvider.get(), (DisplayInsetsController) this.displayInsetsControllerProvider.get(), (TabletopModeController) this.pipTabletopControllerProvider.get(), (Optional) this.oneHandedControllerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get(), (Handler) this.handlerProvider.get());
    }
}
