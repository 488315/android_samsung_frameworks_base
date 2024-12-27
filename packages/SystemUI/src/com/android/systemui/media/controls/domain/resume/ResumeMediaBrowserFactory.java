package com.android.systemui.media.controls.domain.resume;

import android.content.Context;

public final class ResumeMediaBrowserFactory {
    public final MediaBrowserFactory mBrowserFactory;
    public final Context mContext;
    public final ResumeMediaBrowserLogger mLogger;

    public ResumeMediaBrowserFactory(Context context, MediaBrowserFactory mediaBrowserFactory, ResumeMediaBrowserLogger resumeMediaBrowserLogger) {
        this.mContext = context;
        this.mBrowserFactory = mediaBrowserFactory;
        this.mLogger = resumeMediaBrowserLogger;
    }
}
