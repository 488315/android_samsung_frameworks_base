package com.android.systemui.statusbar.layout.ui.viewmodel;

import com.android.systemui.statusbar.layout.StatusBarContentInsetsProvider;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class StatusBarContentInsetsViewModel {
    public final Flow contentArea = FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new StatusBarContentInsetsViewModel$contentArea$2(this, null), FlowConflatedKt.conflatedCallbackFlow(new StatusBarContentInsetsViewModel$contentArea$1(this, null))));
    public final StatusBarContentInsetsProvider statusBarContentInsetsProvider;

    public StatusBarContentInsetsViewModel(StatusBarContentInsetsProvider statusBarContentInsetsProvider) {
        this.statusBarContentInsetsProvider = statusBarContentInsetsProvider;
    }
}
