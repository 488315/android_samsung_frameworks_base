package com.android.systemui.media.controls.ui.controller;

import android.animation.ValueAnimator;
import android.content.Intent;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MediaControlPanel$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                MediaControlPanel mediaControlPanel = (MediaControlPanel) obj;
                if (!mediaControlPanel.mMetadataAnimationHandler.isRunning()) {
                    mediaControlPanel.mMediaViewController.refreshState();
                    break;
                }
                break;
            case 1:
                final MediaControlPanel mediaControlPanel2 = (MediaControlPanel) obj;
                MediaData mediaData = mediaControlPanel2.mMediaData;
                final MediaButton mediaButton = mediaData.semanticActions;
                mediaControlPanel2.bindScrubbingTime(mediaData);
                MediaControlPanel.SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING.forEach(new Consumer() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda13
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        MediaControlPanel mediaControlPanel3 = mediaControlPanel2;
                        MediaButton mediaButton2 = mediaButton;
                        Integer num = (Integer) obj2;
                        Intent intent = MediaControlPanel.SETTINGS_INTENT;
                        mediaControlPanel3.getClass();
                        mediaControlPanel3.setSemanticButtonVisibleAndAlpha(num.intValue(), mediaButton2.getActionById(num.intValue()), mediaButton2);
                    }
                });
                if (!mediaControlPanel2.mMetadataAnimationHandler.isRunning()) {
                    mediaControlPanel2.mMediaViewController.refreshState();
                    break;
                }
                break;
            default:
                LoadingEffect loadingEffect = (LoadingEffect) obj;
                if (loadingEffect.state == LoadingEffect.AnimationState.MAIN) {
                    ValueAnimator valueAnimator = loadingEffect.currentAnimator;
                    if (valueAnimator != null) {
                        valueAnimator.pause();
                    }
                    loadingEffect.currentAnimator = null;
                    loadingEffect.playEaseOut();
                    break;
                }
                break;
        }
    }
}
