package com.android.systemui.haptics.slider;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SliderEventType {
    public static final /* synthetic */ SliderEventType[] $VALUES;
    public static final SliderEventType NOTHING;
    public static final SliderEventType PROGRESS_CHANGE_BY_PROGRAM;
    public static final SliderEventType PROGRESS_CHANGE_BY_USER;
    public static final SliderEventType STARTED_TRACKING_PROGRAM;
    public static final SliderEventType STARTED_TRACKING_TOUCH;
    public static final SliderEventType STOPPED_TRACKING_PROGRAM;
    public static final SliderEventType STOPPED_TRACKING_TOUCH;

    static {
        SliderEventType sliderEventType = new SliderEventType("NOTHING", 0);
        NOTHING = sliderEventType;
        SliderEventType sliderEventType2 = new SliderEventType("STARTED_TRACKING_TOUCH", 1);
        STARTED_TRACKING_TOUCH = sliderEventType2;
        SliderEventType sliderEventType3 = new SliderEventType("STARTED_TRACKING_PROGRAM", 2);
        STARTED_TRACKING_PROGRAM = sliderEventType3;
        SliderEventType sliderEventType4 = new SliderEventType("PROGRESS_CHANGE_BY_USER", 3);
        PROGRESS_CHANGE_BY_USER = sliderEventType4;
        SliderEventType sliderEventType5 = new SliderEventType("PROGRESS_CHANGE_BY_PROGRAM", 4);
        PROGRESS_CHANGE_BY_PROGRAM = sliderEventType5;
        SliderEventType sliderEventType6 = new SliderEventType("STOPPED_TRACKING_TOUCH", 5);
        STOPPED_TRACKING_TOUCH = sliderEventType6;
        SliderEventType sliderEventType7 = new SliderEventType("STOPPED_TRACKING_PROGRAM", 6);
        STOPPED_TRACKING_PROGRAM = sliderEventType7;
        SliderEventType[] sliderEventTypeArr = {sliderEventType, sliderEventType2, sliderEventType3, sliderEventType4, sliderEventType5, sliderEventType6, sliderEventType7};
        $VALUES = sliderEventTypeArr;
        EnumEntriesKt.enumEntries(sliderEventTypeArr);
    }

    private SliderEventType(String str, int i) {
    }

    public static SliderEventType valueOf(String str) {
        return (SliderEventType) Enum.valueOf(SliderEventType.class, str);
    }

    public static SliderEventType[] values() {
        return (SliderEventType[]) $VALUES.clone();
    }
}
