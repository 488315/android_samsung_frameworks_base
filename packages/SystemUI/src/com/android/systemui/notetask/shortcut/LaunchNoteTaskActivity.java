package com.android.systemui.notetask.shortcut;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import com.android.systemui.notetask.NoteTaskController;
import com.android.systemui.notetask.NoteTaskEntryPoint;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LaunchNoteTaskActivity extends ComponentActivity {
    public static final Companion Companion = new Companion(null);
    public final NoteTaskController controller;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
