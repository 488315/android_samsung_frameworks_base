package com.android.server.pm.pu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Slog;

import com.android.server.PinnerService$$ExternalSyntheticOutline0;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ProfileUtilizationStorage {
    public final SharedPreferences mSharedPrefs;
    public final SharedPreferences mSharedPrefsDumps;

    public ProfileUtilizationStorage(Context context) {
        Context createDeviceProtectedStorageContext = context.createDeviceProtectedStorageContext();
        this.mSharedPrefs =
                createDeviceProtectedStorageContext.getSharedPreferences(
                        new File(
                                new File(
                                        Environment.getDataSystemDirectory(),
                                        "profile_utilization"),
                                "apps_weights"),
                        0);
        this.mSharedPrefsDumps =
                createDeviceProtectedStorageContext.getSharedPreferences(
                        new File(
                                new File(
                                        Environment.getDataSystemDirectory(),
                                        "profile_utilization"),
                                "dumps"),
                        0);
    }

    public final List loadAppsList() {
        String string = this.mSharedPrefs.getString("json_apps_weights", null);
        if (string == null) {
            Slog.w("PU_Storage", "Stored apps not found");
            return Collections.emptyList();
        }
        Slog.d("PU_Storage", "Load ".concat(string));
        try {
            JSONObject jSONObject = new JSONObject(string);
            ArrayList arrayList = new ArrayList(jSONObject.length());
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                long optLong = jSONObject.optLong(next);
                if (optLong == 0) {
                    PinnerService$$ExternalSyntheticOutline0.m(
                            "Skip ", next, " having incorrect weight", "PU_Storage");
                } else {
                    arrayList.add(new ProfileUtilizationService.App(optLong, next));
                    Slog.d("PU_Storage", next + " " + optLong);
                }
            }
            return arrayList;
        } catch (JSONException e) {
            Slog.e("PU_Storage", "Failed to parse json string apps", e);
            return Collections.emptyList();
        }
    }
}
