package com.android.systemui.shared.clocks;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DigitalTimespec {
    public static final /* synthetic */ DigitalTimespec[] $VALUES;
    public static final DigitalTimespec DIGIT_PAIR;
    public static final DigitalTimespec FIRST_DIGIT;
    public static final DigitalTimespec SECOND_DIGIT;
    public static final DigitalTimespec TIME_FULL_FORMAT;

    static {
        DigitalTimespec digitalTimespec = new DigitalTimespec("TIME_FULL_FORMAT", 0);
        TIME_FULL_FORMAT = digitalTimespec;
        DigitalTimespec digitalTimespec2 = new DigitalTimespec("DIGIT_PAIR", 1);
        DIGIT_PAIR = digitalTimespec2;
        DigitalTimespec digitalTimespec3 = new DigitalTimespec("FIRST_DIGIT", 2);
        FIRST_DIGIT = digitalTimespec3;
        DigitalTimespec digitalTimespec4 = new DigitalTimespec("SECOND_DIGIT", 3);
        SECOND_DIGIT = digitalTimespec4;
        DigitalTimespec[] digitalTimespecArr = {digitalTimespec, digitalTimespec2, digitalTimespec3, digitalTimespec4};
        $VALUES = digitalTimespecArr;
        EnumEntriesKt.enumEntries(digitalTimespecArr);
    }

    private DigitalTimespec(String str, int i) {
    }

    public static DigitalTimespec valueOf(String str) {
        return (DigitalTimespec) Enum.valueOf(DigitalTimespec.class, str);
    }

    public static DigitalTimespec[] values() {
        return (DigitalTimespec[]) $VALUES.clone();
    }
}
