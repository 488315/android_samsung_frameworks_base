package com.sec.ims.extensions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.UserHandle;
import java.lang.reflect.Field;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ContextExt {
    public static final UserHandle OWNER = (UserHandle) ReflectionUtils.getValueOf("OWNER", (Class<?>) UserHandle.class);
    public static final UserHandle CURRENT_OR_SELF = (UserHandle) ReflectionUtils.getValueOf("CURRENT_OR_SELF", (Class<?>) UserHandle.class);
    public static final UserHandle CURRENT = (UserHandle) ReflectionUtils.getValueOf("CURRENT", (Class<?>) UserHandle.class);
    public static final UserHandle ALL = (UserHandle) ReflectionUtils.getValueOf("ALL", (Class<?>) UserHandle.class);
    public static final String STATUS_BAR_SERVICE = (String) ReflectionUtils.getValueOf("STATUS_BAR_SERVICE", (Class<?>) Context.class);
    public static final String HQM_SERVICE = getStringFromField("HQM_SERVICE", "HqmManagerService");

    public static boolean bindServiceAsUser(Context context, Intent intent, ServiceConnection serviceConnection, int i, UserHandle userHandle) {
        return context.bindServiceAsUser(intent, serviceConnection, i, userHandle);
    }

    public static String getStringFromField(String str, String str2) {
        try {
            Field field = ReflectionUtils.getField(Context.class, str);
            if (field != null) {
                return (String) field.get(null);
            }
        } catch (IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return str2;
    }

    public static Intent registerReceiverAsUser(Context context, BroadcastReceiver broadcastReceiver, UserHandle userHandle, IntentFilter intentFilter, String str, Handler handler) {
        try {
            return (Intent) ReflectionUtils.invoke2(context.getClass().getMethod("registerReceiverAsUser", BroadcastReceiver.class, UserHandle.class, IntentFilter.class, String.class, Handler.class), context, broadcastReceiver, userHandle, intentFilter, str, handler);
        } catch (IllegalStateException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendBroadcastAsUser(Context context, Intent intent, UserHandle userHandle) {
        try {
            ReflectionUtils.invoke(context.getClass().getMethod("sendBroadcastAsUser", Intent.class, UserHandle.class), context, intent, userHandle);
        } catch (IllegalStateException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
