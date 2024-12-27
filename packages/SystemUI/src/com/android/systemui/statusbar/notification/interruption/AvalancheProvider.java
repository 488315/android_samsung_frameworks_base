package com.android.systemui.statusbar.notification.interruption;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.popup.util.PopupUIUtil;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.Intrinsics;

public final class AvalancheProvider {
    public final String TAG = "AvalancheProvider";
    public final Set avalancheTriggerIntents = SetsKt__SetsKt.mutableSetOf("android.intent.action.AIRPLANE_MODE", PopupUIUtil.ACTION_BOOT_COMPLETED, "android.intent.action.MANAGED_PROFILE_AVAILABLE", "android.intent.action.USER_SWITCHED");
    public final AvalancheProvider$broadcastReceiver$1 broadcastReceiver;
    public final UiEventLogger uiEventLogger;

    public AvalancheProvider(BroadcastDispatcher broadcastDispatcher, VisualInterruptionDecisionLogger visualInterruptionDecisionLogger, UiEventLogger uiEventLogger) {
        this.uiEventLogger = uiEventLogger;
        new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.interruption.AvalancheProvider$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                if (CollectionsKt___CollectionsKt.contains(AvalancheProvider.this.avalancheTriggerIntents, intent.getAction())) {
                    if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.AIRPLANE_MODE") && intent.getBooleanExtra("state", false)) {
                        Log.d(AvalancheProvider.this.TAG, "broadcastReceiver: ignore airplane mode on");
                        return;
                    }
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("broadcastReceiver received intent.action=", intent.getAction(), AvalancheProvider.this.TAG);
                    AvalancheProvider.this.uiEventLogger.log(AvalancheSuppressor$AvalancheEvent.AVALANCHE_SUPPRESSOR_RECEIVED_TRIGGERING_EVENT);
                    AvalancheProvider avalancheProvider = AvalancheProvider.this;
                    System.currentTimeMillis();
                    avalancheProvider.getClass();
                }
            }
        };
    }
}
