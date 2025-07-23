package com.android.systemui.media.controls.util;

import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSession;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
