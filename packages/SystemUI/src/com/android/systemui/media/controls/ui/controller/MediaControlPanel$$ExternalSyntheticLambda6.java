package com.android.systemui.media.controls.ui.controller;

import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MediaControlPanel$$ExternalSyntheticLambda6(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                final TurbulenceNoiseController turbulenceNoiseController = (TurbulenceNoiseController) obj;
                if (turbulenceNoiseController.state == TurbulenceNoiseController.Companion.AnimationState.MAIN) {
                    turbulenceNoiseController.turbulenceNoiseView.finish(new Runnable() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController$finish$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            TurbulenceNoiseController turbulenceNoiseController2 = TurbulenceNoiseController.this;
                            if (turbulenceNoiseController2.state != TurbulenceNoiseController.Companion.AnimationState.MAIN) {
                                return;
                            }
                            turbulenceNoiseController2.setState(TurbulenceNoiseController.Companion.AnimationState.EASE_OUT);
                            turbulenceNoiseController2.turbulenceNoiseView.playEaseOut(new TurbulenceNoiseController$playEaseOutAnimation$1(turbulenceNoiseController2));
                        }
                    });
                    break;
                }
                break;
            default:
                final MediaControlPanel mediaControlPanel = (MediaControlPanel) obj;
                MediaData mediaData = mediaControlPanel.mMediaData;
                final MediaButton mediaButton = mediaData.semanticActions;
                mediaControlPanel.bindScrubbingTime(mediaData);
                MediaControlPanel.SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING.forEach(new Consumer() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda18
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        MediaControlPanel mediaControlPanel2 = MediaControlPanel.this;
                        MediaButton mediaButton2 = mediaButton;
                        Integer num = (Integer) obj2;
                        mediaControlPanel2.getClass();
                        mediaControlPanel2.setSemanticButtonVisibleAndAlpha(num.intValue(), mediaButton2.getActionById(num.intValue()), mediaButton2);
                    }
                });
                if (!mediaControlPanel.mMetadataAnimationHandler.isRunning()) {
                    mediaControlPanel.mMediaViewController.refreshState();
                    break;
                }
                break;
        }
    }
}
