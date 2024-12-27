package com.android.server.enterprise.storage;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

public final class Column {
    public String mColumnName;
    public DATA_TYPE mColumnType;
    public String mDefaultValue;
    public boolean mIsPrimaryKey;
    public String mProperties;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class DATA_TYPE {
        public static final /* synthetic */ DATA_TYPE[] $VALUES;
        public static final DATA_TYPE BLOB;
        public static final DATA_TYPE INTEGER;
        public static final DATA_TYPE NUMERIC;
        public static final DATA_TYPE REAL;
        public static final DATA_TYPE TEXT;

        static {
            DATA_TYPE data_type = new DATA_TYPE("INTEGER", 0);
            INTEGER = data_type;
            DATA_TYPE data_type2 = new DATA_TYPE("TEXT", 1);
            TEXT = data_type2;
            DATA_TYPE data_type3 = new DATA_TYPE("NUMERIC", 2);
            NUMERIC = data_type3;
            DATA_TYPE data_type4 = new DATA_TYPE("BLOB", 3);
            BLOB = data_type4;
            DATA_TYPE data_type5 = new DATA_TYPE("REAL", 4);
            REAL = data_type5;
            $VALUES = new DATA_TYPE[] {data_type, data_type2, data_type3, data_type4, data_type5};
        }

        public static DATA_TYPE valueOf(String str) {
            return (DATA_TYPE) Enum.valueOf(DATA_TYPE.class, str);
        }

        public static DATA_TYPE[] values() {
            return (DATA_TYPE[]) $VALUES.clone();
        }
    }

    public final String getSQLDeclaration() {
        String m =
                BootReceiver$$ExternalSyntheticOutline0.m(
                        new StringBuilder(), this.mColumnName, " ", this.mColumnType.toString());
        String str = this.mProperties;
        if (str != null) {
            m = AnyMotionDetector$$ExternalSyntheticOutline0.m(m, " ", str);
        }
        String str2 = this.mDefaultValue;
        return str2 != null
                ? AnyMotionDetector$$ExternalSyntheticOutline0.m(m, " DEFAULT ", str2)
                : m;
    }
}
