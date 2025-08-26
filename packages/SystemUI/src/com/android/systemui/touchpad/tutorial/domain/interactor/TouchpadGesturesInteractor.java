package com.android.systemui.touchpad.tutorial.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger;
import com.android.systemui.log.ConstantStringsLoggerImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.model.SysUiState;
import com.android.systemui.settings.DisplayTracker;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class TouchpadGesturesInteractor {
    public final CoroutineScope backgroundScope;
    public final DisplayTracker displayTracker;
    public final InputDeviceTutorialLogger logger;
    public final SysUiState sysUiState;

    public TouchpadGesturesInteractor(SysUiState sysUiState, DisplayTracker displayTracker, CoroutineScope coroutineScope, InputDeviceTutorialLogger inputDeviceTutorialLogger) {
        this.sysUiState = sysUiState;
        this.displayTracker = displayTracker;
        this.backgroundScope = coroutineScope;
        this.logger = inputDeviceTutorialLogger;
    }

    public final void disableGestures() {
        ConstantStringsLoggerImpl constantStringsLoggerImpl = this.logger.$$delegate_0;
        constantStringsLoggerImpl.getClass();
        LogBuffer.log$default(constantStringsLoggerImpl.buffer, constantStringsLoggerImpl.tag, LogLevel.DEBUG, "Disabling touchpad gestures across the system");
        CoroutineTracingKt.launchTraced$default(this.backgroundScope, null, null, new TouchpadGesturesInteractor$setGesturesState$1(this, true, null), 7);
    }

    public final void enableGestures() {
        ConstantStringsLoggerImpl constantStringsLoggerImpl = this.logger.$$delegate_0;
        constantStringsLoggerImpl.getClass();
        LogBuffer.log$default(constantStringsLoggerImpl.buffer, constantStringsLoggerImpl.tag, LogLevel.DEBUG, "Enabling touchpad gestures across the system");
        CoroutineTracingKt.launchTraced$default(this.backgroundScope, null, null, new TouchpadGesturesInteractor$setGesturesState$1(this, false, null), 7);
    }
}
