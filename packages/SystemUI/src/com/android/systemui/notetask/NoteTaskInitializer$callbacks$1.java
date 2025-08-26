package com.android.systemui.notetask;

import android.app.role.OnRoleHoldersChangedListener;
import android.content.Context;
import android.hardware.input.InputManager;
import android.hardware.input.KeyGestureEvent;
import android.os.Build;
import android.os.IBinder;
import android.os.UserHandle;
import android.view.KeyEvent;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.CommandQueue;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public final class NoteTaskInitializer$callbacks$1 extends KeyguardUpdateMonitorCallback implements CommandQueue.Callbacks, UserTracker.Callback, OnRoleHoldersChangedListener, InputManager.KeyGestureEventHandler {
    public final /* synthetic */ NoteTaskInitializer this$0;

    public NoteTaskInitializer$callbacks$1(NoteTaskInitializer noteTaskInitializer) {
        this.this$0 = noteTaskInitializer;
    }

    public final void handleKeyGestureEvent(KeyGestureEvent keyGestureEvent, IBinder iBinder) {
        final NoteTaskInitializer noteTaskInitializer = this.this$0;
        int i = NoteTaskInitializer.$r8$clinit;
        noteTaskInitializer.getClass();
        if (keyGestureEvent.getKeyGestureType() != 33) {
            return;
        }
        DebugLogger debugLogger = DebugLogger.INSTANCE;
        boolean z = Build.IS_DEBUGGABLE;
        Reflection.getOrCreateKotlinClass(NoteTaskInitializer.class).getSimpleName();
        if (keyGestureEvent.getKeycodes().length == 1 && keyGestureEvent.getKeycodes()[0] == 311) {
            noteTaskInitializer.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.notetask.NoteTaskInitializer$handleKeyGestureEvent$2
                @Override // java.lang.Runnable
                public final void run() {
                    NoteTaskController noteTaskController = noteTaskInitializer.controller;
                    NoteTaskEntryPoint noteTaskEntryPoint = NoteTaskEntryPoint.TAIL_BUTTON;
                    if (noteTaskController.isEnabled) {
                        noteTaskController.showNoteTaskAsUser(noteTaskEntryPoint, noteTaskController.getUserForHandlingNotesTaking(noteTaskEntryPoint));
                    }
                }
            });
        } else {
            noteTaskInitializer.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.notetask.NoteTaskInitializer$handleKeyGestureEvent$3
                @Override // java.lang.Runnable
                public final void run() {
                    NoteTaskController noteTaskController = noteTaskInitializer.controller;
                    NoteTaskEntryPoint noteTaskEntryPoint = NoteTaskEntryPoint.KEYBOARD_SHORTCUT;
                    if (noteTaskController.isEnabled) {
                        noteTaskController.showNoteTaskAsUser(noteTaskEntryPoint, noteTaskController.getUserForHandlingNotesTaking(noteTaskEntryPoint));
                    }
                }
            });
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void handleSystemKey(KeyEvent keyEvent) {
        throw new IllegalStateException("handleSystemKey must not be used when KeyGestureEventHandler is used");
    }

    @Override // com.android.systemui.settings.UserTracker.Callback
    public final void onProfilesChanged(List list) {
        this.this$0.controller.updateNoteTaskForCurrentUserAndManagedProfiles();
    }

    public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
        NoteTaskController noteTaskController = this.this$0.controller;
        noteTaskController.getClass();
        if (Intrinsics.areEqual(str, "android.app.role.NOTES")) {
            noteTaskController.updateNoteTaskAsUser(userHandle);
        }
    }

    @Override // com.android.systemui.settings.UserTracker.Callback
    public final void onUserChanged(int i, Context context) {
        this.this$0.controller.updateNoteTaskForCurrentUserAndManagedProfiles();
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onUserUnlocked() {
        this.this$0.controller.updateNoteTaskForCurrentUserAndManagedProfiles();
    }
}
