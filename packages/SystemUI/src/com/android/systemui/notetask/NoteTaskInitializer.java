package com.android.systemui.notetask;

import android.app.role.RoleManager;
import android.hardware.input.InputManager;
import android.view.ViewConfiguration;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.CommandQueue;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.Optional;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NoteTaskInitializer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Executor backgroundExecutor;
    public final NoteTaskInitializer$callbacks$1 callbacks = new NoteTaskInitializer$callbacks$1(this);
    public final NoteTaskController controller;
    public final InputManager inputManager;
    public final boolean isEnabled;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final Optional optionalBubbles;
    public final RoleManager roleManager;
    public final UserTracker userTracker;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        ViewConfiguration.getMultiPressTimeout();
        ViewConfiguration.getLongPressTimeout();
    }

    public NoteTaskInitializer(NoteTaskController noteTaskController, RoleManager roleManager, CommandQueue commandQueue, Optional<Bubbles> optional, UserTracker userTracker, KeyguardUpdateMonitor keyguardUpdateMonitor, InputManager inputManager, Executor executor, boolean z) {
        this.controller = noteTaskController;
        this.roleManager = roleManager;
        this.optionalBubbles = optional;
        this.userTracker = userTracker;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.inputManager = inputManager;
        this.backgroundExecutor = executor;
        this.isEnabled = z;
    }
}
