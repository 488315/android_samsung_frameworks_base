package com.android.systemui.media.controls.resume;

import android.content.ComponentName;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.os.Bundle;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaResumeListener$getResumeAction$1 implements Runnable {
    public final /* synthetic */ ComponentName $componentName;
    public final /* synthetic */ MediaResumeListener this$0;

    public MediaResumeListener$getResumeAction$1(MediaResumeListener mediaResumeListener, ComponentName componentName) {
        this.this$0 = mediaResumeListener;
        this.$componentName = componentName;
    }

    @Override // java.lang.Runnable
    public final void run() {
        MediaResumeListener mediaResumeListener = this.this$0;
        ResumeMediaBrowserFactory resumeMediaBrowserFactory = mediaResumeListener.mediaBrowserFactory;
        mediaResumeListener.setMediaBrowser(new ResumeMediaBrowser(resumeMediaBrowserFactory.mContext, null, this.$componentName, resumeMediaBrowserFactory.mBrowserFactory, resumeMediaBrowserFactory.mLogger, mediaResumeListener.currentUserId));
        final ResumeMediaBrowser resumeMediaBrowser = this.this$0.mediaBrowser;
        if (resumeMediaBrowser != null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("android.service.media.extra.RECENT", true);
            MediaBrowser.ConnectionCallback c18043 = new MediaBrowser.ConnectionCallback() { // from class: com.android.systemui.media.controls.resume.ResumeMediaBrowser.3
                public C18043() {
                }

                @Override // android.media.browse.MediaBrowser.ConnectionCallback
                public final void onConnected() {
                    Log.d("ResumeMediaBrowser", "Connected for restart " + ResumeMediaBrowser.this.mMediaBrowser.isConnected());
                    ResumeMediaBrowser.this.updateMediaController();
                    if (!ResumeMediaBrowser.this.isBrowserConnected()) {
                        Callback callback = ResumeMediaBrowser.this.mCallback;
                        if (callback != null) {
                            callback.onError();
                        }
                        ResumeMediaBrowser.this.disconnect();
                        return;
                    }
                    MediaController createMediaController = ResumeMediaBrowser.this.createMediaController(ResumeMediaBrowser.this.mMediaBrowser.getSessionToken());
                    createMediaController.getTransportControls();
                    createMediaController.getTransportControls().prepare();
                    createMediaController.getTransportControls().play();
                    Callback callback2 = ResumeMediaBrowser.this.mCallback;
                    if (callback2 != null) {
                        callback2.onConnected();
                    }
                }

                @Override // android.media.browse.MediaBrowser.ConnectionCallback
                public final void onConnectionFailed() {
                    Callback callback = ResumeMediaBrowser.this.mCallback;
                    if (callback != null) {
                        callback.onError();
                    }
                    ResumeMediaBrowser.this.disconnect();
                }

                @Override // android.media.browse.MediaBrowser.ConnectionCallback
                public final void onConnectionSuspended() {
                    Callback callback = ResumeMediaBrowser.this.mCallback;
                    if (callback != null) {
                        callback.onError();
                    }
                    ResumeMediaBrowser.this.disconnect();
                }
            };
            MediaBrowserFactory mediaBrowserFactory = resumeMediaBrowser.mBrowserFactory;
            mediaBrowserFactory.getClass();
            resumeMediaBrowser.connectBrowser(new MediaBrowser(mediaBrowserFactory.mContext, resumeMediaBrowser.mComponentName, c18043, bundle), "restart");
        }
    }
}
