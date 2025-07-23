package com.samsung.android.sdk.command.template;

import android.os.Bundle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MediaControlTemplate extends CommandTemplate {
    public final int mCurrentActiveMode;
    public final String mMediaInfo;
    public final int mModeFlags;

    public MediaControlTemplate(int i, int i2, String str) {
        super("mediacontrol");
        this.mCurrentActiveMode = i;
        this.mModeFlags = i2;
        this.mMediaInfo = str;
    }

    @Override // com.samsung.android.sdk.command.template.CommandTemplate
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putInt("key_current_active_mode", this.mCurrentActiveMode);
        dataBundle.putInt("key_mode_flags", this.mModeFlags);
        dataBundle.putString("key_media_info", this.mMediaInfo);
        return dataBundle;
    }

    @Override // com.samsung.android.sdk.command.template.CommandTemplate
    public final int getTemplateType() {
        return 7;
    }

    public MediaControlTemplate(Bundle bundle) {
        super(bundle);
        this.mCurrentActiveMode = bundle.getInt("key_current_active_mode");
        this.mModeFlags = bundle.getInt("key_mode_flags");
        this.mMediaInfo = bundle.getString("key_media_info");
    }
}
