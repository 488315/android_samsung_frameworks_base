package com.android.systemui.notetask;

import android.app.role.RoleManager;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.util.Log;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

public final class NoteTaskInfoResolver {
    public static final Companion Companion = new Companion(null);
    public static final PackageManager.ApplicationInfoFlags EMPTY_APPLICATION_INFO_FLAGS;
    public static final String TAG;
    public final PackageManager packageManager;
    public final RoleManager roleManager;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        String simpleName = Reflection.getOrCreateKotlinClass(NoteTaskInfoResolver.class).getSimpleName();
        if (simpleName == null) {
            simpleName = "";
        }
        TAG = simpleName;
        PackageManager.ApplicationInfoFlags of = PackageManager.ApplicationInfoFlags.of(0L);
        Intrinsics.checkNotNull(of);
        EMPTY_APPLICATION_INFO_FLAGS = of;
    }

    public NoteTaskInfoResolver(RoleManager roleManager, PackageManager packageManager) {
        this.roleManager = roleManager;
        this.packageManager = packageManager;
    }

    public final NoteTaskInfo resolveInfo(NoteTaskEntryPoint noteTaskEntryPoint, boolean z, UserHandle userHandle) {
        int i;
        NoteTaskRoleManagerExt noteTaskRoleManagerExt = NoteTaskRoleManagerExt.INSTANCE;
        RoleManager roleManager = this.roleManager;
        noteTaskRoleManagerExt.getClass();
        String str = (String) CollectionsKt___CollectionsKt.firstOrNull(roleManager.getRoleHoldersAsUser("android.app.role.NOTES", userHandle));
        if (str == null || str.length() == 0) {
            return null;
        }
        PackageManager packageManager = this.packageManager;
        Companion.getClass();
        try {
            i = packageManager.getApplicationInfoAsUser(str, EMPTY_APPLICATION_INFO_FLAGS, userHandle).uid;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Couldn't find notes app UID", e);
            i = 0;
        }
        return new NoteTaskInfo(str, i, userHandle, noteTaskEntryPoint, z);
    }
}
