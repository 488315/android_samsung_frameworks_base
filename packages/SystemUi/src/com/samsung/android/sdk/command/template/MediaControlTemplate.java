package com.samsung.android.sdk.command.template;

import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class MediaControlTemplate extends CommandTemplate {
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
