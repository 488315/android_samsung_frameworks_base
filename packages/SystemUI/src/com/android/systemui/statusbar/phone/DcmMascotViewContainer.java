package com.android.systemui.statusbar.phone;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.nttdocomo.android.screenlockservice.IScreenLockService;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DcmMascotViewContainer extends LinearLayout {
    public static final String DCM_SCREEN_LOCK_SERVICE_ACTION;
    public static final boolean DEBUG;
    public static final String[] MASCOT_ACTION;
    public final String[] DCM_LAUNCHER;
    public final ActivityStarter activityStart;
    public final Executor bgExecutor;
    public final BlockingDeque blockingQueue;
    public final BroadcastDispatcher broadcastDispatcher;
    public final DcmMascotViewContainer$broadcastReceiver$1 broadcastReceiver;
    public Runnable cancelUpdateRunnable;
    public NotificationPanelViewController.AnonymousClass9 injector;
    public boolean isBootCompleted;
    public boolean isMascotAppRunning;
    public boolean isWaitingForBootComplete;
    public final NotificationLockscreenUserManager lockscreenUserManager;
    public final DelayableExecutor mainExecutor;
    public int mascotBottomMarin;
    public int mascotHeight;
    public int mascotTopMarin;
    public final PackageManager pm;
    public RemoteViews remoteViews;
    public boolean sIsDcmLauncher;
    public boolean sUseCachedIsDcmLauncher;
    public final StatusBarStateController sbStateController;
    public final DcmMascotViewContainer$serviceConnection$1 serviceConnection;
    public final KeyguardUpdateMonitor updateMonitor;
    public final KeyguardUpdateMonitorCallback updateMonitorCallback;
    public final DcmMascotViewContainer$updateRunnable$1 updateRunnable;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        DEBUG = DeviceType.getDebugLevel() == DeviceType.DEBUG_LEVEL_MID || !DeviceType.isShipBuild();
        DCM_SCREEN_LOCK_SERVICE_ACTION = IScreenLockService.class.getName();
        MASCOT_ACTION = new String[]{null, "LOCK_CLICK_MASCOT", "LOCK_CLICK_POPUP", "ACTION_UNLOCK", "LOCK_CLICK_POPUP"};
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.phone.DcmMascotViewContainer$serviceConnection$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.phone.DcmMascotViewContainer$updateRunnable$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.phone.DcmMascotViewContainer$broadcastReceiver$1] */
    public DcmMascotViewContainer(Context context, DelayableExecutor delayableExecutor, Executor executor, BroadcastDispatcher broadcastDispatcher, StatusBarStateController statusBarStateController, NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardUpdateMonitor keyguardUpdateMonitor, PackageManager packageManager, ActivityStarter activityStarter) {
        super(context);
        this.mainExecutor = delayableExecutor;
        this.bgExecutor = executor;
        this.broadcastDispatcher = broadcastDispatcher;
        this.sbStateController = statusBarStateController;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.updateMonitor = keyguardUpdateMonitor;
        this.pm = packageManager;
        this.activityStart = activityStarter;
        this.blockingQueue = new LinkedBlockingDeque(1);
        this.serviceConnection = new ServiceConnection() { // from class: com.android.systemui.statusbar.phone.DcmMascotViewContainer$serviceConnection$1
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                DcmMascotViewContainer dcmMascotViewContainer = DcmMascotViewContainer.this;
                boolean z = DcmMascotViewContainer.DEBUG;
                dcmMascotViewContainer.getClass();
                DcmMascotViewContainer.log("onServiceConnected");
                try {
                    BlockingDeque blockingDeque = DcmMascotViewContainer.this.blockingQueue;
                    int i = IScreenLockService.Stub.$r8$clinit;
                    IInterface queryLocalInterface = iBinder.queryLocalInterface("com.nttdocomo.android.screenlockservice.IScreenLockService");
                    ((LinkedBlockingDeque) blockingDeque).put((queryLocalInterface == null || !(queryLocalInterface instanceof IScreenLockService)) ? new IScreenLockService.Stub.Proxy(iBinder) : (IScreenLockService) queryLocalInterface);
                } catch (InterruptedException e) {
                    DcmMascotViewContainer dcmMascotViewContainer2 = DcmMascotViewContainer.this;
                    String str = "onServiceConnected exception " + e.getMessage();
                    dcmMascotViewContainer2.getClass();
                    DcmMascotViewContainer.log(str);
                }
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                DcmMascotViewContainer dcmMascotViewContainer = DcmMascotViewContainer.this;
                boolean z = DcmMascotViewContainer.DEBUG;
                dcmMascotViewContainer.getClass();
                DcmMascotViewContainer.log("onServiceDisconnected");
            }
        };
        this.updateRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.DcmMascotViewContainer$updateRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                DcmMascotViewContainer dcmMascotViewContainer = DcmMascotViewContainer.this;
                dcmMascotViewContainer.cancelUpdateRunnable = null;
                dcmMascotViewContainer.removeAllViews();
                if (!dcmMascotViewContainer.isMascotEnabled()) {
                    dcmMascotViewContainer.setMascotViewVisible(8);
                    NotificationPanelViewController.AnonymousClass9 anonymousClass9 = dcmMascotViewContainer.injector;
                    NotificationPanelViewController.this.positionClockAndNotifications(false);
                    return;
                }
                RemoteViews remoteViews = dcmMascotViewContainer.remoteViews;
                if (remoteViews != null) {
                    Context context2 = dcmMascotViewContainer.getContext();
                    NotificationPanelViewController.AnonymousClass9 anonymousClass92 = dcmMascotViewContainer.injector;
                    if (anonymousClass92 == null) {
                        anonymousClass92 = null;
                    }
                    dcmMascotViewContainer.addView(remoteViews.apply(context2, NotificationPanelViewController.this.mNotificationContainerParent));
                    if (dcmMascotViewContainer.sbStateController.getState() == 1) {
                        NotificationPanelViewController.AnonymousClass9 anonymousClass93 = dcmMascotViewContainer.injector;
                        if (anonymousClass93 == null) {
                            anonymousClass93 = null;
                        }
                        dcmMascotViewContainer.setMascotViewVisible(NotificationPanelViewController.this.mDozing ? 8 : 0);
                        NotificationPanelViewController.AnonymousClass9 anonymousClass94 = dcmMascotViewContainer.injector;
                        NotificationPanelViewController.this.positionClockAndNotifications(false);
                    }
                }
            }
        };
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.DcmMascotViewContainer$broadcastReceiver$1
            /* JADX WARN: Removed duplicated region for block: B:39:0x00ce  */
            /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onReceive(android.content.Context r5, android.content.Intent r6) {
                /*
                    r4 = this;
                    java.lang.String r5 = r6.getAction()
                    com.android.systemui.statusbar.phone.DcmMascotViewContainer r0 = com.android.systemui.statusbar.phone.DcmMascotViewContainer.this
                    java.lang.String r1 = "onReceive "
                    java.lang.String r1 = androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(r1, r5)
                    boolean r2 = com.android.systemui.statusbar.phone.DcmMascotViewContainer.DEBUG
                    r0.getClass()
                    com.android.systemui.statusbar.phone.DcmMascotViewContainer.log(r1)
                    if (r5 != 0) goto L17
                    return
                L17:
                    int r0 = r5.hashCode()
                    r1 = 0
                    r2 = 1
                    java.lang.String r3 = "com.android.internal.policy.impl.CARRIERMAIL_COUNT_UPDATE"
                    switch(r0) {
                        case -1371817472: goto L9c;
                        case -322599088: goto L6e;
                        case 798292259: goto L51;
                        case 891285360: goto L2c;
                        case 1330693824: goto L24;
                        default: goto L22;
                    }
                L22:
                    goto Lee
                L24:
                    boolean r6 = r5.equals(r3)
                    if (r6 != 0) goto L36
                    goto Lee
                L2c:
                    java.lang.String r6 = "jp.co.nttdocomo.carriermail.APP_LINK_RECEIVED_MESSAGE"
                    boolean r6 = r5.equals(r6)
                    if (r6 != 0) goto L36
                    goto Lee
                L36:
                    com.android.systemui.statusbar.phone.DcmMascotViewContainer r6 = com.android.systemui.statusbar.phone.DcmMascotViewContainer.this
                    java.util.concurrent.Executor r0 = r6.bgExecutor
                    com.android.systemui.statusbar.phone.DcmMascotViewContainer$sendUnreadCountBroadcast$1 r1 = new com.android.systemui.statusbar.phone.DcmMascotViewContainer$sendUnreadCountBroadcast$1
                    r1.<init>(r6)
                    r0.execute(r1)
                    boolean r5 = r3.equals(r5)
                    if (r5 == 0) goto Lee
                    com.android.systemui.statusbar.phone.DcmMascotViewContainer r4 = com.android.systemui.statusbar.phone.DcmMascotViewContainer.this
                    android.widget.RemoteViews r5 = r4.remoteViews
                    r4.setMascotRemoteViews(r5)
                    goto Lee
                L51:
                    java.lang.String r6 = "android.intent.action.BOOT_COMPLETED"
                    boolean r5 = r5.equals(r6)
                    if (r5 != 0) goto L5b
                    goto Lee
                L5b:
                    com.android.systemui.statusbar.phone.DcmMascotViewContainer r5 = com.android.systemui.statusbar.phone.DcmMascotViewContainer.this
                    r5.isBootCompleted = r2
                    boolean r6 = r5.isWaitingForBootComplete
                    if (r6 == 0) goto Lee
                    android.widget.RemoteViews r6 = r5.remoteViews
                    r5.setMascotRemoteViews(r6)
                    com.android.systemui.statusbar.phone.DcmMascotViewContainer r4 = com.android.systemui.statusbar.phone.DcmMascotViewContainer.this
                    r4.isWaitingForBootComplete = r1
                    goto Lee
                L6e:
                    java.lang.String r0 = "com.nttdocomo.android.mascot.KEYGUARD_UPDATE"
                    boolean r5 = r5.equals(r0)
                    if (r5 != 0) goto L78
                    goto Lee
                L78:
                    java.lang.String r5 = "RemoteViews"
                    android.os.Parcelable r5 = r6.getParcelableExtra(r5)
                    android.widget.RemoteViews r5 = (android.widget.RemoteViews) r5
                    com.android.systemui.statusbar.phone.DcmMascotViewContainer r6 = com.android.systemui.statusbar.phone.DcmMascotViewContainer.this
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    java.lang.String r1 = "mascotView: "
                    r0.<init>(r1)
                    r0.append(r5)
                    java.lang.String r0 = r0.toString()
                    r6.getClass()
                    com.android.systemui.statusbar.phone.DcmMascotViewContainer.log(r0)
                    com.android.systemui.statusbar.phone.DcmMascotViewContainer r4 = com.android.systemui.statusbar.phone.DcmMascotViewContainer.this
                    r4.setMascotRemoteViews(r5)
                    goto Lee
                L9c:
                    java.lang.String r0 = "com.nttdocomo.android.mascot.widget.LockScreenMascotWidget.ACTION_SCREEN_UNLOCK"
                    boolean r5 = r5.equals(r0)
                    if (r5 != 0) goto La5
                    goto Lee
                La5:
                    java.lang.String r5 = "eventType"
                    int r6 = r6.getIntExtra(r5, r1)
                    com.android.systemui.statusbar.phone.DcmMascotViewContainer r0 = com.android.systemui.statusbar.phone.DcmMascotViewContainer.this
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    java.lang.String r3 = "eventType "
                    r1.<init>(r3)
                    r1.append(r6)
                    java.lang.String r1 = r1.toString()
                    r0.getClass()
                    com.android.systemui.statusbar.phone.DcmMascotViewContainer.log(r1)
                    if (r6 < 0) goto Lcb
                    java.lang.String[] r0 = com.android.systemui.statusbar.phone.DcmMascotViewContainer.MASCOT_ACTION
                    int r1 = r0.length
                    if (r6 >= r1) goto Lcb
                    r0 = r0[r6]
                    goto Lcc
                Lcb:
                    r0 = 0
                Lcc:
                    if (r0 == 0) goto Lee
                    com.android.systemui.statusbar.phone.DcmMascotViewContainer r4 = com.android.systemui.statusbar.phone.DcmMascotViewContainer.this
                    com.android.systemui.plugins.ActivityStarter r4 = r4.activityStart
                    android.content.Intent r1 = new android.content.Intent
                    r1.<init>(r0)
                    java.lang.String r0 = "com.nttdocomo.android.mascot"
                    java.lang.String r3 = "com.nttdocomo.android.mascot.application.MascotApplicationProxy"
                    r1.setClassName(r0, r3)
                    java.lang.String r0 = "android.intent.category.LAUNCHER"
                    r1.addCategory(r0)
                    r1.putExtra(r5, r6)
                    r5 = 270532608(0x10200000, float:3.1554436E-29)
                    r1.setFlags(r5)
                    r4.startActivity(r1, r2)
                Lee:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.DcmMascotViewContainer$broadcastReceiver$1.onReceive(android.content.Context, android.content.Intent):void");
            }
        };
        this.updateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.DcmMascotViewContainer$updateMonitorCallback$1
            /* JADX WARN: Removed duplicated region for block: B:13:0x0036  */
            /* JADX WARN: Removed duplicated region for block: B:16:0x003d  */
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onKeyguardVisibilityChanged(boolean r4) {
                /*
                    r3 = this;
                    if (r4 == 0) goto L42
                    boolean r4 = com.android.systemui.statusbar.phone.DcmMascotViewContainer.DEBUG
                    com.android.systemui.statusbar.phone.DcmMascotViewContainer r3 = com.android.systemui.statusbar.phone.DcmMascotViewContainer.this
                    r3.getClass()
                    r4 = 0
                    android.content.pm.PackageManager r0 = r3.pm     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L2b
                    java.lang.String r1 = "com.nttdocomo.android.mascot"
                    r2 = 8704(0x2200, float:1.2197E-41)
                    android.content.pm.ApplicationInfo r0 = r0.getApplicationInfo(r1, r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L2b
                    boolean r1 = r0.enabled     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L2b
                    if (r1 == 0) goto L22
                    int r0 = r0.flags     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L2b
                    r1 = 2097152(0x200000, float:2.938736E-39)
                    r0 = r0 & r1
                    if (r0 == 0) goto L20
                    goto L22
                L20:
                    r0 = 1
                    goto L28
                L22:
                    java.lang.String r0 = "isMascotAppRunning : Mascot is stopped."
                    com.android.systemui.statusbar.phone.DcmMascotViewContainer.log(r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L2b
                    r0 = r4
                L28:
                    r3.isMascotAppRunning = r0     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L2b
                    goto L32
                L2b:
                    r3.isMascotAppRunning = r4
                    java.lang.String r0 = "Not installed MASCOT_PACKAGE"
                    com.android.systemui.statusbar.phone.DcmMascotViewContainer.log(r0)
                L32:
                    com.android.systemui.shade.NotificationPanelViewController$9 r0 = r3.injector
                    if (r0 != 0) goto L37
                    r0 = 0
                L37:
                    com.android.systemui.shade.NotificationPanelViewController r0 = com.android.systemui.shade.NotificationPanelViewController.this
                    boolean r0 = r0.mDozing
                    if (r0 == 0) goto L3f
                    r4 = 8
                L3f:
                    r3.setMascotViewVisible(r4)
                L42:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.DcmMascotViewContainer$updateMonitorCallback$1.onKeyguardVisibilityChanged(boolean):void");
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStartedGoingToSleep(int i) {
                DcmMascotViewContainer.this.sUseCachedIsDcmLauncher = false;
            }
        };
        this.DCM_LAUNCHER = new String[]{"com.nttdocomo.android.dhome", "com.nttdocomo.android.homezozo"};
    }

    public static void log(String str) {
        if (DEBUG) {
            Log.d("DcmMascotViewContainer", str);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0037, code lost:
    
        if (r0.userAllowsPrivateNotificationsInPublic(r0.mCurrentUserId) != false) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x001e, code lost:
    
        if (r0.userAllowsPrivateNotificationsInPublic(r0.mCurrentUserId) != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isMascotEnabled() {
        /*
            r2 = this;
            android.widget.RemoteViews r0 = r2.remoteViews
            if (r0 == 0) goto L3f
            com.android.systemui.statusbar.NotificationLockscreenUserManager r0 = r2.lockscreenUserManager
            com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r0 = (com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl) r0
            int r1 = r0.mCurrentUserId
            boolean r0 = r0.isLockscreenPublicMode(r1)
            if (r0 != 0) goto L21
            com.android.systemui.statusbar.NotificationLockscreenUserManager r0 = r2.lockscreenUserManager
            com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r0 = (com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl) r0
            boolean r1 = r0.mShowLockscreenNotifications
            if (r1 == 0) goto L3f
            int r1 = r0.mCurrentUserId
            boolean r0 = r0.userAllowsPrivateNotificationsInPublic(r1)
            if (r0 == 0) goto L3f
            goto L39
        L21:
            com.android.systemui.statusbar.NotificationLockscreenUserManager r0 = r2.lockscreenUserManager
            com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r0 = (com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl) r0
            int r1 = r0.mCurrentUserId
            boolean r0 = r0.isLockscreenPublicMode(r1)
            if (r0 == 0) goto L39
            com.android.systemui.statusbar.NotificationLockscreenUserManager r0 = r2.lockscreenUserManager
            com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r0 = (com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl) r0
            int r1 = r0.mCurrentUserId
            boolean r0 = r0.userAllowsPrivateNotificationsInPublic(r1)
            if (r0 == 0) goto L3f
        L39:
            boolean r2 = r2.isMascotAppRunning
            if (r2 == 0) goto L3f
            r2 = 1
            goto L40
        L3f:
            r2 = 0
        L40:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "isMascotEnabled "
            r0.<init>(r1)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            log(r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.DcmMascotViewContainer.isMascotEnabled():boolean");
    }

    public final void setMascotRemoteViews(RemoteViews remoteViews) {
        this.remoteViews = remoteViews;
        NotificationPanelViewController.AnonymousClass9 anonymousClass9 = this.injector;
        if (anonymousClass9 == null) {
            anonymousClass9 = null;
        }
        NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
        if (((BiometricUnlockController) notificationPanelViewController.mBioUnlockControllerLazy.get()).isBiometricUnlock() || notificationPanelViewController.mPluginLockViewMode == 1) {
            return;
        }
        Runnable runnable = this.cancelUpdateRunnable;
        if (runnable != null) {
            runnable.run();
        }
        this.cancelUpdateRunnable = this.mainExecutor.executeDelayed(this.updateRunnable, 0L, TimeUnit.MILLISECONDS);
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0076, code lost:
    
        if (r2 == false) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setMascotViewVisible(int r7) {
        /*
            r6 = this;
            if (r7 != 0) goto L8
            boolean r0 = r6.isMascotEnabled()
            if (r0 == 0) goto L78
        L8:
            android.content.Context r0 = r6.getContext()
            boolean r1 = com.android.systemui.CscRune.KEYGUARD_DCM_LIVE_UX
            r2 = 0
            if (r1 == 0) goto L76
            boolean r1 = r6.sUseCachedIsDcmLauncher
            if (r1 == 0) goto L18
            boolean r2 = r6.sIsDcmLauncher
            goto L76
        L18:
            r1 = 1
            r6.sUseCachedIsDcmLauncher = r1
            r6.sIsDcmLauncher = r2
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            android.content.Intent r3 = new android.content.Intent
            java.lang.String r4 = "android.intent.action.MAIN"
            r3.<init>(r4)
            java.lang.String r4 = "android.intent.category.HOME"
            android.content.Intent r3 = r3.addCategory(r4)
            r4 = 65536(0x10000, float:9.18355E-41)
            android.content.pm.ResolveInfo r0 = r0.resolveActivity(r3, r4)
            if (r0 == 0) goto L3d
            android.content.pm.ActivityInfo r0 = r0.activityInfo
            if (r0 == 0) goto L3d
            java.lang.String r0 = r0.packageName
            goto L3e
        L3d:
            r0 = 0
        L3e:
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            if (r3 != 0) goto L57
            java.lang.String[] r3 = r6.DCM_LAUNCHER
            int r4 = r3.length
        L47:
            if (r2 >= r4) goto L57
            r5 = r3[r2]
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r0)
            if (r5 == 0) goto L54
            r6.sIsDcmLauncher = r1
            goto L57
        L54:
            int r2 = r2 + 1
            goto L47
        L57:
            boolean r1 = r6.sIsDcmLauncher
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "isDcmLauncher "
            r2.<init>(r3)
            r2.append(r0)
            java.lang.String r0 = " / "
            r2.append(r0)
            r2.append(r1)
            java.lang.String r0 = r2.toString()
            java.lang.String r1 = "DcmMascotViewContainer"
            android.util.Log.d(r1, r0)
            boolean r2 = r6.sIsDcmLauncher
        L76:
            if (r2 != 0) goto L7a
        L78:
            r7 = 8
        L7a:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "setMascotViewVisible() "
            r0.<init>(r1)
            r0.append(r7)
            java.lang.String r0 = r0.toString()
            log(r0)
            r6.setVisibility(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.DcmMascotViewContainer.setMascotViewVisible(int):void");
    }

    public final int updatePosition(int i, int i2) {
        int i3;
        int i4;
        int i5;
        if (getVisibility() == 0 && isMascotEnabled()) {
            i3 = this.mascotHeight;
            i5 = this.mascotTopMarin;
            if (i2 > 0) {
                i5 += i2;
            }
            i4 = this.mascotBottomMarin;
            setY(i + i5);
        } else {
            setMascotViewVisible(8);
            i3 = 0;
            i4 = 0;
            i5 = 0;
        }
        return i3 + i5 + i4;
    }

    public final void updateRes() {
        Resources resources = getContext().getResources();
        this.mascotHeight = resources.getDimensionPixelSize(R.dimen.mascot_display_height);
        this.mascotTopMarin = resources.getDimensionPixelSize(R.dimen.mascot_top_margin);
        this.mascotBottomMarin = resources.getDimensionPixelSize(R.dimen.mascot_bottom_margin);
    }
}
