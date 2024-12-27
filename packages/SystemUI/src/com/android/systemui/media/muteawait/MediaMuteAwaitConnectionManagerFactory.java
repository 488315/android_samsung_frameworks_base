package com.android.systemui.media.muteawait;

import android.content.Context;
import com.android.settingslib.media.DeviceIconUtil;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
