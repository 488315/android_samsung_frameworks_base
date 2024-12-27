package com.android.systemui.dagger;

import com.android.wm.shell.keyguard.KeyguardTransitions;
import com.android.wm.shell.shared.ShellTransitions;
import com.android.wm.shell.sysui.ShellInterface;
import java.util.Optional;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface WMComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
