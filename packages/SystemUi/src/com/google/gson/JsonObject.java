package com.google.gson;

import com.google.gson.internal.LinkedTreeMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class JsonObject extends JsonElement {
    public final LinkedTreeMap members = new LinkedTreeMap();

    public final void add(String str, JsonElement jsonElement) {
        if (jsonElement == null) {
            jsonElement = JsonNull.INSTANCE;
        }
        this.members.put(str, jsonElement);
    }

    public final void addProperty(String str, String str2) {
        add(str, str2 == null ? JsonNull.INSTANCE : new JsonPrimitive(str2));
    }

    public final boolean equals(Object obj) {
        return obj == this || ((obj instanceof JsonObject) && ((JsonObject) obj).members.equals(this.members));
    }

    public final JsonElement get(String str) {
        return (JsonElement) this.members.get(str);
    }

    public final int hashCode() {
        return this.members.hashCode();
    }

    public final void addProperty(String str, Boolean bool) {
        add(str, bool == null ? JsonNull.INSTANCE : new JsonPrimitive(bool));
    }
}
