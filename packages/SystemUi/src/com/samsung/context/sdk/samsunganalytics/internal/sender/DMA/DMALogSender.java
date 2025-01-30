package com.samsung.context.sdk.samsunganalytics.internal.sender.DMA;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.Callback;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import com.sec.android.diagmonagent.sa.IDMAInterface;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DMALogSender extends BaseLogSender {
    public final DMABinder dmaBinder;
    public int dmaStatus;

    public DMALogSender(Context context, Configuration configuration) {
        super(context, configuration);
        this.dmaStatus = 0;
        if (PolicyUtils.senderType == 2) {
            DMABinder dMABinder = new DMABinder(context, new Callback() { // from class: com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMALogSender.1
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

    @Override // com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender
    public final int send(Map map) {
        if (PolicyUtils.senderType == 3) {
            Context context = this.context;
            if (!Preferences.getPreferences(context).getBoolean("sendCommonSuccess", false)) {
                sendCommon();
            }
            ContentValues contentValues = new ContentValues();
            HashMap hashMap = (HashMap) map;
            String str = (String) hashMap.get("pd");
            if (!TextUtils.isEmpty(str)) {
                contentValues.put("pd", str);
                hashMap.remove("pd");
            }
            String str2 = (String) hashMap.get("ps");
            if (!TextUtils.isEmpty(str2)) {
                contentValues.put("ps", str2);
                hashMap.remove("ps");
            }
            Configuration configuration = this.configuration;
            configuration.getClass();
            contentValues.put("tcType", (Integer) 0);
            contentValues.put("agree", Integer.valueOf(configuration.userAgreement.isAgreement() ? 1 : 0));
            contentValues.put("tid", configuration.trackingId);
            contentValues.put("logType", BaseLogSender.getLogType(hashMap).getAbbrev());
            contentValues.put("timeStamp", Long.valueOf((String) hashMap.get("ts")));
            setCommonParamToLog(hashMap);
            contentValues.put(PhoneRestrictionPolicy.BODY, Utils.makeDelimiterString(hashMap, Utils.Depth.ONE_DEPTH));
            this.executor.execute(new SendLogTaskV2(context, 2, contentValues));
        } else {
            DMABinder dMABinder = this.dmaBinder;
            if (dMABinder.isTokenFail) {
                return -8;
            }
            int i = this.dmaStatus;
            if (i != 0) {
                return i;
            }
            insert(map);
            if (!dMABinder.isBind) {
                dMABinder.bind();
            } else if (dMABinder.dmaInterface != null) {
                sendAll();
            }
        }
        return this.dmaStatus;
    }

    public final void sendAll() {
        if (PolicyUtils.senderType == 2 && this.dmaStatus == 0) {
            Queue queue = this.manager.get(0);
            while (!queue.isEmpty()) {
                this.executor.execute(new SendLogTask(this.dmaBinder.dmaInterface, this.configuration, (SimpleLog) queue.poll()));
            }
        }
    }

    public final void sendCommon() {
        String str;
        Configuration configuration = this.configuration;
        configuration.getClass();
        String str2 = configuration.trackingId;
        HashMap hashMap = new HashMap();
        hashMap.put("av", this.deviceInfo.appVersionName);
        hashMap.put("uv", configuration.version);
        hashMap.put("v", "6.05.033");
        Utils.Depth depth = Utils.Depth.ONE_DEPTH;
        String makeDelimiterString = Utils.makeDelimiterString(hashMap, depth);
        HashMap hashMap2 = new HashMap();
        if (TextUtils.isEmpty(configuration.deviceId)) {
            str = null;
        } else {
            hashMap2.put("auid", configuration.deviceId);
            hashMap2.put("at", String.valueOf(configuration.auidType));
            str = Utils.makeDelimiterString(hashMap2, depth);
        }
        if (PolicyUtils.senderType != 3) {
            try {
                this.dmaStatus = ((IDMAInterface.Stub.Proxy) this.dmaBinder.dmaInterface).sendCommon(0, str2, makeDelimiterString, str);
                return;
            } catch (Exception e) {
                Debug.LogException(e.getClass(), e);
                this.dmaStatus = -9;
                return;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("tcType", (Integer) 0);
        contentValues.put("tid", str2);
        contentValues.put("data", makeDelimiterString);
        contentValues.put("did", str);
        this.executor.execute(new SendLogTaskV2(this.context, 1, contentValues));
    }
}
