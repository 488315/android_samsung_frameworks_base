package com.android.systemui.touchpad.tutorial.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger;
import com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger$$ExternalSyntheticLambda0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.touchpad.tutorial.domain.interactor.TouchpadGesturesInteractor;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class TouchpadTutorialViewModel extends ViewModel {
    public final StateFlowImpl _screen;
    public final TouchpadGesturesInteractor gesturesInteractor;
    public final InputDeviceTutorialLogger logger;
    public final StateFlowImpl screen;

    public final class Factory implements ViewModelProvider.Factory {
        public final TouchpadGesturesInteractor gesturesInteractor;
        public final InputDeviceTutorialLogger logger;

        public Factory(TouchpadGesturesInteractor touchpadGesturesInteractor, InputDeviceTutorialLogger inputDeviceTutorialLogger) {
            this.gesturesInteractor = touchpadGesturesInteractor;
            this.logger = inputDeviceTutorialLogger;
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public final ViewModel create(Class cls) {
            return new TouchpadTutorialViewModel(this.gesturesInteractor, this.logger);
        }
    }

    public TouchpadTutorialViewModel(TouchpadGesturesInteractor touchpadGesturesInteractor, InputDeviceTutorialLogger inputDeviceTutorialLogger) {
        this.gesturesInteractor = touchpadGesturesInteractor;
        this.logger = inputDeviceTutorialLogger;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Screen.TUTORIAL_SELECTION);
        this._screen = stateFlowImplMutableStateFlow;
        this.screen = stateFlowImplMutableStateFlow;
    }

    public final void goTo(Screen screen) {
        InputDeviceTutorialLogger.TutorialContext tutorialContext = InputDeviceTutorialLogger.TutorialContext.TOUCHPAD_TUTORIAL;
        InputDeviceTutorialLogger inputDeviceTutorialLogger = this.logger;
        inputDeviceTutorialLogger.getClass();
        InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda0 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(4);
        LogLevel logLevel = LogLevel.INFO;
        LogBuffer logBuffer = inputDeviceTutorialLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("InputDeviceTutorial", logLevel, inputDeviceTutorialLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = screen.toString();
        logMessageImpl.str2 = tutorialContext.getString();
        logBuffer.commit(logMessageObtain);
        this._screen.updateState(null, screen);
    }
}
