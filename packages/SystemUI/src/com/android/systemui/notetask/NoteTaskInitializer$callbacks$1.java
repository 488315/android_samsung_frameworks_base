package com.android.systemui.notetask;

import android.app.role.OnRoleHoldersChangedListener;
import android.content.Context;
import android.os.UserHandle;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.CommandQueue;
import java.util.List;

public final class NoteTaskInitializer$callbacks$1 extends KeyguardUpdateMonitorCallback implements CommandQueue.Callbacks, UserTracker.Callback, OnRoleHoldersChangedListener {
    public final /* synthetic */ NoteTaskInitializer this$0;

    public NoteTaskInitializer$callbacks$1(NoteTaskInitializer noteTaskInitializer) {
        this.this$0 = noteTaskInitializer;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleSystemKey(android.view.KeyEvent r9) {
        /*
            r8 = this;
            com.android.systemui.notetask.NoteTaskInitializer r0 = r8.this$0
            int r1 = com.android.systemui.notetask.NoteTaskInitializer.$r8$clinit
            r0.getClass()
            int r1 = r9.getKeyCode()
            r2 = 311(0x137, float:4.36E-43)
            if (r1 != r2) goto L5b
            int r1 = r9.getKeyCode()
            if (r1 != r2) goto L5b
            int r1 = r9.getAction()
            r2 = 1
            if (r1 == r2) goto L1d
            goto L5b
        L1d:
            long r3 = r9.getDownTime()
            long r5 = r0.lastStylusButtonTailUpEventTime
            long r3 = r3 - r5
            long r5 = com.android.systemui.notetask.NoteTaskInitializer.MULTI_PRESS_TIMEOUT
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            r3 = 0
            if (r1 >= 0) goto L2d
            r1 = r2
            goto L2e
        L2d:
            r1 = r3
        L2e:
            long r4 = r9.getEventTime()
            long r6 = r9.getDownTime()
            long r4 = r4 - r6
            long r6 = com.android.systemui.notetask.NoteTaskInitializer.LONG_PRESS_TIMEOUT
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 < 0) goto L3e
            goto L3f
        L3e:
            r2 = r3
        L3f:
            long r3 = r9.getEventTime()
            r0.lastStylusButtonTailUpEventTime = r3
            int r0 = com.android.systemui.log.DebugLogger.$r8$clinit
            boolean r0 = android.os.Build.IS_DEBUGGABLE
            java.lang.Class r0 = r9.getClass()
            kotlin.jvm.internal.ClassReference r0 = kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r0)
            r0.getSimpleName()
            if (r1 != 0) goto L5b
            if (r2 != 0) goto L5b
            com.android.systemui.notetask.NoteTaskEntryPoint r0 = com.android.systemui.notetask.NoteTaskEntryPoint.TAIL_BUTTON
            goto L73
        L5b:
            int r0 = r9.getKeyCode()
            r1 = 42
            if (r0 != r1) goto L72
            boolean r0 = r9.isMetaPressed()
            if (r0 == 0) goto L72
            boolean r0 = r9.isCtrlPressed()
            if (r0 == 0) goto L72
            com.android.systemui.notetask.NoteTaskEntryPoint r0 = com.android.systemui.notetask.NoteTaskEntryPoint.KEYBOARD_SHORTCUT
            goto L73
        L72:
            r0 = 0
        L73:
            int r1 = com.android.systemui.log.DebugLogger.$r8$clinit
            boolean r1 = android.os.Build.IS_DEBUGGABLE
            java.lang.Class r9 = r9.getClass()
            kotlin.jvm.internal.ClassReference r9 = kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r9)
            r9.getSimpleName()
            if (r0 == 0) goto L94
            com.android.systemui.notetask.NoteTaskInitializer r8 = r8.this$0
            com.android.systemui.notetask.NoteTaskController r8 = r8.controller
            boolean r9 = r8.isEnabled
            if (r9 != 0) goto L8d
            goto L94
        L8d:
            android.os.UserHandle r9 = r8.getUserForHandlingNotesTaking(r0)
            r8.showNoteTaskAsUser(r0, r9)
        L94:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.notetask.NoteTaskInitializer$callbacks$1.handleSystemKey(android.view.KeyEvent):void");
    }

    @Override // com.android.systemui.settings.UserTracker.Callback
    public final void onProfilesChanged(List list) {
        this.this$0.controller.updateNoteTaskForCurrentUserAndManagedProfiles();
    }

    public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
        NoteTaskController noteTaskController = this.this$0.controller;
        noteTaskController.getClass();
        if (str.equals("android.app.role.NOTES")) {
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
