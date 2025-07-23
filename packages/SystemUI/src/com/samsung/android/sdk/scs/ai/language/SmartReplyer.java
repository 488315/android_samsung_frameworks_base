package com.samsung.android.sdk.scs.ai.language;

import android.content.Context;
import android.os.RemoteException;
import com.samsung.android.sdk.scs.ai.language.service.LlmServiceObserver2;
import com.samsung.android.sdk.scs.ai.language.service.LlmServiceRunnable;
import com.samsung.android.sdk.scs.ai.language.service.SmartReplyServiceExecutor;
import com.samsung.android.sdk.scs.base.tasks.OnCompleteListener;
import com.samsung.android.sdk.scs.base.tasks.Task;
import com.samsung.android.sdk.scs.base.tasks.TaskCompletionSource;
import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;
import com.samsung.android.sdk.scs.base.utils.Log;
import com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SmartReplyer {
    public final SmartReplyServiceExecutor mServiceExecutor;

    public SmartReplyer(Context context) {
        Log.d("SmartReplyer", "SmartReplyer");
        this.mServiceExecutor = new SmartReplyServiceExecutor(context);
    }

    public final void release() {
        StringBuilder sb = new StringBuilder("release: ");
        SmartReplyServiceExecutor smartReplyServiceExecutor = this.mServiceExecutor;
        sb.append(smartReplyServiceExecutor.isConnected());
        Log.i("SmartReplyer", sb.toString());
        smartReplyServiceExecutor.deInit();
        LlmServiceRunnable llmServiceRunnable = new LlmServiceRunnable("FEATURE_AI_GEN_SMART_REPLY", false, new Consumer() { // from class: com.samsung.android.sdk.scs.ai.language.SmartReplyer$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TaskCompletionSource taskCompletionSource;
                SmartReplyer smartReplyer = SmartReplyer.this;
                LlmServiceObserver2 llmServiceObserver2 = (LlmServiceObserver2) obj;
                smartReplyer.getClass();
                try {
                    ((ISmartReplyService.Stub.Proxy) smartReplyer.mServiceExecutor.service).unLoadModel();
                    ArrayList arrayList = new ArrayList();
                    LlmServiceRunnable.AnonymousClass1 anonymousClass1 = (LlmServiceRunnable.AnonymousClass1) llmServiceObserver2;
                    taskCompletionSource = ((TaskRunnable) LlmServiceRunnable.this).mSource;
                    taskCompletionSource.setResult(LlmServiceRunnable.this.resultMapper.apply(arrayList));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new SmartReplyer$$ExternalSyntheticLambda1(0));
        smartReplyServiceExecutor.execute(llmServiceRunnable);
        llmServiceRunnable.getTask().addOnCompleteListener(new OnCompleteListener() { // from class: com.samsung.android.sdk.scs.ai.language.SmartReplyer$$ExternalSyntheticLambda2
            @Override // com.samsung.android.sdk.scs.base.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                StringBuilder sb2 = new StringBuilder("connected: ");
                SmartReplyServiceExecutor smartReplyServiceExecutor2 = SmartReplyer.this.mServiceExecutor;
                sb2.append(smartReplyServiceExecutor2.isConnected());
                Log.i("SmartReplyer", sb2.toString());
                smartReplyServiceExecutor2.deInit();
            }
        });
    }
}
