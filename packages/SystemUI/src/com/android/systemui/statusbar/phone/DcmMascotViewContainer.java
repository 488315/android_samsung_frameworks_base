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
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.CscRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.nttdocomo.android.screenlockservice.IScreenLockService;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) throws InterruptedException {
                IScreenLockService proxy;
                DcmMascotViewContainer dcmMascotViewContainer = this.this$0;
                boolean z = DcmMascotViewContainer.DEBUG;
                dcmMascotViewContainer.getClass();
                DcmMascotViewContainer.log("onServiceConnected");
                try {
                    BlockingDeque blockingDeque = this.this$0.blockingQueue;
                    int i = IScreenLockService.Stub.$r8$clinit;
                    if (iBinder == null) {
                        proxy = null;
                    } else {
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.nttdocomo.android.screenlockservice.IScreenLockService");
                        proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IScreenLockService)) ? new IScreenLockService.Stub.Proxy(iBinder) : (IScreenLockService) iInterfaceQueryLocalInterface;
                    }
                    ((LinkedBlockingDeque) blockingDeque).put(proxy);
                } catch (InterruptedException e) {
                    DcmMascotViewContainer dcmMascotViewContainer2 = this.this$0;
                    String str = "onServiceConnected exception " + e.getMessage();
                    dcmMascotViewContainer2.getClass();
                    DcmMascotViewContainer.log(str);
                }
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                DcmMascotViewContainer dcmMascotViewContainer = this.this$0;
                boolean z = DcmMascotViewContainer.DEBUG;
                dcmMascotViewContainer.getClass();
                DcmMascotViewContainer.log("onServiceDisconnected");
            }
        };
        this.updateRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.DcmMascotViewContainer$updateRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                DcmMascotViewContainer dcmMascotViewContainer = this.this$0;
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
            /* JADX WARN: Removed duplicated region for block: B:38:0x00ca  */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onReceive(Context context2, Intent intent) {
                String str;
                String action = intent.getAction();
                DcmMascotViewContainer dcmMascotViewContainer = this.this$0;
                String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onReceive ", action);
                boolean z = DcmMascotViewContainer.DEBUG;
                dcmMascotViewContainer.getClass();
                DcmMascotViewContainer.log(strM);
                if (action == null) {
                    return;
                }
                switch (action.hashCode()) {
                    case -1371817472:
                        if (action.equals("com.nttdocomo.android.mascot.widget.LockScreenMascotWidget.ACTION_SCREEN_UNLOCK")) {
                            int intExtra = intent.getIntExtra("eventType", 0);
                            this.this$0.getClass();
                            DcmMascotViewContainer.log("eventType " + intExtra);
                            if (intExtra >= 0) {
                                String[] strArr = DcmMascotViewContainer.MASCOT_ACTION;
                                str = intExtra < strArr.length ? strArr[intExtra] : null;
                            }
                            if (str != null) {
                                ActivityStarter activityStarter2 = this.this$0.activityStart;
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
                        return;
                    case -322599088:
                        if (action.equals("com.nttdocomo.android.mascot.KEYGUARD_UPDATE")) {
                            RemoteViews remoteViews = (RemoteViews) intent.getParcelableExtra("RemoteViews");
                            this.this$0.getClass();
                            DcmMascotViewContainer.log("mascotView: " + remoteViews);
                            this.this$0.setMascotRemoteViews(remoteViews);
                            return;
                        }
                        return;
                    case 798292259:
                        if (action.equals(PopupUIUtil.ACTION_BOOT_COMPLETED)) {
                            DcmMascotViewContainer dcmMascotViewContainer2 = this.this$0;
                            dcmMascotViewContainer2.isBootCompleted = true;
                            if (dcmMascotViewContainer2.isWaitingForBootComplete) {
                                dcmMascotViewContainer2.setMascotRemoteViews(dcmMascotViewContainer2.remoteViews);
                                this.this$0.isWaitingForBootComplete = false;
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
                DcmMascotViewContainer dcmMascotViewContainer3 = this.this$0;
                dcmMascotViewContainer3.bgExecutor.execute(new DcmMascotViewContainer$sendUnreadCountBroadcast$1(dcmMascotViewContainer3));
                if ("com.android.internal.policy.impl.CARRIERMAIL_COUNT_UPDATE".equals(action)) {
                    DcmMascotViewContainer dcmMascotViewContainer4 = this.this$0;
                    dcmMascotViewContainer4.setMascotRemoteViews(dcmMascotViewContainer4.remoteViews);
                }
            }
        };
        this.updateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.DcmMascotViewContainer$updateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) throws PackageManager.NameNotFoundException {
                boolean z2;
                if (z) {
                    boolean z3 = DcmMascotViewContainer.DEBUG;
                    DcmMascotViewContainer dcmMascotViewContainer = this.this$0;
                    dcmMascotViewContainer.getClass();
                    try {
                        ApplicationInfo applicationInfo = dcmMascotViewContainer.pm.getApplicationInfo("com.nttdocomo.android.mascot", 8704);
                        if (applicationInfo.enabled && (applicationInfo.flags & 2097152) == 0) {
                            z2 = true;
                        } else {
                            DcmMascotViewContainer.log("isMascotAppRunning : Mascot is stopped.");
                            z2 = false;
                        }
                        dcmMascotViewContainer.isMascotAppRunning = z2;
                    } catch (PackageManager.NameNotFoundException unused) {
                        dcmMascotViewContainer.isMascotAppRunning = false;
                        DcmMascotViewContainer.log("Not installed MASCOT_PACKAGE");
                    }
                    NotificationPanelViewController.AnonymousClass9 anonymousClass9 = dcmMascotViewContainer.injector;
                    if (anonymousClass9 == null) {
                        anonymousClass9 = null;
                    }
                    dcmMascotViewContainer.setMascotViewVisible(NotificationPanelViewController.this.mDozing ? 8 : 0);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStartedGoingToSleep(int i) {
                this.this$0.sUseCachedIsDcmLauncher = false;
            }
        };
        this.DCM_LAUNCHER = new String[]{"com.nttdocomo.android.dhome", "com.nttdocomo.android.homezozo"};
    }

    public static void log(String str) {
        if (DEBUG) {
            Log.d("DcmMascotViewContainer", str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isMascotEnabled() {
        boolean z;
        if (this.remoteViews != null) {
            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
            if (notificationLockscreenUserManagerImpl.isLockscreenPublicMode(notificationLockscreenUserManagerImpl.mCurrentUserId)) {
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl2 = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
                if (notificationLockscreenUserManagerImpl2.isLockscreenPublicMode(notificationLockscreenUserManagerImpl2.mCurrentUserId)) {
                    NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl3 = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
                    if (notificationLockscreenUserManagerImpl3.userAllowsPrivateNotificationsInPublic(notificationLockscreenUserManagerImpl3.mCurrentUserId)) {
                    }
                }
            } else {
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl4 = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
                if (!notificationLockscreenUserManagerImpl4.mShowLockscreenNotifications || !notificationLockscreenUserManagerImpl4.userAllowsPrivateNotificationsInPublic(notificationLockscreenUserManagerImpl4.mCurrentUserId)) {
                    z = false;
                } else if (this.isMascotAppRunning) {
                    z = true;
                }
            }
        }
        log("isMascotEnabled " + z);
        return z;
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

    /* JADX WARN: Removed duplicated region for block: B:26:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setMascotViewVisible(int i) {
        ActivityInfo activityInfo;
        if (i != 0 || isMascotEnabled()) {
            Context context = getContext();
            boolean z = false;
            int i2 = 0;
            if (CscRune.KEYGUARD_DCM_LIVE_UX) {
                if (this.sUseCachedIsDcmLauncher) {
                    z = this.sIsDcmLauncher;
                } else {
                    this.sUseCachedIsDcmLauncher = true;
                    this.sIsDcmLauncher = false;
                    ResolveInfo resolveInfoResolveActivity = context.getPackageManager().resolveActivity(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME"), 65536);
                    String str = (resolveInfoResolveActivity == null || (activityInfo = resolveInfoResolveActivity.activityInfo) == null) ? null : activityInfo.packageName;
                    if (!TextUtils.isEmpty(str)) {
                        String[] strArr = this.DCM_LAUNCHER;
                        int length = strArr.length;
                        while (true) {
                            if (i2 >= length) {
                                break;
                            }
                            String str2 = strArr[i2];
                            str2.getClass();
                            if (str2.equals(str)) {
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
            if (!z) {
                i = 8;
            }
        }
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
