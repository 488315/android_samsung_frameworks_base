package com.android.systemui.media.controls.util;

import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSession;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaControllerFactory {
    public final Context mContext;

    public MediaControllerFactory(Context context) {
        this.mContext = context;
    }

    public final MediaController create(MediaSession.Token token) {
        return new MediaController(this.mContext, token);
    }
}
