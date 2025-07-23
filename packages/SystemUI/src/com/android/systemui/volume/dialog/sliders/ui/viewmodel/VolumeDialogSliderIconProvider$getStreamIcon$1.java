package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import com.android.settingslib.volume.shared.model.RingerMode;
import com.android.systemui.statusbar.policy.domain.model.ActiveZenModes;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogSliderIconProvider$getStreamIcon$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ boolean $isMuted;
    final /* synthetic */ boolean $isRoutedToBluetooth;
    final /* synthetic */ int $level;
    final /* synthetic */ int $levelMax;
    final /* synthetic */ int $levelMin;
    final /* synthetic */ int $stream;
    int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ VolumeDialogSliderIconProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogSliderIconProvider$getStreamIcon$1(VolumeDialogSliderIconProvider volumeDialogSliderIconProvider, int i, int i2, int i3, int i4, boolean z, boolean z2, Continuation continuation) {
        super(3, continuation);
        this.this$0 = volumeDialogSliderIconProvider;
        this.$stream = i;
        this.$level = i2;
        this.$levelMin = i3;
        this.$levelMax = i4;
        this.$isMuted = z;
        this.$isRoutedToBluetooth = z2;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        VolumeDialogSliderIconProvider$getStreamIcon$1 volumeDialogSliderIconProvider$getStreamIcon$1 = new VolumeDialogSliderIconProvider$getStreamIcon$1(this.this$0, this.$stream, this.$level, this.$levelMin, this.$levelMax, this.$isMuted, this.$isRoutedToBluetooth, (Continuation) obj3);
        volumeDialogSliderIconProvider$getStreamIcon$1.L$0 = (ActiveZenModes) obj;
        volumeDialogSliderIconProvider$getStreamIcon$1.L$1 = (RingerMode) obj2;
        return volumeDialogSliderIconProvider$getStreamIcon$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00d8  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            Method dump skipped, instructions count: 327
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderIconProvider$getStreamIcon$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
