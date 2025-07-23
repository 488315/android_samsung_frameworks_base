package com.android.systemui.qs.tiles.dialog;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.notification.modes.EnableDndDialogMetricsLogger;
import com.android.systemui.qs.QSDndEvent;
import com.android.systemui.qs.QSEvents;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class QSEnableDndDialogMetricsLogger extends EnableDndDialogMetricsLogger {
    public final UiEventLogger mUiEventLogger;

    public QSEnableDndDialogMetricsLogger(Context context) {
        super(context);
        QSEvents.INSTANCE.getClass();
        this.mUiEventLogger = QSEvents.qsUiEventsLogger;
    }

    @Override // com.android.settingslib.notification.modes.EnableDndDialogMetricsLogger
    public final void logOnClickTimeButton(boolean z) {
        super.logOnClickTimeButton(z);
        this.mUiEventLogger.log(z ? QSDndEvent.QS_DND_TIME_UP : QSDndEvent.QS_DND_TIME_DOWN);
    }

    @Override // com.android.settingslib.notification.modes.EnableDndDialogMetricsLogger
    public final void logOnConditionSelected() {
        super.logOnConditionSelected();
        this.mUiEventLogger.log(QSDndEvent.QS_DND_CONDITION_SELECT);
    }

    @Override // com.android.settingslib.notification.modes.EnableDndDialogMetricsLogger
    public final void logOnEnableZenModeForever() {
        super.logOnEnableZenModeForever();
        this.mUiEventLogger.log(QSDndEvent.QS_DND_DIALOG_ENABLE_FOREVER);
    }

    @Override // com.android.settingslib.notification.modes.EnableDndDialogMetricsLogger
    public final void logOnEnableZenModeUntilAlarm() {
        super.logOnEnableZenModeUntilAlarm();
        this.mUiEventLogger.log(QSDndEvent.QS_DND_DIALOG_ENABLE_UNTIL_ALARM);
    }

    @Override // com.android.settingslib.notification.modes.EnableDndDialogMetricsLogger
    public final void logOnEnableZenModeUntilCountdown() {
        super.logOnEnableZenModeUntilCountdown();
        this.mUiEventLogger.log(QSDndEvent.QS_DND_DIALOG_ENABLE_UNTIL_COUNTDOWN);
    }
}
