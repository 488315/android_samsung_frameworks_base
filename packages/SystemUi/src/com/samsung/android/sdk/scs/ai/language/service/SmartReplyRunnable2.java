package com.samsung.android.sdk.scs.ai.language.service;

import android.os.RemoteException;
import com.samsung.android.sdk.scs.base.tasks.TaskCompletionSource;
import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;
import com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SmartReplyRunnable2 extends TaskRunnable {
    public Map authHeader;
    public final Map extraPrompt;
    public String inputText;
    public final SmartReplyServiceExecutor mServiceExecutor;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.samsung.android.sdk.scs.ai.language.service.SmartReplyRunnable2$1 */
    public final class BinderC47601 extends LlmServiceObserver2 {
        public BinderC47601() {
        }
    }

    public SmartReplyRunnable2(SmartReplyServiceExecutor smartReplyServiceExecutor, TaskCompletionSource taskCompletionSource) {
        super(taskCompletionSource);
        this.extraPrompt = new HashMap();
        this.mServiceExecutor = smartReplyServiceExecutor;
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public final void execute() {
        try {
            BinderC47601 binderC47601 = new BinderC47601();
            ((ISmartReplyService.Stub.Proxy) this.mServiceExecutor.service).replyWithHeader3(this.authHeader, this.inputText, binderC47601, this.extraPrompt);
        } catch (RemoteException e) {
            e.printStackTrace();
            this.mSource.setException(e);
        }
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public final String getFeatureName() {
        return "FEATURE_AI_GEN_SMART_REPLY";
    }
}
