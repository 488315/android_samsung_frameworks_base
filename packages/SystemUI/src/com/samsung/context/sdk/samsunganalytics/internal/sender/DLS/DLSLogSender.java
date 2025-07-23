package com.samsung.context.sdk.samsunganalytics.internal.sender.DLS;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.device.DeviceInfo;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.Manager;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import com.sec.android.diagmonagent.common.util.CommonUtils;
import com.sec.android.diagmonagent.common.util.executor.AsyncTaskCallback;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class DLSLogSender extends BaseLogSender {
    public final DeviceInfo deviceInfo;

    public DLSLogSender(Context context, Configuration configuration) {
        super(context, configuration);
        this.deviceInfo = DeviceInfo.getDeviceInfo(context);
    }

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
            int min = Math.min(51200, i3 - i2);
            while (true) {
                boolean hasNext = it.hasNext();
                manager = this.manager;
                if (!hasNext) {
                    break;
                }
                SimpleLog simpleLog = (SimpleLog) it.next();
                if (simpleLog.type == logType) {
                    if (simpleLog.data.getBytes().length + i4 > min) {
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
            this.executor.execute(new DLSAPIClient(logType, linkedBlockingQueue, this.configuration.trackingId, anonymousClass1));
            Debug.LogD("DLSLogSender", "send packet : num(" + linkedBlockingQueue.size() + ") size(" + i4 + ")");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.samsung.context.sdk.samsunganalytics.internal.sender.DLS.DLSLogSender$1] */
    @Override // com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender
    public final int send(Map map) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo();
        int i = -4;
        final int type = (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) ? -4 : activeNetworkInfo.getType();
        if (type == -4) {
            Debug.LogD("DLS Sender", "Network unavailable.");
        } else if (PolicyUtils.isPolicyExpired(this.context)) {
            Debug.LogD("DLS Sender", "policy expired. request policy");
            i = -6;
        } else {
            i = 0;
        }
        Manager manager = this.manager;
        if (i != 0) {
            insert(map);
            if (i == -6) {
                PolicyUtils.updatePolicy(this.context, this.configuration, this.executor, this.deviceInfo, null);
                if (manager.useDatabase) {
                    manager.dbManager.dbOpenHelper.getWritableDatabase().delete("logs_v2", ValueAnimator$$ExternalSyntheticOutline0.m("timestamp <= ", System.currentTimeMillis() - (5 * 86400000)), null);
                }
            }
            return i;
        }
        ?? r1 = new AsyncTaskCallback() { // from class: com.samsung.context.sdk.samsunganalytics.internal.sender.DLS.DLSLogSender.1
            @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskCallback
            public final void onFail(String str, String str2, String str3) {
                DLSLogSender dLSLogSender = DLSLogSender.this;
                Manager manager2 = dLSLogSender.manager;
                long parseLong = Long.parseLong(str);
                LogType logType = LogType.DEVICE;
                if (!str3.equals(logType.getAbbrev())) {
                    logType = LogType.UIX;
                }
                manager2.getClass();
                manager2.insert(new SimpleLog(parseLong, str2, logType));
                PolicyUtils.useQuota(dLSLogSender.context, type, str2.getBytes().length * (-1));
            }

            @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskCallback
            public final void onSuccess() {
            }
        };
        long parseLong = Long.parseLong((String) map.get("ts"));
        setCommonParamToLog(map);
        int sendOne = sendOne(type, new SimpleLog(parseLong, Utils.makeDelimiterString(map, Utils.Depth.ONE_DEPTH), BaseLogSender.getLogType(map)), r1);
        if (sendOne == -1) {
            return sendOne;
        }
        Queue queue = manager.get(200);
        if (manager.useDatabase) {
            flushBufferedLogs(type, LogType.UIX, queue, r1);
            flushBufferedLogs(type, LogType.DEVICE, queue, r1);
            return sendOne;
        }
        while (!queue.isEmpty() && (sendOne = sendOne(type, (SimpleLog) queue.poll(), r1)) != -1) {
        }
        return sendOne;
    }

    public final int sendOne(int i, SimpleLog simpleLog, AnonymousClass1 anonymousClass1) {
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
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i3, i4, "Quota : ", "/ Uploaded : ", "/ limit : ");
        m.append(i2);
        m.append("/ size : ");
        m.append(length);
        Debug.LogENG(m.toString());
        if (i3 < i4 + length) {
            StringBuilder m2 = MutableObjectList$$ExternalSyntheticOutline0.m(i3, i4, "send result fail : Over daily quota (quota: ", "/ uploaded: ", "/ size: ");
            m2.append(length);
            m2.append(")");
            Debug.LogD("DLS Sender", m2.toString());
            i5 = -1;
        } else if (i2 < length) {
            Debug.LogD("DLS Sender", MutableVectorKt$$ExternalSyntheticOutline0.m(i2, length, "send result fail : Over once quota (limit: ", "/ size: ", ")"));
            i5 = -11;
        } else {
            i5 = 0;
        }
        if (i5 != 0) {
            return i5;
        }
        PolicyUtils.useQuota(this.context, i, length);
        this.executor.execute(new DLSAPIClient(simpleLog, this.configuration.trackingId, anonymousClass1));
        return 0;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender
    public final Map setCommonParamToLog(Map map) {
        DeviceInfo deviceInfo = this.deviceInfo;
        map.put("la", deviceInfo.language);
        if (!TextUtils.isEmpty(deviceInfo.mcc)) {
            map.put("mcc", deviceInfo.mcc);
        }
        if (!TextUtils.isEmpty(deviceInfo.mnc)) {
            map.put("mnc", deviceInfo.mnc);
        }
        map.put("dm", deviceInfo.deviceModel);
        Configuration configuration = this.configuration;
        configuration.getClass();
        map.put("auid", null);
        map.put("do", deviceInfo.androidVersion);
        map.put("av", CommonUtils.getPackageVersion(this.context));
        map.put("uv", configuration.version);
        map.put("v", "6.05.073");
        map.put("at", String.valueOf(configuration.auidType));
        map.put("fv", deviceInfo.firmwareVersion);
        map.put("tid", configuration.trackingId);
        map.put("tz", String.valueOf(Utils.getTimeZoneOffset()));
        return map;
    }
}
