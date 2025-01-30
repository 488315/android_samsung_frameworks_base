package com.google.gson.internal.sql;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.DefaultDateTypeAdapter;
import java.sql.Date;
import java.sql.Timestamp;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SqlTypesSupport {
    public static final C45191 DATE_DATE_TYPE;
    public static final TypeAdapterFactory DATE_FACTORY;
    public static final boolean SUPPORTS_SQL_TYPES;
    public static final C45202 TIMESTAMP_DATE_TYPE;
    public static final TypeAdapterFactory TIMESTAMP_FACTORY;
    public static final TypeAdapterFactory TIME_FACTORY;

    /* JADX WARN: Type inference failed for: r0v3, types: [com.google.gson.internal.sql.SqlTypesSupport$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.google.gson.internal.sql.SqlTypesSupport$2] */
    static {
        boolean z;
        try {
            Class.forName("java.sql.Date");
            z = true;
        } catch (ClassNotFoundException unused) {
            z = false;
        }
        SUPPORTS_SQL_TYPES = z;
        if (z) {
            DATE_DATE_TYPE = new DefaultDateTypeAdapter.DateType(Date.class) { // from class: com.google.gson.internal.sql.SqlTypesSupport.1
                @Override // com.google.gson.internal.bind.DefaultDateTypeAdapter.DateType
                public final java.util.Date deserialize(java.util.Date date) {
                    return new Date(date.getTime());
                }
            };
            TIMESTAMP_DATE_TYPE = new DefaultDateTypeAdapter.DateType(Timestamp.class) { // from class: com.google.gson.internal.sql.SqlTypesSupport.2
                @Override // com.google.gson.internal.bind.DefaultDateTypeAdapter.DateType
                public final java.util.Date deserialize(java.util.Date date) {
                    return new Timestamp(date.getTime());
                }
            };
            DATE_FACTORY = SqlDateTypeAdapter.FACTORY;
            TIME_FACTORY = SqlTimeTypeAdapter.FACTORY;
            TIMESTAMP_FACTORY = SqlTimestampTypeAdapter.FACTORY;
            return;
        }
        DATE_DATE_TYPE = null;
        TIMESTAMP_DATE_TYPE = null;
        DATE_FACTORY = null;
        TIME_FACTORY = null;
        TIMESTAMP_FACTORY = null;
    }

    private SqlTypesSupport() {
    }
}
