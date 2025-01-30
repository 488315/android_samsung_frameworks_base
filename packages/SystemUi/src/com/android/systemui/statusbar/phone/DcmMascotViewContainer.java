package com.android.systemui.statusbar.phone;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.IBinder;
import android.os.IInterface;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.nttdocomo.android.screenlockservice.IScreenLockService;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
    public ExecutorImpl.ExecutionToken cancelUpdateRunnable;
    public NotificationPanelViewController.C24418 injector;
    public boolean isBootCompleted;
    public boolean isMascotAppRunning;
    public boolean isWaitingForBootComplete;
    public final NotificationLockscreenUserManager lockscreenUserManager;
    public final DelayableExecutor mainExecutor;
    public int mascotBottomMarin;
    public int mascotHeight;
    public int mascotTopMarin;

    /* renamed from: pm */
    public final PackageManager f354pm;
    public RemoteViews remoteViews;
    public boolean sIsDcmLauncher;
    public boolean sUseCachedIsDcmLauncher;
    public final StatusBarStateController sbStateController;
    public final DcmMascotViewContainer$serviceConnection$1 serviceConnection;
    public final CentralSurfaces statusBar;
    public final KeyguardUpdateMonitor updateMonitor;
    public final KeyguardUpdateMonitorCallback updateMonitorCallback;
    public final DcmMascotViewContainer$updateRunnable$1 updateRunnable;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        boolean z = true;
        if (DeviceType.getDebugLevel() != 1 && DeviceType.isShipBuild()) {
            z = false;
        }
        DEBUG = z;
        DCM_SCREEN_LOCK_SERVICE_ACTION = IScreenLockService.class.getName();
        MASCOT_ACTION = new String[]{null, "LOCK_CLICK_MASCOT", "LOCK_CLICK_POPUP", "ACTION_UNLOCK", "LOCK_CLICK_POPUP"};
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.phone.DcmMascotViewContainer$serviceConnection$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.phone.DcmMascotViewContainer$updateRunnable$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.phone.DcmMascotViewContainer$broadcastReceiver$1] */
    public DcmMascotViewContainer(Context context, DelayableExecutor delayableExecutor, Executor executor, CentralSurfaces centralSurfaces, BroadcastDispatcher broadcastDispatcher, StatusBarStateController statusBarStateController, NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardUpdateMonitor keyguardUpdateMonitor, PackageManager packageManager, ActivityStarter activityStarter) {
        super(context);
        this.mainExecutor = delayableExecutor;
        this.bgExecutor = executor;
        this.statusBar = centralSurfaces;
        this.broadcastDispatcher = broadcastDispatcher;
        this.sbStateController = statusBarStateController;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.updateMonitor = keyguardUpdateMonitor;
        this.f354pm = packageManager;
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
                    NotificationPanelViewController.C24418 c24418 = dcmMascotViewContainer.injector;
                    NotificationPanelViewController.this.positionClockAndNotifications(false);
                    return;
                }
                RemoteViews remoteViews = dcmMascotViewContainer.remoteViews;
                if (remoteViews != null) {
                    Context context2 = dcmMascotViewContainer.getContext();
                    NotificationPanelViewController.C24418 c244182 = dcmMascotViewContainer.injector;
                    if (c244182 == null) {
                        c244182 = null;
                    }
                    dcmMascotViewContainer.addView(remoteViews.apply(context2, NotificationPanelViewController.this.mNotificationContainerParent));
                    if (dcmMascotViewContainer.sbStateController.getState() == 1) {
                        NotificationPanelViewController.C24418 c244183 = dcmMascotViewContainer.injector;
                        if (c244183 == null) {
                            c244183 = null;
                        }
                        dcmMascotViewContainer.setMascotViewVisible(NotificationPanelViewController.this.mDozing ? 8 : 0);
                        NotificationPanelViewController.C24418 c244184 = dcmMascotViewContainer.injector;
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
            */
            public final void onReceive(Context context2, Intent intent) {
                String str;
                String action = intent.getAction();
                DcmMascotViewContainer dcmMascotViewContainer = DcmMascotViewContainer.this;
                String m21m = KeyAttributes$$ExternalSyntheticOutline0.m21m("onReceive ", action);
                boolean z = DcmMascotViewContainer.DEBUG;
                dcmMascotViewContainer.getClass();
                DcmMascotViewContainer.log(m21m);
                if (action == null) {
                    return;
                }
                switch (action.hashCode()) {
                    case -1371817472:
                        if (!action.equals("com.nttdocomo.android.mascot.widget.LockScreenMascotWidget.ACTION_SCREEN_UNLOCK")) {
                            return;
                        }
                        int intExtra = intent.getIntExtra("eventType", 0);
                        DcmMascotViewContainer.this.getClass();
                        DcmMascotViewContainer.log("eventType " + intExtra);
                        if (intExtra >= 0) {
                            String[] strArr = DcmMascotViewContainer.MASCOT_ACTION;
                            if (intExtra < strArr.length) {
                                str = strArr[intExtra];
                                if (str == null) {
                                    DcmMascotViewContainer dcmMascotViewContainer2 = DcmMascotViewContainer.this;
                                    ((CentralSurfacesImpl) dcmMascotViewContainer2.statusBar).userActivity();
                                    ActivityStarter activityStarter2 = dcmMascotViewContainer2.activityStart;
                                    Intent intent2 = new Intent(str);
                                    intent2.setClassName("com.nttdocomo.android.mascot", "com.nttdocomo.android.mascot.application.MascotApplicationProxy");
                                    intent2.addCategory("android.intent.category.LAUNCHER");
                                    intent2.putExtra("eventType", intExtra);
                                    intent2.setFlags(270532608);
                                    activityStarter2.startActivity(intent2, true);
                                    return;
                                }
                                return;
                            }
                        }
                        str = null;
                        if (str == null) {
                        }
                    case -322599088:
                        if (action.equals("com.nttdocomo.android.mascot.KEYGUARD_UPDATE")) {
                            RemoteViews remoteViews = (RemoteViews) intent.getParcelableExtra("RemoteViews");
                            DcmMascotViewContainer.this.getClass();
                            DcmMascotViewContainer.log("mascotView: " + remoteViews);
                            DcmMascotViewContainer.this.setMascotRemoteViews(remoteViews);
                            return;
                        }
                        return;
                    case 798292259:
                        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
                            DcmMascotViewContainer dcmMascotViewContainer3 = DcmMascotViewContainer.this;
                            dcmMascotViewContainer3.isBootCompleted = true;
                            if (dcmMascotViewContainer3.isWaitingForBootComplete) {
                                dcmMascotViewContainer3.setMascotRemoteViews(dcmMascotViewContainer3.remoteViews);
                                DcmMascotViewContainer.this.isWaitingForBootComplete = false;
                                return;
                            }
                            return;
                        }
                        return;
                    case 891285360:
                        if (!action.equals("jp.co.nttdocomo.carriermail.APP_LINK_RECEIVED_MESSAGE")) {
                            return;
                        }
                        break;
                    case 1330693824:
                        if (!action.equals("com.android.internal.policy.impl.CARRIERMAIL_COUNT_UPDATE")) {
                            return;
                        }
                        break;
                    default:
                        return;
                }
                DcmMascotViewContainer dcmMascotViewContainer4 = DcmMascotViewContainer.this;
                dcmMascotViewContainer4.bgExecutor.execute(new DcmMascotViewContainer$sendUnreadCountBroadcast$1(dcmMascotViewContainer4));
                if (Intrinsics.areEqual("com.android.internal.policy.impl.CARRIERMAIL_COUNT_UPDATE", action)) {
                    DcmMascotViewContainer dcmMascotViewContainer5 = DcmMascotViewContainer.this;
                    dcmMascotViewContainer5.setMascotRemoteViews(dcmMascotViewContainer5.remoteViews);
                }
            }
        };
        this.updateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.DcmMascotViewContainer$updateMonitorCallback$1
            /* JADX WARN: Removed duplicated region for block: B:13:0x0036  */
            /* JADX WARN: Removed duplicated region for block: B:16:0x003d  */
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onKeyguardVisibilityChanged(boolean z) {
                NotificationPanelViewController.C24418 c24418;
                ApplicationInfo applicationInfo;
                boolean z2;
                if (z) {
                    boolean z3 = DcmMascotViewContainer.DEBUG;
                    DcmMascotViewContainer dcmMascotViewContainer = DcmMascotViewContainer.this;
                    dcmMascotViewContainer.getClass();
                    try {
                        applicationInfo = dcmMascotViewContainer.f354pm.getApplicationInfo("com.nttdocomo.android.mascot", 8704);
                    } catch (PackageManager.NameNotFoundException unused) {
                        dcmMascotViewContainer.isMascotAppRunning = false;
                        DcmMascotViewContainer.log("Not installed MASCOT_PACKAGE");
                    }
                    if (applicationInfo.enabled && (applicationInfo.flags & QuickStepContract.SYSUI_STATE_DEVICE_DOZING) == 0) {
                        z2 = true;
                        dcmMascotViewContainer.isMascotAppRunning = z2;
                        c24418 = dcmMascotViewContainer.injector;
                        if (c24418 == null) {
                            c24418 = null;
                        }
                        dcmMascotViewContainer.setMascotViewVisible(NotificationPanelViewController.this.mDozing ? 8 : 0);
                    }
                    DcmMascotViewContainer.log("isMascotAppRunning : Mascot is stopped.");
                    z2 = false;
                    dcmMascotViewContainer.isMascotAppRunning = z2;
                    c24418 = dcmMascotViewContainer.injector;
                    if (c24418 == null) {
                    }
                    dcmMascotViewContainer.setMascotViewVisible(NotificationPanelViewController.this.mDozing ? 8 : 0);
                }
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

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0039, code lost:
    
        if (r0.userAllowsPrivateNotificationsInPublic(r0.mCurrentUserId) != false) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0020, code lost:
    
        if (r0.userAllowsPrivateNotificationsInPublic(r0.mCurrentUserId) != false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isMascotEnabled() {
        boolean z;
        boolean z2 = false;
        if (this.remoteViews != null) {
            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
            if (notificationLockscreenUserManagerImpl.isLockscreenPublicMode(notificationLockscreenUserManagerImpl.mCurrentUserId)) {
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl2 = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
                if (notificationLockscreenUserManagerImpl2.isLockscreenPublicMode(notificationLockscreenUserManagerImpl2.mCurrentUserId)) {
                    NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl3 = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
                }
                z = true;
            } else {
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl4 = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
                if (notificationLockscreenUserManagerImpl4.mShowLockscreenNotifications) {
                }
                z = false;
            }
            if (z && this.isMascotAppRunning) {
                z2 = true;
            }
        }
        log("isMascotEnabled " + z2);
        return z2;
    }

    public final void setMascotRemoteViews(RemoteViews remoteViews) {
        this.remoteViews = remoteViews;
        NotificationPanelViewController.C24418 c24418 = this.injector;
        if (c24418 == null) {
            c24418 = null;
        }
        NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
        boolean z = true;
        if (!((CentralSurfacesImpl) notificationPanelViewController.mCentralSurfaces).mBiometricUnlockController.isBiometricUnlock()) {
            if (!(notificationPanelViewController.mPluginLockViewMode == 1)) {
                z = false;
            }
        }
        if (z) {
            return;
        }
        ExecutorImpl.ExecutionToken executionToken = this.cancelUpdateRunnable;
        if (executionToken != null) {
            executionToken.run();
        }
        this.cancelUpdateRunnable = ((ExecutorImpl) this.mainExecutor).executeDelayed(this.updateRunnable, 0L, TimeUnit.MILLISECONDS);
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0076, code lost:
    
        if (r2 == false) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setMascotViewVisible(int i) {
        ActivityInfo activityInfo;
        if (i != 0 || isMascotEnabled()) {
            Context context = getContext();
            boolean z = false;
            int i2 = 0;
            if (LsRune.KEYGUARD_DCM_LIVE_UX) {
                if (this.sUseCachedIsDcmLauncher) {
                    z = this.sIsDcmLauncher;
                } else {
                    this.sUseCachedIsDcmLauncher = true;
                    this.sIsDcmLauncher = false;
                    ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME"), 65536);
                    String str = (resolveActivity == null || (activityInfo = resolveActivity.activityInfo) == null) ? null : activityInfo.packageName;
                    if (!TextUtils.isEmpty(str)) {
                        String[] strArr = this.DCM_LAUNCHER;
                        int length = strArr.length;
                        while (true) {
                            if (i2 >= length) {
                                break;
                            }
                            if (Intrinsics.areEqual(strArr[i2], str)) {
                                this.sIsDcmLauncher = true;
                                break;
                            }
                            i2++;
                        }
                    }
                    Log.d("DcmMascotViewContainer", "isDcmLauncher " + str + " / " + this.sIsDcmLauncher);
                    z = this.sIsDcmLauncher;
                }
            }
        }
        i = 8;
        log("setMascotViewVisible() " + i);
        setVisibility(i);
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
