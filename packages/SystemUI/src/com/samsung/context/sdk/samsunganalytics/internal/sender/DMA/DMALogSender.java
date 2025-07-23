package com.samsung.context.sdk.samsunganalytics.internal.sender.DMA;

import android.content.ContentValues;
import android.content.Context;
import android.os.Trace;
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
import com.sec.android.diagmonagent.common.util.CommonUtils;
import com.sec.android.diagmonagent.sa.IDMAInterface;
import com.sec.ims.settings.ImsProfile;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class DMALogSender extends BaseLogSender {
    public final DMABinder dmaBinder;
    public int dmaStatus;
    public boolean isReset;

    public DMALogSender(Context context, Configuration configuration) {
        super(context, configuration);
        this.isReset = false;
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
        Trace.beginSection("DMALogSender send");
        if (PolicyUtils.senderType == 3) {
            ContentValues contentValues = new ContentValues();
            boolean isSendingAppCommonSupported = Utils.isSendingAppCommonSupported(this.context);
            Configuration configuration = this.configuration;
            if (!isSendingAppCommonSupported) {
                Utils.addAppCommonData(this.context, contentValues, configuration);
            } else if (!Preferences.getPreferences(this.context).getBoolean("sendCommonSuccess", false)) {
                sendCommon();
            }
            if (map.containsKey("pd")) {
                String str = (String) map.get("pd");
                if (!TextUtils.isEmpty(str)) {
                    contentValues.put("pd", str);
                }
                map.remove("pd");
            }
            if (map.containsKey("ps")) {
                String str2 = (String) map.get("ps");
                if (!TextUtils.isEmpty(str2)) {
                    contentValues.put("ps", str2);
                }
                map.remove("ps");
            }
            boolean parseBoolean = Boolean.parseBoolean((String) map.remove(ImsProfile.SERVICE_IS));
            configuration.getClass();
            contentValues.put("tcType", (Integer) 0);
            contentValues.put("agree", Integer.valueOf(configuration.userAgreement.isAgreement() ? 1 : 0));
            contentValues.put("tid", configuration.trackingId);
            contentValues.put("logType", BaseLogSender.getLogType(map).getAbbrev());
            contentValues.put("timeStamp", Long.valueOf((String) map.get("ts")));
            setCommonParamToLog(map);
            contentValues.put(PhoneRestrictionPolicy.BODY, Utils.makeDelimiterString(map, Utils.Depth.ONE_DEPTH));
            if (!Utils.isSendingAppCommonSupported(this.context)) {
                contentValues.put("networkType", (Integer) (-1));
                contentValues.put("isSummary", Boolean.valueOf(parseBoolean));
            }
            this.executor.execute(new SendLogTaskV2(this.context, 2, contentValues));
        } else {
            DMABinder dMABinder = this.dmaBinder;
            if (dMABinder.isTokenFail) {
                Trace.endSection();
                return -8;
            }
            if (this.dmaStatus != 0) {
                Trace.endSection();
                return this.dmaStatus;
            }
            insert(map);
            if (!dMABinder.isBind) {
                dMABinder.bind();
            } else if (dMABinder.dmaInterface != null) {
                sendAll();
                if (this.isReset) {
                    sendCommon();
                    this.isReset = false;
                }
            }
        }
        Trace.endSection();
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
        Trace.beginSection("DMALogSender sendCommon");
        Configuration configuration = this.configuration;
        configuration.getClass();
        String str = configuration.trackingId;
        HashMap hashMap = new HashMap();
        hashMap.put("av", CommonUtils.getPackageVersion(this.context));
        hashMap.put("uv", configuration.version);
        hashMap.put("v", "6.05.073");
        Utils.Depth depth = Utils.Depth.ONE_DEPTH;
        String makeDelimiterString = Utils.makeDelimiterString(hashMap, depth);
        HashMap hashMap2 = new HashMap();
        String str2 = null;
        if (!TextUtils.isEmpty(null)) {
            hashMap2.put("auid", null);
            hashMap2.put("at", String.valueOf(configuration.auidType));
            str2 = Utils.makeDelimiterString(hashMap2, depth);
        }
        if (PolicyUtils.senderType == 3) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("tcType", (Integer) 0);
            contentValues.put("tid", str);
            contentValues.put("data", makeDelimiterString);
            contentValues.put("did", str2);
            this.executor.execute(new SendLogTaskV2(this.context, 1, contentValues));
        } else {
            try {
                this.dmaStatus = ((IDMAInterface.Stub.Proxy) this.dmaBinder.dmaInterface).sendCommon(str, makeDelimiterString, str2);
            } catch (Exception e) {
                Debug.logwingW("failed to send app common" + e.getMessage());
                this.dmaStatus = -9;
            }
        }
        Trace.endSection();
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender
    public final Map setCommonParamToLog(Map map) {
        map.put("tz", String.valueOf(Utils.getTimeZoneOffset()));
        return map;
    }
}
