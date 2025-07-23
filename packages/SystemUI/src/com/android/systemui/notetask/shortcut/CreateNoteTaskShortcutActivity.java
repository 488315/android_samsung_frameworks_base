package com.android.systemui.notetask.shortcut;

import android.app.role.RoleManager;
import android.content.pm.ShortcutManager;
import android.os.Bundle;
import android.os.UserHandle;
import androidx.activity.ComponentActivity;
import com.android.systemui.notetask.NoteTaskRoleManagerExt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CreateNoteTaskShortcutActivity extends ComponentActivity {
    public final RoleManager roleManager;
    public final ShortcutManager shortcutManager;

    public CreateNoteTaskShortcutActivity(RoleManager roleManager, ShortcutManager shortcutManager) {
        this.roleManager = roleManager;
        this.shortcutManager = shortcutManager;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        NoteTaskRoleManagerExt noteTaskRoleManagerExt = NoteTaskRoleManagerExt.INSTANCE;
        RoleManager roleManager = this.roleManager;
        UserHandle user = getUser();
        noteTaskRoleManagerExt.getClass();
        setResult(-1, this.shortcutManager.createShortcutResultIntent(NoteTaskRoleManagerExt.createNoteShortcutInfoAsUser(roleManager, this, user)));
        finish();
    }
}
