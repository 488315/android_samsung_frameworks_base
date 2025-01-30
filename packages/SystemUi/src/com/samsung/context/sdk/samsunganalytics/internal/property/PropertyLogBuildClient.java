package com.samsung.context.sdk.samsunganalytics.internal.property;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.Sender;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class PropertyLogBuildClient implements AsyncTaskClient {
    public final Configuration mConfig;
    public final Context mContext;
    public Map mMap;

    public PropertyLogBuildClient(Context context, Configuration configuration) {
        this.mContext = context;
        this.mConfig = configuration;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final int onFinish() {
        Configuration configuration = this.mConfig;
        boolean isAgreement = configuration.userAgreement.isAgreement();
        Context context = this.mContext;
        if (!Utils.isDMADataProvideVersion(context) && !isAgreement) {
            Debug.LogD("user do not agree Property");
            return 0;
        }
        Map map = this.mMap;
        if (map == null || map.isEmpty()) {
            Debug.LogD("PropertyLogBuildClient", "No Property log");
            return 0;
        }
        if (configuration.isAlwaysRunningApp) {
            Utils.registerReceiver(context, configuration);
        }
        boolean z = true;
        if (Utils.compareDays(1, Long.valueOf(context.getSharedPreferences("SamsungAnalyticsPrefs", 0).getLong("property_sent_date", 0L)))) {
            context.getSharedPreferences("SamsungAnalyticsPrefs", 0).edit().putLong("property_sent_date", System.currentTimeMillis()).apply();
        } else {
            Debug.LogD("do not send property < 1day");
            z = false;
        }
        if (!z) {
            return 0;
        }
        Debug.LogD("send Property Logs");
        HashMap hashMap = new HashMap();
        String valueOf = String.valueOf(System.currentTimeMillis());
        hashMap.put("ts", valueOf);
        hashMap.put("t", "pp");
        hashMap.put("cp", Utils.makeDelimiterString(Validation.checkSizeLimit(this.mMap), Utils.Depth.TWO_DEPTH));
        int i = PolicyUtils.senderType;
        if (i >= 3) {
            Uri parse = Uri.parse("content://com.sec.android.log.diagmonagent.sa/log");
            hashMap.put("v", "6.05.033");
            hashMap.put("tz", String.valueOf(TimeUnit.MILLISECONDS.toMinutes(TimeZone.getDefault().getRawOffset())));
            ContentValues contentValues = new ContentValues();
            configuration.getClass();
            contentValues.put("tcType", (Integer) 0);
            contentValues.put("tid", configuration.trackingId);
            contentValues.put("logType", LogType.UIX.getAbbrev());
            contentValues.put("timeStamp", valueOf);
            contentValues.put("agree", Integer.valueOf(isAgreement ? 1 : 0));
            contentValues.put(PhoneRestrictionPolicy.BODY, Utils.makeDelimiterString(hashMap, Utils.Depth.ONE_DEPTH));
            try {
                context.getContentResolver().insert(parse, contentValues);
            } catch (IllegalArgumentException unused) {
                Debug.LogD("Property send fail");
            }
        } else {
            Sender.get(context, i, configuration).send(hashMap);
        }
        return 0;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final void run() {
        this.mMap = this.mContext.getSharedPreferences("SAProperties", 0).getAll();
    }
}
