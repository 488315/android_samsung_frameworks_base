package com.android.server.media;

import android.content.Context;

import com.samsung.android.server.audio.AudioSettingsHelper;

public class CustomSessionPolicyProvider extends MediaSessionPolicyProvider {
    private final AudioSettingsHelper mAudioSettingsHelper;

    public CustomSessionPolicyProvider(Context context) {
        this.mAudioSettingsHelper = AudioSettingsHelper.getInstance(context);
    }

    @Override // com.android.server.media.MediaSessionPolicyProvider
    public int getSessionPoliciesForApplication(int i, String str) {
        return this.mAudioSettingsHelper.checkAppCategory(str, "media_button_deny") ? 1 : 0;
    }
}
