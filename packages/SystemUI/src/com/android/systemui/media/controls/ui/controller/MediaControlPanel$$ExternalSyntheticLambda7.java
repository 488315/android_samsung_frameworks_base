package com.android.systemui.media.controls.ui.controller;

import android.media.session.MediaController;
import android.util.Log;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaControlPanel f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ MediaControlPanel$$ExternalSyntheticLambda7(MediaControlPanel mediaControlPanel, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = mediaControlPanel;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mSeekBarViewModel.updateController((MediaController) this.f$1);
                break;
            default:
                MediaControlPanel mediaControlPanel = this.f$0;
                MediaData mediaData = (MediaData) this.f$1;
                if (mediaControlPanel.mKey == null) {
                    Log.w("MediaControlPanel", "Dismiss media with null notification. Token uid=" + mediaData.token.getUid());
                    break;
                } else {
                    mediaControlPanel.closeGuts(false);
                    if (!((MediaDataManager) mediaControlPanel.mMediaDataManagerLazy.get()).dismissMediaData(mediaControlPanel.mKey, MediaViewController.GUTS_ANIMATION_DURATION + 100, true)) {
                        Log.w("MediaControlPanel", "Manager failed to dismiss media " + mediaControlPanel.mKey);
                        mediaControlPanel.mMediaCarouselController.removePlayer(mediaControlPanel.mKey, true);
                        break;
                    }
                }
                break;
        }
    }
}
