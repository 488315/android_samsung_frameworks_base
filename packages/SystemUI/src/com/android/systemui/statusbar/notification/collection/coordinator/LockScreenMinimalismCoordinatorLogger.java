package com.android.systemui.statusbar.notification.collection.coordinator;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.globalactions.presentation.features.FakeFeatures$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LockScreenMinimalismCoordinatorLogger {
    public static final int $stable = 8;
    private final LogBuffer buffer;

    public LockScreenMinimalismCoordinatorLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logHunHasBeenSeen$lambda$11(LogMessage logMessage) {
        return FakeFeatures$$ExternalSyntheticOutline0.m("Heads up notif has been seen: ", logMessage.getStr1(), " wasUnseen=", logMessage.getBool1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logShadeVisible$lambda$3(LogMessage logMessage) {
        return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Shade expanded. Notifications marked as seen: ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logTopHeadsUpRow$lambda$13(LogMessage logMessage) {
        return FakeFeatures$$ExternalSyntheticOutline0.m("New notif is top heads up: ", logMessage.getStr1(), " wasUnseen=", logMessage.getBool1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logTrackingUnseen$lambda$1(LogMessage logMessage) {
        return (logMessage.getBool1() ? "Start" : "Stop").concat(" tracking unseen notifications because of settings change.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logUnseenAdded$lambda$5(LogMessage logMessage) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Unseen notif added: ", logMessage.getStr1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logUnseenRemoved$lambda$9(LogMessage logMessage) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Unseen notif removed: ", logMessage.getStr1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logUnseenUpdated$lambda$7(LogMessage logMessage) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Unseen notif updated: ", logMessage.getStr1());
    }

    public final void logHunHasBeenSeen(String str, boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("LockScreenMinimalismCoordinator", LogLevel.DEBUG, new LockScreenMinimalismCoordinatorLogger$$ExternalSyntheticLambda0(6), null);
        ((LogMessageImpl) obtain).str1 = str;
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void logShadeHidden() {
        LogBuffer.log$default(this.buffer, "LockScreenMinimalismCoordinator", LogLevel.DEBUG, "Shade no longer expanded.");
    }

    public final void logShadeVisible(int i) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("LockScreenMinimalismCoordinator", LogLevel.DEBUG, new LockScreenMinimalismCoordinatorLogger$$ExternalSyntheticLambda0(4), null);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logTopHeadsUpRow(String str, boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("LockScreenMinimalismCoordinator", LogLevel.DEBUG, new LockScreenMinimalismCoordinatorLogger$$ExternalSyntheticLambda0(0), null);
        ((LogMessageImpl) obtain).str1 = str;
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void logTrackingUnseen(boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("LockScreenMinimalismCoordinator", LogLevel.DEBUG, new LockScreenMinimalismCoordinatorLogger$$ExternalSyntheticLambda0(1), null);
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void logUnseenAdded(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("LockScreenMinimalismCoordinator", LogLevel.DEBUG, new LockScreenMinimalismCoordinatorLogger$$ExternalSyntheticLambda0(2), null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logUnseenRemoved(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("LockScreenMinimalismCoordinator", LogLevel.DEBUG, new LockScreenMinimalismCoordinatorLogger$$ExternalSyntheticLambda0(3), null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logUnseenUpdated(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("LockScreenMinimalismCoordinator", LogLevel.DEBUG, new LockScreenMinimalismCoordinatorLogger$$ExternalSyntheticLambda0(5), null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }
}
