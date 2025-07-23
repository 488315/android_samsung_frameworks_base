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

    public boolean isValueNonNull(JsonReader jsonReader) throws IOException {
        return jsonReader.peek() != JsonToken.NULL;
    }

    public Blocklist parseConfigWithJsonReader(InputStream inputStream) {
        try {
            return readJsonConfiguration(new JsonReader(new InputStreamReader(inputStream, "UTF-8")));
        } catch (IOException e) {
            if (!GraphicsRendererPolicy.DEBUG) {
                return null;
            }
            Log.w(TAG, "parseConfigWithJsonReader failed. ", e);
            return null;
        }
    }

    private Blocklist readJsonConfiguration(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        Blocklist blocklist = null;
        while (jsonReader.hasNext()) {
            if (jsonReader.nextName().equalsIgnoreCase("skiaGL") && isValueNonNull(jsonReader)) {
                blocklist = new Blocklist(readBlocklist(jsonReader));
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return blocklist;
    }

    private List<BlockItem> readBlocklist(JsonReader jsonReader) throws IOException {
        ArrayList arrayList = new ArrayList();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            arrayList.add(readBlockItem(jsonReader));
        }
        jsonReader.endArray();
        return arrayList;
    }

    private BlockItem readBlockItem(JsonReader jsonReader) throws IOException {
        List<String> arrayList = new ArrayList<>();
        List<String> arrayList2 = new ArrayList<>();
        List<Integer> arrayList3 = new ArrayList<>();
        jsonReader.beginObject();
        String str = null;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            if (nextName.equalsIgnoreCase("packageID") && isValueNonNull(jsonReader)) {
                str = jsonReader.nextString();
            } else if (nextName.equalsIgnoreCase("modelName") && isValueNonNull(jsonReader)) {
                arrayList = readStringArray(jsonReader);
            } else if (nextName.equalsIgnoreCase("chipsetName") && isValueNonNull(jsonReader)) {
                arrayList2 = readStringArray(jsonReader);
            } else if (nextName.equalsIgnoreCase("osVersion") && isValueNonNull(jsonReader)) {
                arrayList3 = readIntegerArray(jsonReader);
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return new BlockItem(str, arrayList, arrayList2, arrayList3);
    }

    private List<String> readStringArray(JsonReader jsonReader) throws IOException {
        ArrayList arrayList = new ArrayList();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            arrayList.add(jsonReader.nextString());
        }
        jsonReader.endArray();
        return arrayList;
    }

    private List<Integer> readIntegerArray(JsonReader jsonReader) throws IOException {
        ArrayList arrayList = new ArrayList();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            arrayList.add(Integer.valueOf(jsonReader.nextInt()));
        }
        jsonReader.endArray();
        return arrayList;
    }
}
