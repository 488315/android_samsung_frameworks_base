package com.android.server.media;

import android.content.Context;
import com.samsung.android.server.audio.AudioSettingsHelper;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
