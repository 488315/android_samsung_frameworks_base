package com.android.systemui.screenrecord;

import android.content.res.Resources;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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

    public String getSaveError() {
        return this.res.getString(R.string.screenrecord_save_error);
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
