package com.android.systemui.common.ui.view;

import android.view.View;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ViewExtKt$onApplyWindowInsets$1 implements DisposableHandle {
    public final /* synthetic */ View $this_onApplyWindowInsets;

    public ViewExtKt$onApplyWindowInsets$1(View view) {
        this.$this_onApplyWindowInsets = view;
    }

    @Override // kotlinx.coroutines.DisposableHandle
    public final void dispose() {
        this.$this_onApplyWindowInsets.setOnApplyWindowInsetsListener(null);
    }
}
