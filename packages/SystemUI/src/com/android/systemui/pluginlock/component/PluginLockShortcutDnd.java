package com.android.systemui.pluginlock.component;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.service.notification.Condition;
import android.service.notification.ZenModeConfig;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.LogUtil;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class PluginLockShortcutDnd extends PluginLockShortcutTask implements ZenModeController.Callback {
    private static final String TAG = "PluginLockShortcutDnd";
    private final PluginLockMediator mMediator;
    private final ZenModeController mZenModeController;

    public PluginLockShortcutDnd(Context context, PluginLockMediator pluginLockMediator) {
        super(context);
        ZenModeController zenModeController = (ZenModeController) Dependency.sDependency.getDependencyInner(ZenModeController.class);
        this.mZenModeController = zenModeController;
        ((ZenModeControllerImpl) zenModeController).addCallback(this);
        this.mMediator = pluginLockMediator;
    }

    private int setZenState(int i) {
        return i == 1 ? 0 : 1;
    }

    @Override // com.android.systemui.pluginlock.component.PluginLockShortcutTask
    public void excute() {
        LogUtil.d(TAG, "excute", new Object[0]);
        ZenModeController zenModeController = this.mZenModeController;
        ((ZenModeControllerImpl) zenModeController).setZen(setZenState(((ZenModeControllerImpl) zenModeController).mZenMode), null, "Keyguard");
    }

    @Override // com.android.systemui.pluginlock.component.PluginLockShortcutTask
    public String getAppLabel() {
        return "";
    }

    @Override // com.android.systemui.pluginlock.component.PluginLockShortcutTask
    public Drawable getDrawble() {
        return this.mContext.getResources().getDrawable(R.drawable.fg_do_not_disturb_off);
    }

    @Override // com.android.systemui.pluginlock.component.PluginLockShortcutTask
    public boolean isEnabled() {
        return ((ZenModeControllerImpl) this.mZenModeController).mZenMode == 1;
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public void onZenChanged(int i) {
        LogUtil.d(TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onZenChanged [zen] "), new Object[0]);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEnable", isEnabled());
        Bundle bundle2 = new Bundle();
        bundle2.putString("action", PluginLockShortcutTask.GET_LOCKSTAR_TASK_SHORTCUT_STATE);
        bundle2.putString("arg", PluginLockShortcutTask.DO_NOT_DISTURB_TASK);
        bundle2.putBundle("extras", bundle);
        this.mMediator.onEventReceived(bundle2);
    }

    @Override // com.android.systemui.pluginlock.component.PluginLockShortcutTask
    public void removeListener() {
        ((ZenModeControllerImpl) this.mZenModeController).removeCallback(this);
    }

    @Override // com.android.systemui.pluginlock.component.PluginLockShortcutTask
    public void setState(boolean z) {
        LogUtil.d(TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("setState [state] ", z), new Object[0]);
        ((ZenModeControllerImpl) this.mZenModeController).setZen(z ? 1 : 0, null, "Keyguard");
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public /* bridge */ /* synthetic */ void onEffectsSupressorChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public /* bridge */ /* synthetic */ void onNextAlarmChanged() {
    }

    public /* bridge */ /* synthetic */ void onConditionsChanged(Condition[] conditionArr) {
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public /* bridge */ /* synthetic */ void onConfigChanged(ZenModeConfig zenModeConfig) {
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public /* bridge */ /* synthetic */ void onConsolidatedPolicyChanged(NotificationManager.Policy policy) {
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public /* bridge */ /* synthetic */ void onManualRuleChanged(ZenModeConfig.ZenRule zenRule) {
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public /* bridge */ /* synthetic */ void onZenAvailableChanged(boolean z) {
    }
}
