package com.android.wm.shell.shared.bubbles;

import android.view.View;

/* loaded from: classes3.dex */
public final class BaseBubblePinController$hideDropTarget$1$1 implements Runnable {
    public final /* synthetic */ View $view;
    public final /* synthetic */ BaseBubblePinController this$0;

    public BaseBubblePinController$hideDropTarget$1$1(BaseBubblePinController baseBubblePinController, View view) {
        this.this$0 = baseBubblePinController;
        this.$view = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.this$0.removeDropTargetView(this.$view);
    }
}
