package com.samsung.android.knox.analytics.model;

import android.app.backup.FullBackup;
import com.samsung.android.ims.settings.SemImsProfile;
import com.samsung.android.knox.analytics.database.Contract;
import com.samsung.android.knox.analytics.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class CleanEvent extends JSONObject {
    private static final String TAG = "[KnoxAnalytics] CleanEvent";
    private int counter;
    private long ft;
    private int id;
    private long lt;
    private int rev;
    private int rsn;
    private int rsz;
    private int vid;

    public CleanEvent(String str) throws JSONException {
        super(str);
    }

    public CleanEvent(int i, int i2, int i3, long j, long j2, int i4, int i5, int i6) throws JSONException {
        put("id", i);
        put(Contract.Events.Field.VERSIONING_ID, i2);
        put("c", i3);
        put(SemImsProfile.ImsFeature.FT, j);
        put("lt", j2);
        put("rev", i4);
        put("rsz", i5);
        put("rsn", i6);
    }

    public int getId() {
        try {
            return getInt("id");
        } catch (JSONException e) {
            Log.e(TAG, "getId(): Error getting ID from JSON " + e.getMessage());
            return -1;
        }
    }

    public int getVid() {
        try {
            return getInt(Contract.Events.Field.VERSIONING_ID);
        } catch (JSONException e) {
            Log.e(TAG, "getVid(): Error getting VID from JSON " + e.getMessage());
            return -1;
        }
    }

    public int getCounter() {
        try {
            return getInt("c");
        } catch (JSONException e) {
            Log.e(TAG, "getCounter(): Error getting Counter from JSON " + e.getMessage());
            return -1;
        }
    }

    public long getFt() {
        try {
            return getLong(SemImsProfile.ImsFeature.FT);
        } catch (JSONException e) {
            Log.e(TAG, "getFt(): Error getting Fts from JSON " + e.getMessage());
            return -1L;
        }
    }

    public long getLt() {
        try {
            return getLong("lt");
        } catch (JSONException e) {
            Log.e(TAG, "getLt(): Error getting Lts from JSON " + e.getMessage());
            return -1L;
        }
    }

    public int getRev() {
        try {
            return getInt("rev");
        } catch (JSONException e) {
            Log.e(TAG, "getRev(): Error getting Rev from JSON " + e.getMessage());
            return -1;
        }
    }

    public int getRsz() {
        try {
            return getInt("rsz");
        } catch (JSONException e) {
            Log.e(TAG, "getRsz(): Error getting Rsz from JSON " + e.getMessage());
            return -1;
        }
    }

    public int getRsn() {
        try {
            return getInt("rsn");
        } catch (JSONException e) {
            Log.e(TAG, "getRsn(): Error getting Rsn from JSON " + e.getMessage());
            return -1;
        }
    }

    public String toJsonString() {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("eID", getId());
            jSONObject2.put(FullBackup.FILES_TREE_TOKEN, "KNOX_ANALYTICS");
            jSONObject2.put("sV", 2);
            jSONObject2.put("e", "databaseCleanedV2");
            jSONObject2.put("c", getCounter());
            jSONObject.put(SemImsProfile.ImsFeature.FT, getFt());
            jSONObject.put("lt", getLt());
            jSONObject.put("rev", getRev());
            jSONObject.put("rsz", getRsz());
            jSONObject.put("rsn", getRsn());
            jSONObject2.put("p", jSONObject);
        } catch (JSONException e) {
            Log.e(TAG, "toJsonString(): Failed " + e.getMessage());
        }
        return jSONObject2.toString();
    }

    private static class Fields {
        static final String COUNTER = "c";
        static final String DB_CLEAN_EVENT_EVENT_NAME = "databaseCleanedV2";
        static final String DB_CLEAN_EVENT_FEATURE = "KNOX_ANALYTICS";
        static final String EID = "eID";
        static final String EVENT = "e";
        static final String FEATURE = "f";
        static final String FT = "ft";
        static final String ID = "id";
        static final String LT = "lt";
        static final String PAYLOAD = "p";
        static final String REV = "rev";
        static final String RSN = "rsn";
        static final String RSZ = "rsz";
        static final String SCHEMA_VERSION = "sV";
        static final int SCHEMA_VERSION_VALUE = 2;
        static final String VID = "vid";

        private Fields() {
        }
    }
}
