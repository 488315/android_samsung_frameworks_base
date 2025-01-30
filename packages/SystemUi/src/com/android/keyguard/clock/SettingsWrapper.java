package com.android.keyguard.clock;

import android.content.ContentResolver;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.plugins.subscreen.SubRoom;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SettingsWrapper {
    public final ContentResolver mContentResolver;
    public final Migration mMigration;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Migration {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Migrator implements Migration {
        public final ContentResolver mContentResolver;

        public Migrator(ContentResolver contentResolver) {
            this.mContentResolver = contentResolver;
        }
    }

    public SettingsWrapper(ContentResolver contentResolver) {
        this(contentResolver, new Migrator(contentResolver));
    }

    public String decode(String str, int i) {
        if (str == null) {
            return str;
        }
        try {
            try {
                return new JSONObject(str).getString(SubRoom.EXTRA_VALUE_CLOCK);
            } catch (JSONException e) {
                Log.e("ClockFaceSettings", "JSON object does not contain clock field.", e);
                return null;
            }
        } catch (JSONException e2) {
            Log.e("ClockFaceSettings", "Settings value is not valid JSON", e2);
            Migrator migrator = (Migrator) this.mMigration;
            migrator.getClass();
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(SubRoom.EXTRA_VALUE_CLOCK, str);
                Settings.Secure.putStringForUser(migrator.mContentResolver, "lock_screen_custom_clock_face", jSONObject.toString(), i);
            } catch (JSONException e3) {
                Log.e("ClockFaceSettings", "Failed migrating settings value to JSON format", e3);
            }
            return str;
        }
    }

    public SettingsWrapper(ContentResolver contentResolver, Migration migration) {
        this.mContentResolver = contentResolver;
        this.mMigration = migration;
    }
}
