package com.android.internal.vibrator.persistence;

import com.android.internal.vibrator.persistence.SerializedAmplitudeStepWaveform;
import java.util.function.BiConsumer;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes4.dex */
public final /* synthetic */ class VibrationEffectSerializer$$ExternalSyntheticLambda0 implements BiConsumer {
    public final /* synthetic */ SerializedAmplitudeStepWaveform.Builder f$0;

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        this.f$0.addDurationAndAmplitude(((Long) obj).longValue(), ((Integer) obj2).intValue());
    }
}
