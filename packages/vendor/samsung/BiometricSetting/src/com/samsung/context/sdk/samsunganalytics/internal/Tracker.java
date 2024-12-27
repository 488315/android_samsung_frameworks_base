package com.samsung.context.sdk.samsunganalytics.internal;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.sender.Sender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.Manager;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database.DbManager;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.queue.QueueManager;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class Tracker {
    public Application application;
    public Configuration configuration;

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    /* renamed from: com.samsung.context.sdk.samsunganalytics.internal.Tracker$1, reason: invalid class name */
    public final class AnonymousClass1 implements Callback {
        public final /* synthetic */ Object val$context;

        public /* synthetic */ AnonymousClass1(Object obj) {
            this.val$context = obj;
        }

        @Override // com.samsung.context.sdk.samsunganalytics.internal.Callback
        public void onResult(Object obj) {
            if (((Boolean) obj).booleanValue()) {
                Tracker tracker = (Tracker) this.val$context;
                tracker.configuration.getClass();
                Manager manager =
                        Manager.getInstance(
                                tracker.application.getApplicationContext(), tracker.configuration);
                Context applicationContext = tracker.application.getApplicationContext();
                manager.getClass();
                DbManager dbManager = new DbManager(applicationContext);
                manager.useDatabase = true;
                manager.dbManager = dbManager;
                QueueManager queueManager = manager.queueManager;
                if (queueManager.logQueue.isEmpty()) {
                    return;
                }
                Iterator it = queueManager.logQueue.iterator();
                while (it.hasNext()) {
                    manager.dbManager.insert((SimpleLog) it.next());
                }
                queueManager.logQueue.clear();
            }
        }
    }

    public final int sendLog(Map map) {
        if (Settings.System.getInt(
                        ((Context) this.configuration.userAgreement.val$context)
                                .getContentResolver(),
                        "samsung_errorlog_agree",
                        0)
                != 1) {
            Debug.LogD("user do not agree");
            return -2;
        }
        if (map != null) {
            HashMap hashMap = (HashMap) map;
            if (!hashMap.isEmpty()) {
                int i = PolicyUtils.senderType;
                Configuration configuration = this.configuration;
                if (i < 2 && TextUtils.isEmpty(configuration.deviceId)) {
                    Debug.LogD("did is empty");
                    return -5;
                }
                if (((String) hashMap.get("t")).equals("pp")) {
                    if (!Utils.compareDays(
                            1,
                            Long.valueOf(
                                    Preferences.getPreferences(this.application)
                                            .getLong("property_sent_date", 0L)))) {
                        Debug.LogD("do not send property < 1day");
                        return -9;
                    }
                    Preferences.getPreferences(this.application)
                            .edit()
                            .putLong("property_sent_date", System.currentTimeMillis())
                            .apply();
                }
                return Sender.get(this.application, PolicyUtils.senderType, configuration)
                        .send(map);
            }
        }
        Debug.LogD("Failure to send Logs : No data");
        return -3;
    }
}
