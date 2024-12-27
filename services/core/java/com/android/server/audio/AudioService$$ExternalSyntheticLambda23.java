package com.android.server.audio;

import android.media.AudioSystem;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AudioService$$ExternalSyntheticLambda23 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioService f$0;

    public /* synthetic */ AudioService$$ExternalSyntheticLambda23(AudioService audioService, int i) {
        this.$r8$classId = i;
        this.f$0 = audioService;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        int i = this.$r8$classId;
        AudioService audioService = this.f$0;
        Integer num = (Integer) obj;
        Integer num2 = (Integer) obj2;
        switch (i) {
            case 0:
                AudioSystemAdapter audioSystemAdapter = audioService.mAudioSystem;
                int intValue = num.intValue();
                int intValue2 = num2.intValue();
                audioSystemAdapter.getClass();
                AudioSystem.setDeviceAbsoluteVolumeEnabled(intValue, "", true, intValue2);
                break;
            default:
                AudioSystemAdapter audioSystemAdapter2 = audioService.mAudioSystem;
                int intValue3 = num.intValue();
                int intValue4 = num2.intValue();
                audioSystemAdapter2.getClass();
                AudioSystem.setDeviceAbsoluteVolumeEnabled(intValue3, "", true, intValue4);
                break;
        }
    }
}
