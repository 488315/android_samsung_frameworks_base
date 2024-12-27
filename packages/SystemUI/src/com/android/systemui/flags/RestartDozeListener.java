package com.android.systemui.flags;

import android.os.PowerManager;
import com.android.systemui.flags.RestartDozeListener;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.time.SystemClock;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RestartDozeListener {
    public static final Companion Companion = new Companion(null);
    public static final String RESTART_SLEEP_KEY = "restart_nap_after_start";
    public final DelayableExecutor bgExecutor;
    public boolean inited;
    public final RestartDozeListener$listener$1 listener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.flags.RestartDozeListener$listener$1
        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onDozingChanged(final boolean z) {
            RestartDozeListener.Companion companion = RestartDozeListener.Companion;
            final RestartDozeListener restartDozeListener = RestartDozeListener.this;
            restartDozeListener.getClass();
            restartDozeListener.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.flags.RestartDozeListener$storeSleepState$1
                @Override // java.lang.Runnable
                public final void run() {
                    SecureSettings secureSettings = RestartDozeListener.this.settings;
                    RestartDozeListener.Companion.getClass();
                    secureSettings.putBool(RestartDozeListener.RESTART_SLEEP_KEY, z);
                }
            });
        }
    };
    public final PowerManager powerManager;
    public final SecureSettings settings;
    public final StatusBarStateController statusBarStateController;
    public final SystemClock systemClock;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getRESTART_SLEEP_KEY$annotations() {
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.flags.RestartDozeListener$listener$1] */
    public RestartDozeListener(SecureSettings secureSettings, StatusBarStateController statusBarStateController, PowerManager powerManager, SystemClock systemClock, DelayableExecutor delayableExecutor) {
        this.settings = secureSettings;
        this.statusBarStateController = statusBarStateController;
        this.powerManager = powerManager;
        this.systemClock = systemClock;
        this.bgExecutor = delayableExecutor;
    }
}
