package com.android.systemui.notetask;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import androidx.activity.ComponentActivity;
import com.android.systemui.settings.UserTrackerImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LaunchNotesRoleSettingsTrampolineActivity extends ComponentActivity {
    public final NoteTaskController controller;

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
        Intent intent2 = new Intent("android.intent.action.MANAGE_DEFAULT_APP");
        intent2.putExtra("android.intent.extra.ROLE_NAME", "android.app.role.NOTES");
        startActivityAsUser(intent2, userHandle);
        finish();
    }
}
