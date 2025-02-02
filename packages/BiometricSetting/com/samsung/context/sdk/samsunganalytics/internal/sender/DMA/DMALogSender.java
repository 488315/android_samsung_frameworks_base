package com.samsung.context.sdk.samsunganalytics.internal.sender.DMA;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.Callback;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Delimiter;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/* loaded from: classes.dex */
public final class DMALogSender extends BaseLogSender {
    private DMABinder dmaBinder;
    private int dmaStatus;

    public DMALogSender(Context context, Configuration configuration) {
        super(context, configuration);
        this.dmaStatus = 0;
        if (PolicyUtils.getSenderType() == 2) {
            DMABinder dMABinder = new DMABinder(context, new Callback<Void, String>() { // from class: com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMALogSender.1
                @Override // com.samsung.context.sdk.samsunganalytics.internal.Callback
                public final void onResult(Object obj) {
                    DMALogSender dMALogSender = DMALogSender.this;
                    dMALogSender.sendCommon();
                    dMALogSender.sendAll();
                }
            });
            this.dmaBinder = dMABinder;
            dMABinder.bind();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAll() {
        if (PolicyUtils.getSenderType() == 2 && this.dmaStatus == 0) {
            Queue<SimpleLog> queue = this.manager.get(0);
            while (!queue.isEmpty()) {
                this.executor.execute(new SendLogTask(this.dmaBinder.getDmaInterface(), this.configuration, queue.poll()));
            }
        }
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.sender.LogSender
    public final int send(Map<String, String> map) {
        if (PolicyUtils.getSenderType() == 3) {
            ContentValues contentValues = new ContentValues();
            this.configuration.getClass();
            contentValues.put("tcType", (Integer) 0);
            contentValues.put("tid", this.configuration.getTrackingId());
            contentValues.put("logType", BaseLogSender.getLogType(map).getAbbrev());
            contentValues.put("timeStamp", Long.valueOf((String) ((HashMap) map).get("ts")));
            setCommonParamToLog(map);
            contentValues.put("body", makeBodyString(map));
            this.executor.execute(new SendLogTaskV2(this.context, 2, contentValues));
            return 0;
        }
        if (this.dmaBinder.isTokenfail()) {
            return -8;
        }
        int i = this.dmaStatus;
        if (i != 0) {
            return i;
        }
        insert(map);
        if (!this.dmaBinder.isBind()) {
            this.dmaBinder.bind();
        } else if (this.dmaBinder.getDmaInterface() != null) {
            sendAll();
        }
        return this.dmaStatus;
    }

    public final void sendCommon() {
        String str;
        this.configuration.getClass();
        String trackingId = this.configuration.getTrackingId();
        HashMap hashMap = new HashMap();
        hashMap.put("av", this.deviceInfo.getAppVersionName());
        hashMap.put("uv", this.configuration.getVersion());
        Delimiter.Depth depth = Delimiter.Depth.ONE_DEPTH;
        String makeDelimiterString = Delimiter.makeDelimiterString(hashMap, depth);
        HashMap hashMap2 = new HashMap();
        if (TextUtils.isEmpty(this.configuration.getDeviceId())) {
            str = null;
        } else {
            hashMap2.put("auid", this.configuration.getDeviceId());
            hashMap2.put("at", String.valueOf(this.configuration.getAuidType()));
            str = Delimiter.makeDelimiterString(hashMap2, depth);
        }
        if (PolicyUtils.getSenderType() != 3) {
            try {
                this.dmaStatus = this.dmaBinder.getDmaInterface().sendCommon(trackingId, makeDelimiterString, str);
                return;
            } catch (Exception e) {
                Debug.LogException(e.getClass(), e);
                this.dmaStatus = -9;
                return;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("tcType", (Integer) 0);
        contentValues.put("tid", trackingId);
        contentValues.put("data", makeDelimiterString);
        contentValues.put("did", str);
        this.executor.execute(new SendLogTaskV2(this.context, 1, contentValues));
    }
}
