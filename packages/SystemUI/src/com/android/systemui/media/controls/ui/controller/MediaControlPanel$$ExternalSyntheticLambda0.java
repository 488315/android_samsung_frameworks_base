package com.android.systemui.media.controls.ui.controller;

import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda0 implements SeekBarViewModel.ScrubbingChangeListener, SeekBarViewModel.EnabledChangeListener {
    public final /* synthetic */ MediaControlPanel f$0;

    @Override // com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel.EnabledChangeListener
    public void onEnabledChanged(boolean z) {
        MediaControlPanel mediaControlPanel = this.f$0;
        if (z == mediaControlPanel.mIsSeekBarEnabled) {
            return;
        }
        mediaControlPanel.mIsSeekBarEnabled = z;
        mediaControlPanel.updateSeekBarVisibility();
    }

    @Override // com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel.ScrubbingChangeListener
    public void onScrubbingChanged(boolean z) {
        MediaControlPanel mediaControlPanel = this.f$0;
        MediaData mediaData = mediaControlPanel.mMediaData;
        if (mediaData == null || mediaData.semanticActions == null || z == mediaControlPanel.mIsScrubbing) {
            return;
        }
        mediaControlPanel.mIsScrubbing = z;
        mediaControlPanel.mMainExecutor.execute(new MediaControlPanel$$ExternalSyntheticLambda6(mediaControlPanel, 1));
    }
}
