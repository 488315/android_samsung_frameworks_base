package com.android.systemui.qs.tiles.dialog;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.notification.ZenModeDialogMetricsLogger;
import com.android.systemui.qs.QSDndEvent;
import com.android.systemui.qs.QSEvents;

public final class QSZenModeDialogMetricsLogger extends ZenModeDialogMetricsLogger {
    public final UiEventLogger mUiEventLogger;

    public QSZenModeDialogMetricsLogger(Context context) {
        super(context);
        QSEvents.INSTANCE.getClass();
        this.mUiEventLogger = QSEvents.qsUiEventsLogger;
    }

    @Override // com.android.settingslib.notification.ZenModeDialogMetricsLogger
    public final void logOnClickTimeButton(boolean z) {
        super.logOnClickTimeButton(z);
        this.mUiEventLogger.log(z ? QSDndEvent.QS_DND_TIME_UP : QSDndEvent.QS_DND_TIME_DOWN);
    }

    @Override // com.android.settingslib.notification.ZenModeDialogMetricsLogger
    public final void logOnConditionSelected() {
        super.logOnConditionSelected();
        this.mUiEventLogger.log(QSDndEvent.QS_DND_CONDITION_SELECT);
    }

    @Override // com.android.settingslib.notification.ZenModeDialogMetricsLogger
    public final void logOnEnableZenModeForever() {
        super.logOnEnableZenModeForever();
        this.mUiEventLogger.log(QSDndEvent.QS_DND_DIALOG_ENABLE_FOREVER);
    }

    @Override // com.android.settingslib.notification.ZenModeDialogMetricsLogger
    public final void logOnEnableZenModeUntilAlarm() {
        super.logOnEnableZenModeUntilAlarm();
        this.mUiEventLogger.log(QSDndEvent.QS_DND_DIALOG_ENABLE_UNTIL_ALARM);
    }

    @Override // com.android.settingslib.notification.ZenModeDialogMetricsLogger
    public final void logOnEnableZenModeUntilCountdown() {
        super.logOnEnableZenModeUntilCountdown();
        this.mUiEventLogger.log(QSDndEvent.QS_DND_DIALOG_ENABLE_UNTIL_COUNTDOWN);
    }
}
