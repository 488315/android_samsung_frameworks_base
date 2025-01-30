package com.google.gson;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class JsonNull extends JsonElement {
    public static final JsonNull INSTANCE = new JsonNull();

    @Deprecated
    public JsonNull() {
    }

    public final boolean equals(Object obj) {
        return this == obj || (obj instanceof JsonNull);
    }

    public final int hashCode() {
        return JsonNull.class.hashCode();
    }
}
