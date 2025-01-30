package com.samsung.android.knox;

import android.app.admin.DeviceAdminReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class EnterpriseDeviceAdminReceiver extends DeviceAdminReceiver {
    public ComponentName mWho;

    @Override // android.app.admin.DeviceAdminReceiver
    public final ComponentName getWho(Context context) {
        ComponentName componentName = this.mWho;
        if (componentName != null) {
            return componentName;
        }
        ComponentName componentName2 = new ComponentName(context, (Class<?>) EnterpriseDeviceAdminReceiver.class);
        this.mWho = componentName2;
        return componentName2;
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final CharSequence onDisableRequested(Context context, Intent intent) {
        return null;
    }

    @Override // android.app.admin.DeviceAdminReceiver, android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.app.action.ACTION_PASSWORD_CHANGED".equals(action) || "android.app.action.ACTION_PASSWORD_FAILED".equals(action) || "android.app.action.ACTION_PASSWORD_SUCCEEDED".equals(action) || "android.app.action.DEVICE_ADMIN_ENABLED".equals(action) || "android.app.action.DEVICE_ADMIN_DISABLE_REQUESTED".equals(action)) {
            return;
        }
        "android.app.action.DEVICE_ADMIN_DISABLED".equals(action);
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onDisabled(Context context, Intent intent) {
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onEnabled(Context context, Intent intent) {
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onPasswordChanged(Context context, Intent intent) {
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onPasswordFailed(Context context, Intent intent) {
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onPasswordSucceeded(Context context, Intent intent) {
    }

    public final void onRecoveryPasswordRequested(Context context, Intent intent) {
    }
}
