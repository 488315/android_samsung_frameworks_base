package com.android.systemui.qs.external;

import android.service.quicksettings.Tile;
import org.json.JSONObject;

public abstract class CustomTileStatePersisterKt {
    public static final Tile readTileFromString(String str) {
        JSONObject jSONObject = new JSONObject(str);
        Tile tile = new Tile();
        tile.setState(jSONObject.getInt("state"));
        tile.setLabel(jSONObject.has("label") ? jSONObject.getString("label") : null);
        tile.setSubtitle(jSONObject.has("subtitle") ? jSONObject.getString("subtitle") : null);
        tile.setContentDescription(jSONObject.has("content_description") ? jSONObject.getString("content_description") : null);
        tile.setStateDescription(jSONObject.has("state_description") ? jSONObject.getString("state_description") : null);
        return tile;
    }

    public static final String writeToString(Tile tile) {
        return new JSONObject().put("state", tile.getState()).put("label", tile.getCustomLabel()).put("subtitle", tile.getSubtitle()).put("content_description", tile.getContentDescription()).put("state_description", tile.getStateDescription()).toString();
    }
}
