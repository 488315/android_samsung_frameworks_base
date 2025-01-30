package com.android.systemui.dagger;

import android.os.HandlerThread;
import com.android.p038wm.shell.keyguard.KeyguardTransitions;
import com.android.p038wm.shell.sysui.ShellInterface;
import com.android.p038wm.shell.transition.ShellTransitions;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface WMComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Builder {
        WMComponent build();

        Builder setShellMainThread(HandlerThread handlerThread);
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

    Optional getSplitScreen();

    Optional getSplitScreenController();

    Optional getStartingSurface();

    Optional getTaskViewFactory();

    ShellTransitions getTransitions();

    default void init() {
        getShell().onInit();
    }
}
