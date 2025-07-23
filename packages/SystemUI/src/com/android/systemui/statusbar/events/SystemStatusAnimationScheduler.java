package com.android.systemui.statusbar.events;

import com.android.systemui.Dumpable;
import com.android.systemui.statusbar.policy.CallbackController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface SystemStatusAnimationScheduler extends CallbackController, Dumpable {
    public static final long DEBOUNCE_DELAY_CONST;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Companion();
        }

        private Companion() {
        }
    }

    static {
        int i = Companion.$r8$clinit;
        DEBOUNCE_DELAY_CONST = 1000L;
    }
}
