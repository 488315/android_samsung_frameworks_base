package com.android.systemui.screenrecord;

import android.content.res.Resources;
import com.android.systemui.R;

public class RecordingServiceStrings {
    public final Resources res;

    public RecordingServiceStrings(Resources resources) {
        this.res = resources;
    }

    public String getBackgroundProcessingLabel() {
        return this.res.getString(R.string.screenrecord_background_processing_label);
    }

    public String getOngoingRecording() {
        return this.res.getString(R.string.screenrecord_ongoing_screen_only);
    }

    public String getSaveTitle() {
        return this.res.getString(R.string.screenrecord_save_title);
    }

    public String getStartError() {
        return this.res.getString(R.string.screenrecord_start_error);
    }

    public String getTitle() {
        return this.res.getString(R.string.screenrecord_title);
    }
}
