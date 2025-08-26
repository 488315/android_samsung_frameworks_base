package com.samsung.android.sdk.scs.ai.language;

import android.os.RemoteException;
import com.samsung.android.sdk.scs.ai.language.service.AuthHeader;
import com.samsung.android.sdk.scs.ai.language.service.LlmServiceObserver2;
import com.samsung.android.sdk.scs.ai.language.service.SmartReplyServiceExecutor;
import com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService;
import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public final /* synthetic */ class SmartReplyer$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ SmartReplyer f$0;
    public final /* synthetic */ AppInfo f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ Map f$3;

    public /* synthetic */ SmartReplyer$$ExternalSyntheticLambda5(SmartReplyer smartReplyer, AppInfo appInfo, String str, Map map) {
        this.f$0 = smartReplyer;
        this.f$1 = appInfo;
        this.f$2 = str;
        this.f$3 = map;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SmartReplyer smartReplyer = this.f$0;
        AppInfo appInfo = this.f$1;
        String str = this.f$2;
        Map map = this.f$3;
        LlmServiceObserver2 llmServiceObserver2 = (LlmServiceObserver2) obj;
        SmartReplyServiceExecutor smartReplyServiceExecutor = smartReplyer.mServiceExecutor;
        try {
            ((ISmartReplyService.Stub.Proxy) smartReplyServiceExecutor.service).replyWithHeader3(new AuthHeader(appInfo).generateHeaderMap(smartReplyServiceExecutor.context), str, llmServiceObserver2, map);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
