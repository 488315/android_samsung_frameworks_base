package com.android.systemui.qs.tiles.dialog;

import com.android.systemui.plugins.qs.TileDetailsViewModel;
import com.android.systemui.screenrecord.RecordingController;

/* loaded from: classes2.dex */
public final class ScreenRecordDetailsViewModel implements TileDetailsViewModel {
    public final Runnable onStartRecordingClicked;

    public ScreenRecordDetailsViewModel(RecordingController recordingController, Runnable runnable) {
        this.onStartRecordingClicked = runnable;
    }

    @Override // com.android.systemui.plugins.qs.TileDetailsViewModel
    public final String getSubTitle() {
        return "";
    }

    @Override // com.android.systemui.plugins.qs.TileDetailsViewModel
    public final String getTitle() {
        return "Screen recording";
    }

    @Override // com.android.systemui.plugins.qs.TileDetailsViewModel
    public final void clickOnSettingsButton() {
    }
}
