package com.android.systemui.assist;

import com.android.internal.logging.UiEventLogger;
import com.samsung.android.knox.custom.CustomDeviceManager;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class AssistantInvocationEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ AssistantInvocationEvent[] $VALUES;
    public static final AssistantInvocationEvent ASSISTANT_INVOCATION_HOME_LONG_PRESS;
    public static final AssistantInvocationEvent ASSISTANT_INVOCATION_HOTWORD;
    public static final AssistantInvocationEvent ASSISTANT_INVOCATION_PHYSICAL_GESTURE;
    public static final AssistantInvocationEvent ASSISTANT_INVOCATION_POWER_LONG_PRESS;
    public static final AssistantInvocationEvent ASSISTANT_INVOCATION_QUICK_SEARCH_BAR;
    public static final AssistantInvocationEvent ASSISTANT_INVOCATION_START_PHYSICAL_GESTURE;
    public static final AssistantInvocationEvent ASSISTANT_INVOCATION_START_TOUCH_GESTURE;
    public static final AssistantInvocationEvent ASSISTANT_INVOCATION_START_UNKNOWN;
    public static final AssistantInvocationEvent ASSISTANT_INVOCATION_TOUCH_GESTURE;
    public static final AssistantInvocationEvent ASSISTANT_INVOCATION_UNKNOWN;
    public static final Companion Companion;
    private final int id;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        AssistantInvocationEvent assistantInvocationEvent = new AssistantInvocationEvent("ASSISTANT_INVOCATION_UNKNOWN", 0, CustomDeviceManager.MULTI_WINDOW_PERCENTAGE);
        ASSISTANT_INVOCATION_UNKNOWN = assistantInvocationEvent;
        AssistantInvocationEvent assistantInvocationEvent2 = new AssistantInvocationEvent("ASSISTANT_INVOCATION_TOUCH_GESTURE", 1, 443);
        ASSISTANT_INVOCATION_TOUCH_GESTURE = assistantInvocationEvent2;
        AssistantInvocationEvent assistantInvocationEvent3 = new AssistantInvocationEvent("ASSISTANT_INVOCATION_TOUCH_GESTURE_ALT", 2, 444);
        AssistantInvocationEvent assistantInvocationEvent4 = new AssistantInvocationEvent("ASSISTANT_INVOCATION_HOTWORD", 3, 445);
        ASSISTANT_INVOCATION_HOTWORD = assistantInvocationEvent4;
        AssistantInvocationEvent assistantInvocationEvent5 = new AssistantInvocationEvent("ASSISTANT_INVOCATION_QUICK_SEARCH_BAR", 4, 446);
        ASSISTANT_INVOCATION_QUICK_SEARCH_BAR = assistantInvocationEvent5;
        AssistantInvocationEvent assistantInvocationEvent6 = new AssistantInvocationEvent("ASSISTANT_INVOCATION_HOME_LONG_PRESS", 5, 447);
        ASSISTANT_INVOCATION_HOME_LONG_PRESS = assistantInvocationEvent6;
        AssistantInvocationEvent assistantInvocationEvent7 = new AssistantInvocationEvent("ASSISTANT_INVOCATION_PHYSICAL_GESTURE", 6, 448);
        ASSISTANT_INVOCATION_PHYSICAL_GESTURE = assistantInvocationEvent7;
        AssistantInvocationEvent assistantInvocationEvent8 = new AssistantInvocationEvent("ASSISTANT_INVOCATION_START_UNKNOWN", 7, 530);
        ASSISTANT_INVOCATION_START_UNKNOWN = assistantInvocationEvent8;
        AssistantInvocationEvent assistantInvocationEvent9 = new AssistantInvocationEvent("ASSISTANT_INVOCATION_START_TOUCH_GESTURE", 8, 531);
        ASSISTANT_INVOCATION_START_TOUCH_GESTURE = assistantInvocationEvent9;
        AssistantInvocationEvent assistantInvocationEvent10 = new AssistantInvocationEvent("ASSISTANT_INVOCATION_START_PHYSICAL_GESTURE", 9, 532);
        ASSISTANT_INVOCATION_START_PHYSICAL_GESTURE = assistantInvocationEvent10;
        AssistantInvocationEvent assistantInvocationEvent11 = new AssistantInvocationEvent("ASSISTANT_INVOCATION_POWER_LONG_PRESS", 10, 758);
        ASSISTANT_INVOCATION_POWER_LONG_PRESS = assistantInvocationEvent11;
        AssistantInvocationEvent[] assistantInvocationEventArr = {assistantInvocationEvent, assistantInvocationEvent2, assistantInvocationEvent3, assistantInvocationEvent4, assistantInvocationEvent5, assistantInvocationEvent6, assistantInvocationEvent7, assistantInvocationEvent8, assistantInvocationEvent9, assistantInvocationEvent10, assistantInvocationEvent11};
        $VALUES = assistantInvocationEventArr;
        EnumEntriesKt.enumEntries(assistantInvocationEventArr);
        Companion = new Companion(null);
    }

    private AssistantInvocationEvent(String str, int i, int i2) {
        this.id = i2;
    }

    public static AssistantInvocationEvent valueOf(String str) {
        return (AssistantInvocationEvent) Enum.valueOf(AssistantInvocationEvent.class, str);
    }

    public static AssistantInvocationEvent[] values() {
        return (AssistantInvocationEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this.id;
    }
}
