package com.android.systemui.shade;

import android.content.Context;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecPanelTouchBlockHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy centralSurfaces$delegate;
    public final Lazy keyguardStateController$delegate;
    public final Lazy knoxStateMonitor$delegate;
    public final Lazy statusBarStateController$delegate;
    public final AtomicBoolean userChangeInProgress = new AtomicBoolean(false);
    public final SecPanelTouchBlockHelper$userTrackerCallback$1 userTrackerCallback;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.settings.UserTracker$Callback, com.android.systemui.shade.SecPanelTouchBlockHelper$userTrackerCallback$1] */
    public SecPanelTouchBlockHelper(UserTracker userTracker, DelayableExecutor delayableExecutor) {
        final int i = 0;
        this.centralSurfaces$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecPanelTouchBlockHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i2 = SecPanelTouchBlockHelper.$r8$clinit;
                        return (CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class);
                    case 1:
                        int i3 = SecPanelTouchBlockHelper.$r8$clinit;
                        return (KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class);
                    case 2:
                        int i4 = SecPanelTouchBlockHelper.$r8$clinit;
                        return (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
                    default:
                        int i5 = SecPanelTouchBlockHelper.$r8$clinit;
                        return (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
                }
            }
        });
        final int i2 = 1;
        this.keyguardStateController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecPanelTouchBlockHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i22 = SecPanelTouchBlockHelper.$r8$clinit;
                        return (CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class);
                    case 1:
                        int i3 = SecPanelTouchBlockHelper.$r8$clinit;
                        return (KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class);
                    case 2:
                        int i4 = SecPanelTouchBlockHelper.$r8$clinit;
                        return (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
                    default:
                        int i5 = SecPanelTouchBlockHelper.$r8$clinit;
                        return (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
                }
            }
        });
        final int i3 = 2;
        this.knoxStateMonitor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecPanelTouchBlockHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        int i22 = SecPanelTouchBlockHelper.$r8$clinit;
                        return (CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class);
                    case 1:
                        int i32 = SecPanelTouchBlockHelper.$r8$clinit;
                        return (KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class);
                    case 2:
                        int i4 = SecPanelTouchBlockHelper.$r8$clinit;
                        return (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
                    default:
                        int i5 = SecPanelTouchBlockHelper.$r8$clinit;
                        return (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
                }
            }
        });
        final int i4 = 3;
        this.statusBarStateController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecPanelTouchBlockHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        int i22 = SecPanelTouchBlockHelper.$r8$clinit;
                        return (CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class);
                    case 1:
                        int i32 = SecPanelTouchBlockHelper.$r8$clinit;
                        return (KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class);
                    case 2:
                        int i42 = SecPanelTouchBlockHelper.$r8$clinit;
                        return (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
                    default:
                        int i5 = SecPanelTouchBlockHelper.$r8$clinit;
                        return (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
                }
            }
        });
        ?? r0 = new UserTracker.Callback() { // from class: com.android.systemui.shade.SecPanelTouchBlockHelper$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onBeforeUserSwitching(int i5) {
                SecPanelTouchBlockHelper.this.userChangeInProgress.set(true);
                Log.d("SecPanelTouchBlockHelper", "onBeforeUserSwitching: " + i5);
            }

            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i5, Context context) {
                SecPanelTouchBlockHelper.this.userChangeInProgress.set(false);
                Log.d("SecPanelTouchBlockHelper", "onUserChanged: " + i5);
            }
        };
        this.userTrackerCallback = r0;
        ((UserTrackerImpl) userTracker).addCallback(r0, delayableExecutor);
        Unit unit = Unit.INSTANCE;
        Log.d("SecPanelTouchBlockHelper", "addCallback");
    }

    public final boolean isBlockedByKeyguardAnimating() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) ((KeyguardStateController) this.keyguardStateController$delegate.getValue());
        boolean z = keyguardStateControllerImpl.mKeyguardFadingAway || keyguardStateControllerImpl.mKeyguardGoingAway;
        if (z) {
            Log.d("SecPanelTouchBlockHelper", "isBlockedByKeyguardAnimating");
        }
        return z;
    }

    public final boolean isBlockedByKnoxPanelExpandDisabled() {
        KnoxStateMonitorImpl knoxStateMonitorImpl;
        CustomSdkMonitor customSdkMonitor;
        EdmMonitor edmMonitor;
        KnoxStateMonitor knoxStateMonitor = (KnoxStateMonitor) this.knoxStateMonitor$delegate.getValue();
        boolean z = false;
        if (knoxStateMonitor != null && (((customSdkMonitor = (knoxStateMonitorImpl = (KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor) == null || !customSdkMonitor.mStatusBarNotificationsState) && ((edmMonitor = knoxStateMonitorImpl.mEdmMonitor) == null || !edmMonitor.mStatusBarExpandAllowed || edmMonitor.mIsStatusBarHidden))) {
            z = true;
        }
        if (z) {
            Log.d("SecPanelTouchBlockHelper", "isBlockedByKnoxPanelExpandDisabled");
        }
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x007f, code lost:
    
        if (r5 != false) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isKeyguardPanelDisabled() {
        /*
            r5 = this;
            kotlin.Lazy r0 = r5.statusBarStateController$delegate
            java.lang.Object r0 = r0.getValue()
            com.android.systemui.plugins.statusbar.StatusBarStateController r0 = (com.android.systemui.plugins.statusbar.StatusBarStateController) r0
            r1 = 0
            java.lang.String r2 = "SecPanelTouchBlockHelper"
            if (r0 == 0) goto L82
            int r0 = r0.getState()
            r3 = 1
            if (r0 != r3) goto L82
            boolean r0 = r5.isBlockedByKeyguardAnimating()
            if (r0 != 0) goto L81
            boolean r0 = r5.isBlockedByKnoxPanelExpandDisabled()
            if (r0 != 0) goto L81
            kotlin.Lazy r0 = r5.knoxStateMonitor$delegate
            java.lang.Object r0 = r0.getValue()
            com.android.systemui.knox.KnoxStateMonitor r0 = (com.android.systemui.knox.KnoxStateMonitor) r0
            if (r0 == 0) goto L34
            com.android.systemui.knox.KnoxStateMonitorImpl r0 = (com.android.systemui.knox.KnoxStateMonitorImpl) r0
            boolean r0 = r0.isStatusBarHidden()
            if (r0 != r3) goto L34
            r0 = r3
            goto L35
        L34:
            r0 = r1
        L35:
            if (r0 == 0) goto L3c
            java.lang.String r4 = "isBlockedByKnoxStatusBarHidden"
            android.util.Log.d(r2, r4)
        L3c:
            if (r0 != 0) goto L81
            java.util.concurrent.atomic.AtomicBoolean r0 = r5.userChangeInProgress
            boolean r0 = r0.get()
            if (r0 == 0) goto L4b
            java.lang.String r4 = "isBlockedByUserChangeInProgress"
            android.util.Log.d(r2, r4)
        L4b:
            if (r0 != 0) goto L81
            kotlin.Lazy r0 = r5.centralSurfaces$delegate
            java.lang.Object r0 = r0.getValue()
            com.android.systemui.statusbar.phone.CentralSurfaces r0 = (com.android.systemui.statusbar.phone.CentralSurfaces) r0
            com.android.systemui.statusbar.phone.CentralSurfacesImpl r0 = (com.android.systemui.statusbar.phone.CentralSurfacesImpl) r0
            com.android.systemui.statusbar.phone.CentralSurfacesCommandQueueCallbacks r0 = r0.mCommandQueueCallbacks
            int r0 = r0.mDisabled1
            r4 = 268435456(0x10000000, float:2.524355E-29)
            r0 = r0 & r4
            if (r0 == 0) goto L62
            r0 = r3
            goto L63
        L62:
            r0 = r1
        L63:
            if (r0 == 0) goto L6a
            java.lang.String r4 = "isBlockedByDisableExpandOnKeyguard"
            android.util.Log.d(r2, r4)
        L6a:
            if (r0 != 0) goto L81
            kotlin.Lazy r5 = r5.keyguardStateController$delegate
            java.lang.Object r5 = r5.getValue()
            com.android.systemui.statusbar.policy.KeyguardStateController r5 = (com.android.systemui.statusbar.policy.KeyguardStateController) r5
            com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r5 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r5
            boolean r5 = r5.mOccluded
            if (r5 == 0) goto L7f
            java.lang.String r0 = "isBlockedByKeyguardOccluded"
            android.util.Log.d(r2, r0)
        L7f:
            if (r5 == 0) goto L82
        L81:
            r1 = r3
        L82:
            if (r1 == 0) goto L89
            java.lang.String r5 = "isKeyguardPanelDisabled"
            android.util.Log.d(r2, r5)
        L89:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.SecPanelTouchBlockHelper.isKeyguardPanelDisabled():boolean");
    }
}
