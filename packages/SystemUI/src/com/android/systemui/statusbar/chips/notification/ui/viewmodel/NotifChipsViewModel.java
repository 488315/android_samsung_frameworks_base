package com.android.systemui.statusbar.chips.notification.ui.viewmodel;

import android.content.Context;
import com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor;
import com.android.systemui.util.time.SystemClock;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotifChipsViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Flow chips;
    public final StatusBarNotificationChipsInteractor notifChipsInteractor;
    public final SystemClock systemClock;

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

    public NotifChipsViewModel(Context context, CoroutineScope coroutineScope, StatusBarNotificationChipsInteractor statusBarNotificationChipsInteractor, HeadsUpNotificationInteractor headsUpNotificationInteractor, SystemClock systemClock) {
        this.notifChipsInteractor = statusBarNotificationChipsInteractor;
        this.systemClock = systemClock;
        this.chips = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(statusBarNotificationChipsInteractor.allNotificationChips, headsUpNotificationInteractor.statusBarHeadsUpState, new NotifChipsViewModel$chips$1(this, null)));
    }
}
