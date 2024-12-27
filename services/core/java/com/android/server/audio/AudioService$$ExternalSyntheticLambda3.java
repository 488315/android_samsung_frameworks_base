package com.android.server.audio;

import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.audiopolicy.AudioProductStrategy;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AudioService$$ExternalSyntheticLambda3 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = AudioService.BECOMING_NOISY_DELAY_MS;
                return ((AudioDeviceAttributes) obj).getRole() == 2;
            case 1:
                int i2 = AudioService.BECOMING_NOISY_DELAY_MS;
                return !((AudioAttributes) obj).equals(AudioProductStrategy.getDefaultAttributes());
            case 2:
                int i3 = AudioService.BECOMING_NOISY_DELAY_MS;
                return ((AudioDeviceAttributes) obj).getRole() == 1;
            default:
                return ((BooleanSupplier) obj).getAsBoolean();
        }
    }
}
