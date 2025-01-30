package com.samsung.android.server.pm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.knox.analytics.service.KnoxAnalyticsSystemService;
import java.util.HashMap;
import java.util.function.BiConsumer;

/* loaded from: classes2.dex */
public class SALoggingHelper {
    public void sendSettingLog(String str, Context context, HashMap hashMap) {
        if (hashMap == null || hashMap.isEmpty()) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            str = "PackageManager";
        }
        Bundle commonBundle = getCommonBundle();
        commonBundle.putString("type", KnoxAnalyticsSystemService.STATUS_PARAMETER_NAME);
        commonBundle.putSerializable("setting", hashMap);
        Intent commonIntent = getCommonIntent();
        commonIntent.putExtras(commonBundle);
        final StringBuilder sb = new StringBuilder(KnoxAnalyticsSystemService.STATUS_PARAMETER_NAME);
        hashMap.forEach(new BiConsumer() { // from class: com.samsung.android.server.pm.SALoggingHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                SALoggingHelper.lambda$sendSettingLog$0(sb, (String) obj, (String) obj2);
            }
        });
        Log.i(str, sb.toString());
        context.sendBroadcast(commonIntent);
    }

    public static /* synthetic */ void lambda$sendSettingLog$0(StringBuilder sb, String str, String str2) {
        sb.append("\n  " + str + " / " + str2);
    }

    public final Bundle getCommonBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("tracking_id", "7IH-399-559998");
        bundle.putString("pkg_name", "System");
        return bundle;
    }

    public final Intent getCommonIntent() {
        Intent intent = new Intent();
        intent.setAction("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
        intent.setPackage("com.sec.android.diagmonagent");
        return intent;
    }
}
