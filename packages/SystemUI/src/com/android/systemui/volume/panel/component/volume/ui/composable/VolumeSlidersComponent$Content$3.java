package com.android.systemui.volume.panel.component.volume.ui.composable;

import com.android.systemui.volume.panel.component.volume.ui.viewmodel.AudioVolumeComponentViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final /* synthetic */ class VolumeSlidersComponent$Content$3 extends FunctionReferenceImpl implements Function1 {
    public VolumeSlidersComponent$Content$3(Object obj) {
        super(1, obj, AudioVolumeComponentViewModel.class, "onExpandedChanged", "onExpandedChanged(Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        ((AudioVolumeComponentViewModel) this.receiver).onExpandedChanged(((Boolean) obj).booleanValue());
        return Unit.INSTANCE;
    }
}
