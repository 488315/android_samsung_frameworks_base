package com.samsung.android.sesl.transparentvideo.renderer.gl.utils;

import com.samsung.android.sesl.transparentvideo.renderer.gl.Debugger;
import com.sec.ims.settings.ImsSettings;
import java.util.Objects;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class DataType {
    public static final /* synthetic */ DataType[] $VALUES;
    public static final DataType FLOAT;
    public static final DataType INT;
    public static final DataType VEC2;
    private final int dataLength;
    private final int glConstantType;

    static {
        DataType dataType = new DataType(ImsSettings.TYPE_INT, 0, 1, 5124);
        INT = dataType;
        DataType dataType2 = new DataType("FLOAT", 1, 1, 5126);
        FLOAT = dataType2;
        DataType dataType3 = new DataType("VEC2", 2, 2, 35664);
        VEC2 = dataType3;
        DataType[] dataTypeArr = {dataType, dataType2, dataType3, new DataType("VEC3", 3, 3, 35665), new DataType("VEC4", 4, 4, 35666), new DataType("MAT3", 5, 9, 35675), new DataType("MAT4", 6, 16, 35676)};
        $VALUES = dataTypeArr;
        EnumEntriesKt.enumEntries(dataTypeArr);
    }

    private DataType(String str, int i, int i2, int i3) {
        this.dataLength = i2;
        this.glConstantType = i3;
    }

    public static DataType valueOf(String str) {
        return (DataType) Enum.valueOf(DataType.class, str);
    }

    public static DataType[] values() {
        return (DataType[]) $VALUES.clone();
    }

    public final void check(Object obj) {
        toString();
        Objects.toString(obj);
        if (obj instanceof Integer) {
            Debugger.Companion.getClass();
            return;
        }
        if (obj instanceof int[]) {
            Debugger.Companion.getClass();
            return;
        }
        if (obj instanceof Float) {
            Debugger.Companion.getClass();
        } else if (obj instanceof float[]) {
            Debugger.Companion.getClass();
        } else {
            Debugger.Companion.getClass();
        }
    }

    public final int getDataLength() {
        return this.dataLength;
    }
}
