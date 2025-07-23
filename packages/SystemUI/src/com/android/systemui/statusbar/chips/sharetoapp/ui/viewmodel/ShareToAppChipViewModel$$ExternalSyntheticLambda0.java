package com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel;

import com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ShareToAppChipViewModel$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ShareToAppChipViewModel.Companion companion = ShareToAppChipViewModel.Companion;
                return "The media projection stop dialog was dismissed";
            case 1:
                ShareToAppChipViewModel.Companion companion2 = ShareToAppChipViewModel.Companion;
                return "Stop sharing requested from dialog";
            default:
                return "Hiding the chip as stop dialog is being shown";
        }
    }
}
