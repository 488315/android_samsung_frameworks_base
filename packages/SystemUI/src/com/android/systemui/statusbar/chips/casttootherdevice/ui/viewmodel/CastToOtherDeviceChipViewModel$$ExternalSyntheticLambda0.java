package com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel;

import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel.CastToOtherDeviceChipViewModel;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class CastToOtherDeviceChipViewModel$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ CastToOtherDeviceChipViewModel$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                CastToOtherDeviceChipViewModel.Companion companion = CastToOtherDeviceChipViewModel.Companion;
                return "Stop casting requested from dialog (router)";
            case 1:
                CastToOtherDeviceChipViewModel.Companion companion2 = CastToOtherDeviceChipViewModel.Companion;
                return "Stop casting requested from dialog (projection)";
            default:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("projectionChip=", logMessage.getStr1(), " > routerChip=", logMessage.getStr2());
        }
    }
}
