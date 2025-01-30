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
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.samsung.android.desktopmode.SemDesktopModeState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public boolean startActivityInBixby(Context context, String str, String str2, int i, boolean z) {
        boolean z2;
        SemDesktopModeState semDesktopModeState;
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(str, str2));
        intent.putExtra("from-bixby", true);
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        int currentUser = ActivityManager.getCurrentUser();
        DesktopManager desktopManager = this.mDesktopManager;
        int i2 = (desktopManager == null || (semDesktopModeState = ((DesktopManagerImpl) desktopManager).getSemDesktopModeState()) == null || semDesktopModeState.getEnabled() != 4 || ((DesktopManagerImpl) this.mDesktopManager).isStandalone()) ? 0 : 2;
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
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isPackageEnabledForCoverLauncher = ", z2, TAG);
            if (BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && isFolderClosed() && z2) {
                if (Settings.Secure.getInt(context.getContentResolver(), SHOW_NAVIGATION_FOR_SUBSCREEN, 0) == 0) {
                    Settings.Secure.putInt(context.getContentResolver(), SHOW_NAVIGATION_FOR_SUBSCREEN, 1);
                }
                i2 = 1;
            }
        } else {
            if (BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && isFolderClosed()) {
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
        if (z) {
            Log.d(TAG, "start activity in cover screen");
            i2 = 1;
        }
        makeBasic.setLaunchDisplayId(i2);
        try {
            if (BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && isFolderClosed() && i2 == 0) {
                Intent intent2 = new Intent();
                intent2.putExtra("showCoverToast", true);
                intent2.putExtra("ignoreKeyguardState", true);
                this.mKeyguardManager.semSetPendingIntentAfterUnlock(PendingIntent.getActivityAsUser(context, 0, intent, 201326592, null, UserHandle.CURRENT_OR_SELF), intent2);
            } else {
                context.startActivity(intent, makeBasic.toBundle());
            }
            return true;
        } catch (ActivityNotFoundException unused) {
            return false;
        }
    }
}
