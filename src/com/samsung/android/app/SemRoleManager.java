package com.samsung.android.app;

import android.app.role.RoleManager;
import android.content.Context;
import android.os.UserHandle;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes6.dex */
public final class SemRoleManager {
    public static final int MANAGE_HOLDERS_FLAG_DONT_KILL_APP = 1;
    private final RoleManager mRoleManager;

    public SemRoleManager(Context context) {
        this.mRoleManager = (RoleManager) context.getSystemService(RoleManager.class);
    }

    public void addRoleHolderAsUser(String str, String str2, int i, UserHandle userHandle, Executor executor, Consumer<Boolean> consumer) {
        this.mRoleManager.addRoleHolderAsUser(str, str2, i, userHandle, executor, consumer);
    }

    public void removeRoleHolderAsUser(String str, String str2, int i, UserHandle userHandle, Executor executor, Consumer<Boolean> consumer) {
        this.mRoleManager.removeRoleHolderAsUser(str, str2, i, userHandle, executor, consumer);
    }

    public List<String> getRoleHolders(String str) {
        return this.mRoleManager.getRoleHolders(str);
    }
}
