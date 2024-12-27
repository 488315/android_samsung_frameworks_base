package com.android.systemui.notetask;

import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Icon;
import android.os.PersistableBundle;
import android.os.UserHandle;
import com.android.systemui.R;
import com.android.systemui.notetask.shortcut.LaunchNoteTaskActivity;
import kotlin.Result;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NoteTaskRoleManagerExt {
    public static final NoteTaskRoleManagerExt INSTANCE = new NoteTaskRoleManagerExt();

    private NoteTaskRoleManagerExt() {
    }

    public static ShortcutInfo createNoteShortcutInfoAsUser(RoleManager roleManager, Context context, UserHandle userHandle) {
        Object failure;
        String string;
        String str = (String) CollectionsKt___CollectionsKt.firstOrNull(roleManager.getRoleHoldersAsUser("android.app.role.NOTES", userHandle));
        PersistableBundle persistableBundle = new PersistableBundle();
        if (str != null) {
            persistableBundle.putString("extra_shortcut_badge_override_package", str);
        }
        String string2 = context.getString(R.string.note_task_button_label);
        PackageManager packageManager = context.getPackageManager();
        String str2 = null;
        if (str != null) {
            try {
                int i = Result.$r8$clinit;
                failure = packageManager.getApplicationInfo(str, 0);
                Intrinsics.checkNotNull(failure);
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            if (failure instanceof Result.Failure) {
                failure = null;
            }
            ApplicationInfo applicationInfo = (ApplicationInfo) failure;
            if (applicationInfo != null) {
                str2 = packageManager.getApplicationLabel(applicationInfo).toString();
            }
        }
        if (str2 == null) {
            string = string2;
        } else {
            string = context.getString(R.string.note_task_shortcut_long_label, str2);
            Intrinsics.checkNotNull(string);
        }
        Icon createWithResource = Icon.createWithResource(context, R.drawable.ic_note_task_shortcut_widget);
        ShortcutInfo.Builder builder = new ShortcutInfo.Builder(context, "note_task_shortcut_id");
        LaunchNoteTaskActivity.Companion.getClass();
        Intent intent = new Intent(context, (Class<?>) LaunchNoteTaskActivity.class);
        intent.setAction("android.intent.action.CREATE_NOTE");
        return builder.setIntent(intent).setActivity(new ComponentName(context, (Class<?>) LaunchNoteTaskActivity.class)).setShortLabel(string2).setLongLabel(string).setLongLived(true).setIcon(createWithResource).setExtras(persistableBundle).build();
    }
}
