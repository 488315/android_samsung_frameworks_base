package com.android.systemui.notetask.shortcut;

import android.app.role.RoleManager;
import android.content.pm.ShortcutManager;
import android.os.Bundle;
import android.os.UserHandle;
import androidx.activity.ComponentActivity;
import com.android.systemui.notetask.NoteTaskRoleManagerExt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        UserHandle user = getUser();
        noteTaskRoleManagerExt.getClass();
        setResult(-1, this.shortcutManager.createShortcutResultIntent(NoteTaskRoleManagerExt.createNoteShortcutInfoAsUser(this.roleManager, this, user)));
        finish();
    }
}
