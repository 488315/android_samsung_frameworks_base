package androidx.compose.animation.graphics.vector.compat;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
final class ValueType {
    public static final /* synthetic */ ValueType[] $VALUES;
    public static final ValueType Color;
    public static final ValueType Float;
    public static final ValueType Int;
    public static final ValueType Path;

    static {
        ValueType valueType = new ValueType("Float", 0);
        Float = valueType;
        ValueType valueType2 = new ValueType("Int", 1);
        Int = valueType2;
        ValueType valueType3 = new ValueType("Color", 2);
        Color = valueType3;
        ValueType valueType4 = new ValueType("Path", 3);
        Path = valueType4;
        ValueType[] valueTypeArr = {valueType, valueType2, valueType3, valueType4};
        $VALUES = valueTypeArr;
        EnumEntriesKt.enumEntries(valueTypeArr);
    }

    private ValueType(String str, int i) {
    }

    public static ValueType valueOf(String str) {
        return (ValueType) Enum.valueOf(ValueType.class, str);
    }

    public static ValueType[] values() {
        return (ValueType[]) $VALUES.clone();
    }
}
