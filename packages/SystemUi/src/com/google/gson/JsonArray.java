package com.google.gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class JsonArray extends JsonElement implements Iterable {
    public final List elements;

    public JsonArray() {
        this.elements = new ArrayList();
    }

    public final void add(JsonElement jsonElement) {
        if (jsonElement == null) {
            jsonElement = JsonNull.INSTANCE;
        }
        ((ArrayList) this.elements).add(jsonElement);
    }

    public final boolean equals(Object obj) {
        return obj == this || ((obj instanceof JsonArray) && ((JsonArray) obj).elements.equals(this.elements));
    }

    @Override // com.google.gson.JsonElement
    public final boolean getAsBoolean() {
        if (((ArrayList) this.elements).size() == 1) {
            return ((JsonElement) ((ArrayList) this.elements).get(0)).getAsBoolean();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public final int getAsInt() {
        if (((ArrayList) this.elements).size() == 1) {
            return ((JsonElement) ((ArrayList) this.elements).get(0)).getAsInt();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public final String getAsString() {
        if (((ArrayList) this.elements).size() == 1) {
            return ((JsonElement) ((ArrayList) this.elements).get(0)).getAsString();
        }
        throw new IllegalStateException();
    }

    public final int hashCode() {
        return this.elements.hashCode();
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return this.elements.iterator();
    }

    public JsonArray(int i) {
        this.elements = new ArrayList(i);
    }
}
