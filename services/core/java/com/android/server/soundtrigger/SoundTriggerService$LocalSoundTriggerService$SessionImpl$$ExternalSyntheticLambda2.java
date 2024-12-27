package com.android.server.soundtrigger;

import java.util.function.Consumer;

public final /* synthetic */
class SoundTriggerService$LocalSoundTriggerService$SessionImpl$$ExternalSyntheticLambda2
        implements Consumer {
    public final /* synthetic */ SoundTriggerHelper f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SoundTriggerHelper soundTriggerHelper = this.f$0;
        boolean booleanValue = ((Boolean) obj).booleanValue();
        synchronized (soundTriggerHelper.mLock) {
            try {
                if (soundTriggerHelper.mIsAppOpPermitted == booleanValue) {
                    return;
                }
                soundTriggerHelper.mIsAppOpPermitted = booleanValue;
                soundTriggerHelper.updateAllRecognitionsLocked();
            } finally {
            }
        }
    }
}
