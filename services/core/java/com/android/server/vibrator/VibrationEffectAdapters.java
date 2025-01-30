package com.android.server.vibrator;

import android.os.VibrationEffect;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class VibrationEffectAdapters {

    public interface SegmentsAdapter {
        int apply(List list, int i, Object obj);
    }

    public static VibrationEffect apply(VibrationEffect vibrationEffect, List list, Object obj) {
        if (!(vibrationEffect instanceof VibrationEffect.Composed)) {
            return vibrationEffect;
        }
        VibrationEffect.Composed composed = (VibrationEffect.Composed) vibrationEffect;
        ArrayList arrayList = new ArrayList(composed.getSegments());
        int repeatIndex = composed.getRepeatIndex();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            repeatIndex = ((SegmentsAdapter) list.get(i)).apply(arrayList, repeatIndex, obj);
        }
        return new VibrationEffect.Composed(arrayList, repeatIndex);
    }
}
