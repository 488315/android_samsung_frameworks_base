package com.google.gson.internal.sql;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.sql.Timestamp;
import java.util.Date;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
class SqlTimestampTypeAdapter extends TypeAdapter {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() { // from class: com.google.gson.internal.sql.SqlTimestampTypeAdapter.1
        @Override // com.google.gson.TypeAdapterFactory
        public final TypeAdapter create(Gson gson, TypeToken typeToken) {
            if (typeToken.rawType != Timestamp.class) {
                return null;
            }
            gson.getClass();
            return new SqlTimestampTypeAdapter(gson.getAdapter(new TypeToken(Date.class)));
        }
    };
    public final TypeAdapter dateTypeAdapter;

    @Override // com.google.gson.TypeAdapter
    /* renamed from: read */
    public final Object mo2768read(JsonReader jsonReader) {
        Date date = (Date) this.dateTypeAdapter.mo2768read(jsonReader);
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return null;
    }

    @Override // com.google.gson.TypeAdapter
    public final void write(JsonWriter jsonWriter, Object obj) {
        this.dateTypeAdapter.write(jsonWriter, (Timestamp) obj);
    }

    private SqlTimestampTypeAdapter(TypeAdapter typeAdapter) {
        this.dateTypeAdapter = typeAdapter;
    }
}
