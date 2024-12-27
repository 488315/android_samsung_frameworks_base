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

    public Event(String s) throws JSONException {
        super(s);
    }

    public Event(int id, int vid, int bulk, String data) throws JSONException {
        put("id", id);
        put(Contract.Events.Field.VERSIONING_ID, vid);
        put("bulk", bulk);
        put("data", data);
    }

    public int getId() {
        try {
            int id = getInt("id");
            return id;
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getVid() {
        try {
            int vid = getInt(Contract.Events.Field.VERSIONING_ID);
            return vid;
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getBulk() {
        try {
            int bulk = getInt("bulk");
            return bulk;
        } catch (JSONException e) {
            e.printStackTrace();
            return 1;
        }
    }

    public String getData() {
        try {
            String data = getString("data");
            return data;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // org.json.JSONObject
    public int getInt(String name) throws JSONException {
        if (name.equals("bulk") && !has(name)) {
            return 1;
        }
        return super.getInt(name);
    }

    private static class Fields {
        static final String BULK = "bulk";
        static final String DATA = "data";
        static final String ID = "id";
        static final String VID = "vid";

        private Fields() {}
    }
}
