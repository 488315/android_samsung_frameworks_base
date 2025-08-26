package com.android.systemui.statusbar.events;

import com.android.systemui.Dumpable;
import com.android.systemui.statusbar.policy.CallbackController;

/* loaded from: classes3.dex */
public interface SystemStatusAnimationScheduler extends CallbackController, Dumpable {
    public static final long DEBOUNCE_DELAY_CONST;

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
