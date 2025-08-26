package com.android.wm.shell.dagger;

import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;
import com.android.wm.shell.keyguard.KeyguardTransitions;
import com.android.wm.shell.shared.ShellTransitions;
import com.android.wm.shell.sysui.ShellInterface;
import java.util.Optional;

/* loaded from: classes3.dex */
public interface WMComponent {

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
