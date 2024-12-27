package com.android.systemui.notetask.shortcut;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import com.android.systemui.notetask.NoteTaskController;
import com.android.systemui.notetask.NoteTaskEntryPoint;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class LaunchNoteTaskActivity extends ComponentActivity {
    public static final Companion Companion = new Companion(null);
    public final NoteTaskController controller;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public LaunchNoteTaskActivity(NoteTaskController noteTaskController) {
        this.controller = noteTaskController;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.controller.showNoteTaskAsUser(isInMultiWindowMode() ? NoteTaskEntryPoint.WIDGET_PICKER_SHORTCUT_IN_MULTI_WINDOW_MODE : NoteTaskEntryPoint.WIDGET_PICKER_SHORTCUT, getUser());
        finish();
    }
}
