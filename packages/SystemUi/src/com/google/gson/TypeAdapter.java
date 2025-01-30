package com.google.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class TypeAdapter {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.gson.TypeAdapter$1 */
    class C44441 extends TypeAdapter {
        public C44441() {
        }

        @Override // com.google.gson.TypeAdapter
        /* renamed from: read */
        public final Object mo2768read(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return TypeAdapter.this.mo2768read(jsonReader);
            }
            jsonReader.nextNull();
            return null;
        }

        @Override // com.google.gson.TypeAdapter
        public final void write(JsonWriter jsonWriter, Object obj) {
            if (obj == null) {
                jsonWriter.nullValue();
            } else {
                TypeAdapter.this.write(jsonWriter, obj);
            }
        }
    }

    public final TypeAdapter nullSafe() {
        return new C44441();
    }

    /* renamed from: read */
    public abstract Object mo2768read(JsonReader jsonReader);

    public abstract void write(JsonWriter jsonWriter, Object obj);
}
