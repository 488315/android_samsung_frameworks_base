package com.samsung.android.server.pm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.knox.analytics.service.KnoxAnalyticsSystemService;

import java.util.HashMap;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class SALoggingHelper {
    public static void sendSettingLog(Context context, HashMap hashMap) {
        if (hashMap.isEmpty()) {
            return;
        }
        String str = TextUtils.isEmpty("RoleLogger") ? "PackageManager" : "RoleLogger";
        Bundle bundle = new Bundle();
        bundle.putString("tracking_id", "7IH-399-559998");
        bundle.putString("pkg_name", "System");
        bundle.putString("type", KnoxAnalyticsSystemService.STATUS_PARAMETER_NAME);
        bundle.putSerializable("setting", hashMap);
        Intent intent = new Intent();
        intent.setAction("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
        intent.setPackage("com.sec.android.diagmonagent");
        intent.putExtras(bundle);
        final StringBuilder sb =
                new StringBuilder(KnoxAnalyticsSystemService.STATUS_PARAMETER_NAME);
        hashMap.forEach(
                new BiConsumer() { // from class:
                                   // com.samsung.android.server.pm.SALoggingHelper$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        sb.append("\n  " + ((String) obj) + " / " + ((String) obj2));
                    }
                });
        Log.i(str, sb.toString());
        context.sendBroadcast(intent);
    }
}
