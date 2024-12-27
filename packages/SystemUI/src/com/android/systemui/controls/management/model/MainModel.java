package com.android.systemui.controls.management.model;

import kotlin.enums.EnumEntriesKt;

public abstract class MainModel {
    public boolean needToMakeDim;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class Type {
        public static final /* synthetic */ Type[] $VALUES;
        public static final Type COMPONENT;
        public static final Type CONTROL;
        public static final Type SMALL_CONTROL;
        public static final Type STRUCTURE;

        static {
            Type type = new Type("STRUCTURE", 0);
            STRUCTURE = type;
            Type type2 = new Type("CONTROL", 1);
            CONTROL = type2;
            Type type3 = new Type("SMALL_CONTROL", 2);
            SMALL_CONTROL = type3;
            Type type4 = new Type("COMPONENT", 3);
            COMPONENT = type4;
            Type[] typeArr = {type, type2, type3, type4};
            $VALUES = typeArr;
            EnumEntriesKt.enumEntries(typeArr);
        }

        private Type(String str, int i) {
        }

        public static Type valueOf(String str) {
            return (Type) Enum.valueOf(Type.class, str);
        }

        public static Type[] values() {
            return (Type[]) $VALUES.clone();
        }
    }

    public abstract Type getType();
}
