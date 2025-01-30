package com.android.p038wm.shell.pip.p039tv;

import android.content.Context;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.pip.PipAnimationController;
import com.android.p038wm.shell.pip.PipDisplayLayoutState;
import com.android.p038wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.p038wm.shell.pip.PipTransition;
import com.android.p038wm.shell.pip.PipTransitionState;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.android.p038wm.shell.sysui.ShellInit;
import com.android.p038wm.shell.transition.Transitions;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvPipTransition extends PipTransition {
    public TvPipTransition(Context context, ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, Transitions transitions, TvPipBoundsState tvPipBoundsState, PipDisplayLayoutState pipDisplayLayoutState, PipTransitionState pipTransitionState, TvPipMenuController tvPipMenuController, TvPipBoundsAlgorithm tvPipBoundsAlgorithm, PipAnimationController pipAnimationController, PipSurfaceTransactionHelper pipSurfaceTransactionHelper, Optional<SplitScreenController> optional) {
        super(context, shellInit, shellTaskOrganizer, transitions, tvPipBoundsState, pipDisplayLayoutState, pipTransitionState, tvPipMenuController, tvPipBoundsAlgorithm, pipAnimationController, pipSurfaceTransactionHelper, optional);
    }
}
