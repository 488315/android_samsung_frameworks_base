package com.android.systemui.inputdevice.tutorial.ui.view;

import com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger;
import com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger$$ExternalSyntheticLambda0;
import com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyboardTouchpadTutorialActivity$onCreate$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyboardTouchpadTutorialActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyboardTouchpadTutorialActivity$onCreate$1(KeyboardTouchpadTutorialActivity keyboardTouchpadTutorialActivity, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyboardTouchpadTutorialActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyboardTouchpadTutorialActivity$onCreate$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyboardTouchpadTutorialActivity$onCreate$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            KeyboardTouchpadTutorialActivity keyboardTouchpadTutorialActivity = this.this$0;
            int i2 = KeyboardTouchpadTutorialActivity.$r8$clinit;
            StateFlowImpl stateFlowImpl = ((KeyboardTouchpadTutorialViewModel) keyboardTouchpadTutorialActivity.vm$delegate.getValue()).closeActivity;
            final KeyboardTouchpadTutorialActivity keyboardTouchpadTutorialActivity2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.inputdevice.tutorial.ui.view.KeyboardTouchpadTutorialActivity$onCreate$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    if (((Boolean) obj2).booleanValue()) {
                        KeyboardTouchpadTutorialActivity keyboardTouchpadTutorialActivity3 = KeyboardTouchpadTutorialActivity.this;
                        InputDeviceTutorialLogger inputDeviceTutorialLogger = keyboardTouchpadTutorialActivity3.logger;
                        InputDeviceTutorialLogger.TutorialContext tutorialContext = InputDeviceTutorialLogger.TutorialContext.KEYBOARD_TOUCHPAD_TUTORIAL;
                        inputDeviceTutorialLogger.getClass();
                        InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda0 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(3);
                        LogLevel logLevel = LogLevel.INFO;
                        LogBuffer logBuffer = inputDeviceTutorialLogger.buffer;
                        LogMessage obtain = logBuffer.obtain("InputDeviceTutorial", logLevel, inputDeviceTutorialLogger$$ExternalSyntheticLambda0, null);
                        ((LogMessageImpl) obtain).str1 = tutorialContext.getString();
                        logBuffer.commit(obtain);
                        keyboardTouchpadTutorialActivity3.finish();
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (stateFlowImpl.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
