package com.android.systemui.notetask;

import android.app.role.RoleManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.p038wm.shell.bubbles.Bubbles;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.CommandQueue;
import java.util.Optional;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NoteTaskInitializer {
    public final Executor backgroundExecutor;
    public final NoteTaskController controller;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final NoteTaskInitializer$onUserUnlockedCallback$1 onUserUnlockedCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.notetask.NoteTaskInitializer$onUserUnlockedCallback$1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserUnlocked() {
            NoteTaskInitializer noteTaskInitializer = NoteTaskInitializer.this;
            noteTaskInitializer.controller.setNoteTaskShortcutEnabled(true, ((UserTrackerImpl) noteTaskInitializer.userTracker).getUserHandle());
            noteTaskInitializer.keyguardUpdateMonitor.removeCallback(this);
        }
    };
    public final UserTracker userTracker;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.notetask.NoteTaskInitializer$onUserUnlockedCallback$1] */
    public NoteTaskInitializer(NoteTaskController noteTaskController, RoleManager roleManager, CommandQueue commandQueue, Optional<Bubbles> optional, UserTracker userTracker, KeyguardUpdateMonitor keyguardUpdateMonitor, Executor executor, boolean z) {
        this.controller = noteTaskController;
        this.userTracker = userTracker;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.backgroundExecutor = executor;
    }
}
