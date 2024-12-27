package com.android.server.display.brightness.clamper;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BrightnessClamperController$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ BrightnessClamperController$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((BrightnessModifier) obj).setAmbientLux(-1.0f);
                break;
            case 1:
                ((BrightnessClamper) obj).onDeviceConfigChanged();
                break;
            case 2:
                ((BrightnessClamper) obj).stop();
                break;
            default:
                ((BrightnessModifier) obj).stop();
                break;
        }
    }
}
