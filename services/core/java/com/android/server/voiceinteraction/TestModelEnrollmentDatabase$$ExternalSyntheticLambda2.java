package com.android.server.voiceinteraction;

import android.hardware.soundtrigger.SoundTrigger;

import java.util.Map;
import java.util.function.Function;

public final /* synthetic */ class TestModelEnrollmentDatabase$$ExternalSyntheticLambda2
        implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        switch (this.$r8$classId) {
        }
        return (SoundTrigger.KeyphraseSoundModel) entry.getValue();
    }
}
