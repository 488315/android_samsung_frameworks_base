package com.android.systemui.touchpad.tutorial.ui.view;

import com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger;
import com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger$$ExternalSyntheticLambda0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
final /* synthetic */ class TouchpadTutorialActivity$onCreate$1$1$1$1 extends FunctionReferenceImpl implements Function0 {
    public TouchpadTutorialActivity$onCreate$1$1$1$1(Object obj) {
        super(0, obj, TouchpadTutorialActivity.class, "finishTutorial", "finishTutorial()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        TouchpadTutorialActivity touchpadTutorialActivity = (TouchpadTutorialActivity) this.receiver;
        InputDeviceTutorialLogger inputDeviceTutorialLogger = touchpadTutorialActivity.logger;
        InputDeviceTutorialLogger.TutorialContext tutorialContext = InputDeviceTutorialLogger.TutorialContext.TOUCHPAD_TUTORIAL;
        inputDeviceTutorialLogger.getClass();
        InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda0 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(3);
        LogLevel logLevel = LogLevel.INFO;
        LogBuffer logBuffer = inputDeviceTutorialLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("InputDeviceTutorial", logLevel, inputDeviceTutorialLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = tutorialContext.getString();
        logBuffer.commit(logMessageObtain);
        touchpadTutorialActivity.finish();
        return Unit.INSTANCE;
    }
}
