package com.samsung.context.sdk.samsunganalytics.internal.sender.DLS;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.Manager;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DLSLogSender extends BaseLogSender {
    public DLSLogSender(Context context, Configuration configuration) {
        super(context, configuration);
    }

    public final void flushBufferedLogs(int i, LogType logType, Queue queue, C47911 c47911) {
        int i2;
        int i3;
        Manager manager;
        ArrayList arrayList = new ArrayList();
        Iterator it = queue.iterator();
        while (it.hasNext()) {
            LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
            Context context = this.context;
            int i4 = 0;
            SharedPreferences sharedPreferences = context.getSharedPreferences("SamsungAnalyticsPrefs", 0);
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
            PolicyUtils.useQuota(i, context, i4);
            this.executor.execute(new DLSAPIClient(logType, linkedBlockingQueue, this.configuration.trackingId, c47911));
            Debug.LogD("DLSLogSender", "send packet : num(" + linkedBlockingQueue.size() + ") size(" + i4 + ")");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.context.sdk.samsunganalytics.internal.sender.DLS.DLSLogSender$1] */
    @Override // com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender
    public final int send(Map map) {
        Context context = this.context;
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        int i = -4;
        final int type = (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) ? -4 : activeNetworkInfo.getType();
        if (type == -4) {
            Debug.LogD("DLS Sender", "Network unavailable.");
        } else if (PolicyUtils.isPolicyExpired(context)) {
            Debug.LogD("DLS Sender", "policy expired. request policy");
            i = -6;
        } else {
            i = 0;
        }
        Manager manager = this.manager;
        if (i != 0) {
            insert(map);
            if (i == -6) {
                this.executor.execute(PolicyUtils.makeGetPolicyClient(context, this.configuration, this.deviceInfo, null));
                manager.delete();
            }
            return i;
        }
        ?? r0 = new AsyncTaskCallback() { // from class: com.samsung.context.sdk.samsunganalytics.internal.sender.DLS.DLSLogSender.1
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
                PolicyUtils.useQuota(type, dLSLogSender.context, str2.getBytes().length * (-1));
            }

            @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback
            public final void onSuccess() {
            }
        };
        HashMap hashMap = (HashMap) map;
        long longValue = Long.valueOf((String) hashMap.get("ts")).longValue();
        setCommonParamToLog(hashMap);
        int sendOne = sendOne(type, new SimpleLog(longValue, Utils.makeDelimiterString(hashMap, Utils.Depth.ONE_DEPTH), BaseLogSender.getLogType(hashMap)), r0);
        if (sendOne == -1) {
            return sendOne;
        }
        Queue queue = manager.get(200);
        if (manager.useDatabase) {
            flushBufferedLogs(type, LogType.UIX, queue, r0);
            flushBufferedLogs(type, LogType.DEVICE, queue, r0);
        } else {
            while (!queue.isEmpty() && (sendOne = sendOne(type, (SimpleLog) queue.poll(), r0)) != -1) {
            }
        }
        return sendOne;
    }

    public final int sendOne(int i, SimpleLog simpleLog, C47911 c47911) {
        int i2;
        int i3;
        int i4;
        int i5;
        if (simpleLog == null) {
            return -100;
        }
        int length = simpleLog.data.getBytes().length;
        Context context = this.context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("SamsungAnalyticsPrefs", 0);
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
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("Quota : ", i3, "/ Uploaded : ", i4, "/ limit : ");
        m45m.append(i2);
        m45m.append("/ size : ");
        m45m.append(length);
        Debug.LogENG(m45m.toString());
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
        PolicyUtils.useQuota(i, context, length);
        this.executor.execute(new DLSAPIClient(simpleLog, this.configuration.trackingId, c47911));
        return 0;
    }
}
