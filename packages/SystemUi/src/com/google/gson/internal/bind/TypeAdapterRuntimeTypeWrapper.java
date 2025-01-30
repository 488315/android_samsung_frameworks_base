package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter {
    public final Gson context;
    public final TypeAdapter delegate;
    public final Type type;

    public TypeAdapterRuntimeTypeWrapper(Gson gson, TypeAdapter typeAdapter, Type type) {
        this.context = gson;
        this.delegate = typeAdapter;
        this.type = type;
    }

    @Override // com.google.gson.TypeAdapter
    /* renamed from: read */
    public final Object mo2768read(JsonReader jsonReader) {
        return this.delegate.mo2768read(jsonReader);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.reflect.Type] */
    @Override // com.google.gson.TypeAdapter
    public final void write(JsonWriter jsonWriter, Object obj) {
        ?? r0 = this.type;
        Class<?> cls = (obj == null || !(r0 == Object.class || (r0 instanceof TypeVariable) || (r0 instanceof Class))) ? r0 : obj.getClass();
        TypeAdapter typeAdapter = this.delegate;
        if (cls != r0) {
            TypeAdapter adapter = this.context.getAdapter(new TypeToken(cls));
            if (!(adapter instanceof ReflectiveTypeAdapterFactory.Adapter) || (typeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter)) {
                typeAdapter = adapter;
            }
        }
        typeAdapter.write(jsonWriter, obj);
    }
}
