package com.android.systemui.media.controls.util;

import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSession;

public final class MediaControllerFactory {
    public final Context mContext;

    public MediaControllerFactory(Context context) {
        this.mContext = context;
    }

    public final MediaController create(MediaSession.Token token) {
        return new MediaController(this.mContext, token);
    }
}
