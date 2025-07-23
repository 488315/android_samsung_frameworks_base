package com.android.systemui.statusbar.chips.call.ui.viewmodel;

import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.chips.call.ui.viewmodel.CallChipViewModel;
import com.android.systemui.statusbar.phone.ongoingcall.StatusBarChipsModernization;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class CallChipViewModel$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ CallChipViewModel$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                CallChipViewModel.Companion companion = CallChipViewModel.Companion;
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = StatusBarChipsModernization.$r8$clinit;
                throw new IllegalStateException("New code path not supported when com.android.systemui.status_bar_chips_modernization is disabled.");
            default:
                return "Chip clicked";
        }
    }
}
