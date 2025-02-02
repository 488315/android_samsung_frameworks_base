package com.samsung.context.sdk.samsunganalytics.internal.setting;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Delimiter;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class BuildClient implements AsyncTaskClient {
    private Configuration config;
    private Context context;
    private Uri logUri = Uri.parse("content://com.sec.android.log.diagmonagent.sa/log");
    private List<String> settings;

    public BuildClient(Context context, Configuration configuration) {
        this.context = context;
        this.config = configuration;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final int onFinish() {
        Uri uri;
        if (!this.config.getUserAgreement().isAgreement()) {
            Debug.LogD("user do not agree setting");
            return 0;
        }
        List<String> list = this.settings;
        if (list == null || list.isEmpty()) {
            Debug.LogD("Setting Sender", "No status log");
            return 0;
        }
        this.config.getClass();
        if (!Utils.compareDays(7, Long.valueOf(Preferences.getPreferences(this.context).getLong("status_sent_date", 0L)))) {
            Debug.LogD("do not send setting < 7days");
            return 0;
        }
        Debug.LogD("send setting");
        HashMap hashMap = new HashMap();
        String valueOf = String.valueOf(System.currentTimeMillis());
        hashMap.put("ts", valueOf);
        hashMap.put("t", "st");
        hashMap.put("v", "6.05.015");
        hashMap.put("tz", String.valueOf(TimeUnit.MILLISECONDS.toMinutes(TimeZone.getDefault().getRawOffset())));
        this.config.getClass();
        Iterator<String> it = this.settings.iterator();
        boolean z = false;
        while (it.hasNext()) {
            hashMap.put("sti", it.next());
            ContentValues contentValues = new ContentValues();
            this.config.getClass();
            contentValues.put("tcType", (Integer) 0);
            contentValues.put("tid", this.config.getTrackingId());
            contentValues.put("logType", LogType.UIX.getAbbrev());
            contentValues.put("timeStamp", valueOf);
            contentValues.put("body", Delimiter.makeDelimiterString(hashMap, Delimiter.Depth.ONE_DEPTH));
            try {
                uri = this.context.getContentResolver().insert(this.logUri, contentValues);
            } catch (IllegalArgumentException unused) {
                uri = null;
            }
            if (uri != null) {
                int parseInt = Integer.parseInt(uri.getLastPathSegment());
                Debug.LogD("Send SettingLog Result = " + parseInt);
                if (parseInt == 0) {
                    z = true;
                }
            }
        }
        if (z) {
            Preferences.getPreferences(this.context).edit().putLong("status_sent_date", System.currentTimeMillis()).apply();
        } else {
            Preferences.getPreferences(this.context).edit().putLong("status_sent_date", 0L).apply();
        }
        Debug.LogD("Save Setting Result = " + z);
        return 0;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final void run() {
        this.settings = new SettingReader(this.context).read();
    }
}
