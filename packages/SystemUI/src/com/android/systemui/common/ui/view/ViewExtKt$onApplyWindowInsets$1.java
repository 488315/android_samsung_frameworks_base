package com.android.systemui.common.ui.view;

import android.view.View;
import kotlinx.coroutines.DisposableHandle;

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
