package com.android.systemui.inputdevice.tutorial;

import com.android.systemui.log.ConstantStringsLoggerImpl;
import com.android.systemui.log.LogBuffer;
import kotlin.enums.EnumEntriesKt;

/* loaded from: classes2.dex */
public final class InputDeviceTutorialLogger {
    public final /* synthetic */ ConstantStringsLoggerImpl $$delegate_0;
    public final LogBuffer buffer;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class TutorialContext {
        public static final /* synthetic */ TutorialContext[] $VALUES;
        public static final TutorialContext KEYBOARD_TOUCHPAD_TUTORIAL;
        public static final TutorialContext TOUCHPAD_TUTORIAL;
        private final String string;

        static {
            TutorialContext tutorialContext = new TutorialContext("KEYBOARD_TOUCHPAD_TUTORIAL", 0, "keyboard touchpad tutorial");
            KEYBOARD_TOUCHPAD_TUTORIAL = tutorialContext;
            TutorialContext tutorialContext2 = new TutorialContext("TOUCHPAD_TUTORIAL", 1, "touchpad tutorial");
            TOUCHPAD_TUTORIAL = tutorialContext2;
            TutorialContext[] tutorialContextArr = {tutorialContext, tutorialContext2};
            $VALUES = tutorialContextArr;
            EnumEntriesKt.enumEntries(tutorialContextArr);
        }

        private TutorialContext(String str, int i, String str2) {
            this.string = str2;
        }

        public static TutorialContext valueOf(String str) {
            return (TutorialContext) Enum.valueOf(TutorialContext.class, str);
        }

        public static TutorialContext[] values() {
            return (TutorialContext[]) $VALUES.clone();
        }

        public final String getString() {
            return this.string;
        }
    }

    public InputDeviceTutorialLogger(LogBuffer logBuffer) {
        this.$$delegate_0 = new ConstantStringsLoggerImpl(logBuffer, "InputDeviceTutorial");
        this.buffer = logBuffer;
    }
}
