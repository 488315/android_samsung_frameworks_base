package com.google.gson;

import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class JsonParser {
    @Deprecated
    public JsonParser() {
    }

    public static JsonElement parseReader(JsonReader jsonReader) {
        JsonElement jsonElement;
        boolean z = jsonReader.lenient;
        boolean z2 = true;
        jsonReader.lenient = true;
        try {
            try {
                try {
                    try {
                        jsonReader.peek();
                    } catch (EOFException e) {
                        e = e;
                    }
                    try {
                        jsonElement = (JsonElement) TypeAdapters.JSON_ELEMENT.mo2768read(jsonReader);
                    } catch (EOFException e2) {
                        e = e2;
                        z2 = false;
                        if (!z2) {
                            throw new JsonSyntaxException(e);
                        }
                        jsonElement = JsonNull.INSTANCE;
                        return jsonElement;
                    }
                    return jsonElement;
                } catch (OutOfMemoryError e3) {
                    throw new JsonParseException("Failed parsing JSON source: " + jsonReader + " to Json", e3);
                } catch (StackOverflowError e4) {
                    throw new JsonParseException("Failed parsing JSON source: " + jsonReader + " to Json", e4);
                }
            } catch (MalformedJsonException e5) {
                throw new JsonSyntaxException(e5);
            } catch (IOException e6) {
                throw new JsonIOException(e6);
            } catch (NumberFormatException e7) {
                throw new JsonSyntaxException(e7);
            }
        } finally {
            jsonReader.lenient = z;
        }
    }

    public static JsonElement parseString(String str) {
        try {
            JsonReader jsonReader = new JsonReader(new StringReader(str));
            JsonElement parseReader = parseReader(jsonReader);
            parseReader.getClass();
            if (!(parseReader instanceof JsonNull) && jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonSyntaxException("Did not consume the entire document.");
            }
            return parseReader;
        } catch (MalformedJsonException e) {
            throw new JsonSyntaxException(e);
        } catch (IOException e2) {
            throw new JsonIOException(e2);
        } catch (NumberFormatException e3) {
            throw new JsonSyntaxException(e3);
        }
    }
}
