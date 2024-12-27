package com.android.systemui.notetask.shortcut;

import android.app.role.RoleManager;
import android.content.pm.ShortcutManager;
import android.os.Bundle;
import android.os.UserHandle;
import androidx.activity.ComponentActivity;
import com.android.systemui.notetask.NoteTaskRoleManagerExt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
