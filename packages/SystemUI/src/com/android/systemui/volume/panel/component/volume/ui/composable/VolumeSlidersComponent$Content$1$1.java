package com.android.systemui.volume.panel.component.volume.ui.composable;

import com.android.systemui.volume.panel.component.volume.ui.viewmodel.AudioVolumeComponentViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
final /* synthetic */ class VolumeSlidersComponent$Content$1$1 extends FunctionReferenceImpl implements Function1 {
    public VolumeSlidersComponent$Content$1$1(Object obj) {
        super(1, obj, AudioVolumeComponentViewModel.class, "onExpandedChanged", "onExpandedChanged(Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        ((AudioVolumeComponentViewModel) this.receiver).onExpandedChanged(((Boolean) obj).booleanValue());
        return Unit.INSTANCE;
    }
}
