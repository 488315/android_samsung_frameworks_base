package com.android.systemui.volume.panel.component.volume.ui.composable;

import androidx.compose.runtime.MutableState;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class ColumnVolumeSlidersKt$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SliderViewModel f$0;
    public final /* synthetic */ MutableState f$1;

    public /* synthetic */ ColumnVolumeSlidersKt$$ExternalSyntheticLambda0(SliderViewModel sliderViewModel, MutableState mutableState, int i) {
        this.$r8$classId = i;
        this.f$0 = sliderViewModel;
        this.f$1 = mutableState;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        int i = this.$r8$classId;
        float fFloatValue = ((Float) obj).floatValue();
        switch (i) {
            case 0:
                this.f$0.onValueChanged((SliderState) this.f$1.getValue(), fFloatValue);
                break;
            default:
                this.f$0.onValueChanged((SliderState) this.f$1.getValue(), fFloatValue);
                break;
        }
        return Unit.INSTANCE;
    }
}
