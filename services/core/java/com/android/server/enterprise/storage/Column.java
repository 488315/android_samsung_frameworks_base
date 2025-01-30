package com.android.server.enterprise.storage;

/* loaded from: classes2.dex */
public class Column {
    public String mColumnName;
    public DATA_TYPE mColumnType;
    public String mDefaultValue;
    public boolean mIsPrimaryKey;
    public String mProperties;

    public enum DATA_TYPE {
        INTEGER,
        TEXT,
        NUMERIC,
        BLOB,
        REAL
    }

    public Column(String str, DATA_TYPE data_type, boolean z, String str2, String str3) {
        this.mColumnName = str;
        this.mColumnType = data_type;
        this.mIsPrimaryKey = z;
        this.mProperties = str2;
        this.mDefaultValue = str3;
    }

    public String getSQLDeclaration() {
        String format = String.format("%s %s", this.mColumnName, this.mColumnType.toString());
        String str = this.mProperties;
        if (str != null) {
            format = String.format("%s %s", format, str);
        }
        String str2 = this.mDefaultValue;
        return str2 != null ? String.format("%s DEFAULT %s", format, str2) : format;
    }
}
