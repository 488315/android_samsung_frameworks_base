package com.android.systemui.statusbar.notification.headsup;

import com.android.systemui.statusbar.notification.headsup.AvalancheController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AvalancheController$logDroppedHunsInBackground$1 implements Runnable {
    public final /* synthetic */ int $numDropped;
    public final /* synthetic */ AvalancheController this$0;

    public AvalancheController$logDroppedHunsInBackground$1(int i, AvalancheController avalancheController) {
        this.$numDropped = i;
        this.this$0 = avalancheController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 1;
        if (1 > this.$numDropped) {
            return;
        }
        while (true) {
            this.this$0.uiEventLogger.log(AvalancheController.ThrottleEvent.AVALANCHE_THROTTLING_HUN_DROPPED);
            if (i == this.$numDropped) {
                return;
            } else {
                i++;
            }
        }
    }
}
