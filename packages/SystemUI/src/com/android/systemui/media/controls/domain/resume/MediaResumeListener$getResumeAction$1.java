package com.android.systemui.media.controls.domain.resume;

import android.content.ComponentName;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.os.Bundle;
import android.util.Log;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
            ComponentName componentName = resumeMediaBrowser.mComponentName;
            MediaBrowser.ConnectionCallback anonymousClass3 = new MediaBrowser.ConnectionCallback() { // from class: com.android.systemui.media.controls.domain.resume.ResumeMediaBrowser.3
                public AnonymousClass3() {
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
            resumeMediaBrowser.connectBrowser(new MediaBrowser(mediaBrowserFactory.mContext, componentName, anonymousClass3, bundle), "restart");
        }
    }
}
