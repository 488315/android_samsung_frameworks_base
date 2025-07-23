package com.android.systemui.notetask.shortcut;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import com.android.systemui.notetask.NoteTaskController;
import com.android.systemui.notetask.NoteTaskEntryPoint;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LaunchNoteTaskActivity extends ComponentActivity {
    public static final Companion Companion = new Companion(null);
    public final NoteTaskController controller;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
