package com.android.settingslib.media.session;

import android.media.session.MediaSessionManager;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class MediaSessionManagerExtKt {
    public static final Flow getActiveMediaChanges(MediaSessionManager mediaSessionManager) {
        return FlowKt.buffer$default(FlowKt.callbackFlow(new MediaSessionManagerExtKt$activeMediaChanges$1(mediaSessionManager, null)), -1, 2);
    }

    public static final Flow getDefaultRemoteSessionChanged(MediaSessionManager mediaSessionManager) {
        return FlowKt.buffer$default(FlowKt.callbackFlow(new MediaSessionManagerExtKt$defaultRemoteSessionChanged$1(mediaSessionManager, null)), -1, 2);
    }
}
