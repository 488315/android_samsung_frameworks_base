package com.google.gson.internal.sql;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final class SqlTimeTypeAdapter extends TypeAdapter {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() { // from class: com.google.gson.internal.sql.SqlTimeTypeAdapter.1
        @Override // com.google.gson.TypeAdapterFactory
        public final TypeAdapter create(Gson gson, TypeToken typeToken) {
            Class cls = typeToken.rawType;
            if (cls == Time.class) {
                return new SqlTimeTypeAdapter();
            }
            return null;
        }
    };
    public final DateFormat format;

    @Override // com.google.gson.TypeAdapter
    /* renamed from: read */
    public final Object mo2768read(JsonReader jsonReader) {
        Time time;
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        String nextString = jsonReader.nextString();
        try {
            synchronized (this) {
                time = new Time(this.format.parse(nextString).getTime());
            }
            return time;
        } catch (ParseException e) {
            StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("Failed parsing '", nextString, "' as SQL Time; at path ");
            m4m.append(jsonReader.getPreviousPath());
            throw new JsonSyntaxException(m4m.toString(), e);
        }
    }

    @Override // com.google.gson.TypeAdapter
    public final void write(JsonWriter jsonWriter, Object obj) {
        String format;
        Time time = (Time) obj;
        if (time == null) {
            jsonWriter.nullValue();
            return;
        }
        synchronized (this) {
            format = this.format.format((Date) time);
        }
        jsonWriter.value(format);
    }

    private SqlTimeTypeAdapter() {
        this.format = new SimpleDateFormat("hh:mm:ss a");
    }
}
