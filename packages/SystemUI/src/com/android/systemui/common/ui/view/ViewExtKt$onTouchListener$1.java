package com.android.systemui.common.ui.view;

import android.view.View;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ViewExtKt$onTouchListener$1 implements DisposableHandle {
    public final /* synthetic */ View $this_onTouchListener;

    public ViewExtKt$onTouchListener$1(View view) {
        this.$this_onTouchListener = view;
    }

    @Override // kotlinx.coroutines.DisposableHandle
    public final void dispose() {
        this.$this_onTouchListener.setOnTouchListener(null);
    }
}
