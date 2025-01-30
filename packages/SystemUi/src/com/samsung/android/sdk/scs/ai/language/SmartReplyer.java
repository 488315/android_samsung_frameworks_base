package com.samsung.android.sdk.scs.ai.language;

import android.content.Context;
import com.samsung.android.sdk.scs.base.tasks.TaskCompletionSource;
import com.samsung.android.sdk.scs.base.tasks.TaskImpl;
import com.samsung.android.sdk.scs.base.tasks.TaskStreamingCompletionSource;
import com.samsung.android.sdk.scs.base.utils.Log;
import com.samsung.android.sdk.scs.ai.language.service.AuthHeader;
import com.samsung.android.sdk.scs.ai.language.service.SmartReplyRunnable2;
import com.samsung.android.sdk.scs.ai.language.service.SmartReplyServiceExecutor;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SmartReplyer {
    public final SmartReplyServiceExecutor mServiceExecutor;

    public SmartReplyer(Context context) {
        Log.m266d("SmartReplyer", "SmartReplyer");
        this.mServiceExecutor = new SmartReplyServiceExecutor(context);
    }

    public final TaskImpl smartReply(AppInfo appInfo, String str, Map map) {
        TaskCompletionSource taskStreamingCompletionSource = appInfo.enableStreaming ? new TaskStreamingCompletionSource() : new TaskCompletionSource();
        SmartReplyServiceExecutor smartReplyServiceExecutor = this.mServiceExecutor;
        SmartReplyRunnable2 smartReplyRunnable2 = new SmartReplyRunnable2(smartReplyServiceExecutor, taskStreamingCompletionSource);
        Log.m268i("SmartReplyer", "created: " + smartReplyRunnable2.hashCode());
        AuthHeader authHeader = new AuthHeader(appInfo);
        Context context = smartReplyRunnable2.mServiceExecutor.context;
        HashMap hashMap = new HashMap();
        AppInfo appInfo2 = authHeader.appInfo;
        if (appInfo2 != null) {
            hashMap.put("api-key", appInfo2.apiKey);
            hashMap.put("package-signing-key", appInfo2.signingKey);
            hashMap.put("ssp-app-id", appInfo2.appId);
            hashMap.put("ssp-access-token", appInfo2.accessToken);
            hashMap.put("ssp-user-id", appInfo2.userId);
            hashMap.put("ssp-server-url", appInfo2.serverUrl);
            hashMap.put("request-type", appInfo2.requestType.name());
            hashMap.put("streaming-mode", Boolean.toString(appInfo2.enableStreaming));
        }
        hashMap.put("package-name", context.getPackageName());
        Log.m268i("AuthHeader", "SCS SDK VERSION: 3.1.24");
        smartReplyRunnable2.authHeader = hashMap;
        smartReplyRunnable2.inputText = str;
        ((HashMap) smartReplyRunnable2.extraPrompt).putAll(map);
        smartReplyServiceExecutor.execute(smartReplyRunnable2);
        Log.m268i("SmartReplyer", "executed: " + smartReplyRunnable2.hashCode());
        return smartReplyRunnable2.mSource.task;
    }
}
