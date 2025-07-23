package com.android.wm.shell.dagger;

import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;
import com.android.wm.shell.keyguard.KeyguardTransitions;
import com.android.wm.shell.shared.ShellTransitions;
import com.android.wm.shell.sysui.ShellInterface;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface WMComponent {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Builder {
    }

    Optional getAppZoomOut();

    Optional getBackAnimation();

    Optional getBubbles();

    Optional getCoverLauncherAppCompatStartController();

    Optional getDesktopMode();

    Optional getDisplayAreaHelper();

    Optional getDisplayController();

    Optional getEnterSplitGestureHandler();

    KeyguardTransitions getKeyguardTransitions();

    Optional getOneHanded();

    Optional getPip();

    Optional getRecentTasks();

    ShellInterface getShell();

    ShellTransitions getShellTransitions();

    Optional getSplitScreen();

    Optional getSplitScreenController();

    Optional getStartingSurface();

    Optional getTaskViewFactory();

    void init();

    void inject(BubbleBarExpandedView bubbleBarExpandedView);
}
