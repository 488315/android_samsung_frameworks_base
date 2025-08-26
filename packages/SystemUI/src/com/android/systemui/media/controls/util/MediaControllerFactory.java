package com.android.systemui.media.controls.util;

import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSession;

/* loaded from: classes2.dex */
public class MediaControllerFactory {
    public final Context context;

    public MediaControllerFactory(Context context) {
        this.context = context;
    }

    public final MediaController create(MediaSession.Token token) {
        return new MediaController(this.context, token);
    }
}
