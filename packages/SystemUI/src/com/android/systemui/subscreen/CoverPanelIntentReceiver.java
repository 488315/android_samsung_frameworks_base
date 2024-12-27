package com.android.systemui.subscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.popup.util.PopupUIUtil;

public final class CoverPanelIntentReceiver extends BroadcastReceiver implements WakefulnessLifecycle.Observer {
    public final Runnable mCollapsePanel;
    public final IntentFilter mFilter;
    public final Runnable mOnPocketModeChanged;
    public boolean mIsInPocket = false;
    public boolean mClockFaceShowing = false;
    public final BroadcastDispatcher mBroadcastDispatcher = (BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class);
    public final WakefulnessLifecycle mWakefulnessLifecycle = (WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class);

    public CoverPanelIntentReceiver(Runnable runnable, Runnable runnable2) {
        this.mOnPocketModeChanged = runnable;
        this.mCollapsePanel = runnable2;
        IntentFilter intentFilter = new IntentFilter();
        this.mFilter = intentFilter;
        intentFilter.addAction("com.samsung.intent.action.KSO_SHOW_POPUP_SUB");
        intentFilter.addAction("com.samsung.intent.action.KSO_CLOSE_POPUP_SUB");
        intentFilter.addAction("com.samsung.android.app.aodservice.ACTION_COVER_HOME_QUICK_PANEL_TOUCH_AREA_CHANGED");
        intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        action.getClass();
        switch (action) {
            case "android.intent.action.CLOSE_SYSTEM_DIALOGS":
                this.mCollapsePanel.run();
                break;
            case "com.samsung.android.app.aodservice.ACTION_COVER_HOME_QUICK_PANEL_TOUCH_AREA_CHANGED":
                this.mClockFaceShowing = intent.getBooleanExtra("isSmallQuickPanelTouchArea", false);
                break;
            case "com.samsung.intent.action.KSO_SHOW_POPUP_SUB":
            case "com.samsung.intent.action.KSO_CLOSE_POPUP_SUB":
                boolean z = this.mIsInPocket;
                String action2 = intent.getAction();
                action2.getClass();
                boolean z2 = action2.equals("com.samsung.intent.action.KSO_SHOW_POPUP_SUB") ? true : !action2.equals("com.samsung.intent.action.KSO_CLOSE_POPUP_SUB") ? z : false;
                if (this.mIsInPocket != z2) {
                    this.mIsInPocket = z2;
                    this.mOnPocketModeChanged.run();
                    break;
                }
                break;
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onStartedGoingToSleep() {
        this.mIsInPocket = false;
    }
}
