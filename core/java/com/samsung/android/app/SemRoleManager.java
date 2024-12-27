package com.samsung.android.app;

import android.app.role.RoleManager;
import android.content.Context;
import android.os.UserHandle;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
public final class SemRoleManager {
    public static final int MANAGE_HOLDERS_FLAG_DONT_KILL_APP = 1;
    private final RoleManager mRoleManager;

    public SemRoleManager(Context context) {
        this.mRoleManager = (RoleManager) context.getSystemService(RoleManager.class);
    }

    public void addRoleHolderAsUser(
            String roleName,
            String packageName,
            int flags,
            UserHandle user,
            Executor executor,
            Consumer<Boolean> callback) {
        this.mRoleManager.addRoleHolderAsUser(
                roleName, packageName, flags, user, executor, callback);
    }

    public void removeRoleHolderAsUser(
            String roleName,
            String packageName,
            int flags,
            UserHandle user,
            Executor executor,
            Consumer<Boolean> callback) {
        this.mRoleManager.removeRoleHolderAsUser(
                roleName, packageName, flags, user, executor, callback);
    }

    public List<String> getRoleHolders(String roleName) {
        return this.mRoleManager.getRoleHolders(roleName);
    }
}
