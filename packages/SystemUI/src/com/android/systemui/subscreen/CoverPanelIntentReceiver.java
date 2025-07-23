package com.android.systemui.subscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.view.Display;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CoverPanelIntentReceiver extends BroadcastReceiver implements WakefulnessLifecycle.Observer {
    public final Runnable mCollapsePanel;
    public final DisplayManager mDisplayManager;
    public final IntentFilter mFilter;
    public final Runnable mOnPocketModeChanged;
    public boolean mIsInPocket = false;
    public boolean mClockFaceShowing = false;
    public final BroadcastDispatcher mBroadcastDispatcher = (BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class);
    public final WakefulnessLifecycle mWakefulnessLifecycle = (WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class);

    public CoverPanelIntentReceiver(Runnable runnable, Runnable runnable2, Context context) {
        this.mOnPocketModeChanged = runnable;
        this.mCollapsePanel = runnable2;
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        IntentFilter intentFilter = new IntentFilter();
        this.mFilter = intentFilter;
        KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter, "com.samsung.intent.action.KSO_SHOW_POPUP_SUB", "com.samsung.intent.action.KSO_CLOSE_POPUP_SUB", "com.samsung.android.app.aodservice.ACTION_COVER_HOME_QUICK_PANEL_TOUCH_AREA_CHANGED", PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        int i;
        String action = intent.getAction();
        Log.d("CoverPanelIntentReceiver", "Received " + action);
        action.getClass();
        switch (action) {
            case "android.intent.action.SCREEN_OFF":
                for (Display display : this.mDisplayManager.getDisplays()) {
                    if ((display.getFlags() & 131072) != 0) {
                        Log.d("CoverPanelIntentReceiver", "isDesktopWindowing true , display ID:" + display.getDisplayId());
                        SubscreenFlashLightController.getInstance(context).onPowerKeyPressed();
                        break;
                    }
                }
                break;
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
