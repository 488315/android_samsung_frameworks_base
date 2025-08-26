package com.samsung.context.sdk.samsunganalytics.internal.sender;

import android.content.Context;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.Manager;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import com.sec.android.diagmonagent.common.util.executor.SingleThreadExecutor;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class BaseLogSender {
    public final Configuration configuration;
    public final Context context;
    public final SingleThreadExecutor executor = SingleThreadExecutor.getInstance();
    public final Manager manager;

    public BaseLogSender(Context context, Configuration configuration) {
        this.context = context.getApplicationContext();
        this.configuration = configuration;
        this.manager = Manager.getInstance(context, configuration);
    }

    public static LogType getLogType(Map map) {
        return "dl".equals((String) map.get("t")) ? LogType.DEVICE : LogType.UIX;
    }

    public final void insert(Map map) {
        this.manager.insert(new SimpleLog((String) map.get("t"), Long.parseLong((String) map.get("ts")), Utils.makeDelimiterString(setCommonParamToLog(map), Utils.Depth.ONE_DEPTH), getLogType(map)));
    }

    public abstract int send(Map map);

    public abstract Map setCommonParamToLog(Map map);
}
