package com.android.systemui.shade.domain.interactor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class SecPanelTouchProximityInteractor {
    public final BroadcastDispatcher broadcastDispatcher;
    public final SecPanelTouchProximityInteractor$broadcastReceiver$1 broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.shade.domain.interactor.SecPanelTouchProximityInteractor$broadcastReceiver$1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (Intrinsics.areEqual(intent != null ? intent.getAction() : null, "android.intent.action.ACTION_SCREEN_OFF_BY_PROXIMITY")) {
                Log.d("SecPanelTouchProximityInteractor", "onReceive(): ACTION_SCREEN_OFF_BY_PROXIMITY");
                if (SecPanelTouchProximityInteractor.this.statusBarStateController.getState() == 0 && ((Boolean) ((ShadeInteractorImpl) SecPanelTouchProximityInteractor.this.shadeInteractor).baseShadeInteractor.isAnyExpanded().getValue()).booleanValue()) {
                    Runnable runnable = SecPanelTouchProximityInteractor.this.postAnimateForceCollapseShadeRunnable;
                    if (runnable != null) {
                        runnable.run();
                        Unit unit = Unit.INSTANCE;
                    }
                    Log.d("SecPanelTouchProximityInteractor", "postAnimateForceCollapseShadeRunnable()");
                }
            }
        }
    };
    public final Context context;
    public Runnable postAnimateForceCollapseShadeRunnable;
    public final ShadeInteractor shadeInteractor;
    public final StatusBarStateController statusBarStateController;

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

    public SecPanelTouchProximityInteractor(BroadcastDispatcher broadcastDispatcher, Context context, StatusBarStateController statusBarStateController, ShadeInteractor shadeInteractor) {
        this.broadcastDispatcher = broadcastDispatcher;
        this.context = context;
        this.statusBarStateController = statusBarStateController;
        this.shadeInteractor = shadeInteractor;
    }
}
