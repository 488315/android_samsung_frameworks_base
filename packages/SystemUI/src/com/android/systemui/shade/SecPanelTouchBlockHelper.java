package com.android.systemui.shade;

import android.app.SemStatusBarManager;
import android.content.Context;
import android.util.Log;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
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
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecPanelTouchBlockHelper {
    public final Lazy centralSurfaces$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecPanelTouchBlockHelper$centralSurfaces$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class);
        }
    });
    public final Lazy keyguardStateController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecPanelTouchBlockHelper$keyguardStateController$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class);
        }
    });
    public final Lazy knoxStateMonitor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecPanelTouchBlockHelper$knoxStateMonitor$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
        }
    });
    public final Lazy statusBarStateController$delegate;
    public final AtomicInteger userChangeInProgress;
    public final SecPanelTouchBlockHelper$userTrackerCallback$1 userTrackerCallback;

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
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.settings.UserTracker$Callback, com.android.systemui.shade.SecPanelTouchBlockHelper$userTrackerCallback$1] */
    public SecPanelTouchBlockHelper(final Context context, UserTracker userTracker, DelayableExecutor delayableExecutor) {
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecPanelTouchBlockHelper$statusBarManager$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object systemService = context.getSystemService("sem_statusbar");
                if (systemService instanceof SemStatusBarManager) {
                    return (SemStatusBarManager) systemService;
                }
                return null;
            }
        });
        this.statusBarStateController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecPanelTouchBlockHelper$statusBarStateController$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
            }
        });
        this.userChangeInProgress = new AtomicInteger(0);
        ?? r2 = new UserTracker.Callback() { // from class: com.android.systemui.shade.SecPanelTouchBlockHelper$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onBeforeUserSwitching(int i) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, SecPanelTouchBlockHelper.this.userChangeInProgress.incrementAndGet(), "onBeforeUserSwitching: ", " : ", "SecPanelTouchBlockHelper");
            }

            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, SecPanelTouchBlockHelper.this.userChangeInProgress.decrementAndGet(), "onUserChanged: ", " : ", "SecPanelTouchBlockHelper");
            }
        };
        this.userTrackerCallback = r2;
        ((UserTrackerImpl) userTracker).addCallback(r2, delayableExecutor);
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

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0066, code lost:
    
        if (r5 != false) goto L28;
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
            if (r0 == 0) goto L69
            int r0 = r0.getState()
            r3 = 1
            if (r0 != r3) goto L69
            boolean r0 = r5.isBlockedByKeyguardAnimating()
            if (r0 != 0) goto L68
            boolean r0 = r5.isBlockedByKnoxPanelExpandDisabled()
            if (r0 != 0) goto L68
            java.util.concurrent.atomic.AtomicInteger r0 = r5.userChangeInProgress
            int r0 = r0.get()
            if (r0 <= 0) goto L2a
            r0 = r3
            goto L2b
        L2a:
            r0 = r1
        L2b:
            if (r0 == 0) goto L32
            java.lang.String r4 = "isBlockedByUserChangeInProgress"
            android.util.Log.d(r2, r4)
        L32:
            if (r0 != 0) goto L68
            kotlin.Lazy r0 = r5.centralSurfaces$delegate
            java.lang.Object r0 = r0.getValue()
            com.android.systemui.statusbar.phone.CentralSurfaces r0 = (com.android.systemui.statusbar.phone.CentralSurfaces) r0
            com.android.systemui.statusbar.phone.CentralSurfacesImpl r0 = (com.android.systemui.statusbar.phone.CentralSurfacesImpl) r0
            com.android.systemui.statusbar.phone.CentralSurfacesCommandQueueCallbacks r0 = r0.mCommandQueueCallbacks
            int r0 = r0.mDisabled1
            r4 = 268435456(0x10000000, float:2.5243549E-29)
            r0 = r0 & r4
            if (r0 == 0) goto L49
            r0 = r3
            goto L4a
        L49:
            r0 = r1
        L4a:
            if (r0 == 0) goto L51
            java.lang.String r4 = "isBlockedByDisableExpandOnKeyguard"
            android.util.Log.d(r2, r4)
        L51:
            if (r0 != 0) goto L68
            kotlin.Lazy r5 = r5.keyguardStateController$delegate
            java.lang.Object r5 = r5.getValue()
            com.android.systemui.statusbar.policy.KeyguardStateController r5 = (com.android.systemui.statusbar.policy.KeyguardStateController) r5
            com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r5 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r5
            boolean r5 = r5.mOccluded
            if (r5 == 0) goto L66
            java.lang.String r0 = "isBlockedByKeyguardOccluded"
            android.util.Log.d(r2, r0)
        L66:
            if (r5 == 0) goto L69
        L68:
            r1 = r3
        L69:
            if (r1 == 0) goto L70
            java.lang.String r5 = "isKeyguardPanelDisabled"
            android.util.Log.d(r2, r5)
        L70:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.SecPanelTouchBlockHelper.isKeyguardPanelDisabled():boolean");
    }
}
