package com.android.systemui.media.muteawait;

import android.content.Context;
import com.android.settingslib.media.DeviceIconUtil;
import com.android.systemui.media.controls.util.MediaFlags;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaMuteAwaitConnectionManagerFactory {
    public final Context context;
    public final DeviceIconUtil deviceIconUtil = new DeviceIconUtil();
    public final MediaMuteAwaitLogger logger;
    public final Executor mainExecutor;
    public final MediaFlags mediaFlags;

    public MediaMuteAwaitConnectionManagerFactory(MediaFlags mediaFlags, Context context, MediaMuteAwaitLogger mediaMuteAwaitLogger, Executor executor) {
        this.mediaFlags = mediaFlags;
        this.context = context;
        this.logger = mediaMuteAwaitLogger;
        this.mainExecutor = executor;
    }
}
