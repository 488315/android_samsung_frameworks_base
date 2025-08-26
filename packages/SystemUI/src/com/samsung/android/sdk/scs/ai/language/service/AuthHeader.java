package com.samsung.android.sdk.scs.ai.language.service;

import android.content.Context;
import com.samsung.android.sdk.scs.ai.language.AppInfo;
import com.samsung.android.sdk.scs.base.utils.Log;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class AuthHeader {
    public final AppInfo appInfo;

    public AuthHeader(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public final Map generateHeaderMap(Context context) {
        String str;
        HashMap map = new HashMap();
        AppInfo appInfo = this.appInfo;
        if (appInfo != null) {
            map.put("api-key", appInfo.apiKey);
            map.put("package-signing-key", appInfo.signingKey);
            map.put("ssp-app-id", appInfo.appId);
            map.put("ssp-access-token", appInfo.accessToken);
            map.put("ssp-user-id", appInfo.userId);
            String str2 = appInfo.accountType;
            if ("B2B".equals(str2)) {
                Log.i("AppInfo", "B2B account is set");
                str = "";
            } else {
                Log.i("AppInfo", "B2B account is not set");
                str = appInfo.serverUrl;
            }
            map.put("ssp-server-url", str);
            map.put("ssp-account-type", str2);
            map.put("request-type", appInfo.requestType.name());
            map.put("streaming-mode", Boolean.toString(false));
        }
        map.put("package-name", context.getPackageName());
        Log.i("AuthHeader", "SCS SDK VERSION: 4.0.26");
        return map;
    }
}
