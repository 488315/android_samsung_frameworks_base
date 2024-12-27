package com.android.systemui.notetask;

import android.app.role.RoleManager;
import android.view.ViewConfiguration;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.CommandQueue;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.Optional;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NoteTaskInitializer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long LONG_PRESS_TIMEOUT;
    public static final long MULTI_PRESS_TIMEOUT;
    public final Executor backgroundExecutor;
    public final CommandQueue commandQueue;
    public final NoteTaskController controller;
    public final boolean isEnabled;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final Optional optionalBubbles;
    public final RoleManager roleManager;
    public final UserTracker userTracker;
    public final NoteTaskInitializer$callbacks$1 callbacks = new NoteTaskInitializer$callbacks$1(this);
    public long lastStylusButtonTailUpEventTime = -MULTI_PRESS_TIMEOUT;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        MULTI_PRESS_TIMEOUT = ViewConfiguration.getMultiPressTimeout();
        LONG_PRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
    }

    public NoteTaskInitializer(NoteTaskController noteTaskController, RoleManager roleManager, CommandQueue commandQueue, Optional<Bubbles> optional, UserTracker userTracker, KeyguardUpdateMonitor keyguardUpdateMonitor, Executor executor, boolean z) {
        this.controller = noteTaskController;
        this.roleManager = roleManager;
        this.commandQueue = commandQueue;
        this.optionalBubbles = optional;
        this.userTracker = userTracker;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.backgroundExecutor = executor;
        this.isEnabled = z;
    }
}
