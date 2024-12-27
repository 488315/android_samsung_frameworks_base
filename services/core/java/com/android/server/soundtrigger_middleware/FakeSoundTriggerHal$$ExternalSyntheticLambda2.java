package com.android.server.soundtrigger_middleware;

import android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class FakeSoundTriggerHal$$ExternalSyntheticLambda2
        implements FunctionalUtils.ThrowingConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ FakeSoundTriggerHal$$ExternalSyntheticLambda2(int i) {
        this.$r8$classId = i;
    }

    public final void acceptOrThrow(Object obj) {
        ISoundTriggerHwGlobalCallback iSoundTriggerHwGlobalCallback =
                (ISoundTriggerHwGlobalCallback) obj;
        switch (this.$r8$classId) {
            case 0:
                iSoundTriggerHwGlobalCallback.onResourcesAvailable();
                break;
            case 1:
                iSoundTriggerHwGlobalCallback.onResourcesAvailable();
                break;
            case 2:
                iSoundTriggerHwGlobalCallback.onResourcesAvailable();
                break;
            default:
                iSoundTriggerHwGlobalCallback.onResourcesAvailable();
                break;
        }
    }
}
