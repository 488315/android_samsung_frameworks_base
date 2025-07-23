package com.android.systemui.statusbar.notification.stack.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AccessibilityScrollEvent {
    public static final /* synthetic */ AccessibilityScrollEvent[] $VALUES;
    public static final AccessibilityScrollEvent SCROLL_DOWN;
    public static final AccessibilityScrollEvent SCROLL_UP;

    static {
        AccessibilityScrollEvent accessibilityScrollEvent = new AccessibilityScrollEvent("SCROLL_UP", 0);
        SCROLL_UP = accessibilityScrollEvent;
        AccessibilityScrollEvent accessibilityScrollEvent2 = new AccessibilityScrollEvent("SCROLL_DOWN", 1);
        SCROLL_DOWN = accessibilityScrollEvent2;
        AccessibilityScrollEvent[] accessibilityScrollEventArr = {accessibilityScrollEvent, accessibilityScrollEvent2};
        $VALUES = accessibilityScrollEventArr;
        EnumEntriesKt.enumEntries(accessibilityScrollEventArr);
    }

    private AccessibilityScrollEvent(String str, int i) {
    }

    public static AccessibilityScrollEvent valueOf(String str) {
        return (AccessibilityScrollEvent) Enum.valueOf(AccessibilityScrollEvent.class, str);
    }

    public static AccessibilityScrollEvent[] values() {
        return (AccessibilityScrollEvent[]) $VALUES.clone();
    }
}
