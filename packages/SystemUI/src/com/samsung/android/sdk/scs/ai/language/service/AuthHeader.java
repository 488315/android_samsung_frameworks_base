package com.samsung.android.sdk.scs.ai.language.service;

import android.content.Context;
import com.samsung.android.sdk.scs.ai.language.AppInfo;
import com.samsung.android.sdk.scs.base.utils.Log;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class AuthHeader {
    public final AppInfo appInfo;

    public AuthHeader(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public final Map generateHeaderMap(Context context) {
        String str;
        HashMap hashMap = new HashMap();
        AppInfo appInfo = this.appInfo;
        if (appInfo != null) {
            hashMap.put("api-key", appInfo.apiKey);
            hashMap.put("package-signing-key", appInfo.signingKey);
            hashMap.put("ssp-app-id", appInfo.appId);
            hashMap.put("ssp-access-token", appInfo.accessToken);
            hashMap.put("ssp-user-id", appInfo.userId);
            String str2 = appInfo.accountType;
            if ("B2B".equals(str2)) {
                Log.i("AppInfo", "B2B account is set");
                str = "";
            } else {
                Log.i("AppInfo", "B2B account is not set");
                str = appInfo.serverUrl;
            }
            hashMap.put("ssp-server-url", str);
            hashMap.put("ssp-account-type", str2);
            hashMap.put("request-type", appInfo.requestType.name());
            hashMap.put("streaming-mode", Boolean.toString(false));
        }
        hashMap.put("package-name", context.getPackageName());
        Log.i("AuthHeader", "SCS SDK VERSION: 4.0.26");
        return hashMap;
    }
}
