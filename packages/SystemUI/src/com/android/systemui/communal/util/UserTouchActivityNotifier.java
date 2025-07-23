package com.android.systemui.communal.util;

import android.view.MotionEvent;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class UserTouchActivityNotifier {
    public Long lastNotification;
    public final PowerInteractor powerInteractor;
    public final int rateLimitMs;
    public final CoroutineScope scope;

    public UserTouchActivityNotifier(CoroutineScope coroutineScope, PowerInteractor powerInteractor, int i) {
        this.scope = coroutineScope;
        this.powerInteractor = powerInteractor;
        this.rateLimitMs = i;
    }

    public final void notifyActivity(MotionEvent motionEvent) {
        Long l;
        int action = motionEvent.getAction();
        if (action != 0 && action != 1 && action != 3 && (l = this.lastNotification) != null) {
            if (motionEvent.getEventTime() - l.longValue() < this.rateLimitMs) {
                return;
            }
        }
        this.lastNotification = Long.valueOf(motionEvent.getEventTime());
        BuildersKt.launch$default(this.scope, null, null, new UserTouchActivityNotifier$notifyActivity$2(this, null), 3);
    }
}
