package com.android.systemui.shade;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecPanelTouchProximityHelper extends BroadcastReceiver {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final CentralSurfaces mCentralSurfaces;
    public final Context mContext;
    public final StatusBarStateController mStatusBarStateController;
    public int mIsSupportProximity = -1;
    public final Handler mHandler = (Handler) Dependency.get(Dependency.MAIN_HANDLER);

    public SecPanelTouchProximityHelper(Context context, StatusBarStateController statusBarStateController, BroadcastDispatcher broadcastDispatcher, CentralSurfaces centralSurfaces) {
        this.mContext = context;
        this.mStatusBarStateController = statusBarStateController;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mCentralSurfaces = centralSurfaces;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Log.i("SecPanelTouchProximityHelper", "ACTION_SCREEN_OFF_BY_PROXIMITY");
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.shade.SecPanelTouchProximityHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SecPanelTouchProximityHelper secPanelTouchProximityHelper = SecPanelTouchProximityHelper.this;
                if (secPanelTouchProximityHelper.mStatusBarStateController.getState() == 0) {
                    Log.i("SecPanelTouchProximityHelper", "Collapsing panel by ACTION_SCREEN_OFF_BY_PROXIMITY");
                    ((CentralSurfacesImpl) secPanelTouchProximityHelper.mCentralSurfaces).postAnimateForceCollapsePanels();
                }
            }
        }, 500L);
    }
}
