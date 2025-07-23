package com.android.systemui.qs.pipeline.data.domain.interactor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.android.systemui.Prefs;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.util.DeviceState;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ResetTilesInteractorImpl implements ResetTilesInteractor {
    public final CurrentTilesInteractor currentTileInteractor;
    public final CurrentTilesInteractor qqsInteractor;
    public final CurrentTilesInteractor subQsInteractor;

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

    public ResetTilesInteractorImpl(CurrentTilesInteractor currentTilesInteractor, CurrentTilesInteractor currentTilesInteractor2, CurrentTilesInteractor currentTilesInteractor3, BroadcastDispatcher broadcastDispatcher, Context context) {
        this.currentTileInteractor = currentTilesInteractor;
        this.qqsInteractor = currentTilesInteractor2;
        this.subQsInteractor = currentTilesInteractor3;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.pipeline.data.domain.interactor.ResetTilesInteractorImpl$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent != null ? intent.getAction() : null;
                if (StringsKt__StringsJVMKt.equals(action, "com.samsung.intent.action.SETTINGS_SOFT_RESET", false)) {
                    Log.d("ResetTilesInteractor", "softResetStarted()");
                    Prefs.putInt(context2, "QsWifiCallingTileIndex", -1);
                    ResetTilesInteractorImpl.this.currentTileInteractor.resetTiles();
                    ResetTilesInteractorImpl.this.qqsInteractor.resetTiles();
                    ResetTilesInteractorImpl.this.subQsInteractor.resetTiles();
                    return;
                }
                if (StringsKt__StringsJVMKt.equals(action, "com.samsung.sea.rm.DEMO_RESET_STARTED", false) && DeviceState.isShopDemo(context2)) {
                    Log.d("ResetTilesInteractor", "demoResetStarted()");
                    ResetTilesInteractorImpl.this.currentTileInteractor.resetTiles();
                    ResetTilesInteractorImpl.this.qqsInteractor.resetTiles();
                    ResetTilesInteractorImpl.this.subQsInteractor.resetTiles();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.intent.action.SETTINGS_SOFT_RESET");
        intentFilter.addAction("com.samsung.sea.rm.DEMO_RESET_STARTED");
        context.registerReceiver(broadcastReceiver, intentFilter, 2);
    }
}
