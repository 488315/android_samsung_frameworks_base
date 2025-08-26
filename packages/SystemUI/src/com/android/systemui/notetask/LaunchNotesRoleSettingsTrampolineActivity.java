package com.android.systemui.notetask;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import androidx.activity.ComponentActivity;
import com.android.systemui.settings.UserTrackerImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class LaunchNotesRoleSettingsTrampolineActivity extends ComponentActivity {
    public final NoteTaskController controller;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public LaunchNotesRoleSettingsTrampolineActivity(NoteTaskController noteTaskController) {
        this.controller = noteTaskController;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        NoteTaskEntryPoint noteTaskEntryPoint = Intrinsics.areEqual(intent != null ? intent.getAction() : null, "com.android.systemui.action.MANAGE_NOTES_ROLE_FROM_QUICK_AFFORDANCE") ? NoteTaskEntryPoint.QUICK_AFFORDANCE : null;
        NoteTaskController noteTaskController = this.controller;
        UserHandle userHandle = noteTaskEntryPoint == null ? ((UserTrackerImpl) noteTaskController.userTracker).getUserHandle() : noteTaskController.getUserForHandlingNotesTaking(noteTaskEntryPoint);
        NoteTaskController.Companion.getClass();
        startActivityAsUser(new Intent("android.intent.action.MANAGE_DEFAULT_APP").putExtra("android.intent.extra.ROLE_NAME", "android.app.role.NOTES"), userHandle);
        finish();
    }
}
