package com.android.systemui.keyguard;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.policy.IKeyguardDrawnCallback;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.RemoteLockInfo;
import com.android.keyguard.KeyguardConstants$KeyguardDismissActionType;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.LsRune;
import com.android.systemui.Rune;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardSurfaceController;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.log.LogLevel;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.sensor.PickupController;
import com.android.systemui.sensor.PickupController$baseSensorListener$1;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelper;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ScrimState;
import com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.uithreadmonitor.BinderCallMonitorConstants;
import com.android.systemui.uithreadmonitor.BinderCallMonitorImpl;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.samsung.android.cover.CoverState;
import com.sec.ims.configuration.DATA;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.text.CharsKt__CharJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardViewMediatorHelperImpl f$0;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda6(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardViewMediatorHelperImpl;
    }

    /* JADX WARN: Code restructure failed: missing block: B:125:0x0172, code lost:
    
        if (r9 == false) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0185, code lost:
    
        r9 = r0.fixedRotationMonitor;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0189, code lost:
    
        if (r9.isMonitorStarted == false) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x018d, code lost:
    
        if (r9.isFixedRotated == false) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x018f, code lost:
    
        r13 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0192, code lost:
    
        if (r13 == false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0194, code lost:
    
        r13 = r1.mFinishedCallback;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x0196, code lost:
    
        if (r13 == null) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0198, code lost:
    
        r9.setPendingRunnable(new com.android.systemui.keyguard.RunnableC1497x5708ee72(r13, r0));
        r1.mFinishedCallback = null;
        r1.mApps = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x01a4, code lost:
    
        r9 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x01a9, code lost:
    
        if (com.android.systemui.LsRune.SECURITY_FINGERPRINT_IN_DISPLAY_OPTICAL == false) goto L112;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x01af, code lost:
    
        if (r12.isEnabled() == false) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x01b5, code lost:
    
        if (r12.biometricSourceType != android.hardware.biometrics.BiometricSourceType.FINGERPRINT) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x01b7, code lost:
    
        r13 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x01ba, code lost:
    
        if (r13 == false) goto L112;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x01bc, code lost:
    
        r0.updateMonitor.removeMaskViewForOpticalFpSensor();
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x01b9, code lost:
    
        r13 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x01c1, code lost:
    
        r12.setForceInvisible(null, true);
        r14 = new kotlin.Pair(java.lang.Boolean.TRUE, java.lang.Boolean.valueOf(r9));
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x01a6, code lost:
    
        r9 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0191, code lost:
    
        r13 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x0183, code lost:
    
        if (r9 != false) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:335:0x0581, code lost:
    
        if (r2 != false) goto L312;
     */
    /* JADX WARN: Code restructure failed: missing block: B:341:0x0587, code lost:
    
        if (r0.isFastWakeAndUnlockMode() == false) goto L312;
     */
    /* JADX WARN: Code restructure failed: missing block: B:429:0x0735, code lost:
    
        if (r10 != false) goto L415;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:330:0x056d  */
    /* JADX WARN: Removed duplicated region for block: B:483:0x084d  */
    /* JADX WARN: Removed duplicated region for block: B:486:0x0858  */
    /* JADX WARN: Removed duplicated region for block: B:553:0x0969 A[LOOP:3: B:547:0x092e->B:553:0x0969, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:554:0x0967 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x013d  */
    /* JADX WARN: Type inference failed for: r10v13 */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v15 */
    /* JADX WARN: Type inference failed for: r13v10 */
    /* JADX WARN: Type inference failed for: r13v14 */
    /* JADX WARN: Type inference failed for: r13v15 */
    /* JADX WARN: Type inference failed for: r13v18 */
    /* JADX WARN: Type inference failed for: r13v21 */
    /* JADX WARN: Type inference failed for: r13v9 */
    /* JADX WARN: Type inference failed for: r14v8, types: [com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$showForegroundImmediatelyIfNeeded$1$1] */
    /* JADX WARN: Type inference failed for: r1v53 */
    /* JADX WARN: Type inference failed for: r1v54 */
    /* JADX WARN: Type inference failed for: r1v61 */
    /* JADX WARN: Type inference failed for: r2v46 */
    /* JADX WARN: Type inference failed for: r2v47 */
    /* JADX WARN: Type inference failed for: r2v50 */
    /* JADX WARN: Type inference failed for: r2v51 */
    /* JADX WARN: Type inference failed for: r2v53 */
    /* JADX WARN: Type inference failed for: r2v57 */
    /* JADX WARN: Type inference failed for: r3v109 */
    /* JADX WARN: Type inference failed for: r3v110 */
    /* JADX WARN: Type inference failed for: r3v119 */
    /* JADX WARN: Type inference failed for: r3v125 */
    /* JADX WARN: Type inference failed for: r3v126 */
    /* JADX WARN: Type inference failed for: r3v130 */
    /* JADX WARN: Type inference failed for: r3v131 */
    /* JADX WARN: Type inference failed for: r3v132 */
    /* JADX WARN: Type inference failed for: r3v137 */
    /* JADX WARN: Type inference failed for: r3v139 */
    /* JADX WARN: Type inference failed for: r3v71 */
    /* JADX WARN: Type inference failed for: r3v72 */
    /* JADX WARN: Type inference failed for: r3v98 */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r8v19 */
    /* JADX WARN: Type inference failed for: r8v20 */
    /* JADX WARN: Type inference failed for: r8v22 */
    /* JADX WARN: Type inference failed for: r9v106 */
    /* JADX WARN: Type inference failed for: r9v107 */
    /* JADX WARN: Type inference failed for: r9v110 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v6 */
    /* JADX WARN: Type inference failed for: r9v65 */
    /* JADX WARN: Type inference failed for: r9v66 */
    /* JADX WARN: Type inference failed for: r9v7 */
    /* JADX WARN: Type inference failed for: r9v71 */
    /* JADX WARN: Type inference failed for: r9v80 */
    /* JADX WARN: Type inference failed for: r9v81 */
    /* JADX WARN: Type inference failed for: r9v83 */
    /* JADX WARN: Type inference failed for: r9v84, types: [com.android.systemui.keyguard.KeyguardFixedRotationMonitor] */
    /* JADX WARN: Type inference failed for: r9v90 */
    /* JADX WARN: Type inference failed for: r9v91 */
    /* JADX WARN: Type inference failed for: r9v93 */
    /* renamed from: accept$com$android$systemui$keyguard$KeyguardViewMediator$12$$InternalSyntheticLambda$1$a0b69a91035649d75be7d9c1b05e8e4316a3b85b56842dba41aec11ee28c01bc$2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void m137xf3e6883c(Object obj) {
        ?? r9;
        boolean z;
        String stringExtra;
        String str;
        ?? r10;
        ?? r2;
        ?? r22;
        ?? r3;
        ?? r32;
        SurfaceControl surfaceControl;
        ?? r92;
        Pair pair;
        ?? r93;
        boolean z2;
        ?? r94;
        final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.f$0;
        Message message = (Message) obj;
        keyguardViewMediatorHelperImpl.getClass();
        int i = message.what;
        if (i == 1101) {
            Object obj2 = message.obj;
            KeyguardViewMediatorHelperImpl.logD("handleRemoteLockRequested");
            if (!(obj2 instanceof RemoteLockInfo) || ((RemoteLockInfo) obj2).lockState) {
                if (keyguardViewMediatorHelperImpl.isShowing()) {
                    keyguardViewMediatorHelperImpl.resetStateLocked();
                } else {
                    keyguardViewMediatorHelperImpl.doKeyguardLocked(null);
                }
                keyguardViewMediatorHelperImpl.f297pm.wakeUp(SystemClock.uptimeMillis());
                return;
            }
            if (keyguardViewMediatorHelperImpl.updateMonitor.getRemoteLockType() == -1 && ((KeyguardViewMediator) keyguardViewMediatorHelperImpl.viewMediatorLazy.get()).isSecure()) {
                keyguardViewMediatorHelperImpl.resetStateLocked();
                return;
            }
            return;
        }
        if (i == 1102) {
            KeyguardViewMediatorHelperImpl.logD("handleLaunchPersoLock");
            keyguardViewMediatorHelperImpl.getHandler().removeMessages(VolteConstants.ErrorCode.CALL_SESSION_TERMINATED);
            synchronized (keyguardViewMediatorHelperImpl.getLock()) {
                if (keyguardViewMediatorHelperImpl.isShowing()) {
                    keyguardViewMediatorHelperImpl.removeMessage(((Number) keyguardViewMediatorHelperImpl.KEYGUARD_DONE$delegate.getValue()).intValue());
                    keyguardViewMediatorHelperImpl.removeMessage(((Number) keyguardViewMediatorHelperImpl.HIDE$delegate.getValue()).intValue());
                    if (((KeyguardViewMediator) keyguardViewMediatorHelperImpl.viewMediatorLazy.get()).isHiding()) {
                        keyguardViewMediatorHelperImpl.getViewMediatorProvider().setHiding.invoke(Boolean.FALSE);
                    }
                    keyguardViewMediatorHelperImpl.resetStateLocked();
                } else {
                    KeyguardViewMediatorHelperImpl.logD("doKeyguardLocked");
                    keyguardViewMediatorHelperImpl.removeMessage(((Number) keyguardViewMediatorHelperImpl.KEYGUARD_DONE_DRAWING$delegate.getValue()).intValue());
                    keyguardViewMediatorHelperImpl.doKeyguardLocked(null);
                }
                Unit unit = Unit.INSTANCE;
            }
            return;
        }
        int i2 = 0;
        if (i == 1201) {
            KeyguardViewMediatorHelperImpl.logD("handleBootCompleted");
            if (LsRune.SUBSCREEN_UI) {
                final SubScreenManager subScreenManager = keyguardViewMediatorHelperImpl.subScreenManager;
                subScreenManager.getClass();
                android.util.Log.d("SubScreenManager", "onBootCompleted() ");
                if (subScreenManager.mSubDisplay == null) {
                    android.util.Log.d("SubScreenManager", "init() ");
                    IKeyguardDrawnCallback[] displays = subScreenManager.mDisplayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
                    if (displays != null) {
                        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("getSubDisplay() : length "), displays.length, "SubScreenManager");
                        int length = displays.length;
                        int i3 = 0;
                        while (true) {
                            if (i3 >= length) {
                                break;
                            }
                            IKeyguardDrawnCallback iKeyguardDrawnCallback = displays[i3];
                            if (iKeyguardDrawnCallback == null) {
                                android.util.Log.i("SubScreenManager", "Do not show SubScreen UI on null display");
                            } else if (iKeyguardDrawnCallback.getDisplayId() == 1) {
                                android.util.Log.i("SubScreenManager", "Show SubScreen UI on this display " + iKeyguardDrawnCallback);
                                r9 = true;
                                if (r9 != true) {
                                    r4 = iKeyguardDrawnCallback;
                                    break;
                                }
                                i3++;
                            } else {
                                android.util.Log.i("SubScreenManager", "Do not show SubScreen UI on this display " + iKeyguardDrawnCallback);
                            }
                            r9 = false;
                            if (r9 != true) {
                            }
                        }
                    }
                    subScreenManager.mSubDisplay = r4;
                    subScreenManager.initWindow();
                    subScreenManager.mDumpManager.registerNsDumpable("SubScreenManager", subScreenManager);
                    if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
                        subScreenManager.mNotifPipeline.addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.subscreen.SubScreenManager.6
                            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
                            public final void onEntryAdded(NotificationEntry notificationEntry) {
                                PluginSubScreen pluginSubScreen = SubScreenManager.this.mSubScreenPlugin;
                                if (pluginSubScreen == null) {
                                    Log.w("SubScreenManager", "onEntryAdded() no plugin");
                                } else {
                                    pluginSubScreen.onEntryAdded(notificationEntry.mKey, notificationEntry.mSbn);
                                }
                            }

                            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
                            public final void onEntryRemoved(NotificationEntry notificationEntry, int i4) {
                                PluginSubScreen pluginSubScreen = SubScreenManager.this.mSubScreenPlugin;
                                if (pluginSubScreen == null) {
                                    Log.w("SubScreenManager", "onEntryRemoved() no plugin");
                                } else {
                                    pluginSubScreen.onEntryRemoved(notificationEntry.mSbn, i4);
                                }
                            }

                            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
                            public final void onEntryUpdated(NotificationEntry notificationEntry, boolean z3) {
                                PluginSubScreen pluginSubScreen = SubScreenManager.this.mSubScreenPlugin;
                                if (pluginSubScreen == null) {
                                    Log.w("SubScreenManager", " onEntryUpdated () no plugin");
                                } else {
                                    pluginSubScreen.onEntryUpdated(notificationEntry.mSbn);
                                }
                            }
                        });
                        subScreenManager.mDeviceStateManager.registerCallback(subScreenManager.mContext.getMainExecutor(), subScreenManager.mDeviceStateCallback);
                    }
                    subScreenManager.mWakefulnessLifeCycle.addObserver(subScreenManager);
                    DisplayLifecycle displayLifecycle = subScreenManager.mDisplayLifecycle;
                    subScreenManager.mIsFolderOpened = displayLifecycle.mIsFolderOpened;
                    displayLifecycle.addObserver(subScreenManager);
                    subScreenManager.mScreenLifecycle.addObserver(subScreenManager);
                    return;
                }
                return;
            }
            return;
        }
        if (i == 1301) {
            try {
                if (keyguardViewMediatorHelperImpl.barService == null) {
                    keyguardViewMediatorHelperImpl.barService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
                }
                IStatusBarService iStatusBarService = keyguardViewMediatorHelperImpl.barService;
                if (iStatusBarService != null) {
                    int i4 = keyguardViewMediatorHelperImpl.disableFlags;
                    CharsKt__CharJVMKt.checkRadix(16);
                    KeyguardViewMediatorHelperImpl.logD("adjustStatusBarLocked - ADJUST_STATUS_BAR : flags=0x".concat(Integer.toString(i4, 16)));
                    iStatusBarService.disable(keyguardViewMediatorHelperImpl.disableFlags, keyguardViewMediatorHelperImpl.token, keyguardViewMediatorHelperImpl.context.getPackageName());
                    return;
                }
                return;
            } catch (RemoteException e) {
                Slog.m144w("adjustStatusBarLocked - ADJUST_STATUS_BAR - disable failed", e);
                return;
            }
        }
        int i5 = 6;
        switch (i) {
            case 1001:
                Bundle data = message.getData();
                final PendingIntent pendingIntent = (PendingIntent) data.getParcelable("PI");
                final Intent intent = (Intent) data.getParcelable("FI");
                boolean booleanExtra = intent != null ? intent.getBooleanExtra("ignoreKeyguardState", false) : false;
                boolean booleanExtra2 = intent != null ? intent.getBooleanExtra("ignoreUnlock", false) : false;
                r4 = intent != null ? intent.getStringExtra("notificationKey") : null;
                boolean booleanExtra3 = intent != null ? intent.getBooleanExtra("runOnCover", false) : false;
                boolean z3 = LsRune.COVER_SUPPORTED;
                KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardViewMediatorHelperImpl.updateMonitor;
                if ((z3 && keyguardUpdateMonitor.isCoverClosed()) || (LsRune.SUBSCREEN_WATCHFACE && !keyguardViewMediatorHelperImpl.foldControllerImpl.isFoldOpened())) {
                    boolean z4 = LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY;
                    if ((z4 || LsRune.SUBSCREEN_WATCHFACE) && booleanExtra3) {
                        keyguardUpdateMonitor.dispatchStartSubscreenBiometric(intent);
                    }
                    if (!z4) {
                        boolean booleanExtra4 = intent != null ? intent.getBooleanExtra("runOnCover", false) : false;
                        boolean booleanExtra5 = intent != null ? intent.getBooleanExtra("bio_extend_duration", false) : false;
                        if (!(intent != null ? intent.getBooleanExtra("showCoverToast", false) : false) && !booleanExtra5 && !booleanExtra4) {
                            r10 = false;
                            break;
                        } else {
                            r10 = true;
                            break;
                        }
                    }
                    PluginAODManager pluginAODManager = (PluginAODManager) keyguardViewMediatorHelperImpl.pluginAODManagerLazy.get();
                    pluginAODManager.getClass();
                    android.util.Log.d("PluginAODManager", "showCoverToast() with FIntent");
                    if (pluginAODManager.mCoverPlugin != null) {
                        pluginAODManager.mCoverPlugin.showCoverToast(pendingIntent, intent.getBooleanExtra("ignoreUnlock", false));
                    }
                    PluginSubScreen pluginSubScreen = pluginAODManager.mSubScreenPlugin;
                    if (pluginSubScreen != null) {
                        pluginSubScreen.requestOpenAppPopup(pendingIntent, intent);
                        return;
                    }
                    return;
                }
                if (booleanExtra2) {
                    KeyguardViewMediatorHelperImpl.logD("handleSetPendingIntentAfterUnlock() ignoreUnlock");
                    keyguardViewMediatorHelperImpl.startSetPendingIntent(pendingIntent, intent);
                    return;
                }
                if (!keyguardViewMediatorHelperImpl.isShowing()) {
                    if (booleanExtra) {
                        keyguardViewMediatorHelperImpl.startSetPendingIntent(pendingIntent, intent);
                        return;
                    } else {
                        if (((Boolean) keyguardViewMediatorHelperImpl.getViewMediatorProvider().isExternallyEnabled.invoke()).booleanValue() || r4 == null) {
                            return;
                        }
                        keyguardViewMediatorHelperImpl.startSetPendingIntent(pendingIntent, intent);
                        return;
                    }
                }
                boolean isSecure = ((KeyguardViewMediator) keyguardViewMediatorHelperImpl.viewMediatorLazy.get()).isSecure();
                boolean booleanExtra6 = intent != null ? intent.getBooleanExtra("afterKeyguardGone", false) : false;
                int currentUser = (r4 == null || (str = (String) CollectionsKt___CollectionsKt.getOrNull(0, StringsKt__StringsKt.split$default(r4, new String[]{"|"}, 0, 6))) == null) ? KeyguardUpdateMonitor.getCurrentUser() : Integer.parseInt(str);
                if (!booleanExtra6 && ((KnoxStateMonitorImpl) keyguardViewMediatorHelperImpl.knoxStateMonitor).isPersona(currentUser) && ((KeyguardViewMediator) keyguardViewMediatorHelperImpl.viewMediatorLazy.get()).isSecure(currentUser)) {
                    booleanExtra6 = true;
                }
                boolean userHasTrust = keyguardUpdateMonitor.getUserHasTrust(((UserTrackerImpl) keyguardViewMediatorHelperImpl.userTracker).getUserId());
                boolean z5 = LsRune.SECURITY_SWIPE_BOUNCER;
                boolean z6 = !z5 || (isSecure && !userHasTrust);
                if (intent != null && (stringExtra = intent.getStringExtra("dismissType")) != null) {
                    int hashCode = stringExtra.hashCode();
                    if (hashCode != -934938715) {
                        if (hashCode != -169343402) {
                            if (hashCode == 514447268 && stringExtra.equals("fingerprinterror")) {
                                keyguardUpdateMonitor.setDismissActionType(KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_FINGERPRINT_ERROR);
                                z6 = true;
                            }
                        } else if (stringExtra.equals("shutdown")) {
                            keyguardUpdateMonitor.setDismissActionType(KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_SHUTDOWN);
                            z6 = true;
                        }
                    } else if (stringExtra.equals("reboot")) {
                        keyguardUpdateMonitor.setDismissActionType(KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_REBOOT);
                        z6 = true;
                    }
                }
                KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_PENDING_INTENT);
                boolean booleanExtra7 = intent != null ? intent.getBooleanExtra("dismissIfInsecure", z6) : false;
                Lazy lazy = keyguardViewMediatorHelperImpl.viewControllerLazy;
                if (!booleanExtra7 && (!isSecure || userHasTrust)) {
                    if (!z5) {
                        z = false;
                        keyguardViewMediatorHelperImpl.goingAwayWithAnimation = intent != null ? intent.getBooleanExtra("withAnimation", true) : true;
                        boolean booleanExtra8 = intent != null ? intent.getBooleanExtra("wakeAndUnlock", false) : false;
                        boolean z7 = keyguardViewMediatorHelperImpl.goingAwayWithAnimation;
                        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("handleSetPendingIntentAfterUnlock() : afterKeyguardGone=", booleanExtra6, " isInstantDismiss=", z, " withAnimation=");
                        m69m.append(z7);
                        m69m.append(" wakeAndUnlock=");
                        m69m.append(booleanExtra8);
                        Log.m140e("KeyguardViewMediator", m69m.toString());
                        ((KeyguardViewController) lazy.get()).dismissWithAction(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$handleSetPendingIntentAfterUnlock$2
                            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                            public final boolean onDismiss() {
                                KeyguardViewMediatorHelperImpl.this.startSetPendingIntent(pendingIntent, intent);
                                return false;
                            }
                        }, null, booleanExtra6, z, booleanExtra8);
                        return;
                    }
                    ((KeyguardViewController) lazy.get()).setShowSwipeBouncer(true);
                }
                z = true;
                keyguardViewMediatorHelperImpl.goingAwayWithAnimation = intent != null ? intent.getBooleanExtra("withAnimation", true) : true;
                if (intent != null) {
                }
                boolean z72 = keyguardViewMediatorHelperImpl.goingAwayWithAnimation;
                StringBuilder m69m2 = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("handleSetPendingIntentAfterUnlock() : afterKeyguardGone=", booleanExtra6, " isInstantDismiss=", z, " withAnimation=");
                m69m2.append(z72);
                m69m2.append(" wakeAndUnlock=");
                m69m2.append(booleanExtra8);
                Log.m140e("KeyguardViewMediator", m69m2.toString());
                ((KeyguardViewController) lazy.get()).dismissWithAction(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$handleSetPendingIntentAfterUnlock$2
                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                    public final boolean onDismiss() {
                        KeyguardViewMediatorHelperImpl.this.startSetPendingIntent(pendingIntent, intent);
                        return false;
                    }
                }, null, booleanExtra6, z, booleanExtra8);
                return;
            case 1002:
                Object obj3 = message.obj;
                r4 = obj3 != null ? (IKeyguardDrawnCallback) obj3 : null;
                if (r4 == null) {
                    if (keyguardViewMediatorHelperImpl.isFastWakeAndUnlockMode()) {
                        KeyguardFastBioUnlockController keyguardFastBioUnlockController = keyguardViewMediatorHelperImpl.fastUnlockController;
                        int mode = keyguardFastBioUnlockController.getMode();
                        if (keyguardFastBioUnlockController.isEnabled() && (KeyguardFastBioUnlockController.MODE_FLAG_FRAME_COMMIT & mode) == 0) {
                            int i6 = KeyguardFastBioUnlockController.MODE_FLAG_FRAME_REQUEST;
                            if ((mode & i6) == i6) {
                                r2 = true;
                                if (r2 == false) {
                                    KeyguardFastBioUnlockController keyguardFastBioUnlockController2 = keyguardViewMediatorHelperImpl.fastUnlockController;
                                    int mode2 = keyguardFastBioUnlockController2.getMode();
                                    if (keyguardFastBioUnlockController2.isEnabled()) {
                                        int i7 = KeyguardFastBioUnlockController.MODE_FLAG_FRAME_COMMIT;
                                        if ((mode2 & i7) == i7) {
                                            r22 = true;
                                            break;
                                        }
                                    }
                                    r22 = false;
                                    break;
                                }
                                KeyguardViewMediatorHelperImpl.logD("handleNotifyScreenTurningOn");
                                keyguardViewMediatorHelperImpl.notifyDrawn();
                                return;
                            }
                        }
                        r2 = false;
                        if (r2 == false) {
                        }
                        KeyguardViewMediatorHelperImpl.logD("handleNotifyScreenTurningOn");
                        keyguardViewMediatorHelperImpl.notifyDrawn();
                        return;
                    }
                    break;
                }
                synchronized (keyguardViewMediatorHelperImpl.getLock()) {
                    boolean hasShowMsg = keyguardViewMediatorHelperImpl.hasShowMsg();
                    boolean z8 = ((BiometricUnlockController) keyguardViewMediatorHelperImpl.biometricUnlockControllerLazy.get()).mMode != 0;
                    long elapsedRealtime = keyguardViewMediatorHelperImpl.lastShowingTime != 0 ? SystemClock.elapsedRealtime() - keyguardViewMediatorHelperImpl.lastShowingTime : 0L;
                    boolean isFastWakeAndUnlockMode = keyguardViewMediatorHelperImpl.fastUnlockController.isFastWakeAndUnlockMode();
                    boolean booleanValue = ((Boolean) keyguardViewMediatorHelperImpl.getViewMediatorProvider().hasPendingLock.invoke()).booleanValue();
                    KeyguardViewMediatorHelperImpl.logD("handleNotifyScreenTurningOn fastWakeUnlockMode=" + isFastWakeAndUnlockMode + ", bioUnlock=" + z8 + " hasShow=" + hasShowMsg + ", pendingLock=" + booleanValue + ", hasCb=" + (r4 != null) + ", interval=" + elapsedRealtime + ", hasOccluded=" + keyguardViewMediatorHelperImpl.hasOccludedMsg$1());
                    if (r4 == null) {
                        return;
                    }
                    if ((keyguardViewMediatorHelperImpl.fastUnlockController.needsBlankScreen || !isFastWakeAndUnlockMode) && (z8 || !(hasShowMsg || booleanValue))) {
                        if (LsRune.KEYGUARD_DELAY_NOTIFY_DRAWN_PREMIUM_WATCH && !keyguardViewMediatorHelperImpl.foldControllerImpl.isFoldOpened()) {
                            if ((keyguardViewMediatorHelperImpl.settingsHelper.mItemLists.get("premium_watch_switch_onoff").getIntValue() == 1) != false && (keyguardViewMediatorHelperImpl.hasOccludedMsg$1() || !((KeyguardViewMediator) keyguardViewMediatorHelperImpl.viewMediatorLazy.get()).isShowingAndNotOccluded())) {
                                KeyguardVisibilityMonitor keyguardVisibilityMonitor = keyguardViewMediatorHelperImpl.keyguardVisibilityMonitor;
                                if ((keyguardVisibilityMonitor.curVisibility == 0 ? 1 : 0) != 0) {
                                    keyguardViewMediatorHelperImpl.drawnCallback = r4;
                                    keyguardVisibilityMonitor.addVisibilityChangedListener(new C1502x3aaf87fe((Function1) keyguardViewMediatorHelperImpl.visibilityListener));
                                    KeyguardViewMediatorHelperImpl.logD("delayed notifyDrawn caused by occluded");
                                    return;
                                }
                            }
                        }
                        if (elapsedRealtime < 200 && !z8) {
                            keyguardViewMediatorHelperImpl.drawnCallback = r4;
                            keyguardViewMediatorHelperImpl.getHandler().post(keyguardViewMediatorHelperImpl.delayedDrawnRunnable);
                            KeyguardViewMediatorHelperImpl.logD("delayed notifyDrawn");
                            return;
                        }
                        keyguardViewMediatorHelperImpl.notifyDrawn(r4);
                    } else {
                        keyguardViewMediatorHelperImpl.drawnCallback = r4;
                    }
                    Unit unit2 = Unit.INSTANCE;
                    return;
                }
            case 1003:
                KeyguardFoldControllerImpl keyguardFoldControllerImpl = keyguardViewMediatorHelperImpl.foldControllerImpl;
                keyguardFoldControllerImpl.getClass();
                boolean z9 = message.arg1 == 1;
                boolean z10 = message.arg2 == 0;
                KeyguardFoldControllerDependency keyguardFoldControllerDependency = keyguardFoldControllerImpl.dependency;
                String str2 = "handleFoldMessage: isOpened=" + z9 + ", showing=" + keyguardFoldControllerImpl.getViewMediator().isShowingAndNotOccluded();
                ((KeyguardFoldControllerDependencyImpl) keyguardFoldControllerDependency).getClass();
                Log.m138d("KeyguardFoldController", str2);
                if (!z10) {
                    ((KeyguardFoldControllerConfigImpl) keyguardFoldControllerImpl.foldConfig).getClass();
                    if (!LsRune.KEYGUARD_SUB_DISPLAY_LOCK) {
                        ((KeyguardFoldControllerConfigImpl) keyguardFoldControllerImpl.foldConfig).getClass();
                        if (LsRune.SUBSCREEN_WATCHFACE) {
                            ((KeyguardViewMediatorHelperImpl) ((KeyguardViewMediatorHelper) keyguardFoldControllerImpl.viewMediatorHelper$delegate.getValue())).getViewMediatorProvider().adjustStatusBarLocked.invoke();
                        }
                    } else if (z9) {
                        KeyguardViewMediator viewMediator = keyguardFoldControllerImpl.getViewMediator();
                        KeyguardFoldControllerDependency keyguardFoldControllerDependency2 = keyguardFoldControllerImpl.dependency;
                        KeyguardUnlockInfo.UnlockTrigger unlockTrigger = KeyguardUnlockInfo.UnlockTrigger.TRIGGER_FOLD_OPENED;
                        ((KeyguardFoldControllerDependencyImpl) keyguardFoldControllerDependency2).getClass();
                        KeyguardUnlockInfo.setUnlockTrigger(unlockTrigger);
                        if (viewMediator.mHelper.hasShowMsg()) {
                            keyguardFoldControllerImpl.setFoldOpenState(1);
                            viewMediator.dismiss(null, null);
                        } else if (viewMediator.isShowingAndNotOccluded()) {
                            ((KeyguardFoldControllerConfigImpl) keyguardFoldControllerImpl.foldConfig).getClass();
                            if (LsRune.SECURITY_SIM_PERM_DISABLED && keyguardFoldControllerImpl.updateMonitor.isIccBlockedPermanently()) {
                                ((KeyguardFoldControllerDependencyImpl) keyguardFoldControllerImpl.dependency).getClass();
                                Log.m138d("KeyguardFoldController", "dismiss failed. Permanent state.");
                            } else {
                                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = (KeyguardViewMediatorHelperImpl) ((KeyguardViewMediatorHelper) keyguardFoldControllerImpl.viewMediatorHelper$delegate.getValue());
                                synchronized (keyguardViewMediatorHelperImpl2.getLock()) {
                                    if (keyguardViewMediatorHelperImpl2.hasOccludedMsg$1()) {
                                        r3 = true == keyguardViewMediatorHelperImpl2.curIsOccluded;
                                    }
                                }
                                if (r3 == true) {
                                    ((KeyguardFoldControllerDependencyImpl) keyguardFoldControllerImpl.dependency).getClass();
                                    Log.m138d("KeyguardFoldController", "will handle occluded");
                                } else {
                                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = (KeyguardViewMediatorHelperImpl) ((KeyguardViewMediatorHelper) keyguardFoldControllerImpl.viewMediatorHelper$delegate.getValue());
                                    keyguardViewMediatorHelperImpl3.getHandler().removeMessages(((Number) keyguardViewMediatorHelperImpl3.RESET$delegate.getValue()).intValue());
                                    keyguardViewMediatorHelperImpl3.getViewMediatorProvider().resetPendingReset.invoke();
                                    boolean z11 = !keyguardFoldControllerImpl.getViewMediator().isSecure();
                                    if (z11) {
                                        KeyguardFoldControllerDependency keyguardFoldControllerDependency3 = keyguardFoldControllerImpl.dependency;
                                        KeyguardSecurityModel.SecurityMode securityMode = KeyguardSecurityModel.SecurityMode.None;
                                        ((KeyguardFoldControllerDependencyImpl) keyguardFoldControllerDependency3).getClass();
                                        KeyguardUnlockInfo.setAuthDetail(securityMode);
                                    }
                                    KeyguardUpdateMonitor keyguardUpdateMonitor2 = keyguardFoldControllerImpl.updateMonitor;
                                    ((KeyguardFoldControllerDependencyImpl) keyguardFoldControllerImpl.dependency).getClass();
                                    boolean userCanSkipBouncer = keyguardUpdateMonitor2.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser());
                                    if (z11 || userCanSkipBouncer) {
                                        keyguardFoldControllerImpl.setFoldOpenState((!keyguardFoldControllerImpl.getViewMediator().getViewMediatorCallback().isScreenOn() || keyguardFoldControllerImpl.wakeReason == 9) ? 3 : 2);
                                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl4 = (KeyguardViewMediatorHelperImpl) ((KeyguardViewMediatorHelper) keyguardFoldControllerImpl.viewMediatorHelper$delegate.getValue());
                                        keyguardViewMediatorHelperImpl4.goingAwayWithAnimation = false;
                                        keyguardViewMediatorHelperImpl4.getViewMediatorProvider().tryKeyguardDone.invoke();
                                    } else {
                                        keyguardFoldControllerImpl.setFoldOpenState(1);
                                        ((KeyguardViewController) keyguardFoldControllerImpl.viewControllerLazy.get()).folderOpenAndDismiss();
                                    }
                                }
                            }
                        } else {
                            viewMediator.maybeHandlePendingLock();
                            if (viewMediator.mHelper.hasShowMsg()) {
                                keyguardFoldControllerImpl.setFoldOpenState(1);
                                viewMediator.dismiss(null, null);
                            }
                        }
                    } else {
                        KeyguardViewMediator viewMediator2 = keyguardFoldControllerImpl.getViewMediator();
                        if (viewMediator2.isShowingAndNotOccluded()) {
                            viewMediator2.getViewMediatorCallback().resetKeyguard();
                        }
                    }
                }
                keyguardFoldControllerImpl.onFoldStateChanged(keyguardFoldControllerImpl.normalRankedStateListeners, z9, z10, ((KeyguardFoldControllerConfigImpl) keyguardFoldControllerImpl.foldConfig).isDebug());
                return;
            case VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI /* 1004 */:
                KeyguardViewMediator.StartKeyguardExitAnimParams startKeyguardExitAnimParams = (KeyguardViewMediator.StartKeyguardExitAnimParams) message.obj;
                final Message obtainMessage = keyguardViewMediatorHelperImpl.getHandler().obtainMessage(((Number) keyguardViewMediatorHelperImpl.START_KEYGUARD_EXIT_ANIM$delegate.getValue()).intValue(), startKeyguardExitAnimParams);
                boolean isKeyguardHiding = keyguardViewMediatorHelperImpl.isKeyguardHiding();
                SettingsHelper settingsHelper = keyguardViewMediatorHelperImpl.settingsHelper;
                if (isKeyguardHiding) {
                    boolean isFastWakeAndUnlockMode2 = keyguardViewMediatorHelperImpl.isFastWakeAndUnlockMode();
                    KeyguardFastBioUnlockController keyguardFastBioUnlockController3 = keyguardViewMediatorHelperImpl.fastUnlockController;
                    if (!isFastWakeAndUnlockMode2 || keyguardFastBioUnlockController3.isInvisibleAfterGoingAwayTransStarted) {
                        if (keyguardFastBioUnlockController3.isFastUnlockMode() || (keyguardViewMediatorHelperImpl.isFastWakeAndUnlockMode() && keyguardFastBioUnlockController3.isInvisibleAfterGoingAwayTransStarted)) {
                            if (!settingsHelper.isEnabledFaceStayOnLock()) {
                                if (!keyguardFastBioUnlockController3.isEnabled() || keyguardFastBioUnlockController3.biometricSourceType != BiometricSourceType.FACE) {
                                    r93 = false;
                                    break;
                                } else {
                                    r93 = true;
                                    break;
                                }
                            }
                            if (!keyguardFastBioUnlockController3.isEnabled() || keyguardFastBioUnlockController3.biometricSourceType != BiometricSourceType.FINGERPRINT) {
                                r92 = false;
                                break;
                            } else {
                                r92 = true;
                                break;
                            }
                        }
                        Boolean bool = Boolean.FALSE;
                        pair = new Pair(bool, bool);
                    } else {
                        if (!keyguardFastBioUnlockController3.needsBlankScreen) {
                            int mode3 = keyguardFastBioUnlockController3.getMode();
                            if (keyguardFastBioUnlockController3.isEnabled()) {
                                int i8 = KeyguardFastBioUnlockController.MODE_FLAG_FRAME_COMMIT;
                                if ((mode3 & i8) == i8) {
                                    r94 = true;
                                    if (r94 == false) {
                                        z2 = true;
                                        pair = new Pair(Boolean.valueOf(z2), Boolean.TRUE);
                                    }
                                }
                            }
                            r94 = false;
                            if (r94 == false) {
                            }
                        }
                        z2 = false;
                        pair = new Pair(Boolean.valueOf(z2), Boolean.TRUE);
                    }
                    boolean booleanValue2 = ((Boolean) pair.component1()).booleanValue();
                    if (((Boolean) pair.component2()).booleanValue()) {
                        IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = startKeyguardExitAnimParams.mFinishedCallback;
                        if (iRemoteAnimationFinishedCallback != null) {
                            try {
                                iRemoteAnimationFinishedCallback.onAnimationFinished();
                            } catch (RemoteException unused) {
                                android.util.Slog.e("KeyguardViewMediator", "RemoteException");
                                KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.ERROR, "RemoteException", null);
                            }
                            startKeyguardExitAnimParams.mFinishedCallback = null;
                        }
                        startKeyguardExitAnimParams.mApps = null;
                    }
                    AODAmbientWallpaperHelper aODAmbientWallpaperHelper = keyguardViewMediatorHelperImpl.aodAmbientWallpaperHelper;
                    if (aODAmbientWallpaperHelper.isAODAmbientWallpaperMode() && keyguardViewMediatorHelperImpl.isFastWakeAndUnlockMode() && keyguardFastBioUnlockController3.isInvisibleAfterGoingAwayTransStarted) {
                        aODAmbientWallpaperHelper.setAODAmbientWallpaperState(true);
                    }
                    if (booleanValue2) {
                        KeyguardViewMediatorHelperImpl.logD("showForegroundImmediatelyIfNeeded returns true");
                    }
                    if (booleanValue2) {
                        Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$handleOverrideStartKeyguardExitAnimation$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                KeyguardViewMediatorHelperImpl.this.getHandler().handleMessage(obtainMessage);
                                return Unit.INSTANCE;
                            }
                        };
                        KeyguardFastBioUnlockController.DelayedActionParams delayedActionParams = keyguardFastBioUnlockController3.delayedActionParams;
                        if (delayedActionParams != null) {
                            Handler handler = delayedActionParams.handler;
                            RunnableC1452xbe71cbf runnableC1452xbe71cbf = delayedActionParams.runnableWrapper;
                            if (handler.hasCallbacks(runnableC1452xbe71cbf)) {
                                handler.removeCallbacks(runnableC1452xbe71cbf);
                            }
                            delayedActionParams.isDiscard = true;
                        }
                        KeyguardFastBioUnlockController.DelayedActionParams delayedActionParams2 = new KeyguardFastBioUnlockController.DelayedActionParams(keyguardFastBioUnlockController3.mainHandler, function0, 50L);
                        delayedActionParams2.start(true);
                        keyguardFastBioUnlockController3.delayedActionParams = delayedActionParams2;
                        return;
                    }
                }
                if (!((KeyguardViewMediator) keyguardViewMediatorHelperImpl.viewMediatorLazy.get()).requestedShowSurfaceBehindKeyguard()) {
                    ScrimState scrimState = ((ScrimController) keyguardViewMediatorHelperImpl.scrimControllerLazy.get()).mState;
                    ScrimState scrimState2 = ScrimState.UNLOCKED;
                    SysuiStatusBarStateController sysuiStatusBarStateController = keyguardViewMediatorHelperImpl.sysuiStatusBarStateController;
                    if (scrimState != scrimState2) {
                        i5 = 1;
                    } else {
                        NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) ((CentralSurfacesImpl) ((CentralSurfaces) keyguardViewMediatorHelperImpl.centralSurfacesLazy.get())).getShadeViewController();
                        if ((notificationPanelViewController.mIsLaunchTransitionRunning || notificationPanelViewController.mIsLaunchTransitionFinished) == true) {
                            i5 = 2;
                        } else if (((StatusBarStateControllerImpl) sysuiStatusBarStateController).mLeaveOpenOnKeyguardHide) {
                            i5 = 3;
                        } else {
                            RemoteAnimationTarget[] remoteAnimationTargetArr = startKeyguardExitAnimParams.mApps;
                            if (remoteAnimationTargetArr != null && remoteAnimationTargetArr.length > 1) {
                                i5 = 4;
                            } else if (keyguardViewMediatorHelperImpl.disableRemoteUnlockAnimation) {
                                i5 = 5;
                            } else if (((keyguardViewMediatorHelperImpl.lastGoingAwayFlags & 2) == 2) == false) {
                                i5 = ((settingsHelper.getTransitionAnimationScale() > 0.0f ? 1 : (settingsHelper.getTransitionAnimationScale() == 0.0f ? 0 : -1)) == 0) != false ? 7 : ((DesktopManagerImpl) keyguardViewMediatorHelperImpl.desktopManager).isStandalone() ? 8 : KeyguardViewMediatorHelperImplKt.DEBUG_DISABLE_REMOTE_UNLOCK_ANIMATION ? 255 : 0;
                            }
                        }
                    }
                    if (i5 != 0) {
                        KeyguardViewMediatorHelperImpl.logD("isDisabledUnlockAnimation why=" + i5);
                        r32 = true;
                    } else {
                        r32 = false;
                    }
                    if (r32 != false) {
                        RemoteAnimationTarget[] remoteAnimationTargetArr2 = startKeyguardExitAnimParams.mApps;
                        if (remoteAnimationTargetArr2 != null) {
                            if (((remoteAnimationTargetArr2.length == 0) ^ true) != false) {
                                boolean z12 = ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mLeaveOpenOnKeyguardHide;
                                Lazy lazy2 = keyguardViewMediatorHelperImpl.surfaceControllerLazy;
                                if (!z12) {
                                    KeyguardSurfaceController.DefaultImpls.setKeyguardSurfaceAppearAmount$default((KeyguardSurfaceController) lazy2.get());
                                }
                                for (RemoteAnimationTarget remoteAnimationTarget : startKeyguardExitAnimParams.mApps) {
                                    if (remoteAnimationTarget != null && (surfaceControl = remoteAnimationTarget.leash) != null) {
                                        KeyguardSurfaceControllerImpl keyguardSurfaceControllerImpl = (KeyguardSurfaceControllerImpl) ((KeyguardSurfaceController) lazy2.get());
                                        keyguardSurfaceControllerImpl.getClass();
                                        if (KeyguardSurfaceControllerImpl.isValid(surfaceControl, 1.0f)) {
                                            ((SyncRtSurfaceTransactionApplier) keyguardSurfaceControllerImpl.surfaceTransactionApplier$delegate.getValue()).scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(surfaceControl).withAlpha(1.0f).build()});
                                        }
                                    }
                                }
                            }
                            startKeyguardExitAnimParams.mApps = null;
                        }
                        IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = startKeyguardExitAnimParams.mFinishedCallback;
                        if (iRemoteAnimationFinishedCallback2 != null) {
                            try {
                                KeyguardViewMediatorHelperImpl.logD("keepOrDisableUnlockAnimation disabled");
                                iRemoteAnimationFinishedCallback2.onAnimationFinished();
                            } catch (RemoteException unused2) {
                            }
                            startKeyguardExitAnimParams.mFinishedCallback = null;
                        }
                    }
                }
                keyguardViewMediatorHelperImpl.f297pm.userActivity(SystemClock.uptimeMillis(), 2, 0);
                if (keyguardViewMediatorHelperImpl.isShowing() || keyguardViewMediatorHelperImpl.hidingByDisabled) {
                    keyguardViewMediatorHelperImpl.getHandler().handleMessage(obtainMessage);
                    return;
                }
                Log.m142w("KeyguardViewMediator", "no need to handle msg: " + obtainMessage.what);
                try {
                    IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback3 = startKeyguardExitAnimParams.mFinishedCallback;
                    if (iRemoteAnimationFinishedCallback3 != null) {
                        iRemoteAnimationFinishedCallback3.onAnimationFinished();
                        return;
                    }
                    return;
                } catch (RemoteException unused3) {
                    return;
                }
            case 1005:
                ((DesktopManagerImpl) keyguardViewMediatorHelperImpl.desktopManager).notifyOccluded(message.arg1 != 0);
                return;
            case 1006:
                if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
                    SubScreenManager subScreenManager2 = keyguardViewMediatorHelperImpl.subScreenManager;
                    boolean z13 = message.arg1 != 0;
                    subScreenManager2.getClass();
                    StringBuilder sb = new StringBuilder("setCoverOccluded ");
                    sb.append(z13);
                    sb.append(", request Cover Bouncer : ");
                    ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, subScreenManager2.mRequestBouncerForLauncherTask, "SubScreenManager");
                    List list = subScreenManager2.mOccludedApps;
                    if (!z13) {
                        ArrayList arrayList = (ArrayList) list;
                        if (arrayList.isEmpty()) {
                            android.util.Log.w("SubScreenManager", "no prev occluded app");
                        } else {
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                AbstractC0000x2c234b15.m3m("prev occluded app: ", (String) it.next(), "SubScreenManager");
                            }
                        }
                        arrayList.contains("com.android.systemui.subscreen.SubHomeActivity");
                        arrayList.contains("com.android.systemui.qp.flashlight.SubroomFlashLightSettingsActivity");
                        arrayList.clear();
                        if (!((KeyguardStateControllerImpl) subScreenManager2.mKeyguardStateController).mShowing || subScreenManager2.mRequestBouncerForLauncherTask) {
                            return;
                        }
                        subScreenManager2.startSubHomeActivity();
                        return;
                    }
                    for (ActivityManager.RunningTaskInfo runningTaskInfo : subScreenManager2.mActivityManager.getRunningTasks(1)) {
                        StringBuilder sb2 = new StringBuilder("Current running task: ");
                        sb2.append(runningTaskInfo.topActivity);
                        sb2.append(", ");
                        NotificationListener$$ExternalSyntheticOutline0.m123m(sb2, runningTaskInfo.isRunning, "SubScreenManager");
                        ComponentName componentName = runningTaskInfo.topActivity;
                        if (componentName != null && runningTaskInfo.isRunning) {
                            i2++;
                            String className = componentName.getClassName();
                            ArrayList arrayList2 = (ArrayList) list;
                            if (!arrayList2.contains(className)) {
                                arrayList2.add(className);
                            }
                        }
                    }
                    if (i2 == 0) {
                        android.util.Log.w("SubScreenManager", "no running task");
                        return;
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:95:0x021c, code lost:
    
        r4 = false;
     */
    /* JADX WARN: Removed duplicated region for block: B:97:0x021f  */
    @Override // java.util.function.Consumer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void accept(Object obj) {
        boolean z;
        boolean z2 = true;
        switch (this.$r8$classId) {
            case 0:
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.f$0;
                int intValue = ((Integer) obj).intValue();
                keyguardViewMediatorHelperImpl.enableLooperLogController(4, 3000L);
                keyguardViewMediatorHelperImpl.lastWakeReason = intValue;
                if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK) {
                    keyguardViewMediatorHelperImpl.foldControllerImpl.wakeReason = intValue;
                }
                KeyguardDumpLog.state$default(KeyguardDumpLog.INSTANCE, 3, false, false, false, 1, intValue, 14);
                return;
            case 1:
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = this.f$0;
                int intValue2 = ((Integer) obj).intValue();
                keyguardViewMediatorHelperImpl2.enableLooperLogController(5, 3000L);
                keyguardViewMediatorHelperImpl2.lastSleepReason = intValue2;
                if (LsRune.AOD_FULLSCREEN && keyguardViewMediatorHelperImpl2.aodAmbientWallpaperHelper.isAODFullScreenMode()) {
                    keyguardViewMediatorHelperImpl2.getHandler().post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$onStartedGoingToSleep$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = KeyguardViewMediatorHelperImpl.this.unlockedScreenOffAnimationHelper;
                            boolean z3 = false;
                            if (secUnlockedScreenOffAnimationHelper.statusBarStateControllerImpl.mState == 0) {
                                KeyguardVisibilityMonitor keyguardVisibilityMonitor = secUnlockedScreenOffAnimationHelper.keyguardVisibilityMonitor;
                                if ((keyguardVisibilityMonitor.curVisibility == 0) && keyguardVisibilityMonitor.panelState == 2) {
                                    z3 = true;
                                }
                            }
                            secUnlockedScreenOffAnimationHelper.isPanelOpenedOnGoingToSleep = z3;
                        }
                    });
                    return;
                }
                return;
            case 2:
                this.f$0.doKeyguardPendingIntent = (PendingIntent) obj;
                return;
            case 3:
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = this.f$0;
                int intValue3 = ((Integer) obj).intValue();
                if (keyguardViewMediatorHelperImpl3.extraUserPresentIntent == null) {
                    keyguardViewMediatorHelperImpl3.extraUserPresentIntent = (Intent) ((Intent) keyguardViewMediatorHelperImpl3.getViewMediatorProvider().userPresentIntent.invoke()).clone();
                }
                Intent intent = keyguardViewMediatorHelperImpl3.extraUserPresentIntent;
                if (intent != null) {
                    boolean z3 = LsRune.KEYGUARD_EXTRA_USER_PRESENT;
                    Context context = keyguardViewMediatorHelperImpl3.context;
                    if (z3) {
                        intent.setPackage("com.verizon.mips.services");
                        context.sendBroadcastAsUser(intent, UserHandle.of(intValue3));
                    }
                    intent.setPackage("com.sec.android.daemonapp");
                    intent.addFlags(32);
                    context.sendBroadcastAsUser(intent, UserHandle.of(intValue3));
                    return;
                }
                return;
            case 4:
                this.f$0.cancelLockWhenCoverIsOpened(((Boolean) obj).booleanValue());
                return;
            case 5:
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl4 = this.f$0;
                Message message = (Message) obj;
                int i = keyguardViewMediatorHelperImpl4.handleMsgLogKey;
                if (i != -1) {
                    final int i2 = message.what;
                    LogUtil.internalEndTime(i, 0, new LongConsumer() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$endHandleMsgTime$1
                        @Override // java.util.function.LongConsumer
                        public final void accept(long j) {
                            if (j >= 30) {
                                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = KeyguardViewMediatorHelperImpl.this;
                                String str = "handleMessage " + i2 + " / elapsed time: " + j + "ms";
                                keyguardViewMediatorHelperImpl5.getClass();
                                KeyguardViewMediatorHelperImpl.logD(str);
                            }
                        }
                    }, null, null, new Object[0]);
                    keyguardViewMediatorHelperImpl4.handleMsgLogKey = -1;
                }
                int i3 = message.what;
                keyguardViewMediatorHelperImpl4.handleMsgLogKey = LogUtil.startTime(-1);
                KeyguardViewMediatorHelperImpl.logD("handleMessage " + i3);
                int i4 = message.what;
                if (i4 == 1004) {
                    if (keyguardViewMediatorHelperImpl4.getHandler().hasMessages(((Number) keyguardViewMediatorHelperImpl4.CANCEL_KEYGUARD_EXIT_ANIM$delegate.getValue()).intValue())) {
                        keyguardViewMediatorHelperImpl4.getHandler().removeMessages(((Number) keyguardViewMediatorHelperImpl4.CANCEL_KEYGUARD_EXIT_ANIM$delegate.getValue()).intValue());
                        if (keyguardViewMediatorHelperImpl4.isSecure()) {
                            keyguardViewMediatorHelperImpl4.updateMonitor.getUserCanSkipBouncer(((UserTrackerImpl) keyguardViewMediatorHelperImpl4.userTracker).getUserId());
                        }
                        keyguardViewMediatorHelperImpl4.disableRemoteUnlockAnimation = true;
                        KeyguardViewMediatorHelperImpl.logD("cancel CANCEL_KEYGUARD_EXIT_ANIM");
                        return;
                    }
                    return;
                }
                if (!(i4 == keyguardViewMediatorHelperImpl4.getSHOW() || i4 == ((Number) keyguardViewMediatorHelperImpl4.NOTIFY_STARTED_GOING_TO_SLEEP$delegate.getValue()).intValue())) {
                    if ((i4 == ((Number) keyguardViewMediatorHelperImpl4.CANCEL_KEYGUARD_EXIT_ANIM$delegate.getValue()).intValue() || i4 == ((Number) keyguardViewMediatorHelperImpl4.START_KEYGUARD_EXIT_ANIM$delegate.getValue()).intValue()) || i4 == keyguardViewMediatorHelperImpl4.getSET_OCCLUDED()) {
                        KeyguardSysDumpTrigger keyguardSysDumpTrigger = keyguardViewMediatorHelperImpl4.sysDumpTrigger;
                        synchronized (keyguardSysDumpTrigger) {
                            ExecutorImpl.ExecutionToken executionToken = keyguardSysDumpTrigger.cancelExecToken;
                            if (executionToken != null) {
                                executionToken.run();
                                keyguardSysDumpTrigger.cancelExecToken = null;
                                android.util.Log.d("KeyguardSysDumpTrigger", "cancel");
                            }
                        }
                        return;
                    }
                    return;
                }
                Lazy lazy = keyguardViewMediatorHelperImpl4.surfaceControllerLazy;
                SyncRtSurfaceTransactionApplier.SurfaceParams surfaceParams = ((KeyguardSurfaceControllerImpl) ((KeyguardSurfaceController) lazy.get())).lastKeyguardSurfaceParams;
                if (surfaceParams != null) {
                    SurfaceControl surfaceControl = surfaceParams.surface;
                    z2 = true ^ (surfaceControl != null ? surfaceControl.isValid() : false);
                }
                if (!z2) {
                    ((KeyguardSurfaceControllerImpl) ((KeyguardSurfaceController) lazy.get())).restoreKeyguardSurface();
                }
                KeyguardSysDumpTrigger keyguardSysDumpTrigger2 = keyguardViewMediatorHelperImpl4.sysDumpTrigger;
                synchronized (keyguardSysDumpTrigger2) {
                    ExecutorImpl.ExecutionToken executionToken2 = keyguardSysDumpTrigger2.cancelExecToken;
                    if (executionToken2 != null) {
                        executionToken2.run();
                        keyguardSysDumpTrigger2.cancelExecToken = null;
                        android.util.Log.d("KeyguardSysDumpTrigger", "cancel");
                    }
                }
                return;
            case 6:
                m137xf3e6883c(obj);
                return;
            case 7:
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = this.f$0;
                Message message2 = (Message) obj;
                keyguardViewMediatorHelperImpl5.getClass();
                int i5 = message2.what;
                if (i5 == ((Number) keyguardViewMediatorHelperImpl5.SYSTEM_READY$delegate.getValue()).intValue()) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = keyguardViewMediatorHelperImpl5.updateCallback;
                    KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardViewMediatorHelperImpl5.updateMonitor;
                    keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
                    boolean z4 = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
                    final KeyguardFoldControllerImpl keyguardFoldControllerImpl = keyguardViewMediatorHelperImpl5.foldControllerImpl;
                    if (z4 || LsRune.KEYGUARD_SUB_DISPLAY_COVER) {
                        keyguardFoldControllerImpl.getClass();
                        Context context2 = keyguardFoldControllerImpl.context;
                        ((DeviceStateManager) context2.getSystemService(DeviceStateManager.class)).registerCallback(Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.android.systemui.keyguard.KeyguardFoldControllerImpl$init$1
                            @Override // java.util.concurrent.ThreadFactory
                            public final Thread newThread(Runnable runnable) {
                                return new Thread(runnable, "KeyguardFoldControllerImpl");
                            }
                        }), new DeviceStateManager.FoldStateListener(context2, new Consumer() { // from class: com.android.systemui.keyguard.KeyguardFoldControllerImpl$init$2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                Boolean bool = (Boolean) obj2;
                                if (bool != null) {
                                    KeyguardFoldControllerImpl.this.changeFoldState(bool.booleanValue());
                                }
                            }
                        }));
                    }
                    if (z4) {
                        keyguardFoldControllerImpl.addCallback(new KeyguardFoldController.StateListener() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$postHandleSystemReady$1
                            @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
                            public final void onFoldStateChanged(boolean z5) {
                                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl6 = KeyguardViewMediatorHelperImpl.this;
                                keyguardViewMediatorHelperImpl6.getHandler().post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$postHandleSystemReady$1$onFoldStateChanged$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        if (((KeyguardViewMediator) KeyguardViewMediatorHelperImpl.this.viewMediatorLazy.get()).isShowingAndNotOccluded()) {
                                            KeyguardViewMediatorHelperImpl.this.updateRefreshRate();
                                        }
                                    }
                                });
                            }
                        }, 6, true);
                    }
                    ((KnoxStateMonitorImpl) keyguardViewMediatorHelperImpl5.knoxStateMonitor).registerCallback(keyguardViewMediatorHelperImpl5.knoxStateCallback);
                    try {
                        if (keyguardViewMediatorHelperImpl5.lockSettingsService == null) {
                            keyguardViewMediatorHelperImpl5.lockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
                        }
                        ILockSettings iLockSettings = keyguardViewMediatorHelperImpl5.lockSettingsService;
                        if (iLockSettings != null) {
                            iLockSettings.registerRemoteLockCallback(4, keyguardViewMediatorHelperImpl5.remoteLockMonitorCallback);
                        }
                    } catch (RemoteException e) {
                        android.util.Log.d("KeyguardViewMediator", "RemoteLockMonitorCallback regi Failed!", e);
                        KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.DEBUG, "RemoteLockMonitorCallback regi Failed!", e);
                    }
                    PickupController pickupController = keyguardViewMediatorHelperImpl5.pickupController;
                    PickupController$baseSensorListener$1 pickupController$baseSensorListener$1 = pickupController.baseSensorListener;
                    if (!pickupController.pickupListener.contains(pickupController$baseSensorListener$1)) {
                        pickupController.pickupListener.add(pickupController$baseSensorListener$1);
                    }
                    KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m70m("register listener caller=", Debug.getCallers(2), "PickupController");
                    if (LsRune.KEYGUARD_FBE) {
                        Log.m141i("KeyguardViewMediator", "postHandleSystemReady(). check FBE");
                        keyguardUpdateMonitor.updateUserUnlockNotification(((UserTrackerImpl) keyguardViewMediatorHelperImpl5.userTracker).getUserId());
                    }
                } else if (i5 == keyguardViewMediatorHelperImpl5.getSHOW()) {
                    KeyguardViewMediatorHelperImpl$delayedDrawnRunnable$1 keyguardViewMediatorHelperImpl$delayedDrawnRunnable$1 = keyguardViewMediatorHelperImpl5.delayedDrawnRunnable;
                    Handler handler = keyguardViewMediatorHelperImpl5.getHandler();
                    if (handler.hasCallbacks(keyguardViewMediatorHelperImpl$delayedDrawnRunnable$1)) {
                        handler.removeCallbacks(keyguardViewMediatorHelperImpl$delayedDrawnRunnable$1);
                    }
                    synchronized (keyguardViewMediatorHelperImpl5.getLock()) {
                        IKeyguardDrawnCallback iKeyguardDrawnCallback = keyguardViewMediatorHelperImpl5.drawnCallback;
                        if (iKeyguardDrawnCallback != null) {
                            if (((Number) keyguardViewMediatorHelperImpl5.getViewMediatorProvider().getDelayedShowingSequence.invoke()).intValue() >= 2 && (!LsRune.KEYGUARD_SUB_DISPLAY_LOCK || keyguardViewMediatorHelperImpl5.lastSleepReason != 4 || !keyguardViewMediatorHelperImpl5.foldControllerImpl.isFoldOpened())) {
                                keyguardViewMediatorHelperImpl5.getHandler().post(keyguardViewMediatorHelperImpl5.delayedDrawnRunnable);
                            }
                            keyguardViewMediatorHelperImpl5.notifyDrawn(iKeyguardDrawnCallback);
                            keyguardViewMediatorHelperImpl5.drawnCallback = null;
                            Unit unit = Unit.INSTANCE;
                        }
                    }
                    if (((KeyguardViewMediator) keyguardViewMediatorHelperImpl5.viewMediatorLazy.get()).isShowingAndNotOccluded()) {
                        keyguardViewMediatorHelperImpl5.lastShowingTime = SystemClock.elapsedRealtime();
                    }
                    if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK) {
                        keyguardViewMediatorHelperImpl5.foldControllerImpl.resetFoldOpenState(true);
                    }
                    if (keyguardViewMediatorHelperImpl5.isShowing()) {
                        ((DesktopManagerImpl) keyguardViewMediatorHelperImpl5.desktopManager).notifyShowKeyguard();
                    }
                    keyguardViewMediatorHelperImpl5.disableRemoteUnlockAnimation = false;
                    keyguardViewMediatorHelperImpl5.fixedRotationMonitor.cancel();
                } else if (i5 == ((Number) keyguardViewMediatorHelperImpl5.NOTIFY_STARTED_WAKING_UP$delegate.getValue()).intValue()) {
                    if (Rune.SYSUI_BINDER_CALL_MONITOR) {
                        BinderCallMonitorImpl binderCallMonitorImpl = (BinderCallMonitorImpl) keyguardViewMediatorHelperImpl5.binderCallMonitor;
                        binderCallMonitorImpl.getClass();
                        binderCallMonitorImpl.startMonitoring(1, BinderCallMonitorConstants.MAX_DURATION / 1000000, 3000L);
                    }
                    int i6 = keyguardViewMediatorHelperImpl5.lastWakeReason;
                    Lazy lazy2 = keyguardViewMediatorHelperImpl5.viewMediatorLazy;
                    if (i6 != 10) {
                        ((KeyguardViewMediator) lazy2.get()).setDozing(false);
                    }
                    boolean isShowing = keyguardViewMediatorHelperImpl5.isShowing();
                    boolean isHiding = ((KeyguardViewMediator) lazy2.get()).isHiding();
                    boolean isSecure = ((KeyguardViewMediator) lazy2.get()).isSecure();
                    boolean z5 = LsRune.COVER_SUPPORTED;
                    KeyguardUpdateMonitor keyguardUpdateMonitor2 = keyguardViewMediatorHelperImpl5.updateMonitor;
                    if (z5) {
                        CoverState coverState = keyguardUpdateMonitor2.getCoverState();
                        int i7 = keyguardViewMediatorHelperImpl5.switchingUserId;
                        if (keyguardViewMediatorHelperImpl5.lastWakeReason == 103 && isShowing && !isHiding && coverState != null && coverState.attached && coverState.getSwitchState() && keyguardViewMediatorHelperImpl5.settingsHelper.isAutomaticUnlockEnabled()) {
                            if (i7 != -1) {
                                if (z2) {
                                    keyguardViewMediatorHelperImpl5.getViewMediatorProvider().handleHide.invoke();
                                }
                            } else if (z2) {
                            }
                        }
                    }
                    if (!isShowing && !keyguardViewMediatorHelperImpl5.hasShowMsg()) {
                        keyguardUpdateMonitor2.requestSessionClose();
                    } else if (keyguardUpdateMonitor2.isFingerprintOptionEnabled()) {
                        keyguardUpdateMonitor2.updateFingerprintListeningState(2);
                    }
                    keyguardViewMediatorHelperImpl5.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$postHandleNotifyStartedWakingUp$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            String str;
                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl6 = KeyguardViewMediatorHelperImpl.this;
                            int i8 = keyguardViewMediatorHelperImpl6.lastWakeReason;
                            KeyguardViewMediatorHelperImpl.logD("updateSALogging " + i8);
                            String str2 = i8 != 1 ? i8 != 4 ? i8 != 7 ? (i8 == 9 || i8 == 103) ? "4" : i8 != 112 ? i8 != 113 ? DATA.DM_FIELD_INDEX.LBO_PCSCF_ADDRESS_TYPE : DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE_WB : "2" : DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE : DATA.DM_FIELD_INDEX.PUBLIC_USER_ID : "1";
                            String str3 = "1062";
                            if (!LsRune.SUBSCREEN_UI || keyguardViewMediatorHelperImpl6.foldControllerImpl.isFoldOpened()) {
                                str = DATA.DM_FIELD_INDEX.UT_APN_NAME;
                            } else if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
                                str = "101_S";
                            } else {
                                str = "500";
                                str3 = "5001";
                            }
                            SystemUIAnalytics.sendEventLog(str, str3, str2);
                        }
                    });
                } else if (i5 == ((Number) keyguardViewMediatorHelperImpl5.NOTIFY_STARTED_GOING_TO_SLEEP$delegate.getValue()).intValue()) {
                    synchronized (keyguardViewMediatorHelperImpl5.getLock()) {
                        z = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
                        if ((!z || !keyguardViewMediatorHelperImpl5.getHandler().hasMessages(((Number) keyguardViewMediatorHelperImpl5.NOTIFY_STARTED_WAKING_UP$delegate.getValue()).intValue())) && keyguardViewMediatorHelperImpl5.isKeyguardHiding() && !keyguardViewMediatorHelperImpl5.hidingByDisabled) {
                            KeyguardViewMediatorHelperImpl.logD("change mHiding = false");
                            keyguardViewMediatorHelperImpl5.getViewMediatorProvider().setHiding.invoke(Boolean.FALSE);
                        }
                        Unit unit2 = Unit.INSTANCE;
                    }
                    keyguardViewMediatorHelperImpl5.fastUnlockController.reset();
                    if (z) {
                        keyguardViewMediatorHelperImpl5.foldControllerImpl.resetFoldOpenState(true);
                        if (keyguardViewMediatorHelperImpl5.lastSleepReason == 4 && !keyguardViewMediatorHelperImpl5.foldControllerImpl.isFoldOpened()) {
                            ((SecNotificationShadeWindowControllerHelperImpl) ((SecNotificationShadeWindowControllerHelper) keyguardViewMediatorHelperImpl5.shadeWindowControllerHelper$delegate.getValue())).resetForceInvisible(false);
                        }
                    }
                    if (!keyguardViewMediatorHelperImpl5.isAODShowStateCbRegistered) {
                        keyguardViewMediatorHelperImpl5.settingsHelper.registerCallback(keyguardViewMediatorHelperImpl5.aodShowStateCallback, Settings.System.getUriFor("aod_show_state"));
                    }
                    keyguardViewMediatorHelperImpl5.isAODShowStateCbRegistered = true;
                    keyguardViewMediatorHelperImpl5.disableRemoteUnlockAnimation = false;
                    keyguardViewMediatorHelperImpl5.fixedRotationMonitor.cancel();
                } else if (i5 == ((Number) keyguardViewMediatorHelperImpl5.KEYGUARD_DONE_PENDING_TIMEOUT$delegate.getValue()).intValue()) {
                    boolean booleanValue = ((Boolean) keyguardViewMediatorHelperImpl5.getViewMediatorProvider().isKeyguardDonePending.invoke()).booleanValue();
                    KeyguardViewMediatorHelperImpl.logD("handleKeyguardDonePendingTimeout donePending=" + booleanValue);
                    if (booleanValue) {
                        ((KeyguardViewMediator) keyguardViewMediatorHelperImpl5.viewMediatorLazy.get()).getViewMediatorCallback().readyForKeyguardDone();
                    }
                }
                final int i8 = message2.what;
                LogUtil.internalEndTime(keyguardViewMediatorHelperImpl5.handleMsgLogKey, 0, new LongConsumer() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$endHandleMsgTime$1
                    @Override // java.util.function.LongConsumer
                    public final void accept(long j) {
                        if (j >= 30) {
                            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl52 = KeyguardViewMediatorHelperImpl.this;
                            String str = "handleMessage " + i8 + " / elapsed time: " + j + "ms";
                            keyguardViewMediatorHelperImpl52.getClass();
                            KeyguardViewMediatorHelperImpl.logD(str);
                        }
                    }
                }, null, null, new Object[0]);
                keyguardViewMediatorHelperImpl5.handleMsgLogKey = -1;
                return;
            case 8:
                this.f$0.removeMessage(((Integer) obj).intValue());
                return;
            case 9:
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl6 = this.f$0;
                ((Integer) obj).intValue();
                keyguardViewMediatorHelperImpl6.switchingUserId = -1;
                SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = (SecNotificationShadeWindowControllerHelperImpl) ((SecNotificationShadeWindowControllerHelper) keyguardViewMediatorHelperImpl6.shadeWindowControllerHelper$delegate.getValue());
                secNotificationShadeWindowControllerHelperImpl.isKeyguardScreenRotation = secNotificationShadeWindowControllerHelperImpl.isLockScreenRotationAllowed();
                return;
            case 10:
                this.f$0.switchingUserId = ((Integer) obj).intValue();
                return;
            default:
                this.f$0.fastUnlockController.logLapTime((String) obj, new Object[0]);
                return;
        }
    }
}
