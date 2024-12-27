package com.android.server.voiceinteraction;

import android.media.AudioManagerInternal;

import com.android.server.LocalServices;

public final /* synthetic */ class HotwordDetectionConnection$$ExternalSyntheticLambda7
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ HotwordDetectionConnection$$ExternalSyntheticLambda7(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        switch (i) {
            case 0:
                AudioManagerInternal audioManagerInternal =
                        (AudioManagerInternal) LocalServices.getService(AudioManagerInternal.class);
                if (audioManagerInternal != null) {
                    audioManagerInternal.removeAssistantServiceUid(i2);
                    break;
                }
                break;
            default:
                AudioManagerInternal audioManagerInternal2 =
                        (AudioManagerInternal) LocalServices.getService(AudioManagerInternal.class);
                if (audioManagerInternal2 != null) {
                    audioManagerInternal2.addAssistantServiceUid(i2);
                    break;
                }
                break;
        }
    }
}
