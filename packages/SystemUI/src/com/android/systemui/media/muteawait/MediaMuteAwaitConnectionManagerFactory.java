package com.android.systemui.media.muteawait;

import android.content.Context;
import com.android.settingslib.media.DeviceIconUtil;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
