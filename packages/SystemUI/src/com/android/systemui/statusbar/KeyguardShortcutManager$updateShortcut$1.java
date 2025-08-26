package com.android.systemui.statusbar;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Slog;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final class KeyguardShortcutManager$updateShortcut$1 implements Runnable {
    public final /* synthetic */ ComponentName $componentName;
    public final /* synthetic */ int $th;
    public final /* synthetic */ KeyguardShortcutManager this$0;

    public KeyguardShortcutManager$updateShortcut$1(ComponentName componentName, KeyguardShortcutManager keyguardShortcutManager, int i) {
        this.$componentName = componentName;
        this.this$0 = keyguardShortcutManager;
        this.$th = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            final KeyguardShortcutManager keyguardShortcutManager = this.this$0;
            final int i = this.$th;
            if (new Predicate() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcut$1.1
                /* JADX WARN: Code restructure failed: missing block: B:17:0x0081, code lost:
                
                    if (((com.android.systemui.settings.UserTrackerImpl) r1.userSwitcherController.userTracker).getUserId() != 77) goto L41;
                 */
                @Override // java.util.function.Predicate
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final boolean test(Object obj) throws PackageManager.NameNotFoundException {
                    ComponentName componentName = (ComponentName) obj;
                    KeyguardShortcutManager keyguardShortcutManager2 = keyguardShortcutManager;
                    keyguardShortcutManager2.shortcutsData[i].enabled = false;
                    if (componentName == null || keyguardShortcutManager2.getSuspended(componentName.getPackageName())) {
                        NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "updateShortcut : ", " is disabled from settings", "KeyguardShortcutManager");
                        return false;
                    }
                    String packageName = componentName.getPackageName();
                    Intent intent = new Intent("android.intent.action.MAIN");
                    intent.setComponent(componentName);
                    KeyguardShortcutManager keyguardShortcutManager3 = keyguardShortcutManager;
                    ResolveInfo resolveInfoResolveActivityAsUser = keyguardShortcutManager3.packageManager.resolveActivityAsUser(intent, 129, ((UserTrackerImpl) keyguardShortcutManager3.userTracker).getUserId());
                    ActivityInfo activityInfo = resolveInfoResolveActivityAsUser != null ? resolveInfoResolveActivityAsUser.activityInfo : null;
                    if (activityInfo != null) {
                        PackageManager packageManager = keyguardShortcutManager.packageManager;
                        try {
                            packageManager.getApplicationInfo(componentName.getPackageName(), 1);
                            packageManager.getActivityInfo(componentName, 1);
                        } catch (PackageManager.NameNotFoundException e) {
                            Log.d("KeyguardShortcutManager", "isAppEnabled() Error: " + e + ", Component: " + componentName);
                        }
                        if (!activityInfo.enabled && keyguardShortcutManager.packageManager.getComponentEnabledSetting(componentName) != 1) {
                            Log.d("KeyguardShortcutManager", "getComponentEnabled ... !COMPONENT_...STATE_ENABLED.");
                            return false;
                        }
                        Bundle bundle = activityInfo.metaData;
                        boolean z = bundle != null ? bundle.getBoolean("com.samsung.keyguard.SHOW_WHEN_LOCKED_SHORTCUT", false) : false;
                        KeyguardShortcutManager keyguardShortcutManager4 = keyguardShortcutManager;
                        keyguardShortcutManager4.shortcutsData[i].noUnlockNeeded = z && keyguardShortcutManager4.isShortcutPermission(packageName);
                        KeyguardShortcutManager.ShortcutData shortcutData = keyguardShortcutManager.shortcutsData[i];
                        if (shortcutData.noUnlockNeeded && bundle != null) {
                            shortcutData.launchInsecureMain = bundle.getBoolean("com.samsung.keyguard.LAUNCH_INSECURE_MAIN_SHORTCUT", false);
                        }
                        KeyguardShortcutManager keyguardShortcutManager5 = keyguardShortcutManager;
                        KeyguardShortcutManager.ShortcutData[] shortcutDataArr = keyguardShortcutManager5.shortcutsData;
                        int i2 = i;
                        KeyguardShortcutManager.ShortcutData shortcutData2 = shortcutDataArr[i2];
                        shortcutData2.enabled = activityInfo.exported;
                        shortcutData2.isMonotoneIcon = KeyguardShortcutManager.access$isMonotoneIconRequired(keyguardShortcutManager5, i2);
                        KeyguardShortcutManager keyguardShortcutManager6 = keyguardShortcutManager;
                        KeyguardShortcutManager.ShortcutData[] shortcutDataArr2 = keyguardShortcutManager6.shortcutsData;
                        int i3 = i;
                        shortcutDataArr2[i3].drawable = KeyguardShortcutManager.access$getShortcutIcon(keyguardShortcutManager6, activityInfo, false, i3);
                        KeyguardShortcutManager keyguardShortcutManager7 = keyguardShortcutManager;
                        KeyguardShortcutManager.ShortcutData[] shortcutDataArr3 = keyguardShortcutManager7.shortcutsData;
                        int i4 = i;
                        shortcutDataArr3[i4].panelDrawable = KeyguardShortcutManager.access$getShortcutIcon(keyguardShortcutManager7, activityInfo, true, i4);
                        KeyguardShortcutManager keyguardShortcutManager8 = keyguardShortcutManager;
                        KeyguardShortcutManager.ShortcutData shortcutData3 = keyguardShortcutManager8.shortcutsData[i];
                        shortcutData3.componentName = componentName;
                        shortcutData3.taskName = null;
                        shortcutData3.appLabel = activityInfo.loadLabel(keyguardShortcutManager8.packageManager).toString();
                        KeyguardShortcutManager.ShortcutData[] shortcutDataArr4 = keyguardShortcutManager.shortcutsData;
                        int i5 = i;
                        KeyguardShortcutManager.ShortcutData shortcutData4 = shortcutDataArr4[i5];
                        shortcutData4.isUnlockWaitNeeded = false;
                        ComponentName componentName2 = shortcutData4.componentName;
                        ActionBarContextView$$ExternalSyntheticOutline0.m(KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(i5, "updateShortcut th : ", ", class : ", componentName2 != null ? componentName2.getClassName() : null, ", activity exported : "), activityInfo.exported, "KeyguardShortcutManager");
                        keyguardShortcutManager.getQuickAffordanceConfigList();
                        return true;
                    }
                    Slog.d("KeyguardShortcutManager", "updateShortcut : " + i + " activityInfo is null, resolveInfo is : " + resolveInfoResolveActivityAsUser + ",  return FALSE");
                    return false;
                }
            }.test(this.$componentName)) {
                final KeyguardShortcutManager keyguardShortcutManager2 = this.this$0;
                Handler handler = keyguardShortcutManager2.handler;
                final int i2 = this.$th;
                handler.post(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcut$1.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardShortcutManager keyguardShortcutManager3 = keyguardShortcutManager2;
                        int i3 = i2;
                        KeyguardShortcutManager.Companion companion = KeyguardShortcutManager.Companion;
                        keyguardShortcutManager3.sendUpdateShortcutViewToCallback(i3);
                    }
                });
                return;
            }
            final KeyguardShortcutManager keyguardShortcutManager3 = this.this$0;
            Handler handler2 = keyguardShortcutManager3.handler;
            final int i3 = this.$th;
            handler2.post(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcut$1.3
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardShortcutManager.access$resetShortcut(keyguardShortcutManager3, i3);
                }
            });
        } catch (Exception e) {
            this.this$0.settingsHelper.resetShortcutValue(this.this$0.selectedUserInteractor.getSelectedUserId());
            Log.e("KeyguardShortcutManager", "getPositionCorrectionRatio exception = " + e);
        }
    }
}
