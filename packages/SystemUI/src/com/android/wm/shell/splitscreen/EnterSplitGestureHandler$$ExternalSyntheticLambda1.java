package com.android.wm.shell.splitscreen;

import android.app.ActivityTaskManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Slog;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;
import com.samsung.android.rune.CoreRune;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class EnterSplitGestureHandler$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EnterSplitGestureHandler f$0;

    public /* synthetic */ EnterSplitGestureHandler$$ExternalSyntheticLambda1(EnterSplitGestureHandler enterSplitGestureHandler, int i) {
        this.$r8$classId = i;
        this.f$0 = enterSplitGestureHandler;
    }

    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.wm.shell.splitscreen.EnterSplitGestureHandler$5] */
    @Override // java.lang.Runnable
    public final void run() {
        int i;
        switch (this.$r8$classId) {
            case 0:
                final EnterSplitGestureHandler enterSplitGestureHandler = this.f$0;
                String str = EnterSplitGestureHandler.TAG;
                boolean z = EnterSplitGestureHandler.DEBUG;
                if (z) {
                    Slog.d(str, "init");
                }
                enterSplitGestureHandler.mIsSupportSplitScreen = ActivityTaskManager.deviceSupportsMultiWindow(enterSplitGestureHandler.mContext);
                if (z) {
                    Slog.d(str, "get settings");
                }
                ContentResolver contentResolver = enterSplitGestureHandler.mContext.getContentResolver();
                boolean z2 = false;
                enterSplitGestureHandler.mIsSettingEnabled = Settings.Global.getInt(contentResolver, SettingsHelper.INDEX_MW_ENTER_SPLIT_USING_GESTURE, 0) == 1;
                String string = Settings.Secure.getString(contentResolver, SettingsHelper.INDEX_NAVIGATION_MODE);
                try {
                    i = Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    Slog.d(str, "failed to load nav mode=" + string);
                    e.printStackTrace();
                    i = 0;
                }
                enterSplitGestureHandler.mNavMode = i;
                enterSplitGestureHandler.mIsDeviceProvisioned = Settings.Global.getInt(contentResolver, "device_provisioned", 0) != 0;
                enterSplitGestureHandler.mIsUserSetupComplete = Settings.Secure.getIntForUser(contentResolver, SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0, -2) != 0;
                try {
                    enterSplitGestureHandler.mIsLockTaskMode = enterSplitGestureHandler.mAtm.isInLockTaskMode();
                } catch (RemoteException e2) {
                    if (z) {
                        Slog.e(str, "Failed to get lock task mode.");
                    }
                    e2.printStackTrace();
                }
                Set enabledServicesFromSettings = AccessibilityUtils.getEnabledServicesFromSettings(enterSplitGestureHandler.mContext, 0);
                ComponentName talkbackComponent = enterSplitGestureHandler.getTalkbackComponent();
                if (talkbackComponent == null) {
                    talkbackComponent = new ComponentName("com.samsung.android.accessibility.talkback", ControlPanelUtils.TALKBACK_SERVICE);
                }
                enterSplitGestureHandler.mIsTalkbackEnabled = enabledServicesFromSettings.contains(talkbackComponent);
                if (z) {
                    Slog.d(str, "register observer");
                }
                final Uri uriFor = Settings.Global.getUriFor(SettingsHelper.INDEX_MW_ENTER_SPLIT_USING_GESTURE);
                final Uri uriFor2 = Settings.Secure.getUriFor("MultiWindow_twoFingerSplitGesture_TestTouchSlop");
                final Uri uriFor3 = Settings.Secure.getUriFor("MultiWindow_twoFingerSplitGesture_TestFlag");
                final Uri uriFor4 = Settings.Secure.getUriFor(SettingsHelper.INDEX_NAVIGATION_MODE);
                final Uri uriFor5 = Settings.Global.getUriFor("device_provisioned");
                final Uri uriFor6 = Settings.Secure.getUriFor(SettingsHelper.INDEX_USER_SETUP_COMPLETE);
                final Uri uriFor7 = Settings.Secure.getUriFor(SettingsHelper.INDEX_ENABLED_ACCESSIBILITY_SERVICES);
                final ContentResolver contentResolver2 = enterSplitGestureHandler.mContext.getContentResolver();
                final Handler handler = enterSplitGestureHandler.mHandler;
                enterSplitGestureHandler.mObserver = new ContentObserver(handler) { // from class: com.android.wm.shell.splitscreen.EnterSplitGestureHandler.5
                    public final /* synthetic */ Uri val$accessibilityServiceUri;
                    public final /* synthetic */ ContentResolver val$cr;
                    public final /* synthetic */ Uri val$deviceProvisionedUri;
                    public final /* synthetic */ Uri val$navigationModeUri;
                    public final /* synthetic */ Uri val$splitGestureUri;
                    public final /* synthetic */ Uri val$testFlagsUri;
                    public final /* synthetic */ Uri val$testTouchSlopUri;
                    public final /* synthetic */ Uri val$userSetupCompleteUri;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass5(final Handler handler2, final Uri uriFor8, final ContentResolver contentResolver22, final Uri uriFor22, final Uri uriFor32, final Uri uriFor42, final Uri uriFor52, final Uri uriFor62, final Uri uriFor72) {
                        super(handler2);
                        r3 = uriFor8;
                        r4 = contentResolver22;
                        r5 = uriFor22;
                        r6 = uriFor32;
                        r7 = uriFor42;
                        r8 = uriFor52;
                        r9 = uriFor62;
                        r10 = uriFor72;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z3, Uri uri) {
                        boolean z4;
                        boolean z5 = EnterSplitGestureHandler.DEBUG;
                        if (z5) {
                            Slog.d(EnterSplitGestureHandler.TAG, "onChange: " + uri);
                        }
                        if (r3.equals(uri)) {
                            z4 = Settings.Global.getInt(r4, SettingsHelper.INDEX_MW_ENTER_SPLIT_USING_GESTURE, 0) == 1;
                            EnterSplitGestureHandler enterSplitGestureHandler2 = EnterSplitGestureHandler.this;
                            if (enterSplitGestureHandler2.mIsSettingEnabled == z4) {
                                return;
                            } else {
                                enterSplitGestureHandler2.mIsSettingEnabled = z4;
                            }
                        } else {
                            if (r5.equals(uri)) {
                                float parseFloat = Float.parseFloat(Settings.Secure.getString(r4, "MultiWindow_twoFingerSplitGesture_TestTouchSlop"));
                                EnterSplitGestureHandler.this.mGestureDetector.setTouchSlopForTest(parseFloat);
                                if (z5) {
                                    Slog.d(EnterSplitGestureHandler.TAG, String.format("test touch slop=%f", Float.valueOf(parseFloat)));
                                    return;
                                }
                                return;
                            }
                            if (r6.equals(uri)) {
                                Integer decode = Integer.decode(Settings.Secure.getString(r4, "MultiWindow_twoFingerSplitGesture_TestFlag"));
                                int intValue = decode.intValue();
                                EnterSplitGestureHandler.this.mGestureDetector.setDebug((intValue & 1) != 0);
                                EnterSplitGestureHandler.this.mGestureDetector.setDebugNoise((intValue & 2) != 0);
                                EnterSplitGestureHandler.this.mIsTalkbackEnabled = (intValue & 4) != 0;
                                if (z5) {
                                    Slog.d(EnterSplitGestureHandler.TAG, String.format("test flags=%x", decode));
                                    return;
                                }
                                return;
                            }
                            if (r7.equals(uri)) {
                                int i2 = Settings.Secure.getInt(r4, SettingsHelper.INDEX_NAVIGATION_MODE, 0);
                                EnterSplitGestureHandler enterSplitGestureHandler3 = EnterSplitGestureHandler.this;
                                if (enterSplitGestureHandler3.mNavMode == i2) {
                                    return;
                                } else {
                                    enterSplitGestureHandler3.mNavMode = i2;
                                }
                            } else if (r8.equals(uri)) {
                                z4 = Settings.Global.getInt(r4, "device_provisioned", 0) != 0;
                                EnterSplitGestureHandler enterSplitGestureHandler4 = EnterSplitGestureHandler.this;
                                if (enterSplitGestureHandler4.mIsDeviceProvisioned == z4) {
                                    return;
                                } else {
                                    enterSplitGestureHandler4.mIsDeviceProvisioned = z4;
                                }
                            } else {
                                if (!r9.equals(uri)) {
                                    if (r10.equals(uri)) {
                                        Set enabledServicesFromSettings2 = AccessibilityUtils.getEnabledServicesFromSettings(EnterSplitGestureHandler.this.mContext, 0);
                                        ComponentName talkbackComponent2 = EnterSplitGestureHandler.this.getTalkbackComponent();
                                        z4 = talkbackComponent2 != null && enabledServicesFromSettings2.contains(talkbackComponent2);
                                        EnterSplitGestureHandler enterSplitGestureHandler5 = EnterSplitGestureHandler.this;
                                        if (enterSplitGestureHandler5.mIsTalkbackEnabled != z4) {
                                            enterSplitGestureHandler5.mIsTalkbackEnabled = z4;
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                }
                                z4 = Settings.Secure.getIntForUser(r4, SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0, -2) != 0;
                                EnterSplitGestureHandler enterSplitGestureHandler6 = EnterSplitGestureHandler.this;
                                if (enterSplitGestureHandler6.mIsUserSetupComplete == z4) {
                                    return;
                                } else {
                                    enterSplitGestureHandler6.mIsUserSetupComplete = z4;
                                }
                            }
                        }
                        EnterSplitGestureHandler.this.updateEnableState("changed " + uri.toSafeString());
                    }
                };
                enterSplitGestureHandler.mContext.getContentResolver().registerContentObserver(uriFor8, false, enterSplitGestureHandler.mObserver, 0);
                enterSplitGestureHandler.mContext.getContentResolver().registerContentObserver(uriFor22, false, enterSplitGestureHandler.mObserver, 0);
                enterSplitGestureHandler.mContext.getContentResolver().registerContentObserver(uriFor32, false, enterSplitGestureHandler.mObserver, 0);
                enterSplitGestureHandler.mContext.getContentResolver().registerContentObserver(uriFor42, false, enterSplitGestureHandler.mObserver, 0);
                enterSplitGestureHandler.mContext.getContentResolver().registerContentObserver(uriFor52, false, enterSplitGestureHandler.mObserver, 0);
                enterSplitGestureHandler.mContext.getContentResolver().registerContentObserver(uriFor62, false, enterSplitGestureHandler.mObserver, 0);
                enterSplitGestureHandler.mContext.getContentResolver().registerContentObserver(uriFor72, false, enterSplitGestureHandler.mObserver, 0);
                if (z) {
                    Slog.d(str, "register broadcast");
                }
                enterSplitGestureHandler.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.wm.shell.splitscreen.EnterSplitGestureHandler.4
                    public AnonymousClass4() {
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        if ("com.samsung.android.action.LOCK_TASK_MODE".equals(intent.getAction())) {
                            EnterSplitGestureHandler.this.mIsLockTaskMode = intent.getBooleanExtra("enable", false);
                            EnterSplitGestureHandler.this.updateEnableState("lock task mode changed");
                        }
                    }
                }, new IntentFilter("com.samsung.android.action.LOCK_TASK_MODE"), "com.samsung.android.permission.LOCK_TASK_MODE", enterSplitGestureHandler.mHandler, 2);
                Configuration configuration = enterSplitGestureHandler.mContext.getResources().getConfiguration();
                if (CoreRune.MD_DEX_SUPPORT_STANDALONE && configuration.dexMode == 1) {
                    z2 = true;
                }
                enterSplitGestureHandler.mIsStandAlone = z2;
                enterSplitGestureHandler.mDisplayController.addDisplayWindowListener(new DisplayController.OnDisplaysChangedListener() { // from class: com.android.wm.shell.splitscreen.EnterSplitGestureHandler.3
                    public AnonymousClass3() {
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:5:0x0009, code lost:
                    
                        if (r4.dexMode == 1) goto L25;
                     */
                    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onDisplayConfigurationChanged(int r3, android.content.res.Configuration r4) {
                        /*
                            r2 = this;
                            boolean r0 = com.samsung.android.rune.CoreRune.MD_DEX_SUPPORT_STANDALONE
                            if (r0 == 0) goto Lc
                            if (r3 != 0) goto Lc
                            int r0 = r4.dexMode
                            r1 = 1
                            if (r0 != r1) goto Lc
                            goto Ld
                        Lc:
                            r1 = 0
                        Ld:
                            com.android.wm.shell.splitscreen.EnterSplitGestureHandler r2 = com.android.wm.shell.splitscreen.EnterSplitGestureHandler.this
                            boolean r0 = r2.mIsStandAlone
                            if (r0 == r1) goto L1b
                            r2.mIsStandAlone = r1
                            java.lang.String r0 = "standAlone"
                            r2.updateEnableState(r0)
                        L1b:
                            if (r3 != 0) goto L21
                            int r3 = r4.semDisplayDeviceType
                            r2.mDisplayDeviceType = r3
                        L21:
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.EnterSplitGestureHandler.AnonymousClass3.onDisplayConfigurationChanged(int, android.content.res.Configuration):void");
                    }
                }, -1);
                enterSplitGestureHandler.updateEnableState("init");
                break;
            default:
                EnterSplitGestureHandler enterSplitGestureHandler2 = this.f$0;
                boolean z3 = EnterSplitGestureHandler.DEBUG;
                enterSplitGestureHandler2.updateEnableState("onSystemUiStateChanged");
                break;
        }
    }
}
