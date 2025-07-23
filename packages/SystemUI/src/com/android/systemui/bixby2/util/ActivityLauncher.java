package com.android.systemui.bixby2.util;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManagerGlobal;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.util.DesktopManager;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ActivityLauncher {
    private static final String BIXBYCLIENT_TISKID = "bixbyClient_taskId";
    private static final String PACKAGENAME_CAMERA = "com.sec.android.app.camera";
    private static final String PACKAGENAME_TMAP = "com.skt.tmap.ku";
    private static final int SEM_LAUNCH_ON_DEX = -1;
    private static final int SEM_LAUNCH_ON_FOCUSED_STACK = -10000;
    private static final String SHOW_NAVIGATION_FOR_SUBSCREEN = "show_navigation_for_subscreen";
    private static final String TAG = "ActivityLauncher";
    private final DesktopManager mDesktopManager;
    private final DisplayLifecycle mDisplayLifecycle;
    private KeyguardManager mKeyguardManager;

    public ActivityLauncher(DesktopManager desktopManager, DisplayLifecycle displayLifecycle, KeyguardManager keyguardManager) {
        this.mDesktopManager = desktopManager;
        this.mDisplayLifecycle = displayLifecycle;
        this.mKeyguardManager = keyguardManager;
    }

    private boolean isFolderClosed() {
        return !this.mDisplayLifecycle.mIsFolderOpened;
    }

    public boolean startActivityInBixby(Context context, String str, String str2, int i) {
        boolean z;
        int i2;
        boolean z2;
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(str, str2));
        intent.putExtra("from-bixby", true);
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        int currentUser = ActivityManager.getCurrentUser();
        boolean isFolderClosed = isFolderClosed();
        DesktopStateImpl.Companion.getClass();
        DesktopStateImpl.DWExternalDisplayMode desktopExternalDisplayMode = DesktopStateImpl.Companion.getDesktopExternalDisplayMode();
        if (desktopExternalDisplayMode != DesktopStateImpl.DWExternalDisplayMode.DW_EXTERNAL_DISPLAY_UNDEFINED) {
            try {
                i2 = WindowManagerGlobal.getWindowManagerService().getTopFocusedDisplayId();
                z = true;
            } catch (RemoteException unused) {
                Log.w(TAG, "Unable to get focusedDisplayId");
                z = true;
                i2 = 0;
            }
        } else {
            i2 = 0;
            z = false;
        }
        Log.d(TAG, "startActivityInBixby() Focused targetDisplayId = " + i2 + ", getDesktopExternalDisplayMode() = " + desktopExternalDisplayMode);
        try {
            Intent.class.getDeclaredMethod("semSetLaunchOverTargetTask", Integer.TYPE, Boolean.TYPE).invoke(intent, Integer.valueOf(i == 0 ? i2 == 2 ? -1 : SEM_LAUNCH_ON_FOCUSED_STACK : i), Boolean.FALSE);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        if (str == null || !str.equals(PACKAGENAME_CAMERA)) {
            intent.setFlags(270532608);
            try {
                z2 = ActivityTaskManager.getService().isPackageEnabledForCoverLauncher(str, currentUser);
            } catch (RemoteException e2) {
                Log.e(TAG, e2.getMessage());
                z2 = false;
            }
            EmergencyButtonController$$ExternalSyntheticOutline0.m("isPackageEnabledForCoverLauncher = ", TAG, z2);
            if (!z) {
                if (BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && isFolderClosed && z2) {
                    if (Settings.Secure.getInt(context.getContentResolver(), "show_navigation_for_subscreen", 0) == 0) {
                        Settings.Secure.putInt(context.getContentResolver(), "show_navigation_for_subscreen", 1);
                    }
                    i2 = 1;
                } else {
                    i2 = 0;
                }
            }
        } else {
            if (BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && isFolderClosed) {
                i2 = 1;
            }
            if (this.mKeyguardManager == null) {
                this.mKeyguardManager = (KeyguardManager) context.getSystemService("keyguard");
            }
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            KeyguardManager keyguardManager = this.mKeyguardManager;
            if (keyguardManager == null || !keyguardManager.isKeyguardLocked()) {
                intent.setFlags(270532608);
            } else {
                intent.setFlags(268468224);
                intent.putExtra("isSecure", true);
            }
        }
        if (str != null && str.equals(PACKAGENAME_TMAP)) {
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
        }
        makeBasic.setLaunchDisplayId(i2);
        Log.d(TAG, "startActivityInBixby() setLaunchDisplayId targetDisplayId = " + i2);
        try {
            if (BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && isFolderClosed && i2 == 0) {
                Intent intent2 = new Intent();
                intent2.putExtra("showCoverToast", true);
                intent2.putExtra("ignoreKeyguardState", true);
                PendingIntent activityAsUser = PendingIntent.getActivityAsUser(context, 0, intent, 201326592, null, UserHandle.CURRENT_OR_SELF);
                KeyguardManager keyguardManager2 = this.mKeyguardManager;
                if (keyguardManager2 != null) {
                    keyguardManager2.semSetPendingIntentAfterUnlock(activityAsUser, intent2);
                }
            } else {
                context.startActivity(intent, makeBasic.toBundle());
            }
            return true;
        } catch (ActivityNotFoundException unused2) {
            return false;
        }
    }
}
