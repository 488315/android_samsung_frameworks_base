package com.android.systemui.assist;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;

public final class AssistantSessionEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ AssistantSessionEvent[] $VALUES;
    public static final AssistantSessionEvent ASSISTANT_SESSION_CLOSE;
    public static final AssistantSessionEvent ASSISTANT_SESSION_INVOCATION_CANCELLED;
    public static final AssistantSessionEvent ASSISTANT_SESSION_UPDATE;
    private final int id;

    static {
        AssistantSessionEvent assistantSessionEvent = new AssistantSessionEvent("ASSISTANT_SESSION_UNKNOWN", 0, 0);
        AssistantSessionEvent assistantSessionEvent2 = new AssistantSessionEvent("ASSISTANT_SESSION_TIMEOUT_DISMISS", 1, 524);
        AssistantSessionEvent assistantSessionEvent3 = new AssistantSessionEvent("ASSISTANT_SESSION_INVOCATION_START", 2, 525);
        AssistantSessionEvent assistantSessionEvent4 = new AssistantSessionEvent("ASSISTANT_SESSION_INVOCATION_CANCELLED", 3, 526);
        ASSISTANT_SESSION_INVOCATION_CANCELLED = assistantSessionEvent4;
        AssistantSessionEvent assistantSessionEvent5 = new AssistantSessionEvent("ASSISTANT_SESSION_USER_DISMISS", 4, 527);
        AssistantSessionEvent assistantSessionEvent6 = new AssistantSessionEvent("ASSISTANT_SESSION_UPDATE", 5, 528);
        ASSISTANT_SESSION_UPDATE = assistantSessionEvent6;
        AssistantSessionEvent assistantSessionEvent7 = new AssistantSessionEvent("ASSISTANT_SESSION_CLOSE", 6, 529);
        ASSISTANT_SESSION_CLOSE = assistantSessionEvent7;
        AssistantSessionEvent[] assistantSessionEventArr = {assistantSessionEvent, assistantSessionEvent2, assistantSessionEvent3, assistantSessionEvent4, assistantSessionEvent5, assistantSessionEvent6, assistantSessionEvent7};
        $VALUES = assistantSessionEventArr;
        EnumEntriesKt.enumEntries(assistantSessionEventArr);
    }

    private AssistantSessionEvent(String str, int i, int i2) {
        this.id = i2;
    }

    public static AssistantSessionEvent valueOf(String str) {
        return (AssistantSessionEvent) Enum.valueOf(AssistantSessionEvent.class, str);
    }

    public static AssistantSessionEvent[] values() {
        return (AssistantSessionEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this.id;
    }
}
