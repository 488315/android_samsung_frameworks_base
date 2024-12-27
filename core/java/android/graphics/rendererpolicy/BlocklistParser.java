package android.graphics.rendererpolicy;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
class BlocklistParser {
    private static final String TAG = "BlocklistParser";
    public static final String UTF_8 = "UTF-8";

    BlocklistParser() {
    }

    public boolean isValueNonNull(JsonReader reader) throws IOException {
        return reader.peek() != JsonToken.NULL;
    }

    public Blocklist parseConfigWithJsonReader(InputStream is) {
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(is, "UTF-8"));
            return readJsonConfiguration(jsonReader);
        } catch (IOException e) {
            if (GraphicsRendererPolicy.DEBUG) {
                Log.w(TAG, "parseConfigWithJsonReader failed. ", e);
                return null;
            }
            return null;
        }
    }

    private Blocklist readJsonConfiguration(JsonReader reader) throws IOException {
        Blocklist blocklist = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equalsIgnoreCase("skiaGL") && isValueNonNull(reader)) {
                blocklist = new Blocklist(readBlocklist(reader));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return blocklist;
    }

    private List<BlockItem> readBlocklist(JsonReader reader) throws IOException {
        List<BlockItem> blockItems = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            blockItems.add(readBlockItem(reader));
        }
        reader.endArray();
        return blockItems;
    }

    private BlockItem readBlockItem(JsonReader reader) throws IOException {
        String packageName = null;
        List<String> modelNames = new ArrayList<>();
        List<String> chipsetNames = new ArrayList<>();
        List<Integer> osVersions = new ArrayList<>();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equalsIgnoreCase("packageID") && isValueNonNull(reader)) {
                packageName = reader.nextString();
            } else if (name.equalsIgnoreCase("modelName") && isValueNonNull(reader)) {
                modelNames = readStringArray(reader);
            } else if (name.equalsIgnoreCase("chipsetName") && isValueNonNull(reader)) {
                chipsetNames = readStringArray(reader);
            } else if (name.equalsIgnoreCase("osVersion") && isValueNonNull(reader)) {
                osVersions = readIntegerArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new BlockItem(packageName, modelNames, chipsetNames, osVersions);
    }

    private List<String> readStringArray(JsonReader reader) throws IOException {
        List<String> strings = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            strings.add(reader.nextString());
        }
        reader.endArray();
        return strings;
    }

    private List<Integer> readIntegerArray(JsonReader reader) throws IOException {
        List<Integer> integers = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            integers.add(Integer.valueOf(reader.nextInt()));
        }
        reader.endArray();
        return integers;
    }
}
