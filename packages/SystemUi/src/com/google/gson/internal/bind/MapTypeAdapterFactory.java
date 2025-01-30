package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MapTypeAdapterFactory implements TypeAdapterFactory {
    public final boolean complexMapKeySerialization;
    public final ConstructorConstructor constructorConstructor;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Adapter<K, V> extends TypeAdapter {
        public final ObjectConstructor constructor;
        public final TypeAdapter keyTypeAdapter;
        public final TypeAdapter valueTypeAdapter;

        public Adapter(Gson gson, Type type, TypeAdapter typeAdapter, Type type2, TypeAdapter typeAdapter2, ObjectConstructor objectConstructor) {
            this.keyTypeAdapter = new TypeAdapterRuntimeTypeWrapper(gson, typeAdapter, type);
            this.valueTypeAdapter = new TypeAdapterRuntimeTypeWrapper(gson, typeAdapter2, type2);
            this.constructor = objectConstructor;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.gson.TypeAdapter
        /* renamed from: read */
        public final Object mo2768read(JsonReader jsonReader) {
            JsonToken peek = jsonReader.peek();
            if (peek == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            Map map = (Map) this.constructor.construct();
            JsonToken jsonToken = JsonToken.BEGIN_ARRAY;
            TypeAdapter typeAdapter = this.valueTypeAdapter;
            TypeAdapter typeAdapter2 = this.keyTypeAdapter;
            if (peek == jsonToken) {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    jsonReader.beginArray();
                    Object mo2768read = typeAdapter2.mo2768read(jsonReader);
                    if (map.put(mo2768read, typeAdapter.mo2768read(jsonReader)) != null) {
                        throw new JsonSyntaxException("duplicate key: " + mo2768read);
                    }
                    jsonReader.endArray();
                }
                jsonReader.endArray();
            } else {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    JsonReaderInternalAccess.INSTANCE.getClass();
                    if (jsonReader instanceof JsonTreeReader) {
                        JsonTreeReader jsonTreeReader = (JsonTreeReader) jsonReader;
                        jsonTreeReader.expect(JsonToken.NAME);
                        Map.Entry entry = (Map.Entry) ((Iterator) jsonTreeReader.peekStack()).next();
                        jsonTreeReader.push(entry.getValue());
                        jsonTreeReader.push(new JsonPrimitive((String) entry.getKey()));
                    } else {
                        int i = jsonReader.peeked;
                        if (i == 0) {
                            i = jsonReader.doPeek();
                        }
                        if (i == 13) {
                            jsonReader.peeked = 9;
                        } else if (i == 12) {
                            jsonReader.peeked = 8;
                        } else {
                            if (i != 14) {
                                throw new IllegalStateException("Expected a name but was " + jsonReader.peek() + jsonReader.locationString());
                            }
                            jsonReader.peeked = 10;
                        }
                    }
                    Object mo2768read2 = typeAdapter2.mo2768read(jsonReader);
                    if (map.put(mo2768read2, typeAdapter.mo2768read(jsonReader)) != null) {
                        throw new JsonSyntaxException("duplicate key: " + mo2768read2);
                    }
                }
                jsonReader.endObject();
            }
            return map;
        }

        @Override // com.google.gson.TypeAdapter
        public final void write(JsonWriter jsonWriter, Object obj) {
            String str;
            Map map = (Map) obj;
            if (map == null) {
                jsonWriter.nullValue();
                return;
            }
            boolean z = MapTypeAdapterFactory.this.complexMapKeySerialization;
            TypeAdapter typeAdapter = this.valueTypeAdapter;
            if (!z) {
                jsonWriter.beginObject();
                for (Map.Entry<K, V> entry : map.entrySet()) {
                    jsonWriter.name(String.valueOf(entry.getKey()));
                    typeAdapter.write(jsonWriter, entry.getValue());
                }
                jsonWriter.endObject();
                return;
            }
            ArrayList arrayList = new ArrayList(map.size());
            ArrayList arrayList2 = new ArrayList(map.size());
            int i = 0;
            boolean z2 = false;
            for (Map.Entry<K, V> entry2 : map.entrySet()) {
                TypeAdapter typeAdapter2 = this.keyTypeAdapter;
                K key = entry2.getKey();
                typeAdapter2.getClass();
                try {
                    JsonTreeWriter jsonTreeWriter = new JsonTreeWriter();
                    typeAdapter2.write(jsonTreeWriter, key);
                    if (!((ArrayList) jsonTreeWriter.stack).isEmpty()) {
                        throw new IllegalStateException("Expected one JSON element but was " + jsonTreeWriter.stack);
                    }
                    JsonElement jsonElement = jsonTreeWriter.product;
                    arrayList.add(jsonElement);
                    arrayList2.add(entry2.getValue());
                    jsonElement.getClass();
                    z2 |= (jsonElement instanceof JsonArray) || (jsonElement instanceof JsonObject);
                } catch (IOException e) {
                    throw new JsonIOException(e);
                }
            }
            if (z2) {
                jsonWriter.beginArray();
                int size = arrayList.size();
                while (i < size) {
                    jsonWriter.beginArray();
                    TypeAdapters.JSON_ELEMENT.write(jsonWriter, (JsonElement) arrayList.get(i));
                    typeAdapter.write(jsonWriter, arrayList2.get(i));
                    jsonWriter.endArray();
                    i++;
                }
                jsonWriter.endArray();
                return;
            }
            jsonWriter.beginObject();
            int size2 = arrayList.size();
            while (i < size2) {
                JsonElement jsonElement2 = (JsonElement) arrayList.get(i);
                jsonElement2.getClass();
                boolean z3 = jsonElement2 instanceof JsonPrimitive;
                if (z3) {
                    if (!z3) {
                        throw new IllegalStateException("Not a JSON Primitive: " + jsonElement2);
                    }
                    JsonPrimitive jsonPrimitive = (JsonPrimitive) jsonElement2;
                    Object obj2 = jsonPrimitive.value;
                    if (obj2 instanceof Number) {
                        str = String.valueOf(jsonPrimitive.getAsNumber());
                    } else if (obj2 instanceof Boolean) {
                        str = Boolean.toString(jsonPrimitive.getAsBoolean());
                    } else {
                        if (!(obj2 instanceof String)) {
                            throw new AssertionError();
                        }
                        str = jsonPrimitive.getAsString();
                    }
                } else {
                    if (!(jsonElement2 instanceof JsonNull)) {
                        throw new AssertionError();
                    }
                    str = "null";
                }
                jsonWriter.name(str);
                typeAdapter.write(jsonWriter, arrayList2.get(i));
                i++;
            }
            jsonWriter.endObject();
        }
    }

    public MapTypeAdapterFactory(ConstructorConstructor constructorConstructor, boolean z) {
        this.constructorConstructor = constructorConstructor;
        this.complexMapKeySerialization = z;
    }

    @Override // com.google.gson.TypeAdapterFactory
    public final TypeAdapter create(Gson gson, TypeToken typeToken) {
        Type[] actualTypeArguments;
        Type type = typeToken.type;
        if (!Map.class.isAssignableFrom(typeToken.rawType)) {
            return null;
        }
        Class rawType = C$Gson$Types.getRawType(type);
        if (type == Properties.class) {
            actualTypeArguments = new Type[]{String.class, String.class};
        } else {
            Type supertype = C$Gson$Types.getSupertype(type, rawType, Map.class);
            actualTypeArguments = supertype instanceof ParameterizedType ? ((ParameterizedType) supertype).getActualTypeArguments() : new Type[]{Object.class, Object.class};
        }
        Type type2 = actualTypeArguments[0];
        return new Adapter(gson, actualTypeArguments[0], (type2 == Boolean.TYPE || type2 == Boolean.class) ? TypeAdapters.BOOLEAN_AS_STRING : gson.getAdapter(new TypeToken(type2)), actualTypeArguments[1], gson.getAdapter(new TypeToken(actualTypeArguments[1])), this.constructorConstructor.get(typeToken));
    }
}
