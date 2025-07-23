package com.samsung.android.knox.analytics.model;

import com.samsung.android.knox.analytics.database.Contract;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class Event extends JSONObject {
    private static final int DEFAULT_BULK_VALUE = 1;
    private int bulk;
    private String data;
    private int id;
    private int vid;

    public Event(String str) throws JSONException {
        super(str);
    }

    public Event(int i, int i2, int i3, String str) throws JSONException {
        put("id", i);
        put(Contract.Events.Field.VERSIONING_ID, i2);
        put("bulk", i3);
        put("data", str);
    }

    public int getId() {
        try {
            return getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getVid() {
        try {
            return getInt(Contract.Events.Field.VERSIONING_ID);
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getBulk() {
        try {
            return getInt("bulk");
        } catch (JSONException e) {
            e.printStackTrace();
            return 1;
        }
    }

    public String getData() {
        try {
            return getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // org.json.JSONObject
    public int getInt(String str) throws JSONException {
        if (!str.equals("bulk") || has(str)) {
            return super.getInt(str);
        }
        return 1;
    }

    private static class Fields {
        static final String BULK = "bulk";
        static final String DATA = "data";
        static final String ID = "id";
        static final String VID = "vid";

        private Fields() {
        }
    }
}
