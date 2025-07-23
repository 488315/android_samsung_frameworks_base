package com.android.systemui.volume.panel.component.volume.ui.composable;

import androidx.compose.runtime.MutableState;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ColumnVolumeSlidersKt$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SliderViewModel f$0;
    public final /* synthetic */ MutableState f$1;

    public /* synthetic */ ColumnVolumeSlidersKt$$ExternalSyntheticLambda1(SliderViewModel sliderViewModel, MutableState mutableState, int i) {
        this.$r8$classId = i;
        this.f$0 = sliderViewModel;
        this.f$1 = mutableState;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.toggleMuted((SliderState) this.f$1.getValue());
                break;
            default:
                this.f$0.toggleMuted((SliderState) this.f$1.getValue());
                break;
        }
        return Unit.INSTANCE;
    }
}
