package com.android.systemui.shade.domain.interactor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeControllerImpl$$ExternalSyntheticLambda0;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecPanelTouchProximityInteractor {
    public final BroadcastDispatcher broadcastDispatcher;
    public final SecPanelTouchProximityInteractor$broadcastReceiver$1 broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.shade.domain.interactor.SecPanelTouchProximityInteractor$broadcastReceiver$1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (Intrinsics.areEqual(intent != null ? intent.getAction() : null, "android.intent.action.ACTION_SCREEN_OFF_BY_PROXIMITY")) {
                Log.d("SecPanelTouchProximityInteractor", "onReceive(): ACTION_SCREEN_OFF_BY_PROXIMITY");
                if (SecPanelTouchProximityInteractor.this.statusBarStateController.getState() == 0 && ((Boolean) ((ShadeInteractorImpl) SecPanelTouchProximityInteractor.this.shadeInteractor).baseShadeInteractor.isAnyExpanded().getValue()).booleanValue()) {
                    ShadeControllerImpl$$ExternalSyntheticLambda0 shadeControllerImpl$$ExternalSyntheticLambda0 = SecPanelTouchProximityInteractor.this.postAnimateForceCollapseShadeRunnable;
                    if (shadeControllerImpl$$ExternalSyntheticLambda0 != null) {
                        shadeControllerImpl$$ExternalSyntheticLambda0.run();
                        Unit unit = Unit.INSTANCE;
                    }
                    Log.d("SecPanelTouchProximityInteractor", "postAnimateForceCollapseShadeRunnable()");
                }
            }
        }
    };
    public final Context context;
    public ShadeControllerImpl$$ExternalSyntheticLambda0 postAnimateForceCollapseShadeRunnable;
    public final ShadeInteractor shadeInteractor;
    public final StatusBarStateController statusBarStateController;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.shade.domain.interactor.SecPanelTouchProximityInteractor$broadcastReceiver$1] */
    public SecPanelTouchProximityInteractor(BroadcastDispatcher broadcastDispatcher, Context context, StatusBarStateController statusBarStateController, ShadeInteractor shadeInteractor) {
        this.broadcastDispatcher = broadcastDispatcher;
        this.context = context;
        this.statusBarStateController = statusBarStateController;
        this.shadeInteractor = shadeInteractor;
    }
}
