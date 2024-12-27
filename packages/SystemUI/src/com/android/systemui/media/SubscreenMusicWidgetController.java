package com.android.systemui.media;

import android.content.Context;

public final class SubscreenMusicWidgetController {
    public final Context mContext;
    public final SecMediaHost mMediaHost;

    public SubscreenMusicWidgetController(Context context, SecMediaHost secMediaHost) {
        this.mContext = context;
        this.mMediaHost = secMediaHost;
    }
}
