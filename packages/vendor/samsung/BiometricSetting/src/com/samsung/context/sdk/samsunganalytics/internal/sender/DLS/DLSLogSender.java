package com.samsung.context.sdk.samsunganalytics.internal.sender.DLS;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback;
import com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor;
import com.samsung.context.sdk.samsunganalytics.internal.policy.GetPolicyClient;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.Manager;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database.DbManager;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database.DefaultDBOpenHelper;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Delimiter;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class DLSLogSender extends BaseLogSender {
    public final void flushBufferedLogs(int i, LogType logType, Queue queue, AnonymousClass1 anonymousClass1) {
        int i2;
        int i3;
        Manager manager;
        ArrayList arrayList = new ArrayList();
        Iterator it = queue.iterator();
        while (it.hasNext()) {
            LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
            SharedPreferences preferences = Preferences.getPreferences(this.context);
            int i4 = 0;
            if (i == 1) {
                i3 = preferences.getInt("dq-w", 0);
                i2 = preferences.getInt("wifi_used", 0);
            } else if (i == 0) {
                i3 = preferences.getInt("dq-3g", 0);
                i2 = preferences.getInt("data_used", 0);
            } else {
                i2 = 0;
                i3 = 0;
            }
            int i5 = i3 - i2;
            if (51200 <= i5) {
                i5 = 51200;
            }
            while (true) {
                boolean hasNext = it.hasNext();
                manager = this.manager;
                if (!hasNext) {
                    break;
                }
                SimpleLog simpleLog = (SimpleLog) it.next();
                if (simpleLog.type == logType) {
                    if (simpleLog.data.getBytes().length + i4 > i5) {
                        break;
                    }
                    i4 += simpleLog.data.getBytes().length;
                    linkedBlockingQueue.add(simpleLog);
                    it.remove();
                    arrayList.add(simpleLog._id);
                    if (queue.isEmpty()) {
                        manager.remove(arrayList);
                        queue = manager.get(200);
                        it = queue.iterator();
                    }
                }
            }
            if (linkedBlockingQueue.isEmpty()) {
                return;
            }
            manager.remove(arrayList);
            PolicyUtils.useQuota(this.context, i, i4);
            String str = this.configuration.trackingId;
            DLSAPIClient dLSAPIClient = new DLSAPIClient();
            dLSAPIClient.conn = null;
            dLSAPIClient.logs = linkedBlockingQueue;
            dLSAPIClient.trid = str;
            dLSAPIClient.asyncTaskCallback = anonymousClass1;
            dLSAPIClient.isBatch = Boolean.TRUE;
            dLSAPIClient.timeoutInMilliseconds = 3000;
            dLSAPIClient.logType = logType;
            this.executor.getClass();
            SingleThreadExecutor.execute(dLSAPIClient);
            Debug.LogD("DLSLogSender", "send packet : num(" + linkedBlockingQueue.size() + ") size(" + i4 + ")");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.samsung.context.sdk.samsunganalytics.internal.sender.DLS.DLSLogSender$1] */
    @Override // com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender
    public final int send(Map map) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo();
        final int type = (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) ? -4 : activeNetworkInfo.getType();
        int i = -4;
        if (type == -4) {
            Debug.LogD("DLS Sender", "Network unavailable.");
        } else if (PolicyUtils.isPolicyExpired(this.context)) {
            Debug.LogD("DLS Sender", "policy expired. request policy");
            i = -6;
        } else {
            this.configuration.getClass();
            if (-1 == type) {
                Debug.LogD("DLS Sender", "Network unavailable by restrict option:" + type);
            } else {
                i = 0;
            }
        }
        Manager manager = this.manager;
        if (i != 0) {
            insert(map);
            if (i == -6) {
                GetPolicyClient makeGetPolicyClient = PolicyUtils.makeGetPolicyClient(this.context, this.configuration, this.deviceInfo, null);
                this.executor.getClass();
                SingleThreadExecutor.execute(makeGetPolicyClient);
                if (manager.useDatabase) {
                    DbManager dbManager = manager.dbManager;
                    long currentTimeMillis = System.currentTimeMillis() - (5 * 86400000);
                    ((DefaultDBOpenHelper) dbManager.dbOpenHelper).getWritableDatabase().delete("logs_v2", "timestamp <= " + currentTimeMillis, null);
                }
            }
            return i;
        }
        ?? r2 = new AsyncTaskCallback() { // from class: com.samsung.context.sdk.samsunganalytics.internal.sender.DLS.DLSLogSender.1
            @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback
            public final void onFail(String str, String str2, String str3) {
                DLSLogSender dLSLogSender = DLSLogSender.this;
                Manager manager2 = dLSLogSender.manager;
                long longValue = Long.valueOf(str).longValue();
                LogType logType = LogType.DEVICE;
                if (!str3.equals(logType.getAbbrev())) {
                    logType = LogType.UIX;
                }
                manager2.getClass();
                manager2.insert(new SimpleLog(longValue, str2, logType));
                PolicyUtils.useQuota(dLSLogSender.context, type, str2.getBytes().length * (-1));
            }

            @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback
            public final void onSuccess() {
            }
        };
        HashMap hashMap = (HashMap) map;
        long longValue = Long.valueOf((String) hashMap.get("ts")).longValue();
        setCommonParamToLog(hashMap);
        Delimiter.Depth depth = Delimiter.Depth.ONE_DEPTH;
        this.delimiterUtil.getClass();
        int sendOne = sendOne(type, new SimpleLog(longValue, Delimiter.makeDelimiterString(hashMap, depth), BaseLogSender.getLogType(hashMap)), r2, false);
        if (sendOne == -1) {
            return sendOne;
        }
        Queue queue = manager.get(200);
        if (manager.useDatabase) {
            flushBufferedLogs(type, LogType.UIX, queue, r2);
            flushBufferedLogs(type, LogType.DEVICE, queue, r2);
        } else {
            while (!queue.isEmpty() && (sendOne = sendOne(type, (SimpleLog) queue.poll(), r2, false)) != -1) {
            }
        }
        return sendOne;
    }

    public final int sendOne(int i, SimpleLog simpleLog, AnonymousClass1 anonymousClass1, boolean z) {
        int i2;
        int i3;
        int i4;
        int i5;
        if (simpleLog == null) {
            return -100;
        }
        int length = simpleLog.data.getBytes().length;
        SharedPreferences preferences = Preferences.getPreferences(this.context);
        if (i == 1) {
            i3 = preferences.getInt("dq-w", 0);
            i4 = preferences.getInt("wifi_used", 0);
            i2 = preferences.getInt("oq-w", 0);
        } else if (i == 0) {
            i3 = preferences.getInt("dq-3g", 0);
            i4 = preferences.getInt("data_used", 0);
            i2 = preferences.getInt("oq-3g", 0);
        } else {
            i2 = 0;
            i3 = 0;
            i4 = 0;
        }
        Debug.LogENG("Quota : " + i3 + "/ Uploaded : " + i4 + "/ limit : " + i2 + "/ size : " + length);
        if (i3 < i4 + length) {
            Debug.LogD("DLS Sender", "send result fail : Over daily quota");
            i5 = -1;
        } else if (i2 < length) {
            Debug.LogD("DLS Sender", "send result fail : Over once quota");
            i5 = -11;
        } else {
            i5 = 0;
        }
        if (i5 != 0) {
            return i5;
        }
        PolicyUtils.useQuota(this.context, i, length);
        String str = this.configuration.trackingId;
        DLSAPIClient dLSAPIClient = new DLSAPIClient();
        dLSAPIClient.conn = null;
        dLSAPIClient.isBatch = Boolean.FALSE;
        dLSAPIClient.simpleLog = simpleLog;
        dLSAPIClient.trid = str;
        dLSAPIClient.asyncTaskCallback = anonymousClass1;
        dLSAPIClient.timeoutInMilliseconds = 3000;
        dLSAPIClient.logType = simpleLog.type;
        if (z) {
            Debug.LogENG("sync send");
            dLSAPIClient.run();
            return dLSAPIClient.onFinish();
        }
        this.executor.getClass();
        SingleThreadExecutor.execute(dLSAPIClient);
        return 0;
    }
}
