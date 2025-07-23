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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AvalancheProvider {
    public final Set avalancheTriggerIntents = SetsKt__SetsKt.mutableSetOf("android.intent.action.AIRPLANE_MODE", PopupUIUtil.ACTION_BOOT_COMPLETED, "android.intent.action.MANAGED_PROFILE_AVAILABLE", "android.intent.action.USER_SWITCHED");
    public final AvalancheProvider$broadcastReceiver$1 broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.interruption.AvalancheProvider$broadcastReceiver$1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (CollectionsKt___CollectionsKt.contains(AvalancheProvider.this.avalancheTriggerIntents, intent.getAction())) {
                if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.AIRPLANE_MODE") && intent.getBooleanExtra("state", false)) {
                    AvalancheProvider.this.getClass();
                    Log.d("AvalancheProvider", "broadcastReceiver: ignore airplane mode on");
                    return;
                }
                AvalancheProvider.this.getClass();
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("broadcastReceiver received intent.action=", intent.getAction(), "AvalancheProvider");
                AvalancheProvider.this.uiEventLogger.log(AvalancheSuppressor$AvalancheEvent.AVALANCHE_SUPPRESSOR_RECEIVED_TRIGGERING_EVENT);
                AvalancheProvider avalancheProvider = AvalancheProvider.this;
                System.currentTimeMillis();
                avalancheProvider.getClass();
            }
        }
    };
    public final UiEventLogger uiEventLogger;

    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.statusbar.notification.interruption.AvalancheProvider$broadcastReceiver$1] */
    public AvalancheProvider(BroadcastDispatcher broadcastDispatcher, UiEventLogger uiEventLogger) {
        this.uiEventLogger = uiEventLogger;
    }
}
