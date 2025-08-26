package com.android.systemui.statusbar.chips.ui.viewmodel;

import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel;
import com.android.systemui.statusbar.phone.ongoingcall.StatusBarChipsModernization;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class OngoingActivityChipViewModel$Companion$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        OngoingActivityChipViewModel.Companion companion = OngoingActivityChipViewModel.Companion.$$INSTANCE;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarChipsModernization.$r8$clinit;
        throw new IllegalStateException("New code path not supported when com.android.systemui.status_bar_chips_modernization is disabled.");
    }
}
