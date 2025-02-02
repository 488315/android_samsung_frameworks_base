package com.samsung.context.sdk.samsunganalytics.internal.sender.DLS;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback;
import com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.Manager;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes.dex */
public final class DLSLogSender extends BaseLogSender {
    private int checkAvailableLogging(int i) {
        if (i == -4) {
            Debug.LogD("DLS Sender", "Network unavailable.");
            return -4;
        }
        if (PolicyUtils.isPolicyExpired(this.context)) {
            Debug.LogD("DLS Sender", "policy expired. request policy");
            return -6;
        }
        this.configuration.getClass();
        if (-1 != i) {
            return 0;
        }
        Debug.LogD("DLS Sender", "Network unavailable by restrict option:" + i);
        return -4;
    }

    private void flushBufferedLogs(int i, LogType logType, Queue queue, AsyncTaskCallback asyncTaskCallback) {
        int i2;
        int i3;
        ArrayList arrayList = new ArrayList();
        Iterator it = queue.iterator();
        while (it.hasNext()) {
            LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
            int i4 = 0;
            SharedPreferences sharedPreferences = this.context.getSharedPreferences("SamsungAnalyticsPrefs", 0);
            if (i == 1) {
                i3 = sharedPreferences.getInt("dq-w", 0);
                i2 = sharedPreferences.getInt("wifi_used", 0);
            } else if (i == 0) {
                i3 = sharedPreferences.getInt("dq-3g", 0);
                i2 = sharedPreferences.getInt("data_used", 0);
            } else {
                i2 = 0;
                i3 = 0;
            }
            int i5 = i3 - i2;
            if (51200 <= i5) {
                i5 = 51200;
            }
            while (it.hasNext()) {
                SimpleLog simpleLog = (SimpleLog) it.next();
                if (simpleLog.getType() == logType) {
                    if (simpleLog.getData().getBytes().length + i4 > i5) {
                        break;
                    }
                    i4 += simpleLog.getData().getBytes().length;
                    linkedBlockingQueue.add(simpleLog);
                    it.remove();
                    arrayList.add(simpleLog.getId());
                    if (queue.isEmpty()) {
                        this.manager.remove(arrayList);
                        queue = this.manager.get(200);
                        it = queue.iterator();
                    }
                }
            }
            if (linkedBlockingQueue.isEmpty()) {
                return;
            }
            this.manager.remove(arrayList);
            PolicyUtils.useQuota(this.context, i, i4);
            SingleThreadExecutor singleThreadExecutor = this.executor;
            String trackingId = this.configuration.getTrackingId();
            this.configuration.getClass();
            singleThreadExecutor.execute(new DLSAPIClient(logType, linkedBlockingQueue, trackingId, asyncTaskCallback));
            Debug.LogD("DLSLogSender", "send packet : num(" + linkedBlockingQueue.size() + ") size(" + i4 + ")");
        }
    }

    private int getNetworkType() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return -4;
        }
        return activeNetworkInfo.getType();
    }

    private int sendOne(int i, SimpleLog simpleLog, AsyncTaskCallback asyncTaskCallback, boolean z) {
        int i2;
        int i3;
        int i4;
        int i5;
        if (simpleLog == null) {
            return -100;
        }
        int length = simpleLog.getData().getBytes().length;
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("SamsungAnalyticsPrefs", 0);
        if (i == 1) {
            i3 = sharedPreferences.getInt("dq-w", 0);
            i4 = sharedPreferences.getInt("wifi_used", 0);
            i2 = sharedPreferences.getInt("oq-w", 0);
        } else if (i == 0) {
            i3 = sharedPreferences.getInt("dq-3g", 0);
            i4 = sharedPreferences.getInt("data_used", 0);
            i2 = sharedPreferences.getInt("oq-3g", 0);
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
        String trackingId = this.configuration.getTrackingId();
        this.configuration.getClass();
        DLSAPIClient dLSAPIClient = new DLSAPIClient(simpleLog, trackingId, asyncTaskCallback);
        if (!z) {
            this.executor.execute(dLSAPIClient);
            return 0;
        }
        Debug.LogENG("sync send");
        dLSAPIClient.run();
        return dLSAPIClient.onFinish();
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.sender.LogSender
    public final int send(Map<String, String> map) {
        final int networkType = getNetworkType();
        int checkAvailableLogging = checkAvailableLogging(networkType);
        if (checkAvailableLogging != 0) {
            insert(map);
            if (checkAvailableLogging == -6) {
                this.executor.execute(PolicyUtils.makeGetPolicyClient(this.context, this.configuration, this.deviceInfo, null));
                this.manager.delete();
            }
            return checkAvailableLogging;
        }
        AsyncTaskCallback asyncTaskCallback = new AsyncTaskCallback() { // from class: com.samsung.context.sdk.samsunganalytics.internal.sender.DLS.DLSLogSender.1
            @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback
            public final void onFail(String str, String str2, String str3) {
                DLSLogSender dLSLogSender = DLSLogSender.this;
                Manager manager = ((BaseLogSender) dLSLogSender).manager;
                long longValue = Long.valueOf(str).longValue();
                LogType logType = LogType.DEVICE;
                if (!str3.equals(logType.getAbbrev())) {
                    logType = LogType.UIX;
                }
                manager.getClass();
                manager.insert(new SimpleLog(longValue, str2, logType));
                PolicyUtils.useQuota(((BaseLogSender) dLSLogSender).context, networkType, str2.getBytes().length * (-1));
            }

            @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback
            public final void onSuccess() {
            }
        };
        HashMap hashMap = (HashMap) map;
        long longValue = Long.valueOf((String) hashMap.get("ts")).longValue();
        setCommonParamToLog(hashMap);
        int sendOne = sendOne(networkType, new SimpleLog(longValue, makeBodyString(hashMap), BaseLogSender.getLogType(hashMap)), asyncTaskCallback, false);
        if (sendOne == -1) {
            return sendOne;
        }
        Queue queue = this.manager.get(200);
        if (this.manager.isEnabledDatabaseBuffering()) {
            flushBufferedLogs(networkType, LogType.UIX, queue, asyncTaskCallback);
            flushBufferedLogs(networkType, LogType.DEVICE, queue, asyncTaskCallback);
        } else {
            while (!queue.isEmpty() && (sendOne = sendOne(networkType, queue.poll(), asyncTaskCallback, false)) != -1) {
            }
        }
        return sendOne;
    }
}
