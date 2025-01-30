package com.android.server.ibs;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Slog;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;

/* loaded from: classes2.dex */
public class IntelligentBatterySaverSurvey {
    public boolean mBigdataEnable = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE");
    public Context mContext;

    public IntelligentBatterySaverSurvey(Context context) {
        this.mContext = context;
    }

    public void insertLog(String str, String str2, String str3) {
        if (this.mBigdataEnable) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put(LauncherConfigurationInternal.KEY_FEATURE_INT, str2);
            if (str3 != null) {
                contentValues.put("extra", str3);
            }
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.providers.context.log.action.USE_APP_FEATURE_SURVEY");
            intent.putExtra("data", contentValues);
            intent.setPackage("com.samsung.android.providers.context");
            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
        }
        Slog.d("IntelligentBatterySaverSurvey", "app_id = " + str + ", feature = " + str2 + ", extra = " + str3);
    }
}
