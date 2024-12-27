package com.samsung.context.sdk.samsunganalytics.internal.sender;

import android.content.Context;
import android.text.TextUtils;

import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.device.DeviceInfo;
import com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.Manager;
import com.samsung.context.sdk.samsunganalytics.internal.util.Delimiter;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseLogSender {
    public final Configuration configuration;
    public final Context context;
    public final DeviceInfo deviceInfo;
    public final Manager manager;
    public final SingleThreadExecutor executor = SingleThreadExecutor.getInstance();
    public final Delimiter delimiterUtil = new Delimiter();

    public BaseLogSender(Context context, Configuration configuration) {
        this.context = context.getApplicationContext();
        this.configuration = configuration;
        this.deviceInfo = new DeviceInfo(context);
        this.manager = Manager.getInstance(context, configuration);
    }

    public static LogType getLogType(Map map) {
        return "dl".equals((String) ((HashMap) map).get("t")) ? LogType.DEVICE : LogType.UIX;
    }

    public final void insert(Map map) {
        HashMap hashMap = (HashMap) map;
        String str = (String) hashMap.get("t");
        long longValue = Long.valueOf((String) hashMap.get("ts")).longValue();
        Map commonParamToLog = setCommonParamToLog(hashMap);
        Delimiter.Depth depth = Delimiter.Depth.ONE_DEPTH;
        this.delimiterUtil.getClass();
        this.manager.insert(
                new SimpleLog(
                        str,
                        longValue,
                        Delimiter.makeDelimiterString(commonParamToLog, depth),
                        getLogType(hashMap)));
    }

    public abstract int send(Map map);

    public Map setCommonParamToLog(Map map) {
        int i = PolicyUtils.senderType;
        Configuration configuration = this.configuration;
        DeviceInfo deviceInfo = this.deviceInfo;
        if (i < 2) {
            map.put("la", deviceInfo.language);
            if (!TextUtils.isEmpty(deviceInfo.mcc)) {
                map.put("mcc", deviceInfo.mcc);
            }
            if (!TextUtils.isEmpty(deviceInfo.mnc)) {
                map.put("mnc", deviceInfo.mnc);
            }
            map.put("dm", deviceInfo.deviceModel);
            map.put("auid", configuration.deviceId);
            map.put("do", deviceInfo.androidVersion);
            map.put("av", deviceInfo.appVersionName);
            map.put("uv", configuration.version);
            map.put("at", String.valueOf(configuration.auidType));
            map.put("fv", deviceInfo.firmwareVersion);
            map.put("tid", configuration.trackingId);
        }
        map.put("v", "6.05.015");
        map.put("tz", deviceInfo.timeZoneOffset);
        configuration.getClass();
        return map;
    }
}
