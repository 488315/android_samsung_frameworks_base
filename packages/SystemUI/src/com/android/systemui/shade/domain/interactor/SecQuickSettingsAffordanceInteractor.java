package com.android.systemui.shade.domain.interactor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.SecQuickSettingsAffordance;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.AnimHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class SecQuickSettingsAffordanceInteractor {
    public static final int MAX_PANEL_AFFORDANCE_SHOW;
    public final BroadcastDispatcher broadcastDispatcher;
    public final ConfigurationController configurationController;
    public final Context context;
    public final LockscreenShadeTransitionController lockscreenShadeTransitionController;
    public SecQuickSettingsAffordance secQuickSettingsAffordance;
    public final StatusBarStateController statusBarStateController;
    public final SecQuickSettingsAffordanceInteractor$broadcastReceiver$1 broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor$broadcastReceiver$1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (Intrinsics.areEqual(intent != null ? intent.getAction() : null, "android.intent.action.SCREEN_OFF")) {
                Log.d("SecQuickSettingsAffordanceInteractor", "onReceive : ACTION_SCREEN_OFF");
                SecQuickSettingsAffordanceInteractor.access$hideEffect(SecQuickSettingsAffordanceInteractor.this);
                return;
            }
            if (Intrinsics.areEqual(intent != null ? intent.getAction() : null, "android.intent.action.SCREEN_ON")) {
                Log.d("SecQuickSettingsAffordanceInteractor", "onReceive : ACTION_SCREEN_ON");
                if (SecQuickSettingsAffordanceInteractor.this.statusBarStateController.getState() != 1) {
                    SecQuickSettingsAffordanceInteractor.access$hideEffect(SecQuickSettingsAffordanceInteractor.this);
                    return;
                }
                ListPopupWindow$$ExternalSyntheticOutline0.m(Prefs.get(SecQuickSettingsAffordanceInteractor.this.context).getInt("PanelAffordanceCount", 0), "onReceive : ACTION_SCREEN_ON & KEYGUARD : ", "SecQuickSettingsAffordanceInteractor");
                if (Prefs.get(SecQuickSettingsAffordanceInteractor.this.context).getInt("PanelAffordanceCount", 0) >= SecQuickSettingsAffordanceInteractor.MAX_PANEL_AFFORDANCE_SHOW) {
                    SecQuickSettingsAffordanceInteractor secQuickSettingsAffordanceInteractor = SecQuickSettingsAffordanceInteractor.this;
                    if (secQuickSettingsAffordanceInteractor.secQuickSettingsAffordance != null) {
                        secQuickSettingsAffordanceInteractor.statusBarStateController.removeCallback(secQuickSettingsAffordanceInteractor.statusBarStateListener);
                        SecQuickSettingsAffordanceInteractor.this.broadcastDispatcher.unregisterReceiver(this);
                        SecQuickSettingsAffordanceInteractor secQuickSettingsAffordanceInteractor2 = SecQuickSettingsAffordanceInteractor.this;
                        ConfigurationController configurationController = secQuickSettingsAffordanceInteractor2.configurationController;
                        SecQuickSettingsAffordance secQuickSettingsAffordance = secQuickSettingsAffordanceInteractor2.secQuickSettingsAffordance;
                        Intrinsics.checkNotNull(secQuickSettingsAffordance);
                        ((ConfigurationControllerImpl) configurationController).removeCallback(secQuickSettingsAffordance);
                        ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).removeCallback(SecQuickSettingsAffordanceInteractor.this.updateMonitorCallback);
                        SecQuickSettingsAffordanceInteractor secQuickSettingsAffordanceInteractor3 = SecQuickSettingsAffordanceInteractor.this;
                        secQuickSettingsAffordanceInteractor3.lockscreenShadeTransitionController.removeCallback(secQuickSettingsAffordanceInteractor3.transitionCallback);
                        SecQuickSettingsAffordanceInteractor.this.secQuickSettingsAffordance = null;
                        return;
                    }
                }
                final SecQuickSettingsAffordanceInteractor secQuickSettingsAffordanceInteractor4 = SecQuickSettingsAffordanceInteractor.this;
                SecQuickSettingsAffordance secQuickSettingsAffordance2 = secQuickSettingsAffordanceInteractor4.secQuickSettingsAffordance;
                if (secQuickSettingsAffordance2 != null) {
                    secQuickSettingsAffordance2.displayEffect(AnimHelper.AnimationState.SHOWING, new Runnable() { // from class: com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor$broadcastReceiver$1$onReceive$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Context context2 = SecQuickSettingsAffordanceInteractor.this.context;
                            Prefs.putInt(context2, "PanelAffordanceCount", Prefs.get(context2).getInt("PanelAffordanceCount", 0) + 1);
                        }
                    });
                }
            }
        }
    };
    public final SecQuickSettingsAffordanceInteractor$statusBarStateListener$1 statusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor$statusBarStateListener$1
        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onExpandedChanged(boolean z) {
            super.onExpandedChanged(z);
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onExpandedChanged isExpanded = ", "SecQuickSettingsAffordanceInteractor", z);
            if (z) {
                SecQuickSettingsAffordanceInteractor.access$hideEffect(SecQuickSettingsAffordanceInteractor.this);
            }
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStatePreChange(int i, int i2) {
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, i2, "onStatePreChange ", " > ", "SecQuickSettingsAffordanceInteractor");
            if (i2 != 1) {
                SecQuickSettingsAffordanceInteractor.access$hideEffect(SecQuickSettingsAffordanceInteractor.this);
            }
        }
    };
    public final SecQuickSettingsAffordanceInteractor$updateMonitorCallback$1 updateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor$updateMonitorCallback$1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardBouncerStateChanged(boolean z) {
            super.onKeyguardBouncerStateChanged(z);
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onKeyguardBouncerStateChanged : bouncerIsOrWillBeShowing = ", "SecQuickSettingsAffordanceInteractor", z);
            if (z) {
                SecQuickSettingsAffordanceInteractor.access$hideEffect(SecQuickSettingsAffordanceInteractor.this);
            }
        }
    };
    public final SecQuickSettingsAffordanceInteractor$transitionCallback$1 transitionCallback = new LockscreenShadeTransitionController.Callback() { // from class: com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor$transitionCallback$1
        @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
        public final void onExpansionStarted() {
            Log.d("SecQuickSettingsAffordanceInteractor", "onExpansionStarted");
            SecQuickSettingsAffordanceInteractor.access$hideEffect(SecQuickSettingsAffordanceInteractor.this);
        }
    };

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        MAX_PANEL_AFFORDANCE_SHOW = "user".equals(Build.TYPE) ? 10 : 100;
    }

    public SecQuickSettingsAffordanceInteractor(Context context, BroadcastDispatcher broadcastDispatcher, StatusBarStateController statusBarStateController, ConfigurationController configurationController, LockscreenShadeTransitionController lockscreenShadeTransitionController) {
        this.context = context;
        this.broadcastDispatcher = broadcastDispatcher;
        this.statusBarStateController = statusBarStateController;
        this.configurationController = configurationController;
        this.lockscreenShadeTransitionController = lockscreenShadeTransitionController;
    }

    public static final void access$hideEffect(SecQuickSettingsAffordanceInteractor secQuickSettingsAffordanceInteractor) {
        SecQuickSettingsAffordance secQuickSettingsAffordance = secQuickSettingsAffordanceInteractor.secQuickSettingsAffordance;
        if (secQuickSettingsAffordance != null) {
            if (Prefs.get(secQuickSettingsAffordanceInteractor.context).getInt("PanelAffordanceCount", 0) >= MAX_PANEL_AFFORDANCE_SHOW) {
                secQuickSettingsAffordance.isRemoveView = true;
            }
            AnimHelper.AnimationState animationState = AnimHelper.AnimationState.HIDING;
            AnimHelper.AnimationType[] animationTypeArr = SecQuickSettingsAffordance.INIT_PROPERTY_FIELDS;
            secQuickSettingsAffordance.displayEffect(animationState, null);
        }
    }
}
