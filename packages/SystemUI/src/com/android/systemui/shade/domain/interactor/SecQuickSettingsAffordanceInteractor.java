package com.android.systemui.shade.domain.interactor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.SecQuickSettingsAffordance;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.AnimHelper;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class SecQuickSettingsAffordanceInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BroadcastDispatcher broadcastDispatcher;
    public final ConfigurationController configurationController;
    public final Context context;
    public final DisplayLifecycle displayLifecycle;
    public final KeyguardViewController keyguardViewController;
    public final LockscreenShadeTransitionController lockscreenShadeTransitionController;
    public SecQuickSettingsAffordance secQuickSettingsAffordance;
    public final StatusBarStateController statusBarStateController;
    public final Lazy commandQueue$delegate = LazyKt__LazyJVMKt.lazy(new SecQuickSettingsAffordanceInteractor$$ExternalSyntheticLambda0());
    public final SecQuickSettingsAffordanceInteractor$broadcastReceiver$1 broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor$broadcastReceiver$1
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor$broadcastReceiver$1$onReceive$1] */
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            SecQuickSettingsAffordanceInteractor secQuickSettingsAffordanceInteractor = this.this$0;
            int i = SecQuickSettingsAffordanceInteractor.$r8$clinit;
            if (secQuickSettingsAffordanceInteractor.isEnabled()) {
                if (Intrinsics.areEqual(intent != null ? intent.getAction() : null, "android.intent.action.SCREEN_OFF")) {
                    Log.d("SecQuickSettingsAffordanceInteractor", "onReceive : ACTION_SCREEN_OFF");
                    this.this$0.hideEffect();
                    return;
                }
                if (Intrinsics.areEqual(intent != null ? intent.getAction() : null, "android.intent.action.SCREEN_ON")) {
                    Log.d("SecQuickSettingsAffordanceInteractor", "onReceive : ACTION_SCREEN_ON");
                    if (this.this$0.statusBarStateController.getState() != 1) {
                        this.this$0.hideEffect();
                        return;
                    }
                    ListPopupWindow$$ExternalSyntheticOutline0.m(Prefs.getInt(this.this$0.context, "PanelAffordanceCount", 0), "onReceive : ACTION_SCREEN_ON & KEYGUARD : ", "SecQuickSettingsAffordanceInteractor");
                    if (this.this$0.keyguardViewController.isBouncerShowing()) {
                        Log.d("SecQuickSettingsAffordanceInteractor", "onReceive : isBouncerShowing");
                        this.this$0.hideEffect();
                        return;
                    }
                    if (!this.this$0.isPanelAffordanceAvailableCount()) {
                        SecQuickSettingsAffordanceInteractor secQuickSettingsAffordanceInteractor2 = this.this$0;
                        if (secQuickSettingsAffordanceInteractor2.secQuickSettingsAffordance != null) {
                            secQuickSettingsAffordanceInteractor2.hideEffect();
                            SecQuickSettingsAffordanceInteractor secQuickSettingsAffordanceInteractor3 = this.this$0;
                            secQuickSettingsAffordanceInteractor3.statusBarStateController.removeCallback(secQuickSettingsAffordanceInteractor3.statusBarStateListener);
                            this.this$0.broadcastDispatcher.unregisterReceiver(this);
                            SecQuickSettingsAffordanceInteractor secQuickSettingsAffordanceInteractor4 = this.this$0;
                            ConfigurationController configurationController = secQuickSettingsAffordanceInteractor4.configurationController;
                            SecQuickSettingsAffordance secQuickSettingsAffordance = secQuickSettingsAffordanceInteractor4.secQuickSettingsAffordance;
                            secQuickSettingsAffordance.getClass();
                            ((ConfigurationControllerImpl) configurationController).removeCallback(secQuickSettingsAffordance);
                            ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).removeCallback(this.this$0.updateMonitorCallback);
                            SecQuickSettingsAffordanceInteractor secQuickSettingsAffordanceInteractor5 = this.this$0;
                            secQuickSettingsAffordanceInteractor5.lockscreenShadeTransitionController.removeCallback(secQuickSettingsAffordanceInteractor5.transitionCallback);
                            this.this$0.secQuickSettingsAffordance = null;
                            return;
                        }
                    }
                    final SecQuickSettingsAffordanceInteractor secQuickSettingsAffordanceInteractor6 = this.this$0;
                    SecQuickSettingsAffordance secQuickSettingsAffordance2 = secQuickSettingsAffordanceInteractor6.secQuickSettingsAffordance;
                    if (secQuickSettingsAffordance2 != 0) {
                        secQuickSettingsAffordance2.displayEffect(AnimHelper.AnimationState.SHOWING, new Runnable() { // from class: com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor$broadcastReceiver$1$onReceive$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Context context2 = secQuickSettingsAffordanceInteractor6.context;
                                Prefs.putInt(context2, "PanelAffordanceCount", Prefs.getInt(context2, "PanelAffordanceCount", 0) + 1);
                            }
                        });
                    }
                }
            }
        }
    };
    public final SecQuickSettingsAffordanceInteractor$statusBarStateListener$1 statusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor$statusBarStateListener$1
        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onExpandedChanged(boolean z) {
            int i = SecQuickSettingsAffordanceInteractor.$r8$clinit;
            this.this$0.hideEffectIfNeeded("onExpandedChanged", z);
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStatePreChange(int i, int i2) {
            boolean z = i2 != 1;
            String strM = ListImplementation$$ExternalSyntheticOutline0.m(i, i2, "onStatePreChange ", " > ");
            int i3 = SecQuickSettingsAffordanceInteractor.$r8$clinit;
            this.this$0.hideEffectIfNeeded(strM, z);
        }
    };
    public final SecQuickSettingsAffordanceInteractor$updateMonitorCallback$1 updateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor$updateMonitorCallback$1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onDlsViewModeChanged(int i) {
            boolean z = i == 1;
            int i2 = SecQuickSettingsAffordanceInteractor.$r8$clinit;
            this.this$0.hideEffectIfNeeded("onDlsViewModeChanged", z);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardBouncerStateChanged(boolean z) {
            int i = SecQuickSettingsAffordanceInteractor.$r8$clinit;
            this.this$0.hideEffectIfNeeded("onKeyguardBouncerStateChanged", z);
        }
    };
    public final SecQuickSettingsAffordanceInteractor$transitionCallback$1 transitionCallback = new LockscreenShadeTransitionController.Callback() { // from class: com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor$transitionCallback$1
        @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
        public final void onExpansionStarted() {
            Log.d("SecQuickSettingsAffordanceInteractor", "onExpansionStarted");
            int i = SecQuickSettingsAffordanceInteractor.$r8$clinit;
            this.this$0.hideEffect();
        }
    };

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

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor$broadcastReceiver$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor$statusBarStateListener$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor$updateMonitorCallback$1] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor$transitionCallback$1] */
    public SecQuickSettingsAffordanceInteractor(Context context, BroadcastDispatcher broadcastDispatcher, StatusBarStateController statusBarStateController, ConfigurationController configurationController, LockscreenShadeTransitionController lockscreenShadeTransitionController, DisplayLifecycle displayLifecycle, KeyguardViewController keyguardViewController) {
        this.context = context;
        this.broadcastDispatcher = broadcastDispatcher;
        this.statusBarStateController = statusBarStateController;
        this.configurationController = configurationController;
        this.lockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.displayLifecycle = displayLifecycle;
        this.keyguardViewController = keyguardViewController;
    }

    public final void hideEffect() {
        SecQuickSettingsAffordance secQuickSettingsAffordance = this.secQuickSettingsAffordance;
        if (secQuickSettingsAffordance != null) {
            if (!isPanelAffordanceAvailableCount()) {
                secQuickSettingsAffordance.isRemoveView = true;
            }
            if (secQuickSettingsAffordance.isVisibleView) {
                secQuickSettingsAffordance.displayEffect(AnimHelper.AnimationState.HIDING, null);
            }
        }
    }

    public final void hideEffectIfNeeded(String str, boolean z) {
        if (isEnabled()) {
            Log.d("SecQuickSettingsAffordanceInteractor", str + " enable = " + z);
            if (z) {
                hideEffect();
            }
        }
    }

    public final boolean isEnabled() {
        CommandQueue commandQueue = (CommandQueue) this.commandQueue$delegate.getValue();
        boolean z = (commandQueue == null || commandQueue.panelsEnabled()) ? false : true;
        if (z) {
            Log.d("SecQuickSettingsAffordanceInteractor", "isEnabled: commandQueue?.panelsEnabled() is false");
        }
        if (!z) {
            SecPanelSplitHelper.Companion.getClass();
            boolean z2 = SecPanelSplitHelper.isEnabled;
            if (!z2) {
                Log.d("SecQuickSettingsAffordanceInteractor", "isEnabled: SecPanelSplitHelper.isEnabled is false");
            }
            if (z2) {
                return true;
            }
        }
        return false;
    }

    public final boolean isPanelAffordanceAvailableCount() {
        return Prefs.getInt(this.context, "PanelAffordanceCount", 0) < (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet() ? 15 : 10);
    }
}
