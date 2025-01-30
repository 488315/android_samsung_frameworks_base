package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.ToNumberPolicy;
import com.google.gson.ToNumberStrategy;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ObjectTypeAdapter extends TypeAdapter {
    public static final TypeAdapterFactory DOUBLE_FACTORY = new C44761(ToNumberPolicy.DOUBLE);
    public final Gson gson;
    public final ToNumberStrategy toNumberStrategy;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.gson.internal.bind.ObjectTypeAdapter$1 */
    class C44761 implements TypeAdapterFactory {
        public final /* synthetic */ ToNumberStrategy val$toNumberStrategy;

        public C44761(ToNumberStrategy toNumberStrategy) {
            this.val$toNumberStrategy = toNumberStrategy;
        }

        @Override // com.google.gson.TypeAdapterFactory
        public final TypeAdapter create(Gson gson, TypeToken typeToken) {
            C44761 c44761 = null;
            if (typeToken.rawType == Object.class) {
                return new ObjectTypeAdapter(gson, this.val$toNumberStrategy, c44761);
            }
            return null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.gson.internal.bind.ObjectTypeAdapter$2 */
    public abstract /* synthetic */ class AbstractC44772 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$gson$stream$JsonToken;

        static {
            int[] iArr = new int[JsonToken.values().length];
            $SwitchMap$com$google$gson$stream$JsonToken = iArr;
            try {
                iArr[JsonToken.BEGIN_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.BEGIN_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.NUMBER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.BOOLEAN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.NULL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public /* synthetic */ ObjectTypeAdapter(Gson gson, ToNumberStrategy toNumberStrategy, C44761 c44761) {
        this(gson, toNumberStrategy);
    }

    public static TypeAdapterFactory getFactory(ToNumberStrategy toNumberStrategy) {
        return toNumberStrategy == ToNumberPolicy.DOUBLE ? DOUBLE_FACTORY : new C44761(toNumberStrategy);
    }

    @Override // com.google.gson.TypeAdapter
    /* renamed from: read */
    public final Object mo2768read(JsonReader jsonReader) {
        switch (AbstractC44772.$SwitchMap$com$google$gson$stream$JsonToken[jsonReader.peek().ordinal()]) {
            case 1:
                ArrayList arrayList = new ArrayList();
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    arrayList.add(mo2768read(jsonReader));
                }
                jsonReader.endArray();
                return arrayList;
            case 2:
                LinkedTreeMap linkedTreeMap = new LinkedTreeMap();
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    linkedTreeMap.put(jsonReader.nextName(), mo2768read(jsonReader));
                }
                jsonReader.endObject();
                return linkedTreeMap;
            case 3:
                return jsonReader.nextString();
            case 4:
                return this.toNumberStrategy.readNumber(jsonReader);
            case 5:
                return Boolean.valueOf(jsonReader.nextBoolean());
            case 6:
                jsonReader.nextNull();
                return null;
            default:
                throw new IllegalStateException();
        }
    }

    @Override // com.google.gson.TypeAdapter
    public final void write(JsonWriter jsonWriter, Object obj) {
        if (obj == null) {
            jsonWriter.nullValue();
            return;
        }
        Class<?> cls = obj.getClass();
        Gson gson = this.gson;
        gson.getClass();
        TypeAdapter adapter = gson.getAdapter(new TypeToken(cls));
        if (!(adapter instanceof ObjectTypeAdapter)) {
            adapter.write(jsonWriter, obj);
        } else {
            jsonWriter.beginObject();
            jsonWriter.endObject();
        }
    }

    private ObjectTypeAdapter(Gson gson, ToNumberStrategy toNumberStrategy) {
        this.gson = gson;
        this.toNumberStrategy = toNumberStrategy;
    }
}
