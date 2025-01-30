package com.android.systemui.subscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.log.SecPanelLoggerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CoverPanelIntentReceiver extends BroadcastReceiver implements WakefulnessLifecycle.Observer {
    public final Runnable mCollapsePanel;
    public final IntentFilter mFilter;
    public final Runnable mOnPocketModeChanged;
    public final SecPanelLogger mPanelLogger;
    public boolean mIsInPocket = false;
    public boolean mClockFaceShowing = false;
    public final BroadcastDispatcher mBroadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);
    public final WakefulnessLifecycle mWakefulnessLifecycle = (WakefulnessLifecycle) Dependency.get(WakefulnessLifecycle.class);

    public CoverPanelIntentReceiver(Runnable runnable, Runnable runnable2, SecPanelLogger secPanelLogger) {
        this.mOnPocketModeChanged = runnable;
        this.mCollapsePanel = runnable2;
        this.mPanelLogger = secPanelLogger;
        IntentFilter intentFilter = new IntentFilter();
        this.mFilter = intentFilter;
        intentFilter.addAction("com.samsung.intent.action.KSO_SHOW_POPUP_SUB");
        intentFilter.addAction("com.samsung.intent.action.KSO_CLOSE_POPUP_SUB");
        intentFilter.addAction("com.samsung.android.app.aodservice.ACTION_COVER_HOME_QUICK_PANEL_TOUCH_AREA_CHANGED");
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        boolean z;
        String action = intent.getAction();
        action.getClass();
        z = true;
        switch (action) {
            case "android.intent.action.CLOSE_SYSTEM_DIALOGS":
                ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog("ACTION_CLOSE_SYSTEM_DIALOGS");
                this.mCollapsePanel.run();
                break;
            case "com.samsung.android.app.aodservice.ACTION_COVER_HOME_QUICK_PANEL_TOUCH_AREA_CHANGED":
                this.mClockFaceShowing = intent.getBooleanExtra("isSmallQuickPanelTouchArea", false);
                ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog("CLOCK_FACE: " + this.mClockFaceShowing);
                break;
            case "com.samsung.intent.action.KSO_SHOW_POPUP_SUB":
            case "com.samsung.intent.action.KSO_CLOSE_POPUP_SUB":
                boolean z2 = this.mIsInPocket;
                String action2 = intent.getAction();
                action2.getClass();
                if (action2.equals("com.samsung.intent.action.KSO_SHOW_POPUP_SUB")) {
                    ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog("IN_POCKET_ACTION");
                } else if (action2.equals("com.samsung.intent.action.KSO_CLOSE_POPUP_SUB")) {
                    ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog("NO_POCKET_ACTION");
                    z = false;
                } else {
                    z = z2;
                }
                if (this.mIsInPocket != z) {
                    this.mIsInPocket = z;
                    this.mOnPocketModeChanged.run();
                    break;
                }
                break;
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onStartedGoingToSleep() {
        if (this.mIsInPocket) {
            this.mIsInPocket = false;
            this.mOnPocketModeChanged.run();
        }
    }
}
