package com.android.server.companion.utils;

import android.app.role.RoleManager;
import android.companion.AssociationInfo;
import android.content.Context;
import android.os.UserHandle;

import java.util.function.Consumer;

public abstract class RolesUtils {
    public static void addRoleHolderForAssociation(
            Context context, AssociationInfo associationInfo, Consumer consumer) {
        String deviceProfile = associationInfo.getDeviceProfile();
        if (deviceProfile == null) {
            consumer.accept(Boolean.TRUE);
        } else {
            ((RoleManager) context.getSystemService(RoleManager.class))
                    .addRoleHolderAsUser(
                            deviceProfile,
                            associationInfo.getPackageName(),
                            1,
                            UserHandle.of(associationInfo.getUserId()),
                            context.getMainExecutor(),
                            consumer);
        }
    }
}
