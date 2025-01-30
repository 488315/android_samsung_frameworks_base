package com.android.systemui.notetask.shortcut;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import androidx.activity.ComponentActivity;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.notetask.NoteTaskController;
import com.android.systemui.notetask.NoteTaskEntryPoint;
import com.android.systemui.settings.UserTracker;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LaunchNoteTaskActivity extends ComponentActivity {
    public static final Companion Companion = new Companion(null);
    public final NoteTaskController controller;
    public final UserManager userManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public LaunchNoteTaskActivity(NoteTaskController noteTaskController, UserManager userManager, UserTracker userTracker) {
        this.controller = noteTaskController;
        this.userManager = userManager;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        UserManager userManager = this.userManager;
        UserHandle mainUser = userManager.getMainUser();
        boolean isManagedProfile = userManager.isManagedProfile();
        NoteTaskController noteTaskController = this.controller;
        if (!isManagedProfile) {
            NoteTaskEntryPoint noteTaskEntryPoint = isInMultiWindowMode() ? NoteTaskEntryPoint.WIDGET_PICKER_SHORTCUT_IN_MULTI_WINDOW_MODE : NoteTaskEntryPoint.WIDGET_PICKER_SHORTCUT;
            if (noteTaskController.isEnabled) {
                noteTaskController.showNoteTaskAsUser(noteTaskEntryPoint, noteTaskController.getUserForHandlingNotesTaking(noteTaskEntryPoint));
            }
        } else if (mainUser == null) {
            int i = DebugLogger.$r8$clinit;
            boolean z = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(LaunchNoteTaskActivity.class).getSimpleName();
        } else {
            noteTaskController.getClass();
            Intent intent = new Intent();
            Context context = noteTaskController.context;
            intent.setComponent(new ComponentName(context, (Class<?>) LaunchNoteTaskManagedProfileProxyActivity.class));
            intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
            context.startActivityAsUser(intent, mainUser);
        }
        finish();
    }
}
