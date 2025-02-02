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

/* loaded from: classes.dex */
public abstract class BaseLogSender implements LogSender {
    protected Configuration configuration;
    protected Context context;
    protected DeviceInfo deviceInfo;
    protected Manager manager;
    protected SingleThreadExecutor executor = SingleThreadExecutor.getInstance();
    protected Delimiter<String, String> delimiterUtil = new Delimiter<>();

    public BaseLogSender(Context context, Configuration configuration) {
        this.context = context.getApplicationContext();
        this.configuration = configuration;
        this.deviceInfo = new DeviceInfo(context);
        this.manager = Manager.getInstance(context, configuration);
    }

    protected static LogType getLogType(Map map) {
        return "dl".equals((String) ((HashMap) map).get("t")) ? LogType.DEVICE : LogType.UIX;
    }

    protected final void insert(Map<String, String> map) {
        HashMap hashMap = (HashMap) map;
        this.manager.insert(new SimpleLog((String) hashMap.get("t"), Long.valueOf((String) hashMap.get("ts")).longValue(), makeBodyString(setCommonParamToLog(hashMap)), getLogType(hashMap)));
    }

    protected final String makeBodyString(Map<String, String> map) {
        Delimiter<String, String> delimiter = this.delimiterUtil;
        Delimiter.Depth depth = Delimiter.Depth.ONE_DEPTH;
        delimiter.getClass();
        return Delimiter.makeDelimiterString(map, depth);
    }

    protected Map<String, String> setCommonParamToLog(Map<String, String> map) {
        if (PolicyUtils.getSenderType() < 2) {
            map.put("la", this.deviceInfo.getLanguage());
            if (!TextUtils.isEmpty(this.deviceInfo.getMcc())) {
                map.put("mcc", this.deviceInfo.getMcc());
            }
            if (!TextUtils.isEmpty(this.deviceInfo.getMnc())) {
                map.put("mnc", this.deviceInfo.getMnc());
            }
            map.put("dm", this.deviceInfo.getDeviceModel());
            map.put("auid", this.configuration.getDeviceId());
            map.put("do", this.deviceInfo.getAndroidVersion());
            map.put("av", this.deviceInfo.getAppVersionName());
            map.put("uv", this.configuration.getVersion());
            map.put("at", String.valueOf(this.configuration.getAuidType()));
            map.put("fv", this.deviceInfo.getFirmwareVersion());
            map.put("tid", this.configuration.getTrackingId());
        }
        map.put("v", "6.05.015");
        map.put("tz", this.deviceInfo.getTimeZoneOffset());
        this.configuration.getClass();
        return map;
    }
}
