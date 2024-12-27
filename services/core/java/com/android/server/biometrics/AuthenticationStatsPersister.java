package com.android.server.biometrics;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import org.json.JSONObject;

import java.io.File;

public final class AuthenticationStatsPersister {
    public final SharedPreferences mSharedPreferences;

    public AuthenticationStatsPersister(Context context) {
        this.mSharedPreferences =
                context.getSharedPreferences(
                        new File(Environment.getDataSystemDirectory(), "authentication_stats"), 0);
    }

    public static int getIntValue(JSONObject jSONObject, String str, int i) {
        return jSONObject.has(str) ? jSONObject.getInt(str) : i;
    }
}
