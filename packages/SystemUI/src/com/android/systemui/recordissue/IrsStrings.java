package com.android.systemui.recordissue;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.screenrecord.RecordingServiceStrings;

public final class IrsStrings extends RecordingServiceStrings {
    public final Resources res;

    public IrsStrings(Resources resources) {
        super(resources);
        this.res = resources;
    }

    @Override // com.android.systemui.screenrecord.RecordingServiceStrings
    public final String getBackgroundProcessingLabel() {
        return this.res.getString(R.string.issuerecord_background_processing_label);
    }

    @Override // com.android.systemui.screenrecord.RecordingServiceStrings
    public final String getOngoingRecording() {
        return this.res.getString(R.string.issuerecord_ongoing_screen_only);
    }

    @Override // com.android.systemui.screenrecord.RecordingServiceStrings
    public final String getSaveTitle() {
        return this.res.getString(R.string.issuerecord_save_title);
    }

    @Override // com.android.systemui.screenrecord.RecordingServiceStrings
    public final String getStartError() {
        return this.res.getString(R.string.issuerecord_start_error);
    }

    @Override // com.android.systemui.screenrecord.RecordingServiceStrings
    public final String getTitle() {
        return this.res.getString(R.string.issuerecord_title);
    }
}
