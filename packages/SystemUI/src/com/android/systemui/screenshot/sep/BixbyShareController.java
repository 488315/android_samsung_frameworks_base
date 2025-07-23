package com.android.systemui.screenshot.sep;

import android.os.Bundle;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BixbyShareController {
    public static final String TAG;
    public final boolean isBixbyCaptureShared;
    public final String shareActivityName;
    public final String sharePackageName;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TAG = "Screenshot";
    }

    public BixbyShareController(Bundle bundle) {
        String string = bundle != null ? bundle.getString("packageName") : null;
        String str = TAG;
        if (string != null) {
            this.isBixbyCaptureShared = true;
            try {
                JSONArray jSONArray = new JSONArray(string);
                if (jSONArray.length() > 0) {
                    this.shareActivityName = jSONArray.getJSONObject(0).getString("activityName");
                    this.sharePackageName = jSONArray.getJSONObject(1).getString("packageName");
                }
            } catch (JSONException e) {
                Log.e(str, "bixby share exception : " + e);
            }
        }
        Log.i(str, "isBixbyCaptureShared = " + this.isBixbyCaptureShared + " sharePackageName = " + this.sharePackageName + " shareActivityName = " + this.shareActivityName);
    }
}
