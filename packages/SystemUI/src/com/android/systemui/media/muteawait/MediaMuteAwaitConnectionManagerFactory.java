package com.android.systemui.media.muteawait;

import android.content.Context;
import com.android.settingslib.media.DeviceIconUtil;
import java.util.concurrent.Executor;

public final class MediaMuteAwaitConnectionManagerFactory {
    public final Context context;
    public final DeviceIconUtil deviceIconUtil;
    public final MediaMuteAwaitLogger logger;
    public final Executor mainExecutor;

    public MediaMuteAwaitConnectionManagerFactory(Context context, MediaMuteAwaitLogger mediaMuteAwaitLogger, Executor executor) {
        this.context = context;
        this.logger = mediaMuteAwaitLogger;
        this.mainExecutor = executor;
        this.deviceIconUtil = new DeviceIconUtil(context);
    }
}
