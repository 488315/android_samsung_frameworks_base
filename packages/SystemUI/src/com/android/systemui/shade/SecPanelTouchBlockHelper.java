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
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class SecPanelTouchBlockHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy centralSurfaces$delegate;
    public final Lazy keyguardStateController$delegate;
    public final Lazy knoxStateMonitor$delegate;
    public final Lazy statusBarStateController$delegate;
    public final AtomicBoolean userChangeInProgress = new AtomicBoolean(false);
    public final SecPanelTouchBlockHelper$userTrackerCallback$1 userTrackerCallback;

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
                this.this$0.userChangeInProgress.set(true);
                Log.d("SecPanelTouchBlockHelper", "onBeforeUserSwitching: " + i5);
            }

            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i5, Context context) {
                this.this$0.userChangeInProgress.set(false);
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

    /* JADX WARN: Removed duplicated region for block: B:34:0x0081  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isKeyguardPanelDisabled() {
        StatusBarStateController statusBarStateController = (StatusBarStateController) this.statusBarStateController$delegate.getValue();
        boolean z = false;
        if (statusBarStateController != null && statusBarStateController.getState() == 1) {
            if (isBlockedByKeyguardAnimating() || isBlockedByKnoxPanelExpandDisabled()) {
                z = true;
            } else {
                KnoxStateMonitor knoxStateMonitor = (KnoxStateMonitor) this.knoxStateMonitor$delegate.getValue();
                boolean z2 = knoxStateMonitor != null && ((KnoxStateMonitorImpl) knoxStateMonitor).isStatusBarHidden();
                if (z2) {
                    Log.d("SecPanelTouchBlockHelper", "isBlockedByKnoxStatusBarHidden");
                }
                if (!z2) {
                    boolean z3 = this.userChangeInProgress.get();
                    if (z3) {
                        Log.d("SecPanelTouchBlockHelper", "isBlockedByUserChangeInProgress");
                    }
                    if (!z3) {
                        boolean z4 = (((CentralSurfacesImpl) ((CentralSurfaces) this.centralSurfaces$delegate.getValue())).mCommandQueueCallbacks.mDisabled1 & 268435456) != 0;
                        if (z4) {
                            Log.d("SecPanelTouchBlockHelper", "isBlockedByDisableExpandOnKeyguard");
                        }
                        if (!z4) {
                            boolean z5 = ((KeyguardStateControllerImpl) ((KeyguardStateController) this.keyguardStateController$delegate.getValue())).mOccluded;
                            if (z5) {
                                Log.d("SecPanelTouchBlockHelper", "isBlockedByKeyguardOccluded");
                            }
                            if (z5) {
                            }
                        }
                    }
                }
            }
        }
        if (z) {
            Log.d("SecPanelTouchBlockHelper", "isKeyguardPanelDisabled");
        }
        return z;
    }
}
