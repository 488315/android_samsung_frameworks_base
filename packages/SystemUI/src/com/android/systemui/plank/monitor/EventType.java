package com.android.systemui.plank.monitor;

import kotlin.enums.EnumEntriesKt;

public final class EventType {
    public static final /* synthetic */ EventType[] $VALUES;
    public static final EventType CLICK;
    public static final EventType LONGCLICK;
    public static final EventType SLEEP;
    public static final EventType SWIPE;

    static {
        EventType eventType = new EventType("CLICK", 0);
        CLICK = eventType;
        EventType eventType2 = new EventType("LONGCLICK", 1);
        LONGCLICK = eventType2;
        EventType eventType3 = new EventType("SWIPE", 2);
        SWIPE = eventType3;
        EventType eventType4 = new EventType("SLEEP", 3);
        SLEEP = eventType4;
        EventType[] eventTypeArr = {eventType, eventType2, eventType3, eventType4};
        $VALUES = eventTypeArr;
        EnumEntriesKt.enumEntries(eventTypeArr);
    }

    private EventType(String str, int i) {
    }

    public static EventType valueOf(String str) {
        return (EventType) Enum.valueOf(EventType.class, str);
    }

    public static EventType[] values() {
        return (EventType[]) $VALUES.clone();
    }
}
