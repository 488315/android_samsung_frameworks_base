package com.android.systemui.statusbar.chips.ui.viewmodel;

import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel;
import com.android.systemui.statusbar.phone.ongoingcall.StatusBarChipsModernization;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class OngoingActivityChipViewModel$Companion$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        OngoingActivityChipViewModel.Companion companion = OngoingActivityChipViewModel.Companion.$$INSTANCE;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarChipsModernization.$r8$clinit;
        throw new IllegalStateException("New code path not supported when com.android.systemui.status_bar_chips_modernization is disabled.");
    }
}
