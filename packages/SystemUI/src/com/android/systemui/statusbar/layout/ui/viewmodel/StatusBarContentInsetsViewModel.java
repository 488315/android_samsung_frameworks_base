package com.android.systemui.statusbar.layout.ui.viewmodel;

import com.android.systemui.statusbar.layout.StatusBarContentInsetsProvider;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarContentInsetsViewModel {
    public final Flow contentArea = FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new StatusBarContentInsetsViewModel$contentArea$2(this, null), FlowConflatedKt.conflatedCallbackFlow(new StatusBarContentInsetsViewModel$contentArea$1(this, null))));
    public final StatusBarContentInsetsProvider statusBarContentInsetsProvider;

    public StatusBarContentInsetsViewModel(StatusBarContentInsetsProvider statusBarContentInsetsProvider) {
        this.statusBarContentInsetsProvider = statusBarContentInsetsProvider;
    }
}
