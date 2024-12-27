package com.android.systemui.statusbar.notification.interruption;

import android.content.pm.PackageManager;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderWrapper;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.EventLog;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SystemSettings;
import com.android.systemui.util.time.SystemClock;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.Optional;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class VisualInterruptionDecisionProviderImpl implements VisualInterruptionDecisionProvider {
    public final EventLog eventLog;
    public final GlobalSettings globalSettings;
    public final HeadsUpManager headsUpManager;
    public final VisualInterruptionDecisionLogger logger;
    public final Handler mainHandler;
    public final StatusBarStateController statusBarStateController;
    public final SystemClock systemClock;
    public final SystemSettings systemSettings;

    public VisualInterruptionDecisionProviderImpl(AmbientDisplayConfiguration ambientDisplayConfiguration, BatteryController batteryController, DeviceProvisionedController deviceProvisionedController, EventLog eventLog, GlobalSettings globalSettings, HeadsUpManager headsUpManager, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, KeyguardStateController keyguardStateController, VisualInterruptionDecisionLogger visualInterruptionDecisionLogger, Handler handler, PowerManager powerManager, StatusBarStateController statusBarStateController, SystemClock systemClock, UiEventLogger uiEventLogger, UserTracker userTracker, AvalancheProvider avalancheProvider, SystemSettings systemSettings, PackageManager packageManager, Optional<Bubbles> optional) {
        this.eventLog = eventLog;
        this.globalSettings = globalSettings;
        this.headsUpManager = headsUpManager;
        this.logger = visualInterruptionDecisionLogger;
        this.mainHandler = handler;
        this.statusBarStateController = statusBarStateController;
        this.systemClock = systemClock;
        this.systemSettings = systemSettings;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = VisualInterruptionRefactor.$r8$clinit;
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.visual_interruptions_refactor to be enabled.");
        throw new IllegalStateException("Check failed.".toString());
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public final void addLegacySuppressor(NotificationInterruptSuppressor notificationInterruptSuppressor) {
        throw null;
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public final void logFullScreenIntentDecision(NotificationInterruptStateProviderWrapper.FullScreenIntentDecisionImpl fullScreenIntentDecisionImpl) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("VisualInterruptionDecisionProviderImpl#logFullScreenIntentDecision");
        }
        try {
            throw new IllegalStateException("Check failed.".toString());
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public final VisualInterruptionDecisionProvider.Decision makeAndLogBubbleDecision(NotificationEntry notificationEntry) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("VisualInterruptionDecisionProviderImpl#makeAndLogBubbleDecision");
        }
        try {
            throw new IllegalStateException("Check failed.".toString());
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public final VisualInterruptionDecisionProvider.Decision makeAndLogHeadsUpDecision(NotificationEntry notificationEntry) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("VisualInterruptionDecisionProviderImpl#makeAndLogHeadsUpDecision");
        }
        try {
            throw new IllegalStateException("Check failed.".toString());
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public final NotificationInterruptStateProviderWrapper.FullScreenIntentDecisionImpl makeUnloggedFullScreenIntentDecision(NotificationEntry notificationEntry) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("VisualInterruptionDecisionProviderImpl#makeUnloggedFullScreenIntentDecision");
        }
        try {
            throw new IllegalStateException("Check failed.".toString());
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public final VisualInterruptionDecisionProvider.Decision makeUnloggedHeadsUpDecision(NotificationEntry notificationEntry) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("VisualInterruptionDecisionProviderImpl#makeUnloggedHeadsUpDecision");
        }
        try {
            throw new IllegalStateException("Check failed.".toString());
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public void removeCondition(VisualInterruptionCondition visualInterruptionCondition) {
        throw null;
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public void removeFilter(VisualInterruptionFilter visualInterruptionFilter) {
        throw null;
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public final void removeLegacySuppressor(NotificationInterruptSuppressor notificationInterruptSuppressor) {
        throw null;
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider, com.android.systemui.CoreStartable
    public final void start() {
        new PeekDisabledSuppressor(this.globalSettings, this.headsUpManager, this.logger, this.mainHandler);
        throw null;
    }
}
