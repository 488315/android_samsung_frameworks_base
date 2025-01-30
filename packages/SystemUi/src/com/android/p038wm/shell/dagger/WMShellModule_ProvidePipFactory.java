package com.android.p038wm.shell.dagger;

import android.content.Context;
import com.android.p038wm.shell.WindowManagerShellWrapper;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.common.DisplayInsetsController;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.common.TabletopModeController;
import com.android.p038wm.shell.common.TaskStackListenerImpl;
import com.android.p038wm.shell.pip.PipAnimationController;
import com.android.p038wm.shell.pip.PipAppOpsListener;
import com.android.p038wm.shell.pip.PipBoundsAlgorithm;
import com.android.p038wm.shell.pip.PipBoundsState;
import com.android.p038wm.shell.pip.PipDisplayLayoutState;
import com.android.p038wm.shell.pip.PipMediaController;
import com.android.p038wm.shell.pip.PipParamsChangedForwarder;
import com.android.p038wm.shell.pip.PipTaskOrganizer;
import com.android.p038wm.shell.pip.PipTransitionController;
import com.android.p038wm.shell.pip.PipTransitionState;
import com.android.p038wm.shell.pip.phone.PhonePipKeepClearAlgorithm;
import com.android.p038wm.shell.pip.phone.PhonePipMenuController;
import com.android.p038wm.shell.pip.phone.PipController;
import com.android.p038wm.shell.pip.phone.PipMotionHelper;
import com.android.p038wm.shell.pip.phone.PipSizeSpecHandler;
import com.android.p038wm.shell.pip.phone.PipTouchHandler;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.sysui.ShellCommandHandler;
import com.android.p038wm.shell.sysui.ShellController;
import com.android.p038wm.shell.sysui.ShellInit;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvidePipFactory implements Provider {
    public final Provider contextProvider;
    public final Provider displayControllerProvider;
    public final Provider displayInsetsControllerProvider;
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
    public final Provider pipSizeSpecHandlerProvider;
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

    public WMShellModule_ProvidePipFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26) {
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
        this.pipSizeSpecHandlerProvider = provider11;
        this.pipDisplayLayoutStateProvider = provider12;
        this.pipMotionHelperProvider = provider13;
        this.pipMediaControllerProvider = provider14;
        this.phonePipMenuControllerProvider = provider15;
        this.pipTaskOrganizerProvider = provider16;
        this.pipTransitionStateProvider = provider17;
        this.pipTouchHandlerProvider = provider18;
        this.pipTransitionControllerProvider = provider19;
        this.windowManagerShellWrapperProvider = provider20;
        this.taskStackListenerProvider = provider21;
        this.pipParamsChangedForwarderProvider = provider22;
        this.displayInsetsControllerProvider = provider23;
        this.pipTabletopControllerProvider = provider24;
        this.oneHandedControllerProvider = provider25;
        this.mainExecutorProvider = provider26;
    }

    public static Optional providePip(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellController shellController, DisplayController displayController, PipAnimationController pipAnimationController, PipAppOpsListener pipAppOpsListener, PipBoundsAlgorithm pipBoundsAlgorithm, PhonePipKeepClearAlgorithm phonePipKeepClearAlgorithm, PipBoundsState pipBoundsState, PipSizeSpecHandler pipSizeSpecHandler, PipDisplayLayoutState pipDisplayLayoutState, PipMotionHelper pipMotionHelper, PipMediaController pipMediaController, PhonePipMenuController phonePipMenuController, PipTaskOrganizer pipTaskOrganizer, PipTransitionState pipTransitionState, PipTouchHandler pipTouchHandler, PipTransitionController pipTransitionController, WindowManagerShellWrapper windowManagerShellWrapper, TaskStackListenerImpl taskStackListenerImpl, PipParamsChangedForwarder pipParamsChangedForwarder, DisplayInsetsController displayInsetsController, TabletopModeController tabletopModeController, Optional optional, ShellExecutor shellExecutor) {
        PipController.PipImpl pipImpl;
        int i = PipController.$r8$clinit;
        if (context.getPackageManager().hasSystemFeature("android.software.picture_in_picture")) {
            pipImpl = new PipController(context, shellInit, shellCommandHandler, shellController, displayController, pipAnimationController, pipAppOpsListener, pipBoundsAlgorithm, phonePipKeepClearAlgorithm, pipBoundsState, pipSizeSpecHandler, pipDisplayLayoutState, pipMotionHelper, pipMediaController, phonePipMenuController, pipTaskOrganizer, pipTransitionState, pipTouchHandler, pipTransitionController, windowManagerShellWrapper, taskStackListenerImpl, pipParamsChangedForwarder, displayInsetsController, tabletopModeController, optional, shellExecutor).mImpl;
        } else {
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 377854703, 0, "%s: Device doesn't support Pip feature", "PipController");
            }
            pipImpl = null;
        }
        Optional ofNullable = Optional.ofNullable(pipImpl);
        Preconditions.checkNotNullFromProvides(ofNullable);
        return ofNullable;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providePip((Context) this.contextProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellCommandHandler) this.shellCommandHandlerProvider.get(), (ShellController) this.shellControllerProvider.get(), (DisplayController) this.displayControllerProvider.get(), (PipAnimationController) this.pipAnimationControllerProvider.get(), (PipAppOpsListener) this.pipAppOpsListenerProvider.get(), (PipBoundsAlgorithm) this.pipBoundsAlgorithmProvider.get(), (PhonePipKeepClearAlgorithm) this.pipKeepClearAlgorithmProvider.get(), (PipBoundsState) this.pipBoundsStateProvider.get(), (PipSizeSpecHandler) this.pipSizeSpecHandlerProvider.get(), (PipDisplayLayoutState) this.pipDisplayLayoutStateProvider.get(), (PipMotionHelper) this.pipMotionHelperProvider.get(), (PipMediaController) this.pipMediaControllerProvider.get(), (PhonePipMenuController) this.phonePipMenuControllerProvider.get(), (PipTaskOrganizer) this.pipTaskOrganizerProvider.get(), (PipTransitionState) this.pipTransitionStateProvider.get(), (PipTouchHandler) this.pipTouchHandlerProvider.get(), (PipTransitionController) this.pipTransitionControllerProvider.get(), (WindowManagerShellWrapper) this.windowManagerShellWrapperProvider.get(), (TaskStackListenerImpl) this.taskStackListenerProvider.get(), (PipParamsChangedForwarder) this.pipParamsChangedForwarderProvider.get(), (DisplayInsetsController) this.displayInsetsControllerProvider.get(), (TabletopModeController) this.pipTabletopControllerProvider.get(), (Optional) this.oneHandedControllerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}
