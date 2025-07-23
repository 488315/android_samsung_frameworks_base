package com.samsung.android.sdk.scs.ai.visual.c2pa;

import com.android.systemui.util.SystemUIAnalytics;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class DataFieldDeserializer implements JsonDeserializer<Object> {
    @Override // com.google.gson.JsonDeserializer
    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        if (jsonElement == null) {
            return null;
        }
        if (jsonElement.isJsonObject()) {
            jsonElement = jsonElement.getAsJsonObject().getAsJsonArray(SystemUIAnalytics.QPNE_VID_ACTIONS);
        } else if (!jsonElement.isJsonArray()) {
            jsonElement = null;
        }
        return new Data(jsonDeserializationContext != null ? (List) jsonDeserializationContext.deserialize(jsonElement, new TypeToken<List<? extends Action>>() { // from class: com.samsung.android.sdk.scs.ai.visual.c2pa.DataFieldDeserializer$deserialize$actionsList$1
        }.getType()) : null);
    }
}
