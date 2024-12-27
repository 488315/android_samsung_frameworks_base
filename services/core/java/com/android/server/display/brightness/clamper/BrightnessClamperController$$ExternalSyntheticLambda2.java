package com.android.server.display.brightness.clamper;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BrightnessClamperController$$ExternalSyntheticLambda2
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ BrightnessClamperController$$ExternalSyntheticLambda2(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((BrightnessModifier) obj).shouldListenToLightSensor();
            default:
                return ((BrightnessClamper) obj).mIsActive;
        }
    }
}
