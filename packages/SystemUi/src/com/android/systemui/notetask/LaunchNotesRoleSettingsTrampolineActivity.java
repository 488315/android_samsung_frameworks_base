package com.android.systemui.notetask;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import androidx.activity.ComponentActivity;
import com.android.systemui.settings.UserTrackerImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LaunchNotesRoleSettingsTrampolineActivity extends ComponentActivity {
    public final NoteTaskController controller;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
