package com.android.systemui.qs.tiles.dialog;

import com.android.systemui.plugins.qs.TileDetailsViewModel;
import com.android.systemui.screenrecord.RecordingController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
