package com.android.server.voiceinteraction;

import android.hardware.soundtrigger.SoundTrigger;

import java.util.Map;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
