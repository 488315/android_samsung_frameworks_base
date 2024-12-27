package com.android.keyguard.logging;

import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

public final class KeyguardTransitionAnimationLogger {
    public final LogBuffer buffer;

    public KeyguardTransitionAnimationLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logCreate(String str, float f) {
        if (str == null) {
            return;
        }
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardTransitionAnimationLogger$logCreate$2 keyguardTransitionAnimationLogger$logCreate$2 = new Function1() { // from class: com.android.keyguard.logging.KeyguardTransitionAnimationLogger$logCreate$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("[", logMessage.getStr1(), "] starts at: ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardTransitionAnimationLog", logLevel, keyguardTransitionAnimationLogger$logCreate$2, null);
        ((LogMessageImpl) obtain).str1 = str;
        ((LogMessageImpl) obtain).str2 = String.valueOf(f);
        logBuffer.commit(obtain);
    }

    public final void logTransitionStep(String str, TransitionStep transitionStep, Float f) {
        if (str == null) {
            return;
        }
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardTransitionAnimationLogger$logTransitionStep$2 keyguardTransitionAnimationLogger$logTransitionStep$2 = new Function1() { // from class: com.android.keyguard.logging.KeyguardTransitionAnimationLogger$logTransitionStep$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return logMessage.getStr1() + " transitionStep=" + logMessage.getStr2() + ", animationValue=" + logMessage.getStr3();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardTransitionAnimationLog", logLevel, keyguardTransitionAnimationLogger$logTransitionStep$2, null);
        ((LogMessageImpl) obtain).str1 = "[" + str + "][" + transitionStep.transitionState + "]";
        String valueOf = String.valueOf(transitionStep.value);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str2 = valueOf;
        logMessageImpl.str3 = String.valueOf(f);
        logBuffer.commit(obtain);
    }
}
