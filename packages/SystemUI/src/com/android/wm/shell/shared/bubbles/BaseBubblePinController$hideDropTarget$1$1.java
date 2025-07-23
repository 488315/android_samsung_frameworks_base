package com.android.wm.shell.shared.bubbles;

import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
