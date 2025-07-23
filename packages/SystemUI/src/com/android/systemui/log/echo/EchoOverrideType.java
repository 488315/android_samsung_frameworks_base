package com.android.systemui.log.echo;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class EchoOverrideType {
    public static final /* synthetic */ EchoOverrideType[] $VALUES;
    public static final EchoOverrideType BUFFER;
    public static final EchoOverrideType TAG;

    static {
        EchoOverrideType echoOverrideType = new EchoOverrideType("BUFFER", 0);
        BUFFER = echoOverrideType;
        EchoOverrideType echoOverrideType2 = new EchoOverrideType("TAG", 1);
        TAG = echoOverrideType2;
        EchoOverrideType[] echoOverrideTypeArr = {echoOverrideType, echoOverrideType2};
        $VALUES = echoOverrideTypeArr;
        EnumEntriesKt.enumEntries(echoOverrideTypeArr);
    }

    private EchoOverrideType(String str, int i) {
    }

    public static EchoOverrideType valueOf(String str) {
        return (EchoOverrideType) Enum.valueOf(EchoOverrideType.class, str);
    }

    public static EchoOverrideType[] values() {
        return (EchoOverrideType[]) $VALUES.clone();
    }
}
