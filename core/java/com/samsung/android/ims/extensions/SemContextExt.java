package com.samsung.android.ims.extensions;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.UserHandle;

import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;

import java.lang.reflect.Method;

/* loaded from: classes6.dex */
public class SemContextExt {
    public static final UserHandle OWNER =
            (UserHandle) SemReflectionUtils.getValueOf("OWNER", (Class<?>) UserHandle.class);
    public static final UserHandle CURRENT_OR_SELF =
            (UserHandle)
                    SemReflectionUtils.getValueOf("CURRENT_OR_SELF", (Class<?>) UserHandle.class);
    public static final UserHandle CURRENT =
            (UserHandle) SemReflectionUtils.getValueOf("CURRENT", (Class<?>) UserHandle.class);
    public static final UserHandle ALL =
            (UserHandle)
                    SemReflectionUtils.getValueOf(
                            SemInputDeviceManager.MOTION_CONTROL_TYPE_ALL,
                            (Class<?>) UserHandle.class);
    public static final String STATUS_BAR_SERVICE =
            (String) SemReflectionUtils.getValueOf("STATUS_BAR_SERVICE", (Class<?>) Context.class);

    public static boolean bindServiceAsUser(
            Context context, Intent service, ServiceConnection conn, int flags, UserHandle user) {
        try {
            Method method =
                    context.getClass()
                            .getMethod(
                                    "bindServiceAsUser",
                                    Intent.class,
                                    ServiceConnection.class,
                                    Integer.TYPE,
                                    UserHandle.class);
            return ((Boolean)
                            SemReflectionUtils.invoke2(
                                    method, context, service, conn, Integer.valueOf(flags), user))
                    .booleanValue();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return false;
        }
    }
}
