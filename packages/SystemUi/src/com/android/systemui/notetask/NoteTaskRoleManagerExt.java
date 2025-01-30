package com.android.systemui.notetask;

import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Icon;
import android.os.PersistableBundle;
import android.os.UserHandle;
import com.android.systemui.R;
import com.android.systemui.notetask.shortcut.LaunchNoteTaskActivity;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NoteTaskRoleManagerExt {
    public static final NoteTaskRoleManagerExt INSTANCE = new NoteTaskRoleManagerExt();

    private NoteTaskRoleManagerExt() {
    }

    public static ShortcutInfo createNoteShortcutInfoAsUser(RoleManager roleManager, Context context, UserHandle userHandle) {
        PersistableBundle persistableBundle = new PersistableBundle();
        String str = (String) CollectionsKt___CollectionsKt.firstOrNull(roleManager.getRoleHoldersAsUser("android.app.role.NOTES", userHandle));
        if (str != null) {
            persistableBundle.putString("extra_shortcut_badge_override_package", str);
        }
        Icon createWithResource = Icon.createWithResource(context, R.drawable.ic_note_task_shortcut_widget);
        ShortcutInfo.Builder builder = new ShortcutInfo.Builder(context, "note_task_shortcut_id");
        LaunchNoteTaskActivity.Companion.getClass();
        Intent intent = new Intent(context, (Class<?>) LaunchNoteTaskActivity.class);
        intent.setAction("android.intent.action.CREATE_NOTE");
        return builder.setIntent(intent).setActivity(new ComponentName(context, (Class<?>) LaunchNoteTaskActivity.class)).setShortLabel(context.getString(R.string.note_task_button_label)).setLongLived(true).setIcon(createWithResource).setExtras(persistableBundle).build();
    }
}
