package com.samsung.context.sdk.samsunganalytics.internal.sender.DLC;

import android.content.Context;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.Callback;
import com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/* loaded from: classes.dex */
public final class DLCLogSender extends BaseLogSender {
    private DLCBinder binder;

    public DLCLogSender(Context context, Configuration configuration) {
        super(context, configuration);
        DLCBinder dLCBinder = new DLCBinder(context, new Callback<Void, Void>() { // from class: com.samsung.context.sdk.samsunganalytics.internal.sender.DLC.DLCLogSender.1
            @Override // com.samsung.context.sdk.samsunganalytics.internal.Callback
            public final void onResult(Object obj) {
                DLCLogSender.this.sendAll();
            }
        });
        this.binder = dLCBinder;
        dLCBinder.sendRegisterRequestToDLC();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAll() {
        Queue<SimpleLog> queue = this.manager.get(0);
        while (!queue.isEmpty()) {
            this.executor.execute(new SendLogTask(this.binder, this.configuration, queue.poll()));
        }
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.sender.LogSender
    public final int send(Map<String, String> map) {
        insert(map);
        if (!this.binder.isBindToDLC()) {
            this.binder.sendRegisterRequestToDLC();
            return 0;
        }
        if (this.binder.getDlcService() == null) {
            return 0;
        }
        sendAll();
        return 0;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender
    protected final Map<String, String> setCommonParamToLog(Map<String, String> map) {
        super.setCommonParamToLog(map);
        HashMap hashMap = (HashMap) map;
        hashMap.remove("do");
        hashMap.remove("dm");
        hashMap.remove("v");
        return map;
    }
}
