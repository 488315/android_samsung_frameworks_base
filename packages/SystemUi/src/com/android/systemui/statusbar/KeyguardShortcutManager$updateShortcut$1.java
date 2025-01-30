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
import androidx.core.app.AbstractC0147x487e7be7;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import java.util.function.Predicate;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
                /* JADX WARN: Code restructure failed: missing block: B:17:0x008b, code lost:
                
                    if ((((com.android.systemui.settings.UserTrackerImpl) r1.userSwitcherController.userTracker).getUserId() == 77) == false) goto L47;
                 */
                @Override // java.util.function.Predicate
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final boolean test(Object obj) {
                    boolean z;
                    boolean z2;
                    KeyguardShortcutManager.ShortcutData shortcutData;
                    ComponentName componentName = (ComponentName) obj;
                    KeyguardShortcutManager.ShortcutData shortcutData2 = KeyguardShortcutManager.this.mShortcuts[i];
                    Intrinsics.checkNotNull(shortcutData2);
                    shortcutData2.enabled = false;
                    if (componentName == null || KeyguardShortcutManager.this.getSuspended(componentName.getPackageName())) {
                        AbstractC0147x487e7be7.m26m("updateShortcut : ", i, " is disabled from settings", "KeyguardShortcutManager");
                        return false;
                    }
                    String packageName = componentName.getPackageName();
                    Intent intent = new Intent("android.intent.action.MAIN");
                    intent.setComponent(componentName);
                    ResolveInfo resolveActivityAsUser = KeyguardShortcutManager.this.mPm.resolveActivityAsUser(intent, 129, KeyguardUpdateMonitor.getCurrentUser());
                    ActivityInfo activityInfo = resolveActivityAsUser != null ? resolveActivityAsUser.activityInfo : null;
                    if (activityInfo != null) {
                        PackageManager packageManager = KeyguardShortcutManager.this.mPm;
                        try {
                            packageManager.getApplicationInfo(componentName.getPackageName(), 1);
                            packageManager.getActivityInfo(componentName, 1);
                            z = true;
                        } catch (PackageManager.NameNotFoundException e) {
                            Log.d("KeyguardShortcutManager", "isAppEnabled() Error: " + e + ", Component: " + componentName);
                            z = false;
                        }
                        if (!z) {
                        }
                        if (!activityInfo.enabled) {
                            PackageManager packageManager2 = KeyguardShortcutManager.this.mPm;
                            Intrinsics.checkNotNull(packageManager2);
                            if (packageManager2.getComponentEnabledSetting(componentName) != 1) {
                                Log.d("KeyguardShortcutManager", "getComponentEnabled ... !COMPONENT_...STATE_ENABLED.");
                                return false;
                            }
                        }
                        KeyguardQuickAffordanceConfig[] keyguardQuickAffordanceConfigArr = KeyguardShortcutManager.this.mKeyguardBottomAreaShortcutTask;
                        int i2 = i;
                        if (keyguardQuickAffordanceConfigArr[i2] != null) {
                            keyguardQuickAffordanceConfigArr[i2] = null;
                        }
                        Bundle bundle = activityInfo.metaData;
                        boolean z3 = bundle != null ? bundle.getBoolean("com.samsung.keyguard.SHOW_WHEN_LOCKED_SHORTCUT", false) : false;
                        KeyguardShortcutManager.ShortcutData shortcutData3 = KeyguardShortcutManager.this.mShortcuts[i];
                        Intrinsics.checkNotNull(shortcutData3);
                        if (z3) {
                            KeyguardShortcutManager keyguardShortcutManager2 = KeyguardShortcutManager.this;
                            Intrinsics.checkNotNull(packageName);
                            if (keyguardShortcutManager2.isShortcutPermission(packageName)) {
                                z2 = true;
                                shortcutData3.mNoUnlockNeeded = z2;
                                shortcutData = KeyguardShortcutManager.this.mShortcuts[i];
                                Intrinsics.checkNotNull(shortcutData);
                                if (shortcutData.mNoUnlockNeeded && bundle != null) {
                                    KeyguardShortcutManager.ShortcutData shortcutData4 = KeyguardShortcutManager.this.mShortcuts[i];
                                    Intrinsics.checkNotNull(shortcutData4);
                                    shortcutData4.launchInsecureMain = bundle.getBoolean("com.samsung.keyguard.LAUNCH_INSECURE_MAIN_SHORTCUT", false);
                                }
                                Intrinsics.checkNotNull(KeyguardShortcutManager.this.mShortcuts[i]);
                                KeyguardShortcutManager.this.isDefaultShortcutIcon(packageName);
                                KeyguardShortcutManager.ShortcutData shortcutData5 = KeyguardShortcutManager.this.mShortcuts[i];
                                Intrinsics.checkNotNull(shortcutData5);
                                shortcutData5.enabled = true;
                                KeyguardShortcutManager.ShortcutData shortcutData6 = KeyguardShortcutManager.this.mShortcuts[i];
                                Intrinsics.checkNotNull(shortcutData6);
                                shortcutData6.mDrawable = KeyguardShortcutManager.access$getShortcutIcon(KeyguardShortcutManager.this, activityInfo, false);
                                KeyguardShortcutManager.ShortcutData shortcutData7 = KeyguardShortcutManager.this.mShortcuts[i];
                                Intrinsics.checkNotNull(shortcutData7);
                                shortcutData7.mPanelDrawable = KeyguardShortcutManager.access$getShortcutIcon(KeyguardShortcutManager.this, activityInfo, true);
                                KeyguardShortcutManager.ShortcutData shortcutData8 = KeyguardShortcutManager.this.mShortcuts[i];
                                Intrinsics.checkNotNull(shortcutData8);
                                shortcutData8.mComponentName = componentName;
                                KeyguardShortcutManager.ShortcutData shortcutData9 = KeyguardShortcutManager.this.mShortcuts[i];
                                Intrinsics.checkNotNull(shortcutData9);
                                shortcutData9.taskName = null;
                                KeyguardShortcutManager.ShortcutData shortcutData10 = KeyguardShortcutManager.this.mShortcuts[i];
                                Intrinsics.checkNotNull(shortcutData10);
                                PackageManager packageManager3 = KeyguardShortcutManager.this.mPm;
                                Intrinsics.checkNotNull(packageManager3);
                                shortcutData10.mAppLabel = activityInfo.loadLabel(packageManager3).toString();
                                int i3 = i;
                                KeyguardShortcutManager.ShortcutData shortcutData11 = KeyguardShortcutManager.this.mShortcuts[i3];
                                Intrinsics.checkNotNull(shortcutData11);
                                ComponentName componentName2 = shortcutData11.mComponentName;
                                Intrinsics.checkNotNull(componentName2);
                                Log.d("KeyguardShortcutManager", "updateShortcut th : " + i3 + " class : " + componentName2.getClassName());
                                KeyguardShortcutManager.this.getQuickAffordanceConfigList();
                                return true;
                            }
                        }
                        z2 = false;
                        shortcutData3.mNoUnlockNeeded = z2;
                        shortcutData = KeyguardShortcutManager.this.mShortcuts[i];
                        Intrinsics.checkNotNull(shortcutData);
                        if (shortcutData.mNoUnlockNeeded) {
                            KeyguardShortcutManager.ShortcutData shortcutData42 = KeyguardShortcutManager.this.mShortcuts[i];
                            Intrinsics.checkNotNull(shortcutData42);
                            shortcutData42.launchInsecureMain = bundle.getBoolean("com.samsung.keyguard.LAUNCH_INSECURE_MAIN_SHORTCUT", false);
                        }
                        Intrinsics.checkNotNull(KeyguardShortcutManager.this.mShortcuts[i]);
                        KeyguardShortcutManager.this.isDefaultShortcutIcon(packageName);
                        KeyguardShortcutManager.ShortcutData shortcutData52 = KeyguardShortcutManager.this.mShortcuts[i];
                        Intrinsics.checkNotNull(shortcutData52);
                        shortcutData52.enabled = true;
                        KeyguardShortcutManager.ShortcutData shortcutData62 = KeyguardShortcutManager.this.mShortcuts[i];
                        Intrinsics.checkNotNull(shortcutData62);
                        shortcutData62.mDrawable = KeyguardShortcutManager.access$getShortcutIcon(KeyguardShortcutManager.this, activityInfo, false);
                        KeyguardShortcutManager.ShortcutData shortcutData72 = KeyguardShortcutManager.this.mShortcuts[i];
                        Intrinsics.checkNotNull(shortcutData72);
                        shortcutData72.mPanelDrawable = KeyguardShortcutManager.access$getShortcutIcon(KeyguardShortcutManager.this, activityInfo, true);
                        KeyguardShortcutManager.ShortcutData shortcutData82 = KeyguardShortcutManager.this.mShortcuts[i];
                        Intrinsics.checkNotNull(shortcutData82);
                        shortcutData82.mComponentName = componentName;
                        KeyguardShortcutManager.ShortcutData shortcutData92 = KeyguardShortcutManager.this.mShortcuts[i];
                        Intrinsics.checkNotNull(shortcutData92);
                        shortcutData92.taskName = null;
                        KeyguardShortcutManager.ShortcutData shortcutData102 = KeyguardShortcutManager.this.mShortcuts[i];
                        Intrinsics.checkNotNull(shortcutData102);
                        PackageManager packageManager32 = KeyguardShortcutManager.this.mPm;
                        Intrinsics.checkNotNull(packageManager32);
                        shortcutData102.mAppLabel = activityInfo.loadLabel(packageManager32).toString();
                        int i32 = i;
                        KeyguardShortcutManager.ShortcutData shortcutData112 = KeyguardShortcutManager.this.mShortcuts[i32];
                        Intrinsics.checkNotNull(shortcutData112);
                        ComponentName componentName22 = shortcutData112.mComponentName;
                        Intrinsics.checkNotNull(componentName22);
                        Log.d("KeyguardShortcutManager", "updateShortcut th : " + i32 + " class : " + componentName22.getClassName());
                        KeyguardShortcutManager.this.getQuickAffordanceConfigList();
                        return true;
                    }
                    Slog.d("KeyguardShortcutManager", "updateShortcut : " + i + " activityInfo is null, resolveInfo is : " + resolveActivityAsUser + ",  return FALSE");
                    return false;
                }
            }.test(this.$componentName)) {
                final KeyguardShortcutManager keyguardShortcutManager2 = this.this$0;
                Handler handler = keyguardShortcutManager2.mHandler;
                final int i2 = this.$th;
                handler.post(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcut$1.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardShortcutManager keyguardShortcutManager3 = KeyguardShortcutManager.this;
                        int i3 = i2;
                        KeyguardShortcutManager.Companion companion = KeyguardShortcutManager.Companion;
                        keyguardShortcutManager3.sendUpdateShortcutViewToCallback(i3);
                    }
                });
            } else {
                final KeyguardShortcutManager keyguardShortcutManager3 = this.this$0;
                Handler handler2 = keyguardShortcutManager3.mHandler;
                final int i3 = this.$th;
                handler2.post(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcut$1.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardShortcutManager.access$resetShortcut(KeyguardShortcutManager.this, i3);
                    }
                });
            }
        } catch (Exception e) {
            this.this$0.mSettingsHelper.resetShortcutValue();
            Log.e("KeyguardShortcutManager", "getPositionCorrectionRatio exception = " + e);
        }
    }
}
