package com.samsung.android.server.pm;

import android.os.UserHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public abstract class PmFileValidator {
    public static void validateRoleFile(int[] iArr) {
        for (int i : iArr) {
            try {
                Class<?> cls = Class.forName("com.android.role.persistence.RolesPersistenceImpl");
                Constructor<?> declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
                declaredConstructor.setAccessible(true);
                cls.getMethod("readForUser", UserHandle.class).invoke(declaredConstructor.newInstance(new Object[0]), UserHandle.of(i));
            } catch (InvocationTargetException unused) {
                PmLog.logCriticalInfoAndLogcat("!@Failed to read roles.xml. Initiate the files.");
                PmServerUtils.deletePermissionApexFile(i, "runtime-permissions.xml");
                PmServerUtils.deletePermissionApexFile(i, "roles.xml");
            } catch (Exception e) {
                PmLog.logCriticalInfoAndLogcat("!@Failed to use RolesPersistence class.");
                e.printStackTrace();
            }
        }
    }
}
