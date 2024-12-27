package com.android.systemui.dagger;

import com.android.wm.shell.keyguard.KeyguardTransitions;
import com.android.wm.shell.shared.ShellTransitions;
import com.android.wm.shell.sysui.ShellInterface;
import java.util.Optional;

public interface WMComponent {

    public interface Builder {
        WMComponent build();
    }

    Optional getBackAnimation();

    Optional getBubbles();

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
}
