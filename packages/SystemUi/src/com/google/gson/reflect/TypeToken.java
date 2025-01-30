package com.google.gson.reflect;

import com.google.gson.internal.C$Gson$Types;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class TypeToken<T> {
    public final int hashCode;
    public final Class rawType;
    public final Type type;

    public TypeToken() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        Type canonicalize = C$Gson$Types.canonicalize(((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
        this.type = canonicalize;
        this.rawType = C$Gson$Types.getRawType(canonicalize);
        this.hashCode = canonicalize.hashCode();
    }

    public final boolean equals(Object obj) {
        if (obj instanceof TypeToken) {
            if (C$Gson$Types.equals(this.type, ((TypeToken) obj).type)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.hashCode;
    }

    public final String toString() {
        return C$Gson$Types.typeToString(this.type);
    }

    public TypeToken(Type type) {
        type.getClass();
        Type canonicalize = C$Gson$Types.canonicalize(type);
        this.type = canonicalize;
        this.rawType = C$Gson$Types.getRawType(canonicalize);
        this.hashCode = canonicalize.hashCode();
    }
}
