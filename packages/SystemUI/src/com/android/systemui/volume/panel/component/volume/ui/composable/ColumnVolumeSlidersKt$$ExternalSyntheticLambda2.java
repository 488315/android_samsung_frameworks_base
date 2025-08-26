package com.android.systemui.volume.panel.component.volume.ui.composable;

import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class ColumnVolumeSlidersKt$$ExternalSyntheticLambda2 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SliderViewModel f$0;

    public /* synthetic */ ColumnVolumeSlidersKt$$ExternalSyntheticLambda2(SliderViewModel sliderViewModel, int i) {
        this.$r8$classId = i;
        this.f$0 = sliderViewModel;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.onValueChangeFinished();
                break;
            default:
                this.f$0.onValueChangeFinished();
                break;
        }
        return Unit.INSTANCE;
    }
}
