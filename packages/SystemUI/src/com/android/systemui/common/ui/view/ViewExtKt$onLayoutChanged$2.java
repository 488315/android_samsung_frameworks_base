package com.android.systemui.common.ui.view;

import android.view.View;
import kotlinx.coroutines.DisposableHandle;

/* loaded from: classes.dex */
public final class ViewExtKt$onLayoutChanged$2 implements DisposableHandle {
    public final /* synthetic */ View.OnLayoutChangeListener $listener;
    public final /* synthetic */ View $this_onLayoutChanged;

    public ViewExtKt$onLayoutChanged$2(View view, View.OnLayoutChangeListener onLayoutChangeListener) {
        this.$this_onLayoutChanged = view;
        this.$listener = onLayoutChangeListener;
    }

    @Override // kotlinx.coroutines.DisposableHandle
    public final void dispose() {
        this.$this_onLayoutChanged.removeOnLayoutChangeListener(this.$listener);
    }
}
